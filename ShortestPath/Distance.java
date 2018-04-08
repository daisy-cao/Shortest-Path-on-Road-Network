package ShortestPath;

public class Distance {
    int contractId;		//id for the vertex that is going to be contracted.
    int sourceId;           //it contains the id of vertex for which we will apply dijkstra while contracting.
    double distance; 		//stores the value of distance while contracting.

    //used in query time for bidirectional dijkstra algo
    int forwqueryId; 	//for forward search.
    int revqueryId;//for backward search.

    double queryDist; 	//for forward distance.
    double revDistance; 	//for backward distance.

    public Distance(){
        this.contractId = -1;
        this.sourceId = -1;

        this.forwqueryId = -1;
        this.revqueryId = -1;

        this.distance = Double.MAX_VALUE;

        this.revDistance = Double.MAX_VALUE;
        this.queryDist = Double.MAX_VALUE;
    }

}
