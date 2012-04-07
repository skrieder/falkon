#!/bin/bash

#ERROR_LOG=/home/falkon/users/error.log
ERROR_LOG=/dev/null


 if [ -z "$1" ]; then 
              echo "usage: $0 <FALKON_HOME>" >> ${ERROR_LOG}
              echo "usage: $0 /home/falkon/falkon" >> ${ERROR_LOG}
              exit 1
          fi

    MACH_ID=`uname -n`
LOG_FILE_DEBUG="${1}/logs/provisioner/STATUS_ION_${MACH_ID}"

echo "LOG_FILE_DEBUG ==> ${LOG_FILE_DEBUG}" >> ${ERROR_LOG}
                           
cd $1

source falkon.env.bgp-io
    EXIT_CODE=$? 
    if [ "${EXIT_CODE}" -ne "0" ]; then
        echo "Error in executing command 'source falkon.env.bgp-io'... exit code ${EXIT_CODE}" >> ${ERROR_LOG}
        DATE=`date`
        echo "${DATE} : falkon-ion-start-blocking-zeptocn : failed : Error in executing command 'source falkon.env.bgp-io'... exit code ${EXIT_CODE}" >> ${LOG_FILE_DEBUG}
        exit ${EXIT_CODE}
    fi


              
if [ -z "${FALKON_HOME}" ]; then
    echo "ERROR: environment variable FALKON_HOME not defined"  >> ${ERROR_LOG}
    exit 1
fi
    
       #this should be done via some configuration parameter
    #FALKON_HOME=/gpfs1/falkon/falkon
    #FALKON_HOME=/home/falkon/falkon
    #LOG_FILE_DEBUG="${FALKON_HOME}/logs/provisioner/STATUS_ION_${MACH_ID}"
    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking-zeptocn : starting the I/O node Falkon startup" >> ${LOG_FILE_DEBUG}



    FALKON_PORT=50001
IP=`hostname -i`

    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking-zeptocn : found IP: ${IP}" >> ${LOG_FILE_DEBUG}



    #cd ${FALKON_HOME}
    #EXIT_CODE=$? 

    #if [ "${EXIT_CODE}" -ne "0" ]; then
    #echo "Error in executing command 'cd ${FALKON_HOME}'... exit code ${EXIT_CODE}"  >> ${ERROR_LOG}
    #DATE=`date`
    #echo "${DATE} : falkon-ion-start-blocking-zeptocn : failed : Error in executing command 'cd ${FALKON_HOME}'... exit code ${EXIT_CODE}" >> ${LOG_FILE_DEBUG}
    #exit ${EXIT_CODE}
    #fi

    #DATE=`date`
    #echo "${DATE} : falkon-ion-start-blocking-zeptocn : changed dir to ${FALKON_HOME}" >> ${LOG_FILE_DEBUG}

    #source falkon.env.bgp-io
    #EXIT_CODE=$? 

    #    if [ "${EXIT_CODE}" -ne "0" ]; then
    #echo "Error in executing command 'source falkon.env.bgp-io'... exit code ${EXIT_CODE}" >> ${ERROR_LOG}
    #DATE=`date`
    #echo "${DATE} : falkon-ion-start-blocking-zeptocn : failed : Error in executing command 'source falkon.env.bgp-io'... exit code ${EXIT_CODE}" >> ${LOG_FILE_DEBUG}
    #exit ${EXIT_CODE}
    #fi

    #DATE=`date`
    #echo "${DATE} : falkon-ion-start-blocking-zeptocn : performed source falkon.env.bgp-io" >> ${LOG_FILE_DEBUG}


if [ -z "${FALKON_CONFIG}" ]; then
    echo "ERROR: environment variable FALKON_CONFIG not defined" >> ${ERROR_LOG}
        DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking-zeptocn : failed : environment variable FALKON_CONFIG not defined... exit code ${EXIT_CODE}" >> ${LOG_FILE_DEBUG}

    exit 1
fi




   #need to fix
   cd ${JAVA_HOME}/bin
    EXIT_CODE=$? 

    if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in executing command 'cd ${JAVA_HOME}'... exit code ${EXIT_CODE}" >> ${ERROR_LOG}
        DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking-zeptocn : failed : Error in executing command 'cd ${JAVA_HOME}'... exit code ${EXIT_CODE}" >> ${LOG_FILE_DEBUG}
    exit ${EXIT_CODE}
    fi
    
    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking-zeptocn : changed dir to ${JAVA_HOME}/bin" >> ${LOG_FILE_DEBUG}


    falkon-service.sh ${FALKON_PORT} ${FALKON_CONFIG}/Falkon-TCPCore.config


    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking-zeptocn : started the GT4 container on port ${FALKON_PORT} and using config file ${FALKON_CONFIG}/Falkon-TCPCore.config" >> ${LOG_FILE_DEBUG}

    sleep 10

    
    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking-zeptocn : slept for 10 sec to give service time to start" >> ${LOG_FILE_DEBUG}

LOG_DIR=${FALKON_LOGS}/service/
                                                    
    falkon-tcpcore-start.sh ${IP} ${FALKON_PORT} >> ${LOG_DIR}/falkon-tcpcore-start.log


    EXIT_CODE=$? 

if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in executing command `falkon-tcpcore-start.sh`... exit code ${EXIT_CODE}" >> ${ERROR_LOG}
    echo "${IP}${FALKON_PORT}" >> ${FALKON_CONFIG}/Client-service-URIs-bad.config
        DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking-zeptocn : failed : Error in executing command `falkon-tcpcore-start.sh`... exit code ${EXIT_CODE}" >> ${LOG_FILE_DEBUG}
    
    exit ${EXIT_CODE}
fi

    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking-zeptocn : started the Falkon service succesfully" >> ${LOG_FILE_DEBUG}


echo "${IP}${FALKON_PORT}" >> ${FALKON_CONFIG}/Client-service-URIs.config
    EXIT_CODE=$? 

if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in executing command `echo IP PORT ` to ${FALKON_CONFIG}/Client-service-URIs.config... exit code ${EXIT_CODE}" >> ${ERROR_LOG}
    
        DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking-zeptocn : failed : Error in executing command `echo IP PORT ` to ${FALKON_CONFIG}/Client-service-URIs.config... exit code ${EXIT_CODE}" >> ${LOG_FILE_DEBUG}
    exit ${EXIT_CODE}
fi

    DATE=`date`
    echo "${DATE} : falkon-ion-start-blocking-zeptocn : registered the service by appending ${IP}${FALKON_PORT} to ${FALKON_CONFIG}/Client-service-URIs.config" >> ${LOG_FILE_DEBUG}



echo "exit code ${EXIT_CODE}"
    DATE=`date`

    echo "${DATE} : falkon-ion-start-blocking-zeptocn : completed the I/O node Falkon startup!" >> ${LOG_FILE_DEBUG}


exit ${EXIT_CODE}


                   
