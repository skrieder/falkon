#!/bin/bash


if [ -z "${FALKON_HOME}" ]; then
    echo "ERROR: environment variable FALKON_HOME not defined"  1>&2
    return 1
fi

if [ ! -d "${FALKON_HOME}" ]; then
    echo "ERROR: invalid FALKON_HOME set: $FALKON_HOME" 1>&2
    return 1
fi

cd ${FALKON_HOME}
         
rm -rf swift-libs
mkdir swift-libs
mkdir temp
cp container/lib/org_globus_GenericPortal_common.jar container/lib/org_globus_GenericPortal_services_core_WS_stubs.jar temp/
cd temp
jar xf org_globus_GenericPortal_common.jar 
jar xf org_globus_GenericPortal_services_core_WS_stubs.jar
rm -rf M*
jar cf FalkonStubs.jar org
mv FalkonStubs.jar ../swift-libs/
cd ..
rm -rf temp
