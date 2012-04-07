#!/bin/bash

cd ${FALKON_HOME}

rm -rf ${FALKON_HOME}/service/org/globus/GenericPortal/stubs
rm -rf ${FALKON_HOME}/service/org/globus/GenericPortal/common/*.class
rm -rf ${FALKON_HOME}/service/org/globus/GenericPortal/services/core/WS/impl/*.class
rm -rf ${FALKON_HOME}/service/org/globus/GenericPortal/clients/GPService_instance/*.class
                    
rm -rf ${FALKON_HOME}/service/org_globus_GenericPortal_services_core_WS.gar
rm -rf ${FALKON_HOME}/service/build/

rm -rf ${FALKON_HOME}/service/org_globus_GenericPortal_common.jar
rm -rf ${GLOBUS_LOCATION}/lib/org_globus_GenericPortal_common.jar

rm -rf ${GLOBUS_LOCATION}/lib/org_globus_GenericPortal_services_core_WS.jar
rm -rf ${GLOBUS_LOCATION}/lib/org_globus_GenericPortal_services_core_WS_stubs.jar

echo "Service cleanup complete!"

