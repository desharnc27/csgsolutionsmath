/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matriceTwado;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author desharnc27
 * 
 * Useless for competitors
 * 
 * This class was used to create the 2020-twadolicious matrix
 * 
 * 
 */
public class TwadoGenerator {

    public static void main(String[] args) {
        int n = 2020;
        
        //Random procedure that will decide the morphology of the matrix
        boolean[] brk = new boolean[n];
        for (int i = 0; i < brk.length - 2; i++) {    
            if (i > Math.random() * n) {
                brk[i] = true;
                i += 2;
            }

        }
        brk[n - 1] = true;
        
        //Create the matrix
        TwadoMatrix matri = new TwadoMatrix(n, brk);
        
        //Randomly swap columns and rows
        for (int i=n;i>=2;i--){
            int a=(int)(Math.random()*i);
            matri.ranPer(i-1,a);
            a=(int)(Math.random()*i);
            matri.colPer(i-1,a);
        }
        
        //Write matrix in file "matricetwadolicieuse.txt"
        PrintWriter writer;
        try {
            writer = new PrintWriter("matricetwadolicieuse.txt", "UTF-8");
            for (int i = 0; i < n; i++) {
                String s = i + ":" + matri.ran[i].toString();
                writer.println(s);
            }
            writer.close();
        } catch (Exception ex) {System.out.println("Error somewhere");
            Logger.getLogger(TwadoGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }
}
