package ShortestPath;

import java.util.ArrayList;

public class edge {
    int id;
    double dis;
    boolean shortcut;
    ArrayList<Integer> nodes;
    public edge(){
        id = -1;
        dis = Double.MAX_VALUE;
        shortcut = false;
        nodes = null;
    }
    public edge(int id, double dis){
        this.id = id;
        this.dis = dis;
    }
}
