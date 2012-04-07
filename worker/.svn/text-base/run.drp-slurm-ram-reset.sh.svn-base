#!/bin/bash


 if [ -z "$2" ]; then 
              echo "usage: $0 <NumWorkers> <MaxTimeMin>"
              echo "usage: $0 971 1"
              exit
          fi


NUM_WORKERS=$1
MAX_TIME_MIN=$2

RAM_PATH="/tmp"
DIR_RAM="falkon-worker-cache"
START_LOCK="falkon-worker-cache-lock-started"
END_LOCK="falkon-worker-cache-lock-finished"

srun -p scx --job-name=Falkon -t ${MAX_TIME_MIN} -n ${NUM_WORKERS} --cpus-per-task=1 --ntasks-per-node=1 -q -D /home/iraicu/java/svn/falkon/worker -l rm -rf ${RAM_PATH}/${DIR_RAM} ${RAM_PATH}/${END_LOCK} ${RAM_PATH}/${START_LOCK}


