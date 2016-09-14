#!/usr/bin/python
##  CSE6140 HW1
##  This assignment requires installation of networkx package if you want to make use of available graph data structures or you can write your own!!
##  Please feel free to modify this code or write your own

import networkx as nx
import time
import sys

def main():

    num_args = len(sys.argv)

    if num_args < 4:
        print "error: not enough input arguments"
        exit(1)

    graph_file = sys.argv[1]
    change_file = sys.argv[2]
    output_file = sys.argv[3]

    #Construct graph
    G = parseEdges(graph_file) #TODO: Write this method to read the graph file input

    start_MST = time.time() #time in seconds
    MSTweight = computeMST(G) #TODO: Write this method to return total weight of MST
    total_time = (time.time() - start_MST) * 1000 #to convert to milliseconds

    #Write initial MST weight and time to file
    output = open(output_file, 'w')
    output.write(str(MSTweight) + " " + str(total_time) + "\n")

    #Changes file
    with open(change_file, 'r') as changes:
        num_changes = changes.readline()

        for line in changes:
            #parse edge and weight
            edge_data = list(map(lambda x: int(x), line.split()))
            assert(len(edge_data) == 3)

            u,v,weight = edge_data[0], edge_data[1], edge_data[2]

            #call recomputeMST function
            start_recompute = time.time()
            new_weight = recomputeMST(u, v, weight, G) #TODO: Write this method to return the weight of the modified MST (note: this function should not just recompute the full MST)
            total_recompute = (time.time() - start_recompute) * 1000 # to convert to milliseconds

            #write new weight and time to output file
            output.write(str(new_weight) + " " + str(total_recompute) + "\n")

if __name__ == '__main__':
    # run the experiments
    main()
