#!/bin/bash

CLIENT_LOG=logs/client-output.txt

FALKON_SERVICE_URL=viper.uchicago.edu
FALKON_SERVICE_PORT=50001
MAX_NUM_THREADS=1
                  
######################################################


#MAX_EXECUTORS=1
#MAX_NUM_TASKS=( 1024	1024	1024	512	256	128	32	4 )
#MAX_EXECUTORS=2
#MAX_NUM_TASKS=( 2048	2048	2048	1024	512	256	64	8 )
#MAX_EXECUTORS=4
#MAX_NUM_TASKS=( 4096	4096	4096	2048	1024	512	128	16 )
#MAX_EXECUTORS=8
#MAX_NUM_TASKS=( 8192	8192	8192	4096	2048	1024	256	32 )
#MAX_EXECUTORS=16
#MAX_NUM_TASKS=( 8192	8192	8192	4096	2048	1024	256	32 )
###MAX_EXECUTORS=32
###MAX_NUM_TASKS=( 32768	32768	16384	8192	4096	2048	1024	128 )
#MAX_EXECUTORS=128
#MAX_NUM_TASKS=( 32768	32768	32768	32768	32768	32768	4096	512 )

###DATA_SIZE=( 1B 1KB 10KB 100KB 1MB 10MB 100MB 1GB )
#DATA_SIZE=( 100KB 1MB 10MB 100MB 1GB )
#EXP_DESC=( data_gpfs_cache_0loc_read data_gpfs_cache_100loc_read data_gpfs_cache_0loc_read_write data_gpfs_cache_100loc_read_write data_gpfs_0loc_read data_gpfs_100loc_read data_gpfs_0loc_read_write data_gpfs_100loc_read_write data_gpfs_wrapper_0loc_read data_gpfs_wrapper_100loc_read data_gpfs_wrapper_0loc_read_write data_gpfs_wrapper_100loc_read_write data_gpfs_cache_sched_0loc_read data_gpfs_cache_sched_100loc_read data_gpfs_cache_sched_0loc_read_write data_gpfs_cache_sched_100loc_read_write )
# do now...
#EXP_DESC=( data_gpfs_cache_read data_gpfs_cache_read_write )
# do later
###EXP_DESC=( data_gpfs_read_write )
#MAX_EXECUTORS=( 1 2 4 8 16 32 64 )

###count=${#MAX_NUM_TASKS[*]}
###expCount=${#EXP_DESC[*]}
#tasks_count=${#MAX_NUM_TASKS[*]}
#data_count=${#DATA_SIZE[*]}
#executors_count=${#MAX_EXECUTORS[*]}


###for ((j=0; j < expCount ; j++))  # Double parentheses, and "LIMIT" with no "$".
###do

###for ((i=0; i < count ; i++))  # Double parentheses, and "LIMIT" with no "$".
###do


###echo "Doing test ${EXP_DESC[j]} for ${MAX_NUM_TASKS[i]} tasks with data size ${DATA_SIZE[i]} and 0% data locality on ${MAX_EXECUTORS} executors with no security..."
###echo "Doing test ${EXP_DESC[j]} for ${MAX_NUM_TASKS[i]} tasks with data size ${DATA_SIZE[i]} and 0% data locality on ${MAX_EXECUTORS} executors with no security..." >> ${CLIENT_LOG} 2>&1
#./run.user.auto-caching.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} workloads/cp/${EXP_DESC[j]}_${DATA_SIZE[i]} ${MAX_NUM_TASKS[i]} ${MAX_NUM_THREADS} etc/client-security-config.xml ${DATA_SIZE[i]} ${MAX_EXECUTORS} ${EXP_DESC[j]} 1 >> ${CLIENT_LOG} 2>&1
###./run.user.auto.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} workloads/cp/${EXP_DESC[j]}_${DATA_SIZE[i]} ${MAX_NUM_TASKS[i]} ${MAX_NUM_THREADS} etc/client-security-config.xml ${DATA_SIZE[i]} ${MAX_EXECUTORS} ${EXP_DESC[j]} 1 >> ${CLIENT_LOG} 2>&1

#echo "Doing test ${EXP_DESC[j]} for ${MAX_NUM_TASKS[i]} tasks with data size ${DATA_SIZE[i]} and 100% data locality on ${MAX_EXECUTORS} executors with no security..."  
#echo "Doing test ${EXP_DESC[j]} for ${MAX_NUM_TASKS[i]} tasks with data size ${DATA_SIZE[i]} and 100% data locality on ${MAX_EXECUTORS} executors with no security..." >> ${CLIENT_LOG} 2>&1
#./run.user.auto-caching.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} workloads/cp/${EXP_DESC[j]}_${DATA_SIZE[i]} ${MAX_NUM_TASKS[i]} ${MAX_NUM_THREADS} etc/client-security-config.xml ${DATA_SIZE[i]} ${MAX_EXECUTORS} ${EXP_DESC[j]} 8 >> ${CLIENT_LOG} 2>&1


