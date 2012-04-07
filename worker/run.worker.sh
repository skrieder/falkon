#!/bin/bash

#/bin/sleep 120
         
                if [ -z "${FALKON_HOME}" ]; then
                    echo "ERROR: environment variable FALKON_HOME not defined"  1>&2
                    echo "ERROR: environment variable FALKON_HOME not defined" >> ${HOME}/falkon-provisioner-log.txt
                    return 1
                fi

                if [ ! -d "${FALKON_HOME}" ]; then
                    echo "ERROR: invalid FALKON_HOME set: $FALKON_HOME" 1>&2
                    echo "ERROR: invalid FALKON_HOME set: $FALKON_HOME" >> ${HOME}/falkon-provisioner-log.txt
                    return 1
                fi

cd ${FALKON_HOME}                     
source falkon.env

MACH_ID=`uname -n`                                    
SECURITY_DESCRIPTION_FILE=${FALKON_CONFIG}/worker-security-config.xml
EXP_START=`date +%Y.%m.%d_%H.%M.%S`
mkdir -p ${FALKON_LOGS}/worker
LOG_FILE=${FALKON_LOGS}/worker/${MACH_ID}.${EXP_START}.${RANDOM}.txt
#LOG_FILE=/dev/null

if [ -z "$3" ]; then 
              echo "usage: $0 <LIFETIME> <IDLE_TIME> <MAX_NUM_WORKERS>"
              echo "usage: $0 0 0 1"

              echo "usage: $0 <LIFETIME> <IDLE_TIME> <MAX_NUM_WORKERS>" >> ${LOG_FILE}
              
              exit -1
          fi

    echo "Worker Script Started: ${HOSTNAME}" >> ${LOG_FILE}


                                     
if [ -z "${FALKON_WORKER_HOME}" ]; then
    echo "ERROR: environment variable FALKON_WORKER_HOME not defined" 1>&2
    echo "ERROR: environment variable FALKON_WORKER_HOME not defined" >> ${LOG_FILE}
    return 1
fi

if [ ! -d "${FALKON_WORKER_HOME}" ]; then
    echo "ERROR: invalid FALKON_WORKER_HOME set: $FALKON_WORKER_HOME" 1>&2
    echo "ERROR: invalid FALKON_WORKER_HOME set: $FALKON_WORKER_HOME" >> ${LOG_FILE}
    return 1
fi
          
if [ -z "${GLOBUS_LOCATION}" ]; then
    echo "ERROR: environment variable GLOBUS_LOCATION not defined"  1>&2
    echo "ERROR: environment variable GLOBUS_LOCATION not defined" >> ${LOG_FILE}
    return 1
fi

if [ ! -d "${GLOBUS_LOCATION}" ]; then
    echo "ERROR: invalid GLOBUS_LOCATION set: $GLOBUS_LOCATION" 1>&2
    echo "ERROR: invalid GLOBUS_LOCATION set: $GLOBUS_LOCATION" >> ${LOG_FILE}
    return 1
fi

SCRATCH=${FALKON_WORKER_HOME}/scratch
EPR_FILE=${FALKON_WORKER_HOME}/WorkerEPR.txt
MAX_THREADS=1
LIFETIME=$1
IDLE_TIME=$2
MAX_NUM_WORKERS=$3

echo "Starting Falkon Java Executor and writing log to ${LOG_FILE}..."
java -Xrs -Xmx128m -Djava.security.egd=file:///dev/urandom -classpath $CLASSPATH:${FALKON_WORKER_HOME} org/globus/GenericPortal/clients/GPService_instance/WorkerRun -epr ${EPR_FILE} -lifetime ${LIFETIME} -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads ${MAX_THREADS} -SO_TIMEOUT 0 -idletime ${IDLE_TIME} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -NUM_WORKERS ${MAX_NUM_WORKERS} >> "${LOG_FILE}" 2>&1

if [ "$?" -ne "0" ]; then
  echo "Error in executing worker... exit code $?" >> ${LOG_FILE}
  exit $?
fi


#might be needed at ANL/UC TG site
#JAVA_TEST=`uname -a | grep "ia64" -c`
 #if [ ${JAVA_TEST} = 0 ]; then 
  #      export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia32
#        echo "IA32" > java_test.${RANDOM}.txt
   #       fi

 #if [ ${JAVA_TEST} = 1 ]; then 
  #      export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia64
#        echo "IA64" > java_test.${RANDOM}.txt
   #       fi

