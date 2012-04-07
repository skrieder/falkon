#!/bin/bash

export GLOBUS_LOCATION=`pwd`
                                       
rm -rf org/globus/GenericPortal/common/*.class
rm -rf org_globus_GenericPortal_common.jar
rm -rf ${GLOBUS_LOCATION}/lib/org_globus_GenericPortal_common.jar
rm -rf org/globus/GenericPortal/clients/FactoryService_GP/*.class
rm -rf org/globus/GenericPortal/clients/GPService_instance/*.class
echo "Monitor cleanup complete!"
