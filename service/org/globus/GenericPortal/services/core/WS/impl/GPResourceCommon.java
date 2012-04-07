//SVN versoin latest
package org.globus.GenericPortal.services.core.WS.impl;

import org.globus.GenericPortal.common.*;
import org.globus.wsrf.ResourceKey;

import org.globus.GenericPortal.common.*;
import org.globus.GenericPortal.stubs.GPService_instance.*;

//import java.io.Serializable;
import java.util.*;
import java.io.*;

import java.net.*;


import java.util.logging.*;

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



public class GPResourceCommon implements Serializable 
{
    //public NotificationBroker notificationModel = null;

    //public static GPResource globalGP = null;

    public static int resourceAllocated = 0;
    static final Log logger = LogFactory.getLog(GPResourceCommon.class);

    //public static boolean DEBUG = false;

    public boolean DEMO = false;
    public boolean PERF_LOG = true;

    //this should be a WorkerSet... change soon!
    public WorkerSet roundRobin = new WorkerSet();

    //public WorkQueue freeWorkers = new WorkQueue();
    //public WorkQueue pendWorkers = new WorkQueue();
    //public WorkQueue busyWorkers = new WorkQueue();
    public WorkerSet freeWorkers = new WorkerSet();
    public WorkerSet pendWorkers = new WorkerSet();
    public WorkerSet busyWorkers = new WorkerSet();

    public WorkQueue notificationQ = new WorkQueue();
    public WorkQueue notificationWorkerQ = new WorkQueue();



    public int initialWorkerPoolSize = 10000;
    public int initialNumElementSize = 2000000;
    public int initialNumFiles = 2000000;
    public float initialLoadFactor = 0.75f;

    public Map workerCacheEmulated = Collections.synchronizedMap(new HashMap());





    //public CacheGridGlobal cacheGridGlobal = new CacheGridGlobal(initialWorkerPoolSize, initialNumElementSize);

    //public CacheGridIndex cacheGridIndex = new CacheGridIndex(initialNumFiles, initialLoadFactor);
    public CacheIndex cacheGridIndex = new CacheIndex(initialNumFiles, initialLoadFactor);

    //public PendingTasks pendingTasks = new PendingTasks(initialWorkerPoolSize, initialLoadFactor);


    public Hashtable data;

    public double Up_Time;
    public int Num_Resources;
    public double Avr_Resource_Util;
    public int Avr_Q_Length;
    public double Avr_Resp_Time;
    public double Avr_Task_TP;
    public int Avr_Job_Size;
    public double Avr_Job_Time;
    public double Avr_Jobs_TP;
    public int Num_Active_Jobs;
    public int Num_Active_Tasks;
    public int Num_Completed_Jobs;
    public int Num_Completed_Tasks;
    public int Num_Failed_Jobs;
    public int Num_Failed_Tasks;

    public boolean isFirstTime = true;

    public String machName;

    //public int servPort;
    //public Thread monServ;
    public Thread monOut = null;
    public Thread monIdleWorkers = null;
    public Socket socket;
    public boolean exit_flag = false;

    public boolean monitorStarted = false;


    public double quanta_min;
    public double cur_Up_Time = 0;
    public double all_Num_Failed_Jobs = 0;
    public double all_Num_Failed_Tasks = 0;
    public double all_Num_Completed_Jobs = 0;
    public double all_Num_Completed_Tasks = 0;

    public BufferedWriter outUser = null;
    public BufferedWriter outWorker = null;
    public BufferedWriter outTaskPerf = null;
    public BufferedWriter outTask = null;
    public BufferedWriter loggerPerfFile = null;


    //Set tasksWaitSet = Collections.synchronizedSet(new HashSet());
    //Set tasksActiveSet = Collections.synchronizedSet(new HashSet());
    //Set tasksDoneSet = Collections.synchronizedSet(new HashSet());
    Map userMap = Collections.synchronizedMap(new HashMap());
    Set resourceSet = Collections.synchronizedSet(new HashSet());

    Map activeTasksCacheHits = Collections.synchronizedMap(new HashMap());


    private double submittedTasks = 0;
    private double dispatchedTasks = 0;
    private double waitingTasks = 0;
    private double activeTasks = 0;
    private double doneTasks = 0;
    private double deliveredTasks = 0;


    public boolean writeLogTest = true;
    public boolean writeLogPerfTest = true;
    public boolean writeLogWorkerTest = true;
    public boolean writeLogWorkerTestComplex = false;



    public boolean writeLogUserTest = true;
    public boolean writeLogTaskPerfTest = true;
    public boolean writeLogTaskTest = true;

    public Map falkonConfig = null;//Collections.synchronizedMap(new HashMap<String, TaskPerformance>());

    //*****************************************

    Map activeTasksMap = null;//Collections.synchronizedMap(new HashMap<String, TaskPerformance>());
    //*****************************************


    public static CPUInfo cpuInfo = null;//new CPUInfo(1); //once a sec


    private int tasksSuccessDone = 0;
    private int tasksFailedDone = 0;

    //Map workerCaches = Collections.synchronizedMap(new HashMap());

    private int GPFSConcOps = 0;

    private int GPFSBWRead = 1000000; //KB/s
    private int GPFSBWWrite = 100000;  //KB/s

    private int PeerBWRead = 50000; //KB/s
    private int PeerBWWrite = 25000;  //KB/s

    private int LocalBWRead = 100000; //KB/s
    private int LocalBWWrite = 50000;  //KB/s


    public synchronized void openFileGPFS()
    {
        GPFSConcOps++;


    }

    public int getOpenFileGPFS()
    {
        return GPFSConcOps;


    }


    public synchronized void closeFileGPFS()
    {
        if (GPFSConcOps>0)
        {
            GPFSConcOps--;
        }
    }


    public long readFileGPFS(int fileSizeKB)
    {
        int numOpenFiles = getOpenFileGPFS();
        if (numOpenFiles > 0)
        {
            return fileSizeKB*1000/(Math.min(GPFSBWRead,LocalBWRead*numOpenFiles)/numOpenFiles);
        }
        else
        {
            return fileSizeKB*1000/Math.min(GPFSBWRead,LocalBWRead);

        }

    }

    public long writeFileGPFS(int fileSizeKB)
    {
        int numOpenFiles = getOpenFileGPFS();
        if (numOpenFiles > 0)
        {
            return fileSizeKB*1000/(Math.min(GPFSBWWrite,LocalBWWrite*numOpenFiles)/numOpenFiles);
        }
        else
        {
            return fileSizeKB*1000/Math.min(GPFSBWWrite,LocalBWWrite);

        }
    }


    /*
    Map workerLoad = Collections.synchronizedMap(new HashMap());

    public synchronized void openFileWorker(String machID)
    {
        Integer load = workerLoad.get(machID);
        if (load == null)
        {
            load = new Integer(1);
        }
        else
        {
            load = new Integer(load.intValue() + 1);

        }
        workerLoad.put(machID, load);
    }

    public int getOpenFileWorker(String machID)
    {
        Integer load = workerLoad.get(machID);
        if (load != null)
        {
            return load.intValue();

        }
        else
        {
            return 0;
        }


    }


    public synchronized void closeFileWorker(String machID)
    {
        Integer load = workerLoad.get(machID);
        if (load == null)
        {
        }
        else
        {
            if (load.intValue() > 1)
            {
                load = new Integer(load.intValue() - 1);
                workerLoad.put(machID, load);
            }
            else
            {
                workerLoad.remove(machID);
            }
        }
        
    }
    */


    public long readFilePeer(int fileSizeKB)
    {
        return fileSizeKB*1000/PeerBWRead;
    }

    public long writeFilePeer(int fileSizeKB)
    {
        return fileSizeKB*1000/PeerBWWrite;
    }

    public long readFileLocal(int fileSizeKB)
    {
        return fileSizeKB*1000/LocalBWRead;
    }

    public long writeFileLocal(int fileSizeKB)
    {
        return fileSizeKB*1000/LocalBWWrite;
    }




    //public synchronized int getTasksSuccessDone()
    public int getTasksSuccessDone()
    {
        return tasksSuccessDone;

    }

    //public synchronized int getTasksFailedDone()
    public int getTasksFailedDone()
    {
        return tasksFailedDone;

    }

    public synchronized void setTasksSuccessDone()
    {
        tasksSuccessDone++;

    }

    public synchronized void setTasksFailedDone()
    {
        tasksFailedDone++;
    }

    public synchronized void setTasksFailedRetry()
    {
        tasksFailedRetry++;

    }

    private int tasksFailedRetry = 0;
    public int getTasksFailedRetry()
    //public synchronized int getTasksFailedRetry()
    {
        return tasksFailedRetry;

    }


    public Map readFalkonConfig()
    {
        Map falkonConf = Collections.synchronizedMap(new HashMap());

        Properties props = System.getProperties();
        String deefConfigFile = (String)props.get("FalkonConfig");

        if (deefConfigFile == null)
        {
            return falkonConf;
        }

        // Enumerate all system properties
        /*
        Enumeration enum = props.propertyNames();
    for (; enum.hasMoreElements(); ) {
        // Get property name
        String propName = (String)enum.nextElement();

        // Get property value
        String propValue = (String)props.get(propName);

        falkonConf.put(propName, propValue);
    }     */

        try
        {
            BufferedReader in = new BufferedReader(new FileReader(deefConfigFile));
            String str;
            while ((str = in.readLine()) != null)
            {
                //process(str);

                if (str.startsWith("#"))
                {
                    logger.debug("readFalkonConfig(): ignoring comment " + str);

                }
                else
                {


                    String tokens[] = str.split("=");
                    if (tokens.length == 2)
                    {
                        falkonConf.put(tokens[0], tokens[1]);


                    }

                    else
                    {

                        logger.debug("readFalkonConfig(): reading DeeF config file error at " + str + "... ignorng!");
                    }
                }

            }
            in.close();
        }
        catch (IOException e)
        {
            logger.debug("readFalkonConfig(): reading DeeF config file " + deefConfigFile + " failed: " + e);
            if (logger.isDebugEnabled()) e.printStackTrace();
            return falkonConf;

        }


        //   props.getProperty("");//.put("axis.ClientConfigFile", "/scratch/local/iraicu/GT4.0.1/client-config.wsdd");


        return falkonConf;

    }




    public synchronized void setWaitingTask()
    {
        waitingTasks++;
    }

    public synchronized void setActiveTask()
    {
        waitingTasks--;
        activeTasks++;
    }

    public synchronized void setDoneTask()
    {
        activeTasks--;
        doneTasks++;
    }

    public synchronized void setDeliveredTask()
    {
        doneTasks--;
        deliveredTasks++;
    }


    //public synchronized double getWaitingTasks()
    public double getWaitingTasks()
    {
        return waitingTasks;
    }

