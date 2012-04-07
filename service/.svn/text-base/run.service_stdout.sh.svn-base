#!/bin/bash 

#echo "==========================================================================================================="

 if [ -z "$2" ]; then 
              echo "usage: $0 <FALKON_PORT> <CONFIG_FILE>"
              echo "$0 50001 ${FALKON_CONFIG}/Falkon.config"
              exit
          fi


if [ -z "${FALKON_SERVICE_HOME}" ]; then
    echo "ERROR: environment variable FALKON_SERVICE_HOME not defined"  1>&2
    return 1
fi

if [ ! -d "${FALKON_SERVICE_HOME}" ]; then
    echo "ERROR: invalid FALKON_SERVICE_HOME set: $FALKON_SERVICE_HOME" 1>&2
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

CONFIG_FILE=$2
MACH_ID=`uname -n`
#HEAP_SIZE=1536M
#STACK_SIZE=128K
FALKON_PORT=$1 

#declare -i Y            
#Y=`date +%Y`
#declare -i m
#m=`date +%m`
#declare -i dd            
##dd=`date +%d`
#declare -i k            
#k=`date +%H`
#declare -i M            
#M=`date +%M`
#declare -i S            
#S=`date +%S`
#EXP_START="${Y}.${m}.`date +%d`_${k}.${M}.${S}"
EXP_START=`date +%Y.%m.%d_%H.%M.%S`
LOG_DIR=${FALKON_LOGS}/service/${EXP_START}_${MACH_ID}
mkdir -p ${LOG_DIR} 
LOG_FILE=${LOG_DIR}/container.txt

      
#-Xnojit for use with IBM JDK
#export GLOBUS_OPTIONS="-XX:+UseConcMarkSweepGC -Xms1536M -Xmx1536M -Xss128K -server -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} -DFALKON_SERVICE_HOME=${FALKON_SERVICE_HOME} -DFalkonConfig=${CONFIG_FILE}"
#export GLOBUS_OPTIONS="-verbose:gc -XX:+PrintGCTimeStamps -verbose:gc -Xrunhprof:heap=sites,format=b,file=java.hprof.sites.falkon.bin  -Xms${HEAP_SIZE} -Xmx${HEAP_SIZE} -Xss${STACK_SIZE} -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} -DFALKON_SERVICE_HOME=${FALKON_SERVICE_HOME} -DFalkonConfig=${CONFIG_FILE} -DFALKON_LOGS=${LOG_DIR}"
#export GLOBUS_OPTIONS="-Xms${HEAP_SIZE} -Xmx${HEAP_SIZE} -Xss${STACK_SIZE} -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} -DFALKON_SERVICE_HOME=${FALKON_SERVICE_HOME} -DFalkonConfig=${CONFIG_FILE} -DFALKON_LOGS=${LOG_DIR}"
export GLOBUS_OPTIONS="${GLOBUS_OPTIONS_MISC} -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} -DFALKON_SERVICE_HOME=${FALKON_SERVICE_HOME} -DFalkonConfig=${CONFIG_FILE} -DFALKON_LOGS=${LOG_DIR}"

#export GLOBUS_OPTIONS="-server -XX:+UseConcMarkSweepGC -Xms${HEAP_SIZE} -Xmx${HEAP_SIZE} -Xss${STACK_SIZE} -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} -DFALKON_SERVICE_HOME=${FALKON_SERVICE_HOME} -DFalkonConfig=${CONFIG_FILE} -DFALKON_LOGS=${LOG_DIR}"
#-XX:+UseParallelGC -XX:+UseParallelGC -XX:ParallelGCThreads=8 
#used for profiling full....
#export GLOBUS_OPTIONS="-Xrunhprof:heap=all,depth=10,cpu=times,cutoff=0,thread=y,file=${FALKON_SERVICE_HOME}/logs/java.hprof.txt -Xms1536M -Xmx1536M -Xss128K -server -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} -DFALKON_SERVICE_HOME=${FALKON_SERVICE_HOME} -DFalkonConfig=${FALKON_SERVICE_HOME}/etc/Falkon.config"
#used for profiling light....
#export GLOBUS_OPTIONS="-Xrunhprof:heap=sites,depth=1,cpu=samples,thread=y,file=${FALKON_SERVICE_HOME}/logs/java.hprof.txt -Xms1536M -Xmx1536M -Xss128K -server -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} -DFALKON_SERVICE_HOME=${FALKON_SERVICE_HOME} -DFalkonConfig=${FALKON_SERVICE_HOME}/etc/Falkon.config"

echo "${MACH_ID} ${FALKON_PORT}" >> ${FALKON_CONFIG}/Client-service-URIs.config                             

echo "starting GT4.0.4 container based on config file ${CONFIG_FILE} and with Falkon service on port ${FALKON_PORT}..." 
globus-start-container -nosec -p ${FALKON_PORT}
#globus-start-container -p ${FALKON_PORT}

