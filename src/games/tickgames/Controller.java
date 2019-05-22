package games.tickgames;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import games.BaseController;

public class Controller extends BaseController<JLabel, Model, View> implements KeyListener, ActionListener {
    final static int UP = 38;
    final static int DOWN = 40;
    final static int LEFT = 37;
    final static int RIGHT = 39;
    final static int SPACE = 32;
    final static int ENTER = 10;
    final static int DD = 68;

    private Timer timer;


    Controller(Model m, View v) {
        super(m, v, 300, 100);
        view.addKeyListener(this);
        timer = new Timer(m.getTick(), this);
    }

    @Override
    protected void run() {
        super.run();
        timer.start();
    }

    @Override
    protected void pause() {
        super.pause();
        timer.stop();
    }

    private void setResult(String str) {
        status.setText(str);
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (running) {
            int keynr = arg0.getKeyCode();
            if (keynr==SPACE) {
                // TODO: This function probably should be called by the Menu instead?
                restart();
            } else model.request(keynr);
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

    /** Called each time the timer fires an event, i.e. once each "tick". */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (running) {
            model.simulate();
            if (model.isOver()) {
                setResult(model.getResult());
            }
        }
    }


}
