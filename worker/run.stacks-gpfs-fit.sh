#!/bin/bash

#LAN-GZ-100x100
HOME_PATH=/disks/scratchgpfs1/iraicu
NUM_STACKS=1000
HEIGHT=100
WIDTH=100
NUM_THREADS=2

#LAN-FIT-100x100
FS="gpfs"
FORMAT="fit"
RESULT="result_${RANDOM}.fit"
STATS_FILE="log-stats-stack-${FS}-${FORMAT}.txt"
VMSTAT_FILE="log-vmstat-stack-${FS}-${FORMAT}.txt"


vmstat -n 1 > ${VMSTAT_FILE} &
./run.stack.sh ${HOME_PATH}/stack-list-${FS}-1000-${FORMAT}.txt ${NUM_STACKS} ${HEIGHT} ${WIDTH} ${HOME_PATH}/${RESULT} ${NUM_THREADS} >> "${STATS_FILE}" 2>&1
./run.stack.sh ${HOME_PATH}/stack-list-${FS}-2000-${FORMAT}.txt ${NUM_STACKS} ${HEIGHT} ${WIDTH} ${HOME_PATH}/${RESULT} ${NUM_THREADS} >> "${STATS_FILE}" 2>&1
./run.stack.sh ${HOME_PATH}/stack-list-${FS}-3000-${FORMAT}.txt ${NUM_STACKS} ${HEIGHT} ${WIDTH} ${HOME_PATH}/${RESULT} ${NUM_THREADS} >> "${STATS_FILE}" 2>&1
./run.stack.sh ${HOME_PATH}/stack-list-${FS}-4000-${FORMAT}.txt ${NUM_STACKS} ${HEIGHT} ${WIDTH} ${HOME_PATH}/${RESULT} ${NUM_THREADS} >> "${STATS_FILE}" 2>&1
./run.stack.sh ${HOME_PATH}/stack-list-${FS}-5000-${FORMAT}.txt ${NUM_STACKS} ${HEIGHT} ${WIDTH} ${HOME_PATH}/${RESULT} ${NUM_THREADS} >> "${STATS_FILE}" 2>&1
./run.stack.sh ${HOME_PATH}/stack-list-${FS}-6000-${FORMAT}.txt ${NUM_STACKS} ${HEIGHT} ${WIDTH} ${HOME_PATH}/${RESULT} ${NUM_THREADS} >> "${STATS_FILE}" 2>&1
./run.stack.sh ${HOME_PATH}/stack-list-${FS}-7000-${FORMAT}.txt ${NUM_STACKS} ${HEIGHT} ${WIDTH} ${HOME_PATH}/${RESULT} ${NUM_THREADS} >> "${STATS_FILE}" 2>&1
./run.stack.sh ${HOME_PATH}/stack-list-${FS}-8000-${FORMAT}.txt ${NUM_STACKS} ${HEIGHT} ${WIDTH} ${HOME_PATH}/${RESULT} ${NUM_THREADS} >> "${STATS_FILE}" 2>&1
./run.stack.sh ${HOME_PATH}/stack-list-${FS}-9000-${FORMAT}.txt ${NUM_STACKS} ${HEIGHT} ${WIDTH} ${HOME_PATH}/${RESULT} ${NUM_THREADS} >> "${STATS_FILE}" 2>&1
./run.stack.sh ${HOME_PATH}/stack-list-${FS}-10000-${FORMAT}.txt ${NUM_STACKS} ${HEIGHT} ${WIDTH} ${HOME_PATH}/${RESULT} ${NUM_THREADS} >> "${STATS_FILE}" 2>&1
killall -9 vmstat




