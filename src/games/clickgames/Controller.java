package games.clickgames;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import games.BaseController;


public class Controller extends BaseController<JButton, Model, View> implements MouseListener {
    JButton[][] buttons; // a reference to the buttons from the View
    JLabel instructions; // not used?
    private static final int RIGHT = 3;
    private static final int LEFT = 1;

    public Controller(Model m, View v){
        super(m, v, 200, 100);
        buttons = view.getButtons();
        addListeners();
    }

    private void addListeners(){
        for (int i=0; i<buttons.length; i++){
            for (int j=0; j<buttons[i].length; j++){
                JButton button = buttons[i][j];
                button.addMouseListener(this);
            }
        }
    }


    @Override
    public void mouseClicked(MouseEvent me) {
        if (running) {
            Object source = me.getSource();
            JButton button;
            for (int i=0; i<buttons.length; i++){
                for (int j=0; j<buttons[i].length; j++){
                    button = buttons[i][j];
                    if (source==button && !model.isOver()){
                        int mouseButton = me.getButton();
                        if (mouseButton==LEFT)
                            model.leftClick(i, j);
                        else if (mouseButton==RIGHT)
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
