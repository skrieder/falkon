//SVN versoin
/*
 * This file is licensed under the terms of the Globus Toolkit Public License
 * v3, found at http://www.globus.org/toolkit/legal/4.0/license-v3.html.
 * 
 * This notice must appear in redistributions of this file, with or without
 * modification.
 */
package org.globus.GenericPortal.clients.GPService_instance;

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

import java.io.FileInputStream;

import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.GenericPortal.services.core.WS.impl.GPConstants;
import org.globus.GenericPortal.stubs.GPService_instance.GPPortType;
import org.globus.GenericPortal.stubs.GPService_instance.service.GPServiceAddressingLocator;
import org.globus.wsrf.ResourceKey;

import org.globus.wsrf.encoding.ObjectDeserializer;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.xml.sax.InputSource;
//import org.apache.axis.message.addressing.AttributedURI;


import org.globus.wsrf.utils.AddressingUtils;

import org.globus.GenericPortal.stubs.GPService_instance.*;

import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.*;//ThreadLocal
import java.util.zip.*;

import org.globus.GenericPortal.common.*;

import org.globus.GenericPortal.clients.GPService_instance.*;


public class WorkerRun
{
    //AstroPortal: this must be set to true for stacking code to work
    public static final boolean STACKING_ENABLE = true; 
    public static int NUM_WORKERS = 1;


    //public static String ARGS[] = null;


