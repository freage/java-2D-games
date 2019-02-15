package games.clickgames;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* Denna controller uppdaterar endast modellen, inte vyn.
 * Vyn lyssnar istället själv på modellens förändringar.
 * Denna klass är också en JPanel som presenterar status, instruktioner etc.
 */



public class Controller extends JPanel implements MouseListener {
        protected Model modell;
        View vy;
        JButton[][] knappar; // har en referens till knapparna, som uppdateras
        JLabel instruktioner;
        JLabel status;
        public int width = 200;
        public int height = 100;
        private static final int RIGHT = 3;
        private static final int LEFT = 1;

        public Controller(Model mo, View vi){
                modell = mo;
                vy = vi;
                knappar = vy.getButtons();
                setPreferredSize(new Dimension(width, height));
                addLabels();
                addListeners();
        }

        private void addLabels(){
//              instruktioner = new JLabel(modell.getInstructions());
                status = new JLabel();
                status.setVisible(true);
                add(status);
        }

        private void addListeners(){
                for (int i=0; i<knappar.length; i++){
                        for (int j=0; j<knappar[i].length; j++){
                                JButton knapp = knappar[i][j];
                                knapp.addMouseListener(this);
                        }
                }
        }


        @Override
        public void mouseClicked(MouseEvent me) {
                Object source = me.getSource();
                JButton knapp;
                for (int i=0; i<knappar.length; i++){
                        for (int j=0; j<knappar[i].length; j++){
                                knapp = knappar[i][j];
                                if (source==knapp && !modell.isOver()){
                                        int musknapp = me.getButton();
                                        if (musknapp==LEFT)
                                                modell.leftClick(i, j);
                                        else if (musknapp==RIGHT)
                                                modell.rightClick(i, j);
                                        if (modell.isOver())
                                                status.setText(modell.getResult());
                                }
                        }
                }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

        }

        @Override
        public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

        }

}
