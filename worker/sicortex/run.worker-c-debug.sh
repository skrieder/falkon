#!/bin/bash

#source /home/iraicu/.bashrc
         
 if [ -z "$3" ]; then 
              echo "usage: $0 <LIFETIME> <IDLE_TIME> <MAX_NUM_WORKERS>"
              echo "usage: $0 0 0"
              exit
          fi
                           
VERSION=1.2
                                                                    
export HOME_PATH=`pwd`
export MACH_ID=`uname -n`

JAVA_TEST=`uname -a | grep "ia64" -c`

PLATFORM="ia32"

 if [ ${JAVA_TEST} -eq 0 ]; then 
#        export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia32
        PLATFORM="ia32"
        #echo "IA32" > java_test.${RANDOM}.txt
          fi

if [ ${JAVA_TEST} -eq 1 ]; then 
#        export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia64
        PLATFORM="ia64"
       #echo "IA64" > java_test.${RANDOM}.txt
         fi

PLATFORM="mips"


export LIFETIME=$1

export IDLE_TIME=$2


export SERVICE_FILE=${HOME_PATH}/ServiceName.txt

 
export FALKON_SERVICE_IP=`head -n 1 ${SERVICE_FILE}`

#        export SLEEP_TIME=$[$RANDOM/250]
#       /bin/sleep ${SLEEP_TIME}

#
#for i in `seq 2 $3`;
#        do
#                #echo $i

            #export LOG_FILE=logs/worker.${MACH_ID}.${RANDOM}.txt
#            export LOG_FILE=/dev/null
            
#            echo "Starting C Executor and writing log to ${LOG_FILE}..."
            #/bin/date >> "${LOG_FILE}"

#            /bin/echo "starting... connecting to ${FALKON_SERVICE_IP}..." >> "${LOG_FILE}"

 #           ${HOME_PATH}/src-c/BGexec_${PLATFORM} ${FALKON_SERVICE_IP} 56000 56001 -perf >> "${LOG_FILE}" 2>&1 &
            #${HOME_PATH}/src-c/BGexec_${PLATFORM} ${FALKON_SERVICE_IP} 56000 56001 -debug
            #${HOME_PATH}/src-c/BGexec_${PLATFORM} ${FALKON_SERVICE_IP} 56000 56001 -perf
#            /bin/echo "finished!" >> "${LOG_FILE}"
            #/bin/date >> "${LOG_FILE}"

#        done    

            #export LOG_FILE=logs/worker.${MACH_ID}.${RANDOM}.txt
            export LOG_FILE=/dev/null
            
            echo "Starting C Executor and writing log to ${LOG_FILE}..."
            #/bin/date >> "${LOG_FILE}"

            /bin/echo "starting... connecting to ${FALKON_SERVICE_IP}..." >> "${LOG_FILE}"
#	export SLEEP_TIME=$[$RANDOM/500]
#	/bin/sleep ${SLEEP_TIME}

#for ((b=0; b < 100 ; b++))
#do
           # ${HOME_PATH}/src-c/BGexec_${PLATFORM} ${FALKON_SERVICE_IP} 56000 56001 -perf >> "${LOG_FILE}" 2>&1
${HOME_PATH}/src-c/BGexec ${FALKON_SERVICE_IP} 56000 56001 -debug	
#/bin/sleep 1
#done           
 /bin/echo "finished!" >> "${LOG_FILE}"

