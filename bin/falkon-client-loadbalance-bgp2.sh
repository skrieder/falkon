#!/bin/bash

 if [ -z "$5" ]; then 
              echo "usage: $0 <NOTIFICATION_ENDPOINT> <EXEC_FILE> <NUM_TASKS> <FALKON_ID> <NUM_NODES>"
              exit 1
          fi

if [ -z "${FALKON_ROOT}" ]; then
    echo "ERROR: environment variable FALKON_ROOT not defined"
    exit 1
fi

if [ ! -d "/tmp/${USER}/falkon" ]; then

    mkdir -p /tmp/${USER}
                           
    dd bs=128k if=${FALKON_HOME}/falkon.tgz of=/tmp/${USER}/falkon.tgz 

    EXIT_CODE=$? 
    if [ "${EXIT_CODE}" -ne "0" ]; then
        echo "Error in executing command 'dd bs=128k if=${FALKON_HOME}/falkon.tgz of=/tmp/${USER}/falkon.tgz'... exit code ${EXIT_CODE}" >> ${ERROR_LOG}
        DATE=`date`
        echo "${DATE} : falkon-ion-start-blocking-zeptocn-ram : failed : Error in executing command 'dd bs=128k if=${FALKON_HOME}/falkon.tgz of=/tmp/${USER}/falkon.tgz'... exit code ${EXIT_CODE}" >> ${LOG_FILE_DEBUG}
        exit ${EXIT_CODE}
    fi


cd /tmp/${USER}
        
tar fxz falkon.tgz
    EXIT_CODE=$? 
    if [ "${EXIT_CODE}" -ne "0" ]; then
        echo "Error in executing command 'tar fxz falkon.tgz'... exit code ${EXIT_CODE}" >> ${ERROR_LOG}
        DATE=`date`
        echo "${DATE} : falkon-ion-start-blocking-zeptocn-ram : failed : Error in executing command 'tar fxz falkon.tgz'... exit code ${EXIT_CODE}" >> ${LOG_FILE_DEBUG}
        exit ${EXIT_CODE}
    fi

rm -rf falkon.tgz    
    #echo "ERROR: invalid FALKON_CLIENT_HOME set: $FALKON_CLIENT_HOME"
    #exit 1
fi

                          
CUR_DIR=`pwd`
                       
cd ${FALKON_ROOT}/users/${USER}/${4}
echo `pwd`
source falkon.env.bgp

cd ${CUR_DIR}

if [ -z "${FALKON_CLIENT_HOME}" ]; then
    echo "ERROR: environment variable FALKON_CLIENT_HOME not defined"
    exit 1
fi

if [ ! -d "${FALKON_CLIENT_HOME}" ]; then
    echo "ERROR: invalid FALKON_CLIENT_HOME set: $FALKON_CLIENT_HOME"
    exit 1
fi
          
if [ -z "${GLOBUS_LOCATION}" ]; then
    echo "ERROR: environment variable GLOBUS_LOCATION not defined"
    exit 1
fi

if [ ! -d "${GLOBUS_LOCATION}" ]; then
    echo "ERROR: invalid GLOBUS_LOCATION set: $GLOBUS_LOCATION"
    exit 1
fi


#used for throttling            
#SERVICE_LIST_FILE=$1
SERVICE_LIST_FILE=${FALKON_CONFIG}/Client-service-URIs.config
NOTIFICATION_ENDPOINT=$1
MAX_CONCURENT_TASKS=256
HOME_PATH=`pwd`
#GenericPortalIP=$1
#GenericPortalPORT=$2
#EXEC_FILE=workloads/sleep-archive/sleep_0_2M
EXEC_FILE=$2
#EXEC_FILE=workloads/sleep-archive/sleep_0x1M_1K
#EXEC_FILE=workloads/sleep-archive/sleep_0x1K_1K2
#EXEC_FILE=workloads/sleep-archive/sleep_0x1K_1K3
#EXEC_FILE=workloads/sleep-archive/sleep_0x1K_10K
#EXEC_FILE=workloads/sleep-archive/sleep_0x1M_1K3
#EXEC_FILE=workloads/sleep-archive/sleep_0x100K_1K3
NUM_EXECS=$3
NUM_RESOURCES=1
NUM_THREADS=1
EXECS_PER_WS=256
MONITOR_DELAY=1000
MAX_SUBMIT_THROUGHPUT=1000000
#MAX_WALL_TIME_MS=$4
MAX_WALL_TIME_MS=0
SECURITY_DESCRIPTION_FILE=${FALKON_CONFIG}/client-nosecurity-config.xml


EXP_START=`date +%Y.%m.%d_%H.%M.%S`
LOG_DIR=${FALKON_LOGS}/client/${EXP_START}
mkdir -p ${LOG_DIR} 
LOG_FILE=${LOG_DIR}/client-output.txt

                                               
NUM_NODES=$5
let NUM_ION=NUM_NODES/64

echo "waiting for at least ${NUM_NODES} nodes to register before submitting workload..."
falkon-wait-for-allocation.sh ${SERVICE_LIST_FILE} ${NUM_ION}
                                
echo "found at least ${NUM_NODES} registered, submitting workload..."
#-XX:+UseConcMarkSweepGC                                     
#java -Xms1536M -Xmx1536M -Xss128K -DFALKON_LOGS=${LOG_DIR} -classpath $CLASSPATH:${FALKON_CLIENT_HOME} org/globus/GenericPortal/clients/GPService_instance/UserRun -serviceURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -MAX_EXECS_PER_WS ${EXECS_PER_WS} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -job_description ${EXEC_FILE} -num_execs ${NUM_EXECS} -num_threads ${NUM_RESOURCES} -num_submit_threads ${NUM_THREADS} -monitor ${MONITOR_DELAY} -locality 1 -max_concurent_tasks ${MAX_CONCURENT_TASKS} -max_submit_throughput ${MAX_SUBMIT_THROUGHPUT} -maxWallTimeMS ${MAX_WALL_TIME_MS} -min_submit_throughput ${NUM_THREADS} -increase_submit_throughput 0 -increase_submit_throughput_mult 1.06 -increase_submit_throughput_interval 60
java -Xms1536M -Xmx1536M -Xss128K -Djava.security.egd=file:///dev/urandom -DFALKON_LOGS=${LOG_DIR} -classpath $CLASSPATH:${FALKON_CLIENT_HOME} org/globus/GenericPortal/clients/GPService_instance/UserRun -serviceURIfile ${SERVICE_LIST_FILE} -MAX_EXECS_PER_WS ${EXECS_PER_WS} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -job_description ${EXEC_FILE} -num_execs ${NUM_EXECS} -num_submit_threads ${NUM_THREADS} -monitor ${MONITOR_DELAY} -max_concurent_tasks ${MAX_CONCURENT_TASKS} -max_submit_throughput ${MAX_SUBMIT_THROUGHPUT} -notificationEndpoint ${NOTIFICATION_ENDPOINT} -taskPerfLog ${LOG_DIR}/client_taskPerf.txt -taskDescriptionLog ${LOG_DIR}/client_taskDescription.txt -maxWallTimeMS ${MAX_WALL_TIME_MS}


