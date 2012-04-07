#!/bin/bash


 if [ -z "$2" ]; then 
              echo "usage: $0 <NumWorkers> <MaxTimeMin>"
              echo "usage: $0 5790 60"
              exit
          fi


NUM_WORKERS=$1
MAX_TIME_MIN=$2

           

srun -p scx-comp --job-name=Falkon -t ${MAX_TIME_MIN} -n ${NUM_WORKERS} --cpus-per-task=1 --ntasks-per-node=6 -q -D /home/iraicu/falkon/worker -l ./run.worker-c.sh 0 0 1                

