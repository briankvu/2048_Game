
/*
 * Name: Brian Vu
 * Login: cs8bkq
 * Date April 30, 2015
 * File: Board.java
 * Sources: Elaine, Tutors
 *
 * Description: This program is used to construct a new board with random
 * tiles to be implemented into the game 2048. The program will also allow
 * the board to be loaded and saved. 
 */
 
 

import java.util.*;
import java.io.*;
/*
 * Name: Board
 * Purpose: To construct a new board with randomly placed tiles that can be
 *      loaded and saved in order to create the layout for the game.
 * Parameters: None
 * Return: void
 */

public class Board
{
    public final int NUM_START_TILES = 2;
    public final int TWO_PROBABILITY = 90;
    public final int GRID_SIZE;


    private final Random random;
    private int[][] grid;
    private int score;

   /*
    * Name: Board (ctor)
    * Purpose: Constructs a new board with random tiles.
    * Parameters: boardSize is a type integer that represents the size of the
    *          size of the board being made. random is a variable of type 
    *          Random that represents the class where we get the random number generator from.
    * Return: void
    */
 
    public Board(int boardSize, Random random)
    {
      //Initializes the instance variables 
      this.random = random;
      this.GRID_SIZE = boardSize;
      //Initializes the 2D array and score variable
      this.grid = new int[this.GRID_SIZE][this.GRID_SIZE];
      this.score = 0;
      
      //Loops through twice while assigning a tile to a randomized space
      for(int i = 0; i < NUM_START_TILES; i++){
        this.addRandomTile();
      }       
    }

   /*
    * Name: Board (ctor)
    * Purpose: Constructs a board based off of an input file.
    * Parameters: inputBoard is of type String and it represents the file
    *          name to be used for the scanner. random is of type Random that 
    *          represents the class to which we get the random number 
    *          generator from.
    * Return: void
    */
     
    public Board(String inputBoard, Random random) throws IOException
    {
      //Creates a scanner to read through the file
      Scanner input = new Scanner(new File(inputBoard));
      //Initializes the instance variables as the file is being read by the
      //scanner
      this.GRID_SIZE = input.nextInt();
      this.score = input.nextInt();
      this.random = random;
      //Initializes the 2D array 
      this.grid = new int[this.GRID_SIZE][this.GRID_SIZE];
      
      //Loops through the board's row first and then column while loading
      //each value into the grid
      for(int x = 0; x < this.GRID_SIZE; x++){
        for(int y = 0; y < this.GRID_SIZE; y++){
          this.grid[x][y] = input.nextInt();
        }
      }
    }

   /*
    * Name: saveBoard (method)
    * Purpose: Saves the current board to a file.
    * Parameters: outputBoard is of type String and represents the filename
    *         to which we save the board to.
    * Return: void
    */
 
    public void saveBoard(String outputBoard) throws IOException
    {
      //Implements class PrintWriter with designated file name
      PrintWriter output =  new PrintWriter(outputBoard);
      //Prints out each variable and saves it to the file
      output.println(this.GRID_SIZE);
      output.println(this.score);
      
      //Loops through the board's row first and then the column   
      for(int x = 0; x < GRID_SIZE; x++){
        for(int y = 0; y < GRID_SIZE; y++){
           //Prints the grid's row and column and prints a space in between
           //each column
           output.print(this.grid[x][y]);
           output.print(" ");
        }
        //Prints a blank line between each row on the grid
        output.println();
      }
      //Ends the PrintWriter
      output.close();
    }

   /*
    * Name: addRandomTile()
    * Purpose: Adds a random tile of value 2 or 4 to a random empty space
    *       on the board.
    * Parameters: None
    * Return: void
    */
    
    public void addRandomTile()
    {
      //Initializes a variable to count number of available tiles
      int count = 0;
      
      //Loops through the board's rows and columns
      for(int i = 0; i < GRID_SIZE; i++){
        for(int j = 0; j < GRID_SIZE; j++){
          //Checks to see if there is is an available tile and increments the 
          //count if there is an available tile
          if(this.grid[i][j] == 0){
            count++;
          }
        }
      }
      //Checks to see if there is no available tiles and exits if there are 
      //no more available tiles
      if(count == 0){
        return;
      }
        
      //Creates a new integer between 0 and the count subtracted by 1
      int location = random.nextInt(count);
      //Creates a new integer between 0 and 99
      int value = random.nextInt(100);
      //Creates a counter to keep track of the number of empty spaces
      int emptySpace = 0;
      
      //Loops through the rows and columns of the board's grid
      for(int x = 0; x < GRID_SIZE; x++){ 
        for(int y = 0; y < GRID_SIZE; y++){
          //Checks to see if there is an available tile 
          
          if(this.grid[x][y] == 0){
            //Checks to see if the integer location is equivalent to the
            //empty space counter
            if(location == emptySpace){ 
              //Places a number 2 if the random integer is less than 90%
              //otherwise it places a number 4 
              if(value < TWO_PROBABILITY){
                this.grid[x][y] = 2; 
              }            
              else{
                this.grid[x][y] = 4;
              }
            }
            //Increments the empty space counter
            emptySpace++;
          }    
        } 
      } 
    }
   /*
    * Name: isGameOver() (method)
    * Purpose: Method that checks to see if the game is over meaning
    *       there are no valid moves left.
    * Parameters: None
    * Return: boolean. True represents the game being over and false 
    *       represents the game being not over.
    */
    
