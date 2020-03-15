
package matriceTwado;

/**
 *
 * garbage code
 */
public class Garbage {
    /*public static void main0()  {
        
        final int modu=999999937;
        
        //First step:create the matrix from file,
        
        TwadoMatrix tm;
        try {
            tm = readTwadoFile("matricetwadolicieuse.txt");
        } catch (IOException ex) {
            System.out.println("La congolexicomatisation du fichier n'a pas pu se faire, c'est clair");
            return;
        }
        
        //Second step:  filling numcycles, where numcycles[i] indicates the
        //              number of i-cycles in the twado matrix
        
        int n=tm.n;
        tm.printTiff();       
        General.printLine();
        boolean [] viewed=new boolean[n];
        int [] numCycles=new int[n+1];
        int idx;
        for (idx=0;idx<n;idx++){
            if (viewed[idx]){
                continue;
            }
            int y=idx;
            int x=tm.ran[idx].v0;
            int count=0;
            do{
                y=tm.otherRan(y,x);
                x=tm.otherCol(y,x);
                count++;
                viewed[y]=true;
            }while(idx!=y);
            numCycles[count]++;
        }
        
        General.print(numCycles);
        General.printLine();
        
        //Third step:   from numCycles and theoretical formula, create a
        //              product array representing the number of possibilites.
        
        int [] divs=PrimeMeths.nonTrivialDivisors(n+1);
        
        int [] fact=new int[n+1];
        for (int i=0;i<numCycles.length;i++){
            int nCy=numCycles[i];
            if (nCy>0){
                fact[2]-=nCy;
                fact[i]-=nCy;
                for (int j=2;j<=nCy;j++)
                    fact[j]--;
            }
        }
        for (int i=1;i<=n;i++){
            fact[i]+=2;
        }
        General.print(fact);
        General.printLine();
        
        //Fourth step: arrange fact such that it has a prime product form (gets rid of negative powers)
        
        PrimeMeths.turnToPrimeProduct(fact,divs);
        General.print(fact);
        General.printLine();
        
        //modu step: calculate value of number represented by fact modulo modu
        
        long ans=1;
        for (int i=2;i<fact.length;i++)
            for (int j=0;j<fact[i];j++){
                ans*=i;
                ans=ans%modu;
            }
        System.out.println("The final number is:"+ans);        
        
        
        //Next,depends on requirements
        
        BigInteger nbDiv=new BigInteger("1");
        for (int i=2;i<fact.length;i++){
            if (fact[i]==0)
                continue;
            nbDiv=nbDiv.multiply(new BigInteger(Integer.toString(fact[i]+1)));
        }
        System.out.println("The final number is:"+nbDiv);
        
    }*/
    
      // Like public version, but without range checks.

    /*private static int binarySearch0(int[] a, int fromIndex, int toIndex,

                                     int key) {

        int low = fromIndex;

        int high = toIndex - 1;


        while (low <= high) {

            int mid = (low + high) >>> 1;

            int midVal = a[mid];


            if (midVal < key)

                low = mid + 1;

            else if (midVal > key)

                high = mid - 1;

            else

                return mid; // key found

        }

        return -(low + 1);  // key not found.

    }/*
    
    
    
    
    */
    //System.out.println("The final number is:"+ans);        
        
        /*
        //Next,depends on requirements
        
        BigInteger nbDiv=new BigInteger("1");
        for (int i=2;i<fact.length;i++){
            if (fact[i]==0)
                continue;
            nbDiv=nbDiv.multiply(new BigInteger(Integer.toString(fact[i]+1)));
        }
        System.out.println("The final number is:"+nbDiv);
        */
}
