#!/bin/bash

#source /home/iraicu/.bashrc
         
 if [ -z "$13" ]; then 
              echo "usage: $0 <GT4_IP> <GT4_PORT> <GRAM_IP> <exec_script> <maxWallTime_min> <hostType> <hostCount> <idleTime> <pollTime> <minHostCount> <minWallTime_min> <project> <MAX_NUM_WORKERS_PER_HOST>"
              echo "usage: $0 localhost 50001 tg-grid1.uc.teragrid.org `pwd`/run.worker.sh 1 ia32-compute 1 60000 1000 1 1"
              exit
          fi
                           
VERSION=1.2

export GenericPortalIP=$1
export GenericPortalPORT=$2
export EPR_FILE=${HOME_PATH}/WorkerEPR.txt
export SECURITY_DESCRIPTION_FILE=etc/client-security-config.xml


#echo "creating GenericWorker resource..."
#echo ${HOME_PATH}
#echo ${CLASSPATH}
#echo ${GenericPortalIP}
#echo ${GenericPortalPORT}
#echo ${EPR_FILE}
#echo ${SECURITY_DESCRIPTION_FILE}
#echo `pwd`
#java -classpath ${HOME_PATH}:${CLASSPATH}:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate -factoryURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -epr ${EPR_FILE} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE}
#./run.create-worker.sh ${GenericPortalIP} ${GenericPortalPORT} ${SECURITY_DESCRIPTION_FILE}


                                                                    
export HOME_PATH=`pwd`
#export HOME_PATH=/home/iraicu/container
export GLOBUS_LOCATION=${HOME_PATH}
#export GLOBUS_LOCATION=/home/iraicu/container
export GLOBUS_PATH=${GLOBUS_LOCATION}
export LD_LIBRARY_PATH=${GLOBUS_LOCATION}/lib
#export LD_LIBRARY_PATH=/disks/scratchgpfs1/iraicu/ModLyn/intel/9.1.049/lib:$LD_LIBRARY_PATH
export MACH_ID=`uname -n`
export EXP_START=`date +%Y.%m.%d_%k.%M.%S`
#export LOG_FILE=${HOME_PATH}/logs/worker.${MACH_ID}.${EXP_START}.txt
export LOG_FILE=logs/worker.${MACH_ID}.${RANDOM}.txt

#should change this to the local disk if WS will be used to transmit image data...
#export SCRATCH=${HOME_PATH}/scratch
export SCRATCH=scratch
export EPR_FILE=${HOME_PATH}/WorkerEPR.txt

                MAX_NUM_WORKERS=1

                


#cd ..
#export JAVA_HOME=`pwd`/jre
#cd ${HOME_PATH}

#JAVA_TEST=`uname -a | grep "ia64" -c`
# if [ ${JAVA_TEST} = 0 ]; then 
#        export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia32
#        echo "IA32" > java_test.${RANDOM}.txt
#          fi

# if [ ${JAVA_TEST} = 1 ]; then 
#        export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia64
#        echo "IA64" > java_test.${RANDOM}.txt
#          fi


#export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia32
#export JAVA_HOME=/home/iraicu/jdk1.5.0_06
#export JAVA_HOME=/home/iraicu/jdk1.6.0
export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia64
#export PATH=${GLOBUS_LOCATION}:${PATH}
export PATH=${JAVA_HOME}:${JAVA_HOME}/bin:${GLOBUS_LOCATION}:${GLOBUS_LOCATION}/bin:${PATH}

#export SECURITY_DESCRIPTION_FILE=$5


#export LIFETIME=$1

# if [ -z "$1" ]; then 
#              LIFETIME=1440
#          fi

source ${GLOBUS_LOCATION}/etc/globus-devel-env.sh

#original call       
#${JAVA_HOME}/bin/java -Xmx1024m -classpath $CLASSPATH:${HOME_PATH}:${HOME_PATH}/build/stubs/classes/ org/globus/GenericPortal/clients/GPService_instance/WorkerRun -epr ${EPR_FILE} -lifetime 10800000 -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads 10 -SO_TIMEOUT 0 -interactive -diperf
#${JAVA_HOME}/bin/java -Xmx1536m -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/WorkerRun -epr ${EPR_FILE} -lifetime 86400000 -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads 10 -SO_TIMEOUT 0 > ${LOG_FILE} 2>&1


#setting the idle time to 0 means it will wait forever; it is in ms
#export IDLE_TIME=600000
#export IDLE_TIME=$2

# if [ -z "$2" ]; then 
#              IDLE_TIME=300000
#          fi

export MAX_THREADS=2
                    
#export GenericPortalIP=192.5.198.97
#export GenericPortalPORT=50001


# if [ -n "$3" ]; then 
#              GenericPortalIP=$3
#          fi


# if [ -n "$4" ]; then 
#              GenericPortalPORT=$4
#          fi

