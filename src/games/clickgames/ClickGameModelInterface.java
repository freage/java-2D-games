package games.clickgames;

import java.awt.Color;
import java.awt.Font;

import games.MatrixObservers;

public interface ClickGameModelInterface {
        /* int[][] matris spelplan: n rader innehållande n kolumner
         * klass: senaste_draget: int player, int m, int n, boolean executed
         * int[] players;
         * boolean isOver;
         * int vems_tur;
         *
         */


        /**
         *
         * @param piece - piece to be moved/placed
         * @param m - row
         * @param n - column
         */
        void LeftClick(int m, int n);
        /* Luffarschack: piece dyker upp på position m, n
         * 15-spel: ruta på m, n flyttas till tom
         * Anropar sedan Verify() för att verifiera
         * Sedan antingen Execute() eller felmeddelande
         */

        void RightClick(int m, int n);
        /* Luffarschack: piece dyker upp på position m, n
         * 15-spel: ruta på m, n flyttas till tom
         * Anropar sedan Verify() för att verifiera
         * Sedan antingen Execute() eller felmeddelande
         */


        /** Used by view
         * @return the matrix representing the gameboard
         */
        int[][] getGame();
        /* return matrix;
         */


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
        void PrintMatrix(int[][] mtrx);


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

        MatrixObservers getObservers();

        /** Preferred size of button in pixels. Specified in this function. */
        int getButtonSize();

        void setWidth(int i);

        void setHeight(int i);

//      String getInstructions();
}