    //public synchronized double getActiveTasks()
    public double getActiveTasks()
    {
        return activeTasks;
    }

    public double getDoneTasks()
    //public synchronized double getDoneTasks()
    {
        //return doneTasks;
        return notificationQ.size();
    }

    //public synchronized double getSubmittedTasks()
    public double getSubmittedTasks()
    {
        return submittedTasks;
    }

    //public synchronized double getDispatchedTasks()
    public double getDispatchedTasks()
    {
        return dispatchedTasks;
    }



    public double getDeliveredTasks()
    //public synchronized double getDeliveredTasks()
    {
        return deliveredTasks;
    }

    //public synchronized double getWorkerNotificationQueueSize()
    public double getWorkerNotificationQueueSize()
    {
        //return doneTasks;
        return notificationWorkerQ.size();
    }



    //*****************************************

    //must change this to make it clean up pending tasks again...
    public boolean cleanupWorkerSet = false;
    //*****************************************

    /*
    public void fireNotification(Object arg)
    {
        //System.out.println(System.currentTimeMillis() + " SEND: " + arg);
        notificationModel.notifyObservers(arg);
        notificationModel.setChanged();

    }
    */

    public Timer activeWorkerCleanup = new Timer();

    public Timer activeTaskCleanup = new Timer();

    public Timer workerEmulation = new Timer();

    /*
    public void listenerNotification()
    {
        final GPResourceCommon commonState = this;
        // Register for events
        notificationModel.addObserver(new Observer() 
                                      {
                                          public void update(Observable o, Object arg) 
                                          {
                                              //System.out.println("listenerNotification(): received.... dummy function, not doing anything...");

                                              NotificationObject notObject = (NotificationObject) arg;
                                              try
                                              {
                                                  if (notObject.wallTime > 0)
                                                  {

                                                      System.out.println("listenerNotification(): scheduling timer for " + notObject.wallTime + " in the future to cleaning up active tasks for " + notObject.workerID + ", and then de-registere...");

                                                      activeWorkerCleanup.schedule(new WorkerDeregister(notObject.workerID, notObject.gpResource, commonState), new Date(System.currentTimeMillis()+notObject.wallTime));



                                                  }
                                                  else
                                                  {
                                                      System.out.println("listenerNotification(): nothing to schedule as the worker lifetime is 0 (infinity)...");

                                                  }

                                              } catch (Exception e)
                                              {
                                                  e.printStackTrace();
                                              }


                                          }
                                      }
                                     );
    }
    */


    //boolean isTaskPerfStartTime = false;
    long taskPerfStartTime = System.currentTimeMillis();

    public static boolean printFirstTime = true;

    //public List resourceList = Collections.synchronizedList(new LinkedList());
    public WorkQueue resourceQ = new WorkQueue();

    public GPResource getNextResource()
    {
        GPResource next = null;
        try
        {

            next = (GPResource)resourceQ.remove();
            resourceQ.insert(next);
        }

        catch (Exception e)
        {
            logger.debug("Error in getting next resource... possible a race condition, or maybe there were just no resources registered, and the logic was wrong...");
            return null;
            //throw new Exception("Error in getting next worker... possible a race condition, or maybe there were just no workers registered, and the logic was wrong...", e);
        }
        return next; //could be null if there were no workers registered
    }

    public GPResourceCommon()
    {



        if (printFirstTime)
        {
            System.err.println("Falkon Service Started...");
            printFirstTime = false;

        }

        if (falkonConfig == null)
        {
            logger.debug("initializing readFalkonConfig()...");
            falkonConfig = readFalkonConfig();
        }


        try
        {
            String temp1 = (String)falkonConfig.get("maxNumErrorsPerTask");
            String temp2 = (String)falkonConfig.get("maxNumErrorsPerExecutor");
            maxNumErrorsPerTask = Integer.parseInt(temp1);
            maxNumErrorsPerExecutor = Integer.parseInt(temp2);
        }
        catch (Exception e)
        {
            if (logger.isDebugEnabled()) e.printStackTrace();

        }
        //this.DEBUG = logger.isDebugEnabled();
        /*  if (isTaskPerfStartTime == false)
          {
              taskPerfStartTime = System.currentTimeMillis();
              isTaskPerfStartTime = true;
          }*/

        //*****************************************

        logger.debug("initializing activeTasksMap...");
        if (activeTasksMap == null) this.activeTasksMap = Collections.synchronizedMap(new HashMap());
        //*****************************************

        /*
if (notificationModel == null)
{
   logger.debug("starting listenerNotification()...");
   notificationModel = new NotificationBroker();

   listenerNotification();

}          */

        if (cpuInfo == null)
        {
            logger.debug("initializing CPUInfo...");
            cpuInfo = new CPUInfo(1); //once a sec
            logger.debug("starting CPUInfo...");
            boolean cpuStartTest = cpuInfo.startUp();
            logger.debug("started CPUInfo: "+cpuStartTest);
        }





        if (monitorStarted == false)
        {
            logger.debug("initializing initState()...");
            initState();
            monitorStarted = true;

        }

        //*****************************************


        if (cleanupWorkerSet == true)
        {

            logger.debug("initializing cleaning up pending workers...");
            cleanupWorkerSet = false;
            int suspendTimeoutInterval_ms = 15000;

            try
            {
                String temp1 = (String)falkonConfig.get("suspendTimeoutInterval_ms");
                suspendTimeoutInterval_ms = Integer.parseInt(temp1);
            }
            catch (Exception e)
            {
                if (logger.isDebugEnabled()) e.printStackTrace();

            }


            final int delay = suspendTimeoutInterval_ms;   // delay for 60 sec.
            final int period = suspendTimeoutInterval_ms;  // repeat every 60 sec.
            Timer timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask()
                                      {
                                          public void run()
                                          {
                                              logger.debug("cleaning up pending workers..."); 

                                              // Task here ...
                                              String pendWorkersArray[] = getPendWorkers();

                                              logger.debug("number of pending workers to check: " + pendWorkersArray.length); 


                                              for (int i=0;i<pendWorkersArray.length;i++)
                                              {
                                                  Long pwTime = (Long)pendWorkerTime.get(pendWorkersArray[i]);
                                                  if (pwTime != null && pwTime.longValue() + period < System.currentTimeMillis())
                                                  {
                                                      synchronized(pendWorkers)
                                                      {
                                                          logger.debug(pendWorkersArray[i] + " pendWorker has expired, cleaning up..."); 

                                                          if (pendWorkers.exists(pendWorkersArray[i]))
                                                          {
                                                              logger.debug(pendWorkersArray[i] + " pendWorker exists..."); 


                                                              logger.debug("Found pending worker that has been pending for " + (System.currentTimeMillis() - pwTime.longValue()) + " ms, which is longer than the allowed time of " + period + " ms... this should never happen, fixing the problem..."); 
                                                              //pendWorkerTime.remove(pendWorkersArray[i]);
                                                              setFreeWorker(pendWorkersArray[i]);
                                                          }
                                                      }

                                                  }
                                                  else
                                                  {
                                                      logger.debug(pendWorkersArray[i] + " pendWorker is not pending, or it has not been pending for enough time..."); 
                                                  }



                                              }




                                          }
                                      }, delay, period);


        }






