#!/bin/bash

# Now, let's do the same, using C-like syntax.

CUR_DIR=`pwd`

AWKSCRIPT=' { srand(); print rand() } '

START_NUM=0
END_NUM=100000
DATA_SIZE=1MB
NUM_DIRS=0
COMPUTE_TIME=1
OUTPUT_FILE="${CUR_DIR}/workload.txt"

((a = 1))      # a=1
echo "generating workload for ${END_NUM} data files of size ${DATA_SIZE}..."
while (( a <= END_NUM ))
    do
    
        for ((b=${START_NUM}; b <= ${NUM_DIRS} ; b++))  # Double parentheses, and "LIMIT" with no "$".
            do
                #mkdir -p in/${DATA_SIZE}/${b}
                #cp data_${DATA_SIZE} in/${DATA_SIZE}/${b}/file_${a}

                x=`echo | awk "$AWKSCRIPT"`
                y=$(echo |awk '{ print x*100000}')
                
                rounded_value=`echo "tmp=$y; tmp /= 1; tmp" | bc`;
                #echo "${CUR_DIR}/read-sleep-write.sh in/${DATA_SIZE}/${b}/file_${a} out/${DATA_SIZE}/${b}/file_${a} ${COMPUTE_TIME}"
                #echo "${CUR_DIR}/read-sleep-write.sh in/${DATA_SIZE}/${b}/file_${a} /dev/null ${COMPUTE_TIME}" >> ${OUTPUT_FILE}
                echo "${CUR_DIR}/read-sleep-write.sh in/${DATA_SIZE}/${b}/file_${rounded_value} /dev/null ${COMPUTE_TIME}"
                #echo "xxx"
                ((a += 1))   # let "a+=1"
            done   
    done                           # A construct borrowed from 'ksh93'.

