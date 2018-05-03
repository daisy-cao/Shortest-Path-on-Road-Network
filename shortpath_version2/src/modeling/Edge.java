package modeling;

import java.io.Serializable;
import java.util.List;

public interface Edge<V> extends Comparable, Serializable{

    V getAnotherVertex(V v);

    List<V> getVertexs();

    V getSource();

    V getTarget();

    Double getLength();
}
