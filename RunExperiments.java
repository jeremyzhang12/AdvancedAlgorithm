/*
CSE6140 HW1
This is an example of how your experiments should look like.
Feel free to use and modify the code below, or write your own experimental code, as long as it produces the desired output.
@author Jingtian Zhang
*/


import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class RunExperiments{
    // used as a storage for sorted edge
    public static Graph<Integer> G;
    public static PriorityQueue<Edge<Integer>> pq;
    public static Graph<Integer> mst;

    public static Graph<Integer> parseEdges(BufferedReader bf) throws IOException {
        Graph<Integer> g = new Graph<>();
        String[] first = bf.readLine().split(" ");
        int N = Integer.parseInt(first[0]);
        int E = Integer.parseInt(first[1]);
        for(int i = 0; i < E; i++){
            String[] temp = bf.readLine().split(" ");
            int u = Integer.parseInt(temp[0]);
            int v = Integer.parseInt(temp[1]);
            int weight = Integer.parseInt(temp[2]);
            g.addEdge(u,v,weight);
        }
        bf.close();
        return g;
    }

    public static void sortPQ(Set<Integer> s, Graph<Integer> g){
        pq.clear();
        for(Integer v : s){
            Map<Integer, Integer> m = g.getGraph().get(v);
            if(m != null) {
                for (Integer dest : m.keySet()) {
                    pq.add(new Edge<>(m.get(dest), v, dest));
                }
            }
        }
    }

    /**
     *
     * @param g Graph object, used to get whole set of vertices
     * @return MST cost
     */
    public static int computeMST(Graph<Integer> g){
        int cost = 0;
        HashSet<Integer> visited = new HashSet<>();
        Set<Integer> wholeset = g.getVertices();
        mst = new Graph<>();
        while(pq.size() > 0){
            Edge<Integer> e = pq.poll();
            int u = e.getStart();
            int v = e.getEnd();
            if(!visited.contains(u) || !visited.contains(v)){
                mst.addEdge(u,v,e.getWeight());
                cost += e.getWeight();
                if(!visited.contains(u)){
                    visited.add(u);
                }
                if(!visited.contains(v)){
                    visited.add(v);
                }
            }
        }
        return cost;
    }

    public static int recomputeMST(int u, int v, int weight, Graph<Integer> g){
        g.addEdge(u,v,weight);
        return computeMST(g);
    }
	public static void main(String[] args) throws IOException {
		if (args.length < 3) {
			System.err.println("Unexpected number of command line arguments");
			System.exit(1);
		}

        pq = new PriorityQueue<>(10, new Comparator<Edge<Integer>>() {
            @Override
            public int compare(Edge<Integer> e1, Edge<Integer> e2) {
                if (e1.getWeight() == e2.getWeight()) {
                    return e1.compareTo(e2);
                }else{
                    return e1.getWeight() < e2.getWeight()? -1 : 1;
                }
            }
        });

		String graph_file = args[0];
		String change_file = args[1];
		String output_file = args[2];

        PrintStream output = new PrintStream(output_file);
        FileReader inputFile = new FileReader(graph_file);
        BufferedReader bufferReader = new BufferedReader(inputFile);

		G = parseEdges(bufferReader);

        System.out.println(G.size()); //initially 64
        sortPQ(G.getVertices(), G);
        /*
        while(pq.size() > 0){
            Edge<Integer> e = pq.poll();
            System.out.println(e.getStart() + " " + e.getEnd()+" " + e.getWeight());
        }*/

		long startMST = System.nanoTime();
		int MSTweight = computeMST(G);
		long finishMST = System.nanoTime();

        System.out.println("Old MST cost: " + MSTweight);
		//Subtract the start time from the finish time to get the actual algorithm running time
		double MSTtotal = (finishMST - startMST);///1000000;

		//Write to output file the initial MST weight and time
		output.println("First MST" + Integer.toString(MSTweight) + " " + Double.toString(MSTtotal));

		//Iterate through changes file
		BufferedReader br = new BufferedReader(new FileReader(change_file));
		String line = br.readLine();
		String[] split = line.split(" ");
		int num_changes = Integer.parseInt(split[0]);
		int u, v, weight;

		while ((line = br.readLine()) != null) {
			split = line.split(" ");
			u = Integer.parseInt(split[0]);
			v = Integer.parseInt(split[1]);
			weight = Integer.parseInt(split[2]);
            G.addEdge(u,v,weight);
            sortPQ(G.getVertices(),G);
            //System.out.println("Graph size: " + G.size());
            //System.out.println("PQ size: " + pq.size());
			//Run your recomputeMST function to recalculate the new weight of the MST given the addition of this new edge
			//Note: you are responsible for maintaining the MST in order to update the cost without recalculating the entire MST
			long start_newMST = System.nanoTime();
			int newMST_weight = recomputeMST(u,v,weight,G);
			long finish_newMST = System.nanoTime();
            //System.out.println("new MST cost: " + newMST_weight);
			double newMST_total = (finish_newMST - start_newMST);///1000000;

			//Write new MST weight and time to output file
			output.println(Integer.toString(newMST_weight) + " " + Double.toString(newMST_total));
		}
        /* For check purpose
        sortPQ(G.getVertices(),G);
        while(pq.size() > 0){
            Edge<Integer> e = pq.poll();
            System.out.println(e.getStart() + " " + e.getEnd()+" " + e.getWeight());
        }*/
		output.close();
		br.close();
	}
}