#!/bin/bash

# Now, let's do the same, using C-like syntax.


START_NUM=1
END_NUM=100000
DATA_SIZE=1MB
NUM_DIRS=0

((a = 1))      # a=1
echo "generating ${END_NUM} data files of size ${DATA_SIZE}..."
while (( a <= END_NUM ))
    do
        for ((b=${START_NUM}; b <= ${NUM_DIRS} ; b++))  # Double parentheses, and "LIMIT" with no "$".
            do
                mkdir -p in/${DATA_SIZE}/${b}
                cp data_${DATA_SIZE} in/${DATA_SIZE}/${b}/file_${a}
                ((a += 1))   # let "a+=1"
            done   
    done                           # A construct borrowed from 'ksh93'.

