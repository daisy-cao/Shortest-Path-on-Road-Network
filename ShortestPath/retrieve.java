package ShortestPath;

public class retrieve {
    public void retrieve(Vertex[] graph, int sourceId, int targetId) {
        int curId = targetId;
        while(curId != sourceId) {
            System.out.println(curId + " ");
            relax(graph, curId, graph[curId].distance.forwqueryId);
            curId = graph[curId].distance.forwqueryId;
        }

    }
    private void relax(Vertex[] graph, int cur, int prev) {
        for(edge nei : graph[cur].outEdges) {
            if(nei.id != prev) {
                continue;
            }else{
                if(nei.shortcut) {
                    for(int id : nei.nodes) {
                        System.out.print(id + " ");
                        relax(graph, id, prev);
                    }
                }else{
                    System.out.print(nei.id + " ");
                }
            }
        }

    }
}
