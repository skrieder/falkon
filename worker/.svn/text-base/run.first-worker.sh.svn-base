#!/bin/bash

if [ -z "$2" ]; then 
              echo "usage: $0 <GT4_IP> <GT4_PORT>"
              exit
          fi
          
export HOME_PATH=`pwd`
export GLOBUS_LOCATION=${HOME_PATH}
export GLOBUS_PATH=${HOME_PATH}
export LD_LIBRARY_PATH=${HOME_PATH}/lib
#export GenericPortalIP=192.5.198.97
export GenericPortalIP=$1
export GenericPortalPORT=$2
export EPR_FILE=${HOME_PATH}/WorkerEPR.txt

source ${GLOBUS_LOCATION}/etc/globus-devel-env.sh

# erase common state at GPWS
#rm -rf /home/iraicu/.globus/persisted/GPWS/GenericPortalCommonResource.dat

echo "creating first worker resource..."
java -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService ${EPR_FILE}
echo "worker resource created succesful!"

         
export HOME_PATH=/home/iraicu/java/GenericWorker_v0.3
#export HOME_PATH=/home/iraicu/GT4.0.1
export GLOBUS_LOCATION=/home/iraicu/GT4.0.1
export GLOBUS_PATH=${GLOBUS_LOCATION}
export LD_LIBRARY_PATH=${GLOBUS_LOCATION}/lib
export MACH_ID=`uname -n`
export EXP_START=`date +%Y.%m.%d_%k.%M.%S`
#export LOG_FILE=${HOME_PATH}/logs/worker.${MACH_ID}.${EXP_START}.txt
export LOG_FILE=${HOME_PATH}/logs/worker.${MACH_ID}.txt
export SCRATCH=${HOME_PATH}/scratch
export EPR_FILE=${HOME_PATH}/WorkerEPR.txt
#export JAVA_HOME=/usr/java/jdk1.5.0_07
export PATH=${JAVA_HOME}:${GLOBUS_LOCATION}:${PATH}

export LIFETIME=0


source ${GLOBUS_LOCATION}/etc/globus-devel-env.sh

#original call       
#${JAVA_HOME}/bin/java -Xmx1024m -classpath $CLASSPATH:${HOME_PATH}:${HOME_PATH}/build/stubs/classes/ org/globus/GenericPortal/clients/GPService_instance/WorkerRun -epr ${EPR_FILE} -lifetime 10800000 -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads 10 -SO_TIMEOUT 0 -interactive -diperf
#${JAVA_HOME}/bin/java -Xmx1536m -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/WorkerRun -epr ${EPR_FILE} -lifetime 86400000 -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads 10 -SO_TIMEOUT 0 > ${LOG_FILE} 2>&1


#setting the idle time to 0 means it will wait forever; it is in ms
#export IDLE_TIME=600000
export IDLE_TIME=0
echo "Starting GenericWorker and writing log to ${LOG_FILE}..."
#${JAVA_HOME}/bin/java -Xmx1536m -classpath $CLASSPATH:${HOME_PATH} org/globus/GenericPortal/clients/GPService_instance/WorkerRun -epr ${EPR_FILE} -lifetime ${LIFETIME} -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads 10 -SO_TIMEOUT ${IDLE_TIME} >> "${LOG_FILE}" 2>&1 &
${JAVA_HOME}/bin/java -Xmx1536m -classpath $CLASSPATH:${HOME_PATH} org/globus/GenericPortal/clients/GPService_instance/WorkerRun -epr ${EPR_FILE} -lifetime ${LIFETIME} -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads 10 -SO_TIMEOUT ${IDLE_TIME} -interactive -debug
#${JAVA_HOME}/bin/java -Xms64m -Xmx1024m -classpath $CLASSPATH:${HOME_PATH}:${HOME_PATH}/build/stubs/classes/ org/globus/GenericPortal/clients/GPService_instance/WorkerRun -epr ${EPR_FILE} -lifetime 10800000 -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads 10 -SO_TIMEOUT 0 -interactive -debug

#standalone invocation (for testing only)              
#${JAVA_HOME}/bin/java -Xms64m -Xmx1024m -classpath $CLASSPATH:${HOME_PATH}:${HOME_PATH}/build/stubs/classes/ org/globus/GenericPortal/clients/GPService_instance/WorkerRun -lifetime 1000 -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads 10 -SO_TIMEOUT 0 -interactive -desc /disks/scratchgpfs1/iraicu/generic.portal/data/test.txt -standalone -job_size 5 -height 100 -width 100                                           

