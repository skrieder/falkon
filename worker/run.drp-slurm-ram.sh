#!/bin/bash


 if [ -z "$2" ]; then 
              echo "usage: $0 <NumWorkers> <MaxTimeMin>"
              echo "usage: $0 1 6 60"
              exit
          fi


NUM_WORKERS=$1
MAX_TIME_MIN=$2

           

srun -p scx --job-name=Falkon -t ${MAX_TIME_MIN} -n ${NUM_WORKERS} --cpus-per-task=1 --ntasks-per-node=6 -q -D /home/iraicu/java/svn/falkon/worker -l ./run.worker-c-ram.sh 0 0 1                

