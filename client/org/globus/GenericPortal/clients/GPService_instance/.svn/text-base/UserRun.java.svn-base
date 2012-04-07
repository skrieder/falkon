//SVN version
/*
 * This file is licensed under the terms of the Globus Toolkit Public License
 * v3, found at http://www.globus.org/toolkit/legal/4.0/license-v3.html.
 * 
 * This notice must gppear in redistributions of this file, with or without
 * modification.
 */
package org.globus.GenericPortal.clients.GPService_instance;

import java.io.*;

import java.net.*;
import java.nio.*;


import javax.xml.rpc.Stub;
import org.globus.wsrf.impl.security.authorization.NoAuthorization;
//import org.globus.wsrf.security.Constants;
import org.globus.wsrf.impl.security.authorization.SelfAuthorization;
import org.globus.wsrf.client.BaseClient;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLine;
import org.globus.wsrf.impl.security.authentication.*;
import org.globus.axis.gsi.GSIConstants;
import org.globus.axis.util.Util;
import org.globus.GenericPortal.common.*;

import org.globus.wsrf.impl.security.authentication.Constants.*;
import javax.security.auth.Subject;
import java.security.cert.X509Certificate;
import org.globus.gsi.CertUtil;
import org.globus.wsrf.impl.security.authentication.encryption.EncryptionCredentials;
import org.globus.gsi.GSIConstants.*;


//import java.io.FileInputStream;
import java.io.*;

import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.GenericPortal.services.core.WS.impl.GPConstants;
import org.globus.GenericPortal.stubs.GPService_instance.GPPortType;
import org.globus.GenericPortal.stubs.GPService_instance.service.GPServiceAddressingLocator;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.xml.sax.InputSource;
import org.globus.wsrf.NotificationConsumerManager;

import org.globus.GenericPortal.stubs.GPService_instance.*;

import org.globus.wsrf.ResourceKey;


import org.globus.wsrf.utils.AddressingUtils;

import org.globus.GenericPortal.common.*;


import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.GenericPortal.services.core.WS.impl.GPConstants;
import org.globus.GenericPortal.stubs.Factory.CreateResource;
import org.globus.GenericPortal.stubs.Factory.CreateResourceResponse;
import org.globus.GenericPortal.stubs.Factory.FactoryPortType;
import org.globus.GenericPortal.stubs.Factory.service.FactoryServiceAddressingLocator;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.GenericPortal.clients.GPService_instance.*;
import org.globus.axis.util.Util;


import javax.xml.rpc.Stub;
import org.globus.wsrf.impl.security.authorization.NoAuthorization;
import org.globus.wsrf.security.Constants;
import org.globus.wsrf.impl.security.authorization.SelfAuthorization;
import org.globus.wsrf.client.BaseClient;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLine;
import org.globus.wsrf.impl.security.authentication.*;
import org.globus.axis.gsi.GSIConstants;
import org.globus.axis.util.Util;
import org.globus.GenericPortal.common.*;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.util.*;

import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.GenericPortal.services.core.WS.impl.GPConstants;
import org.globus.GenericPortal.stubs.Factory.CreateResource;
import org.globus.GenericPortal.stubs.Factory.CreateResourceResponse;
import org.globus.GenericPortal.stubs.Factory.FactoryPortType;
import org.globus.GenericPortal.stubs.Factory.service.FactoryServiceAddressingLocator;
import org.globus.wsrf.encoding.ObjectSerializer;


import java.io.*;

import javax.xml.rpc.Stub;
import org.globus.wsrf.impl.security.authorization.NoAuthorization;
import org.globus.wsrf.security.Constants;
import org.globus.wsrf.impl.security.authorization.SelfAuthorization;
import org.globus.wsrf.client.BaseClient;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLine;
import org.globus.wsrf.impl.security.authentication.*;
import org.globus.axis.gsi.GSIConstants;
import org.globus.axis.util.Util;
import org.globus.GenericPortal.common.*;

import javax.xml.rpc.Stub;
import org.globus.wsrf.impl.security.authorization.NoAuthorization;
import org.globus.wsrf.security.Constants;


import org.oasis.wsrf.lifetime.WSResourceLifetimeServiceAddressingLocator;
import org.oasis.wsrf.lifetime.ImmediateResourceTermination;
import org.oasis.wsrf.faults.BaseFaultType;

import org.globus.wsrf.utils.FaultHelper;

import javax.xml.rpc.Stub;

import org.apache.commons.cli.ParseException;

import java.io.*;

import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.GenericPortal.services.core.WS.impl.GPConstants;
import org.globus.GenericPortal.stubs.GPService_instance.GPPortType;
import org.globus.GenericPortal.stubs.GPService_instance.service.GPServiceAddressingLocator;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.xml.sax.InputSource;
import org.globus.wsrf.NotificationConsumerManager;

import org.globus.GenericPortal.stubs.GPService_instance.*;

import org.globus.wsrf.ResourceKey;


import org.globus.wsrf.utils.AddressingUtils;

import org.globus.GenericPortal.common.*;


import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.GenericPortal.services.core.WS.impl.GPConstants;
import org.globus.GenericPortal.stubs.Factory.CreateResource;
import org.globus.GenericPortal.stubs.Factory.CreateResourceResponse;
import org.globus.GenericPortal.stubs.Factory.FactoryPortType;
import org.globus.GenericPortal.stubs.Factory.service.FactoryServiceAddressingLocator;
import org.globus.wsrf.encoding.ObjectSerializer;


import KDTree.*;


class MonitorThread extends Thread
{
    public UserRun user;
    public long pollInterval = 1000;

    StatCalc statCalc = new StatCalc();

    public MonitorThread(UserRun user, long pollInterval)
    {
        this.user = user;
        this.pollInterval = pollInterval;
    }

