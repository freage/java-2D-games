package games;

import java.util.List;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.lang.reflect.Constructor;

public abstract class BaseMenu<T extends JComponent, MI extends BaseModel, V extends BaseView<T, MI>, C extends BaseController<T,MI,V>> extends JFrame implements ActionListener {
        MI model; // MI is either click.Model or tick.Model, i.e. an abstract class
        protected C ctrl;
        V view;
        List<Class<? extends MI>> games;
        List<JButton> buttons = new ArrayList<JButton>();
        JPanel button_box;
        JPanel view_box;


        void addButtons(){
                button_box = new JPanel();
                button_box.setVisible(true);
                button_box.setLayout(new FlowLayout());
                button_box.setBackground(Color.GRAY);
                add(button_box);

                JButton btn = null;
                for (Class<? extends MI> cls : games) {
                        try {
                                btn = new JButton("Start new "+cls.getSimpleName());
                        } catch (Exception e) {
                                System.out.println("ERROR: could not find title of game.");
                        }
                        buttons.add(btn);
                        btn.setVisible(true);
                        button_box.add(btn);
                        btn.addActionListener(this);
                }
        }

        public BaseMenu(String title, List<Class<? extends MI>> g) {
                super(title);
                games = g;
                setVisible(true);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
                addButtons();

                view_box = new JPanel();
                view_box.setVisible(true);
                view_box.setLayout(new FlowLayout());
                view_box.setBackground(new Color(100, 100, 100));
                add(view_box);

                pack();

        }

        protected void addNewGame(){
                if (view!=null){
                        view_box.remove(view);
                        remove(ctrl);
                }
                view = createView(model);
                model.addObserver(view);
                view_box.add(view);
                view.setVisible(true);
                ctrl = createController(model, view);
                add(ctrl);
                ctrl.setVisible(true);
                pack();
        }

        protected abstract V createView(MI model);
        // return new V(model);

        protected abstract C createController(MI model, V view);
        // return new C(model, view);


        @Override
        public void actionPerformed(ActionEvent arg0) {
                for (int i=0; i<games.size(); i++) {
                        if (arg0.getSource() == buttons.get(i)) {
                                Class<? extends MI> cls = games.get(i);
                                try {
                                        Constructor<? extends MI> c = cls.getConstructor();
                                        model = c.newInstance();
                                } catch (Exception e) {
                                        System.out.println("ERROR: Could not instantiate "+cls.getName()+"; stack trace:");
                                        e.printStackTrace();
                                }
                        }
                }
                addNewGame();

                /* Unless we request Focus after adding a game,
                 * Focus will be on the latest added/used button
                 * and subclasses with KeyListeners will not work. */
                requestFocus();

                ctrl.start(); // start the game
        }

}

