#!/bin/bash

#IP=`hostname -i`
#echo ${IP} >> /home/falkon/falkon/logs/provisioner/zoid-log.txt
                       
#startup
    if [ "${1}" -eq "1" ]; then
                            
#ERROR_LOG=/home/falkon/users/error.log
ERROR_LOG=/dev/null

    echo "starting zoid-user-script.sh..." >> ${ERROR_LOG}
    #echo "reviewing the command and arguements: $0 $1 $2 $3 $4 $5 $6 $7..." >> ${ERROR_LOG}
    #tENV=`env`
    #echo "reviewing the envirnoment: $tENV..." >> ${ERROR_LOG}

original=$ZOID_JOB_ARGS
a0=${original%%:*}; rest=${original#*:}
a1=${rest%%:*}; rest=${rest#*:}
a2=${rest%%:*}; rest=${rest#*:}
a3=${rest%%:*}; rest=${rest#*:}
a4=${rest%%:*}; rest=${rest#*:}
a5=${rest%%:*}; rest=${rest#*:}
a6=${rest%%:*}; rest=${rest#*:}
a7=${rest%%:*};

    
    echo "reviewing the command and arguements: $a0 $a1 $a2 $a3 $a4 $a5 $a6 $a7..." >> ${ERROR_LOG}
    
    
#FALKON_JOBID_HOME=/home/falkon/users/${USER}/${ZOID_JOB_ID}
FALKON_JOBID_HOME=${a7}/users/${a5}/${a6}
FALKON_HOME=${a7}/users/${a5}/${a6}
    echo "FALKON_JOBID_HOME: $FALKON_JOBID_HOME" >> ${ERROR_LOG}


if [ ! -d "${FALKON_JOBID_HOME}" ]; then
    echo "ERROR: invalid path ${FALKON_JOBID_HOME}... exiting" >> ${ERROR_LOG}
    exit 1
fi



    MACH_ID=`uname -n`
       #this should be done via some configuration parameter

   cd ${FALKON_JOBID_HOME}
   #source falkon.env.bgp-io

   echo "ION at ${MACH_ID} ==> ${FALKON_HOME}..."  >> ${ERROR_LOG}

    #LOG_FILE_DEBUG="${FALKON_HOME}/logs/provisioner/STATUS_ION_${MACH_ID}"
   LOG_FILE_DEBUG="/tmp/STATUS_ION_${MACH_ID}"
    DATE=`date`
    echo "${DATE} : falkon-ion-start : starting the I/O node Falkon startup in the background..." >> ${LOG_FILE_DEBUG}


if [ ! -e "${FALKON_HOME}/bin/falkon-ion-start-blocking-zeptocn.sh" ]; then
    echo "ERROR: invalid script ${FALKON_HOME}/bin/falkon-ion-start-blocking-zeptocn.sh" >> ${ERROR_LOG}
    exit 1
fi

     #used to run from GPFS
    #${FALKON_JOBID_HOME}/bin/falkon-ion-start-blocking-zeptocn.sh ${FALKON_JOBID_HOME} &
    #used to run from RAM
    ${FALKON_JOBID_HOME}/bin/falkon-ion-start-blocking-zeptocn-ram.sh ${FALKON_JOBID_HOME} &


    EXIT_CODE=$? 
    DATE=`date`

    echo "${DATE} : falkon-ion-start : completed the I/O node Falkon startup in the background..." >> ${LOG_FILE_DEBUG}

   echo "ION at ${MACH_ID} finished OK!"  >> ${ERROR_LOG}

                   
exit ${EXIT_CODE}


    fi


                   
#cleanup
    if [ "${1}" -eq "0" ]; then
        
        killall -9 java                      
        exit 0

    fi
    
