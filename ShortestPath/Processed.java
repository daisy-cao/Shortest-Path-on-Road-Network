package ShortestPath;

public class Processed {
    boolean forwProcessed; 	//is processed in forward search.
    boolean revProcessed;//is processed in backward search.
    int forwqueryId; 	//id for forward search.
    int revqueryId; 	//id for backward search.

    public Processed(){
        this.forwqueryId = -1;
        this.revqueryId = -1;
    }
}
