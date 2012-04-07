#!/bin/bash

#export HOME_PATH=`pwd`
#export GLOBUS_LOCATION=${HOME_PATH}
#export GLOBUS_PATH=${GLOBUS_LOCATION}
#export LD_LIBRARY_PATH=${GLOBUS_LOCATION}/lib


#export JAVA_HOME=/home/iraicu/jdk1.5.0_06
#export JAVA_HOME=/home/iraicu/j2sdk1.4.2_14
#export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia32
#export JAVA_HOME=/home/iraicu/j2sdk1.4.2_14
#export JAVA_HOME=/home/iraicu/jdk1.6.0
#export PATH=${JAVA_HOME}:${JAVA_HOME}/bin:${GLOBUS_LOCATION}:${PATH}
#export PATH=${GLOBUS_LOCATION}:${PATH}




#echo "cleaning up KDTreeIndex files..."
#rm KDTree/*.class
#rm -rf ${GLOBUS_LOCATION}/lib/KDTree.jar
#rm -rf ${GLOBUS_LOCATION}/lib/kd.jar
#rm edu/wlu/cs/levy/CG/*.class

#echo "compiling the KDTreeIndex..."
#javac edu/wlu/cs/levy/CG/*.java
#jar cf kd.jar edu
#mv kd.jar ${GLOBUS_LOCATION}/lib/
#javac -classpath .:kd.jar KDTree/KDTreeIndex.java
 
#echo "creating JAR file of KDTreeIndex and updating the library path..."
#jar cf KDTree.jar KDTree
#mv KDTree.jar ${GLOBUS_LOCATION}/lib/

#source ${GLOBUS_LOCATION}/etc/globus-devel-env.sh

#echo "compile common stuff"
#javac org/globus/GenericPortal/common/*.java

#echo "Compiling ClientCreate.java"
#javac -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate.java
echo "Compiling Console.java"
javac -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/Console.java

if [ "$?" -ne "0" ]; then
  echo "Error in compile... exiting"
  exit 1
fi

echo "Compiling UserRun.java"
javac -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/UserRun.java

if [ "$?" -ne "0" ]; then
  echo "Error in compile... exiting"
  exit 1
fi


#echo "Compiling ClientDestroy.java"
#javac -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientDestroy.java



