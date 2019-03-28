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
        // Controller controller;
        // Model model;
        // View view;
        JButton restart;


        Menu(){
            super("Tick-based games",
                  Arrays.asList(Snake.class, AdvancedSnake.class));
                // model = spel;
                // view = new View(model);
            // model.start(); // TODO: move to base so that the Model/Controller can be private
                // add(view);
                // setLayout(new FlowLayout());
                // ctrl = new Controller(model, view);
                // add(ctrl);
                // this.addKeyListener(ctrl);


                // setDefaultCloseOperation(EXIT_ON_CLOSE);
                // setVisible(true);
                // pack();
            // this.addKeyListener(listener);
        }

    @Override
    protected void addNewGame(){
        super.addNewGame();
        this.addKeyListener(ctrl);
        ctrl.start();
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
