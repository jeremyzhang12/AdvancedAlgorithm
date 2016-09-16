/*
CSE6140 HW1
This is an example of how your experiments should look like.
Feel free to use and modify the code below, or write your own experimental code, as long as it produces the desired output.
@author Jingtian Zhang
*/

//package code.RunExperiments;
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
    public static int size;

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
            int from = Math.min(u,v);
            int to = Math.max(u,v);
            g.addEdge(from,to,weight);
        }/*
        for(Integer i : g.getVertices()){
            Map<Integer, Integer> m = g.getGraph().get(i);
            if(m != null){
                for(Integer key : m.keySet()){
                    System.out.println(i + " " + key + " " + g.getGraph().get(i).get(key));
                }
            }

        }*/
        bf.close();
        size = g.getVertices().size();
        return g;
    }

    /**
     * Helper function for rebuild PriorityQueue storing edges based on graph(mst)
     * @param s vertices set of the graph(mst)
     * @param g graph(mst)
     */
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
     * function to calculate MST based on graph given
     * @param g Graph object, used to get whole set of vertices
     * @return MST cost
     */
    public static int computeMST(Graph<Integer> g){
        int cost = 0;
        mst = new Graph<>();
        //mset is a map contains hashsets to store traversed vertices
        HashMap<Integer,HashSet<Integer>> mset = new HashMap<>();
        //HashMap<Integer,Integer> m = new HashMap<>();
        while(mst.getVertices().size() < size){
            //System.out.println(m.size());
            Edge<Integer> e = pq.poll();
            int u = e.getStart();
            int v = e.getEnd();
            int weight = e.getWeight();
            //check if u,v linked to existing set:
            // Yes: 1. both in same existing set: discard;
            // 2. one in, increase set
            // 3. in two different sets, add weight.
            // No: create a new set.
            int uback = checkSet(mset,u);
            int vback = checkSet(mset,v);
            if(uback == -1 && vback == -1){
                int sid = mset.size();
                mset.put(sid,new HashSet<>());
                mset.get(sid).add(u);
                mset.get(sid).add(v);
                cost += weight;
                mst.addEdge(u,v,weight);
            }
            if(uback != vback){ //add edge to existing vertices
                if(uback == -1){
                    mset.get(vback).add(u);
                }else if(vback == -1){
                    mset.get(uback).add(v);
                }else{//union
                    mset.get(uback).addAll(mset.get(vback));
                    mset.get(vback).clear();
                }
                cost += weight;
                mst.addEdge(u,v,weight);
            }
        }
        return cost;
    }

    /**
     * helper function checking the specific element is in which set
     * @param mset HashMap containing the sets of vertices
     * @param e Specific element to be checked
     * @return key value which specific set it is in. -1 means not found in current sets.
     */
    public static int checkSet(HashMap<Integer,HashSet<Integer>> mset, int e){
        for(int i = 0; i < mset.size(); i++){
            if(mset.get(i).contains(e)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Helper function for checking if the mst size is okay
     * @param mset the hashmap containing the sets of vertices
     * @return return the current largest set size
     */
    public static int checkSize(HashMap<Integer, HashSet<Integer>> mset){
        int s = 0;
        for(Integer key : mset.keySet()) {
            if(mset.get(key).size() == size){
                return size;
            }else{
                //System.out.println(mset.get(key).size());
                s = Math.max(s,mset.get(key).size());
            }
        }
        return s;
    }

    /**
     * function to iteratively calculate mst based on previous mst
     * @param u node(Integer)
     * @param v node(Integer)
     * @param weight cost of edge
     * @return mst value
     */
    public static int recomputeMST(int u, int v, int weight){
        mst.addEdge(u,v,weight);
        sortPQ(mst.getVertices(),mst);
        return computeMST(mst);
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
        //Initialize pq
        sortPQ(G.getVertices(),G);
        System.out.println(pq.size());
        //For testing priorityqueue purpose
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
		double MSTtotal = (finishMST - startMST) / 1000000;

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
            //G.addEdge(u,v,weight);
            //sortPQ(G.getVertices(),G);
            //System.out.println("Graph size: " + G.size());
            //System.out.println("PQ size: " + pq.size());
			//Run your recomputeMST function to recalculate the new weight of the MST given the addition of this new edge
			//Note: you are responsible for maintaining the MST in order to update the cost without recalculating the entire MST
			long start_newMST = System.nanoTime();
			int newMST_weight = recomputeMST(u,v,weight);
			long finish_newMST = System.nanoTime();
            //System.out.println("new MST cost: " + newMST_weight);
			double newMST_total = (finish_newMST - start_newMST) / 1000000;

			//Write new MST weight and time to output file
			output.println(Integer.toString(newMST_weight) + " " + Double.toString(newMST_total));
		}
		output.close();
		br.close();
	}
}