    public void run()
    {
        String notificationMessage = null;
        int lastNumNot = 0;
        int lastTaskCompl = 0;
        long lastTimeStamp = 0;
        double tp = 0.0;

        while ((user.INTERACTIVE || !user.finished) && !user.MONITOR_THREAD_EXIT)
        {
            try
            {


                if (user.getSendTasks() == 0 || user.getRecvTasks() == 0)
                {
                    //System.out.println(user.COMMENT3  + " time " + user.jobTime.getElapsedTime()*1.0/1000.0 + " tasks_success " + user.getSuccessExecs() + " tasks_failed " + user.getFailedExecs() + " tasks_sent " + user.getSendTasks() + " completed 0.0 tasks_tp 0.0 aver_tp 0.0 stdev_tp 0.0 ETA ?");
                    System.out.println(user.jobTime.getElapsedTime()*1.0/1000.0 + " tasks+ " + user.getSuccessExecs() + " tasks- " + user.getFailedExecs() + " tasks-> " + user.getSendTasks() + " completed 0.0 tasks_tp 0.0 aver_tp 0.0 stdev_tp 0.0 ETA ?" + " HEAP_SIZE " + (int)Math.ceil(Runtime.getRuntime().totalMemory()/(1024*1024)) + " HEAP_FREE " + (int)Math.ceil(Runtime.getRuntime().freeMemory()/(1024*1024)) + " HEAP_MAX " + (int)Math.ceil(Runtime.getRuntime().maxMemory()/(1024*1024)));
                }
                else
                {
                    tp = (user.getRecvTasks() - lastTaskCompl)/((user.jobTime.getElapsedTime() - lastTimeStamp)/1000.0);
                    statCalc.enter(tp);

                    //System.out.println(user.COMMENT3  + " time " + user.jobTime.getElapsedTime()*1.0/1000.0 + " tasks_success " + user.getSuccessExecs() + " tasks_failed " + user.getFailedExecs() + " tasks_sent " + user.getSendTasks() + " completed 0.0 tasks_tp 0.0 aver_tp 0.0 stdev_tp 0.0 ETA ?");
                    //System.out.println(user.COMMENT3  + " time " + user.jobTime.getElapsedTime()*1.0/1000.0 + " tasks_success " + user.getSuccessExecs() + " tasks_failed " + user.getFailedExecs() + " tasks_sent " + user.getSendTasks() + " completed " + Math.round((user.getRecvTasks()*100.0/user.getSendTasks())*100)/100.0 + " tasks_tp " + Math.round((tp)*100)/100.0 + " aver_tp " + Math.round(statCalc.getMean()*100)/100.0 + " stdev_tp " + Math.round(statCalc.getStandardDeviation()*1000)/1000.0 + " ETA " + (Math.round(((user.jobTime.getElapsedTime()*1.0/user.getRecvTasks())*(user.getSendTasks() - user.getRecvTasks())))/1000.0));
                    System.out.println(user.jobTime.getElapsedTime()*1.0/1000.0 + " tasks+ " + user.getSuccessExecs() + " tasks- " + user.getFailedExecs() + " tasks-> " + user.getSendTasks() + " completed " + Math.round((user.getRecvTasks()*100.0/user.getSendTasks())*100)/100.0 + " tasks_tp " + Math.round((tp)*100)/100.0 + " aver_tp " + Math.round(statCalc.getMean()*100)/100.0 + " stdev_tp " + Math.round(statCalc.getStandardDeviation()*1000)/1000.0 + " ETA " + (Math.round(((user.jobTime.getElapsedTime()*1.0/user.getRecvTasks())*(user.getSendTasks() - user.getRecvTasks())))/1000.0) + " HEAP_SIZE " + (int)Math.ceil(Runtime.getRuntime().totalMemory()/(1024*1024)) + " HEAP_FREE " + (int)Math.ceil(Runtime.getRuntime().freeMemory()/(1024*1024)) + " HEAP_MAX " + (int)Math.ceil(Runtime.getRuntime().maxMemory()/(1024*1024)));
                }
                //System.out.println(userThread.user.jobTime.getElapsedTime() + " : NOTIFICATION " + userThread.getRecvTasks() + " of " + userThread.getSendTasks() + " : " + taskID + " ... notifications left to receive " + userThread.execsIDs.size());
                lastTaskCompl = user.getRecvTasks();
                lastNumNot = user.getNumNotifications();
                lastTimeStamp = user.jobTime.getElapsedTime();

                //every 60 seconds, flush log to disk
                if ((int)(user.jobTime.getElapsedTime()*1.0/1000.0)%60 == 0)
                {
                    if (user.outTaskPerf != null)
                    {
                        user.outTaskPerf.flush();
                    }
                    if (user.outTaskDescSuccess != null)
                    {
                        user.outTaskDescSuccess.flush();
                    }
                    if (user.outTaskDescFailed != null)
                    {
                        user.outTaskDescFailed.flush();
                    }
                }


                Thread.sleep(pollInterval);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

}


class CreateResourceThread extends Thread
{
    public String serviceURI;
    public int i;
    public ThreadState clientTS[];
    public UserRun user;

    public CreateResourceThread(String serviceURI, int i, ThreadState clientTS[], UserRun user)
    {
        this.i = i;
        this.clientTS = clientTS;
        this.user = user;
        this.serviceURI = serviceURI;
    }

    public void run()
    {
        try
        {

            clientTS[i] = user.createResource(serviceURI);
        }
        catch (Exception e)
        {
            clientTS[i] = null;
            if (user.DEBUG) e.printStackTrace();

        }
    }

}


class DestroyResourceThread extends Thread
{
    public ThreadState clientTS;
    public UserRun user;

    //public int i;

    public DestroyResourceThread(ThreadState clientTS, UserRun user)
    {
        //this.i = i;
        this.clientTS = clientTS;
        this.user = user;
        //this.serviceURI = serviceURI;
    }

    public void run()
    {
        user.destroyResource(clientTS.instanceEPR);
    }

}




public class UserRun
{

    public long SUBMIT_TIME_QUANTA = 1000;

    public boolean finished = false;
    public boolean MONITOR_THREAD_EXIT = false;
    public boolean retrieveResult = false;

    public /*static*/ String CLIENT_DESC = null;


    /*static*/ boolean AUTHENTICATE = false;
    /*static*/ boolean AUTHORIZE = false;
    /*static*/ boolean ENCRYPT = false;
    /*static*/ boolean SIGN = false;
    /*static*/ boolean TSL = false;
    /*static*/ boolean MSG = false;
    /*static*/ boolean CONV = false;

    /*static*/ {
        Util.registerTransport();
    }

    //public Notification userNot;


    public boolean SINGLE_RESOURCE = false;
    public boolean CREATE_TEST = false;
    public boolean ONE_TASK_PER_RESOURCE = false;


    public boolean resultsRetrieved = false;
    String finalResultStrings[] = null;
    //public String machID;

    public boolean DEBUG = false;
    public boolean INTERACTIVE = false;
    public boolean DIPERF;
    public boolean SUMMARY = false;
    public boolean NOINIT = false;
    public boolean IN_MEMORY = false;

    public boolean STACKING = false;

    public int EMULATED_FILE_SIZE = 0;

    //public int activeNotifications;	//# of concurent notifications
    //public int MAX_NUM_NOTIFICATIONS;
    //public int activeThreads;	//# of concurent threads accross all the jobs
    public int MAX_NUM_THREADS = 1;
    public int MAX_NUM_SUBMIT_THREADS = 1;
    //public int queueLength;	//# of individual tasks (image ROI) scheduled to take place

    //public int LIFETIME; 	//time in ms that the worker should accept work

    //public Thread                              notificationThread;

    //public Notification userNot;
    //private int SO_TIMEOUT;

    public String fileEPR;
    public String NOTIFICATION_ENDPOINT = null;
    public String serviceURI = null;
    public String serviceURIs[] = null;
    public String serviceURIfile = null;
    //public String factoryURI;
    //public EndpointReferenceType homeEPR;
    public String homeURI;

    public String path;
    public String fileName;

    public int NUM_TASKS;

    public String configFile;
    public String jobDescriptionFile;
    public String stackDescriptionFile;
    public int HEIGHT = 100;
    public int WIDTH = 100;

    public String SDSS_INDEX = null;
    public String STACK_RESULT = new String("scratch/result.fit");

    public String REMOTE_SCRATCH = new String("");
    public boolean PERSIST_OBJECTS = false;

    public int num_execs = 1;

    boolean userResultAvailable = false;

    StopWatch jobTime = new StopWatch();


    public /*static*/ int old_GPWS_qLength = 0;
    public /*static*/ int old_GPWS_activeTasks = 0;
    public /*static*/ int old_GPWS_numWorkers = 0;
    public /*static*/ int old_completedTasks = 0;
    public /*static*/ int old_queuedTask = 0;
    public /*static*/ int old_activeTask = 0;

    public int MAX_COM_PER_WS = 1;
    public int MAX_COM_PER_WS2 = MAX_COM_PER_WS;

    public boolean DATA_CACHING = false;


    // Create the task queue
    public static WorkQueue execQ = new WorkQueue();
    // Create the stack queue
    public static WorkQueue resultQ = new WorkQueue();



    public Console console = null;
    public GPPortType Ggp = null;

    private int taskID = 0;

    public BufferedWriter outTaskPerf = null;
    public BufferedWriter outTaskDescSuccess = null;
    public BufferedWriter outTaskDescFailed = null;

    private int sendTasks = 0;
    //private int sendTasksGlobal = 0;
    private int recvTasks = 0;

    public synchronized void setSendTasks()
    {
        sendTasks++;
        //sendTasksGlobal++;
    }

    public synchronized int getSendTasks()
    {
        return sendTasks;
    }

    /*
    public synchronized int getSendTasksGlobal()
    {
        return sendTasksGlobal;
    } */


    public synchronized void setRecvTasks()
    {
        recvTasks++;
    }

    public synchronized int getRecvTasks()
    {
        return recvTasks;
    }

    int taskNum = 0;
    public boolean writeLogTaskPerfTest = false;
    public String writeLogTaskPerfFile = null;
    public String writeLogTaskDescFile = null;
    public boolean writeLogTaskDescTest = false;


    public synchronized void writeLogTaskPerf(String msg)
    {   
        //System.out.println("writeLogTaskPerf(): "+ msg);

        Properties props = System.getProperties();
        FALKON_LOGS = (String)props.get("FALKON_LOGS");
        if (FALKON_LOGS == null)
        {
            //FALKON_LOGS = new String("");
            System.out.println("Warning, ${FALKON_LOGS} is not set, you must include -DFALKON_LOGS=${FALKON_LOGS} when starting the JVM...  things might not work due to relative path names...");

        }
        else
        {

            if (writeLogTaskPerfTest)
            {


                try
                {
                    if (outTaskPerf == null)
                    {
                        //String outTaskPerfFileName = (String)deefConfig.get("client_taskPerf");
                        //if (outTaskPerfFileName == null)
                        //{

                        //    outTaskPerfFileName = new String("/dev/null");
                        //    writeLogTaskPerfTest = false;
                        //}


                        //outTaskPerf = new BufferedWriter(new FileWriter(outTaskPerfFileName));
                        //outTaskPerf = new BufferedWriter(new FileWriter(FALKON_LOGS+"/client_taskPerf.txt"));
                        if (writeLogTaskPerfFile == null)
                        {
                            writeLogTaskPerfFile = new String(FALKON_LOGS+"/client_taskPerf.txt");

                        }
                        outTaskPerf = new BufferedWriter(new FileWriter(writeLogTaskPerfFile));
                        //outTaskPerf.write("Time_ms taskID startTime endTime getSubmitQueueTime getNotificationTime getResultsQueueTime getTotalTime\n");
                        outTaskPerf.write("taskNum taskID workerID startTimeStamp submitTimeStamp resultsQueueTimeStamp endTimeStamp submitTime queueExecTime resultsTime totalTime exitCode\n");
                    }

                    outTaskPerf.write(System.currentTimeMillis() + " " + msg + "\n");
                    //outTaskPerf.flush();

                    taskNum++;

                    //outWorker.close();
                }
                catch (IOException e)
                {
                    System.out.println("Error in writeLogTaskPerf() when trying to log message: " + e);
                    e.printStackTrace();
                }
            }
        }

    }


    public synchronized void writeLogTaskDescSuccess(String msg)
    {   
        if (writeLogTaskDescTest)
        {


            try
            {
                if (outTaskDescSuccess == null)
                {
                    if (writeLogTaskDescFile == null)
                    {
                        writeLogTaskDescFile = new String(FALKON_LOGS+"/client_taskDescription.txt");

                    }
                    outTaskDescSuccess = new BufferedWriter(new FileWriter(writeLogTaskDescFile+".success"));
                }

                outTaskDescSuccess.write(msg + "\n");
                //outTaskPerf.flush();
                //outWorker.close();
            }
            catch (IOException e)
            {
                System.out.println("Error in writeLogTaskDescSuccess() when trying to log message: " + e);
                e.printStackTrace();
            }
        }

    }

    public synchronized void writeLogTaskDescFailed(String msg)
    {   
        if (writeLogTaskDescTest)
        {


            try
            {
                if (outTaskDescSuccess == null)
                {
                    if (writeLogTaskDescFile == null)
                    {
                        writeLogTaskDescFile = new String(FALKON_LOGS+"/client_taskDescription.txt");

                    }
                    outTaskDescFailed = new BufferedWriter(new FileWriter(writeLogTaskDescFile+".failed"));
                }

                outTaskDescFailed.write(msg + "\n");
                //outTaskPerf.flush();
                //outWorker.close();
            }
            catch (IOException e)
            {
                System.out.println("Error in writeLogTaskDescFailed() when trying to log message: " + e);
                e.printStackTrace();
            }
        }

    }


    MonitorThread monitorThread = null;

    public UserRun(String[] args) throws Exception
    {


        StopWatch sw = new StopWatch();

        sw.start();
        try
        {

            parseArgs(args);

        }
        catch (Exception e)
        {
            throw new Exception("Error in UserRun() constructor... " + e);

        }

        //execQ.DEBUG = DEBUG;
        //resultQ.DEBUG = DEBUG;

        sw.stop();


        if (DEBUG) System.out.println("USER: Parse Arguements and Setup Corresponding State (" + sw.getElapsedTime() + "ms)");
        sw.reset();





    }

    private int successExecs = 0;
    private int failedExecs = 0;
    private int errorNotification = 0;

    private int numNotifications = 0;

    public synchronized void setNumNotifications()
    {
        numNotifications++;

    }

    public synchronized int getNumNotifications()
    {
        return numNotifications;

    }


    public synchronized void setSuccessExecs()
    {
        successExecs++;

    }
    public synchronized void setFailedExecs()
    {
        failedExecs++;

    }
    public synchronized void setErrorNotification()
    {
        errorNotification++;

    }


    public synchronized int getSuccessExecs()
    {
        return successExecs;

    }
    public synchronized int getFailedExecs()
    {
        return failedExecs;

    }
    public synchronized int getErrorNotification()
    {
        return errorNotification;

    }



    public Notification GuserNot = null;



    //public synchronized ThreadState createResource(String factoryURI)
    public ThreadState createResource(String factoryURI)
    {
        StopWatch lt = new StopWatch();
        StopWatch sw = new StopWatch();
        lt.start();

        //parseArgs(args);


        FactoryServiceAddressingLocator factoryLocator = new FactoryServiceAddressingLocator();

        try
        {

            EndpointReferenceType factoryEPR, instanceEPR;
            FactoryPortType gpFactory;

            // Get factory portType
            factoryEPR = new EndpointReferenceType();
            factoryEPR.setAddress(new Address(factoryURI));
            gpFactory = factoryLocator.getFactoryPortTypePort(factoryEPR);

            if (CLIENT_DESC != null && (new File(CLIENT_DESC)).exists())
            {
                if (DEBUG) //System.out.println("Setting appropriate security from file '" + CLIENT_DESC + "'!");
                    ((Stub)gpFactory)._setProperty(Constants.CLIENT_DESCRIPTOR_FILE, CLIENT_DESC);


            }

            // Create resource and get endpoint reference of WS-Resource
            CreateResourceResponse createResponse = gpFactory
                                                    .createResource(new CreateResource());
            instanceEPR = createResponse.getEndpointReference();
            lt.stop();
            lt.reset();


            GPPortType gp = getGPPortType(instanceEPR);


            if (CLIENT_DESC != null && (new File(CLIENT_DESC)).exists())
            {
                if (DEBUG) //System.out.println("Setting appropriate security from file '" + CLIENT_DESC + "'!");
                    ((Stub)gp)._setProperty(Constants.CLIENT_DESCRIPTOR_FILE, CLIENT_DESC);


            }

            //if (!NOINIT)
            //{


            int SO_TIMEOUT = 0;
            //Notification userNot = null;
            //if (userNot == null)
            //{

            int recvPort = 0;

            if (CREATE_TEST && GuserNot == null)
            {
                GuserNot = new Notification(SO_TIMEOUT, DEBUG);
                GuserNot.DEBUG = DEBUG;
                recvPort = GuserNot.recvPort;

            }

            Notification userNot = null;
            if (!CREATE_TEST)
            {



                userNot = new Notification(SO_TIMEOUT, DEBUG);
                userNot.DEBUG = DEBUG;
                recvPort = userNot.recvPort;
            }


            if (DEBUG) System.out.println("Notification initialized on port: " + recvPort);
            //}





            sw.start();

            String machID = getMachNamePort(recvPort); 
            sw.stop();
            if (DEBUG)
            {
                System.out.println("WORKER: Get Machine Name (" + sw.getElapsedTime() + "ms)");
            }

            if (DIPERF) System.out.println("WORKER:getMachNamePort(): " + sw.getElapsedTime() + " ms");

            if (DEBUG)
            {
                System.out.println("User " + machID + " started succesful!");
            }
            sw.reset();



            sw.start();
            Init initObject = new Init();
            initObject.setValid(true);
            initObject.setMachID(machID);
            InitResponse ir = gp.init(initObject);
            sw.stop();
            if (ir.isValid())
            {

                if (DEBUG) System.out.println("Initialized GenericPortalWS (" + sw.getElapsedTime() + ")");
            }
            else
            {
                System.out.println("Failed to initialize GenericPortalWS (" + sw.getElapsedTime() + "), exiting...");
                System.exit(0);
            }
            sw.reset();
            //}

            ThreadState ts = new ThreadState(instanceEPR, gp, userNot, machID);

            //return instanceEPR;
            return ts;

            /*
            String endpointString = ObjectSerializer.toString(instanceEPR,
                            GPConstants.RESOURCE_REFERENCE);

            FileWriter fileWriter = new FileWriter(eprFilename);
            BufferedWriter bfWriter = new BufferedWriter(fileWriter);
            bfWriter.write(endpointString);
            bfWriter.close();
            System.out.println("Endpoint reference written to file "
                            + eprFilename + " (" + lt.getElapsedTime()+ "ms)");
             */



        }
        catch (Exception e)
        {
            if (DEBUG) e.printStackTrace();
            return null;
        }

    }


    //public synchronized boolean destroyResource(EndpointReferenceType EPR)
    public boolean destroyResource(EndpointReferenceType EPR)
    {
        StopWatch lt = new StopWatch();
        lt.start();

        StopWatch sw = new StopWatch();

        //parseArgs(args);


        try
        {
            /*  EndpointReferenceType EPR = null;
  
  
              if (args[0].equals("-epr"))
              {
                  eprFilename = new String(args[1]);
                  FileInputStream fis = new FileInputStream(eprFilename);
                  EPR = getEPR(fis);
                  fis.close();
              } else
              {
                  System.out.println("usage: java org.globus.GenericPortal.clients.FactoryService_GP.ClientDestroy -epr fileName");
                  return;
  
              }
  
              */


            // if (!NOINIT)
            // {
            sw.start();
            GPPortType gp = getGPPortType(EPR);


            if (CLIENT_DESC != null && (new File(CLIENT_DESC)).exists())
            {
                if (DEBUG) //System.out.println("Setting appropriate security from file '" + CLIENT_DESC + "'!");
                    ((Stub)gp)._setProperty(Constants.CLIENT_DESCRIPTOR_FILE, CLIENT_DESC);


            }


            DeInit deInit = new DeInit();
            deInit.setValid(true);
            DeInitResponse dr = gp.deInit(deInit);
            sw.stop();
            if (dr.isValid())
            {

                if (DEBUG) System.out.println("DeInit GenericPortalWS (" + sw.getElapsedTime() + ")");
            }
            else
            {
                System.out.println("Failed to deInit GenericPortalWS (" + sw.getElapsedTime() + ")");
                //System.exit(0);
            }
            sw.reset();
            // }


            //parse args... -epr epr_file        
            WSResourceLifetimeServiceAddressingLocator locator =
            new WSResourceLifetimeServiceAddressingLocator();

            try
            {
                ImmediateResourceTermination port = 
                locator.getImmediateResourceTerminationPort(EPR);

                if (CLIENT_DESC != null && (new File(CLIENT_DESC)).exists())
                {
                    if (DEBUG) //System.out.println("Setting appropriate security from file '" + CLIENT_DESC + "'!");
                        ((Stub)port)._setProperty(Constants.CLIENT_DESCRIPTOR_FILE, CLIENT_DESC);


                }
                port.destroy(new org.oasis.wsrf.lifetime.Destroy());
                if (DEBUG) System.out.println("ClientDestroy operation was successful");




                return true;

                /*
                boolean success = (new File(eprFilename)).delete();
                if (!success)
                {
                    // Deletion failed
                    System.out.println("EPR file removal failed, but the resource was still removed succesfully.");
                }
                else
                    System.out.println("EPR file removed");
                //System.out.println("EPR file removed");
                */

            }
            catch (Exception e)
            {
                System.err.println("Error: " + FaultHelper.getMessage(e));
                return false;
            }
        }
        catch (Exception ex)
        {
            if (DEBUG) System.out.println("Error: destroyResource(): " + ex);
            return false;
        }

    }

    public static Random randInt = new Random();

    public synchronized String getID(String machID)
    {
        taskID++;
        return new String(machID + ":"+ taskID + "_" + randInt.nextInt((int)Math.pow(2,31)));
        //return new String(machID + ":"+ taskID);
    }





    public int numTuples(String fileName) throws Exception
    {
        //try
        // {
        int countRec = 0;
        RandomAccessFile randFile = new RandomAccessFile(fileName,"r");
        long lastRec=randFile.length();
        randFile.close();
        FileReader fileRead = new FileReader(fileName);
        LineNumberReader lineRead = new LineNumberReader(fileRead);
        lineRead.skip(lastRec);
        countRec=lineRead.getLineNumber();
        fileRead.close();
        lineRead.close();
        return countRec;
        // }
        //  catch(IOException e)
        // {
        //
        //      if (DEBUG) System.out.println("Error: " + e);
        //      return 0;
        // }
    }


    public int min(int a, int b)
    {
        if (a<b)
        {
            return a;
        }
        else
            return b;
    }

    public EndpointReferenceType getEPR(EndpointReferenceType hEPR, ResourceKey key) throws Exception
    {
        String instanceURI = hEPR.getAddress().toString();
        return(EndpointReferenceType) AddressingUtils.createEndpointReference(instanceURI, key);
    }

    public EndpointReferenceType getEPR(FileInputStream fis) throws Exception
    {
        return(EndpointReferenceType) ObjectDeserializer.deserialize(new InputSource(fis),EndpointReferenceType.class);
    }




    public EndpointReferenceType getEPR(String serviceURI) throws Exception
    {
        EndpointReferenceType epr = new EndpointReferenceType();
        epr.setAddress(new Address(serviceURI));
        return epr;
    }


    public GPPortType getGPPortType(EndpointReferenceType epr) throws Exception
    {
        GPServiceAddressingLocator instanceLocator = new GPServiceAddressingLocator();
        return instanceLocator.getGPPortTypePort(epr);

    }

    /*

    public GPPortType getGPPortType() throws Exception
    {
        GPServiceAddressingLocator instanceLocator = new GPServiceAddressingLocator();
        return instanceLocator.getGPPortTypePort(homeEPR);

    }
    */

    public String COMMENT1 = new String("null");
    public String COMMENT2 = new String("null");
    public String COMMENT3 = new String("null");


    public long POLL_INTERVAL = -1;
    public int MAX_CONCURENT_TASKS = 0; //0 means infinity
    public int LOCALITY = 1;

    public int MAX_WALL_TIME_MS = 0; //0 means don't enforce wall time...

    public int MAX_SUBMIT_PER_SEC = 0; //0 means no throttling
    public int MAX_SUBMIT_PER_SEC2 = 0; //0 means no throttling

    public int MIN_SUBMIT_PER_SEC = 1;
    public boolean VARIABLE_SUBMIT_RATE = false;
    public int INC_SUBMIT = 0;
    public double INC_SUBMIT_MULT = 1;
    public boolean INC_SUBMIT_SIN = false;
    public int INC_SUBMIT_INTERVAL = 1;



    String replace(String str, String pattern, String replace)
    {
        try
        {

            int s = 0;
            int e = 0;
            StringBuffer result = new StringBuffer();

            while ((e = str.indexOf(pattern, s)) >= 0)
            {
                result.append(str.substring(s, e));
                result.append(replace);
                s = e+pattern.length();
            }
            result.append(str.substring(s));
            return result.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }



    public void parseArgs(String[] args) throws Exception
    {
        if (args.length < 1)
        {
            usage(args);
            return;
        }

        int ctr;
        for (ctr = 0; ctr < args.length; ctr++)
        {
            if (args[ctr].equals("-epr") && ctr + 1 < args.length)
            {
                ctr++;
                fileEPR = new String(args[ctr]);
                //FileInputStream fis = new FileInputStream(fileEPR);
                //homeEPR = (EndpointReferenceType) ObjectDeserializer.deserialize(new InputSource(fis),EndpointReferenceType.class);
                //homeEPR = getEPR(fis);

                //fis.close();
            }
            //must implement properly as a new resource would have to be created :(
            else if (args[ctr].equals("-notificationEndpoint") && ctr + 1 < args.length)
            {
                ctr++;
                NOTIFICATION_ENDPOINT = new String(args[ctr]);
                //FileInputStream fis = new FileInputStream(fileEPR);
                //homeEPR = (EndpointReferenceType) ObjectDeserializer.deserialize(new InputSource(fis),EndpointReferenceType.class);
                //homeEPR = getEPR(serviceURI);
                //homeEPR = createResource(serviceURI);

                //fis.close();
            }

            else if (args[ctr].equals("-serviceURI") && ctr + 1 < args.length)
            {
                ctr++;
                serviceURI = new String(args[ctr]);
                //FileInputStream fis = new FileInputStream(fileEPR);
                //homeEPR = (EndpointReferenceType) ObjectDeserializer.deserialize(new InputSource(fis),EndpointReferenceType.class);
                //homeEPR = getEPR(serviceURI);
                //homeEPR = createResource(serviceURI);

                //fis.close();
            }
            else if (args[ctr].equals("-serviceURIs") && ctr + 1 < args.length)
            {
                ctr++;
                serviceURIs = args[ctr].split(",");
                if (serviceURIs != null)
                {
                    MAX_NUM_THREADS = serviceURIs.length;
                    //System.out.println("1: setting MAX_NUM_THREADS = " + MAX_NUM_THREADS);
                }
                else
                {
                    System.out.println("invalid service uris... exit");
                }

                //FileInputStream fis = new FileInputStream(fileEPR);
                //homeEPR = (EndpointReferenceType) ObjectDeserializer.deserialize(new InputSource(fis),EndpointReferenceType.class);
                //homeEPR = getEPR(serviceURI);
                //homeEPR = createResource(serviceURI);

                //fis.close();
            }
            else if (args[ctr].equals("-serviceURIfile") && ctr + 1 < args.length)
            {
                ctr++;

                serviceURIfile = new String(args[ctr]);

                try
                {
                    BufferedReader in = new BufferedReader(new FileReader(serviceURIfile));
                    String str;
                    /*
                    int numServiceURIs = 0;
                    str = in.readLine();
                    if (str != null)
                    {
                        numServiceURIs = Integer.parseInt(str);

                        if (numServiceURIs > 0)
                        {
                            serviceURIs = new String[numServiceURIs];
                        }

                    }

                    */

                    String baseServiceURI = null;
                    str = in.readLine();
                    if (str != null)
                    {
                        baseServiceURI = new String(str);

                    }

                    List serviceURIsList = new ArrayList(1000);



                    //int index = 0;
                    //while ((str = in.readLine()) != null && baseServiceURI != null && numServiceURIs > 0)
                    while ((str = in.readLine()) != null && baseServiceURI != null)
                    {

                        String tokens[] = str.split(" ");
                        if (tokens.length == 2)
                        {
                            //serviceURIs[index] = new String(replace(replace(baseServiceURI, "$IP", tokens[0]), "$PORT", tokens[1]));
                            serviceURIsList.add(new String(replace(replace(baseServiceURI, "$IP", tokens[0]), "$PORT", tokens[1])));
                            //System.out.println("Service URI[" + index + "] = " + serviceURIs[index]);
                            //index++;
                        }



                    }



                    int numServiceURIs = serviceURIsList.size();

                    if (numServiceURIs > 0)
                    {

                        serviceURIs = new String[numServiceURIs];

                        for (int i=0;i<numServiceURIs;i++)
                        {
                            serviceURIs[i] = (String) serviceURIsList.get(i);
                            System.out.println("Service URI[" + i + "] = " + serviceURIs[i]);

                        }

                    }
                    else
                    {
                        System.out.println("no service URIs defined, exiting...");
                        System.exit(7);
                    }
                    in.close();


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }



                //serviceURIs = args[ctr].split(",");
                if (serviceURIs != null)
                {
                    MAX_NUM_THREADS = serviceURIs.length;
                    //System.out.println("2: setting MAX_NUM_THREADS = " + MAX_NUM_THREADS);

                }
                else
                {
                    System.out.println("invalid service uris... exit");
                }

                //FileInputStream fis = new FileInputStream(fileEPR);
                //homeEPR = (EndpointReferenceType) ObjectDeserializer.deserialize(new InputSource(fis),EndpointReferenceType.class);
                //homeEPR = getEPR(serviceURI);
                //homeEPR = createResource(serviceURI);

                //fis.close();
            }
            else if (args[ctr].equals("-comment1") && ctr + 1 < args.length)
            {
                ctr++;
                COMMENT1 = new String(args[ctr]);
            }
            else if (args[ctr].equals("-comment2") && ctr + 1 < args.length)
            {
                ctr++;
                COMMENT2 = new String(args[ctr]);
            }
            else if (args[ctr].equals("-comment3") && ctr + 1 < args.length)
            {
                ctr++;
                COMMENT3 = new String(args[ctr]);
            }
            else if (args[ctr].equals("-monitor") && ctr + 1 < args.length)
            {
                ctr++;
                POLL_INTERVAL = (new Long(args[ctr])).longValue();;
            }
            else if (args[ctr].equals("-max_concurent_tasks") && ctr + 1 < args.length)
            {
                ctr++;
                MAX_CONCURENT_TASKS = (new Integer(args[ctr])).intValue();
                /*
                if (MAX_NUM_THREADS > 0)
                {
                    MAX_CONCURENT_TASKS = (int)Math.max(1,(MAX_CONCURENT_TASKS*1.0/MAX_NUM_THREADS));

                }*/
            }
            else if (args[ctr].equals("-max_submit_throughput") && ctr + 1 < args.length)
            {
                ctr++;
                MAX_SUBMIT_PER_SEC = (new Integer(args[ctr])).intValue();
                MAX_SUBMIT_PER_SEC2 = MAX_SUBMIT_PER_SEC;
                ////System.out.println("Set MAX_SUBMIT_PER_SEC to " + MAX_SUBMIT_PER_SEC);

                if (MAX_NUM_SUBMIT_THREADS > 0)
                {
                    MAX_SUBMIT_PER_SEC = (int)Math.max(1,(MAX_SUBMIT_PER_SEC*1.0/MAX_NUM_SUBMIT_THREADS));
                    MAX_SUBMIT_PER_SEC2 = MAX_SUBMIT_PER_SEC;
                    ////System.out.println("Set MAX_SUBMIT_PER_SEC to " + MAX_SUBMIT_PER_SEC);

                }
            }
            else if (args[ctr].equals("-min_submit_throughput") && ctr + 1 < args.length)
            {
                ctr++;
                MIN_SUBMIT_PER_SEC = (new Integer(args[ctr])).intValue();
                //System.out.println("Set MIN_SUBMIT_PER_SEC to " + MIN_SUBMIT_PER_SEC);
                if (MAX_NUM_SUBMIT_THREADS > 0)
                {
                    MIN_SUBMIT_PER_SEC = (int)Math.max(1,(MIN_SUBMIT_PER_SEC*1.0/MAX_NUM_SUBMIT_THREADS));
                    //System.out.println("Set MIN_SUBMIT_PER_SEC to " + MIN_SUBMIT_PER_SEC);

                }
                VARIABLE_SUBMIT_RATE = true;
            }
            else if (args[ctr].equals("-increase_submit_throughput") && ctr + 1 < args.length)
            {
                ctr++;
                INC_SUBMIT = Integer.parseInt(args[ctr]);
                //System.out.println("Set INC_SUBMIT to " + INC_SUBMIT);
                if (MAX_NUM_SUBMIT_THREADS > 0)
                {
                    INC_SUBMIT = (int)Math.max(0,(INC_SUBMIT*1.0/MAX_NUM_SUBMIT_THREADS));
                    //System.out.println("Set INC_SUBMIT to " + INC_SUBMIT);

                }
                VARIABLE_SUBMIT_RATE = true;
            }
            else if (args[ctr].equals("-increase_submit_throughput_mult") && ctr + 1 < args.length)
            {
                ctr++;
                INC_SUBMIT_MULT = Double.parseDouble(args[ctr]);
                VARIABLE_SUBMIT_RATE = true;
            }
            else if (args[ctr].equals("-increase_submit_throughput_sin"))
            {
                INC_SUBMIT_SIN = true;
                VARIABLE_SUBMIT_RATE = true;
            }

            else if (args[ctr].equals("-increase_submit_throughput_interval") && ctr + 1 < args.length)
            {
                ctr++;
                INC_SUBMIT_INTERVAL = (new Integer(args[ctr])).intValue();
                VARIABLE_SUBMIT_RATE = true;
            }

            //max_submit_throughput
            else if (args[ctr].equals("-locality") && ctr + 1 < args.length)
            {
                ctr++;
                LOCALITY = (new Integer(args[ctr])).intValue();;
            }

            else if (args[ctr].equals("-maxWallTimeMS") && ctr + 1 < args.length)
            {
                ctr++;
                MAX_WALL_TIME_MS = (new Integer(args[ctr])).intValue();;
            }
            else if (args[ctr].equals("-emulated_file_size_KB") && ctr + 1 < args.length)
            {
                ctr++;
                EMULATED_FILE_SIZE = (new Integer(args[ctr])).intValue();;
            }






            /*
            else if (args[ctr].equals("-factoryURI") && ctr + 1 < args.length)
            {
                ctr++;
                factoryURI = new String(args[ctr]);
            } */
            else if (args[ctr].equals("-config") && ctr + 1 < args.length)
            {
                ctr++;
                configFile = new String(args[ctr]);
            }
            else if (args[ctr].equals("-path") && ctr + 1 < args.length)
            {
                ctr++;
                path = new String(args[ctr]);
            }
            else if (args[ctr].equals("-result") && ctr + 1 < args.length)
            {
                ctr++;
                fileName = new String(args[ctr]);
            }
            else if (args[ctr].equals("-stackResult") && ctr + 1 < args.length)
            {
                ctr++;
                STACK_RESULT = new String(args[ctr]);
            }
            else if (args[ctr].equals("-job_description") && ctr + 1 < args.length)
            {
                ctr++;
                jobDescriptionFile = new String(args[ctr]);
            }
            else if (args[ctr].equals("-stack_description") && ctr + 1 < args.length)
            {
                ctr++;
                stackDescriptionFile = new String(args[ctr]);
            }

            //
            else if (args[ctr].equals("-num_execs") && ctr + 1 < args.length)
            {
                ctr++;
                num_execs = (new Integer(args[ctr])).intValue();
            }
            else if (args[ctr].equals("-num_threads") && ctr + 1 < args.length)
            {
                ctr++;
                MAX_NUM_THREADS = (new Integer(args[ctr])).intValue();
                //System.out.println("3: setting MAX_NUM_THREADS = " + MAX_NUM_THREADS);


                //System.out.println("Set MAX_NUM_THREADS to " + MAX_NUM_THREADS);
            }
            else if (args[ctr].equals("-num_submit_threads") && ctr + 1 < args.length)
            {
                ctr++;
                MAX_NUM_SUBMIT_THREADS = (new Integer(args[ctr])).intValue();
                //System.out.println("Set MAX_NUM_SUBMIT_THREADS to " + MAX_NUM_SUBMIT_THREADS);
            }
            else if (args[ctr].equals("-debug") )
            {//&& ctr + 1 < args.length)
                //ctr++;
                DEBUG = true;
            }
            else if (args[ctr].equals("-inMemory") )
            {//&& ctr + 1 < args.length)
                //ctr++;
                IN_MEMORY = true;
            }


            /*
            else if (args[ctr].equals("-taskPerfLog") )
            {//&& ctr + 1 < args.length)
                //ctr++;
                writeLogTaskPerfTest = true;
            } */


            else if (args[ctr].equals("-taskDescriptionLog") && ctr + 1 < args.length)
            {
                ctr++;
                writeLogTaskDescFile = new String(args[ctr]);
                writeLogTaskDescTest = true;
            }
            else if (args[ctr].equals("-taskPerfLog") && ctr + 1 < args.length)
            {
                ctr++;
                writeLogTaskPerfFile = new String(args[ctr]);
                writeLogTaskPerfTest = true;
            }

            else if (args[ctr].equals("-stacking") )
            {//&& ctr + 1 < args.length)
                //ctr++;
                STACKING = true;
            }
            else if (args[ctr].equals("-persist_objects") )
            {//&& ctr + 1 < args.length)
                //ctr++;
                PERSIST_OBJECTS = true;
            }

            else if (args[ctr].equals("-remote_scratch") && ctr + 1 < args.length)
            {
                ctr++;
                REMOTE_SCRATCH = new String(args[ctr]);
            }
            else if (args[ctr].equals("-height") && ctr + 1 < args.length)
            {
                ctr++;
                HEIGHT = (new Integer(args[ctr])).intValue();
            }
            else if (args[ctr].equals("-width") && ctr + 1 < args.length)
            {
                ctr++;
                WIDTH = (new Integer(args[ctr])).intValue();
            }
            else if (args[ctr].equals("-SDSS_index") && ctr + 1 < args.length)
            {
                ctr++;
                SDSS_INDEX = new String(args[ctr]);
            }
            else if (args[ctr].equals("-interactive") )
            {//&& ctr + 1 < args.length)
                //ctr++;
                INTERACTIVE = true;

            }
            else if (args[ctr].equals("-diperf") )
            {//&& ctr + 1 < args.length)
                //ctr++;
                DIPERF = true;

            }

            else if (args[ctr].equals("-summary") )
            {//&& ctr + 1 < args.length)
                //ctr++;
                SUMMARY = true;

            }
            else if (args[ctr].equals("-noinit") )
            {//&& ctr + 1 < args.length)
                //ctr++;
                NOINIT = true;

            }
            else if (args[ctr].equals("-single_resource") )
            {//&& ctr + 1 < args.length)
                //ctr++;
                SINGLE_RESOURCE = true;

            }
            else if (args[ctr].equals("-create_test") )
            {//&& ctr + 1 < args.length)
                //ctr++;
                CREATE_TEST = true;

            }
            else if (args[ctr].equals("-one_task_per_resource") )
            {//&& ctr + 1 < args.length)
                //ctr++;
                ONE_TASK_PER_RESOURCE = true;

            }


            /*
            else if (args[ctr].equals("-authenticate"))
            {
                AUTHENTICATE = true;

            } else if (args[ctr].equals("-authorize"))
            {
                AUTHORIZE = true;

            } else if (args[ctr].equals("-encrypt"))
            {
                ENCRYPT = true;

            } else if (args[ctr].equals("-sign"))
            {
                SIGN = true;

            } else if (args[ctr].equals("-TSL"))
            {
                TSL = true;

            } else if (args[ctr].equals("-MSG"))
            {
                MSG = true;

            } else if (args[ctr].equals("-CONV"))
            {
                CONV = true;

            }
            */
//            CLIENT_DESC

            else if (args[ctr].equals("-CLIENT_DESC") && ctr + 1 < args.length)
            {
                ctr++;
                CLIENT_DESC = new String(args[ctr]);
            }
            else if (args[ctr].equals("-MAX_EXECS_PER_WS") && ctr + 1 < args.length)
            {
                ctr++;
                MAX_COM_PER_WS = (new Integer(args[ctr])).intValue();
                MAX_COM_PER_WS2 = MAX_COM_PER_WS;
            }
            else if (args[ctr].equals("-dataCaching") )
            {//&& ctr + 1 < args.length)
                //ctr++;
                DATA_CACHING = true;

            }


            else if (args[ctr].equals("-help") )
            {//&& ctr + 1 < args.length)
                //ctr++;
                if (DEBUG) System.out.println("Help Screen:");
                usage(args);

            }
            else
            {

                System.out.println("ERROR: invalid parameter - " + args[ctr]);
                System.out.println("Current parameters values:");
                System.out.println("-epr " + fileEPR);
                System.out.println("-serviceURI " + serviceURI);
                System.out.println("-config " + configFile);
                System.out.println("-path " + path);
                System.out.println("-result " + fileName);
                System.out.println("-job_description " + jobDescriptionFile);
                System.out.println("-num_execs " + num_execs);
                System.out.println("-debug " + DEBUG);
                System.out.println("-diperf " + DIPERF);
                System.out.println("-summary " + SUMMARY);



                usage(args);
            }
        }


    }


    public void usage(String[] args)
    {
        System.out.println("Help Screen: ");
        System.out.println("-fi <>");
        System.out.println("-fo <>");
        System.out.println("-fc <>");
        System.out.println("-ft <>");
        System.out.println("-nf <>");
        System.out.println("-nr <>");
        System.out.println("-fpi <>");
        System.out.println("-fpo <>");
        System.out.println("-x <>");
        System.out.println("-y <>");
        System.out.println("-h <>");
        System.out.println("-w <>");
        System.out.println("-i <>");
        System.out.println("-o <>");
        System.out.println("-d1 <>");
        System.out.println("-d2 <>");
        System.out.println("-gz <>");
        System.out.println("-diperf <>");
        System.out.println("-help <>");
        System.exit(0);

    }


    public String getMachNamePort(int recvPort)
    {
        String machName = new String("localhost:"+recvPort);
        try
        {
            String method = new String("overide");
            if (NOTIFICATION_ENDPOINT == null)
            {
                NOTIFICATION_ENDPOINT = java.net.InetAddress.getLocalHost().getCanonicalHostName();
                method = new String("automatic");
            }
            machName = NOTIFICATION_ENDPOINT +  ":" + recvPort;
            System.out.println("Notification Endpoint (" + method + "): " + NOTIFICATION_ENDPOINT);
        }
        catch (Exception e)
        {
            System.out.println("WORKER: ERROR: java.net.InetAddress.getLocalHost().getHostName() failed " + e);
            if (DEBUG) e.printStackTrace();
        }
        return machName;
    }




/*
    <xsd:complexType name="Task">
          <xsd:sequence>
        <xsd:element name="fileName" type="xsd:string"/> 
        <xsd:element name="tuple" type="tns:Tuple"/> 
        <xsd:element name="x_coord" type="xsd:int"/> 
        <xsd:element name="y_coord" type="xsd:int"/> 
           </xsd:sequence>
     </xsd:complexType>
     */
    String taskToString(Task task)
    {
        //String s = "[" + t.getFileName() + ", " + t.getX_coord() + "x" + t.getY_coord() + ", " + tupleToString(t.getTuple()) + "]";
        //return s;

        Executable exec = task.getExecutable();
        String s = executableToString(exec);
        s += "EXIT_CODE: " + task.getExitCode() + "\n";
        s += "STDOUT:\n";
        s += task.getStdout() + "\n";
        s += "STDERR:\n";
        s += task.getStderr() + "\n";

        return s;





    }

/*
    <xsd:complexType name="Tuple">
          <xsd:sequence>
        <xsd:element name="band" type="xsd:string"/> 
        <xsd:element name="dec" type="xsd:double"/> 
        <xsd:element name="ra" type="xsd:double"/> 
           </xsd:sequence>
     </xsd:complexType>
     */
    String executableToString(Executable exec)
    {
        String com = exec.getCommand();
        String args[] = exec.getArguements();
        String env[] = exec.getEnvironment();
        String dir = exec.getDirectory();

        String s = new String("Executing: ");
        if (com != null)
        {
            s += com + " ";

            if (args != null)
            {

                for (int i=0;i<args.length;i++)
                {
                    s += args[i] + " ";

                } 
                s += "\n";
            }
            else
                s += "\n";

            s += "Environment:\n";
            if (env != null)
            {

                for (int i=0;i<env.length;i++)
                {
                    s += env[i] + "\n";

                }
            }
            else
            {
                //s += "\n";
            }

            s += "Working Directory: ";
            if (dir != null)
            {

                s += dir + "\n";
            }
            else
                s += "\n";


        }
        else
        {
            s += "null\n";
            s += "Environment: null\n";
            s += "Working Directory: null\n";
        }

        return s;

    }

    List tuplesNotFound = Collections.synchronizedList(new LinkedList());
    List tuplesParseError = Collections.synchronizedList(new LinkedList());

    public static KDTreeIndex sdssIndex = null;
    public static boolean indexValid = false;
    public static boolean indexFailed = false;

    public boolean initSDSSIndex()
    {
        String fName = SDSS_INDEX;
        System.out.println("Loading index from '" + fName + "' this might take some time, depending on the size of the index... expect about 10000~20000 entries per second...");
        try
        {

            sdssIndex = new KDTreeIndex(fName);
        }
        catch (Exception e)
        {
            if (DEBUG) e.printStackTrace();
            System.out.println("Failed to loaded index from file '" + fName + "'...  exiting...");
            indexFailed = true;
            System.exit(-1);
        }


        if (sdssIndex.isValid)
        {

            System.out.println("Index loaded successfully");
            indexValid = true;
            return true;
        }
        else
        {

            System.out.println("Failed to loaded index from file '" + fName + "'...  exiting...");
            indexFailed = true;
            System.exit(-1);
        }

        return false;

    }

    public class AstroDataCachingNames
    {
        public String inputLogical;
        public String inputURL;
        public String outputLogical;
        public String outputURL;

        public AstroDataCachingNames(Entry entry, int height, int width, String remote_scratch, boolean persistOutput, boolean caching)
        {


            if (!caching)
            {
                inputLogical = new String("null");
                inputURL = entry.image;
                outputLogical = new String("SDSS_DR5_OBJECT_" + entry.coord[0] + "_" + entry.coord[1] + "_" + entry.band + "_" + height + "x" + width+".fit");
                outputURL = new String(remote_scratch + "/" + outputLogical + ".fit");
            }
            else
            {
                String tokens[] = ((entry.image).trim()).split("/");

                if (tokens.length > 0)
                {
                    inputLogical = new String("SDSS_DR5_FILE_"+(entry.image).hashCode()+".fit");
                }
                else
                    inputLogical = new String("SDSS_DR5_FILE_"+(entry.image).hashCode()+"_"+tokens[tokens.length - 1]+".fit");

                inputURL = new String(entry.image);
                outputLogical = new String("SDSS_DR5_OBJECT_" + entry.coord[0] + "_" + entry.coord[1] + "_" + entry.band + "_" + height + "x" + width+".fit");
                if (persistOutput)
                {
                    outputURL = new String(remote_scratch + "/" + outputLogical + ".fit");

                }
                else
                    outputURL = new String("null");


            }




        }

        public AstroDataCachingNames(String logical, String url, String remote_scratch, boolean persistOutput, boolean caching)
        {


            if (!caching)
            {
                inputLogical = new String("null");
                inputURL = new String("null");
                outputLogical = new String("null");
                outputURL = new String(logical);
            }
            else
            {
                inputLogical = new String("null");
                inputURL = new String("null");

                outputLogical = new String(logical);
                if (persistOutput)
                {
                    outputURL = new String(remote_scratch + "/" + outputLogical + ".fit");

                }
                else
                    outputURL = new String("null");


            }




        }


    }

    Map cachingInfoMap = Collections.synchronizedMap(new HashMap());
    Map cachingStackingMap = Collections.synchronizedMap(new HashMap());

    List listToStack = Collections.synchronizedList(new LinkedList());
    List listCutoutFailed = Collections.synchronizedList(new LinkedList());


    public String getExecString(String tuple)
    {
        String tokens[] = (tuple.trim()).split(" ");
        if (tokens.length == 3)
        {
            try
            {
                double ra = Double.parseDouble(tokens[0].trim());
                double dec = Double.parseDouble(tokens[1].trim());
                char band = (tokens[2].trim()).charAt(0);



                try
                {


                    if (DEBUG) System.out.println("Performing query: {" + ra + ", " + dec + ", "+band+"}");


                    Entry entry = sdssIndex.lookupQuerySkyCal(ra, dec, band);

                    if (entry.valid)
                    {

                        if (DEBUG) System.out.println("Query result: {" + ra + ", " + dec + ", "+band+"} ==> ("+entry.coord[0]+"x"+entry.coord[1]+")");
                        entry.band = band;

                        //java Cutout /home/iraicu/java/svn/falkon/client/workloads/astro/files/fpC-004576-u6-0817.fit 245.876148 19.905431 u 100 100 1.0 1.0 /home/iraicu/java/svn/falkon/client/workloads/astro/files/out1_100x100.fit
                        double sky = 1.0;
                        double cal = 1.0;

                        //u g r i z

                        switch (entry.band)
                        {
                        case 'u': 
                            sky = entry.sky[0];
                            cal = entry.cal[0];
                            break;
                        case 'g': 
                            sky = entry.sky[1];
                            cal = entry.cal[1];
                            break;
                        case 'r': 
                            sky = entry.sky[2];
                            cal = entry.cal[2];
                            break;
                        case 'i': 
                            sky = entry.sky[3];
                            cal = entry.cal[3];
                            break;
                        case 'z': 
                            sky = entry.sky[4];
                            cal = entry.cal[4];
                            break;
                        default:
                            tuplesNotFound.add(tuple + " : not found in RTree Index due to invalid band '" + entry.band + "'");

                        }

                        AstroDataCachingNames adcn = new AstroDataCachingNames(entry, HEIGHT, WIDTH, REMOTE_SCRATCH, PERSIST_OBJECTS, DATA_CACHING);

                        String exec = null;
                        if (!DATA_CACHING)
                        {
                            //String ouputLogicalName = new String("SDSS_DR5_OBJECT_" + entry.coord[0] + "_" + entry.coord[1] + "_" + entry.band + "_" + HEIGHT + "x" + WIDTH);
                            //String ouputURLName = new String(REMOTE_SCRATCH + "/" + ouputLogicalName + ".fit");
                            exec = new String("java Cutout " + adcn.inputURL + " " + entry.coord[0] + " " + entry.coord[1] + " " + entry.band + " " + HEIGHT + " " + WIDTH + " " + sky + " " + cal + " " + adcn.outputURL);
                        }
                        else
                        {

                            exec = new String("java Cutout " + adcn.inputLogical + " " + entry.coord[0] + " " + entry.coord[1] + " " + entry.band + " " + HEIGHT + " " + WIDTH + " " + sky + " " + cal + " " + adcn.outputLogical + " %%% " + adcn.inputLogical + " %%% " + adcn.inputURL + " %%% " + adcn.outputLogical + " %%% " + adcn.outputURL + " %%%");




                        }


                        cachingInfoMap.put(exec, adcn);

                        return exec;





                        //resultsFound++;
                    }
                    else
                    {
                        tuplesNotFound.add(tuple + " : not found in RTree Index");

                    }
                }
                catch (Exception e)
                {
                    if (DEBUG) e.printStackTrace();
                    tuplesNotFound.add(tuple + " : " + e.getMessage());
                }



            }
            catch (Exception e)
            {

                if (DEBUG) e.printStackTrace();

                tuplesParseError.add(tuple + " : " + e.getMessage());
            }

        }
        else
        {
            tuplesParseError.add(tuple + " : invalid number of arguements");

        }

        return null;

    }

    public int readStacksDescription(String fileName)
    {
        System.out.println("Reading file: " + stackDescriptionFile + "... ");
        //numExecs = 0;
        //String execs[] = null;
        try
        {
            int numExecs = Math.min(numTuples(stackDescriptionFile)*LOCALITY, num_execs);
            //execs = new String[numExecs];
            int execsIndex = 0;

            for (int i=0;i<LOCALITY;i++)
            {



                BufferedReader in2 = new BufferedReader(new FileReader(stackDescriptionFile));
                String tuple;
                //String exec;
                int curIndex = 0;
                while ((tuple = in2.readLine()) != null && curIndex < Math.ceil(numExecs/LOCALITY))
                {
                    String exec = getExecString(tuple);

                    if (exec != null)
                    {


                        execQ.insert(exec);
                        execsIndex++;
                        curIndex++;
                    }
                    else
                    {
                        if (DEBUG) System.out.println("getExecString(" + tuple + ") failed...");

                    }
                }
                in2.close();
            }
            return execsIndex;
        }
        catch (Exception e)
        {
            System.out.println("Error in reading file: " + stackDescriptionFile + "... :" + e);
            e.printStackTrace();

        }
        return 0;

    }


    public int readFileDescription(String fileName)
    {
        System.out.println("Reading file: " + fileName + "... ");
        //numExecs = 0;
        //String execs[] = null;
        try
        {
            int numExecs = Math.min(numTuples(fileName), num_execs);
            //execs = new String[numExecs];
            int execsIndex = 0;

            BufferedReader in2 = new BufferedReader(new FileReader(fileName));
            String exec;
            while ((exec = in2.readLine()) != null && execsIndex < numExecs)
            {
                execQ.insert(new String(exec));
                execsIndex++;
            }
            in2.close();

            int numUsers = (int)Math.min(numExecs, MAX_NUM_THREADS);
            for (int i=0;i<numUsers;i++)
            {
                if (DEBUG) System.out.println("NO_MORE_WORK : 1");
                execQ.insert(UserRunThread.NO_MORE_WORK);
            }


            return execsIndex;
        }
        catch (Exception e)
        {
            System.out.println("Error in reading file: " + fileName + "... :" + e);
            e.printStackTrace();

        }
        return 0;

    }

    public int readFileDescriptionLocality(String fileName)
    {
        System.out.println("Reading file: " + fileName + "... ");
        //numExecs = 0;
        //String execs[] = null;
        try
        {
            int numExecs = Math.min(numTuples(fileName)*LOCALITY, num_execs);
            //execs = new String[numExecs];
            int execsIndex = 0;

            for (int i=0;i<LOCALITY;i++)
            {



                BufferedReader in2 = new BufferedReader(new FileReader(fileName));
                String exec;
                int curIndex = 0;
                while ((exec = in2.readLine()) != null && curIndex < Math.ceil(numExecs/LOCALITY))
                {
                    execQ.insert(new String(exec));
                    execsIndex++;
                    curIndex++;
                }
                in2.close();
            }
            return execsIndex;
        }
        catch (Exception e)
        {
            System.out.println("Error in reading file: " + fileName + "... :" + e);
            e.printStackTrace();

        }
        return 0;

    }



    public List doStackingRound(int minStackSize, int maxStackSize, boolean useSquareRoot, StopWatch fsTimer, List finalListToStack)
    {
        Random rand = new Random();
        if (DEBUG) System.out.println("creating final stacking resource...");
        ThreadState cTS = createResource(serviceURI);

        fsTimer.start();

        UserRunThread userThread = new UserRunThread(this, cTS, fsTimer);


        if (DEBUG) System.out.println("starting final stacking thread...");
        userThread.start();


        //int lastRecvTasks = userThreads[0].getRecvTasks();

        int sizeToStack = (int)Math.max(Math.min(Math.ceil(Math.sqrt(finalListToStack.size())), maxStackSize), minStackSize);



        List nextStacking = new LinkedList();


        while (finalListToStack.size() > 0)
        {

            int upperIndex = (int)Math.min(sizeToStack, finalListToStack.size());

            List curStacking = new LinkedList();

            while (curStacking.size() < upperIndex && finalListToStack.size() > 0)
            {
                try
                {

                    curStacking.add(finalListToStack.remove(0));
                }
                catch (Exception e)
                {
                    if (DEBUG) e.printStackTrace();
                    break;
                }
            }

            //synchronized (finalListToStack)
            //{
            //int upperIndex = (int)Math.min(sizeToStack, finalListToStack.size());
            //List curStacking = new LinkedList(finalListToStack.subList(0, upperIndex));
            //finalListToStack.subList(0, upperIndex).clear();
            //}
            if (curStacking.size() >= 2)
            {


                if (DEBUG) System.out.println("");
                //if (DEBUG) System.out.println("Assembling final stacking of " + listToStack.size() + " images...");
                if (DEBUG) System.out.println("Assembling final stacking of " + curStacking.size() + " images...");
                String exec = new String("java Stack " + HEIGHT + " " + WIDTH);

                if (DATA_CACHING)
                {
                    //java Stack 100 100 /home/iraicu/java/svn/falkon/client/workloads/astro/files/out1_100x100.fit /home/iraicu/java/svn/falkon/client/workloads/astro/files/out2_100x100.fit /home/iraicu/java/svn/falkon/client/workloads/astro/files/out3_100x100.fit /home/iraicu/java/svn/falkon/client/workloads/astro/files/out4_100x100.fit /home/iraicu/java/svn/falkon/client/workloads/astro/files/out5_100x100.fit /home/iraicu/java/svn/falkon/client/workloads/astro/files/out6_100x100.fit /home/iraicu/java/svn/falkon/client/workloads/astro/files/out7_100x100.fit /home/iraicu/java/svn/falkon/client/workloads/astro/files/out8_100x100.fit /home/iraicu/java/svn/falkon/client/workloads/astro/files/out9_100x100.fit /home/iraicu/java/svn/falkon/client/workloads/astro/files/out10_100x100.fit /home/iraicu/java/svn/falkon/client/workloads/astro/files/final_100x100.fit
                    // For a set or list
                    for (Iterator it=curStacking.iterator(); it.hasNext(); )
                    {
                        AstroDataCachingNames adcn = (AstroDataCachingNames)it.next();
                        //exec = exec + " " + adcn.outputLogical;
                        //exec = exec.concat(" " + adcn.outputLogical);

                        if (adcn != null)
                        {
                            if (adcn.outputURL != null)
                            {
                                exec = exec.concat(" " + adcn.outputLogical);
                            }
                            else
                            {
                                if (!DEBUG) System.out.println("adcn.outputURL is null, ignoring...");

                            }

                        }
                        else
                        {
                            if (!DEBUG) System.out.println("adcn is null, ignoring...");

                        }
                    }

                    String stackResultLogical = new String(System.currentTimeMillis()+"_"+rand.nextInt((int)Math.pow(2,31)) + "_"+STACK_RESULT);
                    //exec = exec + " " + stackResultLogical + " %%%";
                    exec = exec.concat(" " + stackResultLogical + " %%%");

                    for (Iterator it=curStacking.iterator(); it.hasNext(); )
                    {
                        AstroDataCachingNames adcn = (AstroDataCachingNames)it.next();
                        //exec = exec + " " + adcn.outputLogical;

                        //exec = exec.concat(" " + adcn.outputLogical);


                        if (adcn != null)
                        {
                            if (adcn.outputURL != null)
                            {
                                exec = exec.concat(" " + adcn.outputLogical);
                            }
                            else
                            {
                                if (!DEBUG) System.out.println("adcn.outputURL is null, ignoring by entering null...");
                                exec = exec.concat(" null");

                            }

                        }
                        else
                        {
                            if (!DEBUG) System.out.println("adcn is null, ignoring by entering null...");
                            exec = exec.concat(" null");

                        }

                    }

                    //exec = exec + " %%%";
                    exec = exec.concat(" %%%");

                    for (int i=0;i<curStacking.size();i++)
                    {
                        //exec = exec + " null";
                        exec = exec.concat(" null");
                    }

                    //exec = exec + " %%% " + stackResultLogical + " %%% " + REMOTE_SCRATCH+"/"+stackResultLogical + " %%%";

                    String stackResultURL = new String(REMOTE_SCRATCH+"/"+stackResultLogical);

                    exec = exec.concat(" %%% " + stackResultLogical + " %%% " + stackResultURL + " %%%");


                    if (DEBUG) System.out.println("Sending final stacking (caching): " + exec);

                    //Entry curEntry = new Entry();
                    AstroDataCachingNames curAdcn = new AstroDataCachingNames(stackResultLogical, stackResultURL, REMOTE_SCRATCH, PERSIST_OBJECTS, DATA_CACHING);
                    nextStacking.add(curAdcn);

                }
                else
                {

                    // For a set or list
                    for (Iterator it=curStacking.iterator(); it.hasNext(); )
                    {
                        AstroDataCachingNames adcn = (AstroDataCachingNames)it.next();
                        if (adcn != null)
                        {
                            if (adcn.outputURL != null)
                            {
                                exec = exec.concat(" " + adcn.outputURL);
                            }
                            else
                            {
                                if (!DEBUG) System.out.println("adcn.outputURL is null, ignoring...");

                            }

                        }
                        else
                        {
                            if (!DEBUG) System.out.println("adcn is null, ignoring...");

                        }

                        //exec = exec + " " + adcn.outputURL;
                    }

                    String stackResultURL = new String(REMOTE_SCRATCH+"/"+System.currentTimeMillis()+"_"+rand.nextInt((int)Math.pow(2,31)) +"_"+STACK_RESULT);

                    exec = exec.concat(" " + stackResultURL);
                    //exec = exec + " " + REMOTE_SCRATCH+"/"+STACK_RESULT;
                    if (DEBUG) System.out.println("Sending final stacking: " + exec);

                    AstroDataCachingNames curAdcn = new AstroDataCachingNames(stackResultURL, /*this field should be ignored*/stackResultURL, REMOTE_SCRATCH, PERSIST_OBJECTS, DATA_CACHING);
                    nextStacking.add(curAdcn);


                    //}


                }


                // Add special end-of-stream markers to terminate the workers
                //Executable finalStack = userThreads[0].getFinalStack(exec);
                //if (finalStack == null)
                //{
                //    System.out.println("getFinalStack("+exec+") failed... aborting!");
                //}
                //else
                //{
                if (DEBUG) System.out.println("submitting final stacking...");

                //execQ.insert(finalStack);
                execQ.insert(exec);

            }
            //there was only 1 stack left...
            else if (curStacking.size() == 1)
            {
                if (DEBUG) System.out.println("No need to assembling final stacking for " + curStacking.size() + " image...");

                AstroDataCachingNames adcn = (AstroDataCachingNames)curStacking.get(0);
                //AstroDataCachingNames curAdcn = new AstroDataCachingNames(stackResultURL, /*this field should be ignored*/stackResultURL, REMOTE_SCRATCH, PERSIST_OBJECTS, DATA_CACHING);
                nextStacking.add(adcn);


            }
            else
            {
                if (DEBUG) System.out.println("No stackings found, should never reach this... " + curStacking.size() + " images...");
                //nothing to do...
            }



        }

        if (DEBUG) System.out.println("NO_MORE_WORK : 2");
        execQ.insert(UserRunThread.NO_MORE_WORK);



        /*
        while (userThreads[0].isAlive() && !userThreads[0].doneSending && userThreads[0].getRecvTasks() < 1 + lastRecvTasks)
        {
            try
            {

                //System.out.println("***: " + userThreads[0].isAlive() + " " + userThreads[0].doneSending + " " + userThreads[0].getRecvTasks() + " " + userThreads[0].getSendTasks());

            Thread.sleep(1000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } */
        System.out.println("Waiting for stacking round to complete...");
        try
        {

            userThread.join();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("there was an error in waiting for the stacking round to complete... double check the output...");

        }
        if (nextStacking.size() == 1)
        {
            finalCTS = cTS;
            finalUserThread = userThread;
        }
        else
        {

            if (DEBUG) System.out.println("Destroying Final Stacking Resource...");
            if (DEBUG) System.out.println("destroyGetResults(): 1");
            userThread.gr.destroyGetResults();
            destroyResource(cTS.instanceEPR);
            if (DEBUG) System.out.println("Destroy finished!");

        }

        return nextStacking;


        /////////////////perform final level stacking


    }

    public ThreadState finalCTS = null;
    public UserRunThread finalUserThread = null;

    String FALKON_LOGS = null;

    public VariableSubmitThrottle vst = null;

    //public void run(String[] args)
    public void main_run()
    {
        //GPPortType gp = null;

        try
        {



            StopWatch sw = new StopWatch();
            StopWatch lt = new StopWatch();

            StopWatch initIndexTimer = new StopWatch();
            StopWatch lookupCoordinatesTimer = new StopWatch();



            StopWatch jobTime = new StopWatch();

            sw.reset();
            sw.start();

            //homeEPR = createResource(serviceURI);
            if (MAX_NUM_THREADS <1)
            {
                System.out.println("invalid number of threads: " + MAX_NUM_THREADS);
                System.exit(0);
            }



            int numExecs = 0;
            if (INTERACTIVE == false)
            {

                if (STACKING)
                {
                    initIndexTimer.start();
                    System.out.println("USER: Initializing SDSS DR5 index...");

                    if (!initSDSSIndex())
                    {
                        System.out.println("USER: Initializing SDSS DR5 index failed, exiting!");
                        System.exit(-1);

                    }
                    initIndexTimer.stop();

                    if (DEBUG) System.out.println("USER: Reading User Stacking Description from file '" + stackDescriptionFile + "'...");
                    //sw.start();
                    System.out.println("Starting non-interactive mode....");
                    lookupCoordinatesTimer.start();
                    numExecs = readStacksDescription(stackDescriptionFile);
                    lookupCoordinatesTimer.stop();
                    //this.jobTime.start();
                    NUM_TASKS = numExecs;

                }
                else
                {

                    if (DEBUG) System.out.println("USER: Reading User Job Description from file '" + jobDescriptionFile + "'...");
                    //sw.start();
                    System.out.println("Starting non-interactive mode....");


                    if (IN_MEMORY)
                    {
                        numExecs = readFileDescription(jobDescriptionFile);
                        System.out.println("Finished reading " + numExecs + " tasks in memory....");
                    }
                    else
                    {
                        numExecs = Math.min(numTuples(jobDescriptionFile), num_execs);
                        //start some thread to read task descriptions
                        ReadFileDescriptionThread readFileDescThread = new ReadFileDescriptionThread(this);
                        readFileDescThread.start();

                    }
                    //this.jobTime.start();
                    NUM_TASKS = numExecs;
                }


                if (numExecs < 1)
                {
                    System.out.println("There were no commands to process... probably there was nothing in the file...");
                    System.exit(0);
                }


            }



            if (POLL_INTERVAL > 0 && monitorThread == null)
            {
                monitorThread = new MonitorThread(this, POLL_INTERVAL);

                monitorThread.start();
                System.out.println("");
            }


            int numResources = 1;
            if (SINGLE_RESOURCE)
            {
                numResources  = 1;
            }
            else
                numResources = (int)Math.min(MAX_NUM_THREADS, numExecs);

            ThreadState clientTS[] = new ThreadState[numResources];

            //EndpointReferenceType clientEPR[] = new EndpointReferenceType[numResources];
            //GPPortType clientGP[] = new GPPortType[numResources];

            boolean setSecurity = false;

            if (CLIENT_DESC != null && (new File(CLIENT_DESC)).exists())
            {
                setSecurity = true;

            }







            //this needs to be parallized
            /*
            for (int i=0;i<numResources;i++)
            {

                sw.reset();
                sw.start();
                if (!ONE_TASK_PER_RESOURCE)
                //clientTS[i] = new ThreadState();
                //else
                {

                    if (serviceURIs != null)
                    {
                        clientTS[i] = createResource(serviceURIs[i]);
                    }
                    else

                    {
                        clientTS[i] = createResource(serviceURI);
                    }

                    sw.stop();
                    if (DEBUG) System.out.println("Created resource " + i + " : "+sw.getElapsedTime() + " ms");
                    else if (this.POLL_INTERVAL <= 0) System.out.print(".");
                }


                //clientEPR[i] = ts.instanceEPR;
                //clientGP[i] = ts.gp;

            }

            */
            //parallel version
            sw.reset();
            sw.start();

            CreateResourceThread crt[] = new CreateResourceThread[numResources];

            for (int i=0;i<numResources;i++)
            {

                if (serviceURIs != null)
                {
                    //clientTS[i] = createResource(serviceURIs[i]);
                    crt[i] = new CreateResourceThread(serviceURIs[i], i, clientTS, this);
                    crt[i].start();
                }
                else
                {
                    //clientTS[i] = createResource(serviceURI);
                    crt[i] = new CreateResourceThread(serviceURI, i, clientTS, this);
                    crt[i].start();
                }
            }

            for (int i=0;i<numResources;i++)
            {
                crt[i].join();
            }


            int numResourcesOK = 0;
            for (int i=0;i<clientTS.length;i++)
            {
                if (clientTS[i] != null)
                {
                    numResourcesOK++;


                }
            }

            //System.out.println("Created " + numResourcesOK + " resources in "+sw.getElapsedTime() + " ms");




            sw.stop();
            System.out.println("Created " + numResourcesOK + " resources of " + numResources + " in "+sw.getElapsedTime() + " ms");
            if (numResources - numResourcesOK > 0)
            {
                System.out.println("Failed to establish a connection with " + (numResources - numResourcesOK) +  " resources...");
            }



            int index = 0;

            if (numResourcesOK != numResources)
            {
                ThreadState tClientTS[] = new ThreadState[numResourcesOK];

                for (int i=0;i<clientTS.length;i++)
                {

                    if (clientTS[i] != null)
                    {

                        tClientTS[index] = clientTS[i];
                        index++;
                    }
                }
                clientTS = null;
                clientTS = tClientTS;

            }


            if (numResources == MAX_NUM_THREADS)
            {
                MAX_NUM_THREADS = numResourcesOK;

            }

            numResources = numResourcesOK;

            if (numResources == 0)
            {
                System.out.println("No resources found, exiting...");
                System.exit(0);
            }



            if (this.POLL_INTERVAL <= 0) System.out.println("");

            //sw.stop();
            //if (DEBUG) System.out.println("USER: Get ProtType (" + sw.getElapsedTime() + "ms)");
            //if (DIPERF) System.out.println("USER:getGPPortType(): " + sw.getElapsedTime() + " ms");

            sw.reset();
            /*
            if (CREATE_TEST && !ONE_TASK_PER_RESOURCE)
            {
                System.out.println("CREATE_TEST... sleeping for 2 min to wait for all resources to be created before it attempts to destroy the resources...");
                try
                {

                    Thread.sleep(120000);
                }
                catch (Exception sleepError)
                {
                    System.out.println("Exited sleep early...." + sleepError);
                    sleepError.printStackTrace();
                }
                System.out.println("CREATE_TEST: destroying resources...");
                sw.reset();
                sw.start();

                DestroyResourceThread drt[] = new DestroyResourceThread[numResources];

                for (int i=0;i<numResources;i++)
                {

                    //destroyResource(clientTS[i].instanceEPR);
                    drt[i] = new DestroyResourceThread(clientTS[i], this); 
                    drt[i].start();

                }

                for (int i=0;i<numResources;i++)
                {

                    drt[i].join();

                }


                sw.stop();
                System.out.println("Destroyed " + numResources + " resources in "+sw.getElapsedTime() + " ms");
                //else
                //    if (this.POLL_INTERVAL <= 0) System.out.print("X");


                System.exit(0);
            }

                     */

            //populate the job description...


            //UserJob job = new UserJob(readUserJob(jobDescriptionFile));



            //if ((INTERACTIVE == false && numExecs > 0) || INTERACTIVE == true)
            //{

            //if (numExecs > 0)
            //{

            //System.out.println("Processing " + numExecs + " executables... ");

            if (VARIABLE_SUBMIT_RATE)
            {
                //start a thread to monitor and update the submit throttle...
                vst = new VariableSubmitThrottle(this);
                vst.start();
            }





            long sTime = System.currentTimeMillis();
            //String results[] = doExecutables(execs);
            this.jobTime.reset();
            this.jobTime.start();



            //int numTasksToDo = numExecs;

            // Create a set of worker threads
            //int numUsers = min(MAX_NUM_THREADS, numTasksToDo);


            int numUsers = (int)Math.min(numExecs, MAX_NUM_THREADS);
            UserRunThread[] userThreads = new UserRunThread[numUsers];

            if (DEBUG) System.out.println(numUsers + " readThreads initialized...");


            Thread killListen = new KillListener(this, userThreads);
            if (DEBUG) System.out.println("WORKER: killListen Thread starting...");

            killListen.start();
            if (DEBUG) System.out.println("WORKER: killListen Thread started!");


            //taskTimer.start();
            for (int i=0; i<userThreads.length; i++)
            {
                if (ONE_TASK_PER_RESOURCE)
                    userThreads[i] = new UserRunThread(this, lt);
                else
                {
                    if (userThreads.length == numResources)
                    {
                        userThreads[i] = new UserRunThread(this, clientTS[i], lt);
                    }
                    else
                    {
                        //ThreadState tClientTS = new ThreadState(clientTS[0]);
                        //ThreadState tClientTS = ((ThreadState)clientTS[0]).clone();
                        //ThreadState tClientTS = (ThreadState)clientTS[0].clone();
                        //  userThreads[i] = new UserRunThread(this, tClientTS, lt);
                        userThreads[i] = new UserRunThread(this, clientTS[0], lt);

                    }


                }
                // Add special end-of-stream markers to terminate the workers
                //if (INTERACTIVE == false)
                //{
                //    if (IN_MEMORY)
                //    {

                //    execQ.insert(UserRunThread.NO_MORE_WORK);
                //    }
                //}

                userThreads[i].start();
            }
            if (DEBUG) if (DIPERF == false) System.out.println("readThreads started...");


            if (DEBUG) if (DIPERF == false) System.out.println("waiting for readThreads to finish...");

            if (INTERACTIVE == false)
            {

                for (int i=0;i<userThreads.length;i++)
                {
                    // Wait indefinitely for the thread to finish
                    try
                    {
                        if (userThreads[i].isAlive() && userThreads[i].doneSending && userThreads[i].getRecvTasks() == 0 && userThreads[i].getSendTasks() == 0 )
                        {
                            //if (!STACKING)
                            //{

                            if (DEBUG) System.out.println("destroyGetResults(): 2");
                            userThreads[i].gr.destroyGetResults();
                            if (DEBUG) System.out.println("UserRunThread # "  + i + " destroyed!");
                            //}
                            //else System.out.print("x");
                            //userThreads[i].join();
                            //userThreads[i].interrupt();
                            //if (DEBUG) System.out.println("UserRunThread # "  + i + " finished!");
                            //else System.out.print("-");

                        }
                        else
                        {

                            userThreads[i].join();
                            if (DEBUG) System.out.println("UserRunThread # "  + i + " finished!");// + ": userThreads #" + userThreads[i].getID(machID));// + ": State = " + readThreads[i].State);
                            //else System.out.print("-");
                        }
                        // Finished
                    }
                    catch (InterruptedException e)
                    {
                        if (DEBUG) if (DIPERF == false) System.out.println("Error in UserRunThread # "  + i + " : " + e);// + ": userThreads #" + userThreads[i].getID(machID)/* + ": State = " + readThreads[i].State*/ + ": was interupted :(");
                        e.printStackTrace();

                        // Thread was interrupted
                    }
                }

                //if (!DEBUG)
                //{
                if (DEBUG) System.out.println("");
                //}

                /*
            if (POLL_INTERVAL > 0)
            {
                //System.out.println("time_ms " + jobTime.getElapsedTime() + " pending_notifications_received " + getNumNotifications() + " tasks_completed " + getRecvTasks() + " total_tasks " + getSendTasks() + " percent_done " + (getRecvTasks()*100.0/getSendTasks()) + " ETA " + (jobTime.getElapsedTime()*1.0/getRecvTasks())*(getSendTasks() - getRecvTasks()));


                //System.out.println(user.COMMENT3  + " time " + user.jobTime.getElapsedTime()*1.0/1000.0 + " tasks_success " + user.getSuccessExecs() + " tasks_failed " + user.getFailedExecs() + " tasks_sent " + user.getSendTasks() + " completed 0.0 tasks_tp 0.0 aver_tp 0.0 stdev_tp 0.0 ETA ?");
                System.out.println(COMMENT3 + " time3 " + this.jobTime.getElapsedTime()*1.0/1000.0 + " tasks_success " + getSuccessExecs() + " tasks_failed " + getFailedExecs() + " tasks_sent " + getSendTasks() + " completed " + Math.round((getRecvTasks()*100.0/getSendTasks())*100)/100.0 + " tasks_tp 0.0 aver_tp " + Math.round(monitorThread.statCalc.getMean()*100)/100.0 + " stdev_tp " + Math.round(monitorThread.statCalc.getStandardDeviation()*1000)/1000.0 + " ETA " + (Math.round(((this.jobTime.getElapsedTime()*1.0/getRecvTasks())*(getSendTasks() - getRecvTasks())))/1000.0));
                //System.out.println(COMMENT3 + " time " + this.jobTime.getElapsedTime()*1.0/1000.0 + " pend_not_queue " + getNumNotifications() + " tasks_recv " + getRecvTasks() + " tasks_sent " + getSendTasks() + " completed " + Math.round((getRecvTasks()*100.0/getSendTasks())*100)/100.0 + " not_tp 0.0 tasks_tp 0.0 ETA 0.0 aver_tp " + Math.round(monitorThread.statCalc.getMean()*100)/100.0 + " stdev_tp " + Math.round(monitorThread.statCalc.getStandardDeviation()*1000)/1000.0);

                System.out.println("");
            }    */

                //get other summary statistics from service...
                MonitorState ms = new MonitorState(new String(""));
                MonitorStateResponse msr = null;
                try
                {

                    msr = clientTS[0].gp.monitorState(ms);
                }
                catch (Exception ee)
                {
                    if (DEBUG)
                    {
                        ee.printStackTrace();
                    }

                }



                if (STACKING)
                {
                    long cutoutTime = this.jobTime.getElapsedTime();


                    StopWatch fsTimer = new StopWatch();
                    //fsTimer.start();
                    fsTimer.reset();


                    int minStackSize = 10;
                    int maxStackSize = 100;
                    boolean useSquareRoot = true;

                    int totNumStackings = listToStack.size();
                    //List finalListToStack = doStackingRound(maxStackSize, useSquareRoot, fsTimer, listToStack);

                    //List finalListToStack = new LinkedList(


                    while (listToStack.size() > 1)
                    {
                        System.out.println("doing " + listToStack.size() + " stackings...");
                        listToStack = doStackingRound(minStackSize, maxStackSize, useSquareRoot, fsTimer, listToStack);
                        System.out.println("still " + listToStack.size() + " stackings to do...");
                        try
                        {
                            //Thread.sleep(5000);

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                    }

                    if (listToStack.size() == 1)
                    {
                        AstroDataCachingNames adcn = (AstroDataCachingNames)listToStack.remove(0);
                        System.out.println("Completed stacking, final output can be found at: " + adcn.outputLogical + " ==> " + adcn.outputURL);
                    }
                    else
                    {

                        System.out.println("Stacking failed, no stacking result produced...");
                    }





                    fsTimer.stop();
                    this.jobTime.stop();
                    try
                    {

                        Thread.sleep(POLL_INTERVAL);
                    }
                    catch (Exception e)
                    {

                    }
                    MONITOR_THREAD_EXIT = true;


                    //get other summary statistics from service...
                    if (finalCTS != null)
                    {

                        MonitorState msStack = new MonitorState(new String(""));
                        MonitorStateResponse msrStack = finalCTS.gp.monitorState(ms);
                        msr.setCacheSize(Math.max(msr.getCacheSize(), msrStack.getCacheSize()));
                        msr.setCacheHitsLocal(msr.getCacheHitsLocal() + msrStack.getCacheHitsLocal());
                        msr.setCacheHitsGlobal(msr.getCacheHitsGlobal() + msrStack.getCacheHitsGlobal());
                        msr.setCacheMisses(msr.getCacheMisses() + msrStack.getCacheMisses());
                    }
                    if (DEBUG) System.out.println("Final stacking finished after " + fsTimer.getElapsedTime() + " ms");


                    System.out.println("");
                    System.out.println("Image Stacking Specific Output");
                    System.out.println("Number of image cutouts success: " + getSuccessExecs());
                    System.out.println("Number of coordinates not found in SDSS Index: " + tuplesNotFound.size());
                    System.out.println("Number of parse errors when reading stacking description: " + tuplesParseError.size());
                    System.out.println("Number of image cutouts failed: " + listCutoutFailed.size());
                    System.out.println("Loading SDSS DR5 Index in memory: " + initIndexTimer.getElapsedTime() + " ms");
                    System.out.println("Coordinates Lookup: " + lookupCoordinatesTimer.getElapsedTime() + " ms");
                    System.out.println("Performing Cutouts: " + cutoutTime + " ms");
                    System.out.println("Final Stacking: " + fsTimer.getElapsedTime() + " ms");
                    System.out.println("Total Time: " + (lookupCoordinatesTimer.getElapsedTime() + cutoutTime + fsTimer.getElapsedTime())*1.0/1000.0 + " sec");


                    if (DEBUG) System.out.println("Destroying Final Stacking Resource...");
                    if (finalUserThread != null)
                    {
                        if (DEBUG) System.out.println("destroyGetResults(): 3");
                        finalUserThread.gr.destroyGetResults();
                    }
                    //else
                    //{
                    //    System.out.println("error in finalUserThread destroy... ignoring...");
                    //}
                    if (finalCTS != null)
                    {
                        destroyResource(finalCTS.instanceEPR);
                    }
                    //else
                    //{
                    //    System.out.println("error in finalCTS destroy... ignoring...");
                    //}


                    if (DEBUG) System.out.println("Destroy finished!");




                }
                else
                {

                    try
                    {

                        Thread.sleep(POLL_INTERVAL);
                    }
                    catch (Exception e)
                    {

                    }
                    MONITOR_THREAD_EXIT = true;

                }





                long eTime = System.currentTimeMillis();
                System.out.println((getSuccessExecs()+getFailedExecs()) + " tasks completed in " + this.jobTime.getElapsedTime()*1.0/1000.0 + " sec");
                System.out.println("Successful tasks: " + getSuccessExecs());
                System.out.println("Failed tasks: " + getFailedExecs());
                System.out.println("Notification Errors: " + getErrorNotification());
                System.out.println("Overall Throughput (tasks/sec): " + Math.round(((getSuccessExecs()+getFailedExecs())/(this.jobTime.getElapsedTime()/1000.0))*100)/100.0);
                if (monitorThread != null) System.out.println("Overall Throughput Standard Deviation: " + Math.round(monitorThread.statCalc.getStandardDeviation()*1000)/1000.0);
                if (DATA_CACHING)
                {

                    System.out.println("Data Caching Specific Output");
                    System.out.println("Cache Size: " + msr.getCacheSize());
                    System.out.println("Cache hits (local): " + msr.getCacheHitsLocal());
                    System.out.println("Cache hits (global): " + msr.getCacheHitsGlobal());
                    System.out.println("Cache misses: " + msr.getCacheMisses());
                    int numCacheAccesses = msr.getCacheMisses()+msr.getCacheHitsLocal()+msr.getCacheHitsGlobal();
                    if (numCacheAccesses > 0)
                    {
                        System.out.println("Cache hits/miss ratio (local): " + Math.round((msr.getCacheHitsLocal())*100/(numCacheAccesses))/100.0);
                        System.out.println("Cache hits/miss ratio (global): " + Math.round((msr.getCacheHitsLocal()+msr.getCacheHitsGlobal())*100/(numCacheAccesses))/100.0);
                    }
                    else
                    {
                        System.out.println("Cache hits/miss ratio (local): 0");
                        System.out.println("Cache hits/miss ratio (global): 0");
                    }


                }



                //for (int i=0;i<userThreads.length;i++)
                //{
                //        try
                //        {
                //userThreads[i].gr.destroyGetResults();
                //                   userThreads[i].gr.destroyNotificationListener();
                ////                   if (this.POLL_INTERVAL <= 0) System.out.println("NotificationListener # "  + i + " destroyed!");
                ////       } catch (Exception e)
                //       {
                //          if (DEBUG) if (DIPERF == false) System.out.println("Error in destroying NotificationListener # "  + i + " : " + e);// + ": userThreads #" + userThreads[i].getID(machID)/* + ": State = " + readThreads[i].State*/ + ": was interupted :(");
                //          e.printStackTrace();

                // Thread was interrupted
                //     }
                //}


//                <xsd:element name="cacheHits" type="xsd:int"/>
//                <xsd:element name="cacheMisses" type="xsd:int"/>



                try
                {

                    Properties props = System.getProperties();
                    FALKON_LOGS = (String)props.get("FALKON_LOGS");
                    if (FALKON_LOGS == null)
                    {
                        //FALKON_LOGS = new String("");
                        System.out.println("Warning, ${FALKON_LOGS} is not set, you must include -DFALKON_LOGS=${FALKON_LOGS} when starting the JVM...  things might not work due to relative path names...");

                    }
                    else
                    {

                        BufferedWriter out = new BufferedWriter(new FileWriter(FALKON_LOGS+"/client-summary.txt", true));
                        BufferedWriter outHeader = new BufferedWriter(new FileWriter(FALKON_LOGS+"/client-summary-header.txt", false));
                        // + " stdev_tp " + statCalc.getStandardDeviation()
                        //out.write("#_tasks task_length #_executors success_tasks failed_tasks failed_notifications time_ms");
                        //*************************
                        //add here the datacaching summary...

                        String tpStdDev = null;


                        if (monitorThread != null)
                        {
                            tpStdDev = "" + monitorThread.statCalc.getStandardDeviation();
                        }
                        else
                            tpStdDev = new String("NA");

                        outHeader.write("numExecs COMMENT1 COMMENT2 COMMENT3 LOCALITY successExecs failedExecs errorNotification elapsedTime throughput_tasks/sec throughput_stdev cacheSize cacheHitLocal cacheHitGlobal cacheMiss cacheHitMissRatioLocal cacheHitMissRatioGlobal\n");
                        if (!DATA_CACHING)
                            out.write(numExecs + " " + COMMENT1 + " " + COMMENT2 + " " + COMMENT3 + " " + LOCALITY + " " + getSuccessExecs() + " " + getFailedExecs() + " " + getErrorNotification() + " " + this.jobTime.getElapsedTime()+ " " + ((getSuccessExecs()+getFailedExecs())/(this.jobTime.getElapsedTime()/1000.0)) + " " + tpStdDev +  " 0 0 0 0 0 0\n");
                        else
                        {
                            if (msr.getCacheMisses()+msr.getCacheHitsLocal()+msr.getCacheHitsGlobal() == 0)
                            {
                                out.write(numExecs + " " + COMMENT1 + " " + COMMENT2 + " " + COMMENT3 + " " + LOCALITY + " " + getSuccessExecs() + " " + getFailedExecs() + " " + getErrorNotification() + " " + this.jobTime.getElapsedTime()+ " " + ((getSuccessExecs()+getFailedExecs())/(this.jobTime.getElapsedTime()/1000.0)) + " " + tpStdDev +  " " + msr.getCacheSize() +  " " + msr.getCacheHitsLocal() +  " " + msr.getCacheHitsGlobal() +  " " + msr.getCacheMisses() +  " 0 0\n");
                            }
                            else
                            {
                                out.write(numExecs + " " + COMMENT1 + " " + COMMENT2 + " " + COMMENT3 + " " + LOCALITY + " " + getSuccessExecs() + " " + getFailedExecs() + " " + getErrorNotification() + " " + this.jobTime.getElapsedTime()+ " " + ((getSuccessExecs()+getFailedExecs())/(this.jobTime.getElapsedTime()/1000.0)) + " " + tpStdDev +  " " + msr.getCacheSize() +  " " + msr.getCacheHitsLocal() +  " " + msr.getCacheHitsGlobal() +  " " + msr.getCacheMisses() +  " " + Math.round((msr.getCacheHitsLocal())/(msr.getCacheMisses()+msr.getCacheHitsLocal()+msr.getCacheHitsGlobal())*100)/100.0 + " " + Math.round((msr.getCacheHitsLocal()+msr.getCacheHitsGlobal())/(msr.getCacheMisses()+msr.getCacheHitsLocal()+msr.getCacheHitsGlobal())*100)/100.0 + "\n");
                            }

                        }
                        out.close();
                        outHeader.close();
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }


                if (DEBUG)
                {

                    System.out.print("Results: ");
                    if (resultQ != null && resultQ.size() > 0)
                    {
                        while (resultQ.size() > 0)
                        {
                            String results = (String)resultQ.remove();

                            System.out.println(results);
                        }
                        //for (int k=0;k<results.length;k++)
                        //{
                        //    System.out.println(results[k]);
                        //}


                    }
                    else
                        System.out.println("null");
                }
            }
            //} 

            //}



            if (INTERACTIVE)
            {

                console = new Console(this);
                console.start();


                console.join();
                System.out.println("Console thread finished...");

            }

            //System.out.println("DeInit...");

            //sw.start();


            //if (!ONE_TASK_PER_RESOURCE)
            //{


            DestroyResourceThread drt[] = new DestroyResourceThread[numResources];

            for (int i=0;i<numResources;i++)
            {

                //destroyResource(clientTS[i].instanceEPR);
                drt[i] = new DestroyResourceThread(clientTS[i], this); 
                drt[i].start();

            }

            for (int i=0;i<numResources;i++)
            {

                System.out.println("waiting to destroy all resources...");
                drt[i].join();

            }


            /*
            for (int i=0;i<numResources;i++)
            {
                sw.reset();
                sw.start();
                //should paralize with a pool of threads
                destroyResource(clientTS[i].instanceEPR);
                sw.stop();
                if (DEBUG) System.out.println("Destroyed resource " + i + " : "+sw.getElapsedTime() + " ms");
                else
                    if (this.POLL_INTERVAL <= 0) System.out.print("X");

            }      */
            //}

            //if (!DEBUG)
            //{
            if (this.POLL_INTERVAL <= 0) System.out.println("");
            //}

            this.finished = true;


            if (outTaskPerf != null)
            {
                outTaskPerf.flush();
                outTaskPerf.close();
            }

            if (outTaskDescSuccess != null)
            {
                outTaskDescSuccess.flush();
                outTaskDescSuccess.close();
            }
            if (outTaskDescFailed != null)
            {
                outTaskDescFailed.flush();
                outTaskDescFailed.close();
            }




            killListen.stop();

            System.exit(0);





        }
        catch (Exception e)
        {
            System.out.println("Fatal ERROR main(): " + e.getMessage());
            e.printStackTrace();
            //sw.start();

            //destroyResource(homeEPR);
        }



    }


    private boolean getUserResultAvailable(GPPortType gp, long ET) throws Exception
    {
        int GPWS_qLength = 0;
        int GPWS_activeTasks = 0;
        int GPWS_numWorkers = 0;
        int completedTasks = 0;
        int queuedTask = 0;
        int activeTask = 0;


/*
        <xsd:element name="StatusUserResponse">
            <xsd:complexType>
                <xsd:sequence>
                    <xsd:element name="queueLength" type="xsd:int"/>
                    <xsd:element name="activeTasks" type="xsd:int"/>
                    <xsd:element name="queuedTask" type="xsd:int"/>
                    <xsd:element name="activeTask" type="xsd:int"/>
                    <xsd:element name="numWorkers" type="xsd:int"/>
                    <xsd:element name="numWorkerResults" type="xsd:int"/>
                    <xsd:element name="valid" type="xsd:boolean"/>
                </xsd:sequence>
            </xsd:complexType>
        </xsd:element>
        */

        if (DEBUG) System.out.println("Retrieving GPWS_USER status...");
        StatusUser stat = new StatusUser("");
        StatusUserResponse sur = gp.statusUser(stat);
        if (sur.isValid())
        {
            GPWS_qLength = sur.getQueueLength();
            GPWS_activeTasks = sur.getActiveTasks();
            GPWS_numWorkers = sur.getNumWorkers();

            completedTasks = sur.getNumWorkerResults();
            queuedTask = sur.getQueuedTask();
            activeTask = sur.getActiveTask();
            //if (DIPERF == false && SUMMARY == false) System.out.println(lt.getElapsedTime()*1.0/1000.0 + ": qLength=" + status_qLength + " activeTask=" + status_activeStacks + " numWorkers=" + status_numWorkers);
        }
        else
        {
            if (DEBUG) System.out.println("State was not valid, trying again...");
        }


        if (GPWS_qLength!=old_GPWS_qLength || GPWS_activeTasks!=old_GPWS_activeTasks || GPWS_numWorkers!=old_GPWS_numWorkers || completedTasks!=old_completedTasks || queuedTask!=old_queuedTask || activeTask!=old_activeTask)
        {

            //if (DIPERF == false && SUMMARY == false) System.out.println("Time=" + ET*1.0/1000.0 + " queuedStacks=" + queuedStacks + " activeStacks=" + activeStacks + " completedStacks=" + completedStacks + " allStacks=" + NUM_TASKS + " %completed=" + completedStacks*100.0/NUM_TASKS + " GPWS_queuedStacks=" + GPWS_qLength + " GPWS_activeStacks=" + GPWS_activeStacks + " GPWS_activeResources=" + GPWS_numWorkers);
            if (SUMMARY == false) System.out.println("Time=" + ET*1.0/1000.0 + " queuedTask=" + queuedTask + " activeTask=" + activeTask + " completedTasks=" + completedTasks + " allTasks=" + NUM_TASKS + " %completed=" + completedTasks*100.0/NUM_TASKS + " ETA=" + ((ET*1.0/1000.0)*100.0/(completedTasks*100.0/NUM_TASKS)-(ET*1.0/1000.0)) +  " GPWS_queuedTasks=" + GPWS_qLength + " GPWS_activeTasks=" + GPWS_activeTasks + " GPWS_activeResources=" + GPWS_numWorkers);
        }

        old_GPWS_qLength = GPWS_qLength;
        old_GPWS_activeTasks = GPWS_activeTasks;
        old_GPWS_numWorkers = GPWS_numWorkers;
        old_completedTasks = completedTasks;
        old_queuedTask = queuedTask;
        old_activeTask = activeTask;




        if (completedTasks == NUM_TASKS)
        {
            return true;
        }
        else
        {

            return false;
        }


    }




    public static void main(String[] args) 
    {
        System.out.println("Starting Falkon Command Line Client v1.0");
        /*
        int num_threads = 0;
        int ctr;
        for (ctr = 0; ctr < args.length; ctr++)
        {
            if (args[ctr].equals("-num_threads") && ctr + 1 < args.length)
            {
                ctr++;
                num_threads = (new Integer(args[ctr])).intValue();
            }
        }

        if (num_threads <= 0)
        {
            System.out.println("arguement -num_threads not set properly, num_threads cannot be less than 1... exiting!"); 
            System.exit(0);

        }  */

        try
        {
            UserRun client = new UserRun(args);
            client.main_run();

            /*
            UserRun clients[] = new UserRun[num_threads];

            for (int i=0;i<num_threads;i++)
            {
                System.out.println("creating thread " + i);

                clients[i] = new UserRun(args);
            }

            for (int i=0;i<num_threads;i++)
            {
                System.out.println("starting thread " + i);
                clients[i].start();
            }

            for (int i=0;i<num_threads;i++)
            {
                System.out.println("waiting for thread " + i + " to finish...");
                clients[i].join();
                System.out.println("thread " + i + " has finished!");
            }
            */

        }
        catch (Exception e)
        {
            System.out.println("Error in main(): " + e);
            e.printStackTrace();
        }

        //System.out.println("Finished all threads!");


        /*
    System.out.println("Starting threaded UserRun...");
    try
    {
    
    UserRun client = new UserRun(args);
    client.start();
    client.join();
    }
    catch (Exception e)
    {
        System.out.println("Error in main(): " + e);
        e.printStackTrace();
    }
    System.out.println("Finished all threads!");
    */
    }


}


class GetNotification extends Thread
{
    public WorkQueue notificationQ;
    public Notification userNot;
    private UserRunThread userThread = null;
    boolean DEBUG = true;

    public GetNotification(WorkQueue notificationQ, Notification userNot, UserRunThread userThread, boolean DEBUG)
    {
        this.userNot = userNot;
        this.notificationQ = notificationQ;

        this.userThread = userThread;
        this.DEBUG = DEBUG;
    }

    public void run()
    {
        String notificationMessage[] = null;
        while (userThread.user.INTERACTIVE || !userThread.doneSending || !userThread.execsIDs.isEmpty())
        {
            try
            {


                if (DEBUG) System.out.println("waiting for notification at " + userThread.machID);

                //notificationMessage = userNot.recvString();
                notificationMessage = userNot.recvStrings();
                if (notificationMessage != null && notificationMessage.length > 0)
                {

                    for (int i=0;i<notificationMessage.length;i++)
                    {
                        notificationQ.insert(notificationMessage[i]);
                        if (DEBUG) System.out.println("received notification " + notificationMessage[i]);
                    }


                }
                else
                {
                    if (DEBUG) System.out.println("notificaitaon received was null, double check why?");

                }
            }
            catch (Exception e)
            {
                if (DEBUG) e.printStackTrace();
                try
                {
                    Thread.sleep(100);

                }
                catch (Exception ee)
                {

                }
            }
        }

    }

}


class GetResults extends Thread
{
    //Logger logger = Logger.getLogger("impl.GetStatus");

    private UserRunThread userThread = null;
    private GPPortType Ggp = null;

    public StopWatch lt = null;
    Notification userNot = null;
    public boolean DEBUG = false;
    public boolean DIPERF = false;

    public WorkQueue notificationQ = new WorkQueue();

    public GetNotification getNotificationThread = null;

    //public int receivedTasks = 0;

    //public static boolean shutingDown = false;

    // public int NUM_TASKS = 0;



    public GetResults(UserRunThread userThread, GPPortType Ggp, StopWatch lt, Notification userNot)
    {
        this.userThread = userThread;
        this.Ggp = Ggp;
        this.lt = lt;
        this.userNot = userNot;
        this.DEBUG = userThread.DEBUG;
        this.DIPERF = userThread.DIPERF;
        //this.NUM_TASK = num_task;

        this.getNotificationThread = new GetNotification(notificationQ, userNot, userThread, DEBUG);

        getNotificationThread.start();

    }


    public String waitForNotification() throws Exception
    {
        //DEBUG = true;
        //boolean test = false;
        //ResourceKey key = null;
        //String key = null;
        String taskID = null;

        while (taskID == null)
        {

            try
            {

                //key = userNot.recv();
                if (DEBUG) System.out.println("waiting for notification from notification Q " + userThread.machID);

                //taskID = userNot.recvString();
                taskID = (String)notificationQ.remove();

                if (taskID == null)
                {
                    //try
                    //{
                    if (DEBUG) if (DIPERF == false) System.out.println("Notification received, but taskID was null...");
                    userThread.user.setErrorNotification();
                    //throw new Exception("Notification received, but taskID was null...");
                    //return null;

                    //Thread.sleep(1000);
                    //} catch (Exception e)
                    //{
                    //    if (DEBUG) if (DIPERF == false) System.out.println("ERROR: sleep() " + e);

                    //}

                }
                else
                {
                    String tokens[] = taskID.split(" ");
                    if (tokens.length == 2)
                    {
                        if (userThread.execsIDs.contains(tokens[0]))
                        {
                            userThread.execsIDs.remove(tokens[0]);
                            if (DEBUG) System.out.println("Received taskID = " + tokens[0] + " with an exit code of " + tokens[1]);

                            if (tokens[1].contentEquals(new StringBuffer("0")))
                            {
                                userThread.user.setSuccessExecs();

                                if (userThread.user.STACKING)
                                {
                                    userThread.user.listToStack.add((UserRun.AstroDataCachingNames)userThread.user.cachingStackingMap.remove(tokens[0]));
                                }

                            }
                            //this means that this task was succesful (in the context of the AstroPortal) but there was no output needed from this task
                            else if (tokens[1].contentEquals(new StringBuffer("33333")))
                            {
                                if (userThread.user.STACKING)
                                {
                                    userThread.user.setSuccessExecs();
                                    userThread.user.cachingStackingMap.remove(tokens[0]);
                                }
                                else
                                {
                                    userThread.user.setFailedExecs();
                                }

                            }
                            else
                            {
                                userThread.user.setFailedExecs();
                                if (userThread.user.STACKING)
                                {
                                    userThread.user.listCutoutFailed.add("Cutout failed: " + taskID);
                                }
                            }
                            //return taskID;
                        }

                        //taskID not found, perhaps it was a duplicate notification
                        else
                        {
                            if (DEBUG) System.out.println("Received taskID = " + taskID + " but it was not found in the list of notifications this thread was expecting... perhaps it was a duplicate notification...");
                            taskID = null;
                            userThread.user.setErrorNotification();

                        }

                        //if (DEBUG) if (DIPERF == false) System.out.println("Received key = " + key);
                        //return getEPRfromKey(key);

                        ExecutablePerformance ePerf = (ExecutablePerformance)userThread.execsPerf.get(tokens[0]);
                        if (ePerf == null)
                        {
                            //gpResource.logger.debug("ERROR: TaskPerformance not found in HashMap for taskID " + (tasks[i].getExecutable()).getId() + " although it should have been there... double check why not!");
                            //this should never happen
                            ePerf = new ExecutablePerformance();

                            ePerf.setTaskID(tokens[0]);
                            ePerf.setSubmitTime();
                            ePerf.setNotificationTime();
                            ePerf.setResultsTime();
                            ePerf.setEndTime();
                            ePerf.setExitCode(Integer.parseInt(tokens[1]));
                        }
                        else
                        {

                            ePerf.setNotificationTime();
                            ePerf.setResultsTime();
                            ePerf.setEndTime();
                            ePerf.setExitCode(Integer.parseInt(tokens[1]));
                        }

                        this.userThread.user.writeLogTaskPerf(ePerf.toString());

                        if (ePerf.getExitCode() == 0)
                        {
                            this.userThread.user.writeLogTaskDescSuccess(ePerf.getExecutable());
                        }


                        this.userThread.execsPerf.remove(ePerf.getTaskID());
                        ePerf = null;

                        //execsPerf.put(ePerf.getTaskID(), ePerf);




                    }
                    else
                    {
                        if (DEBUG) System.out.println("Received taskID = " + taskID + " but it was not formatted properly.... it should hae been 'taskID exitCode' separated by a space...");
                        taskID = null;
                        userThread.user.setErrorNotification();


                    }
                }

            }
            catch (Exception e)
            {
                if (DEBUG) System.out.println("exception received in notification recvString() " + e);
                if (DEBUG) e.printStackTrace();
                //DEBUG = false;
                throw new Exception("exception received in notification recvString() " + e);
                //userThread.user.setErrorNotification();

            }
        }

        //DEBUG = false;

        return taskID;

    }




    public void destroyGetResults()
    {
        if (DEBUG) System.out.println("destroyGetResults(): destroying notification engine...");

        //shutingDown = true;
        userNot.destroy();

    }

    public void destroyNotificationListener()
    {
        //shutingDown = true;
        //userNot.destroy();

    }


    public void run()
    {


        if (DEBUG) System.out.println("GetResults Thread started...");
        StopWatch sw = new StopWatch();
        //int pollStep = 100;

        boolean userResult = false;

        int resIndex = 0;

        while (userThread.user.INTERACTIVE || !userThread.doneSending || !userThread.execsIDs.isEmpty())
        //while (userThread.user.INTERACTIVE || !userThread.execsIDs.isEmpty())
        //while (!shutingDown && (userThread.user.INTERACTIVE || userThread.rQ.size() < userThread.user.NUM_TASKS))
        //while (!userThread.shutingDown)
        {
            if (DEBUG) System.out.println("userThread.user.INTERACTIVE => " + userThread.user.INTERACTIVE);
            if (DEBUG) System.out.println("userThread.doneSending => " + userThread.doneSending);
            if (DEBUG) System.out.println("userThread.execsIDs.isEmpty() => " + userThread.execsIDs.isEmpty());

            //if (!userThread.user.INTERACTIVE && userThread.rQ.size()+1 == userThread.user.NUM_TASKS)
            //{
            //}
            //shutingDown = true;




            sw.start();
            if (DEBUG) System.out.println("Waiting for results...");

            try
            {
                String taskID = waitForNotification();
                userThread.setRecvTasks();

                if (userThread.user.retrieveResult)
                {
                    System.out.println("retrieving results via WS and placing results in result queue...");
                    userThread.rQ.insert(taskID);
                }

                if (this.userThread.user.POLL_INTERVAL <= 0)
                {


                    if (userThread.user.INTERACTIVE)
                    {
                        System.out.println(userThread.user.jobTime.getElapsedTime() + " : NOTIFICATION " + userThread.getRecvTasks() + " : " + taskID);
                    }
                    else
                        System.out.println(userThread.user.jobTime.getElapsedTime() + " : NOTIFICATION " + userThread.getRecvTasks() + " of " + userThread.getSendTasks() + " : " + taskID + " ... notifications left to receive " + userThread.execsIDs.size());
                }

            }
            catch (Exception ee)
            {
                if (StringUtil.contains(ee.getMessage(), "Socket closed"))
                    break;
                else
                {
                    userThread.user.setErrorNotification();
                    if (DEBUG) System.out.println("Error in receiving notification: " + ee);
                    if (DEBUG) ee.printStackTrace();
                }

            }
            /*
            if (userThread.shutingDown)
            {
                break;
            } */

            sw.stop();
            if (DEBUG) System.out.println("WORKER: waitForNotification (" + sw.getElapsedTime() + "ms)");
            sw.reset();

            if (userThread.user.retrieveResult)
            {

                sw.start();
                UserResultResponse resultRP = null;


                try
                {
                    UserResult ur = new UserResult();
                    ur.setValid(true);
                    ur.setMax_per_call(1);

                    resultRP = Ggp.userResult(ur);
                }
                catch (Exception e)
                {
                    if (DEBUG) System.out.println("USER: Error in GetResults() thread: " + e);

                }
                sw.stop();

                if (DIPERF) System.out.println("USER:userResult(): " + sw.getElapsedTime() + " ms");
                long WScallTime = sw.getElapsedTime();
                sw.reset();


                sw.start();
                if (resultRP.isValid())
                {
                    int numTasks = resultRP.getNumTasks();
                    Task[] tasks = null;
                    if (numTasks > 0)
                    {

                        tasks = resultRP.getTasks();
                    }


                    if (numTasks > 0)
                    {

                        for (int i=0;i<tasks.length;i++)
                        {
                            //finalResultString += taskToString(tasks[i]);
                            //user.finalResultStrings[resIndex++] = user.taskToString(tasks[i]);
                            System.out.println("retrieved results via WS and placed in result queue...");
                            userThread.rQ.insert(userThread.user.taskToString(tasks[i]));

                            //System.out.println(tasksX[i].getErrorMsg() + " " + failedCounter++ + ": " + taskToString(tasksX[i]));
                        }
                    }
                }
                else
                {

                    if (DEBUG) System.out.println("USER:getResults(): no results available...");
                }
                sw.stop();

                if (DIPERF) System.out.println("USER:processResult(): " + sw.getElapsedTime() + " ms");
                sw.reset();
            }

        }

        if (DEBUG) System.out.println("userThread.user.INTERACTIVE => " + userThread.user.INTERACTIVE);
        if (DEBUG) System.out.println("userThread.doneSending => " + userThread.doneSending);
        if (DEBUG) System.out.println("userThread.execsIDs.isEmpty() => " + userThread.execsIDs.isEmpty());


        if (DEBUG) System.out.println("destroyGetResults(): 4");
        destroyGetResults();
    }


}

class SubmitThread extends Thread 
{
    //static final Object NO_MORE_WORK = new Object();
    public UserRunThread urt = null;
    //boolean shutingDown = false;
    public int id;

    public SubmitThread(UserRunThread urt, int id)
    {
        this.urt = urt;
        this.id = id;

    }

    public void run()
    {
        /*
        try
        {
            Thread.sleep(id*1000*(urt.user.SUBMIT_TIME_QUANTA/10000));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
          */


        StopWatch timeSinceJobSubmitted = new StopWatch();
        timeSinceJobSubmitted.start();

        while (urt.shutingDown == false && urt.doneSending == false)
        {

            try
            {
                StopWatch sw = new StopWatch();
                sw.start();



                //set bundle size more dynamically
                int bundleSize = (int)Math.min(urt.user.MAX_COM_PER_WS, urt.user.MAX_SUBMIT_PER_SEC-urt.getSubmitSent());
                //bundleSize = (int)Math.max(bundleSize - (urt.getSendTasks() - urt.getRecvTasks()),0);
                int activeTasks = (urt.getSendTasks() - urt.getRecvTasks());
                bundleSize = (int)Math.min(urt.user.MAX_CONCURENT_TASKS - activeTasks, bundleSize);
                //bundleSize = (int)Math.max(bundleSize - urt.getSendTasks(),0);
                if (urt.DEBUG) System.out.println("bundleSize = " + bundleSize + " urt.user.MAX_COM_PER_WS = " + urt.user.MAX_COM_PER_WS + " urt.user.MAX_SUBMIT_PER_SEC = " + urt.user.MAX_SUBMIT_PER_SEC + " urt.getSubmitSent() = " + urt.getSubmitSent()+ " urt.getSendTasks() = " + urt.getSendTasks()+ " urt.getRecvTasks() = " + urt.getRecvTasks());

                if (bundleSize == 0 && urt.getSendTasks() == urt.user.NUM_TASKS && urt.getRecvTasks() == urt.user.NUM_TASKS)
                {

                    if (urt.DEBUG) System.out.println("doneSending = true : 0");
                    urt.doneSending = true; //??
                    urt.shutingDown = true;

                    System.out.println("It appears that all planed submitted tasks have been received, commencing shuting down sequence...");
                }
                else
                {
                    if (urt.DEBUG) System.out.println("bundleSize => " + bundleSize);
                    if (urt.DEBUG) System.out.println("urt.getSendTasks() => " + urt.getSendTasks());
                    if (urt.DEBUG) System.out.println("urt.user.NUM_TASKS => " + urt.user.NUM_TASKS);
                    if (urt.DEBUG) System.out.println("urt.getRecvTasks() => " + urt.getRecvTasks());

                }

                if (bundleSize > 0)
                {



                    if (urt.DEBUG) System.out.println("Getting at most " + bundleSize + " tasks...");

                    Executable execs[] = urt.getExecs(bundleSize);

                    if (execs == null)
                    {
                        if (urt.DEBUG) System.out.println("Got null task, shuting down SubmitThread...");
                        //urt.shutingDown = true;
                        if (urt.DEBUG) System.out.println("doneSending = true : 1");
                        urt.doneSending = true; //??
                        break;



                    }
                    else
                    {
                        if (urt.DEBUG) System.out.println("Got " + execs.length + " tasks!");

                        int numErrors = 0;
                        int maxNumErrors = 10;


                        if (urt.DEBUG) System.out.println("doExecutables(execs)...");
                        while (!urt.doExecutables(execs) && numErrors < maxNumErrors)
                        {
                            try
                            {
                                if (urt.DEBUG) System.out.println("failed doExecutables(execs), sleeping for 1000 ms and trying again...");
                                Thread.sleep(1000);
                            }
                            catch (Exception e)
                            {
                                if (urt.DEBUG) System.out.println("Error: " + e);
                                if (urt.DEBUG) e.printStackTrace();

                            }
                            numErrors++;
                        }

                        if (numErrors >= maxNumErrors)
                        {
                            if (urt.DEBUG) System.out.println("doneSending = true : 2");
                            urt.doneSending = true;
                            urt.shutingDown = true;
                            System.out.println("Giving up with " + numErrors + " submittion errors, exiting submit thread...");
                        }
                        else
                        {

                            timeSinceJobSubmitted.reset();
                            timeSinceJobSubmitted.start();

                        }

                    }

                    execs = null;

                }
                //this logic might not be right...
                else
                {

                    try
                    {
                        if (this.urt.DEBUG) System.out.println("bundleSize = " + bundleSize + " which is less than 1... sleeping for 100 ms to try again...");
                        //this is a hack, should figure out why this is needed...
                        //this hack might disable throttling...
                        //urt.resetSubmitSent();
                        Thread.sleep(100);

                        if (urt.DEBUG) System.out.println("Submission window is callapsed to 0, most likely cause is too many concurent tasks, need to wait for some to complete before continuing...");
                        long defaultPollingInterval = 60000;
                        if (urt.user.MAX_NUM_SUBMIT_THREADS >= 16)
                        {
                            defaultPollingInterval = defaultPollingInterval * (int)Math.ceil(urt.user.MAX_NUM_THREADS/16);

                        }

                        if (timeSinceJobSubmitted.getElapsedTime() > defaultPollingInterval )
                        {

                            try
                            {
                                MonitorStateResponse msr = urt.gp.monitorState(new MonitorState(new String("")));
                                System.out.println("Monitor heartbeat...");
                                timeSinceJobSubmitted.reset();
                            }
                            catch (Exception ee)
                            {
                                if (urt.DEBUG) ee.printStackTrace();

                                if (urt.DEBUG) System.out.println("doneSending = true : 3");
                                urt.doneSending = true;
                                urt.shutingDown = true;
                                System.out.println("Monitor heartbeat failed, exiting submit thread...");

                            }
                        }



                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();

                    }
                }
                sw.stop();
                if (urt.DIPERF) System.out.println("USER:UserRunThread:SubmitThread(): " + sw.getElapsedTime() + " ms");
                sw.reset();


            }
            catch (Exception e)
            {
                System.out.println("Error: " + e);
                e.printStackTrace();
                //throw new Exception(e);
            }

        }

        if (urt.DEBUG) System.out.println("Exiting submit thread...");



    }


}





class ReadFileDescriptionThread extends Thread 
{
    UserRun user;

    public ReadFileDescriptionThread(UserRun user)
    {
        this.user = user;
    }


    public void run()
    {

        String fileName = user.jobDescriptionFile;
        System.out.println("Reading file: " + fileName + "... ");
        //numExecs = 0;
        //String execs[] = null;
        try
        {
            int numExecs = user.num_execs;
            //execs = new String[numExecs];
            int execsIndex = 0;

            BufferedReader in2 = new BufferedReader(new FileReader(fileName));
            String exec;
            while ((exec = in2.readLine()) != null && execsIndex < numExecs)
            {
                user.execQ.insert(new String(exec));
                execsIndex++;

                while (user.execQ.size() > user.MAX_CONCURENT_TASKS)
                {

                    if (user.DEBUG) System.out.println("Too many queued tasks, waiting for some to complete before continuing to queue more...");
                    Thread.sleep(100);
                }
            }
            in2.close();

            int numUsers = (int)Math.min(user.num_execs, user.MAX_NUM_THREADS);
            if (user.DEBUG) System.out.println("NO_MORE_WORK : inserting " + numUsers + " entries...");
            for (int i=0;i<numUsers;i++)
            {
                if (user.DEBUG) System.out.println("NO_MORE_WORK : 3");
                user.execQ.insert(UserRunThread.NO_MORE_WORK);
            }


            if (execsIndex != numExecs)
            {
                System.out.println("execsIndex != numExecs : " + execsIndex + " != " + numExecs);
            }

            System.out.println("Finished reading " + execsIndex + " tasks from " + fileName + ".");

            //return execsIndex;
        }
        catch (Exception e)
        {
            System.out.println("Error in reading file: " + fileName + "... :" + e);
            e.printStackTrace();

        }
        //return 0;

    }


}


class VariableSubmitThrottle extends Thread 
{
    UserRun user;

    public VariableSubmitThrottle(UserRun user)
    {
        this.user = user;
    }

    public synchronized void initSubmitThrottle()
    {
        if (user.INC_SUBMIT_SIN)
        {
            //=MAX(FLOOR((SIN(SQRT(B2)*3)+1)*B2*3.4584,1), 1)
            //new function with 1M tasks and up to 550 tasks/sec
            //user.MAX_SUBMIT_PER_SEC = (int)Math.min(Math.max((Math.sin(Math.sqrt((sw.getElapsedTime()*1.0/60000.0))*3)+1)*(sw.getElapsedTime()*1.0/60000.0)*3.459407,MIN_SUBMIT),MAX_SUBMIT);
            //new function with 2M tasks and up to 1000 tasks/sec
            //=MAX(FLOOR((SIN(SQRT((B2+0.11))*2.859678)+1)*(B2+0.11)*5.705,1), 0)
            user.MAX_SUBMIT_PER_SEC = (int)Math.min(Math.max((Math.sin(Math.sqrt((sw.getElapsedTime()*1.0/60000.0+0.11))*2.859678)+1)*(sw.getElapsedTime()*1.0/60000.0+0.11)*5.705,MIN_SUBMIT),MAX_SUBMIT);

            //old function with 3M tasks and up to 1000 tasks/sec
            //user.MAX_SUBMIT_PER_SEC = (int)Math.min(Math.max((Math.sin(Math.sqrt((sw.getElapsedTime()*1.0/60000.0)+3.2)*4.05)+1)*(sw.getElapsedTime()*1.0/60000.0)*3.1,MIN_SUBMIT),MAX_SUBMIT);
            user.MAX_COM_PER_WS = (int)Math.min(user.MAX_SUBMIT_PER_SEC, user.MAX_COM_PER_WS2);

            //System.out.println("MAX_SUBMIT_PER_SEC: " + sw.getElapsedTime() + " : " + user.MAX_SUBMIT_PER_SEC);

        }
        else
        {

            user.MAX_SUBMIT_PER_SEC = (int)Math.min(user.MIN_SUBMIT_PER_SEC, user.MAX_SUBMIT_PER_SEC2);
            user.MAX_COM_PER_WS = (int)Math.min(user.MAX_SUBMIT_PER_SEC, user.MAX_COM_PER_WS2);

            //System.out.println("MAX_SUBMIT_PER_SEC: " + sw.getElapsedTime() + " : " + user.MAX_SUBMIT_PER_SEC);
            //System.out.println("Set MAX_SUBMIT_PER_SEC to " + user.MAX_SUBMIT_PER_SEC);
            //System.out.println("Value MAX_SUBMIT_PER_SEC2 = " + user.MAX_SUBMIT_PER_SEC2);
            //System.out.println("Set MAX_COM_PER_WS to " + user.MAX_COM_PER_WS);
        }
    }

    //set this to true if you want to enable this feature
    public boolean incSubmitByTen = false;
    public static int MIN_SUBMIT=0;
    public static int MAX_SUBMIT=1000;

    public synchronized void updateSubmitThrottle()
    {
        //used to have a sin wave arrival rate
        if (user.INC_SUBMIT_SIN)
        {

            //=MAX(FLOOR((SIN(SQRT(B2)*3)+1)*B2*3.4584,1), 1)
            //new function with 1M tasks and up to 550 tasks/sec
            //user.MAX_SUBMIT_PER_SEC = (int)Math.min(Math.max((Math.sin(Math.sqrt((sw.getElapsedTime()*1.0/60000.0))*3)+1)*(sw.getElapsedTime()*1.0/60000.0)*3.459407,MIN_SUBMIT),MAX_SUBMIT);
            //new function with 2M tasks and up to 1000 tasks/sec
            //=MAX(FLOOR((SIN(SQRT((B2+0.11))*2.859678)+1)*(B2+0.11)*5.705,1), 0)
            user.MAX_SUBMIT_PER_SEC = (int)Math.min(Math.max((Math.sin(Math.sqrt((sw.getElapsedTime()*1.0/60000.0+0.11))*2.859678)+1)*(sw.getElapsedTime()*1.0/60000.0+0.11)*5.705,MIN_SUBMIT),MAX_SUBMIT);



            //=MAX(FLOOR((SIN(SQRT(C2)*4.05)+1)*B2*3.1,1), 1)
            //user.MAX_SUBMIT_PER_SEC = (int)Math.min(Math.max((Math.sin(Math.sqrt((sw.getElapsedTime()*1.0/60000.0)+3.2)*4.05)+1)*(sw.getElapsedTime()*1.0/60000.0)*3.1,MIN_SUBMIT),MAX_SUBMIT);
            //System.out.println("MAX_SUBMIT_PER_SEC: " + sw.getElapsedTime() + " : " + user.MAX_SUBMIT_PER_SEC);

            user.MAX_COM_PER_WS = (int)Math.min(user.MAX_SUBMIT_PER_SEC, user.MAX_COM_PER_WS2);
        }
        //used to have a monotonically increasing arrival rate
        else
        {


            if (user.INC_SUBMIT > 0)
            {
                //additive
                user.MAX_SUBMIT_PER_SEC = (int)Math.min(user.MAX_SUBMIT_PER_SEC + user.INC_SUBMIT, user.MAX_SUBMIT_PER_SEC2);
                if (user.MAX_SUBMIT_PER_SEC/(user.SUBMIT_TIME_QUANTA/1000) >= 100 && incSubmitByTen)
                {
                    user.INC_SUBMIT = user.INC_SUBMIT*10;
                    incSubmitByTen = false;

                }
            }
            else if (user.INC_SUBMIT_MULT > 1)
            {
                //multiplicative
                //System.out.println("user.MAX_SUBMIT_PER_SEC=" + user.MAX_SUBMIT_PER_SEC + " user.MAX_SUBMIT_PER_SEC*user.INC_SUBMIT_MULT=" + Math.ceil(user.MAX_SUBMIT_PER_SEC*user.INC_SUBMIT_MULT) + " user.MAX_SUBMIT_PER_SEC2=" + user.MAX_SUBMIT_PER_SEC2);
                user.MAX_SUBMIT_PER_SEC = (int)Math.min(Math.ceil(user.MAX_SUBMIT_PER_SEC * user.INC_SUBMIT_MULT), user.MAX_SUBMIT_PER_SEC2);

            }

            //System.out.println("MAX_SUBMIT_PER_SEC: " + sw.getElapsedTime() + " : " + user.MAX_SUBMIT_PER_SEC);

            user.MAX_COM_PER_WS = (int)Math.min(user.MAX_SUBMIT_PER_SEC, user.MAX_COM_PER_WS2);





            //System.out.println("Set MAX_SUBMIT_PER_SEC to " + user.MAX_SUBMIT_PER_SEC);
            //System.out.println("Value MAX_SUBMIT_PER_SEC2 = " + user.MAX_SUBMIT_PER_SEC2);
            //System.out.println("Set MAX_COM_PER_WS to " + user.MAX_COM_PER_WS);
            //System.out.println("Value INC_SUBMIT = " + user.INC_SUBMIT);
            //System.out.println("Value MIN_SUBMIT_PER_SEC = " + user.MIN_SUBMIT_PER_SEC);
        }
    }

    public static StopWatch sw = new StopWatch();

    public void run()
    {
        sw.start();
        initSubmitThrottle();

        int delay = user.INC_SUBMIT_INTERVAL*1000;   // delay for 5 sec.
        int period = user.INC_SUBMIT_INTERVAL*1000;  // repeat every sec.
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
                                      public void run() {
                                          updateSubmitThrottle();
                                          // Task here ...
                                      }
                                  }, delay, period);

        while ((user.INTERACTIVE || !user.finished) && !user.MONITOR_THREAD_EXIT)
        {
            try
            {

                if (user.DEBUG) System.out.println("user.INTERACTIVE => " + user.INTERACTIVE);
                if (user.DEBUG) System.out.println("user.finished => " + user.finished);
                if (user.DEBUG) System.out.println("user.MONITOR_THREAD_EXIT => " + user.MONITOR_THREAD_EXIT);
                Thread.sleep(1000);
            }
            catch (Exception e)
            {
                e.printStackTrace();

            }

        }


        System.out.println("timer.cancel()...");
        timer.cancel();
        System.out.println("monitoring thread exited!");

        /*
         while ((user.INTERACTIVE || !user.finished) && !user.MONITOR_THREAD_EXIT)
         {
             try
             {
             
             Thread.sleep(user.INC_SUBMIT_INTERVAL*1000);
             }
             catch (Exception e)
             {
                 e.printStackTrace();
 
             }
             updateSubmitThrottle();
             
         }
              */

    }


}


class UserRunThread extends Thread
{
    // Special end-of-stream marker. If a worker retrieves
    // an Integer that equals this marker, the worker will terminate.
    static final Object NO_MORE_WORK = new Object();
    //static final Object NO_MORE_WORK = new Object();

    UserRun user;
    WorkQueue eQ;
    WorkQueue rQ;
    boolean DEBUG;
    boolean DIPERF;
    private static long id = 0;


    public EndpointReferenceType instanceEPR;
    public GPPortType gp;
    public Notification userNot;

    public boolean shutingDown = false;

    public String machID = null;

    public StopWatch lt;
    GetResults gr = null;

    private int sendTasks = 0;
    private int recvTasks = 0;

    public boolean doneSending = false;

    public long SUBMIT_TIME_QUANTA;

    public synchronized void setSendTasks()
    {
        sendTasks++;
        user.setSendTasks();
    }

    public synchronized int getSendTasks()
    {
        return sendTasks;
    }

    public synchronized void setRecvTasks()
    {
        recvTasks++;
        user.setRecvTasks();
    }

    public synchronized int getRecvTasks()
    {
        return recvTasks;
    }



    UserRunThread(UserRun j, ThreadState ts, StopWatch lt)
    {
        this.user = j;
        //this.MAX_SUBMIT_PER_SEC = user.MAX_SUBMIT_PER_SEC;
        this.eQ = this.user.execQ;
        this.rQ = this.user.resultQ;

        this.DEBUG = this.user.DEBUG;
        this.DIPERF = this.user.DIPERF;

        this.instanceEPR = ts.instanceEPR;
        this.gp = ts.gp;
        this.userNot = ts.userNot;
        this.machID = ts.machID;

        this.lt = lt;

        this.SUBMIT_TIME_QUANTA = user.SUBMIT_TIME_QUANTA;



        if (gp == null)
        {
            System.out.println("GenericPortalWS not initialized properly... exiting!");
            System.exit(0);
        }
        else
        {
            //gs = new GetStatus(this, Ggp, lt);
            //gs.start();

            try
            {



                gr = new GetResults(this, gp, lt, userNot);
                gr.start();
            }
            catch (Exception e)
            {
                System.out.println("failed on thread creation: " + e + " ... exiting!");
                e.printStackTrace();
                System.exit(0);
            }
        }

    }

    UserRunThread(UserRun j, StopWatch lt)
    {

        this.user = j;
        //this.MAX_SUBMIT_PER_SEC = user.MAX_SUBMIT_PER_SEC;
        this.eQ = this.user.execQ;
        this.rQ = this.user.resultQ;

        this.DEBUG = this.user.DEBUG;
        this.DIPERF = this.user.DIPERF;

        this.instanceEPR = null;
        this.gp = null;
        this.userNot = null;
        this.machID = null;

        this.lt = lt;

        this.gr = null;

        this.SUBMIT_TIME_QUANTA = user.SUBMIT_TIME_QUANTA;
    }



    //this is not correct
    public long getID()
    {
        return id++;
    }


    public Executable processLine(String s) throws Exception
    {

        if (user.DATA_CACHING)
            return processLine_dataCaching(s);
        else
            return processLine_simple(s);
    }

    public Executable processLine_simple(String s) throws Exception
    {
        try
        {
            if (s == null)
            {
                throw new Exception("Formatting error in job description 'null'");
            }

            String ss = s.trim();
            String[] tokens = ss.split(" ");
            if (tokens.length > 0)
            {
                Executable exec = new Executable();
                //set exec
                exec.setCommand(tokens[0]);
                if (tokens.length > 1)
                {
                    String args[] = new String[tokens.length - 1];
                    for (int i=1;i<tokens.length;i++)
                    {
                        args[i-1] = tokens[i];
                    }

                    exec.setArguements(args);
                }
                else
                    exec.setArguements(null);
                exec.setEnvironment(null);
                exec.setDirectory(null);

                exec.setDataCaching(false);

                exec.setWallTime(user.MAX_WALL_TIME_MS);

                return exec;
            }
            else
            {
                throw new Exception("Formatting error in job description '" + s + "'");
            }
        }
        catch (IOException e)
        {
            throw new Exception("Formatting error in job description '" + s + "': " + e);
        }


    }

    public Executable processLine_dataCaching(String s) throws Exception
    {
        try
        {
            if (s == null)
            {
                throw new Exception("Formatting error in job description 'null'");
            }

            String ss = s.trim();
            String[] gTokens = ss.split("%%%");

            if (gTokens.length == 5)
            {
                Executable exec = new Executable();

                String comArgs = gTokens[0].trim();
                String[] tokens = comArgs.split(" ");
                if (tokens.length > 0)
                {
                    //Executable exec = new Executable();

                    //set exec
                    exec.setCommand(tokens[0]);
                    if (tokens.length > 1)
                    {
                        String args[] = new String[tokens.length - 1];
                        for (int i=1;i<tokens.length;i++)
                        {
                            args[i-1] = tokens[i];
                        }

                        exec.setArguements(args);
                    }
                    else
                        exec.setArguements(null);
                    exec.setEnvironment(null);
                    exec.setDirectory(null);





                }
                else
                {
                    throw new Exception("Formatting error in job description '" + s + "'");
                }

                DataFiles inputDataFiles = new DataFiles();

                String inputDataFilesString = gTokens[1].trim();
                String[] tokens2 = inputDataFilesString.split(" ");
                //task.setNumInputDataFiles(tokens2.length);
                if (tokens2.length > 0)
                {
                    exec.setDataCaching(true);
                }
                else
                {
                    exec.setDataCaching(false);
                }

                inputDataFiles.setLogicalName(tokens2);


                String inputDataFilesURLString = gTokens[2].trim();
                String[] tokens3 = inputDataFilesURLString.split(" ");

                if (tokens2.length != tokens3.length)
                {
                    throw new Exception("Formatting error in job description '" + s + "', incorrect number of inputDataFilesURLs ("+ tokens3.length + ") when expecting " + tokens2.length);

                }

                for (int i=0;i<tokens3.length;i++)
                {
                    if (tokens3[i].equalsIgnoreCase("null"))
                    {

                        tokens3[i] = null;

                    }
                }

                //task.setNumInputDataFiles(tokens2.length);
                inputDataFiles.setFileURL(tokens3);

                int fileSizes[] = new int[tokens3.length];

                for (int i=0;i<tokens3.length;i++)
                {

                    if (tokens3[i] == null)
                    {
                        fileSizes[i] = 0;
                    }
                    else
                    {
                        fileSizes[i] = user.EMULATED_FILE_SIZE;

                    }
                    inputDataFiles.setFileSize(fileSizes);
                }

                exec.setInputData(inputDataFiles);



                DataFiles outputDataFiles = new DataFiles();

                String outputDataFilesString = gTokens[3].trim();
                String[] tokens4 = outputDataFilesString.split(" ");

                outputDataFiles.setLogicalName(tokens4);

                String outputDataFilesURLString = gTokens[4].trim();
                String[] tokens5 = outputDataFilesURLString.split(" ");

                if (tokens4.length != tokens5.length)
                {
                    throw new Exception("Formatting error in job description '" + s + "', incorrect number of outputDataFilesURLs ("+ tokens5.length + ") when expecting " + tokens4.length);

                }

                for (int i=0;i<tokens5.length;i++)
                {
                    if (tokens5[i].equalsIgnoreCase("null"))
                    {

                        tokens5[i] = null;

                    }
                }

                outputDataFiles.setFileURL(tokens5);

                int fileSizesO[] = new int[tokens5.length];

                for (int i=0;i<tokens5.length;i++)
                {

                    if (tokens5[i] == null)
                    {
                        fileSizesO[i] = 0;
                    }
                    else
                    {
                        fileSizesO[i] = user.EMULATED_FILE_SIZE;

                    }
                    outputDataFiles.setFileSize(fileSizesO);
                }


                exec.setOutputData(outputDataFiles);
                exec.setWallTime(user.MAX_WALL_TIME_MS);


                return exec;

            }
            else
            {
                throw new Exception("Formatting error in job description, did not follow command arguements %%% inputDataFiles %%% outputDataFiles, '" + s + "'");
            }

        }
        catch (IOException e)
        {
            throw new Exception("Formatting error in job description '" + s + "': " + e);
        }


    }


    //public int MAX_SUBMIT_PER_SEC = 0;
    private int SUBMIT_SENT = 0;

    public StopWatch throttleTimer = new StopWatch();

    public synchronized int getSubmitSent()
    {
        return SUBMIT_SENT;

    }
    public synchronized void incSubmitSent()
    {
        SUBMIT_SENT++;

    }
    public synchronized void resetSubmitSent()
    {
        SUBMIT_SENT = 0;

    }

    private boolean firstThrottleStart = true;

    public boolean doExecutables(Executable execs[])
    {

        if (firstThrottleStart)
        {
            throttleTimer.start();
            firstThrottleStart = false;

        }

        //Executable exec = null;
        //try
        //{


        //    exec = processLine(command);
        //} catch (Exception e)
        //{
        //    return "Error: " + e;
        //}

        //int numTotalJobs = (int)Math.ceil(commands.length*1.0/user.MAX_COM_PER_WS);
        //if (DEBUG) System.out.println("Will brake down the " + commands.length + " commands into " + numTotalJobs + " jobs...");
        int NUM_TASKS = execs.length;


        //UserJob jobs[] = new UserJob[numTotalJobs];
        UserJob job = new UserJob();

        //for (int j=0;j<jobs.length;j++)
        //{


        //job = new UserJob();

        String userID = "127.0.0.1";
        try
        {
            userID = java.net.InetAddress.getLocalHost().getHostAddress();
        }
        catch (Exception e)
        {
            if (DEBUG) System.out.println("ERROR: java.net.InetAddress.getLocalHost().getHostName() failed " + e);
        }
        userID += ":console";

        job.setUserID(userID);
        job.setPassword("");

        /*
        Executable execs[] = null;
        if (j == jobs.length - 1)
        {
            execs = new Executable[commands.length - MAX_COM_PER_WS*j];
        }
        //else if (j == jobs.length - 1)
        //{
        //    execs = null;
        //}
        else
        {

            execs = new Executable[MAX_COM_PER_WS];
        }

        //if (execs != null)
        //{

                         */

        /*
        for (int i=0;i<execs.length;i++)
        {
            try
            {

                execs[i] = processLine(commands[j*MAX_COM_PER_WS+i]);
                execs[i].setId(getID());
                execs[i].setNotification(machID);

            } catch (Exception e)
            {
                //System.out.println();

                //return "Error at command["+i+"]: " + e;
                return null;
            }

        }
        //}
        //execs[0] = exec;
        */

        job.setExecutables(execs);
        //jobs[j].setNumExecs(NUM_TASKS);


        //}
        if (DEBUG) System.out.println("Ready to send job of size " + NUM_TASKS + " tasks!");

        for (int tt=0;tt<execs.length;tt++)
        {
            ExecutablePerformance ePerf = (ExecutablePerformance)execsPerf.get(execs[tt].getId());

            if (ePerf == null)
            {


                ePerf.setExecutable(new String("null"));
                ePerf = new ExecutablePerformance();
                ePerf.setTaskID(execs[tt].getId());
            }


            //ePerf.setSubmitTime();
            ePerf.setStartTime();

            //System.out.println("setting start time for task " + ePerf.getTaskID() + " to " + ePerf.getStartTime() + "...");


            execsPerf.put(ePerf.getTaskID(), ePerf);

        }


        //if (DEBUG) System.out.println("Task IDs send:");

        for (int tt=0;tt<execs.length;tt++)
        {
            //if (DEBUG) System.out.println(tt + " " + execs[tt].getId());
            //setSendTasks();
            //incSubmitSent();
            if (execsIDs.contains(execs[tt].getId()))
            {
                System.out.println("taskID duplicate... this should never happen: " + execs[tt].getId());
            }
            execsIDs.add(execs[tt].getId());
        }




        StopWatch sw = new StopWatch();
        sw.start();
        //if (DEBUG) System.out.println("USER: Submitting job with " + NUM_TASKS + " expected number of executables...");




        UserJobResponse jobRP = null;

        try
        {

            //System.out.println("starting submitting job...");

            jobRP = gp.userJob(job);

            //System.out.println("finished submitting job...");
        }
        catch (Exception e)
        {
            if (DEBUG) System.out.println("Error: " + e);
            if (DEBUG) e.printStackTrace();

            if (DEBUG) System.out.println("Task submission failed due to exception...");
            for (int tt=0;tt<execs.length;tt++)
            {
                execsIDs.remove(execs[tt].getId());
            }

            return false;
        }
        if (jobRP == null)
        {
            if (DEBUG) System.out.println("Task submission failed due to null value...");

            for (int tt=0;tt<execs.length;tt++)
            {
                execsIDs.remove(execs[tt].getId());
            }
            return false;

        }

        //Executable tExecs[] = job.getExecutables();
        //System.out.println("setting submit time...");
        for (int tt=0;tt<execs.length;tt++)
        {
            ExecutablePerformance ePerf = (ExecutablePerformance)execsPerf.get(execs[tt].getId());

            if (ePerf == null)
            {
                ePerf = new ExecutablePerformance();
                ePerf.setExecutable(new String("null"));

                ePerf.setTaskID(execs[tt].getId());
                ePerf.setStartTime();

            }
            //

            //ePerf.setTaskID(execs[tt].getId());
            ePerf.setSubmitTime();
            //System.out.println("setting submit time for task " + ePerf.getTaskID() + " !=? " + execs[tt].getId() + " to " + ePerf.getSubmitTime() + "...");
            //ePerf.setStartTime();

            execsPerf.put(ePerf.getTaskID(), ePerf);
            //System.out.println("setting submit time: " + tt);

        }
        //System.out.println("setting submit time done!");


        //boolean jobRP = gp.userJob(job);

        sw.stop();
        if (jobRP.isValid())
        {


            //release memory resources
            for (int tt=0;tt<execs.length;tt++)
            {
                if (DEBUG) System.out.println(tt + " " + execs[tt].getId());
                setSendTasks();
                incSubmitSent();
                execs[tt] = null;
            }

            execs = null;


            //if (DEBUG) System.out.println("USER: Job submitted succesfully (" + sw.getElapsedTime() + "ms)");
            //else

            //System.out.println("SEND: " + lt.getElapsedTime()*1.0/1000.0 + " : " + NUM_TASKS + " tasks submited succesful in " + sw.getElapsedTime() + " ms!");
            //userThread.user.jobTime.getElapsedTime() + 



//                if (DEBUG) System.out.println("Task IDs sent:");

            //          for (int tt=0;tt<execs.length;tt++)
            //        {
            //          if (DEBUG) System.out.println(tt + " " + execs[tt].getId());
            //        setSendTasks();
            //      if (execsIDs.contains(execs[tt].getId()))
            //    {
            //      System.out.println("taskID duplicate... this should never happen: " + execs[tt].getId());
            //}
            //execsIDs.add(execs[tt].getId());

            /*
            if (sendTasks != execsIDs.size())
            {
                System.out.println("Total number of tasks does not equal the execsIDs.size()... this should never happen: " + sendTasks + " != " + execsIDs.size());

            } */
            //    }

            if (this.user.POLL_INTERVAL <= 0) System.out.println(user.jobTime.getElapsedTime() + " : SEND : " + execs.length +" tasks submited succesful [" + getSendTasks() + "] in " + sw.getElapsedTime() + " ms!");

            //throttle the submission to have at most user.MAX_CONCURENT_TASKS tasks outstanding...
            if (user.MAX_CONCURENT_TASKS > 0)
            {

                while (getSendTasks() - getRecvTasks() > user.MAX_CONCURENT_TASKS)
                {



                    try
                    {

                        Thread.sleep(100);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            if (user.MAX_SUBMIT_PER_SEC > 0)
            {

                if (this.shutingDown == false && getSendTasks() < user.NUM_TASKS && getSubmitSent() >= user.MAX_SUBMIT_PER_SEC)
                {
                    long timeLeft = SUBMIT_TIME_QUANTA - throttleTimer.getElapsedTime();
                    if (timeLeft > 20)
                    {


                        try
                        {
                            if (DEBUG) System.out.println(getSubmitSent() + " tasks submitted in " + throttleTimer.getElapsedTime() + " ms, sleeping for " + timeLeft + " ms");

                            //System.currentTimeMillis();

                            Thread.sleep(timeLeft);
                        }
                        catch (Exception sss)
                        {
                            sss.printStackTrace();

                        }

                    }

                }

                if (throttleTimer.getElapsedTime() >= SUBMIT_TIME_QUANTA)
                {

                    if (DEBUG) System.out.println("throttling submit rate: " + SUBMIT_SENT + " notifications sent to workers in " + throttleTimer.getElapsedTime() + " ms, maximum allowed notifications " + user.MAX_SUBMIT_PER_SEC + " per " + SUBMIT_TIME_QUANTA + " ms...");

                    throttleTimer.reset();
                    resetSubmitSent();
                    throttleTimer.start();
                }
            }


            /*
        try
        {
            Thread.sleep(500);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        } 
        */

            //NUM_TASKS

            //if (i == jobs.length - 1)
            //{

            //    userJob = true;
            //}

        }
        else
        {

            if (DEBUG) System.out.println("USER: Jobs submition failed (" + sw.getElapsedTime() + "ms)");
            else

            {

                if (DEBUG) System.out.println("Job submition failed due to invalid job results...");
            }
            for (int tt=0;tt<execs.length;tt++)
            {
                execsIDs.remove(execs[tt].getId());
            }
            return false;
        }
        if (DIPERF) System.out.println("USER:userJob(job): " + sw.getElapsedTime() + " ms");

        /*try {
                BufferedWriter out = new BufferedWriter(new FileWriter("/home/iraicu/java/GenericClient.1.1/WScall_perf.txt", true));
                out.write(NUM_TASKS + " " + sw.getElapsedTime() + "\n");
                out.close();
            } catch (IOException e) {
            }
            */

        sw.reset();

        /*

        try
        {

               System.out.println("USER: finished sending tasks!");

                //System.out.println("USER: waiting for getStatus thread to terminate...");
                //gs.join();
                System.out.println("USER: waiting for getResults thread to terminate...");
                gr.join();
                System.out.println("USER: all threads terminated!");
            } catch (Exception e)
            {
                if (DEBUG) System.out.println("USER: ERROR in waiting for threads to terminate...: " + e.getMessage());

            }
            resultsRetrieved = true;
            lt.stop();
            System.out.println("Received all results in " + lt.getElapsedTime() + " ms");

        */

        return true;//"FIX THIS: should be the results...";//sendJob(job);
    }

    public Set execsIDs = Collections.synchronizedSet(new HashSet());

    public Map execsPerf = Collections.synchronizedMap(new HashMap());


    public Executable getFinalStack(String exec)
    {
        Executable execs = null;//new Executable[1];
        try
        {


            execs = processLine(exec);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        String taskID = user.getID(machID);
        execs.setId(taskID);
        execs.setNotification(machID);

        return execs;
        //if (doExecutables(execs))
        //{
        //    System.out.println("Final Stacking submitted succesfully...");

        //}
        //else
        //{
        //    System.out.println("Final Stacking submission failed...");
        //}




    }

    public synchronized Executable[] getExecs(int numExecs)
    {
        int totNumExecs = (int)Math.max(Math.min(eQ.size(), numExecs), 1);
        List list = new LinkedList();    // Doubly-linked list

        int curNumExecs = 0;
        //while (!eQ.isEmpty() && curNumExecs < numExecs)
        //changed logic
        //System.out.println("Requesting at most " + totNumExecs + " tasks to submit...");
        while (curNumExecs < totNumExecs && !shutingDown)
        {
            if (DEBUG) System.out.println("waiting for executable to be picked up off the execQ...");
            Object o = null;
            try
            {


                o = eQ.remove();
            }
            catch (Exception e)
            {
                System.out.println("Error: " + e);
                e.printStackTrace();
                return null;

            }
            //if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): tQ.remove() " + index++);
            if (o == NO_MORE_WORK)
            {
                if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): if(o == NO_MORE_WORK)... shutting down thread...");
                //shutingDown = true;

                if (DEBUG) System.out.println("doneSending = true : 4");

                doneSending = true;
                break;
            }
            else
            {

                list.add(o);
                curNumExecs++;
            }
        }

        if (list.size() > 0)
        {



            Executable execs[] = new Executable[list.size()];

            for (int i=0;i<execs.length;i++)
            {
                String exec = null;
                try
                {

                    exec = (String)list.remove(0);
                    execs[i] = processLine(exec);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }

                String taskID = user.getID(machID);
                execs[i].setId(taskID);
                execs[i].setNotification(machID);



                ExecutablePerformance ePerf = (ExecutablePerformance)execsPerf.get(execs[i].getId());
                if (ePerf == null)
                {
                    ePerf = new ExecutablePerformance();
                }

                ePerf.setTaskID(execs[i].getId());
                ePerf.setExecutable(exec);

                execsPerf.put(ePerf.getTaskID(), ePerf);


                if (user.STACKING)
                {

                    UserRun.AstroDataCachingNames adcn = (UserRun.AstroDataCachingNames)user.cachingInfoMap.get(exec);
                    if (adcn == null)
                    {
                        if (DEBUG) System.out.println("exec " + exec + " not found in cachingInfoMap, this should not have happened, double check why!");
                    }
                    else
                        user.cachingStackingMap.put(taskID, adcn);

                }
            }

            //System.out.println("Found " + execs.length + " tasks to submit...");

            if (list != null)
            {
                list.clear();
                list = null;
            }

            return execs;
        }
        else return null;


    }

    /*

    public String waitForNotification() throws Exception
    {
        //boolean test = false;
        //ResourceKey key = null;
        //String key = null;
        String taskID = null;

        while (taskID == null)
        {

            try
            {

                //key = userNot.recv();
                if (DEBUG) System.out.println("waiting for notification at " + machID);

                taskID = userNot.recvString();

                if (taskID == null)
                {
                    //try
                    //{
                    if (DEBUG) if (DIPERF == false) System.out.println("Notification received, but taskID was null...");
                    user.setErrorNotification();
                    //throw new Exception("Notification received, but taskID was null...");
                    //return null;

                    //Thread.sleep(1000);
                    //} catch (Exception e)
                    //{
                    //    if (DEBUG) if (DIPERF == false) System.out.println("ERROR: sleep() " + e);

                    //}

                } else
                {
                    String tokens[] = taskID.split(" ");
                    if (tokens.length == 2)
                    {
                        if (execsIDs.contains(tokens[0]))
                        {
                            execsIDs.remove(tokens[0]);
                            if (DEBUG) System.out.println("Received taskID = " + tokens[0] + " with an exit code of " + tokens[1]);

                            if (tokens[1].contentEquals(new StringBuffer("0")))
                            {
                                user.setSuccessExecs();
                            } else
                            {
                                user.setFailedExecs();
                            }
                            return taskID;
                        }

                        //taskID not found, perhaps it was a duplicate notification
                        else
                        {
                            if (DEBUG) System.out.println("Received taskID = " + taskID + " but it was not found in the list of notifications this thread was expecting... perhaps it was a duplicate notification...");
                            taskID = null;
                            user.setErrorNotification();

                        }

                        //if (DEBUG) if (DIPERF == false) System.out.println("Received key = " + key);
                        //return getEPRfromKey(key);

                    } else
                    {
                        if (DEBUG) System.out.println("Received taskID = " + taskID + " but it was not formatted properly.... it should hae been 'taskID exitCode' separated by a space...");
                        taskID = null;
                        user.setErrorNotification();


                    }
                }

            } catch (Exception e)
            {
                if (DEBUG) System.out.println("exception received in notification recvString() " + e);
                if (DEBUG) e.printStackTrace();
                throw new Exception("exception received in notification recvString() " + e);
                //userThread.user.setErrorNotification();

            }
        }

        return taskID;

    }
    */


    public int bundleSize = 1;

    public void run()
    {
        int index = 0;


        //if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): start " + index++);
        try
        {
            StopWatch sw = new StopWatch();

            /*
            while (!shutingDown) 
                {
                sw.start();

                bundleSize = user.MAX_COM_PER_WS;

                if (DEBUG) System.out.println("Getting at most " + bundleSize + " tasks...");


                Executable execs[] = getExecs(bundleSize);

                if (execs == null) {
                    if (DEBUG) System.out.println("Got null task, shuting down UserRunThread...");
                    shutingDown = true;
                    break;



                } else {
                    if (DEBUG) System.out.println("Got " + execs.length + " tasks!");



                    if (user.ONE_TASK_PER_RESOURCE && DEBUG) System.out.println("Sending task...");
                    if (DEBUG) System.out.println("doExecutables(execs)...");
                    while (!doExecutables(execs)) {
                        try {
                            System.out.println("failed doExecutables(execs), sleeping for 1000 ms and trying again...");
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            System.out.println("Error: " + e);
                            e.printStackTrace();

                        }
                    }

                }

                sw.stop();
                if (DIPERF) System.out.println("USER:UserRunThread:run(): " + sw.getElapsedTime() + " ms");
                sw.reset();

            }
            */

            if (!shutingDown)
            {
                SubmitThread submitThread[] = new SubmitThread[user.MAX_NUM_SUBMIT_THREADS];
                for (int i=0;i<user.MAX_NUM_SUBMIT_THREADS;i++)
                {
                    submitThread[i] = new SubmitThread(this, i);
                }

                for (int i=0;i<user.MAX_NUM_SUBMIT_THREADS;i++)
                {
                    submitThread[i].start();
                }

                //start threads
            }

            //doneSending = true;

            //if (!user.ONE_TASK_PER_RESOURCE) {

            if (DEBUG) System.out.println("Waiting for the getResult thread to terminate -- i.e. receive all expected notifications!");
            while (!shutingDown)
            {
                if (DEBUG) System.out.println("getResult Thread is still alive, waiting another 1000 ms and checking again...");
                gr.join(1000);

                if (gr.isAlive() == false)
                {
                    if (DEBUG) System.out.println("getResult Thread is not alive anymore, it means that all notifications have been received...");
                    shutingDown = true;

                }
            }

            if (gr.isAlive())
            {
                gr.stop();
                shutingDown = true;
                if (DEBUG) System.out.println("getResult Thread is still alive, although it should not be... shuting it down forcefully...");
            }


            //}




            if (this.user.POLL_INTERVAL <=0)
                if (DEBUG) System.out.println("************* UserRunThread finished succesfully: sent " + this.getSendTasks() + " recv " + this.getRecvTasks());

        }
        catch (Exception e)
        {
            System.out.println("Error: " + e);
            e.printStackTrace();
            //throw new Exception(e);
        }



    }



}


class ThreadState implements Cloneable
{
    public EndpointReferenceType instanceEPR;
    public GPPortType gp;
    public Notification userNot;
    public String machID;

    public ThreadState(EndpointReferenceType instanceEPR, GPPortType gp, Notification userNot, String machID)
    {
        this.gp = gp;
        this.instanceEPR = instanceEPR;
        this.userNot = userNot;
        this.machID = machID;
    }

    public ThreadState()
    {
        this.gp = null;
        this.instanceEPR = null;
        this.userNot = null;
        this.machID = null;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /*
    public ThreadState(ThreadState ts)
    {
        this.gp = (GPPortType)ts.gp.clone();
        this.instanceEPR = (EndpointReferenceType)ts.instanceEPR.clone();
        this.userNot = (Notification)ts.userNot.clone();
        this.machID = (String)ts.machID.clone();
    } */


}



/* 
   An object of class StatCalc can be used to compute several simple statistics
   for a set of numbers.  Numbers are entered into the dataset using
   the enter(double) method.  Methods are provided to return the following
   statistics for the set of numbers that have been entered: The number
   of items, the sum of the items, the average, and the standard deviation.
*/

/*
class StatCalc {

    private int count;   // Number of numbers that have been entered.
    private double sum;  // The sum of all the items that have been entered.
    private double squareSum;  // The sum of the squares of all the items.

    public synchronized void enter(double num) {
        // Add the number to the dataset.
        count++;
        sum += num;
        squareSum += num*num;
    }

    public int getCount() {   
        // Return number of items that have been entered.
        return count;
    }

    public double getSum() {
        // Return the sum of all the items that have been entered.
        return sum;
    }

    public double getMean() {
        // Return average of all the items that have been entered.
        // Value is Double.NaN if count == 0.
        return sum / count;  
    }

    public double getStandardDeviation() {  
        // Return standard deviation of all the items that have been entered.
        // Value will be Double.NaN if count == 0.
        double mean = getMean();
        return Math.sqrt( squareSum/count - mean*mean );
    }

}  // end of class StatCalc

*/

class KillListener extends Thread 
{
    boolean DIPERF = false;
    boolean PROFILING = false;


    String str = null;
    UserRun worker;
    boolean DEBUG;

    UserRunThread[] userThreads;

    KillListener(UserRun wr, UserRunThread[] userThreads) 
    {
        this.worker = wr;

        this.DEBUG = this.worker.DEBUG;
        this.userThreads = userThreads;
    }

    public void run()
    {
        try
        {




            if (!DIPERF && !PROFILING) System.out.println("Waiting for shutdownHook to be triggered...");
            Runtime.getRuntime().addShutdownHook(new Thread()
                                                 {
                                                     public void run()
                                                     {
                                                         if (userThreads != null)
                                                         {
                                                         }
                                                         for (int i=0;i<userThreads.length;i++)
                                                         {

                                                             if (userThreads[i] != null)
                                                             {

                                                                 userThreads[i].doneSending = true;
                                                                 userThreads[i].shutingDown = true;
                                                                 userThreads[i].interrupt();
                                                                 userThreads[i].stop();
                                                             }
                                                         }

                                                         System.out.println("ShutdownHook triggered successfully!");


                                                     }
                                                 });



        }
        catch (Exception e)
        {

            //if (DEBUG) System.out.print("WORKER: Error: " + e);
            e.printStackTrace();

            //return;
        }
    }

}

