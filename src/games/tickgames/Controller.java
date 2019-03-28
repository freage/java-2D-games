package games.tickgames;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import games.BaseController;

public class Controller extends BaseController<JLabel, Model, View> implements KeyListener, ActionListener {
        private final static int UP = 38;
        private final static int DOWN = 40;
        private final static int LEFT = 37;
        private final static int RIGHT = 39;
        private final static int SPACE = 32;

        private int request;
        private Timer timer;


        Controller(Model m, View v){
                super(m, v, 300, 100);
                view.addKeyListener(this);
                addLabels();

                request = Model.NORTH;
                timer = new Timer(100, this);
        }

        void start() {
                model.start();
                timer.start();
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
                model.simulate(request);
                request = Model.NONE;
                if (model.isOver()) {
                        setResult(model.getResult());
                }
        }


}
