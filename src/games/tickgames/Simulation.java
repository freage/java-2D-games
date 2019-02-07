package games.tickgames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

// bättre göra en SnakeSimulation

public class Simulation implements ActionListener {
        private int request;
        private Timer timer;
        private Spel2DInterface model;
        View view;
        Controller controller;
        boolean newrequest;

        public Simulation(Spel2DInterface m, View v){
                model = m;
                view = v;
                request = Spel2DInterface.NORTH;
                timer = new Timer(100, this);
                timer.start();
        }

        // används av Controller - nollställs efter varje actionevent
        public void setRequest(int i){
                request = i;
        }

        public void addController(Controller c){
                controller = c;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
                model.simulate(request);
//              view.paintMatrix();
                request = Spel2DInterface.NONE;
                if (model.isOver())
                        controller.setResult(model.getResult());
        }





}
