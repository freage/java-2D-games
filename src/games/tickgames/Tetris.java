package games.tickgames;

import games.Position;
import java.util.List;
import java.util.Arrays;
import java.awt.Color;

public class Tetris extends Model {
    Tetris ref = this;
    // "Colors" in the game grid:
    final static int EMPTY = 0;
    final static int LONG = 1;
    final static int SQUARE = 2;
    final static int HALFPLUS = 3;
    final static int UP_L = 4;
    final static int DOWN_L = 5;
    final static int LEFT_BOLT = 6;
    final static int RIGHT_BOLT = 7;


    // "Requests"
    enum Request {LEFT, RIGHT, DOWN, DROP, ROTATE_CW, ROTATE_CCW, DUMMY};

    class P extends Position { P(int m, int n) {super(m,n);} } // Easier constructor...

    class RB { // RB = rotation block
        Position[] squares;
        Position dim; // dimensions of minimal enclosing rectangle
        Position UL; // the offset of `dim` relative the 3x3 enclosing box

        RB(Position[] squares, Position dim, Position UL) {
            this.squares = squares;
            this.dim = dim;
            this.UL = UL;
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////
    // ALL BLOCKS and their rotations
    /////////////////////////////////////////////////////////////////////////////////////////////////

    /** the HALFPLUS
     *     . x .   . . .   . x .   . x .
     *     . x x   x x x   x x .   x x x
     *     . x .   . x .   . x .   . . .
     */
    Block halfplus = new Block(
        new RB[] {
            new RB(new P[] { new P(0,1), new P(1,1), new P(2,1), new P(1,2) }, new P(3,2), new P(0,1)),
            new RB(new P[] { new P(1,0), new P(1,1), new P(1,2), new P(2,1) }, new P(2,3), new P(1,0)),
            new RB(new P[] { new P(0,1), new P(1,1), new P(2,1), new P(1,0) }, new P(3,2), new P(0,0)),
            new RB(new P[] { new P(1,0), new P(1,1), new P(1,2), new P(0,1) }, new P(2,3), new P(0,0)) },
        HALFPLUS, "halfplus");

    /** the LONG
     *     . x .   . . .
     *     . x .   x x x x
     *     . x .   . . .
     *       x
     */
    Block longOne = new Block(
       new RB[] {
           new RB(new P[] { new P(0,1), new P(1,1), new P(2,1), new P(3,1) }, new P(4,1), new P(0,1)),
           new RB(new P[] { new P(1,0), new P(1,1), new P(1,2), new P(1,3) }, new P(1,4), new P(1,0)) },
       LONG, "long");

    /** the SQUARE
     *     x x .
     *     x x .
     *     . . .
     */
    Block square = new Block(
      new RB[] {
          new RB(new P[] { new P(0,0), new P(0,1), new P(1,0), new P(1,1) }, new P(2,2), new P(0,0)) },
      SQUARE, "square");

    /** the LEFT BOLT
     *     x . .   . x x   . x .   . . .
     *     x x .   x x .   . x x   . x x
     *     . x .   . . .   . . x   x x .
     */
    Block leftBolt = new Block(
        new RB[] {
            new RB(new P[] { new P(0,0), new P(1,0), new P(1,1), new P(2,1) }, new P(3,2), new P(0,0)),
            new RB(new P[] { new P(1,0), new P(1,1), new P(0,1), new P(0,2) }, new P(2,3), new P(0,0)),
            new RB(new P[] { new P(0,1), new P(1,1), new P(1,2), new P(2,2) }, new P(3,2), new P(0,1)),
            new RB(new P[] { new P(2,0), new P(2,1), new P(1,1), new P(1,2) }, new P(2,3), new P(1,0)) },
        LEFT_BOLT, "leftBolt");

    /** the DOWN L
     *     x x .   . . x   . x .   . . .
     *     . x .   x x x   . x .   x x x
     *     . x .   . . .   . x x   x . .
     */
    Block downL = new Block(
        new RB[] {
            new RB(new P[] { new P(0,0), new P(0,1), new P(1,1), new P(2,1) }, new P(3,2), new P(0,0)),
            new RB(new P[] { new P(1,0), new P(1,1), new P(1,2), new P(0,2) }, new P(2,3), new P(0,0)),
            new RB(new P[] { new P(0,1), new P(1,1), new P(2,1), new P(2,2) }, new P(3,2), new P(0,1)),
            new RB(new P[] { new P(2,0), new P(1,0), new P(1,1), new P(1,2) }, new P(2,3), new P(1,0)) },
        DOWN_L, "downL");

    /** the RIGHT BOLT
     *     . . x   . . .   . x .   x x .
     *     . x x   x x .   x x .   . x x
     *     . x .   . x x   x . .   . . .
     */
    Block rightBolt = new Block(
        new RB[] {
            new RB(new P[] { new P(0,2), new P(1,2), new P(1,1), new P(2,1) }, new P(3,2), new P(0,1)),
            new RB(new P[] { new P(1,0), new P(1,1), new P(2,1), new P(2,2) }, new P(2,3), new P(1,0)),
            new RB(new P[] { new P(0,1), new P(1,1), new P(1,0), new P(2,0) }, new P(3,2), new P(0,0)),
            new RB(new P[] { new P(0,0), new P(0,1), new P(1,1), new P(1,2) }, new P(2,3), new P(0,0)) },
        RIGHT_BOLT, "rightBolt");

    /** the UP L
     *     . x x   . . .   . x .   x . .
     *     . x .   x x x   . x .   x x x
     *     . x .   . . x   x x .   . . .
     */
    Block upL = new Block(
        new RB[] {
            new RB(new P[] { new P(0,2), new P(0,1), new P(1,1), new P(2,1) }, new P(3,2), new P(0,1)),
            new RB(new P[] { new P(1,0), new P(1,1), new P(1,2), new P(2,2) }, new P(2,3), new P(1,0)),
            new RB(new P[] { new P(0,1), new P(1,1), new P(2,1), new P(2,0) }, new P(3,2), new P(0,0)),
            new RB(new P[] { new P(0,0), new P(1,0), new P(1,1), new P(1,2) }, new P(2,3), new P(0,0)) },
        UP_L, "upL");


    class Block {
        private RB[] rotations; // should be constant
        private int rotptr; // current index in `rotations`
        private int color;
        private Position boxUL; // upper left corner of enclosing 3x3 box
        private String name; // for debugging
        private boolean contact;

        /////////////////////////////////////////////////////////////////////////////////////////////////
        // INTERFACE for Tetris class

        Block(RB[] rotations, int color, String name) {
            this.rotations = rotations;
            this.color = color;
            this.name = name;
            boxUL = new Position(0, 0);
        }

        synchronized void newBlock() {
            System.out.println("Tetris::Block::newBlock() "+ name);
            // pick a rotation
            rotptr = rgen.nextInt(rotations.length);
            // pick a horizonal position for the min-UL
            int offset = rgen.nextInt(ref.width - rotations[rotptr].dim.n);
            // set the offset for the 3x3-UL
            boxUL.m = 0;
            boxUL.n = offset - rotations[rotptr].UL.n;
            contact = false;
        }

        synchronized void drawNewBlock() {
            // draw the block
            if (!collisionCheck(0,0)) {
                contactCheck();
                setVisible(true);
                if (contact && rotations[rotptr].UL.m == 0) {
                    ref.setOver();
                }
            } else {
                ref.setOver();
                // draw the block partially; the lower part that fits
                drawPartial();
            }
        }

        void tick() {
            move(Request.DUMMY, true);
        }

        void request(Request r) {
            move(r, false);
        }

        /** does the block touch the heap? */
        boolean contacts() {
            return contact;
        }


        //////////////////////////////////////////////////////////////////////////////////////////
        // private HELP FUNCTIONS

        private synchronized void move(Request request, boolean tick) {
            // assume a contact check is done in `simulate()` before calling the function...

            // first erase the block
            setVisible(false);

            // Then move it
            if (tick) {
                boxUL.m++;
                calcMove(request); // for the contact check
            }
            else if (request == Request.DROP) {
                while (!contact)
                    calcMove(Request.DOWN);
            }
            else calcMove(request);

            // then redraw the block
            setVisible(true);
        }

        private void setVisible(boolean flag) {
            for (Position s : rotations[rotptr].squares) {
                set(boxUL.m + s.m, boxUL.n + s.n, flag?color:EMPTY);
            }
        }

        private void calcMove(Request request) {
            /** Moving:
             * We want to avoid overwriting other blocks when moving left/right,
             * so first we do collsion check.
             * This will only work if the block has been temporarily deleted, as done in `move()`.
             */
            int tmpptr; // for rotation
            switch (request) {
            case LEFT:
                if (boxUL.n + rotations[rotptr].UL.n > 0) {
                    if (!collisionCheck(0,-1)) boxUL.n--;
                }
                break;
            case RIGHT:
                if (boxUL.n + rotations[rotptr].UL.n + rotations[rotptr].dim.n < ref.width) {
                    if (!collisionCheck(0,1)) boxUL.n++;
                }
                break;
            case DOWN:
                if (contact) return; // RETURN early: there is nothing to be done
                if (boxUL.m + rotations[rotptr].UL.m + rotations[rotptr].dim.m < ref.height) {
                    if (!collisionCheck(1,0)) boxUL.m++;
                }
                break;
            case ROTATE_CW:
                tmpptr = (rotptr + 1) % rotations.length;
                rotateCollision(tmpptr);
                break;
            case ROTATE_CCW:
                tmpptr = (rotations.length + rotptr - 1) % rotations.length;
                rotateCollision(tmpptr);
                break;
            default:
                break;
            }
            // FINALLY, check if we contact the other blocks or the floor now
            contactCheck();
        }

        private boolean collisionCheck(int dm, int dn) {
            boolean collision = false;
            for (Position s : rotations[rotptr].squares) {
                collision = collision || (game[boxUL.m + s.m + dm][boxUL.n + s.n + dn] != EMPTY);
            }
            return collision;
        }


        private void contactCheck() {
            contact = true;
            // avoid out-of-bounds access: if we are not on the floor, check the surrounding squares
            if (boxUL.m + rotations[rotptr].UL.m + rotations[rotptr].dim.m < ref.height) {
                contact = false;
                for (Position s : rotations[rotptr].squares) {
                    contact = contact || (game[boxUL.m + s.m + 1][boxUL.n + s.n] != EMPTY);
                }
            }
        }

        private void rotateCollision(int tmpptr) {
            // avoid out-of-bounds access:
            if ((boxUL.n + rotations[tmpptr].UL.n >= 0) // not outside left wall
                && // not outside right wall
                (boxUL.n + rotations[tmpptr].UL.n + rotations[tmpptr].dim.n <= ref.width)
                && // not below floor
                (boxUL.m + rotations[tmpptr].UL.m + rotations[tmpptr].dim.m <= ref.height)
                ) {
                boolean collision = false;
                for (Position s : rotations[tmpptr].squares) {
                    collision = collision || (game[boxUL.m + s.m][boxUL.n + s.n] != EMPTY);
                }
                if (! collision)
                    rotptr = tmpptr;
            }
        }

        private void drawPartial() {
            // first decide the offset
            int offset;
            // TODO think about the range here
            for (offset=0; offset<rotations[rotptr].UL.m+rotations[rotptr].dim.m; offset++) {
                boolean collision = false;
                for (Position s : rotations[rotptr].squares) {
                    if (boxUL.m + s.m - offset >= 0)
                        collision = collision || (game[boxUL.m + s.m - offset][boxUL.n + s.n] != EMPTY);
                }
                if (!collision) break;
            }
            // then draw the block (the squares that are visible)
            for (Position s : rotations[rotptr].squares) {
                if (boxUL.m + s.m - offset >= 0)
                    set(boxUL.m + s.m - offset, boxUL.n + s.n, color);
            }
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    //////////// END of Blocks //////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////

    /** Yes, instances, they are inactive until invoked. There is never more than one visible.
     * Invoking (creating a "new" block) means just resetting the instance of that type.
     * TODO this will be a problem if you want a preview of the next one...
     */
    List<Block> allTypes = Arrays.asList(longOne , square, halfplus, downL, upL, leftBolt, rightBolt);
    Block block; // current incoming


    public Tetris() {
        super(20, 10);
        squareSize = 20;
        tick = 1000;
    }

    private void newBlock() {
        synchronized (this) {
            block = allTypes.get(rgen.nextInt(allTypes.size()));
            block.newBlock();
        }
    }

    /////////////////////////////////////////////////////////////////////
    // INITIALIZATION; Implementation of functions declared in BaseModel

    @Override
    protected void reset() {
        super.reset();
        newBlock();
    }

    @Override
    protected void fill() {
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                game[i][j] = EMPTY;
            }
        }
        block.drawNewBlock();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Functions declared in tick.Model

    @Override
    public void simulate() {
        if (!isOver) {
            synchronized (this) {
                if (block.contacts()) {
                    pointCheck();
                    newBlock();
                    block.drawNewBlock();
                } else
                    block.tick();
            }
        }
    }

    @Override
    public void request(int keynr) {
        if (!isOver) {
            if (keynr==Controller.LEFT)
                block.request(Request.LEFT);
            else if (keynr==Controller.RIGHT)
                block.request(Request.RIGHT);
            else if (keynr==Controller.ENTER)
                block.request(Request.ROTATE_CW);
            else if (keynr==Controller.UP)
                block.request(Request.ROTATE_CCW);
            else if (keynr==Controller.DOWN)
                block.request(Request.DOWN);
            else if (keynr==Controller.DD) {
                synchronized (this) {
                    block.request(Request.DROP);
                    // and finally
                    pointCheck();
                    newBlock();
                    block.drawNewBlock();
                }
            }
        }
    }

    @Override
    public Color translate(int element) {
        if (element==EMPTY)
            return Color.GRAY;
        if (element==LONG)
            return Color.RED;
        if (element==SQUARE)
            return Color.BLUE;
        if (element==HALFPLUS)
            return Color.GREEN;
        if (element==UP_L)
            return Color.MAGENTA;
        if (element==DOWN_L)
            return Color.CYAN;
        if (element==LEFT_BOLT)
            return Color.YELLOW;
        if (element==RIGHT_BOLT)
            return new Color(0xff, 0x8c, 0x00); // "dark orange" instead of Color.ORANGE;
        return null;
    }

    //////////////////////////////////////////////////////////////////////////////
    // private HELP FUNCTIONS

    private void pointCheck() {
        boolean completeRow;
        int collapse = 0;
        for (int i=height-1; i>=0; i--) {
            completeRow = true;
            for (int j=0; j<width; j++) {
                completeRow = completeRow && (game[i][j] != EMPTY);
                if (collapse > 0) {
                    set(i+collapse, j, game[i][j]);
                }
            }
            if (completeRow) collapse++;
        }
        points += collapse;
        if (collapse > 0)
            System.out.println("Tetris::pointCheck() +"+collapse+" --> "+points+" points!");
    }

    void setOver() {
        isOver = true;
        result = "Game over! Your score: "+points+" points.";
    }

}
