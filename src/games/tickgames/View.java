package games.tickgames;

import java.awt.Dimension;

import javax.swing.JLabel;

import games.BaseView;

public class View extends BaseView<JLabel, Model> {
        JLabel[][] grid;

        View(Model mo){
            super(mo);
                // int H = model.getHeight(), W = model.getWidth();
                // grid = new JLabel[H][W];
                // setLayout(new GridLayout(H, W));
                setPreferredSize(new Dimension(300, 350)); // <-- !!!
                // addMatrix();
                // updateMatrix();
                // setVisible(true);

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
            System.out.println("View::updateSquare(int,int,int);");
                JLabel square = grid[m][n];
                square.setBackground(model.translate(number));
                square.repaint();
        }


}
