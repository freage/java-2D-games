package games.clickgames;

import java.awt.Color;
import java.awt.Font;


public class TicTacToe extends Model {

    private static final int O = 1;
    private static final int X = 2;
    private static final int EMPTY = 0;
    private int nextplayer;

    public TicTacToe() {
        super(3, 3);
        squareSize = 75;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // Initialisation

    @Override
    protected void reset() {
        super.reset();
        nextplayer = O;
    }

    @Override
    protected void fill() {
        // do nothing; grid should be initially empty
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // help functions - execute a move:

    private boolean isValid(int m, int n) { // is the square empty?
        return game[m][n]==0;
    }

    private void execute(int m, int n) {
        set(m, n, nextplayer);
        nextplayer = nextplayer % 2 + 1;
        if (hasThreeInARow())
            isOver = true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // help functions - check for a winner:


    private boolean hasThreeInARow() {
        // rows?
        for (int i=0; i<game.length; i++) {
            if (checkRow(i))
                return true;
        } // columns?
        for (int i=0; i<game.length; i++) {
            if (checkColumn(i))
                return true;
        } // diagonals?
        return checkDiagonals();
    }

    private boolean checkRow(int m) {
        if (game[m][0]!=0) { // check if first square in row `m` is non-zero
            if (game[m][0]==game[m][1] && game[m][0]==game[m][2]) {
                setWinner(game[m][0]);
                return true;
            }
        }
        return false;
    }

    private boolean checkColumn(int n) {
        if (game[0][n]!=0) { // check if first square in column `m` is non-zero
            if (game[0][n]==game[1][n] && game[0][n]==game[2][n]) {
                setWinner(game[0][n]);
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        if (game[1][1]!=0) {
            // main diagonal
            if (game[1][1]==game[0][0] && game[1][1]==game[2][2]) {
                setWinner(game[1][1]);
                return true;
            } // anti-diagonal
            if (game[1][1]==game[0][2] && game[1][1]==game[2][0]) {
                setWinner(game[1][1]);
                return true;
            }
        }
        return false;
    }

    private void setWinner(int i) {
        result = translateString(i)+" has won";
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    // Interface for Controller:

    @Override
    public void leftClick(int m, int n) {
        if (isValid(m, n))
            execute(m, n);
    }


    @Override
    public void rightClick(int m, int n) {
        // nothing happens at a right click
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    // Interface for View:

    @Override
    public String translateString(int i) {
        if (i==EMPTY)
            return "";
        if (i==X)
            return "X";
        if (i==O)
            return "O";
        else return null;
    }

    @Override
    public Color translateTextColor(int i) {
        return Color.BLACK;
    }

    @Override
    public Color translateBgColor(int i) {
        if (i==EMPTY)
            return Color.GRAY;
        else return Color.CYAN;
    }


}
