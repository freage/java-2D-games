package games.tickgames;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import games.MatrixObserverInterface;

public class View extends JPanel implements MatrixObserverInterface {
        Model model;
        JLabel[][] rutor;

        View(Model spel){
                model = spel;
                model.getObservers().addObserver(this);
                int size = model.getGame().length;
                rutor = new JLabel[size][size];
                setLayout(new GridLayout(size, size));
                setPreferredSize(new Dimension(300, 350));
                addMatrix();
                updateMatrix();
                setVisible(true);

        }

        void addMatrix(){
                for (int i=0; i < model.getGame().length; i++){
                        for (int j=0; j < model.getGame()[i].length; j++){
                                JLabel ruta = new JLabel("");
                                ruta.setVisible(true);
                                ruta.setOpaque(true);
                                ruta.setSize(10, 10);
                                rutor[i][j] = ruta;
                                this.add(ruta);
                        }
                }
        }

        @Override
        public void updateMatrix(){
                int element;
                for (int i=0; i < rutor.length; i++){
                        for (int j=0; j < rutor[i].length; j++){
                                element = model.getGame()[i][j];
                                updateSquare(i, j, element);
                        }
                }
        }

        @Override
        public void updateSquare(int m, int n, int number) {
                JLabel ruta = rutor[m][n];
                ruta.setBackground(model.translate(number));
                ruta.repaint();
        }


}
