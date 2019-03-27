package games;

import java.awt.Font;
import java.util.Random;

import java.util.LinkedList;
import java.util.ListIterator;

public abstract class BaseModel {
        protected int[][] game; // the size is set in start()
        protected boolean isOver;
        protected int points;
        protected int level;
        protected String result;
        protected static String title = "GameTitle";
        protected Random rgen = new Random();
        private LinkedList<MatrixObserverInterface> observers;
        protected Font font = new Font(Font.DIALOG, Font.PLAIN, 20);
        protected int width;
        protected int height;


        public void initialise(int W, int H){
                observers = new LinkedList<MatrixObserverInterface>();
                width = W;
                height = H;
                game = new int[height][width]; // TODO: correct order?
                isOver = false;
                points = 0;
                level = 0;
                result = "";
        }

        /** Used by controller
         * @return true if game is over
         */
        public boolean isOver() {
                return isOver;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getLevel() {
                return level;
        }

        public int getPoints() {
                return points;
        }

        /** Title of this game. */
        public String getTitle() {
                return title;
        }

        /** Get the game matrix. */
        public int[][] getGame() {
                return game;
        }

        /** Get the number of square m, n in game matrix. Used by View. */
        public int getSquare(int m, int n){
                return game[m][n];
        }

        public String getResult(){
                return result;
        }

        public void printMatrix(int[][] mtrx){
                for (int i=0; i < mtrx.length; i++){
                        for (int j=0; j < mtrx[i].length; j++){
                                System.out.print(mtrx[i][j]+"  ");
                        }
                        System.out.println(); // ny rad
                }
        }

        public Font getFont(){
                return font;
        }

    /** The squares in the grid. Override if it should be different. */
    public int getSquareSize(){
        return 1;
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

        //////////////////////////////////////////////////
        // Observer-related:

        // Interface for a subclass:

        /** Set square m,n to number and notify observers. */
        protected void set(int m, int n, int number){
            System.out.println("BaseModel::set();");
                game[m][n] = number;
                notifyObservers(m, n, number);
        }

        /** A call to redraw the entire board. */
        protected void updateAll() {
                notifyObservers();
        }

        // Interface for the View:

        public void addObserver(MatrixObserverInterface vy){
                observers.add(vy);
        }

        // Private help functions:

        private void notifyObservers(int i, int j, int tal){
            System.out.println("BaseModel::notifyObservers()");
            if (observers.size() == 0) System.out.println("No observers!");
                ListIterator<MatrixObserverInterface> listiterator = observers.listIterator();
                while (listiterator.hasNext()){
                    System.out.println("BaseModel::notifyObservers() -- call next observer");
                        listiterator.next().updateSquare(i, j, tal);
                }
        }

        private void notifyObservers(){
            System.out.println("BaseModel::notifyObservers()");
                ListIterator<MatrixObserverInterface> listiterator = observers.listIterator();
                while (listiterator.hasNext()){
                        listiterator.next().updateMatrix();
                }
        }



}
