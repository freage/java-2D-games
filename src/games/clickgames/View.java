package games.clickgames;

import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;

import games.BaseView;

public class View extends BaseView<JButton, Model> {
        // TODO: Apparently these are used by the Menu. The other View does not have these.
        int width, height;

        public View(Model model){
                super(model);
                width = model.getWidth() * model.getSquareSize();
                height = model.getHeight() * model.getSquareSize();
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


}
