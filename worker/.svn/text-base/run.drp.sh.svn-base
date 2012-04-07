#!/bin/bash


 if [ -z "$2" ]; then 
              echo "usage: $0 <ProvisionerConfigFile> <ConfigFilePollInterval_sec>"
              echo "usage: $0 etc/Provisioner.config 60"
              exit
          fi


export HOME_PATH=`pwd`
export FALKON_WORKER_HOME=`pwd`
#export HOME_PATH=/home/iraicu/container
export GLOBUS_LOCATION=${HOME_PATH}
#export GLOBUS_LOCATION=/home/iraicu/container
export GLOBUS_PATH=${GLOBUS_LOCATION}
export LD_LIBRARY_PATH=${GLOBUS_LOCATION}/lib
#export LD_LIBRARY_PATH=/disks/scratchgpfs1/iraicu/ModLyn/intel/9.1.049/lib:$LD_LIBRARY_PATH
#export MACH_ID=`uname -n`
#export EXP_START=`date +%Y.%m.%d_%k.%M.%S`
#export LOG_FILE=${HOME_PATH}/logs/worker.${MACH_ID}.${EXP_START}.txt
#export LOG_FILE=logs/worker.${MACH_ID}.${RANDOM}.txt
#should change this to the local disk if WS will be used to transmit image data...
#export SCRATCH=${HOME_PATH}/scratch
export SCRATCH=scratch
#export EPR_FILE=${HOME_PATH}/WorkerEPR.txt

#cd ${HOME_PATH}
                           
#cd ..
#export JAVA_HOME=`pwd`/jre
#cd ${HOME_PATH}
                   
#export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia64


JAVA_TEST=`uname -a | grep "ia64" -c`


 if [ ${JAVA_TEST} -eq 0 ]; then 
        export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia32
#        echo "IA32" > java_test.${RANDOM}.txt
          fi

 if [ ${JAVA_TEST} -eq 1 ]; then 
        export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia64
#        echo "IA64" > java_test.${RANDOM}.txt
          fi

#export JAVA_HOME=/home/iraicu/jdk1.5.0_06
#export JAVA_HOME=/home/iraicu/jdk1.6.0
#export PATH=${JAVA_HOME}:${JAVA_HOME}/bin:${GLOBUS_LOCATION}:${PATH}
export PATH=${JAVA_HOME}:${JAVA_HOME}/bin:${GLOBUS_LOCATION}:${GLOBUS_LOCATION}/bin:${PATH}

#export GenericPortalIP=$1
#export GenericPortalPORT=$2
#export EPR_FILE=${HOME_PATH}/WorkerEPR.txt

source ${GLOBUS_LOCATION}/etc/globus-devel-env.sh
#export SECURITY_DESCRIPTION_FILE=etc/client-security-config.xml

###########################
#export EXP_START=`date +%Y.%m.%d_%k.%M.%S`
#export LOG_DIR=${HOME_PATH}/logs/worker.${MACH_ID}.${EXP_START}
#mkdir ${LOG_DIR}

#mv ${HOME_PATH}/logs/*.txt ${LOG_DIR}/

############################                           

#-authenticate -authorize -encrypt -sign -TSL -MSG -CONV
#echo "creating GenericWorker resource..."
#java -classpath ${GLOBUS_LOCATION}:${HOME_PATH}:${CLASSPATH}:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate -factoryURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -epr ${EPR_FILE} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE}
#java -classpath ${HOME_PATH}:${CLASSPATH}:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate -factoryURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -epr ${EPR_FILE} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -debug

#echo "initializing the GenericPortal Web Service..."
#java -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate http://${GenericPortalIP}:50001/wsrf/services/GenericPortal/core/WS/GPFactoryService ${EPR_FILE}
echo "starting up the GramClient boost strapping mechanism..."
#java -classpath ${HOME_PATH}:${CLASSPATH}:. -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} org/globus/GenericPortal/common/GramClient $2 $3 $4 $5 $6
#java -classpath ${HOME_PATH}:${CLASSPATH}:. -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} org/globus/GenericPortal/clients/GPService_instance/WorkerRunGram -serviceURI http://${GenericPortalIP}:50002/wsrf/services/GenericPortal/core/WS/GPFactoryService -maxWallTime $4 -executable $3 -hostType $5 -hostCount $6 -contact $2  > "${HOME_PATH}/logs/gram.txt" 2>&1 &
#java -classpath ${GLOBUS_LOCATION}:${HOME_PATH}:${CLASSPATH}:. -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} -DProvisionerConfig=${HOME_PATH}/etc/Provisioner.config org/globus/GenericPortal/clients/GPService_instance/WorkerRunGram -serviceURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -epr ${EPR_FILE} -maxWallTime $5 -minWallTime ${11} -executable $4 -hostType $6 -hostCount $7 -contact $3 -idleTime $8 -pollTime $9 -minHostCount ${10} -project ${12} -MAX_NUM_WORKERS_PER_HOST ${13} -debug
java -classpath ${GLOBUS_LOCATION}:${HOME_PATH}:${CLASSPATH}:. -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} -DFALKON_WORKER_HOME=${FALKON_WORKER_HOME} org/globus/GenericPortal/clients/GPService_instance/WorkerRunGram -config_file $1 -config_poll $2 > logs/drp.txt 2>&1 &

#echo "GramClient finished.... destroying GenericWorker resource..."
#wsrf-destroy -e ${EPR_FILE}
#rm -rf ${EPR_FILE}

#host viper.uchicago.edu | awk '{print $4}'
