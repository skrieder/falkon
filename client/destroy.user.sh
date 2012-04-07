#!/bin/bash
                  
export HOME_PATH=`pwd`
export SCRATCH=${HOME_PATH}/scratch/
export EPR_FILE=${SCRATCH}/epr-user.txt

wsrf-destroy -e ${EPR_FILE}
rm -rf ${EPR_FILE}
