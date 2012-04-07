#!/bin/bash 

#echo "==========================================================================================================="

 if [ -z "$3" ]; then 
              echo "usage: $0 <FALKON_ID> <NUMBER_NODES> <PERCENT>"
              echo "$0 ${FALKON_CONFIG}/Client-service-URIs.config 64 90"
              exit
          fi
FALKON_ID=$1
cd $FALKON_ROOT/users/$USER/$FALKON_ID
source falkon.env.bgp

if [ -z "${FALKON_SERVICE_HOME}" ]; then
    echo "ERROR: environment variable FALKON_SERVICE_HOME not defined"  1>&2
    return 1
fi

if [ ! -d "${FALKON_SERVICE_HOME}" ]; then
    echo "ERROR: invalid FALKON_SERVICE_HOME set: $FALKON_SERVICE_HOME" 1>&2
    return 1
fi
         
CLIENT_CONFIG_FILE=${FALKON_CONFIG}/Client-service-URIs.config 
NUM_NODES=$2
PERC=$3
let FACTOR=64*100/PERC
let NUM_ION=NUM_NODES/FACTOR


#NUMBER_SERVICES=$2

   echo "waiting to find at least ${NUM_ION} services in file ${CLIENT_CONFIG_FILE}..."


   NUM_LINES=`wc ${CLIENT_CONFIG_FILE} | gawk '{ print $1 }'`

for ((b=NUM_LINES; b <= ${NUM_ION} ; b=NUM_LINES))
do
   /bin/sleep 10
   NUM_LINES=`wc ${CLIENT_CONFIG_FILE} | gawk '{ print $1 }'`
done           

echo "all done, file has found at least ${NUM_ION} services"

