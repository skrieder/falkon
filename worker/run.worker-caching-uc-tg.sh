#!/bin/bash

#/bin/sleep 120

MACH_ID=`uname -n`                                    
         
                if [ -z "${FALKON_HOME}" ]; then
                    echo "ERROR: environment variable FALKON_HOME not defined"  1>&2
                    echo "${MACH_ID}: ERROR: environment variable FALKON_HOME not defined" >> ${HOME}/falkon-provisioner-log.txt

                    exit 1
                fi

                if [ ! -d "${FALKON_HOME}" ]; then
                    echo "ERROR: invalid FALKON_HOME set: $FALKON_HOME" 1>&2
                    echo "${MACH_ID}: ERROR: invalid FALKON_HOME set: $FALKON_HOME" >> ${HOME}/falkon-provisioner-log.txt
                    exit 1
                fi

cd ${FALKON_HOME}                     
source falkon.env

#might be needed at ANL/UC TG site
JAVA_TEST=`uname -a | grep "ia64" -c`
 if [ ${JAVA_TEST} = 0 ]; then 
        #export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia32
        #export JAVA_HOME=/home/iraicu/jdk1.5.0_09
        export JAVA_HOME=/home/iraicu/jdk1.6.0
        
#        echo "IA32" > java_test.${RANDOM}.txt
          fi

 if [ ${JAVA_TEST} = 1 ]; then 
        export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia64
#        echo "IA64" > java_test.${RANDOM}.txt
          fi

export PATH=${JAVA_HOME}/bin:${PATH}   


SECURITY_DESCRIPTION_FILE=${FALKON_CONFIG}/worker-security-config.xml
EXP_START=`date +%Y.%m.%d_%H.%M.%S`
mkdir -p ${FALKON_LOGS}/worker
#LOG_FILE=${FALKON_LOGS}/worker/${MACH_ID}.${EXP_START}.${RANDOM}.txt
LOG_FILE=/dev/null

DATA_TRACE_LOG_FILE=${FALKON_LOGS}/worker/data_trace_${MACH_ID}.${EXP_START}.${RANDOM}.txt



if [ -z "$3" ]; then 
              echo "usage: $0 <LIFETIME> <IDLE_TIME> <MAX_NUM_WORKERS>"
              echo "usage: $0 0 0 1"

              echo "usage: $0 <LIFETIME> <IDLE_TIME> <MAX_NUM_WORKERS>" >> ${LOG_FILE}
              echo "${MACH_ID}: usage: $0 <LIFETIME> <IDLE_TIME> <MAX_NUM_WORKERS>" >> ${HOME}/falkon-provisioner-log.txt
              
                                      
              exit -1
          fi

    echo "Worker Script Started: ${HOSTNAME}" >> ${LOG_FILE}


                                     
if [ -z "${FALKON_WORKER_HOME}" ]; then
    echo "ERROR: environment variable FALKON_WORKER_HOME not defined" 1>&2
    echo "ERROR: environment variable FALKON_WORKER_HOME not defined" >> ${LOG_FILE}
              echo "${MACH_ID}: ERROR: environment variable FALKON_WORKER_HOME not defined" >> ${HOME}/falkon-provisioner-log.txt
    exit 1
fi

if [ ! -d "${FALKON_WORKER_HOME}" ]; then
    echo "ERROR: invalid FALKON_WORKER_HOME set: $FALKON_WORKER_HOME" 1>&2
    echo "ERROR: invalid FALKON_WORKER_HOME set: $FALKON_WORKER_HOME" >> ${LOG_FILE}
    echo "${MACH_ID}: ERROR: invalid FALKON_WORKER_HOME set: $FALKON_WORKER_HOME" >> ${HOME}/falkon-provisioner-log.txt
    exit 1
fi
          
if [ -z "${GLOBUS_LOCATION}" ]; then
    echo "ERROR: environment variable GLOBUS_LOCATION not defined"  1>&2
    echo "ERROR: environment variable GLOBUS_LOCATION not defined" >> ${LOG_FILE}
    echo "${MACH_ID}: ERROR: environment variable GLOBUS_LOCATION not defined" >> ${HOME}/falkon-provisioner-log.txt
    exit 1
fi

if [ ! -d "${GLOBUS_LOCATION}" ]; then
    echo "ERROR: invalid GLOBUS_LOCATION set: $GLOBUS_LOCATION" 1>&2
    echo "ERROR: invalid GLOBUS_LOCATION set: $GLOBUS_LOCATION" >> ${LOG_FILE}
    echo "${MACH_ID}: ERROR: invalid GLOBUS_LOCATION set: $GLOBUS_LOCATION" >> ${HOME}/falkon-provisioner-log.txt
    exit 1
fi

SCRATCH=${FALKON_WORKER_HOME}/scratch
EPR_FILE=${FALKON_WORKER_HOME}/WorkerEPR.txt
MAX_THREADS=2
LIFETIME=$1
IDLE_TIME=$2
MAX_NUM_WORKERS=$3

