package games.tickgames;

import java.util.Arrays;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import games.BaseMenu;

public class Menu extends BaseMenu<JLabel, Model, View, Controller> {


        Menu() {
                super("Tick-based games",
                      Arrays.asList(Snake.class, AdvancedSnake.class));
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
