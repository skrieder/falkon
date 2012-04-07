#!/bin/bash

#source /home/iraicu/.bashrc
         
 if [ -z "$3" ]; then 
              echo "usage: $0 <LIFETIME> <IDLE_TIME> <MAX_NUM_WORKERS>"
              echo "usage: $0 0 0"
              exit
          fi
                           
export HOME_PATH=`pwd`
export MACH_ID=`uname -n`


PLATFORM="mips"


export LIFETIME=$1

export IDLE_TIME=$2




            #export LOG_FILE=logs/worker.${MACH_ID}.${RANDOM}.txt
            export LOG_FILE=/dev/null
            
            #echo "Starting C Executor and writing log to ${LOG_FILE}..."
            #/bin/date >> "${LOG_FILE}"

RAM_PATH="/tmp"
DIR_RAM="falkon-worker-cache"
START_LOCK="falkon-worker-cache-lock-started"
END_LOCK="falkon-worker-cache-lock-finished"

mkdir ${RAM_PATH}/${START_LOCK} >> /dev/null  2>&1

EXIT_CODE=$? 

if [ "${EXIT_CODE}" -ne "0" ]; then


    echo "waiting for data to be copied from GPFS to RAM" >> /dev/null
    ((i = 0))      # a=1
    while (( i == 0 ))
    do
    
        if [ -d "${RAM_PATH}/${END_LOCK}" ]; then
            ((i = 1))
        fi

        if [ ! -d "${RAM_PATH}/${END_LOCK}" ]; then
            /bin/sleep 1
        fi
    done

else

         


    if [ ! -f "${HOME_PATH}/src-c/BGexec_${PLATFORM}" ]; then
        echo "executable doesn't exist... exiting"
        exit -2
    fi

    echo "copying over data from GPFS to RAM"

    if [ ! -d "${RAM_PATH}/${DIR_RAM}" ]; then
        mkdir -p ${RAM_PATH}/${DIR_RAM}
    fi
    cp ${HOME_PATH}/src-c/BGexec_${PLATFORM} ${RAM_PATH}/${DIR_RAM}/

    EXIT_CODE=$? 

    if [ "${EXIT_CODE}" -ne "0" ]; then
        echo "Error in cp ${HOME_PATH}/src-c/BGexec_${PLATFORM} ${RAM_PATH}/${DIR_RAM}/... exit code ${EXIT_CODE}"
        exit ${EXIT_CODE}
    fi

    cd ${RAM_PATH}/${DIR_RAM}

    if [ ! -f "BGexec_${PLATFORM}" ]; then
        echo "command doesn't exist... exiting"
        exit -4
    fi


    mkdir -p ${RAM_PATH}/${END_LOCK}


    EXIT_CODE=$? 

    if [ "${EXIT_CODE}" -ne "0" ]; then
        echo "Error in mkdir ${RAM_PATH}/${END_LOCK}... exit code ${EXIT_CODE}"
        exit ${EXIT_CODE}
    fi
   

fi



if [ ! -d "${RAM_PATH}/${DIR_RAM}" ]; then
    echo "working directory on RAM disk is not created, maybe we ran out of space.... exiting"
    exit -3
fi


cd ${RAM_PATH}/${DIR_RAM}
#cp -u ${HOME_PATH}/src-c/BGexec_${PLATFORM} .

#export SERVICE_FILE=${HOME_PATH}/ServiceName.txt
#cp -u ${HOME_PATH}/ServiceName.txt ${RAM_PATH}/${DIR_RAM}/ServiceName.txt

 
#export FALKON_SERVICE_IP=`head -n 1 ${RAM_PATH}/${DIR_RAM}/ServiceName.txt`
export FALKON_SERVICE_IP=`head -n 1 ${HOME_PATH}/ServiceName.txt`



            /bin/echo "starting worker ${MACH_ID} and connecting to ${FALKON_SERVICE_IP}..."
	export SLEEP_TIME=$[$RANDOM/500]
	/bin/sleep ${SLEEP_TIME}


for ((b=0; b < 100 ; b++))
do
            ${RAM_PATH}/${DIR_RAM}/BGexec_${PLATFORM} ${FALKON_SERVICE_IP} 55000 55001 -perf >> "${LOG_FILE}" 2>&1
#${RAM_PATH}/${DIR_RAM}/BGexec_${PLATFORM} ${FALKON_SERVICE_IP} 55000 55001 -debug	
/bin/sleep 1
done           
/bin/echo "finished!" >> "${LOG_FILE}"

