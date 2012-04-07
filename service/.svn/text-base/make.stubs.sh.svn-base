#!/bin/bash

#echo "compiling the GenericPortal..."
#echo ""
        
#export HOME_PATH=`pwd`

#cd ..
#export GLOBUS_LOCATION=`pwd`/container
#export GLOBUS_PATH=${GLOBUS_LOCATION}
#export LD_LIBRARY_PATH=${GLOBUS_LOCATION}/lib:${LD_LIBRARY_PATH}
#cd ${HOME_PATH}


#export JAVA_HOME=/home/iraicu/jdk1.6.0
#export PATH=${JAVA_HOME}:${JAVA_HOME}/bin:${GLOBUS_LOCATION}:${GLOBUS_LOCATION}/bin:${PATH}

#CLASSPATH=""                
#source ${GLOBUS_LOCATION}/etc/globus-devel-env.sh     


#ant -buildfile build-falkon.xml
./globus-build-service.sh GenericPortal

mv org_globus_GenericPortal_services_core_WS.gar tmp/
cd tmp
jar xf org_globus_GenericPortal_services_core_WS.gar
cd lib
#jar xf org_globus_GenericPortal_services_core_WS_stubs.jar
#jar xf org_globus_GenericPortal_services_core_WS.jar

#mkdir -p org/globus/GenericPortal/stubs/Factory                                                            
#mv org/globus/www/namespaces/GenericPortal/FactoryService/* org/globus/GenericPortal/stubs/Factory/
#mkdir -p org/globus/GenericPortal/stubs/GPService_instance                                                            
#mv org/globus/www/namespaces/GenericPortal/GPService_instance/* org/globus/GenericPortal/stubs/GPService_instance/

#rm org_globus_GenericPortal_services_core_WS.jar
#jar cf org_globus_GenericPortal_services_core_WS.jar org 

#rm org_globus_GenericPortal_services_core_WS_stubs.jar

mv org_globus_GenericPortal_services_core_WS.jar ../../lib/
mv org_globus_GenericPortal_services_core_WS_stubs.jar ../../lib/

cd ../..
rm -rf tmp
mkdir tmp


