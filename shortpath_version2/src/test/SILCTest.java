package test;

import algorithm.SILC;
import modeling.Edge;
import modeling.WeightEdge;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.FileWriter;  
import java.io.IOException;

public class SILCTest {
	
	Graph<Integer, Edge> graph;
    SILC<Integer,Edge> silc;

    @Before
    public void before() throws Exception {
        List<Integer> order = new ArrayList<>();
        graph = new SimpleWeightedGraph<Integer, Edge>(WeightEdge.class);
        File input = new File("/Users/gejiali/study/ECE/Spring2018/CS562/project/qq.txt");
        Scanner in = new Scanner(input);
        //int num = in.nextInt();
        for(int i = 0; i < 6105; i++){
            graph.addVertex(i);
            order.add(i);
        }
        //in.nextInt();
        //System.out.println();
        while (in.hasNext()){
        	    //in.nextInt();
            int v = in.nextInt();
            //System.out.print(v);
            int w = in.nextInt();
            double weight = in.nextDouble();
            //if (v < 12000 && w < 12000) {
            	WeightEdge<Integer> edge = new WeightEdge<Integer>(v,w,weight);
            	graph.addEdge(v,w,edge);
            //}
        }
        in.close();
        silc = new SILC<>(graph);
        long starttime1 = System.nanoTime();
        silc.init();
        long endtime1 = System.nanoTime();
        long time1 = endtime1 - starttime1;
        System.out.println(time1);
    }

    @Test
    public void testGetPath() throws Exception{
    	
    		String newLine = System.getProperty("line.separator");
    		FileWriter fw = new FileWriter("/Users/gejiali/Desktop/test/CA1.txt");
    		File inputPoint = new File("/Users/gejiali/Desktop/randomqueryCA1.txt");
    		Scanner inPoint = new Scanner(inputPoint);
    		while (inPoint.hasNext()){
    			int x = inPoint.nextInt();
    			int y = inPoint.nextInt();
    			//System.out.println(x);
    			long starttime2 = System.nanoTime();
    			silc.getPathVertex(x,y);
    			long endtime2 = System.nanoTime();
    	        long time2 = endtime2 - starttime2;
    	        String time = Long.toString(time2);
    	        fw.write(time + newLine);
    	        
    		}
//    		System.out.println(silc.getPathVertex(0,1));
    		inPoint.close();
    		fw.close();
    		long memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    		System.out.println(memory);
    		//long starttime2 = System.nanoTime();
//        System.out.println(silc.getPathVertex(2613,2664));
//        System.out.println(silc.getPathVertex(1423,712));
//        System.out.println(silc.getPathVertex(1609,1622));
        
//        System.out.println(time2);
    }

}
