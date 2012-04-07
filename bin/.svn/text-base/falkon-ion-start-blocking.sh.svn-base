#!/bin/bash

    MACH_ID=`uname -n`
       #this should be done via some configuration parameter
    FALKON_HOME=/home/falkon/falkon
    #FALKON_HOME=/home/falkon/falkon
    LOG_FILE_DEBUG="${FALKON_HOME}/logs/provisioner/STATUS_ION_${MACH_ID}"
    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking : starting the I/O node Falkon startup" >> ${LOG_FILE_DEBUG}



    FALKON_PORT=50001
IP=`hostname -i`

    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking : found IP: ${IP}" >> ${LOG_FILE_DEBUG}



    cd ${FALKON_HOME}
    EXIT_CODE=$? 

    if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in executing command 'cd ${FALKON_HOME}'... exit code ${EXIT_CODE}"
    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking : failed : Error in executing command 'cd ${FALKON_HOME}'... exit code ${EXIT_CODE}" >> ${LOG_FILE_DEBUG}
    exit ${EXIT_CODE}
    fi

    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking : changed dir to ${FALKON_HOME}" >> ${LOG_FILE_DEBUG}

    source falkon.env.bgp-io
    EXIT_CODE=$? 

        if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in executing command 'source falkon.env.bgp-io'... exit code ${EXIT_CODE}"
    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking : failed : Error in executing command 'source falkon.env.bgp-io'... exit code ${EXIT_CODE}" >> ${LOG_FILE_DEBUG}
    exit ${EXIT_CODE}
    fi

    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking : performed source falkon.env.bgp-io" >> ${LOG_FILE_DEBUG}


if [ -z "${FALKON_CONFIG}" ]; then
    echo "ERROR: environment variable FALKON_CONFIG not defined"
        DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking : failed : environment variable FALKON_CONFIG not defined... exit code ${EXIT_CODE}" >> ${LOG_FILE_DEBUG}

    exit 1
fi




   #need to fix
   cd ${JAVA_HOME}/bin
    EXIT_CODE=$? 

    if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in executing command 'cd ${JAVA_HOME}'... exit code ${EXIT_CODE}"
        DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking : failed : Error in executing command 'cd ${JAVA_HOME}'... exit code ${EXIT_CODE}" >> ${LOG_FILE_DEBUG}
    exit ${EXIT_CODE}
    fi
    
    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking : changed dir to ${JAVA_HOME}/bin" >> ${LOG_FILE_DEBUG}


    falkon-service.sh ${FALKON_PORT} ${FALKON_CONFIG}/Falkon-TCPCore.config


    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking : started the GT4 container on port ${FALKON_PORT} and using config file ${FALKON_CONFIG}/Falkon-TCPCore.config" >> ${LOG_FILE_DEBUG}

    sleep 10

    
    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking : slept for 10 sec to give service time to start" >> ${LOG_FILE_DEBUG}

    falkon-tcpcore-start.sh ${IP} ${FALKON_PORT}


    EXIT_CODE=$? 

if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in executing command `falkon-tcpcore-start.sh`... exit code ${EXIT_CODE}"
    echo "${IP}${FALKON_PORT}" >> ${FALKON_CONFIG}/Client-service-URIs-bad.config
        DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking : failed : Error in executing command `falkon-tcpcore-start.sh`... exit code ${EXIT_CODE}" >> ${LOG_FILE_DEBUG}
    
    exit ${EXIT_CODE}
fi

    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking : started the Falkon service succesfully" >> ${LOG_FILE_DEBUG}


echo "${IP}${FALKON_PORT}" >> ${FALKON_CONFIG}/Client-service-URIs.config
    EXIT_CODE=$? 

if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in executing command `echo IP PORT ` to ${FALKON_CONFIG}/Client-service-URIs.config... exit code ${EXIT_CODE}"
    
        DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking : failed : Error in executing command `echo IP PORT ` to ${FALKON_CONFIG}/Client-service-URIs.config... exit code ${EXIT_CODE}" >> ${LOG_FILE_DEBUG}
    exit ${EXIT_CODE}
fi

    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking : registered the service by appending ${IP}${FALKON_PORT} to ${FALKON_CONFIG}/Client-service-URIs.config" >> ${LOG_FILE_DEBUG}



echo "exit code ${EXIT_CODE}"
    DATE=`date`

    echo "${DATE} : falkon-ion-start-blocking : completed the I/O node Falkon startup!" >> ${LOG_FILE_DEBUG}


exit ${EXIT_CODE}


                   
