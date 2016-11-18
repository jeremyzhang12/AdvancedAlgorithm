import java.util.*;
public class NearestNeighbor{
	public float cost;
	public int[] path;
	public Set<Integer> vset;
	public int[] getPath(float[][] table){
		vset = new HashSet<>();
		path = new int[table.length];
		int start = 0;
		cost = 0;
		vset.add(start);
		while(vset.size() != table.length){
			int v = findNearest(table, start, vset);
			path[start] = v;
			cost += table[start][v];
			start = v;
			vset.add(v);
		}
		return path;
	}
	
	public int findNearest(float[][] table, int u, Set<Integer> vset){
		float temp = Float.POSITIVE_INFINITY;
		int res = 0;
		for(int i = 0; i < table[u].length; i++){
			//if(i == u) continue;
			if(table[u][i] < temp && !vset.contains(i)){
				//System.out.println(i + " updated");
				temp = table[u][i];
				res = i;
			}
		}
		return res;
	}
	
	public float getCost(){
		return this.cost;
	}
	
	public void printPath(int[] nearestPath){
		int count = 0, start = 0;
		while(count != nearestPath.length){
			System.out.println("from " + start + " to " + nearestPath[start]);
			start = nearestPath[start];
			count++;
		}
	}
}
