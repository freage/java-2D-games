package games.tickgames;

/* Denna klass sköter spelplan och navigation
 * Poängräkning mm finns i AdvancedSnakeModel
 *
 */

import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

import games.BaseModel;

public class Snake extends BaseModel implements Model {
        // level, points, game (dvs matris) och isOver finns i tickgames.Model
        // riktningarna finns i interface
        // TODO: ändra i riktningarna och skapa en isOrthogonal(dir1, dir2) som är oberoende av int-representat
        private int direction;
        private LinkedList<Position> snake;

        // objekt på spelplanen
        final static int WALL = 1;
        final static int CHEESE = 2;
        final static int SELF = 3;
        final static int EMPTY = 0;
        final static int HEAD = 4;

        // övrigt
        int calls; // används i huvudsak av barn
        int cheeses; // används i huvudsak av barn
        Random rgen = new Random(); // används för att slumpa fram positioner


        // GAME CONSTRUCTOR

        public Snake(){
                start();
        }

        private void start(){
                initialise(20,20);
                calls = 0;
                cheeses = 0;
                observers.notifyObservers();
                buildSnake();
                title = "Snake";
        }


        // PUBLIC IMPLEMENTED METHODS AND SELECTORS

        @Override
        public void simulate(int request){
                if (!isOver || request==RESTART){
                        if (request==NONE);
                        else if (request==RESTART)
                                restart();
                        else if (request!=-direction)
                                direction = request;
                        move();
                }
        }

        @Override
        public void restart(){
                start();
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


        // PRIVATE HELP FUNCTIONS

        // used in constructor
        private void buildSnake(){
                snake = new LinkedList<Position>();
                // lägg till huvud:
                int m = rgen.nextInt(game.length);
                int n = rgen.nextInt(game.length);
                addSegment(m, n, HEAD);
                // ett kroppssegment:
                addSegment((m + 1) % game.length, n, SELF);
                // samt svans:
                addSegment((m + 2) % game.length, n, SELF);
                // på väg norrut:
                direction = NORTH;
        }

        // used buildSnake()
        private void addSegment(int m, int n, int element){
                Position pos = new Position();
                pos.m = m;
                pos.n = n;
                set(m, n, element);
//              game[m][n] = element;
                snake.addLast(pos);
        }

        // used by public method simulate()
        void move(){
                int object = advance();
                if (object == WALL || object == SELF){
                        isOver = true;
                } else if (object == CHEESE){
                        eatCheese();
                } else if (object==EMPTY) popTail();
        }

        // used by private method move()
        private int advance(){
                int dm = 0, dn = 0;
                if (direction == NORTH){
                        dm = -1;
                        dn = 0;
                } else if (direction == SOUTH){
                        dm = 1;
                        dn = 0;
                } else if (direction == WEST){
                        dm = 0;
                        dn = -1;
                } else if (direction == EAST){
                        dm = 0;
                        dn = 1;
                }
                Position headpos = snake.peekFirst();
                int headM = (headpos.m + game.length + dm) % game.length;
                int headN = (headpos.n + game.length + dn) % game.length;
                int object = game[headM][headN];
                if (object != HEAD || object != WALL){
                        set(headpos.m, headpos.n, SELF);
//                      game[headpos.m][headpos.n] = SELF;
                        Position newpos = new Position(headM, headN);
                        set(newpos.m, newpos.n, HEAD);
//                      game[newpos.m][newpos.n] = HEAD;
                        snake.addFirst(newpos);
                }
                return object;
        }

        // used by move()
        void eatCheese(){
                cheeses--;
        }
        // used by move()
        private void popTail(){
                Position tailpos = snake.pollLast();
//              game[tailpos.m][tailpos.n] = EMPTY;
                set(tailpos.m, tailpos.n, EMPTY);
        }



        // MAIN METHOD

        public static void main(String[] args) {
                Snake sm = new Snake();
                sm.PrintMatrix(sm.game);
        }

}
