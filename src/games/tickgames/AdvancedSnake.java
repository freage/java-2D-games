package games.tickgames;

import games.Position;
import java.util.List;
import java.util.Arrays;

/* Extensions to SnakeModel
 * - increase level
 * - add optional walls
 *
 */

public class AdvancedSnake extends Snake {
    Level level;
    int lvlptr;
    int wait;
    boolean allLevelsCleared;

    /////////////////////////////////////////////////////////////////////////////
    // LEVELS

    AdvancedSnake ref = this;
    abstract class Level {
        int pointLimit;

        Level(int P) {
            pointLimit = P;
        }

        ////////////////////////////////////////////////////////
        // METHODS to override

        /** Add all the walls. */
        void addWalls() {}

        /** Give the next position when you pass through an edge of the matrix. */
        Position advance(Position head, int direction) {
            int adir = Math.abs(direction);
            // int north_south = (adir & 1); // 1 if north/south, 0 otherwise
            // int east_west = (adir >> 1); // 1 if east/west, 0 otherwise
            int dm = (adir & 1) * direction;
            int dn = (adir >> 1) * (direction / 2);
            int M = (head.m + ref.height + dm) % ref.height;
            int N = (head.n + ref.width + dn) % ref.width;
            return new Position(M, N);
        }

        int isAlt(Position p) {
            return NALT;
        }




    }
    Level torus = new Level(10) {
            // Standard torus
        };
    Level plane = new Level(5) {
            // A square, i.e. all edges are walls.
            @Override
            void addWalls() {
                for (int i=0; i<ref.height; i++) {
                    ref.set(i, 0, WALL);
                    ref.set(i, width-1, WALL);
                }
                for (int j=0; j<ref.width; j++) {
                    ref.set(0, j, WALL);
                    ref.set(height-1, j, WALL);
                }
            }
        };

    Level inv_plane = new Level(5) {
            // The walls form a plus instead of a square. Torus edges. Basically the same as a square.
            @Override
            void addWalls() {
                for (int i=0; i<ref.height; i++) {
                    ref.set(i, ref.width/2, WALL);
                    ref.set(i, ref.width/2-1, WALL);
                }
                for (int j=0; j<ref.width; j++) {
                    ref.set(ref.height/2, j, WALL);
                    ref.set(ref.height/2-1, j, WALL);
                }
            }
        };

    Level mirrored_columns = new Level(10) {
            // The right/left edges are ordinary torus edges.
            // However, the up/down edges have you enter the *same* wall you exited, in the mirrored column.
            @Override
            void addWalls() {
                for (int j=0; j<ref.width; j++) {
                    ref.set(0, j, PORTAL);
                    ref.set(height-1, j, PORTAL);
                }
            }

            @Override
            Position advance(Position head, int direction) {
                Position p = super.advance(head, direction);
                boolean exit_north = (head.m == 1) && (direction == NORTH);
                boolean exit_south = (head.m == ref.height-2) && (direction == SOUTH);
                if (exit_north || exit_south) {
                    p.m = head.m;
                    p.n = ref.width - 1 - head.n;
                    ref.direction = -direction;
                }
                return p;
            }
        };

    Level corners = new Level(5) {
            // A square, i.e. all edges are walls.
            @Override
            void addWalls() {
                int dh = ref.height/6;
                int dw = ref.width/6;
                // rows (columns constant)
                for (int i=dh; i<2*dh; i++) {
                    ref.set(i, dw, WALL);
                    ref.set(height-1-i, dw, WALL);
                    ref.set(i, width-1-dw, WALL);
                    ref.set(height-1-i, width-1-dw, WALL);
                }
                // columns (rows constant)
                for (int j=dw; j<2*dw; j++) {
                    ref.set(dh, j, WALL);
                    ref.set(dh, width-1-j, WALL);
                    ref.set(height-1-dh, j, WALL);
                    ref.set(height-1-dh, width-1-j, WALL);
                }
            }
        };