    public boolean isGameOver()
    {
       //Checks to see if tile can move up, down, right, or left and
       //returns false if possible moves exist
       if((this.canMove(Direction.UP)) || (this.canMove(Direction.DOWN)) ||
        (this.canMove(Direction.RIGHT)) || (this.canMove(Direction.LEFT))){
          return false;
       }
        //If moves don't exist, then return true meaning the game ends
        else{
        return true;
        }
    }

   
   /*
    * Name: canMove() (method)
    * Purpose: Determines if we can move in a given direction
    * Parameters: direction is of type Direction which represents variables
    *          initialized in the Direction class.
    * Return: boolean which represents true if we can move in the desired
    *      direction.
    */
    public boolean canMove(Direction direction)
    {
         //Checks to see if tile can move right and returns true if it can
         if(direction == Direction.RIGHT){
           return this.canMoveRight();
          }
          //Checks to see if tile can move left and returns true if it can
          if(direction == Direction.LEFT){
            return this.canMoveLeft();
          }
          //Checks to see if tile can move up and returns true if it can
          if(direction == Direction.UP){
            return this.canMoveUp();
          }
          //Checks to see if tile can move down and returns true if it can
          if(direction == Direction.DOWN){
            return this.canMoveDown();
          }  
       //Returns false if the tile cannot move in any of the four directions
       return false;
      }
   
   /*
    * Name: canMoveRight() (helper method)
    * Purpose: Used to split up canMove() method and focuses on seeing if
    *       the tile is able to move to the right
    * Parameters: None
    * Return: boolean variable compareFin represents the comparison between
    *       the two other boolean variables to see whether the method would 
    *       return true allowing the tile to move or false not allowing
    *       it to move
    *
    */

   private boolean canMoveRight()
   {
     //Loops through the rows and columns of the board 
     for(int row = 0; row < GRID_SIZE; row++){
      for(int col = 0; col < GRID_SIZE-1; col++){
        
        //Checks if there is an empty space or an identical value to the
        //right of the tile and returns true if there is
        if(this.grid[row][col+1] == 0 || 
          this.grid[row][col] == this.grid[row][col+1]){ 
          //Checks if the current tile is not empty and if the tile next
          //to it is not empty
          if(this.grid[row][col] == 0 && this.grid[row][col+1] == 0){
            return true;
          }
        }
      }
     }
    return false;
   }
   
   
   /*
    * Name: canMoveLeft() (helper method)
    * Purpose: Used to split up canMove() method and focuses on seeing if
    *       the tile is able to move to the left
    * Parameters: None
    * Return: boolean variable compareFin represents the comparison between
    *       the two other boolean variables to see whether the method would 
    *       return true allowing the tile to move or false not allowing
    *       it to move
    *
    */

   private boolean canMoveLeft()
   {
     //Loops through the rows and the columns of the board
     for(int x = 0; x < GRID_SIZE; x++){
      for(int y = 1; y < GRID_SIZE; y++){
     
        //Checks if there is an empty space or an identical value to the
        //top of the tile and returns true if there is
        if(this.grid[x][y-1] == 0 || this.grid[x][y] == this.grid[x][y-1]){
          //Checks if the current tile is not empty and if the tile next
          //to it is not empty
          if(this.grid[x][y] != 0 && this.grid[x][y-1] != 0){
            return true;
          }
        }
      }
     }
    return false;
   }

   
   /*
    * Name: canMoveUp() (helper method)
    * Purpose: Used to split up canMove() method and focuses on seeing if
    *       the tile is able to move to the up
    * Parameters: None
    * Return: boolean variable compareFin represents the comparison between
    *       the two other boolean variables to see whether the method would 
    *       return true allowing the tile to move or false not allowing
    *       it to move
    *
    */

