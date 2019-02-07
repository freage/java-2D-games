package games.clickgames;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

import games.MatrixObserverInterface;


public class ClickGameView extends JPanel implements MatrixObserverInterface {
        private ClickGameModelInterface m;
        private JButton[][] buttons;
        public int width;
        public int height;

        public ClickGameView(ClickGameModelInterface model){
                m = model;
                int size = m.getGame().length;
                setLayout(new GridLayout(size, size));
                width = size*m.getButtonSize();
                height = size*m.getButtonSize();
                setPreferredSize(new Dimension(width, height));
                addButtonMatrix(); // lägger till knappar med rätt innehåll
                setVisible(true);
        }

        public JButton[][] getButtons(){
                return this.buttons;
        }


//      void update(){
////            System.out.println("Uppdaterar vyn...");
//              updateButtonMatrix();
//      }

        private void addButtonMatrix(){
                buttons = new JButton[m.getGame().length][m.getGame().length];
                for (int i=0; i<m.getGame().length; i++){
                        for (int j=0; j<m.getGame()[i].length; j++){
                                addButton(i, j);
                        }
                }
        }

        private void addButton(int i, int j){
                JButton knapp = new JButton();
                knapp.setMargin(new Insets(2, 4, 2, 4));
//              knapp.setFont(getFont().deriveFont(30).deriveFont(Font.BOLD));
                knapp.setFont(m.getFont());
                knapp.setVisible(true);
                buttons[i][j] = knapp;
                updateSquare(i, j, m.getGame()[i][j]);
                this.add(knapp);
        }

        public void updateSquare(int i, int j, int tal){
                JButton knapp = buttons[i][j];
                String content = m.translateString(tal);
                knapp.setBackground(m.translateBgColor(tal));
                knapp.setForeground(m.translateTextColor(tal));
                knapp.setText(content);
        }

        @Override
        public void updateMatrix(){
                int tal;
                for (int i=0; i<buttons.length; i++){
                        for (int j=0; j<buttons[i].length; j++){
                                tal = m.getGame()[i][j];
                                updateSquare(i, j, tal);
                        }
                }
        }



}
