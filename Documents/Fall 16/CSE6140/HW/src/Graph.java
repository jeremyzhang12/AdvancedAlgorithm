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
public class Graph<V>{
    private Map<V,Map<V,Integer>> graph;
    private Set<V> vertices;
    private int size;
    private static final int DEFAULT_WEIGHT = Integer.MAX_VALUE;

    public Graph(){
        this.graph = new HashMap<>();
        this.vertices = new HashSet<>();
        size = 0;
    }

    public Map<V, Map<V,Integer>> getGraph(){
        return this.graph;
    }

    public boolean isEmpty(){
        return this.vertices.isEmpty();
    }

    public void addEdge(V src, V dest, Integer w){
        Map<V,Integer> adjList = graph.get(src);

        if (adjList == null) {
            adjList = new HashMap<>();
        }
        if(adjList.containsKey(dest)){
            if(w < adjList.get(dest)){
                adjList.put(dest,w);
            }
        }else{
            adjList.put(dest,w);
            vertices.add(dest);
            vertices.add(src);
            size++;
        }
        graph.put(src, adjList);
    }

    public Set<V> getVertices() {
        return Collections.unmodifiableSet(this.vertices);
        //return this.vertices;
    }

    public int size(){
        return size;
    }
}
