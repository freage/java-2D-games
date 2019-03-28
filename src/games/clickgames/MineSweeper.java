package games.clickgames;

import java.awt.Color;
import java.awt.Font;

/* TODO
 * Display all mines (black/safe) at a win.
 * Display all mines (red/exploded) at a loss.
 */


public class MineSweeper extends Model {

        // here the mines are stored
        private int[][] solution;
        // the matrix visible for the user is in BaseModel.game
        private int numberofmines = 30;

        private static final int EMPTY = 0;
        private static final int MINE = 9;
        private static final int COVERED = -1;
        private static final int FLAGGED = -2;
        private static final int FALSE_FLAG = -3;
        // Numbers 1-8 are used "as is"

        private boolean youWon = false;

        public MineSweeper() {
                super(15,15);
                font = new Font(Font.DIALOG, Font.BOLD, 20);
        }

        @Override
        protected void fill() {
                addMines(numberofmines);
                addNumbers();
                loadMatrix();
        }

        private void addMines(int number) {
                solution = new int[height][width];
                int i=0;
                while (i<number) {
                        int m = rgen.nextInt(height);
                        int n = rgen.nextInt(width);
                        if (solution[m][n]==EMPTY) {
                                solution[m][n] = MINE;
                                i++;
                        }
                }
        }

        private void addNumbers() {
                for (int i=0; i<height; i++) {
                        for (int j=0; j<width; j++) {
                                if (solution[i][j]!=MINE) {
                                        int neighbours = neighbours(i, j);
                                        solution[i][j] = neighbours;
                                }
                        }
                }
        }


        private int neighbours(int m, int n) {
                int mines = 0;
                for (int i=m-1; i<m+2; i++) {
                        if (isOutOfRange(i));
                        else for (int j=n-1; j<n+2; j++) {
                                if (isOutOfRange(j));
                                else if (solution[i][j]==MINE)
                                        mines++;
                        };
                }
                return mines;
        }


        private boolean isOutOfRange(int i) {
                return (i<0 || i>= game.length);
        }

        private void loadMatrix() {
                for (int i=0; i<height; i++) {
                        for (int j=0; j<width; j++) {
                                game[i][j] = COVERED;
                        }
                }
        }

        private void uncover(int m, int n) {
                set(m, n, solution[m][n]);
                if (game[m][n]==EMPTY)
                        uncoverAllNeighbours(m, n);
        }

        private void uncoverAllNeighbours(int m, int n) {
                for (int i=m-1; i<m+2; i++) {
                        if (isOutOfRange(i));
                        else for (int j=n-1; j<n+2; j++) {
                                if (isOutOfRange(j));
                                else if (game[i][j]==COVERED)
                                        uncover(i, j);
                        };
                }
        }

        private void uncoverAllMines() {
                for (int i=0; i<height; i++) {
                        for (int j=0; j<width; j++) {
                                if ((solution[i][j] == MINE) && (! (game[i][j]==FLAGGED))) {
                                        set(i, j, MINE);
                                } if ((solution[i][j] == MINE) && ((game[i][j]==FLAGGED))) {
                                        set(i, j, FLAGGED); // this will only update the color, not the content
                                } else if ((game[i][j]==FLAGGED) && (! (solution[i][j] == MINE))) {
                                        set(i, j, FALSE_FLAG);
                                }
                        }
                }
        }


        @Override
        public void leftClick(int m, int n) {
                if (!isOver) {
                        if (game[m][n]==COVERED) {
                                uncover(m,n);
                                if (solution[m][n]==MINE) {
                                        result = "You blew it up!";
                                        isOver = true;
                                } else if (hasWon()) {
                                        result = "You won!";
                                        isOver = true;
                                }
                                if (isOver) uncoverAllMines();
                        }
                }

        }

        private boolean hasWon() {
                for (int i=0; i<height; i++) {
                        for (int j=0; j<width; j++) {
                                if (game[i][j] == COVERED || game[i][j]==FLAGGED)
                                        if (solution[i][j]!=MINE) return false;
                        }
                }
                youWon = true;
                return true;
        }


        @Override
        public String translateString(int i) {
                String str = "";
                if (i==EMPTY || i==COVERED)
                        str = "";
                else if (i==MINE) {
                        if (youWon) {
                                // str = "M";
                                str = "💣"; // unicode bomb
                        } else {
                                // str = "M";
                                str = "💥"; // unicode explosion
                        }
                }
                else if ((i==FLAGGED) || (i==FALSE_FLAG))
                        // str = "F";
                        str = "🚩"; // unicode flag
                else str = ""+i;
                return str;
        }


        public static void main(String[] args) {
                MineSweeper ms = new MineSweeper();
                ms.printMatrix(ms.game);
        }

        @Override
        public void rightClick(int m, int n) {
                if (game[m][n]==COVERED) {
                        set(m, n, FLAGGED);
                }
                else if (game[m][n]==FLAGGED) {
                        set(m, n, COVERED);
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
                        return new Color(128, 0, 0);  // test maroon* 0,0,128
                if (i==6)
                        return new Color(0, 255, 239); // turquoise
                /* turquoiseblue 0,199,140   aquamarine3 102,205,170 */
                if (i==7)
                        return Color.BLACK;
                if (i==8)
                        return Color.GRAY;
                if (i==MINE) {
                        if (youWon) return Color.BLACK;
                        else return Color.ORANGE;
                } if (i==FLAGGED) {
                        // if (youWon) return Color.GREEN;
                        // else
                            return Color.RED;
                }
                if (i==FALSE_FLAG) return Color.RED;
                return Color.BLACK;
        }

        @Override
        public Color translateBgColor(int i) {
                if (isOver) {
                        if (i==MINE) {
                                if (youWon) return Color.GRAY;
                                else return Color.RED;
                        } if (i==FLAGGED) return Color.GRAY;
                        if (i==FALSE_FLAG) return Color.ORANGE;
                }
                if (i==COVERED || i==FLAGGED)
                        return new Color(229, 229, 229);
                else
                        return new Color(204, 204, 204);
        }

        @Override
        public int getSquareSize() {
                return 35;
        }

        // TODO: is this even used somewhere?
        public void setMines(int i) {
                if (i > 0)
                        numberofmines = i;
                else throw new IllegalArgumentException("Number of mines must be positive");
        }

}