   private boolean canMoveUp()
   {  
     //Loops through the rows and the columns of the board
     for(int x = 1; x < GRID_SIZE; x++){
      for(int y = 0; y < GRID_SIZE; y++){
        
        //Checks if there is an empty space or an identical value to the
        //top of the tile and returns true if there is
        if(this.grid[x-1][y] == 0 || this.grid[x][y] == this.grid[x-1][y]){
          //Checks if the current tile is not empty and if the tile next
          //to it is not empty
          if(this.grid[x][y] != 0 && this.grid[x-1][y] != 0){
            return true;
          }
        }
      }
     }
    //Returns false if none of the conditions are met above
    return false;
   }

   
   /*
    * Name: canMoveDown() (helper method)
    * Purpose: Used to split up canMove() method and focuses on seeing if
    *       the tile is able to move to the down
    * Parameters: None
    * Return: boolean variable compareFin represents the comparison between
    *       the two other boolean variables to see whether the method would 
    *       return true allowing the tile to move or false not allowing
    *       it to move
    *
    */

   private boolean canMoveDown()
   { 
     //Loops through the rows and the columns of the board
     for(int x = 0; x < GRID_SIZE-1; x++){
      for(int y = 0; y < GRID_SIZE; y++){
     
        //Checks if there is an empty space or an identical value to the
        //bottom of the tile and returns true if there is
        if(this.grid[x+1][y] == 0 || this.grid[x][y] == this.grid[x+1][y]){
          //Checks if the tile is an empty space and if the one under it
          //is also an empty space and returns false
          if(this.grid[x][y] == 0 && this.grid[x+1][y] == 0){
            return true;
          }
        }
      }
     }
    //Returns false if none of the conditions are met above
    return false;
   }

   // TODO PA4
   /*
    * Name: move() (method)
    * Purpose: Performs a move operation
    * Parameter: direction is of type Direction which represents the
    *        variables in the Direction class.
    * Return: boolean which represents true verifying the move operation. 
    */

    public boolean move(Direction direction)
    {

      if(direction.equals(Direction.UP)){
        this.moveUp();
        return true;
      }
      else if(direction.equals(Direction.DOWN)){
        this.moveDown();
        return true;
      }
      else if(direction.equals(Direction.RIGHT)){
        this.moveRight();
        return true;
      }
      else if(direction.equals(Direction.LEFT)){
        this.moveLeft();
        return true; 
      }
      else{
      return false;
      }
    }
   
   /*
    * Name: moveUp() (helper method)
    * Purpose: allows the tiles to move up
    * Parameters: None
    * Return: boolean which represents the tile being able to move when true
    */

   private boolean moveUp()
    {
      //This loops through the columns of the board
      for(int col = 0; col < GRID_SIZE; col++){
        //Creates a new arraylist of type integer
        ArrayList<Integer> colArray = new ArrayList<Integer>();
        //Loops through the rows of the board
        for(int row = 0; row < GRID_SIZE; row++){

          //Checks if the value on the board is not zero and adds it to the
          //array list
          if(this.grid[row][col] != 0){

            colArray.add(this.grid[row][col]);
          }
          //This loops through the full length of the array list
          for(int num = 0; num < colArray.size() - 1; num++){
            //Checks if the number and the number next to it are the same
            if(colArray.get(num) == colArray.get(num + 1)){
              //Adds the two identical tiles together
              colArray.set(num,(colArray.get(num)) + (colArray.get(num + 1)));

              //Removes the element on the right that was added to shift
              //the array list
              colArray.remove(num + 1);
              //Increments the score by the sum of the two numbers
              this.score += colArray.set(num, (colArray.get(num)));
            }
          }    
        }
    
        //Loops through the size difference of the board and the array list
        //and adds in zero
        while(colArray.size() < GRID_SIZE){
          colArray.add(0);
        }
        int newNum = 0;
        //
        for(int newRow = 0; newRow < colArray.size(); newRow++){
          this.grid[newRow][col] = colArray.get(newNum);
          newNum++;
        }
      }
     
     return true;
    } 

   /*
    * Name: moveDown() (helper method)
    * Purpose: allows the tiles to move down
    * Parameters: None
    * Return: boolean which represents true if tile can move
    */
   private boolean moveDown()
   {
     //This loops through the columns of the board
     for(int col = 0; col < GRID_SIZE; col++){
        //Creates a new arraylist of type integer
        ArrayList<Integer> colArray = new ArrayList<Integer>();
        //Loops through the rows of the board
        for(int row = 0; row < GRID_SIZE; row++){

          //Checks if the value on the board is not zero and adds it to the
          //array list
          if(this.grid[row][col] != 0){

            colArray.add(this.grid[row][col]);
          }
          //This loops through the full length of the array list
          for(int num = 0; num < colArray.size() - 1; num++){
            //Checks if the number and the number next to it are the same
            if(colArray.get(num) == colArray.get(num + 1)){
              //Adds the two identical tiles together
              colArray.set(num,(colArray.get(num)) + (colArray.get(num + 1)));

              //Removes the element on the right that was added to shift
              //the array list
              colArray.remove(num + 1);
              //Increments the score by the sum of the two numbers
              this.score += colArray.set(num, (colArray.get(num)));
            }
          }    
        }
    
        //Loops through the size difference of the board and the array list
        //and adds in zero
        while(colArray.size() < GRID_SIZE){
          colArray.add(0);
        }
        int newNum = 0;
        //
        for(int newRow = 0; newRow < colArray.size(); newRow++){
          this.grid[newRow][col] = colArray.get(newNum);
          newNum++;
        }
      }
     
     return true;
   } 

