package games.tickgames;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class Controller extends JPanel implements KeyListener, ActionListener {
        private final static int UP = 38;
        private final static int DOWN = 40;
        private final static int LEFT = 37;
        private final static int RIGHT = 39;
        private final static int SPACE = 32;
        int width = 300;
        int height = 100;
        JLabel status;

        private int request;
        private Timer timer;
        private Model model;
        View view;
//      boolean newrequest;


        Controller(Model m, View v){
                model = m;
                view = v;
                view.addKeyListener(this);
                setPreferredSize(new Dimension(width, height));
                addLabels();

                request = Model.NORTH;
                timer = new Timer(100, this);
                timer.start();


        }

        private void addLabels(){
                add(new JLabel("Press <space> to restart game"));
                status = new JLabel();
                add(status);
        }

        private void setResult(String str){
                status.setText(str);
        }

        // reset after each actionevent
        private void setRequest(int i){
                request = i;
        }

        @Override
        public void keyPressed(KeyEvent arg0) {
                int keynr = arg0.getKeyCode();
             System.out.println("Controller: Pressed keynr "+keynr);
                if (keynr==UP)
                        setRequest(Model.NORTH);
                else if (keynr==DOWN)
                        setRequest(Model.SOUTH);
                else if (keynr==LEFT)
                        setRequest(Model.WEST);
                else if (keynr==RIGHT)
                        setRequest(Model.EAST);
                else if (keynr==SPACE) {
                        setRequest(Model.RESTART);
                        System.out.println("Restart requested");
                }
        }

        @Override
        public void keyReleased(KeyEvent arg0) {
                // do nothing

        }

        @Override
        public void keyTyped(KeyEvent arg0) {
                // do nothing

        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            System.out.println("Controller::actionPerformed();");
            if (request == Model.RESTART) {
                System.out.println("Restart requested but nothing happens?");
            }
                model.simulate(request);
//              view.paintMatrix();
                request = Model.NONE;
                if (model.isOver()) {
                        setResult(model.getResult());
                        System.out.println("Model::isOver()");
                }
        }


}
