package games.tickgames;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Spel2D extends JFrame {
        Controller controller;
        Simulation simulation;
        Spel2DInterface model;
        View view;
        JButton restart;


        Spel2D(Spel2DInterface spel){
                super(spel.getTitle());
                model = spel;
                view = new View(model);
                add(view);
                setLayout(new FlowLayout());
                simulation = new Simulation(model, view);
                controller = new Controller(simulation);
                simulation.addController(controller);
                add(controller);
                this.addKeyListener(controller);


                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setVisible(true);
                pack();
        }

        public static void main(String[] args) {
                new Spel2D(new AdvancedSnakeModel());
        }


}
