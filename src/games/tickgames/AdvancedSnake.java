package games.tickgames;

import games.Position;
import java.util.List;
import java.util.Arrays;

/* Extensions to SnakeModel
 * - planned: increase level
 * - planned: add optional walls
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
        int size;
        int pointLimit;

        Level(int S, int P) {
            size = S;
            pointLimit = P;
        }

        /** Add all the walls. */
        abstract void addWalls();

        /** Give the next position when you pass through an edge of the matrix. */
        abstract Position advance(Position head, int direction);


    }
    Level torus = new Level(20, 10) {
            @Override
            void addWalls() {}

            @Override
            Position advance(Position head, int direction) {
                int adir = Math.abs(direction);
                int dm = (adir & 1) * direction;
                int dn = (adir >> 1) * (direction >> 1);
                int M = (head.m + ref.height + dm) % ref.height;
                int N = (head.n + ref.width + dn) % ref.width;
                return new Position(M, N);
            }
        };
    Level plane = new Level(20, 5) {
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

            @Override
            Position advance(Position head, int direction) {
                int adir = Math.abs(direction);
                int dm = (adir & 1) * direction;
                int dn = (adir >> 1) * (direction >> 1);
                if ( (head.m + dm == ref.height) || (head.n + dn == ref.width) )
                    assert(false); // hit a wall
                int M = (head.m + ref.height + dm) % ref.height;
                int N = (head.n + ref.width + dn) % ref.width;
                return new Position(M, N);
            }
        };

    List<Level> levels = Arrays.asList( torus,
                                        plane

                                       );


    ///////////////////////////////////////////////////////////////////////////////////
    // LOGIC

        public AdvancedSnake() {
                super();
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
        protected int advance(){
                Position headpos = snake.peekFirst();
                Position newpos = level.advance(headpos, direction);
                int object = game[newpos.m][newpos.n];
                if (object != SELF && object != WALL){
                        set(headpos.m, headpos.n, SELF);
                        set(newpos.m, newpos.n, HEAD);
                        snake.addFirst(newpos);
                }
                return object;
        }

        @Override
        public void simulate(int request){
            super.simulate(request);
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

        // used by move()
    @Override
    void eatCheese() {
        super.eatCheese();
        System.out.println("Your point: "+points+" of "+level.pointLimit);
    }
}
