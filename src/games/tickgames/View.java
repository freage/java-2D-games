package games.tickgames;

import java.awt.Dimension;

import javax.swing.JLabel;

import games.MatrixObserverInterface;

public class View extends BaseView<JLabel, Model> {
        Model model;
        JLabel[][] squares;

        View(Model mo){
                model = mo;
                int H = model.getHeight(), W = model.getWidth();
                squares = new JLabel[H][W];
                setLayout(new GridLayout(H, W));
                setPreferredSize(new Dimension(300, 350));
                addMatrix();
                updateMatrix();
                setVisible(true);

        }

    protected void addButton(int i, int j) {
        JLabel square = new JLabel("");
        square.setVisible(true);
        square.setOpaque(true);
        square.setSize(10, 10);
        squares[i][j] = square;
        this.add(square);
    }



        @Override
        public void updateMatrix(){
                int element;
                for (int i=0; i < squares.length; i++){
                        for (int j=0; j < squares[i].length; j++){
                                element = model.getSquare(i,j);
                                updateSquare(i, j, element);
                        }
                }
        }

        @Override
        public void updateSquare(int m, int n, int number) {
            System.out.println("View::updateSquare(int,int,int);");
                JLabel square = squares[m][n];
                square.setBackground(model.translate(number));
                square.repaint();
        }


}
