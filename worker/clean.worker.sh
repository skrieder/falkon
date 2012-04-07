#!/bin/bash

cd ${FALKON_HOME}/worker

#C++ and JNI funsky cleanup
rm JNI/FunTools/*.so 
rm JNI/FunTools/*.class
rm JNI/FunTools/*.h
rm lib/*.so
rm -rf JNI_FunTools.jar
rm -rf lib/JNI_FunTools.jar
                                       
rm -rf org/globus/GenericPortal/clients/FactoryService_GP/*.class
rm -rf org/globus/GenericPortal/clients/GPService_instance/*.class
echo "Worker cleanup complete!"
