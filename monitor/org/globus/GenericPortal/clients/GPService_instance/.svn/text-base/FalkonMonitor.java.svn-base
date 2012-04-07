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

public class FalkonMonitor
{

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

    public String executable = "/home/iraicu/java/GenericWorker.1.3/run.worker.sh";
    public int maxWallTime = 60*1*24; //in min
    public int minWallTime = 60*1*24; //in min
    public int idleTime = 600000; //in ms
    public int Poll_Time = 1000; //in ms
    public String hostType = "ia32-compute";
    public int hostCount = 48;
    public String contact = "tg-grid1.uc.teragrid.org";

    public String machID;

    public boolean DEBUG;
    public boolean DIPERF;

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
    public int MinAllocatedWorkers = 0;


    //public EndpointReferenceType notificationEPR;



    //constructor
    public FalkonMonitor(boolean debug)
    {
        this.ROI_Height = 100;
        this.ROI_Width = 100;
        this.randIndex = new Random();

        this.machID = "localhost:0";
        this.scratchDisk = "";

        this.rand = new Random();
        this.DEBUG = debug;
        this.DIPERF = false;
        this.activeNotifications = 0;
        this.activeThreads = 0;
        this.MAX_NUM_THREADS = 10;
        this.MAX_NUM_NOTIFICATIONS = 1000000; //some large #, if setting lower, there needs a way to enforce it...
        this.queueLength = 0;
        this.SO_TIMEOUT = 0; //receive will block forever...
        this.LIFETIME = 0;
        //this.LIFETIME_STACK = 0;

        this.workerNot = new Notification(this.SO_TIMEOUT, debug);
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

        } catch (Exception e)
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
            } else
            {
                tPID = (new Integer(iPID)).toString();

            }

            machName = java.net.InetAddress.getLocalHost().getHostName();
            machName = machName +  ":" + tPID;
            //machIP = java.net.InetAddress.getLocalHost().getHostAddress();
            if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Machine ID = " + machName);

        } catch (Exception e)
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
            if (args[ctr].equals("-serviceURI") && ctr + 1 < args.length)
            {
                ctr++;
                serviceURI = new String(args[ctr]);
                //System.out.println("Setting serviceURI to " + serviceURI);
            }

            else if (args[ctr].equals("-epr") && ctr + 1 < args.length)
            {
                ctr++;


                fileEPR = new String(args[ctr]);
                boolean exists = (new File(fileEPR)).exists();
                if (exists)
                {
                    //OK
                } else
                {
                    throw new Exception("File '" + fileEPR + "' does not exist.");
                }
            } else if (args[ctr].equals("-maxWallTime") && ctr + 1 < args.length)
            {
                ctr++;
                maxWallTime = Integer.parseInt(args[ctr]); //in minutes
            }

            else if (args[ctr].equals("-minWallTime") && ctr + 1 < args.length)
            {
                ctr++;
                minWallTime = Integer.parseInt(args[ctr]); //in minutes
            } else if (args[ctr].equals("-idleTime") && ctr + 1 < args.length)
            {
                ctr++;
                idleTime = Integer.parseInt(args[ctr]); //in minutes
            } 
            else if (args[ctr].equals("-pollTime") && ctr + 1 < args.length)
            {
                ctr++;
                Poll_Time = Integer.parseInt(args[ctr]); //in minutes
            }
            else if (args[ctr].equals("-minHostCount") && ctr + 1 < args.length)
            {
                ctr++;
                MinAllocatedWorkers = Integer.parseInt(args[ctr]); 
            }
            else if (args[ctr].equals("-executable") && ctr + 1 < args.length)
            {
                ctr++;
                executable = new String(args[ctr]);
            }

            else if (args[ctr].equals("-hostType") && ctr + 1 < args.length)
            {
                ctr++;
                hostType = new String(args[ctr]);
            } else if (args[ctr].equals("-hostCount") && ctr + 1 < args.length)
            {
                ctr++;
                hostCount = Integer.parseInt(args[ctr]); //in minutes
            } else if (args[ctr].equals("-contact") && ctr + 1 < args.length)
            {
                ctr++;
                contact = new String(args[ctr]);
            } else if (args[ctr].equals("-debug"))//&& ctr + 1 < args.length)
            {
                DEBUG = true;
            } else if (args[ctr].equals("-diperf"))//&& ctr + 1 < args.length)
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

            } else
            {

                if (DIPERF == false) System.out.println("ERROR: invalid parameter - " + args[ctr] + " at arguement " + ctr + " of " + args.length + " arguements");
                if (DIPERF == false) System.out.println("Current parameters values:");
                if (DIPERF == false) System.out.println("-serviceURI " + serviceURI);
                if (DIPERF == false) System.out.println("-maxWallTime " + maxWallTime);
                if (DIPERF == false) System.out.println("-executable " + executable);
                if (DIPERF == false) System.out.println("hostType " + hostType);
                if (DIPERF == false) System.out.println("hostCount " + hostCount);
                if (DIPERF == false) System.out.println("contact " + contact);
                if (DIPERF == false) System.out.println("-debug " + DEBUG);
                if (DIPERF == false) System.out.println("-diperf " + DIPERF);


                usage(args);
            }
        }

    }

    public void usage(String[] args)
    {
        if (DIPERF == false) System.out.println("Help Screen: ");
        if (DIPERF == false) System.out.println("-diperf <>");
        if (DIPERF == false) System.out.println("-help <>");
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
            throw new Exception("homeEPR == null, probably EPR was not correctly read from file :(");
        } else
        {

            GPServiceAddressingLocator instanceLocator = new GPServiceAddressingLocator();
            return instanceLocator.getGPPortTypePort(homeEPR);
        }

    }

    public boolean createWorkerResource(String factoryURI, String eprFilename)
    {

        //static final String EPR_FILENAME = "epr.txt";
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
            factoryEPR = new EndpointReferenceType();
            factoryEPR.setAddress(new Address(factoryURI));
            apFactory = factoryLocator.getFactoryPortTypePort(factoryEPR);

            // Create resource and get endpoint reference of WS-Resource
            CreateResourceResponse createResponse = apFactory
                                                    .createResource(new CreateResource());
            instanceEPR = createResponse.getEndpointReference();

            String endpointString = ObjectSerializer.toString(instanceEPR,
                                                              GPConstants.RESOURCE_REFERENCE);

            FileWriter fileWriter = new FileWriter(eprFilename);
            BufferedWriter bfWriter = new BufferedWriter(fileWriter);
            bfWriter.write(endpointString);
            bfWriter.close();
            System.out.println("Endpoint reference written to file "
                               + eprFilename);
            return true;
        } catch (Exception e)
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
                throw new Exception("parseArgs(): homeEPR == null, probably EPR was not correctly read from file :(");
            } else
            {

                if (DEBUG) if (DIPERF == false) System.out.println("parseArgs(): homeEPR is set correctly");
            }

            fis.close();
            return true;
        } else
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
        allocatedWorkers += i;

    }

    public synchronized void decAllocatedWorkers(int i)
    {
        allocatedWorkers -= i;

    }

    public synchronized int getAllocatedWorkers()
    {
        return allocatedWorkers;

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
        } else
        {
            System.out.println("State was not valid, trying again...");
        }


        System.out.println("queuedTask=" + queuedTask + " activeTask=" + activeTask + " completedTasks=" + completedTasks + " GPWS_queuedTask=" + GPWS_qLength + " GPWS_activeTasks=" + GPWS_activeTasks + " GPWS_activeResources=" + GPWS_numWorkers);
    }


    public boolean createResource(String securityFile, String serviceURI)
    {
        try
        {
        
        if (fileEPR == null)
            fileEPR = "WorkerEPR.txt";

        if (createWorkerResource(serviceURI, fileEPR))
        {
            if (DEBUG) System.out.println("WORKERS: Created worker resource... saved in '" + fileEPR + "'");

        } else
            //throw new Exception("main_run(): createWorkerResource() failed, could not create worker resource...");
            return false;


        if (readWorkerResource(fileEPR))
        {
            if (DEBUG) System.out.println("WORKERS: initialized worker reasource state from '" + fileEPR + "'");

        } else
            //throw new Exception("main_run(): readWorkerResource() failed, could not read worker resource from '" + fileEPR + "'");
            return false;







        if (homeEPR == null)
        {
            //throw new Exception("main_run(): homeEPR == null, something is wrong :(");
            return false;

        } else
        {

            if (DEBUG) System.out.println("main_run(): homeEPR is OK");
        }

        //sw.start();
        // Get PortType
        ap = getGPPortType();//= instanceLocator


        if (securityFile != null && (new File(securityFile)).exists())
        {
            if (DEBUG) System.out.println("Setting appropriate security from file '" + securityFile + "'!");
            ((Stub)ap)._setProperty(Constants.CLIENT_DESCRIPTOR_FILE, securityFile);


        }

        return true;
        }
        catch (Exception e)
        {
            if (DEBUG) System.out.println("main_run(): error: " + e);
            e.printStackTrace();
            return false;
        }

    }


    /*
    public StatusResponse getState()
    {

        Status stat = new Status("");

            if (DEBUG) System.out.println("Retrieving GPWS status...");

            try
            {
            
            StatusResponse sr = ap.status(stat);
            return sr;
            }
            catch (Exception e)
            {
                return null;
            }
            
            //if (sr.isValid())
            //{
             


                //int qLength = sr.getQueueLength();
                //int activeTask = sr.getActiveTasks();
                //int numWorkers = sr.getNumWorkers();

                //int busyWorkers = sr.getBusyWorkers();
                //int newWorkers = sr.getNewWorkers();
                //int deregisteredWorkers = sr.getDeregisteredWorkers();

            //}

    }  */


    public MonitorConfigResponse getMonitorConfig()
    {

        MonitorConfig stat = new MonitorConfig("");

            if (DEBUG) System.out.println("Retrieving GPWS MonitorConfig...");

            try
            {

            MonitorConfigResponse sr = ap.monitorConfig(stat);
            return sr;
            }
            catch (Exception e)
            {
                return null;
            }
    }

    public MonitorStateResponse getMonitorState()
    {

        MonitorState stat = new MonitorState("");

            if (DEBUG) System.out.println("Retrieving GPWS MonitorState...");

            try
            {

            MonitorStateResponse sr = ap.monitorState(stat);
            return sr;
            }
            catch (Exception e)
            {
                return null;
            }
    }

    public MonitorWorkerStateResponse getMonitorWorkerState()
    {

        MonitorWorkerState stat = new MonitorWorkerState("");

            if (DEBUG) System.out.println("Retrieving GPWS MonitorWorkerState...");

            try
            {

            MonitorWorkerStateResponse sr = ap.monitorWorkerState(stat);
            return sr;
            }
            catch (Exception e)
            {
                return null;
            }
    }


    public MonitorTaskStateResponse getMonitorTaskState()
    {

        MonitorTaskState stat = new MonitorTaskState("");

            if (DEBUG) System.out.println("Retrieving GPWS MonitorTaskState...");

            try
            {

            MonitorTaskStateResponse sr = ap.monitorTaskState(stat);
            return sr;
            }
            catch (Exception e)
            {
                return null;
            }
    }



}




