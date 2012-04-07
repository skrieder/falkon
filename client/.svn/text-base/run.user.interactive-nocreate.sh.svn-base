#!/bin/bash

 if [ -z "$4" ]; then 
              echo "usage: $0 <GT4_IP> <GT4_PORT> <EPR_FILE> <SECURITY_OPTIONS>"
              exit
          fi

export HOME_PATH=`pwd`
export GLOBUS_LOCATION=${HOME_PATH}
export GLOBUS_PATH=${HOME_PATH}
export LD_LIBRARY_PATH=${HOME_PATH}/lib
export SCRATCH=${HOME_PATH}/scratch/
export GenericPortalIP=$1
export GenericPortalPORT=$2
export EPR_FILE=${SCRATCH}/$3
#export EXEC_FILE="10c"

export SECURITY_DESCRIPTION_FILE=$4

                                    
#export JAVA_HOME=/home/iraicu/jdk1.5.0_06
export JAVA_HOME=/home/iraicu/jdk1.6.0
export PATH=${JAVA_HOME}:${JAVA_HOME}/bin:${GLOBUS_LOCATION}:${PATH}

source ${GLOBUS_LOCATION}/etc/globus-devel-env.sh


#-authenticate -authorize -encrypt -sign -TSL -MSG -CONV
#echo "Creating GenericPortal resource..."                                                                                                                                  
#java -Djavax.net.ssl.keyStore=/etc/grid-security/certificates/b57985f0.0 -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate https://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService ${EPR_FILE}
#java -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate -s https://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -z host https://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService ${EPR_FILE} 
#java -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate -factoryURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -epr ${EPR_FILE} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} 
#/scratch/local/iraicu/GT-4.03/bin/genericclient-create https://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService ${EPR_FILE}
echo "Starting up the GenericConsole..."                                                                                                                                  
java -Xmx1536m -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/UserRun -epr ${EPR_FILE} -MAX_EXECS_PER_WS 1 -interactive -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -noinit 
#java -Xmx1536m -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/UserRun -epr ${EPR_FILE} -interactive -MAX_EXECS_PER_WS 1 
#echo "Destroying GenericPortal resource..."                                                                                                                                  
#java -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientDestroy -epr ${EPR_FILE} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE}
#echo "Stacking via the GenericPortal is complete!"


