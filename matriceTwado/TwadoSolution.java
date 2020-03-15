/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matriceTwado;

import general.General;
import java.math.BigInteger;
import java.util.Locale;

/**
 *
 * @author desharnc27
 * 
 * Solves all the csg twadocicious matrices track 
 * See the .tex file for understanding of the recursive formula calculations
 * 
 * Used .java files are: TwadoSolution, TwadoMorphology, TwadoReadChallenge , TwadoMatrix and those in general Package
 * Other files (Useless for competitors):
 * Garbage is garbage, and the rest of the .java files are only for validations/debugging.
 * 
 */
public class TwadoSolution {
    static final boolean debugPrint=false;
    public static void main(String[] args) {
        //Print answer of the first question
        int val= 25;
        BigInteger[] arr =getTotalCountsBI(val+1);
        System.out.println("Answer to the first question: "+ arr[val]);
        
        //Print answer of the second question        
        val=666;
        arr=getMorphCountsBI(val+1);
        System.out.println("Answer to the second question: "+ arr[val]);
        
        //Print answer of the third question
        int modu=999999937;
        System.out.println("Answer to the third question: "+ TwadoReadChallenge.proceed(modu));
        
    }
    //Returns tot, an array of BigInteger  such that tot[i]
    //is the total number of i-twadolicious matrices.
    public static BigInteger[] getTotalCountsBI(int n) {
        
        //Building a Pascal Triangle
        //comb[i][j] will be assigned with C(i,j)
        BigInteger comb[][]=new BigInteger [n][n];
        comb[0][0]=new BigInteger("1");
        for (int i=1;i<n;i++)
            comb[i][0]=(comb[i][i]=new BigInteger("1"));
        for (int i=0;i<n;i++)
            for (int j=1;j<i;j++)
                comb[i][j]=comb[i-1][j].add(comb[i-1][j-1]);
        
        //tot[i] will be the number of n-twadolicious matrices
        //insep[i] will be the number of n-twadolicious unitrack matrices
        BigInteger[] insep = new BigInteger[n];
        BigInteger tot[] = new BigInteger[n];
        
        //Initializing
        for (int i=0;i<n;i++){
            tot[i]=new BigInteger("0");
        }
        insep[2]=new BigInteger("1");
        tot[2]=new BigInteger("1");
        
        //Recursive calculations
        for (int i = 3; i < n; i++) {
            BigInteger temp=new BigInteger(Integer.toString(i*i-i));
            insep[i]=insep[i-1].multiply(temp);
            for (int j=2;j<i-1;j++){
                temp=new BigInteger("1");
                temp=temp.multiply(tot[i-j]);
                temp=temp.multiply(insep[j]);
                temp=temp.multiply(comb[i-1][j-1]);
                temp=temp.multiply(comb[i][j]);
                tot[i]=tot[i].add(temp);
                //tot[i]=insep[j]*tot[i-j]*comb[i-1][j-1]*comb[i][j];
            }
            tot[i]=tot[i].add(insep[i]);
        }
        if (debugPrint){
            General.printVert(tot);
        }
        return tot;
    }
    //Returns ceq, an array of long  such that ceq[i]
    //is the total number morphologies for i-twadolicious matrices
    public static BigInteger[] getMorphCountsBI(int n){
               
        BigInteger [][]bank =new BigInteger[n][n];
        BigInteger [] ceq  =new BigInteger [n];
        
        //ceq[i] is the number of way to choose a multisets of integers of {2,3...} such that
        //the ceq of its elements is i
        //bank[i][j] the number of way to choose a multisets of integers of {2,3...} such that
        //the ceq of its elements is i and such that it's greatest element is j
        
        for (int i=0;i<n;i++){
            ceq[i]=new BigInteger("0");
            for (int j=0;j<n;j++){
                bank[i][j]=new BigInteger("0");
            }
        }
        bank[0][0]=new BigInteger("1");
        ceq[0]=new BigInteger("1");

        for (int k=2;k<n;k++)
            for (int m=2;m<=k;m++){
                for (int i=0;i<=m;i++){
                    bank[k][m]=bank[k][m].add(bank[k-m][i]);
                }
                ceq[k]=ceq[k].add(bank[k][m]);    
                
            }
        if (debugPrint){
            General.printVert(ceq);
            
        }
        //for (int i=0;i<ceq.length;i++)
          //  System.out.println("ceq["+i+"]: "+ceq[i]);
        return ceq;
    }
}
