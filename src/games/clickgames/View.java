package games.clickgames;

// import java.awt.Dimension;
import java.awt.Font;
// import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

import games.BaseView;

public class View extends BaseView<JButton, Model> {
    int width, height;

        public View(Model model){
            super(model);
        //         m = model;
        //         int W = m.getWidth(), H = m.getHeight();
        //         setLayout(new GridLayout(W,H));
            width = model.getWidth() * model.getSquareSize();
            height = model.getHeight() * model.getSquareSize();
        //         setPreferredSize(new Dimension(width, height));
        //         addButtonMatrix();
        //         setVisible(true);
        }

        // TODO: is this even used?
        public JButton[][] getButtons(){
                return this.grid;
        }

        @Override
        protected void initMatrix() {
                grid = new JButton[model.getHeight()][model.getWidth()];
        }


        @Override
        protected void addSquare(int i, int j){
                JButton knapp = new JButton();
                knapp.setMargin(new Insets(2, 4, 2, 4));
//              knapp.setFont(getFont().deriveFont(30).deriveFont(Font.BOLD));
                knapp.setFont(model.getFont());
                knapp.setVisible(true);
                grid[i][j] = knapp;
                updateSquare(i, j, model.getSquare(i,j));
                this.add(knapp);
        }

    ///////////////////////////////////////////////////////////////
    //// Implemented functions from `MatrixObserverInterface`


        @Override
        public void updateSquare(int i, int j, int number){
                JButton knapp = grid[i][j];
                String content = model.translateString(number);
                knapp.setBackground(model.translateBgColor(number));
                knapp.setForeground(model.translateTextColor(number));
                knapp.setText(content);
        }

        // @Override
        // public void updateMatrix(){
        //         int number;
        //         for (int i=0; i<grid.length; i++){
        //                 for (int j=0; j<grid[i].length; j++){
        //                         number = model.getSquare(i,j);
        //                         updateSquare(i, j, number);
        //                 }
        //         }
        // }



}