    Level moebius_strip = new Level(10) {
            // north/south edge are walls
            // east/west edge are torus edges, except that the m-coordinate is mirrored
            @Override
            void addWalls() {
                for (int i=0; i<ref.height; i++) {
                    ref.set(i, 0, PORTAL);
                    ref.set(i, width-1, PORTAL);
                }
                for (int j=0; j<ref.width; j++) {
                    ref.set(0, j, WALL);
                    ref.set(height-1, j, WALL);
                }
            }
            @Override
            Position advance(Position head, int direction) {
                Position p = super.advance(head, direction);
                boolean exit_west = (head.n == 1) && (direction == WEST);
                boolean exit_east = (head.n == ref.width-2) && (direction == EAST);
                if (exit_west || exit_east) {
                    p.m = ref.height - 1 - head.m;
                    p.n = ref.width - 1 - head.n;
                }
                return p;
            }
        };

    Level columns2D = new Level(10) {
            @Override
            void addWalls() {
                // for (int i=0; i<ref.height; i++) {
                //     ref.set(i, 0, PORTAL);
                //     ref.set(i, width-1, PORTAL);
                // }
            }
            @Override
            Position advance(Position head, int direction) {
                int adir = Math.abs(direction);
                // int north_south = (adir & 1); // 1 if north/south, 0 otherwise
                // int east_west = (adir >> 1); // 1 if east/west, 0 otherwise
                int dm = (adir & 1) * direction;
                int dn = (adir >> 1) * direction; // change here
                int M = (head.m + ref.height + dm) % ref.height;
                int N = (head.n + ref.width + dn) % ref.width;
                return new Position(M, N);
            }
            @Override
            int isAlt(Position p) {
                return (p.n % 2) << 3;
                // if (p.n % 2 == 1)
                //     return ALT;
                // else
                //     return NALT;
            }
        };

    List<Level> levels = Arrays.asList( torus,
                                        plane,
                                        inv_plane,
                                        mirrored_columns,
                                        corners,
                                        moebius_strip,
                                        columns2D
                                        );


    ///////////////////////////////////////////////////////////////////////////////////
    // LOGIC

    public AdvancedSnake() {
        super(20, 21);
        lvlptr = 0;
        level = levels.get(lvlptr);
        allLevelsCleared = false;
    }

    @Override
    protected void fill() {
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                set(i, j, EMPTY);
            }
        }
        level.addWalls();
        buildSnake();
    }

    @Override
    protected int advance() {
        int object;
        synchronized (this.snake) {
            Position headpos = snake.peekFirst();
            Position newpos = level.advance(headpos, direction);
            object = game[newpos.m][newpos.n];
            if ((object & ~ALT) != SELF && object != WALL) {
                set(headpos.m, headpos.n, isAlt(headpos) | SELF);
                set(newpos.m, newpos.n, isAlt(headpos) | HEAD);
                snake.addFirst(newpos);
            }
        }
        return object;
    }

    @Override
    public void simulate() {
        super.simulate();
        if (isOver && !allLevelsCleared) {
            if (points >= level.pointLimit) {
                if (! pause)
                    System.out.println("You cleared level "+lvlptr+"!");
                if (lvlptr < levels.size()-1 ) {
                    level = levels.get(++lvlptr);
                    // fire a leveling event to the Controller; to have a proper reset
                    reset();
                    fill();
                    pause = true;
                    isOver = false;
                    wait = 0;
                } else {
                    isOver = true;
                    allLevelsCleared = true;
                    System.out.println("You cleared all levels!");
                }
            }
        } else if (pause) {
            System.out.println("Pausing..."+wait);
            if (++wait > 10) pause = false;
        }
    }
    @Override
    protected int isAlt(Position p) {
        return level.isAlt(p);
    }

    // used by move()
    @Override
    void eatCheese() {
        super.eatCheese();
        System.out.println("Your point: "+points+" of "+level.pointLimit);
    }
}
