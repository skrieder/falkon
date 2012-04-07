#!/bin/bash 

echo "==========================================================================================================="

 if [ -z "$2" ]; then 
              echo "usage: $0 <WWW_PORT> <POLL_TIME_SEC>"
              exit
          fi

export SERVICE_PATH=`pwd`
cd ..
export FALKON_PATH=`pwd`
cd webserver
export WWW_PATH=`pwd`
export PATH=${PATH}:${GLOBUS_LOCATION}/bin
#export GRAPHING_LOG=/dev/stdout
export GRAPHING_LOG=GraphingLog.txt

       
#export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia64
#export JAVA_HOME=`pwd`/jre
#export JAVA_HOME=/home/iraicu/jdk1.5.0_06
#export JAVA_HOME=/home/iraicu/jdk1.6.0
export PATH=${JAVA_HOME}:${JAVA_HOME}/bin:${FALKON_PATH}/ploticus/src:${PATH}

#./run.webserver.sh $1 html > WebServerLog.txt 2>&1 &
./run.webserver.sh $1 html &

export POLL_TIME=$2


cd ../service/logs/
 
while true
do

 tail GenericPortalWS_perf_per_sec.txt -n 10000 > summary_10000.txt
 tail GenericPortalWS_taskPerf.txt -n 10000 > task_10000.txt
 tail GenericPortalWS_taskPerf.txt -n 10000 > exec_10000.txt

 EXP_DESC=summary_graph
 echo "`date +%s`: generating ${EXP_DESC}..." >> ${GRAPHING_LOG}
 pl plot/${EXP_DESC}.plot png -o ${EXP_DESC}.jpg
 #cp ${EXP_DESC}.png ${WWW_PATH}/html/
 #pl plot/${EXP_DESC}.plot ps -o ${EXP_DESC}.ps

 EXP_DESC=task_graph
 echo "`date +%s`: generating ${EXP_DESC}..." >> ${GRAPHING_LOG}
 pl plot/${EXP_DESC}.plot png -o ${EXP_DESC}.jpg  
 #cp ${EXP_DESC}.png ${WWW_PATH}/html/
 #pl plot/${EXP_DESC}.plot ps -o ${EXP_DESC}.ps 

 EXP_DESC=executor_graph
 echo "`date +%s`: generating ${EXP_DESC}..." >> ${GRAPHING_LOG}
 pl plot/${EXP_DESC}.plot png -o ${EXP_DESC}.jpg 
 #cp ${EXP_DESC}.png ${WWW_PATH}/html/
 #pl plot/${EXP_DESC}.plot ps -o ${EXP_DESC}.ps 

 #EXP_DESC=throughput_graph
 #echo "`date +%s`: generating ${EXP_DESC}..." >> ${GRAPHING_LOG}
 #pl plot/${EXP_DESC}.plot png -o ${EXP_DESC}.png 
 #cp ${EXP_DESC}.png ${WWW_PATH}/html/
 #pl plot/${EXP_DESC}.pl ps -o ${EXP_DESC}.ps

 echo "`date +%s`: sleeping for ${POLL_TIME} until next graph generation cycle..." >> ${GRAPHING_LOG}
 sleep ${POLL_TIME}
done
