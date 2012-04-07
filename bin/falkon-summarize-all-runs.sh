#!/bin/bash

 if [ -z "$1" ]; then 
              echo "usage: $0 <TIME_QUANTA_SEC>"
              echo "usage: $0 60"
              echo "-----------"

              echo "usage: $0 <TIME_QUANTA_SEC> <LOGS_PATH>"
              echo "usage: $0 60 $HOME/logs"
              exit 1
          fi


if [ -z "${FALKON_HOME}" ]; then
    echo "ERROR: environment variable FALKON_HOME not defined"
    exit 1
fi

if [ ! -d "${FALKON_HOME}" ]; then
    echo "ERROR: invalid FALKON_CLIENT_HOME set: $FALKON_CLIENT_HOME"
    exit 1
fi

TIME_QUANTA=$1
               

FALKON_UTILITIES=${FALKON_HOME}/utilities

PATH_LOGS=${FALKON_ROOT}/users
             
if [ ! -z $2 ];then
   PATH_LOGS=$2
fi 

OUTPUT_FILE=`pwd`/falkon_summary_${TIME_QUANTA}.txt

java -Xms4096M -Xmx4096M -classpath $FALKON_UTILITIES MergeFalkonSummary ${PATH_LOGS} ${TIME_QUANTA} > ${OUTPUT_FILE}