        //*****************************************


    }


    private int cacheHits = 0;
    private int cacheMisses = 0;


    public synchronized void resetCacheHitMiss()
    {
        cacheHits = 0;
        cacheMisses = 0;
        cacheGlobalHits = 0;
    }


    public synchronized void setCacheHits(int n)
    {
        cacheHits += n;
    }
    public synchronized void setCacheMisses(int n)
    {
        cacheMisses += n;
    }

    //public synchronized int getCacheHits()
    public int getCacheHits()
    {
        return cacheHits;
    }
    //public synchronized int getCacheMisses()
    public int getCacheMisses()
    {
        return cacheMisses;
    }


    private int cacheGlobalHits = 0;

    public synchronized void setCacheGlobalHits(int n)
    {
        cacheGlobalHits += n;
    }
    //public synchronized int getCacheGlobalHits()
    public int getCacheGlobalHits()
    {
        return cacheGlobalHits;
    }


    public void initState()
    {
        /*
        this.SO_TIMEOUT = 10;
        this.notification = new Notification(SO_TIMEOUT);
        this.roundRobin = new WorkQueue();
        */
        //data = new Hashtable(13);  


        Up_Time = 0;
        Num_Resources = 0;
        Avr_Resource_Util = 0;
        Avr_Q_Length = 0;
        Avr_Resp_Time = 0;
        Avr_Task_TP = 0;
        Avr_Job_Size = 0;
        Avr_Job_Time = 0;
        Avr_Jobs_TP = 0;
        Num_Active_Jobs = 0;
        Num_Active_Tasks = 0;
        Num_Completed_Jobs = 0;
        Num_Completed_Tasks = 0;
        Num_Failed_Jobs = 0;
        Num_Failed_Tasks = 0;


        try
        {


            machName = java.net.InetAddress.getLocalHost().getHostName();
        }
        catch (Exception e)
        {

            logger.debug("ERROR: "+e.getMessage());
            if (logger.isDebugEnabled()) e.printStackTrace();
        }


        //servPort = 50555;


        try
        {
            //monServ = new MonitorServer(this);
            //monServ.start();
            if (DEMO || PERF_LOG)
            {


                if (monOut == null)
                {

                    monOut = new MonitorOutput(this);
                    monOut.start();
                }
            }

        }
        catch (Exception e)
        {
            logger.debug("ERROR: "+e.getMessage());
            if (logger.isDebugEnabled()) e.printStackTrace();
        }


        try
        {
            //monServ = new MonitorServer(this);
            //monServ.start();

            String sEMULATED = (String)falkonConfig.get("EMULATION");
            if (sEMULATED == null)
            {

                EMULATED = false;
            }
            else
            {
                if (sEMULATED.startsWith("true"))
                {
                    EMULATED = true;
                }
                else
                    EMULATED = false;
            }

            
            sEMULATED = (String)falkonConfig.get("EMULATION_MAX_CACHE_SIZE_MB");
            if (sEMULATED == null)
            {

                EMULATION_MAX_CACHE_SIZE_MB = 0;
            }
            else
            {
                EMULATION_MAX_CACHE_SIZE_MB = Integer.parseInt(sEMULATED);

            }


            sEMULATED = (String)falkonConfig.get("EMULATION_IDLE_TIME");
            if (sEMULATED == null)
            {

                EMULATION_IDLE_TIME = 0;
            }
            else
            {
                EMULATION_IDLE_TIME = Integer.parseInt(sEMULATED);

            }

            sEMULATED = (String)falkonConfig.get("EMULATION_IDLE_TIME_POLL");
            if (sEMULATED == null)
            {

                EMULATION_IDLE_TIME_POLL = 1000;
            }
            else
            {
                EMULATION_IDLE_TIME_POLL = Integer.parseInt(sEMULATED);

            }


            if (EMULATED)
            {


                if (monIdleWorkers == null)
                {

                    monIdleWorkers = new MonitorIdleWorkers(this);
                    monIdleWorkers.start();
                }
            }




        }
        catch (Exception e)
        {
            logger.debug("ERROR: "+e.getMessage());
            if (logger.isDebugEnabled()) e.printStackTrace();
        }









    }

    //*****************************************

    public boolean EMULATION = false;
    public boolean EMULATED = false;
    public int EMULATION_IDLE_TIME = 0;
    public int EMULATION_IDLE_TIME_POLL = 1000;
    public int EMULATION_MAX_CACHE_SIZE_MB = 1000;
    

    public void cleanupActiveTasks(String workerID)
    {
        try
        {

            //GPResource gpResource = getResource();

            //Task oldTasks[] = (Task[])activeTasksMap.remove(workerID);
            ActiveTasks oldActiveTasks = (ActiveTasks)activeTasksMap.remove(workerID);

            //if (oldActiveTasks != null && EMULATION == false)
            if (oldActiveTasks != null)
            {
                Task oldTasks[] = oldActiveTasks.oldTasks;


                if (oldTasks.length > 0)
                {
                    //insert them in results with error...
                    logger.debug("workerWork(): found tasks that have note been accounted for... inserting them into the results queue with an error exit code...");


                    GPResource gpResource = oldActiveTasks.gpResource;

                    synchronized(gpResource)
                    {

                        gpResource.setNumWorkerResults(gpResource.getNumWorkerResults() + oldTasks.length);
                    }


                    for (int ot=0;ot<oldTasks.length;ot++)
                    {
                        oldTasks[ot].setExitCode(-88888);

                        GPResourceHome.commonState.writeLogTask((oldTasks[ot].getExecutable()).getId(), "FAILED_CLEANUP_ACTIVE_" + oldTasks[ot].getExitCode());

                        gpResource.resultQ.insert(oldTasks[ot]);

                        setDoneTask();


                        logger.debug("inserting task performance metrics in hashMap...");
                        TaskPerformance tPerf = (TaskPerformance)gpResource.taskPerf.get((oldTasks[ot].getExecutable()).getId());
                        if (tPerf == null)
                        {
                            logger.debug("ERROR: TaskPerformance not found in HashMap for taskID " + (oldTasks[ot].getExecutable()).getId() + " although it should have been there... double check why not!");
                            //this should never happen
                            tPerf = new TaskPerformance();

                            tPerf.setTaskID((oldTasks[ot].getExecutable()).getId());
                            tPerf.setWorkerID(workerID);
                            tPerf.setWaitQueueTime();
                            tPerf.setWorkerQueueTime();
                        }
                        tPerf.setResultsQueueTime();

                        gpResource.taskPerf.put(tPerf.getTaskID(), tPerf);
                        logger.debug("inserted task performance metrics in hashMap!");


                        logger.debug("Inserting task in a global queue for notification thread to send it out to the user...");
                        //should be inserting just the notification info, not entire task...
                        notificationQ.insert(new NotificationTask(gpResource, oldTasks[ot]));



                    }
                    logger.debug("Added " + oldTasks.length + " erroneous tasks to the results task queue");
                }



            }
            else
            {
                //if (DEBUG)
                //{
                logger.debug("All tasks have been accounted for... nothing to clean up for worker " + workerID); 
                //}
            }

        }
        catch (Exception e)
        {
            if (logger.isDebugEnabled()) e.printStackTrace();

        }




    }



    //*****************************************



    //public synchronized String getMachName()
    public String getMachName()
    {
        return machName;

    }

    //public synchronized double getUp_Time()
    public double getUp_Time()
    {
        return Up_Time;

    }

    //public synchronized int getNum_Resources()
    public int getNum_Resources()
    {
        return Num_Resources;
    }

    //public synchronized double getAvr_Resource_Util()
    public double getAvr_Resource_Util()
    {
        return Avr_Resource_Util;
    }

    //public synchronized int getAvr_Q_Length()
    public int getAvr_Q_Length()
    {
        return Avr_Q_Length;
    }

    //public synchronized double getAvr_Resp_Time()
    public double getAvr_Resp_Time()
    {
        return Avr_Resp_Time;
    }

    //public synchronized double getAvr_Task_TP()
    public double getAvr_Task_TP()
    {
        return Avr_Task_TP;
    }

    //public synchronized int getAvr_Job_Size()
    public int getAvr_Job_Size()
    {
        return Avr_Job_Size;
    }

    //public synchronized double getAvr_Job_Time()
    public double getAvr_Job_Time()
    {
        return Avr_Job_Time;
    }

    //public synchronized double getAvr_Jobs_TP()
    public double getAvr_Jobs_TP()
    {
        return Avr_Jobs_TP;
    }

    //public synchronized int getNum_Active_Jobs()
    public int getNum_Active_Jobs()
    {
        return Num_Active_Jobs;
    }

    //public synchronized int getNum_Active_Tasks()
    public int getNum_Active_Tasks()
    {
        return Num_Active_Tasks;
    }

    //public synchronized int getNum_Completed_Jobs()
    public int getNum_Completed_Jobs()
    {
        return Num_Completed_Jobs;
    }


    //public synchronized int getNum_Completed_Tasks()
    public int getNum_Completed_Tasks()
    {
        return Num_Completed_Tasks;
    }


    //public synchronized int getNum_Failed_Jobs()
    public int getNum_Failed_Jobs()
    {
        return Num_Failed_Jobs;
    }


    //public synchronized int getNum_Failed_Tasks()
    public int getNum_Failed_Tasks()
    {
        return Num_Failed_Tasks;
    }

