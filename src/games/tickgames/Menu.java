package games.tickgames;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Menu extends JFrame {
        Controller controller;
        Model model;
        View view;
        JButton restart;


        Menu(Model spel){
                super(spel.getTitle());
                model = spel;
                view = new View(model);
                model.start();
                add(view);
                setLayout(new FlowLayout());
                controller = new Controller(model, view);
                add(controller);
                this.addKeyListener(controller);


                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setVisible(true);
                pack();
        }

        public static void main(String[] args) {
                new Menu(new AdvancedSnake());
        }


}
