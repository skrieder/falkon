#!/bin/bash

#source /home/iraicu/.bashrc

#ERROR_LOG=${FALKON_ROOT_PATH}/users/error.log
#ERROR_LOG=/home/falkon/users/error-workers.log
ERROR_LOG=/dev/null
                    echo "worker started..." >> ${ERROR_LOG}

           
 if [ -z "$7" ]; then 
              echo "usage: $0 <FALKON_IP> <TCPCORE_PORT1> <TCPCORE_PORT2> <NUM_WORKERS> <USER_ID> <FALKON_JOB_ID> <FALKON_ROOT_PATH>"
              echo "usage: $0 <FALKON_IP> <TCPCORE_PORT1> <TCPCORE_PORT2> <NUM_WORKERS> <USER_ID> <FALKON_JOB_ID> <FALKON_ROOT_PATH>" >> ${ERROR_LOG}
              echo "$0 127.0.0.1 55000 55001 16 iraicu 0001"
              echo "$0 127.0.0.1 55000 55001 16 iraicu 0001" >> ${ERROR_LOG}
              echo "command: $0"
              echo "FALKON_SERVICE_IP: $1"
              echo "TCPCORE_PORT1: $2"
              echo "TCPCORE_PORT2: $3"
              echo "NUM_WORKERS: $4"
              echo "USER_ID: $5"
              echo "FALKON_JOB_ID: $6"
              echo "FALKON_ROOT_PATH: $7"
              exit 2
          fi

FALKON_SERVICE_IP=$1
TCPCORE_PORT1=$2
TCPCORE_PORT2=$3
NUM_WORKERS=$4
USER_ID=$5
FALKON_JOB_ID=$6
FALKON_ROOT_PATH=$7

echo "run.worker-c-ranger.sh: $FALKON_SERVICE_IP $TCPCORE_PORT1 $TCPCORE_PORT2 $NUM_WORKERS $USER_ID $FALKON_JOB_ID $FALKON_ROOT_PATH"


         
                  #ln -s /fuse/gpfs1 /gpfs1
                  #FALKON_HOME=/gpfs1/falkon/falkon
                  #HOME=/gpfs1/falkon



                  #echo "Worker for job id ${ZOID_JOB_ID} starting..." >> ${ERROR_LOG}

                  FALKON_HOME=${FALKON_ROOT_PATH}/users/${USER_ID}/${FALKON_JOB_ID}

                  echo "FALKON_HOME = ${FALKON_HOME}" >> ${ERROR_LOG}

                  #FALKON_HOME=/home/falkon/falkon
                  
                  HOME=${FALKON_ROOT_PATH}
                  echo "HOME = ${HOME}" >> ${ERROR_LOG}
                  #HOME=${FALKON_HOME}

                if [ -z "${FALKON_HOME}" ]; then
                    echo "ERROR: environment variable FALKON_HOME not defined"
                    echo "ERROR: environment variable FALKON_HOME not defined" >> ${ERROR_LOG}
                    exit 1
                fi

                if [ ! -d "${FALKON_HOME}" ]; then
                    echo "ERROR: invalid FALKON_HOME set: $FALKON_HOME"
                    echo "ERROR: invalid FALKON_HOME set: $FALKON_HOME" >> ${ERROR_LOG}
                    exit 1
                fi

cd ${FALKON_HOME}                     
source falkon.env.ranger
          



HOME_PATH=`pwd`
#export MACH_ID=`uname -i`
#RAND_ID2=`grep BG_RANK /proc/personality.sh`

#a0=${RAND_ID2%%=*}; rest=${RAND_ID2#*=}
#RAND_ID=${rest%%=*};

#PLATFORM=`uname -m`



EXP_START=`date +%Y.%m.%d_%H.%M.%S`

for ((b=1; b < ${NUM_WORKERS} ; b++))
do
            #LOG_FILE=${FALKON_LOGS}/worker/${RAND_ID}.${b}.${EXP_START}.txt
            LOG_FILE=/dev/null

            #${FALKON_WORKER_HOME}/src-c/BGexec_${PLATFORM} ${FALKON_SERVICE_IP} ${TCPCORE_PORT1} ${TCPCORE_PORT2} -perf >> "${LOG_FILE}" 2>&1 &
            #echo "starting .... ${FALKON_WORKER_HOME}/src-c/BGexec ${FALKON_SERVICE_IP} ${TCPCORE_PORT1} ${TCPCORE_PORT2} -perf"
            echo "starting worker ${b}..." >> ${ERROR_LOG}
            #${FALKON_WORKER_HOME}/src-c/BGexec ${FALKON_SERVICE_IP} ${TCPCORE_PORT1} ${TCPCORE_PORT2} -debug >> ${LOG_FILE} 2>&1 &
            ${FALKON_WORKER_HOME}/src-c/BGexec ${FALKON_SERVICE_IP} ${TCPCORE_PORT1} ${TCPCORE_PORT2} -debug &
            #${HOME_PATH}/src-c/BGexec_${PLATFORM} ${FALKON_SERVICE_IP} 55000 55001 -debug	
/bin/sleep 1
done           

            #LOG_FILE=${FALKON_LOGS}/worker/${RAND_ID}.${NUM_WORKERS}.${EXP_START}.txt
            LOG_FILE=/dev/null
            echo "starting worker ${NUM_WORKERS}..." >> ${ERROR_LOG}

             #${FALKON_WORKER_HOME}/src-c/BGexec_${PLATFORM} ${FALKON_SERVICE_IP} ${TCPCORE_PORT1} ${TCPCORE_PORT2} -perf >> "${LOG_FILE}" 2>&1
            #echo "starting .... ${FALKON_WORKER_HOME}/src-c/BGexec ${FALKON_SERVICE_IP} ${TCPCORE_PORT1} ${TCPCORE_PORT2} -perf"
             ${FALKON_WORKER_HOME}/src-c/BGexec ${FALKON_SERVICE_IP} ${TCPCORE_PORT1} ${TCPCORE_PORT2} -debug
             #${FALKON_WORKER_HOME}/src-c/BGexec ${FALKON_SERVICE_IP} ${TCPCORE_PORT1} ${TCPCORE_PORT2} -debug >> ${LOG_FILE} 2>&1

echo "run.worker-c-ranger.sh finished, exit code $?"



 echo "finished!" >> ${ERROR_LOG}