///////////////////////////////////////

    //not sure if these sets can be made non-synchronized... but lets try it...
    public synchronized void setUp_Time(double val)
    //public void setUp_Time(double val)
    {
        Up_Time = val;

    }

    public synchronized void setNum_Resources(int val)
    //public void setNum_Resources(int val)
    {
        Num_Resources = val;
    }

    public synchronized void setAvr_Resource_Util(double val)
    //public void setAvr_Resource_Util(double val)
    {
        Avr_Resource_Util = val;
    }

    public synchronized void setAvr_Q_Length(int val)
    //public void setAvr_Q_Length(int val)
    {
        Avr_Q_Length = val;
    }

    public synchronized void setAvr_Resp_Time(double val)
    //public void setAvr_Resp_Time(double val)
    {
        Avr_Resp_Time = val;
    }

    public synchronized void setAvr_Task_TP(double val)
    //public void setAvr_Task_TP(double val)
    {
        Avr_Task_TP = val;
    }

    public synchronized void setAvr_Job_Size(int val)
    //public void setAvr_Job_Size(int val)
    {
        Avr_Job_Size = val;
    }

    public synchronized void setAvr_Job_Time(double val)
    //public void setAvr_Job_Time(double val)
    {
        Avr_Job_Time = val;
    }

    public synchronized void setAvr_Jobs_TP(double val)
    //public void setAvr_Jobs_TP(double val)
    {
        Avr_Jobs_TP = val;
    }

    public synchronized void setNum_Active_Jobs(int val)
    //public void setNum_Active_Jobs(int val)
    {
        Num_Active_Jobs = val;
    }

    public synchronized void setNum_Active_Tasks(int val)
    //public void setNum_Active_Tasks(int val)
    {
        Num_Active_Tasks = val;
    }

    public synchronized void setNum_Completed_Jobs(int val)
    //public void setNum_Completed_Jobs(int val)
    {
        Num_Completed_Jobs = val;
    }


    public synchronized void setNum_Completed_Tasks(int val)
    //public void setNum_Completed_Tasks(int val)
    {
        Num_Completed_Tasks = val;
    }


    public synchronized void setNum_Failed_Jobs(int val)
    //public void setNum_Failed_Jobs(int val)
    {
        Num_Failed_Jobs = val;
    }


    public synchronized void setNum_Failed_Tasks(int val)
    //public void setNum_Failed_Tasks(int val)
    {
        Num_Failed_Tasks = val;
    }


    public void updateState()
    {

        /*
        data.clear();
        data.put("Up_Time", getUp_Time());
        data.put("Num_Resources", getNum_Resources());
        data.put("Avr_Resource_Util", getAvr_Resource_Util());
        data.put("Avr_Q_Length", getAvr_Q_Length());
        data.put("Avr_Resp_Time", getAvr_Resp_Time());
        data.put("Avr_Task_TP", getAvr_Task_TP());
        data.put("Avr_Job_Size", getAvr_Job_Size());
        data.put("Avr_Job_Time", getAvr_Job_Time());
        data.put("Avr_Jobs_TP", getAvr_Jobs_TP());
        data.put("Num_Active_Jobs", getNum_Active_Jobs());
        data.put("Num_Active_Tasks", getNum_Active_Tasks());
        data.put("Num_Completed_Jobs", getNum_Completed_Jobs());
        data.put("Num_Completed_Tasks", getNum_Completed_Tasks());
          */

    }

    public void updateScreen()
    {
        if (getNum_Active_Jobs() > 0 || getNum_Active_Tasks() > 0 || getNum_Resources() > 0 || getAvr_Q_Length() > 0 || isFirstTime == true)
        {

            System.out.print("\033[2J\033[H");
            System.out.println("#########################################################");
            System.out.println("#########################################################");
            System.out.println("####                                                     ");
            System.out.println("#### GenericPortal Web Service Monitor -- Tasking Service ");
            System.out.println("####                                                     ");
            System.out.println("#########################################################");
            System.out.println("#### GenericPortal Location          ## " + getMachName());
            System.out.println("#### UP Time (seconds)             ## " + getUp_Time());
            System.out.println("#### Number of Resources           ## " + getNum_Resources());
            //System.out.println("#### Average Resource Utilization  ## " + getAvr_Resource_Util());
            System.out.println("#### Number of Queued Tasks    ## " + getAvr_Q_Length());
            System.out.println("#### Average Tasking Time (ms)    ## " + getAvr_Resp_Time());
            System.out.println("#### Average Tasks / second    ## " + getNum_Completed_Tasks()*1.0 / getUp_Time());
            System.out.println("#### Average Job Size              ## " + getAvr_Job_Size());
            System.out.println("#### Average Job Time              ## " + getAvr_Job_Time());
            System.out.println("#### Average Jobs / minute         ## " + getNum_Completed_Jobs()*1.0 / (getUp_Time()/60.0));
            System.out.println("#### Number of Active Jobs         ## " + getNum_Active_Jobs());
            System.out.println("#### Number of Active Tasks    ## " + getNum_Active_Tasks());
            System.out.println("#### Number of Completed Jobs      ## " + getNum_Completed_Jobs());
            System.out.println("#### Number of Failed Jobs         ## " + getNum_Failed_Jobs());
            System.out.println("#### Number of Completed Tasks ## " + getNum_Completed_Tasks());
            System.out.println("#### Number of Failed Tasks    ## " + getNum_Failed_Tasks());
            System.out.println("#########################################################");
            System.out.println("#########################################################");

            isFirstTime = false;
        }



    }


    public void writeLog(Logger loggerPerf)
    //public synchronized void writeLog(Logger loggerPerf)
    {

        if (writeLogTest)
        {


            quanta_min = (getUp_Time() - cur_Up_Time)*1.0 / 60.0;

            all_Num_Completed_Jobs+=getNum_Completed_Jobs();
            all_Num_Failed_Jobs+=getNum_Failed_Jobs();
            all_Num_Completed_Tasks+=getNum_Completed_Tasks();
            all_Num_Failed_Tasks+=getNum_Failed_Tasks();

            //loggerPerf.info(" GP_location " + getMachName() + " UPTime_min " + getUp_Time()*1.0/60.0 + " Resources_num " + getNum_Resources() + " Resc_util " + getAvr_Resource_Util() + " Task_q " + getAvr_Q_Length() + " Task_time_ms " + getAvr_Resp_Time() + " Task_tp_per_min " + getNum_Completed_Tasks()*1.0 / (getUp_Time()/60.0) + " JOB_size " + getAvr_Job_Size() + " Job_time_sec " + getAvr_Job_Time() + " TP_job_per_min " + getNum_Completed_Jobs()*1.0 / (getUp_Time()/60.0) + " Jobs_active " + getNum_Active_Jobs() + " Task_active " + getNum_Active_Tasks() + " Jobs_complete " + getNum_Completed_Jobs() + " Jobs_failed " + getNum_Failed_Jobs() + " Task_complete " + getNum_Completed_Tasks() + " Task_failed " + getNum_Failed_Tasks() + " ");
            loggerPerf.info(" GP_location " + getMachName() + " UPTime_min " + getUp_Time()*1.0/60.0 + " quanta_min " + quanta_min + " Resources_num " + getNum_Resources() + " Resc_util " + getAvr_Resource_Util() + " Task_q " + getAvr_Q_Length() + " Task_time_ms " + getAvr_Resp_Time() + " Task_tp_per_min " + getNum_Completed_Tasks()*1.0 / quanta_min + " JOB_size " + getAvr_Job_Size() + " Job_time_sec " + getAvr_Job_Time() + " TP_job_per_min " + getNum_Completed_Jobs()*1.0 / quanta_min + " Jobs_active " + getNum_Active_Jobs() + " Task_active " + getNum_Active_Tasks() + " Jobs_complete " + getNum_Completed_Jobs() + " Jobs_failed " + getNum_Failed_Jobs() + " Task_complete " + getNum_Completed_Tasks() + " Task_failed " + getNum_Failed_Tasks() + " all_Jobs_complete " + all_Num_Completed_Jobs + " all_Task_complete " + all_Num_Completed_Tasks + " all_Jobs_failed " + all_Num_Failed_Jobs + " all_Task_failed " + all_Num_Failed_Tasks + " ");

            cur_Up_Time = getUp_Time();


            setAvr_Job_Time(0);
            setAvr_Resp_Time(0);
            setNum_Completed_Jobs(0);
            setNum_Failed_Jobs(0);
            setNum_Completed_Tasks(0);
            setNum_Failed_Tasks(0);
            setAvr_Job_Size(0);
        }


    }                             

    public static double lastTimeSec = 0;
    public static double lastDelvTasks = 0;
    public static long startTime = System.currentTimeMillis();

    public StopWatch timeSinceLastFlush = null;

    //public synchronized void writeLogPerf()
    public void writeLogPerf()
    {

        int ti = -1;
        if (writeLogPerfTest)
        {


            double curTimeSec;// = 0;// = (System.currentTimeMillis() - startTime)*1.0/1000.0;
            double curDelvTasks;// = 0;// = getDeliveredTasks();

            try
            {
                if (loggerPerfFile == null)
                {
                    if (timeSinceLastFlush == null)
                    {
                        timeSinceLastFlush = new StopWatch();
                    }
                    timeSinceLastFlush.start();

                    Properties props = System.getProperties();
                    String falkonServiceHome = (String)props.get("FALKON_LOGS");

                    String loggerPerfFileName = (String)falkonConfig.get("GenericPortalWS_perf_per_sec");
                    if (loggerPerfFileName == null)
                    {

                        loggerPerfFileName = new String("/dev/null");
                        writeLogPerfTest = false;
                    }
                    else
                    {

                        loggerPerfFileName = new String(falkonServiceHome + "/" + loggerPerfFileName);
                    }

                    /*
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
                    sr.setCacheHits(GPResourceHome.commonState.getCacheHits());
                    sr.setCacheMisses(GPResourceHome.commonState.getCacheMisses());
                    sr.setSystemNumThreads(Thread.activeCount());
                    sr.setSystemCPUuser(GPResourceHome.commonState.cpuInfo.user);
                    sr.setSystemCPUsystem(GPResourceHome.commonState.cpuInfo.system);
                    sr.setSystemCPUidle(GPResourceHome.commonState.cpuInfo.idle);
                    sr.setSystemHeapSize((int)Math.ceil(Runtime.getRuntime().totalMemory()/(1024*1024)));
                    sr.setSystemHeapFree((int)Math.ceil(Runtime.getRuntime().freeMemory()/(1024*1024)));
                    sr.setSystemHeapMax((int)Math.ceil(Runtime.getRuntime().maxMemory()/(1024*1024)));
                    */
                    //
                    //

                    loggerPerfFile = new BufferedWriter(new FileWriter(loggerPerfFileName));
                    loggerPerfFile.write("//Time_ms num_users num_resources num_threads num_all_workers num_free_workers num_pend_workers num_busy_workers waitQ_length waitNotQ_length activeQ_length doneQ_length delivered_tasks throughput_tasks/sec success_tasks failed_tasks retried_tasks resourceAllocated submittedTasks cacheSize cacheLocalHits cacheGlobalHits cacheMisses cacheLocalHitRatio cacheGlobalHitRatio systemCPUuser systemCPUsystem systemCPUidle systemHeapSize systemHeapFree systemHeapMax\n");
                    //extra stuff
                    //loggerPerfFile.write("roundRobin freeWorkers pendWorkers busyWorkers notificationQ notificationWorkerQ cacheGridIndex data userMap resourceSet falkonConfig activeTasksMap resourceQ erroredTasksStats erroredExecutorsStats pendWorkerTime\n");

                    startTime = System.currentTimeMillis();
//<<<<<<< .mine
//                    //added for Ben
                    loggerPerfFile.write("//0-time is "+startTime+"ms\n");
//=======
//                    loggerPerfFile.write("//0-time is"+startTime+"ms\n");
//>>>>>>> .r1331
                    curTimeSec = (System.currentTimeMillis() - startTime)*1.0/1000.0; 
                    curDelvTasks = getDeliveredTasks();

                    lastTimeSec = curTimeSec - 0.001;
                    lastDelvTasks = curDelvTasks;
                }
                else
                {

                    curTimeSec = (System.currentTimeMillis() - startTime)*1.0/1000.0; 
                    curDelvTasks = getDeliveredTasks();
                }


                //loggerPerfFile.write(curTimeSec + " " + userMap.size() + " " + resourceSet.size() + " " + Thread.activeCount() + " " + roundRobin.size() + " " + freeWorkers.size() + " " + pendWorkers.size() + " " + busyWorkers.size() + " " + (int)getWaitingTasks() + " " + (int)getWorkerNotificationQueueSize() + " " +(int)getActiveTasks() + " " + (int)getDoneTasks() + " " + (int)curDelvTasks + " " + Math.round(((curDelvTasks - lastDelvTasks)*1.0/(curTimeSec - lastTimeSec))*10)/10.0 + " " + getTasksSuccessDone() + " " + getTasksFailedDone() + " " + getTasksFailedRetry() + " " + (int)resourceAllocated + " " + (int)getSubmittedTasks() + " " + cacheGridIndex.size() + " " + getCacheHits() + " " + getCacheGlobalHits() + " " + getCacheMisses() + " " + Math.round(((getCacheHits())*1.0/(getCacheHits() + getCacheGlobalHits() + getCacheMisses()))*100)/100.0 + " " + Math.round(((getCacheHits()+getCacheGlobalHits())*1.0/(getCacheHits() + getCacheGlobalHits() + getCacheMisses()))*100)/100.0 + " " + cpuInfo.user + " " + cpuInfo.system + " " + cpuInfo.idle + " " + (int)Math.ceil(Runtime.getRuntime().totalMemory()/(1024*1024)) + " " + (int)Math.ceil(Runtime.getRuntime().freeMemory()/(1024*1024)) + " " + (int)Math.ceil(Runtime.getRuntime().maxMemory()/(1024*1024)) +"\n");
                loggerPerfFile.write(curTimeSec + " " + userMap.size() + " " + resourceSet.size() + " " + Thread.activeCount() + " " + roundRobin.size() + " " + freeWorkers.size() + " " + pendWorkers.size() + " " + busyWorkers.size() + " " + (int)getWaitingTasks() + " " + (int)getWorkerNotificationQueueSize() + " " +(int)getActiveTasks() + " " + (int)getDoneTasks() + " " + (int)curDelvTasks + " " + Math.round(((curDelvTasks - lastDelvTasks)*1.0/(curTimeSec - lastTimeSec))*10)/10.0 + " " + getTasksSuccessDone() + " " + getTasksFailedDone() + " " + getTasksFailedRetry() + " " + (int)resourceAllocated + " " + (int)getSubmittedTasks() + " " + cacheGridIndex.size() + " " + getCacheHits() + " " + getCacheGlobalHits() + " " + getCacheMisses() + " " + Math.round(((getCacheHits())*1.0/(getCacheHits() + getCacheGlobalHits() + getCacheMisses()))*100)/100.0 + " " + Math.round(((getCacheHits()+getCacheGlobalHits())*1.0/(getCacheHits() + getCacheGlobalHits() + getCacheMisses()))*100)/100.0 + " " + cpuInfo.user + " " + cpuInfo.system + " " + cpuInfo.idle + " " + (int)Math.ceil(Runtime.getRuntime().totalMemory()/(1024*1024)) + " " + (int)Math.ceil(Runtime.getRuntime().freeMemory()/(1024*1024)) + " " + (int)Math.ceil(Runtime.getRuntime().maxMemory()/(1024*1024)) +"\n");
                //extra stuff...


                /*
                if (roundRobin != null)
                {
                    ti = roundRobin.size();
                    loggerPerfFile.write(ti + " ");
                }
                else 
                    loggerPerfFile.write("null "); 

                if (freeWorkers != null)
                {
                    ti = freeWorkers.size();
                    loggerPerfFile.write(ti + " ");
                }
                else 
                    loggerPerfFile.write("null "); 

                if (pendWorkers != null)
                {
                    ti = pendWorkers.size();
                    loggerPerfFile.write(ti + " ");
                }
                else 
                    loggerPerfFile.write("null "); 

                if (busyWorkers != null)
                {
                    ti = busyWorkers.size();
                    loggerPerfFile.write(ti + " ");
                }
                else 
                    loggerPerfFile.write("null "); 

                if (notificationQ != null)
                {
                    ti = notificationQ.size();
                    loggerPerfFile.write(ti + " ");
                }
                else 
                    loggerPerfFile.write("null "); 

                if (notificationWorkerQ != null)
                {
                    ti = notificationWorkerQ.size();
                    loggerPerfFile.write(ti + " ");
                }
                else 
                    loggerPerfFile.write("null "); 

                if (cacheGridIndex != null)
                {
                    ti = cacheGridIndex.size();
                    loggerPerfFile.write(ti + " ");
                }
                else 
                    loggerPerfFile.write("null "); 

                if (data != null)
                {
                    ti = data.size();
                    loggerPerfFile.write(ti + " ");
                }
                else 
                    loggerPerfFile.write("null "); 

                if (userMap != null)
                {
                    ti = userMap.size();
                    loggerPerfFile.write(ti + " ");
                }
                else 
                    loggerPerfFile.write("null "); 

                if (resourceSet != null)
                {
                    ti = resourceSet.size();
                    loggerPerfFile.write(ti + " ");
                }
                else 
                    loggerPerfFile.write("null "); 

                if (falkonConfig != null)
                {
                    ti = falkonConfig.size();
                    loggerPerfFile.write(ti + " ");
                }
                else 
                    loggerPerfFile.write("null "); 

                if (activeTasksMap != null)
                {
                    ti = activeTasksMap.size();
                    loggerPerfFile.write(ti + " ");
                }
                else 
                    loggerPerfFile.write("null "); 

                if (resourceQ != null)
                {
                    ti = resourceQ.size();
                    loggerPerfFile.write(ti + " ");
                }
                else 
                    loggerPerfFile.write("null "); 

                if (erroredTasksStats != null)
                {
                    ti = erroredTasksStats.size();
                    loggerPerfFile.write(ti + " ");
                }
                else 
                    loggerPerfFile.write("null "); 

                if (erroredExecutorsStats != null)
                {
                    ti = erroredExecutorsStats.size();
                    loggerPerfFile.write(ti + " ");
                }
                else 
                    loggerPerfFile.write("null "); 

                if (pendWorkerTime != null)
                {
                    ti = pendWorkerTime.size();
                    loggerPerfFile.write(ti + " ");
                }
                else 
                    loggerPerfFile.write("null "); 

                loggerPerfFile.write("\n");

                  */



                if (GPResourceHome.perfProfile.PROFILE && GPResourceHome.perfProfile.captureCPU)
                {

                    GPResourceHome.perfProfile.addSample("x_cpu_user", cpuInfo.user*1.0/100.0);
                    GPResourceHome.perfProfile.addSample("x_cpu_system", cpuInfo.system*1.0/100.0);
                    GPResourceHome.perfProfile.addSample("x_cpu_idle", cpuInfo.idle*1.0/100.0);
                }

                if (timeSinceLastFlush == null)
                {
                    timeSinceLastFlush = new StopWatch();
                    timeSinceLastFlush.start();

                }

                //this should be higher for BG/P
                if (timeSinceLastFlush.getElapsedTime() >= 1000)
                {

                    loggerPerfFile.flush();
                    if (outTaskPerf != null)
                    {
                        outTaskPerf.flush();
                    }

                    timeSinceLastFlush.reset();
                    timeSinceLastFlush.start();
                }

                lastTimeSec = curTimeSec;
                lastDelvTasks = curDelvTasks;

                //outWorker.close();

                //System.gc();
            }
            catch (IOException e)
            {
                logger.debug("Error in writeLog() when trying to log message: " + e);
                if (logger.isDebugEnabled()) e.printStackTrace();
            }
        }

    }



    public void printState()
    {

        System.out.println("Up_Time=" + getUp_Time());
        System.out.println("Num_Resources="+ getNum_Resources());
        System.out.println("Avr_Resource_Util="+ getAvr_Resource_Util());
        System.out.println("Avr_Q_Length="+ getAvr_Q_Length());
        System.out.println("Avr_Resp_Time="+ getAvr_Resp_Time());
        System.out.println("Avr_Task_TP="+ getAvr_Task_TP());
        System.out.println("Avr_Job_Size="+ getAvr_Job_Size());
        System.out.println("Avr_Job_Time="+ getAvr_Job_Time());
        System.out.println("Avr_Jobs_TP="+ getAvr_Jobs_TP());
        System.out.println("Num_Active_Jobs="+ getNum_Active_Jobs());
        System.out.println("Num_Active_Tasks="+ getNum_Active_Tasks());
        System.out.println("Num_Completed_Jobs="+ getNum_Completed_Jobs());
        System.out.println("Num_Completed_Tasks="+ getNum_Completed_Tasks());


    }


    public void writeLogWorker()
    //public synchronized void writeLogWorker()
    {

        if (writeLogWorkerTest)
        {

            try
            {
                if (outWorker == null)
                {

                    Properties props = System.getProperties();
                    String falkonServiceHome = (String)props.get("FALKON_LOGS");

                    String outWorkerFileName = (String)falkonConfig.get("GenericPortalWS_workers");
                    if (outWorkerFileName == null)
                    {

                        outWorkerFileName = new String("/dev/null");
                        writeLogWorkerTest = false;
                    }
                    else
                    {
                        outWorkerFileName = new String(falkonServiceHome + "/" + outWorkerFileName);

                    }


                    outWorker = new BufferedWriter(new FileWriter(outWorkerFileName));
                    if (writeLogWorkerTestComplex)
                    {
                        outWorker.write("//Time_ms RegisteredWorkers FreeWorkers PendWorkers BusyWorkers FREE[] PEND[] BUSY[]\n");
                    }
                    else
                        outWorker.write("//Time_ms RegisteredWorkers FreeWorkers PendWorkers BusyWorkers\n");
                }

                if (writeLogWorkerTestComplex)
                {
                    outWorker.write(System.currentTimeMillis() + " " + roundRobin.size() + " " + freeWorkers.size() + " " + pendWorkers.size() + " " + busyWorkers.size() + " " + freeWorkers.toString() + " " + pendWorkers.toString() + " " + busyWorkers.toString() + "\n");

                }
                else
                    outWorker.write(System.currentTimeMillis() + " " + roundRobin.size() + " " + freeWorkers.size() + " " + pendWorkers.size() + " " + busyWorkers.size() + "\n");

                outWorker.flush();

                //outWorker.close();
            }
            catch (IOException e)
            {
                logger.debug("Error in writeLogWorker() when trying to log message: " + e);
                if (logger.isDebugEnabled()) e.printStackTrace();
            }
        }

    }

    public void writeLogUser()
    //public synchronized void writeLogUser()
    {

        if (writeLogUserTest)
        {
            try
            {
                if (outUser == null)
                {

                    Properties props = System.getProperties();
                    String falkonServiceHome = (String)props.get("FALKON_LOGS");

                    String outUserFileName = (String)falkonConfig.get("GenericPortalWS_user");
                    if (outUserFileName == null)
                    {

                        outUserFileName = new String("/dev/null");
                        writeLogUserTest = false;
                    }
                    else
                    {

                        outUserFileName = new String(falkonServiceHome + "/" + outUserFileName);
                    }



                    outUser = new BufferedWriter(new FileWriter(outUserFileName));
                    outUser.write("//Time_ms Users Resources JVM_Threads WaitingTasks ActiveTasks DoneTasks DeliveredTasks\n");
                }

                outUser.write(System.currentTimeMillis() + " " + userMap.size() + " " + resourceSet.size() + " " + Thread.activeCount() + " " + getWaitingTasks() + " " + getActiveTasks() + " " + getDoneTasks() + " " + getDeliveredTasks() + "\n");
                outUser.flush();

                //outWorker.close();
            }
            catch (IOException e)
            {
                logger.debug("Error in writeLogUser() when trying to log message: " + e);
                if (logger.isDebugEnabled()) e.printStackTrace();
            }
        }

    }

    //public long taskPerfStartTime = 0;
    private long taskPerfCounter = 0;

    public synchronized long getNextTaskPerfCounter()
    {
        taskPerfCounter++;
        return taskPerfCounter;

    }

    public void writeLogTaskPerf(TaskPerformance tPerf)
    //public synchronized void writeLogTaskPerf(TaskPerformance tPerf)
    {

        if (writeLogTaskPerfTest)
        {


            try
            {
                if (outTaskPerf == null)
                {

                    Properties props = System.getProperties();
                    String falkonServiceHome = (String)props.get("FALKON_LOGS");

                    String outTaskPerfFileName = (String)falkonConfig.get("GenericPortalWS_taskPerf");
                    if (outTaskPerfFileName == null)
                    {

                        outTaskPerfFileName = new String("/dev/null");
                        writeLogTaskPerfTest = false;
                    }
                    else
                    {

                        outTaskPerfFileName = new String(falkonServiceHome + "/" + outTaskPerfFileName);
                    }

                    //taskPerfStartTime = System.currentTimeMillis() - tPerf.getTotalTime();

                    outTaskPerf = new BufferedWriter(new FileWriter(outTaskPerfFileName));
                    outTaskPerf.write("//taskNum taskID workerID startTimeStamp execTimeStamp resultsQueueTimeStamp endTimeStamp waitQueueTime execTime resultsQueueTime totalTime exitCode\n");
//<<<<<<< .mine

                    //added for Ben
                    outTaskPerf.write("//0-time is "+taskPerfStartTime+"ms\n");
//=======
//                    outTaskPerf.write("//0-time is"+taskPerfStartTime+"ms\n");
//>>>>>>> .r1331
                }


                outTaskPerf.write(getNextTaskPerfCounter() + " " + tPerf.toString(taskPerfStartTime) + "\n");
                //outTaskPerf.flush();



                //outWorker.close();
            }
            catch (IOException e)
            {
                logger.debug("Error in writeLogTaskPerf() when trying to log message: " + e);
                if (logger.isDebugEnabled()) e.printStackTrace();
            }
        }

    }

    //public synchronized void writeLogTask(String taskID, String status)
    public void writeLogTask(String taskID, String status)
    {

        if (writeLogTaskTest)
        {


            try
            {
                if (outTask == null)
                {

                    Properties props = System.getProperties();
                    String falkonServiceHome = (String)props.get("FALKON_LOGS");

                    String outTaskFileName = (String)falkonConfig.get("GenericPortalWS_task");
                    if (outTaskFileName == null)
                    {

                        outTaskFileName = new String("/dev/null");
                        writeLogTaskTest = false;
                    }
                    else
                    {

                        outTaskFileName = new String(falkonServiceHome + "/" + outTaskFileName);
                    }

                    //taskPerfStartTime = System.currentTimeMillis() - tPerf.getTotalTime();

                    outTask = new BufferedWriter(new FileWriter(outTaskFileName));
                    outTask.write("//time_ms taskID state\n");
                }


                outTask.write(System.currentTimeMillis() + " " + taskID + " " + status + "\n");
                outTask.flush();



                //outWorker.close();
            }
            catch (IOException e)
            {
                logger.debug("Error in writeLogTask() when trying to log message: " + e);
                if (logger.isDebugEnabled()) e.printStackTrace();
            }
        }

    }



    //can the synchronized be taken out?
    public synchronized boolean setFreeWorker(String workerID)
    {

        logger.debug("*** trying setFreeWorker(" + workerID + ")");
        boolean isBusy = busyWorkers.remove(workerID);
        if (isBusy)
        {

            logger.debug("*** setFreeWorker1(" + workerID + ") success");
            freeWorkers.insert(workerID);
            writeLogWorker();
            //write to file change
        }
        else
        {
            isBusy = pendWorkers.remove(workerID);
            if (isBusy)
            {

                logger.debug("*** setFreeWorker2(" + workerID + ") success");
                freeWorkers.insert(workerID);

                pendWorkerTime.remove(workerID);


                writeLogWorker();
                //write to file change
            }
            else
            {

                boolean isFree = freeWorkers.exists(workerID);

                if (isFree)
                {
                    isBusy = true;
                }
                else
                {
                    //logger.debug("*** setFreeWorker(" + workerID + ") failed");
                    logger.debug("*** setFreeWorker(" + workerID + ") failed: roundRobin.toString(): " + roundRobin.toString());
                    logger.debug("*** setFreeWorker(" + workerID + ") failed: freeWorkers.toString(): " + freeWorkers.toString());
                    logger.debug("*** setFreeWorker(" + workerID + ") failed: pendWorkers.toString(): " + pendWorkers.toString());
                    logger.debug("*** setFreeWorker(" + workerID + ") failed: busyWorkers.toString(): " + busyWorkers.toString());
                }

            }

        }

        return isBusy;

    }

    //fix this!!!

    //can the synchronized be taken out?
    public synchronized boolean setBusyWorker(String workerID)
    {

        logger.debug("*** trying setBusyWorker(" + workerID + ")");
        boolean isPend = pendWorkers.remove(workerID);
        if (isPend)
        {

            logger.debug("*** setBusyWorker1(" + workerID + ") success");
            busyWorkers.insert(workerID);
            writeLogWorker();
            //write to file change
        }
        else
        {
            //this should never happen
            isPend = freeWorkers.remove(workerID);
            if (isPend)
            {
                logger.debug("*** setBusyWorker2(" + workerID + ") success");
                busyWorkers.insert(workerID);
                writeLogWorker();
                //write to file change
            }
            else
            {

                boolean isBusy = busyWorkers.exists(workerID);

                if (isBusy)
                {
                    isPend = true;
                }
                else
                {
                    //logger.debug("*** setBusyWorker(" + workerID + ") failed");

                    logger.debug("*** setBusyWorker(" + workerID + ") failed: roundRobin.toString(): " + roundRobin.toString());
                    logger.debug("*** setBusyWorker(" + workerID + ") failed: freeWorkers.toString(): " + freeWorkers.toString());
                    logger.debug("*** setBusyWorker(" + workerID + ") failed: pendWorkers.toString(): " + pendWorkers.toString());
                    logger.debug("*** setBusyWorker(" + workerID + ") failed: busyWorkers.toString(): " + busyWorkers.toString());
                }
            }
        }

        return isPend;
    }

    public synchronized String[] getPendWorkers()
    {
        return pendWorkers.toArray();
    }

    //this should be configurable from the config file...
    public int maxNumErrorsPerTask = 10;
    public Map erroredTasksStats = Collections.synchronizedMap(new HashMap());    // hash table
    public int maxNumErrorsPerExecutor = 3;
    public Map erroredExecutorsStats = Collections.synchronizedMap(new HashMap());    // hash table


    public Map pendWorkerTime = Collections.synchronizedMap(new HashMap());    // hash table

    //can the synchronized be taken out?
    public synchronized boolean setPendWorker(String workerID)
    {
        final String fWorkerID = new String(workerID);

        logger.debug("*** trying setPendWorker(" + workerID + ")");

        boolean isFree = freeWorkers.remove(workerID);

        if (!isFree)
            isFree = busyWorkers.remove(workerID);

        if (isFree)
        {
            logger.debug("*** setPendWorker(" + workerID + ") success");

            pendWorkers.insert(workerID);
            writeLogWorker();
            //write to file change

            pendWorkerTime.put(fWorkerID, new Long(System.currentTimeMillis()));
            return true;

        }
        else
        {
            boolean isPend = pendWorkers.exists(workerID);

            if (isPend)
            {
                //isFree = true;
                return true;
            }
            else
            {


                //logger.debug("*** setPendWorker(" + workerID + ") failed");

                logger.debug("*** setPendWorker(" + workerID + ") failed: roundRobin.toString(): " + roundRobin.toString());
                logger.debug("*** setPendWorker(" + workerID + ") failed: freeWorkers.toString(): " + freeWorkers.toString());
                logger.debug("*** setPendWorker(" + workerID + ") failed: pendWorkers.toString(): " + pendWorkers.toString());
                logger.debug("*** setPendWorker(" + workerID + ") failed: busyWorkers.toString(): " + busyWorkers.toString());

                return false;
            }
        }

        //final int numberOfMillisecondsInTheFuture = 60000; // 10 sec
        //Date timeToRun = new Date(System.currentTimeMillis()+numberOfMillisecondsInTheFuture);
        //Timer timer = new Timer();

        //timer.schedule(new TimerTask()
        //              {
        //                   public void run()
        //                   {
        //need a timestampt to figure this out...
        //                       Long pwTime = (Long)pendWorkerTime.get(fWorkerID);
        //                       if (pwTime == null)
        //                       {
        //                           setFreeWorker(fWorkerID);
        //pendWorkers.remove(workerID);
        //                       } else
        //                       {
        //                           if (pwTime.longValue() + numberOfMillisecondsInTheFuture < System.currentTimeMillis())
        //                          {
        //                               setFreeWorker(fWorkerID);
        //pendWorkers.remove(workerID);

        //                           }
        //                       }

        //                       // Task here ...
        //                   }
        //               }, timeToRun);




        //return isFree;
    }



    public String getNextWorker() //throws
    {
        String next = null;
        synchronized(roundRobin)
        {

            if (roundRobin.size() > 0)
            {
                try
                {

                    next = (String)roundRobin.remove();
                    roundRobin.insert(next);
                }

                catch (Exception e)
                {
                    logger.debug("Error in getting next worker... possible a race condition, or maybe there were just no workers registered, and the logic was wrong...");
                    return null;
                    //throw new Exception("Error in getting next worker... possible a race condition, or maybe there were just no workers registered, and the logic was wrong...", e);
                }
            }
        }
        return next; //could be null if there were no workers registered
    }


    public String getNextFreeBestWorker(Task task)
    {
        //get list of files for this task...
        if ( (task.getExecutable()).getInputData() != null && ((task.getExecutable()).getInputData()).getLogicalName() != null && (((task.getExecutable()).getInputData()).getLogicalName()).length > 0)
        //if (task.getNumInputDataFiles() > 0)
        {

            //String fileList[] = task.getInputDataFiles();
            String fileList[] = ((task.getExecutable()).getInputData()).getLogicalName();


            Map tMap = new HashMap(fileList.length*10);

            int cacheHitMax = -1;
            String cacheHitMaxExecutor = null;

            for (int i=0;i<fileList.length;i++)
            {
                Set tResourceSet = cacheGridIndex.getIndexElementLocal(fileList[i]);
                Set resourceSet = null;
                if (tResourceSet == null)
                {
                    resourceSet = new TreeSet();
                }
                else
                    resourceSet = new TreeSet(tResourceSet);

                resourceSet.retainAll(freeWorkers.queue);

                // For a set or list

                for (Iterator it=resourceSet.iterator(); it.hasNext(); )
                {
                    String element = (String)it.next();
                    Integer tCount = (Integer)tMap.get(element);
                    if (tCount == null)
                    {
                        tMap.put(element, new Integer(1));
                        if (1 > cacheHitMax)
                        {
                            if (freeWorkers.removeNoWait(element))
                            {
                                if (cacheHitMaxExecutor != null)
                                {
                                    freeWorkers.insert(cacheHitMaxExecutor);

                                }


                                cacheHitMax = 1;
                                cacheHitMaxExecutor = element;
                            }
                        }
                    }
                    else
                    {

                        tMap.put(element, new Integer(tCount.intValue() + 1));
                        if (tCount.intValue() + 1 > cacheHitMax)
                        {
                            if (freeWorkers.removeNoWait(element))
                            {

                                if (cacheHitMaxExecutor != null)
                                {
                                    freeWorkers.insert(cacheHitMaxExecutor);

                                }
                                cacheHitMax = tCount.intValue() + 1;
                                cacheHitMaxExecutor = element;
                            }
                        }
                    }


                }
            }

            if (cacheHitMaxExecutor != null)
            {
                pendWorkers.insert(cacheHitMaxExecutor);
            }


            try
            {

                writeLogWorker();
            }
            catch (Exception logE)
            {
                if (logger.isDebugEnabled()) logE.printStackTrace();
            }

            return cacheHitMaxExecutor;


        }
        else
        {
            return getNextFreeWorker();

        }
    }

    /*
    public Random rand = new Random();

    public String getRandomWorker(Set resourceSet)
    {
        int n = 10;
        int index = rand.nextInt(resourceSet.size());

        int i=0;

        // For a set or list
        String first = null;
        String last = null;

                        for (Iterator it=resourceSet.iterator(); it.hasNext();i++ ) 
                        {
                            if (i==0)
                            {
                                first = (String)it.next();

                            }

                            if (i != 0 && i == index)
                            {
                                last = (String)it.next();
                            }
                            
                        }


                        if (last != null)
                        {
                            return last;

                        }
                        if (first != null)
                        {

                            return first;
                        }
                        else
                            return null;
    }
    */

    public CacheHitMiss getCacheHit(Task task, String executor)
    {
        CacheHitMiss cacheHitMiss = new CacheHitMiss();
        //get list of files for this task...
        //if ((((task.getExecutable()).getInputData()).getLogicalName()).length > 0)

        try
        {

            if ( (task.getExecutable()).getInputData() != null && ((task.getExecutable()).getInputData()).getLogicalName() != null && (((task.getExecutable()).getInputData()).getLogicalName()).length > 0)
            {

                String fileList[] = ((task.getExecutable()).getInputData()).getLogicalName();
                int fileListSize[] = ((task.getExecutable()).getInputData()).getFileSize();


                //Map tMap = new HashMap(fileList.length*10);


                //String cacheHitMaxExecutor = null;

                for (int i=0;i<fileList.length;i++)
                {
                    Set resourceSet = cacheGridIndex.getIndexElementLocal(fileList[i]);

                    //resourceSet.retainAll(freeWorkers.queue);

                    // For a set or list

                    if (resourceSet != null && resourceSet.contains(executor))
                    {

                        cacheHitMiss.localHit++;
                        cacheHitMiss.localHitKB += fileListSize[i];
                    }
                    else if (resourceSet != null && !resourceSet.isEmpty())
                    {
                        //should double check that the list of workers also contains at least 1 active worker...
                        //should iterate over all elements of resourceSet, and make sure they are still registered!
                        // For a set or list
                        //for (Iterator it=resourceSet.iterator(); it.hasNext(); ) {
                        //    String element = (String)it.next();
                        //}


                        resourceSet.retainAll(roundRobin.queue);
                        if (!resourceSet.isEmpty())
                        {
                            cacheHitMiss.globalHit++;
                            cacheHitMiss.globalHitKB += fileListSize[i];
                            //cacheHitMiss.globalHitMachID = getRandomWorker(resourceSet);
                        }
                        else
                        {

                            cacheHitMiss.miss++;   
                            cacheHitMiss.missKB += fileListSize[i];
                        }

                    }
                    else
                    {
                        cacheHitMiss.miss++;   
                        cacheHitMiss.missKB += fileListSize[i];

                    }


                }


                if (fileList.length != (cacheHitMiss.localHit+cacheHitMiss.globalHit+cacheHitMiss.miss)  )
                {
                    logger.info("***** too many cache hit/misses, should have summed to " + fileList.length + " but there were " + (cacheHitMiss.localHit+cacheHitMiss.globalHit+cacheHitMiss.miss) + " cache hit/misses...");

                }

                //logger.info("****** number of files " + fileList.length + " ; cacheHitMiss.localHit = " + cacheHitMiss.localHit+ " cacheHitMiss.globalHit = " + cacheHitMiss.globalHit+ " cacheHitMiss.miss = " + cacheHitMiss.miss);


            }
        }
        catch (Exception e)
        {
            logger.debug("error in getCacheHit(): " + e.getMessage());

        }


        return cacheHitMiss;
    }

    public String getNextFreeWorkerOld() //throws
    {
        String next = null;



        while (true)
        {

            synchronized(freeWorkers)
            {

                if (freeWorkers.size() > 0)
                {
                    try
                    {
                        //logger.debug("getNextFreeWorker(): waiting for free worker...");
                        //next = (String)freeWorkers.remove();
                        //logger.debug("getNextFreeWorker(): free worker found at " + next);
                        //freeWorkers.insert(next);

                        //this.commonState.setPendWorker(next);


                        logger.debug("*** trying setPendWorker(any)");
                        next = (String)freeWorkers.remove();
                        if (next != null)
                        {
                            logger.debug("*** setPendWorker(" + next + ") success");
                            pendWorkers.insert(next);
                            writeLogWorker();
                            //write to file change

                            final String fNext = new String(next);
                            pendWorkerTime.put(fNext, new Long(System.currentTimeMillis()));

                            /*
                            final int numberOfMillisecondsInTheFuture = 60000; // 10 sec
                            Date timeToRun = new Date(System.currentTimeMillis()+numberOfMillisecondsInTheFuture);
                            Timer timer = new Timer();

                            timer.schedule(new TimerTask()
                                           {
                                               public void run()
                                               {
                                                   //need a timestampt to figure this out...
                                                   Long pwTime = (Long)pendWorkerTime.get(fNext);
                                                   if (pwTime == null)
                                                   {
                                                       setFreeWorker(fNext);
                                                       //pendWorkers.remove(workerID);
                                                   } else
                                                   {
                                                       if (pwTime.longValue() + numberOfMillisecondsInTheFuture < System.currentTimeMillis())
                                                       {
                                                           setFreeWorker(fNext);
                                                           //pendWorkers.remove(workerID);

                                                       }
                                                   }

                                                   // Task here ...
                                               }
                                           }, timeToRun);
                              */

                            return next;
                        }
                        else
                        {
                            logger.debug("*** setPendWorker(null) failed");
                            //return null;
                        }

                    }

                    catch (Exception e)
                    {
                        logger.debug("getNextFreeWorker(): Error in getting next free worker... possible a race condition, or maybe there were just no workers registered, and the logic was wrong...");
                        if (logger.isDebugEnabled()) e.printStackTrace();
                        //return null;
                        //throw new Exception("Error in getting next worker... possible a race condition, or maybe there were just no workers registered, and the logic was wrong...", e);
                    }
                }


            }

            //return null;
            try
            {
                logger.debug("getNextFreeWorker(): freeWorkers.waitUntilNotEmpty()...");

                freeWorkers.waitUntilNotEmpty();
            }
            catch (Exception eee)
            {
                logger.debug("getNextFreeWorker(): Error in freeWorkers.waitUntilNotEmpty(): " + eee);
                if (logger.isDebugEnabled()) eee.printStackTrace();                //return null;

            }

        }
        //return next; //could be null if there were no workers registered
    }



    public boolean waitForFreeWorker() //throws
    {
        String next = null;



        try
        {
            logger.debug("*** freeWorkers.waitUntilNotEmpty()");
            return freeWorkers.waitUntilNotEmpty();
        }
        catch (Exception e)
        {
            logger.debug("waitForFreeWorker(): Error in waiting for freeWorker to not be empty... possible a race condition, or maybe there were just no workers registered, and the logic was wrong...");
            if (logger.isDebugEnabled()) e.printStackTrace();
            return false;
        }

    }


    public String getNextFreeWorker() //throws
    {
        String next = null;



        //while (true)
        //{

        //(freeWorkers)
        //  {

        // if (freeWorkers.size() > 0)
        // {
        try
        {
            //logger.debug("getNextFreeWorker(): waiting for free worker...");
            //next = (String)freeWorkers.remove();
            //logger.debug("getNextFreeWorker(): free worker found at " + next);
            //freeWorkers.insert(next);

            //this.commonState.setPendWorker(next);


            logger.debug("*** trying setPendWorker(any)");
            next = (String)freeWorkers.remove();
            if (next != null)
            {
                logger.debug("*** setPendWorker(" + next + ") success");
                pendWorkers.insert(next);
                try
                {

                    writeLogWorker();
                }
                catch (Exception logE)
                {
                    if (logger.isDebugEnabled()) logE.printStackTrace();
                }
                //write to file change

                final String fNext = new String(next);
                pendWorkerTime.put(fNext, new Long(System.currentTimeMillis()));

                /*
                final int numberOfMillisecondsInTheFuture = 60000; // 10 sec
                Date timeToRun = new Date(System.currentTimeMillis()+numberOfMillisecondsInTheFuture);
                Timer timer = new Timer();

                timer.schedule(new TimerTask()
                               {
                                   public void run()
                                   {
                                       //need a timestampt to figure this out...
                                       Long pwTime = (Long)pendWorkerTime.get(fNext);
                                       if (pwTime == null)
                                       {
                                           setFreeWorker(fNext);
                                           //pendWorkers.remove(workerID);
                                       } else
                                       {
                                           if (pwTime.longValue() + numberOfMillisecondsInTheFuture < System.currentTimeMillis())
                                           {
                                               setFreeWorker(fNext);
                                               //pendWorkers.remove(workerID);

                                           }
                                       }

                                       // Task here ...
                                   }
                               }, timeToRun);
                  */

                return next;
            }
            else
            {
                logger.debug("*** setPendWorker(null) failed");
                return null;
            }

        }

        catch (Exception e)
        {
            logger.debug("getNextFreeWorker(): Error in getting next free worker... possible a race condition, or maybe there were just no workers registered, and the logic was wrong...");
            if (logger.isDebugEnabled()) e.printStackTrace();

            if (next != null)
            {

                freeWorkers.insert(next);
                //pendfreeWorkers.remove(next);
            }
            return null;
            //return null;
            //throw new Exception("Error in getting next worker... possible a race condition, or maybe there were just no workers registered, and the logic was wrong...", e);
        }

        //return null;
        // }


        //   }

        /*
        //return null;
            try
            {
                logger.debug("getNextFreeWorker(): freeWorkers.waitUntilNotEmpty()...");

                freeWorkers.waitUntilNotEmpty();
            } catch (Exception eee)
            {
                logger.debug("getNextFreeWorker(): Error in freeWorkers.waitUntilNotEmpty(): " + eee);
                eee.printStackTrace();
                //return null;

            }    */

        // }
        //return next; //could be null if there were no workers registered
    }



    /*
    public boolean sendNotification(ResourceKey key)
    {
    //boolean test = false;
    while(true)
    {

    
        String next = getNextWorker();
        if(next == null)
        {
        //no workers registered
        return false;
    
        }
        else
        {
        
        if (notification.send(next, key))
            return true;
        else
        {
            //notification failed, should remove the failed worker
            deRegister(next); //try again to the next worker
            //return false;
        }
        }
        //return true;
    }
    //return false;
    }
    */

    public synchronized boolean register(String worker, boolean CACHE_GRID_GLOBAL)
    {
        //if (!CACHE_GRID_GLOBAL)
        //{




        if (roundRobin.exists(worker))
        //this should have worked from above, but something is strange with the common.* classes...
        //if(roundRobin.queue.contains(worker))
        {
            logger.debug("Worker '" + worker + "' already exists, no need to add to the registered list of workers...");
        }
        else
        {

            try
            {
            

            if (EMULATED && CACHE_GRID_GLOBAL)
            {
                CacheLRU cache = new CacheLRU(EMULATION_MAX_CACHE_SIZE_MB);
                workerCacheEmulated.put(worker, cache);

            }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            roundRobin.insert(worker);
            freeWorkers.insert(worker);
            writeLogWorker();
        }


        //return true;
        //}
        //if (CACHE_GRID_GLOBAL)
        //{


            //return cacheGridGlobal.registerWorker(worker);
            //return pendingTasks.registerExecutor(worker);
        //}

        return true;



        //should also update persistent state...

    }


    public synchronized boolean deRegister(String worker, boolean CACHE_GRID_GLOBAL)
    {

        try
        {


        if (EMULATED && CACHE_GRID_GLOBAL)
        {
            //CacheLRU cache = new CacheLRU(MAX_CACHE_SIZE);
            CacheLRU cache = (CacheLRU)workerCacheEmulated.remove(worker);
            cache = null;

        }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //should also update persistent state
        boolean removeRoundRobin = roundRobin.remove(worker);
        if (removeRoundRobin) logger.debug("removed worker " + worker + " from worker list...");
        if (freeWorkers.remove(worker))
            logger.debug("removed worker " + worker + " from free list...");
        //if still busy, maybe we don't want to de-register yet...
        //perhaps we should wait here for worker to not exist in either pendWorkers or busyWorkers...
        if (pendWorkers.remove(worker))
            logger.debug("removed worker " + worker + " from pend list...");
        if (busyWorkers.remove(worker))
            logger.debug("removed worker " + worker + " from busy list...");


        writeLogWorker();


        if (CACHE_GRID_GLOBAL)
        {
            //return cacheGridGlobal.deregisterWorker(worker);

            //return pendingTasks.deregisterExecutor(worker);
        }
        return removeRoundRobin;
    }

    //dummy function to disbale deRegistration
    public boolean deRegisterDummy(String worker, boolean CACHE_GRID_GLOBAL)
    {
        return true;
    }


    public int numWorkers()
    {
        return roundRobin.size();
    }


    public int numFreeWorkers()
    {
        return freeWorkers.size();
    }

    public int numBusyWorkers()
    {
        return busyWorkers.size();
    }


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


    }   



}

