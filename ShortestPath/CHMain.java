package ShortestPath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CHMain {
    public double CHCompute(Vertex[] graph, int start, int end, FileWriter fw) throws Exception{
        bidijstra ab = new bidijstra();

        long startTime = System.nanoTime();
        bidijstra.PreProcess process = new bidijstra.PreProcess();
        int[] nodeOrdering = process.processing(graph);
        long midTime = System.nanoTime();
//        for(int i = 0; i < nodeOrdering.length; i++) {
//            System.out.println(nodeOrdering[i]);
//        }

        bidijstra.BidirectionalDijkstra bd = new bidijstra.BidirectionalDijkstra();
        double res = bd.computeDist(graph, start, end, nodeOrdering);
        long endTime = System.nanoTime();
        long preTime = midTime - startTime;
        long queryTime = endTime - midTime;
        String newLine = System.getProperty("line.separator");
        if(res != -1 && res != 0) {
            fw.write(res + " " + preTime + " " + queryTime + newLine);
        }
        return res;
//        retrieve b = new retrieve();
//        System.out.println("The nodes we go through are : ");
//        b.retrieve(graph, 5804, 5817);

    }


}
