package games.tickgames;

import java.awt.Color;

import games.BaseModel;

public abstract class Model extends BaseModel {

    // changing these will crash the hacks in the snake navigation...
        static int NORTH = -1;
        static int SOUTH = 1;
        static int WEST = -2;
        static int EAST = 2;
        static int NONE = 0;

        Model(int H, int W) {
                super(H, W);
        }

        /**
         * Called after every ActionEvent created by Timer in Controller class
         */
        abstract void simulate(int request);

        abstract Color translate(int element);

        @Override
        public String getInstructions() {
                return "Press <space> to restart game.";
        }

}
