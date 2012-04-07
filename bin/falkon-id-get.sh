#!/bin/bash


 if [ -z "$1" ]; then 
              echo "usage: $0 <LRM_ID>"
              echo "usage: $0 12345"
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

cd ${FALKON_HOME}/id
                                    
#last=`(/bin/ls -1d ${FALKON_HOME}/id/falkon_* | tail -1 | sed -e s/falkon_//)`
last=`(/bin/ls -1d falkon_* | tail -1 | sed -e s/falkon_//)`
#echo $last
next=`echo $last | awk '{printf "%.4d",$1+1}'`
#echo $next

RUNFILE="falkon_${next}"
if [ -e ${FALKON_HOME}/id/$RUNFILE ]; then
  echo $0: Error: ${FALKON_HOME}/id/$RUNFILE exists
  exit -1
fi




   echo "${1}" > ${FALKON_HOME}/id/${RUNFILE}
    #echo "Saved Cobalt ID $1 in ${FALKON_HOME}/id/$RUNFILE"
   echo "${next}"

exit 0
