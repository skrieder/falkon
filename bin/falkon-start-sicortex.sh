#!/bin/bash


 if [ -z "$8" ]; then 
              echo "usage: $0 <QueueName> <NumNodes> <MaxTimeMin> <WorkersPerNode> <WS_IP> <WS_PORT> <TCPCore_PORT1> <TCPCore_PORT2>"
              echo "usage: $0 prod 10 60 6 128.135.125.17 50001 56000 56001"
              exit 1
          fi

          
if [ -z "${FALKON_HOME}" ]; then
    echo "ERROR: environment variable FALKON_HOME not defined"
    exit 1
fi


QUEUE_NAME=$1
#PROFILE_NAME="zeptocn"
NUM_NODES=$2
#let NUM_ION=NUM_NODES/64
MAX_TIME_MIN=$3
WORKERS_PER_NODE=$4
let NUM_CPUs=NUM_NODES*WORKERS_PER_NODE
FALKON_IP=$5
FALKON_PORT=$6
TCPCORE_PORT1=$7
TCPCORE_PORT2=$8


#cp ${FALKON_HOME}/bin/zoid-user-script.sh ${HOME}/zoid-user-script.sh
#chmod +x ${HOME}/zoid-user-script.sh

FALKON_JOB_ID=`falkon-id-get.sh N/A`                   
EXIT_CODE=$? 


    if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in geting a unique falkon ID.. 'falkon-get-id.sh N/A'"
    #cqdel ${ZOID_JOB_ID}
    exit ${EXIT_CODE}
    fi



#ZOID_JOB_ID=`cqsub -q ${QUEUE_NAME} -k ${PROFILE_NAME} -C ${HOME} -t ${MAX_TIME_MIN} -n ${NUM_NODES} -e LD_LIBRARY_PATH=/lib:/fuse/lib:/fuse/usr/lib /bgsys/linux/1.2.020080512/bin/bash /fuse/${FALKON_WORKER_HOME}/run.worker-c-bgp.sh ${SERVICE_IP} ${SERVICE_PORT1} ${SERVICE_PORT2} ${WORKERS_PER_NODE} ${USER} ${FALKON_JOB_ID} ${FALKON_ROOT}`
#should invoke ssh to start the workers remotely :)
#ssh fd-login.mcs.anl.gov "command"

#should use the actual slurm job id
ZOID_JOB_ID="00000"

#EXIT_CODE=$? 

#    if [ "${EXIT_CODE}" -ne "0" ]; then
    #echo "Error in submitting job to Cobalt.. 'cqsub -q ${QUEUE_NAME} -k ${PROFILE_NAME} -C ${HOME} -t ${MAX_TIME_MIN} -n ${NUM_NODES} -e LD_LIBRARY_PATH=/lib:/fuse/lib:/fuse/usr/lib /bgsys/linux/1.2.020080512/bin/bash /fuse/${FALKON_WORKER_HOME}/run.worker-c-bgp.sh ${SERVICE_IP} ${SERVICE_PORT1} ${SERVICE_PORT2} ${WORKERS_PER_NODE} ${USER} ${FALKON_JOB_ID} ${FALKON_ROOT}' "
#    echo "Error in submitting job..."
#    exit ${EXIT_CODE}
#    fi

falkon-id-update.sh ${FALKON_JOB_ID} ${ZOID_JOB_ID}

EXIT_CODE=$? 

    if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in updating cobalt job info for falkon.. 'falkon-update-id.sh ${ZOID_JOB_ID}'"
    #cqdel ${ZOID_JOB_ID}
    #should delete the remote job
    exit ${EXIT_CODE}
    fi


FALKON_JOBID_HOME=${FALKON_ROOT}/users/${USER}/${FALKON_JOB_ID}

echo "Creating the job specific Falkon tree for logs and configuration in ${FALKON_JOBID_HOME}..."     
                                            
mkdir -p ${FALKON_JOBID_HOME}

cp ${FALKON_HOME}/falkon.env.ci* ${FALKON_JOBID_HOME}/
cp -r ${FALKON_HOME}/config ${FALKON_JOBID_HOME}/
cp ${FALKON_JOBID_HOME}/config/Client-service-URIs.config2 ${FALKON_JOBID_HOME}/config/Client-service-URIs.config
mkdir -p ${FALKON_JOBID_HOME}/logs/client ${FALKON_JOBID_HOME}/logs/service ${FALKON_JOBID_HOME}/logs/provisioner ${FALKON_JOBID_HOME}/logs/worker

                           
#DATE=`date +%s`      
#echo "$DATE: pre-creating log dirs for Falkon service..."
#RACK_START=0
#RACK_END=48
#SEQUENCE_DIR=`seq -w ${RACK_START} ${RACK_END}`
#PSET_START=1
#PSET_END=16
#for a in ${SEQUENCE_DIR}
#do
#    for ((b=${PSET_START}; b <= ${PSET_END} ; b++))  # Double parentheses, and "LIMIT" with no "$".
#    do
#        DIR_NAME="ion-R${a}-${b}"
#        mkdir -p ${FALKON_JOBID_HOME}/logs/service/$DIR_NAME
#    done
#done

#for ((b=${PSET_START}; b <= ${PSET_END} ; b++))  # Double parentheses, and "LIMIT" with no "$".
#do
#        DIR_NAME="ion-${b}"
#        mkdir -p ${FALKON_JOBID_HOME}/logs/service/$DIR_NAME
#done


#DATE=`date +%s`      
#echo "$DATE: done creating log dirs for Falkon service!"



