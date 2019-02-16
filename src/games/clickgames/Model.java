package games.clickgames;

import java.awt.Color;
import java.awt.Font;

import games.MatrixObserverInterface;

public interface Model {

        void leftClick(int m, int n);
        /* Tic-tac-toe: X/O appears at position m, n
         * 15-puzzle: square at m, n is moved to empty
         * Then calls verify()
         * and then either execute() or an error
         */

        void rightClick(int m, int n);


        /** Used by view
         * @return a square of the matrix representing the gameboard
         */
        public int getSquare(int m, int n);

        public int getWidth();

        public int getHeight();


        /** Used by controller
         * @return true if game is over
         */
        boolean isOver();
        /* return isOver;
         */

        /** How the result of this game should be displayed. */
        String getResult();


        void restart();


        /**
         * Prints the gameboard in the command line <br>
         * Useful for errorchecking or command line-playing
         */
        void printMatrix(int[][] mtrx);


        /** String representation of this number */
        String translateString(int i);

        /** Text colour of String representation of this number. */
        Color translateTextColor(int i);

        /** Font used in this game */
        Font getFont();

        /** Background colour of this number's button. */
        Color translateBgColor(int i);

        /** Title of this game. */
        String getTitle();

        public void addObserver(MatrixObserverInterface view);

        /** Preferred size of button in pixels. Specified in this function. */
        int getButtonSize();

        void setWidth(int i);

        void setHeight(int i);

//      String getInstructions();
}
