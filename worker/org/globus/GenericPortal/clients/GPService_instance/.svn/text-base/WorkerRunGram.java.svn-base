//SVN v0.8.1
/*
 * This file is licensed under the terms of the Globus Toolkit Public License
 * v3, found at http://www.globus.org/toolkit/legal/4.0/license-v3.html.
 * 
 * This notice must appear in redistributions of this file, with or without
 * modification.
 */
package org.globus.GenericPortal.clients.GPService_instance;


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


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

//I am not sure this is correct... 
//import nom.tam.fits.*;
//import nom.tam.util.*;
//import nom.tam.image.ImageTiler;

//import JNI.FunTools.*;


import java.io.BufferedWriter;
import java.io.FileWriter;

import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.GenericPortal.services.core.WS.impl.GPConstants;
import org.globus.GenericPortal.stubs.Factory.CreateResource;
import org.globus.GenericPortal.stubs.Factory.CreateResourceResponse;
import org.globus.GenericPortal.stubs.Factory.FactoryPortType;
import org.globus.GenericPortal.stubs.Factory.service.FactoryServiceAddressingLocator;
import org.globus.wsrf.encoding.ObjectSerializer;


class PBSQueue 
{
    int Q;
    int R;
    int H;
    int C;

    PBSQueue()
    {
        this.Q = 0;
        this.R = 0;
        this.H = 0;
        this.C = 0;
    }
}

public class WorkerRunGram
{

    public static final String WORKER_GRAM_VERSION = "v0.2.0";

    public Map provisionerConf = Collections.synchronizedMap(new HashMap());

    public int provisionerConfigInterval = 60; //default value in sec
    public String provisionerConfigFile = null;

    public String drpLogFileName = new String("drp-status.txt");

    public synchronized boolean readProvConfig() throws Exception
    {

        StopWatch sw = new StopWatch();
        sw.start(); 
        if (DEBUG) System.out.println("readProvisionerConfig()...");


        try
        {
            BufferedReader in = new BufferedReader(new FileReader(provisionerConfigFile));

            String str;
            while ((str = in.readLine()) != null)
            {
                if (str.startsWith("#"))
                {
                    if (DEBUG) System.out.println("readProvisionerConfig(): ignoring comment " + str);

                }
                else
                {


                    String tokens[] = str.split("=");
                    if (tokens.length == 2)
                    {
                        provisionerConf.put(tokens[0], tokens[1]);


                    }

                    else
                    {

                        if (DEBUG) System.out.println("readProvisionerConfig(): reading Provisioner config file error at " + str + "... ignoring");
                    }
                }

            }

            interpretProvisionerConfig();
            in.close();

            sw.stop();
            if (DIPERF) System.out.println(System.currentTimeMillis() + ": WORKERS:readProvisionerConfig(): " + sw.getElapsedTime() + " ms");
            sw.reset();
            return true;

        }
        catch (IOException e)
        {
            System.out.println("readProvisionerConfig(): reading Provisioner config file " + provisionerConfigFile + " failed: " + e);
            e.printStackTrace();
            return false;


        }
        //return false;
    }

