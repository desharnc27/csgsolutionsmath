/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matriceTwado;

/**
 *
 * @author desharnc27
 * 
 * This class is used to encode a twadolicious matrix
 * 
 * The code is shitty, unsafe and only usefull for our specifics problems.
 */
public class TwadoMatrix {

    IntPair col[];
    IntPair ran[];
    int n;

    public static class IntPair {

        int v0;
        int v1;

        public void insert(int a) {
            if (a < v0) {
                v1 = v0;
                v0 = a;
            } else {
                v1 = a;
            }
        }

        public void replace(int a, int b) {
            if (v0 == a) {
                v0 = b;
            } else {
                v1 = b;
            }
            sort();
        }

        public void sort() {
            if (v1 < v0) {
                int temp = v1;
                v1 = v0;
                v0 = temp;
            }
        }

        @Override
        public String toString() {
            return v0 + "," + v1;
        }
    }

    public TwadoMatrix(int n, String s) {
        ran = new IntPair[n];
        col = new IntPair[n];
        this.n = n;
        char[] c = s.toCharArray();
        int idx = 0;
        int exVal = 0;
        for (int i = 0; i < n; i++) {
            col[i] = new IntPair();
            ran[i] = new IntPair();
        }
        for (int i = 0; i < n; i++) {

            col[i].v0 = i;
            ran[i].v0 = i;
            if ((i == n - 1) || (idx < c.length && c[idx] - '0' == i)) {
                ran[i].v1 = exVal;
                col[exVal].v1 = i;
                exVal = i + 1;
                idx++;
            } else {
                ran[i].v1 = (i + 1) % n;
                col[(i + 1) % n].v1 = i;

            }

        }
        for (int i = 0; i < n; i++) {
            col[i].sort();
            ran[i].sort();
        }
        //TODO:optimize;
    }

    public TwadoMatrix(int n, boolean[] brk) {
        ran = new IntPair[n];
        col = new IntPair[n];
        this.n = n;
        //int idx=0;
        int exVal = 0;
        for (int i = 0; i < n; i++) {
            col[i] = new IntPair();
            ran[i] = new IntPair();
        }
        for (int i = 0; i < n; i++) {

            col[i].v0 = i;
            ran[i].v0 = i;
            if ((i == n - 1) || brk[i]) {
                ran[i].v1 = exVal;
                col[exVal].v1 = i;
                exVal = i + 1;
            } else {
                ran[i].v1 = (i + 1) % n;
                col[(i + 1) % n].v1 = i;

            }

        }
        for (int i = 0; i < n; i++) {
            col[i].sort();
            ran[i].sort();
        }
        //TODO:optimize;
    }

    public TwadoMatrix(int[][] tab) {
        n = tab.length;
        ran = new IntPair[n];
        col = new IntPair[n];
        //int idx=0;
        int exVal = 0;
        for (int i = 0; i < n; i++) {
            col[i] = new IntPair();
            ran[i] = new IntPair();
            ran[i].v0 = tab[i][0];
            ran[i].v1 = tab[i][1];
            col[i].v0 = -1;
            col[i].v1 = -1;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                if (col[tab[i][j]].v0 == -1) {
                    col[tab[i][j]].v0 = i;
                } else {
                    col[tab[i][j]].v1 = i;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            col[i].sort();
            ran[i].sort();
        }

        //TODO:optimize;
    }

    public void ranPer(int a, int b) {
        IntPair temp;
        temp = ran[a];
        ran[a] = ran[b];
        ran[b] = temp;

        col[ran[a].v0].replace(b, a);
        col[ran[a].v1].replace(b, a);
        col[ran[b].v0].replace(a, b);
        col[ran[b].v1].replace(a, b);

    }

    public void colPer(int a, int b) {
        IntPair temp;
        temp = col[a];
        col[a] = col[b];
        col[b] = temp;

        ran[col[a].v0].replace(b, a);
        ran[col[a].v1].replace(b, a);
        ran[col[b].v0].replace(a, b);
        ran[col[b].v1].replace(a, b);

    }
    int otherRan(int iRan, int iCol) {
        if (col[iCol].v0==iRan){
            return col[iCol].v1;
            
        }else{
            return col[iCol].v0;
        }       
    }
    int otherCol(int iRan, int iCol) {
        if (ran[iRan].v0==iCol){
            return ran[iRan].v1;
            
        }else{
            return ran[iRan].v0;
        }       
    }

    public static void countEquivs() {
           //boolean isPrime=;
    }

    public void printWide() {
        for (int i = 0; i < n; i++) {
            int val = ran[i].v0;
            int idx = 0;
            for (int j = 0; j < n; j++) {
                if (val == j) {
                    idx++;
                    val = ran[i].v1;
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }

    }

    public void printTiff() {
        for (int i = 0; i < n; i++) {
            System.out.print(i + ": " + ran[i].v0 + " " + ran[i].v1);
            System.out.println("..." + col[i].v0 + " " + col[i].v1);
        }

    }

}
