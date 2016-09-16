/**
 * Created by Jingtian on 9/12/16.
 */
public class Edge<V> implements Comparable<Edge<V>>{
    private int weight;
    private V start;
    private V end;
    private String begin = String.valueOf(start);
    private String dest = String.valueOf(end);

    public Edge(int weight, V start, V end) {
        this.weight = weight;
        this.start = start;
        this.end = end;
    }

    public String getBegin(){
        return begin;
    }

    public String getDest(){
        return dest;
    }
    @Override
    public int compareTo(Edge<V> e){
        if(begin.equals(e.begin)){
            return dest.compareTo(e.dest);
        }else{
            return begin.compareTo(e.begin);
        }
    }

    public int getWeight() {
        return weight;
    }

    public V getStart() {
        return start;
    }

    public V getEnd() {
        return end;
    }

    public int hashCode(){
        return this.start.hashCode();
    }
}
