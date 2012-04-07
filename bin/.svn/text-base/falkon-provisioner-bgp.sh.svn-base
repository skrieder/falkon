#!/bin/bash


 if [ -z "$2" ]; then 
              echo "usage: $0 <NumNodes> <MaxTimeMin>"
              echo "usage: $0 1024 60"
              exit
          fi


QUEUE_NAME="prod"
PROFILE_NAME="falkon"
NUM_NODES=$1
let NUM_ION=NUM_NODES/64
MAX_TIME_MIN=$2
SERVICE_IP="192.168.1.254"
SERVICE_PORT1=55000
SERVICE_PORT2=55001
WORKERS_PER_NODE=4

cp ${FALKON_CONFIG}/Client-service-URIs.config2 ${FALKON_CONFIG}/Client-service-URIs.config

cqsub -q ${QUEUE_NAME} -k ${PROFILE_NAME} -C ${HOME} -t ${MAX_TIME_MIN} -n ${NUM_NODES} -e LD_LIBRARY_PATH=/lib:/fuse/lib:/fuse/usr/lib /bgsys/linux/1.2.020080512/bin/bash ${FALKON_WORKER_HOME}/run.worker-c-bgp.sh ${SERVICE_IP} ${SERVICE_PORT1} ${SERVICE_PORT2} ${WORKERS_PER_NODE}

falkon-wait-for-allocation.sh ${FALKON_CONFIG}/Client-service-URIs.config ${NUM_ION}
