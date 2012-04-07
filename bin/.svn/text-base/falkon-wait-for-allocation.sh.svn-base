#!/bin/bash 

#echo "==========================================================================================================="

 if [ -z "$2" ]; then 
              echo "usage: $0 <CLIENT_CONFIG_FILE> <NUMBER_SERVICES>"
              echo "$0 ${FALKON_CONFIG}/Client-service-URIs.config 16"
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
          
CLIENT_CONFIG_FILE=$1 
NUMBER_SERVICES=$2

   echo "waiting to find at least ${NUMBER_SERVICES} services in file ${CLIENT_CONFIG_FILE}..."


   NUM_LINES=`wc ${CLIENT_CONFIG_FILE} | gawk '{ print $1 }'`

for ((b=NUM_LINES; b <= ${NUMBER_SERVICES} ; b=NUM_LINES))
do
   /bin/sleep 10
   NUM_LINES=`wc ${CLIENT_CONFIG_FILE} | gawk '{ print $1 }'`
done           

echo "all done, file has found at least ${NUMBER_SERVICES} services"

