package games;

import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;


public abstract class BaseView<T extends JComponent, M extends BaseModel> extends JPanel implements MatrixObserverInterface {
        protected M m;
        protected T [][] grid;

        public BaseView(M model){
                m = model;
                m.addObserver(this);
                int W = m.getWidth(), H = m.getHeight();
                setLayout(new GridLayout(W,H));
                int bs = m.getSquareSize();
                setPreferredSize(new Dimension(W*bs, H*bs));
                addMatrix();
                setVisible(true);
        }


        private void addMatrix(){
            initMatrix();
                for (int i=0; i<m.getHeight(); i++){
                    for (int j=0; j<m.getWidth(); j++){
                                addButton(i, j);
                        }
                }
        }

        protected abstract void initMatrix();
        // Do this; cannot do generic array creation.
        // grid = new T[m.getHeight()][m.getWidth()];


        protected abstract void addButton(int i, int j);

        // @Override
        // void updateSquare(int m, int n, int number);

        // @Override
        // void updateMatrix();


}
