package games.clickgames;


import java.awt.Color;
import java.util.Collections;
import java.util.LinkedList;

import games.SpelModel;


public class FemtonModel extends SpelModel implements ClickGameModelInterface {
        private int[][] end = new int[4][4];
        private int emptysqM; // initiera!
        private int emptysqN; // initiera!!
        private int winner = 1; // finns bara en spelare


        public FemtonModel(){
                constructFinal();
                Start();
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        // methods for controller:

        @Override
        /**
         * flytta rutan [m][n] till tomma rutan
         */
        public void LeftClick(int m, int n) {
                if (isValid(m, n))
                        Execute(m, n);
        }

        private boolean isValid(int m, int n) {
                boolean rowneighbour = Math.abs(m-emptysqM)==1;
                boolean columnneighbour = Math.abs(n-emptysqN)==1;
                boolean samerow = (m==emptysqM);
                boolean samecolumn = (n==emptysqN);
                return ((rowneighbour && samecolumn) || (columnneighbour && samerow));
        }

        @Override
        public void restart(){
                Start();
        }

        public void PrintEnd() {
                for (int i=0; i<end.length; i++){
                        for (int j=0; j<end[i].length; j++){
                                System.out.print(end[i][j]+" ");
                        }
                        System.out.println(); // ny rad
                }
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////
        // getters:


        @Override
        public String translateString(int i) {
                if (i==0){
                        return "";
                } else return ""+i;
        }

        @Override
        public String getTitle() {
                return "Femtonspel";
        }


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // private methods:


        private void Execute(int m, int n) {
                int tal = game[m][n];
                set(m, n, 0);
                set(emptysqM, emptysqN, tal);
//              System.out.println(tal+" flyttas från "+m+n+" till "+emptysqM+emptysqN);
                emptysqM = m;
                emptysqN = n;
                isOver = (game==end);
                if (isOver){
                        winner=1;
                        result="You did it!";
                }
        }

        /**
         * ladda matrisen med tal 0-15 i slumpade positioner
         */
        private void Start(){
                initialise(4,4);
                int[] numbers = loadArray();
//              numbers = shuffle(numbers); // första blandaren gick ut på att blanda arrayen innan matrisen laddades
                loadMatrix(numbers, game);
                // tomma rutan är till en början i nedre högra hörnet
                emptysqM = game.length-1;
                emptysqN = game.length-1;
                // blanda nu om (gör ett antal slumpade drag)
                newShuffle();
        }

//      private int[] getEmpty(){
//              for (int i=0; i<game.length; i++){
//                      for (int j=0; j<game[i].length; j++){
//                              int tal = game[i][j];
//                              if (tal==0){
//                                      int[] indices = new int[2];
//                                      indices[0] = i;
//                                      indices[1] = j;
//                                      return indices;
//                              }
//                      }
//              }
//              throw new IllegalArgumentException("Kunde inte hitta tomma rutan.");
//      }


        /////////////////////////////////////////////////////////////////////////////////
        // MAIN-method

        /**
         * @param args
         */
        public static void main(String[] args) {
                FemtonModel spel = new FemtonModel();
                spel.PrintMatrix(spel.game);
                spel.PrintEnd();
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////
        // hjälpfunktioner

        /**
         * ladda end-matrisen med tal 1-15,0 i ordning
         */
        private void constructFinal(){
                // laddar slutplanen
                int[] tal = loadArray();
                loadMatrix(tal, end);
        }

        /** returnera en array med tal 1-15,0 i ordning*/
        private int[] loadArray(){
                int[] numbers = new int[16];
                for (int i=0; i<numbers.length; i++){
                        numbers[i] = (i + 1) % numbers.length;
                }
                return numbers;
        }



//      /**
//       * @param array - en array
//       * @return arrayen med talen i omkastad (slumpad) ordning (transponerad)
//       */
//      private int[] Shuffle(int[] array){
//              LinkedList<Integer> list = new LinkedList<Integer>();
//              for (int i=0; i<array.length; i++){
//                      list.add(array[i]);
//              }
//              Collections.shuffle(list);
//              for (int i=0; i<array.length; i++){
//                      array[i] = list.pollFirst();
//              }
//              return array;
//      }


        private boolean inrange(int n){
                return (n >= 0 && n < game.length);
        }

        private void newShuffle(){
                for (int i=0; i < 200; i++){
                        aShuffle();
                }
        }
        private void aShuffle(){
                boolean vertical = rgen.nextBoolean();
                boolean decrease = rgen.nextBoolean();
                int delta = decrease?-1:1;
                int m = emptysqM;
                int n = emptysqN;
                if (vertical){
                        if (inrange(emptysqM+delta)) m += delta;
                } else {
                        if (inrange(emptysqN+delta)) n += delta;
                }
                LeftClick(m, n);
        }

        /**
         *
         * @param tal - ladda talen i denna array i matris
         * @param mtrx - ladda talen i denna matris
         */
        private void loadMatrix(int[] tal, int[][] mtrx){
                assert(tal.length == Math.pow(mtrx.length, 2));
                for (int i=0; i<tal.length; i++){
                        int b = mtrx.length;
                        int m = (int) (i / b); // kvot
                        int n = i % mtrx.length; // rest
                        int number = tal[i];
                        mtrx[m][n] = number;
                }
        }

        @Override
        public void RightClick(int m, int n) {
                // gör ingenting
        }

        @Override
        public Color translateTextColor(int i) {
                return Color.BLACK;
        }

        @Override
        public Color translateBgColor(int i) {
                if (i==0)
                        return Color.GRAY;
                else return Color.CYAN;
        }

        @Override
        public int getButtonSize() {
                // TODO Auto-generated method stub
                return 50;
        }


}
