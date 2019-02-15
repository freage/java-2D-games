package games.tickgames;

/* Extensions to SnakeModel
 * - adding cheese on the board
 * - counting points
 * - planned: increase level
 * - planned: add optional walls
 *
 */

public class AdvancedSnake extends Snake {

        public AdvancedSnake() {
                super();
        }

        @Override
        public void simulate(int request){ // called by Simulation each tick
                // A request in Snake must pass through Controller, to prevent several moves/tick
                super.simulate(request);
                calls++;
                if (calls%10==0 && cheeses < 3){
                        addCheese();
                }

        }


        // used by move() alt. used by Simulation class
        void addCheese(){
                boolean ok = false;
                while (!ok){
                        int m = rgen.nextInt(game.length);
                        int n = rgen.nextInt(game.length);
                        if (game[m][n] == EMPTY){
                                set(m, n, CHEESE);
                                ok = true;
                        }
                }
                cheeses++;
        }

        @Override
        void eatCheese(){
                super.eatCheese();
                points++;
        }

        void levelUp(){
                // TODO
        }

        void move(){
                super.move();
                if (isOver){
                        result = "You got "+points+" points.";
                }
        }



}
