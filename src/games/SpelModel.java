package games;

import java.awt.Font;
import java.util.Random;

public class SpelModel {
        protected int[][] game; // storleken sätts i start()
        protected boolean isOver;
        protected int points;
        protected int level;
        protected String result;
        protected static String title = "GameTitle";
        protected Random rgen = new Random();
        protected MatrixObservers observers = new MatrixObservers();
        protected Font font = new Font(Font.DIALOG, Font.PLAIN, 20);
        protected int width;
        protected int height;


        public void initialise(int Width, int Height){
                width = Width;
                height = Height;
                game = new int[width][height];
                isOver = false;
                points = 0;
                level = 0;
                result = "";
        }

        public boolean isOver() {
                return isOver;
        }

        public int getLevel() {
                return level;
        }

        public int getPoints() {
                return points;
        }

        public String getTitle() {
                return title;
        }

        /** Get the game matrix. */
        public int[][] getGame() {
                return game;
        }

        /** Get the number of square m, n in game matrix. */
        public int getSquare(int m, int n){
                return game[m][n];
        }

        public String getResult(){
                return result;
        }

        public MatrixObservers getObservers(){
                return observers;
        }

        public void PrintMatrix(int[][] mtrx){
                for (int i=0; i < mtrx.length; i++){
                        for (int j=0; j < mtrx[i].length; j++){
                                System.out.print(mtrx[i][j]+"  ");
                        }
                        System.out.println(); // ny rad
                }
        }

        /** Set square m,n to number and notify observers. */
        protected void set(int m, int n, int number){
                game[m][n] = number;
                observers.notifyObservers(m, n, number);
        }

        public Font getFont(){
                return font;
        }

        public void setWidth(int i){
                if (i > 0)
                        width = i;
                else throw new IllegalArgumentException("Width must be positive");
        }

        public void setHeight(int i){
                if (i > 0)
                        height = i;
                else throw new IllegalArgumentException("Height must be positive");
        }

}
