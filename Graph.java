import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Created by Jingtian on 9/11/16.
 */
public class Graph<V> {
    private Map<V,List<Node<V>>> adjList;
    private Set<V> vertices;
    private static final int DEFAULT_WEIGHT = Integer.MAX_VALUE;

    public Graph(){
        this.adjList = new HashMap<>();
        this.vertices = new HashSet<>();
    }

    public Map<V, List<Node<V>>> getGraph(){
        return this.adjList;
    }

    public boolean isEmpty(){
        return this.vertices.isEmpty();
    }

    public void addEdge(V src, V dest, int w){
        List<Node<V>> adjVertices = adjList.get(src);
        if(!vertices.contains(src)){
            vertices.add(src);
        }
        if (adjVertices == null || adjVertices.isEmpty()) {
            adjVertices = new ArrayList<Node<V>>();
            adjVertices.add(new Node<V>(dest,w));
        }else{
            adjVertices.add(new Node<V>(dest,w));
        }
        adjList.put(src,adjVertices);
    }

    public Set<V> getVertices() {
        //return Collections.unmodifiableSet(this.vertices);
        return this.vertices;
    }
}
