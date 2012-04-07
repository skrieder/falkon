#!/bin/bash

 if [ -z "$1" ]; then 
              echo "usage: $0 <FALKON_ID>"
              echo "usage: $0 0001"
              echo "-----------"

              echo "usage: $0 <FALKON_ID> <USER_ID>"
              echo "usage: $0 0001 iraicu"
              exit 1
          fi

if [ -z "${FALKON_ROOT}" ]; then
    echo "ERROR: environment variable FALKON_ROOT not defined"
    exit 1
fi

FALKON_UTILITIES=${FALKON_HOME}/utilities

USER_ID=$USER
             
if [ ! -z $2 ];then
   USER_ID=$2
fi 

CUR_DIR=`pwd`
OUTPUT_DIR=$CUR_DIR/${1}_${USER_ID}
                     
cd ${FALKON_ROOT}/users/${USER_ID}/${1}
source falkon.env.bgp
#should be generic, not BG/P specific
#source falkon.env

if [ -z "${FALKON_HOME}" ]; then
    echo "ERROR: environment variable FALKON_CLIENT_HOME not defined"
    exit 1
fi

if [ ! -d "${FALKON_HOME}" ]; then
    echo "ERROR: invalid FALKON_CLIENT_HOME set: $FALKON_CLIENT_HOME"
    exit 1
fi

#rm -rf ${FALKON_LOGS}/service_aggregated
mkdir -p ${OUTPUT_DIR}
java -Xms1536M -Xmx1536M -classpath $FALKON_UTILITIES MergeFalkonSummary ${FALKON_LOGS}/service/ 1 > ${OUTPUT_DIR}/falkon_summary.txt
#java SummarizeTaskPerfDir /scratch/falkon_logs_history/logs_history/bgp/intrepid/hockyg/1102/logs/service/

cd ${FALKON_LOGS}/service
find ./ -name "falkon_task_perf.txt" -exec cat {} \; >> ${OUTPUT_DIR}/falkon_task_global_raw.tmp
head ${OUTPUT_DIR}/falkon_task_global_raw.tmp -n 1 > ${OUTPUT_DIR}/falkon_task_perf_unsorted.tmp
cat ${OUTPUT_DIR}/falkon_task_global_raw.tmp | grep ":" >> ${OUTPUT_DIR}/falkon_task_perf_unsorted.tmp
sort -g --key=5,5 ${OUTPUT_DIR}/falkon_task_perf_unsorted.tmp -o ${OUTPUT_DIR}/falkon_task_perf.txt

rm -rf ${OUTPUT_DIR}/*.tmp

cp -r $FALKON_HOME/ploticus/plot ${OUTPUT_DIR}/

cd ${OUTPUT_DIR}/plot
./generate.graphs.png.sh 

echo "Graphs in PNG format created, they can be found in ${OUTPUT_DIR}/plot/"
