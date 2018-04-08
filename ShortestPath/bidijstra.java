package ShortestPath;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class bidijstra {

    private static class PQIMPcomparator implements Comparator<Vertex> {
        public int compare(Vertex node1, Vertex node2){
            if(node1.importance > node2.importance){
                return 1;
            }
            if(node1.importance < node2.importance){
                return -1;
            }
            return 0;
        }
    }

    private static class PriorityQueueComp implements Comparator<Vertex>{
        public int compare(Vertex node1,Vertex node2){
            if(node1.distance.distance > node2.distance.distance){
                return 1;
            }
            if(node1.distance.distance < node2.distance.distance){
                return -1;
            }
            return 0;
        }
    }

    static class PreProcess{
        Comparator<Vertex> comp = new PQIMPcomparator();
        PriorityQueue<Vertex> PQImp;  	//queue for importance parameter.

        Comparator<Vertex> PQcomp = new PriorityQueueComp();
        PriorityQueue<Vertex> queue;	//queue for distance parameter.


        //calculate initial importance for all vertices.
        private void computeImportance(Vertex [] graph){
            PQImp = new PriorityQueue<Vertex>(graph.length,comp);
            for(int i = 0; i < graph.length; i++){
                if(graph[i].inEdges != null && graph[i].outEdges != null ) {
                    graph[i].edgeDiff = (graph[i].inEdges.size() * graph[i].outEdges.size()) - graph[i].inEdges.size() - graph[i].outEdges.size();
                    graph[i].shortcutCover = graph[i].inEdges.size() + graph[i].outEdges.size();
                    graph[i].importance = graph[i].edgeDiff * 14 + graph[i].shortcutCover * 25 + graph[i].delNeighbors * 10;
                    PQImp.add(graph[i]);
                }else{
                    graph[i].edgeDiff = 0;
                    graph[i].shortcutCover = 0;
                    graph[i].importance = 0;
                    PQImp.add(graph[i]);
                }
            }
        }


        //compute importance for individual vertex while processing.
        private void computeImportancePro(Vertex [] graph, Vertex vertex){
            vertex.edgeDiff = (vertex.inEdges.size() * vertex.outEdges.size()) - vertex.inEdges.size() - vertex.outEdges.size();
            vertex.shortcutCover = vertex.inEdges.size() + vertex.outEdges.size();
            vertex.importance = vertex.edgeDiff*14 + vertex.shortcutCover*25 + vertex.delNeighbors*10;
        }


        //function that will pre-process the graph.
        private int [] preProcess(Vertex[] graph){
            int[] nodeOrdering = new int[graph.length];	//contains the vertices in the order they are contracted.
            int pos = 0; 				//stores the number of vertices that are contracted.

            while(PQImp.size()!=0){
                Vertex cur = PQImp.poll();
                computeImportancePro(graph,cur);	//recompute importance before contracting the vertex.

                //if the vertex's recomputed importance is still minimum then contract it.
                if(PQImp.size()!=0 && cur.importance > PQImp.peek().importance){
                    PQImp.add(cur);
                    continue;
                }

                nodeOrdering[pos] = cur.ID;
                cur.orderPos = pos;
                contractNode(graph, cur, pos);
                pos++;

            }
            return nodeOrdering;
        }

        //update the neighbors of the contracted vertex that this vertex is contracted.
        private void updateNeighbors(Vertex[] graph, Vertex vertex){
            for(int i = 0; i < vertex.inEdges.size();i++){
                edge temp = vertex.inEdges.get(i);
                graph[temp.id].delNeighbors++;
            }

            for(int i=0;i< vertex.outEdges.size();i++){
                edge temp = vertex.outEdges.get(i);
                graph[temp.id].delNeighbors++;
            }
        }

        private void contractNode(Vertex[] graph, Vertex vertex, int contractId){
            ArrayList<edge> inEdges = vertex.inEdges;
            ArrayList<edge> outEdges = vertex.outEdges;

            vertex.contracted = true;

            updateNeighbors(graph, vertex);	//update the given vertex's neighbors about that the given vertex is contracted.

            for(int i = 0;i < inEdges.size();i++){
                edge inVertex = inEdges.get(i);
                if(graph[inVertex.id].contracted){
                    continue;
                }
                //this code adds shortcuts.
                for(int j = 0 ; j < outEdges.size();j++){
                    edge outVertex = outEdges.get(j);
                    if(graph[outVertex.id].contracted){
                        continue;
                    }
                    dijkstra(graph, inVertex.id, contractId, outVertex.id);
                    if(graph[outVertex.id].distance.contractId == contractId){
                        outVertex.shortcut = true;
                        outVertex.nodes.add(contractId);
                        graph[inVertex.id].outEdges.add(outVertex);
                        inVertex.shortcut = true;
                        inVertex.nodes.add(contractId);
                        graph[outVertex.id].inEdges.add(inVertex);

                    }
                }
            }
        }

        private void dijkstra(Vertex[] graph, int sourceId, int contractId, int targetid){
            queue = new PriorityQueue<Vertex>(graph.length, PQcomp);
            graph[sourceId].distance.distance = 0;
            graph[sourceId].distance.contractId = contractId;
            graph[sourceId].distance.sourceId = sourceId;
            queue.add(graph[sourceId]);
            while(queue.size() != 0 && queue.peek().ID != targetid){
                Vertex cur = queue.poll();
                ArrayList<edge> neigh = graph[cur.ID].outEdges;
                for(int i = 0; i < neigh.size(); i++) {
                    int temp = neigh.get(i).id;
                    if(graph[temp].contracted){
                        continue;
                    }
                    if(graph[temp].distance.distance > graph[cur.ID].distance.distance + neigh.get(i).dis) {
                        graph[temp].distance.distance = graph[cur.ID].distance.distance + neigh.get(i).dis;
                        if(cur.ID == contractId) {
                            graph[temp].distance.contractId = contractId;
                        }
                        graph[temp].distance.sourceId = cur.ID;
                    }
                    queue.add(graph[temp]);
                }
            }
        }

        public int [] processing(Vertex [] graph){
            computeImportance(graph);		//find initial importance by traversing all vertices.
            int[] nodeOrdering = preProcess(graph);
            return nodeOrdering;
        }
    }

    public static class forwComparator implements Comparator<Vertex>{
        public int compare(Vertex vertex1, Vertex vertex2){
            if(vertex1.distance.queryDist > vertex2.distance.queryDist){
                return 1;
            }
            if(vertex1.distance.queryDist < vertex2.distance.queryDist){
                return -1;
            }
            return 0;
        }
    }



    //priorityQueue(min heap) for bidirectional dijkstra algorithms.(for backward search)
    public static class revComparator implements Comparator<Vertex>{
        public int compare(Vertex vertex1, Vertex vertex2){
            if( vertex1.distance.revDistance > vertex2.distance.revDistance){
                return 1;
            }
            if(vertex1.distance.revDistance < vertex2.distance.revDistance){
                return -1;
            }
            return 0;
        }
    }


    static class BidirectionalDijkstra{
        Comparator<Vertex> forwComp = new forwComparator();
        Comparator<Vertex> revComp = new revComparator();
        PriorityQueue<Vertex> forwQ;
        PriorityQueue<Vertex> revQ;

        //main function that will compute distances.
        public double computeDist(Vertex[] graph, int source, int target, int [] nodeOrdering){
            graph[source].distance.queryDist = 0;
            graph[source].distance.forwqueryId = source;
            graph[source].processed.forwqueryId = source;

            graph[target].distance.revDistance = 0;
            graph[target].distance.revqueryId = target;
            graph[target].processed.revqueryId = target;

            forwQ = new PriorityQueue<Vertex>(graph.length,forwComp);
            revQ = new PriorityQueue<Vertex>(graph.length,revComp);

            forwQ.add(graph[source]);
            revQ.add(graph[target]);

            double estimate = Double.MAX_VALUE;

            while(forwQ.size()!=0 || revQ.size()!=0){
                if(forwQ.size() != 0){
                    Vertex cur = forwQ.poll();
                    if(cur.distance.queryDist <= estimate){
                        relaxEdges(graph, cur.ID,"f", nodeOrdering);
                    }
                    if(cur.processed.revProcessed){
                        if(cur.distance.queryDist + cur.distance.revDistance < estimate){
                            estimate = cur.distance.queryDist + cur.distance.revDistance;
                        }
                    }
                }

                if(revQ.size()!=0){
                    Vertex vertex2 = revQ.poll();
                    if(vertex2.distance.revDistance <= estimate){
                        relaxEdges(graph,vertex2.ID,"r",nodeOrdering);
                    }
                    if(vertex2.processed.forwProcessed){
                        if(vertex2.distance.revDistance + vertex2.distance.queryDist < estimate){
                            estimate = vertex2.distance.queryDist + vertex2.distance.revDistance;
                        }
                    }
                }
            }

            if(estimate == Double.MAX_VALUE){
                return -1;
            }
            return estimate;
        }



        //function to relax edges.(according to the direction forward or backward)
        private void relaxEdges(Vertex [] graph, int vertex, String str,int [] nodeOrdering){
            if(str == "f"){
                ArrayList<edge> neigh = graph[vertex].outEdges;
                graph[vertex].processed.forwProcessed = true;

                for(int i = 0; i < neigh.size();i++){
                    edge temp = neigh.get(i);
                   // if(graph[vertex].orderPos < graph[temp.id].orderPos){
                        if(graph[temp.id].distance.queryDist > graph[vertex].distance.queryDist + temp.dis){
                            graph[temp.id].distance.forwqueryId = vertex;
                            graph[temp.id].distance.queryDist = graph[vertex].distance.queryDist + temp.dis;
                            forwQ.add(graph[temp.id]);
                     //   }
                    }
                }
            }

            else{
                ArrayList<edge> neigh = graph[vertex].inEdges;
                graph[vertex].processed.revProcessed = true;
                for(int i=0; i< neigh.size(); i++){
                    edge temp = neigh.get(i);
                    if(graph[vertex].orderPos < graph[temp.id].orderPos){
                        if(graph[vertex].distance.revqueryId != graph[temp.id].distance.revqueryId || graph[temp.id].distance.revDistance > graph[vertex].distance.revDistance + temp.dis){
                            graph[temp.id].distance.revqueryId = vertex;
                            graph[temp.id].distance.revDistance = graph[vertex].distance.revDistance + temp.dis;
                            revQ.add(graph[temp.id]);
                        }
                    }
                }
            }
        }

    }

}
