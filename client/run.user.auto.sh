#!/bin/bash

 if [ -z "${11}" ]; then 
              echo "usage: $0 <GT4_IP> <GT4_PORT> <EXEC_FILE> <NUM_EXECS> <NUM_THREADS> <SECURITY_OPTIONS> <task_length> <#_of_executors> <exp_desc> <LOCALITY> <monitor_interval>"
              exit
          fi

export HOME_PATH=`pwd`
export GLOBUS_LOCATION=${HOME_PATH}
export GLOBUS_PATH=${GLOBUS_LOCATION}
export LD_LIBRARY_PATH=${GLOBUS_LOCATION}/lib
#export SCRATCH=${HOME_PATH}/scratch/
export GenericPortalIP=$1
export GenericPortalPORT=$2
#export EPR_FILE=${SCRATCH}/$3
export EXEC_FILE=$3
export NUM_EXECS=$4
export NUM_THREADS=$5

export SECURITY_DESCRIPTION_FILE=$6


                                    
cd ..
#export JAVA_HOME=`pwd`/jre
cd ${HOME_PATH}
#export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia32
#export JAVA_HOME=/home/iraicu/jdk1.5.0_06
#export JAVA_HOME=/home/iraicu/jdk1.6.0
#export JAVA_HOME=/home/iraicu/j2sdk1.4.2_14
export PATH=${GLOBUS_LOCATION}:${PATH}
#export PATH=${JAVA_HOME}:${JAVA_HOME}/bin:${GLOBUS_LOCATION}:${GLOBUS_LOCATION}/bin:${PATH}

source ${GLOBUS_LOCATION}/etc/globus-devel-env.sh


#-authenticate -authorize -encrypt -sign -TSL -MSG -CONV
#echo "Creating GenericPortal resource..."                                                                                                                                  
#java -Djavax.net.ssl.keyStore=/etc/grid-security/certificates/b57985f0.0 -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate https://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService ${EPR_FILE}
#java -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate -s https://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -z host https://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService ${EPR_FILE} 
#java -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate -factoryURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -epr ${EPR_FILE} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE}
#/scratch/local/iraicu/GT-4.03/bin/genericclient-create https://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService ${EPR_FILE}
#echo "Starting up the GenericConsole..."                                                                                                                                  
#java -Xmx1536m -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/UserRun -epr ${EPR_FILE} -MAX_EXECS_PER_WS 1 -job_description ${EXEC_FILE} -num_execs ${NUM_EXECS} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE}

#java -XX:+UseConcMarkSweepGC -Xms256M -Xmx256M -Xss128K -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/UserRun -serviceURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -MAX_EXECS_PER_WS 100 -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -job_description ${EXEC_FILE} -num_execs ${NUM_EXECS} -num_threads ${NUM_THREADS} -monitor 1000
java -XX:+UseConcMarkSweepGC -Xms512M -Xmx512M -Xss128K -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/UserRun -serviceURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -MAX_EXECS_PER_WS 100 -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -job_description ${EXEC_FILE} -num_execs ${NUM_EXECS} -num_threads ${NUM_THREADS} -comment1 $7 -comment2 $8 -comment3 $9 -monitor ${11} -locality ${10}


#java -Xmx1536m -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/UserRun -epr ${EPR_FILE} -interactive -MAX_EXECS_PER_WS 1 
#echo "Destroying GenericPortal resource..."                                                                                                                                  
#java -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientDestroy -epr ${EPR_FILE} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} 
#echo "Stacking via the GenericPortal is complete!"


