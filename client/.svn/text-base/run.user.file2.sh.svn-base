#!/bin/bash

 if [ -z "$6" ]; then 
              echo "usage: $0 <GT4_IP> <GT4_PORT> <EXEC_FILE> <NUM_EXECS> <NUM_THREADS> <SECURITY_OPTIONS>"
              exit
          fi

if [ -z "${FALKON_HOME}" ]; then
    echo "ERROR: environment variable FALKON_HOME not defined"  1>&2
    return 1
fi

if [ ! -d "${FALKON_HOME}" ]; then
    echo "ERROR: invalid FALKON_HOME set: $FALKON_HOME" 1>&2
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
export MAX_CONCURENT_TASKS=100000
export HOME_PATH=`pwd`
export GenericPortalIP=$1
export GenericPortalPORT=$2
export EXEC_FILE=$3
export NUM_EXECS=$4
export NUM_THREADS=$5
export SECURITY_DESCRIPTION_FILE=$6
                                    
cd ${FALKON_CLIENT_HOME}
                                                                               
java -XX:+UseConcMarkSweepGC -Xms1536M -Xmx1536M -Xss128K -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/UserRun -serviceURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -MAX_EXECS_PER_WS 1000 -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -job_description ${EXEC_FILE} -num_execs ${NUM_EXECS} -num_threads ${NUM_THREADS} -monitor 1000 -locality 1 -max_concurent_tasks ${MAX_CONCURENT_TASKS}


