package games;

import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;


public abstract class BaseView<T extends JComponent, M extends BaseModel> extends JPanel implements MatrixObserverInterface {
    protected M model;
    protected T [][] grid;

    public BaseView(M m) {
        model = m;
        model.addObserver(this);
        int H = model.getHeight(), W = model.getWidth();
        setLayout(new GridLayout(H,W));
        int bs = model.getSquareSize();
        setPreferredSize(new Dimension(W*bs, H*bs)); // Dimension(x, y)
        addMatrix();
        setVisible(true);
    }


    private void addMatrix() {
        initMatrix();
        for (int i=0; i<model.getHeight(); i++){
            for (int j=0; j<model.getWidth(); j++){
                addSquare(i, j);
            }
        }
    }

    protected abstract void initMatrix();
    // Do this; cannot do generic array creation.
    // grid = new T[model.getHeight()][model.getWidth()];


    /** Should *not* call updateSquare();
     * Only create the squares, do not fill them with content from the model; the model is not ready yet.
     */
    protected abstract void addSquare(int i, int j);


    ///////////////////////////////////////////////////////////////
    //// Implemented functions from `MatrixObserverInterface`

    @Override
    public void updateMatrix() {
        int element;
        for (int i=0; i < model.getHeight(); i++){
            for (int j=0; j < model.getWidth(); j++){
                element = model.getSquare(i,j);
                updateSquare(i, j, element);
            }
        }
    }

}
