package games.tickgames;

import java.util.Arrays;
import javax.swing.JLabel;
import games.BaseMenu;

public class Menu extends BaseMenu<JLabel, Model, View, Controller> {

        Menu() {
                super("Tick-based games",
                      Arrays.asList(Snake.class, AdvancedSnake.class, Tetris.class));
        }

        @Override
        protected void addNewGame() {
                super.addNewGame();
                this.addKeyListener(ctrl);
        }

        @Override
        protected View createView(Model model) {
                return new View(model);
        }

        @Override
        protected Controller createController(Model model, View view) {
                return new Controller(model, view);
        }

        public static void main(String[] args) {
                new Menu();
        }


}
