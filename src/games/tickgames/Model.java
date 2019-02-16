package games.tickgames;

import java.awt.Color;

import games.MatrixObserverInterface;

public interface Model {

        int NORTH = 1;
        int SOUTH = -1;
        int WEST = 2;
        int EAST = -2;
        int RESTART = 3;
        int NONE = 0;

        /**
         * Called after every ActionEvent created by Timer in Simulation class
         * @param request - request forwarded by Simulation from Controller
         */
        void simulate(int request);

        void restart();

        boolean isOver();

        int getLevel();

        int getPoints();

        Color translate(int element);

        String getTitle();

        int getSquare(int m, int n);

        public int getWidth();

        public int getHeight();


        void printMatrix(int[][] mtrx);

        public void addObserver(MatrixObserverInterface view);

        String getResult();

}
