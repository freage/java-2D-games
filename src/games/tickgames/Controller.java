package games.tickgames;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;



public class Controller extends JPanel implements KeyListener {
        Simulation simulation;
        private final static int UP = 38;
        private final static int DOWN = 40;
        private final static int LEFT = 37;
        private final static int RIGHT = 39;
        private final static int SPACE = 32;
        int width = 300;
        int height = 100;
        JLabel status;


        Controller(Simulation s){
                simulation = s;
                simulation.view.addKeyListener(this);
                setPreferredSize(new Dimension(width, height));
                addLabels();

        }

        private void addLabels(){
                add(new JLabel("Press <space> to restart game"));
                status = new JLabel();
                add(status);
        }

        public void setResult(String str){
                status.setText(str);
        }


        @Override
        public void keyPressed(KeyEvent arg0) {
                int keynr = arg0.getKeyCode();
//              System.out.println("Controller: Pressed keynr "+keynr);
                if (keynr==UP)
                        simulation.setRequest(Model.NORTH);
                else if (keynr==DOWN)
                        simulation.setRequest(Model.SOUTH);
                else if (keynr==LEFT)
                        simulation.setRequest(Model.WEST);
                else if (keynr==RIGHT)
                        simulation.setRequest(Model.EAST);
                else if (keynr==SPACE)
                        simulation.setRequest(Model.RESTART);
        }

        @Override
        public void keyReleased(KeyEvent arg0) {
                // gör inget

        }

        @Override
        public void keyTyped(KeyEvent arg0) {
                // gör inget

        }



}
