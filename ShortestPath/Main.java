package ShortestPath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Main {


    public Vertex[] CHread(String fileName) throws Exception {
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        List<String> rawGraph = new ArrayList<>();
        Vertex[] graph = new Vertex[17000];
        while (sc.hasNext()) {
            int tmp = sc.nextInt();
            int start = sc.nextInt();
            int end = sc.nextInt();
            double dis = sc.nextDouble();
            if (start < 17000 && end < 17000){
                graph[start] = new Vertex(start);
                graph[end] = new Vertex(end);
                graph[start].outEdges.add(new edge(end, dis));
                graph[end].inEdges.add(new edge(start, dis));
            }
        }



        sc.close();

        return graph;
    }

    public simBiDij.Vertex[] BiDiGraph(String fileName, int mode) throws Exception {
        File file = new File(fileName);
        Scanner sc = new Scanner(file);

        List<String> rawGraph = new ArrayList<>();
        simBiDij.Vertex[] graph = new simBiDij.Vertex[17000];
        while (sc.hasNext()) {
            int tmp = sc.nextInt();
            int start = sc.nextInt();
            int end = sc.nextInt();
            double dis = sc.nextDouble();


            if (start < 17000 && end < 17000) {
                graph[start] = new simBiDij().new Vertex(start);
                graph[end] = new simBiDij().new Vertex(end);
                if (mode == 0) {
                    graph[start].adjList.add(end);
                    graph[start].costList.add(dis);
                } else {
                    graph[end].adjList.add(start);
                    graph[end].costList.add(dis);
                }
            }
        }



        sc.close();


        return graph;
    }

    public static void main(String[] args) throws Exception{

//
        String file = "D:\\cs\\graph\\CA.txt";
//        FileWriter fw = new FileWriter("D:\\cs\\OLresults\\randomqueryCA1.txt");
//        FileWriter fw2 = new FileWriter("D:\\cs\\OLresults\\runtimeCA1.txt");
        try {
            Main main = new Main();
            CHMain CH = new CHMain();
            simBiDij BI = new simBiDij();
//            Vertex[] graph = main.CHread(file);
//
//
//            for(int i = 0; i < 500; i++) {
//                Random r = new Random();
//                int random1 = r.nextInt(graph.length);
//                int random2 = r.nextInt(graph.length);
//
//                double tmp = CH.CHCompute(graph, random1, random2, fw2);
//                String newLine = System.getProperty("line.separator");
//                if( tmp != -1 && tmp != 0) {
//                    fw.write(random1 + " " + random2 + newLine);
//
//                }
//
//            }
//            fw.close();
//            fw2.close();

            simBiDij.Vertex[] BIGraph = main.BiDiGraph(file, 0);
            simBiDij.Vertex[] BIRevGraph = main.BiDiGraph(file, 1);
            FileReader fr = new FileReader("D:\\cs\\OLresults\\randomquerycA1.txt");
            BufferedReader bf = new BufferedReader(fr);
            String line = null;
            FileWriter fw3 = new FileWriter("D:\\cs\\OLresults\\runtimeBiNA2.txt");
            String newLine = System.getProperty("line.separator");
            while ((line = bf.readLine()) != null) {
                String[] temp = line.split(" ");
                long startTimebi = System.nanoTime();
                BI.computeBiDi(BIGraph, BIRevGraph, Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
                long endTimebi = System.nanoTime();
                long totalTimebi = endTimebi - startTimebi;

                fw3.write(totalTimebi + newLine);

            }
            fw3.close();
            long memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            System.out.println(memory);
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}

