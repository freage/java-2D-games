package games.clickgames;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import games.BaseController;

/* Denna controller uppdaterar endast modellen, inte vyn.
 * Vyn lyssnar istället själv på modellens förändringar.
 * Denna klass är också en JPanel som presenterar status, instruktioner etc.
 */



public class Controller extends BaseController<JButton, Model, View> implements MouseListener {
        JButton[][] knappar; // har en referens till knapparna, som uppdateras
        JLabel instruktioner;
        private static final int RIGHT = 3;
        private static final int LEFT = 1;

        public Controller(Model mo, View vi){
                super(mo, vi, 200, 100);
                knappar = view.getButtons();
                addLabels();
                addListeners();
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
            if (running) {
                Object source = me.getSource();
                JButton knapp;
                for (int i=0; i<knappar.length; i++){
                        for (int j=0; j<knappar[i].length; j++){
                                knapp = knappar[i][j];
                                if (source==knapp && !model.isOver()){
                                        int musknapp = me.getButton();
                                        if (musknapp==LEFT)
                                                model.leftClick(i, j);
                                        else if (musknapp==RIGHT)
                                                model.rightClick(i, j);
                                        if (model.isOver())
                                                status.setText(model.getResult());
                                }
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