/*

class Writer extends Thread
{
    MonitorServer con;
    public Writer(MonitorServer s)
    {
        super("Server Writer");
        this.con = s;
    }
    public void run()
    {
        // TODO code gpplication logic here
        //int servPort = 999;
        //int servPort = 999;//Integer.valueOf(GUI.GP_WS_Port.getText());
        //String servAddr = GUI.GP_WS_Location.getText();
        try
        {

            boolean DEBUG = false;

            logger.debug("SERVER: Connection accepted from: " + con.server.socket.getInetAddress());
            logger.debug("SERVER: Creating object output stream... ");

            ObjectOutputStream oos = new ObjectOutputStream(con.server.socket.getOutputStream());

            logger.debug("SERVER: Creating data input stream... ");
            DataInputStream dis = new DataInputStream(con.server.socket.getInputStream());
            logger.debug("SERVER: Creating object input stream... ");
            ObjectInputStream ois = new ObjectInputStream(dis);

            logger.debug("SERVER: Waiting for start command and frequency update value... ");
            int freq = ois.readInt();//.readObject();
            logger.debug("SERVER: Received frequency update value: " + freq);

            logger.debug("SERVER: Initializing state information... ");
            //server.initState();
            while (con.server.socket.isConnected())
            {

                logger.debug("SERVER: Updating state information... ");
                con.server.updateState();

                //System.out.println("GPResourceCommon: State information... ");   
                //con.server.printState();




                logger.debug("SERVER: Sending state to the client... ");
                oos.writeObject(con.server.data);

                oos.flush();
                if (con.server.exit_flag == true)
                {
                    logger.debug("SERVER: Shuting down...");
                    oos.close();

                    con.server.socket.close();

                } else
                {
                    logger.debug("SERVER: Sleeping for " + freq + " ms");
                    Thread.sleep(freq);
                }
            }



        } catch (Exception e)
        {
            System.out.println("ERROR: "+e.getMessage());
            //GUI.Debug_Console_field.append(GUI.getTime() + ": ERROR: "+e.getMessage() + "\n");

        }


        //GUI.Debug_Console_field.append(GUI.getTime() + ": Connection to GP WS shut down!\n");    
        //System.exit(0);
    }
}



class MonitorServer extends Thread
{
    GPResourceCommon server;
    public Thread writer;

    public MonitorServer(GPResourceCommon s)
    {
        super("Server Writer");
        this.server = s;
    }
    public void run() 
    {

        try
        {
            System.out.println("MonitorServer: Starting up...");   
            ServerSocket listener = new ServerSocket(server.servPort);
            //ObjectOutputStream oos;

            while (true)
            {



                System.out.println("MonitorServer: Listening on port: " + server.servPort);   
                server.socket = listener.accept();
                //startServer();
                writer = new Writer(this);
                writer.start();


            }
        } catch (Exception e)
        {
            System.out.println("ERROR: "+e.getMessage());
        }

    }
}
  */

