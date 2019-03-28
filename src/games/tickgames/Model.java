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
         * Called after every ActionEvent created by Timer in Controller class
         */
        abstract void simulate(int request);

        abstract void start();

        abstract void restart();

        abstract Color translate(int element);

        @Override
        public String getInstructions() {
                return "Press <space> to restart game.";
        }

}
