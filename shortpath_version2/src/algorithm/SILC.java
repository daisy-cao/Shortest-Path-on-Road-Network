package algorithm;

import modeling.Edge;
import modeling.ShortestPath;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.*;

public class SILC<V,E extends Edge> implements ShortestPathStrategy<V,Edge> {

    Hashtable<V,Hashtable<V,HashSet<V>>> hashtable;
    private Graph<V, Edge> graph;
    private DijkstraShortestPath<V, Edge> dijkstra;

    public SILC(Graph<V,Edge> graph){
        this.graph = graph;
        dijkstra = new DijkstraShortestPath<V, Edge>(graph);
    }

    public void init(){
        hashtable = new Hashtable<>();
        Set<V> vSet = graph.vertexSet();
        //TODO directed graph
        for(V v : vSet){
            Hashtable<V, HashSet<V>> table = new Hashtable<V, HashSet<V>>();
            ShortestPathAlgorithm.SingleSourcePaths paths = dijkstra.getPaths(v);
            for(V w : vSet){
                if(w != v){
                    GraphPath<V,Edge> path = paths.getPath(w);
                    if(path == null){
                        continue;
                    }
                    List<V> list = path.getVertexList();
                    //v->w first node in shortest path
                    V firstV = list.get(1);
                    if(table.get(firstV) == null){
                        HashSet<V> set = new HashSet<>();
                        set.add(w);
                        table.put(firstV,set);
                    } else{
                        table.get(firstV).add(w);
                    }
                }
            }
            hashtable.put(v,table);
        }
    }

    public ShortestPath<V, Edge> getPath(V source, V sink){
        List<V> vList = getPathVertex(source, sink);
        List<Edge> eList = new ArrayList<Edge>();
        for(int i = 0; i < eList.size()-1; i++){
            Edge edge = graph.getEdge(vList.get(i), vList.get(i+1));
            eList.add(edge);
        }
        return new ShortestPath<V, Edge>(eList);
    }

    @Override
    public List<V> getPathVertex(V source, V sink) {
        if(source.equals(sink)){
        	    //System.out.println(source);
            List<V> path = new ArrayList<V>();
            path.add(source);
            //System.out.println(path);
            return path;
        } else {
            Hashtable<V, HashSet<V>> table = hashtable.get(source);
            for(V v : table.keySet()){
            	    //System.out.println(v);
                HashSet<V> set = table.get(v);
                //System.out.println(set);
                //System.out.println(sink);
                if(set.contains(sink)){
//                    System.out.println(v);
//                    System.out.println(sink);
                    List<V> list = getPathVertex(v,sink);
                    list.add(source);
                    return list;
                }
            }  
        }
        return null;
        
    }

    @Override
    public double getPathWeight(V source, V sink) {
        return getPath(source, sink).getWeight();
    }

}
