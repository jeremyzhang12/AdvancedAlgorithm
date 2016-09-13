#!/bin/bash
javac RunExperiments.java Node.java Graph.java Edge.java
#java RunExperiments "rmat0406.gr" "rmat0406.extra" "output.txt"
graphFiles=`ls ./data/ | grep .gr`
out=output
echo $out
for graph in $graphFiles

do
	filename=`echo $graph | cut -d'.' -f1`
	java RunExperiments "./data/$graph" "./data/$filename"."extra" "$out""$filename"

	#You can change the following line to use your code, then use this file to run all of your experiments. For example, you can execute the python code with:
	#python src/RunExperiments.py ./data/$graph ./data/$filename.extra ./results/$filename_output.txt

done