   /*
    * Name: moveRight() (helper method)
    * Purpose: allows the tiles to move right
    * Parameters: None
    * Return: boolean which represents true if tile can move
    */

   private boolean moveRight()
    {
      //This loops through the columns of the board
      for(int col = 0; col < GRID_SIZE; col++){
        //Creates a new arraylist of type integer
        ArrayList<Integer> colArray = new ArrayList<Integer>();
        //Loops through the rows of the board
        for(int row = 0; row < GRID_SIZE; row++){

          //Checks if the value on the board is not zero and adds it to the
          //array list
          if(this.grid[row][col] != 0){

            colArray.add(this.grid[row][col]);
          }
          //This loops through the full length of the array list
          for(int num = 0; num < colArray.size() - 1; num++){
            //Checks if the number and the number next to it are the same
            if(colArray.get(num) == colArray.get(num + 1)){
              //Adds the two identical tiles together
              colArray.set(num,(colArray.get(num)) + (colArray.get(num + 1)));

              //Removes the element on the right that was added to shift
              //the array list
              colArray.remove(num + 1);
              //Increments the score by the sum of the two numbers
              this.score += colArray.set(num, (colArray.get(num)));
            }
          }    
        }
    
        //Loops through the size difference of the board and the array list
        //and adds in zero
        while(colArray.size() < GRID_SIZE){
          colArray.add(0);
        }
        int newNum = 0;
        //
        for(int newRow = 0; newRow < colArray.size(); newRow++){
          this.grid[newRow][col] = colArray.get(newNum);
          newNum++;
        }
      }
     
     return true;
  
    } 

    /*
     * Name: moveLeft() (helper method)
     * Purpose: allows the tiles to move left
     * Parameters: boolean which represents true if tiles can move
     */

    private boolean moveLeft()
    {
       //This loops through the columns of the board
      for(int col = 0; col < GRID_SIZE; col++){
        //Creates a new arraylist of type integer
        ArrayList<Integer> colArray = new ArrayList<Integer>();
        //Loops through the rows of the board
        for(int row = 0; row < GRID_SIZE; row++){

          //Checks if the value on the board is not zero and adds it to the
          //array list
          if(this.grid[row][col] != 0){

            colArray.add(this.grid[row][col]);
          }
          //This loops through the full length of the array list
          for(int num = 0; num < colArray.size() - 1; num++){
            //Checks if the number and the number next to it are the same
            if(colArray.get(num) == colArray.get(num + 1)){
              //Adds the two identical tiles together
              colArray.set(num,(colArray.get(num)) + (colArray.get(num + 1)));

              //Removes the element on the right that was added to shift
              //the array list
              colArray.remove(num + 1);
              //Increments the score by the sum of the two numbers
              this.score += colArray.set(num, (colArray.get(num)));
            }
          }    
        }
    
        //Loops through the size difference of the board and the array list
        //and adds in zero
        while(colArray.size() < GRID_SIZE){
          colArray.add(0);
        }
        int newNum = 0;
        //
        for(int newRow = 0; newRow < colArray.size(); newRow++){
          this.grid[newRow][col] = colArray.get(newNum);
          newNum++;
        }
      }
     
     return true;

    } 


   /*
    * Name: getGrid() (method)
    * Purpose: Returns the reference to the 2048 grid.
    * Parameter: None
    * Return: int[][] which represents the grid in which the integers
    * are placed on the board.
    */
    
    public int[][] getGrid()
    {
        return grid;
    }

   /*
    * Name: getScore() (method)
    * Purpose: Returns the score.
    * Parameter: None
    * Return: int refers to the instance variable score 
    */
    
    public int getScore()
    {
        return score;
    }

    /*
     * Name: toString() (method)
     * Purpose: Used to convert an object to a String
     * Parameter: None
     * Return: String which refers to String that is made after the object
     *       converted.
     */
    @Override
    public String toString()
    {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        
        //Loops through row and column of the grid
        for (int row = 0; row < GRID_SIZE; row++)
        {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                                    String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }
}
