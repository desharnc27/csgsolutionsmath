
package shapefilling;

import general.General;
import general.PrimeMeths;

/**
 *
 * @author desharnc27
 * 
 * Only necessary class for the 3 Filling rectangles puzzle (with general package)
 * Prints the answer of all three problems
 * 
 */
public class Solution {
    
    public static long baseIndexMethod(int n0, int n1) {
        return baseIndexMethod(n0, n1,-1);
    }
    public static long baseIndexMethod(int n0, int n1, int modu) {
        
        //don't pay any attention to modu, it is used only for testing/validating

        if (n0 < n1) {
            //Working with few ranks and a lot of columns is much more efficient
            int temp = n0;
            n0 = n1;
            n1 = temp;
        }

        //rankFill[i] will the represent the number of already filled tiles in rank 
        //i of the configuration investigated at the moment.
        int[] rankFill = new int[n1];

        //counts[i] is the number of way to get to config i        
        long[] counts = new long[(int) PrimeMeths.pow(n0 + 1, n1)];

        counts[0] = 1;

        //globalIdx will be the configuration under investigation at the moment        
        int globalIdx = 0;
        int powTab[] = new int[n1];
        powTab[0] = 1;
        for (int i = 1; i < n1; i++) {
            powTab[i] = (n0 + 1) * powTab[i - 1];
        }

        //Since globalIdx and rankFill always both independently indicates the acutal configuration,
        //the two must remain matching all way long. That's why the following formula will always be true:
        //sum(rankfill[i]*powTab[n0-i-1],i=0 to n1-1)=globalIdx
        
        //Front propagation:
        //for any config, find all configs that we can get by adding a single rectangle
        //covering at least the mandatory next tile. For every successor config
        //its number of possibilities must be increased by the one
        //of the current config

        do {

            if (counts[globalIdx] == 0) {
                //The current config is unreachable, so no need to look for its successors
                globalIdx++;
                continue;
            }

            //Compute the mandatory next tile to fill
            //TODO:complete explanation
            int minIdx = General.argmin(rankFill);
            int minVal = rankFill[minIdx];

            if (minVal > n0) {
                //We are now in the fully filled config, no more computations needed
                break;
            }

            // exclIdx is the first rank that cannot be covered by the next rectangle
            //(because it would cover at least one tile that is already filled)
            int exclIdx = minIdx + 1;
            while (exclIdx < n1 && rankFill[exclIdx] == minVal) {
                exclIdx++;
            }

            int xxad = 0;

            //Iterate over all the possible rectangles that we can add next
            for (int i = minIdx; i < exclIdx; i++) {
                //i-minIdx+1 is the height of the rectangle to add

                //xxad represents how much the global index of a config increases
                //when we add a single column rectangle from ranks minIdx to i inclusively
                xxad += powTab[n1 - i - 1];
                for (int j = minVal; j < n0; j++) {

                    //zxcv is the width of the rectangle to add
                    int zxcv = j - minVal + 1;

                    if (i - minIdx + 1 == zxcv) {
                        //Squares are not allowed
                        continue;
                    }

                    //Compute the global index of the new config by adding the index score
                    // of the rectangle, xxad * zxcv, to the current global index
                    int newGlobIdx = globalIdx + xxad * zxcv;
                    counts[newGlobIdx] += counts[globalIdx];
                    if (modu>0){
                        counts[newGlobIdx]%=modu;
                    }

                    //Overflow (summing over 2^61, resulting in negative) check
                    /*if (counts[newGlobIdx] < 0) {                        
                        System.out.println("An overflow problem occured during the process");
                        return -1;
                    }*/
                }
            }

            //Move on to next config
            globalIdx++;
        } while (General.baseIter(n0 + 1, rankFill));
        return counts[globalIdx - 1];
    }

    //This function return f(2,n)
    public static long twoLayersCaseCount(int n, int modu) {
        
        //The main rectangle to fill has 2 rows and n columns
        //to understand the calculations, note in general that the
        //filling is performed from left to right, having the choice at each
        //step of putting 1. a thick rectangle covering both rows or
        //2. two thin rectangles covering one row each.
        
        
        int len = n + 1;

        //thinLayerCount[i] is the number of ways to fill a 1 x i rect (without squares)
        //thinLayerCount2[i] is the number of ways to fill two 1 x i rect (without squares)
        //one on top of the other
        long thinLayerCount[] = new long[len];
        long thinLayerCount2[] = new long[len];

        thinLayerCount[0] = thinLayerCount2[0] = 1;
        
        //You get a layer of size i by  1.choosing a number j in {2...i-2}
        //2. Putting at leftmost  rectangle of width i-j 
        //3. Filling the rest of the main rectangle with any 
        //   of the thinLayerCount[j] possible configurations
        //Which explains the following calculations:
        for (int i = 2; i < len; i++) {
            thinLayerCount[i] = 1;
            for (int j = 2; j < i - 1; j++) {
                thinLayerCount[i] = (thinLayerCount[i] + thinLayerCount[j]) % modu;
            }
            thinLayerCount2[i] = (thinLayerCount[i] * thinLayerCount[i]) % modu;
        }

        //Filling a 2 x i big rectangle from left to right has 2 possible starts
        //Thick start: putting at leftmost a rectangle that covers the 2 rows
        //Thin2 start: putting at leftmost two rectangles, one on top of the other
        //So we have:
        //thickStartCount[i] is the way to fill a 2 x i big rectangle using a thick start
        //thickStartCount[i] is the way to fill a 2 x i big rectangle using a thin2 start
        //anyStartCount[i] is the sum of both (i.e f(2,i)) except fo i==0
        long thickStartCount[] = new long[len];
        long thin2StartCount[] = new long[len];
        long anyStartCount[] = new long[len];

        thickStartCount[0] = 1;
        thin2StartCount[0] = 1;
        anyStartCount[0] = 1;

        for (int i = 1; i < len; i++) {

            thin2StartCount[i] = 0;
            //with a thin2 start, you choose j, the length between the start and the 
            //next thick block, then there is thinLayerCount2[j] ways to fill that part
            //and thickStartCount[i - j] to fill the rest of the main rectangle
            //Note that this last value must not be anyStartCount[i - j] since
            //we don't want to count short thin layers and then 
            //other short thin layers, because it is already counted by 
            //considerating this as one bigger section of thin layers.
            for (int j = 2; j <= i; j++) {
                thin2StartCount[i] += (thinLayerCount2[j] * thickStartCount[i - j]) % modu;
            }
            thin2StartCount[i] %= modu;

            //For computing thickStartCount[i], just choose the width j of the first thick rectangle
            //and then add any 2 x (i-j) config after
            //Note that j!=2 since squares are not allowed
            thickStartCount[i] = anyStartCount[i - 1];
            for (int j = 3; j <= i; j++) {
                thickStartCount[i] += anyStartCount[i - j];
            }
            thickStartCount[i] %= modu;
            anyStartCount[i] = thin2StartCount[i] + thickStartCount[i];
            anyStartCount[i] %= modu;
        }

        return anyStartCount[n];
    }

    public static void main(String[] args) {

        //Print the solution of the first problem
        int v0 = 5;
        int v1 = 5;
        System.out.println("Solution to first problem is: "+baseIndexMethod(v0, v1));

        //Print the solution of the second problem
        v0=20203;
        int modu=999999937;
        System.out.println("Solution to second problem is: "+Solution.twoLayersCaseCount(v0,modu));
        
        //Print the solution of the third problem
        v0=7;
        v1=11;
        System.out.println("Solution to third problem is: "+baseIndexMethod(v0, v1));
    }
}
