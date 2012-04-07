#!/bin/bash

#source /home/iraicu/.bashrc
         
 if [ -z "$6" ]; then 
              echo "usage: $0 <stackListName> <numStacks> <height> <width> <resultFile> <numThreads>"
              echo "usage: $0 /disks/scratchgpfs1/iraicu/sdss.temp.txt 1 100 100 result.fit 1"
              exit
          fi
                           
#VERSION=1.2
                                                                    
#export HOME_PATH=`pwd`
#export GLOBUS_LOCATION=${HOME_PATH}
#export GLOBUS_PATH=${GLOBUS_LOCATION}
#export LD_LIBRARY_PATH=${GLOBUS_LOCATION}/lib
#export LD_LIBRARY_PATH=/disks/scratchgpfs1/iraicu/ModLyn/intel/9.1.049/lib:$LD_LIBRARY_PATH
#export MACH_ID=`uname -n`
#export EXP_START=`date +%Y.%m.%d_%k.%M.%S`
#export SCRATCH=scratch
#export EPR_FILE=${HOME_PATH}/WorkerEPR.txt

#cd ..
#export JAVA_HOME=`pwd`/jre
#cd ${HOME_PATH}
#JAVA_TEST=`uname -a | grep "ia64" -c`

# if [ ${JAVA_TEST} -eq 0 ]; then 
#        export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia32
#        echo "IA32" > java_test.${RANDOM}.txt
#          fi

# if [ ${JAVA_TEST} -eq 1 ]; then 
#        export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia64
#        echo "IA64" > java_test.${RANDOM}.txt
#          fi


#export JAVA_HOME=/home/iraicu/jdk1.5.0_06
#export JAVA_HOME=/home/iraicu/jdk1.6.0
#export JAVA_HOME=/home/iraicu/j2sdk1.4.2_14
#export PATH=${GLOBUS_LOCATION}:${PATH}
#export PATH=${JAVA_HOME}:${JAVA_HOME}/bin:${GLOBUS_LOCATION}:${GLOBUS_LOCATION}/bin:${PATH}


#source ${GLOBUS_LOCATION}/etc/globus-devel-env.sh

          

#export MAX_THREADS=10
                    
            #export LOG_FILE=logs/worker.${MACH_ID}.${RANDOM}.txt
   #         export LOG_FILE=/dev/null
            
            echo "Starting GenericWorker and writing log to ${LOG_FILE}..."
            #${JAVA_HOME}/bin/java -Xrs -Xmx128m -classpath $CLASSPATH:${HOME_PATH} org/globus/GenericPortal/clients/GPService_instance/ImageStackingMain $1 $2 $3 $4 $5  >> "${LOG_FILE}" 2>&1
            ${JAVA_HOME}/bin/java -Xrs -Xms1536m -Xmx1536m -classpath $CLASSPATH:${HOME_PATH} org/globus/GenericPortal/clients/GPService_instance/ImageStackingMain $1 $2 $3 $4 $5 $6

