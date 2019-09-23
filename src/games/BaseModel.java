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
    private LinkedList<MatrixObserverInterface> observers;
    protected Font font = new Font(Font.DIALOG, Font.PLAIN, 20);
    protected int width;
    protected int height;
    protected Random rgen; // random generator
    protected int squareSize = 1;


    public BaseModel(int H, int W) {
        observers = new LinkedList<MatrixObserverInterface>();
        height = H;
        width = W;
        game = new int[height][width];
        rgen = new Random();
    }

    ///////////////////////////////////////////////////////////////////
    // Initialisation

    /** When the observers have been added, this will be called.
     * Also used for restarting.
     * Must be synchronized for threading safety in tick games.
     */
    public final synchronized void start() {
        reset();
        fill();
    }

    /** When restarting the game: reset all variables.
     * Override this if you want to reset additional game-specific variables, after a super-call. */
    protected void reset() {
        isOver = false;
        points = 0;
        level = 0;
        result = "";
    }
    /** Fill the game matrix with its initial content. */
    protected abstract void fill();

    ////////////////////////////////////////////////////////////////////
    // Interface for the Controller


    /** Used by controller
     * @return true if game is over
     */
    public final boolean isOver() {
        return isOver;
    }

    /** The text next to the game. Override if it should be different. */
    public String getInstructions() {
        return "";
    }

    public final String getResult(){
        return result;
    }

    public final int getLevel() {
        return level;
    }

    public final int getPoints() {
        return points;
    }

    ///////////////////////////////////////////////////////////////////
    // Interface for the View

    /** The squares in the grid. Set your preferred square size in the constructor. */
    public final int getSquareSize(){
        return squareSize;
    }

    public final Font getFont(){
        return font;
    }

    /** Get the number of square m, n in game matrix. Used by View. */
    public final int getSquare(int m, int n){
        return game[m][n];
    }


    //////////////////////////////////////////////////
    // Observer-related:

    // Interface for a subclass:

    /** Set square m,n to number and notify observers. */
    protected final void set(int m, int n, int number){
        game[m][n] = number;
        notifyObservers(m, n, number);
    }

    /** A call to redraw the entire board. */
    protected final void updateAll() {
        notifyObservers();
    }

    // Interface for the View:

    public final void addObserver(MatrixObserverInterface vy){
        observers.add(vy);
    }

    // Private help functions:

    private void notifyObservers(int i, int j, int tal){
        ListIterator<MatrixObserverInterface> listiterator = observers.listIterator();
        while (listiterator.hasNext()){
            listiterator.next().updateSquare(i, j, tal);
        }
    }

    private void notifyObservers(){
        ListIterator<MatrixObserverInterface> listiterator = observers.listIterator();
        while (listiterator.hasNext()){
            listiterator.next().updateMatrix();
        }
    }

    ///////////////////////////////////////////////////
    // Other

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }

    /** For debugging */
    public final void printMatrix(int[][] mtrx){
        for (int i=0; i < mtrx.length; i++){
            for (int j=0; j < mtrx[i].length; j++){
                System.out.print(mtrx[i][j]+"  ");
            }
            System.out.println(); // new row
        }
    }

}
