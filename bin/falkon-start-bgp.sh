#!/bin/bash


 if [ -z "$3" ]; then 
              echo "usage: $0 <QueueName> <NumNodes> <MaxTimeMin>"
              echo "usage: $0 prod 1024 60"
              exit 1
          fi

          
if [ -z "${FALKON_HOME}" ]; then
    echo "ERROR: environment variable FALKON_HOME not defined"
    exit 1
fi


QUEUE_NAME=$1
PROFILE_NAME="zeptocn"
NUM_NODES=$2
let NUM_ION=NUM_NODES/64
MAX_TIME_MIN=$3
SERVICE_IP="192.168.1.254"
SERVICE_PORT1=55000
SERVICE_PORT2=55001
WORKERS_PER_NODE=4


cp ${FALKON_HOME}/bin/zoid-user-script.sh ${HOME}/zoid-user-script.sh
chmod +x ${HOME}/zoid-user-script.sh

FALKON_JOB_ID=`falkon-id-get.sh N/A`                   
EXIT_CODE=$? 


    if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in geting a unique falkon ID.. 'falkon-get-id.sh N/A'"
    cqdel ${ZOID_JOB_ID}
    exit ${EXIT_CODE}
    fi



ZOID_JOB_ID=`cqsub -q ${QUEUE_NAME} -k ${PROFILE_NAME} -C ${HOME} -t ${MAX_TIME_MIN} -n ${NUM_NODES} -e LD_LIBRARY_PATH=/lib:/fuse/lib:/fuse/usr/lib /bgsys/linux/1.2.020080512/bin/bash /fuse/${FALKON_WORKER_HOME}/run.worker-c-bgp.sh ${SERVICE_IP} ${SERVICE_PORT1} ${SERVICE_PORT2} ${WORKERS_PER_NODE} ${USER} ${FALKON_JOB_ID} ${FALKON_ROOT}`

EXIT_CODE=$? 

    if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in submitting job to Cobalt.. 'cqsub -q ${QUEUE_NAME} -k ${PROFILE_NAME} -C ${HOME} -t ${MAX_TIME_MIN} -n ${NUM_NODES} -e LD_LIBRARY_PATH=/lib:/fuse/lib:/fuse/usr/lib /bgsys/linux/1.2.020080512/bin/bash /fuse/${FALKON_WORKER_HOME}/run.worker-c-bgp.sh ${SERVICE_IP} ${SERVICE_PORT1} ${SERVICE_PORT2} ${WORKERS_PER_NODE} ${USER} ${FALKON_JOB_ID} ${FALKON_ROOT}' "
    exit ${EXIT_CODE}
    fi

falkon-id-update.sh ${FALKON_JOB_ID} ${ZOID_JOB_ID}

EXIT_CODE=$? 

    if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in updating cobalt job info for falkon.. 'falkon-update-id.sh ${ZOID_JOB_ID}'"
    cqdel ${ZOID_JOB_ID}
    exit ${EXIT_CODE}
    fi


FALKON_JOBID_HOME=${FALKON_ROOT}/users/${USER}/${FALKON_JOB_ID}

echo "Submitted job ${ZOID_JOB_ID} to Cobalt, creating the job specific Falkon tree for logs and configuration in ${FALKON_JOBID_HOME}..."     
                                            
mkdir -p ${FALKON_JOBID_HOME}

cp ${FALKON_HOME}/falkon.env.bgp* ${FALKON_JOBID_HOME}/
cp -r ${FALKON_HOME}/config ${FALKON_JOBID_HOME}/
cp ${FALKON_JOBID_HOME}/config/Client-service-URIs.config2 ${FALKON_JOBID_HOME}/config/Client-service-URIs.config
mkdir -p ${FALKON_JOBID_HOME}/logs/client ${FALKON_JOBID_HOME}/logs/service ${FALKON_JOBID_HOME}/logs/provisioner ${FALKON_JOBID_HOME}/logs/worker
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
ln -s ${FALKON_HOME}/apache-ant-1.7.0 ${FALKON_JOBID_HOME}/apache-ant-1.7.0
ln -s ${FALKON_HOME}/ibm-java2-ppc64-50 ${FALKON_JOBID_HOME}/ibm-java2-ppc64-50
ln -s ${FALKON_HOME}/ibm-java2-ppc-50 ${FALKON_JOBID_HOME}/ibm-java2-ppc-50

if [ ! -d "${FALKON_JOBID_HOME}" ]; then
    echo "ERROR: invalid path ${FALKON_JOBID_HOME}... exiting"
    cqdel ${ZOID_JOB_ID}
    exit 1
fi

echo "Succesfully submitted the job to Cobalt, and setup job specific Falkon tree!"
echo "To monitor the job status, type 'cqstat | grep ${USER}'; once it is in running state, you can use the Falkon specific command ...."
echo "To submit your Falkon-based workload, type: ....; you can do this any time, the falkon workload will wait for the resources to come online, and will only be submitted when everything is ready; the script is run in the background, so the workload will run even if the ssh session gets disconnected."
echo ""
echo "Remember, your job id is ${ZOID_JOB_ID}, and if you need to look through the logs manually for anything, remember that you can find them at ${HOME}/${ZOID_JOB_ID}.output, ${HOME}/${ZOID_JOB_ID}.error, and ${FALKON_JOBID_HOME}/logs/..."


