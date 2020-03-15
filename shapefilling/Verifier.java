
package shapefilling;

import general.General;
import general.PrimeMeths;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author desharnc27
 * 
 * Useless for competitors
 * 
 * Class for debugging/validations
 * 
 */
public class Verifier {
    public static void main(String[] args) {
        verifier();
    }

    public static BigInteger mainMethod(int n0, int n1) {
        int n = n0 * n1;

        ArrayList<FillState> queue = new ArrayList<>();

        FillState first = new FillState(n0, n1);
        queue.add(first);
        FillState actual = first;
        while (!queue.isEmpty()) {
            actual = queue.remove(0);
            actual.further(queue);

        }

        //System.out.println("fullState: " + actual.numPoss);
        return actual.numPoss;
    }

    public static long depthFirstMethod(int n0, int n1) {

        FillState first = new FillState(n0, n1);
        return first.dfsCount();
    }

    public static BigInteger twoLayersCaseCountBI(int n) {
        return twoLayersCaseCountBI(n, -1);
    }

    public static BigInteger twoLayersCaseCountBI(int n, int modu) {

        int len = n + 1;

        BigInteger thinLayerCount[] = new BigInteger[len];
        BigInteger thinLayerCount2[] = new BigInteger[len];

        BigInteger BI0 = new BigInteger("0");
        BigInteger BI1 = new BigInteger("1");

        thinLayerCount[0] = thinLayerCount2[0] = BI1;

        for (int i = 2; i < len; i++) {
            thinLayerCount[i] = BI1;
            for (int j = 2; j < i - 1; j++) {
                thinLayerCount[i] = thinLayerCount[i].add(thinLayerCount[j]);
            }
            thinLayerCount2[i] = thinLayerCount[i].multiply(thinLayerCount[i]);
        }

        BigInteger thickStartCount[] = new BigInteger[len];
        BigInteger thin2StartCount[] = new BigInteger[len];
        BigInteger anyStartCount[] = new BigInteger[len];

        thickStartCount[0] = BI1;
        thin2StartCount[0] = BI1;
        anyStartCount[0] = BI1;

        for (int i = 1; i < len; i++) {
            thin2StartCount[i] = BI0;
            for (int j = 2; j <= i; j++) {
                thin2StartCount[i] = thin2StartCount[i].add(
                        thinLayerCount2[j].multiply(thickStartCount[i - j]));
            }
            thickStartCount[i] = anyStartCount[i - 1];
            for (int j = 3; j <= i; j++) {
                thickStartCount[i] = thickStartCount[i].add(anyStartCount[i - j]);
            }
            anyStartCount[i] = thin2StartCount[i].add(thickStartCount[i]);

            //TODO delete??
            if (modu > 0) {
                BigInteger modBI = new BigInteger(String.valueOf(modu));
                anyStartCount[i] = anyStartCount[i].mod(modBI);
                thickStartCount[i] = thickStartCount[i].mod(modBI);
                thin2StartCount[i] = thin2StartCount[i].mod(modBI);
            }

        }

        /*for (int i = 0; i < len; i++) {
            System.out.println(i + ": " + anyStartCount[i]);
        }*/
        return anyStartCount[n];
    }

    public static void verifier() {

        int verif = 0;
        while (verif < 4) {
            Long t1;
            Long t2;
            Long t3;
            int d0;
            int d1;
            int modu;
            long ans0, ans1, ans2;

            boolean match;

            switch (verif) {
                case 0:
                    d0 = 5;
                    d1 = 5;
                    ans0 = depthFirstMethod(d0, d1);
                    ans1 = mainMethod(d0, d1).longValue();
                    ans2 = Solution.baseIndexMethod(d0, d1);
                    match = (ans0 == ans1) && (ans0 == ans2);
                    System.out.println("Test(" + d0 + "," + d1 + "):");
                    System.out.println("dfs: " + ans0);
                    System.out.println("arrList: " + ans1);
                    System.out.println("main: " + ans2);
                    System.out.println("values match: " + match);
                    break;
                case 1:
                    d0 = 6;
                    d1 = 6;
                    ans0 = mainMethod(d0, d1).longValue();
                    ans1 = Solution.baseIndexMethod(d0, d1);
                    match = (ans0 == ans1);
                    System.out.println("Test(" + d0 + "," + d1 + "):");
                    System.out.println("arrList: " + ans0);
                    System.out.println("main: " + ans1);
                    System.out.println("values match: " + match);
                    break;
                case 2:
                    d0 = 2;
                    d1 = 500;
                    modu = 999999937;
                    ans0 = Solution.baseIndexMethod(d0, d1,modu);
                    ans1 = Solution.twoLayersCaseCount(d1, modu);
                    ans2 = twoLayersCaseCountBI(d1,modu).longValue();
                    match = (ans0 == ans1) && (ans0 == ans2);
                    System.out.println("Test(" + d0 + "," + d1 + "):");
                    System.out.println("main: " + ans0);
                    System.out.println("meth2: " + ans1);
                    System.out.println("methBI: " + ans2);
                    System.out.println("values match: " + match);
                    break;
                case 3:
                    d0 = 2;
                    d1 = 4000;
                    modu = 999999937;
                    ans0 = Solution.twoLayersCaseCount(d1, modu);
                    ans1 = twoLayersCaseCountBI(d1,modu).longValue();
                    match = (ans0 == ans1);
                    System.out.println("Test(" + d0 + "," + d1 + "):");
                    System.out.println("meth2: " + ans0);
                    System.out.println("methBI: " + ans1);
                    System.out.println("values match: " + match);

            }
            General.printLine();
            verif++;
        }
    }

}
