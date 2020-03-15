/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matriceTwado;

import java.math.BigInteger;

/**
 *
 * @author desharnc27
 * 
 * This class is used only for validations (useless for competitors)
 * Purpose: being sure that the results are consistent.
 */
public class TwadoVerifier {
    
    //Returns tot, an array of long  such that tot[i]
    //is the total number of i-twadolicious matrices, modulo modu
    public static long[] getTotalCounts(int n, int modu) {

        //Building a Pascal Triangle
        //comb[i][j] will be assigned with C(i,j)
        long comb[][] = new long[n][n];
        comb[0][0] = 1;
        for (int i = 1; i < n; i++) {
            comb[i][0] = comb[i][i] = 1;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < i; j++) {
                comb[i][j] = (comb[i - 1][j] + comb[i - 1][j - 1]) % modu;
            }
        }

        //tot[i] will be the number of n-twadolicious matrices
        //insep[i] will be the number of n-twadolicious unitrack matrices
        long[] insep = new long[n];
        long tot[] = new long[n];

        //Initializing
        for (int i = 0; i < n; i++) {
            tot[i] = 0;
        }
        insep[2] = 1;
        tot[2] = 1;

        //Recursive calculations
        for (int i = 3; i < n; i++) {
            long temp = i * i - i;
            insep[i] = (insep[i - 1] * temp) % modu;
            for (int j = 2; j < i - 1; j++) {
                temp = 1;
                temp = temp * tot[i - j];
                temp = (temp * insep[j]) % modu;
                temp = (temp * comb[i - 1][j - 1]) % modu;
                temp = (temp * comb[i][j]) % modu;
                tot[i] = (tot[i] + temp) % modu;
            }
            tot[i] = (tot[i] + insep[i]) % modu;
        }
        return tot;
    }
    
    //Returns ceq, an array of long  such that ceq[i]
    //is the total number morphologies for i-twadolicious matrices
    public static long[] getMorphCounts(int n) {

        long[][] bank = new long[n][n];
        long ceq[] = new long[n];

        //ceq[i] is the number of way to choose a multisets of integers of {2,3...} such that
        //the ceq of its elements is i
        //bank[i][j] the number of way to choose a multisets of integers of {2,3...} such that
        //the ceq of its elements is i and such that it's greatest element is j
        for (int i = 0; i < n; i++) {
            ceq[i] = 0;
            for (int j = 0; j < n; j++) {
                bank[i][j] = 0;
            }
        }
        bank[0][0] = 1;
        ceq[0] = 1;

        for (int k = 2; k < n; k++) {
            for (int m = 2; m <= k; m++) {
                for (int i = 0; i <= m; i++) {
                    bank[k][m] += bank[k - m][i];
                }
                ceq[k] = ceq[k] += bank[k][m];

            }
        }
        return ceq;

    }

    //This methods verifies calculations by comparing different methods
    //to be sure that they all lead to the same results.
    //Different methods have different speeds, so overall,
    //this function computes slowly and is not suited for high value of n
    public static void verifyMathMatches(int n, int modu) {
        
        boolean globalMistake=false;
        
        //We don't want to manage too big integers
        if (n > 50) {
            n = 50;
        }
        
        long[] totalCount = getTotalCounts(n, modu);
        long[] morphCount = getMorphCounts(n);
        boolean mistakeFound = false;
        for (int i = 3; i < n; i++) {
            TotalCounter_Alt tcalt = new TotalCounter_Alt(i, modu);
            tcalt.compute();
            boolean morphMatch = morphCount[i] == tcalt.morphCount;
            boolean totalMatch = totalCount[i] == tcalt.totalCount;
            mistakeFound = mistakeFound || !morphMatch || !totalMatch;
            System.out.print(i + ": " + tcalt.morphCount + " , " + tcalt.totalCount + " , ");
            System.out.println(morphMatch + " , " + totalMatch);

        }
        if (mistakeFound){
            System.out.print("For at least one value, the two methods compute different answers using modulo ");
            System.out.println(modu);
        }else{
            System.out.print("The 2 methods match using modulo ");
            System.out.println(modu+" for all values up to "+n+" exclusively");
        }
        
        globalMistake=globalMistake || mistakeFound;
        mistakeFound=false;
        BigInteger moduBI = new BigInteger(String.valueOf(modu));

        BigInteger[] totalCountBI = TwadoSolution.getTotalCountsBI(n);
        //BigInteger[] totalCountBImod=new BigInteger[n];
        for (int i = 2; i < n; i++) {
            //totalCountBImod[i]=totalCountBI[i].mod(moduBI);
            boolean matchLongToBI = (totalCountBI[i].mod(moduBI)).intValue() == totalCount[i];
            mistakeFound=mistakeFound || !matchLongToBI;
            System.out.println(i + ": " + matchLongToBI);
        }
        if (mistakeFound){
            System.out.print("Using BigInteger over long values do not lead to the same results with modulo ");
            System.out.println(modu);
        }else{
            System.out.print("ToTal count comps with BigInteger match computations with long with modulo ");
            System.out.println(modu+" for all values up to "+n+" exclusively");
        }
        
        
        //Next validations are doable for much bigger values of n
        //So let's boost it up
        
        n= n*8;
        
        morphCount = getMorphCounts(n);
        BigInteger []morphCountBI = TwadoSolution.getMorphCountsBI(n);
        globalMistake=globalMistake || mistakeFound;
        mistakeFound=false;
        for (int i = 2; i < n; i++) {
            boolean matchLongToBI = morphCountBI[i].longValue() == morphCount[i];
            mistakeFound=mistakeFound || !matchLongToBI;
            //System.out.println(i + ": " + matchLongToBI);
        }
        if (mistakeFound){
            System.out.println("Using BigInteger over long values do not lead to the same results ");
        }else{
            System.out.print("morph count computations with BigInteger match computations with long ");
            System.out.println("for all values up to "+n+" exclusively");
        }
        if (globalMistake){
            System.out.print("Overall, at least a mistake occured");
        }else{
            System.out.print("Total success!! ");
        }
        
        
    }

    public static void main(String[] args) {
        verify(60,10001);
    }
    public static void verify(int n, int modu) {
        verifyMathMatches(n, modu);
    }
}
