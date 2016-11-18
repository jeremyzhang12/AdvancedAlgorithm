import java.util.*;
import java.lang.Math;
import java.io.*;
public class Graph {
	float[][] graph;
	Vertex[] vset;
	public float[][] getGraph(String filename) throws FileNotFoundException, IOException{
		readFile(filename);
		return graph;
	}
	
	public Vertex[] getVertex(){
		return vset;
	}
	
	private float getDist(float x1, float y1, float x2, float y2){
		return (float) Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
	}
	
	public void readFile(String filename) throws FileNotFoundException, IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String[] cityName = br.readLine().split(" ");
		br.readLine();
		String[] dimension = br.readLine().split(" ");
		int d = Integer.parseInt(dimension[1]);
		br.readLine();
		br.readLine();
		graph = new float[d][d];
		vset = new Vertex[d]; 
		for(int i = 0; i < d; i++){
			String[] line = br.readLine().split(" ");
			int id = Integer.parseInt(line[0]) - 1;
			float x = Float.parseFloat(line[1]);
			float y = Float.parseFloat(line[2]);
			Vertex v = new Vertex(x,y,Float.POSITIVE_INFINITY,id);
			vset[i] = v;
		}
		for(int i = 0; i < d; i++){
			for(int j = i+1; j < d; j++){
				graph[i][j] = getDist(vset[i].x, vset[i].y, vset[j].x, vset[j].y);
				graph[j][i] = graph[i][j];
			}
		}
		//return graph;
	}
}
