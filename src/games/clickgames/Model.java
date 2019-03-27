package games.clickgames;

import java.awt.Color;
import java.awt.Font;

import games.MatrixObserverInterface;
import games.BaseModel;

public abstract class Model extends BaseModel {

        abstract void leftClick(int m, int n);
        /* Tic-tac-toe: X/O appears at position m, n
         * 15-puzzle: square at m, n is moved to empty
         * Then calls verify()
         * and then either execute() or an error
         */

        abstract void rightClick(int m, int n);


        // /**
        //  * @return a square of the matrix representing the gameboard
        //  */
        // public abstract int getSquare(int m, int n);

        // public abstract int getWidth();

        // public abstract int getHeight();


        // /** Used by controller
        //  * @return true if game is over
        //  */
        // abstract boolean isOver();
        // /* return isOver;
        //  */

        // /** How the result of this game should be displayed. */
        // abstract String getResult();


        abstract void restart();


        // /**
        //  * Prints the gameboard in the command line <br>
        //  * Useful for errorchecking or command line-playing
        //  */
        // public abstract void printMatrix(int[][] mtrx);


        /** String representation of this number */
        abstract String translateString(int i);

        /** Text colour of String representation of this number. */
        abstract Color translateTextColor(int i);

        // /** Font used in this game */
        // public abstract Font getFont();

        /** Background colour of this number's button. */
        abstract Color translateBgColor(int i);

        // public abstract String getTitle();

        // public abstract void addObserver(MatrixObserverInterface view);

        // /** Preferred size of button in pixels. Specified in this function. */
        // abstract int getButtonSize();

        // public abstract void setWidth(int i);

        // public abstract void setHeight(int i);

//      abstract String getInstructions();
}
