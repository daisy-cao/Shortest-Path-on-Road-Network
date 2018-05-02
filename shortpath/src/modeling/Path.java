package modeling;

import modeling.Edge;
import java.util.List;

public interface Path<V,E extends Edge> {

    List<V> getVertexList();
    List<E> getEdgeList();
    Double getWeight();
    Path<V, E> append(Path path) throws Exception;
}
