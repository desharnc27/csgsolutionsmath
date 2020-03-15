/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matriceTwado;

import java.util.ArrayList;

/**
 *
 * @author desharnc27
 * 
 * Useless to competitor, for validations only
 *
 * This class contains an object that, for some n, iterates
 * over all possibles morphologies of n-twadolicious matrices
 * and counts the number of twadolicious matrices in each of them, then
 * sums it up in totalCount. During the process
 * it also keeps track of morphCount, the number of different morphologies it found
 */
public class TotalCounter_Alt {

    int n;
    int modu;
    long totalCount = 0;
    int morphCount = 0;
    ArrayList<TwadoMorphology> leaves;

    private void includeCountOfMorphology(int[] morph, int idx) {
        totalCount += morphEquivCount(morph);
        totalCount %= modu;
        morphCount++;
    }

    public TotalCounter_Alt(int n, int modu) {
        this.n = n;
        this.modu = modu;
    }
    
    //Performs calculations of totalCount and morphcount
    public void compute() {
        totalCount = 0;
        morphCount = 0;
        int[] morph = new int[n + 1];
        int idx = 2;
        int partN = 0;

        while (true) {

            if (partN > n - idx - 2) {
                //Only one possible fill
                if (partN == n - idx) {
                    morph[idx] = 1;
                    includeCountOfMorphology(morph, idx);
                    morph[idx] = 0;
                } else if (partN == n - idx - 1 && idx < n) {
                    morph[idx + 1] = 1;
                    includeCountOfMorphology(morph, idx + 1);
                    morph[idx + 1] = 0;
                }

                idx--;
                if (idx < 2) {
                    break;
                }
                morph[idx]++;
                partN += idx;
                if (n - partN < idx) {
                    if (partN == n) {
                        includeCountOfMorphology(morph, idx);
                    }
                    partN -= morph[idx] * idx;
                    morph[idx] = 0;
                    idx--;
                    if (idx < 2) {
                        break;
                    }
                    morph[idx]++;
                    partN += idx;
                }
                idx++;
                continue;
            }
            //if (partN<n){
            if (idx == n) {
                morph[idx] = 1;
                partN += idx;
            } else {
                morph[idx] = 0;
            }
            idx++;
        }
    }

    public long morphEquivCount(int[] numCycles) {
        int[] morphArray = new int[n + 1];
        System.arraycopy(numCycles, 0, morphArray, 0, n + 1);
        TwadoMorphology tmo = new TwadoMorphology(morphArray);
        return tmo.getNumberOfEquivs(modu);
    }

    public void debugPrint(int[] numCycles, int lastIdx) {
        System.out.println("----------");
        for (int i = 0; i <= lastIdx; i++) {
            System.out.print(numCycles[i] + " ");
        }
        System.out.println();
    }

}
