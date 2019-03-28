package games.clickgames;

import java.util.Arrays;
import javax.swing.JButton;
import games.BaseMenu;

public class Menu extends BaseMenu<JButton, Model, View, Controller> {

        Menu() {
                super("Click-based games",
                      Arrays.asList(FifteenPuzzle.class, TicTacToe.class, MineSweeper.class));
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

