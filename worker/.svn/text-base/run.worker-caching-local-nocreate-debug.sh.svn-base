#!/bin/bash

#source /home/iraicu/.bashrc
         
# if [ -z "$2" ]; then 
#              echo "usage: $0 <GT4_IP> <GT4_PORT>"
#              exit 1
#          fi
                           
if [ -z "${FALKON_WORKER_HOME}" ]; then
    echo "ERROR: environment variable FALKON_WORKER_HOME not defined"  1>&2
    return 1
fi

if [ ! -d "${FALKON_WORKER_HOME}" ]; then
    echo "ERROR: invalid FALKON_WORKER_HOME set: $FALKON_WORKER_HOME" 1>&2
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


SECURITY_DESCRIPTION_FILE=${FALKON_CONFIG}/worker-security-config.xml
SCRATCH=${FALKON_WORKER_HOME}/scratch
EPR_FILE=${FALKON_WORKER_HOME}/WorkerEPR.txt
MAX_NUM_WORKERS=1        
LIFETIME=0
IDLE_TIME=0
MAX_THREADS=10
GenericPortalIP=$1
GenericPortalPORT=$2

#-authenticate -authorize -encrypt -sign -TSL -MSG -CONV
#echo "creating Falkon Java Executor resource..."
#java -classpath ${HOME_PATH}:${CLASSPATH}:${FALKON_WORKER_HOME} org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate -factoryURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -epr ${EPR_FILE} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE}


#start gridftp server to serve data caching...
#59000
killall -9 globus-gridftp-server
globus-gridftp-server -c ${FALKON_CONFIG}/gridftp.conf &
if [ "$?" -ne "0" ]; then
  echo "Error in starting gridftp server... exit code $?"
  exit $?
fi

            
echo "Starting Falkon Java Executor in interactive mode..."
#java -Xrs -Xmx64m -Xss128K -classpath $CLASSPATH:${FALKON_WORKER_HOME} org/globus/GenericPortal/clients/GPService_instance/WorkerRun -epr ${EPR_FILE} -lifetime ${LIFETIME} -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads ${MAX_THREADS} -SO_TIMEOUT 0 -idletime ${IDLE_TIME} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -NUM_WORKERS ${MAX_NUM_WORKERS}
java -Xrs -Xmx512m -classpath $CLASSPATH:${FALKON_WORKER_HOME} org/globus/GenericPortal/clients/GPService_instance/WorkerRun -profiling -debug -epr ${EPR_FILE} -lifetime ${LIFETIME} -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads ${MAX_THREADS} -SO_TIMEOUT 0 -idletime ${IDLE_TIME} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -NUM_WORKERS ${MAX_NUM_WORKERS} -interactive -CACHE_SIZE_MB 50000 -resetCache -cachingLRU

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