    public boolean readProvisionerConfig()
    {

        //Properties props = System.getProperties();
        //String provisionerConfigFile = (String)props.get("ProvisionerConfig");

        try
        {




            if (provisionerConfigFile == null)
            {
                return false;
            }
            else
            {


                readProvConfig();



                try
                {
                    URL serviceURL = new URL(serviceURI);
                    InetAddress addr = InetAddress.getByName(serviceURL.getHost());
                    String serviceIP = addr.getHostAddress();

                    BufferedWriter out = new BufferedWriter(new FileWriter(falkonServiceFileName));
                    out.write(serviceIP + "\n");
                    out.flush();
                    out.close();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                int period = provisionerConfigInterval*1000;  // repeat every sec.
                int delay = period;   // delay for 5 sec.
                final Timer timer = new Timer();



                timer.scheduleAtFixedRate(new TimerTask()
                                          {
                                              public void run()
                                              {
                                                  try
                                                  {

                                                      if (readProvConfig() == false)
                                                          timer.cancel();


                                                  }
                                                  catch (Exception e)
                                                  {
                                                      e.printStackTrace();
                                                  }

                                              }
                                          }, delay, period);


            }

            return true;

        }
        catch (Exception eee)
        {
            eee.printStackTrace();
            return false;
        }

    }



    final Object _MIN_NUM_EXECUTORS = "MinNumExecutors";
    final Object _MAX_NUM_EXECUTORS = "MaxNumExecutors";
    final Object _EXECUTORS_PER_HOST = "ExecutorsPerHost";
    final Object _MIN_RESOURCE_ALLOCATION_TIME_MIN = "MinResourceAllocationTime_min";
    final Object _MAX_RESOURCE_ALLOCATION_TIME_MIN = "MaxResourceAllocationTime_min";
    final Object _HOST_TYPE = "HostType";
    final Object _ALLOCATION_STRATEGY = "AllocationStrategy";
    final Object _MIN_NUM_HOSTS_PER_ALLOCATION = "MinNumHostsPerAllocation";
    final Object _MAX_NUM_HOSTS_PER_ALLOCATION = "MaxNumHostsPerAllocation";
    final Object _DEALLOCATION_IDLE_TIME = "DeAllocationIdleTime_sec";
    final Object _FALKON_SERVICE_URI = "FalkonServiceURI";
    final Object _EPR_FILE_NAME = "EPR_FileName";
    final Object _FALKON_SERVICE_NAME_FILE_NAME = "FalkonServiceName_FileName";
    final Object _FALKON_STATE_POLL_TIME_SEC = "FalkonStatePollTime_sec";
    final Object _GRAM4_LOCATION = "GRAM4_Location";
    final Object _GRAM4_FACTORY_TYPE = "GRAM4_FactoryType";
    final Object _PROJECT = "Project";
    final Object _EXECUTOR_SCRIPT = "ExecutorScript";
    final Object _FALKON_HOME = "FALKON_HOME";
    final Object _SECURITY_FILE = "SecurityFile";
    final Object _DEBUG = "DEBUG";
    final Object _DIPERF = "DIPERF";
    final Object _DRP_LOG_FILE_NAME = "DRP_Log";

    final Object _EMULATED = "Emulated";
    //drpLogFileName

    public String allocationStrategy = "one-at-a-time";
    public String GRAM4_factory_type = "PBS"; //

    public int minNumHostsPerAllocation = 1;
    public int maxNumHostsPerAllocation = 1;

    public void interpretProvisionerConfig()
    {
        String sTemp = null;

        sTemp = (String)provisionerConf.get(_FALKON_SERVICE_URI);
        if (sTemp != null)
        {
            serviceURI = new String(sTemp);
        }

        sTemp = (String)provisionerConf.get(_EPR_FILE_NAME);
        if (sTemp != null)
        {
            try
            {

                File file = new File(sTemp);
                fileEPR = new String(file.getCanonicalPath());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        sTemp = (String)provisionerConf.get(_FALKON_SERVICE_NAME_FILE_NAME);
        if (sTemp != null)
        {
            try
            {

                File file = new File(sTemp);
                falkonServiceFileName = new String(file.getCanonicalPath());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


        sTemp = (String)provisionerConf.get(_MAX_RESOURCE_ALLOCATION_TIME_MIN);
        if (sTemp != null)
        {
            maxWallTime = Integer.parseInt(sTemp); //in minutes
        }


        sTemp = (String)provisionerConf.get(_MIN_RESOURCE_ALLOCATION_TIME_MIN);
        if (sTemp != null)
        {
            minWallTime = Integer.parseInt(sTemp); //in minutes
        }


        sTemp = (String)provisionerConf.get(_DEALLOCATION_IDLE_TIME);
        if (sTemp != null)
        {
            idleTime = Integer.parseInt(sTemp); //in minutes
        }


        sTemp = (String)provisionerConf.get(_FALKON_STATE_POLL_TIME_SEC);
        if (sTemp != null)
        {
            pollTime = Long.parseLong(sTemp)*1000; //in minutes
        }


        sTemp = (String)provisionerConf.get(_MIN_NUM_EXECUTORS);
        if (sTemp != null)
        {
            minAllocatedWorkers = Integer.parseInt(sTemp);
        }


        sTemp = (String)provisionerConf.get(_EXECUTORS_PER_HOST);
        if (sTemp != null)
        {
            MAX_NUM_WORKERS_PER_HOST = Integer.parseInt(sTemp); 
        }

        sTemp = (String)provisionerConf.get(_FALKON_HOME);
        if (sTemp != null)
        {
            try
            {

                falkon_home = new String(sTemp);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        else
        {
            Properties props = System.getProperties();
            falkon_home = (String)props.get("FALKON_HOME");
            if (falkon_home == null)
            {
                //set to some default...
                System.out.println("Warning, ${FALKON_HOME} is not set, you must include -DFALKON_HOME=${FALKON_HOME} when starting the JVM, or set it in the Provisioner.config...  things might not work due to relative path names...");

            }
        }


        sTemp = (String)provisionerConf.get(_EXECUTOR_SCRIPT);
        if (sTemp != null)
        {
            try
            {
                if (falkon_home == null)
                {


                    File file = new File(sTemp);
                    executable = new String(file.getCanonicalPath());
                }
                else
                {
                    executable = new String(falkon_home+"/worker/"+sTemp);

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }





        sTemp = (String)provisionerConf.get(_PROJECT);
        if (sTemp != null)
        {
            project = new String(sTemp);
        }


        sTemp = (String)provisionerConf.get(_HOST_TYPE);
        if (sTemp != null)
        {
            hostType = new String(sTemp);
        }


        sTemp = (String)provisionerConf.get(_MAX_NUM_EXECUTORS);
        if (sTemp != null)
        {
            hostCount = Integer.parseInt(sTemp); //in minutes
        }


        sTemp = (String)provisionerConf.get(_GRAM4_LOCATION);
        if (sTemp != null)
        {
            contact = new String(sTemp);
        }


        sTemp = (String)provisionerConf.get(_DEBUG);
        if (sTemp != null)
        {
            if (sTemp.contentEquals(new StringBuffer("true")))
            {
                DEBUG = true;
            }
            else
            {
                DEBUG = false;
            }
            //DEBUG = Boolean.parseBoolean(sTemp);
        }


        sTemp = (String)provisionerConf.get(_DIPERF);
        if (sTemp != null)
        {
            if (sTemp.contentEquals(new StringBuffer("true")))
            {
                DIPERF = true;
            }
            else
            {
                DIPERF = false;
            }

            //DIPERF = Boolean.parseBoolean(sTemp);
        }


        sTemp = (String)provisionerConf.get(_EMULATED);
        if (sTemp != null)
        {
            if (sTemp.contentEquals(new StringBuffer("true")))
            {
                EMULATED = true;
            }
            else
            {
                EMULATED = false;
            }

            //DIPERF = Boolean.parseBoolean(sTemp);
        }


        sTemp = (String)provisionerConf.get(_SECURITY_FILE);
        if (sTemp != null)
        {

            try
            {

                File file = new File(sTemp);
                CLIENT_DESC = new String(file.getCanonicalPath());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        sTemp = (String)provisionerConf.get(_DRP_LOG_FILE_NAME);
        if (sTemp != null)
        {
            try
            {
                //Properties props = System.getProperties();
                //String FALKON_LOGS = (String)props.get("FALKON_LOGS");


                //File file = new File(sTemp);
                //if (FALKON_LOGS != null) 
                //{

                //drpLogFileName = new String(FALKON_LOGS+"/"+sTemp);//new String(file.getCanonicalPath());
                //}
                //else
                drpLogFileName = sTemp;

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }


        sTemp = (String)provisionerConf.get(_ALLOCATION_STRATEGY);
        if (sTemp != null)
        {
            allocationStrategy = new String(sTemp);
        }


        sTemp = (String)provisionerConf.get(_GRAM4_FACTORY_TYPE);
        if (sTemp != null)
        {
            GRAM4_factory_type = new String(sTemp);
        }


        sTemp = (String)provisionerConf.get(_MIN_NUM_HOSTS_PER_ALLOCATION);
        if (sTemp != null)
        {
            minNumHostsPerAllocation = Integer.parseInt(sTemp);
        }

        sTemp = (String)provisionerConf.get(_MAX_NUM_HOSTS_PER_ALLOCATION);
        if (sTemp != null)
        {
            maxNumHostsPerAllocation = Integer.parseInt(sTemp);
        }

    }


    public static String CLIENT_DESC = null;

/*
    static boolean AUTHENTICATE = false;
    static boolean AUTHORIZE = false;
    static boolean ENCRYPT = false;
    static boolean SIGN = false;
    static boolean TSL = false;
    static boolean MSG = false;
    static boolean CONV = false;
  */
    static {
        Util.registerTransport();
    }

    // TODO this shouldn't be a hardcoded path
    public String executable = "";
    public String falkon_home = "";
    public String project = "default";
    public int maxWallTime = 60*1*24; //in min
    public int minWallTime = 60*1*24; //in min
    public int idleTime = 600000; //in ms
    public long pollTime = 1000; //in ms
    public String hostType = "ia32-compute";
    public int hostCount = 48;
    public String contact = "tg-grid1.uc.teragrid.org";

    public String machID;

    public boolean DEBUG;
    public boolean DIPERF;

    public boolean EMULATED=false;


    public int activeNotifications; //# of concurent notifications
    public int MAX_NUM_NOTIFICATIONS;
    public int activeThreads;   //# of concurent threads accross all the jobs
    public int MAX_NUM_THREADS;
    public int queueLength; //# of individual tasks (image ROI) scheduled to take place

    public int LIFETIME;    //time in ms that the worker should accept work
    //public int LIFETIME_STACK;  //time in ms that the worker should accept work

    //public Thread                              notificationThread;

    public Notification workerNot;
    private int SO_TIMEOUT;


    public String fileEPR = null;
    public String falkonServiceFileName = null;
    public String serviceURI;
    public EndpointReferenceType homeEPR;
    public String homeURI;

    public Random rand;

    public String scratchDisk;

    GPPortType ap;


    WorkQueue threadQ;
    StopWatch lt;
    public int jobID;
    public boolean INTERACTIVE;
    public boolean STANDALONE;
    public String DESC_FILE;
    public int JOB_SIZE;

    public int NUM_JOBS;
    public int ROI_Height;
    public int ROI_Width;



    public BufferedReader inBufRead = null;


    public int numTasksFailed = 0;
    //public int numImagesStacked = 0;
    public Random randIndex;

    public String[] data;

    long elapsedTime;

    public int allocatedWorkers = 0;
    public int minAllocatedWorkers = 0;
    public int MAX_NUM_WORKERS_PER_HOST = 1;


    //public EndpointReferenceType notificationEPR;



    //constructor
    public WorkerRunGram()
    {
        this.ROI_Height = 100;
        this.ROI_Width = 100;
        this.randIndex = new Random();

        this.machID = "localhost:0";
        this.scratchDisk = "";

        this.rand = new Random();
        this.DEBUG = false;
        this.DIPERF = false;
        this.activeNotifications = 0;
        this.activeThreads = 0;
        this.MAX_NUM_THREADS = 10;
        this.MAX_NUM_NOTIFICATIONS = 1000000; //some large #, if setting lower, there needs a way to enforce it...
        this.queueLength = 0;
        this.SO_TIMEOUT = 0; //receive will block forever...
        this.LIFETIME = 0;
        //this.LIFETIME_STACK = 0;

        this.workerNot = new Notification(this.SO_TIMEOUT, this.DEBUG);
        if (DEBUG) if (DIPERF == false) System.out.println("Notification initialized on port: " + this.workerNot.recvPort);
        this.threadQ = new WorkQueue();
        this.lt = new StopWatch();
        this.jobID = 0;
        this.INTERACTIVE = false;
        this.elapsedTime = 0;
        this.STANDALONE = false;


    }

    public long getElapsedTime()
    {
        return elapsedTime;

    }


    public void incElapsedTime(long t)
    {
        elapsedTime += t;

    }

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

            machName = java.net.InetAddress.getLocalHost().getHostName();
            machName = machName +  ":" + workerNot.recvPort;
            //machIP = java.net.InetAddress.getLocalHost().getHostAddress();
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Machine ID = " + machName);

        }
        catch (Exception e)
        {
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: ERROR: java.net.InetAddress.getLocalHost().getHostName() failed " + e);
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

            machName = java.net.InetAddress.getLocalHost().getHostName();
            machName = machName +  ":" + tPID;
            //machIP = java.net.InetAddress.getLocalHost().getHostAddress();
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Machine ID = " + machName);

        }
        catch (Exception e)
        {
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: ERROR: java.net.InetAddress.getLocalHost().getHostName() failed " + e);
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
            GPPortType ap = instanceLocator
                    .getGPPortTypePort(instanceEPR);
                        sw.stop();
                        if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Get ProtType (" + sw.getElapsedTime() + "ms)");
                        sw.reset();

            // Perform registration
//			ap.workerRegistration(machID);
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
                                    ap.workerRegistration(machID);
                                    sw.stop();
                                    if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Performed one time Worker registration (" + sw.getElapsedTime() + "ms)");
                                    sw.reset();

                                    // Print resource properties
                                    printResourceProperties(ap);

                                    isRegistered = true;
                                }


                                //Thread.sleep(1000);
                            

                                /*
                                while (workerWorkAvailable == false) 
                                {
                                
                                    sw.start();
                                    // Perform status
                                    workerWorkAvailable = getWorkerWorkAvailable(ap);
                                    //ap.status(0);
                                    sw.stop();
                                    if (DEBUG) if (DIPERF == false) System.out.println("WORKER: getWorkerWorkAvailable (" + sw.getElapsedTime() + "ms)");
                                    sw.reset();

                                    // Print resource properties

                                    //printResourceProperties(ap);

                                    //this should be replaced by some notification mechanism
                                    Thread.sleep(pollStep);
                                } * /

                                //this call blocks until a notification is received
                                waitForNotification();
                                

                                // get work
                                sw.start();
                                //ap.status(0);
                                ap.workerWork(0);
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
                                ap.workerResult(0);
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

    public String FALKON_WORKER_HOME = null;

    public void parseArgs(String[] args) throws Exception
    {
        if (args.length < 1)
        {
            usage(args);
            return;
        }

        Properties props = System.getProperties();
        FALKON_WORKER_HOME = (String)props.get("FALKON_WORKER_HOME");
        if (FALKON_WORKER_HOME == null)
        {
            FALKON_WORKER_HOME = new String("");
            System.out.println("Warning, ${FALKON_WORKER_HOME} is not set, you must include -DFALKON_WORKER_HOME=${FALKON_WORKER_HOME} when starting the JVM...  things might not work due to relative path names...");

        }


        int ctr;
        for (ctr = 0; ctr < args.length; ctr++)
        {
            if (args[ctr].equals("-serviceURI") && ctr + 1 < args.length)
            {
                ctr++;
                serviceURI = new String(args[ctr]);
                //System.out.println("Setting serviceURI to " + serviceURI);
            }

            else if (args[ctr].equals("-config_file") && ctr + 1 < args.length)
            {
                ctr++;


                provisionerConfigFile = new String(args[ctr]);
            }
            else if (args[ctr].equals("-config_poll") && ctr + 1 < args.length)
            {
                ctr++;


                provisionerConfigInterval = Integer.parseInt(args[ctr]);
            }
            else if (args[ctr].equals("-epr") && ctr + 1 < args.length)
            {
                ctr++;


                fileEPR = new String(args[ctr]);
                boolean exists = (new File(fileEPR)).exists();
                if (exists)
                {
                    //OK
                }
                else
                {
                    throw new Exception("File '" + fileEPR + "' does not exist.");
                }
            }
            else if (args[ctr].equals("-maxWallTime") && ctr + 1 < args.length)
            {
                ctr++;
                maxWallTime = Integer.parseInt(args[ctr]); //in minutes
            }

            else if (args[ctr].equals("-minWallTime") && ctr + 1 < args.length)
            {
                ctr++;
                minWallTime = Integer.parseInt(args[ctr]); //in minutes
            }
            else if (args[ctr].equals("-idleTime") && ctr + 1 < args.length)
            {
                ctr++;
                idleTime = Integer.parseInt(args[ctr]); //in minutes
            }
            else if (args[ctr].equals("-pollTime") && ctr + 1 < args.length)
            {
                ctr++;
                pollTime = Long.parseLong(args[ctr]); //in minutes
            }
            else if (args[ctr].equals("-minHostCount") && ctr + 1 < args.length)
            {
                ctr++;
                minAllocatedWorkers = Integer.parseInt(args[ctr]); 
            }
            else if (args[ctr].equals("-MAX_NUM_WORKERS_PER_HOST") && ctr + 1 < args.length)
            {
                ctr++;
                MAX_NUM_WORKERS_PER_HOST = Integer.parseInt(args[ctr]); 
            }
            else if (args[ctr].equals("-executable") && ctr + 1 < args.length)
            {
                ctr++;
                executable = new String(args[ctr]);
            }
            else if (args[ctr].equals("-project") && ctr + 1 < args.length)
            {
                ctr++;
                project = new String(args[ctr]);
            }

            else if (args[ctr].equals("-hostType") && ctr + 1 < args.length)
            {
                ctr++;
                hostType = new String(args[ctr]);
            }
            else if (args[ctr].equals("-hostCount") && ctr + 1 < args.length)
            {
                ctr++;
                hostCount = Integer.parseInt(args[ctr]); //in minutes
            }
            else if (args[ctr].equals("-contact") && ctr + 1 < args.length)
            {
                ctr++;
                contact = new String(args[ctr]);
            }
            else if (args[ctr].equals("-debug"))//&& ctr + 1 < args.length)
            {
                DEBUG = true;
            }
            else if (args[ctr].equals("-diperf"))//&& ctr + 1 < args.length)
            {
                DIPERF = true;

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
                if (DEBUG) if (DIPERF == false) System.out.println("Help Screen:");
                usage(args);

            }
            else
            {

                if (DIPERF == false)
                {
                    System.out.println("ERROR: invalid parameter - " + args[ctr] + " at arguement " + ctr + " of " + args.length + " arguements");
                    System.out.println("Current parameters values:");
                    System.out.println("-serviceURI " + serviceURI);
                    System.out.println("-maxWallTime " + maxWallTime);
                    System.out.println("-executable " + executable);
                    System.out.println("hostType " + hostType);
                    System.out.println("hostCount " + hostCount);
                    System.out.println("contact " + contact);
                    System.out.println("-debug " + DEBUG);
                    System.out.println("-diperf " + DIPERF);
                }

                usage(args);
            }
        }

    }

    public void usage(String[] args)
    {
        if (DIPERF == false)
        {
            System.out.println("Help Screen: ");
            System.out.println("-diperf <>");
            System.out.println("-help <>");
        }
        System.exit(0);

    }

    public EndpointReferenceType getEPR(ResourceKey key) throws Exception
    {
        String instanceURI = homeEPR.getAddress().toString();
        return(EndpointReferenceType) AddressingUtils.createEndpointReference(instanceURI, key);
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
            throw new Exception("homeEPR == null, probably EPR was not correctly read from file");
        }
        else
        {

            GPServiceAddressingLocator instanceLocator = new GPServiceAddressingLocator();
            return instanceLocator.getGPPortTypePort(homeEPR);
        }

    }

    public boolean createWorkerResource(String factoryURI, String eprFilename)
    {

        //static final Object EPR_FILENAME = "epr.txt";
        if (DEBUG) System.out.println("createWorkerResource() started... ");
        FactoryServiceAddressingLocator factoryLocator = new FactoryServiceAddressingLocator();

        try
        {
            //String factoryURI = args[0];
            //String eprFilename;

            //if (args.length == 2)
            //	eprFilename = args[1];
            //else
            //	eprFilename = EPR_FILENAME;

            EndpointReferenceType factoryEPR, instanceEPR;
            FactoryPortType apFactory;

            // Get factory portType
            if (DEBUG) System.out.println("createWorkerResource(): new EndpointReferenceType()... ");
            factoryEPR = new EndpointReferenceType();
            if (DEBUG) System.out.println("createWorkerResource(): factoryEPR.setAddress(new Address(factoryURI))... ");
            factoryEPR.setAddress(new Address(factoryURI));
            if (DEBUG) System.out.println("createWorkerResource(): factoryLocator.getFactoryPortTypePort(factoryEPR)... ");
            apFactory = factoryLocator.getFactoryPortTypePort(factoryEPR);


            if (CLIENT_DESC != null && (new File(CLIENT_DESC)).exists())
            {
                if (DEBUG) System.out.println("Setting appropriate security from file '" + CLIENT_DESC + "'!");
                ((Stub)apFactory)._setProperty(Constants.CLIENT_DESCRIPTOR_FILE, CLIENT_DESC);


            }

            // Create resource and get endpoint reference of WS-Resource
            if (DEBUG) System.out.println("createWorkerResource(): apFactory.createResource(new CreateResource())... ");
            CreateResourceResponse createResponse = apFactory.createResource(new CreateResource());
            if (DEBUG) System.out.println("createWorkerResource(): createResponse.getEndpointReference()... ");
            instanceEPR = createResponse.getEndpointReference();

            if (DEBUG) System.out.println("createWorkerResource(): ObjectSerializer.toString(instanceEPR,GPConstants.RESOURCE_REFERENCE)... ");
            String endpointString = ObjectSerializer.toString(instanceEPR,GPConstants.RESOURCE_REFERENCE);

            if (DEBUG) System.out.println("createWorkerResource(): new FileWriter(eprFilename)... ");
            FileWriter fileWriter = new FileWriter(eprFilename);
            if (DEBUG) System.out.println("createWorkerResource(): new BufferedWriter(fileWriter)... ");
            BufferedWriter bfWriter = new BufferedWriter(fileWriter);
            if (DEBUG) System.out.println("createWorkerResource(): bfWriter.write(endpointString)... ");
            bfWriter.write(endpointString);
            if (DEBUG) System.out.println("createWorkerResource(): bfWriter.close()... ");
            bfWriter.close();
            System.out.println("Endpoint reference written to file "
                               + eprFilename);
            if (DEBUG) System.out.println("createWorkerResource() ended... ");
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean readWorkerResource(String fileEPR) throws Exception
    {


        //fileEPR = new String(args[ctr]);
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
                throw new Exception("parseArgs(): homeEPR == null, probably EPR was not correctly read from file");
            }
            else
            {

                if (DEBUG) if (DIPERF == false) System.out.println("parseArgs(): homeEPR is set correctly");
            }

            fis.close();
            return true;
        }
        else
        {
            //throw new Exception("File '" + fileEPR + "' does not exist.");
            return false;

        }


        //fileEPR = new String(args[ctr]);
        //FileInputStream fis = new FileInputStream(fileEPR);

        //homeEPR = (EndpointReferenceType) ObjectDeserializer.deserialize(new InputSource(fis),EndpointReferenceType.class);
        //homeEPR = getEPR(fis);

        //fis.close();
        //}
        //must implement properly as a new resource would have to be created :(

    }

    public synchronized void incAllocatedWorkers(int i)
    {
        if (DEBUG) System.out.println("Current allocated workers: " + allocatedWorkers);
        allocatedWorkers += i;
        if (DEBUG) System.out.println("New allocated workers: " + allocatedWorkers);

    }

    public synchronized void decAllocatedWorkers(int i)
    {
        allocatedWorkers -= i;

    }

    public synchronized int getAllocatedWorkers()
    {
        return allocatedWorkers;

    }


    public synchronized void setAllocatedWorkers(int n)
    {
        allocatedWorkers = n;
    }


    private void getUserResultAvailable(GPPortType ap) throws Exception
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

        System.out.println("Retrieving GPWS_USER status...");
        StatusUser stat = new StatusUser("");
        StatusUserResponse sur = ap.statusUser(stat);
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
            System.out.println("State was not valid, trying again...");
        }


        System.out.println("queuedTask=" + queuedTask + " activeTask=" + activeTask + " completedTasks=" + completedTasks + " GPWS_queuedTask=" + GPWS_qLength + " GPWS_activeTasks=" + GPWS_activeTasks + " GPWS_activeResources=" + GPWS_numWorkers);
    }


    public int getNewAllocation(int AllocatedWorkers, int RegisteredWorkers, int FreeWorkers, int BusyWorkers, int newWorkers, int oldWorkers, int minWorkers, int maxWorkers, int workersPerNode, int queueLength)
    {
        return(int)Math.max(0, Math.ceil(Math.max(Math.min(Math.max(Math.max(minWorkers - AllocatedWorkers, 0), queueLength - FreeWorkers), Math.max(maxWorkers - (AllocatedWorkers), 0)), 0)*1.0/workersPerNode)-Math.max(0, AllocatedWorkers - RegisteredWorkers));
    }



    public static String comArgsQ[] = new String[] {"bash", "-c", "qstat | grep iraicu | grep Q | wc -l"};
    public static String comArgsR[] = new String[] {"bash", "-c", "qstat | grep iraicu | grep R | wc -l"};
    public static String comArgsH[] = new String[] {"bash", "-c", "qstat | grep iraicu | grep H | wc -l"};
    public static String comArgsC[] = new String[] {"bash", "-c", "qstat | grep iraicu | grep C | wc -l"};

    //public static String comArgsQ = new String("bash -c qstat | grep iraicu | grep Q | wc -l");
    //public static String comArgsR = new String("bash -c qstat | grep iraicu | grep R | wc -l");
    //public static String comArgsH = new String("bash -c qstat | grep iraicu | grep H | wc -l");
    //public static String comArgsC = new String("bash -c qstat | grep iraicu | grep C | wc -l");

    public PBSQueue getPbsState(boolean DEBUG2)
    {
        PBSQueue pbsState = new PBSQueue();

        try
        {


            //qstat | grep iraicu | grep Q | wc -l
            //qstat | grep iraicu | grep R | wc -l
            //qstat | grep iraicu | grep H | wc -l
            MyProcess myChild = new MyProcess(DEBUG2, true);


            //String comArgs = new String("qstat | grep iraicu | grep Q | wc -l");
            int exitCode = myChild.exec(comArgsQ);
            if (exitCode == 0)
                if (DEBUG2) System.out.println("Execution of task succesful: exitCode = " + exitCode);
                else
                {
                    if (DEBUG2) System.out.println("Execution of task failed: exitCode = " + exitCode);
                    if (DEBUG2) System.out.println("Error stream: " + myChild.getStreamErr() +"\n Output stream: " + myChild.getStreamOut() );
                }
            pbsState.Q = Integer.parseInt(myChild.getStreamOutFirstLine());

            //comArgs[6] = "R";
            exitCode = myChild.exec(comArgsR);
            if (exitCode == 0)
                if (DEBUG2) System.out.println("Execution of task succesful: exitCode = " + exitCode);
                else
                {
                    if (DEBUG2) System.out.println("Execution of task failed: exitCode = " + exitCode);
                    if (DEBUG2) System.out.println("Error stream: " + myChild.getStreamErr() +"\n Output stream: " + myChild.getStreamOut() );
                }

            pbsState.R = Integer.parseInt(myChild.getStreamOutFirstLine());

            //comArgs[6] = "H";
            exitCode = myChild.exec(comArgsH);
            if (exitCode == 0)
                if (DEBUG2) System.out.println("Execution of task succesful: exitCode = " + exitCode);
                else
                {
                    if (DEBUG2) System.out.println("Execution of task failed: exitCode = " + exitCode);
                    if (DEBUG2) System.out.println("Error stream: " + myChild.getStreamErr() +"\n Output stream: " + myChild.getStreamOut() );
                }
            pbsState.H = Integer.parseInt(myChild.getStreamOutFirstLine());

            //comArgs[6] = "C";
            exitCode = myChild.exec(comArgsC);
            if (exitCode == 0)
                if (DEBUG2) System.out.println("Execution of task succesful: exitCode = " + exitCode);
                else
                {
                    if (DEBUG2) System.out.println("Execution of task failed: exitCode = " + exitCode);
                    if (DEBUG2) System.out.println("Error stream: " + myChild.getStreamErr() +"\n Output stream: " + myChild.getStreamOut() );
                }
            pbsState.C = Integer.parseInt(myChild.getStreamOutFirstLine());


        }
        catch (Exception e)
        {
            if (DEBUG2) System.out.println("Exception in getPbsState(): " + e.getMessage());
            //return null;

        }

        return pbsState;

    }

    private int workerID = 0;
    public synchronized String getWorkerName()
    {
        workerID++;
        return new String("emulated.executor:"+workerID);
    }

    public boolean incurRandomDelay(int maxDelay)
    {
        try
        {
            Thread.sleep(rand.nextInt(maxDelay));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean EmulatedSubmit(int wallTime, int hostsNum)
    {

        incurRandomDelay(30000);

        for (int i=0;i<hostsNum;i++)
        {
            incurRandomDelay(1000);


            //WorkerRegistration wreg = new WorkerRegistration();
            //wreg.setMachID(getWorkerName());
            //wreg.setWallTime(wallTime*60);

            try
            {

                //if (logger.isDebugEnabled()) System.out.println("GPResourceHome(): reading Emulation configuration...: starting worker " + i + " of EMULATION_NUM_EXECUTORS");
                WorkerRegistration wreg = new WorkerRegistration();
                wreg.setMachID(getWorkerName());
                wreg.setWallTime(wallTime*60*1000);

                WorkerRegistrationResponse wrr = this.ap.workerRegistration(wreg);

                if (!wrr.isValid())
                {
                    return false;
                }
                //else
                //{
                //    return false;
                //}



                //if (commonState.registerWorker(getWorkerName(), wallTime*60))
                //    return true;
                //else
                //    return false;
                //this.gpResource.registerWorker(wreg);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }



    boolean one_at_a_time = true;
    boolean additive = false;
    boolean exponential = false;
    boolean all_at_a_time = false;

    public void main_run(String[] args) throws Exception
    {
        if (DEBUG) System.out.println("WORKERS-GRAM: "+WORKER_GRAM_VERSION);


        StopWatch sw = new StopWatch();
        lt.start();

        //sw.start();
        //parseArgs(args);
        //sw.stop();
        //if(DEBUG) System.out.println("WORKER: Parse Arguements and Setup Corresponding State (" + sw.getElapsedTime() + "ms)");
        //sw.reset();


        //sw.start();
        //machID = getMachNamePort(); 
        //sw.stop();
        //if (DEBUG)
        //{
        //    System.out.println("WORKER: Get Machine Name (" + sw.getElapsedTime() + "ms)");
        //}

        //if (DIPERF) System.out.println("WORKER:getMachNamePort(): " + sw.getElapsedTime() + " ms");

        //if (DIPERF == false)
        //{
        //    System.out.println("Worker " + machID + " started succesful!");
        //}
        //sw.reset();

        if (fileEPR == null)
            fileEPR = "WorkerEPR.txt";


        if (DEBUG) System.out.println("WORKERS: Creating worker resource...");
        if (createWorkerResource(serviceURI, fileEPR))
        {
            if (DEBUG) System.out.println("WORKERS: Created worker resource... saved in '" + fileEPR + "'");

        }
        else
            throw new Exception("main_run(): createWorkerResource() failed, could not create worker resource...");

        if (readWorkerResource(fileEPR))
        {
            if (DEBUG) System.out.println("WORKERS: initialized worker reasource state from '" + fileEPR + "'");

        }
        else
            throw new Exception("main_run(): readWorkerResource() failed, could not read worker resource from '" + fileEPR + "'");






        if (homeEPR == null)
        {
            throw new Exception("main_run(): homeEPR == null, something is wrong");
        }
        else
        {

            if (DEBUG) System.out.println("main_run(): homeEPR is OK");
        }

        sw.start();
        // Get PortType
        ap = getGPPortType();//= instanceLocator


        if (CLIENT_DESC != null && (new File(CLIENT_DESC)).exists())
        {
            if (DEBUG) System.out.println("Setting appropriate security from file '" + CLIENT_DESC + "'");
            ((Stub)ap)._setProperty(Constants.CLIENT_DESCRIPTOR_FILE, CLIENT_DESC);


        }

/*
        if (AUTHORIZE)
        {
            //authorization
            System.out.println("Enable authorization!");
            ((Stub)ap)._setProperty(Constants.AUTHORIZATION,SelfAuthorization.getInstance());
        }
        else
        {
            //no authorization
            System.out.println("Disable authorization!");
            ((Stub)ap)._setProperty(Constants.AUTHORIZATION,NoAuthorization.getInstance());
        }

        if (AUTHENTICATE)
        {
            //authentication
            System.out.println("Enable authentication!");
            ((Stub)ap)._setProperty(Constants.GSI_ANONYMOUS, Boolean.FALSE );
        }
        else
        {

        //no authentication
            System.out.println("Disable authentication!");
            ((Stub)ap)._setProperty(Constants.GSI_ANONYMOUS, Boolean.TRUE );
        }

        if (SIGN)
        {
            if (CONV)
            {
                //GSI Transport Conversation + Signature
                System.out.println("Use GSI Transport Conversation with Signature!");
                ((Stub)ap)._setProperty(Constants.GSI_SEC_CONV,Constants.SIGNATURE);
            }
            else if (MSG)
            {
                //GSI Transport Message + Signature
                System.out.println("Use GSI Transport Message with Signature!");
                ((Stub)ap)._setProperty(Constants.GSI_SEC_MSG,Constants.SIGNATURE);
            }
            else if (TSL)
            {
                //GSI (TSL) Transport Message + Signature
                System.out.println("Use GSI (TSL) Transport Message with Signature!");
                ((Stub)ap)._setProperty(GSIConstants.GSI_TRANSPORT, Constants.SIGNATURE);
            }
        }

        if (ENCRYPT)
        {
            if (CONV)
            {
                //GSI Transport Conversation + Encryption
                System.out.println("Use GSI Transport Conversation with Encryption!");
                ((Stub)ap)._setProperty(Constants.GSI_SEC_CONV,Constants.ENCRYPTION);
            }
            else if (MSG)
            {
                //GSI Transport Message + Encryption
                System.out.println("Use GSI Transport Message with Encryption!");
                ((Stub)ap)._setProperty(Constants.GSI_SEC_MSG,Constants.ENCRYPTION);
            }
            else if (TSL)
            {
                //GSI (TSL) Transport Message + Encryption
                System.out.println("Use GSI (TSL) Transport Message with Encryption!");
                ((Stub)ap)._setProperty(GSIConstants.GSI_TRANSPORT, Constants.ENCRYPTION);
            }
        }
  */
        //.getGPPortTypePort(instanceEPR);
        sw.stop();
        if (DEBUG) System.out.println("WORKER: Get PortType (" + sw.getElapsedTime() + "ms)");

        if (DIPERF) System.out.println("WORKER:getGPPortType(): " + sw.getElapsedTime() + " ms");
        sw.reset();

        //WorkerRegistration reg = new WorkerRegistration();
        //reg.setMachID(machID);

        //sw.start();
        //ap.workerRegistration(new WorkerRegistration(machID));
        //ap.workerRegistration(reg);
        //sw.stop();
        //if (DEBUG) System.out.println("WORKER: Performed one time Worker registration (" + sw.getElapsedTime() + "ms)");

        //if (DIPERF) System.out.println("WORKER:workerRegistration(): " + sw.getElapsedTime() + " ms");
        //sw.reset();

        //boolean useGram = true;

        //String rslFile = "tmp_rsl.xml";


        //just for testing...  remove later
        //getUserResultAvailable(ap);

        Status stat = new Status();

        /*
        <xsd:element name="message" type="xsd:string"/>
        <xsd:element name="drpAllocation" type="xsd:string"/>
        <xsd:element name="drpMinResc" type="xsd:int"/>
        <xsd:element name="drpMaxResc" type="xsd:int"/>
        <xsd:element name="drpMinTime" type="xsd:int"/>
        <xsd:element name="drpMaxTime" type="xsd:int"/>
        <xsd:element name="drpIdle" type="xsd:int"/>
        <xsd:element name="resourceAllocated" type="xsd:int"/>
        */
        stat.setMessage("");


        if (one_at_a_time)
        {
            stat.setDrpAllocation("One-at-a-Time");
        }
        else if (additive)
        {
            stat.setDrpAllocation("Additive");
        }
        else if (exponential)
        {
            stat.setDrpAllocation("Exponential");
        }
        else if (all_at_a_time)
        {
            stat.setDrpAllocation("All-at-Once");
        }
        else
        {
            stat.setDrpAllocation("Unkown");
        }

        stat.setDrpMinResc(minAllocatedWorkers);
        stat.setDrpMaxResc(hostCount);
        stat.setDrpMinTime(minWallTime);
        stat.setDrpMaxTime(maxWallTime);
        stat.setDrpIdle(idleTime);
        //int allocatedWorkers = 0; //workers submited through GRAM, but not necesarly that are running yet

        //long pollTime = pollTime;
        int lastQLength = 0;
        int gramID = 0;


        System.out.println("####################### WorkerRunGram started....");
        //int minAllocatedWorkers = minAllocatedWorkers;

        //List threadList = Collections.synchronizedList(new LinkedList());
        int executors2AllocateInFuture = 0;
        int lastNumWorkers = 0;

        BufferedWriter drpStatus = null;

        Properties props = System.getProperties();
        String FALKON_LOGS = (String)props.get("FALKON_LOGS");
        if (FALKON_LOGS == null)
        {
            //FALKON_LOGS = new String("");
            System.out.println("Warning, ${FALKON_LOGS} is not set, you must include -DFALKON_LOGS=${FALKON_LOGS} when starting the JVM...  things might not work due to relative path names...");
            drpStatus = new BufferedWriter(new FileWriter(drpLogFileName, false));

        }
        else
        {
            drpStatus = new BufferedWriter(new FileWriter(FALKON_LOGS+"/"+drpLogFileName, false));
        }

        if (DEBUG) System.out.println("Time_sec qLength activeTask allocatedWorkers numWorkers freeWorkers busyWorkers newWorkers deregisteredWorkers numThreads host2Allocate");


        try
        {
//drpStatus.write("aString");
            drpStatus.write("Time_sec qLength activeTask allocatedWorkers numWorkers freeWorkers busyWorkers newWorkers deregisteredWorkers numThreads host2Allocate\n");
            drpStatus.flush();
//out.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        while (true)
        {
            /*
                            // For a set or list
                for (Iterator it=threadList.iterator(); it.hasNext(); ) {
                    GramSubmit curThread = (GramSubmit)it.next();
                    if (!curThread.isAlive())
                    {
                        allocatedHosts = allocatedHosts - curThread.host2Allocate;
                        it.remove();
                    }
                }
              */


            //String executable = "/home/iraicu/java/WebServices/exGRAM_IBM/run.sh";
            //int maxWallTime = 1;
            //String hostType = "ia32-compute";
            //int hostCount = 1;
            //String contact = "tg-grid1.uc.teragrid.org";

            if (DEBUG) System.out.println("Retrieving GPWS status...");
            StatusResponse sr = ap.status(stat);
            if (sr.isValid())
            {



                int qLength = sr.getQueueLength();
                int activeTask = sr.getActiveTasks();
                int numWorkers = sr.getNumWorkers();

                int busyWorkers = sr.getBusyWorkers();
                int newWorkers = sr.getNewWorkers();
                int deregisteredWorkers = sr.getDeregisteredWorkers();



                //System.out.println(lt.getElapsedTime()*1.0/1000.0 + ": qLength=" + qLength + " activeTask=" + activeTask + " allocatedWorkers=" + getAllocatedWorkers() + " numWorkers=" + numWorkers + " freeWorkers=" + (numWorkers-busyWorkers) + " busyWorkers=" + busyWorkers +" newWorkers=" + newWorkers+" deregisteredWorkers="+deregisteredWorkers);





                //long sleepTime = maxWallTime*60*1000 - 60000;
                //if (sleepTime < 60000)
                //{
                //    sleepTime = 60000;
                //}

                /*
                if (qLength == 0)
                {
                    lastQLength = qLength;
                    //nothing to do...
                    try
                    {

                        if (DEBUG) System.out.println("qLength is 0, nothing to do, sleep for " + pollTime + " ms to poll for state again...");
                        Thread.sleep(pollTime);
                    } catch (Exception e)
                    {
                        System.out.println("Error: " + e);
                    }
                } else if (qLength - lastQLength > 0 || getAllocatedWorkers() < minAllocatedWorkers || qLength > 0)
                {
                */
                int newQLength = qLength - lastQLength;
                //alocate some resources...
                //int host2Allocate = (int) Math.max(Math.min(Math.ceil(newQLength), hostCount - getAllocatedWorkers()), minAllocatedWorkers - getAllocatedWorkers());
                //needed for DRP test
                //int host2Allocate = (int)Math.min(Math.max(newQLength - (getAllocatedWorkers() - busyWorkers), Math.max(minAllocatedWorkers - getAllocatedWorkers(), 0)), hostCount);
                //int host2Allocate = (int)Math.min(Math.max(newQLength - (getAllocatedWorkers() - busyWorkers), Math.max(minAllocatedWorkers - getAllocatedWorkers(), 0)), hostCount);
                //int executors2Allocate = ((int)Math.max(Math.min(Math.max(newQLength - (getAllocatedWorkers() - busyWorkers), 0), hostCount - getAllocatedWorkers()), minAllocatedWorkers - getAllocatedWorkers()))*MAX_NUM_WORKERS_PER_HOST + executors2AllocateInFuture;
                //int host2Allocate = (int)(executors2Allocate / MAX_NUM_WORKERS_PER_HOST);
                //executors2AllocateInFuture = executors2Allocate - host2Allocate*MAX_NUM_WORKERS_PER_HOST;

                deregisteredWorkers = (lastNumWorkers - numWorkers) + newWorkers;


                decAllocatedWorkers(deregisteredWorkers);

                //new formula, to make allocation less aggregsive

                PBSQueue pbsState = new PBSQueue();
                if (!EMULATED)
                {

                    //augment state info with that form PBS
                    pbsState = getPbsState(DEBUG);

                    //if (pbsState != null && (pbsState.Q + pbsState.R + pbsState.H + pbsState.C > 0))
                    if (pbsState != null && (pbsState.Q + pbsState.R + pbsState.H > 0))
                    {
                        if (DEBUG) System.out.println("PBS State: Q="+pbsState.Q+ " R="+pbsState.R + " H="+pbsState.H+ " C="+pbsState.C);
                        //if (numWorkers > pbsState.Q + pbsState.R + pbsState.H + pbsState.C)
                        if (getAllocatedWorkers() > pbsState.Q + pbsState.R + pbsState.H)
                        {
                            //numWorkers = pbsState.Q + pbsState.R + pbsState.H + pbsState.C;
                            //the pbsState.C seems to change slowly... it makes the reaction of this hack slow

                            System.out.println("reseting the number of workers from " + getAllocatedWorkers() + " to " + (pbsState.Q + pbsState.R + pbsState.H)); 
                            setAllocatedWorkers(pbsState.Q + pbsState.R + pbsState.H);

                        }
                    }
                }


                int host2Allocate = getNewAllocation(getAllocatedWorkers(), numWorkers, numWorkers - busyWorkers, busyWorkers, newWorkers, deregisteredWorkers, minAllocatedWorkers, hostCount, MAX_NUM_WORKERS_PER_HOST, (int)Math.floor(qLength/10));
                //old formula
                //int host2Allocate = getNewAllocation(getAllocatedWorkers(), numWorkers, numWorkers - busyWorkers, busyWorkers, newWorkers, deregisteredWorkers, minAllocatedWorkers, hostCount, MAX_NUM_WORKERS_PER_HOST, qLength);
                lastNumWorkers = numWorkers;

                try
                {
                    //drpStatus.write("aString");
                    drpStatus.write(lt.getElapsedTime()*1.0/1000.0 + " " + qLength + " " + activeTask + " " + getAllocatedWorkers() + " " + numWorkers + " " + (numWorkers-busyWorkers) + " " + busyWorkers +" " + newWorkers+" "+deregisteredWorkers + " " + Thread.activeCount() + " " + host2Allocate*MAX_NUM_WORKERS_PER_HOST + " " + pbsState.Q + " " + pbsState.R + " " + pbsState.H + " " + pbsState.C + "\n");
                    drpStatus.flush();
                    //out.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                System.out.println(lt.getElapsedTime()*1.0/1000.0 + " " + qLength + " " + activeTask + " " + getAllocatedWorkers() + " " + numWorkers + " " + (numWorkers-busyWorkers) + " " + busyWorkers +" " + newWorkers+" "+deregisteredWorkers + " " + Thread.activeCount() + " " + host2Allocate + " " + pbsState.Q + " " + pbsState.R + " " + pbsState.H + " " + pbsState.C);


                //System.out.println("***DEBUG***: " + host2Allocate + " = (int)Math.min(Math.max( " + newQLength + " - ( " + getAllocatedWorkers() + " - " + busyWorkers + " ), Math.max( " + minAllocatedWorkers + " - " + getAllocatedWorkers() + ", 0)), " + hostCount + ")");
                if (host2Allocate > 0)
                {

                    //int minWallTime = 60; //in minutes
                    int wallTime = (int) Math.min((int) Math.max(minWallTime, Math.ceil((newQLength / host2Allocate) / 60)), maxWallTime);

                    gramID++;



                    if (allocationStrategy.contentEquals(new StringBuffer("one_at_a_time")))
                    {
                        one_at_a_time = true;
                        additive = false;
                        exponential = false;
                        all_at_a_time = false;
                    }
                    else if (allocationStrategy.contentEquals(new StringBuffer("additive")))
                    {
                        one_at_a_time = false;
                        additive = true;
                        exponential = false;
                        all_at_a_time = false;
                    }
                    else if (allocationStrategy.contentEquals(new StringBuffer("exponential")))
                    {
                        one_at_a_time = false;
                        additive = false;
                        exponential = true;
                        all_at_a_time = false;
                    }
                    else if (allocationStrategy.contentEquals(new StringBuffer("all_at_a_time")))
                    {
                        one_at_a_time = false;
                        additive = false;
                        exponential = false;
                        all_at_a_time = true;
                    }
                    else
                    {
                        //the default values have already been set to one_at_a_time
                    }




                    int numGramCalls = 0;

                    if (one_at_a_time)
                    {
                        numGramCalls = host2Allocate;
                    }
                    else if (additive)
                    {
                        numGramCalls = (int)Math.ceil((Math.sqrt(8*host2Allocate+1) - 1)*1.0/2);
                    }
                    else if (exponential)
                    {
                        numGramCalls = (int)Math.ceil(Math.log(host2Allocate+1)/Math.log(2));

                    }
                    else if (all_at_a_time)
                    {
                        numGramCalls = 1;

                    }
                    else
                    {
                        numGramCalls = host2Allocate;

                    }




                    GramSubmit thread[] = new GramSubmit[numGramCalls];


                    int allocatedHosts = 0;


                    int hostsNum = 0;


                    if (one_at_a_time)
                    {
                        hostsNum = 1;
                    }
                    else if (additive)
                    {
                        hostsNum = 1;
                    }
                    else if (exponential)
                    {
                        hostsNum = 1;
                    }
                    else if (all_at_a_time)
                    {
                        hostsNum = host2Allocate;

                    }
                    else
                    {
                        hostsNum = 1;

                    }

                    for (int ii = 0;ii<numGramCalls;ii++)
                    {


                        System.out.println("Submitting job " + ii + ": allocating " + hostsNum + " of " + host2Allocate + " hosts with " + MAX_NUM_WORKERS_PER_HOST + " workers per host of type " + hostType + " for " + wallTime + " minutes...");
                        //thread[ii] = new GramSubmit(this, executable, wallTime, hostType, hostsNum, contact, gramID, ii, project, MAX_NUM_WORKERS_PER_HOST, GRAM4_factory_type, falkon_home);  //allocate 1 host at a time
                        //thread[ii].start();


                        //if (!EMULATED)
                        //{
                        thread[ii] = new GramSubmit(this, executable, wallTime, hostType, hostsNum, contact, gramID, ii, project, MAX_NUM_WORKERS_PER_HOST, GRAM4_factory_type, falkon_home);  //allocate 1 host at a time
                        thread[ii].start();
                        /*}
                        else
                        {
                            boolean success = EmulatedSubmit(wallTime, hostsNum);

                            if (success)
                            {
                                if (DEBUG) System.out.println("Job submitted successful...");
                            }
                            else
                            {

                                if (DEBUG) System.out.println("Job submission failed...");
                            }

                        } */


                        try
                        {
                            Thread.sleep(1000);

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        //            threadList.add(thread[ii]);

                        allocatedHosts += hostsNum;


                        if (one_at_a_time)
                        {
                            hostsNum = 1;
                        }
                        else if (additive)
                        {
                            hostsNum = (int)Math.min(hostsNum+1, host2Allocate - allocatedHosts);
                        }
                        else if (exponential)
                        {
                            hostsNum = (int)Math.min(hostsNum*2, host2Allocate - allocatedHosts);
                        }
                        else if (all_at_a_time)
                        {
                            hostsNum = host2Allocate;

                        }
                        else
                        {
                            hostsNum = 1;

                        }

                    }

                }
                else
                    if (DEBUG) System.out.println("No hosts to allocate: " + getAllocatedWorkers() + " allocated resources of a maximum of " + hostCount + "!");

                lastQLength = qLength;


                try
                {

                    if (DEBUG) System.out.println("Sleeping for " + pollTime + " ms to poll for state again...");
                    //System.out.println("Allocated " + host2Allocate + " resources for " + maxWallTime +" min, sleeping for " + pollTime + " ms to poll for state again...");
                    Thread.sleep(pollTime);
                }
                catch (Exception e)
                {
                    System.out.println("Error: " + e);
                }
                /*
             } else
             {
                 try
                 {

                     if (DEBUG) System.out.println("qLength has not changed since the last poll, nothing to do, sleep for " + pollTime + " ms to poll for state again...");
                     //System.out.println("Allocated " + host2Allocate + " resources for " + maxWallTime +" min, sleeping for " + pollTime + " ms to poll for state again...");
                     Thread.sleep(pollTime);
                 } catch (Exception e)
                 {
                     System.out.println("Error: " + e);
                 }
             }    */


            }
            else
            {

                try
                {

                    if (DEBUG) System.out.println("State was not valid, sleeping for " + pollTime + " ms to poll for state again...");
                    Thread.sleep(pollTime);
                }
                catch (Exception e)
                {
                    System.out.println("Error: " + e);
                }
            }


        }



        //}

    }



    public static void main(String[] args)
    {
        try
        {
            StopWatch all = new StopWatch();
            StopWatch sw = new StopWatch();
            all.start();

            WorkerRunGram workers = new WorkerRunGram();
            //System.out.println("DIPERF=" + worker.DIPERF);

            sw.start(); 
            workers.parseArgs(args);
            sw.stop();
            if (workers.DIPERF) System.out.println("WORKERS:parseArgs(): " + sw.getElapsedTime() + " ms");
            sw.reset();

            sw.start(); 
            boolean exists = (new File(workers.provisionerConfigFile)).exists();
            if (exists)
            {
                //OK
                workers.readProvisionerConfig();
            }
            else
            {
                System.out.println("Config file '" + workers.provisionerConfigFile + "' does not exist, using either default values or other command line arguements.");
            }
            sw.stop();
            if (workers.DIPERF) System.out.println("WORKERS:readProvisionerConfig(): " + sw.getElapsedTime() + " ms");
            sw.reset();

            //allow time for the config file to be read...
            //Thread.sleep(1000);

            //workers.interpretProvisionerConfig();


            //System.out.println("DIPERF=" + worker.DIPERF);
            if (workers.DEBUG) if (workers.DIPERF == false) System.out.println("WORKERS: Parse Arguements and Setup Corresponding State...");


            workers.main_run(args);

            all.stop();

            if (workers.DIPERF == true) System.out.println("WORKERS:WorkTime: " + all.getElapsedTime() + " ms");
        }
        catch (Exception e)
        {
            System.out.println("WORKERS: Fatal ERROR: " + e + "... exiting");
            e.printStackTrace();

            System.exit(0);
        }

    }

}



// This class extends Thread
class GramSubmit extends Thread
{

    public WorkerRunGram wrg;
    public String executable;
    public int maxWallTime;
    public String hostType;
    public int host2Allocate;
    public String contact;
    public String rslFile;
    public int jobID;
    public int gramID;
    public int idleTime;
    public String project;
    public int MAX_NUM_WORKERS_PER_HOST;
    public String GRAM_factory_type;
    public String falkon_home;

    public GramSubmit(WorkerRunGram wrg, String executable, int maxWallTime, String hostType, int host2Allocate, String contact, int gramID, int jobID, String project, int MAX_NUM_WORKERS_PER_HOST, String GRAM_factory_type, String falkon_home)
    {
        this.wrg = wrg;
        this.executable = executable;
        this.maxWallTime = maxWallTime;
        this.hostType = hostType;
        this.host2Allocate = host2Allocate;
        this.contact = contact;
        this.rslFile = getRslFileName();
        this.jobID = jobID;
        this.gramID = gramID;
        this.idleTime = wrg.idleTime;
        if (project.equals("default"))
        {
            this.project = null;
        }
        else
            this.project = project;
        this.MAX_NUM_WORKERS_PER_HOST = MAX_NUM_WORKERS_PER_HOST;
        this.GRAM_factory_type = GRAM_factory_type;
        this.falkon_home = falkon_home;
    }

    public String getRslFileName()
    {
        return "RSL." + this.gramID + "." + this.jobID + "." + this.hostType + "." + this.host2Allocate + "." + this.MAX_NUM_WORKERS_PER_HOST + "." + this.maxWallTime + "." + this.hashCode() + ".xml"; //this should be a unique name

    }

    public boolean cleanup(String rslFile)
    {
        boolean success = (new File(rslFile)).delete();
        if (!success)
        {
            // Deletion failed
            if (wrg.DEBUG) System.out.println("Cleaup failed to remove file '" + rslFile + "'");
            return false;
        }
        return true;




    }


    // This method is called when the thread runs
    public void run()
    {

        wrg.incAllocatedWorkers(host2Allocate*MAX_NUM_WORKERS_PER_HOST);
        //System.out.println("allocating " + host2Allocate + " executors...");
        //wrg.incAllocatedWorkers(host2Allocate);

        if (!wrg.EMULATED)
        {
            GramClient gramClient = new GramClient();
            gramClient.writeRSL(rslFile,executable ,maxWallTime ,hostType ,host2Allocate, idleTime,project, MAX_NUM_WORKERS_PER_HOST, falkon_home);


            if (wrg.DEBUG) System.out.println("####################### WorkerRunThread starting job submission test.... this thread will wait for the job to complete...");

            gramClient.main_run(contact, rslFile, GRAM_factory_type, wrg, host2Allocate*MAX_NUM_WORKERS_PER_HOST);
            if (wrg.DEBUG) System.out.println("####################### WorkerRunThread finished starting job submission test.... ");
            //wrg.decAllocatedWorkers(host2Allocate);
            cleanup(rslFile);
        }
        else
        {
            if (wrg.DEBUG) System.out.println("####################### WorkerRunThread starting job submission test.... this thread will wait for the job to complete...");
            boolean success = wrg.EmulatedSubmit(maxWallTime, host2Allocate);
            if (wrg.DEBUG) System.out.println("####################### WorkerRunThread finished starting job submission test.... ");

            if (success)
            {
                if (wrg.DEBUG) System.out.println("Job submitted successful...");
            }
            else
            {

                if (wrg.DEBUG) System.out.println("Job submission failed...");
            }

        } 



    }
}









