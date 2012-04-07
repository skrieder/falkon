#!/bin/bash

if [ -z "${FALKON_HOME}" ]; then
    echo "ERROR: environment variable FALKON_HOME not defined"
    return 1
fi

if [ ! -d "${FALKON_HOME}" ]; then
    echo "ERROR: invalid FALKON_HOME set: $FALKON_HOME"
    return 1
fi

cd ${FALKON_HOME}

ant all     

chmod g+rws ${FALKON_HOME}/container/etc/org_globus_GenericPortal_services_core_WS/server-config.wsdd
chmod g+rws ${FALKON_HOME}/container/etc/org_globus_GenericPortal_services_core_WS/jndi-config.xml
