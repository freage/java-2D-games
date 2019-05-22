package games.clickgames;

import java.awt.Color;
import java.awt.Font;

import games.MatrixObserverInterface;
import games.BaseModel;

public abstract class Model extends BaseModel {

    Model(int H, int W) {
        super(H, W);
    }

    abstract void leftClick(int m, int n);
    /* Tic-tac-toe: X/O appears at position m, n
     * 15-puzzle: square at m, n is moved to empty
     * Then calls verify()
     * and then either execute() or an error
     * TODO: very generic description; implement here?
     */

    abstract void rightClick(int m, int n);

    /** String representation of this number */
    abstract String translateString(int i);

    /** Text colour of String representation of this number. */
    abstract Color translateTextColor(int i);

    /** Background colour of this number's button. */
    abstract Color translateBgColor(int i);

}
