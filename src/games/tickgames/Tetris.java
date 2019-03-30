package games.tickgames;

import games.Position;
import java.util.List;
import java.util.Arrays;
import java.awt.Color;

public class Tetris extends Model {
    Tetris ref = this;
    // "Colors":
    final static int EMPTY = 0;
    final static int LONG = 1;
    final static int SQUARE = 2;
    final static int HALFPLUS = 3;
    final static int UP_L = 4;
    final static int DOWN_L = 5;
    final static int LEFT_BOLT = 6;
    final static int RIGHT_BOLT = 7;

    // TODO sed s/brick/block/g

    class Brick {
        Position[] squares;
        Position[] initial; // should be constant
        int color;
        Position dimensions; // should be constant
        Position boxUL; // upper left corner of minimal enclosing box
        String name; // for debugging
        Brick(Position[] squares, Position dimensions, int color, String name) {
            this.squares = squares;
            // deep copy squares -> initial
            this.initial = new Position[squares.length];
            for (int i=0; i<initial.length; i++) {
                initial[i] = new Position(squares[i].m, squares[i].n);
            }
            boxUL = new Position(dimensions.m, dimensions.n);
            this.color = color;
            this.dimensions = dimensions;
            this.name = name;
        }

        synchronized void newBrick() {
            System.out.println("Tetris::Brick::newBrick() "+ name);
            for (int i=0; i<initial.length; i++) {
                squares[i].m = initial[i].m;
                squares[i].n = initial[i].n;
            }
            int offset = rgen.nextInt(ref.width - dimensions.n);
            for (Position s : squares) {
                s.n += offset;
            }
            boxUL.m = 0;
            boxUL.n = offset;
        }

        void tick() {
            move(Model.NONE, true);
        }

        void request(int r) {
            move(r, false);
        }

        synchronized void move(int request, boolean tick) {
            // TODO anything else that should be synchronized?
            // TODO add support for rotation (space?)

            // first erase the brick and increase (fall) all y-coordinates with 1
            for (Position s : squares) {
                set(s.m, s.n, EMPTY);
                if (tick) {
                    s.m++;
                    boxUL.m++;
                }
            }
            /** Moving left/right:
             * We want to avoid overwriting other blocks when moving left/right,
             * so first we do collsion check.
             */
            boolean noCollision = true;
            switch (request) {
            case WEST:
                if (boxUL.n > 0) {
                    for (Position s : squares) noCollision = noCollision && (game[s.m][s.n-1] == EMPTY);
                    if (noCollision) {
                        for (Position s : squares) s.n--;
                        boxUL.n--;
                    }

                }
                break;
            case EAST:
                if (boxUL.n + dimensions.n < ref.width) {
                    for (Position s : squares) noCollision = noCollision && (game[s.m][s.n+1] == EMPTY);
                    if (noCollision) {
                        for (Position s : squares) s.n++;
                        boxUL.n++;
                    }
                }
                break;
            case SOUTH:
                int minDrop = ref.height;
                for (Position s : squares)
                    minDrop = Math.min(minDrop, columnTops[s.n]-s.m-1);
                for (Position s : squares)
                    s.m += minDrop;
                boxUL.m += minDrop;
                break;
            default:
                break;
            }
            // then redraw the brick
            for (Position s : squares) {
                set(s.m, s.n, color);
            }
        }


        // TODO: Should `contacts()` and `transfer()` be synchronized as well?

        boolean contacts() { // does the brick touch the heap?
            for (Position s : squares) {
                if (ref.columnTops[s.n] == s.m+1 )
                    return true;
            }
            return false;
        }

        void transfer() { // when you hit the heap, transfer the squares to the heap
            for (Position s : squares) {
                ref.columnTops[s.n] = Math.min(ref.columnTops[s.n], s.m);
            }
        }
    }

