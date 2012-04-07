//SVN version
/*
 * This file is licensed under the terms of the Globus Toolkit Public License
 * v3, found at http://www.globus.org/toolkit/legal/4.0/license-v3.html.
 * 
 * This notice must appear in redistributions of this file, with or without
 * modification.
 */
package org.globus.GenericPortal.services.core.WS.impl;

import java.rmi.RemoteException;

import org.globus.GenericPortal.stubs.GPService_instance.*;
import org.globus.wsrf.NoSuchResourceException;
import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.ResourceContextException;
import org.globus.wsrf.ResourceKey;

import org.globus.wsrf.impl.ResourceContextImpl;
import org.globus.wsrf.impl.ResourceHomeImpl;

import java.util.*; 

import java.net.*;
import java.io.*;

import java.lang.Object;
import java.lang.*;

import org.globus.GenericPortal.common.*;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class GPService
{

    static final Log logger = LogFactory.getLog(GPService.class);
    //public boolean DIPERF = false;

    /*
     * Private method that gets a reference to the resource specified in the
     * endpoint reference.
     */
    private GPResource getResource() throws RemoteException {
        //StopWatch ct = new StopWatch(false, false, true);
        //ct.start();


        Object resource = null;
        try
        {
            resource = ResourceContext.getResourceContext().getResource();
        }
        catch (NoSuchResourceException e)
        {
            throw new RemoteException("Specified resource does not exist", e);
        }
        catch (ResourceContextException e)
        {
            throw new RemoteException("Error during resource lookup", e);
        }
        catch (Exception e)
        {
            throw new RemoteException("", e);
        }

        GPResource gpResource = (GPResource) resource;

        gpResource.logger.trace("GPService: getResource()");

        //ct.stop();
        //if(DIPERF) gpResource.logger.warn("GPService:subcall:getResource(): " + ct.getElapsedTime() + " ms");

        return(GPResource) resource;
    }


    private GPResourceHome getResourceHome() throws RemoteException {
        //StopWatch ct = new StopWatch(false, false, true);
        //   ct.start();

        Object resourceHome = null;
        try
        {
            resourceHome = ResourceContext.getResourceContext().getResourceHome();
        }
        //catch (NoSuchResourceException e) {
        //	throw new RemoteException("Specified resourceHome does not exist", e);
        //} 
        catch (ResourceContextException e)
        {
            throw new RemoteException("Error during resourceHome lookup", e);
        }
        catch (Exception e)
        {
            throw new RemoteException("", e);
        }

        //GPResourceHome gpResourceHome = (GPResourceHome) resourceHome;

        GPResource gpResource = getResource();
        gpResource.logger.debug("GPService: getResourceHome()");

        // ct.stop();
        // if(DIPERF) gpResource.logger.warn("GPService:subcall:getResourceHome(): " + ct.getElapsedTime() + " ms");


        return(GPResourceHome) resourceHome;
    }

    /* Implementation of status, workerRegistration, and getNumJobs operations */



    //user job submittion 
    public UserJobResponse userJob(UserJob job) throws RemoteException 
    {
        StopWatch ct = null;
        if (GPResourceHome.perfProfile.PROFILE)
        {

            ct = new StopWatch(false, false, true);
            ct.start();
        }

        UserJobResponse ujr = null;

        try
        {

            GPResource gpResource = getResource();
            ujr = gpResource.submitTasks(job);

        }
        catch (Exception e)
        {
            ujr = new UserJobResponse();
            ujr.setValid(false);
            ujr.setService(false);

        }

        if (GPResourceHome.perfProfile.PROFILE)
        {
            ct.stop();
            GPResourceHome.perfProfile.addSample("submit_task", ct.getElapsedTimeMX());
            ct = null;
        }

        job = null;

        return ujr;


    }


    //user job submittion 
    public UserResultResponse userResult(UserResult ur) throws RemoteException 
    {

        StopWatch ct = null;
        if (GPResourceHome.perfProfile.PROFILE)
        {

            ct = new StopWatch(false, false, true);
            ct.start();
        }

        UserResultResponse urr = null;

        try
        {

            GPResource gpResource = getResource();

            urr = gpResource.retrieveTasks(ur);
        }
        catch (Exception e)
        {
            urr = new UserResultResponse();

            urr.setValid(false);

        }

        if (GPResourceHome.perfProfile.PROFILE)
        {
            ct.stop();
            GPResourceHome.perfProfile.addSample("deliver_task", ct.getElapsedTimeMX());
            ct = null;
        }
        return urr;

    }

    //worker interaction
    public WorkerRegistrationResponse workerRegistration(WorkerRegistration wr ) throws RemoteException 
    {

        StopWatch ct = null;
        if (GPResourceHome.perfProfile.PROFILE)
        {

            ct = new StopWatch(false, false, true);
            ct.start();
        }

        WorkerRegistrationResponse wrr = null;




        try
        {


            //this is only a hack since it seems that this node is super slow...
            String wID = wr.getMachID();


            //if (wID.startsWith("tg-v024") || wID.startsWith("tg-v083") || wID.startsWith("tg-v051") || wID.startsWith("tg-c001") || wID.startsWith("tg-c002") || wID.startsWith("tg-c004") || wID.startsWith("tg-c005"))
            if (false)
            {
                //deny registration
                wrr = new WorkerRegistrationResponse();

                //set to true so the worker doesn't keep trying...
                wrr.setValid(true);
            }
            else
            {
                GPResource gpResource = getResource();

                wrr = gpResource.registerWorker(wr);

            }     


        }
        catch (Exception e)
        {
            wrr = new WorkerRegistrationResponse();

            wrr.setValid(false);


        }


        if (GPResourceHome.perfProfile.PROFILE)
        {
            ct.stop();
            GPResourceHome.perfProfile.addSample("worker_registration", ct.getElapsedTimeMX());
            ct = null;
        }

        return wrr;


    }   

    //worker interaction
    public WorkerDeRegistrationResponse workerDeRegistration(WorkerDeRegistration wr) throws RemoteException {


        StopWatch ct = null;
        if (GPResourceHome.perfProfile.PROFILE)
        {

            ct = new StopWatch(false, false, true);
            ct.start();
        }


        WorkerDeRegistrationResponse wdrr = null;

        try
        {

            GPResource gpResource = getResource();

            wdrr = gpResource.deregisterWorker(wr);
        }
        catch (Exception e)
        {
            wdrr = new WorkerDeRegistrationResponse();

            wdrr.setValid(false);


        }


        if (GPResourceHome.perfProfile.PROFILE)
        {
            ct.stop();
            GPResourceHome.perfProfile.addSample("worker_deregistration", ct.getElapsedTimeMX());
            ct = null;
        }


        return wdrr;

    }   




    //add to busy... then to free
    //worker interaction
    //task dispatch
    public WorkerWorkResponse workerWork(WorkerWork sourceWorker) //throws RemoteException 
    {
        /*
        StopWatch ct = null;
        if (GPResourceHome.perfProfile.PROFILE) {

            ct = new StopWatch(false, false, true);
            ct.start();
        }
        */

        WorkerWorkResponse RP = null;

        try
        {

            GPResource gpResource = getResource();

            RP = gpResource.dispatchWork(sourceWorker);
        }

        catch (Exception e)
        {
            //WorkerWorkResponse RP = null;
            e.printStackTrace();

            if (sourceWorker.getMachID() != null)
            {
                GPResourceHome.commonState.setFreeWorker(sourceWorker.getMachID());
            }

            RP = new WorkerWorkResponse();
            RP.setValid(false);
        }


        /*
        if (GPResourceHome.perfProfile.PROFILE) {
            ct.stop();
            GPResourceHome.perfProfile.addSample("dispatch_work", ct.getElapsedTimeMX());
            ct = null;
        }
        */


        return RP;
    }   



    //worker interaction
    //public WorkerResultResponse workerResult(WorkerResult result) throws RemoteException 
    public WorkerResultResponse workerResult(WorkerResult result) //throws RemoteException 
    {
        /*
        StopWatch ct = null;
        if (GPResourceHome.perfProfile.PROFILE) {

            ct = new StopWatch(false, false, true);
            ct.start();
        }          */
        WorkerResultResponse wrr = null;

        try
        {

            GPResource gpResource = getResource();

            wrr = gpResource.receiveResults(result);
        }
        catch (Exception e)
        {
            wrr = new WorkerResultResponse();

            if (result.getMachID() != null)
            {
                GPResourceHome.commonState.setFreeWorker(result.getMachID());
            }
            wrr.setValid(false);
            wrr.setMoreWork(false);


        }
        /*
        if (GPResourceHome.perfProfile.PROFILE) {
            ct.stop();
            GPResourceHome.perfProfile.addSample("receive_result", ct.getElapsedTimeMX());
            ct = null;
        }
          */

        result = null;
        return wrr;
    } 


    public InitResponse init(Init i) throws RemoteException {

        if (GPResourceHome.perfProfile.PROFILE)
        {
            GPResourceHome.perfProfile.reset();
            GPResourceHome.perfProfile.captureCPU = true;
            GPResourceHome.perfProfile.addSample("x_start", System.currentTimeMillis());

        }


        StopWatch ct = null;
        if (GPResourceHome.perfProfile.PROFILE)
        {

            ct = new StopWatch(false, false, true);
            ct.start();
        }

        GPResource gpResource = getResource();
        GPResourceHome gpResourceHome = getResourceHome();
        boolean retValue = false;

        //this is done so we can use a resource multiple times for different jobs
        try
        {

            gpResource.initState();

            String userID = i.getMachID();

            //this is used for a single notification end point for the entire resource, in case the per task notification end point is not specified
            gpResource.userID = userID; 


            String sKey = (String)gpResource.getID();


            gpResource.resourceKey = gpResourceHome.getKey(sKey);
            gpResource.logger.debug("Key translation... " + sKey + " ==> " + gpResource.resourceKey.getValue());

            GPResourceHome.commonState.resourceSet.add(gpResource.resourceKey.getValue());


            //only do this for the experiments...
            GPResourceHome.commonState.resetCacheHitMiss();

            gpResource.logger.debug("Initializing... success!");

            retValue = true;


        }
        catch (Exception e)
        {
            //GPResource gpResource = getResource();
            gpResource.logger.debug("ERROR: initState(): " + e);
            retValue = false;
        }


        if (GPResourceHome.perfProfile.PROFILE)
        {
            ct.stop();
            GPResourceHome.perfProfile.addSample("resource_init", ct.getElapsedTimeMX());
            ct = null;
        }

        return new InitResponse(retValue);

    }


    public DeInitResponse deInit(DeInit i) throws RemoteException {


        StopWatch ct = null;
        if (GPResourceHome.perfProfile.PROFILE)
        {

            ct = new StopWatch(false, false, true);
            ct.start();
        }
        GPResource gpResource = getResource();
        //GPResourceHome gpResourceHome = getResourceHome();
        boolean retValue = false;

        gpResource.logger.debug("DeIniting state in the resource...");

        try
        {

            gpResource.deInitState();

            GPResourceHome.commonState.resourceSet.remove(gpResource.resourceKey.getValue());

            gpResource.logger.debug("DeInit... success!");
            retValue = true;
        }
        catch (Exception e)
        {
            //throw new RemoteException(e.getMessage());
            retValue = false;
        }


        if (GPResourceHome.perfProfile.PROFILE)
        {
            ct.stop();
            GPResourceHome.perfProfile.addSample("resource_deinit", ct.getElapsedTimeMX());
            ct = null;
        }

        if (GPResourceHome.perfProfile.PROFILE)
        {
            GPResourceHome.perfProfile.captureCPU = false;
            GPResourceHome.perfProfile.addSample("x_finish", System.currentTimeMillis());

            GPResourceHome.perfProfile.writePerfProfile();
        }





        return new DeInitResponse(retValue);
    }


    public StatusResponse status(Status s) throws RemoteException {

        StopWatch ct = null;
        if (GPResourceHome.perfProfile.PROFILE)
        {

            ct = new StopWatch(false, false, true);
            ct.start();
        }

        StatusResponse sr = null;
        GPResource gpResource = getResource();
        //GPResourceHome gpResourceHome = getResourceHome();


        GPResourceHome.gpResourceWorker = gpResource;

        //int numWorkers = 0;

        try
        {

            if (GPResourceHome.commonState == null)
            {
                GPResourceHome.load_common();
                //throw new RemoteException("workerDeRegistration(): commonState == null ");

            }
            //else
            //numWorkers = GPResourceHome.commonState.numWorkers();
            //gpResource.logger.debug("Available workers: " + numWorkers);


            gpResource.drpAllocation = s.getDrpAllocation();
            gpResource.drpMinResc = s.getDrpMinResc();
            gpResource.drpMaxResc = s.getDrpMaxResc();
            gpResource.drpMinTime = s.getDrpMinTime();
            gpResource.drpMaxTime = s.getDrpMaxTime();
            gpResource.drpIdle = s.getDrpIdle();
            gpResource.resourceAllocated = s.getResourceAllocated();

            GPResourceHome.commonState.resourceAllocated = gpResource.resourceAllocated;


            sr = new StatusResponse();
            sr.setQueueLength((int)GPResourceHome.commonState.getWaitingTasks());
            sr.setActiveTasks((int)GPResourceHome.commonState.getActiveTasks());
            sr.setNumWorkers(GPResourceHome.commonState.roundRobin.size()); 

            sr.setBusyWorkers(GPResourceHome.commonState.busyWorkers.size()); 
            sr.setNewWorkers(gpResource.getNewWorkers()); 
            sr.setDeregisteredWorkers(gpResource.getDeregisteredWorkers()); 
            sr.setValid(true);


            //gpResource.logger.info("status(): GPWS_queuedTasks=" + GPResourceHome.commonState.getAvr_Q_Length() + " GPWS_activeTasks=" + GPResourceHome.commonState.getNum_Active_Tasks() + " GPWS_activeResources=" + GPResourceHome.commonState.numWorkers());


            //return sr;

        }
        catch (Exception e)
        {
            sr = new StatusResponse();
            sr.setValid(false);


            //return sr;
        }


        if (GPResourceHome.perfProfile.PROFILE)
        {
            ct.stop();
            GPResourceHome.perfProfile.addSample("status", ct.getElapsedTimeMX());
            ct = null;
        }

        return sr;


    }


    public StatusUserResponse statusUser(StatusUser s) throws RemoteException {

        StopWatch ct = null;
        if (GPResourceHome.perfProfile.PROFILE)
        {

            ct = new StopWatch(false, false, true);
            ct.start();
        }

        StatusUserResponse sur = null;
        GPResource gpResource = getResource();
        //GPResourceHome gpResourceHome = getResourceHome();


        //int numWorkers = 0;

        try
        {

            if (GPResourceHome.commonState == null)
            {
                GPResourceHome.load_common();
                //throw new RemoteException("workerDeRegistration(): commonState == null ");

            }
            //else
            //numWorkers = GPResourceHome.commonState.numWorkers();
            //gpResource.logger.debug("Available workers: " + numWorkers);


            sur = new StatusUserResponse();
            sur.setQueueLength(GPResourceHome.commonState.getAvr_Q_Length());
            sur.setActiveTasks(GPResourceHome.commonState.getNum_Active_Tasks());
            sur.setNumWorkers(GPResourceHome.commonState.numWorkers());

            sur.setNumWorkerResults(gpResource.getNumWorkerResults());
            sur.setQueuedTask(gpResource.getqueuedTask());
            sur.setActiveTask(gpResource.getactiveTask());

            sur.setValid(true);

            //gpResource.logger.info("statusUser(): " + sur.getQueueLength());

            //gpResource.logger.info("statusUser(): queuedTasks=" + gpResource.getqueuedTask() + " activeTasks=" + gpResource.getactiveTask() + " completedStacks=" + gpResource.getNumWorkerResults() + " allStacks=" + gpResource.getNumUserResults() + " %completed=" + gpResource.getNumWorkerResults()*100.0/gpResource.getNumUserResults() + " GPWS_queuedTasks=" + GPResourceHome.commonState.getAvr_Q_Length() + " GPWS_activeTasks=" + GPResourceHome.commonState.getNum_Active_Tasks() + " GPWS_activeResources=" + GPResourceHome.commonState.numWorkers());


            //return sur;

        }
        catch (Exception e)
        {
            sur = new StatusUserResponse();
            sur.setValid(false);


            //return sur;
        }


        if (GPResourceHome.perfProfile.PROFILE)
        {
            ct.stop();
            GPResourceHome.perfProfile.addSample("statusUser", ct.getElapsedTimeMX());
            ct = null;
        }

        return sur;


    }

    public MonitorConfigResponse monitorConfig(MonitorConfig s) throws RemoteException {

        StopWatch ct = null;
        if (GPResourceHome.perfProfile.PROFILE)
        {

            ct = new StopWatch(false, false, true);
            ct.start();
        }

        MonitorConfigResponse sr = null;
        GPResource gpResource = getResource();
        GPResourceHome gpResourceHome = getResourceHome();


        //int numWorkers = 0;

        try
        {

            if (GPResourceHome.commonState == null)
            {
                GPResourceHome.load_common();
                //throw new RemoteException("workerDeRegistration(): commonState == null ");

            }
            //else
            //numWorkers = GPResourceHome.commonState.numWorkers();
            //gpResource.logger.debug("Available workers: " + numWorkers);




            sr = new MonitorConfigResponse();
            sr.setMaxBundling(gpResource.MAX_WORK_SIZE);
            sr.setPiggyBacking(gpResource.enablePiggyBacking);
            sr.setPreFetching(gpResource.preFetching);
            sr.setDrpAllocation(gpResource.drpAllocation);
            sr.setDrpMinResc(gpResource.drpMinResc);
            sr.setDrpMaxResc(gpResource.drpMaxResc);
            sr.setDrpMinTime(gpResource.drpMinTime);
            sr.setDrpMaxTime(gpResource.drpMaxTime);
            sr.setDrpIdle(gpResource.drpIdle);
            //if (gpResource.DATA_AWARE_SCHEDULER) {
            sr.setScheduler("Data-Aware");
            //} else
            //    sr.setScheduler("Round-Robin");


            //sr.setDataCaching(gpResource.CACHE_GRID_GLOBAL);
            sr.setDataCaching(true);


            sr.setValid(true);

            //return sr;

        }
        catch (Exception e)
        {
            e.printStackTrace();

            sr = new MonitorConfigResponse();
            sr.setValid(false);


            //return sr;
        }


        if (GPResourceHome.perfProfile.PROFILE)
        {
            ct.stop();
            GPResourceHome.perfProfile.addSample("monitorConfig", ct.getElapsedTimeMX());
            ct = null;
        }

        return sr;

    }


    //user job submittion 
    public MonitorStateResponse monitorState(MonitorState s) throws RemoteException 
    {

        StopWatch ct = null;
        if (GPResourceHome.perfProfile.PROFILE)
        {

            ct = new StopWatch(false, false, true);
            ct.start();
        }

        MonitorStateResponse sr = null;

        try
        {

            GPResource gpResource = getResource();

            sr = gpResource.getMonitorState(s);
        }
        catch (Exception e)
        {
            sr = new MonitorStateResponse();

            sr.setValid(false);

        }

        if (GPResourceHome.perfProfile.PROFILE)
        {
            ct.stop();
            GPResourceHome.perfProfile.addSample("monitorState", ct.getElapsedTimeMX());
            ct = null;
        }
        return sr;

    }




    public MonitorWorkerStateResponse monitorWorkerState(MonitorWorkerState s) throws RemoteException {

        StopWatch ct = null;
        if (GPResourceHome.perfProfile.PROFILE)
        {

            ct = new StopWatch(false, false, true);
            ct.start();
        }

        MonitorWorkerStateResponse sr = null;

        GPResource gpResource = getResource();
        GPResourceHome gpResourceHome = getResourceHome();


        //int numWorkers = 0;

        try
        {

            if (GPResourceHome.commonState == null)
            {
                GPResourceHome.load_common();
                //throw new RemoteException("workerDeRegistration(): commonState == null ");

            }
            //else
            //numWorkers = GPResourceHome.commonState.numWorkers();
            //gpResource.logger.debug("Available workers: " + numWorkers);


            sr = new MonitorWorkerStateResponse();

            /*
            <xsd:element name="MonitorWorkerStateResponse">
                <xsd:complexType>
                    <xsd:sequence>
                        <xsd:element name="freeWorkers" type="xsd:string"
                         minOccurs="0" maxOccurs="unbounded"/>
                        <xsd:element name="freeWorkerNum" type="xsd:int"/>
                        <xsd:element name="pendWorkers" type="xsd:string"
                         minOccurs="0" maxOccurs="unbounded"/>
                        <xsd:element name="pendWorkerNum" type="xsd:int"/>
                        <xsd:element name="busyWorkers" type="xsd:string"
                         minOccurs="0" maxOccurs="unbounded"/>
                        <xsd:element name="busyWorkerNum" type="xsd:int"/>
                    </xsd:sequence>
                </xsd:complexType>
            </xsd:element>
            */


            sr.setFreeWorkers(GPResourceHome.commonState.freeWorkers.toArray());
            sr.setPendWorkers(GPResourceHome.commonState.pendWorkers.toArray());
            sr.setBusyWorkers(GPResourceHome.commonState.busyWorkers.toArray());

            if (sr.getFreeWorkers() != null)
            {
                sr.setFreeWorkerNum((sr.getFreeWorkers()).length);
            }
            else
                sr.setFreeWorkerNum(0);

            if (sr.getPendWorkers() != null)
            {
                sr.setPendWorkerNum((sr.getPendWorkers()).length);
            }
            else
                sr.setPendWorkerNum(0);

            if (sr.getBusyWorkers() != null)
            {
                sr.setBusyWorkerNum((sr.getBusyWorkers()).length);
            }
            else
                sr.setBusyWorkerNum(0);





            //return sr;

        }
        catch (Exception e)
        {
            e.printStackTrace();

            sr = new MonitorWorkerStateResponse();
            sr.setFreeWorkerNum(0);
            sr.setPendWorkerNum(0);
            sr.setBusyWorkerNum(0);


            //return sr;
        }


        if (GPResourceHome.perfProfile.PROFILE)
        {
            ct.stop();
            GPResourceHome.perfProfile.addSample("monitorWorkerState", ct.getElapsedTimeMX());
            ct = null;
        }

        return sr;


    }


    public MonitorTaskStateResponse monitorTaskState(MonitorTaskState s) throws RemoteException 
    {

        StopWatch ct = null;
        if (GPResourceHome.perfProfile.PROFILE)
        {

            ct = new StopWatch(false, false, true);
            ct.start();
        }

        MonitorTaskStateResponse sr = null;

        GPResource gpResource = getResource();
        GPResourceHome gpResourceHome = getResourceHome();


        //int numWorkers = 0;

        try
        {

            if (GPResourceHome.commonState == null)
            {
                GPResourceHome.load_common();
                //throw new RemoteException("workerDeRegistration(): commonState == null ");

            }
            //else
            //numWorkers = GPResourceHome.commonState.numWorkers();
            //gpResource.logger.debug("Available workers: " + numWorkers);


            sr = new MonitorTaskStateResponse();

            /*
            <xsd:element name="MonitorTaskStateResponse">
                <xsd:complexType>
                    <xsd:sequence>
                        <xsd:element name="queuedTasks" type="xsd:string"
                         minOccurs="0" maxOccurs="unbounded"/>
                        <xsd:element name="queuedTaskNum" type="xsd:int"/>
                        <xsd:element name="activeTasks" type="xsd:string"
                         minOccurs="0" maxOccurs="unbounded"/>
                        <xsd:element name="activeTaskNum" type="xsd:int"/>
                        <xsd:element name="doneTasks" type="xsd:string"
                         minOccurs="0" maxOccurs="unbounded"/>
                        <xsd:element name="doneTaskNum" type="xsd:int"/>
                    </xsd:sequence>
                </xsd:complexType>
            </xsd:element>
            */


            //the queued tasks are possibly in many different resources... how to aggregate them all here?
            sr.setQueuedTasks(null);
            //
            // For both the keys and values of a map
            synchronized(GPResourceHome.commonState.activeTasksMap)
            {
                int numActiveTasks = GPResourceHome.commonState.activeTasksMap.size();
                //String activeTasks[] = new String[numActiveTasks];
                List activeTasks = new LinkedList();
                if (numActiveTasks > 0)
                {
                    for (Iterator it=GPResourceHome.commonState.activeTasksMap.entrySet().iterator(); it.hasNext(); )
                    {
                        Map.Entry entry = (Map.Entry)it.next();
                        String key = (String)entry.getKey();
                        ActiveTasks value = (ActiveTasks)entry.getValue();

                        for (int j=0;j<value.oldTasks.length;j++)
                        {
                            activeTasks.add(((value.oldTasks[j]).getExecutable()).getId() + " ==> " + value.workerID);

                        }


                    }

                    //sr.setActiveTaskNum(numActiveTasks);
                    Object[] objectArray = activeTasks.toArray();

                    sr.setActiveTasks((String[])activeTasks.toArray(new String[activeTasks.size()]));

                }
                else
                {
                    //sr.setActiveTaskNum(0);
                }

            }


            //need to implement
            sr.setDoneTasks(null);

            if (sr.getQueuedTasks() != null)
            {
                sr.setQueuedTaskNum((sr.getQueuedTasks()).length);
            }
            else
                sr.setQueuedTaskNum(0);

            if (sr.getActiveTasks() != null)
            {
                sr.setActiveTaskNum((sr.getActiveTasks()).length);
            }
            else
                sr.setActiveTaskNum(0);


            if (sr.getDoneTasks() != null)
            {
                sr.setDoneTaskNum((sr.getDoneTasks()).length);
            }
            else
                sr.setDoneTaskNum(0);




            //return sr;

        }
        catch (Exception e)
        {
            e.printStackTrace();

            sr = new MonitorTaskStateResponse();
            sr.setQueuedTaskNum(0);
            sr.setActiveTaskNum(0);
            sr.setDoneTaskNum(0);


            //return sr;
        }


        if (GPResourceHome.perfProfile.PROFILE)
        {
            ct.stop();
            GPResourceHome.perfProfile.addSample("monitorTaskState", ct.getElapsedTimeMX());
            ct = null;
        }

        return sr;


    }



}
