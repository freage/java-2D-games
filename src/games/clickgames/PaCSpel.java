package games.clickgames;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class PaCSpel extends JFrame implements ActionListener {
        ClickGameModelInterface model;
        PaCController ctrl;
        ClickGameView view;
        JButton femtonknapp = new JButton("Starta nytt femtonspel");
        JButton luffarknapp = new JButton("Starta nytt luffarschack");
        JButton minrojknapp = new JButton("Starta nytt minröj");


        void addButton(JButton knapp){
                knapp.setVisible(true);
                add(knapp);
                knapp.addActionListener(this);
        }

        PaCSpel(){
                super("Rutspel");
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
                view = new ClickGameView(model);
                model.getObservers().addObserver(view);
                ctrl = new PaCController(model, view);
                add(view);
                add(ctrl);
                ctrl.setVisible(true);
                view.setVisible(true);
                setSize(new Dimension(Math.max(view.width+100+ctrl.width, 600),view.height+100+ctrl.height));
        }


        public static void main(String[] args) {
                new PaCSpel();
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
                if (arg0.getSource()==femtonknapp)
                        model = new FemtonModel();
                else if (arg0.getSource()==luffarknapp)
                        model = new LuffarModel();
                else if (arg0.getSource()==minrojknapp)
                        model = new MineSweeperModel();
                addNewGame();
        }

}

