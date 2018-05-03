package algorithm;

import modeling.Edge;
import modeling.ShortestPath;

import java.util.List;

public interface ShortestPathStrategy<V, E extends Edge> {
    ShortestPath getPath(V source, V sink);
    List<V> getPathVertex(V source, V sink);
    double getPathWeight(V source, V sink);
}