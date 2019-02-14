package games.clickgames;

import java.awt.Color;
import java.awt.Font;

import games.BaseModel;


// TODO: skriv om View så att den passar

public class TicTacToe extends BaseModel implements Model {

        private static final int O = 1;
        private static final int X = 2;
        private static final int EMPTY = 0;
        private int nextplayer;

        //////////////////////////////////////////////////////////////////////////////////////////////////////
        // public methods:

        public TicTacToe(){
                start();
        }

        private void start(){
                initialise(3,3);
                nextplayer = O;
        }

        @Override
        public void LeftClick(int m, int n) {
                if (isValid(m, n))
                        Execute(m, n);
        }

        @Override
        public void restart() {
                start();
        }


        ////////////////////////////////////////////////////////////////////////////////////////////////
        // getters:

        @Override
        public String translateString(int i) {
                if (i==0)
                        return "";
                if (i==X)
                        return "X";
                if (i==O)
                        return "O";
                else return null;
        }

        @Override
        public String getTitle() {
                return "Tic-tac-toe";
        }


        ////////////////////////////////////////////////////////////////////////////////////////////////
        // help functions:

        private boolean isValid(int m, int n) { // är rutan tom?
                return game[m][n]==0;
        }

        private void Execute(int m, int n){
                game[m][n] = nextplayer;
                observers.notifyObservers(m, n, nextplayer);
                nextplayer = nextplayer % 2 + 1;
                if (hasThreeInARow())
                        isOver = true;
        }

        private boolean hasThreeInARow(){
                // kolla rader:
                for (int i=0; i<game.length; i++){
                        if (checkRow(i))
                                return true;
                } // kolla kolumner:
                for (int i=0; i<game.length; i++){
                        if (checkColumn(i))
                                return true;
                } // då avgör diagonalen:
                return checkDiagonals();
        }

        private void setWinner(int i){
                result = translateString(i)+" har vunnit";
        }

        private boolean checkRow(int m){
                if (game[m][0]!=0){ // om första på rad m är nollskilld
                        if (game[m][0]==game[m][1] && game[m][0]==game[m][2]){
                                setWinner(game[m][0]);
                                return true;
                        }
                }
                return false;
        }

        private boolean checkColumn(int n){
                if (game[0][n]!=0){ // om första på kolumn n är nollskilld
                        if (game[0][n]==game[1][n] && game[0][n]==game[2][n]){
                                setWinner(game[0][n]);
                                return true;
                        }
                }
                return false;
        }

        private boolean checkDiagonals(){
                if (game[1][1]!=0){
                        // huvuddiagonalen
                        if (game[1][1]==game[0][0] && game[1][1]==game[2][2]){
                                setWinner(game[1][1]);
                                return true;
                        } // anti-diagonalen
                        if (game[1][1]==game[0][2] && game[1][1]==game[2][0]){
                                setWinner(game[1][1]);
                                return true;
                        }
                }
                return false;
        }

        @Override
        public void RightClick(int m, int n) {
                // gör ingenting
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

        @Override
        public int getButtonSize() {
                return 75;
        }




}