    Brick longOne = new Brick(new Position[] {new Position(0,0),
                                              new Position(1,0),
                                              new Position(2,0),
                                              new Position(3,0) },
        new Position(4,1), LONG, "long");
    Brick square = new Brick(new Position[] {new Position(0,0),
                                             new Position(1,0),
                                             new Position(0,1),
                                             new Position(1,1) },
        new Position(2,2), SQUARE, "square");
    Brick halfplus = new Brick(new Position[] {new Position(0,0),
                                               new Position(1,0),
                                               new Position(2,0),
                                               new Position(1,1) },
        new Position(3,2), HALFPLUS, "halfplus");
    Brick downL = new Brick(new Position[] {new Position(0,0),
                                            new Position(1,0),
                                            new Position(2,0),
                                            new Position(2,1) },
        new Position(3,2), DOWN_L, "down_l");
    Brick upL = new Brick(new Position[] {new Position(0,0),
                                          new Position(1,0),
                                          new Position(2,0),
                                          new Position(0,1) },
        new Position(3,2), UP_L, "up_l");
    Brick leftBolt = new Brick(new Position[] {new Position(0,0),
                                               new Position(1,0),
                                               new Position(1,1),
                                               new Position(2,1) },
        new Position(3,2), LEFT_BOLT, "left_bolt");

    Brick rightBolt = new Brick(new Position[] {new Position(0,1),
                                               new Position(1,1),
                                               new Position(1,0),
                                               new Position(2,0) },
        new Position(3,2), RIGHT_BOLT, "right_bolt");
    /////////////////////////////////////////////////////////////////////////////////////////////
    //////////// END of Bricks //////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////

    List<Brick> allTypes = Arrays.asList(longOne, square, halfplus, downL, upL, leftBolt, rightBolt); // Yes, instances, they are inactive until invoked. There is never more than one visible. Invoking (creating a "new" brick) means just resetting that instance. TODO this will be a problem if you want a preview of the next one...
    Brick brick; // current incoming
    int[] columnTops; // y-coordinates for the top bricks in the heap
    // private int request; // to have only one request per tick


    public Tetris() {
        super(20, 20);
        squareSize = 20;
        tick = 1000;
        columnTops = new int[width];
    }

    private void newBrick() {
        // TODO this should preferrably also be a synchronized method, of Brick (!)
        brick = allTypes.get(rgen.nextInt(allTypes.size()));
        brick.newBrick();
    }

        /////////////////////////////////////////////////////////////////////
        // INITIALIZATION; Implementation of functions declared in BaseModel

        @Override
        protected void reset() {
                super.reset();
                newBrick();
                for (int j=0; j<width; j++)
                    columnTops[j] = height;
        }

        @Override
        protected void fill() {
            for (int i=0; i<height; i++) {
                for (int j=0; j<width; j++) {
                    game[i][j] = EMPTY;
                }

            }
            // initially no content
            // TODO entering brick maybe?
        }

        ////////////////////////////////////////////////////////////////////////////
        // Functions declared in tick.Model

        @Override
        public void simulate(){
                if (!isOver) {
                        brick.tick();
                        if (brick.contacts()) {
                            brick.transfer();
                            pointCheck();
                            newBrick();
                        }
                }
        }

        // reset after each actionevent
        @Override
        public void request(int keynr){
                if (keynr==Controller.LEFT)
                        brick.request(WEST);
                else if (keynr==Controller.RIGHT)
                        brick.request(EAST);
                else if (keynr==Controller.DOWN) {
                        brick.request(SOUTH);
                        // and finally
                        brick.transfer();
                        pointCheck();
                        newBrick();
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
                        return Color.ORANGE;
                return null;
        }

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
            if (completeRow) {
                System.out.println("Tetris::pointCheck() Row "+i+" is complete!");
                collapse++;
                for (int j=0; j<width; j++) {
                    if (columnTops[j] == i) {
                        for (int ii=i+collapse; (ii < height) && (game[ii][j] == EMPTY); ii++) {
                            columnTops[j]++;
                        }
                    }
                }
            }
        }
        points += collapse;
        for (int j=0; j<width; j++) {
            columnTops[j] += collapse;
        }
        System.out.println("Tetris::pointCheck() "+points+" points!");
    }



}
