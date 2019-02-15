package games.clickgames;

import java.awt.Color;
import java.awt.Font;

import games.BaseModel;

/* TODO
 * Ska röja alla grannar när man klickar på tom ruta - åtgärdat!
 * Ska slå om till vunnit när alla minor röjda - skapa ytterligare matris?
 * Ska visa alla minor om man förlorar
 */


public class MineSweeper extends BaseModel implements Model {

        // här sparas minorna:
        private int[][] under;
        // spelet som användaren ser det finns i game (ärvd data)
        private int numberofmines = 30;


        // denna information finns i matriserna:
        private static final int EMPTY = 0;
        private static final int MINE = 9;
        private static final int COVERED = -1;
        private static final int FLAGGED = -2;
        /* förutsätter under laddandet av end-matrisen med addNumbers()
         * och översättningen till sträng för View med translateString();
         * att sifforna 1-8 är fria - dessa ska ju skrivas ut i spelet. */

        String instructions = "";

        public MineSweeper() {
                start();
        }

        private void start(){
                initialise(15,15);
                addMines(numberofmines);
                addNumbers();
                loadMatrix();
//              PrintMatrix(under);
        }

        private void addMines(int number){
                under = new int[game.length][game.length];
                int i=0;
                while(i<number){
                        int m = rgen.nextInt(game.length);
                        int n = rgen.nextInt(game.length);
                        if (under[m][n]==EMPTY){
                                under[m][n] = MINE;
                                i++;
                        }
                }
        }

        private void addNumbers(){
                for (int i=0; i<under.length; i++){
                        for (int j=0; j<under[i].length; j++){
                                if (under[i][j]!=MINE){
                                        int neighbours = neighbours(i, j);
                                        under[i][j] = neighbours;
                                }
                        }
                }
        }


        /* I denna funktion görs antagandet att konstanterna ONE, ... , EIGHT
         * faktiskt svarar mot respektive siffra. */
        private int neighbours(int m, int n){
                int mines = 0;
                for (int i=m-1; i<m+2; i++){
                        if (isOutOfRange(i));
                        else for (int j=n-1; j<n+2; j++){
                                if (isOutOfRange(j));
                                else if (under[i][j]==MINE)
                                        mines++;
                        };
                }
                return mines;
        }


        private boolean isOutOfRange(int i){
                return (i<0 || i>= game.length);
        }

        private void loadMatrix(){
                for (int i=0; i<game.length; i++){
                        for (int j=0; j<game[i].length; j++){
                                game[i][j] = COVERED;
                        }
                }
        }

        private void uncover(int m, int n){
                game[m][n] = under[m][n];
                observers.notifyObservers(m, n, under[m][n]);
                if (game[m][n]==EMPTY)
                        uncoverAllNeighbours(m, n);
        }

        private void uncoverAllNeighbours(int m, int n){
                for (int i=m-1; i<m+2; i++){
                        if (isOutOfRange(i));
                        else for (int j=n-1; j<n+2; j++){
                                if (isOutOfRange(j));
                                else if (game[i][j]==COVERED)
                                        uncover(i, j);
                        };
                }
        }


        @Override
        public void leftClick(int m, int n) {
                if (game[m][n]==COVERED){
                        uncover(m,n);
                        if (under[m][n]==MINE){
                                result = "You blew it up!";
                                isOver = true;
                        } else if (hasWon()){
                                result = "You won!";
                                isOver = true;
                        }
                }

        }

        /** pre: not lost */
        private boolean hasWon(){
                for (int i=0; i<game.length; i++){
                        for (int j=0; j<game[i].length; j++){
                                if (game[i][j] == COVERED || game[i][j]==FLAGGED)
                                        if (under[i][j]!=MINE) return false;
                        }
                }
                return true;
        }


        @Override
        public void restart() {
                start();
        }

        @Override
        public String translateString(int i) {
                String str = "";
                if (i==EMPTY || i==COVERED)
                        str = "";
                else if (i==MINE)
                        str = "M";
                else if (i==FLAGGED)
                        str = "F";
                else str = ""+i;
                return str;
        }


        public static void main(String[] args) {
                MineSweeper ms = new MineSweeper();
                ms.PrintMatrix(ms.game);
        }

        @Override
        public void rightClick(int m, int n) {
                if (game[m][n]==COVERED){
                        game[m][n] = FLAGGED;
                        observers.notifyObservers(m, n, FLAGGED);
                }
                else if (game[m][n]==FLAGGED){
                        game[m][n] = COVERED;
                        observers.notifyObservers(m, n, COVERED);
                }
        }

        @Override
        public Color translateTextColor(int i) {
                if (i==1)
                        return Color.BLUE;
                if (i==2)
                        return new Color(0, 139, 0); // green4
                if (i==3)
                        return Color.RED;
                if (i==4)
                        return new Color(0, 0, 128); // navy
                if (i==5)
                        return new Color(128, 0, 0);  // prova maroon* 0,0,128
                if (i==6)
                        return new Color(0, 255, 239); // turkos
                /* turquoiseblue 0,199,140   aquamarine3 102,205,170 */
                if (i==7)
                        return Color.BLACK;
                if (i==8)
                        return Color.GRAY;
                return Color.BLACK;
        }

        @Override
        public Color translateBgColor(int i) {
                if (i==COVERED || i==FLAGGED)
                        return new Color(229, 229, 229);
                else
                        return new Color(204, 204, 204);
        }

        @Override
        public int getButtonSize() {
                return 35;
        }

        @Override
        public Font getFont() {
                return new Font(Font.DIALOG, Font.BOLD, 20);
        }

        public void setMines(int i){
                if (i > 0)
                        numberofmines = i;
                else throw new IllegalArgumentException("Number of mines must be positive");
        }

//      @Override
//      public String getInstructions() {
//              return instructions;
//      }
//


}