#-authenticate -authorize -encrypt -sign -TSL -MSG -CONV
            
#echo "Starting GenericWorker and writing log to ${LOG_FILE}..."
#${JAVA_HOME}/bin/java -Xmx128m -classpath $CLASSPATH:${HOME_PATH} org/globus/GenericPortal/clients/GPService_instance/WorkerRun -epr ${EPR_FILE} -lifetime ${LIFETIME} -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads ${MAX_THREADS} -SO_TIMEOUT 0 -idletime ${IDLE_TIME} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -NUM_WORKERS ${MAX_NUM_WORKERS} -interactive -debug
#${JAVA_HOME}/bin/java -Xmx1536m -classpath $CLASSPATH:${HOME_PATH} org/globus/GenericPortal/clients/GPService_instance/WorkerRun -epr ${EPR_FILE} -lifetime ${LIFETIME} -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads ${MAX_THREADS} -SO_TIMEOUT ${IDLE_TIME} >> "${LOG_FILE}" 2>&1
#${JAVA_HOME}/bin/java -Xmx1536m -classpath $CLASSPATH:${HOME_PATH} org/globus/GenericPortal/clients/GPService_instance/WorkerRun -epr ${EPR_FILE} -lifetime ${LIFETIME} -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads 10 -SO_TIMEOUT ${IDLE_TIME} -caching -diperf >> "${LOG_FILE}" 2>&1
#${JAVA_HOME}/bin/java -Xms64m -Xmx1024m -classpath $CLASSPATH:${HOME_PATH}:${HOME_PATH}/build/stubs/classes/ org/globus/GenericPortal/clients/GPService_instance/WorkerRun -epr ${EPR_FILE} -lifetime 10800000 -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads 10 -SO_TIMEOUT 0 -interactive -debug


echo "starting up the GramClient boot strapping mechanism..."
#java -classpath ${HOME_PATH}:${CLASSPATH}:. -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} org/globus/GenericPortal/common/GramClient $2 $3 $4 $5 $6
#java -classpath ${HOME_PATH}:${CLASSPATH}:. -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} org/globus/GenericPortal/clients/GPService_instance/WorkerRunGram -serviceURI http://${GenericPortalIP}:50002/wsrf/services/GenericPortal/core/WS/GPFactoryService -maxWallTime $4 -executable $3 -hostType $5 -hostCount $6 -contact $2  > "${HOME_PATH}/logs/gram.txt" 2>&1 &
echo ${GLOBUS_LOCATION}
echo ${HOME_PATH}
echo ${CLASSPATH}
echo ${GenericPortalIP}
echo ${GenericPortalPORT}
echo ${EPR_FILE}
echo "GenericPortalIP ${1}"
echo "GenericPortalPORT ${2}"
echo "-contact ${3}"
echo "-executable ${4}"
echo "-maxWallTime ${5}"
echo "-hostType ${6}"
echo "-hostCount ${7}"
echo "-idleTime ${8}"
echo "-pollTime ${9}"
echo "-minHostCount ${10}"
echo "-minWallTime ${11}"
echo "-project ${12}"
echo "-MAX_NUM_WORKERS_PER_HOST ${13}"

#java -classpath ${GLOBUS_LOCATION}:${HOME_PATH}:${CLASSPATH}:. -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} org/globus/GenericPortal/clients/GPService_instance/WorkerRunGram -serviceURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -epr ${EPR_FILE} -maxWallTime $5 -minWallTime ${11} -executable $4 -hostType $6 -hostCount $7 -contact $3 -idleTime $8 -pollTime $9 -minHostCount ${10} -project ${12} -MAX_NUM_WORKERS_PER_HOST ${13} -debug
java -classpath ${GLOBUS_LOCATION}:${HOME_PATH}:${CLASSPATH}:. -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} org/globus/GenericPortal/clients/GPService_instance/WorkerRunGram -serviceURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -epr ${EPR_FILE} -maxWallTime $5 -minWallTime ${11} -executable $4 -hostType $6 -hostCount $7 -contact $3 -idleTime $8 -pollTime $9 -minHostCount ${10} -project ${12} -MAX_NUM_WORKERS_PER_HOST ${13} -debug
#jdb
    
#standalone invocation (for testing only)              
#${JAVA_HOME}/bin/java -Xms64m -Xmx1024m -classpath $CLASSPATH:${HOME_PATH}:${HOME_PATH}/build/stubs/classes/ org/globus/GenericPortal/clients/GPService_instance/WorkerRun -lifetime 1000 -scratch_disk ${SCRATCH}/ -max_not 1 -max_threads 10 -SO_TIMEOUT 0 -interactive -desc /disks/scratchgpfs1/iraicu/generic.portal/data/test.txt -standalone -job_size 5 -height 100 -width 100                                           

