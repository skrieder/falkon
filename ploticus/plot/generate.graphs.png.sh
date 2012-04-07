#!/bin/bash 

 EXP_DESC=summary_graph
 echo "`date +%s`: generating ${EXP_DESC}..."
 pl ${EXP_DESC}.plot png -o ${EXP_DESC}.jpg -maxfields 10000000 -maxvector 1000000

 EXP_DESC=task_graph
 echo "`date +%s`: generating ${EXP_DESC}..."
 pl ${EXP_DESC}.plot png -o ${EXP_DESC}.jpg -maxfields 10000000 -maxvector 1000000

 EXP_DESC=executor_graph
 echo "`date +%s`: generating ${EXP_DESC}..."
 pl ${EXP_DESC}.plot png -o ${EXP_DESC}.jpg -maxfields 10000000 -maxvector 1000000


