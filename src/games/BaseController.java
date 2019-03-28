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


        public BaseController(M m, V v, int wi, int he) {
                model = m;
                view = v;
                width = wi;
                height = he;
                setPreferredSize(new Dimension(width, height));
        }

        protected void addLabels() {
                status = new JLabel(model.getInstructions());
                status.setVisible(true);
                add(status);
        }




}
