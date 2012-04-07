#!/bin/bash

#export HOME_PATH=`pwd`
#export HOME_PATH=/home/iraicu/container
#export GLOBUS_LOCATION=/home/iraicu/container

#cd ..
#export GLOBUS_LOCATION=${HOME_PATH}
#export GLOBUS_PATH=${GLOBUS_LOCATION}
#export LD_LIBRARY_PATH=${GLOBUS_LOCATION}/lib

#export JAVA_HOME=/home/iraicu/jdk1.5.0_06
#export JAVA_HOME=/home/iraicu/jdk1.6.0
#export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia32
#export PATH=${JAVA_HOME}:${JAVA_HOME}/bin:${GLOBUS_LOCATION}:${PATH}
#export PATH=${GLOBUS_LOCATION}:${PATH}


#source ${GLOBUS_LOCATION}/etc/globus-devel-env.sh

#echo "JAVA version"
#java -version


#update the stubs
#echo "update the stubs..."
#cp ../service/org_globus_GenericPortal_common.jar .
#cp -r ../service/schema .
#cp -r ../service/build/stubs/classes/org/globus/GenericPortal/stubs org/globus/GenericPortal/
#cp -r ../service/org .
    

#To compile common stuff
#echo "compile common stuff"
#javac org/globus/GenericPortal/common/*.java
#rm -rf org_globus_GenericPortal_common.jar
#rm -rf ${GLOBUS_LOCATION}/lib/org_globus_GenericPortal_common.jar
#jar cf org_globus_GenericPortal_common.jar org/globus/GenericPortal/common/*.class
#cp org_globus_GenericPortal_common.jar ${GLOBUS_LOCATION}/lib/

echo "Compiling Falkon Monitor"
javac -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/FalkonMonitor.java

if [ "$?" -ne "0" ]; then
  echo "Error in compile... exiting"
  exit 1
fi

echo "Compiling Falkon Monitor GUI"
javac -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/MonitorGUI.java

if [ "$?" -ne "0" ]; then
  echo "Error in compile... exiting"
  exit 1
fi

