//SVN version latest
/*
 * This file is licensed under the terms of the Globus Toolkit Public License
 * v3, found at http://www.globus.org/toolkit/legal/4.0/license-v3.html.
 * 
 * This notice must appear in redistributions of this file, with or without
 * modification.
 */
package org.globus.GenericPortal.services.core.WS.impl;

import java.rmi.RemoteException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.lang.System;

import org.apache.axis.components.uuid.UUIDGen;
import org.apache.axis.components.uuid.UUIDGenFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.wsrf.InvalidResourceKeyException;
import org.globus.wsrf.NoSuchResourceException;
import org.globus.wsrf.PersistentResource;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.ResourceLifetime;
import org.globus.wsrf.ResourceProperties;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.impl.ReflectionResourceProperty;
import org.globus.wsrf.impl.SimpleResourcePropertyMetaData;
import org.globus.wsrf.impl.SimpleResourcePropertySet;
import org.globus.wsrf.utils.FilePersistenceHelper;
import org.globus.wsrf.impl.SimpleResourceKey;


import java.io.Serializable;
import java.text.StringCharacterIterator;
import java.util.*;
import java.io.*;


import org.globus.GenericPortal.common.*;
import org.globus.GenericPortal.stubs.GPService_instance.*;


//import org.globus.wsrf.jndi.*;

//import javax.xml.rpc.server.*;



//public class GPResource implements PersistentResource, ResourceProperties, ResourceLifetime, Serializable 
public class GPResource implements PersistentResource, ResourceProperties, ResourceLifetime/*, ServiceLifecycle, Initializable */
{

    /*
    public void init(Object o)
    {
        System.out.println("started falkon: init");

    }


    public void destroy()
    {
        System.out.println("stopped falkon: destroy");

    }   
    */

    private int deregisteredWorkers = 0;
    private int newWorkers = 0;

    public synchronized int getDeregisteredWorkers()
    {
        int val = deregisteredWorkers;
        deregisteredWorkers = 0;
        return val;

    }

    public synchronized int getNewWorkers()
    {

        int val = newWorkers;
        newWorkers = 0;
        return val;
    }

    public synchronized void setNewWorkers()
    {
        newWorkers++;
    }
    public synchronized void setDeregisteredWorkers()
    {
        deregisteredWorkers++;
    }


    static final Task NO_MORE_TASK = new Task();

    public ResourceKey resourceKey = null;
    public String userID = null;

    public static boolean DIPERF = false;

    public static boolean CACHE_GRID_GLOBAL = false;
    public static boolean LOAD_BALANCER = false;
    public static int CACHE_WINDOW_SIZE = 0;
    public static int MAX_CACHE_WINDOW_SIZE = 100;
    public static boolean CACHE_WINDOW_SIZE_FOLLOWS_REGISTERED_EXECUTORS = false;
    public static int CACHE_WINDOW_SIZE_MULTIPLIER = 1;
    public static boolean DATA_AWARE_SCHEDULER = false;
    public static boolean MAX_COMPUTE_UTIL = false;
    public static boolean MAX_CACHE_HIT = false;
    public static double CPU_UTILIZATION_THRESHOLD = 0.9;

    public StopWatch jobTime = new StopWatch();

    /* Added for logging */
    static final Log logger = LogFactory.getLog(GPService.class);

    /* Resource Property set */
    private ResourcePropertySet propSet; //this is not serializable
    //private static ResourcePropertySet propSet;

    /* Resource key. This uniquely identifies this resource. */
    private Object key;

    /* UUID generator to generate unique resource key */
    private static final UUIDGen uuidGen = UUIDGenFactory.getUUIDGen();

    /* Persistence helper class */
    private FilePersistenceHelper persistenceHelper; //this is not serializable
    //private static FilePersistenceHelper persistenceHelper;

    /* Resource properties */
    private int numJobs;

    private String state;

    private int numWorkers;

    private int numUserResults; //used to store the initial # of execs
    private int numWorkerResults; //used to store the # of results finished for a particular job

    public int activeTask;
    public int queuedTask;

    public WorkQueue execQ;
    public WorkQueue taskQ;
    public WorkQueue resultQ;
    //public WorkQueue execXQ;
    //public WorkQueue taskXQ;
    public WorkQueue taskPendQ;


    private boolean isWorker;

    //public static GPResourceCommon commonState;

    //public int numTasksCompleted;
    //public int numTuplesNotFound;
    //public int numTasksFailed;
    //public int numImagesStacked;

    private Calendar terminationTime;

    public int MIN_WORK_SIZE = 1;
    public int MAX_WORK_SIZE = 1;

    //public boolean resultsRetrieved = false;

    //public static String localScratchDisk = "/scratch/local/iraicu/sdss.scratch/";  //this should really be set from a config file

    //public boolean busy = false;

    public Random rand;

    //public boolean firstTime = true;
    //public boolean lastTime = false;

    public boolean shutingNEDown = false;
    public boolean shutingT2TDown = false;


    public int MAX_EXECS_PER_CALL = 1;

    public boolean NOT_PER_TASK = true;

    public boolean enablePiggyBacking = true;
    //piggy backing should not be used with TCPCore
    public boolean useTCPCore = false;

    //public int retrievedTasks = 0;
    //public int initialNumTasks = 0;

    //static final Executable NO_MORE_EXECUTABLES = new Executable();


    /*
    public synchronized boolean isBusy()
    {
        return busy;
    }

    public synchronized void setBusy(boolean b)
    {
        busy = b;
    }     */

    //private GPResource gpResourceWorker; //need to be made persistent ... curently does not work
    //Thread t2t = null;
    Thread ne = null;

    Map taskPerf = null;//Collections.synchronizedMap(new HashMap<String, TaskPerformance>());


    public String drpAllocation = new String("disabled");
    public int drpMinResc = 0;
    public int drpMaxResc = 0;
    public int drpMinTime = 0;
    public int drpMaxTime = 0;
    public int drpIdle = 0;

    public boolean preFetching = false;

    public int resourceAllocated = 0;

    //public long timeStart = 0;
    //public long timeEnd = 0;



    public void initState() throws Exception
    {

        logger.trace("GPResource: initState()");
        if (this.execQ == null) this.execQ = new WorkQueue();
        if (this.taskQ == null) this.taskQ = new WorkQueue();
        if (this.resultQ == null) this.resultQ = new WorkQueue();
        if (this.taskPendQ == null) this.taskPendQ = new WorkQueue();


        if (taskPerf == null) this.taskPerf = Collections.synchronizedMap(new HashMap());


        //this.execXQ = new WorkQueue();
        //this.taskXQ = new WorkQueue();

        this.numJobs = 0;
        this.state = "NOT INITIALIZED";
        this.numWorkers = 0;
        this.numUserResults = 0;
        this.numWorkerResults = 0;
        //this.numImagesStacked = 0;

        this.activeTask=0;
        this.queuedTask=0;

        //this.finalResult = null;
        this.rand = new Random();
        store();



        //this.resultsRetrieved = false;

        this.jobTime.reset();
        this.jobTime.start();







        setNumUserResults(0);
        setNumWorkerResults(0);
        setqueuedTask(0);

        String temp = (String)GPResourceHome.falkonConfig.get("enablePiggyBacking");
        if (temp != null)
        {
            if (temp.contentEquals(new StringBuffer("true")))
            {
                this.enablePiggyBacking = true;
            }
            else
                this.enablePiggyBacking = false;
        }
        else
            this.enablePiggyBacking = false;


        temp = (String)GPResourceHome.falkonConfig.get("LOAD_BALANCER");
        if (temp != null)
        {
            if (temp.contentEquals(new StringBuffer("true")))
            {
                this.LOAD_BALANCER = true;
                //must also start notification engine... and perhaps do other stuff... 
                //TODO
            }
            else
                this.LOAD_BALANCER = false;

            //this.CACHE_GRID_GLOBAL = Boolean.parseBoolean(temp);
        }
        else
            this.CACHE_GRID_GLOBAL = false;


        temp = (String)GPResourceHome.falkonConfig.get("CACHE_GRID_GLOBAL");
        if (temp != null)
        {
            if (temp.contentEquals(new StringBuffer("true")))
            {
                this.CACHE_GRID_GLOBAL = true;
            }
            else
                this.CACHE_GRID_GLOBAL = false;

            //this.CACHE_GRID_GLOBAL = Boolean.parseBoolean(temp);
        }
        else
            this.CACHE_GRID_GLOBAL = false;


        temp = (String)GPResourceHome.falkonConfig.get("DATA_AWARE_SCHEDULER");
        if (temp != null)
        {
            //this.DATA_AWARE_SCHEDULER = Boolean.parseBoolean(temp);

            if (temp.contentEquals(new StringBuffer("true")))
            {
                this.DATA_AWARE_SCHEDULER = true;
                this.MAX_COMPUTE_UTIL = true;
            }
            else
                this.DATA_AWARE_SCHEDULER = false;
        }
        else
            this.DATA_AWARE_SCHEDULER = false;

        temp = (String)GPResourceHome.falkonConfig.get("MAX_COMPUTE_UTIL");
        if (temp != null)
        {
            //this.DATA_AWARE_SCHEDULER = Boolean.parseBoolean(temp);

            if (temp.contentEquals(new StringBuffer("true")))
            {
                this.MAX_COMPUTE_UTIL = true;
                this.MAX_CACHE_HIT = false;
            }
            else
                this.MAX_COMPUTE_UTIL = false;
        }
        else
            this.MAX_COMPUTE_UTIL = false;

        temp = (String)GPResourceHome.falkonConfig.get("MAX_CACHE_HIT");
        if (temp != null)
        {
            //this.DATA_AWARE_SCHEDULER = Boolean.parseBoolean(temp);

            if (temp.contentEquals(new StringBuffer("true")))
            {
                this.MAX_CACHE_HIT = true;
                this.MAX_COMPUTE_UTIL = false;
            }
            else
                this.MAX_CACHE_HIT = false;
        }
        else
            this.MAX_CACHE_HIT = false;

        temp = (String)GPResourceHome.falkonConfig.get("CPU_UTILIZATION_THRESHOLD");
        if (temp != null)
        {
            //this.DATA_AWARE_SCHEDULER = Boolean.parseBoolean(temp);

            this.CPU_UTILIZATION_THRESHOLD = Double.parseDouble(temp);
        }
        else
            this.CPU_UTILIZATION_THRESHOLD = 0.9;



        if (this.CACHE_WINDOW_SIZE == 0)
        {

            temp = (String)GPResourceHome.falkonConfig.get("MAX_CACHE_WINDOW_SIZE");
            if (temp != null)
            {
                this.CACHE_WINDOW_SIZE = Integer.parseInt(temp);

                if (this.CACHE_WINDOW_SIZE <= 0)
                {
                    this.CACHE_WINDOW_SIZE = 1;
                }
            }
            else
                this.CACHE_WINDOW_SIZE = 1;


            temp = (String)GPResourceHome.falkonConfig.get("CACHE_WINDOW_SIZE_FOLLOWS_REGISTERED_EXECUTORS");
            if (temp != null)
            {
                if (temp.contentEquals(new StringBuffer("true")))
                {
                    this.CACHE_WINDOW_SIZE_FOLLOWS_REGISTERED_EXECUTORS = true;
                }
                else
                    this.CACHE_WINDOW_SIZE_FOLLOWS_REGISTERED_EXECUTORS = false;
            }
            else
                this.CACHE_WINDOW_SIZE_FOLLOWS_REGISTERED_EXECUTORS = false;



            temp = (String)GPResourceHome.falkonConfig.get("CACHE_WINDOW_SIZE_MULTIPLIER");
            if (temp != null)
            {
                this.CACHE_WINDOW_SIZE_MULTIPLIER = Integer.parseInt(temp);

                if (this.CACHE_WINDOW_SIZE_MULTIPLIER <= 0)
                {
                    this.CACHE_WINDOW_SIZE_MULTIPLIER = 1;
                }
            }
            else
                this.CACHE_WINDOW_SIZE_MULTIPLIER = 1;
        }



        temp = (String)GPResourceHome.falkonConfig.get("useTCPCore");
        if (temp != null)
        {
            //this.useTCPCore = Boolean.parseBoolean(temp);
            if (temp.contentEquals(new StringBuffer("true")))
            {
                this.useTCPCore = true;
            }
            else
                this.useTCPCore = false;
        }
        else
            this.useTCPCore = false;



        temp =  (String)GPResourceHome.falkonConfig.get("MAX_EXECS_PER_CALL");
        if (temp != null)
        {
            this.MAX_EXECS_PER_CALL = Math.max(Integer.parseInt(temp), 1);
        }
        else
            this.MAX_EXECS_PER_CALL = 1;


        temp =  (String)GPResourceHome.falkonConfig.get("MIN_WORK_SIZE");
        if (temp != null)
        {
            this.MIN_WORK_SIZE = Math.max(Integer.parseInt(temp), 1);
        }
        else
            this.MIN_WORK_SIZE = 1;


        temp =  (String)GPResourceHome.falkonConfig.get("MAX_WORK_SIZE");
        if (temp != null)
        {
            this.MAX_WORK_SIZE = Math.max(Integer.parseInt(temp), 1);
        }
        else
            this.MAX_WORK_SIZE = 1;


        GPResourceHome.commonState.resourceQ.insert(this);

    }

    /* Initializes resource and returns a unique identifier for this resource */
    public Object initialize() throws Exception {

        logger.trace("GPResource: initialize()");
        String key = uuidGen.nextUUID();
        initialize(key);
        store();
        return key;
    }

    /* Initializes resource and returns a unique identifier for this resource */
    public Object initialize(GPResourceCommon common) throws Exception {

        logger.trace("GPResource: initialize()");
        String key = uuidGen.nextUUID();
        initialize(key, common);
        store();
        return key;
    }


    /* Initializes resource with a given key */
    public void initialize(Object key, GPResourceCommon common) throws Exception {
        logger.trace("GPResource: initialize(key)");
        this.key = key;
        this.propSet = new SimpleResourcePropertySet(
                                                    GPConstants.RESOURCE_PROPERTIES);

        ResourceProperty numJobsRP = new ReflectionResourceProperty(
                                                                   GPConstants.RP_NUMJOBS, "NumJobs", this);
        this.propSet.add(numJobsRP);
        this.numJobs = 0;

        ResourceProperty stateRP = new ReflectionResourceProperty(
                                                                 GPConstants.RP_STATE, "State", this);
        this.propSet.add(stateRP);
        this.state = "NOT INITIALIZED";

        ResourceProperty numWorkersRP = new ReflectionResourceProperty(
                                                                      GPConstants.RP_NUMWORKERS, "NumWorkers", this);
        this.propSet.add(numWorkersRP);
        this.numWorkers = 0;

        ResourceProperty numUserResultsRP = new ReflectionResourceProperty(
                                                                          GPConstants.RP_NUMUSERRESULTS, "NumUserResults", this);
        this.propSet.add(numUserResultsRP);
        this.numUserResults = 0;

        ResourceProperty numWorkerResultsRP = new ReflectionResourceProperty(
                                                                            GPConstants.RP_NUMWORKERRESULTS, "NumWorkerResults", this);
        this.propSet.add(numWorkerResultsRP);
        this.numWorkerResults = 0;


        ResourceProperty termTimeRP = new ReflectionResourceProperty(
                                                                    SimpleResourcePropertyMetaData.TERMINATION_TIME, this);
        this.propSet.add(termTimeRP);

        ResourceProperty currTimeRP = new ReflectionResourceProperty(
                                                                    SimpleResourcePropertyMetaData.CURRENT_TIME, this);
        this.propSet.add(currTimeRP);

        //this.numTasksCompleted = 0;
        //this.numTasksFailed = 0;
        //this.numTuplesNotFound = 0;
        //this.gpResourceWorker = null;
        //this.finalResult = null;
        //this.setCommonState(commonState);

        //t2t = new Tuple2Task(this);
        //t2t.start();


    }


    /* Initializes resource with a given key */
    public void initialize(Object key) throws Exception {
        logger.trace("GPResource: initialize(key)");
        this.key = key;
        this.propSet = new SimpleResourcePropertySet(
                                                    GPConstants.RESOURCE_PROPERTIES);

        ResourceProperty numJobsRP = new ReflectionResourceProperty(
                                                                   GPConstants.RP_NUMJOBS, "NumJobs", this);
        this.propSet.add(numJobsRP);
        this.numJobs = 0;

        ResourceProperty stateRP = new ReflectionResourceProperty(
                                                                 GPConstants.RP_STATE, "State", this);
        this.propSet.add(stateRP);
        this.state = "NOT INITIALIZED";

        ResourceProperty numWorkersRP = new ReflectionResourceProperty(
                                                                      GPConstants.RP_NUMWORKERS, "NumWorkers", this);
        this.propSet.add(numWorkersRP);
        this.numWorkers = 0;

        ResourceProperty numUserResultsRP = new ReflectionResourceProperty(
                                                                          GPConstants.RP_NUMUSERRESULTS, "NumUserResults", this);
        this.propSet.add(numUserResultsRP);
        this.numUserResults = 0;

        ResourceProperty numWorkerResultsRP = new ReflectionResourceProperty(
                                                                            GPConstants.RP_NUMWORKERRESULTS, "NumWorkerResults", this);
        this.propSet.add(numWorkerResultsRP);
        this.numWorkerResults = 0;


        ResourceProperty termTimeRP = new ReflectionResourceProperty(
                                                                    SimpleResourcePropertyMetaData.TERMINATION_TIME, this);
        this.propSet.add(termTimeRP);

        ResourceProperty currTimeRP = new ReflectionResourceProperty(
                                                                    SimpleResourcePropertyMetaData.CURRENT_TIME, this);
        this.propSet.add(currTimeRP);

        //this.numTasksCompleted = 0;
        //this.numTasksFailed = 0;
        //this.numTuplesNotFound = 0;
        //this.gpResourceWorker = null;
        //this.finalResult = null;

        //t2t = new Tuple2Task(this);
        //t2t.start();


    }