class MonitorOutput extends Thread
{
    static final Log logger = LogFactory.getLog(MonitorOutput.class);
    GPResourceCommon mon;
    public int outputRefresh = 1000; //in ms

    static Logger loggerPerf = null;
    boolean loggerPerfValid = true;
    String logFilePerf = null;
    //"/home/iraicu/.globus/"


    public static boolean append = true;
    public static int numBytes = 1073741824;
    public static int numFiles = 10;


    public MonitorOutput(GPResourceCommon s)
    {
        super("Monitor Output");
        this.mon = s;


        Properties props = System.getProperties();
        String falkonServiceHome = (String)props.get("FALKON_LOGS");

        logFilePerf=(String)s.falkonConfig.get("GenericPortalWS_performance");
        //String logFilePerf = (String)falkonConfig.get("GenericPortalWS_performance");
        if (logFilePerf == null)
        {

            logFilePerf = new String("/dev/null");
        }

        else
        {
            logFilePerf = new String(falkonServiceHome + "/" + logFilePerf);

        }


        try
        {

            FileHandler handlerPerf = new FileHandler(this.logFilePerf, numBytes, numFiles, append);
            this.loggerPerf = Logger.getLogger("GenericPortalWS_performance");
            this.loggerPerf.addHandler(handlerPerf);
            this.loggerPerf.setUseParentHandlers(false);
            this.loggerPerfValid = true;

        }
        catch (IOException e)
        {
            logger.debug("logger to file '" + logFilePerf + "' failed to initialize...");
            if (logger.isDebugEnabled()) e.printStackTrace();


        }
    }

