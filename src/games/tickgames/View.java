package games.tickgames;

import java.awt.Dimension;
import javax.swing.JLabel;

import games.BaseView;

public class View extends BaseView<JLabel, Model> {
        JLabel[][] grid;

        View(Model mo){
                super(mo);
                setPreferredSize(new Dimension(300, 350)); // TODO: avoid hardcoding
        }

        ///////////////////////////////////////////////////////////////
        //// Implemented functions from `BaseView`

        @Override
        protected void initMatrix() {
                grid = new JLabel[model.getHeight()][model.getWidth()];
        }

        @Override
        protected void addSquare(int i, int j) {
            JLabel square = new JLabel("");
            square.setVisible(true);
            square.setOpaque(true);
            square.setSize(10, 10);
            grid[i][j] = square;
            this.add(square);
        }


        ///////////////////////////////////////////////////////////////
        //// Implemented functions from `MatrixObserverInterface`

        @Override
        public void updateSquare(int m, int n, int number) {
                JLabel square = grid[m][n];
                square.setBackground(model.translate(number));
                square.repaint();
        }


}
