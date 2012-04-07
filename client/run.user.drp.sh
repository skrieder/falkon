#!/bin/bash

#for i in `seq 1 10`;
#        do
#                echo "Doing test ${i} with no security..."      
#                time ./run.user.file.sh tg-viz-login2 50001 epr1.txt 30ss " " >> DeeF_overhead_nosec_30x1x10_1u_1w.txt 2>&1
#        done  

#for i in `seq 1 10`;
#        do
#                echo "Doing test ${i} with -TSL -authenticate..."      
#                time ./run.user.file-sec.sh tg-viz-login2 50005 epr1.txt 30ss "-TSL -authenticate" >> DeeF_overhead_TSL-auth_30x1x10_1u_1w.txt 2>&1
#        done  


#for i in `seq 1 10`;
#        do
#                echo "Doing test ${i} with -TSL -authenticate -encrypt..."      
#                time ./run.user.file-sec.sh tg-viz-login2 50002 epr1.txt 30ss "-TSL -authenticate -encrypt" >> DeeF_overhead_TSL-auth-enc_30x1x10_1u_1w.txt 2>&1
#        done  

#for i in `seq 1 10`;
#        do
#                echo "Doing test ${i} with -CONV -authenticate..."      
#                time ./run.user.file.sh tg-viz-login2 50004 epr1.txt 30ss "-CONV -authenticate" >> DeeF_overhead_CONV-auth_30x1x10_1u_1w.txt 2>&1
#        done  


#for i in `seq 1 10`;
#        do
#                echo "Doing test ${i} with -CONV -authenticate -encrypt..."      
#                time ./run.user.file.sh tg-viz-login2 50003 epr1.txt 30ss etc/client-security-config.xml >> DeeF_overhead_CONV-auth-enc_30x1x10_1u_1w.txt 2>&1
#        done  

#for i in `seq 1 10`;
#        do
#                echo "Doing test ${i} with no security..."      
#                time ./run.user.file.sh tg-viz-login1 50001 epr1.txt sleep_0 ${i} etc/client-security-config.xml > DeeF_tests_nosec_30x1x10_1u_1w.txt 2>&1
#        done  

#COUNTER=32
#FILE_NAME=1000c
#MAX_NUM_TASKS=1000
#MAX_NUM_THREADS=1100

#         while [  $COUNTER -lt ${MAX_NUM_THREADS} ]; 
#         do
#            echo "Doing test from file ${FILE_NAME} with $COUNTER tasks and no security..."  
            #<GT4_IP> <GT4_PORT> <EXEC_FILE> <NUM_EXECS> <NUM_THREADS> <SECURITY_OPTIONS>"    
#             time ./run.user.file.sh tg-viz-login2 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_${MAX_NUM_TASKS}t_${COUNTER}u_1w.txt 2>&1
             #time ./run.user.file.sh tg-viz-login2 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_${MAX_NUM_TASKS}t_${COUNTER}u_1w.txt 2>&1
             #time ./run.user.file.sh tg-viz-login2 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_${MAX_NUM_TASKS}t_${COUNTER}u_1w.txt 2>&1
             #echo The counter is $COUNTER
#             let COUNTER=COUNTER*2 
#         done        

 
COUNTER=1
MAX_NUM_TASKS=1000
MAX_NUM_THREADS=1

FILE_NAME=drp_test/stage01
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage02
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage03
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage04
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage05
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage06
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage07
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage08
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage09
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage10
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage11
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage12
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage13
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage14
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage15
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage16
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage17
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1

FILE_NAME=drp_test/stage18
echo "Doing test from file ${FILE_NAME} with all tasks and no security..."  
time ./run.user.file.sh tg-v082 50001 ${FILE_NAME} ${MAX_NUM_TASKS} ${COUNTER} etc/client-security-config.xml >> DeeF_tests_nosec_${FILE_NAME}_1u_32w.txt 2>&1