#start gridftp server to serve data caching...
#59000
killall -9 globus-gridftp-server
globus-gridftp-server -c ${FALKON_CONFIG}/gridftp.conf >> ${LOG_FILE} 2>&1 &

EXIT_CODE=$?
if [ "$EXIT_CODE" -ne "0" ]; then
  echo "Error in starting gridftp server... exit code $EXIT_CODE" >> ${LOG_FILE}
    echo "${MACH_ID}: Error in starting gridftp server... exit code $EXIT_CODE" >> ${HOME}/falkon-provisioner-log.txt
  exit $EXIT_CODE
fi


#these are just hacks to ensure that the cache is reset prior to the worker starting... ideally should be done from the Java code...                     
rm -rf /tmp/iraicu2/3DcacheGrid
rm -rf /scratch/local/iraicu2/3DcacheGrid

mkdir -p /tmp/iraicu2/3DcacheGrid
mkdir -p /scratch/local/iraicu2/3DcacheGrid

  #java -version >> ${LOG_FILE}

#ls /scratch/gpfs/wan/${USER}
#if [ "$?" -ne "0" ]; then
#  echo "WAN GPFS not mounted... exit code $?" >> ${LOG_FILE}
#    echo "${MACH_ID}: WAN GPFS not mounted... exit code $?" >> ${HOME}/falkon-provisioner-log.txt
#  exit $?
#fi

ls /scratch/gpfs/local/${USER}
EXIT_CODE=$?
if [ "$EXIT_CODE" -ne "0" ]; then
  echo "LAN GPFS not mounted... exit code $EXIT_CODE" >> ${LOG_FILE}
    echo "${MACH_ID}: LAN GPFS not mounted... exit code $EXIT_CODE" >> ${HOME}/falkon-provisioner-log.txt
  exit $EXIT_CODE
fi

#ls /scratch/local/${USER}
#if [ "$?" -ne "0" ]; then
#  echo "LOCAL disk not mounted... exit code $?" >> ${LOG_FILE}
#    echo "${MACH_ID}: LOCAL disk not mounted... exit code $?" >> ${HOME}/falkon-provisioner-log.txt
#  exit $?
#fi



echo "Starting Falkon Java Executor and writing log to ${LOG_FILE}..."
#-profiling -data_trace ${DATA_TRACE_LOG_FILE} -debug
#java -Xrs -Xmx1536m -classpath $CLASSPATH:${FALKON_WORKER_HOME} org/globus/GenericPortal/clients/GPService_instance/WorkerRun -data_trace ${DATA_TRACE_LOG_FILE} -debug -epr ${EPR_FILE} -lifetime ${LIFETIME} -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads ${MAX_THREADS} -SO_TIMEOUT 0 -idletime ${IDLE_TIME} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -NUM_WORKERS ${MAX_NUM_WORKERS} -CACHE_SIZE_MB 50000 -resetCache -cachingLRU >> "${LOG_FILE}" 2>&1
# to enforce cache size
java -Xrs -Xmx1536m -classpath $CLASSPATH:${FALKON_WORKER_HOME} org/globus/GenericPortal/clients/GPService_instance/WorkerRun -epr ${EPR_FILE} -lifetime ${LIFETIME} -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads ${MAX_THREADS} -SO_TIMEOUT 0 -idletime ${IDLE_TIME} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -NUM_WORKERS ${MAX_NUM_WORKERS} -CACHE_SIZE_MB 1000000 -resetCache -cachingLRU >> "${LOG_FILE}" 2>&1
#java -Xrs -Xmx1024m -classpath $CLASSPATH:${FALKON_WORKER_HOME} org/globus/GenericPortal/clients/GPService_instance/WorkerRun -epr ${EPR_FILE} -lifetime ${LIFETIME} -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads ${MAX_THREADS} -SO_TIMEOUT 0 -idletime ${IDLE_TIME} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -NUM_WORKERS ${MAX_NUM_WORKERS} -resetCache -cachingLRU

EXIT_CODE=$?
if [ "$EXIT_CODE" -ne "0" ]; then
  echo "Error in executing worker... exit code $EXIT_CODE" >> ${LOG_FILE}
    echo "${MACH_ID}: Error in executing worker... exit code $EXIT_CODE" >> ${HOME}/falkon-provisioner-log.txt

  cd /scratch/local
  df . >> ${HOME}/falkon-provisioner-log.txt

  cd /tmp
  df . >> ${HOME}/falkon-provisioner-log.txt

    let LIFETIME_SEC=LIFETIME/1000


    echo "${MACH_ID}: holding onto bad node by sleeping ${LIFETIME_SEC} seconds..." >> ${HOME}/falkon-provisioner-log.txt

    sleep ${LIFETIME_SEC}

  exit $EXIT_CODE
fi

exit 0



