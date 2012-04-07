#!/bin/bash 

echo "==========================================================================================================="

 if [ -z "$2" ]; then 
              echo "usage: $0 <GT4_PUBLIC_IP> <GT4_PORT>"
              exit
          fi

export HOME_PATH=`pwd`
export FALKON_SERVICE_HOME=`pwd`
export GLOBUS_HOSTNAME=$1


cd ..

export GRAM_LOG=`pwd`/worker/logs/drp.log
#export GRAM_LOG="/dev/null"


export MACH_ID=`uname -n`

cd ${HOME_PATH}
                      

#killall -9 java
         
#cd ..
#export HOME_PATH2=/scratch/local/iraicu
cd ..

export GLOBUS_LOCATION=`pwd`/container

export GLOBUS_PATH=${GLOBUS_LOCATION}
export LD_LIBRARY_PATH=${GLOBUS_LOCATION}/lib
export PATH=${PATH}:${GLOBUS_LOCATION}/bin
#export JAVA_PATH=/soft/java-1.4.2-r1/bin
export EXP_START=`date +%Y.%m.%d_%k.%M.%S`
export EXP_NUM=Generic_PORTAL_V1

       
#export JAVA_HOME=/home/iraicu/j2sdk1.4.2_13_ia64
#export JAVA_HOME=`pwd`/jre
#export JAVA_HOME=/home/iraicu/jdk1.5.0_06
#export JAVA_HOME=/home/iraicu/jdk1.6.0

export PATH=${JAVA_HOME}:${JAVA_HOME}/bin:${GLOBUS_LOCATION}:${GLOBUS_LOCATION}/bin:${PATH}
#export PATH=${GLOBUS_LOCATION}:${GLOBUS_LOCATION}/bin:${PATH}

source ${GLOBUS_LOCATION}/etc/globus-devel-env.sh
#export GLOBUS_OPTIONS="-Xms1536M -Xmx1536M -XX:MaxNewSize=128m -XX:NewSize=128m -XX:SurvivorRatio=128 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:MaxTenuringThreshold=0 -XX:CMSInitiatingOccupancyFraction=60 -server -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} -DDeefConfig=${GLOBUS_LOCATION}/etc/DeeF.config"
export GLOBUS_OPTIONS="-Xms1536M -Xmx1536M -Xss128K -server -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} -DFALKON_SERVICE_HOME=${FALKON_SERVICE_HOME} -DFalkonConfig=${FALKON_SERVICE_HOME}/etc/Falkon.config"


#source ~catalind/bin/env-java5.sh                                         

export GenericPortalPORT=$2 


export MACH_ID=`uname -n`

##################################

export EXP_START=`date +%Y.%m.%d_%k.%M.%S`
export LOG_DIR=${HOME_PATH}/logs/service.${MACH_ID}.${EXP_START}
mkdir ${LOG_DIR}

