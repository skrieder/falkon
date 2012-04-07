#!/bin/bash

 if [ -z "$1" ]; then 
              echo "usage: $0 <port> <rootPath>"
              exit
          fi

export HOME_PATH=`pwd`
export PORT=$1
export ROOT_PATH=$2

#export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia64
#export JAVA_HOME=`pwd`/jre
#export JAVA_HOME=/home/iraicu/jdk1.5.0_06
#export JAVA_HOME=/home/iraicu/jdk1.6.0
export PATH=${JAVA_HOME}:${JAVA_HOME}/bin:${PATH}

java -Xms128M -Xmx128M -Xss128K -classpath $CLASSPATH:. WebServer -port $PORT -rootPath $ROOT_PATH -timeout 60000 -threads 20

