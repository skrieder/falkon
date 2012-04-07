#!/bin/bash

# Now, let's do the same, using C-like syntax.

 if [ -z "$1" ]; then 
              echo "usage: $0 <SHARED_FS_PATH>"
              exit 1
          fi


#GPFS_PATH=/home/iraicu/Falkon-data/in
GPFS_PATH=${1}/in
GPFS_PATH_OUT=${1}/out

  
START_NUM=1
END_NUM=32768
NUM_DIRS=32
DATA_SIZE=1B
EXP_NAME=data_gpfs_read_write_${DATA_SIZE}

   
((a = 1))      # a=1
echo "generating ${END_NUM} task descriptions for data files of size ${DATA_SIZE}..."
while (( a <= END_NUM ))
    do
        for ((b=${START_NUM}; b <= ${NUM_DIRS} ; b++))  # Double parentheses, and "LIMIT" with no "$".
            do
                echo "/bin/cp ${GPFS_PATH}/${DATA_SIZE}/${b}/file_${a} $GPFS_PATH_OUT/$DATA_SIZE/${b}/file_${a}" >> ${EXP_NAME} 
                ((a += 1))   # let "a+=1"
            done   
    done                           # A construct borrowed from 'ksh93'.


START_NUM=1
END_NUM=32768
NUM_DIRS=32
DATA_SIZE=1KB
EXP_NAME=data_gpfs_read_write_${DATA_SIZE}

((a = 1))      # a=1
echo "generating ${END_NUM} task descriptions for data files of size ${DATA_SIZE}..."
while (( a <= END_NUM ))
    do
        for ((b=${START_NUM}; b <= ${NUM_DIRS} ; b++))  # Double parentheses, and "LIMIT" with no "$".
            do
                echo "/bin/cp ${GPFS_PATH}/${DATA_SIZE}/${b}/file_${a} $GPFS_PATH_OUT/$DATA_SIZE/${b}/file_${a}" >> ${EXP_NAME} 
                ((a += 1))   # let "a+=1"
            done   
    done                           # A construct borrowed from 'ksh93'.

START_NUM=1
END_NUM=32768
NUM_DIRS=32
DATA_SIZE=10KB
EXP_NAME=data_gpfs_read_write_${DATA_SIZE}

((a = 1))      # a=1
echo "generating ${END_NUM} task descriptions for data files of size ${DATA_SIZE}..."
while (( a <= END_NUM ))
    do
        for ((b=${START_NUM}; b <= ${NUM_DIRS} ; b++))  # Double parentheses, and "LIMIT" with no "$".
            do
                echo "/bin/cp ${GPFS_PATH}/${DATA_SIZE}/${b}/file_${a} $GPFS_PATH_OUT/$DATA_SIZE/${b}/file_${a}" >> ${EXP_NAME} 
                ((a += 1))   # let "a+=1"
            done   
    done                           # A construct borrowed from 'ksh93'.

START_NUM=1
END_NUM=32768
NUM_DIRS=32
DATA_SIZE=100KB
EXP_NAME=data_gpfs_read_write_${DATA_SIZE}

((a = 1))      # a=1
echo "generating ${END_NUM} task descriptions for data files of size ${DATA_SIZE}..."
while (( a <= END_NUM ))
    do
        for ((b=${START_NUM}; b <= ${NUM_DIRS} ; b++))  # Double parentheses, and "LIMIT" with no "$".
            do
                echo "/bin/cp ${GPFS_PATH}/${DATA_SIZE}/${b}/file_${a} $GPFS_PATH_OUT/$DATA_SIZE/${b}/file_${a}" >> ${EXP_NAME} 
                ((a += 1))   # let "a+=1"
            done   
    done                           # A construct borrowed from 'ksh93'.

START_NUM=1
END_NUM=32768
NUM_DIRS=32
DATA_SIZE=1MB
EXP_NAME=data_gpfs_read_write_${DATA_SIZE}

((a = 1))      # a=1
echo "generating ${END_NUM} task descriptions for data files of size ${DATA_SIZE}..."
while (( a <= END_NUM ))
    do
        for ((b=${START_NUM}; b <= ${NUM_DIRS} ; b++))  # Double parentheses, and "LIMIT" with no "$".
            do
                echo "/bin/cp ${GPFS_PATH}/${DATA_SIZE}/${b}/file_${a} $GPFS_PATH_OUT/$DATA_SIZE/${b}/file_${a}" >> ${EXP_NAME} 
                ((a += 1))   # let "a+=1"
            done   
    done                           # A construct borrowed from 'ksh93'.

START_NUM=1
END_NUM=32768
NUM_DIRS=32
DATA_SIZE=10MB
EXP_NAME=data_gpfs_read_write_${DATA_SIZE}

((a = 1))      # a=1
echo "generating ${END_NUM} task descriptions for data files of size ${DATA_SIZE}..."
while (( a <= END_NUM ))
    do
        for ((b=${START_NUM}; b <= ${NUM_DIRS} ; b++))  # Double parentheses, and "LIMIT" with no "$".
            do
                echo "/bin/cp ${GPFS_PATH}/${DATA_SIZE}/${b}/file_${a} $GPFS_PATH_OUT/$DATA_SIZE/${b}/file_${a}" >> ${EXP_NAME} 
                ((a += 1))   # let "a+=1"
            done   
    done                           # A construct borrowed from 'ksh93'.

START_NUM=1
END_NUM=4096
NUM_DIRS=4
DATA_SIZE=100MB
EXP_NAME=data_gpfs_read_write_${DATA_SIZE}

((a = 1))      # a=1
echo "generating ${END_NUM} task descriptions for data files of size ${DATA_SIZE}..."
while (( a <= END_NUM ))
    do
        for ((b=${START_NUM}; b <= ${NUM_DIRS} ; b++))  # Double parentheses, and "LIMIT" with no "$".
            do
                echo "/bin/cp ${GPFS_PATH}/${DATA_SIZE}/${b}/file_${a} $GPFS_PATH_OUT/$DATA_SIZE/${b}/file_${a}" >> ${EXP_NAME} 
                ((a += 1))   # let "a+=1"
            done   
    done                           # A construct borrowed from 'ksh93'.

START_NUM=1
END_NUM=1024
NUM_DIRS=1
DATA_SIZE=1GB
EXP_NAME=data_gpfs_read_write_${DATA_SIZE}

((a = 1))      # a=1
echo "generating ${END_NUM} task descriptions for data files of size ${DATA_SIZE}..."
while (( a <= END_NUM ))
    do
        for ((b=${START_NUM}; b <= ${NUM_DIRS} ; b++))  # Double parentheses, and "LIMIT" with no "$".
            do
                echo "/bin/cp ${GPFS_PATH}/${DATA_SIZE}/${b}/file_${a} $GPFS_PATH_OUT/$DATA_SIZE/${b}/file_${a}" >> ${EXP_NAME} 
                ((a += 1))   # let "a+=1"
            done   
    done                           # A construct borrowed from 'ksh93'.

