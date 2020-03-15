/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matriceTwado;

import general.General;
import general.PrimeMeths;
import java.util.ArrayList;

/**
 *
 * @author desharnc27
 *
 * This class represents a possible morphology of a n-twadolicious matrix.
 */
public class TwadoMorphology {

    private int n;

    //numCycles[i] is the number of circuits of size i contained in the morphology    
    private int[] numCycles;

    TwadoMorphology(int[] morphArray) {
        n = morphArray.length - 1;
        numCycles = morphArray;
    }

    public int getSize() {
        return n;
    }

    //Returns the number of twdolicious matrices having that morphology
    public long getNumberOfEquivs(int modu) {

        //Third step:   from numCycles and theoretical formula, create a
        //              product array representing the number of possibilites.

        int[] fact = new int[n + 1];
        for (int i = 0; i < numCycles.length; i++) {
            int nCy = numCycles[i];
            if (nCy > 0) {
                fact[2] -= nCy;
                fact[i] -= nCy;
                for (int j = 2; j <= nCy; j++) {
                    fact[j]--;
                }
            }
        }
        for (int i = 1; i <= n; i++) {
            fact[i] += 2;
        }
        //Fourth step: arrange fact such that it has a prime product form (gets rid of negative powers)
        
        int[] divs = PrimeMeths.nonTrivialDivisors(n + 1);
        PrimeMeths.turnToPrimeProduct(fact, divs);

        //Fifth step: calculate value of number represented by fact, modulo modu
        long ans = 1;
        for (int i = 2; i < fact.length; i++) {
            ans *= PrimeMeths.powMod(i, fact[i], modu);
            ans = ans % modu;
        }

        return ans;
    }
}
