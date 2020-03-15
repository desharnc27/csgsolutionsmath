
package matriceTwado;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import general.General;
import general.PrimeMeths;
import java.io.*;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;



public class TwadoReadChallenge {
    public static TwadoMatrix readTwadoFile(String filename) throws FileNotFoundException, IOException{
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        int n=0;
        while(br.readLine() != null)
            n++;
        br = new BufferedReader(new FileReader(file));
        String st;
        int[][] tab =new int[n][2];
        int idx=0;
        while ((st = br.readLine()) != null) {
            String [] s=st.split("[,:]");
            tab[idx][0]=Integer.parseInt(s[1]);
            tab[idx][1]=Integer.parseInt(s[2]);
            idx++;
        }
        return new TwadoMatrix(tab);
    }
    public static long proceed(int modu)  {
        
        //First step: create the matrix from file,
        
        TwadoMatrix tm;
        try {
            tm = readTwadoFile("matricetwadolicieuse.txt");
        } catch (IOException ex) {
            System.out.println("La congolexicomatisation du fichier n'a pas pu se faire, c'est clair");
            
            return (-1);
        }
        
        //Second step:  filling numcycles, where numcycles[i] indicates the
        //              number of i-cycles in the twado matrix
        
        int n=tm.n;      
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
        
        //Third step:   create the morphology corresponding to numCycles
        //              and calculate the number
        
        TwadoMorphology twadoMorph= new TwadoMorphology(numCycles);
        
        return twadoMorph.getNumberOfEquivs(modu);
    }
    
}
