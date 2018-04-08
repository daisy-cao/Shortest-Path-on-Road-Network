package ShortestPath;

import java.util.ArrayList;

public class Vertex {
    int ID;			//id of the vertex.
    ArrayList<edge> inEdges; 	//list of incoming edges to this vertex
    ArrayList<edge> outEdges; 	//list of outgoing edges from this vertex.


    int orderPos; 			//position of vertex in nodeOrderingQueue.

    boolean contracted; 		//to check if vertex is contracted

    Distance distance;
    Processed processed;

    //parameters for computing importance according to which we will contract the vertices. Vertex wih least importance wil be contracted first.
    int edgeDiff; 			//egdediff = sE - inE - outE. (sE=inE*outE , i.e number of shortcuts that we may have to add.)
    long delNeighbors; 		//number of contracted neighbors.
    int shortcutCover; 		//number of shortcuts to be introduced if this vertex is contracted.

    long importance; 		//total importance = edgediff + shortcutcover + delneighbors.

    public Vertex(){
    }

    public Vertex(int id){
        this.ID = id;
        this.inEdges = new ArrayList<edge>();
        this.outEdges = new ArrayList<edge>();
        this.distance = new Distance();
        this.processed = new Processed();
        this.delNeighbors = 0;
        this.contracted=false;
    }

}