    public void deInitState() throws Exception
    {
        try
        {

            logger.trace("GPResource: deInitState() for key = " + String.valueOf(this.key) + "...");

            GPResourceHome.commonState.resourceQ.remove(this);

            //if (this.t2t != null)
            //{
            /*
            this.t2t.stop();
            this.t2t.destroy();
            this.t2t = null;
            */
            //}

            //if (this.ne != null)
            //{

            /*
            this.ne.stop();
            this.ne.destroy();
            this.ne = null;
            */
            //}

            this.shutingNEDown = true;
            //this.shutingT2TDown = true;

            //inserting this into the queue in order to have the waitUntilNotEmpty() break from the blocking wait()...
            this.taskQ.insert(NO_MORE_TASK);

            /*
            if (this.t2t != null && this.t2t.isAlive())
            {
              logger.debug("GPResource: deInitState(): waiting for Tuple2Task thread to finish...");
              this.t2t.join();
              logger.debug("GPResource: deInitState(): Tuple2Task thread finished!");
    
            }
            */
            if (this.ne != null && this.ne.isAlive())
            {
                logger.debug("GPResource: deInitState(): waiting for NotificationEngine thread to finish...");
                this.ne.join();
                logger.debug("GPResource: deInitState(): NotificationEngine thread finished!");
            }

            logger.debug("GPResource: deInitState(): reseting state...");

            this.execQ = null;
            this.taskQ = null;
            this.resultQ = null;
            this.taskPendQ = null;

            this.numJobs = 0;
            this.state = null;
            this.numWorkers = 0;
            this.numUserResults = 0;
            this.numWorkerResults = 0;
            this.activeTask=0;
            this.queuedTask=0;
            this.rand = null;
            this.DIPERF = false;
            this.CACHE_GRID_GLOBAL = false;
            this.DATA_AWARE_SCHEDULER = false;
            this.jobTime = null;
            //this.propSet = null;
            //this.key = null;
            //this.persistenceHelper = null;
            this.isWorker = false;
            //this.terminationTime = null;
            this.MIN_WORK_SIZE = 0;
            this.MAX_WORK_SIZE = 0;
            //this.resultsRetrieved = false;
            //this.busy = false;
            //this.firstTime = false;
            //this.lastTime = false;
            this.MAX_EXECS_PER_CALL = 0;
            //this.retrievedTasks = 0;
            //this.initialNumTasks = 0;

            logger.debug("GPResource: deInitState(): reseting state completed!");

            //Runtime.getRuntime().gc();


            logger.trace("GPResource: deInitState() for key = " + String.valueOf(this.key) + " finished!");
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());

        }

    }



    /*
    public void setCommonState(GPResourceCommon cs)
    {

        logger.trace("GPResource: setCommonState()");
        commonState = cs;

    }


    public GPResourceCommon getCommonState()
    {
        logger.trace("GPResource: getCommonState()");
        return commonState;

    }
    */



    /*
    public synchronized void setFinalResult(String s)
    {
        logger.trace("GPResource: setFinalResult()");
        this.finalResult = s;
    }

    public synchronized String getFinalResult()
    {

        logger.trace("GPResource: getFinalResult()");
        return this.finalResult;
    }
    */



    //public int activeTask;
    //public int queuedTask;

    public synchronized void setactiveTask(int i)
    //public void setactiveTask(int i)
    {
        logger.trace("GPResource: setactiveTask()");
        this.activeTask = i;
    }

    //public synchronized int getactiveTask()
    public int getactiveTask()
    {

        logger.trace("GPResource: getactiveTask()");
        return this.activeTask;
    }


    public synchronized void setqueuedTask(int i)
    //public void setqueuedTask(int i)
    {
        logger.trace("GPResource: setqueuedTask()");
        this.queuedTask = i;
    }

    //public synchronized int getqueuedTask()
    public int getqueuedTask()
    {

        logger.trace("GPResource: getqueuedTask()");
        return this.queuedTask;
    }
    /*
    public synchronized int getNumImagesStacked()
    {

        logger.trace("GPResource: getNumImagesStacked()");
        return numImagesStacked;
    }

    public synchronized void setNumImagesStacked(int n)
    {
        logger.trace("GPResource: setNumImagesStacked()");
        this.numImagesStacked = n;
        try
        {
            store();
        } catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }
    */

    /*
    public synchronized void numTasksCompletedInc()
    {
        this.numTasksCompleted++;
    }


    public int getNumTasksFailed() {
        return numTasksFailed;
    }

    public synchronized void setNumTasksFailed(int n) {
        this.numTasksFailed = n;
        try {
            store();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public synchronized void numTasksFailedInc()
    {
        this.numTasksFailed++;
    }



    public int getNumTuplesNotFound() {
        return numTuplesNotFound;
    }

    public synchronized void setNumTuplesNotFound(int n) {
        this.numTuplesNotFound = n;
        try {
            store();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public synchronized void numTuplesNotFoundInc()
    {
        this.numTuplesNotFound++;
    }
    */

    /* Get/Setters for the RPs */
    public int getNumJobs()
    {

        logger.trace("GPResource: getNumJobs()");
        return numJobs;
    }

    public synchronized void setNumJobs(int numJobs)
    //public void setNumJobs(int numJobs)
    {
        logger.trace("GPResource: setNumJobs()");
        this.numJobs = numJobs;
        /*try
        {
            store();
        } catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        } */
    }

    public int getNumWorkers()
    {

        logger.trace("GPResource: getNumWorkers()");
        return numWorkers;
    }

    public synchronized void setNumWorkers(int numWorkers)
    //public void setNumWorkers(int numWorkers)
    {

        logger.trace("GPResource: setNumWorkers()");
        this.numWorkers = numWorkers;
        /*
        try
        {
            store();
        } catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        } */
    }


    public int getNumUserResults()
    {

        logger.trace("GPResource: getNumUserResults()");
        return numUserResults;
    }

    public synchronized void setNumUserResults(int numUserResults)
    //public void setNumUserResults(int numUserResults)
    {

        logger.trace("GPResource: setNumUserResults()");
        this.numUserResults = numUserResults;
        /*try
        {
            store();
        } catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }  */
    }


    public int getNumWorkerResults()
    {
        logger.trace("GPResource: getNumWorkerResults()");
        return numWorkerResults;
    }

    public synchronized void setNumWorkerResults(int numWorkerResults)
    //public void setNumWorkerResults(int numWorkerResults)
    {

        logger.trace("GPResource: setNumWorkerResults()");
        this.numWorkerResults = numWorkerResults;
        /*try
        {
            store();
        } catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        } */
    }

    public synchronized void numWorkerResultsInc()
    {

        logger.trace("GPResource: numWorkerResultsInc()");
        this.numWorkerResults++;
        /*
        try
        {
            store();
        } catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        } */
    }


    /*
public synchronized void setApResourceWorker(GPResource gprw) 
    {
    this.gpResourceWorker = gprw;
    try {
        store();
    } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
    }
}


public synchronized GPResource getApResourceWorker() 
    {
    return gpResourceWorker;
}   */

    public String getState()
    {

        logger.trace("GPResource: getState()");
        return state;
    }

    public synchronized void setState(String state)
    //public void setState(String state)
    {
        logger.trace("GPResource: setState()");
        this.state = state;
        /*try
        {
            store();
        } catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        } */
    }

    /* Required by interface ResourceProperties */
    public ResourcePropertySet getResourcePropertySet()
    {
        logger.trace("GPResource: getResourcePropertySet()");
        return this.propSet;
    }

    /* Required by interface ResourceIdentifier */
    public Object getID()
    {
        logger.trace("GPResource: getID()");
        return this.key;
    }

    /*
    public ResourceKey getKey() {
        logger.trace("GPResource: getKey()");
        ResourceKey tKey = new SimpleResourceKey(this.getKeyTypeName(),
                this.getID());

        return tKey;
        } */


    /* Required by interface ResourceLifetime */
    public Calendar getCurrentTime()
    {
        logger.trace("GPResource: getCurrentTime()");
        return Calendar.getInstance();
    }

    /* Required by interface ResourceLifetime */
    public Calendar getTerminationTime()
    {
        //logger.trace("GPResource: getTerminationTime()");
        return this.terminationTime;
    }

    /* Required by interface ResourceLifetime */
    public synchronized void setTerminationTime(Calendar terminationTime)
    //public void setTerminationTime(Calendar terminationTime)
    {
        logger.trace("GPResource: setTerminationTime()");
        this.terminationTime = terminationTime;
        /*try
        {
            store();
        } catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        } */
    }

    /* Required by interface RemoveCallback */
    public void remove() throws ResourceException {

        logger.trace("GPResource: remove()");
        logger.debug("Resource " + this.getID() + " is going to be removed.");
        getPersistenceHelper().remove(this.key);
    }


    public void load(ResourceKey key) throws ResourceException 
    {
        logger.trace("GPResource: load()... dummy call, doesn't actually load anything :(");

    }


    public void loadOldGood(ResourceKey key) throws ResourceException 
    {

        StopWatch ct = new StopWatch();
        ct.start();

        logger.trace("GPResource: load()");

        /* Try to retrieve the persisted resource from disk */
        File file = getKeyAsFile(key.getValue());
        /*
         * If the file does not exist, no resource with that key was ever
         * persisted
         */
        if (!file.exists())
        {
            throw new NoSuchResourceException();
        }

        /*
         * We try to initialize the resource. This places default Values in the
         * RPs. We still have to load the Values of the RPs from disk
         */
        try
        {
            initialize(key.getValue());
        }
        catch (Exception e)
        {
            throw new ResourceException("Failed to initialize resource", e);
        }

        /* Now, we open the resource file and load the numJobss */
        logger.debug("Attempting to load resource " + key.getValue());

        /* We will use this to read from the file */
        FileInputStream fis = null;

        /* We will store the RPs in these variables */
        int numJobs;
        String state;
        int numWorkers;
        int numUserResults;
        int numWorkerResults;
        Calendar terminationTime;
        //GPResourceCommon commonState;

        try
        {
            /* Open the file */
            fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            /* Read the RPs */
            numJobs = ois.readInt();
            state = ois.readUTF();
            numWorkers = ois.readInt();
            numUserResults = ois.readInt();
            numWorkerResults = ois.readInt();

            terminationTime = (Calendar) ois.readObject();
            //commonState = (GPResourceCommon) ois.readObject(); 




            /* Assign the RPs to the resource class's attributes */
            this.numJobs = numJobs;
            this.state = state;
            this.numWorkers = numWorkers;
            this.numUserResults = numUserResults;
            this.numWorkerResults = numWorkerResults;

            //this.gpResourceWorker = gpResourceWorker;
            this.terminationTime = terminationTime;
            //this.commonState = commonState;



        }
        catch (Exception e)
        {
            throw new ResourceException("Failed to load resource", e);
        }
        finally
        {
            /* Make sure we clean up, whether the load succeeds or not */
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (Exception ee)
                {
                }
            }
        }


        ct.stop();
        if (DIPERF) logger.warn("GPResource:load(): " + ct.getElapsedTime() + " ms");
        ct.reset();
    }

    /* Required by interface PersistenceCallback */
    public void load2(ResourceKey key) throws ResourceException {

        logger.trace("GPResource: load()");
        /* Try to retrieve the persisted resource from disk */
        File file = getKeyAsFile(key.getValue());
        /*
         * If the file does not exist, no resource with that key was ever
         * persisted
         */
        if (!file.exists())
        {
            throw new NoSuchResourceException();
        }

        /*
         * We try to initialize the resource. This places default Values in the
         * RPs. We still have to load the Values of the RPs from disk
         */
        try
        {

            initialize(key.getValue());
        }
        catch (Exception e)
        {
            throw new ResourceException("Failed to initialize resource", e);
        }

        /* Now, we open the resource file and load the numJobss */
        logger.debug("Attempting to load resource " + key.getValue());

        /* We will use this to read from the file */
        FileInputStream fis = null;

        /* We will store the RPs in these variables */
        int numJobs;
        String state;
        int numWorkers;
        int numUserResults;
        int numWorkerResults;
        WorkQueue execQ;
        WorkQueue taskQ;
        WorkQueue resultQ;
        //WorkQueue execXQ;
        //WorkQueue taskXQ;
        //int numTasksFailed;
        //int numTasksCompleted;
        //int numTuplesNotFound;
        //int numImagesStacked;

        //GPResourceCommon commonState;
        //GPResource gpResourceWorker;
        //String finalResult;

        Calendar terminationTime;

        try
        {
            /* Open the file */
            fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            /* Read the RPs */
            numJobs = ois.readInt();
            state = ois.readUTF();
            numWorkers = ois.readInt();
            numUserResults = ois.readInt();
            numWorkerResults = ois.readInt();
            //gpResourceWorker = (GPResource) ois.readObject();

            terminationTime = (Calendar) ois.readObject();


            execQ = (WorkQueue) ois.readObject();
            taskQ = (WorkQueue) ois.readObject();
            resultQ = (WorkQueue) ois.readObject();
            //execXQ = (WorkQueue) ois.readObject();
            //taskXQ = (WorkQueue) ois.readObject();

            //numImagesStacked = ois.readInt();

            //commonState = (GPResourceCommon) ois.readObject(); 

            //finalResult = ois.readUTF();


            //numTasksFailed = ois.readInt();
            //numTasksCompleted = ois.readInt();
            //numTuplesNotFound = ois.readInt();
            //logger.info("Successfully loaded resource from file '" + file.toString() + "'");

            //if (gpResourceWorker == null) 
            //{
            //    logger.debug("Successfully loaded resource with NumJobs=" + numJobs
            //		+ ", State=" + state + ", GP Resource Worker = null");
            //}
            //else
            //    logger.debug("Successfully loaded resource with NumJobs=" + numJobs
            //		+ ", State=" + state + ", GP Resource Worker = " + gpResourceWorker.toString());


            /* Assign the RPs to the resource class's attributes */
            this.numJobs = numJobs;
            this.state = state;
            this.numWorkers = numWorkers;
            this.numUserResults = numUserResults;
            this.numWorkerResults = numWorkerResults;

            //this.gpResourceWorker = gpResourceWorker;
            this.terminationTime = terminationTime;
            this.execQ = execQ;
            this.taskQ = taskQ;
            this.resultQ = resultQ;
            //this.execXQ = execXQ;
            //this.taskXQ = taskXQ;
            //this.numImagesStacked = numImagesStacked;

            //this.commonState = commonState;

            //this.finalResult = finalResult;

            //this.numTasksFailed = numTasksFailed;
            //this.numTasksCompleted = numTasksCompleted;
            //this.numTuplesNotFound = numTuplesNotFound;


        }
        catch (Exception e)
        {
            throw new ResourceException("Failed to load resource", e);
        }
        finally
        {
            /* Make sure we clean up, whether the load succeeds or not */
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (Exception ee)
                {
                }
            }
        }

    }

    //public synchronized void store() throws ResourceException 
    public void store() throws ResourceException 
    {
        logger.trace("GPResource: store()... dummy call, doesn't actually store anything :(");

    }

    /* Required by interface PersistenceCallback */
    public synchronized void storeOldGood() throws ResourceException 
    {


        StopWatch ct = new StopWatch();
        ct.start();

        logger.trace("GPResource: store()");
        /* We will use these two variables to write the resource to disk */
        FileOutputStream fos = null;
        File tmpFile = null;

        logger.debug("Attempting to store resource " + this.getID());
        try
        {
            /* We start by creating a temporary file */
            tmpFile = File.createTempFile("GenericPortalResource", ".tmp",
                                          getPersistenceHelper().getStorageDirectory());
            /* We open the file for writing */
            fos = new FileOutputStream(tmpFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            /* We write the RPs in the file */
            oos.writeInt(this.numJobs);
            oos.writeUTF(this.state);
            oos.writeInt(this.numWorkers);
            oos.writeInt(this.numUserResults);
            oos.writeInt(this.numUserResults);
            oos.writeObject(this.terminationTime);


            // oos.writeObject(this.commonState);


            oos.flush();
        }
        catch (Exception e)
        {
            /* Delete the temporary file if something goes wrong */
            tmpFile.delete();
            throw new ResourceException("Failed to store resource", e);
        }
        finally
        {
            /* Clean up */
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (Exception ee)
                {
                }
            }
        }

        /*
         * We have successfully created a temporary file with our resource's
         * RPs. Now, if there is a previous copy of our resource on disk, we
         * first have to delete it. Next, we rename the temporary file to the
         * file representing our resource.
         */
        File file = getKeyAsFile(this.key);

        if (file.exists())
        {
            file.delete();
        }
        if (!tmpFile.renameTo(file))
        {
            tmpFile.delete();
            throw new ResourceException("Failed to store resource");
        }


        ct.stop();
        if (DIPERF) logger.warn("GPResource:load(): " + ct.getElapsedTime() + " ms");
        ct.reset();
        //logger.info("Successfully stored resource to file '" + file.toString() + "'");
    }

    /* Required by interface PersistenceCallback */
    public synchronized void store2() throws ResourceException {

        logger.trace("GPResource: store()");
        /* We will use these two variables to write the resource to disk */
        FileOutputStream fos = null;
        File tmpFile = null;

        logger.debug("Attempting to store resource " + this.getID());
        try
        {
            /* We start by creating a temporary file */
            tmpFile = File.createTempFile("GenericPortalResource", ".tmp",
                                          getPersistenceHelper().getStorageDirectory());
            /* We open the file for writing */
            fos = new FileOutputStream(tmpFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            /* We write the RPs in the file */
            oos.writeInt(this.numJobs);
            oos.writeUTF(this.state);
            oos.writeInt(this.numWorkers);
            oos.writeInt(this.numUserResults);
            oos.writeInt(this.numUserResults);
            //oos.writeObject(this.gpResourceWorker);
            oos.writeObject(this.terminationTime);

            oos.writeObject(this.execQ);
            oos.writeObject(this.taskQ);
            oos.writeObject(this.resultQ);
            //oos.writeObject(this.execXQ);
            //oos.writeObject(this.taskXQ);

            //oos.writeInt(this.numImagesStacked);

            //oos.writeObject(this.commonState);

            //oos.writeUTF(this.finalResult);

            //oos.writeInt(this.numTasksFailed);
            //oos.writeInt(this.numTasksCompleted);
            //oos.writeInt(this.numTuplesNotFound);

            oos.flush();
            //if (gpResourceWorker == null) 
            //{
            //    logger.debug("Successfully stored resource with NumJobs=" + numJobs
            //		+ ", State=" + state + ", GP Resource Worker = null");
            //}
            //else
            //    logger.debug("Successfully stored resource with NumJobs=" + numJobs
            //		+ ", State=" + state + ", GP Resource Worker = " + gpResourceWorker.toString());
        }
        catch (Exception e)
        {
            /* Delete the temporary file if something goes wrong */
            tmpFile.delete();
            throw new ResourceException("Failed to store resource", e);
        }
        finally
        {
            /* Clean up */
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (Exception ee)
                {
                }
            }
        }

        /*
         * We have successfully created a temporary file with our resource's
         * RPs. Now, if there is a previous copy of our resource on disk, we
         * first have to delete it. Next, we rename the temporary file to the
         * file representing our resource.
         */
        File file = getKeyAsFile(this.key);

        if (file.exists())
        {
            file.delete();
        }
        if (!tmpFile.renameTo(file))
        {
            tmpFile.delete();
            throw new ResourceException("Failed to store resource");
        }
        //logger.info("Successfully stored resource to file '" + file.toString() + "'");

    }

    /*
     * Given a key, this method returns a File object representing the persisted
     * resource.
     */
    private File getKeyAsFile(Object key) throws InvalidResourceKeyException {

        logger.trace("GPResource: getKeyAsFile()");
        /*
         * If the key is a String, we use the FilePersistenceHelper to retrieve
         * the resource
         */
        if (key instanceof String)
        {
            return getPersistenceHelper().getKeyAsFile(key);
            /* Otherwise, an exception is thrown */
        }
        else
        {
            throw new InvalidResourceKeyException();
        }
    }

    /* Returns this resource's FilePersistenceHelper object */
    protected synchronized FilePersistenceHelper getPersistenceHelper()
    {

        logger.trace("GPResource: getPersistenceHelper()");
        /* If the persistenceHelper has not been created, create it */
        if (this.persistenceHelper == null)
        {
            try
            {
                this.persistenceHelper = new FilePersistenceHelper(this
                                                                   .getClass(), ".data");
            }
            catch (Exception e)
            {
                throw new RuntimeException(e.getMessage());
            }
        }

        /* Return the persistenceHelper */
        return this.persistenceHelper;
    }

    /*
    private void writeObject(java.io.ObjectOutputStream out) throws IOException
    {
        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
    {
         in.defaultReadObject();
    }

    private void readObjectNoData() throws ObjectStreamException
    {
        //hopefully this will never hgppen :)
        logger.debug("Serialization error: readObjectNoData");


    }   */


    public WorkerWorkResponse dispatchWork(WorkerWork sourceWorker) //throws RemoteException 
    {

        StopWatch ct = null;
        if (GPResourceHome.perfProfile.PROFILE)
        {

            ct = new StopWatch(false, false, true);
            ct.start();
        }

        WorkerWorkResponse RP = null;

        try
        {

            GPResource gpResource = this;

            RP = gpResource.dispatchWork2(sourceWorker);
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



        if (GPResourceHome.perfProfile.PROFILE)
        {
            ct.stop();
            GPResourceHome.perfProfile.addSample("dispatch_work", ct.getElapsedTimeMX());
            ct = null;
        }


        return RP;
    }   


    //used to be in the service side...
    public WorkerWorkResponse dispatchWork2(WorkerWork sourceWorker)
    {
        StopWatch ct = new StopWatch();
        ct.start();
        try
        {





            String workerID = sourceWorker.getMachID();



            int workSize = 0;
            GPResource gpResource = this;
            WorkerWorkResponse RP = new WorkerWorkResponse();

            try
            {

                if (GPResourceHome.commonState == null)
                {
                    GPResourceHome.load_common();
                    //throw new RemoteException("deregisterWorker(): commonState == null ");

                }
            }
            catch (Exception eeeeee)
            {
                eeeeee.printStackTrace();

                //GPResourceHome.commonState.setFreeWorker(workerID);

                RP.setValid(false);
                return RP;

            }

            if (gpResource.shutingNEDown)
            {
                GPResourceHome.commonState.setFreeWorker(workerID);

                RP.setValid(false);
                return RP;
            }



            gpResource.logger.debug("Work Request received from " + workerID + "!");
            gpResource.logger.trace("GPService: workerWork()");

            //gpResource.setState("BUSY");
            gpResource.logger.debug("Sending work to " + workerID + "!");

            //synchronized(gpResource)
            //{

            //    gpResource.setNumUserResults(gpResource.getNumUserResults() - 1);
            //}

            //create the WorkerWorkResponse() with the actual work

            //Work 


            //return new WorkerWorkResponse((Work)gpResource.taskQ.remove());


            //GPResourceHome gpResourceHome = getResourceHome();

            int numWorkers = 0;

            try
            {

                //else
                numWorkers = GPResourceHome.commonState.numWorkers();
                gpResource.logger.debug("Available workers: " + numWorkers);
                GPResourceHome.commonState.setBusyWorker(workerID);

                gpResource.logger.debug("set executor " + workerID + " to busy!");
            }
            catch (Exception e)
            {
                e.printStackTrace();
                RP.setValid(false);

                GPResourceHome.commonState.setFreeWorker(workerID);
                return RP;
                //throw new RemoteException("get common state failed: " + e);
            }

            double ETA = gpResource.getNumUserResults()*1.0/numWorkers;



            Task[] tasks;
            try
            {
                int i;
                //synchronized(gpResource)
                //{

                //workSize = gpResource.getNumUserResults()/gpResource.getNumWorkers();
                if (numWorkers == 0)
                {
                    GPResourceHome.commonState.setFreeWorker(workerID);

                    gpResource.logger.debug("set worker " + workerID + " to free");

                    //e.printStackTrace();
                    RP.setValid(false);
                    return RP;


                    //throw new Exception("Number of registered workers is 0, work cannot be delegated when no workers are present...");
                }
                else
                {
                    /*
                    if (gpResource.getNumUserResults() <= 700)
                    {
                        workSize = (int) Math.max(Math.ceil(Math.pow(gpResource.getNumUserResults(), 0.3)), gpResource.MIN_WORK_SIZE);
                    } else if (gpResource.getNumUserResults() <= 10000)
                    {
                        workSize = (int) Math.min(Math.ceil(0.01*gpResource.getNumUserResults() + 0.1075), gpResource.MAX_WORK_SIZE);
                    } else
                    {
                        workSize = (int) Math.min(gpResource.getNumUserResults(), gpResource.MAX_WORK_SIZE);
                    }

                    */
                    //old setting, used to work ok
                    workSize = (int) Math.min(gpResource.getNumUserResults(), gpResource.MAX_WORK_SIZE);


                    //int local_MAX_WORK_SIZE = (int) Math.max(Math.ceil(gpResource.getNumUserResults()/100), 2);
                    //workSize = (int) Math.min(Math.min(Math.max(Math.ceil(gpResource.getNumUserResults()/GPResourceHome.commonState.roundRobin.size()), gpResource.MIN_WORK_SIZE), gpResource.MAX_WORK_SIZE), gpResource.taskQ.size());
                    //workSize = (int) Math.min(Math.min(Math.max(Math.ceil(gpResource.getNumUserResults()/numWorkers), gpResource.MIN_WORK_SIZE), local_MAX_WORK_SIZE), gpResource.taskQ.size());
                }


                if (gpResource.taskQ == null)
                {
                    GPResourceHome.commonState.setFreeWorker(workerID);

                    gpResource.logger.debug("set worker " + workerID + " to free");

                    //e.printStackTrace();
                    RP.setValid(false);
                    return RP;
                }
                gpResource.logger.debug("taskQ.size(): " + gpResource.taskQ.size());
                gpResource.logger.debug("taskPendQ.size(): " + gpResource.taskPendQ.size());

                workSize = (int) Math.max(Math.min(workSize, Math.ceil((gpResource.taskQ.size() + gpResource.taskPendQ.size())/GPResourceHome.commonState.roundRobin.size())), gpResource.MIN_WORK_SIZE);
                gpResource.logger.debug("workSize: " + workSize + " ... at line 742...");

                //if (workSize < 0)
                //    workSize = 0;


                //else if (workSize == 0)
                //{
                //    workSize = 1; //minimum size
                //} else if (workSize > 10) //send tasks of size 100 at most
                //{
                //    workSize = 10;
                //}


                //if (workSize > gpResource.taskQ.size())
                //{
                //    workSize = gpResource.taskQ.size();
                //}

                //gpResource.logger.debug("updated workSize: " + workSize);

                gpResource.logger.debug("workerWork(): checking gpResource.CACHE_GRID_GLOBAL...");
                if (gpResource.CACHE_GRID_GLOBAL && gpResource.DATA_AWARE_SCHEDULER)
                {
                    //this is not implemented yet...
                    gpResource.logger.debug("workerWork(): CACHE_GRID_GLOBAL enabled and DATA_AWARE_SCHEDULER enabled... invoking getTaskQ(workerID)");
                    //int ttIndex = -1;

                    List list = new LinkedList();

                    while ((gpResource.taskQ.size() + gpResource.taskPendQ.size()) > 0 && list.size()<workSize)
                    {
                        gpResource.logger.debug("getTaskQ(" + workerID + "): adding task to list... 0");
                        Task tTask = getTaskQ(workerID);
                        if (tTask != null)
                        {
                            list.add(tTask);
                            gpResource.logger.info("GPResource:dispatchWork2():getTaskQ(" + workerID + "): found 1 task that got some cache hit...");
                        }
                        else
                            break;

                    }



                    Task tTasks[] = null;
                    Task tTasksPend[] = null;
                    int tTasksLength = 0;
                    int tTasksPendLength = 0;

                    if (list.size() < this.MIN_WORK_SIZE)
                    {




                        if (list.size() < workSize)
                        {
                            //tTasks = new Task[workSize - list.size()];
                            tTasks = getTaskQ(workSize - list.size(), workerID);
                            if (tTasks != null)
                                gpResource.logger.info("GPResource:dispatchWork2():getTaskQ(" + tTasks.length + "," + workerID + "): found " + tTasks.length + " task(s) that did not have any cache hits...");

                        }



                        if (tTasks != null)
                        {
                            tTasksLength = tTasks.length;
                        }
                        if (list.size() + tTasksLength < workSize)
                        {
                            //tTasks = new Task[workSize - list.size()];
                            tTasksPend = getTaskPendQ(workSize - list.size() - tTasksLength, workerID);
                            if (tTasksPend != null)
                                gpResource.logger.info("GPResource:dispatchWork2():getTaskPendQ(" + tTasksPend.length + "," + workerID + "): found " + tTasksPend.length + " task(s) that did not have any cache hits...");

                        }



                        if (tTasksPend != null)
                        {
                            tTasksPendLength = tTasksPend.length;
                        }
                    }

                    //getTaskPendQ

                    if (tTasks == null && tTasksPend == null)
                    {
                        tasks = new Task[list.size()];
                    }
                    else if (tTasks == null && tTasksPend != null)
                    {
                        tasks = new Task[list.size() + tTasksPend.length];
                    }
                    else if (tTasks != null && tTasksPend == null)
                    {
                        tasks = new Task[list.size() + tTasks.length];
                    }
                    else
                    {

                        tasks = new Task[list.size() + tTasks.length + tTasksPend.length];
                    }


                    // For a set or list
                    int tti = 0;
                    for (Iterator it=list.iterator(); it.hasNext(); )
                    {
                        tasks[tti] = (Task)it.next();
                        tti++;
                    }



                    if (tTasks != null)
                    {

                        for (int ttii = tti;ttii<list.size() + tTasks.length; ttii++)
                        {
                            tasks[ttii] = tTasks[ttii - tti];
                        }
                    }
                    if (tTasksPend != null)
                    {

                        for (int ttii = tti + tTasksLength;ttii<list.size() + tTasksLength + tTasksPend.length; ttii++)
                        {
                            tasks[ttii] = tTasksPend[ttii - tti - tTasksLength];
                        }
                    }


                }
                else
                {
                    gpResource.logger.debug("workerWork(): CACHE_GRID_GLOBAL or DATA_AWARE_SCHEDULER disabled... invoking getTaskQ(workSize, workerID)");
                    tasks = getTaskQ(workSize, workerID);

                    //*****************************************


                    //if (!this.useTCPCore)
                    //{

                    //}
                    //*****************************************


                }

                GPResourceHome.commonState.cleanupActiveTasks(workerID);

                if (tasks != null)
                {

                    GPResourceHome.commonState.activeTasksMap.put(workerID, new ActiveTasks(gpResource, workerID, tasks));
                    gpResource.logger.debug("workerWork(): placed tasks into the activeTasks map...");


                    i = tasks.length;

                    for (int tid = 0;tid<tasks.length;tid++)
                    {

                        GPResourceHome.commonState.writeLogTask((tasks[tid].getExecutable()).getId(), "RUN_STATE_" + workerID);



                        gpResource.logger.debug("inserting task performance metrics in hashMap...");
                        TaskPerformance tPerf = (TaskPerformance)gpResource.taskPerf.get((tasks[tid].getExecutable()).getId());
                        if (tPerf == null)
                        {
                            gpResource.logger.debug("ERROR: TaskPerformance not found in HashMap for taskID " + (tasks[tid].getExecutable()).getId() + " although it should have been there... double check why not!");
                            //this should never happen
                            tPerf = new TaskPerformance();

                            tPerf.setTaskID((tasks[tid].getExecutable()).getId());
                            tPerf.setWaitQueueTime();
                        }
                        tPerf.setWorkerID(workerID);
                        tPerf.setWorkerQueueTime();

                        gpResource.taskPerf.put(tPerf.getTaskID(), tPerf);
                        gpResource.logger.debug("inserted task performance metrics in hashMap!");

                        GPResourceHome.commonState.setActiveTask();

                        GPResourceHome.commonState.writeLogUser();




                    }

                    gpResource.logger.debug("# of tasks ready to be sent to worker: " + i);
                    //}

                    if (i != workSize)
                    {
                        gpResource.logger.debug("The worksize was different than the # of tasks in the taskQ :(... this should not have hgppened");

                    }

                    if (i == 0)
                    {
                        gpResource.logger.debug("Setting work to invalid as there was no work to be sent: " + i);

                        GPResourceHome.commonState.setFreeWorker(workerID);
                        RP.setValid(false);
                        return RP;


                    }
                    else
                    {

                        gpResource.logger.debug("Setting work...");
                        RP.setTasks(tasks);
                        RP.setValid(true);
                        //RP.setHeight(gpResource.ROI_height);
                        //RP.setWidth(gpResource.ROI_width);
                    }
                }
                else
                {
                    gpResource.logger.debug("Setting work to invalid as tasks == null");

                    GPResourceHome.commonState.setFreeWorker(workerID);
                    RP.setValid(false);
                    return RP;
                }



                //RP = (WorkerWorkResponse)gpResource.taskQ.remove();
            }
            catch (Exception e)
            {
                GPResourceHome.commonState.setFreeWorker(workerID);

                gpResource.logger.debug("set worker " + workerID + " to free");
                e.printStackTrace();
                //throw new RemoteException("Error in retrieving work: ", e);
                e.printStackTrace();
                RP.setValid(false);
                return RP;

            }



            if (gpResource.CACHE_GRID_GLOBAL)
            {
                gpResource.logger.debug("Updating 3DcacheGrid Engine state...");


                //List imageFiles = new LinkedList();
                for (int numTasks = 0;numTasks<tasks.length;numTasks++)
                {
                    if ((tasks[numTasks].getExecutable()).isDataCaching())
                    {
                        DataFiles inputData = (tasks[numTasks].getExecutable()).getInputData();
                        //String inputDataFiles = inputData.getLogicalName();

                        //GPResourceHome.commonState.setCacheMisses(((tasks[i].getInputData()).getLogicalName()).size());


                        //if (tasks[numTasks].getNumInputDataFiles() > 0)
                        //{
                        String inputDataFiles[] = inputData.getLogicalName();
                        String inputDataFilesURL[] = inputData.getFileURL();
                        //the 2 arrays should be the same length!
                        DataCache inputDataCaches[] = new DataCache[inputDataFiles.length];
                        for (int numFiles = 0;numFiles<inputDataFiles.length;numFiles++)
                        {
                            inputDataCaches[numFiles] = new DataCache();

                            String dataCaches[] = null;

                            //this is a hack to avoid cache-to-cache transfers... this will essentially hide all cache locations if the data exists on some persistent storage
                            //to remove hack, simply remove the if condition below...
                            //if (numFiles >= inputDataFilesURL.length || (numFiles < inputDataFilesURL.length && inputDataFilesURL[numFiles] != null && !inputDataFilesURL[numFiles].startsWith("http://") && !inputDataFilesURL[numFiles].startsWith("/")))
                            //if ((inputDataFilesURL[numFiles] != null && !inputDataFilesURL[numFiles].startsWith("http://") && !inputDataFilesURL[numFiles].startsWith("/")))
                            //{
                            dataCaches = GPResourceHome.commonState.cacheGridIndex.getIndexElementLocalArray(inputDataFiles[numFiles]);
                            //}

                            if (dataCaches != null)
                            {

                                inputDataCaches[numFiles].setNumCaches(dataCaches.length);
                                inputDataCaches[numFiles].setCacheLocation(dataCaches);
                            }
                            else
                            {
                                inputDataCaches[numFiles].setNumCaches(0);
                                inputDataCaches[numFiles].setCacheLocation(new String[0]);
                            }


                            GPResourceHome.commonState.cacheGridIndex.insert(inputDataFiles[numFiles], inputDataFilesURL[numFiles], workerID);
                        }

                        gpResource.logger.debug("Setting the input data caches...");
                        inputData.setDataCache(inputDataCaches);
                        gpResource.logger.debug("Set the input data cache: inputDataCaches.length = " + inputDataCaches.length);


                        Executable exec = tasks[numTasks].getExecutable();
                        exec.setInputData(inputData);
                        tasks[numTasks].setExecutable(exec);



                        //}

                        //if (tasks[numTasks].getNumOutputDataFiles() > 0)
                        //{
                        String outputDataFiles[] = ((tasks[numTasks].getExecutable()).getOutputData()).getLogicalName();
                        String outputDataFilesURL[] = ((tasks[numTasks].getExecutable()).getOutputData()).getFileURL();
                        //the 2 arrays should be the same length!
                        for (int numFiles = 0;numFiles<outputDataFiles.length;numFiles++)
                        {
                            GPResourceHome.commonState.cacheGridIndex.insert(outputDataFiles[numFiles] , outputDataFilesURL[numFiles], workerID);

                        }
                        //}

                        //for (int numFiles = 0;numFiles<tasks.length;numFiles++)
                        //{


                        //    imageFiles.add(dataFiles[numFiles]);
                        //}

                    }

                }

                //String imageFilesArray[] = (String[])imageFiles.toArray();


                //synchronized(GPResourceHome.commonState.cacheGridGlobal)
                //{
                //    GPResourceHome.commonState.cacheGridGlobal.workerWorkStateUpdate(workerID, imageFilesArray);
                //}

                gpResource.logger.debug("3DcacheGrid Engine state updated!");
            }

            gpResource.logger.debug("Sending work back to worker...");


            gpResource.logger.debug("Work of size " + workSize + " submitted succeful!");



            synchronized(GPResourceHome.commonState)
            {
                GPResourceHome.commonState.setAvr_Q_Length(GPResourceHome.commonState.getAvr_Q_Length() - workSize);

                GPResourceHome.commonState.setNum_Active_Tasks(GPResourceHome.commonState.getNum_Active_Tasks() + workSize);
            }

            synchronized(gpResource)
            {
                gpResource.setactiveTask(gpResource.getactiveTask() + workSize);
                gpResource.setqueuedTask(gpResource.getqueuedTask() - workSize);
            }


            ct.stop();
            //if (DIPERF) gpResource.logger.warn("GPService:workerWork(): " + ct.getElapsedTime() + " ms");
            if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPService:workerWork(): " + ct.getElapsedTime() + " ms");


            if (!RP.isValid())
            {
                GPResourceHome.commonState.setFreeWorker(workerID);

            }
            else
            {
                gpResource.logger.info("Dispatched " + workSize + " tasks to " + workerID + " in " + ct.getElapsedTime() + " ms");

                //this needs to be fully implemented before it is enabled...
                boolean enforceWallTime = false;

                if (enforceWallTime)
                {


                    for (int numTasks = 0;numTasks<tasks.length;numTasks++)
                    {

                        long wallTime = tasks[numTasks].getExecutable().getWallTime();



                        if (wallTime > 0)
                        {
                            gpResource.logger.debug("task " + tasks[numTasks].getExecutable().getId() + " lifetime is " + wallTime + ", scheduled for clean-up...");
                            //GPResourceHome.commonState.activeWorkerCleanup.schedule(new WorkerDeregister(wID, gpResource, GPResourceHome.commonState), new Date(System.currentTimeMillis()+wallTime));
                            GPResourceHome.commonState.activeTaskCleanup.schedule(new TaskTerminate(), new Date(System.currentTimeMillis()+wallTime));
                        }
                        else
                        {
                            gpResource.logger.debug("task " + tasks[numTasks].getExecutable().getId() + " lifetime is infinite, nothing to schedule for cleaning up...");
                        }


                    }
                }
            }

            //more results to process.... insert a new notification into the queue...

            /*
            if (gpResource.taskQ != null && gpResource.taskQ.size() > 0)
            {
                GPResourceHome.commonState.notificationWorkerQ.insert(gpResource);
            } */



            return RP;
        }
        catch (Exception e)
        {
            GPResourceHome.commonState.setFreeWorker(sourceWorker.getMachID());

            e.printStackTrace();
            //System.out.println("set worker " + sourceWorker.getMachID() + " to free");
            //throw new RemoteException("Error in retrieving work: ", e);
            //e.printStackTrace();
            WorkerWorkResponse RPerror = new WorkerWorkResponse();
            RPerror.setValid(false);
            ct.stop();
            if (DIPERF) System.out.println(System.currentTimeMillis() + " : GPService:workerWork(): " + ct.getElapsedTime() + " ms");
            return RPerror;

        }
    }


    public WorkerResultResponse receiveResults(WorkerResult result) //throws RemoteException 
    {

        StopWatch ct = null;
        if (GPResourceHome.perfProfile.PROFILE)
        {

            ct = new StopWatch(false, false, true);
            ct.start();
        }
        WorkerResultResponse wrr = null;

        try
        {

            GPResource gpResource = this;//getResource();

            wrr = gpResource.receiveResults2(result);
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

        if (GPResourceHome.perfProfile.PROFILE)
        {
            ct.stop();
            GPResourceHome.perfProfile.addSample("receive_result", ct.getElapsedTimeMX());
            ct = null;
        }

        return wrr;
    } 



    public WorkerResultResponse receiveResults2(WorkerResult result) //throws RemoteException 
    {

        WorkerResultResponse wrr = new WorkerResultResponse();

        String workerID = null;

        StopWatch ct = new StopWatch();
        ct.start();

        boolean workerSuspended = false;


        try
        {




            workerID = result.getMachID();

            //Result result = wr.getResult();

            //GPResource gpResource = getResource();
            GPResource gpResource = this;
            //GPResourceHome gpResourceHome = getResourceHome();

            gpResource.logger.debug("receiveResults()...");


            gpResource.logger.debug("Receiving work results from " + workerID + ", processing...");
            gpResource.logger.trace("GPService: workerResult()");
            //gpResource.setState("IDLE");
            gpResource.logger.debug("Received results...");


            //add result to the list of results


            try
            {

                if (GPResourceHome.commonState == null)
                {
                    GPResourceHome.load_common();
                    //throw new RemoteException("deregisterWorker(): commonState == null ");

                }
                //else
                //numWorkers = GPResourceHome.commonState.numWorkers();

                //gpResource.logger.debug("Available workers: " + numWorkers);
            }
            catch (Exception e)
            {
                gpResource.logger.debug("get common state failed: " + e);
                //throw new RemoteException("get common state failed: " + e);
                wrr.setValid(false);
                wrr.setMoreWork(false);

                return wrr;
            }



            if (gpResource.CACHE_GRID_GLOBAL)
            {

                int numCacheEvicted = result.getNumCacheEvicted();
                if (numCacheEvicted > 0)
                {

                    gpResource.logger.debug("Updating 3DcacheGrid Engine state with " + numCacheEvicted + " entries");
                    String cacheEvicted[] = result.getCacheEvicted();


                    for (int numFiles = 0;numFiles<cacheEvicted.length;numFiles++)
                    {

                        gpResource.logger.debug("GPResourceHome.commonState.cacheGridIndex.remove(" + cacheEvicted[numFiles] + ", "+workerID +")...");
                        GPResourceHome.commonState.cacheGridIndex.remove(cacheEvicted[numFiles] , workerID);

                    }
                    gpResource.logger.info("******* evicted " + cacheEvicted.length + " files from "+workerID +"'s cache");


                    //synchronized(gpResourceHome.commonState.cacheGridGlobal)
                    //{
                    //    gpResourceHome.commonState.cacheGridGlobal.workerResultStateUpdate(workerID, cacheEvicted);
                    //}
                    gpResource.logger.debug("3DcacheGrid Engine state updated succesfully!");
                } //else
                //{
                //synchronized(gpResourceHome.commonState.cacheGridGlobal)
                //{
                //    gpResourceHome.commonState.cacheGridGlobal.workerResultStateUpdate(workerID, null);
                //}
                //gpResource.logger.debug("3DcacheGrid Engine state updated succesfully!");
                //}
            }


            Task[] tasks = null;
            int numTasks = result.getNumTasks();
            if (numTasks > 0)
            {


                tasks = result.getTasks();
                gpResource.logger.debug("received results for " + tasks.length + " tasks...");

            }
            else
                gpResource.logger.debug("received results for 0 tasks...");



            synchronized(gpResource)
            {

                gpResource.setNumWorkerResults(gpResource.getNumWorkerResults() + numTasks);

                //gpResource.setNumTasksFailed(gpResource.getNumTasksFailed() + result.getNumFailed());
                //gpResource.setNumImagesStacked(gpResource.getNumImagesStacked() + result.getNumImagesStacked() );
            }


            //*****************************************


            //if (!this.useTCPCore)
            //{

            ActiveTasks oldTasksMap = (ActiveTasks)GPResourceHome.commonState.activeTasksMap.remove(workerID);

            //Task oldTasks[] = (Task[])gpResource.activeTasksMap.remove(workerID);
            if (oldTasksMap != null)
            {

                Task oldTasks[] = oldTasksMap.oldTasks;
                if (oldTasks != null && tasks != null && oldTasks.length == tasks.length)
                {
                    gpResource.logger.debug("found right # of active tasks in map, removing...");
                }
                else
                {
                    gpResource.logger.debug("did not find active tasks in map, or the task lengths were not the same...");
                    if (oldTasks != null && tasks != null)
                    {
                        gpResource.logger.debug("oldTasks.length = " + oldTasks.length);
                        gpResource.logger.debug("tasks.length = " + tasks.length);
                    }

                }
            }
            else
            {
                gpResource.logger.debug("did not find any active tasks from worker " + workerID + ", should double check why we reached this part of the code with no active tasks...");
                gpResource.logger.debug("expected to find " + tasks.length + " active tasks...");
                for (int i=0;i<tasks.length;i++)
                {
                    gpResource.logger.debug("received task: " + tasks[i].getExecutable().getId() + " with an exit code of " + tasks[i].getExitCode());

                }

            }
            //}

            boolean taskToBeRetried = false;
            int numRetries = 0;
            //*****************************************

            if (numTasks > 0 && tasks != null)
            {

                for (int i=0;i<tasks.length;i++)
                {
                    //not needed in current mode of working where a notification is all that is needed
                    //might want to enable this again later, if we want the end user to retrieve the entire task
                    /*
                    if (gpResource.resultQ != null && tasks[i] != null)
                    {
                        gpResource.resultQ.insert(tasks[i]);
                    }
                    else
                    {
                        if (tasks[i] == null)
                        {
                            gpResource.logger.debug("tasks[i] == null..., no task to insert...");
                        }
                        else if (gpResource.resultQ == null)
                        {

                            gpResource.logger.debug("gpResource.resultQ == null..., nowhere to insert result for task " + tasks[i].getExecutable().getId() + " with an exit code of " + tasks[i].getExitCode());
                        }
                        else
                        {
                            gpResource.logger.debug("error in gpResource.resultQ.insert(tasks[i])");
                        }


                    }
                    */




                    GPResourceHome.commonState.setDoneTask();
                    if (tasks[i].getExitCode() == 0)
                    {
                        GPResourceHome.commonState.writeLogTask((tasks[i].getExecutable()).getId(), "SUCCESS_" + tasks[i].getExitCode());
                        GPResourceHome.commonState.setTasksSuccessDone();
                        //we probably need this to save us from a memory leak...
                        //GPResourceHome.commonState.erroredTasksStats.remove(tasks[i].getExecutable().getId());
                        taskToBeRetried = false;
                    }
                    //stale NFS handle
                    //else if (tasks[i].getExitCode() == -12345 || tasks[i].getExitCode() == -999 || tasks[i].getExitCode() == -5)
                    //else if (tasks[i].getExitCode() == -12345)
                    else
                    {
                        //if (tasks[i].getExitCode() == -12345)
                        //{
                        GPResourceHome.commonState.writeLogTask((tasks[i].getExecutable()).getId(), "FAILED_STALE_NFS_HANDLE_" + tasks[i].getExitCode());
                        //}
                        /*else if (tasks[i].getExitCode() == -999 || tasks[i].getExitCode() == -5)
                        {
                            GPResourceHome.commonState.writeLogTask((tasks[i].getExecutable()).getId(), "FAILED_DATA_CACHING_FAILED_" + tasks[i].getExitCode());
                        }
                        else
                        {
                            GPResourceHome.commonState.writeLogTask((tasks[i].getExecutable()).getId(), "FAILED_UNKOWN_" + tasks[i].getExitCode());

                        } */

                        if (GPResourceHome.commonState.maxNumErrorsPerExecutor > 0)
                        {

                        

                        Integer erroredTaskNum = (Integer) GPResourceHome.commonState.erroredTasksStats.get(tasks[i].getExecutable().getId());
                        if (erroredTaskNum == null)
                        {
                            erroredTaskNum = new Integer(0);
                        }
                        else
                            erroredTaskNum = new Integer(erroredTaskNum.intValue() + 1);

                        if (erroredTaskNum.intValue() >= GPResourceHome.commonState.maxNumErrorsPerTask)
                        {
                            GPResourceHome.commonState.erroredTasksStats.remove(tasks[i].getExecutable().getId());
                            GPResourceHome.commonState.setTasksFailedDone();
                            taskToBeRetried = false;

                        }
                        else
                        {
                            GPResourceHome.commonState.erroredTasksStats.put(tasks[i].getExecutable().getId(), erroredTaskNum);
                            GPResourceHome.commonState.setTasksFailedRetry();
                            taskToBeRetried = true;
                        }
                        numRetries = erroredTaskNum.intValue();

                        Integer erroredExecutorsNum = (Integer) GPResourceHome.commonState.erroredExecutorsStats.get(workerID);
                        if (erroredExecutorsNum == null)
                        {
                            erroredExecutorsNum = new Integer(0);
                        }
                        else
                            erroredExecutorsNum = new Integer(erroredExecutorsNum.intValue() + 1);


                        if (erroredExecutorsNum.intValue() >= GPResourceHome.commonState.maxNumErrorsPerExecutor)
                        {
                            GPResourceHome.commonState.erroredExecutorsStats.put(workerID, new Integer(0));


                            workerSuspended = true;

                            //suspend worker
                        }
                        else
                        {
                            GPResourceHome.commonState.erroredExecutorsStats.put(workerID, erroredTaskNum);

                        }
                        }



                    } 
                    //put this back to not retry all failed tasks...
                    /*
                    else
                    {

                        GPResourceHome.commonState.writeLogTask((tasks[i].getExecutable()).getId(), "FAILED_" + tasks[i].getExitCode());
                        GPResourceHome.commonState.setTasksFailedDone();
                        taskToBeRetried = false;
                    } */



                    gpResource.logger.debug("inserting task performance metrics in hashMap...");
                    TaskPerformance tPerf = (TaskPerformance)gpResource.taskPerf.get((tasks[i].getExecutable()).getId());
                    if (tPerf == null)
                    {
                        gpResource.logger.debug("ERROR: TaskPerformance not found in HashMap for taskID " + (tasks[i].getExecutable()).getId() + " although it should have been there... double check why not!");
                        //this should never happen
                        tPerf = new TaskPerformance();

                        tPerf.setTaskID((tasks[i].getExecutable()).getId());
                        tPerf.setWorkerID(workerID);
                        tPerf.setWaitQueueTime();
                        tPerf.setWorkerQueueTime();
                    }
                    tPerf.setResultsQueueTime();
                    if (numRetries > 0)
                    {
                        tPerf.setNumRetries(numRetries);
                    }


                    gpResource.taskPerf.put(tPerf.getTaskID(), tPerf);
                    gpResource.logger.debug("inserted task performance metrics in hashMap!");   


                    //if (gpResource.NOT_PER_TASK)
                    //{


                    if (!taskToBeRetried)
                    {

                        gpResource.logger.debug("Inserting task in a global queue for notification thread to send it out to the user...");
                        //should be inserting just the notification info, not entire task...
                        //String notificationValue = (tasks[i].getExecutable()).getId() + ":" + tasks[i].getExitCode() + " " + (tasks[i].getExecutable()).getNotification();
                        
                        GPResourceHome.commonState.notificationQ.insert(new NotificationTask(gpResource, tasks[i]));
                    }
                    else
                    {
                        gpResource.logger.debug("insert task back into the wait queue due to known failed task exit code...");

                        UserJob userJob = new UserJob();
                        userJob.setUserID("RETRY");
                        userJob.setPassword("");
                        Executable execs[] = new Executable[1];
                        execs[0] = tasks[i].getExecutable();

                        userJob.setExecutables(execs);
                        UserJobResponse ujr = submitTasks(userJob);
                        if (ujr.isValid())
                        {
                            gpResource.logger.debug("task inserted back to be retried!");

                        }
                        else
                            gpResource.logger.debug("task failed to be inserted back to be retried!");


                        //insert task back into the wait queue
                    }

                    //this should go in the notificaiotn thread...
                    /*
                    gpResource.logger.debug("Sending notification per task to user for results available for pickup...");
                    if (gpResourceHome.sendNotificationUser((tasks[i].getExecutable()).getId() + ":" + tasks[i].getExitCode(), (tasks[i].getExecutable()).getNotification()))
                    {
                        gpResource.logger.debug("Notificaiton for task "+ (tasks[i].getExecutable()).getId() + " with an exit code of " +tasks[i].getExitCode() + " sent successfully to "+ (tasks[i].getExecutable()).getNotification()+"!");

                        gpResource.logger.debug("writeLogTaskPerf()...");

                        tPerf.setEndTime();

                        GPResourceHome.commonState.writeLogTaskPerf(tPerf.toString());

                        gpResource.taskPerf.remove((tasks[i].getExecutable()).getId());

                        Integer userMapValue = GPResourceHome.commonState.userMap.get((tasks[i].getExecutable()).getNotification() + ":" + gpResource.resourceKey.getValue());
                        if (userMapValue == null)
                        {

                            //this sould never happen...
                            //GPResourceHome.commonState.userMap.put(execs[i].getNotification() + ":" + gpResource.resourceKey.getValue(), new Integer(1));
                        } else
                        {
                            if (userMapValue.intValue() <= 1)
                            {
                                GPResourceHome.commonState.userMap.remove((tasks[i].getExecutable()).getNotification() + ":" + gpResource.resourceKey.getValue());

                            } else
                                GPResourceHome.commonState.userMap.put((tasks[i].getExecutable()).getNotification() + ":" + gpResource.resourceKey.getValue(), new Integer(userMapValue.intValue() - 1));
                        }

                        GPResourceHome.commonState.writeLogUser();
                        gpResource.logger.debug("updated user in userMap!");

                        GPResourceHome.commonState.setDeliveredTask();


                    } else
                    {
                        gpResource.taskPerf.put(tPerf.getTaskID(), tPerf);
                        gpResource.logger.debug("Notificaiton failed, user should poll to retrieve results...");

                    }*/
                    //}

                }
                gpResource.logger.debug("Added " + numTasks + " tasks to the results task queue");
            }

            //this is not needed since we have a notification thread...
            /*
                if (gpResource.NOT_PER_TASK == false)
                {

                    gpResource.logger.debug("Sending notification to user for results available for pickup...");
                    if (gpResourceHome.sendNotificationUser("null", gpResource.userID))
                    {
                        gpResource.logger.debug("Notificaiton sent to " + gpResource.userID + " successfully!");

                    } else
                    {
                        gpResource.logger.debug("Notificaiton failed, user should poll to retrieve results...");

                    }
                }
                */


            /*
            if (gpResource.getNumWorkerResults() == gpResource.getNumUserResults())
            {

                gpResource.logger.debug("Execution complete: "+ gpResource.getNumWorkerResults() + " executions complete... ");
                gpResource.logger.debug("Performing packaging of results..."); 
                //gpResource.setFinalResult(finalResult);
                //maybe we should do something at this point...
                //gpResource.logger.debug("Final result is stored in " + finalResult + " for later retrieval by the user!"); 


            } else
            {
                gpResource.logger.debug("Still not done... (getNumWorkerResults() = " + gpResource.getNumWorkerResults() + ") != (getNumUserResults() = " + gpResource.getNumUserResults() + ")"); 

                gpResource.logger.debug("Received results: " + numTasks + " executions completed!");
            }      */




            gpResource.logger.debug("Received results: " + numTasks + " executions completed!");
            //send back OK... via WorkerWorkResponse() 






            synchronized(GPResourceHome.commonState)
            {
                GPResourceHome.commonState.setNum_Active_Tasks(GPResourceHome.commonState.getNum_Active_Tasks() - numTasks);
                //if (gpResource.resultsRetrieved == false)
                //{
                GPResourceHome.commonState.setNum_Completed_Tasks(GPResourceHome.commonState.getNum_Completed_Tasks() + result.getNumTasks());
                GPResourceHome.commonState.setNum_Failed_Tasks(0);
                //}
            }


            gpResource.setactiveTask(gpResource.getactiveTask() - numTasks);

            //gpResource.logger.info("workerResult(): queuedTasks=" + gpResource.getqueuedTask() + " activeTasks=" + gpResource.getactiveTask() + " completedStacks=" + gpResource.getNumWorkerResults() + " allStacks=" + gpResource.getNumUserResults() + " %completed=" + gpResource.getNumWorkerResults()*100.0/gpResource.getNumUserResults() + " GPWS_queuedTasks=" + GPResourceHome.commonState.getAvr_Q_Length() + " GPWS_activeTasks=" + GPResourceHome.commonState.getNum_Active_Tasks() + " GPWS_activeResources=" + GPResourceHome.commonState.numWorkers());




            gpResource.logger.info("Received " + numTasks + " work results from " + workerID + " in " + ct.getElapsedTime() + " ms");



            wrr.setValid(true);
            //this is for piggy-backing more tasks on the reply...
            if (gpResource.enablePiggyBacking && result.isShutingDown() == false && gpResource.useTCPCore == false && !workerSuspended)
            {

                wrr.setMoreWork(true);

                WorkerWork sourceWorker = new WorkerWork();
                sourceWorker.setValid(true);
                sourceWorker.setMachID(workerID);

                ct.stop();

                WorkerWorkResponse wwr = dispatchWork(sourceWorker);
                ct.start();

                if (wwr.isValid())
                {
                    wrr.setTasks(wwr.getTasks());

                }
                else
                {
                    wrr.setMoreWork(false);

                }


            }
            else
                wrr.setMoreWork(false);


            if (wrr.isMoreWork() == true)
            {
                gpResource.logger.info("Dispatched more work via piggy-backing to " + workerID + " in XXX ms");
            }
            else if (wrr.isMoreWork() == false && result.isShutingDown() == false)
            {
                if (!workerSuspended)
                {
                    GPResourceHome.commonState.setFreeWorker(workerID);

                    gpResource.logger.debug("set worker " + workerID + " to free");
                }
                else
                {

                    logger.debug("suspending worker... *** setPendWorker(" + workerID + ") due to too many errors...");
                    System.out.println("suspending worker... *** setPendWorker(" + workerID + ") due to too many errors...");

                    GPResourceHome.commonState.setPendWorker(workerID);
                    GPResourceHome.commonState.pendWorkerTime.put(workerID, new Long(System.currentTimeMillis()));

                }

            }
            else if (result.isShutingDown() == true)
            {
                gpResource.logger.debug("received shuting down command from worker " + workerID + "...");
                WorkerDeRegistration wdr = new WorkerDeRegistration();
                wdr.setMachID(workerID);
                wdr.setService(LOAD_BALANCER);
                deregisterWorker(wdr);
            }
            else
            {
                if (!workerSuspended)
                {

                    gpResource.logger.debug("unkown state, setting worker " + workerID + " to free...");
                    GPResourceHome.commonState.setFreeWorker(workerID);
                }
                else
                {

                    logger.debug("suspending worker... *** setPendWorker(" + workerID + ") due to too many errors...");
                    System.out.println("suspending worker... *** setPendWorker(" + workerID + ") due to too many errors...");

                    GPResourceHome.commonState.setPendWorker(workerID);
                    GPResourceHome.commonState.pendWorkerTime.put(workerID, new Long(System.currentTimeMillis()));

                }
            }



            ct.stop();
            //if (DIPERF) gpResource.logger.warn("GPService:workerResult(): " + ct.getElapsedTime() + " ms");
            if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPService:workerResult(): " + ct.getElapsedTime() + " ms");



            return wrr;
            //return (new Boolean("true")).booleanValue();
            // return true;

        }
        catch (Exception e)
        {
            e.printStackTrace();

            wrr.setValid(false);
            wrr.setMoreWork(false);
            if (workerID != null)
            {
                GPResourceHome.commonState.setFreeWorker(workerID);
            }

            ct.stop();
            if (DIPERF) System.out.println(System.currentTimeMillis() + " : GPService:workerResult(): " + ct.getElapsedTime() + " ms");

            return wrr;

        }
    } 


    //used for data aware scheduling...
    public Task getTaskQ(String worker) throws RemoteException
    {

        //GPResource gpResource = getResource();

        GPResource gpResource = this;
        gpResource.logger.debug("getTaskQ(" +worker+") starting...");
        //gpResource.logger.info("Requesting 1 task to be pulled off the wait queue on behalf of " + worker + "; using data aware scheduler to get the best task for this worker...");


        Task bestTask = null;

        //GPResource gpResource = getResource();
        //GPResourceHome gpResourceHome = getResourceHome();

        int numTasksVisited = 0;
        int cacheHitMax = 0;

        boolean notFoundBestTask = true;

        CacheHitMiss cacheHitMiss = new CacheHitMiss();


        try
        {

            //this should not be synchronid... it won't scale... should find a work around...

            synchronized(gpResource.taskPendQ)
            {

                for (ListIterator it=gpResource.taskPendQ.listIterator(); it.hasNext() && notFoundBestTask; )
                {
                    Task tTask = null;
                    //synchronized (gpResource.taskPendQ)
                    //{

                    tTask = (Task)it.next();
                    it.remove();
                    //}
                    cacheHitMiss = GPResourceHome.commonState.getCacheHit(tTask, worker);
                    numTasksVisited++;

                    if (cacheHitMiss.localHit > cacheHitMax)
                    {
                        //bestTask = (Task)gpResource.taskPendQ.remove();
                        if (bestTask != null)
                        {
                            it.add(bestTask);
                        }
                        bestTask = tTask;
                        cacheHitMax = cacheHitMiss.localHit;
                    }
                    else
                    {
                        it.add(tTask);
                    }

                    //GPResourceHome.commonState.setCacheMisses(((tasks[i].getInputData()).getLogicalName()).size());
                    //if ((bestTask != null && (tTask.getExecutable()).getInputData() != null && ((tTask.getExecutable()).getInputData()).getLogicalName() != null && cacheHitMax == (((tTask.getExecutable()).getInputData()).getLogicalName()).length) || numTasksVisited >= gpResource.CACHE_WINDOW_SIZE)
                    //hack: disabled the window size to ensure that the entire wait queue is searched... put back the above test to change back
                    //if ((bestTask != null && (bestTask.getExecutable()).getInputData() != null && ((bestTask.getExecutable()).getInputData()).getLogicalName() != null && cacheHitMax >= (((bestTask.getExecutable()).getInputData()).getLogicalName()).length))
                    if ((bestTask != null && cacheHitMax >= (((bestTask.getExecutable()).getInputData()).getLogicalName()).length) || numTasksVisited >= gpResource.CACHE_WINDOW_SIZE)
                    {
                        notFoundBestTask = false;
                        //old way of computing cache hit rates....
                        //cacheHitMax = Math.max(0, Math.min((((bestTask.getExecutable()).getInputData()).getLogicalName()).length, cacheHitMax));
                        //GPResourceHome.commonState.setCacheHits(Math.max(0, cacheHitMax));
                        //GPResourceHome.commonState.setCacheMisses( (((tTask.getExecutable()).getInputData()).getLogicalName()).length - cacheHitMax);
                        //GPResourceHome.commonState.setCacheMisses(cacheHitMiss.miss);
                        //GPResourceHome.commonState.setCacheGlobalHits(cacheHitMiss.globalHit);
                        //GPResourceHome.commonState.setCacheGlobalHits(Math.max(0, Math.min((((bestTask.getExecutable()).getInputData()).getLogicalName()).length - GPResourceHome.commonState.getCacheHits(), cacheHitMiss.globalHit)));
                        //GPResourceHome.commonState.setCacheMisses(Math.max(0, (((bestTask.getExecutable()).getInputData()).getLogicalName()).length - GPResourceHome.commonState.getCacheHits() - GPResourceHome.commonState.getCacheGlobalHits()));
                        /*
                        try
                        {
                            gpResource.logger.info("GPResource:getTaskQ(worker): Found best task for worker " + worker +" after evaluating " + numTasksVisited + " tasks; CacheLocalHits = " + GPResourceHome.commonState.getCacheHits()+ " CacheGlobalHits = " + GPResourceHome.commonState.getCacheGlobalHits() + " CacheMiss = " + GPResourceHome.commonState.getCacheMisses() + " gpResource.taskQ.size() = " + gpResource.taskQ.size() + " gpResource.taskPendQ.size() = " + gpResource.taskPendQ.size());
                        }
                        catch (Exception e)
                        {
                            gpResource.logger.info("GPResource:getTaskQ(worker): cacheHitMiss is null: " + e);
                            it.add(bestTask);
                            bestTask = null;
                        } */

                        //GPResourceHome.commonState.setCacheHits(cacheHitMiss.localHit);
                        //GPResourceHome.commonState.setCacheGlobalHits(cacheHitMiss.globalHit);
                        //GPResourceHome.commonState.setCacheMisses(cacheHitMiss.miss);
                        //return bestTask;
                    }

                } 
            }

            if (bestTask == null || (bestTask != null && cacheHitMax < (((bestTask.getExecutable()).getInputData()).getLogicalName()).length) )
            {



                //this should not be synchronid... it won't scale... should find a work around...
                synchronized(gpResource.taskQ)
                {

                    for (ListIterator it=gpResource.taskQ.listIterator(); it.hasNext() && notFoundBestTask; )
                    {
                        Task tTask = null;
                        //synchronized (gpResource.taskQ)
                        //{

                        tTask = (Task)it.next();
                        it.remove();
                        //}
                        cacheHitMiss = GPResourceHome.commonState.getCacheHit(tTask, worker);
                        numTasksVisited++;

                        if (cacheHitMiss.localHit > cacheHitMax)
                        {
                            //bestTask = (Task)gpResource.taskQ.remove();

                            if (bestTask != null)
                            {
                                it.add(bestTask);
                            }
                            bestTask = (Task)tTask;
                            cacheHitMax = cacheHitMiss.localHit;
                        }

                        else
                        {
                            it.add(tTask);
                        }

                        //if ((bestTask != null  && (tTask.getExecutable()).getInputData() != null && ((tTask.getExecutable()).getInputData()).getLogicalName() != null &&  cacheHitMax == (((tTask.getExecutable()).getInputData()).getLogicalName()).length) || numTasksVisited >= gpResource.CACHE_WINDOW_SIZE)
                        //hack: disabled the window size to ensure that the entire wait queue is searched... put back the above test to change back
                        if ((bestTask != null && cacheHitMax >= (((bestTask.getExecutable()).getInputData()).getLogicalName()).length) || numTasksVisited >= gpResource.CACHE_WINDOW_SIZE)
                        {
                            notFoundBestTask = false;
                            //old way of computing cache hit rates....
                            //cacheHitMax = Math.max(0, Math.min((((bestTask.getExecutable()).getInputData()).getLogicalName()).length, cacheHitMax));
                            //GPResourceHome.commonState.setCacheHits(Math.max(0, cacheHitMax));
                            //GPResourceHome.commonState.setCacheHits(cacheHitMiss.localHit);
                            //GPResourceHome.commonState.setCacheMisses( (((tTask.getExecutable()).getInputData()).getLogicalName()).length - cacheHitMax);
                            //GPResourceHome.commonState.setCacheMisses(cacheHitMiss.miss);
                            //GPResourceHome.commonState.setCacheGlobalHits(cacheHitMiss.globalHit);
                            //GPResourceHome.commonState.setCacheGlobalHits(Math.max(0, Math.min((((bestTask.getExecutable()).getInputData()).getLogicalName()).length - GPResourceHome.commonState.getCacheHits(), cacheHitMiss.globalHit)));
                            //GPResourceHome.commonState.setCacheGlobalHits(cacheHitMiss.globalHit);
                            //GPResourceHome.commonState.setCacheMisses(Math.max(0, (((bestTask.getExecutable()).getInputData()).getLogicalName()).length - GPResourceHome.commonState.getCacheHits() - GPResourceHome.commonState.getCacheGlobalHits()));
                            //GPResourceHome.commonState.setCacheMisses(cacheHitMiss.miss);

                            /*
                            try
                            {
                                gpResource.logger.info("GPResource:getTaskQ(worker): Found best task for worker " + worker +" after evaluating " + numTasksVisited + " tasks; CacheLocalHits = " + GPResourceHome.commonState.getCacheHits()+ " CacheGlobalHits = " + GPResourceHome.commonState.getCacheGlobalHits() + " CacheMiss = " + GPResourceHome.commonState.getCacheMisses() + " gpResource.taskQ.size() = " + gpResource.taskQ.size() + " gpResource.taskPendQ.size() = " + gpResource.taskPendQ.size());
                            }
                            catch (Exception e)
                            {
                                gpResource.logger.info("GPResource:getTaskQ(worker): cacheHitMiss is null: " + e);
                                it.add(bestTask);
                                bestTask = null;
                            }
    
                            return bestTask;
                            */
                        }

                    }  
                }
            }

            if (bestTask != null && cacheHitMax > 0)
            {

                try
                {
                    GPResourceHome.commonState.setCacheHits(cacheHitMiss.localHit);
                    GPResourceHome.commonState.setCacheGlobalHits(cacheHitMiss.globalHit);
                    GPResourceHome.commonState.setCacheMisses(cacheHitMiss.miss);

                    //(((bestTask.getExecutable()).getInputData()).getLogicalName()).length;

                    if (logger.isDebugEnabled()) System.out.println("**************** GPResourceHome.commonState.activeTasksCacheHits.put(): localHitKB = " + cacheHitMiss.localHitKB + " globalHitKB = " + cacheHitMiss.globalHitKB + " missKB = " + cacheHitMiss.missKB);

                    GPResourceHome.commonState.activeTasksCacheHits.put(bestTask.getExecutable().getId(), cacheHitMiss);


                    gpResource.logger.info("GPResource:getTaskQ(worker): Found best task for worker " + worker +" after evaluating " + numTasksVisited + " tasks; CacheLocalHits = " + cacheHitMiss.localHit+ " CacheGlobalHits = " + cacheHitMiss.globalHit + " CacheMiss = " + cacheHitMiss.miss + " gpResource.taskQ.size() = " + gpResource.taskQ.size() + " gpResource.taskPendQ.size() = " + gpResource.taskPendQ.size());
                }
                catch (Exception e)
                {
                    gpResource.logger.info("GPResource:getTaskQ(worker): cacheHitMiss is null: " + e);
                    gpResource.taskQ.insertFront(bestTask);
                    //bestTask = null;
                }

                return bestTask;


            }
            else if (bestTask != null && cacheHitMax == 0)
            {
                try
                {

                    gpResource.taskQ.insertFront(bestTask);
                    //bestTask = null;
                    gpResource.logger.info("GPResource:getTaskQ(worker): Did not find any task, bestTask != null, but cacheHitMax == 0, for worker " + worker +" after evaluating " + numTasksVisited + " tasks; gpResource.taskQ.size() = " + gpResource.taskQ.size() + " gpResource.taskPendQ.size() = " + gpResource.taskPendQ.size());
                }
                catch (Exception e)
                {

                }
                return bestTask;

            }
            else if (bestTask == null)
            {
                gpResource.logger.info("GPResource:getTaskQ(worker): Did not find any task, bestTask == null, for worker " + worker +" after evaluating " + numTasksVisited + " tasks; gpResource.taskQ.size() = " + gpResource.taskQ.size() + " gpResource.taskPendQ.size() = " + gpResource.taskPendQ.size());

                return bestTask;
            }
            else
            {
                gpResource.logger.info("GPResource:getTaskQ(worker): Did not find any task, bestTask == null, for worker " + worker +" after evaluating " + numTasksVisited + " tasks; gpResource.taskQ.size() = " + gpResource.taskQ.size() + " gpResource.taskPendQ.size() = " + gpResource.taskPendQ.size());

                return null;

            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
            //throw new RemoteException("getTaskQ() failed: ", e);


            gpResource.logger.info("GPResource:getTaskQ(worker): No best task found for worker " + worker +" after evaluating " + numTasksVisited + " tasks...");

            if (bestTask != null)
            {
                gpResource.taskQ.insertFront(bestTask);
                //bestTask = null;
            }

            return null;

        }

        //return null;
    }

    /*
public Task[] getTaskQ(String worker, int size) throws RemoteException
{

//GPResource gpResource = getResource();
GPResource gpResource = this;
gpResource.logger.debug("getTaskQ(" +worker+","+ size + ") starting...");
return null;
}         */


    //used to be synchronized... but it hangs randomly...
    //used for sequential gets from queue, no data aware scheduling is performed...
    public Task[] getTaskQ(int size, String worker) throws RemoteException
    {
        //GPResource gpResource = getResource();
        GPResource gpResource = this;
        gpResource.logger.debug("getTaskQ(" + size + ", " + worker + ") starting unsynchronized...");

        gpResource.logger.info("Requesting " + size + " tasks to be pulled off the wait queue on behalf of " + worker + "; using sequential gets from the wait queue...");




        try
        {

            //GPResource gpResource = getResource();

            gpResource.logger.debug("getTaskQ(" + size + "): creating list of tasks...");
            List list = new LinkedList();

            boolean keepTask = true;

            int numTasksVisited = 0;


            //could control level of replication
            int keepTaskThreshold = 1;
            double busyWorkers = Math.max(GPResourceHome.commonState.busyWorkers.size()*1.0 - 1, 0);
            gpResource.logger.debug("getTaskQ(" + size + "): busyWorkers = " + busyWorkers);
            gpResource.logger.debug("getTaskQ(" + size + "): pendWorkers = " + GPResourceHome.commonState.pendWorkers.size());
            gpResource.logger.debug("getTaskQ(" + size + "): freeWorkers = " + GPResourceHome.commonState.freeWorkers.size());
            double allWorkers = GPResourceHome.commonState.roundRobin.size()*1.0;
            gpResource.logger.debug("getTaskQ(" + size + "): allWorkers = " + allWorkers);

            //double cpuUtilizationThreshold = this.CPU_UTILIZATION_THRESHOLD;
            double cpuUtilization = 0.0;
            if (allWorkers > 0)
                {
                cpuUtilization = busyWorkers/allWorkers;
            }
            else
            {
                cpuUtilization = 0.0;

            }
            gpResource.logger.debug("getTaskQ(" + size + "): cpuUtilization = " + cpuUtilization);

            while (gpResource.taskQ.size() > 0 && list.size()<size && numTasksVisited < Math.min(this.CACHE_WINDOW_SIZE, gpResource.taskQ.size()))
            {
                keepTask = true;
                gpResource.logger.debug("getTaskQ(" + size + "): removing task from wait queue...");
                //list.add((Task)gpResource.taskQ.remove());
                Task curTask = (Task)gpResource.taskQ.remove();
                numTasksVisited++;

                if (gpResource.CACHE_GRID_GLOBAL)
                {
                    if ((curTask.getExecutable()).isDataCaching())
                    {
                        CacheHitMiss cacheHitMiss = GPResourceHome.commonState.getCacheHit(curTask, worker);

                        if (this.MAX_CACHE_HIT)
                        {
                            if (busyWorkers == 0)
                            {
                                keepTaskThreshold = (int)Math.min(allWorkers,1);

                            }
                            else
                            {
                                keepTaskThreshold = (int)Math.min(Math.floor(allWorkers/busyWorkers),1);
                            }
                            //roundRobin.size() + " " + freeWorkers.size() + " " + pendWorkers.size() + " " + busyWorkers.size();
                            //if (cacheHitMiss.localHit == 0 && cacheHitMiss.globalHit >= keepTaskThreshold)

                            if (allWorkers > 0)
                                {
                                cpuUtilization = busyWorkers/allWorkers;
                            }
                            else
                            {
                                cpuUtilization = 0.0;

                            }
                            gpResource.logger.debug("getTaskQ(" + size + "): cpuUtilization = " + cpuUtilization);


                            if (cpuUtilization < this.CPU_UTILIZATION_THRESHOLD)
                            {
                                keepTask = true;

                            }
                            else if (cacheHitMiss.localHit == 0 && cacheHitMiss.globalHit > 0)
                            {
                                keepTask = false;
                            }


                            gpResource.logger.debug("getTaskQ(" + size + "): keepTask = " + keepTask);

                        }

                        if (keepTask)
                        {
                            GPResourceHome.commonState.setCacheHits(cacheHitMiss.localHit);
                            GPResourceHome.commonState.setCacheMisses(cacheHitMiss.miss);
                            GPResourceHome.commonState.setCacheGlobalHits(cacheHitMiss.globalHit);


                            if (logger.isDebugEnabled()) System.out.println("**************** GPResourceHome.commonState.activeTasksCacheHits.put(): localHitKB = " + cacheHitMiss.localHitKB + " globalHitKB = " + cacheHitMiss.globalHitKB + " missKB = " + cacheHitMiss.missKB);

                            GPResourceHome.commonState.activeTasksCacheHits.put(curTask.getExecutable().getId(), cacheHitMiss);
                        }


                    }
                }
                if (keepTask)
                {

                    gpResource.logger.debug("getTaskQ(" + size + "): adding task to list... 2");
                    list.add(curTask);

                }
                else
                {

                    gpResource.logger.debug("getTaskQ(" + size + "): inserting task back to pending queueu adding task to list... 2");
                    this.taskPendQ.insert(curTask);

                }

            }


            if (list.size() > 0)
            {

                gpResource.logger.debug("getTaskQ(" + size + "): converting list of size " + list.size() + " to array...");
                Task[] tasks = (Task[])list.toArray(new Task[list.size()]);


                /*
                if (gpResource.CACHE_GRID_GLOBAL)
                {
                    for (int i=0;i<tasks.length;i++)
                    {
    
                        if ((tasks[i].getExecutable()).isDataCaching())
                        {
    
                            CacheHitMiss cacheHitMiss = GPResourceHome.commonState.getCacheHit(tasks[i], worker);
                            //DataFiles inputData = tasks[i].getInputData();
                            //GPResourceHome.commonState.setCacheMisses((((tasks[i].getExecutable()).getInputData()).getLogicalName()).length - cacheHit);
                            //GPResourceHome.commonState.setCacheHits(cacheHit);
    
                            if (this.MAX_CACHE_HIT)
                            {
    
                            
                            if (cacheHitMiss != null)
                            {
    
                            }
                            }
    
                            if (cacheHitMiss != null)
                            {
    
    
                                //GPResourceHome.commonState.setCacheHits(cacheHitMiss.localHit);
                                GPResourceHome.commonState.setCacheMisses(cacheHitMiss.miss);
                                GPResourceHome.commonState.setCacheGlobalHits(cacheHitMiss.globalHit);
                            }
                        }
    
                    }
                }
                */

                gpResource.logger.debug("getTaskQ(" + size + "): returning task...");

                if (list != null)
                {
                    list.clear();
                    list = null;
                }


                return tasks;

            }
            else
            {
                if (list != null)
                {
                    list.clear();
                    list = null;
                }


                gpResource.logger.debug("getTaskQ(" + size + "): did not find any appropriate tasks to dispatch...");
                return null;
            }


        }
        catch (Exception e)
        {
            //throw new RemoteException("getTaskQ() failed: ", e);
            //it should be putting things back if it removed them...

            gpResource.logger.debug("getTaskQ(" + size + "): exception " + e.getMessage());
            return null;

        }

    }

    public Task[] getTaskPendQ(int size, String worker) throws RemoteException
    {
        //GPResource gpResource = getResource();
        GPResource gpResource = this;
        gpResource.logger.debug("getTaskPendQ(" + size + ", " + worker + ") starting unsynchronized...");

        gpResource.logger.info("Requesting " + size + " tasks to be pulled off the pending queue on behalf of " + worker + "; using sequential gets from the wait queue...");




        try
        {

            //GPResource gpResource = getResource();

            gpResource.logger.debug("getTaskQ(" + size + "): creating list of tasks...");
            List list = new LinkedList();

            boolean keepTask = true;

            int numTasksVisited = 0;

            int keepTaskThreshold = 1;
            double busyWorkers = Math.max(GPResourceHome.commonState.busyWorkers.size()*1.0 - 1, 0);
            gpResource.logger.debug("getTaskPendQ(" + size + "): busyWorkers = " + busyWorkers);
            gpResource.logger.debug("getTaskPendQ(" + size + "): pendWorkers = " + GPResourceHome.commonState.pendWorkers.size());
            gpResource.logger.debug("getTaskPendQ(" + size + "): freeWorkers = " + GPResourceHome.commonState.freeWorkers.size());
            double allWorkers = GPResourceHome.commonState.roundRobin.size()*1.0;
            gpResource.logger.debug("getTaskPendQ(" + size + "): allWorkers = " + allWorkers);

            //double cpuUtilizationThreshold = 0.8;
            double cpuUtilization = 0.0;
            if (allWorkers > 0)
                {
                cpuUtilization = busyWorkers/allWorkers;
            }
            else
            {
                cpuUtilization = 0.0;

            }
            gpResource.logger.debug("getTaskPendQ(" + size + "): cpuUtilization = " + cpuUtilization);


            while (gpResource.taskPendQ.size() > 0 && list.size()<size && numTasksVisited < size)
            {
                keepTask = true;
                gpResource.logger.debug("getTaskPendQ(" + size + "): removed task from pending queue...");
                //list.add((Task)gpResource.taskQ.remove());
                Task curTask = (Task)gpResource.taskPendQ.remove();
                numTasksVisited++;

                if (gpResource.CACHE_GRID_GLOBAL)
                {
                    if ((curTask.getExecutable()).isDataCaching())
                    {
                        CacheHitMiss cacheHitMiss = GPResourceHome.commonState.getCacheHit(curTask, worker);

                        if (this.MAX_CACHE_HIT)
                        {
                            if (busyWorkers == 0)
                            {
                                keepTaskThreshold = (int)Math.min(allWorkers,1);

                            }
                            else
                            {
                                keepTaskThreshold = (int)Math.min(Math.floor(allWorkers/busyWorkers),1);
                            }
                            //roundRobin.size() + " " + freeWorkers.size() + " " + pendWorkers.size() + " " + busyWorkers.size();
                            //if (cacheHitMiss.localHit == 0 && cacheHitMiss.globalHit >= keepTaskThreshold)

                            gpResource.logger.debug("getTaskPendQ(" + size + "): keepTaskThreshold = " + keepTaskThreshold);


                            if (allWorkers > 0)
                                {
                                cpuUtilization = busyWorkers/allWorkers;
                            }
                            else
                            {
                                cpuUtilization = 0.0;

                            }

                            gpResource.logger.debug("getTaskPendQ(" + size + "): cpuUtilization = " + cpuUtilization);


                            if (cpuUtilization < this.CPU_UTILIZATION_THRESHOLD)
                            {
                                keepTask = true;

                            }
                            else if (cacheHitMiss.localHit == 0 && cacheHitMiss.globalHit > 0)
                            {
                                keepTask = false;
                            }

                            gpResource.logger.debug("getTaskPendQ(" + size + "): keepTask = " + keepTask);

                        }

                        if (keepTask)
                        {
                            GPResourceHome.commonState.setCacheHits(cacheHitMiss.localHit);
                            GPResourceHome.commonState.setCacheMisses(cacheHitMiss.miss);
                            GPResourceHome.commonState.setCacheGlobalHits(cacheHitMiss.globalHit);


                            if (logger.isDebugEnabled()) System.out.println("**************** GPResourceHome.commonState.activeTasksCacheHits.put(): localHitKB = " + cacheHitMiss.localHitKB + " globalHitKB = " + cacheHitMiss.globalHitKB + " missKB = " + cacheHitMiss.missKB);

                            GPResourceHome.commonState.activeTasksCacheHits.put(curTask.getExecutable().getId(), cacheHitMiss);
                        }


                    }
                }
                if (keepTask)
                {
                    gpResource.logger.debug("getTaskPendQ(" + size + "): adding task to list... 2");
                    list.add(curTask);

                }
                else
                {
                    gpResource.logger.debug("getTaskPendQ(" + size + "): inserting task back to pending queueu adding task to list... 2");
                    this.taskPendQ.insert(curTask);

                }

            }


            if (list.size() > 0)
            {

                gpResource.logger.debug("getTaskPendQ(" + size + "): converting list of size " + list.size() + " to array...");
                Task[] tasks = (Task[])list.toArray(new Task[list.size()]);


                /*
                if (gpResource.CACHE_GRID_GLOBAL)
                {
                    for (int i=0;i<tasks.length;i++)
                    {
    
                        if ((tasks[i].getExecutable()).isDataCaching())
                        {
    
                            CacheHitMiss cacheHitMiss = GPResourceHome.commonState.getCacheHit(tasks[i], worker);
                            //DataFiles inputData = tasks[i].getInputData();
                            //GPResourceHome.commonState.setCacheMisses((((tasks[i].getExecutable()).getInputData()).getLogicalName()).length - cacheHit);
                            //GPResourceHome.commonState.setCacheHits(cacheHit);
    
                            if (this.MAX_CACHE_HIT)
                            {
    
    
                            if (cacheHitMiss != null)
                            {
    
                            }
                            }
    
                            if (cacheHitMiss != null)
                            {
    
    
                                //GPResourceHome.commonState.setCacheHits(cacheHitMiss.localHit);
                                GPResourceHome.commonState.setCacheMisses(cacheHitMiss.miss);
                                GPResourceHome.commonState.setCacheGlobalHits(cacheHitMiss.globalHit);
                            }
                        }
    
                    }
                }
                */

                gpResource.logger.debug("getTaskPendQ(" + size + "): returning task...");

                if (list != null)
                {
                    list.clear();
                    list = null;
                }


                return tasks;
            }
            else
            {
                if (list != null)
                {
                    list.clear();
                    list = null;
                }
                gpResource.logger.debug("getTaskPendQ(" + size + "): did not find any appropriate tasks to dispatch...");
                return null;
            }

        }
        catch (Exception e)
        {
            //throw new RemoteException("getTaskQ() failed: ", e);
            //it should be putting things back if it removed them...
            gpResource.logger.debug("getTaskPendQ(" + size + "): exception " + e.getMessage());
            return null;

        }

    }



    public Task[] getTaskPendQ_old(int size, String worker) throws RemoteException
    {
        //GPResource gpResource = getResource();
        GPResource gpResource = this;
        gpResource.logger.debug("getTaskQ(" + size + ", " + worker + ") starting unsynchronized...");

        gpResource.logger.info("Requesting " + size + " tasks to be pulled off the wait queue on behalf of " + worker + "; using sequential gets from the wait queue...");




        try
        {

            //GPResource gpResource = getResource();

            gpResource.logger.debug("getTaskQ(" + size + "): creating list of tasks...");
            List list = new LinkedList();


            while (gpResource.taskPendQ.size() > 0 && list.size()<size)
            {
                gpResource.logger.debug("getTaskQ(" + size + "): adding task to list... 3");
                list.add((Task)gpResource.taskPendQ.remove());
            }

            gpResource.logger.debug("getTaskQ(" + size + "): converting list to array...");
            Task[] tasks = (Task[])list.toArray(new Task[list.size()]);


            if (gpResource.CACHE_GRID_GLOBAL)
            {
                for (int i=0;i<tasks.length;i++)
                {

                    if ((tasks[i].getExecutable()).isDataCaching())
                    {

                        CacheHitMiss cacheHitMiss = GPResourceHome.commonState.getCacheHit(tasks[i], worker);
                        //DataFiles inputData = tasks[i].getInputData();
                        //GPResourceHome.commonState.setCacheMisses((((tasks[i].getExecutable()).getInputData()).getLogicalName()).length - cacheHit);
                        //GPResourceHome.commonState.setCacheHits(cacheHit);

                        if (cacheHitMiss != null)
                        {


                            GPResourceHome.commonState.setCacheHits(cacheHitMiss.localHit);
                            GPResourceHome.commonState.setCacheMisses(cacheHitMiss.miss);
                            GPResourceHome.commonState.setCacheGlobalHits(cacheHitMiss.globalHit);


                            if (logger.isDebugEnabled()) System.out.println("**************** GPResourceHome.commonState.activeTasksCacheHits.put(): localHitKB = " + cacheHitMiss.localHitKB + " globalHitKB = " + cacheHitMiss.globalHitKB + " missKB = " + cacheHitMiss.missKB);

                            GPResourceHome.commonState.activeTasksCacheHits.put(tasks[i].getExecutable().getId(), cacheHitMiss);
                        }
                    }

                }
            }


            gpResource.logger.debug("getTaskQ(" + size + "): returning task...");

            return tasks;

        }
        catch (Exception e)
        {
            //throw new RemoteException("getTaskQ() failed: ", e);
            //it should be putting things back if it removed them...
            return null;

        }

    }


    public UserJobResponse submitTasks(UserJob job) throws RemoteException 
    {

        //extra stuff
        /*
        int ti = -1;
        if (execQ != null)
        {
            ti = execQ.size();
            System.out.print(ti + " ");
        }
        else 
            System.out.print("null "); 

        if (taskQ != null)
        {
            ti = taskQ.size();
            System.out.print(ti + " ");
        }
        else 
            System.out.print("null "); 

        if (resultQ != null)
        {
            ti = resultQ.size();
            System.out.print(ti + " ");
        }
        else 
            System.out.print("null "); 

        if (taskPendQ != null)
        {
            ti = taskPendQ.size();
            System.out.print(ti + " ");
        }
        else 
            System.out.print("null "); 

        if (taskPerf != null)
        {
            ti = taskPerf.size();
            System.out.print(ti + " ");
        }
        else 
            System.out.print("null "); 

        System.out.print("\n"); 
          */


        StopWatch ct = new StopWatch();
        ct.start();

        UserJobResponse jobRP = new UserJobResponse();
        //good stuff
        //GPResource gpResource = getResource();
        GPResource gpResource = this;
        //GPResourceHome gpResourceHome = getResourceHome();

        //int numToExpect = job.getNumExecs();

        //gpResource.initialNumTasks = numToExpect;
        /*
        if (numToExpect <= 0)
        {

            jobRP.setValid(false);

            gpResource.logger.info("User Job failed because the number of executables to expect was " + numToExpect + " which is less than 1");
            return jobRP;
        }
           */


        /*
              if (gpResource.firstTime)
              {
      
      
                  gpResource.resultsRetrieved = false;
      
                  gpResource.jobTime.reset();
                  gpResource.jobTime.start();
      
      
      
      
      
      
                  //this is done so we can use a resource multiple times for different jobs
                  try
                  {
      
                      gpResource.initState();
                  } catch (Exception e)
                  {
                      //GPResource gpResource = getResource();
                      gpResource.logger.debug("ERROR: initState(): " + e);
      
                      jobRP.setValid(false);
      
                      gpResource.logger.info("User Job failed because of initState()...");
                      return jobRP;
                  }
      
                  synchronized(gpResource)
                  {
                      gpResource.setNumUserResults(0);
                      gpResource.setNumWorkerResults(0);
                      gpResource.setqueuedTask(0);
                  }
      
      
      
      
              }
            */


        //if (gpResource.isBusy() == false)
        //{
        //    gpResource.setBusy(true);

        String userID = job.getUserID();
        String password = job.getPassword();

        gpResource.logger.debug("User Job submitted from '" + userID + "', processing...");

        gpResource.logger.trace("GPService: userJob()");

        Executable[] execs = job.getExecutables(); 


        //GPResourceHome gpResourceHome = getResourceHome();

        //ResourceKey tKey = (ResourceKey)gpResource.getID();
        //String sKey = (String)gpResource.getID();


        //ResourceKey tempKey = gpResourceHome.getKey(sKey);
        //gpResource.logger.debug("Key translation... " + sKey + " ==> " + tempKey.getValue());




        int jobLength = execs.length;

        /*
        if (gpResource.firstTime)
        {
            gpResource.MAX_EXECS_PER_CALL = jobLength;
        } */


        synchronized(gpResource)
        {
            gpResource.setNumUserResults(gpResource.getNumUserResults() + jobLength);
            //gpResource.setNumWorkerResults(0);
        }

        /*
        if (jobLength > 0)
        {
            GPResourceHome.commonState.notificationWorkerQ.insert(gpResource);
        } */


        for (int i=0;i<jobLength;i++)
        {
            Task task = exec2task(execs[i]);
            //gpResource.execQ.insert(execs[i]);

            gpResource.logger.debug("inserting task in taskQ...");
            gpResource.taskQ.insert(task);
            gpResource.logger.debug("task inserted in taskQ!");

            GPResourceHome.commonState.writeLogTask(execs[i].getId(), "WAIT_QUEUE");

            gpResource.logger.debug("inserting task performance metrics in taskPerf...");
            TaskPerformance tPerf = new TaskPerformance();
            tPerf.setTaskID(execs[i].getId());
            tPerf.setWaitQueueTime();
            gpResource.taskPerf.put(tPerf.getTaskID(), tPerf);
            gpResource.logger.debug("inserted task performance metrics in hashMap!");


            gpResource.logger.debug("inserting user in userMap...");

            Integer userMapValue = (Integer)GPResourceHome.commonState.userMap.get(execs[i].getNotification() + ":" + gpResource.resourceKey.getValue());
            if (userMapValue == null)
            {
                GPResourceHome.commonState.userMap.put(execs[i].getNotification() + ":" + gpResource.resourceKey.getValue(), new Integer(1));
            }
            else
                GPResourceHome.commonState.userMap.put(execs[i].getNotification() + ":" + gpResource.resourceKey.getValue(), new Integer(userMapValue.intValue() + 1));

            GPResourceHome.commonState.writeLogUser();

            gpResource.logger.debug("inserted user in userMap!");

            GPResourceHome.commonState.setWaitingTask();

            //gpResource.logger.debug("inserting task in tasksSet...");
            //GPResourceHome.commonState.tasksSet.add(execs[i].getId() + ":" + gpResource.resourceKey.getValue());
            //gpResource.logger.debug("inserted task in tasksSet!");

            //the if statement is a hack, put back to normal
            //if (GPResourceHome.commonState.notificationWorkerQ.size() < (int)Math.max(GPResourceHome.commonState.roundRobin.size()*10, 1000))
            //{
            //GPResourceHome.commonState.notificationWorkerQ.insert(gpResource);
            //}


        }
        //done to make sure the 
        //gpResource.execQ.insert(null);


        //gpResource.logger.debug("User submitted " + jobLength + "execs... " + gpResource.execQ.size() + " exec queue length...");
        gpResource.logger.debug("User submitted " + jobLength + " tasks... " + gpResource.taskQ.size() + " taskQ queue length...");




        gpResource.logger.debug("User submitted job!");
        //UserJobResponse jobRP = new UserJobResponse();
        jobRP.setValid(true);

        gpResource.logger.debug("User Job of size " + jobLength + " submitted!");


        /*
        if (gpResource.firstTime)
        {
            synchronized(GPResourceHome.commonState)
            {
                GPResourceHome.commonState.setNum_Active_Jobs(GPResourceHome.commonState.getNum_Active_Jobs() + 1);
                //GPResourceHome.commonState.setNum_Active_Tasks(GPResourceHome.commonState.getNum_Active_Tasks() + jobLength);
                //GPResourceHome.commonState.setAvr_Q_Length(GPResourceHome.commonState.getAvr_Q_Length() + jobLength);
                //GPResourceHome.commonState.setAvr_Job_Size((GPResourceHome.commonState.getAvr_Job_Size()*(GPResourceHome.commonState.getNum_Completed_Jobs()+GPResourceHome.commonState.getNum_Active_Jobs() -1) + jobLength)/(GPResourceHome.commonState.getNum_Completed_Jobs() + GPResourceHome.commonState.getNum_Active_Jobs()));
            }
        } */

        synchronized(GPResourceHome.commonState)
        {
            //GPResourceHome.commonState.setNum_Active_Jobs(GPResourceHome.commonState.getNum_Active_Jobs() + 1);
            //GPResourceHome.commonState.setNum_Active_Tasks(GPResourceHome.commonState.getNum_Active_Tasks() + jobLength);
            GPResourceHome.commonState.setAvr_Q_Length(GPResourceHome.commonState.getAvr_Q_Length() + jobLength);
            //GPResourceHome.commonState.setAvr_Job_Size((GPResourceHome.commonState.getAvr_Job_Size()*(GPResourceHome.commonState.getNum_Completed_Jobs()+GPResourceHome.commonState.getNum_Active_Jobs() -1) + jobLength)/(GPResourceHome.commonState.getNum_Completed_Jobs() + GPResourceHome.commonState.getNum_Active_Jobs()));
        }

        //fix this...


        synchronized(gpResource)
        {
            gpResource.setqueuedTask(gpResource.getqueuedTask() + jobLength);
        }


        //GPResourceHome gpResourceHome = getResourceHome();

        //used for logging to file
        if (GPResourceHome.logger2file != null && execs.length > 0)
        {
            String logMsg = "\nUserID " + userID +  " Number_of_Executables " + execs.length + "\n";
            for (int i=0;i<execs.length;i++)
            {
                //skyServerResource.logger2file.info(userID + " : { " + execs[i].getRa() + " " + execs[i].getDec() + " " + execs[i].getBand() + " } ==> " + results[i].getName());
                logMsg += userID + " : " + execs[i].getId() + " : EXECUTABLE " + execs[i].getCommand() + " ARGUEMENTS ";
                String arguements[] = execs[i].getArguements();
                if (arguements != null)
                {

                    for (int j=0;j<arguements.length;j++)
                    {

                        logMsg += arguements[j] + " ";
                    }
                }
                logMsg += "\n";
            }
            GPResourceHome.logger2file.info(logMsg);
        }

        /*
        if (gpResource.firstTime)
        {
            //gpResource.logger.debug("Starting Executable2Task thread once...");
            //gpResource.t2t = new Executable2Task(gpResource, gpResourceHome, tempKey);
            //gpResource.t2t.start();
            gpResource.firstTime = false;
            gpResource.lastTime = false;


        }  */

        /*
        if (gpResource.t2t == null)
        {
            gpResource.logger.debug("Starting Executable2Task thread once...");
            gpResource.t2t = new Executable2Task(gpResource, gpResourceHome, gpResource.resourceKey);
            gpResource.t2t.start();
        }
        */

        /*
        if (gpResource.ne == null)
        {
            gpResource.logger.debug("Starting the Notification Engine tread once...");
            gpResource.ne = new NotificationEngine(gpResource, gpResourceHome, gpResource.resourceKey);
            gpResource.ne.start();
        }
    */

        /*
        int maxNumThreads = 1000;
        DummyThread dt[] = new DummyThread[maxNumThreads];

        gpResource.logger.debug("Number of threads: " + Thread.activeCount());

        for (int tt=0;tt<maxNumThreads;tt++)
        {
            try
            {


                long heapSize = Runtime.getRuntime().totalMemory();

                // Get maximum size of heap in bytes. The heap cannot grow beyond this size.
                // Any attempt will result in an OutOfMemoryException.
                long heapMaxSize = Runtime.getRuntime().maxMemory();

                // Get amount of free memory within the heap in bytes. This size will increase
                // after garbage collection and decrease as new objects are created.
                long heapFreeSize = Runtime.getRuntime().freeMemory();


                gpResource.logger.info("createThread(): heapSize: " + heapSize + " heapMaxSize: " + heapMaxSize + " heapFreeSize: " + heapFreeSize + " numThreads " + java.lang.Thread.activeCount());
            dt[tt] = new DummyThread();
            dt[tt].start();
            gpResource.logger.debug(tt + ": Number of threads: " + Thread.activeCount());
            }
            catch (Exception e)
            {

                gpResource.logger.debug("Error in creating thread: " + e);
                e.printStackTrace();
                throw new RemoteException("Error in creating thread: ", e);
            }
        }
        */

        /*
        //last one...
        if (numToExpect == gpResource.getNumUserResults())
        {

            jobRP.setValid(true);
            gpResource.lastTime = true;

            //gpResource.execQ.insert(null);

            

            return jobRP;

        }   */

        ct.stop();
        //if (DIPERF) gpResource.logger.warn("GPService:userJob(): " + ct.getElapsedTime() + " ms");
        if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPService:userJob(): " + ct.getElapsedTime() + " ms");



        gpResource.logger.info("User '" + userID + "' submitted " + jobLength + " tasks in " + ct.getElapsedTime() + " ms");

        jobRP.setValid(true);
        if (LOAD_BALANCER)
        {

            jobRP.setServiceState(getServiceState());
            jobRP.setService(true);

        }
        else
        {
            jobRP.setService(false);
        }
        return jobRP;
        //return new UserJobResponse(true);
        //}
        //it is busy
        //else
        //{
        //    gpResource.logger.info("User Job submitted to a busy resource, rejecting the job...");
        //    jobRP.setValid(false);
        //    return jobRP;

        //}
    }


    public MonitorStateResponse getMonitorState(MonitorState s) throws RemoteException {

        //StopWatch ct = null;
        //if (GPResourceHome.perfProfile.PROFILE) {

        //    ct = new StopWatch(false, false, true);
        //    ct.start();
        //}

        MonitorStateResponse sr = null;


        GPResource gpResource = this;
        //GPResource gpResource = getResource();
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


            sr = new MonitorStateResponse();

            sr.setResourceAllocated(gpResource.resourceAllocated);
            sr.setResourceRegistered(GPResourceHome.commonState.roundRobin.size());
            sr.setResourceFree(GPResourceHome.commonState.freeWorkers.size());
            sr.setResourcePending(GPResourceHome.commonState.pendWorkers.size());
            sr.setResourceActive(GPResourceHome.commonState.busyWorkers.size());
            sr.setTaskNumQueues(GPResourceHome.commonState.resourceSet.size());
            sr.setTaskSubmit((int)GPResourceHome.commonState.getSubmittedTasks());
            sr.setTaskWaitQueue((int)GPResourceHome.commonState.getWaitingTasks());
            sr.setTaskDispatch((int)GPResourceHome.commonState.getDispatchedTasks());
            sr.setTaskActive((int)GPResourceHome.commonState.getActiveTasks());
            sr.setTaskDeliveryQueue((int)GPResourceHome.commonState.getDoneTasks());
            sr.setTaskDelivered((int)GPResourceHome.commonState.getDeliveredTasks());
            sr.setCacheSize((int)GPResourceHome.commonState.cacheGridIndex.size());
            sr.setCacheHitsLocal(GPResourceHome.commonState.getCacheHits());
            sr.setCacheHitsGlobal(GPResourceHome.commonState.getCacheGlobalHits());
            sr.setCacheMisses(GPResourceHome.commonState.getCacheMisses());
            sr.setSystemNumThreads(Thread.activeCount());
            sr.setSystemCPUuser(GPResourceHome.commonState.cpuInfo.user);
            sr.setSystemCPUsystem(GPResourceHome.commonState.cpuInfo.system);
            sr.setSystemCPUidle(GPResourceHome.commonState.cpuInfo.idle);



            sr.setSystemHeapSize((int)Math.ceil(Runtime.getRuntime().totalMemory()/(1024*1024)));
            sr.setSystemHeapFree((int)Math.ceil(Runtime.getRuntime().freeMemory()/(1024*1024)));
            sr.setSystemHeapMax((int)Math.ceil(Runtime.getRuntime().maxMemory()/(1024*1024)));
            sr.setValid(true);



            //return sr;

        }
        catch (Exception e)
        {
            e.printStackTrace();

            sr = new MonitorStateResponse();
            sr.setValid(false);


            //return sr;
        }


        //if (GPResourceHome.perfProfile.PROFILE) {
        //    ct.stop();
        //    GPResourceHome.perfProfile.addSample("monitorState", ct.getElapsedTimeMX());
        //    ct = null;
        //}

        return sr;


    }

    public ServiceState getServiceState() throws RemoteException {

        //StopWatch ct = null;
        //if (GPResourceHome.perfProfile.PROFILE) {

        //    ct = new StopWatch(false, false, true);
        //    ct.start();
        //}

        ServiceState sr = null;


        GPResource gpResource = this;
        //GPResource gpResource = getResource();
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


            sr = new ServiceState();

            sr.setResourceAllocated(gpResource.resourceAllocated);
            sr.setResourceRegistered(GPResourceHome.commonState.roundRobin.size());
            sr.setResourceFree(GPResourceHome.commonState.freeWorkers.size());
            sr.setResourcePending(GPResourceHome.commonState.pendWorkers.size());
            sr.setResourceActive(GPResourceHome.commonState.busyWorkers.size());
            sr.setTaskNumQueues(GPResourceHome.commonState.resourceSet.size());
            sr.setTaskSubmit((int)GPResourceHome.commonState.getSubmittedTasks());
            sr.setTaskWaitQueue((int)GPResourceHome.commonState.getWaitingTasks());
            sr.setTaskDispatch((int)GPResourceHome.commonState.getDispatchedTasks());
            sr.setTaskActive((int)GPResourceHome.commonState.getActiveTasks());
            sr.setTaskDeliveryQueue((int)GPResourceHome.commonState.getDoneTasks());
            sr.setTaskDelivered((int)GPResourceHome.commonState.getDeliveredTasks());
            sr.setSystemNumThreads(Thread.activeCount());
            sr.setSystemCPUuser(GPResourceHome.commonState.cpuInfo.user);
            sr.setSystemCPUsystem(GPResourceHome.commonState.cpuInfo.system);
            sr.setSystemCPUidle(GPResourceHome.commonState.cpuInfo.idle);



            sr.setSystemHeapSize((int)Math.ceil(Runtime.getRuntime().totalMemory()/(1024*1024)));
            sr.setSystemHeapFree((int)Math.ceil(Runtime.getRuntime().freeMemory()/(1024*1024)));
            sr.setSystemHeapMax((int)Math.ceil(Runtime.getRuntime().maxMemory()/(1024*1024)));
            sr.setValid(true);



            //return sr;

        }
        catch (Exception e)
        {
            e.printStackTrace();

            sr = new ServiceState();
            sr.setValid(false);


            //return sr;
        }


        //if (GPResourceHome.perfProfile.PROFILE) {
        //    ct.stop();
        //    GPResourceHome.perfProfile.addSample("monitorState", ct.getElapsedTimeMX());
        //    ct = null;
        //}

        return sr;


    }



    //user job submittion 
    public UserResultResponse retrieveTasks(UserResult ur) throws RemoteException 
    {
        StopWatch ct = new StopWatch();
        ct.start();


        //GPResource gpResource = getResource();
        GPResource gpResource = this;

        gpResource.logger.debug("User requesting results, processing...");
        gpResource.logger.trace("GPService: userResult()");

        try

        {
            gpResource.logger.debug("Sending job results to the user...");

            gpResource.logger.debug("userResult(): entering getResourceHome()...");

            //GPResourceHome gpResourceHome = getResourceHome();

            //gpResource.setState("JOB RETRIEVED");
            gpResource.logger.debug("userResult(): creating empty result...");

            UserResultResponse result = new UserResultResponse();

            //if (gpResource.getNumWorkerResults() == gpResource.getNumUserResults())
            if (gpResource.resultQ.size() > 0)
            {

                gpResource.logger.debug("userResult(): getFinalResult() exists!");

                result.setValid(true);

                gpResource.MAX_EXECS_PER_CALL = ur.getMax_per_call();

                //result.setNumExecutables(gpResource.getNumImagesStacked());
                int numTasksToSend = (int)Math.max(Math.min(gpResource.MAX_EXECS_PER_CALL, gpResource.resultQ.size()), 1);
                result.setNumTasks(numTasksToSend);

                Task tasks[] = new Task[result.getNumTasks()];

                gpResource.logger.info("userResult(): populating result with " + numTasksToSend +" tasks!");
                int i=0;

                while (i<numTasksToSend && gpResource.resultQ.size() > 0)
                {
                    tasks[i++] = (Task)gpResource.resultQ.remove();
                    GPResourceHome.commonState.writeLogTask((tasks[i].getExecutable()).getId(), "TASK_DELIVERED");



                    gpResource.logger.debug("inserting task performance metrics in hashMap...");
                    TaskPerformance tPerf = (TaskPerformance)gpResource.taskPerf.remove((tasks[i].getExecutable()).getId());
                    if (tPerf == null)
                    {
                        gpResource.logger.debug("ERROR: TaskPerformance not found in HashMap for taskID " + (tasks[i].getExecutable()).getId() + " although it should have been there... double check why not!");
                        //this should never happen
                        tPerf = new TaskPerformance();

                        tPerf.setTaskID((tasks[i].getExecutable()).getId());
                        tPerf.setWorkerID("NONE:0");
                        tPerf.setWaitQueueTime();
                        tPerf.setWorkerQueueTime();
                        tPerf.setResultsQueueTime();
                    }

                    tPerf.setEndTime();
                    tPerf.setExitCode(tasks[i].getExitCode());


                    gpResource.logger.debug("writeLogTaskPerf()...");
                    GPResourceHome.commonState.writeLogTaskPerf(tPerf);

                    GPResourceHome.commonState.setDeliveredTask();


                    GPResourceHome.commonState.writeLogUser();


                    //gpResource.taskPerf.put(tPerf.getTaskID(), tPerf);
                    //gpResource.logger.debug("inserted task performance metrics in hashMap!");

                }
                result.setTasks(tasks);
                //gpResource.retrievedTasks += result.getNumTasks();





                gpResource.logger.debug("userResult(): sending result to user...");


                gpResource.logger.info("Result returned to user succefully!");

                ct.stop();
                //if (DIPERF) gpResource.logger.warn("GPService:userResult(): " + ct.getElapsedTime() + " ms");
                if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPService:userResult(): " + ct.getElapsedTime() + " ms");





                //timeEnd = System.currentTimeMillis();
                //long elapsedTimeMillis = timeEnd - timeStart;

                //if (gpResource.resultQ.size() == 0 && gpResource.retrievedTasks == gpResource.initialNumTasks)
                //{
                //gpResource.jobTime.stop();
                //if (DIPERF) gpResource.logger.warn("GPService:jobTime: " + gpResource.jobTime.getElapsedTime() + " ms");


                /*
                 synchronized(GPResourceHome.commonState)
                 {
                     if (gpResource.resultsRetrieved == false)
                     {

                         GPResourceHome.commonState.setAvr_Job_Time((GPResourceHome.commonState.getAvr_Job_Time()*GPResourceHome.commonState.getNum_Completed_Jobs() + gpResource.jobTime.getElapsedTime()*1.0/1000.0)/(GPResourceHome.commonState.getNum_Completed_Jobs() + 1));
                         GPResourceHome.commonState.setAvr_Resp_Time((GPResourceHome.commonState.getAvr_Resp_Time()*GPResourceHome.commonState.getNum_Completed_Tasks() + gpResource.jobTime.getElapsedTime())/(GPResourceHome.commonState.getNum_Completed_Tasks() + result.getNumTasks()));


                         GPResourceHome.commonState.setNum_Active_Jobs(GPResourceHome.commonState.getNum_Active_Jobs() - 1);

                         //if (gpResource.getNumImagesStacked() > 0)
                         //{


                         GPResourceHome.commonState.setNum_Completed_Jobs(GPResourceHome.commonState.getNum_Completed_Jobs() + 1);
                         //} else
                         GPResourceHome.commonState.setNum_Failed_Jobs(GPResourceHome.commonState.getNum_Failed_Jobs() );


                         // GPResourceHome.commonState.setNum_Completed_Tasks(GPResourceHome.commonState.getNum_Completed_Tasks() + result.getNumTasks());
                         //GPResourceHome.commonState.setNum_Failed_Tasks(GPResourceHome.commonState.getNum_Failed_Tasks() + result.getNumTuplesFailed() + result.getNumTasksFailed());
                         //GPResourceHome.commonState.setNum_Failed_Tasks(0);
                         gpResource.resultsRetrieved = true;
                         gpResource.firstTime = true;
                         gpResource.lastTime = false;
                         gpResource.retrievedTasks = 0;
                         gpResource.initialNumTasks = 0;

                     }
                 }   */



                //gpResource.setBusy(false);
                //}

                /*
                synchronized(GPResourceHome.commonState)
                {
                    //if (gpResource.resultsRetrieved == false)
                    //{
                        GPResourceHome.commonState.setNum_Completed_Tasks(GPResourceHome.commonState.getNum_Completed_Tasks() + result.getNumTasks());
                        GPResourceHome.commonState.setNum_Failed_Tasks(0);
                    //}
                } */

                gpResource.logger.info("User retrieved " + numTasksToSend + " results in " + ct.getElapsedTime() + " ms");


                return result;
            }
            else
            {
                gpResource.logger.debug("userResult(): no tasks ready for pickup... returning empty result...");
                result.setValid(false);
                //gpResource.logger.info("Result returned to user failed!");
                gpResource.logger.info("User failed to retrieve any results in " + ct.getElapsedTime() + " ms");


                ct.stop();
                //if (DIPERF) gpResource.logger.warn("GPService:userResult():failed " + ct.getElapsedTime() + " ms");
                if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPService:userResult():failed: " + ct.getElapsedTime() + " ms");


                return result;


            }
        }
        catch (Exception e)
        {
            gpResource.logger.debug("Error in sending job results to the user: " + e);
            UserResultResponse result = new UserResultResponse();
            result.setValid(false);

            gpResource.logger.info("Result returned to user failed!");

            ct.stop();
            //if (DIPERF) gpResource.logger.warn("GPService:userResult():error " + ct.getElapsedTime() + " ms");
            if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPService:userResult():error: " + ct.getElapsedTime() + " ms");

            return result;

        }
    }



    private Task exec2task(Executable executable)
    {
        Task t = new Task();
        t.setExecutable(executable);
        //int numDataFiles = 0;
        //t.setNumDataFiles(numDataFiles);
        //String dataFiles[] = null;
        //for (int i=0;i<numDataFiles;i++)
        //{
        //    dataFiles[i] = new String("some data file name");
        //}
        //the fileName field below is just here for backwards compatibility with the old 3DcacheGrid code... this should be removed and the 3DcacheGrid code should be updated to reflect the dataFiles[] field that supports an array of files...
        //t.setFileName(new String("some data file name"));
        //t.setDataFiles(dataFiles);
        //t.setResult(new String(""));
        t.setStdout(new String(""));
        t.setStderr(new String(""));
        t.setExitCode(-77777);
        t.setCaptureStdout(logger.isDebugEnabled());
        t.setCaptureStderr(logger.isDebugEnabled());

        return t;
    }

    public WorkerRegistrationResponse registerWorker(WorkerRegistration wr ) throws RemoteException {

        StopWatch ct = new StopWatch();
        ct.start();
        //should display the number of registered workers


        //GPResource gpResource = getResource();
        GPResource gpResource = this;

        gpResource.logger.trace("GPService: registerWorker()");


        String wID = wr.getMachID();
        long wallTime = wr.getWallTime();



        if (wID == null)
        {
            throw new RemoteException("registerWorker(): wID == null");

        }
        else
        {

            gpResource.logger.trace("GPService: registerWorker(): " + wID);

        }




        //gpResource.setNumJobs(0);
        //gpResource.setState("REGISTERED");


        //GPResourceHome gpResourceHome = getResourceHome();

        


        try
        {

            if (GPResourceHome.commonState == null)
            {
                GPResourceHome.load_common();
                //throw new RemoteException("deregisterWorker(): commonState == null ");

            }
            //else

            //*****************************************
            GPResourceHome.commonState.cleanupActiveTasks(wID);
            //*****************************************




            GPResourceHome.commonState.register(wID, gpResource.CACHE_GRID_GLOBAL);



        }
        catch (Exception e)
        {
            throw new RemoteException("registerWorker() failed: " + e);
        }

        GPResourceHome.store_common();


        //synchronized(gpResource)
        //{

        //    gpResource.setNumWorkers(gpResource.getNumWorkers() + 1);
        //}


        //global state - worker resource
        //GPResourceHome gpResourceHome = getResourceHome();
        //this is done in case the container got restarted... the gpResourceHome is not persistent, so we are updating the global non-persistent workerResource with from a local persistent workerResource 
        //if (gpResourceHome.getWorkerResource() == null) 
        //{

        //    gpResourceHome.setWorkerResource(gpWorkerResource);
        //}



        gpResource.logger.info("Worker " + wID + " is registered and ready to receive work!");
        System.out.println("Worker " + wID + " is registered and ready to receive work, for a total of " + GPResourceHome.commonState.numWorkers() + " workers!");
        gpResource.logger.info(GPResourceHome.commonState.numWorkers() + " registered workers available...");
        if (gpResource.DATA_AWARE_SCHEDULER && gpResource.CACHE_WINDOW_SIZE_FOLLOWS_REGISTERED_EXECUTORS && GPResourceHome.commonState.numWorkers() >= 0)
        {

            this.CACHE_WINDOW_SIZE = GPResourceHome.commonState.numWorkers()*this.CACHE_WINDOW_SIZE_MULTIPLIER;
            if (this.CACHE_WINDOW_SIZE <= 0)
            {
                this.CACHE_WINDOW_SIZE = 1;
            }

            if (this.CACHE_WINDOW_SIZE > this.MAX_CACHE_WINDOW_SIZE)
            {
                this.CACHE_WINDOW_SIZE = this.MAX_CACHE_WINDOW_SIZE;
            }

            gpResource.logger.info("Data-Aware Scheduler window size set to " + this.CACHE_WINDOW_SIZE);
        }


        //System.out.println("GPService: State information... ");   
        //GPResourceHome.commonState.printState();


        synchronized(GPResourceHome.commonState)
        {
            GPResourceHome.commonState.setNum_Resources(GPResourceHome.commonState.getNum_Resources() + 1);
        }
        //System.out.println("GPService: State information after registration... ");   
        //GPResourceHome.commonState.printState();


        //gpResource.logger.info("Worker " + wID + " registered and ready to receive work!");
        //fix this to do real worker registration... probalby in a hash table



        //StopWatch ct = new StopWatch();
        //ct.start();
        gpResource.setNewWorkers();
        ct.stop();
        //if (DIPERF) gpResource.logger.warn("GPService:registerWorker(): " + ct.getElapsedTime() + " ms");
        if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPService:registerWorker(): " + ct.getElapsedTime() + " ms");


        //GPResourceHome.commonState.fireNotification(new NotificationObject(gpResource, wID, wallTime));

        //*******************************************



        if (wallTime > 0)
        {
            gpResource.logger.debug("registerWorker(): worker lifetime is " + wallTime + ", schedule for cleaning up active tasks for " + wID + ", and then de-registere...");
            GPResourceHome.commonState.activeWorkerCleanup.schedule(new WorkerDeregister(wID, gpResource, GPResourceHome.commonState), new Date(System.currentTimeMillis()+wallTime));
        }
        else
        {
            gpResource.logger.debug("registerWorker(): worker lifetime is infinite, nothing to schedule for cleaning up...");
        }


        //*******************************************


        return new WorkerRegistrationResponse(true);
    }   


    public WorkerDeRegistrationResponse deregisterWorker(WorkerDeRegistration wr) throws RemoteException {
        StopWatch ct = new StopWatch();
        ct.start();

        //should display the number of registered workers


        //GPResource gpResource = getResource();
        GPResource gpResource = this;


        gpResource.logger.trace("GPService: deregisterWorker()");


        String wID = wr.getMachID();


        //gpResource.setNumJobs(0);

        //gpResourceHome
        //GPResourceHome gpResourceHome = getResourceHome();



        //GPResourceHome gpResourceHome = getResourceHome();


        try
        {

            if (GPResourceHome.commonState == null)
            {
                GPResourceHome.load_common();
                //throw new RemoteException("deregisterWorker(): commonState == null ");

            }
            //else
            //*****************************************

            GPResourceHome.commonState.cleanupActiveTasks(wID);
            //*****************************************
                                                          
            if (GPResourceHome.commonState.deRegister(wID, gpResource.CACHE_GRID_GLOBAL))
            {
                gpResource.logger.info("Worker " + wID + " is de-registered and will not receive work anymore!");
                gpResource.logger.info(GPResourceHome.commonState.numWorkers() + " remaining registered workers...");
                System.out.println("Worker " + wID + " has de-registered and will not receive work anymore, " + GPResourceHome.commonState.numWorkers() + " workers left registered!");




                if (gpResource.DATA_AWARE_SCHEDULER && gpResource.CACHE_WINDOW_SIZE_FOLLOWS_REGISTERED_EXECUTORS && GPResourceHome.commonState.numWorkers() >= 0)
                {
                    this.CACHE_WINDOW_SIZE = GPResourceHome.commonState.numWorkers()*this.CACHE_WINDOW_SIZE_MULTIPLIER;
                    if (this.CACHE_WINDOW_SIZE <= 0)
                    {
                        this.CACHE_WINDOW_SIZE = 1;
                    }


                    if (this.CACHE_WINDOW_SIZE > this.MAX_CACHE_WINDOW_SIZE)
                    {
                        this.CACHE_WINDOW_SIZE = this.MAX_CACHE_WINDOW_SIZE;
                    }

                    gpResource.logger.info("Data-Aware Scheduler window size set to " + this.CACHE_WINDOW_SIZE);
                }





                synchronized(GPResourceHome.commonState)
                {

                    if (GPResourceHome.commonState.getNum_Resources() > 0)
                    {

                        GPResourceHome.commonState.setNum_Resources(GPResourceHome.commonState.getNum_Resources() - 1);
                    }
                }

                //fix this to do real worker registration... probalby in a hash table
                gpResource.setDeregisteredWorkers();
            }
            //persistent socket de-registration....

            gpResource.logger.debug("Removing worker persistent socket...");
            boolean removedPS = GPResourceHome.notification.removePersistentSocket(wID);
            gpResource.logger.debug("Removed worker persistent socket: " + removedPS);

        }
        catch (Exception e)
        {
            System.out.println("deregisterWorker() failed: " + e);
            throw new RemoteException("deregisterWorker() failed: " + e);
        }

        GPResourceHome.store_common();

        //if (gpResourceHome.commonState.deRegister(wID))
        //    gpResource.setState("DE-REGISTERED");
        //else
        //    gpResource.setState("DE-REGISTERED FAILED");



        //synchronized(gpResource)
        //{

        //    gpResource.setNumWorkers(gpResource.getNumWorkers() - 1);
        //}


        //global state - worker resource
        //GPResourceHome gpResourceHome = getResourceHome();
        //this is done in case the container got restarted... the gpResourceHome is not persistent, so we are updating the global non-persistent workerResource with from a local persistent workerResource 
        //if (gpResourceHome.getWorkerResource() == null) 
        //{

        //    gpResourceHome.setWorkerResource(gpWorkerResource);
        //}


        ct.stop();
        //if (DIPERF) gpResource.logger.warn("GPService:deregisterWorker(): " + ct.getElapsedTime() + " ms");
        if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPService:deregisterWorker(): " + ct.getElapsedTime() + " ms");



        return new WorkerDeRegistrationResponse(true);
    }   




}



