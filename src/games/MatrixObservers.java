package games;

import java.util.LinkedList;
import java.util.ListIterator;

public class MatrixObservers {
        LinkedList<MatrixObserverInterface> observers;

        public MatrixObservers(){
                observers = new LinkedList<MatrixObserverInterface>();
        }

        public void addObserver(MatrixObserverInterface vy){
                observers.add(vy);
        }

        public void notifyObservers(int i, int j, int tal){
                ListIterator<MatrixObserverInterface> listiterator = observers.listIterator();
                while (listiterator.hasNext()){
                        listiterator.next().updateSquare(i, j, tal);
                }
        }

        public void notifyObservers(){
                ListIterator<MatrixObserverInterface> listiterator = observers.listIterator();
                while (listiterator.hasNext()){
                        listiterator.next().updateMatrix();
                }
        }
}
