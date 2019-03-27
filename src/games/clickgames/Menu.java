package games.clickgames;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.lang.reflect.Constructor;

public class Menu extends JFrame implements ActionListener {
        Model model;
        Controller ctrl;
        View view;
        List<Class<? extends Model>> games =
                Arrays.asList(FifteenPuzzle.class, TicTacToe.class, MineSweeper.class);
        List<JButton> buttons = new ArrayList<JButton>();


        void addButtons(){
                JButton btn = null;
                for (Class<? extends Model> cls : games) {
                        try {
                                btn = new JButton("Start new "+cls.getSimpleName());
                        } catch (Exception e) {
                                System.out.println("ERROR: could not find title of game.");
                        }
                        buttons.add(btn);
                        btn.setVisible(true);
                        add(btn);
                        btn.addActionListener(this);
                }
        }

        Menu(){
                super("Click-based games");
                setVisible(true);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLayout(new FlowLayout());
                setSize(600, 100); // original windowsize
                addButtons();

//              pack();
        }

        void addNewGame(){
                if (view!=null){
                        view.setVisible(false);
                        ctrl.setVisible(false);
                }
                view = new View(model);
                model.addObserver(view);
                ctrl = new Controller(model, view);
                add(view);
                add(ctrl);
                ctrl.setVisible(true);
                view.setVisible(true);
                setSize(new Dimension(Math.max(view.width+100+ctrl.width, 600),view.height+100+ctrl.height));
        }


        public static void main(String[] args) {
                new Menu();
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
                for (int i=0; i<games.size(); i++) {
                        if (arg0.getSource() == buttons.get(i)) {
                                Class<? extends Model> cls = games.get(i);
                                try {
                                        Constructor<? extends Model> c = cls.getConstructor();
                                        model = c.newInstance();
                                } catch (Exception e) {
                                        System.out.println("ERROR: Could not instantiate "+cls.getName());
                                }
                        }
                }
                addNewGame();
        }

}

