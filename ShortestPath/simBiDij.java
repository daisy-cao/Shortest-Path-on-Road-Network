package ShortestPath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class simBiDij {

    //class Vertex of Graph.
    class Vertex {
        int vertexNum;                  //int vertexNum
        ArrayList<Integer> adjList;     //list of adjacent vertices.
        ArrayList<Double> costList;    //list of cost or distance of adjacent vertices.

        int queuePos;                   //pos of vertex in the priorityqueue.
        double dist;                      //distance from start vetex.
        boolean processed;              //is processed while traversing the graph.

        public Vertex() {
        }


        //Vertex Constructor.
        public Vertex(int vertexNum) {
            this.vertexNum = vertexNum;
            this.adjList = new ArrayList<Integer>();
            this.costList = new ArrayList<Double>();
        }


        //function to create the graph and reverse graph. forwPriorityQ for graph and revPriorityQ for reverse graph.
        public void createGraph(Vertex[] graph, Vertex[] reverseGraph, int[] forwPriorityQ, int[] revPriorityQ) {
            for (int i = 0; i < graph.length; i++) {
                graph[i].queuePos = i;
                graph[i].processed = false;
                graph[i].dist = Double.MAX_VALUE;

                reverseGraph[i].queuePos = i;
                reverseGraph[i].processed = false;
                reverseGraph[i].dist = Double.MAX_VALUE;

                forwPriorityQ[i] = i;
                revPriorityQ[i] = i;
            }
        }
    }


    //Implementing PrioirtyQueue data structure by myself using min_heap property.
    class PriorityQueue {

        //function to swap elements in the PriorityQueue
        public void swap(Vertex[] graph, int[] priorityQ, int index1, int index2) {
            int temp = priorityQ[index1];

            priorityQ[index1] = priorityQ[index2];
            graph[priorityQ[index2]].queuePos = index1;

            priorityQ[index2] = temp;
            graph[temp].queuePos = index2;
        }

        //function to swap start vertex and first vertex in the priorityQ.
        public void makeQueue(Vertex[] graph, int[] forwpriorityQ, int source, int target) {
            swap(graph, forwpriorityQ, 0, source);
        }


        //function to extract the min value from the PriorityQueue
        public int extractMin(Vertex[] graph, int[] priorityQ, int extractNum) {
            int vertex = priorityQ[0];
            int size = priorityQ.length - 1 - extractNum;
            swap(graph, priorityQ, 0, size);
            siftDown(0, graph, priorityQ, size);
            return vertex;
        }

        //function to siftdown the element at the given index in the PriorityQueue.
        public void siftDown(int index, Vertex[] graph, int[] priorityQ, int size) {
            int min = index;
            if (2 * index + 1 < size && graph[priorityQ[index]].dist > graph[priorityQ[2 * index + 1]].dist) {
                min = 2 * index + 1;
            }
            if (2 * index + 2 < size && graph[priorityQ[min]].dist > graph[priorityQ[2 * index + 2]].dist) {
                min = 2 * index + 2;
            }
            if (min != index) {
                swap(graph, priorityQ, min, index);
                siftDown(min, graph, priorityQ, size);
            }
        }

        //function to change priority of an element.(priority can only be decreased.)
        public void changePriority(Vertex[] graph, int[] priorityQ, int index) {
            if ((index - 1) / 2 > -1 && graph[priorityQ[index]].dist < graph[priorityQ[(index - 1) / 2]].dist) {
                swap(graph, priorityQ, index, (index - 1) / 2);
                changePriority(graph, priorityQ, (index - 1) / 2);
            }
        }
    }

    //function to relax edges i.e traverse only the adjacent vertices of the given vertex.
    private void relaxEdges(Vertex[] graph, int vertex, int[] priorityQ, PriorityQueue queue) {
        ArrayList<Integer> vertexList = graph[vertex].adjList;   //get the adjacent vertices list.
        ArrayList<Double> costList = graph[vertex].costList;    //get the cost list of adjacent vertices.
        graph[vertex].processed = true;             //mark processed true.

        for (int i = 0; i < vertexList.size(); i++) {
            int temp = vertexList.get(i);
            double cost = costList.get(i);

            if (graph[temp].dist > graph[vertex].dist + cost) {
                graph[temp].dist = graph[vertex].dist + cost;
                queue.changePriority(graph, priorityQ, graph[temp].queuePos);
            }
        }
    }


    //function to compute distance between start vertex s and target vertex t.
    public double computeBiDi(Vertex[] graph, Vertex[] reverseGraph, int start, int end){

        //create two PriorityQueues forwQ for forward graph and revQ for reverse graph.
        PriorityQueue queue = new PriorityQueue();
        int[] forwPriorityQ = new int[graph.length];  //for forward propagation.
        int[] revPriorityQ = new int[graph.length];   //for reverse propagation.

        //create graph.
        Vertex vertex = new Vertex();
        vertex.createGraph(graph, reverseGraph, forwPriorityQ, revPriorityQ);

        //dist of s from s is 0.
        //in rev graph dist of t from t is 0.
        graph[start].dist = 0;
        reverseGraph[end].dist = 0;
        queue.makeQueue(graph, forwPriorityQ, start, end);
        queue.makeQueue(reverseGraph, revPriorityQ, end, start);

        //store the processed vertices while traversing.
        ArrayList<Integer> forgraphprocessedVertices = new ArrayList<Integer>();  //for forward propagation.
        ArrayList<Integer> revgraphprocessedVertices = new ArrayList<Integer>();  //for reverse propagation.


        for (int i = 0; i < graph.length; i++) {

            //extract the vertex with min dist from forwQ.
            int vertex1 = queue.extractMin(graph, forwPriorityQ, i);
            if (graph[vertex1].dist == Integer.MAX_VALUE) {
                continue;
            }

            //relax the edges of the extracted vertex.
            relaxEdges(graph, vertex1, forwPriorityQ, queue);

            //store into the processed vertices list.
            forgraphprocessedVertices.add(vertex1);

            //check if extratced vertex also processed in the reverse graph. If yes find the shortest distance.
            if (reverseGraph[vertex1].processed) {
                return shortestPath(graph, reverseGraph, forgraphprocessedVertices, revgraphprocessedVertices);
            }


            //extract the vertex with min dist from revQ.
            int revVertex = queue.extractMin(reverseGraph, revPriorityQ, i);
            if (reverseGraph[revVertex].dist == Integer.MAX_VALUE) {
                continue;
            }

            //relax the edges of the extracted vertex.
            relaxEdges(reverseGraph, revVertex, revPriorityQ, queue);

            //store in the processed vertices list of reverse graph.
            revgraphprocessedVertices.add(revVertex);

            //check if extracted vertex is also processed in the forward graph. If yes find the shortest distance.
            if (graph[revVertex].processed) {
                return shortestPath(graph, reverseGraph, forgraphprocessedVertices, revgraphprocessedVertices);
            }

        }

        //if no path between s and t.
        return -1;
    }


    //function to find the shortest distance from the stored processed vertices of both forward and reverse graph.
    private double shortestPath(Vertex[] graph, Vertex[] reverseGraph, ArrayList<Integer> forgraphprocessedVertices, ArrayList<Integer> revgraphprocessedVertices) {
        double distance = Double.MAX_VALUE;

        //process the forward list.
        for (int i = 0; i < forgraphprocessedVertices.size(); i++) {
            int vertex = forgraphprocessedVertices.get(i);
            if (reverseGraph[vertex].dist + graph[vertex].dist >= Double.MAX_VALUE) {
                continue;
            }
            double tempdist = graph[vertex].dist + reverseGraph[vertex].dist;
            if (distance > tempdist) {
                distance = tempdist;
            }
        }

        //process the reverse list.
        for (int i = 0; i < revgraphprocessedVertices.size(); i++) {
            int vertex = revgraphprocessedVertices.get(i);
            if (reverseGraph[vertex].dist + graph[vertex].dist >= Integer.MAX_VALUE) {
                continue;
            }
            double tempdist = reverseGraph[vertex].dist + graph[vertex].dist;
            if (distance > tempdist) {
                distance = tempdist;
            }

        }
        return distance;
    }



}

