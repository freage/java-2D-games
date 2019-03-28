package games;

public interface MatrixObserverInterface {

        /** The BaseModel calls this when updating any entry in its matrix. */
        void updateSquare(int m, int n, int number);

        /** Only called when the Controller starts/restarts the game. */
        void updateMatrix();
}
