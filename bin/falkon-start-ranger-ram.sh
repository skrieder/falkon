#!/bin/bash

#echo "DEBUG: 1"


 if [ -z "$3" ]; then 
              echo "usage: $0 <QueueName> <NumNodes> <MaxWallTime>"
              echo "usage: $0 development 10 00:01:00"
              echo "-- or --"
              echo "usage: $0 <QueueName> <NumNodes> <MaxWallTime> <WorkersPerNode>"
              echo "usage: $0 development 10 00:01:00 16"
              echo "----"
              echo "queue options: development large normal reservation serial"
              exit 1
          fi

#echo "DEBUG: 1"

          
if [ -z "${FALKON_HOME}" ]; then
    echo "ERROR: environment variable FALKON_HOME not defined"
    exit 1
fi

#echo "DEBUG: 1"


QUEUE_NAME=$1
#PROFILE_NAME="zeptocn"
NUM_NODES=$2
#echo "DEBUG: 1"

let NUM_ION=NUM_NODES
MAX_TIME_MIN=$3
#need to determine service IP
#echo "DEBUG: 1"

SERVICE_IP="127.0.0.1"
SERVICE_PORT1=55000
SERVICE_PORT2=55001
WORKERS_PER_NODE=16
#echo "DEBUG: 1"

if [ ! -z $4 ];then
   WORKERS_PER_NODE=$4
fi 

#echo "DEBUG: 1"

let NUM_WORKERS=NUM_NODES*WORKERS_PER_NODE

#echo "DEBUG: 1"

#cp ${FALKON_HOME}/bin/zoid-user-script.sh ${HOME}/zoid-user-script.sh
#chmod +x ${HOME}/zoid-user-script.sh



FALKON_JOB_ID=`falkon-id-get.sh N/A`                   
EXIT_CODE=$? 

#echo "DEBUG: 1"

    if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in geting a unique falkon ID.. 'falkon-get-id.sh N/A'"
    #qdel ${SGE_JOB_ID}
    exit ${EXIT_CODE}
    fi


#echo "DEBUG: 1"

FALKON_JOBID_HOME=${FALKON_ROOT}/users/${USER}/${FALKON_JOB_ID}

#echo "DEBUG: 1"


echo "Submitted job ${SGE_JOB_ID} to SGE, creating the job specific Falkon tree for logs and configuration in ${FALKON_JOBID_HOME}..."     
                                            
mkdir -p ${FALKON_JOBID_HOME}

#echo "DEBUG: 1"


cp ${FALKON_HOME}/falkon.env.ranger ${FALKON_JOBID_HOME}/
cp -r ${FALKON_HOME}/config ${FALKON_JOBID_HOME}/
cp ${FALKON_JOBID_HOME}/config/Client-service-URIs.config2 ${FALKON_JOBID_HOME}/config/Client-service-URIs.config
mkdir -p ${FALKON_JOBID_HOME}/logs/client ${FALKON_JOBID_HOME}/logs/service ${FALKON_JOBID_HOME}/logs/provisioner ${FALKON_JOBID_HOME}/logs/worker

#echo "DEBUG: 1"

                           
DATE=`date +%s`      
echo "$DATE: pre-creating log dirs for Falkon service..."
RACK_START=101
RACK_END=182
SEQUENCE_DIR=`seq -w ${RACK_START} ${RACK_END}`
for a in ${SEQUENCE_DIR}
do
    PSET_START=101
    PSET_END=112
    for ((b=${PSET_START}; b <= ${PSET_END} ; b++))  # Double parentheses, and "LIMIT" with no "$".
    do
        DIR_NAME="i${a}-${b}.ranger.tacc.utexas.edu"
        mkdir -p ${FALKON_JOBID_HOME}/logs/service/$DIR_NAME
    done

    PSET_START=201
    PSET_END=212
    for ((b=${PSET_START}; b <= ${PSET_END} ; b++))  # Double parentheses, and "LIMIT" with no "$".
    do
        DIR_NAME="i${a}-${b}.ranger.tacc.utexas.edu"
        mkdir -p ${FALKON_JOBID_HOME}/logs/service/$DIR_NAME
    done

    PSET_START=301
    PSET_END=312
    for ((b=${PSET_START}; b <= ${PSET_END} ; b++))  # Double parentheses, and "LIMIT" with no "$".
    do
        DIR_NAME="i${a}-${b}.ranger.tacc.utexas.edu"
        mkdir -p ${FALKON_JOBID_HOME}/logs/service/$DIR_NAME
    done

    PSET_START=401
    PSET_END=412
    for ((b=${PSET_START}; b <= ${PSET_END} ; b++))  # Double parentheses, and "LIMIT" with no "$".
    do
        DIR_NAME="i${a}-${b}.ranger.tacc.utexas.edu"
        mkdir -p ${FALKON_JOBID_HOME}/logs/service/$DIR_NAME
    done

done

#echo "DEBUG: 1"


DATE=`date +%s`      
echo "$DATE: done creating log dirs for Falkon service!"

#echo "DEBUG: 1"


FALKON_HOME_RAM=/tmp/${USER}/falkon

#echo "DEBUG: 1"

