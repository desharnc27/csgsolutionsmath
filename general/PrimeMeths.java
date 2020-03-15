/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

/**
 *
 * @author desharnc27
 */
public class PrimeMeths {
    
    //This function returns base^exp 
    //exp must be positive
    public static long pow(int base, int exp){
        if (exp<2){
            if (exp==1)
                return base;
            else
                return 1;
        }
        long res=pow(base,exp/2);
        if (exp%2==1)
            return (res*res*base);
        else
            return (res*res);
    }
    //This function returns the base^exp mod modu
    //exp must be positive
    public static long powMod(int base, int exp, int modu) {
        if (exp < 2) {
            if (exp == 1) {
                return base;
            } else {
                return 1;
            }
        }
        long res = powMod(base, exp / 2, modu);
        long ans = (res * res) % modu;
        if (exp % 2 == 1) {
            ans = (ans * base) % modu;
        }
        if (ans<0){
            System.out.println("Warning!! Overflow occured in powMod");
        }
        
        return ans;
    }
    
    //This method returns array res of size n
    //res[i] will contain a divisor of i other than 1 or itself
    public static int [] nonTrivialDivisors(int n){
        int[] res = new int[n];
        for (int i = 1; i < n; i++) {
            res[i] = i;
        }
        int sqrn = 1 + (int) (Math.sqrt(n));
        int low = 2;
        while (low < sqrn) {
            if(res[low]!=low){
                low++;
                continue;
            }
            int idx = low * low;
            while (idx < n) {
                res[idx] = low;
                idx += low;
            }
            low++;
            
        }
        return res;

    }
    
    //This method returns boolean array of size n isPrime
    //isPrime[i]==true iff i is a prime number
    public static boolean[] erathos(int n) {
        boolean[] isPrime = new boolean[n];
        for (int i = 2; i < n; i++) {
            isPrime[i] = true;
        }
        int sqrn = 1 + (int) (Math.sqrt(n));
        int low = 2;
        while (low < sqrn) {
            if(!isPrime[low]){
                low++;
                continue;
            }
            int idx = low * low;
            while (idx < n) {
                isPrime[idx] = false;
                idx += low;
            }
            low++;
            
        }
        return isPrime;

    }
    
    //The array returned by this method contains all values of i such that isPrime[i]==true
    //Usually called right after erathos
    public static int[] primes(boolean isPrime[]) {
        int n = isPrime.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (isPrime[i]) {
                count++;
            }
        }
        int[] res = new int[count];
        count=0;
        for (int i = 0; i < n; i++) {
            if (isPrime[i]){
                res[count]=i;
                count++;
            }
        }
        return res;
    }
    //This method returns the integer factorization of num
    //primes must contain a list of at least all the potential prime numbers in the factorization
    //The returned array will contain at index i the exponent of primes[i] in the factorization
    public static int [] getFactExp(int num, int [] primes){
        int idx=0;
        int [] res=new int[primes.length];
        while (num>1){
            int fact=primes[idx];
            
            while (num%fact==0){
                res[idx]++;
                num/=fact;
            }
            idx++;
        }
        return res;
        
    }
    // Returns the lowest divisor of n
    // isPrime is an optional array that indicates all primes under its lenght
    // therefore its only purpose is increasing speed (by avoiding to try non-primes divisors)
    public static int lowestDivisor(int n, boolean [] isPrime){
        if (n<0)
            return lowestDivisor(-n, isPrime);
        if (n<2)
            return 1;
        int div=2;
        while(div<isPrime.length){
            if (isPrime[div] &&(n%div==0))
                return div;
            div++;
                
        }
        while (div<=n/2){
            if (n%div==0)
                return div;
            div++;
        }
        return n;
    }
    public static int lowestDivisor(int n){ 
        return lowestDivisor(n, new boolean[]{});
    }
    /**
     * Turns an product array to a prime product array
     * TODO
     * @param fact a product array
     * @param divs known non-trivial divisors of i at index i
     * 
     * Length of div must be at least length of fact
     */
    public static void turnToPrimeProduct(int [] fact, int [] divs){
        int n=fact.length-1;
        for (int i=n;i>3;i--){
            if (divs[i]!=i){
                fact[divs[i]]+=fact[i];
                fact[i/divs[i]]+=fact[i];
                fact[i]=0;
            }
        }
    }
    
}
