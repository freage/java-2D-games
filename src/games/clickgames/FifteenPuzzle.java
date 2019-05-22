package games.clickgames;


import java.awt.Color;

import games.Position;


public class FifteenPuzzle extends Model {
    private Position emptySq = new Position();
    private int winner = 1; // only one player


    public FifteenPuzzle() {
        super(4,4);
        squareSize = 50;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    // methods for controller:


    /**
     * move square [m][n] to the empty square
     */
    @Override
    public void leftClick(int m, int n) {
        if (isValid(m, n))
            execute(m, n);
    }

    @Override
    public void rightClick(int m, int n) {
        // nothing happens at a right click
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    // private help functions

    private boolean isValid(int m, int n) {
        boolean rowneighbour = Math.abs(m-emptySq.m)==1;
        boolean columnneighbour = Math.abs(n-emptySq.n)==1;
        boolean samerow = (m==emptySq.m);
        boolean samecolumn = (n==emptySq.n);
        return ((rowneighbour && samecolumn) || (columnneighbour && samerow));
    }

    private boolean solved() {
        int N = height*width;
        for (int number=0; number<N; number++) {
            int row = number / width;
            int col = number % width;
            if ( game[row][col] != (number + 1) % N ) {
                return false;
            }
        }
        return true;
    }

    /**
     * fill matrix with numbers 1-15,0 and shuffle
     */
    protected void fill() {
        int N = height*width;
        for (int number=0; number<N; number++) {
            int row = number / width;
            int col = number % width;
            game[row][col] = (number + 1) % N;
        }
        // set the position of the empty square
        emptySq.m = height-1;
        emptySq.n = width-1;
        shuffle();

    }


    private void execute(int m, int n) {
        int tal = game[m][n];
        set(m, n, 0);
        set(emptySq.m, emptySq.n, tal);
        emptySq.m = m;
        emptySq.n = n;
        isOver = solved();
        if (isOver){
            winner=1;
            result="You did it!";
        }
    }


    /////////////////////////////////////////////////////////////////////////////////
    // Debugging

    /**
     * @param args
     */
    public static void main(String[] args) {
        FifteenPuzzle my15 = new FifteenPuzzle();
        my15.printMatrix(my15.game);
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    // shuffle the game

    private boolean inrangeRows(int m) {
        return (0 <= m && m < height);
    }

    private boolean inrangeCols(int n) {
        return (0 <= n && n < width);
    }

    private void shuffle() {
        for (int i=0; i < 200; i++) {
            randomMove();
        }
    }
    private void randomMove() {
        boolean vertical = rgen.nextBoolean();
        boolean decrease = rgen.nextBoolean();
        int delta = decrease?-1:1;
        int m = emptySq.m;
        int n = emptySq.n;
        if (vertical) {
            if (inrangeRows(emptySq.m+delta)) m += delta;
        } else {
            if (inrangeCols(emptySq.n+delta)) n += delta;
        }
        leftClick(m, n);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////
    // getters for View:

    @Override
    public String translateString(int i) {
        if (i==0) {
            return "";
        } else return ""+i;
    }

    @Override
    public String getTitle() {
        return "15-puzzle";
    }

    @Override
    public Color translateTextColor(int i) {
        return Color.BLACK;
    }

    @Override
    public Color translateBgColor(int i) {
        if (i==0)
            return Color.GRAY;
        else return Color.CYAN;
    }

}
