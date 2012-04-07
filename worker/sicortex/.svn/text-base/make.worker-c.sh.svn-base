#!/bin/bash

export HOME_PATH=`pwd`
#export HOME_PATH=/home/iraicu/container
#export GLOBUS_LOCATION=/home/iraicu/container

#cd ..
export GLOBUS_LOCATION=${HOME_PATH}
export GLOBUS_PATH=${GLOBUS_LOCATION}
export LD_LIBRARY_PATH=${GLOBUS_LOCATION}/lib

#export JAVA_HOME=/home/iraicu/jdk1.5.0_06
#export JAVA_HOME=/home/iraicu/jdk1.6.0
#export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia32
#export PATH=${JAVA_HOME}:${JAVA_HOME}/bin:${GLOBUS_LOCATION}:${PATH}
export PATH=${GLOBUS_LOCATION}:${PATH}


#source ${GLOBUS_LOCATION}/etc/globus-devel-env.sh

#echo "JAVA version"
#java -version

       
PLATFORM=`uname -m`

echo "Compiling C Executor"
cd src-c
gcc -static -o BGexec_${PLATFORM}_static BGexec_v01.c
gcc -o BGexec_${PLATFORM} BGexec_v01.c
gcc -o BGexec BGexec_v01.c
#ln -sf BGexec_${PLATFORM} BGexec
#gcc -o BGexec_${PLATFORM} BGexec.c


