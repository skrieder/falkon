#!/bin/bash

 if [ -z "$4" ]; then 
              echo "usage: $0 <GT4_IP> <GT4_PORT> <EXEC_FILE> <EPR_FILE>"
              exit
          fi

export HOME_PATH=`pwd`
export GLOBUS_LOCATION=${HOME_PATH}
export GLOBUS_PATH=${HOME_PATH}
export LD_LIBRARY_PATH=${HOME_PATH}/lib
export SCRATCH=${HOME_PATH}/scratch/
export GenericPortalIP=$1
export GenericPortalPORT=$2
export EXEC_FILE=$3
export EPR_FILE=${SCRATCH}/$4

export JAVA_HOME=/home/iraicu/jdk1.6.0
export PATH=${JAVA_HOME}:${JAVA_HOME}/bin:${GLOBUS_LOCATION}:${PATH}

source ${GLOBUS_LOCATION}/etc/globus-devel-env.sh



        for i in `seq 0 1000`;
        do
                #echo $i
                
                export EPR_FILE=${SCRATCH}/epr${i}.txt
                echo "Creating GenericPortal resource and saving to ${EPR_FILE}..."                                                                                                                                  
                java -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService ${EPR_FILE} 

                echo "Starting up the GenericConsole from ${EPR_FILE}..."                                                                                                                                  
                java -Xmx1536m -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/UserRun -epr ${EPR_FILE} -job_description ${EXEC_FILE} -num_execs 1 -debug

                echo "Destroying GenericPortal resource from ${EPR_FILE}..."                                                                                                                                  
                java -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientDestroy -epr ${EPR_FILE}

                
        done         

     
        for i in `seq 0 1000`;
        do
                export EPR_FILE=${SCRATCH}/epr${i}.txt
                
                #echo "Destroying GenericPortal resource from ${EPR_FILE}..."                                                                                                                                  
                #java -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientDestroy -epr ${EPR_FILE}
                #rm ${EPR_FILE}

        done         
