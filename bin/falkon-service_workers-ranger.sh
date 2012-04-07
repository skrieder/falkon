#!/bin/bash

#IP=`hostname -i`
#echo ${IP} >> /home/falkon/falkon/logs/provisioner/zoid-log.txt


 if [ -z "$7" ]; then 
              echo "usage: $0 <SERVICE_IP> <SERVICE_PORT1> <SERVICE_PORT2 <WORKERS_PER_NODE> <USER> <FALKON_JOB_ID> <FALKON_ROOT>"

              echo "command: $0"
              echo "FALKON_SERVICE_IP: $1"
              echo "TCPCORE_PORT1: $2"
              echo "TCPCORE_PORT2: $3"
              echo "NUM_WORKERS: $4"
              echo "USER_ID: $5"
              echo "FALKON_JOB_ID: $6"
              echo "FALKON_ROOT_PATH: $7"
              
              exit 1
          fi

    echo "reviewing the command and arguements: $1 $2 $3 $4 $5 $6 $7..." >> ${ERROR_LOG}

              echo "command: $0"
              echo "FALKON_SERVICE_IP: $1"
              echo "TCPCORE_PORT1: $2"
              echo "TCPCORE_PORT2: $3"
              echo "NUM_WORKERS: $4"
              echo "USER_ID: $5"
              echo "FALKON_JOB_ID: $6"
              echo "FALKON_ROOT_PATH: $7"

SERVICE_IP=$1
SERVICE_PORT1=$2
SERVICE_PORT2=$3
WORKERS_PER_NODE=$4
USER=$5
FALKON_JOB_ID=$6
FALKON_ROOT=$7

                       
#startup
#    if [ "${1}" -eq "1" ]; then
                            
#ERROR_LOG=$HOME/error.log
ERROR_LOG=/dev/null

    echo "starting falkon-service_workers-ranger.sh..." >> ${ERROR_LOG}
    #echo "reviewing the command and arguements: $0 $1 $2 $3 $4 $5 $6 $7..." >> ${ERROR_LOG}
    #tENV=`env`
    #echo "reviewing the envirnoment: $tENV..." >> ${ERROR_LOG}

#original=$ZOID_JOB_ARGS
#a0=${original%%:*}; rest=${original#*:}
#1=${rest%%:*}; rest=${rest#*:}
#2=${rest%%:*}; rest=${rest#*:}
#3=${rest%%:*}; rest=${rest#*:}
#4=${rest%%:*}; rest=${rest#*:}
#5=${rest%%:*}; rest=${rest#*:}
#6=${rest%%:*}; rest=${rest#*:}
#7=${rest%%:*};

    
    echo "reviewing the command and arguements: $1 $2 $3 $4 $5 $6 $7..." >> ${ERROR_LOG}
    
    
#FALKON_JOBID_HOME=/home/falkon/users/${USER}/${ZOID_JOB_ID}
FALKON_JOBID_HOME=${7}/users/${5}/${6}
FALKON_HOME=${7}/users/${5}/${6}
    echo "FALKON_JOBID_HOME: $FALKON_JOBID_HOME" >> ${ERROR_LOG}


if [ ! -d "${FALKON_JOBID_HOME}" ]; then
    echo "ERROR: invalid path ${FALKON_JOBID_HOME}... exiting" >> ${ERROR_LOG}
    exit 1
fi



    MACH_ID=`uname -n`
       #this should be done via some configuration parameter

   cd ${FALKON_JOBID_HOME}
   #source falkon.env.bgp-io

   echo "ION at ${MACH_ID} ==> ${FALKON_HOME}..."  >> ${ERROR_LOG}

    LOG_FILE_DEBUG="${FALKON_HOME}/logs/provisioner/STATUS_SERVICE_${MACH_ID}"
    DATE=`date`
    echo "${DATE} : falkon-service_workers-ranger : starting the Falkon service startup in the background..." >> ${LOG_FILE_DEBUG}


if [ ! -e "${FALKON_HOME}/bin/falkon-service-ranger.sh" ]; then
    echo "ERROR: invalid script ${FALKON_HOME}/bin/falkon-service-ranger.sh" >> ${ERROR_LOG}
    exit 1
fi

     #used to run from GPFS
    #${FALKON_JOBID_HOME}/bin/falkon-ion-start-blocking-zeptocn.sh ${FALKON_JOBID_HOME} &
    #used to run from RAM
    ${FALKON_JOBID_HOME}/bin/falkon-service-ranger-ram.sh ${FALKON_JOBID_HOME} &


    EXIT_CODE=$? 
    DATE=`date`

    echo "${DATE} : falkon-service_workers-ranger : completed the Falkon startup..." >> ${LOG_FILE_DEBUG}

   echo "Falkon service startup at ${MACH_ID} finished OK!"  >> ${ERROR_LOG}

                   
#exit ${EXIT_CODE}


#    fi


    echo "${DATE} : falkon-service_workers-ranger : sleeping for 30 sec to allow the service time to startup..." >> ${LOG_FILE_DEBUG}

    /bin/sleep 30                                          

    echo "${DATE} : falkon-service_workers-ranger : starting workers..." >> ${LOG_FILE_DEBUG}


 #start workers...
 ${FALKON_WORKER_HOME}/run.worker-c-ranger.sh ${SERVICE_IP} ${SERVICE_PORT1} ${SERVICE_PORT2} ${WORKERS_PER_NODE} ${USER} ${FALKON_JOB_ID} ${FALKON_ROOT}

    EXIT_CODE=$? 

    echo "${DATE} : falkon-service_workers-ranger : workers have shut down..." >> ${LOG_FILE_DEBUG}

    
exit ${EXIT_CODE}
    