    public static void main(String args[])
    {

        Boolean shuttingDown = new Boolean(false);

        parseArgs(args);

        //ARGS = args;

        WorkerRunThread wrThread[] = new WorkerRunThread[NUM_WORKERS];

        for (int i=0;i<NUM_WORKERS;i++)
        {
            System.out.println("Started worker " + i + "!");
            wrThread[i] = new WorkerRunThread(args, shuttingDown);
            wrThread[i].start();

            try
            {
                //Thread.sleep(1500);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


        //int aliveWorkers = 0;
        //int deadWorkers = 0;

        //while (deadWorkers < NUM_WORKERS)
        //{
        //    aliveWorkers = 0;
        //    deadWorkers = 0;



        //for (int i=0;i<NUM_WORKERS;i++)
        //{

        /*
        System.out.println("Waiting for workers to finish...");



        synchronized (shuttingDown)
        {

            try
            {


                while (shuttingDown.booleanValue() == false)
                {
                
                    System.out.println("Waiting for all workers to finish for 1000 ms...");
                    shuttingDown.wait(1000);

                }
                System.out.println("Workers finished!");
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        } 
        */  

        //if (wrThread[i].shuttingDown)
        //    deadWorkers++;
        //else
        //    aliveWorkers++;
        //}

        /*
            try
            {

                Thread.sleep(1000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } */


        /*
        System.out.println("Stoping all threads...");
        ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
        root.stop();
          */

        // Find the root thread group
/*    ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
    while (root.getParent() != null) {
        root = root.getParent();
    }
    
    // Visit each thread group
    visit(root, 0);
  */        


        //System.out.println("Final: System.exit(-1)");
        //System.exit(-1);


    }

    /*
    
    // This method recursively visits all thread groups under `group'.
    public static void visit(ThreadGroup group, int level) {
        // Get threads in `group'
        int numThreads = group.activeCount();
        Thread[] threads = new Thread[numThreads*2];
        numThreads = group.enumerate(threads, false);
    
        // Enumerate each thread in `group'
        for (int i=0; i<numThreads; i++) {
            // Get thread
            Thread thread = threads[i];
            System.out.println("thread: " + thread.toString());
        }
    
        // Get thread subgroups of `group'
        int numGroups = group.activeGroupCount();
        ThreadGroup[] groups = new ThreadGroup[numGroups*2];
        numGroups = group.enumerate(groups, false);
    
        // Recursively visit each subgroup
        for (int i=0; i<numGroups; i++) {
            visit(groups[i], level+1);
        }
    }
      */

    public static void parseArgs(String[] args)
    {
        if (args.length < 1)
        {
            usage(args);
            //return;
        }

        int ctr;
        for (ctr = 0; ctr < args.length; ctr++)
        {

            //System.out.println("Checking: " + args[ctr]);
            if (args[ctr].equals("-NUM_WORKERS") && ctr + 1 < args.length)
            {
                ctr++;


                try
                {

                    NUM_WORKERS = Integer.parseInt(args[ctr]);
                    System.out.println("Number of workers: " + NUM_WORKERS);
                }
                catch (Exception e)
                {
                    System.out.println("Error in the number of workers arguement '" + args[ctr] + "': " + e);
                    usage(args);
                }

            }
            else
            {
                //  System.out.println("failed at: " + args[ctr]);
                //ctr++;

                //usage(args);
            }
        }

    }

    public static void usage(String[] args)
    {
        System.out.println("Help Screen: ");
        System.out.println("-help <>");
        System.exit(1);

    }


}


class WorkerRunThread extends Thread
{

    private static int DEAD_WORKERS = 0;

    public static synchronized void setDeadWorkers()
    {
        DEAD_WORKERS++;

    }
    public static synchronized int getDeadWorkers()
    {
        return DEAD_WORKERS;

    }

    public int NUM_WORKERS = 1;

    public static String CLIENT_DESC = null;

    static boolean AUTHENTICATE = false;
    static boolean AUTHORIZE = false;
    static boolean ENCRYPT = false;
    static boolean SIGN = false;
    static boolean TSL = false;
    static boolean MSG = false;
    static boolean CONV = false;

    public Boolean shuttingDown = null;

    static {
        Util.registerTransport();
    }



    public String machID;

    public boolean DEBUG;
    public boolean DIPERF;
    public boolean PROFILING;
    public String DATA_TRACE;

    public int activeNotifications; //# of concurent notifications
    public int MAX_NUM_NOTIFICATIONS;
    public int activeThreads;   //# of concurent threads accross all the jobs
    public int MAX_NUM_THREADS;
    public int queueLength; //# of individual tasks (image ROI) scheduled to take place

    public int LIFETIME;    //time in ms that the worker should accept work
    public int LIFETIME_STACK;  //time in ms that the worker should accept work

    public int IDLETIME;
    public StopWatch idleTime;


    //public Thread                              notificationThread;

    public Notification workerNot;
    private int SO_TIMEOUT;

    public String fileEPR;
    public String serviceURI;
    public EndpointReferenceType homeEPR;
    public String homeURI;

    public Random rand;

    public String scratchDisk;

    GPPortType gp;


    WorkQueue threadQ;
    StopWatch lt;
    public int jobID;
    public boolean INTERACTIVE;
    public boolean STANDALONE;
    public String DESC_FILE;
    public int JOB_SIZE;

    public int NUM_JOBS;
    //public int ROI_Height;
    //public int ROI_Width;



    public BufferedReader inBufRead = null;


    //public int numTasksFailed = 0;
    public int numTasksCompleted = 0;
    public Random randIndex;

    public String[] data;

    long elapsedTime;

    public boolean CACHING;
    public WorkerState cacheGrid = null;

    public int CACHE_SIZE_MB = 0;


    //public EndpointReferenceType notificationEPR;

    public String args[] = null;

    WorkerRun wRun = null;

    public boolean isResetCache = false;

    //constructor
    public WorkerRunThread(String args[], Boolean shuttingDown)
    {
        //this.ROI_Height = 100;
        //this.ROI_Width = 100;
        this.randIndex = new Random();

        this.machID = "localhost:0";
        this.scratchDisk = "";

        this.rand = new Random();
        this.DEBUG = false;
        this.DIPERF = false;
        this.PROFILING = false;
        this.DATA_TRACE = null;
        this.activeNotifications = 0;
        this.activeThreads = 0;
        this.MAX_NUM_THREADS = 10;
        this.MAX_NUM_NOTIFICATIONS = 1000000; //some large #, if setting lower, there needs a way to enforce it...
        this.queueLength = 0;
        this.SO_TIMEOUT = 0; //receive will block forever...
        this.LIFETIME = 0;
        this.LIFETIME_STACK = 0;
        this.IDLETIME = 0;
        this.idleTime = new StopWatch();
        this.idleTime.reset();
        this.idleTime.start();

        this.threadQ = new WorkQueue();
        this.lt = new StopWatch();
        this.jobID = 0;
        this.INTERACTIVE = false;
        this.elapsedTime = 0;
        this.STANDALONE = false;

        this.CACHING = false;

        this.wRun = wRun;

        if (DEBUG) System.out.println("WORKER:parseArgs()...");

        parseArgs(args);

        this.args = args;

        this.shuttingDown = shuttingDown;
/*

        Runtime.getRuntime().addShutdownHook(new Thread() {
    public void run() { 
    // Your code goes here.

        System.out.println("Worker died unexpectedly... perhaps the ctrl-c was pressed, or the kill signal was received... exiting!");
        shutDown("Worker died unexpectedly...");
        System.out.println("Shutdown complete!");

        System.out.println("Stoping all threads...");
        ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
        root.stop();


    }
});
*/

    }



    public long getElapsedTime()
    {
        return elapsedTime;

    }


    public void incElapsedTime(long t)
    {
        elapsedTime += t;

    }


    public void initFileRead()
    {

        try
        {


            inBufRead = new BufferedReader(new FileReader(DESC_FILE));
        }
        catch (Exception e)
        {
            if (DIPERF == false) System.out.println("Error in reading file: " + e);
            e.printStackTrace();


        }
    }


    public synchronized String readLine()
    {

        // Random integers that range from from 0 to n
        //int n = 10;
        int index = randIndex.nextInt(data.length);
        String str = data[index];
        //System.out.println(index + ": " + str);
        return str;



    }


    public int fileSize()
    {
        try
        {


            String str;
            inBufRead = new BufferedReader(new FileReader(DESC_FILE));
            int i=0;
            while ((str = inBufRead.readLine()) != null)
            {
                i++;
            }
            inBufRead.close();
            return i;
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return 0;

        }

    }

    public void fileRead()
    {
        System.out.println("fileRead() is not implemente for the standalone WorkerRunThread... exiting!");
        System.exit(2);
        /*
        try
        {

            String str = null;
            int fileSize = fileSize();
            //System.out.println("Reading file '" + DESC_FILE + "' of size " + fileSize);
            data = new String[fileSize];
            inBufRead = new BufferedReader(new FileReader(DESC_FILE));
            int i=0;
            while ((str = inBufRead.readLine()) != null)
            {
                data[i] = str;

                //System.out.println(i + ": " + str);
                i++;
            }
            //System.out.println("Finished reading file!");
        } catch (Exception e)
        {
            if (DIPERF == false) System.out.println("Error in reading file '" + DESC_FILE + "': " + e);

        }
        */
    }


    public synchronized String readLineFile()
    {
        if (inBufRead == null)
        {
            initFileRead();
        }
        String str;
        try
        {

            if ((str = inBufRead.readLine()) != null)
                return str;
            else
            {
                initFileRead();
                if ((str = inBufRead.readLine()) != null)
                    return str;
                else
                {
                    if (DIPERF == false) System.out.println("Error in reading work description, exiting...");
                    shutDown("1: Error in reading work description");

                }
            }
        }
        catch (Exception e)
        {
            if (DIPERF == false) System.out.println("Error in readLine(): " + e);
            e.printStackTrace();


        }
        return null;
    }

    public synchronized int getJobID()
    {
        jobID++;
        return jobID;

    }


    public void notificationStats()
    {

        if (DEBUG) if (DIPERF == false) System.out.println("Notification Summary");
        if (DEBUG) if (DIPERF == false) System.out.println("Total # of notifications received: " + workerNot.success);
        if (DEBUG) if (DIPERF == false) System.out.println("Total # of notifications failed: " + workerNot.failed);
            //if (DEBUG) if (DIPERF == false) System.out.println("Total # of notifications retransmited: " + workerNot.retransmited);
            //if (DEBUG) if (DIPERF == false) System.out.println("Total # of notifications out of order: " + workerNot.out_of_order);
            //if (DEBUG) if (DIPERF == false) System.out.println("Total # of notifications duplicated: " + workerNot.duplicate);
    }


    public EndpointReferenceType waitForNotification() throws Exception
    {
        //boolean test = false;
        ResourceKey key = null;
        //String key = null;

        while (key == null)
        {
            try
            {

                key = workerNot.recv();
            }
            catch (Exception e)
            {
                if (DEBUG) System.out.println("exception received in notification recv() " + e);
                if (DEBUG) e.printStackTrace();

            }

            if (key == null)
            {
                /*
                if (IDLETIME == 0 && LIFETIME == 0)
                { */
                try
                {
                    if (DEBUG) System.out.println("Notification received, but key was null :( sleeping 1000 ms and trying again...");

                    Thread.sleep(1000);
                }
                catch (Exception e)
                {
                    if (DEBUG) if (DIPERF == false) System.out.println("ERROR: sleep() " + e);
                    e.printStackTrace();


                }
                /*
            } else
            {


               // if (getActiveNotifications() == 0)
               // {


                //    if (DEBUG) if (DIPERF == false) System.out.println("Notification received, but key was null :( no new notification must have been received within the alloted idle time allowed of " + SO_TIMEOUT + " ms, and there are no active notifications pending, shuting down...");
               //     shutDown();
               // } else
               // {

                    try
                    {
                        if (DEBUG) if (DIPERF == false) System.out.println("Notification received, but key was null :(, sleeping 1000 ms and trying again...");

                        Thread.sleep(1000);
                    } catch (Exception e)
                    {
                        if (DEBUG) if (DIPERF == false) System.out.println("ERROR: sleep() " + e);
                        e.printStackTrace();


                    }

               // }
            }     */


            }
            else
            {
                if (DEBUG) System.out.println("Received key = " + key.getValue());
                return getEPR(key);
                //if (DEBUG) if (DIPERF == false) System.out.println("Received key = " + key);
                //return getEPRfromKey(key);
            }

            if (this.shuttingDown.booleanValue())
            {
                return null;
                //this.stop();
            }

        }

        return getEPR(key);

    }

    /*
    private static void printResourceProperties(GPPortType gp)
                    throws Exception {
            GetResourcePropertyResponse numJobsRP, stateRP, numWorkersRP, numUserResultsRP, numWorkerResultsRP, lastLogRP;
            String numJobs, state, numWorkers, numUserResults, numWorkerResults, lastLog;

            numJobsRP = gp.getResourceProperty(GPConstants.RP_NUMJOBS);
            stateRP = gp.getResourceProperty(GPConstants.RP_STATE);
            numWorkersRP = gp.getResourceProperty(GPConstants.RP_NUMWORKERS);
            numUserResultsRP = gp.getResourceProperty(GPConstants.RP_NUMUSERRESULTS);
            numWorkerResultsRP = gp.getResourceProperty(GPConstants.RP_NUMWORKERRESULTS);


            numJobs = numJobsRP.get_any()[0].getValue();
            state = stateRP.get_any()[0].getValue();
            numWorkers = numWorkersRP.get_any()[0].getValue();
            numUserResults = numUserResultsRP.get_any()[0].getValue();
            numWorkerResults = numWorkerResultsRP.get_any()[0].getValue();

            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: NumJobs RP: " + numJobs);
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: State RP: " + state);
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: NumWorkers RP: " + numWorkers);
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: NumUserResults RP: " + numUserResults);
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: NumWorkerResults RP: " + numWorkerResults);

    }


    private static boolean getWorkerWorkAvailable(GPPortType gp)
                    throws Exception {
        GetResourcePropertyResponse numUserResultsRP;
        String numUserResults;

        numUserResultsRP = gp.getResourceProperty(GPConstants.RP_NUMUSERRESULTS);

        numUserResults = numUserResultsRP.get_any()[0].getValue();

        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: NumUserResults RP: " + numUserResults);

        if (Integer.valueOf(numUserResults).intValue() > 1) 
        {
            return true;
        }
        else
            return false;
    }
    */



    public String getMachNamePort()
    {

        String machName = "";
        //String machIP;
        //Utility tools = new Utility();
        try
        {

            //    int iPID = tools.pid();
            //    String tPID = null;

            //    if ( iPID < 0 || iPID > 65535) 
            //    {
            //        tPID = (new Integer(tools.rand())).toString();
            //    }
            //    else
            //    {
            //        tPID = (new Integer(iPID)).toString();

            //    }

            machName = java.net.InetAddress.getLocalHost().getCanonicalHostName();
            //machName = java.net.InetAddress.getLocalHost().getHostAddress();
            if (workerNot == null)
            {
                machName = machName +  ":0";
            }
            else
                machName = machName +  ":" + workerNot.recvPort;

            //machIP = java.net.InetAddress.getLocalHost().getHostAddress();
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Machine ID = " + machName);

        }
        catch (Exception e)
        {
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: ERROR: java.net.InetAddress.getLocalHost().getHostName() failed " + e);
            e.printStackTrace();

        }
        return machName;
    }



    public String getMachNamePID()
    {

        String machName = "";
        //String machIP;
        Utility tools = new Utility();
        try
        {

            int iPID = tools.pid();
            String tPID = null;

            if (iPID < 0 || iPID > 65535)
            {
                tPID = (new Integer(tools.rand())).toString();
            }
            else
            {
                tPID = (new Integer(iPID)).toString();

            }

            machName = java.net.InetAddress.getLocalHost().getCanonicalHostName();
            machName = machName +  ":" + tPID;
            //machIP = java.net.InetAddress.getLocalHost().getHostAddress();
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Machine ID = " + machName);

        }
        catch (Exception e)
        {
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: ERROR: java.net.InetAddress.getLocalHost().getHostName() failed " + e);
            e.printStackTrace();

        }
        return machName;
    }

    /*
        public static void doWork()
        {
            if (DEBUG) if (DIPERF == false) System.out.println("simulating doing work... sleeping for 10 seconds...");
            try
            {
            
                Thread.sleep(10000);
            }
            catch (Exception e) {
                if (DEBUG) if (DIPERF == false) System.out.println("WORKER: ERROR doWork(): " + e.getMessage());
            }

        }   */

    // public static void waitForNotification(/*String host, int port*/)
    // {
    /*
        try
        {
        
        String host = "tg-viz-login1";
        int port = 50002;

        DatagramSocket  socket;
      DatagramPacket  packet;
      InetAddress     address;
      byte[]          message = new byte[256];

      //
      // Send empty request
      //
      socket = new DatagramSocket(port);
      address=InetAddress.getByName(host);
      packet = new DatagramPacket(message, message.length, address, port);
      //socket.send(packet);

      //
      // Receive reply and display on screen
      //
      //packet = new DatagramPacket(message, message.length);
      if (DEBUG) if (DIPERF == false) System.out.println("Waiting for notification...");

      socket.receive(packet);
      String received =new String(packet.getData(), 0);
      if (DEBUG) if (DIPERF == false) System.out.println("Received notification: " + received);

      //Socket.close();
        }
        catch (Exception e) {
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: ERROR waitForNotification(): " + e.getMessage());
        }  */


    //     }

    /*
    public static void main_old(String[] args) {


        //start thread

        
        if (nThread == null) 
        {
        nThread = new NotificationThread(this);
        nThread.start();
        }      

            boolean isRegistered = false;

            String machID = getMachNamePID();

            StopWatch sw = new StopWatch();

            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: v0.1 - " + machID);
        GPServiceAddressingLocator instanceLocator = new GPServiceAddressingLocator();

        try {
            int numJobs = Integer.parseInt(args[1]);

                        sw.start();
                        EndpointReferenceType instanceEPR;

            if (args[0].startsWith("http")) {
                // First argument contains a URI
                String serviceURI = args[0];
                // Create endpoint reference to service
                instanceEPR = new EndpointReferenceType();
                instanceEPR.setAddress(new Address(serviceURI));
            } else {
                // First argument contains an EPR file name
                String eprFile = args[0];
                // Get endpoint reference of WS-Resource from file
                FileInputStream fis = new FileInputStream(eprFile);
                instanceEPR = (EndpointReferenceType) ObjectDeserializer
                        .deserialize(new InputSource(fis),
                                EndpointReferenceType.class);
                fis.close();
            }
                        sw.stop();
                        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Read EPR fro disk (" + sw.getElapsedTime() + "ms)");
                        sw.reset();



                        sw.start();
            // Get PortType
            GPPortType gp = instanceLocator
                    .getGPPortTypePort(instanceEPR);
                        sw.stop();
                        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Get ProtType (" + sw.getElapsedTime() + "ms)");
                        sw.reset();

            // Perform registration
//			gp.workerRegistration(machID);
                        boolean workerWork = false;
                        boolean userResult = false;
                        boolean workerWorkAvailable = false;

                        int sleepErrorRetry = 1000;
                        int sleepErrorRetryMax = 10000;
                        int sleepErrorRetryStep = 1000;
                        int pollStep = 1000;
                        int sleepTime = 0;

                        while (sleepErrorRetry <= sleepErrorRetryMax) 
                        {
                            try
                            {
                                if (isRegistered == false) 
                                {
                               
                                    // Perform registration

                                    sw.start();
                                    gp.workerRegistration(machID);
                                    sw.stop();
                                    if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Performed one time Worker registration (" + sw.getElapsedTime() + "ms)");
                                    sw.reset();

                                    // Print resource properties
                                    printResourceProperties(gp);

                                    isRegistered = true;
                                }


                                //Thread.sleep(1000);
                            

                                /*
                                while (workerWorkAvailable == false) 
                                {
                                
                                    sw.start();
                                    // Perform status
                                    workerWorkAvailable = getWorkerWorkAvailable(gp);
                                    //gp.status(0);
                                    sw.stop();
                                    if (DEBUG) if (DIPERF == false) System.out.println("WORKER: getWorkerWorkAvailable (" + sw.getElapsedTime() + "ms)");
                                    sw.reset();

                                    // Print resource properties

                                    //printResourceProperties(gp);

                                    //this should be replaced by some notification mechanism
                                    Thread.sleep(pollStep);
                                } * /

                                //this call blocks until a notification is received
                                waitForNotification();
                                

                                // get work
                                sw.start();
                                //gp.status(0);
                                gp.workerWork(0);
                                sw.stop();
                                if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Get work (" + sw.getElapsedTime() + "ms)");
                                sw.reset();

                                //do work
                                sw.start();
                                doWork();
                                sw.stop();
                                if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Do work (" + sw.getElapsedTime() + "ms)");
                                sw.reset();

                                //send results back (potentially to the originating resource)
                                sw.start();
                                gp.workerResult(0);
                                sw.stop();
                                if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Sent results (" + sw.getElapsedTime() + "ms)");
                                sw.reset();


                                //this should be replaced by some notification mechanism
                                //Thread.sleep(1000);

                                sleepErrorRetry = sleepErrorRetryStep;
                                sleepTime = 0;

                            }
                            catch (Exception e) {
                                  if (DEBUG) if (DIPERF == false) System.out.println("WORKER: ERROR while(): " + e.getMessage());
                                  isRegistered = false; //try again
                                  sleepErrorRetry += sleepErrorRetryStep;
                                  sleepTime += sleepErrorRetry;
                                  Thread.sleep(sleepErrorRetry);
                          }

                        }

                        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Cannot find GenericPortal WebService... timed out after " + sleepTime/1000.0 + " seconds... exiting :(");
            nThread.stop();

        } catch (Exception e) {
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: ERROR main(): " + e.getMessage());
        }
        
    }   */


    public void parseArgs(String[] args)
    {
        if (args.length < 1)
        {
            usage(args);
            return;
        }

        try
        {

            int ctr;
            for (ctr = 0; ctr < args.length; ctr++)
            {
                if (args[ctr].equals("-epr") && ctr + 1 < args.length)
                {
                    ctr++;


                    fileEPR = new String(args[ctr]);
                    boolean exists = (new File(fileEPR)).exists();
                    if (exists)
                    {

                        // Get endpoint reference of WS-Resource from file
                        FileInputStream fis = new FileInputStream(fileEPR);


                        homeEPR = (EndpointReferenceType) ObjectDeserializer
                                  .deserialize(new InputSource(fis),
                                               EndpointReferenceType.class);


                        if (homeEPR == null)
                        {

                            System.out.println("parseArgs(): homeEPR == null, probably EPR was not correctly read from file :(");
                            System.exit(3);
                        }
                        else
                        {

                            if (DEBUG) if (DIPERF == false) System.out.println("parseArgs(): homeEPR is set correctly");
                        }

                        fis.close();
                    }
                    else
                    {
                        System.out.println("File '" + fileEPR + "' does not exist.");
                        System.exit(4);

                    }


                    //fileEPR = new String(args[ctr]);
                    //FileInputStream fis = new FileInputStream(fileEPR);

                    //homeEPR = (EndpointReferenceType) ObjectDeserializer.deserialize(new InputSource(fis),EndpointReferenceType.class);
                    //homeEPR = getEPR(fis);

                    //fis.close();
                }
                //must implement properly as a new resource would have to be created :(
                else if (args[ctr].equals("-serviceURI") && ctr + 1 < args.length)
                {
                    ctr++;
                    serviceURI = new String(args[ctr]);
                    //FileInputStream fis = new FileInputStream(fileEPR);
                    //homeEPR = (EndpointReferenceType) ObjectDeserializer.deserialize(new InputSource(fis),EndpointReferenceType.class);
                    homeEPR = getEPR(serviceURI);

                    //fis.close();
                }
                else if (args[ctr].equals("-lifetime") && ctr + 1 < args.length)
                {
                    ctr++;
                    Integer NR = new Integer(args[ctr]);
                    LIFETIME = NR.intValue();

                }

                else if (args[ctr].equals("-lifetime_stack") && ctr + 1 < args.length)
                {
                    ctr++;
                    Integer NR = new Integer(args[ctr]);
                    LIFETIME_STACK = NR.intValue();

                }

                else if (args[ctr].equals("-scratch_disk") && ctr + 1 < args.length)
                {
                    ctr++;
                    //Integer NR = new Integer(args[ctr]);
                    //LIFETIME = NR.intValue();
                    scratchDisk = args[ctr];

                }
                else if (args[ctr].equals("-max_not") && ctr + 1 < args.length)
                {
                    ctr++;
                    Integer NR = new Integer(args[ctr]);
                    MAX_NUM_NOTIFICATIONS = NR.intValue();

                }
                else if (args[ctr].equals("-max_threads") && ctr + 1 < args.length)
                {
                    ctr++;
                    Integer NR = new Integer(args[ctr]);
                    MAX_NUM_THREADS = NR.intValue();

                }
                else if (args[ctr].equals("-SO_TIMEOUT") && ctr + 1 < args.length)
                {
                    ctr++;
                    Integer NR = new Integer(args[ctr]);
                    SO_TIMEOUT = NR.intValue();

                }
                else if (args[ctr].equals("-idletime") && ctr + 1 < args.length)
                {
                    ctr++;
                    Integer NR = new Integer(args[ctr]);
                    IDLETIME = NR.intValue();

                }
                else if (args[ctr].equals("-desc") && ctr + 1 < args.length)
                {
                    ctr++;
                    DESC_FILE = args[ctr];
                    //System.out.println("Arguement: " + DESC_FILE + " == " + args[ctr]);

                }
                else if (args[ctr].equals("-job_size") && ctr + 1 < args.length)
                {
                    ctr++;
                    Integer NR = new Integer(args[ctr]);
                    JOB_SIZE = NR.intValue();

                }
                else if (args[ctr].equals("-debug"))//&& ctr + 1 < args.length)
                {
                    //ctr++;
                    DEBUG = true;
                }
                else if (args[ctr].equals("-interactive"))//&& ctr + 1 < args.length)
                {
                    //ctr++;
                    INTERACTIVE = true;
                }
                else if (args[ctr].equals("-diperf"))//&& ctr + 1 < args.length)
                {
                    //ctr++;
                    DIPERF = true;

                }
                else if (args[ctr].equals("-profiling"))//&& ctr + 1 < args.length)
                {
                    //ctr++;
                    PROFILING = true;

                }
                else if (args[ctr].equals("-data_trace") && ctr + 1 < args.length)
                {
                    ctr++;
                    DATA_TRACE = new String(args[ctr]);

                }
                //DATA_MOVEMENT_TRACE
                else if (args[ctr].equals("-resetCache"))//&& ctr + 1 < args.length)
                {
                    //ctr++;
                    isResetCache = true;

                }
                else if (args[ctr].equals("-standalone"))//&& ctr + 1 < args.length)
                {
                    //ctr++;
                    STANDALONE = true;

                }
                else if (args[ctr].equals("-caching"))//&& ctr + 1 < args.length)
                {
                    //default caching... LRU
                    CACHING = true;
                    try
                    {
                        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: setting caching strategy to default(LRU)!");
                        CacheStrategy cs = new CacheStrategy(); 
                        cacheGrid = new WorkerState(DATA_TRACE, PROFILING, DIPERF, DEBUG, cs, getMachNamePort(), isResetCache, CACHE_SIZE_MB);

                        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: initializing worker state");
                        if (!cacheGrid.WorkerStateAcquire() || cacheGrid.MAX_ENTRIES == 0)
                        {
                            System.out.println("ERROR: cacheGrid.WorkerStateAcquire() failed or there was not enough space on the local disk for the cache to operate... turning off caching!");
                            CACHING = false;
                            cacheGrid = null;
                            System.exit(5);

                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("ERROR: cacheGrid.WorkerStateAcquire() failed: " + e);
                        e.printStackTrace();

                    }

                }
                else if (args[ctr].equals("-cachingLRU"))//&& ctr + 1 < args.length)
                {
                    //LRU
                    CACHING = true;
                    try
                    {
                        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: setting caching strategy to LRU!");
                        CacheStrategy cs = new CacheStrategy();
                        cs.setLRU();
                        cacheGrid = new WorkerState(DATA_TRACE, PROFILING, DIPERF, DEBUG, cs, getMachNamePort(), isResetCache, CACHE_SIZE_MB);

                        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: initializing worker state");
                        if (!cacheGrid.WorkerStateAcquire() || cacheGrid.MAX_ENTRIES == 0)
                        {
                            System.out.println("ERROR: cacheGrid.WorkerStateAcquire() failed or there was not enough space on the local disk for the cache to operate... turning off caching!");
                            CACHING = false;
                            cacheGrid = null;

                            System.exit(6);
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("ERROR: cacheGrid.WorkerStateAcquire() failed: " + e);
                        e.printStackTrace();

                    }

                }
                else if (args[ctr].equals("-cachingFIFO"))//&& ctr + 1 < args.length)
                {
                    //LRU
                    CACHING = true;
                    try
                    {
                        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: setting caching strategy to FIFO!");
                        CacheStrategy cs = new CacheStrategy();
                        cs.setFIFO();
                        cacheGrid = new WorkerState(DATA_TRACE, PROFILING, DIPERF, DEBUG, cs, getMachNamePort(), isResetCache, CACHE_SIZE_MB);

                        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: initializing worker state");
                        if (!cacheGrid.WorkerStateAcquire() || cacheGrid.MAX_ENTRIES == 0)
                        {
                            System.out.println("ERROR: cacheGrid.WorkerStateAcquire() failed or there was not enough space on the local disk for the cache to operate... turning off caching!");
                            CACHING = false;
                            cacheGrid = null;

                            System.exit(7);
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("ERROR: cacheGrid.WorkerStateAcquire() failed: " + e);
                        e.printStackTrace();

                    }

                }
                else if (args[ctr].equals("-cachingRANDOM"))//&& ctr + 1 < args.length)
                {
                    //LRU
                    CACHING = true;
                    try
                    {
                        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: setting caching strategy to RANDOM!");
                        CacheStrategy cs = new CacheStrategy();
                        cs.setRANDOM();
                        cacheGrid = new WorkerState(DATA_TRACE, PROFILING, DIPERF, DEBUG, cs, getMachNamePort(), isResetCache, CACHE_SIZE_MB);

                        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: initializing worker state");
                        if (!cacheGrid.WorkerStateAcquire() || cacheGrid.MAX_ENTRIES == 0)
                        {
                            System.out.println("ERROR: cacheGrid.WorkerStateAcquire() failed or there was not enough space on the local disk for the cache to operate... turning off caching!");
                            CACHING = false;
                            cacheGrid = null;

                            System.exit(8);
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("ERROR: cacheGrid.WorkerStateAcquire() failed: " + e);
                        e.printStackTrace();

                    }

                }
                else if (args[ctr].equals("-cachingPOPULAR"))//&& ctr + 1 < args.length)
                {
                    //LRU
                    CACHING = true;
                    try
                    {
                        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: setting caching strategy to POPULAR!");
                        CacheStrategy cs = new CacheStrategy();
                        cs.setPOPULAR();
                        cacheGrid = new WorkerState(DATA_TRACE, PROFILING, DIPERF, DEBUG, cs, getMachNamePort(), isResetCache, CACHE_SIZE_MB);

                        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: initializing worker state");
                        if (!cacheGrid.WorkerStateAcquire() || cacheGrid.MAX_ENTRIES == 0)
                        {
                            System.out.println("ERROR: cacheGrid.WorkerStateAcquire() failed or there was not enough space on the local disk for the cache to operate... turning off caching!");
                            CACHING = false;
                            cacheGrid = null;
                            //System.out.println("Sleeping for 10 min before exiting...this is only for debugging, and should be removed!");
                            //Thread.sleep(600000);


                            System.exit(9);
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("ERROR: cacheGrid.WorkerStateAcquire() failed: " + e);
                        e.printStackTrace();
                        System.exit(10);

                    }

                }
                else if (args[ctr].equals("-CACHE_SIZE_MB") && ctr + 1 < args.length)
                {
                    ctr++;
                    CACHE_SIZE_MB = Integer.parseInt(args[ctr]);
                    //System.out.println("setting WorkerRun CACHE_SIZE_MB = " + CACHE_SIZE_MB);



                }

                /*
                else if (args[ctr].equals("-authenticate"))
                {
                    AUTHENTICATE = true;
    
                }
                else if (args[ctr].equals("-authorize"))
                {
                    AUTHORIZE = true;
    
                }
                else if (args[ctr].equals("-encrypt"))
                {
                    ENCRYPT = true;
    
                }
                else if (args[ctr].equals("-sign"))
                {
                    SIGN = true;
    
                }
                else if (args[ctr].equals("-TSL"))
                {
                    TSL = true;
    
                }
                else if (args[ctr].equals("-MSG"))
                {
                    MSG = true;
    
                }
                else if (args[ctr].equals("-CONV"))
                {
                    CONV = true;
    
                }
                */

                else if (args[ctr].equals("-CLIENT_DESC") && ctr + 1 < args.length)
                {

                    ctr++;
                    CLIENT_DESC = new String(args[ctr]);
                }

                else if (args[ctr].equals("-help"))//&& ctr + 1 < args.length)
                {
                    //ctr++;
                    if (DEBUG) if (DIPERF == false) System.out.println("Help Screen:");
                    usage(args);

                }
                else if (args[ctr].equals("-NUM_WORKERS") && ctr + 1 < args.length)
                {
                    ctr++;


                    try
                    {

                        NUM_WORKERS = Integer.parseInt(args[ctr]);
                    }
                    catch (Exception e)
                    {
                        System.out.println("Error in the number of workers arguement '" + args[ctr] + "': " + e);
                        usage(args);
                    }

                }

                else
                {

                    if (DIPERF == false) System.out.println("ERROR: invalid parameter - " + args[ctr]);
                    if (DIPERF == false) System.out.println("Current parameters values:");
                    if (DIPERF == false) System.out.println("-debug " + DEBUG);
                    if (DIPERF == false) System.out.println("-diperf " + DIPERF);
                    if (DIPERF == false) System.out.println("more... ");


                    usage(args);
                }
            }

            /*
            if (cacheGrid != null)
            {
                cacheGrid.MAX_CACHE_SIZE_MB = CACHE_SIZE_MB;
                System.out.println("setting cacheGrid.MAX_CACHE_SIZE_MB = " + CACHE_SIZE_MB);
            }
            else
            {
                System.out.println("cacheGrid is disbaled");

            } */



        }
        catch (Exception eeeee)
        {
            eeeee.printStackTrace();
            usage(args);
        }

    }

    public void initWorker()
    {
        //nothing yet
    }

    public void usage(String[] args)
    {
        if (DIPERF == false) System.out.println("Help Screen: ");
        if (DIPERF == false) System.out.println("-diperf <>");
        if (DIPERF == false) System.out.println("-help <>");
        System.exit(11);

    }

    public EndpointReferenceType getEPR(ResourceKey key) throws Exception
    {
        String instanceURI = homeEPR.getAddress().toString();
        return(EndpointReferenceType) AddressingUtils.createEndpointReference(instanceURI, key);
    }


    public EndpointReferenceType getEPRfromKey(String key) throws Exception
    {
        //String instanceURI = homeEPR.getAddress().toString();
        //return (EndpointReferenceType) AddressingUtils.createEndpointReference(instanceURI, key);
        return null;
    }

    public EndpointReferenceType getEPR(FileInputStream fis) throws Exception
    {
        return(EndpointReferenceType) ObjectDeserializer.deserialize(new InputSource(fis),EndpointReferenceType.class);
        ///return (EndpointReferenceType) ObjectDeserializer.deserialize(fis,EndpointReferenceType.class);
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


    public GPPortType getGPPortType() throws Exception
    {
        if (homeEPR == null)
        {
            throw new Exception("homeEPR == null, probably EPR was not correctly read from file :(");
        }
        else
        {

            GPServiceAddressingLocator instanceLocator = new GPServiceAddressingLocator();
            return instanceLocator.getGPPortTypePort(homeEPR);
        }

    }

    public void shutDown(String msg)
    {
        StopWatch sw = new StopWatch();

        sw.start();

        try

        {
            if (!DIPERF && !PROFILING) System.out.println("Performing de-registration for '" + machID + "'; reason: " + msg);

            //sw.start();
            WorkerDeRegistration wdr = new WorkerDeRegistration();
            wdr.setMachID(machID);
            wdr.setService(false);

            gp.workerDeRegistration(wdr);
            //sw.stop();
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Performed one time Worker de-registration");

            this.shuttingDown = new Boolean(true);


            this.workerNot.destroy();
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Destroyed notification end-point...");

                //sw.reset();
                //if (DEBUG) if (DIPERF == false) System.out.println("WORKER: waiting for all running threads to terminate");

                /*
                while (threadQ.size() > 0)
                {
                    try
                    {
                        Thread t = (Thread) threadQ.remove();
                        t.join();
                        // Finished
                    } catch (InterruptedException e)
                    {
                        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: waiting for threads to die was interrupted: " + e);
                        e.printStackTrace();
    
    
                    }
                }
                */

            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: all threads have terminated");

            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: realeasing worker state");
            if (CACHING) cacheGrid.WorkerStateRelease();

            //System.exit(-1);
            //this.stop();

            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: counting dead workers...");

            setDeadWorkers();
            if (getDeadWorkers() >= NUM_WORKERS)
            {

                synchronized (this.shuttingDown)
                {

                    try
                    {

                        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: notifying all workers to shutdown...");
                        this.shuttingDown.notifyAll();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }


            }


            //BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
            //String str = "";
            //while (str != null) {
            //if (DEBUG) System.out.println("WORKER: Press any key and hit enter to terminate this worker!");
            //out.write(-1);





        }
        catch (Exception e)
        {

            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: ERROR in shutting down " + e);
            if (DEBUG) if (DIPERF == false) e.printStackTrace();
        }

        if (!msg.startsWith("-99999"))
        {


            sw.stop();
            if (DIPERF) System.out.println("WORKER:shutdown(): " + sw.getElapsedTime() + " ms");
            sw.reset();


            if (DEBUG) if (DIPERF == false) System.out.println("ShutDown(): System.exit(0)");
            System.exit(0);
        }

        sw.stop();
        if (DIPERF) System.out.println("WORKER:shutdown(): " + sw.getElapsedTime() + " ms");
        sw.reset();




    }


    public synchronized void activeNotificationsInc()
    {
        activeNotifications++;
        idleTime.reset();
    }

    public synchronized void activeNotificationsDec()
    {
        activeNotifications--;
        if (activeNotifications == 0)
        {
            idleTime.reset();
            idleTime.start();
        }
    }

    public synchronized int getActiveNotifications()
    {
        return activeNotifications;
    }


    public synchronized boolean canAcceptMoreNotifications()
    {
        /*
        if (getActiveNotifications() < MAX_NUM_NOTIFICATIONS)
        {
            return true;
        } else
            return false;
            */
        return true;
    }

    public void main_run_standalone(String[] args) throws Exception
    {
        //System.out.println("WORKER_STANDALONE: v0.1.1 - " + machID);
        //System.out.println("Standalone function not implemented yet... exiting!");




        if (DEBUG) if (DIPERF == false) System.out.println("WORKER_STANDALONE: v0.1.1 - " + machID);


        StopWatch sw = new StopWatch();
        lt.start();


//		sw.start();
//		parseArgs(args);
//		sw.stop();
//		if(DEBUG) if(DIPERF == false) System.out.println("WORKER: Parse Arguements and Setup Corresponding State (" + sw.getElapsedTime() + "ms)");
//		sw.reset();

        fileRead();




        int sleepErrorRetry = 1000;
        int sleepErrorRetryMax = 10000;
        int sleepErrorRetryStep = 1000;
        int pollStep = 1000;
        int sleepTime = 0;

        if (INTERACTIVE)
        {


            Thread keyListen = new KeyboardListener(this);
            if (DEBUG) System.out.print("WORKER: keyListen Thread starting...");
            keyListen.start();
            if (DEBUG) System.out.print("WORKER: keyListen Thread started!");
        }

        Thread lifeListen = null;
        if (LIFETIME > 0)
        {

            lifeListen = new LifetimeListener(this);
            if (DEBUG) System.out.print("WORKER: lifeListen Thread starting...");

            lifeListen.start();
            if (DEBUG) System.out.print("WORKER: lifeListen Thread started!");
        }
        else if (LIFETIME == 0)
        {
            if (DEBUG) System.out.print("WORKER: lifeListen Thread not started... will live forever until terminated explicitly!!!");
        }
        else
        {
            if (DEBUG) System.out.print("WORKER: invalid lifetime value of " + LIFETIME + "... ignoring.");
        }

        Thread idleListen = null;
        if (IDLETIME > 0)
        {

            idleListen = new IdletimeListener(this);
            if (DEBUG) System.out.print("WORKER: idleListen Thread starting...");

            idleListen.start();
            if (DEBUG) System.out.print("WORKER: idleListen Thread started!");
        }
        else if (IDLETIME == 0)
        {
            if (DEBUG) System.out.print("WORKER: idleListen Thread not started... will live until the LIFETIME is reached or it is terminated explicitly!");
        }
        else
        {
            if (DEBUG) System.out.print("WORKER: invalid idleListen value of " + IDLETIME + "... ignoring.");
        }



        int num_stackings = 0;

        while (lt.getElapsedTime() < LIFETIME || num_stackings < LIFETIME_STACK || LIFETIME == 0)
        {

            //sw.start();
            //if (DIPERF == false) System.out.println("Waiting for work...");

            //EndpointReferenceType notificationEPR = waitForNotification();
            //sw.stop();
            //if(DEBUG) if (DIPERF == false) System.out.println("WORKER: waitForNotification (" + sw.getElapsedTime() + "ms)");
            //sw.reset();


            Thread notificationThread = null;
            if (canAcceptMoreNotifications())
            {
                if (DEBUG) System.out.println("Accepted a new notification!");
                if (DEBUG) System.out.println("Number of concurent active notifications: " + getActiveNotifications() + ", maximum allowed notifications: " + MAX_NUM_NOTIFICATIONS);
                notificationThread = new NotificationThread(this, DESC_FILE, JOB_SIZE);
                notificationThread.start();

                //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%% inserting thread in queue... length = " + threadQ.size());
                threadQ.insert(notificationThread);
                //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%% inserted thread in queue... length = " + threadQ.size());
                notificationThread.join();

                threadQ.removeTask(notificationThread);  //should be removeObject

                //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%% removed thread from queue... length = " + threadQ.size());

                num_stackings += JOB_SIZE;

            }
            else
            {
                System.out.println("********************************* Busy with too many concurent notifications: " + getActiveNotifications());
                if (DEBUG) System.out.println("Busy with too many concurent notifications: " + getActiveNotifications());
                if (DEBUG) System.out.println("Notification not served...");


            }


        }

        if (DIPERF == false) System.out.println("Worker lifetime of " + LIFETIME/1000.0 + " seconds ended after " + lt.getElapsedTime()/1000.0 + " seconds... exiting!");
        if (DIPERF == false) System.out.println("Shutting down worker... 1");
        shutDown("2: Worker lifetime of " + LIFETIME/1000.0 + " seconds ended after " + lt.getElapsedTime()/1000.0 + " seconds...");



    }

    public void main_run(String[] args) throws Exception
    {
        if (DEBUG) System.out.println("WORKER: v0.1.1 - " + machID);


        StopWatch sw = new StopWatch();
        lt.start();

        //sw.start();
        //parseArgs(args);
        //sw.stop();
        //if(DEBUG) System.out.println("WORKER: Parse Arguements and Setup Corresponding State (" + sw.getElapsedTime() + "ms)");
        //sw.reset();


        sw.start();
        machID = getMachNamePort(); 
        sw.stop();
        if (DEBUG)
        {
            System.out.println("WORKER: Get Machine Name (" + sw.getElapsedTime() + "ms)");
        }

        if (DIPERF) System.out.println("WORKER:getMachNamePort(): " + sw.getElapsedTime() + " ms");

        if (DIPERF == false)
        {
            System.out.println("Worker " + machID + " started succesful!");
        }
        sw.reset();

        if (homeEPR == null)
        {
            throw new Exception("main_run(): homeEPR == null, something is wrong :(");
        }
        else
        {

            if (DEBUG) System.out.println("main_run(): homeEPR is OK");
        }

        sw.start();
        // Get PortType
        gp = getGPPortType();//= instanceLocator


        if (CLIENT_DESC != null && (new File(CLIENT_DESC)).exists())
        {
            if (DEBUG) System.out.println("Setting appropriate security from file '" + CLIENT_DESC + "'!");
            ((Stub)gp)._setProperty(Constants.CLIENT_DESCRIPTOR_FILE, CLIENT_DESC);


        }

        /*
        if (AUTHORIZE)
        {
            //authorization
            System.out.println("Enable authorization!");
            ((Stub)gp)._setProperty(Constants.AUTHORIZATION,SelfAuthorization.getInstance());
        }
        else
        {
            //no authorization
            System.out.println("Disable authorization!");
            ((Stub)gp)._setProperty(Constants.AUTHORIZATION,NoAuthorization.getInstance());
        }

        if (AUTHENTICATE)
        {
            //authentication
            System.out.println("Enable authentication!");
            ((Stub)gp)._setProperty(Constants.GSI_ANONYMOUS, Boolean.FALSE );
        }
        else
        {

        //no authentication
            System.out.println("Disable authentication!");
            ((Stub)gp)._setProperty(Constants.GSI_ANONYMOUS, Boolean.TRUE );
        }

        if (SIGN)
        {
            if (CONV)
            {
                //GSI Transport Conversation + Signature
                System.out.println("Use GSI Transport Conversation with Signature!");
                ((Stub)gp)._setProperty(Constants.GSI_SEC_CONV,Constants.SIGNATURE);
            }
            else if (MSG)
            {
                //GSI Transport Message + Signature
                System.out.println("Use GSI Transport Message with Signature!");
                ((Stub)gp)._setProperty(Constants.GSI_SEC_MSG,Constants.SIGNATURE);
            }
            else if (TSL)
            {
                //GSI (TSL) Transport Message + Signature
                System.out.println("Use GSI (TSL) Transport Message with Signature!");
                ((Stub)gp)._setProperty(GSIConstants.GSI_TRANSPORT, Constants.SIGNATURE);
            }
        }

        else if (ENCRYPT)
        {
            if (CONV)
            {
                //GSI Transport Conversation + Encryption
                System.out.println("Use GSI Transport Conversation with Encryption!");
                ((Stub)gp)._setProperty(Constants.GSI_SEC_CONV,Constants.ENCRYPTION);
            }
            else if (MSG)
            {
                //GSI Transport Message + Encryption
                System.out.println("Use GSI Transport Message with Encryption!");
                ((Stub)gp)._setProperty(Constants.GSI_SEC_MSG,Constants.ENCRYPTION);
            }
            else if (TSL)
            {
                //GSI (TSL) Transport Message + Encryption
                System.out.println("Use GSI (TSL) Transport Message with Encryption!");
                ((Stub)gp)._setProperty(GSIConstants.GSI_TRANSPORT, Constants.ENCRYPTION);
            }
        }

        else
        {

            if (CONV)
            {
                //GSI Transport Conversation + Encryption
                System.out.println("Use GSI Transport Conversation!");
                ((Stub)gp)._setProperty(Constants.GSI_SEC_CONV,Constants.NONE);
            } else if (MSG)
            {
                //GSI Transport Message + Encryption
                System.out.println("Use GSI Transport Message!");
                ((Stub)gp)._setProperty(Constants.GSI_SEC_MSG,Constants.NONE);
            } else if (TSL)
            {
                //GSI (TSL) Transport Message + Encryption
                System.out.println("Use GSI (TSL) Transport Message!");
                ((Stub)gp)._setProperty(GSIConstants.GSI_TRANSPORT, Constants.NONE);
            }
        }
        */

        //.getGPPortTypePort(instanceEPR);
        sw.stop();
        if (DEBUG) System.out.println("WORKER: Get PortType (" + sw.getElapsedTime() + "ms)");

        if (DIPERF) System.out.println("WORKER:getGPPortType(): " + sw.getElapsedTime() + " ms");
        sw.reset();

        WorkerRegistration reg = new WorkerRegistration();
        reg.setMachID(machID);
        reg.setWallTime(this.LIFETIME);

        sw.start();
        //gp.workerRegistration(new WorkerRegistration(machID));

        boolean registerSuccess = false;

        int regErrorCount = 0;
        while (registerSuccess == false)
        {

            try
            {

                gp.workerRegistration(reg);
                registerSuccess = true;
            }
            catch (Exception regError)
            {
                regError.printStackTrace();
                if (regErrorCount > 10)
                {
                    throw new Exception("Failed to register worker after " + regErrorCount + " tries: " + regError);
                }
                regErrorCount++;
                try
                {
                    Thread.sleep(1000);
                }
                catch (Exception sleepError)
                {
                    sleepError.printStackTrace();

                }

            }
        }
        sw.stop();
        if (DEBUG) System.out.println("WORKER: Performed one time Worker registration (" + sw.getElapsedTime() + "ms)");

        if (DIPERF) System.out.println("WORKER:workerRegistration(): " + sw.getElapsedTime() + " ms");
        sw.reset();

        Thread killListen = new KillListener(this);
        if (DEBUG) System.out.println("WORKER: killListen Thread starting...");

        killListen.start();
        if (DEBUG) System.out.println("WORKER: killListen Thread started!");

        int sleepErrorRetry = 1000;
        int sleepErrorRetryMax = 10000;
        int sleepErrorRetryStep = 1000;
        int pollStep = 1000;
        int sleepTime = 0;


        sw.start();
        if (INTERACTIVE)
        {


            Thread keyListen = new KeyboardListener(this);
            if (DEBUG) System.out.println("WORKER: keyListen Thread starting...");
            keyListen.start();
            if (DEBUG) System.out.println("WORKER: keyListen Thread started!");
        }



        Thread lifeListen = null;
        if (LIFETIME > 0)
        {


            lifeListen = new LifetimeListener(this);
            if (DEBUG) System.out.println("WORKER: lifeListen Thread starting...");
            else
                System.out.println("WORKER: lifeListen Thread starting and set to " + LIFETIME*1.0/1000.0 + " seconds");

            lifeListen.start();
            if (DEBUG) System.out.println("WORKER: lifeListen Thread started!");
        }
        else if (LIFETIME == 0)
        {
            if (DEBUG) System.out.println("WORKER: lifeListen Thread not started... will live forever until terminated explicitly!!!");
            if (!DIPERF && !PROFILING) System.out.println("WORKER: lifeListen Thread not started... will live forever until terminated explicitly!!!");
        }
        else
        {
            if (DEBUG) System.out.println("WORKER: invalid lifetime value of " + LIFETIME + "... ignoring.");
        }

        Thread idleListen = null;
        if (IDLETIME > 0)
        {

            idleListen = new IdletimeListener(this);
            if (DEBUG) System.out.println("WORKER: idleListen Thread starting...");

            idleListen.start();
            if (DEBUG) System.out.println("WORKER: idleListen Thread started!");
        }
        else if (IDLETIME == 0)
        {
            if (DEBUG) System.out.println("WORKER: idleListen Thread not started... will live until the LIFETIME is reached or it is terminated explicitly!");
        }
        else
        {
            if (DEBUG) System.out.println("WORKER: invalid idleListen value of " + IDLETIME + "... ignoring.");
        }


        int num_stackings = 0;

        while (lt.getElapsedTime() < LIFETIME || LIFETIME == 0)
        {
            //this call blocks until a notification is received

            sw.start();
            if (DEBUG) System.out.println("Waiting for work...");

            EndpointReferenceType notificationEPR = waitForNotification();
            if (notificationEPR == null)
                break;
            sw.stop();
            if (DEBUG) System.out.println("WORKER: waitForNotification (" + sw.getElapsedTime() + "ms)");
            if (DIPERF) System.out.println("WORKER:waitForNotification(): " + sw.getElapsedTime() + " ms");
            sw.reset();

            sw.start();
            Thread notificationThread = null;
            if (canAcceptMoreNotifications())
            {
                if (DEBUG) System.out.println("Accepted a new notification!");
                if (DEBUG) System.out.println("Number of concurent active notifications: " + getActiveNotifications() + ", maximum allowed notifications: " + MAX_NUM_NOTIFICATIONS);
                notificationThread = new NotificationThread(this, notificationEPR);
                notificationThread.start();

                //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%% inserting thread in queue... length = " + threadQ.size());
                threadQ.insert(notificationThread);
                //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%% inserted thread in queue... length = " + threadQ.size());
                notificationThread.join();

                threadQ.removeTask(notificationThread);  //should be removeObject

                //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%% removed thread from queue... length = " + threadQ.size());
                sw.stop();
                if (DIPERF) System.out.println("WORKER:NotificationThread():canAcceptMoreNotifications(): " + sw.getElapsedTime() + " ms");
                sw.reset();

            }
            else
            {
                System.out.println("********************************* Busy with too many concurent notifications: " + getActiveNotifications());
                if (DEBUG) System.out.println("Busy with too many concurent notifications: " + getActiveNotifications());
                if (DEBUG) System.out.println("Notification not served...");
                sw.reset();


            }


        }

        System.out.println("Worker lifetime of " + LIFETIME/1000.0 + " seconds ended after " + lt.getElapsedTime()/1000.0 + " seconds... exiting!");

        if (this.shuttingDown.booleanValue() == false)//lt.getElapsedTime() >= LIFETIME)
        {
            System.out.println("Shutting down worker... 2");

            sw.start();
            shutDown("3: Worker lifetime of " + LIFETIME/1000.0 + " seconds ended after " + lt.getElapsedTime()/1000.0 + " seconds...");
            sw.stop();
            if (DIPERF) System.out.println("WORKER:shutDown(): " + sw.getElapsedTime() + " ms");
            sw.reset();
        }
        else
            System.out.println("ShutDown sequence has already been done!");



        //wait until all notification threads are done...
    }


    public void run()
    {
        try
        {
            StopWatch all = new StopWatch();
            StopWatch sw = new StopWatch();
            all.start();

            //WorkerRunThread worker = new WorkerRunThread();
            //System.out.println("DIPERF=" + worker.DIPERF);
            //System.out.println("WORKER:parseArgs()...");

            //sw.start();                                                                                     
            //worker.parseArgs(args);
            //sw.stop();
            //if (worker.DIPERF) System.out.println("WORKER:parseArgs(): " + sw.getElapsedTime() + " ms");
            //sw.reset();


            workerNot = new Notification(SO_TIMEOUT, DEBUG);
            if (DEBUG) if (DIPERF == false) System.out.println("Notification initialized on port: " + workerNot.recvPort);


                //System.out.println("DIPERF=" + worker.DIPERF);
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Parse Arguements and Setup Corresponding State...");


            if (STANDALONE == true)
            {
                main_run_standalone(args);
            }
            else
                main_run(args);

            all.stop();

            //if(worker.DIPERF == true) System.out.println(worker.MAX_NUM_THREADS + " " + worker.DESC_FILE + " " + worker.getElapsedTime()/1000.0 + " " + worker.numTasksCompleted*1.0 / (worker.getElapsedTime()/1000.0)+ " " + worker.NUM_JOBS + " " + worker.JOB_SIZE + " " + worker.ROI_Height + " " + worker.ROI_Height + " " + worker.numTasksCompleted + " " + worker.numTasksFailed);
            if (DIPERF == true) System.out.println("WORKER:WorkTime: " + all.getElapsedTime() + " ms");
            System.out.println("WORKER:WorkTime: " + all.getElapsedTime() + " ms");
        }
        catch (Exception e)
        {
            System.out.println("WORKER: Fatal ERROR: " + e + "... exiting :(");
            e.printStackTrace();
            System.exit(12);
        }

    }

}

class FileTransferState
{
    private boolean isTransfering = false;
    private boolean isDone = false;
    private boolean isError = false;
    private CacheResp cacheResp[] = null;

    public FileTransferState()
    {

    }

    public synchronized void reset()
    {
        isTransfering = false;
        isDone = false;
        isError = false;
        cacheResp = null;

    }



    public synchronized void setTransfering()
    {
        isTransfering = true;
        isDone = false;
        isError = false;
        cacheResp = null;

    }

    public synchronized void setDone(CacheResp cacheResp[])
    {
        isTransfering = false;
        isDone = true;
        isError = false;
        this.cacheResp = cacheResp;

    }

    public synchronized void setError(CacheResp cacheResp[])
    {
        isTransfering = false;
        isDone = false;
        isError = true;
        this.cacheResp = cacheResp;

    }

    public synchronized boolean isTransfering()
    {
        return isTransfering;
    }

    public synchronized boolean isDone()
    {
        return isDone;
    }

    public synchronized boolean isError()
    {
        return isError;

    }


    public synchronized CacheResp[] getCacheResp()
    {
        return cacheResp;

    }


}


class NotificationThread extends Thread
{


    static {
        Util.registerTransport();
    }

    WorkerRunThread home;
    public Thread[]                              readThreads;
    public boolean DEBUG;
    public boolean DIPERF;
    public boolean PROFILING;
    public String DATA_TRACE;


    //public Queue tasks;
    //public Queue images;

    //public boolean busy;
    public EndpointReferenceType epr;
    public GPPortType gp;

    //WorkQueue taskXQ;
    //WorkQueue resultsQ;
    WorkQueue cacheEvicted;
    //public int numTasksFailed;
    public int numTasksCompleted;
    //public int height;
    //public int width;

//    public FIT f;
    public int jobID;

    public boolean GenericPortal;

    public String desc_file;
    public int job_size;
    //public int ROI_height;
    //public int ROI_width;

    //public boolean CACHING;

    //AstroPortal specific list used to maintain state across different tasks...
    List cutoutList = Collections.synchronizedList(new LinkedList());
    int STACK_HEIGHT = 0;
    int STACK_WIDTH = 0;
    String STACK_FINAL_NAME = null;

    Map FileTransfers = Collections.synchronizedMap(new HashMap());


    public NotificationThread(WorkerRunThread h, String fn, int s) throws Exception 
    {
        super("Worker Notification Thread");
        this.GenericPortal = false;
        this.desc_file = fn;
        this.job_size = s;
        this.home = h;

        //this.ROI_height = home.ROI_Height;
        //this.ROI_width = home.ROI_Width;

        this.DEBUG = this.home.DEBUG;
        //this.CACHING = this.home.CACHING;
        this.DIPERF = this.home.DIPERF;
        this.PROFILING = this.home.PROFILING;
        this.DATA_TRACE = this.home.DATA_TRACE;

        this.home.activeNotificationsInc();

        //this.epr = e;

        //this.gp = this.home.getGPPortType(home.notificationEPR);
        //this.gp = this.home.getGPPortType(this.epr);
        this.jobID = this.home.getJobID();



        //this.readThreads = new Thread[home.MAX_NUM_THREADS];

        //for(int i=0;i<home.MAX_NUM_THREADS;i++)
        //{
        //    this.readThreads[i] = new ExecThread(this);

        //}

        //this.tasks = new Queue();

        //numTasksToDo = 0;
        //numTasksDone = 0;
        //numTasksFailed = 0;
        numTasksCompleted = 0;

        //f = new FIT();

        //resultsQ = new WorkQueue();
        cacheEvicted = new WorkQueue();


    }

    public NotificationThread(WorkerRunThread h, EndpointReferenceType e) throws Exception 
    {
        super("Worker Notification Thread");

        this.GenericPortal = true;
        this.home = h;
        this.DEBUG = this.home.DEBUG;

        this.DATA_TRACE = this.home.DATA_TRACE;

        this.home.activeNotificationsInc();

        this.epr = e;

        //this.gp = this.home.getGPPortType(home.notificationEPR);
        this.gp = this.home.getGPPortType(this.epr);


        if (h.CLIENT_DESC != null && (new File(h.CLIENT_DESC)).exists())
        {
            if (DEBUG) System.out.println("Setting appropriate security from file '" + h.CLIENT_DESC + "'!");
            ((Stub)gp)._setProperty(Constants.CLIENT_DESCRIPTOR_FILE, h.CLIENT_DESC);


        }

        /*

        if (h.AUTHORIZE)
        {
            //authorization
            System.out.println("Enable authorization!");
            ((Stub)gp)._setProperty(Constants.AUTHORIZATION,SelfAuthorization.getInstance());
        }
        else
        {
            //no authorization
            System.out.println("Disable authorization!");
            ((Stub)gp)._setProperty(Constants.AUTHORIZATION,NoAuthorization.getInstance());
        }

        if (h.AUTHENTICATE)
        {
            //authentication
            System.out.println("Enable authentication!");
            ((Stub)gp)._setProperty(Constants.GSI_ANONYMOUS, Boolean.FALSE );
        }
        else
        {

        //no authentication
            System.out.println("Disable authentication!");
            ((Stub)gp)._setProperty(Constants.GSI_ANONYMOUS, Boolean.TRUE );
        }

        if (h.SIGN)
        {
            if (h.CONV)
            {
                //GSI Transport Conversation + Signature
                System.out.println("Use GSI Transport Conversation with Signature!");
                ((Stub)gp)._setProperty(Constants.GSI_SEC_CONV,Constants.SIGNATURE);
            }
            else if (h.MSG)
            {
                //GSI Transport Message + Signature
                System.out.println("Use GSI Transport Message with Signature!");
                ((Stub)gp)._setProperty(Constants.GSI_SEC_MSG,Constants.SIGNATURE);
            }
            else if (h.TSL)
            {
                //GSI (TSL) Transport Message + Signature
                System.out.println("Use GSI (TSL) Transport Message with Signature!");
                ((Stub)gp)._setProperty(GSIConstants.GSI_TRANSPORT, Constants.SIGNATURE);
            }
        }

        else if (h.ENCRYPT)
        {
            if (h.CONV)
            {
                //GSI Transport Conversation + Encryption
                System.out.println("Use GSI Transport Conversation with Encryption!");
                ((Stub)gp)._setProperty(Constants.GSI_SEC_CONV,Constants.ENCRYPTION);
            }
            else if (h.MSG)
            {
                //GSI Transport Message + Encryption
                System.out.println("Use GSI Transport Message with Encryption!");
                ((Stub)gp)._setProperty(Constants.GSI_SEC_MSG,Constants.ENCRYPTION);
            }
            else if (h.TSL)
            {
                //GSI (TSL) Transport Message + Encryption
                System.out.println("Use GSI (TSL) Transport Message with Encryption!");
                ((Stub)gp)._setProperty(GSIConstants.GSI_TRANSPORT, Constants.ENCRYPTION);
            }
        }

        else
        {

            if (h.CONV)
            {
                //GSI Transport Conversation + Encryption
                System.out.println("Use GSI Transport Conversation!");
                ((Stub)gp)._setProperty(Constants.GSI_SEC_CONV,Constants.NONE);
            } else if (h.MSG)
            {
                //GSI Transport Message + Encryption
                System.out.println("Use GSI Transport Message!");
                ((Stub)gp)._setProperty(Constants.GSI_SEC_MSG,Constants.NONE);
            } else if (h.TSL)
            {
                //GSI (TSL) Transport Message + Encryption
                System.out.println("Use GSI (TSL) Transport Message!");
                ((Stub)gp)._setProperty(GSIConstants.GSI_TRANSPORT, Constants.NONE);
            }
        }

        */



        this.jobID = this.home.getJobID();
        this.DIPERF = this.home.DIPERF;
        this.PROFILING = this.home.PROFILING;



        //this.readThreads = new Thread[home.MAX_NUM_THREADS];

        //for(int i=0;i<home.MAX_NUM_THREADS;i++)
        //{
        //    this.readThreads[i] = new ExecThread(this);

        //}

        //this.tasks = new Queue();

        //numTasksToDo = 0;
        //numTasksDone = 0;
        //numTasksFailed = 0;
        numTasksCompleted = 0;

        //f = new FIT();

        //resultsQ = new WorkQueue();
        cacheEvicted = new WorkQueue();


    }

    public synchronized void addCacheEvicted(String key)
    {
        cacheEvicted.insert(key);
    }

    /*
    public synchronized void addResultsQ(Task task)
    {
        resultsQ.insert(task);

    } */

    /*
    public synchronized void numTasksFailedInc()
    {
    numTasksFailed++;

    } */

    public synchronized void numTasksCompletedInc()
    {
        numTasksCompleted++;

    }

    public int max(int a, int b)
    {
        if (a>b)
        {
            return a;
        }
        else
            return b;
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

    //this is not properly implemented
    public long getID()
    {
        //return ThreadLocal.getId();
        long id = 0;
        return id;
    }


    public void run() 
    {
        try
        {
            //if(home.STANDALONE == true)
            //{

            //    doRunMain();
            //}
            //else
            doRun();
        }
        catch (Exception e)
        {
            if (DEBUG) if (DIPERF == false) System.out.println("There was an error in Notification Thread doRun(): " + e);

            if (DEBUG) if (DIPERF == false) System.out.println("Notification thread exiting...");
            e.printStackTrace();

            //throw new Exception(e);
        }

    }

    public WorkerWorkResponse workerWork(WorkerWork ww)
    {
        System.out.println("Standalone WorkerRunThread is not supported yet :(... exiting!");
        System.exit(13);
        /*
        //String t = null;
        String []t = new String[job_size];
        for (int i=0;i<job_size;i++)
        {


            t[i] = home.readLine();
        }


        WorkerWorkResponse RP = new WorkerWorkResponse();
        Task[] tasks;
        try
        {
            //int i;
            int numWorkers = 1;
            int workSize = job_size;

            tasks = new Task[workSize];
            //i = 0;
            for (int i=0;i<job_size;i++)
            {

                Tuple tuple = new Tuple();
                tuple.setRa(0.0);
                tuple.setDec(0.0);
                tuple.setBand("r");
                Task task = new Task();
                task.setTuple(tuple);
                task.setFileName(t[i]);
                task.setX_coord(0);
                task.setY_coord(0);

                tasks[i] = task;
                //i++;


            }

            RP.setTasks(tasks);
            RP.setValid(true);
            RP.setHeight(ROI_height);
            RP.setWidth(ROI_width);

        } catch (Exception e)
        {
            if (DIPERF == false) System.out.println("Error in retrieving work: " + e);
            if (DIPERF == false) System.out.println("Exiting...");
            home.shutDown();
        }

        return RP;  */
        return null;

    }

    //this function is just pseudo code.. fix
    public void doRun() throws Exception //maybe we don't want to throw an exception here since we might want to recover from some of the errors...
    {
        boolean executorShuttingDown = false;


        StopWatch jobTimer = new StopWatch();
        StopWatch taskTimer = new StopWatch();
        StopWatch sw = new StopWatch();
        jobTimer.start();



        //WorkerWorkResponse wt = gp.workerWork(true);
        //int numTasksFailed = 0;
        int numCacheEvicted = 0;

        if (DEBUG) if (DIPERF == false) System.out.println("doRun()...");


        sw.start();
        WorkerWorkResponse wt = null;
        if (home.STANDALONE == true)
        {
            WorkerWork wwObject = new WorkerWork();
            wwObject.setValid(true);
            wwObject.setMachID(home.machID);
            //wt = workerWork(new WorkerWork(true));
            wt = workerWork(wwObject);
            if (DEBUG) if (DIPERF == false) System.out.println("work retrieved from local source...");
        }
        else
        {
            if (DEBUG) if (DIPERF == false) System.out.println("work retrieved from GPWS...");

            WorkerWork wwObject = new WorkerWork();
            wwObject.setValid(true);
            wwObject.setMachID(home.machID);
            //wt = gp.workerWork(new WorkerWork(true));

            //System.out.println("gp");

            boolean WSsuccess = false;
            long sleepTime = 500;
            while (WSsuccess == false)
            {


                try
                {

                    wt = gp.workerWork(wwObject);
                    WSsuccess = true;//wt.isValid();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    try
                    {
                        Thread.sleep(sleepTime);
                        sleepTime = sleepTime*2;
                        if (sleepTime > 600000)
                        {
                            System.out.println("Giving up on contacting the service... shuting down!");
                            this.home.shutDown("4: Giving up on tring to retrieve work from the service...");
                        }
                    }
                    catch (Exception eeeee)
                    {
                        eeeee.printStackTrace();

                    }

                }
            }


        }
        sw.stop();
        if (DEBUG) System.out.println("Received work: " + sw.getElapsedTime() + " ms");
        sw.reset();



        boolean moreWork = true;
        boolean piggyBacking = false;



        while (moreWork)
        {

            //WorkTasks wt = gp.workerWork(0);
            boolean isWork = false;
            isWork = wt.isValid();


            sw.start();
            if (isWork)
            {
                if (DEBUG) if (DIPERF == false) System.out.println("work is valid...");
                int numTasksToDo = 0;

                Task[] tasks = wt.getTasks();

                if (DEBUG) if (DIPERF == false) System.out.println("tasks retrieved...");
                numTasksToDo = tasks.length;

                if (!DIPERF && !PROFILING) System.out.print("\nJob #" + jobID + ": Total tasks to do: " + numTasksToDo + " ; number of active notifications: " + home.getActiveNotifications() + " ... ");
                if (DEBUG)
                {
                    System.out.println("");
                }

                //height = wt.getHeight();
                //width = wt.getWidth();

                //if (DEBUG) if (DIPERF == false) System.out.println("height and width retrieved...");

                // Create the task queue
                WorkQueue taskQ = new WorkQueue();
                // Create the stack queue
                WorkQueue resultsQ = new WorkQueue();




                if (DEBUG) if (DIPERF == false) System.out.println("taskQ and stack initialized...");




                    // Create a set of worker threads
                final int numReaders = min(home.MAX_NUM_THREADS, numTasksToDo);
                ExecThread[] readThreads = new ExecThread[numReaders];

                if (DEBUG) if (DIPERF == false) System.out.println(numReaders + " readThreads initialized...");
                if (DEBUG) System.out.println("Setup state: " + sw.getElapsedTime() + " ms");

                taskTimer.reset();
                taskTimer.start();
                for (int i=0; i<readThreads.length; i++)
                {
                    readThreads[i] = new ExecThread(this, taskQ, resultsQ);
                    readThreads[i].start();
                }
                if (DEBUG) if (DIPERF == false) System.out.println("readThreads started...");

                    //Task array = new Task[10]; //not the right size
                    //get EPR from home thread
                    //GPPortType gp = instanceLocator.getGPPortTypePort(epr);
                    //access GPWS at specified EPR for work

                if (DEBUG) if (DIPERF == false) System.out.println("inserting tasks in taskQ...");


                for (int i=0; i<numTasksToDo; i++)
                {
                    taskQ.insert(tasks[i]);
                }
                if (DEBUG) if (DIPERF == false) System.out.println("tasks inserted in taskQ...");


                    // Add special end-of-stream markers to terminate the workers
                for (int i=0; i<readThreads.length; i++)
                {
                    taskQ.insert(ExecThread.NO_MORE_WORK);
                }


                sw.stop();
                if (DIPERF) System.out.println("WORKER:NotificationThread:initState(): " + sw.getElapsedTime() + " ms");
                sw.reset();

                if (DEBUG) if (DIPERF == false) System.out.println("waiting for readThreads to finish...");


                for (int i=0;i<readThreads.length;i++)
                {
                    // Wait indefinitely for the thread to finish
                    readThreads[i].join();

                    /*
                    try
                    {

                        while (readThreads[i].isAlive())
                        {
                            System.out.println("ExecThread " + readThreads[i].getID() + " is still alive...");
                            try
                            {
                            
                            readThreads[i].join(1000);
                            }
                            catch (Exception ee)
                            {
                                ee.printStackTrace();
                            }

                        }
                        
                        if (DEBUG) if (DIPERF == false) System.out.println("Notification Thread #"  + getID() + ": Read Thread #" + readThreads[i].getID());// + ": State = " + readThreads[i].State);
                            // Finished
                    }
                    catch (Exception e)
                    {
                        if (DEBUG) if (DIPERF == false) System.out.println("Notification Thread #"  + getID() + ": Read Thread #" + readThreads[i].getID() + ": was interupted :(");
                        e.printStackTrace();

                        // Thread was interrupted
                    }
                */
                }

                if (this.home.wRun.STACKING_ENABLE && !cutoutList.isEmpty())
                {

                    int numStackedImages = ImageStacking.doStackingArrays(cutoutList, STACK_HEIGHT, STACK_WIDTH, STACK_FINAL_NAME);
                    if (DEBUG) System.out.println("AstroPortal: stacked " + numStackedImages + " and saved in " + STACK_FINAL_NAME);

                }





                taskTimer.stop();

                sw.start();

                //String resultFile = null;
                //String result = null;
                WorkerResult result = new WorkerResult();
                result.setMachID(home.machID);
                if (DEBUG) if (DIPERF == false) System.out.println("initialized workerResult()...");




                    //result.setNumTasks(numTasksCompleted);
                result.setNumTasks(resultsQ.size());


                if (DEBUG) if (DIPERF == false) System.out.println("set result values...");

                    //numTasksFailed = taskXQ.size();
                    //if (DEBUG) if (DIPERF == false) System.out.println("failed tasks: " + taskXQ.size());

                result.setShutingDown(false);

                if (resultsQ.size() == 0)
                {
                    if (DEBUG) if (DIPERF == false) System.out.println("There were no result tasks to initialize...");
                        //Task[] failedTasks = new Task[0];
                        //result.setTasksFailed(failedTasks);
                }
                else
                {


                    Task[] taskArray = new Task[resultsQ.size()];

                    int i=0;
                    boolean FINAL_STACK = true;
                    while (resultsQ.size() > 0)
                    {
                        taskArray[i] = (Task)resultsQ.remove();

                        if (taskArray[i].getExitCode() == -99999)
                        {
                            //System.out.println("Worker lifetime of " + LIFETIME/1000.0 + " seconds ended after " + lt.getElapsedTime()/1000.0 + " seconds... exiting!");

                            if (home.lt.getElapsedTime() >= home.LIFETIME)
                            {
                                result.setShutingDown(true);
                                executorShuttingDown = true;
                            }
                        }


                        i++;

                    }

                    if (DEBUG) if (DIPERF == false) System.out.println("initialized tasks results...");
                    result.setTasks(taskArray);
                    if (DEBUG) if (DIPERF == false) System.out.println("set tasks results...");
                }


                numCacheEvicted = cacheEvicted.size();
                if (DEBUG) System.out.println("cache evicted: " + cacheEvicted.size());


                if (cacheEvicted.size() == 0)
                {
                    if (DEBUG) System.out.println("There were no cache evicted to initialize...");
                    result.setNumCacheEvicted(0);
                }
                else
                {
                    String[] cacheEvictedArray = new String[cacheEvicted.size()];
                    result.setNumCacheEvicted(cacheEvicted.size());

                    int i=0;
                    while (cacheEvicted.size() > 0)
                    {
                        cacheEvictedArray[i] = (String)cacheEvicted.remove();
                        i++;

                    }

                    if (DEBUG) System.out.println("initialized cache evicted...");
                    result.setCacheEvicted(cacheEvictedArray);
                    if (DEBUG) System.out.println("set cache evicted...");
                }






                sw.stop();
                if (DIPERF) System.out.println("WORKER:NotificationThread:packageResult(): " + sw.getElapsedTime() + " ms");
                if (DEBUG) System.out.println("Package Results: " + sw.getElapsedTime() + " ms");
                sw.reset();

                if (DEBUG) System.out.println("Stacking finished succesful: numTasksCompleted = " + numTasksCompleted);

                if (home.STANDALONE == false)
                {

                    //send back the result to the GPWS to the specific EPR
                    if (DEBUG) if (DIPERF == false) System.out.println("sending back the results to the GPWS...");

                    sw.start();
                    boolean WSsuccess = false;
                    long sleepTime = 500;
                    while (WSsuccess == false)
                    {


                        try
                        {

                            WorkerResultResponse wrr = gp.workerResult(result);
                            WSsuccess = wrr.isValid();

                            moreWork = wrr.isMoreWork();
                            if (moreWork)
                            {
                                wt.setValid(moreWork);
                                wt.setTasks(wrr.getTasks());

                            }
                            else
                            {
                                //break;
                            }

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            try
                            {
                                Thread.sleep(sleepTime);
                                sleepTime = sleepTime*2;
                                if (sleepTime > 600000)
                                {
                                    System.out.println("Giving up on contacting the service... shuting down!");
                                    this.home.shutDown("5: Giving up on tring to retrieve work from the service...");
                                }
                            }
                            catch (Exception eeeee)
                            {
                                eeeee.printStackTrace();

                            }

                        }
                    }

                    sw.stop();
                    if (DIPERF) System.out.println("WORKER:NotificationThread:workerResult(): " + sw.getElapsedTime() + " ms");
                    if (DEBUG) System.out.println("Send the results to service: " + sw.getElapsedTime() + " ms");

                    sw.reset();
                    if (DEBUG) if (DIPERF == false) System.out.println("sent back the results to the GPWS succesfully...");
                }


                synchronized (home)
                {

                    home.NUM_JOBS++;
                    home.numTasksCompleted += numTasksCompleted;
                    //home.numTasksFailed += numTasksFailed;
                    home.elapsedTime += taskTimer.getElapsedTime(); 
                }

                jobTimer.stop();

                if (!DIPERF && !PROFILING) System.out.println("\nJob #" + jobID + ": Finished in " + jobTimer.getElapsedTime() + " ms (" + taskTimer.getElapsedTime() + " ms); " + numTasksCompleted + " tasks completed, " + numCacheEvicted + " cache entries evicted; result sent back to GPWS!");
                else
                {
                    System.out.println("WORKER:NotificationThread:RetrieveTasks: " + (jobTimer.getElapsedTime() - taskTimer.getElapsedTime()));
                    System.out.println("WORKER:NotificationThread:TasksExec: " + taskTimer.getElapsedTime());
                }
                jobTimer.reset();
                jobTimer.start();


                //this.home.activeNotificationsDec();

            }
            else
            {
                if (DEBUG) if (DIPERF == false) System.out.println("There was no work returned by workerWork()...  perhaps the work was collected by a different worker, but should check into why the work signaled by the notification was not available...");
                if (DEBUG) if (DIPERF == false) System.out.println("Notification thread exiting...");
                moreWork = false;
                //this.home.activeNotificationsDec();

            }
        }
        this.home.activeNotificationsDec();

        if (executorShuttingDown)
        {
            this.home.shutDown("-99999: Time has expired, shutting down...");
        }



    }


}



class ExecThread extends Thread
{
    //public boolean CACHING;

    // Special end-of-stream marker. If a worker retrieves
    // an Integer that equals this marker, the worker will terminate.
    static final Object NO_MORE_WORK = new Object();
    //static final Object NO_MORE_WORK = new Object();

    NotificationThread job;
    WorkQueue tQ;
    WorkQueue rQ;
    boolean DEBUG;
    boolean DIPERF;
    boolean PROFILING;
    String DATA_TRACE;

    //public boolean CACHING;



    InetAddress addr;// = InetAddress.getByName("java.sun.com");
    int port;// = 80;
    SocketAddress sockaddr;// = new InetSocketAddress(addr, port);
    Socket socket;// = new Socket();
    int timeoutMs;// = 2000;   // 2 seconds

    PrintWriter out = null;
    //BufferedReader in = null;



    ExecThread(NotificationThread j, WorkQueue tq, WorkQueue rq)
    {
        this.job = j;
        this.tQ = tq;
        this.rQ = rq;

        this.DEBUG = this.job.DEBUG;
        this.DIPERF = this.job.DIPERF;
        this.PROFILING = this.job.PROFILING;
        this.DATA_TRACE = this.job.DATA_TRACE;
        //this.CACHING = this.job.CACHING;

    }

    //this is not correct
    public long getID()
    {
        //return ThreadLocal.getId();
        long id = 0;
        return id;
    }

    public void run()
    {
        try
        {

            doRun();
        }
        catch (Exception e)
        {
            if (DEBUG) if (DIPERF == false) System.out.println("There was an error in Read Thread doRun(): " + e);
            if (DEBUG) if (DIPERF == false) System.out.println("Read thread exiting...");
            e.printStackTrace();

            // throw new Exception(e);
        }

    }


    public void closeStreams(Process p)
    {
        try
        {


            p.getInputStream().close();
            p.getOutputStream().close();
            p.getErrorStream().close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /*
    public String exec(String com, String args[], String env[], String dir)
    {
        MyProcess myChild = new MyProcess(DEBUG, saveOutput);
        //Process child = null;

        try
        {
            // Execute command
            if (com == null)
            {
                if (DEBUG) System.out.println("Command was null, cannot execute...");
                return null;
            }
            //String command = args[0];

            if (DEBUG) System.out.print("Building executable command...");

            String comArgs[];

            if (args != null)
            {

                comArgs = new String[args.length + 1];
                comArgs[0] = com;
                for (int i=0;i<args.length;i++)
                {
                    comArgs[i+1] = args[i];
                }
            }
            else
            {

                comArgs = new String[1];
                comArgs[0] = com;
            }

            File directory = null;

            if (job.home.CACHING && job.home.cacheGrid.scratchPath != null)
            {
                directory = new File(job.home.cacheGrid.scratchPath);
            }
            else if (dir != null)
            {
                directory = new File(dir);
            }



            if (DEBUG) System.out.print("Executing: ");

            for (int i=0;i<comArgs.length;i++)
            {
                if (DEBUG) System.out.print(comArgs[i] + " ");

            } 
            if (DEBUG) System.out.println("");

            if (DEBUG) System.out.println("Environment: ");
            if (env != null)
            {

                for (int i=0;i<env.length;i++)
                {
                    if (DEBUG) System.out.println(env[i]);

                }
            }

            if (DEBUG) System.out.print("Working Directory: ");
            if (dir != null)
            {

                if (DEBUG) System.out.println(dir);
            }

            //if (DEBUG) System.out.println("");

            //child = Runtime.getRuntime().exec(comArgs, env, directory);
            child = Runtime.getRuntime().exec(comArgs, env, directory);

            // Get the input stream and read from it
            InputStream in = child.getInputStream();
            String output = new String("");
            int c;
            char cc[] = new char[1];
            while ((c = in.read()) != -1)
            {
                //process((char)c);
                cc[0] = (char)c;
                //System.out.print((char)c);
                output += new String(cc);
            }
            in.close();

            InputStream inError = child.getErrorStream();
            output += new String("\nErrors:\n");
            //String output = new String("");
            //int c;
            //char cc[] = new char[1];
            while ((c = inError.read()) != -1)
            {
                //process((char)c);
                cc[0] = (char)c;
                //System.out.print((char)c);
                output += new String(cc);
            }
            inError.close();


            //System.out.println("Exit value: " + child.exitValue());

            if (DEBUG) System.out.println("Output:");
            if (DEBUG) System.out.println(output);

            if (DEBUG) System.out.println("Waiting for command to complete...");
            if (DEBUG) System.out.println("Exit value: " + child.waitFor());


            if (child != null)
            {
                closeStreams(child);

            }

            return output;

        }
        catch (Exception e)
        {
            if (DEBUG) System.out.println("Error: " + e);
            if (DEBUG) e.printStackTrace();


            if (child != null)
            {
                closeStreams(child);

            }
            return "Error in executing the command '" + com + "': " + e.getMessage();


            //should return the stack trace...
            /*
            String errorMsg = new String("");
            StackTraceElement stack[] = e.getStackTrace();


            if (stack.length > 0)
            {
            
        for (int i=0; i<stack.length; i++) {
            String filename = stack[i].getFileName();
            if (filename == null) {
                // The source filename is not available
            }
            String className = stack[i].getClassName();
            String methodName = stack[i].getMethodName();
            boolean isNativeMethod = stack[i].isNativeMethod();
            int line = stack[i].getLineNumber();
        }            }
            else
                return e.getMessage();     
        }
    }
    */


    /*
public String exec_old(String com, String args[], String env[], String dir)
{
    Process child = null;

    try
    {
        // Execute command
        if (com == null)
        {
            if (DEBUG) System.out.println("Command was null, cannot execute...");
            return null;
        }
        //String command = args[0];

        if (DEBUG) System.out.print("Building executable command...");

        String comArgs[];

        if (args != null)
        {

            comArgs = new String[args.length + 1];
            comArgs[0] = com;
            for (int i=0;i<args.length;i++)
            {
                comArgs[i+1] = args[i];
            }
        }
        else
        {

            comArgs = new String[1];
            comArgs[0] = com;
        }

        File directory = null;

        if (job.home.CACHING && job.home.cacheGrid.scratchPath != null)
        {
            directory = new File(job.home.cacheGrid.scratchPath);
        }
        else if (dir != null)
        {
            directory = new File(dir);
        }



        if (DEBUG) System.out.print("Executing: ");

        for (int i=0;i<comArgs.length;i++)
        {
            if (DEBUG) System.out.print(comArgs[i] + " ");

        } 
        if (DEBUG) System.out.println("");

        if (DEBUG) System.out.println("Environment: ");
        if (env != null)
        {

            for (int i=0;i<env.length;i++)
            {
                if (DEBUG) System.out.println(env[i]);

            }
        }

        if (DEBUG) System.out.print("Working Directory: ");
        if (dir != null)
        {

            if (DEBUG) System.out.println(dir);
        }

        //if (DEBUG) System.out.println("");

        child = Runtime.getRuntime().exec(comArgs, env, directory);

        // Get the input stream and read from it
        InputStream in = child.getInputStream();
        String output = new String("");
        int c;
        char cc[] = new char[1];
        while ((c = in.read()) != -1)
        {
            //process((char)c);
            cc[0] = (char)c;
            //System.out.print((char)c);
            output += new String(cc);
        }
        in.close();

        InputStream inError = child.getErrorStream();
        output += new String("\nErrors:\n");
        //String output = new String("");
        //int c;
        //char cc[] = new char[1];
        while ((c = inError.read()) != -1)
        {
            //process((char)c);
            cc[0] = (char)c;
            //System.out.print((char)c);
            output += new String(cc);
        }
        inError.close();


        //System.out.println("Exit value: " + child.exitValue());

        if (DEBUG) System.out.println("Output:");
        if (DEBUG) System.out.println(output);

        if (DEBUG) System.out.println("Waiting for command to complete...");
        if (DEBUG) System.out.println("Exit value: " + child.waitFor());


        if (child != null)
        {
            closeStreams(child);

        }

        return output;

    }
    catch (Exception e)
    {
        if (DEBUG) System.out.println("Error: " + e);
        if (DEBUG) e.printStackTrace();


        if (child != null)
        {
            closeStreams(child);

        }
        return "Error in executing the command '" + com + "': " + e.getMessage();


        //should return the stack trace...
        /*
        String errorMsg = new String("");
        StackTraceElement stack[] = e.getStackTrace();


        if (stack.length > 0)
        {
        
    for (int i=0; i<stack.length; i++) {
        String filename = stack[i].getFileName();
        if (filename == null) {
            // The source filename is not available
        }
        String className = stack[i].getClassName();
        String methodName = stack[i].getMethodName();
        boolean isNativeMethod = stack[i].isNativeMethod();
        int line = stack[i].getLineNumber();
    }            }
        else
            return e.getMessage();     
    }
}                                      */

    public static StringUtil stringUtil = new StringUtil();
    public static final String errorMsg = "Stale NFS file handle";
    public static String blankString = new String("");

    boolean saveOutput = true;


    public Task exec(Task task)
    {

        MyProcess myChild = new MyProcess(DEBUG, saveOutput);
        //Process child = null;
        Executable executable = task.getExecutable();
        String com = executable.getCommand();
        String args[] = executable.getArguements();
        String env[] = executable.getEnvironment();
        String dir = executable.getDirectory();
        boolean staleNfsHandle = false;
        //ChildListener childListener = null;

        try
        {
            // Execute command
            if (com == null)
            {
                if (DEBUG) System.out.println("Command was null, cannot execute...");
                task.setExitCode(-1);
                return task;
            }

            if (!DIPERF && !PROFILING) System.out.print("\nExecuting task " + (task.getExecutable()).getId() + "... ");

            if (DEBUG) System.out.print("Building executable command...");

            String comArgs[];

            if (args != null)
            {

                comArgs = new String[args.length + 1];
                comArgs[0] = com;
                for (int i=0;i<args.length;i++)
                {
                    comArgs[i+1] = args[i];
                }
            }
            else
            {

                comArgs = new String[1];
                comArgs[0] = com;
            }

            File directory = null;
            if (job.home.CACHING && job.home.cacheGrid.scratchPath != null)
            {
                directory = new File(job.home.cacheGrid.scratchPath);
            }
            else if (dir != null)
            {
                directory = new File(dir);
            }



            if (DEBUG) System.out.print("Executing: ");

            for (int i=0;i<comArgs.length;i++)
            {
                if (DEBUG) System.out.print(comArgs[i] + " ");

            } 
            if (DEBUG) System.out.println("");

            if (DEBUG) System.out.println("Environment: ");
            if (env != null)
            {

                for (int i=0;i<env.length;i++)
                {
                    if (DEBUG) System.out.println(env[i]);

                }
            }

            if (DEBUG) System.out.print("Working Directory: ");
            if (directory != null)
            {

                if (DEBUG) System.out.println(directory.getCanonicalPath());
            }


            //not exited yet
            int PROCESS_RUNNING = -99999;
            int exitCode = PROCESS_RUNNING;
            task.setExitCode(exitCode);

            boolean normal_task_execution = false;

            if (this.job.home.wRun.STACKING_ENABLE)
            //if (false)
            {

                //the merged steps to cutout and stack in 1 operation
                if (comArgs.length == 11 && comArgs[0].startsWith("java") && comArgs[1].startsWith("Cutout"))
                {
                    /*
                    for (all objects)
                    {
                    logicalImageName | logicalCutoutName = java Lookup RA DEC BAND
                    if ${logicalImageName}
                    logicalCutoutName = java Cutout ${logicalImageName} RA DEC BAND HEIGHT WIDTH SKY CAL logicalCutoutName
                    }
    
                    java Stack HEIGHT WIDTH ${logicalCutoutName1} ${logicalCutoutName2} ... ${logicalCutoutNameN} logicalCutoutNameFinal
    */
                    boolean parseCheck = false;

                    String logicalImageName = null;
                    double RA = 0;
                    double DEC = 0;
                    char BAND = ' ';
                    int HEIGHT = 0;
                    int WIDTH = 0;
                    double SKY = 0;
                    double CAL = 0;
                    String logicalCutoutName = null;

                    try
                    {
                        logicalImageName = comArgs[2];
                        RA = Double.parseDouble(comArgs[3]);
                        DEC = Double.parseDouble(comArgs[4]);
                        BAND = comArgs[5].charAt(0);
                        HEIGHT = Integer.parseInt(comArgs[6]);
                        WIDTH = Integer.parseInt(comArgs[7]);
                        SKY = Double.parseDouble(comArgs[8]);
                        CAL = Double.parseDouble(comArgs[9]);
                        logicalCutoutName = comArgs[10];

                        parseCheck = true;
                    }
                    catch (Exception parseError)
                    {
                        task.setStderr(parseError.getMessage());
                        if (DEBUG) parseError.printStackTrace();
                        exitCode = -3;

                        task.setExitCode(exitCode);
                        parseCheck = false;

                    }

                    if (parseCheck)
                    {
                        try
                        {
                            String dirPath = new String("");



                            if (task.getExecutable().isDataCaching() && directory != null)
                            {
                                dirPath = directory.getCanonicalPath() + "/";
                            }



                            //ASTROPORTAL: Comment out to compile on IA64... 
                            //this call writes the cutout to a file
                            //exitCode = ImageStacking.doCutout(dirPath + logicalImageName, RA, DEC, BAND, HEIGHT, WIDTH, SKY, CAL, dirPath + logicalCutoutName);
                            //this call saves the cutout in memory
                            if (DEBUG) System.out.println("ImageStacking.getCutout() from " + dirPath + logicalImageName + " at {" + RA + "," + DEC+","+BAND+"} for " + HEIGHT+"x"+WIDTH+" with SKY="+SKY+" and CAL="+CAL+"...");
                            double tCutout[][] = ImageStacking.getCutout(dirPath + logicalImageName, RA, DEC, BAND, HEIGHT, WIDTH, SKY, CAL);
                            if (tCutout == null)
                            {
                                //failed
                                exitCode = -44444;
                                System.out.println("ImageStacking.getCutout() from " + dirPath + logicalImageName + " failed with exit code " + exitCode);

                            }
                            else
                            {
                                if (this.job.cutoutList.isEmpty())
                                {

                                    //set to 33333 so the output file is disregarded
                                    exitCode = 0;
                                    if (DEBUG) System.out.println("ImageStacking.getCutout() from " + dirPath + logicalImageName + " successful (first one) with exit code " + exitCode);

                                    //insert tCutout in some list...
                                    this.job.cutoutList.add(tCutout);

                                    this.job.STACK_HEIGHT = HEIGHT;
                                    this.job.STACK_WIDTH = WIDTH;
                                    this.job.STACK_FINAL_NAME = new String(dirPath + logicalCutoutName);
                                }
                                else
                                {
                                    //set to 33333 so the output file is disregarded
                                    exitCode = 33333;
                                    if (DEBUG) System.out.println("ImageStacking.getCutout() from " + dirPath + logicalImageName + " successful with exit code " + exitCode);
                                    this.job.cutoutList.add(tCutout);

                                }
                            }
                            //exitCode = -5555;


                            task.setExitCode(exitCode);
                        }
                        catch (Exception javaClassLoaderException)
                        {
                            task.setStderr(javaClassLoaderException.getMessage());
                            if (DEBUG) javaClassLoaderException.printStackTrace();
                            exitCode = -1;
                            task.setExitCode(exitCode);
                        }
                    }

                }
                else if (comArgs.length >= 6 && comArgs[0].startsWith("java") && comArgs[1].startsWith("Stack"))
                {
                    //java Stack HEIGHT WIDTH ${logicalCutoutName1} ${logicalCutoutName2} ... ${logicalCutoutNameN} logicalCutoutNameFinal

                    boolean parseCheck = false;

                    int HEIGHT = 0;
                    int WIDTH = 0;
                    List logicalCutoutNames = new LinkedList();
                    String logicalCutoutNameFinal = null;

                    try
                    {
                        HEIGHT = Integer.parseInt(comArgs[2]);
                        WIDTH = Integer.parseInt(comArgs[3]);


                        String dirPath = new String("");



                        if (task.getExecutable().isDataCaching() && directory != null)
                        {
                            dirPath = directory.getCanonicalPath() + "/";
                        }



                        for (int i=4;i<comArgs.length - 1;i++)
                        {
                            logicalCutoutNames.add(dirPath + comArgs[i]);
                        }
                        logicalCutoutNameFinal = dirPath + comArgs[comArgs.length - 1];

                        parseCheck = true;
                    }
                    catch (Exception parseError)
                    {
                        task.setStderr(parseError.getMessage());
                        if (DEBUG) parseError.printStackTrace();
                        exitCode = -3;

                        task.setExitCode(exitCode);
                        parseCheck = false;

                    }

                    if (parseCheck)
                    {
                        try
                        {

                            String dirPath = new String("");



                            if (task.getExecutable().isDataCaching() && directory != null)
                            {
                                dirPath = directory.getCanonicalPath() + "/";
                            }


                            if (DEBUG) System.out.println("ImageStacking.doStacking() for " + logicalCutoutNames.size() + " stacks for " + HEIGHT+"x"+WIDTH+" ==> " + logicalCutoutNameFinal + " ...");

                            //this.job.home.wRun.STACKING_ENABLE
                            //ASTROPORTAL: Comment out to compile on IA64... 
                            int numStackings = ImageStacking.doStacking(logicalCutoutNames, HEIGHT, WIDTH, logicalCutoutNameFinal);
                            //int numStackings = 0;


                            if (numStackings == -1)
                            {
                                //could not write the output file, probably no room on the disk device
                                exitCode = -5;
                                if (DEBUG) System.out.println("ImageStacking.doStacking() for " + logicalCutoutNames.size() + " failed... could not write the output file, probably no room on the disk device, exit code " + exitCode);


                            }

                            else if (numStackings > 0)
                            {
                                exitCode = 0;
                                if (DEBUG) System.out.println("ImageStacking.doStacking() for " + logicalCutoutNames.size() + " succesful with " + numStackings + " stackings, exit code " + exitCode);
                            }
                            else
                            {
                                //no images were stacked...
                                exitCode = -4;
                                if (DEBUG) System.out.println("ImageStacking.doStacking() for " + logicalCutoutNames.size() + " failed, exit code " + exitCode);
                            }

                            task.setExitCode(exitCode);
                        }
                        catch (Exception javaClassLoaderException)
                        {
                            task.setStderr(javaClassLoaderException.getMessage());
                            if (DEBUG) javaClassLoaderException.printStackTrace();
                            exitCode = -1;
                            task.setExitCode(exitCode);
                        }
                    }

                }
                //normal task execution
                else
                {
                    normal_task_execution = true;

                }
            }
            //normal task execution
            if (this.job.home.wRun.STACKING_ENABLE == false || normal_task_execution)
            {

                //child = Runtime.getRuntime().exec(comArgs, env, directory);
                //if (DEBUG) System.out.println("job.home.LIFETIME = " + job.home.LIFETIME + " ; job.home.lt.getElapsedTime() = " + job.home.lt.getElapsedTime());
                long maxWallTime = 0;
                if (job.home.LIFETIME > 0)
                {
                    maxWallTime = (long)Math.max(1, job.home.LIFETIME - job.home.lt.getElapsedTime());
                }
                //else

                //this is where we could enforce user specified max wall times


                exitCode = myChild.exec(comArgs, env, directory, maxWallTime);

                if (exitCode == 0)
                {
                    if (DEBUG) System.out.println("Execution of task succesful: exitCode = " + exitCode);

                }
                else
                {

                    if (DEBUG) System.out.println("Execution of task failed: exitCode = " + exitCode);

                    if (stringUtil.contains(myChild.getStreamErr(), errorMsg) || stringUtil.contains(myChild.getStreamOut(), errorMsg))
                    {
                        staleNfsHandle = true;
                        exitCode = -12345;

                        if (DEBUG) System.out.println("Stale NFS file handle error found\n Error stream: " + myChild.getStreamErr() +"\n Output stream: " + myChild.getStreamOut() );

                    }
                    else
                    {
                        if (DEBUG) System.out.println("Stale NFS file handle error not found... ");

                    }
                }


                if (task.isCaptureStderr())
                    task.setStderr(myChild.getStreamErr());
                if (task.isCaptureStderr())
                    task.setStdout(myChild.getStreamOut());

            }


            if (job.home.CACHING && (task.getExecutable()).isDataCaching())
            {
                if (exitCode == 0)
                {

                    exitCode = job.home.cacheGrid.persistCache3(((task.getExecutable()).getOutputData()).getLogicalName(), ((task.getExecutable()).getOutputData()).getFileURL());
                }
            }

            if (!DIPERF && !PROFILING) System.out.print("\nTask " + (task.getExecutable()).getId() + " completed with exit code " + exitCode + "! ");

            if (DEBUG) System.out.println("Exit value: " + exitCode);


            task.setExitCode(exitCode);
            if (staleNfsHandle)
            {
                if (DEBUG) System.out.println("Infamous stale NFS handle error found...");
            }


            return task;

        }
        catch (Exception e)
        {
            if (DEBUG) System.out.println("Error: " + e);
            if (DEBUG) e.printStackTrace();
            task.setExitCode(-1);

            return task;

        }
    }

    /*
   public Task exec_old(Task task)
   {

       Process child = null;
       Executable executable = task.getExecutable();
       String com = executable.getCommand();
       String args[] = executable.getArguements();
       String env[] = executable.getEnvironment();
       String dir = executable.getDirectory();
       boolean staleNfsHandle = false;
       ChildListener childListener = null;

       try
       {
           // Execute command
           if (com == null)
           {
               if (DEBUG) System.out.println("Command was null, cannot execute...");
               task.setExitCode(-1);
               return task;
           }

           if (!DIPERF && !PROFILING) System.out.print("\nExecuting task " + (task.getExecutable()).getId() + "... ");

           if (DEBUG) System.out.print("Building executable command...");

           String comArgs[];

           if (args != null)
           {

               comArgs = new String[args.length + 1];
               comArgs[0] = com;
               for (int i=0;i<args.length;i++)
               {
                   comArgs[i+1] = args[i];
               }
           }
           else
           {

               comArgs = new String[1];
               comArgs[0] = com;
           }

           File directory = null;
           if (job.home.CACHING && job.home.cacheGrid.scratchPath != null)
           {
               directory = new File(job.home.cacheGrid.scratchPath);
           }
           else if (dir != null)
           {
               directory = new File(dir);
           }



           if (DEBUG) System.out.print("Executing: ");

           for (int i=0;i<comArgs.length;i++)
           {
               if (DEBUG) System.out.print(comArgs[i] + " ");

           } 
           if (DEBUG) System.out.println("");

           if (DEBUG) System.out.println("Environment: ");
           if (env != null)
           {

               for (int i=0;i<env.length;i++)
               {
                   if (DEBUG) System.out.println(env[i]);

               }
           }

           if (DEBUG) System.out.print("Working Directory: ");
           if (directory != null)
           {

               if (DEBUG) System.out.println(directory.getCanonicalPath());
           }


           //not exited yet
           int PROCESS_RUNNING = -99999;
           int exitCode = PROCESS_RUNNING;
           task.setExitCode(exitCode);

           boolean normal_task_execution = false;

           if (this.job.home.wRun.STACKING_ENABLE)
           //if (false)
           {

               //the merged steps to cutout and stack in 1 operation
               if (comArgs.length == 11 && comArgs[0].startsWith("java") && comArgs[1].startsWith("Cutout"))
               {
                   /*
                   for (all objects)
                   {
                   logicalImageName | logicalCutoutName = java Lookup RA DEC BAND
                   if ${logicalImageName}
                   logicalCutoutName = java Cutout ${logicalImageName} RA DEC BAND HEIGHT WIDTH SKY CAL logicalCutoutName
                   }
   
                   java Stack HEIGHT WIDTH ${logicalCutoutName1} ${logicalCutoutName2} ... ${logicalCutoutNameN} logicalCutoutNameFinal
   
                   boolean parseCheck = false;

                   String logicalImageName = null;
                   double RA = 0;
                   double DEC = 0;
                   char BAND = ' ';
                   int HEIGHT = 0;
                   int WIDTH = 0;
                   double SKY = 0;
                   double CAL = 0;
                   String logicalCutoutName = null;

                   try
                   {
                       logicalImageName = comArgs[2];
                       RA = Double.parseDouble(comArgs[3]);
                       DEC = Double.parseDouble(comArgs[4]);
                       BAND = comArgs[5].charAt(0);
                       HEIGHT = Integer.parseInt(comArgs[6]);
                       WIDTH = Integer.parseInt(comArgs[7]);
                       SKY = Double.parseDouble(comArgs[8]);
                       CAL = Double.parseDouble(comArgs[9]);
                       logicalCutoutName = comArgs[10];

                       parseCheck = true;
                   }
                   catch (Exception parseError)
                   {
                       task.setStderr(parseError.getMessage());
                       if (DEBUG) parseError.printStackTrace();
                       exitCode = -3;

                       task.setExitCode(exitCode);
                       parseCheck = false;

                   }

                   if (parseCheck)
                   {
                       try
                       {
                           String dirPath = new String("");



                           if (task.getExecutable().isDataCaching() && directory != null)
                           {
                               dirPath = directory.getCanonicalPath() + "/";
                           }



                           //ASTROPORTAL: Comment out to compile on IA64... 
                           //this call writes the cutout to a file
                           //exitCode = ImageStacking.doCutout(dirPath + logicalImageName, RA, DEC, BAND, HEIGHT, WIDTH, SKY, CAL, dirPath + logicalCutoutName);
                           //this call saves the cutout in memory
                           if (DEBUG) System.out.println("ImageStacking.getCutout() from " + dirPath + logicalImageName + " at {" + RA + "," + DEC+","+BAND+"} for " + HEIGHT+"x"+WIDTH+" with SKY="+SKY+" and CAL="+CAL+"...");
                           double tCutout[][] = ImageStacking.getCutout(dirPath + logicalImageName, RA, DEC, BAND, HEIGHT, WIDTH, SKY, CAL);
                           if (tCutout == null)
                           {
                               //failed
                               exitCode = -44444;
                               System.out.println("ImageStacking.getCutout() from " + dirPath + logicalImageName + " failed with exit code " + exitCode);

                           }
                           else
                           {
                               if (this.job.cutoutList.isEmpty())
                               {

                                   //set to 33333 so the output file is disregarded
                                   exitCode = 0;
                                   if (DEBUG) System.out.println("ImageStacking.getCutout() from " + dirPath + logicalImageName + " successful (first one) with exit code " + exitCode);

                                   //insert tCutout in some list...
                                   this.job.cutoutList.add(tCutout);

                                   this.job.STACK_HEIGHT = HEIGHT;
                                   this.job.STACK_WIDTH = WIDTH;
                                   this.job.STACK_FINAL_NAME = new String(dirPath + logicalCutoutName);
                               }
                               else
                               {
                                   //set to 33333 so the output file is disregarded
                                   exitCode = 33333;
                                   if (DEBUG) System.out.println("ImageStacking.getCutout() from " + dirPath + logicalImageName + " successful with exit code " + exitCode);
                                   this.job.cutoutList.add(tCutout);

                               }
                           }
                           //exitCode = -5555;


                           task.setExitCode(exitCode);
                       }
                       catch (Exception javaClassLoaderException)
                       {
                           task.setStderr(javaClassLoaderException.getMessage());
                           if (DEBUG) javaClassLoaderException.printStackTrace();
                           exitCode = -1;
                           task.setExitCode(exitCode);
                       }
                   }

               }
               else if (comArgs.length >= 6 && comArgs[0].startsWith("java") && comArgs[1].startsWith("Stack"))
               {
                   //java Stack HEIGHT WIDTH ${logicalCutoutName1} ${logicalCutoutName2} ... ${logicalCutoutNameN} logicalCutoutNameFinal

                   boolean parseCheck = false;

                   int HEIGHT = 0;
                   int WIDTH = 0;
                   List logicalCutoutNames = new LinkedList();
                   String logicalCutoutNameFinal = null;

                   try
                   {
                       HEIGHT = Integer.parseInt(comArgs[2]);
                       WIDTH = Integer.parseInt(comArgs[3]);


                       String dirPath = new String("");



                       if (task.getExecutable().isDataCaching() && directory != null)
                       {
                           dirPath = directory.getCanonicalPath() + "/";
                       }



                       for (int i=4;i<comArgs.length - 1;i++)
                       {
                           logicalCutoutNames.add(dirPath + comArgs[i]);
                       }
                       logicalCutoutNameFinal = dirPath + comArgs[comArgs.length - 1];

                       parseCheck = true;
                   }
                   catch (Exception parseError)
                   {
                       task.setStderr(parseError.getMessage());
                       if (DEBUG) parseError.printStackTrace();
                       exitCode = -3;

                       task.setExitCode(exitCode);
                       parseCheck = false;

                   }

                   if (parseCheck)
                   {
                       try
                       {

                           String dirPath = new String("");



                           if (task.getExecutable().isDataCaching() && directory != null)
                           {
                               dirPath = directory.getCanonicalPath() + "/";
                           }


                           if (DEBUG) System.out.println("ImageStacking.doStacking() for " + logicalCutoutNames.size() + " stacks for " + HEIGHT+"x"+WIDTH+" ==> " + logicalCutoutNameFinal + " ...");

                           //this.job.home.wRun.STACKING_ENABLE
                           //ASTROPORTAL: Comment out to compile on IA64... 
                           int numStackings = ImageStacking.doStacking(logicalCutoutNames, HEIGHT, WIDTH, logicalCutoutNameFinal);
                           //int numStackings = 0;


                           if (numStackings == -1)
                           {
                               //could not write the output file, probably no room on the disk device
                               exitCode = -5;
                               if (DEBUG) System.out.println("ImageStacking.doStacking() for " + logicalCutoutNames.size() + " failed... could not write the output file, probably no room on the disk device, exit code " + exitCode);


                           }

                           else if (numStackings > 0)
                           {
                               exitCode = 0;
                               if (DEBUG) System.out.println("ImageStacking.doStacking() for " + logicalCutoutNames.size() + " succesful with " + numStackings + " stackings, exit code " + exitCode);
                           }
                           else
                           {
                               //no images were stacked...
                               exitCode = -4;
                               if (DEBUG) System.out.println("ImageStacking.doStacking() for " + logicalCutoutNames.size() + " failed, exit code " + exitCode);
                           }

                           task.setExitCode(exitCode);
                       }
                       catch (Exception javaClassLoaderException)
                       {
                           task.setStderr(javaClassLoaderException.getMessage());
                           if (DEBUG) javaClassLoaderException.printStackTrace();
                           exitCode = -1;
                           task.setExitCode(exitCode);
                       }
                   }

               }
               //normal task execution
               else
               {
                   normal_task_execution = true;

               }
           }
           //normal task execution
           if (this.job.home.wRun.STACKING_ENABLE == false || normal_task_execution)
           {

               child = Runtime.getRuntime().exec(comArgs, env, directory);



               try
               {

                   exitCode = child.exitValue();
                   if (DEBUG) System.out.println("Exit value: " + exitCode);
               }
               catch (Exception e)
               {
                   if (DEBUG)
                   {
                       e.printStackTrace();
                   }



                   if (stringUtil.contains(e.getMessage(), errorMsg))
                   {
                       staleNfsHandle = true;
                       if (DEBUG) System.out.println("Stale NFS file handle error found... " + e.getMessage());

                   }
                   else
                   {
                       if (DEBUG) System.out.println("Stale NFS file handle error not found... " + e.getMessage());

                   }

               }

               if (exitCode == PROCESS_RUNNING)
               {



                   if (DEBUG) System.out.println("starting ChildListener thread...");
                   //need to fix... in case shutting down, need to terminate early!

                   childListener = new ChildListener(task, child);

                   childListener.start();

                   long POLL_INTERVAL = 1000;
                   long SAFETY_INTERVAL = 0;

                   while ((job.home.LIFETIME == 0  && exitCode == PROCESS_RUNNING) || (job.home.lt.getElapsedTime() < job.home.LIFETIME - SAFETY_INTERVAL && exitCode == PROCESS_RUNNING))
                   {
                       if (DEBUG) System.out.println("polling ChildListener thread for termination...");
                       childListener.join(POLL_INTERVAL);


                       if (childListener.isAlive())
                       {
                           if (DEBUG) System.out.println("execution of task is not yet done");
                       }
                       else
                       {
                           try
                           {

                               exitCode = child.exitValue();
                               //if (DEBUG) System.out.println("Exit value: " + exitCode);
                           }
                           catch (Exception e)
                           {
                               if (DEBUG)
                               {
                                   if (DEBUG) System.out.println("execution of task is not yet done");//e.printStackTrace();
                               }

                           }
                       }

                   }

                   //it 
                   /*
                   if (job.home.lt.getElapsedTime() < job.home.LIFETIME - SAFETY_INTERVAL)
                   {
                       if (DEBUG) System.out.println("time expired...");
                   } else
                       if (DEBUG) System.out.println("ChildListener thread terminated...");
                     *

                   try
                   {

                       exitCode = child.exitValue();
                       if (DEBUG) System.out.println("Exit value: " + exitCode);
                   }
                   catch (Exception e)
                   {
                       if (DEBUG) System.out.println("execution of task is not yet done, but executor is shuttin down...");//e.printStackTrace();

                   }



                   if (exitCode == PROCESS_RUNNING)
                   {
                       if (DEBUG) System.out.println("destroy child and child Listener....");

                       if (child != null)
                       {
                           closeStreams(child);

                       }
                       child.destroy();
                       childListener.interrupt();


                   }

                   if (task.isCaptureStderr())
                       task.setStderr(childListener.err);
                   if (task.isCaptureStderr())
                       task.setStdout(childListener.output);
                   //if (DEBUG) System.out.println("Exit value: " + exitCode);


                   //int exitCode = child.waitFor();



                   //int exitCode = child.exitValue();

               }

               //task failed... do pattern mathcing for known errors...
               if (exitCode != 0)
               {
                   if (staleNfsHandle)
                   {
                       exitCode = -12345;

                   }
                   else
                   {
                       try
                       {
                           if (childListener != null)
                           {

                               if (stringUtil.contains(childListener.err, errorMsg))
                               {
                                   staleNfsHandle = true;
                                   exitCode = -12345;

                                   if (DEBUG) System.out.println("Stale NFS file handle error found... " + childListener.err);

                               }
                               else if (stringUtil.contains(childListener.output, errorMsg))
                               {
                                   staleNfsHandle = true;
                                   exitCode = -12345;

                                   if (DEBUG) System.out.println("Stale NFS file handle error found... " + childListener.output);

                               }
                               else
                               {

                                   if (DEBUG) System.out.println("Stale NFS file handle error not found... " + childListener.err + "\n" + childListener.output);
                               }
                           }
                       }
                       catch (Exception e)
                       {
                           if (DEBUG)
                           {
                               e.printStackTrace();
                           }
                       }


                   }

               }
           }


           if (job.home.CACHING && (task.getExecutable()).isDataCaching())
           {

               //DataFiles outputData = (task.getExecutable()).getOutputData();

               //String outputDataFiles[] = outputData.getLogicalName();
               //String outputDataFiles[] = outputData.getFileURL();


               if (exitCode == 0)
               {

                   exitCode = job.home.cacheGrid.persistCache3(((task.getExecutable()).getOutputData()).getLogicalName(), ((task.getExecutable()).getOutputData()).getFileURL());
                   //    exitCode = -1000;
               }
           }

           if (!DIPERF && !PROFILING) System.out.print("\nTask " + (task.getExecutable()).getId() + " completed with exit code " + exitCode + "! ");

           if (DEBUG) System.out.println("Exit value: " + exitCode);


           task.setExitCode(exitCode);
           if (staleNfsHandle)
           {
               if (DEBUG) System.out.println("Infamous stale NFS handle error found...");
           }


           if (child != null)
           {
               closeStreams(child);

           }

           return task;

       }
       catch (Exception e)
       {
           if (DEBUG) System.out.println("Error: " + e);
           if (DEBUG) e.printStackTrace();
           task.setExitCode(-1);

           if (child != null)
           {
               closeStreams(child);

           }

           return task;

           //return "Error in executing the command '" + com + "': " + e.getMessage();


           //should return the stack trace...
           /*
           String errorMsg = new String("");
           StackTraceElement stack[] = e.getStackTrace();


           if (stack.length > 0)
           {

       for (int i=0; i<stack.length; i++) {
           String filename = stack[i].getFileName();
           if (filename == null) {
               // The source filename is not available
           }
           String className = stack[i].getClassName();
           String methodName = stack[i].getMethodName();
           boolean isNativeMethod = stack[i].isNativeMethod();
           int line = stack[i].getLineNumber();
       }            }
           else
               return e.getMessage();     *
       }
   }                                       */



    public void doRun() throws Exception {
        int index = 0;


        //if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): start " + index++);
        try
        {
            StopWatch sw = new StopWatch();


            List outputFilesToBeCreated = Collections.synchronizedList(new LinkedList());    // Doubly-linked list


            while (true)
            {
                if (this.job.home.shuttingDown.booleanValue() == true)
                    return;

                sw.start();


                //if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): while() " + index++);

                Object o = tQ.remove();
                //if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): tQ.remove() " + index++);
                if (o == NO_MORE_WORK)
                {
                    //if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): if(o == NO_MORE_WORK) " + index++);
                    break;
                }
                Task task = (Task) o;
                //if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): (Task)o " + index++);
                if (task == null)
                {
                    //if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): if(task == null) " + index++);
                    break;
                }



                sw.stop();
                if (DIPERF) System.out.println("WORKER:NotificationThread:readThread:getTask(): " + sw.getElapsedTime() + " ms");
                sw.reset();


                //if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): if(exists) " + index++);


                boolean inputDataOK = true;
                boolean outputDataOK = true;
                //an exit code of -1111 indicates an explicit termination of the file transfers due to slow performance
                int cachingExitCode = 0;

                String failedInputData = null;
                String failedOutputData = null;
                String failedInputDataURL = null;

                sw.start();
                StopWatch dsw = new StopWatch();


                //old caching code that needs to be updated
                //String taskFileName = null;
                if (job.home.CACHING)
                {
                    try
                    {

                        //int numInputDataFiles = task.getNumInputDataFiles();
                        //String inputDataFiles[] = null;

                        if ((task.getExecutable()).isDataCaching())
                        {
                            dsw.start();
                            DataFiles inputData = (task.getExecutable()).getInputData();

                            String inputDataFiles[] = inputData.getLogicalName();
                            String inputDataFilesURL[] = inputData.getFileURL();
                            //these should be the same length
                            DataCache dataCache[] = inputData.getDataCache();

                            if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): inputDataFiles.length: " + inputDataFiles.length);
                            if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): inputDataFilesURL.length: " + inputDataFilesURL.length);
                            if (DEBUG)
                            {

                                if (dataCache != null) System.out.println("WORKER:NotificationThread:readThread:doRun(): dataCache.length: " + dataCache.length);
                                else System.out.println("WORKER:NotificationThread:readThread:doRun(): dataCache.length: 0");
                            }


                            for (int tt=0;tt<inputDataFiles.length;tt++)
                            {

                                if (DEBUG) System.out.println("inputDataFiles("+tt+"): " + inputDataFiles[tt] + " ==> " + inputDataFilesURL[tt]);

                            }

                            dsw.stop();
                            if (DIPERF) System.out.println("WORKER:NotificationThread:execThread:getInputDataFilesDescription: " + dsw.getElapsedTime() + " ms");
                            dsw.reset();



                            for (int tt=0;tt<inputDataFiles.length && inputDataOK;tt++)
                            {

                                dsw.start();
                                if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): if(CACHING == true)");
                                //CacheResp response = job.home.cacheGrid.getCache2(inputDataFiles[tt]);

                                CacheResp responses[] = null;

                                //update state for inputDataFiles

                                FileTransferState curFileTransfer = null;
                                synchronized(this.job.FileTransfers)
                                {

                                    curFileTransfer = (FileTransferState) this.job.FileTransfers.get(inputDataFiles[tt]);
                                    if (curFileTransfer == null)
                                    {
                                        FileTransferState tFileTransfer = new FileTransferState();
                                        tFileTransfer.setTransfering();

                                        this.job.FileTransfers.put(inputDataFiles[tt], tFileTransfer);

                                    }
                                }


                                if (curFileTransfer != null)
                                {

                                    long poll_interval = 10;
                                    StopWatch lw = new StopWatch();
                                    lw.start();

                                    while (curFileTransfer.isTransfering())
                                    {


                                        if (curFileTransfer.isDone() || curFileTransfer.isError())
                                        {
                                            CacheResp tResponses[] = curFileTransfer.getCacheResp();

                                            if (tResponses != null && tResponses.length >= 1)
                                            {
                                                responses = new CacheResp[1];
                                                responses[0].success = tResponses[0].success;
                                                responses[0].exitCode = tResponses[0].exitCode;

                                            }
                                            else
                                                responses = null;


                                            break;
                                        }

                                        try
                                        {
                                            //could abort the wait after xxx ms
                                            /*
                                            if (lw.getElapsedTime() > 1000)
                                            {
                                            }
                                            */

                                            if (PROFILING) System.out.println("WORKER:ExecThread(): inputDataFile " + inputDataFiles[tt] + " is in progress by another thread... sleeping for " + poll_interval + " before checking again...");
                                            Thread.sleep(poll_interval);
                                            poll_interval = Math.min(poll_interval*2, 1000);

                                        }
                                        catch (Exception e)
                                        {
                                            if (DEBUG) e.printStackTrace();
                                        }
                                    }




                                }
                                //no transfers in progress for this file.... must retrieve...
                                else
                                {

                                    //this.job.FileTransfers.get(inputDataFiles[tt])

                                    if (dataCache != null)
                                    {
                                        responses = job.home.cacheGrid.getCache3(inputDataFiles[tt], inputDataFilesURL[tt], dataCache[tt].getCacheLocation());
                                    }
                                    else
                                        responses = job.home.cacheGrid.getCache3(inputDataFiles[tt], inputDataFilesURL[tt], null);

                                    /*
                                    try
                                    {

                                    
                                    if (inputDataFiles[tt] != null)
                                    {
                                        job.home.cacheGrid.cache.setCacheContentSize((new File(inputDataFiles[tt])).length(),true);
                                        System.out.println("WorkerRun: updating cache size: " + inputDataFiles[tt] + " "+(new File(inputDataFiles[tt])).length() + " bytes");

                                    }
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                      */

                                    curFileTransfer = (FileTransferState)this.job.FileTransfers.get(inputDataFiles[tt]);
                                    if (curFileTransfer != null)
                                    {
                                        if (responses != null && responses[0].exitCode >= 0)
                                        {
                                            curFileTransfer.setDone(responses);
                                        }
                                        else if (responses != null)
                                        {

                                            responses[0].exitCode = -1111;
                                            if (DEBUG) System.out.println("line 4819: responses[0].exitCode = -1111");
                                            curFileTransfer.setError(responses);

                                            for (int i=0;i<responses.length;i++)
                                            {
                                                if (responses[i].value != null && responses[i].evictedKey != null)
                                                {
                                                    System.out.println("line 4819: responses["+i+"] = " + responses[i].value + " " + responses[i].evictedKey);
                                                }
                                                else if (responses[i].value != null )
                                                {
                                                    System.out.println("line 4819: responses["+i+"] = null " + responses[i].evictedKey);
                                                }
                                                else if (responses[i].evictedKey != null)
                                                {
                                                    System.out.println("line 4819: responses["+i+"] = " + responses[i].value + " null");
                                                }
                                                else
                                                {
                                                    System.out.println("line 4819: responses["+i+"] = null null");
                                                }

                                                
                                            }

                                        }
                                        else
                                        {
                                            responses = new CacheResp[1];
                                            responses[0] = new CacheResp();
                                            responses[0].success = false;
                                            if (DEBUG) System.out.println("line 4829: responses[0].exitCode = -1111");
                                            responses[0].exitCode = -1111;



                                            curFileTransfer.setError(responses);

                                        }
                                        this.job.FileTransfers.put(inputDataFiles[tt], curFileTransfer);


                                    }
                                    else
                                    {
                                        if (responses != null)
                                        {
                                            //this should never be null....
                                            curFileTransfer = new FileTransferState();
                                            if (DEBUG) System.out.println("line 4846: responses[0].exitCode = -1111");
                                            responses[0].exitCode = -1111;
                                            responses[0].success = false;

                                            curFileTransfer.setError(responses);

                                        }
                                        else
                                        {
                                            //this should never be null....
                                            curFileTransfer = new FileTransferState();
                                            responses = new CacheResp[1];
                                            responses[0] = new CacheResp();

                                            if (DEBUG) System.out.println("line 4860: responses[0].exitCode = -1111");
                                            responses[0].exitCode = -1111;
                                            responses[0].success = false;

                                            curFileTransfer.setError(responses);
                                        }

                                        this.job.FileTransfers.put(inputDataFiles[tt], curFileTransfer);

                                    }


                                }
                                dsw.stop();
                                if (DIPERF) System.out.println("WORKER:NotificationThread:execThread:getCache3(): " + dsw.getElapsedTime() + " ms");
                                dsw.reset();
                                if (responses != null)
                                {
                                    if (DEBUG) System.out.println("WORKER:NotificationThread:execThread:getCache3(): Exit Code: " + responses[0].exitCode);
                                }
                                else
                                {
                                    if (DEBUG) System.out.println("WORKER:NotificationThread:execThread:getCache3(): Exit Code: null");

                                }



                                dsw.start();
                                //taskFileName = response.value;
                                if (responses != null)
                                {

                                    for (int ttt=0;ttt<responses.length;ttt++)
                                    {

                                        if (responses[ttt].evictedKey != null)
                                        {
                                            if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): responses.evictedKey[ttt] = " + responses[ttt].evictedKey);

                                            job.addCacheEvicted(responses[ttt].evictedKey);
                                        }
                                        else
                                        {
                                            if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): no cache to evict");

                                        }

                                        if (!responses[ttt].success)
                                        {

                                            inputDataOK = false;
                                            System.out.println("error in inputData... !responses[ttt].success");
                                            failedInputData = inputDataFiles[tt];
                                            failedInputDataURL = inputDataFilesURL[tt];

                                            if (responses[0].exitCode < 0)
                                                cachingExitCode = responses[0].exitCode;
                                            else
                                                cachingExitCode = -98765;

                                            break;
                                        }
                                        //else
                                        //{
                                        //    inputDataOK = true;

                                        //}

                                    }

                                    ////no file found... remove from central index cache...
                                    //if (responses[ttt] == false)
                                    //{
                                    //    job.addCacheEvicted(inputDataFiles[tt]);


                                }
                                else
                                {

                                    if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): no cache to evict");
                                }
                                dsw.stop();
                                if (DIPERF) System.out.println("WORKER:NotificationThread:execThread:addCacheEvicted(): " + dsw.getElapsedTime() + " ms");
                                dsw.reset();
                            }
                        }


                        dsw.start();
                        DataFiles outputData = (task.getExecutable()).getOutputData();

                        dsw.stop();
                        if (DIPERF) System.out.println("WORKER:NotificationThread:execThread:getOutputDataDescription: " + dsw.getElapsedTime() + " ms");
                        dsw.reset();

                        if (outputData != null)
                        {


                            dsw.start();
                            String outputDataFiles[] = outputData.getLogicalName();

                            dsw.stop();
                            if (DIPERF) System.out.println("WORKER:NotificationThread:execThread:getOutputDataLogicalDescription: " + dsw.getElapsedTime() + " ms");
                            dsw.reset();

                            if (outputDataFiles != null && outputDataFiles.length > 0)
                            {
                                for (int tt=0;tt<outputDataFiles.length && outputDataOK;tt++)
                                {
                                    if (outputDataFiles[tt].startsWith("null") || outputDataFiles[tt].startsWith("/dev/null"))
                                    {
                                        if (DEBUG) System.out.println("output data file == null");
                                    }
                                    else
                                    {
                                    
                                    dsw.start();
                                    CacheResp responses[] = null;
                                    responses = job.home.cacheGrid.prepCache3(outputDataFiles[tt]);
                                    if (outputDataFiles[tt] != null)
                                    {
                                        outputFilesToBeCreated.add(outputDataFiles[tt]);
                                    } 
                                    
                                    dsw.stop();
                                    if (DIPERF) System.out.println("WORKER:NotificationThread:execThread:prepCache3(): " + dsw.getElapsedTime() + " ms");
                                    dsw.reset();

                                    dsw.start();
                                    if (responses != null)
                                    {

                                        for (int ttt=0;ttt<responses.length;ttt++)
                                        {

                                            if (responses[ttt].evictedKey != null)
                                            {
                                                if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): responses.evictedKey[ttt] = " + responses[ttt].evictedKey);

                                                job.addCacheEvicted(responses[ttt].evictedKey);
                                            }
                                            else
                                            {
                                                if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): no cache to evict");

                                            }

                                            if (!responses[ttt].success)
                                            {

                                                System.out.println("error in inputData... !responses[ttt].success");
                                                outputDataOK = false;

                                                if (cachingExitCode != -1111 && responses[0].exitCode != 0)
                                                {                  
                                                    cachingExitCode = responses[0].exitCode;

                                                }


                                                failedOutputData = outputDataFiles[tt];
                                                //failedInputData = inputDataFiles[tt];
                                                //failedInputDataURL = inputDataFilesURL[tt];
                                                break;
                                            }

                                            //else
                                            //{
                                            //    outputDataOK = true;

                                            //}

                                        }


                                        //else
                                        //    cachingExitCode = 0;

                                    }
                                    else
                                    {

                                        if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): no cache to evict");
                                    }
                                    dsw.stop();
                                    if (DIPERF) System.out.println("WORKER:NotificationThread:execThread:addCacheEvicted: " + dsw.getElapsedTime() + " ms");
                                    dsw.reset();

                                    }

                                }
                            }
                        }



                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        inputDataOK = false;


                    }
                }
                else
                {
                    if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): if(CACHING == false)");

                    //taskFileName = task.getFileName();
                    //taskFileName = new String("null");
                }
                //if (DEBUG) System.out.println("WORKER:NotificationThread:readThread:doRun(): taskFileName = " + taskFileName);

                sw.stop();
                if (DIPERF) System.out.println("WORKER:NotificationThread:readThread:overallCachingAndStagingIn: " + sw.getElapsedTime() + " ms");
                sw.reset();



                sw.start();
                //get command from task

                //Executable executable = task.getExecutable();
                //invoke exec function!
                //String resultMsg = exec(executable.getCommand(), executable.getArguements(), executable.getEnvironment(), executable.getDirectory());

                //task.setStdout(resultMsg);
                //task.setStderr("");
                //rQ.insert(task);
                if (job.home.CACHING == false || (inputDataOK && outputDataOK && cachingExitCode >= 0))
                {

                    rQ.insert(exec(task));

                    if (DEBUG) System.out.println("Exec task completed...");
                }
                else
                {
                    System.out.println("!@#:job.home.CACHING = " + job.home.CACHING);
                    System.out.println("!@#:inputDataOK = " + inputDataOK);
                    System.out.println("!@#:outputDataOK = " + outputDataOK);
                    System.out.println("!@#:cachingExitCode = " + cachingExitCode);

                    String stderr = new String("");
                    //data not found... return an error code
                    task.setExitCode(cachingExitCode);
                    System.out.println("WORKER:NotificationThread:readThread: failed task due to bad input/output data in data caching, exit code " + cachingExitCode);
                    //task.setExitCode(-999);
                    if (task.isCaptureStderr())
                    {
                        //if (failedInputData == null)
                        //{
                        //    failedInputData = "null";

                        //}
                        //if (failedInputDataURL == null)
                        //{
                        //    failedInputDataURL = "null";

                        //}

                        if (failedInputData != null)
                        {
                            stderr.concat("1. logical input file name " + failedInputData + " not found...\n");
                        }
                        if (failedInputDataURL != null)
                        {
                            stderr.concat("2. input URL " + failedInputDataURL + " might not be valid...\n");
                        }
                        if (failedOutputData != null)
                        {
                            stderr.concat("3. logical output file name " + failedOutputData + " not found...\n");
                        }
                        task.setStderr(stderr);
                    }




                    rQ.insert(task);
                    //if (failedInputData !)
                    //{
                    //}
                    if (DEBUG) System.out.println(stderr);

                }

                //update the index size based on output file names...

                if (job.home.CACHING && outputFilesToBeCreated != null && !outputFilesToBeCreated.isEmpty())
                {

                    try
                    {


                        if (DEBUG) System.out.println("WorkerRun: 6 updating cache size: " + outputFilesToBeCreated.size()+ " entries to be applied...");

                    // For a set or list
                    for (Iterator it=outputFilesToBeCreated.iterator(); it.hasNext(); )
                    {
                        try
                        {
                            String element = (String)it.next();
                            //File file = new File(element);

                            // Get the number of bytes in the file

                            long outFileSize = job.home.cacheGrid.cache.getFileSize(element);
                        job.home.cacheGrid.cache.setCacheContentSize(outFileSize, true);
                        if (DEBUG) System.out.println("WorkerRun: 4 updating cache size: " + element + " "+outFileSize + " bytes");

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                    }
                    outputFilesToBeCreated.clear();
                    //outputFilesToBeCreated = null;
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
                else
                {
                    if (DEBUG) System.out.println("WorkerRun: 5 updating cache size: nothing to update...");

                }



                sw.stop();
                if (DIPERF) System.out.println("WORKER:NotificationThread:readThread:exec(): " + sw.getElapsedTime() + " ms");
                if (DEBUG) System.out.println("Exec task: " + sw.getElapsedTime() + " ms");

                sw.reset();

                job.numTasksCompletedInc();


            }
            //outputFilesToBeCreated = null;

        }
        catch (InterruptedException e)
        {

            throw new Exception(e);
        }

        //in.close();
    }




}


class KeyboardListener extends Thread 
{
    boolean DIPERF = false;
    boolean PROFILING = false;


    String str = null;
    WorkerRunThread worker;
    boolean DEBUG;

    KeyboardListener(WorkerRunThread wr) 
    {
        this.worker = wr;

        this.DEBUG = this.worker.DEBUG;
    }

    public void run()
    {
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            //String str = "";
            //while (str != null) {
            if (DEBUG) System.out.println("WORKER: Press any key and hit enter to terminate this worker!");
            str = in.readLine();

            if (this.worker.shuttingDown.booleanValue() == true)
                return;


            if (DEBUG) System.out.println("Shutting down worker... 3");

            worker.shutDown("6: Interactive user (i.e. a key) has explicitly terminated the worker...");
            // }
        }
        catch (IOException e)
        {

            if (DEBUG) System.out.print("WORKER: Error: " + e);
            e.printStackTrace();

        }
    }

}


class LifetimeListener extends Thread 
{
    boolean DIPERF = false;
    boolean PROFILING = false;


    String str = null;
    WorkerRunThread worker;
    boolean DEBUG;

    LifetimeListener(WorkerRunThread wr) 
    {
        this.worker = wr;

        this.DEBUG = this.worker.DEBUG;
    }

    public void run()
    {
        try
        {

            while (true)
            {
                if (this.worker.shuttingDown.booleanValue() == true)
                    return;


                if (worker.lt.getElapsedTime() >= worker.LIFETIME)
                {

                    break;
                }

                try
                {
                    this.sleep(1000);

                }
                catch (Exception e)
                {
                    if (DEBUG) System.out.print("WORKER: Error, sleep was awoken early: " + e);
                    e.printStackTrace();

                }

            }
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Worker lifetime of " + worker.LIFETIME/1000.0 + " seconds ended after " + worker.lt.getElapsedTime()/1000.0 + " seconds... exiting!");
            if (DEBUG) System.out.println("Shutting down worker... 4");

            worker.shutDown("7: Worker lifetime of " + worker.LIFETIME/1000.0 + " seconds ended after " + worker.lt.getElapsedTime()/1000.0 + " seconds...");
        }
        catch (Exception e)
        {

            if (DEBUG) System.out.print("WORKER: Error: " + e);
            e.printStackTrace();

            //return;
        }
    }

}

/*
class ChildListener extends Thread 
{
    boolean DIPERF = false;
    boolean PROFILING = false;


    //String str = null;
    Process child;
    Task task;
    boolean DEBUG = false;


    String output = new String("");
    String err = new String("");

    ChildListener(Task task, Process child) 
    {
        this.child = child;
        this.task = task;

        //this.DEBUG = this.worker.DEBUG;
    }

    public void run()
    {
        try
        {
            // Get the input stream and read from it
            InputStream in = child.getInputStream();

            int c;
            char cc[] = new char[1];

            if (DEBUG) System.out.println("STDOUT:");
            while ((c = in.read()) != -1)
            {
                //process((char)c);
                cc[0] = (char)c;
                if (DEBUG) System.out.print((char)c);
                output += new String(cc);
            }
            in.close();
            //if (task.isCaptureStdout())
            //    task.setStdout(output);


            InputStream inError = child.getErrorStream();
            //err += new String("\nErrors:\n");
            //String output = new String("");
            //int c;
            //char cc[] = new char[1];
            if (DEBUG) System.out.println("STDERR:");
            while ((c = inError.read()) != -1)
            {
                //process((char)c);
                cc[0] = (char)c;
                if (DEBUG) System.out.print((char)c);
                err += new String(cc);
            }
            inError.close();


            //System.out.println("Exit value: " + child.exitValue());

            //if (DEBUG) System.out.println("STDOUT:");
            //if (DEBUG) System.out.println(output);



            //if (DEBUG) System.out.println("STDERR:");
            //if (DEBUG) System.out.println(err);
            //if (task.isCaptureStderr())
            //{
            //    task.setStderr(err);
            //}


            if (DEBUG) System.out.println("Waiting for command to complete...");



            child.waitFor();

        }
        catch (Exception e)
        {

            if (DEBUG) System.out.print("WORKER: ChildListener(): Error: " + e);
            e.printStackTrace();
            //if (task.isCaptureStdout())
            //{
            //    task.setStdout(output);
            //}
            //else if (task.isCaptureStderr())
            //{
            //    task.setStderr(err);
            //}
        }
    }

}
*/



class IdletimeListener extends Thread 
{
    boolean DIPERF = false;
    boolean PROFILING = false;


    String str = null;
    WorkerRunThread worker;
    boolean DEBUG;

    IdletimeListener(WorkerRunThread wr) 
    {
        this.worker = wr;

        this.DEBUG = this.worker.DEBUG;
    }

    public void run()
    {
        try
        {

            while (true)
            {
                if (this.worker.shuttingDown.booleanValue() == true)
                    return;


                if (worker.idleTime.getElapsedTime() >= worker.IDLETIME)
                {

                    break;
                }

                try
                {
                    this.sleep(1000);

                }
                catch (Exception e)
                {
                    if (DEBUG) System.out.print("WORKER: Error, sleep was awoken early: " + e);
                    e.printStackTrace();

                }

            }
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Worker idletime of " + worker.IDLETIME/1000.0 + " seconds ended after " + worker.idleTime.getElapsedTime()/1000.0 + " seconds... exiting!");
            if (DEBUG) System.out.println("Shutting down worker... 5");

            worker.shutDown("8: Worker idletime of " + worker.IDLETIME/1000.0 + " seconds ended after " + worker.idleTime.getElapsedTime()/1000.0 + " seconds...");
        }
        catch (Exception e)
        {

            if (DEBUG) System.out.print("WORKER: Error: " + e);
            e.printStackTrace();

            //return;
        }
    }

}


class KillListener extends Thread 
{
    boolean DIPERF = false;
    boolean PROFILING = false;


    String str = null;
    WorkerRunThread worker;
    boolean DEBUG;
    String DATA_TRACE;

    KillListener(WorkerRunThread wr) 
    {
        this.worker = wr;

        this.DEBUG = this.worker.DEBUG;
        this.DATA_TRACE = this.worker.DATA_TRACE;
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
                                                         if (worker.shuttingDown.booleanValue() == true)
                                                             return;
                                                         // Your code goes here.
                                                         System.out.println("Shutting down == false...");

                                                         System.out.println("Worker died unexpectedly... perhaps the ctrl-c was pressed, or the kill signal was received... exiting!");
                                                         //shutDown("Worker died unexpectedly...");
                                                         //System.out.println("Shutdown complete!");

                                                         //System.out.println("Stoping all threads...");
                                                         //ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
                                                         //root.stop();



                                                         if (DEBUG) System.out.println("Shutting down worker... -99999");

                                                         worker.shutDown("-99999: Worker shutdown after " + worker.lt.getElapsedTime()/1000.0 + " seconds...");

                                                         //System.exit(-1);
                                                         System.out.println("ShutdownHook triggered successfully!");
                                                          

                                                     }
                                                 });



        }
        catch (Exception e)
        {

            if (DEBUG) System.out.print("WORKER: Error: " + e);
            e.printStackTrace();

            //return;
        }
    }

}


