#!/bin/bash


 if [ -z "$7" ]; then 
              echo "usage: $0 <QueueName> <ProfileName> <NumNodes> <MaxTimeMin> <ServiceIP> <ServicePort1> <ServicePort2>"
              echo "usage: $0 prod falkon 1024 60 192.168.1.254 55000 55001"
              exit
          fi


QUEUE_NAME=$1
PROFILE_NAME=$2
NUM_NODES=$3
let NUM_ION=NUM_NODES/64
MAX_TIME_MIN=$4
SERVICE_IP=$5
SERVICE_PORT1=$6
SERVICE_PORT2=$7
WORKERS_PER_NODE=4
           
                   
cp ${FALKON_CONFIG}/Client-service-URIs.config2 ${FALKON_CONFIG}/Client-service-URIs.config

cqsub -q ${QUEUE_NAME} -k ${PROFILE_NAME} -C ${HOME} -t ${MAX_TIME_MIN} -n ${NUM_NODES} -e LD_LIBRARY_PATH=/lib:/fuse/lib:/fuse/usr/lib /bgsys/linux/1.2.020080512/bin/bash /fuse/${FALKON_WORKER_HOME}/run.worker-c-bgp.sh ${SERVICE_IP} ${SERVICE_PORT1} ${SERVICE_PORT2} ${WORKERS_PER_NODE}

falkon-wait-for-allocation.sh ${FALKON_CONFIG}/Client-service-URIs.config ${NUM_ION}
