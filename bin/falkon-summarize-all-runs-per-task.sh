#!/bin/bash

if [ -z "${FALKON_HOME}" ]; then
    echo "ERROR: environment variable FALKON_HOME not defined"
    exit 1
fi

if [ ! -d "${FALKON_HOME}" ]; then
    echo "ERROR: invalid FALKON_CLIENT_HOME set: $FALKON_CLIENT_HOME"
    exit 1
fi

FALKON_UTILITIES=${FALKON_HOME}/utilities

PATH_LOGS=${FALKON_ROOT}/users
             
if [ ! -z $1 ];then
   PATH_LOGS=$1
fi 

java -Xms1536M -Xmx1536M -classpath $FALKON_UTILITIES SummarizeTaskPerfDir ${PATH_LOGS}
