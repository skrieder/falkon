#!/bin/bash

#export HOME_PATH=`pwd`
#export GLOBUS_LOCATION=${HOME_PATH}
#export GLOBUS_PATH=${GLOBUS_LOCATION}
#export LD_LIBRARY_PATH=${GLOBUS_LOCATION}/lib

#export PATH=${GLOBUS_LOCATION}:${PATH}

#source ${GLOBUS_LOCATION}/etc/globus-devel-env.sh

#update the stubs
#echo "recompile stubs..."
#./make.stubs.sh
#echo "update the stubs..."
#cp -f ../service/org_globus_GenericPortal_common.jar .
#cp -r ../service/build/schema .
#cp -r ../service/build/stubs/classes/org/globus/GenericPortal/stubs org/globus/GenericPortal/
#cp -r ../service/org .


#To compile common stuff
#echo "compile common stuff"
#javac org/globus/GenericPortal/common/*.java
#rm -rf org_globus_GenericPortal_common.jar
#rm -rf ${GLOBUS_LOCATION}/lib/org_globus_GenericPortal_common.jar
#jar cf org_globus_GenericPortal_common.jar org/globus/GenericPortal/common/*.class
#cp org_globus_GenericPortal_common.jar ${GLOBUS_LOCATION}/lib/

echo "Compiling GramClient"
javac org/globus/GenericPortal/common/GramClient.java

if [ "$?" -ne "0" ]; then
  echo "Error in compile... exiting"
  exit 1
fi

echo "Compiling ClientCreate.java"
javac -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate.java

if [ "$?" -ne "0" ]; then
  echo "Error in compile... exiting"
  exit 1
fi

echo "Compiling WorkerRun.java"
javac -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/WorkerRun.java

if [ "$?" -ne "0" ]; then
  echo "Error in compile... exiting"
  exit 1
fi

echo "Compiling WorkerRunGram.java"
javac -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/WorkerRunGram.java

if [ "$?" -ne "0" ]; then
  echo "Error in compile... exiting"
  exit 1
fi



