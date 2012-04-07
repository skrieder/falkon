#!/bin/bash

 if [ -z "$2" ]; then 
              echo "usage: $0 <NUM_TASKS> <NUM_DIRS>"
              exit 1
          fi
             
# Now, let's do the same, using C-like syntax.

ASYS5_HOME=/home/zzhang/DHcode_12Feb2008
BINARY=${ASYS5_HOME}/source/asys5
INPUT_FILE1=${ASYS5_HOME}/scendat/stars/hi_8_07_1nov.txt
INPUT_FILE2=${ASYS5_HOME}/scendat/bgtestcases/first_scendata_a.txt
INPUT_FILE3=${ASYS5_HOME}/scendat/stars/lo_6_07_1nov.txt
INPUT_FILE4=${ASYS5_HOME}/scendat/stars/test_ScalDwn_01102007.txt
OUTPUT_DIR=${ASYS5_HOME}/out_dir


START_NUM=1
END_NUM=$2
LIMIT=$1

((a = 1))      # a=1

   while (( a <= LIMIT ))
do

for ((b=${START_NUM}; b <= ${END_NUM} ; b++))  # Double parentheses, and "LIMIT" with no "$".
do
      OUTPUT_FILE1=${OUTPUT_DIR}/${b}/${a}_1.txt
      OUTPUT_FILE2=${OUTPUT_DIR}/${b}/${a}_2.txt
      OUTPUT_FILE3=${OUTPUT_DIR}/${b}/${a}_3.txt
      OUTPUT_FILE4=${OUTPUT_DIR}/${b}/${a}_4.txt
      OUTPUT_FILE5=${OUTPUT_DIR}/${b}/${a}_5.txt
      echo "${BINARY} ${INPUT_FILE1} ${INPUT_FILE2} ${INPUT_FILE3} ${INPUT_FILE4} ${OUTPUT_FILE1} ${OUTPUT_FILE2} ${OUTPUT_FILE3} ${OUTPUT_FILE4} ${OUTPUT_FILE5}" 

      ((a += 1))   # let "a+=1"
done   


done                           # A construct borrowed from 'ksh93'.

for ((b=${START_NUM}; b <= ${END_NUM} ; b++))  # Double parentheses, and "LIMIT" with no "$".
do
   mkdir -p OUTPUT_DIR/${b}
done
