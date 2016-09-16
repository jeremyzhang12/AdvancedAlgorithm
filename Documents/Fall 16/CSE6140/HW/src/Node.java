import java.util.List;

/**
 * Created by Jingtian on 9/11/16.
 */
public class Node<V> {
    private V name;
    private int weight;

    public Node(V name, int weight){
        this.name = name;
        this.weight = weight;
    }

    public V getName(){
        return name;
    }

    public int getWeight(){
        return weight;
    }

    @Override
    public int hashCode(){
        return this.getName().hashCode();
    }

    @Override
    public String toString(){
        return "(" + this.weight + ")" + this.name;
    }

}
