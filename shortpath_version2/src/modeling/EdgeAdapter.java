package modeling;

import modeling.VertexAdapter;
import lombok.Getter;
import lombok.Setter;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class EdgeAdapter extends DefaultWeightedEdge implements Edge {

    private long id;
    private VertexAdapter source;
    private VertexAdapter target;
    private Double length;
    private short floor;
    private short flag;



    public void setSource(VertexAdapter source) {
        this.source = source;
    }

    public void setTarget(VertexAdapter target) {
        this.target = target;
    }
    
    public void setId(long id) {
    		this.id = id;
    }
    
    public void setLength(Double length) {
    		this.length = length;
    }
    
    public void setFloor(short floor) {
    		this.floor = floor;
    }
    
    public void setFlag(short flag) {
    		this.flag = flag;
    }

    public EdgeAdapter reverse(){
        EdgeAdapter reverseEdge = new EdgeAdapter();
        reverseEdge.setSource(target);
        reverseEdge.setTarget(source);
        reverseEdge.setId(id);
        reverseEdge.setLength(length);
        reverseEdge.setFloor(floor);
        reverseEdge.setFlag(flag);
        return reverseEdge;
    }

    @Override
    public String toString() {
        return "EdgeAdapter{" +
                "id=" + id +
                ", source=" + source.getId() +
                ", target=" + target.getId() +
                ", length=" + length +
                ", floor=" + floor +
                ", flag=" + flag + '\'' +
                '}';
    }

    public VertexAdapter getSource() {
        return source;
    }

    public VertexAdapter getTarget() {
        return target;
    }

    @Override
    public VertexAdapter getAnotherVertex(Object v) {
        if(source.equals(v)){
            return target;
        } else if(target.equals(v)){
            return source;
        } else {
            return null;
        }
    }

    @Override
    public List<VertexAdapter> getVertexs() {
        List<VertexAdapter> result = new ArrayList<VertexAdapter>(2);
        result.add(source);
        result.add(target);
        return result;
    }

    @Override
    public double getWeight(){
        return length;
    }

    @Override
    public Double getLength(){
        return length;
    }

    @Override
    public int compareTo(Object o) {
        Edge e = (Edge) o;
        if(getWeight() > e.getLength()){
            return 1;
        } else if(getWeight() == e.getLength()){
            return 0;
        } else {
            return -1;
        }
    }
}
