/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

import java.math.BigInteger;

/**
 *
 * @author desharnc27
 */
public class General {
    public static <T>void print(T [] tab){
        for (int i=0;i<tab.length;i++){
            System.out.print(tab[i]+" ");
        }
        System.out.println();
    }
    public static <T>void printVert(T [] tab){
        for (int i=0;i<tab.length;i++){
            System.out.println(tab[i]);
        }
    }
    public static void print(int [] tab){
        for (int i=0;i<tab.length;i++){
            System.out.print(tab[i]+" ");
        }
        System.out.println();
    }
    public static void printLine(){
        //System.out.println("-x--x-x--x-x--x-x--x-");
        System.out.println("--------------------------");
    }

    public static void print(boolean[] tab) {
         for (int i=0;i<tab.length;i++){
            if (tab[i])
                System.out.print("T ");
            else
                System.out.print("F ");
        }
        System.out.println();
    }
    public static BigInteger bigInt(long val){
        return new BigInteger(String.valueOf(val));
    }
    public static BigInteger bigInt(int val){
        return new BigInteger(String.valueOf(val));
    }
    public static boolean baseIter(int base, int [] vals){
        int len=vals.length;
        for (int i=len-1;i>=0;i--){
            if (vals[i]<base-1){
                vals[i]++;
                return true;
                //for (j=i+1;j<len;j++)
                //    vals[j]
            }else{
                vals[i]=0;
            }            
        }
        return false;
    }

    public static int argmin(int[] arr) {
        int minVal=999999;//(2<<31-1);
        int minIdx=-1;
        for (int i=0;i<arr.length;i++){
            if (arr[i]<minVal){
                minIdx=i;
                minVal=arr[i];
            }
        }
        return minIdx;
    }
}