ln -s ${FALKON_HOME}/apps ${FALKON_JOBID_HOME}/apps
ln -s ${FALKON_HOME}/container ${FALKON_JOBID_HOME}/container
ln -s ${FALKON_HOME}/service ${FALKON_JOBID_HOME}/service
ln -s ${FALKON_HOME}/worker ${FALKON_JOBID_HOME}/worker
ln -s ${FALKON_HOME}/AstroPortal ${FALKON_JOBID_HOME}/AstroPortal
ln -s ${FALKON_HOME}/client ${FALKON_JOBID_HOME}/client
ln -s ${FALKON_HOME}/monitor ${FALKON_JOBID_HOME}/monitor
ln -s ${FALKON_HOME}/bin ${FALKON_JOBID_HOME}/bin
ln -s ${FALKON_HOME}/config ${FALKON_JOBID_HOME}/config
ln -s ${FALKON_HOME}/ploticus ${FALKON_JOBID_HOME}/ploticus
ln -s ${FALKON_HOME}/webserver ${FALKON_JOBID_HOME}/webserver
ln -s ${FALKON_HOME}/workloads ${FALKON_JOBID_HOME}/workloads
ln -s ${FALKON_HOME}/id ${FALKON_JOBID_HOME}/id
#ln -s ${FALKON_HOME}/apache-ant-1.7.0 ${FALKON_JOBID_HOME}/apache-ant-1.7.0
#ln -s ${FALKON_HOME}/ibm-java2-ppc64-50 ${FALKON_JOBID_HOME}/ibm-java2-ppc64-50
ln -s ${FALKON_HOME}/jdk1.6.0 ${FALKON_JOBID_HOME}/jdk1.6.0
#ln -s ${FALKON_HOME_RAM}/ibm-java2-ppc-50 ${FALKON_JOBID_HOME}/ibm-java2-ppc-50
ln -s ${FALKON_HOME}/falkon_ranger.tgz ${FALKON_JOBID_HOME}/falkon_ranger.tgz


#echo "DEBUG: 1"

if [ ! -d "${FALKON_JOBID_HOME}" ]; then
    echo "ERROR: invalid path ${FALKON_JOBID_HOME}... exiting"
    qdel ${SGE_JOB_ID}
    exit 1
fi



#echo "DEBUG: 1"

#need to substitute with sge call
#qsub -pe 1way 32 -q development -l h_rt=00:01:00 job.simple2
#SGE_JOB_ID=`cqsub -q ${QUEUE_NAME} -k ${PROFILE_NAME} -C ${HOME} -t ${MAX_TIME_MIN} -n ${NUM_NODES} /bin/bash ${FALKON_WORKER_HOME}/run.worker-c-bgp.sh ${SERVICE_IP} ${SERVICE_PORT1} ${SERVICE_PORT2} ${WORKERS_PER_NODE} ${USER} ${FALKON_JOB_ID} ${FALKON_ROOT}`

SGE_JOB_ID=`qsub -terse -V -pe 1way ${NUM_WORKERS} -q ${QUEUE_NAME} -l h_rt=${MAX_TIME_MIN} -N Falkon -j y -wd $HOME /share/sge/default/pe_scripts/ibrun ${FALKON_HOME}/bin/falkon-service_workers-ranger.sh ${SERVICE_IP} ${SERVICE_PORT1} ${SERVICE_PORT2} ${WORKERS_PER_NODE} ${USER} ${FALKON_JOB_ID} ${FALKON_ROOT} | tail -n 1`
#SGE_JOB_ID=`qsub -terse -V -pe 1way ${NUM_WORKERS} -q ${QUEUE_NAME} -l h_rt=${MAX_TIME_MIN} -N Falkon -j y -wd $HOME /share/sge/default/pe_scripts/ibrun ${FALKON_HOME}/bin/falkon-service_workers-ranger.sh ${SERVICE_IP} ${SERVICE_PORT1} ${SERVICE_PORT2} ${WORKERS_PER_NODE} ${USER} ${FALKON_JOB_ID} ${FALKON_ROOT}`

#SGE_JOB_ID=`qsub -V -pe 1way 16 -q development -l h_rt=00:01:00 -N Falkon -j y -wd $HOME /share/sge/default/pe_scripts/ibrun /share/home/00839/tg458952/falkon-test.sh

EXIT_CODE=$? 

/bin/sleep 10

#echo "DEBUG: 1"

    if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in submitting job to SGE.. '...' "
    exit ${EXIT_CODE}
    fi

#echo "DEBUG: 1"

falkon-id-update.sh ${FALKON_JOB_ID} ${SGE_JOB_ID}

EXIT_CODE=$? 

#echo "DEBUG: 1"

    if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in updating SGE job info for falkon.. 'falkon-update-id.sh ${SGE_JOB_ID}'"
    qdel ${SGE_JOB_ID}
    exit ${EXIT_CODE}
    fi

#echo "DEBUG: 1"

echo "TCPCore_RECV_PORT=${SERVICE_PORT1}" >> ${FALKON_JOBID_HOME}/config/Falkon-TCPCore.config
echo "TCPCore_SEND_PORT=${SERVICE_PORT2}" >> ${FALKON_JOBID_HOME}/config/Falkon-TCPCore.config

echo "Succesfully submitted the job to SGE, and setup job specific Falkon tree!"
echo "To monitor the job status, type 'cqstat | grep ${USER}'; once it is in running state, you can use the Falkon specific command ...."
echo "To submit your Falkon-based workload, type: ....; you can do this any time, the falkon workload will wait for the resources to come online, and will only be submitted when everything is ready; the script is run in the background, so the workload will run even if the ssh session gets disconnected."
echo ""
echo "Remember, your job id is ${SGE_JOB_ID}, and if you need to look through the logs manually for anything, remember that you can find them at ${HOME}/${SGE_JOB_ID}.output, ${HOME}/${SGE_JOB_ID}.error, and ${FALKON_JOBID_HOME}/logs/..."

#echo "DEBUG: 1"

