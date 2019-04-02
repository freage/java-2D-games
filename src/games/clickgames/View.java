package games.clickgames;

import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;

import games.BaseView;

public class View extends BaseView<JButton, Model> {

        public View(Model model) {
                super(model);
        }

        // Used by the Controller
        public JButton[][] getButtons() {
                return this.grid;
        }

        @Override
        protected void initMatrix() {
                grid = new JButton[model.getHeight()][model.getWidth()];
        }


        @Override
        protected void addSquare(int i, int j) {
                JButton square = new JButton();
                square.setMargin(new Insets(2, 4, 2, 4));
                square.setFont(model.getFont());
                square.setVisible(true);
                grid[i][j] = square;
                this.add(square);
        }

        ///////////////////////////////////////////////////////////////
        //// Implemented functions from `MatrixObserverInterface`


        @Override
        public void updateSquare(int i, int j, int number) {
                JButton square = grid[i][j];
                String content = model.translateString(number);
                square.setBackground(model.translateBgColor(number));
                square.setForeground(model.translateTextColor(number));
                square.setText(content);
        }


}
