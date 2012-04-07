#!/bin/bash

 if [ -z "$3" ]; then 
              echo "usage: $0 <GT4_IP> <GT4_PORT> <POLL_TIME>"
              exit 1
          fi

if [ -z "${FALKON_MONITOR_HOME}" ]; then
    echo "ERROR: environment variable FALKON_MONITOR_HOME not defined"  1>&2
    return 1
fi

if [ ! -d "${FALKON_MONITOR_HOME}" ]; then
    echo "ERROR: invalid FALKON_MONITOR_HOME set: $FALKON_MONITOR_HOME" 1>&2
    return 1
fi
          
if [ -z "${GLOBUS_LOCATION}" ]; then
    echo "ERROR: environment variable GLOBUS_LOCATION not defined"  1>&2
    return 1
fi

if [ ! -d "${GLOBUS_LOCATION}" ]; then
    echo "ERROR: invalid GLOBUS_LOCATION set: $GLOBUS_LOCATION" 1>&2
    return 1
fi

GenericPortalIP=$1
GenericPortalPORT=$2
SECURITY_DESCRIPTION_FILE=${FALKON_CONFIG}/client-nosecurity-config.xml
            
echo "Starting Falkon TOP-Monitor and writing log to ${LOG_FILE}..."
#${JAVA_HOME}/bin/java -Xmx128m -classpath $CLASSPATH:${HOME_PATH} org/globus/GenericPortal/clients/GPService_instance/MonitorGUI >> "${LOG_FILE}" 2>&1
java -Xmx128m -classpath $CLASSPATH:${FALKON_MONITOR_HOME} org/globus/GenericPortal/clients/GPService_instance/MonitorGUI -serviceURI http://${GenericPortalIP}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE} -monitor $3 -mode_text_ui



