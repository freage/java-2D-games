package games;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComponent;
import java.awt.Dimension;


public abstract class BaseController<T extends JComponent, M extends BaseModel, V extends BaseView<T,M>> extends JPanel {
        int width, height; // used by BaseMenu; TODO getters
        protected M model;
        protected V view;
        protected JLabel status;
        protected boolean running;


        public BaseController(M m, V v, int wi, int he) {
                model = m;
                view = v;
                width = wi;
                height = he;
                setPreferredSize(new Dimension(width, height));
                running = false;
        }

        // TODO: add this call to suitable function
        protected void addLabels() {
                status = new JLabel(model.getInstructions());
                status.setVisible(true);
                add(status);
        }

        /** Start the game, after the model and the view is ready.
         * Override (and call super) if you need.
         */
        protected void run() {
                running = true;
        }

        /** Pause all controllers/events, while the model and the view is reset.
         * Override (and call super) if you need.
         */
        protected void pause() {
                running = false;
        }

        /** Start the game; also used for restarting. */
        public final void start() {
                model.start();
                view.updateMatrix(); // Should be the only time this function is used.
                run();
        }

        /** Pause the controller and restart the game. */
        public final void restart() {
                pause();
                start();
        }


}
