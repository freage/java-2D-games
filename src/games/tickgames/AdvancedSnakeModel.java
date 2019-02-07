package games.tickgames;

/* Som snake-model, med tillägg:
 * - lägga ost på planen
 * - poängräkning
 * - kommande: öka level
 * - kommande: lägg till väggar
 *
 */

public class AdvancedSnakeModel extends SnakeModel {

        public AdvancedSnakeModel() {
                super();
        }

        @Override
        public void simulate(int request){ // anropas av simulation varje 0.1 sek
                // en request i snake måste gå via controller, så att inte ormen gör flera drag/tidsenhet
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
//                              game[m][n] = CHEESE;
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
