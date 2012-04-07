#!/bin/bash


 if [ -z "$2" ]; then 
              echo "usage: $0 <FalkonID> <LRM_ID>"
              echo "usage: $0 0001 12345"
              exit -1
          fi

          
if [ -z "${FALKON_HOME}" ]; then
    echo "ERROR: environment variable FALKON_HOME not defined"
    exit -1
fi


if [ ! -d "${FALKON_HOME}/id" ]; then
    echo "ERROR: invalid path ${FALKON_HOME}/id..."
    exit -1
fi

#cd ${FALKON_HOME}/id

                                    
#last=`(/bin/ls -1d ${FALKON_HOME}/id/falkon_* | tail -1 | sed -e s/falkon_//)`
#last=`(/bin/ls -1d falkon_* | tail -1 | sed -e s/falkon_//)`
#echo $last
#next=`echo $last | awk '{printf "%.4d",$1+1}'`
#echo $next

RUNFILE="${FALKON_HOME}/id/falkon_${1}"
if [ -e $RUNFILE ]; then
   echo "${2}" > ${RUNFILE}
    exit 0
fi

  echo "$0: Error: $RUNFILE does not exist, cannot update a non-existing Falkon ID..."
  exit -1