    public void run()
    {
        int delay = 1000;   // delay for 5 sec.
        int period = 1000;  // repeat every sec.
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask()
                                  {
                                      public void run()
                                      {
                                          mon.writeLogPerf();
                                          // Task here ...
                                      }
                                  }, delay, period);
    }

    /*
   public void run() 
   {

       try
       {
           System.out.println("MonitorOutput: Starting up...");   
           //ServerSocket listener = new ServerSocket(server.servPort);
           //ObjectOutputStream oos;

           while (true)
           {



               //System.out.println("MonitorServer: Listening on port: " + server.servPort);   
               //server.socket = listener.accept();
               //startServer();
               //writer = new Writer(this);
               //writer.start();


               /*
               if (mon.DEMO)
               {
                   mon.updateScreen();
               }
               

               /*
               if (mon.PERF_LOG && loggerPerfValid)
               {
                   mon.writeLog(loggerPerf);

               } 

               mon.writeLogPerf();

               Thread.sleep(outputRefresh);
               //mon.setUp_Time(mon.getUp_Time() + outputRefresh/1000);


           }
       } catch (Exception e)
       {
           System.out.println("ERROR: "+e.getMessage());
       }

   }
   */
}

class MonitorIdleWorkers extends Thread
{
    static final Log logger = LogFactory.getLog(MonitorIdleWorkers.class);
    GPResourceCommon commonState;
    public int outputRefresh = 1000; //in ms
    public int EMULATION_IDLE_TIME = 0;

