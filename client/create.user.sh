#!/bin/bash

 if [ -z "$1" ]; then 
              echo "usage: $0 <GT4_IP>"
              exit
          fi
  
export HOME_PATH=`pwd`
export GLOBUS_LOCATION=${HOME_PATH}
export GLOBUS_PATH=${HOME_PATH}
export LD_LIBRARY_PATH=${HOME_PATH}/lib
#export GenericPortalIP=192.5.198.97
export GenericPortalIP=$1
export SCRATCH=${HOME_PATH}/scratch/
export EPR_FILE=${SCRATCH}/epr-user.txt

source ${GLOBUS_LOCATION}/etc/globus-devel-env.sh

java -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate http://${GenericPortalIP}:50002/wsrf/services/GenericPortal/core/WS/GPFactoryService ${EPR_FILE}
