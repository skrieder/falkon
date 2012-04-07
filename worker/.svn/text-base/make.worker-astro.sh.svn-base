#!/bin/bash

#AstroPortal specific things...
FUN_TOOLS=${FALKON_HOME}/AstroPortal/funtools-1.3.0b21

cd ${FALKON_HOME}/worker

./clean.worker.sh

#To compile the C++ and JNI funsky
#echo "cleaning up funsky files..."
#rm JNI/FunTools/*.so 
#rm JNI/FunTools/*.class
#rm JNI/FunTools/*.h
#rm ${LD_LIBRARY_PATH}/*.so
#rm -rf JNI_FunTools.jar
#rm -rf ${LD_LIBRARY_PATH}/JNI_FunTools.jar

echo "compiling the funsky java interface..."
javac JNI/FunTools/funsky_JNI.java
javah -d JNI/FunTools/ -jni JNI.FunTools.funsky_JNI

echo "compiling the funsky C++ code..."
g++ -shared -static -I${FUN_TOOLS} -I${FUN_TOOLS}/util -I${FUN_TOOLS}/fitsy -I${FUN_TOOLS}/wcs -I${FUN_TOOLS}/filter JNI/FunTools/funsky-so.c -o JNI/FunTools/libfunsky.so JNI/FunTools/libfuntools.a -lm -lpthread
cp JNI/FunTools/libfunsky.so ${GLOBUS_LOCATION}/lib

echo "creating JAR file of funsky and updating the library path..."
jar cf JNI_FunTools.jar JNI
mv JNI_FunTools.jar ${GLOBUS_LOCATION}/lib/
                         
#update the stubs
#echo "recompile stubs..."
#./make.stubs.sh
#echo "update the stubs..."
#cp ../service/org_globus_GenericPortal_common.jar .

#To compile common stuff
#echo "compile common stuff"
#javac org/globus/GenericPortal/common/*.java
#rm -rf org_globus_GenericPortal_common.jar
#rm -rf ${GLOBUS_LOCATION}/lib/org_globus_GenericPortal_common.jar
#jar cf org_globus_GenericPortal_common.jar org/globus/GenericPortal/common/*.class
#cp org_globus_GenericPortal_common.jar ${GLOBUS_LOCATION}/lib/


echo "Compiling GramClient"
javac org/globus/GenericPortal/common/GramClient.java
     
echo "Compiling ClientCreate.java"
javac -classpath $CLASSPATH:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate.java
echo "Compiling ImageStacking.java"
javac -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/ImageStacking.java
echo "Compiling ImageStackingMain.java"
javac -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/ImageStackingMain.java
echo "Compiling WorkerRun.java"
javac -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/WorkerRun.java
echo "Compiling WorkerRunGram.java"
javac -classpath $CLASSPATH:. org/globus/GenericPortal/clients/GPService_instance/WorkerRunGram.java