    public MonitorIdleWorkers(GPResourceCommon s)
    {
        super("MonitorIdleWorkers Thread");
        this.commonState = s;
        this.EMULATION_IDLE_TIME = commonState.EMULATION_IDLE_TIME;
        this.outputRefresh = commonState.EMULATION_IDLE_TIME_POLL;

    }

    public boolean deAllocateFreeWorkers(int numWorkers)
    {
        if (logger.isDebugEnabled()) System.out.println("De-allocating " + numWorkers + " workers due to idle time...");  

        for (int i=0;i<numWorkers;i++)
        {
            String workerID = commonState.getNextFreeWorker();


            try
            {

            WorkerDeRegistration wdr = new WorkerDeRegistration();
            wdr.setMachID(workerID);


            WorkerDeRegistrationResponse wdrr = GPResourceHome.gpResourceWorker.deregisterWorker(wdr);

            if (!wdrr.isValid())
            {
                return false;
                //if (logger.isDebugEnabled()) System.out.println("success in deregistering worker " + workerID);

            }
            //else
            //{
            //    if (logger.isDebugEnabled()) System.out.println("failure in deregistering worker " + workerID);

            //    return false;
            //}

            wdrr = null;
            wdr = null;
            }
            catch (Exception e)
            {
                if (logger.isDebugEnabled()) e.printStackTrace();
                return false;
            }

        }

        return true;
        



    }

    public void run() 
    {
        if (logger.isDebugEnabled()) System.out.println("MonitorIdleWorkers: Starting up...");   
        final int capacity = EMULATION_IDLE_TIME/outputRefresh;
        LinkedList queue = null;

        if (capacity <= 0)
        {
        }
        else
        {
            queue = new LinkedList();

        }



        while (queue != null && capacity > 0 && outputRefresh >= 100)
        {
            try
            {
                //System.out.println("Idle workers: " + commonState.numFreeWorkers()); 

                queue.add(new Integer(commonState.numFreeWorkers()));


                int minFreeWorkers = -1;

                for (Iterator it=queue.iterator(); it.hasNext(); )
                {
                    Integer element = (Integer)it.next();

                    if (minFreeWorkers == -1)
                    {
                        minFreeWorkers = element.intValue();
                    }

                    if (element.intValue() < minFreeWorkers)
                    {
                        minFreeWorkers = element.intValue();
                    }
                }

                if (logger.isDebugEnabled()) System.out.println("Over the last " + EMULATION_IDLE_TIME + " ms (taking " + queue.size() + " samples with a max of " +  capacity + " samples, the minimum idle workers have been " + minFreeWorkers + " ... the current idle workers are " + commonState.numFreeWorkers());  

                if (minFreeWorkers > 0 )
                {
                
                if (deAllocateFreeWorkers(minFreeWorkers))
                {
                     if (logger.isDebugEnabled()) System.out.println("success in deAllocateFreeWorkers(minFreeWorkers)...");
                }
                else
                {
                    if (logger.isDebugEnabled()) System.out.println("failure in deAllocateFreeWorkers(minFreeWorkers)...");

                }
                }


                if (queue.size() >= capacity)
                {
                    queue.remove();
                }

                Thread.sleep(outputRefresh);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }


    }
}

