package ShortestPath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        bidijstra a = new bidijstra();
        Vertex vertex = new Vertex();

        FileReader file = new FileReader("C:\\Users\\caoyu\\Desktop\\OL.txt");
        BufferedReader bufferedReader = new BufferedReader(file);
        String line = null;
        List<String> rawGraph = new ArrayList<>();

        while((line = bufferedReader.readLine()) != null)
        {
            rawGraph.add(line);
        }
        Vertex[] graph = new Vertex[rawGraph.size()];
        for(int i = 0; i < rawGraph.size(); i++) {
            String[] temp = rawGraph.get(i).split(" ");
            int loc = Integer.parseInt(temp[0]);
            graph[loc] = new Vertex(loc);
        }
        for(int i = 0; i < rawGraph.size(); i++){
            String[] temp = rawGraph.get(i).split(" ");
            int start = Integer.parseInt(temp[1]);
            int end = Integer.parseInt(temp[2]);
            double dis = Double.parseDouble(temp[3]);
            graph[start].outEdges.add(new edge(end, dis));
            graph[end].inEdges.add(new edge(start, dis));
        }



        bidijstra.PreProcess process = new bidijstra.PreProcess();
        int[] nodeOrdering = process.processing(graph);
//        for(int i = 0; i < nodeOrdering.length; i++) {
//            System.out.println(nodeOrdering[i]);
//        }

        bidijstra.BidirectionalDijkstra bd = new bidijstra.BidirectionalDijkstra();
        System.out.println("The shortest path is " + bd.computeDist(graph, 5804, 5817, nodeOrdering));
        retrieve b = new retrieve();
        System.out.println("The nodes we go through are : ");
        b.retrieve(graph, 5804, 5817);



    }
}
