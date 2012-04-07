#!/bin/bash

FALKON_SERVICE_URL=viper.uchicago.edu
FALKON_SERVICE_PORT=50001
MAX_NUM_THREADS=1
                  
######################################################


MIN_EXECUTORS=4
MAX_EXECUTORS=4
MAX_TIME=630
scp /home/iraicu/java/Falkon_v0.8.1/client/exp_sc07/Provisioner.config.${MIN_EXECUTORS}.${MAX_EXECUTORS}.${MAX_TIME} tg-ia64:/home/iraicu/java/Falkon_v0.8.1/worker/etc/Provisioner.config

echo "sleeping for 5 min to ensure that the new nodes have been allocated, for a total of ${MAX_EXECUTORS} executors..."
sleep 300

MAX_NUM_TASKS=16384
SLEEP_TIME=0       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=2048
SLEEP_TIME=1       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=1024
SLEEP_TIME=2       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=512
SLEEP_TIME=4       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=256
SLEEP_TIME=8       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=128
SLEEP_TIME=16       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=64
SLEEP_TIME=32       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=32
SLEEP_TIME=64       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1
            

                             
######################################################


MIN_EXECUTORS=8
MAX_EXECUTORS=8
MAX_TIME=540
scp /home/iraicu/java/Falkon_v0.8.1/client/exp_sc07/Provisioner.config.${MIN_EXECUTORS}.${MAX_EXECUTORS}.${MAX_TIME} tg-ia64:/home/iraicu/java/Falkon_v0.8.1/worker/etc/Provisioner.config

echo "sleeping for 5 min to ensure that the new nodes have been allocated, for a total of ${MAX_EXECUTORS} executors..."
sleep 300

MAX_NUM_TASKS=32768
SLEEP_TIME=0       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=4096
SLEEP_TIME=1       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=2048
SLEEP_TIME=2       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=1024
SLEEP_TIME=4       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=512
SLEEP_TIME=8       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=256
SLEEP_TIME=16       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=128
SLEEP_TIME=32       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=64
SLEEP_TIME=64       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1


######################################################


MIN_EXECUTORS=16
MAX_EXECUTORS=16
MAX_TIME=450
scp /home/iraicu/java/Falkon_v0.8.1/client/exp_sc07/Provisioner.config.${MIN_EXECUTORS}.${MAX_EXECUTORS}.${MAX_TIME} tg-ia64:/home/iraicu/java/Falkon_v0.8.1/worker/etc/Provisioner.config

echo "sleeping for 5 min to ensure that the new nodes have been allocated, for a total of ${MAX_EXECUTORS} executors..."
sleep 300

MAX_NUM_TASKS=32768
SLEEP_TIME=0       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=8192
SLEEP_TIME=1       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=4096
SLEEP_TIME=2       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=2048
SLEEP_TIME=4       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=1024
SLEEP_TIME=8       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=512
SLEEP_TIME=16       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=256
SLEEP_TIME=32       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=128
SLEEP_TIME=64       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1
            
######################################################


MIN_EXECUTORS=32
MAX_EXECUTORS=32
MAX_TIME=360
scp /home/iraicu/java/Falkon_v0.8.1/client/exp_sc07/Provisioner.config.${MIN_EXECUTORS}.${MAX_EXECUTORS}.${MAX_TIME} tg-ia64:/home/iraicu/java/Falkon_v0.8.1/worker/etc/Provisioner.config

echo "sleeping for 5 min to ensure that the new nodes have been allocated, for a total of ${MAX_EXECUTORS} executors..."
sleep 300

MAX_NUM_TASKS=32768
SLEEP_TIME=0       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=16384
SLEEP_TIME=1       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=8192
SLEEP_TIME=2       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=4096
SLEEP_TIME=4       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=2048
SLEEP_TIME=8       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=1024
SLEEP_TIME=16       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=512
SLEEP_TIME=32       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=256
SLEEP_TIME=64       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1
            
                    
######################################################