###done                           # A construct borrowed from 'ksh93'.

                                                         
###done                           # A construct borrowed from 'ksh93'.

echo "Doing test sleep_0 for 4096 tasks with data size 1B and 0% data locality on 64 executors with no security..."
echo "Doing test sleep_0 for 4096 tasks with data size 1B and 0% data locality on 64 executors with no security..." >> ${CLIENT_LOG} 2>&1
./run.user.auto.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} workloads/sleep/sleep_0 4096 1 etc/client-security-config.xml 1B 64 data_gpfs_cache_read 1 >> ${CLIENT_LOG} 2>&1

                   
DATA_SIZE=( 1B 1KB 10KB 100KB 1MB 10MB 100MB 1GB )
#DATA_SIZE=( 1GB )

#MAX_EXECUTORS=1
#MAX_NUM_TASKS=( 1024	1024	1024	1024	512	256	64	8 )
#MAX_EXECUTORS=2
#MAX_NUM_TASKS=( 2048	2048	2048	2048	1024	512	128	16 )
#MAX_EXECUTORS=4
#MAX_NUM_TASKS=( 4096	4096	4096	4096	2048	1024	256	32 )
#MAX_EXECUTORS=8
#MAX_NUM_TASKS=( 4096	4096	4096	4096	2048	1024	256	32 )
#MAX_EXECUTORS=16
#MAX_NUM_TASKS=( 4096	4096	4096	4096	2048	1024	512	64 )
#MAX_EXECUTORS=32
#MAX_NUM_TASKS=( 4096	4096	4096	4096	2048	1024	512	128 )
MAX_EXECUTORS=64
MAX_NUM_TASKS=( 4096	4096	4096	4096	2048	1024	512	128 )
#MAX_NUM_TASKS=( 128 )
#EXP_DESC=( data_gpfs_cache_read_write ) 
EXP_DESC=( data_gpfs_cache_read ) 
count=${#MAX_NUM_TASKS[*]}
expCount=${#EXP_DESC[*]}
for ((j=0; j < expCount ; j++))  # Double parentheses, and "LIMIT" with no "$".
do
for ((i=0; i < count ; i++))  # Double parentheses, and "LIMIT" with no "$".
do
echo "Doing test ${EXP_DESC[j]} for ${MAX_NUM_TASKS[i]} tasks with data size ${DATA_SIZE[i]} and 0% data locality on ${MAX_EXECUTORS} executors with no security..."
echo "Doing test ${EXP_DESC[j]} for ${MAX_NUM_TASKS[i]} tasks with data size ${DATA_SIZE[i]} and 0% data locality on ${MAX_EXECUTORS} executors with no security..." >> ${CLIENT_LOG} 2>&1
./run.user.auto-caching.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} workloads/cp/${EXP_DESC[j]}_${DATA_SIZE[i]} ${MAX_NUM_TASKS[i]} ${MAX_NUM_THREADS} etc/client-security-config.xml ${DATA_SIZE[i]} ${MAX_EXECUTORS} ${EXP_DESC[j]} 1 >> ${CLIENT_LOG} 2>&1
echo "Doing test ${EXP_DESC[j]} for ${MAX_NUM_TASKS[i]} tasks with data size ${DATA_SIZE[i]} and 100% data locality on ${MAX_EXECUTORS} executors with no security..."
echo "Doing test ${EXP_DESC[j]} for ${MAX_NUM_TASKS[i]} tasks with data size ${DATA_SIZE[i]} and 100% data locality on ${MAX_EXECUTORS} executors with no security..." >> ${CLIENT_LOG} 2>&1
./run.user.auto-caching.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} workloads/cp/${EXP_DESC[j]}_${DATA_SIZE[i]} ${MAX_NUM_TASKS[i]} ${MAX_NUM_THREADS} etc/client-security-config.xml ${DATA_SIZE[i]} ${MAX_EXECUTORS} ${EXP_DESC[j]} 4 >> ${CLIENT_LOG} 2>&1
done                           # A construct borrowed from 'ksh93'.
done                           # A construct borrowed from 'ksh93'.
echo; echo
       
              


          
echo "tests completed!!!!"
echo "tests completed!!!!" >> ${CLIENT_LOG} 2>&1
