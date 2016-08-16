/*
 * Name: Brian Vu
 * Login: cs8bkq
 * Date: May 28, 2015
 * File: Gui2048.java
 * Sources of Help: Elaine, Tutors, docs.oracle.com
 *
 * Description: This program is designed to create a display interface for
 *            the game 2048. It contains all of the necessary designs and 
 *            customizations to make the game look good aesthetically and
 *            allows the game to run smoothly using Events and JavaFx
 */

/** Gui2048.java */
/** PA8 Release */

import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;


/*
 * Name: Gui2048 (class)
 * Purpose: Displays an interface for the game 2048 and contains all the 
 *          necessary designs and customizations to make the game look
 *          good and function smoothly using Events and JavaFx
 * Parameters: None
 * Return: void
 */

public class Gui2048 extends Application {

    private String outputBoard; // The filename for where to save the Board
    private Board board; // The 2048 Game Board
    
    //Instance variables
    private int GRID_SIZE;
    private GridPane pane;
    private Tile[][] tileArray;
    private int[][] grid;
    private Text scoreText;
    private boolean checker;

    
    // Fill colors for each of the Tile values
    private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218, 0.35);
    private static final Color COLOR_2 = Color.rgb(238, 228, 218);
    private static final Color COLOR_4 = Color.rgb(237, 224, 200);
    private static final Color COLOR_8 = Color.rgb(242, 177, 121);
    private static final Color COLOR_16 = Color.rgb(245, 149, 99);
    private static final Color COLOR_32 = Color.rgb(246, 124, 95);
    private static final Color COLOR_64 = Color.rgb(246, 94, 59);
    private static final Color COLOR_128 = Color.rgb(237, 207, 114);
    private static final Color COLOR_256 = Color.rgb(237, 204, 97);
    private static final Color COLOR_512 = Color.rgb(237, 200, 80);
    private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
    private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
    private static final Color COLOR_OTHER = Color.BLACK;
    private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);
    private static final Color COLOR_BACKGROUND = Color.rgb(100, 173, 160);

    private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242); // For tiles >= 8
    private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101); // For tiles < 8

    
    //Integers to hold magic numbers
    int num2 = 2;
    int num4 = 4;
    int num8 = 8;
    int num16 = 16;
    int num32 = 32;
    int num64 = 64; 
    int num128 = 128;
    int num256 = 256;
    int num512 = 512;
    int num1024 = 1024;
    int num2048 = 2048;

   

    

    /*
     * Name: start() (method)
     * Purpose: Sets the stage, scene, and specifies their design with size
     *          and color
     * Parameters: primaryStage is of type Stage and represents the stage
     *             that is set for the game interface
     * Return: void
     */

    @Override
    public void start(Stage primaryStage) {

      // Process Arguments and Initialize the Game Board
      processArgs(getParameters().getRaw().toArray(new String[0]));
        
      //Sets the grid size to zero
      this.GRID_SIZE = 4;
      
      //Creates a new GridPane object
      pane = new GridPane();
        
      //Makes sure the pane is centered and sets spacing and padding
      //between columns and rows
      pane.setAlignment(Pos.CENTER);
      pane.setPadding(new Insets(11.5,12.5,13.5,14.5));
      pane.setHgap(5.5);
      pane.setVgap(5.5);
        


      //Sets the background color
      pane.setStyle("-fx-background-color: rgb(100, 173, 160)");


      //Creates a new Scene object
      Scene scene = new Scene(pane);
        
        
      //Creates the title of the game and sets the scene into the stage.
      // Then, it displays the stage
      primaryStage.setTitle("Gui2048");
      primaryStage.setScene(scene);
      primaryStage.setHeight(600);
      primaryStage.setWidth(500);
      primaryStage.show();

        

      //Instantiates the key handler to process keys when pressed
      scene.setOnKeyPressed(new myKeyHandler());
       
      //Creates a new Tile object with full row and column length
      tileArray = new Tile[GRID_SIZE][GRID_SIZE];
      
      //Creates a new integer array after getting the grid
      int[][] board = this.board.getGrid();
      
      //Creates a new object of type Rectangle
      Rectangle tileShape = new Rectangle();
      //Sets the width and height of the tile and sets the color to the same
      //as the background
      tileShape.setWidth(100);
      tileShape.setHeight(100);
      tileShape.setFill(COLOR_BACKGROUND);
      
  
      //Sets the title of the game in black
      Text tileText = new Text("2048");
      tileText.setFill(Color.BLACK);
      
      //Sets tje font of the title
      tileText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 35));
      
      //Creates a new object of type Rectangle representing the box around
      //the score value and sets it to the same as the background color
      Rectangle scoreBox = new Rectangle(50, 50);
      scoreBox.setFill(COLOR_BACKGROUND);
      
      //Sets the score's text and sets its font and color to black
      scoreText = new Text("Score: " +
                        Integer.toString(this.board.getScore()));
      scoreText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
      scoreText.setFill(Color.BLACK);

      //Sets the tile at a position and centers it horizontally and verticallly
      this.pane.add(tileShape, 0, 0);
      GridPane.setHalignment(tileShape, HPos.CENTER);
      GridPane.setValignment(tileShape, VPos.CENTER);

      
      //Sets the tile at a position and centers it horizontally and verticallly
      this.pane.add(tileText, 0, 0);
      GridPane.setHalignment(tileText, HPos.CENTER);
      GridPane.setValignment(tileText, VPos.CENTER);

      //Sets the tile at a position and centers it horizontally and verticallly
      this.pane.add(scoreBox, this.board.GRID_SIZE-1, 0);
      GridPane.setHalignment(scoreBox, HPos.CENTER);
      GridPane.setValignment(scoreBox, VPos.CENTER);

      //Sets the tile at a position and centers it horizontally and verticallly
      this.pane.add(scoreText, this.board.GRID_SIZE-1, 0);
      GridPane.setHalignment(scoreText, HPos.CENTER);
      GridPane.setValignment(scoreText, VPos.CENTER);
      
      
      
      //Loops through the rows and columns
      for(int row = 0; row < GRID_SIZE; row++) {
        for(int col = 0; col < GRID_SIZE; col++) {
          
          //Sets the instance variable grid to the values of the grid
          this.grid = this.board.getGrid();
          //Creates an int variable to hold the grid's values
          int numVal = this.grid[row][col];

          //Creates a temporary tile to store the board's row and column values
          Tile tempTile = new Tile(board[row][col]);
          tileArray[row][col] = tempTile;
          
          //Sets the font of the tile's text
          tileArray[row][col].getText().setFont(Font.font("Times New Roman",
                              FontWeight.BOLD, 30));
          
          //Sets the tiles and their number onto the board and centers them
          this.pane.add(tileArray[row][col].shape, col, row+1);
          this.pane.add(tileArray[row][col].text, col, row+1);
          GridPane.setHalignment(tileArray[row][col].text, HPos.CENTER);
          GridPane.setValignment(tileArray[row][col].text, VPos.CENTER);
          
        }
      }
    }


    
     /* Name: updateBoard() (method)
     * Purpose: Updates the board 
     * Parameters: None
     * Return: void
     */

    public void updateBoard() {
      
      //Loops through the rows and columns
      for(int row = 0; row < GRID_SIZE; row++) {
        for(int col = 0; col < GRID_SIZE; col++) {
          
          //Sets the instance variable grid to the values of the grid
          this.grid = this.board.getGrid();
          //Creates an int variable to hold the grid's values
          int numVal = this.grid[row][col];          
          
          //These if/else if statements check if the tile is a certain 
          //value and sets the tile to its corresponding color and text. 
          //Then, it sets the font for the text
          
          if(numVal == 0) {
            tileArray[row][col].getRectangle().setFill(COLOR_EMPTY);
            tileArray[row][col].getText().setText(" ");
            tileArray[row][col].getText().setFont(Font.font("Times New Roman", 
                                FontWeight.BOLD, 30));
          }

          else if(numVal == num2) {
            tileArray[row][col].getRectangle().setFill(COLOR_2);
            tileArray[row][col].getText().setText("" + numVal);
            tileArray[row][col].getText().setFont(Font.font("Times New Roman", 
                                FontWeight.BOLD, 30));
          }
          
          else if(numVal == num4) {
            tileArray[row][col].getRectangle().setFill(COLOR_4);
            tileArray[row][col].getText().setText("" + numVal);
            tileArray[row][col].getText().setFont(Font.font("Times New Roman", 
                                FontWeight.BOLD, 30));
          }
          else if(numVal == num8) {
            tileArray[row][col].getRectangle().setFill(COLOR_8);
            tileArray[row][col].getText().setText("" + numVal);
            tileArray[row][col].getText().setFont(Font.font("Times New Roman", 
                                FontWeight.BOLD, 30));
          }
         
          else if(numVal == num16) {
            tileArray[row][col].getRectangle().setFill(COLOR_16);
            tileArray[row][col].getText().setText("" + numVal);
            tileArray[row][col].getText().setFont(Font.font("Times New Roman", 
                                FontWeight.BOLD, 30));
          }
          else if(numVal == num32) {
            tileArray[row][col].getRectangle().setFill(COLOR_32);
            tileArray[row][col].getText().setText("" + numVal);
            tileArray[row][col].getText().setFont(Font.font("Times New Roman", 
                                FontWeight.BOLD, 30));
          }

          else if(numVal == num64) {
            tileArray[row][col].getRectangle().setFill(COLOR_64);
            tileArray[row][col].getText().setText("" + numVal);
            tileArray[row][col].getText().setFont(Font.font("Times New Roman", 
                                FontWeight.BOLD, 30));
          }

        
          else if(numVal == num128) {
            tileArray[row][col].getRectangle().setFill(COLOR_128);
            tileArray[row][col].getText().setText("" + numVal);
            tileArray[row][col].getText().setFont(Font.font("Times New Roman", 
                                FontWeight.BOLD, 30));
          }

        
          else if(numVal == num256) {
            tileArray[row][col].getRectangle().setFill(COLOR_256);
            tileArray[row][col].getText().setText("" + numVal);
            tileArray[row][col].getText().setFont(Font.font("Times New Roman", 
                                FontWeight.BOLD, 30));
          }

          else if(numVal == num512) {
            tileArray[row][col].getRectangle().setFill(COLOR_512);
            tileArray[row][col].getText().setText("" + numVal);
            tileArray[row][col].getText().setFont(Font.font("Times New Roman", 
                                FontWeight.BOLD, 30));
          }
          else if(numVal == num1024) {
             tileArray[row][col].getRectangle().setFill(COLOR_1024);
             tileArray[row][col].getText().setText("" + numVal);
             tileArray[row][col].getText().setFont(Font.font("Times New Roman", 
                                FontWeight.BOLD, 30));
          }
          else if(numVal == num2048) {
             tileArray[row][col].getRectangle().setFill(COLOR_2048);
             tileArray[row][col].getText().setText("" + numVal);
             tileArray[row][col].getText().setFont(Font.font("Times New Roman", 
                                FontWeight.BOLD, 30));
          }
        }
      }
    }
    
   /*
    * Name: updateScore() (method)
    * Purpose: Updates the score
    * Parameters: None
    * Return: void
    */
    
    public void updateScore() {
      
      //Sets the score 
      this.scoreText.setText("Score: " + board.getScore());  
    }
    
    
    
    /*
     * Name: myKeyHandler (class)
     * Purpose: Manages keypresses and allows each keypress input to 
     *          perform an action. Also updates the board and the score
     *          as each move is implemented
     * Parameter: None
     * Return: None
     */
    
    private class myKeyHandler implements EventHandler<KeyEvent> {
      
      
      @Override
      public void handle(KeyEvent e) { 
        
        //These if statements check to see if the user enters direction
        //left, right, up, or down on the keyboard and then checks to see
        //if the input is valid and moves the tile. It adds a random tile
        //and prints out the corresponding statement of which direction
        //the tile is moving

        if(e.getCode().equals(KeyCode.LEFT) && 
          board.canMove(Direction.LEFT)) {
          board.move(Direction.LEFT);
          board.addRandomTile();

          System.out.println("Moving Left");
                      
        }
        
        if(e.getCode().equals(KeyCode.RIGHT) && 
          board.canMove(Direction.RIGHT)) {
          board.move(Direction.RIGHT);
          board.addRandomTile();
          
          System.out.println("Moving Right");
        }

        if(e.getCode().equals(KeyCode.UP) && 
          board.canMove(Direction.UP)) {
          board.move(Direction.UP);
          board.addRandomTile();
    
          System.out.println("Moving Up");
        }
      

        if(e.getCode().equals(KeyCode.DOWN) && 
          board.canMove(Direction.DOWN)) {
          board.move(Direction.DOWN);
          board.addRandomTile();
     
          System.out.println("Moving Down");
        }
        
        if(e.getCode().equals(KeyCode.S)) {
          try {
              board.saveBoard(outputBoard);
          } catch (IOException exception) {
          System.out.println("saveBoard threw an Exception");
          }
          System.out.println("Saving Board to" + outputBoard);
        }
        
        //Checks to see if the user cannot move anymore in any direction
        //and prints out "Game Over".
        if(board.isGameOver()) {
         
          Text endGame = new Text("Game Over!");
          
          Rectangle endTile = new Rectangle();

          pane.add(endTile, 0, 0, 4, 5);
          pane.add(endGame, 0, 0, 4, 5);

          //Sets the color of the Game Over screen
          endTile.setFill(COLOR_GAME_OVER);
          endGame.setFill(Color.BLACK);
          //Sets the font of the text
          endGame.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
          //Sets the tile on the board and binds it
          endTile.heightProperty().bind(pane.heightProperty());
          endTile.widthProperty().bind(pane.widthProperty());

          //Centers the text and tile both horizontally and vertically
          GridPane.setHalignment(endGame, HPos.CENTER);
          GridPane.setValignment(endGame, VPos.CENTER);
          GridPane.setHalignment(endTile, HPos.CENTER);
          GridPane.setValignment(endTile, VPos.CENTER);
         
        }
        
          //Updates the score and the board
          updateScore();
          updateBoard();
      }
    } 

    /*
     * Name: Tile (class)
     * Purpose: Contains the properties of the tile such as its color and
     *          dimensions. Includes the getter and setter methods
     * Parameters: None
     * Return: void
     */

    public class Tile {
      
      public Text text;
      public Rectangle shape;
      
      /*
       * Name: Tile (ctor)
       * Purpose: Contains the properties of the tile such as its color and
       *       dimensions. Includes the getter and setter methods
       * Parameters: tileVal is of type int and it represents the number
       *              on the tile
       * Return: type Tile returns a tile
       */

    public Tile(int tileVal) {
      
      //Creates an object of Rectangle type
      this.shape = new Rectangle();
      
      //Sets the width and height to be 100
      this.shape.setWidth(100);
      this.shape.setHeight(100);
      this.text = new Text();
      this.text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));        

        
      //Determines whether the number on the tile is a certain number and 
      //sets the corresponding colors
      
      if(tileVal == 0) {
        this.text = new Text(" ");
      }
      else {
        this.text = new Text(String.valueOf(tileVal));
      }
        
      if(tileVal == 0) {
        this.shape.setFill(COLOR_EMPTY);
      }
      else if(tileVal == num2) {
        this.shape.setFill(COLOR_2);
      }
      else if(tileVal == num4) {
        this.shape.setFill(COLOR_4);
      }
      else if(tileVal == num8) {
        this.shape.setFill(COLOR_8);
      }
      else if(tileVal == num16) {
        this.shape.setFill(COLOR_16);
      }
      else if(tileVal == num32) {
        this.shape.setFill(COLOR_32);
      }  
      else if(tileVal == num64) {
        this.shape.setFill(COLOR_64);
      }
      else if(tileVal == num128) {
        this.shape.setFill(COLOR_128);
      }
      else if(tileVal == num256) {
        this.shape.setFill(COLOR_256);
      }
      else if(tileVal == num512) {
        this.shape.setFill(COLOR_512);
      }
      else if(tileVal == num2048) {
        this.shape.setFill(COLOR_1024);
      }
      else if(tileVal == num2048) {
        this.shape.setFill(COLOR_2048);
      }
      else if(tileVal > num2048) {
        this.shape.setFill(COLOR_OTHER);
      }
     
     
    }
    
     /*
      * Name: getText()
      * Purpose: gets the text
      * Parameter: None
      * Return: object text of type Text
      */

      public Text getText() {
        return this.text;
      }
    
     /*
      * Name: getRectangle() 
      * Purpose: gets the tile
      * Parameter:None
      * Return: object shape of type Rectangle
      */
      
      public Rectangle getRectangle() {
        return this.shape;
      }
    
      
     /*
      * Name: setText()
      * Purpose: sets the text
      * Parameter: t is of type Text
      * Return: object t is of type Text
      */
      
      public void setText(Text t) {
        this.text = t;
      }
    }
      
    
    /** DO NOT EDIT BELOW */

    // The method used to process the command line arguments
    private void processArgs(String[] args)
    {
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if((args.length % 2) != 0)
        {
            printUsage();
            System.exit(-1);
        }

        // Process all the arguments 
        for(int i = 0; i < args.length; i += 2)
        {
            if(args[i].equals("-i"))
            {   // We are processing the argument that specifies
                // the input file to be used to set the board
                inputBoard = args[i + 1];
            }
            else if(args[i].equals("-o"))
            {   // We are processing the argument that specifies
                // the output file to be used to save the board
                outputBoard = args[i + 1];
            }
            else if(args[i].equals("-s"))
            {   // We are processing the argument that specifies
                // the size of the Board
                boardSize = Integer.parseInt(args[i + 1]);
            }
            else
            {   // Incorrect Argument 
                printUsage();
                System.exit(-1);
            }
        }

        // Set the default output file if none specified
        if(outputBoard == null)
            outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if(boardSize < 2)
            boardSize = 4;

        // Initialize the Game Board
        try{
            if(inputBoard != null)
                board = new Board(inputBoard, new Random());
            else
                board = new Board(boardSize, new Random());
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getName() + " was thrown while creating a " +
                               "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                               "Constructor is broken or the file isn't " +
                               "formated correctly");
            System.exit(-1);
        }
    }

    // Print the Usage Message 
    private static void printUsage()
    {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be used to save the 2048 board");
        System.out.println("                If none specified then the default \"2048.board\" file will be used");
        System.out.println("  -s [size]  -> Specifies the size of the 2048 board if an input file hasn't been");
        System.out.println("                specified.  If both -s and -i are used, then the size of the board");
        System.out.println("                will be determined by the input file. The default size is 4.");
    }
}
