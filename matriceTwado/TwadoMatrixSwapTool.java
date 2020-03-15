/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matriceTwado;

import java.util.Scanner;

/**
 *
 * @author desharnc27
 * 
 * This class was only used for debugging (unnecessary for competitors). 
 * It starts with a n-twadolicious matrix,
 * Then the user can enter in the console a command to swap lines or permutations:
 * 
 * i,j,k 
 * 
 * where k is 'c' or 'r', depending on if the user wants to swap two rows or two columns
 * i and j are the rows (or columns) to swap
 * 
 * The user can also type "chmod" to change the display of the matrix
 * 
 * The two modes are:
 * 
 * normal: prints the whole matrix
 * 
 * consise: for every i in interval [0,n[, it prints:
 * - the columns where the ones of rank i are 
 * - the ranks where the ones of column i are 
 * 
 */
public class TwadoMatrixSwapTool {

    public static void main(String[] args) {
        int n=5;
        boolean[] brk = new boolean[n];
        for (int i = 0; i < brk.length-2; i++) {

            if (i>Math.random()*n){
                brk[i]=true;
                i+=2;
            }

        }
        brk[n-1]=true;
        TwadoMatrix matri = new TwadoMatrix(n, brk);
        matri.printTiff();
        Scanner scan = new Scanner(System.in);
        boolean mode=true;
        while (true) {
            String[] s = scan.next().split(",");
            if (s[0].equals("chmod")){
                mode=!mode;
                
                if (mode)
                    matri.printTiff();
                else
                    matri.printWide();
                continue;
            } 
            int a;
            int b;
            char c;
            
            try {
                if (s[0].equals("exit")){
                    System.out.println("Will Exit");
                    break;
                }
                a = Integer.parseInt(s[0]);
                b = Integer.parseInt(s[1]);
                c = s[2].charAt(0);
                if (!(c=='r'||c=='c')||s.length!=3 ||s[2].length()!=1||a < 0 || a >= n || b < 0 || b >= n) {
                    throw new Exception();
                }
                
            } catch (Exception e) {
                System.out.println("Wrong format, denied");
                continue;
            }
            boolean ranPer = (c=='r');
            if (ranPer) {
                matri.ranPer(a, b);
            } else {
                matri.colPer(a, b);
            }
            if (mode)
                matri.printTiff();
            else
                matri.printWide();

        }
    }
}
