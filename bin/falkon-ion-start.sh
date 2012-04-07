#!/bin/bash



    MACH_ID=`uname -n`
       #this should be done via some configuration parameter
    FALKON_HOME=/home/falkon/falkon
    #FALKON_HOME=/home/falkon/falkon
    LOG_FILE_DEBUG="${FALKON_HOME}/logs/provisioner/STATUS_ION_${MACH_ID}"
    DATE=`date`
    echo "${DATE} : falkon-ion-start : starting the I/O node Falkon startup in the background..." >> ${LOG_FILE_DEBUG}


if [ ! -e "${FALKON_HOME}/bin/falkon-ion-start-blocking.sh" ]; then
    echo "ERROR: invalid script ${FALKON_HOME}/bin/falkon-ion-start-blocking.sh"
    exit 1
fi

    ${FALKON_HOME}/bin/falkon-ion-start-blocking.sh &


    EXIT_CODE=$? 
    DATE=`date`

    echo "${DATE} : falkon-ion-start : completed the I/O node Falkon startup in the background..." >> ${LOG_FILE_DEBUG}


exit ${EXIT_CODE}


                   
