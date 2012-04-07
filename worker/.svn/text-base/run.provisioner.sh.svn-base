#!/bin/bash


 if [ -z "$2" ]; then 
              echo "usage: $0 <ProvisionerConfigFile> <ConfigFilePollInterval_sec>"
              echo "usage: $0 ${FALKON_CONFIG}/Provisioner.config 60"
              exit 1
          fi



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


#EXP_START=`date +%Y.%m.%d.%k.%M.%S`
EXP_START=`date +%Y.%m.%d_%H.%M.%S`


LOG_DIR=${FALKON_LOGS}/provisioner/${EXP_START}
mkdir -p ${LOG_DIR} 
LOG_FILE=${LOG_DIR}/drp.txt
#LOG_FILE=/dev/null

cd ${FALKON_WORKER_HOME}
echo "Starting up the Provisioner..."
#java -classpath ${HOME_PATH}:${CLASSPATH}:. -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} org/globus/GenericPortal/clients/GPService_instance/WorkerRunGram -serviceURI http://${GenericPortalIP}:50002/wsrf/services/GenericPortal/core/WS/GPFactoryService -maxWallTime $4 -executable $3 -hostType $5 -hostCount $6 -contact $2  > "${HOME_PATH}/logs/gram.txt" 2>&1 &
java -Xrs -Xmx512m -Djava.security.egd=file:///dev/urandom -DFALKON_LOGS=${LOG_DIR} -classpath ${GLOBUS_LOCATION}:${FALKON_WORKER_HOME}:${CLASSPATH} -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} -DFALKON_WORKER_HOME=${FALKON_WORKER_HOME} -DFALKON_HOME=${FALKON_HOME} org/globus/GenericPortal/clients/GPService_instance/WorkerRunGram -config_file $1 -config_poll $2 > ${LOG_FILE} &


