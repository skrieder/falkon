#!/bin/bash

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


            echo "Starting Falkon Monitor..."
            #${JAVA_HOME}/bin/java -Xmx128m -classpath $CLASSPATH:${HOME_PATH} org/globus/GenericPortal/clients/GPService_instance/MonitorGUI >> "${LOG_FILE}" 2>&1
            java -Xmx128m -Djava.security.egd=file:///dev/urandom -classpath $CLASSPATH:${FALKON_MONITOR_HOME} org/globus/GenericPortal/clients/GPService_instance/MonitorGUI -mode_gui


