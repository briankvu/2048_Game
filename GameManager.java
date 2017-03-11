/*
 * Name: Brian Vu
 * Date April 23, 2015
 * File: GameManager.java
 *
 * Description: Contains the essentials for the function of 2048. It provides
 * the necessary commands and controls to play the game
 */

import java.util.*;
import java.io.*;

public class GameManager
{
    // Instance variables
    private Board board;    // The actual 2048 board
    private String outputFileName;  // File to save the board to when exiting
    
    /*
     * Name: GameManager (ctor)
     * Purpose: Generates a new board with given parameter sizes
     * Parameters: boardSize is of type integer and it represents the board's
     *        size. outputBoard is of String type and it represents a 
     *        file to hold the grid. random is of type Random and it is where
     *        we get the random number generator from.
     * Return: void
     */
    
    GameManager(int boardSize, String outputBoard, Random random)
      throws IOException
    { 
      //Creates the board using the given file
      this.outputFileName = outputBoard;
      this.board = new Board(boardSize, random);
      
    }

    /*
     * Name: GameManager
     * Purpose: Load a saved game using the filename passed in the parameter
     * Parameters: inputBoard is of type String and it represents the file 
     *        containing the saved game. outputBoard is a String type and it
     *        represents a file to hold the grid. random is of type Random 
     *        and it represents the class to which we get the random number
     * Return: void
     */
 
    GameManager(String inputBoard, String outputBoard, Random random)
        throws IOException
    {
      //Loads a board using the given filename
      this.board = new Board(inputBoard, random);
      this.outputFileName = outputBoard;
    }

    /*
     * Name: play() (method)
     * Purpose: This method displays the necessary controls and the actual
     *        board to start the game on. Takes in input from the user to 
     *        specify moves to execute "w,s,a,d,q" moves. 
     *        Also allows for user to save game and quit. Displays 
     *        annotations such as "Game Over!" to the terminal.
     * Parameters: None
     * Return: void
     */

    public void play() throws IOException
    {
      Scanner input = new Scanner(System.in);
      //Displays the list of controls
      this.printControls();
      //Prints a blank rows
      System.out.println();

      //Creates a boolean to see if conditions are true to keep the loop 
      //going
      boolean running = true;
     
     //Loops through while checking if conditions are true
      while(running){
        //Displays a 2048 board in the game
        System.out.print(board.toString());
        //Prompts the user to input another command
        System.out.print("> "); 
        //Creates a series of boolean variables to verify if the tiles are
        //able to move
        boolean moveUp = board.canMove(Direction.UP);
        boolean moveDown = board.canMove(Direction.DOWN);
        boolean moveLeft = board.canMove(Direction.LEFT);
        boolean moveRight = board.canMove(Direction.RIGHT);
      
        //Initializes a new string
        String movement = input.next();
        //Checks to see if the move up is valid and if it is true, then 
        //it will move and implement a random tile
        if(movement.equals("w") && moveUp){
          board.move(Direction.UP);
          board.addRandomTile();
        }
        //Checks to see if the move down is valid and if it is true, then
        //it will move and implement a random tile
        if(movement.equals("s") && moveDown){
          board.move(Direction.DOWN);
          board.addRandomTile();
        }
        //Checks to see if the move left is valid and if it is true, then
        //it will move and implement a random tile
        if(movement.equals("a") && moveLeft){
          board.move(Direction.LEFT);
          board.addRandomTile();
        }
        //Checks to see if the move right is valid and if it is true, then 
        //it will move and implement a random tile
        if(movement.equals("d") && moveRight){
          board.move(Direction.RIGHT);
          board.addRandomTile();
        }
        //Quits the game
        if(movement.equals("q")){
          running = false;
          board.saveBoard(this.outputFileName);
        }
        //Displays the controls again 
        else{
          this.printControls();

        }
        //Checks if method returns true and prints out the phrase "Game Over!"
        //if true
        if(board.isGameOver()){
          System.out.print("Game Over!");
        }
    }
    
}   /*
     * Name: printControls()
     * Purpose: Prints the controls for the game.
     * Parameters: None
     * Return: void
     */
    // Print the Controls for the Game 
    private void printControls()
    {
        System.out.println("  Controls:");
        System.out.println("    w - Move Up");
        System.out.println("    s - Move Down");
        System.out.println("    a - Move Left");
        System.out.println("    d - Move Right");
        System.out.println("    q - Quit and Save Board");
        System.out.println();
    }
}
