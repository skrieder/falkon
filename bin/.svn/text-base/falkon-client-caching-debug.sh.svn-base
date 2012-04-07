#!/bin/bash

 if [ -z "$8" ]; then 
              echo "usage: $0 <GT4_IP> <GT4_PORT> <EXEC_FILE> <NUM_TASKS> <SUBMIT_THROTTLE> <BUNDLE_SIZE> <NUM_THREADS> <MAX_CONCURENT_TASKS>"
              exit 1
          fi

if [ -z "${FALKON_CLIENT_HOME}" ]; then
    echo "ERROR: environment variable FALKON_CLIENT_HOME not defined"  1>&2
    return 1
fi

if [ ! -d "${FALKON_CLIENT_HOME}" ]; then
    echo "ERROR: invalid FALKON_CLIENT_HOME set: $FALKON_CLIENT_HOME" 1>&2
    return 1
fi
          
if [ -z "${GLOBUS_LOCATION}" ]; then
    echo "ERROR: environment variable GLOBUS_LOCATION not defined"  1>&2
    return 1
fi

if [ ! -d "${GLOBUS_LOCATION}" ]; then
    echo "ERROR: invalid GLOBUS_LOCATION set: $GLOBUS_LOCATION" 1>&2
    return 1
fi


#used for throttling            
MAX_CONCURENT_TASKS=$8
HOME_PATH=`pwd`
GenericPortalIP=$1
GenericPortalPORT=$2
#EXEC_FILE=workloads/sleep-archive/sleep_0_2M
EXEC_FILE=$3
#EXEC_FILE=workloads/sleep-archive/sleep_0x1M_1K
#EXEC_FILE=workloads/sleep-archive/sleep_0x1K_1K2
#EXEC_FILE=workloads/sleep-archive/sleep_0x1K_1K3
#EXEC_FILE=workloads/sleep-archive/sleep_0x1K_10K
#EXEC_FILE=workloads/sleep-archive/sleep_0x1M_1K3
#EXEC_FILE=workloads/sleep-archive/sleep_0x100K_1K3
NUM_EXECS=$4
NUM_RESOURCES=1
NUM_THREADS=$7
EXECS_PER_WS=$6
MONITOR_DELAY=1000
MAX_SUBMIT_THROUGHPUT=$5
#MAX_WALL_TIME_MS=$4
MAX_WALL_TIME_MS=0
SECURITY_DESCRIPTION_FILE=${FALKON_CONFIG}/client-nosecurity-config.xml


EXP_START=`date +%Y.%m.%d_%H.%M.%S`
LOG_DIR=${FALKON_LOGS}/client/${EXP_START}
mkdir -p ${LOG_DIR} 
LOG_FILE=${LOG_DIR}/client-output.txt

#-XX:+UseConcMarkSweepGC                                     
#java -Xms1536M -Xmx1536M -Xss128K -Djava.security.egd=file:///dev/urandom -DFALKON_LOGS=${LOG_DIR} -classpath $CLASSPATH:${FALKON_CLIENT_HOME} org/globus/GenericPortal/clients/GPService_instance/UserRun -serviceURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -MAX_EXECS_PER_WS ${EXECS_PER_WS} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -job_description ${EXEC_FILE} -num_execs ${NUM_EXECS} -num_threads ${NUM_RESOURCES} -num_submit_threads ${NUM_THREADS} -monitor ${MONITOR_DELAY} -locality 1 -max_concurent_tasks ${MAX_CONCURENT_TASKS} -max_submit_throughput ${MAX_SUBMIT_THROUGHPUT} -taskPerfLog ${LOG_DIR}/client_taskPerf.txt -maxWallTimeMS ${MAX_WALL_TIME_MS} -min_submit_throughput ${NUM_THREADS} -increase_submit_throughput 0 -increase_submit_throughput_mult 1.3 -increase_submit_throughput_interval 60 -dataCaching
#sin
java -Xms1536M -Xmx1536M -Xss128K -Djava.security.egd=file:///dev/urandom -DFALKON_LOGS=${LOG_DIR} -classpath $CLASSPATH:${FALKON_CLIENT_HOME} org/globus/GenericPortal/clients/GPService_instance/UserRun -serviceURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -MAX_EXECS_PER_WS ${EXECS_PER_WS} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -job_description ${EXEC_FILE} -num_execs ${NUM_EXECS} -num_threads ${NUM_RESOURCES} -num_submit_threads ${NUM_THREADS} -monitor ${MONITOR_DELAY} -locality 1 -max_concurent_tasks ${MAX_CONCURENT_TASKS} -max_submit_throughput ${MAX_SUBMIT_THROUGHPUT} -maxWallTimeMS ${MAX_WALL_TIME_MS} -increase_submit_throughput_sin -increase_submit_throughput_interval 1 -dataCaching
#normal
#java -Xms1024M -Xmx1024M -Xss128K -Djava.security.egd=file:///dev/urandom -DFALKON_LOGS=${LOG_DIR} -classpath $CLASSPATH:${FALKON_CLIENT_HOME} org/globus/GenericPortal/clients/GPService_instance/UserRun -serviceURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -MAX_EXECS_PER_WS ${EXECS_PER_WS} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -job_description ${EXEC_FILE} -num_execs ${NUM_EXECS} -num_threads ${NUM_RESOURCES} -num_submit_threads ${NUM_THREADS} -monitor ${MONITOR_DELAY} -locality 1 -max_concurent_tasks ${MAX_CONCURENT_TASKS} -max_submit_throughput ${MAX_SUBMIT_THROUGHPUT} -maxWallTimeMS ${MAX_WALL_TIME_MS} -dataCaching