MIN_EXECUTORS=64
MAX_EXECUTORS=64
MAX_TIME=270
scp /home/iraicu/java/Falkon_v0.8.1/client/exp_sc07/Provisioner.config.${MIN_EXECUTORS}.${MAX_EXECUTORS}.${MAX_TIME} tg-ia64:/home/iraicu/java/Falkon_v0.8.1/worker/etc/Provisioner.config

echo "sleeping for 5 min to ensure that the new nodes have been allocated, for a total of ${MAX_EXECUTORS} executors..."
sleep 300

MAX_NUM_TASKS=32768
SLEEP_TIME=0       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=32768
SLEEP_TIME=1       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=16384
SLEEP_TIME=2       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=8192
SLEEP_TIME=4       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=4096
SLEEP_TIME=8       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=2048
SLEEP_TIME=16       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=1024
SLEEP_TIME=32       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=512
SLEEP_TIME=64       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1
            
######################################################


MIN_EXECUTORS=128
MAX_EXECUTORS=128
MAX_TIME=180
scp /home/iraicu/java/Falkon_v0.8.1/client/exp_sc07/Provisioner.config.${MIN_EXECUTORS}.${MAX_EXECUTORS}.${MAX_TIME} tg-ia64:/home/iraicu/java/Falkon_v0.8.1/worker/etc/Provisioner.config

echo "sleeping for 7 min to ensure that the new nodes have been allocated, for a total of ${MAX_EXECUTORS} executors..."
sleep 420

MAX_NUM_TASKS=32768
SLEEP_TIME=0       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=32768
SLEEP_TIME=1       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=32768
SLEEP_TIME=2       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=16384
SLEEP_TIME=4       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=8192
SLEEP_TIME=8       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=4096
SLEEP_TIME=16       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=2048
SLEEP_TIME=32       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=1024
SLEEP_TIME=64       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1
            
             
######################################################


MIN_EXECUTORS=256
MAX_EXECUTORS=256
MAX_TIME=90
scp /home/iraicu/java/Falkon_v0.8.1/client/exp_sc07/Provisioner.config.${MIN_EXECUTORS}.${MAX_EXECUTORS}.${MAX_TIME} tg-ia64:/home/iraicu/java/Falkon_v0.8.1/worker/etc/Provisioner.config

echo "sleeping for 10 min to ensure that the new nodes have been allocated, for a total of ${MAX_EXECUTORS} executors..."
sleep 600

MAX_NUM_TASKS=32768
SLEEP_TIME=0       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=32768
SLEEP_TIME=1       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=32768
SLEEP_TIME=2       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=32768
SLEEP_TIME=4       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=16384
SLEEP_TIME=8       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=8192
SLEEP_TIME=16       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=4096
SLEEP_TIME=32       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1

MAX_NUM_TASKS=2048
SLEEP_TIME=64       
echo "Doing test for ${MAX_NUM_TASKS} tasks of length ${SLEEP_TIME} seconds on ${MAX_EXECUTORS} executors with no security..."  
./run.user.file.sh ${FALKON_SERVICE_URL} ${FALKON_SERVICE_PORT} sleep_${SLEEP_TIME} ${MAX_NUM_TASKS} ${MAX_NUM_THREADS} etc/client-security-config.xml ${SLEEP_TIME} ${MAX_EXECUTORS} >> /dev/null 2>&1


#############            

MIN_EXECUTORS=0
MAX_EXECUTORS=0
MAX_TIME=10
scp /home/iraicu/java/Falkon_v0.8.1/client/exp_sc07/Provisioner.config.${MIN_EXECUTORS}.${MAX_EXECUTORS}.${MAX_TIME} tg-ia64:/home/iraicu/java/Falkon_v0.8.1/worker/etc/Provisioner.config
            
echo "tests completed!!!!"
