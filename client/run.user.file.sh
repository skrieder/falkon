#!/bin/bash

 if [ -z "$3" ]; then 
              echo "usage: $0 <GT4_IP> <GT4_PORT> <EXEC_FILE>"
              exit 1
          fi

if [ -z "${FALKON_CLIENT_HOME}" ]; then
    echo "ERROR: environment variable FALKON_CLIENT_HOME not defined"  1>&2
    exit 1
fi

if [ ! -d "${FALKON_CLIENT_HOME}" ]; then
    echo "ERROR: invalid FALKON_CLIENT_HOME set: $FALKON_CLIENT_HOME" 1>&2
    exit 1
fi
          
if [ -z "${GLOBUS_LOCATION}" ]; then
    echo "ERROR: environment variable GLOBUS_LOCATION not defined"  1>&2
    exit 1
fi

if [ ! -d "${GLOBUS_LOCATION}" ]; then
    echo "ERROR: invalid GLOBUS_LOCATION set: $GLOBUS_LOCATION" 1>&2
    exit 1
fi


#used for throttling            
MAX_CONCURENT_TASKS=50000
HOME_PATH=`pwd`
GenericPortalIP=$1
GenericPortalPORT=$2
EXEC_FILE=$3
NUM_EXECS=10000000
#NUM_EXECS=10
NUM_RESOURCES=1
#<<<<<<< .mine
#NUM_THREADS=10
#=======
NUM_THREADS=4
#>>>>>>> .r14
EXECS_PER_WS=1000
MONITOR_DELAY=1000
MAX_SUBMIT_THROUGHPUT=100000
MAX_WALL_TIME_MS=0
SECURITY_DESCRIPTION_FILE=${FALKON_CONFIG}/client-nosecurity-config.xml


EXP_START=`date +%Y.%m.%d_%H.%M.%S`
LOG_DIR=${FALKON_LOGS}/client/${EXP_START}
mkdir -p ${LOG_DIR} 
LOG_FILE=${LOG_DIR}/client-output.txt

#-XX:+UseConcMarkSweepGC                                     
java -Xms512M -Xmx512M -Xss128K -Djava.security.egd=file:///dev/urandom -DFALKON_LOGS=${LOG_DIR} -classpath $CLASSPATH:${FALKON_CLIENT_HOME} org/globus/GenericPortal/clients/GPService_instance/UserRun -serviceURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -MAX_EXECS_PER_WS ${EXECS_PER_WS} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -job_description ${EXEC_FILE} -num_execs ${NUM_EXECS} -num_threads ${NUM_RESOURCES} -num_submit_threads ${NUM_THREADS} -monitor ${MONITOR_DELAY} -locality 1 -max_concurent_tasks ${MAX_CONCURENT_TASKS} -max_submit_throughput ${MAX_SUBMIT_THROUGHPUT} -maxWallTimeMS ${MAX_WALL_TIME_MS} -taskPerfLog ${LOG_DIR}/client_taskPerf.txt