#FALKON_HOME_RAM=/tmp/${USER}/falkon

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
ln -s ${FALKON_HOME}/jdk1.6.0_07 ${FALKON_JOBID_HOME}/jdk1.6.0_07


if [ ! -d "${FALKON_JOBID_HOME}" ]; then
    echo "ERROR: invalid path ${FALKON_JOBID_HOME}... exiting"
    #cqdel ${ZOID_JOB_ID}
    exit 1
fi

cd ${FALKON_JOBID_HOME}
source falkon.env.ci

echo "TCPCore_RECV_PORT=${TCPCORE_PORT1}" >> ${FALKON_CONFIG}/Falkon-TCPCore.config
echo "TCPCore_SEND_PORT=${TCPCORE_PORT2}" >> ${FALKON_CONFIG}/Falkon-TCPCore.config

falkon-service.sh ${FALKON_PORT} ${FALKON_CONFIG}/Falkon-TCPCore.config
sleep 10
falkon-tcpcore-start.sh ${FALKON_IP} ${FALKON_PORT}



    EXIT_CODE=$? 

if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in executing command `falkon-tcpcore-start.sh`... exit code ${EXIT_CODE}"
    echo "${FALKON_IP} ${FALKON_PORT}" >> ${FALKON_CONFIG}/Client-service-URIs-bad.config
        DATE=`date`
    echo "${DATE} : failed : Error in executing command `falkon-tcpcore-start.sh`... exit code ${EXIT_CODE}"
    
    exit ${EXIT_CODE}
fi

    DATE=`date`
    echo "${DATE} : started the Falkon service succesfully"


echo "${FALKON_IP} ${FALKON_PORT}" >> ${FALKON_CONFIG}/Client-service-URIs.config
    EXIT_CODE=$? 

if [ "${EXIT_CODE}" -ne "0" ]; then
    echo "Error in executing command `echo $FALKON_IP $FALKON_PORT ` to ${FALKON_CONFIG}/Client-service-URIs.config... exit code ${EXIT_CODE}"
    
        DATE=`date`
    echo "${DATE} : failed : Error in executing command `echo $FALKON_IP $FALKON_PORT ` to ${FALKON_CONFIG}/Client-service-URIs.config... exit code ${EXIT_CODE}"
    exit ${EXIT_CODE}
fi

    DATE=`date`
    echo "${DATE} : registered the service by appending ${FALKON_IP} ${FALKON_PORT} to ${FALKON_CONFIG}/Client-service-URIs.config"



#echo "exit code ${EXIT_CODE}"
    DATE=`date`

    echo "${DATE} : completed the Falkon startup!"

    echo "${DATE} : setting up tunnel from the SiCortex to ${FALKON_IP} ${TCPCORE_PORT1} ${TCPCORE_PORT2}"


ssh -f fd-login.mcs.anl.gov "/home/iraicu/falkon/worker/create.tunnel.sh ${FALKON_IP} ${TCPCORE_PORT1} ${TCPCORE_PORT2}"

    echo "${DATE} : waiting for 5 sec to setup tunnel..."

       
sleep 5

    echo "${DATE} : submitting job to slurm on the SiCortex for ${NUM_CPUs} CPUs for ${MAX_TIME_MIN} minutes..."

#the path (/home/iraicu/falkon/worker/slurm.falkon-sicortex) is on the remote machine, should not be changed
#ssh fd-login.mcs.anl.gov "sbatch -n ${NUM_CPUs} -t ${MAX_TIME_MIN} /home/iraicu/falkon/worker/slurm.falkon-sicortex ${FALKON_IP} ${TCPCORE_PORT1} ${TCPCORE_PORT2}"
#ssh fd-login.mcs.anl.gov "sbatch /home/iraicu/falkon/worker/slurm.falkon-sicortex 172.31.223.206 ${TCPCORE_PORT1} ${TCPCORE_PORT2} ${NUM_CPUs} ${MAX_TIME_MIN}"

ssh -f fd-login.mcs.anl.gov "srun -p scx-comp --job-name=Falkon -t ${MAX_TIME_MIN} -n ${NUM_CPUs} --cpus-per-task=1 --ntasks-per-node=6 -q -D /home/iraicu/falkon/worker -l ./run.worker-c-ram-ip.sh 172.31.223.206 ${TCPCORE_PORT1} ${TCPCORE_PORT2}"

EXIT_CODE=$? 
    if [ "${EXIT_CODE}" -ne "0" ]; then
    #echo "Error in submitting job to Cobalt.. 'cqsub -q ${QUEUE_NAME} -k ${PROFILE_NAME} -C ${HOME} -t ${MAX_TIME_MIN} -n ${NUM_NODES} -e LD_LIBRARY_PATH=/lib:/fuse/lib:/fuse/usr/lib /bgsys/linux/1.2.020080512/bin/bash /fuse/${FALKON_WORKER_HOME}/run.worker-c-bgp.sh ${SERVICE_IP} ${SERVICE_PORT1} ${SERVICE_PORT2} ${WORKERS_PER_NODE} ${USER} ${FALKON_JOB_ID} ${FALKON_ROOT}' "
    echo "Error in submitting job..."
    exit ${EXIT_CODE}
    fi


echo "Succesfully submitted the job, and setup job specific Falkon tree!"
echo "To submit your Falkon-based workload, type: ...."
echo ""
echo "Remember, your job id is ${ZOID_JOB_ID}, and if you need to look through the logs manually for anything, remember that you can find them at ${FALKON_JOBID_HOME}/logs/..."


exit ${EXIT_CODE}
