#!/bin/bash

 if [ -z "$1" ]; then 
              echo "usage: $0 <USER_ID>"
              echo "usage: $0 iraicu"

              exit 1
          fi

if [ -z "${FALKON_ROOT}" ]; then
    echo "ERROR: environment variable FALKON_ROOT not defined"
    exit 1
fi

FALKON_UTILITIES=${FALKON_HOME}/utilities

USER_ID=$1
             
cd ${FALKON_ROOT}/users/${USER_ID}

java -Xms1536M -Xmx1536M -classpath $FALKON_UTILITIES SummarizeTaskPerfDir ./


