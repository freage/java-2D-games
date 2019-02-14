package games.clickgames;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class Menu extends JFrame implements ActionListener {
        Model model;
        Controller ctrl;
        View view;
        JButton femtonknapp = new JButton("Start new 15-puzzle");
        JButton luffarknapp = new JButton("Start new Tic-tac-toe");
        JButton minrojknapp = new JButton("Start new Minesweeper");


        void addButton(JButton knapp){
                knapp.setVisible(true);
                add(knapp);
                knapp.addActionListener(this);
        }

        Menu(){
                super("Click-based games");
                setVisible(true);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLayout(new FlowLayout());
                setSize(600, 100); // ursprunglig fönsterstorlek

                addButton(femtonknapp);
                addButton(luffarknapp);
                addButton(minrojknapp);
//              pack();
        }

        void addNewGame(){
                if (view!=null){
                        view.setVisible(false);
                        ctrl.setVisible(false);
                }
                view = new View(model);
                model.getObservers().addObserver(view);
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
                if (arg0.getSource()==femtonknapp)
                        model = new FifteenPuzzle();
                else if (arg0.getSource()==luffarknapp)
                        model = new TicTacToe();
                else if (arg0.getSource()==minrojknapp)
                        model = new MineSweeper();
                addNewGame();
        }

}

