#!/bin/bash

 if [ -z "${8}" ]; then 
              echo "usage: $0 <GT4_IP> <GT4_PORT> <STACKS_FILE> <NUM_STACKS> <NUM_THREADS> <HEIGHT> <WIDTH> <RESULT_FILE>"
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
MAX_CONCURENT_TASKS=100000
GenericPortalIP=$1
GenericPortalPORT=$2
EXEC_FILE=$3
NUM_EXECS=$4
NUM_RESOURCES=1
NUM_THREADS=$5
EXECS_PER_WS=1000
MONITOR_DELAY=1000
MAX_SUBMIT_THROUGHPUT=0
MAX_WALL_TIME_MS=0
SECURITY_DESCRIPTION_FILE=${FALKON_CONFIG}/client-nosecurity-config.xml

SDSS_INDEX_FILE=${FALKON_HOME}/AstroPortal/SDSS/index-SDSS-DR5.txt
#SDSS_INDEX_FILE=${FALKON_HOME}/AstroPortal/SDSS/index-SDSS-DR5-fit.txt
REMOTE_SCRATCH=/disks/scratchgpfs1/iraicu/sdss.scratch/

LOCALITY=1


EXP_START=`date +%Y.%m.%d_%H.%M.%S`
LOG_DIR=${FALKON_LOGS}/client/${EXP_START}
mkdir -p ${LOG_DIR} 
LOG_FILE=${LOG_DIR}/client-output.txt


                                    
#java -XX:+UseConcMarkSweepGC -Xms256M -Xmx256M -Xss128K -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/UserRun -serviceURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -MAX_EXECS_PER_WS 100 -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -job_description ${EXEC_FILE} -num_execs ${NUM_EXECS} -num_threads ${NUM_THREADS} -monitor 1000
#java -XX:+UseConcMarkSweepGC -Xms1536M -Xmx1536M -Xss1M -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/UserRun -serviceURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -MAX_EXECS_PER_WS 100 -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -stack_description ${EXEC_FILE} -num_execs ${NUM_EXECS} -num_threads ${NUM_THREADS} -stacking -height $7 -width $8 -stackResult ${9} -SDSS_index ${10} -monitor $6 -remote_scratch ${11} -locality 1 -dataCaching

java -Xms1536M -Xmx1536M -Xss1M -DFALKON_LOGS=${LOG_DIR} -classpath $CLASSPATH:${FALKON_CLIENT_HOME} org/globus/GenericPortal/clients/GPService_instance/UserRun -serviceURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -MAX_EXECS_PER_WS ${EXECS_PER_WS} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -stack_description ${EXEC_FILE} -num_execs ${NUM_EXECS} -num_threads ${NUM_RESOURCES} -num_submit_threads ${NUM_THREADS} -monitor ${MONITOR_DELAY} -locality ${LOCALITY} -max_concurent_tasks ${MAX_CONCURENT_TASKS} -max_submit_throughput ${MAX_SUBMIT_THROUGHPUT} -maxWallTimeMS ${MAX_WALL_TIME_MS} -stacking -height $6 -width $7 -stackResult $8 -SDSS_index ${SDSS_INDEX_FILE} -remote_scratch ${REMOTE_SCRATCH} -dataCaching 


