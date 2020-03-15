
package shapefilling;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author desharnc27
 * 
 * Useless for competitor.
 * 
 * State of one of the dyn. prog. methods that were compared
 * for debugging/validations. "one of the" -> not the most efficient
 */
public class FillState implements Comparable<FillState>{
    int nbFilledTiles;
    static int n;
    static int n0;
    static int n1;
    long code;
    boolean [][]filled;
    BigInteger numPoss;
    public FillState(int hei,int wid){
        n0=hei;
        n1=wid;
        n=(n0*n1);
        filled=new boolean[n0][n1];
        for (int i=0;i<n0;i++)
            for (int j=0;j<n1;j++)
                filled[i][j]=false;
        numPoss=new BigInteger("1");
        //numPoss=1;
        nbFilledTiles=0;
    }
    public FillState(FillState parent,int s0,int s1,int end0,int end1){
        filled=new boolean[n0][n1];
        for (int i=0;i<n0;i++)
            System.arraycopy(parent.filled[i], 0, filled[i], 0, n1);
        for (int i=s0;i<end0;i++)
            for (int j=s1;j<end1;j++)
                filled[i][j]=true;
        numPoss=parent.numPoss;
        nbFilledTiles=(parent.nbFilledTiles+(end0-s0)*(end1-s1));
        //TODO:code?
    }
    
    //public void further(ArrayList<FillState> queue,ArrayList<FillState> bank){
    public void further(ArrayList<FillState> queue){
        
        //Find the (s0,s1) tile that will be the top left that we fill
        int s0=0;
        int s1=0;
        int s;
        for (s=0;s<n;s++){
            if (!filled[s0][s1]){
                break;
            }
            s1++;
            if (s1==n1){
                s1=0;
                s0++;
            }
            
        }
        if (s==n){
            //TODO Full state
            return;
        }
        
        
        //Find (s0,t1), the first tile on s0 rank that is already filled
        
        int t1=s1;
        while (t1<n1&&!filled[s0][t1])
            t1++;
        
        //Apply recursive expansion
        for (int i=s0+1;i<=n0;i++)
            for(int j=s1+1;j<=t1;j++){
                
                //Do not consider square rectangles
                if (i-s0==j-s1)
                    continue;
                FillState newSt=new FillState(this,s0,s1,i,j);
                int idx=Collections.binarySearch(queue, newSt);
                if (idx>=0){
                    //If resulting config was already visited, then the number of
                    //possibilities to reach it simply sums up
                    
                    //bank.get(idx).numPoss=(bank.get(idx).numPoss).add(numPoss);
                    queue.get(idx).numPoss=(queue.get(idx).numPoss).add(numPoss);
                    //bank.get(idx).numPoss+=numPoss;
                    continue;
                }
                //Otherwise, add a new config to explore
                idx=-idx-1;
                queue.add(idx,newSt);
                //idx=Collections.binarySearch(queue, newSt);
                //idx=-idx-1;
                //queue.add(idx,newSt);
            }
        
    }

    @Override
    public int compareTo(FillState st) {
        int compa=nbFilledTiles-st.nbFilledTiles;
        if (compa<0)
            return -1;
        if (compa>0)
            return 1;
        for (int i=0;i<n0;i++)
            for (int j=0;j<n1;j++){
                if (!filled[i][j]&&st.filled[i][j])
                    return -1;
                if (filled[i][j]&&!st.filled[i][j])
                    return 1;
            }
        return 0;
    }
    //Prints the parital configuration
    //Useless to competitor. Only usefull for validations.
    public void fancyPrint(){
        System.out.println("----------");
        for (int i=0;i<n0;i++){
            for (int j=0;j<n1;j++){
                System.out.print(filled[i][j]? "X":"O");
            }
            System.out.println();
        }
        System.out.println("numPoss: "+numPoss);
        System.out.println("nbFilledTiles: "+nbFilledTiles);
        System.out.println("----------");
            
    }
    
    //Counts the number of possible final configuration
    //that go through this configuration, using depth-first-search
    //Useless to competitor. Only usefull for validations.
    public int dfsCount() {
        //Find the (s0,s1) tile that will be the top left that we fill
        int s0=0;
        int s1=0;
        int s;
        for (s=0;s<n;s++){
            if (!filled[s0][s1]){
                break;
            }
            s1++;
            if (s1==n1){
                s1=0;
                s0++;
            }
            
        }
        if (s==n){
            //TODO Full state
            return 1;
        }
        
        
        //Find (s0,t1), the first tile on s0 rank that is already filled
        
        int t1=s1;
        while (t1<n1&&!filled[s0][t1])
            t1++;
        
        //Apply recursive expansion
        int res=0;
        for (int i=s0+1;i<=n0;i++)
            for(int j=s1+1;j<=t1;j++){
                
                //Do not consider square rectangles
                if (i-s0==j-s1)
                    continue;
                FillState newSt=new FillState(this,s0,s1,i,j);
                res+=newSt.dfsCount();
            }
        return res;
    }
}
