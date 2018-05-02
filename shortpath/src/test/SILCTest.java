package test;

import algorithm.SILC;
import modeling.Edge;
import modeling.WeightEdge;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SILCTest {
	
	WeightedGraph<Integer, Edge> graph;
    SILC<Integer,Edge> silc;

    @Before
    public void before() throws Exception {
        List<Integer> order = new ArrayList<>();
        graph = new SimpleWeightedGraph<Integer, Edge>(WeightEdge.class);
        File input = new File("/Users/gejiali/study/ECE/Spring2018/CS562/project/medium.txt");
        Scanner in = new Scanner(input);
        int num = in.nextInt();
        for(int i = 0; i < num; i++){
            graph.addVertex(i);
            order.add(i);
        }
        in.nextInt();
        //System.out.println();
        while (in.hasNext()){
        	    //in.nextInt();
            int v = in.nextInt();
            int w = in.nextInt();
            double weight = in.nextDouble();
            WeightEdge<Integer> edge = new WeightEdge<Integer>(v,w,weight);
            graph.addEdge(v,w,edge);
        }
        in.close();
        silc = new SILC<>(graph);
        silc.init();
    }

    @Test
    public void testGetPath(){
        System.out.println(silc.getPathVertex(2,3));
    }

}
