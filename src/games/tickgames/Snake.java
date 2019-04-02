package games.tickgames;

/* Ordinary Snake on a torus. Can be extended and use more advanced topologies including walls and "portals".
 *
 */

import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

import games.Position;
import games.BaseModel;

public class Snake extends Model {
        // volatile: operations are atomic
        protected int direction; // possible directions found the tickgames.Model Interface
        protected volatile LinkedList<Position> snake;

        // all possible objects on the board
        final static int WALL = 1;
        final static int CHEESE = 2;
        final static int SELF = 3;
        final static int HEAD = 4;
        final static int EMPTY = 0;
        final static int PORTAL = 5;
        final static int ALT = 8;
        final static int NALT = 0;
        final static int ALT_SELF = ALT | SELF; // 11
        final static int ALT_HEAD = ALT | HEAD; // 12

        // other:
        int calls; // keep track of ticks, as to know when to add a new cheese
        int cheeses; // have at most 3 cheeses at the board
        boolean pause = false;
        private int request; // to have only one request per tick


        // GAME CONSTRUCTOR

        public Snake() {
                this(20, 20);
        }

        protected Snake(int H, int W) {
                super(H, W);
                title = "Snake";
                squareSize = 15;
                tick = 100;
        }
        /////////////////////////////////////////////////////////////////////
        // INITIALIZATION; Implementation of functions declared in BaseModel

        @Override
        protected void reset() {
                super.reset();
                calls = 0;
                cheeses = 0;
                request = NORTH;
        }

        @Override
        protected void fill() {
                for (int i=0; i<height; i++) {
                        for (int j=0; j<width; j++) {
                                game[i][j] = EMPTY;
                        }
                }
                buildSnake();
        }

        protected void buildSnake() {
                snake = new LinkedList<Position>();
                // add a head:
                int m, n;
                do {
                        m = rgen.nextInt(game.length);
                        n = rgen.nextInt(game.length);
                } while ((game[m][n]!=EMPTY)
                         || (game[(m+1)%game.length][n]!=EMPTY)
                         || (game[(m+2)%game.length][n]!=EMPTY));
                addSegment(m, n, isAlt(new Position(m,n)) | HEAD);
                // a body segment:
                addSegment((m + 1) % game.length, n, isAlt(new Position(m,n)) | SELF);
                // and a tail:
                addSegment((m + 2) % game.length, n, isAlt(new Position(m,n)) | SELF);
                // initially going north:
                direction = NORTH;
        }

        // used buildSnake()
        private void addSegment(int m, int n, int element){
                Position pos = new Position(m, n);
                set(m, n, element);
                snake.addLast(pos);
        }

        ////////////////////////////////////////////////////////////////////////////
        // Functions declared in tick.Model

        @Override
        public void simulate(){
                if (!isOver && !pause) {
                    int r = this.request; // local copy because it can be changed by `request()`
                        if (r!=-direction)
                                direction = r;
                        move();
                        calls++;
                        if (calls%10==0 && cheeses < 3){
                                addCheese();
                        }
                }
        }

        // reset after each actionevent (nope, no longer)
        @Override
        public void request(int keynr){
                if (keynr==Controller.UP)
                        request = NORTH;
                else if (keynr==Controller.DOWN)
                        request = SOUTH;
                else if (keynr==Controller.LEFT)
                        request = WEST;
                else if (keynr==Controller.RIGHT)
                        request = EAST;
        }

        @Override
        public Color translate(int element) {
                if (element==EMPTY)
                        return Color.BLACK;
                if (element==WALL)
                        return Color.GRAY;
                if (element==SELF)
                        return Color.RED;
                if (element==HEAD)
                        return Color.ORANGE;
                if (element==CHEESE)
                        return Color.YELLOW;
                if (element==PORTAL)
                        return Color.BLUE;
                if (element==ALT_SELF)
                        return new Color(0x0, 0x80, 0x0); // green
                if (element==ALT_HEAD)
                        return new Color(0x0, 0xff, 0x0); // lime
                return null;
        }


        //////////////////////////////////////////////////////////////////////////////
        // PRIVATE HELP FUNCTIONS for model logic

        // used by public method simulate()
        void move(){
                int object = advance();
                if (object == WALL || object == SELF){
                        isOver = true;
                } else if (object == CHEESE){
                        eatCheese();
                } else if (object==EMPTY) popTail();
                if (isOver){
                        result = "You got "+points+" points.";
                }
        }

        // used by private method move()
        protected int advance(){
            int direction = this.direction; // local variable to avoid having it change
                int adir = Math.abs(direction);
                // the second factor is just the sign
                int dm = (adir & 1) * direction;
                int dn = (adir >> 1) * (direction / 2);
                // int dm = 0, dn = 0;
                // if (direction == NORTH){
                //         dm = -1;
                //         dn = 0;
                // } else if (direction == SOUTH){
                //         dm = 1;
                //         dn = 0;
                // } else if (direction == WEST){
                //         dm = 0;
                //         dn = -1;
                // } else if (direction == EAST){
                //         dm = 0;
                //         dn = 1;
                // }
                /** you may not change the list `snake` while this is executing
                 * - two `advance()`-calls could interleave
                 * - the `popTail()` function could interleave with this
                 */
                int object;
                synchronized (this.snake) {
                Position headpos = snake.peekFirst();
                int headM = (headpos.m + game.length + dm) % game.length;
                int headN = (headpos.n + game.length + dn) % game.length;
                object = game[headM][headN];
                if ((object & ~ALT) != SELF && object != WALL){
                // if (object != SELF && object != WALL){
                        set(headpos.m, headpos.n, isAlt(headpos) | SELF);
                        Position newpos = new Position(headM, headN);
                        set(newpos.m, newpos.n, isAlt(newpos) | HEAD);
                        snake.addFirst(newpos);
                }
                }
                return object;
        }

        /** Override this if you want to use a different colored snake in certain positions. */
        protected int isAlt(Position p) {
                return NALT;
        }

        // used by move()
        void eatCheese(){
                cheeses--;
                points++;
        }

        // used by move()
        private void popTail(){
                Position tailpos = snake.pollLast();
                set(tailpos.m, tailpos.n, EMPTY);
        }

        // used by simulate()
        void addCheese(){
                int m, n;
                do {
                        m = rgen.nextInt(game.length);
                        n = rgen.nextInt(game.length);

                } while (game[m][n] != EMPTY);
                set(m, n, CHEESE);
                cheeses++;
        }


        ////////////////////////////////////////////////////////////////////
        // MAIN METHOD (for debugging)

        public static void main(String[] args) {
                Snake sm = new Snake();
                sm.printMatrix(sm.game);
        }

}
