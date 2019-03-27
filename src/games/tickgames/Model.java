package games.tickgames;

import java.awt.Color;

import games.BaseModel;

public abstract class Model extends BaseModel {

        static int NORTH = 1;
        static int SOUTH = -1;
        static int WEST = 2;
        static int EAST = -2;
        static int RESTART = 3;
        static int NONE = 0;

        /**
         * Called after every ActionEvent created by Timer in Simulation class
         * @param request - request forwarded by Simulation from Controller
         */
        abstract void simulate(int request);

        abstract void start();

        abstract void restart();

        // boolean isOver();

        // int getLevel();

        // int getPoints();

        abstract Color translate(int element);

        // String getTitle();

        // int getSquare(int m, int n);

        // public int getWidth();

        // public int getHeight();


        // void printMatrix(int[][] mtrx);

        // public void addObserver(MatrixObserverInterface view);

        // String getResult();

}
