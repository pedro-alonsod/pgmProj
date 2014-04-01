/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pgmproj;
import smile.Network;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.math.BigDecimal;
import java.util.Vector;
import java.util.*;
        
/**
 *
 * @author pedroalonso
 */
public class PgmProj {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
        
        Network pgmProj = new Network();
        
        
        //Find where are we
        
        final String dir = System.getProperty("user.dir");
       // System.out.println("current dir = " + dir);
        
        //It looks we are in he correct place, read the genie file network
         pgmProj.readFile("Network/bnproject.xdsl");
         
         // do this always
         pgmProj.updateBeliefs();
         
         //got it, simple check
         String nodes[] = pgmProj.getAllNodeIds();
         int count = 1;
         for(int outNodes = 0; outNodes < nodes.length; outNodes++ ){
             
            // System.out.println("Nodes: \n");
            // System.out.println(nodes[outNodes] + " #");
            // System.out.println(outNodes + "  l");
             count += outNodes;
            // System.out.println(count + " *");
         }
         
         int totalNodes = count+ nodes.length;
         // System.out.println(totalNodes);
         
         //we are good, read the test data
         
         Scanner testData = new Scanner(new FileInputStream("Network/test_data.txt"));
         
         //print first line to see what
         String line = testData.nextLine();
         //System.out.println(" first line " + line );
         
         //still good, now tokenize it, do some more testing to check what is happnening
         
         StringTokenizer namesNodes = new StringTokenizer(line, " ");
         String[] bnNodesnames = new String[count+1];
         int theNodes = 0;
         while(namesNodes.hasMoreTokens()){
             
             bnNodesnames[theNodes] = namesNodes.nextToken();
           //  System.out.println(bnNodesnames[theNodes] +" *");
             theNodes++;
         }
         
        String[] aValues = pgmProj.getOutcomeIds("K");
        double[] aTest = pgmProj.getNodeDefinition("K");
         
         for(int out = 0; out < aValues.length; out++) {
             
          //   System.out.println(aValues[out] +" "+ aTest[out]);
             
             
         }
         
         //K = x2 like so, to everyone
         //first vector test has to be equal to genie probability of evidence
         String vector1 = testData.nextLine();
         StringTokenizer firstV = new StringTokenizer(vector1, " ");
         
        /* //first print it to see it
         while(firstV.hasMoreElements()){
             
             System.out.println(firstV.nextToken()+ " "+ firstV.countTokens());
         }
         */
         //looks fine now try it
         firstV = new StringTokenizer(vector1, " ");
         int tokens = firstV.countTokens();
         for(int v = 0; v < tokens; v++){
            //System.out.println(bnNodesnames[v]+ " val "+("x"+firstV.nextToken()));
            pgmProj.setEvidence(bnNodesnames[v], ("x"+firstV.nextToken()));
           }
         pgmProj.updateBeliefs();
         
         //then we have the first one get p(E)
         
        // System.out.println(pgmProj.probEvidence());
         double Result = pgmProj.probEvidence();
         double[] evProb = new double[1501];
         evProb[0] = Result;
         //got it correct, must 
         
         pgmProj.clearAllEvidence();
         
         // second vector
         /*
         vector1 = testData.nextLine();
         firstV = new StringTokenizer(vector1, " ");
         tokens = firstV.countTokens();
         for(int v = 0; v < tokens; v++){
             
            //System.out.println(bnNodesnames[v]+ " val "+("x"+firstV.nextToken()));
            pgmProj.setEvidence(bnNodesnames[v], ("x"+firstV.nextToken()));
         }
         pgmProj.updateBeliefs();
         System.out.println(pgmProj.probEvidence());
         */
         
         //new 1499 obs
         int evCount = 1;
       while(testData.hasNextLine()){
             pgmProj.updateBeliefs();
             vector1 = testData.nextLine();
         firstV = new StringTokenizer(vector1, " ");
         tokens = firstV.countTokens();
        
         for(int v = 0; v < tokens; v++){
             
            //System.out.println(bnNodesnames[v]+ " val "+("x"+firstV.nextToken()));
            pgmProj.setEvidence(bnNodesnames[v], ("x"+firstV.nextToken()));
         }
         pgmProj.updateBeliefs();
         //System.out.println(pgmProj.probEvidence());
         evProb[evCount] = pgmProj.probEvidence();
         evCount++;
         pgmProj.clearAllEvidence();
         }
         
         for(int i = 0; i < evProb.length-1; i++) {
             
             evProb[1500] += evProb[i];
             //System.out.println(" P(E)["+i+"] = "+ (evProb[i]));
             
         }
         
         //System.out.println(" Normalization = "+evProb[1500]);
         
         for(int i = 0; i < evProb.length; i++){
             
             System.out.println("\n "+(evProb[i]/evProb[1500]));
             
         }
         
         
         //Next is to try to implement the MDL algorithm parameter learning and do it again, structure learning with GENIE
         //MDL
         //Parameter learning MLE
         //Parameter learning bayes
         //Test
         /* after testing the second diagram
         testData.close();
         testData = new Scanner(new FileInputStream("Network/test_data.txt"));
         
         // read first line with names/ throw them away since we have it on nodes
         System.out.println(testData.nextLine());
         Vector<String> nextLine;
         nextLine = new Vector<String>();
         
         
         while(testData.hasNext()) {
         
             String dataOfNet = testData.nextLine();
             System.out.println(dataOfNet+ " * ");
             nextLine.add(dataOfNet);
             
         }
          
         System.out.println(nextLine.size() +" * "+ nextLine.firstElement());
         */
        /* while(!nextLine.isEmpty()) {
             
             //System.out.println(nextLine.pop());
             
         }*/
         
         //here
         //genie normal settings look ok, next try different learning for parameters if possible, loks like is not, what else to try??
         pgmProj.readFile("Network/pgmpro2.xdsl");
         
         pgmProj.updateBeliefs();
         
         testData = new Scanner(new FileInputStream("Network/test_data.txt"));
                 
         //throw first line since we have it already
         testData.nextLine();
         double[] probEv = new double[1501];
         
         for(int j=0; j < 1500; j++) {
         
         String nextLine = testData.nextLine();
         StringTokenizer nextTokents = new StringTokenizer(nextLine, " ");
         System.out.println(nextLine);
         int i = 0;
         
         while(nextTokents.hasMoreTokens()) {
             
             
             String value = nextTokents.nextToken();
             System.out.println(bnNodesnames[i]+ " State"+ value+" +/");
             pgmProj.setEvidence(bnNodesnames[i], ("State"+value));
             i++;
         }
          System.out.println(pgmProj.probEvidence()+" -*");
          probEv[j] = pgmProj.probEvidence();
          pgmProj.clearAllEvidence();
          pgmProj.updateBeliefs();
          
         }
         
         for(int i=0; i< probEv.length;i++) {
             
             System.out.println(probEv[i]);
             probEv[1500] += probEv[i];
            
         }
         
         System.out.println(probEv[1500]+" *");
         
         for(int i=0; i <probEv.length; i++){
             
             probEv[i] = probEv[i]/probEv[1500];
             System.out.println(probEv[i]);
             
         }
          
        } catch(Exception exe) {
            
            //just catch everything no time to worry about them
            System.out.println(exe.toString());
        }
        
        
         
    }
    
}
