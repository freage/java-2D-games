package games.tickgames;

/* This class handles the navigation.
 * Counting points in done in AdvancedSnake
 *
 */

import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

import games.Position;
import games.BaseModel;

public class Snake extends Model {
        protected int direction; // possible directions found the tickgames.Model Interface
        protected LinkedList<Position> snake;

        // all possible objects on the board
        final static int WALL = 1;
        final static int CHEESE = 2;
        final static int SELF = 3;
        final static int EMPTY = 0;
        final static int HEAD = 4;

        // other:
        int calls; // keep track of ticks, as to know when to add a new cheese
        int cheeses; // have at most 3 cheeses at the board
        Random rgen; // for new random positions
        boolean pause = false;


        // GAME CONSTRUCTOR

        public Snake(){
                super(20, 20);
                rgen = new Random();
        }

        /////////////////////////////////////////////////////////////////////
        // INITIALIZATION; Implementation of functions declared in BaseModel

        @Override
        protected void reset() {
                super.reset();
                calls = 0;
                cheeses = 0;
                title = "Snake";
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

        protected void buildSnake(){
                snake = new LinkedList<Position>();
                // add a head:
                int m, n;
                do {
                        m = rgen.nextInt(game.length);
                        n = rgen.nextInt(game.length);
                } while (game[m][n]==WALL);
                addSegment(m, n, HEAD);
                // a body segment:
                addSegment((m + 1) % game.length, n, SELF);
                // and a tail:
                addSegment((m + 2) % game.length, n, SELF);
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
        public void simulate(int request){
                if (!isOver && !pause) {
                        if (request!=NONE && request!=-direction)
                                direction = request;
                        move();
                        calls++;
                        if (calls%10==0 && cheeses < 3){
                                addCheese();
                        }
                }
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
                int adir = Math.abs(direction);
                // the second factor is just the sign
                int dm = (adir & 1) * direction;
                int dn = (adir >> 1) * (direction >> 1);
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
                Position headpos = snake.peekFirst();
                int headM = (headpos.m + game.length + dm) % game.length;
                int headN = (headpos.n + game.length + dn) % game.length;
                int object = game[headM][headN];
                if (object != SELF && object != WALL){
                        set(headpos.m, headpos.n, SELF);
                        Position newpos = new Position(headM, headN);
                        set(newpos.m, newpos.n, HEAD);
                        snake.addFirst(newpos);
                }
                return object;
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

                } while (! (game[m][n] == EMPTY));
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