mv ${HOME_PATH}/logs/*.txt ${HOME_PATH}/logs/*.jpg ${HOME_PATH}/logs/*.ps ${LOG_DIR}/
 
#export SUMMARY_FILE=${HOME_PATH}/logs/summary.txt
#echo "Time: `date`" > ${SUMMARY_FILE}
#echo "Falkon Factory Service: http://${MACH_ID}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService" >> ${SUMMARY_FILE}
#echo "Falkon Service: http://${MACH_ID}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPService" >> ${SUMMARY_FILE}
#echo "GRAM4 Location: ${GRAM_IP}" >> ${SUMMARY_FILE}
#echo "Host Type: ${hostType}" >> ${SUMMARY_FILE}
#echo "Minimum # of Hosts: ${minHostNum}" >> ${SUMMARY_FILE}
#echo "Maximum # of Hosts: ${hostNum}" >> ${SUMMARY_FILE}
#echo "# of Executors per Hosts: ${MAX_NUM_WORKERS_PER_HOST}" >> ${SUMMARY_FILE}
#echo "Minimum Resource Allocation Time (min): ${minWallTime_min}" >> ${SUMMARY_FILE}
#echo "Maximum Resource Allocation Time (min): ${maxWallTime_min}" >> ${SUMMARY_FILE}
#echo "Maximum Idle Time per Resource (ms): ${idleTime}" >> ${SUMMARY_FILE}


##################################



#echo "undeploy ogsadai"
#globus-undeploy-gar ogsadai

echo "starting GT4.0.4 container with Falkon service on port ${GenericPortalPORT}..." 
#export GT401_LOG="/disks/scratchgpfs1/iraicu/Generic.portal/logs/gt401_gpws.log"
#globus-start-container -nosec -p ${GenericPortalPORT} -quiet > ${GT401_LOG} 2>&1 &
#rm -rf /home/iraicu/.globus/persisted/GPWS/GenericPortalCommonResource.dat
#globus-start-container -nosec -p ${GenericPortalPORT}
#globus-start-container -p ${GenericPortalPORT}

#globus-start-container -nosec -p ${GenericPortalPORT} -quiet > service/logs/container.txt 2>&1 &
globus-start-container -nosec -p ${GenericPortalPORT} -quiet > service/logs/container.txt 2>&1 &
#globus-start-container -nosec -p ${GenericPortalPORT} -quiet > /dev/null 2>&1 &
#globus-start-container -nosec -p ${GenericPortalPORT} -quiet >> /home/iraicu/java/GenericPortal_v0.5_java14/logs/GenericPortalWS.txt 2>&1 &
#echo "check /disks/scratchgpfs1/iraicu/Generic.portal/logs/GT-container.log for loging info for the container..."
#echo "wait for 30 seconds for the container to start up..."
sleep 30
#echo ""
#echo "Starting DRP for dynamic resource provisioning:"
#echo "usage. /home/iraicu/java/GenericWorker_v0.5_java14/run.gram.sh <GT4_IP> <GT4_PORT> <GRAM_IP> <exec_script> <maxWallTime_min> <hostType> <hostNum> <idleTime>"
#echo "ex. /home/iraicu/java/GenericWorker_v0.5_java14/run.gram.sh tg-viz-login2.uc.teragrid.org tg-grid1.uc.teragrid.org /home/iraicu/java/GenericWorker_v0.5_java14/run.worker.sh 1440 ia32-compute 48 600000"
#cd worker
#./run.drp.sh ${MACH_ID} ${GenericPortalPORT} ${GRAM_IP} ${exec_script} ${maxWallTime_min} ${hostType} ${hostNum} ${idleTime} ${pollTime} ${minHostNum} ${minWallTime_min} >> ${GRAM_LOG} 2>&1 & 
#echo "check ${GRAM_LOG} for loging info for the GRAM component..."
#echo "waiting 45 seconds for the DRP component to start up..."
#sleep 45
#echo ""
#echo "Start 1 worker resource on the same node as the GT4 container:"
#echo "ex. /home/iraicu/java/GenericWorker_v0.5_java14/run.worker.sh 0 0"
#./run.worker.sh 0 0 &
#echo ""
#echo "To run a user client:"
#echo "ex. ./deef-client.sh localhost ${GenericPortalPORT} sleep_1 10 1 etc/client-security-config.xml"
#echo ""
#echo "Have fun!!!"
#echo "==========================================================================================================="

# to be used for the C Executors...             
cd ${FALKON_SERVICE_HOME}
cd ..
cd worker
export WORKER_PATH=`pwd`
export SECURITY_DESCRIPTION_FILE=${WORKER_PATH}/etc/client-security-config.xml
export EPR_FILE=/nfs/scratch/WorkerEPR.txt
#export SERVICE_FILE=${WORKER_PATH}/ServiceName.txt
            
${JAVA_HOME}/bin/java -classpath ${WORKER_PATH}:${CLASSPATH}:. org/globus/GenericPortal/clients/FactoryService_GP/ClientCreate -factoryURI http://${MACH_ID}:${GenericPortalPORT}/wsrf/services/GenericPortal/core/WS/GPFactoryService -epr ${EPR_FILE} -CLIENT_DESC ${SECURITY_DESCRIPTION_FILE}
                    

#echo "${MACH_ID} " > ${SERVICE_FILE}


