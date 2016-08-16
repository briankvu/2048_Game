//------------------------------------------------------------------//
// PA4Tester.java                                                   //
//                                                                  //
// Tester for 2048                                                  //
//                                                                  //
// Author:  Brian Vu                                        //
// Date:    1/29/15                                                 //
//------------------------------------------------------------------//

import java.util.*;
import java.io.*;

public class PA4Tester
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        System.out.println("Begining Testing 2048");

        testCanMove();
        testIsGameOver();
        testMove();


        /** ADD YOUR OWN DIRECTION TESTS HERE!! */




    }













    /** DO NOT MODIFY BELOW HERE */
    private static void testIsGameOver() throws IOException
    {
        Board board;

        System.out.println("Testing isGameOver() Method");

        board = new Board("isGameOver_true.in", new Random());
       
        System.out.println("   Testing isGameOver_true.in..." + (board.isGameOver() ? "Passed" : "Failed"));
        
        board = new Board("isGameOver_false.in", new Random());

        System.out.println("   Testing isGameOver_false.in..." + (!board.isGameOver() ? "Passed" : "Failed"));

    }

    private static void testCanMove() throws IOException
    {
        Board board;

        System.out.println("Testing canMove() Method");

        board = new Board("canMoveTest.in", new Random());
       
        System.out.println("   Testing UP..." +     (!board.canMove(Direction.UP) ? "Passed" : "Failed"));
        System.out.println("   Testing DOWN..." +   (board.canMove(Direction.DOWN) ? "Passed" : "Failed"));
        System.out.println("   Testing LEFT..." +   (!board.canMove(Direction.LEFT) ? "Passed" : "Failed"));
        System.out.println("   Testing RIGHT..." +  (board.canMove(Direction.RIGHT) ? "Passed" : "Failed"));
    }

    private static void testMove() throws IOException, InterruptedException
    {
        Board board;

        System.out.println("Testing move() Method");

        for(Direction dir: Direction.values())
        {
            board = new Board("move.in", new Random());
            System.out.print("   Testing " + dir.name() + "...");
            board.move(dir);
            String outputKey = "move_" + dir.name() + ".out";
            String studentOut = "studentMove_" + dir.name() + ".out";

            board.saveBoard(studentOut);

            String command = "diff -b " + outputKey + " " + studentOut;
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();

            if(p.exitValue() == 0)
                System.out.println("Passed");
            else
            {
                System.out.println("Failed");
                System.out.println("  Run the following to see the differences:");
                System.out.println("    " + command);
            }
        }
    }
}
