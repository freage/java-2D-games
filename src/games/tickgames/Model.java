package games.tickgames;

import java.awt.Color;

import games.BaseModel;

public abstract class Model extends BaseModel {

    // changing these will crash the hacks in the snake navigation...
    static final int NORTH = -1;
    static final int SOUTH = 1;
    static final int WEST = -2;
    static final int EAST = 2;
    static final int NONE = 0;
    protected int tick;

    Model(int H, int W) {
        super(H, W);
    }

    /**
     * Called after every ActionEvent created by Timer in Controller class
     * Should be implemented as synchronized
     */
    abstract void simulate();

    /** Should be implemented as synchronized
     */
    abstract void request(int key);

    abstract Color translate(int element);

    public final int getTick() {
        return tick;
    }

    @Override
    public String getInstructions() {
        return "Press <space> to restart game.";
    }

}
