//SVN version
/*
 * This file is licensed under the terms of the Globus Toolkit Public License
 * v3, found at http://www.globus.org/toolkit/legal/4.0/license-v3.html.
 * 
 * This notice must appear in redistributions of this file, with or without
 * modification.
 */
package org.globus.GenericPortal.services.core.WS.impl;

import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.impl.ResourceHomeImpl;
import org.globus.wsrf.impl.SimpleResourceKey;


import org.globus.GenericPortal.stubs.GPService_instance.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.globus.GenericPortal.common.*;
//import java.lang.*;
//import java.lang.Object.*;
//import java.util.StringTokenizer;
//import java.io.StreamTokenizer.*;
//import java.util.*;
//import java.io.*;
//import java.util.Arrays.*;
//import java.util.zip.*;

import java.util.*; 
import java.net.*;
import java.io.*;
import java.lang.*;


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

import org.globus.exec.client.GlobusRun;
import org.globus.exec.client.GramJob;
import org.globus.exec.client.GramJobListener;


import org.apache.axis.components.uuid.UUIDGen;
import org.apache.axis.components.uuid.UUIDGenFactory;

//import org.globus.wsrf.jndi.*;
//import javax.xml.rpc.server.*;


//import KDTree.*;

import java.util.logging.*;



public class GPResourceHome extends ResourceHomeImpl /*implements Initializable, ServiceLifecycle*/
{

    /*
public void initialize()
{
    
    System.out.println("Starting Falkon service...");
    try
    {

        ResourceKey rkey = create();

        if (rkey != null)
        {
            System.out.println("Falkon started succesfully!");
        }
    } catch (Exception e)
    {
        System.out.println("Falkon failed to start...");
        e.printStackTrace();

    } 

}

public void init(Object o)
{
    System.out.println("started falkon: init");

}


public void destroy()
{
    System.out.println("stopped falkon: destroy");

}   */



    //public static boolean DEBUG = false;
    public static Logger logger2file = null;
    public static boolean logger2fileValid = false;
    public static String logFile = null;


    public static boolean append = true;
    public static int numBytes = 1073741824;
    public static int numFiles = 10;
    //public static boolean append = false;
    //public static int numBytes = 1024;
    //public static int numFiles = 10;


    /* Added for logging */
    static final Log logger = LogFactory.getLog(GPResourceHome.class);

    public static boolean DIPERF = false;

    private String instanceServicePath;

    //public static Thread                              tWR = null;

    //public static int totValue = 0;

    //public GPResource gpResourceWorker = null;
    public static GPResourceCommon commonState = null;
    public static GPResource gpResourceWorker = null;

    //public static GPResourceCommon commonState = new GPResourceCommon();

    //double check if this is the problem... .timeout is too high????
    private int SO_TIMEOUT = 5000; //number of ms to wait for notifications ACKs before timing out
    public static Notification notification = null;//new Notification(SO_TIMEOUT);


    //  public static KDTreeIndex index = null;

    public static boolean indexValid = false;
    public static boolean indexFailed = false;

    //public static String indexFileName = "/disks/scratchgpfs1/iraicu/generic.portal/data/index-SDSS-DR4.txt";
    //public static String indexFileName = "/home/iraicu/java/GenericPortal.1.3/index-SDSS-DR5.txt";

    private static final UUIDGen uid_logfile = UUIDGenFactory.getUUIDGen();


    public static int maxNumNotificationWorkerThreads = 1;
    public static int maxNumNotificationUserThreads = 1;
    //public NotificationEngineUser neUser[] = new NotificationEngineUser[maxNumNotificationUserThreads];
    public NotificationEngineUser neUser[] = null;
    public boolean neUserTest = false;
    //public NotificationEngineWorker neWorker[] = new NotificationEngineWorker[maxNumNotificationWorkerThreads];
    public NotificationEngineWorker neWorker[] = null;
    public boolean neWorkerTest = false;

    public boolean tcpCoreTest = false;
    public static boolean useTCPCore = false;

    public static TCPWorkQueue tcpWorkQueue = null;
    public static TCPWorkQueue tcpResultQueue = null;

    public static TCPCore tcpCore = null;
    //used to log all tasks...
    public boolean logFileTest = true;

    //static final Log logger = LogFactory.getLog(GPService.class);

    public GPResourceHome()
    {
        if (notification == null)
        {
            //DEBUG = ;

            logger.debug("GPResourceHome()...");
            logger.debug("new Notification(SO_TIMEOUT)...");
            notification = new Notification(SO_TIMEOUT, logger.isDebugEnabled());
            logger.debug("Notification initialized successful!");
        }



    }

    public static Map falkonConfig = null;//Collections.synchronizedMap(new HashMap<String, TaskPerformance>());


    public Map readFalkonConfig()
    {
        Map deefConf = Collections.synchronizedMap(new HashMap());

        Properties props = System.getProperties();
        String falkonConfigFile = (String)props.get("FalkonConfig");

        if (falkonConfigFile == null)
        {
            return deefConf;
        }

        // Enumerate all system properties
        /*
        Enumeration enum = props.propertyNames();
    for (; enum.hasMoreElements(); ) {
        // Get property name
        String propName = (String)enum.nextElement();
    
        // Get property value
        String propValue = (String)props.get(propName);

        deefConf.put(propName, propValue);
    }     */

        try
        {
            BufferedReader in = new BufferedReader(new FileReader(falkonConfigFile));
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
                        deefConf.put(tokens[0], tokens[1]);


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
            logger.debug("readFalkonConfig(): reading DeeF config file " + falkonConfigFile + " failed: " + e);
            System.err.println("readFalkonConfig(): reading DeeF config file " + falkonConfigFile + " failed: " + e);
            if (logger.isDebugEnabled()) e.printStackTrace();
            return deefConf;

        }


        //   props.getProperty("");//.put("axis.ClientConfigFile", "/scratch/local/iraicu/GT4.0.1/client-config.wsdd");


        return deefConf;

    }

    public static PerformanceProfile perfProfile = null; 

    public ResourceKey create() throws Exception {

        if (falkonConfig == null)
        {
            falkonConfig = readFalkonConfig();
        }

        if (perfProfile == null)
        {
            perfProfile = new PerformanceProfile();
        }



        logger.debug("GPResourceHome(): create()");
        logger.debug("StopWatch()...");
        StopWatch ct = new StopWatch();
        ct.start();




        logger.debug("ResourceHomeImpl create()");

        logger.debug("try");

        try
        {
            if (logFileTest == true && logger2fileValid == false && logger2file == null)
            {
                logger.debug("init logger...");
                //logger.debug("getting uuid_logfile...");


                //String key = uid_logfile.nextUUID();
                logger.debug("setting logfile...");


                Properties props = System.getProperties();
                String falkonServiceHome = (String)props.get("FALKON_LOGS");

                String logFile = (String)falkonConfig.get("GenericPortalWS");
                if (logFile == null)
                {

                    logFile = new String("/dev/null");
                    logFileTest = false;
                }
                else
                {
                    logFile = new String(falkonServiceHome + "/" + logFile);



                    //logFile = "/disks/scratchgpfs1/iraicu/generic.portal/logs.1.3/GenericPortalWS.log";

                    logger.debug("logfile = " + logFile);
                    logger.debug("FileHandler(logFile, numBytes, numFiles, append) => ");
                    logger.debug("FileHandler(" + logFile + ", " + numBytes + ", " + numFiles + ", " + append + ")");



                    // Create an appending file handler
                    FileHandler handler = new FileHandler(logFile, numBytes, numFiles, append);

                    logger.debug("Logger.getLogger(GenericPortalWS)...");
                    // Add to the desired logger
                    logger2file = Logger.getLogger("GenericPortalWS");

                    logger.debug("addHandler(handler)...");
                    logger2file.addHandler(handler);
                    logger.debug("setUseParentHandlers(false)...");
                    logger2file.setUseParentHandlers(false);


                    logger2fileValid = true;
                }


                logger.debug("init logger success");
            }
        }
        catch (IOException e)
        {
            logger.debug("logger to file '" + logFile + "' failed to initialize...");
            if (logger.isDebugEnabled()) e.printStackTrace();


        }


        //start thread

        /*
    //should try to load from disk if this was null....
    //this is the GRAM auto starter for worker nodes
    logger.debug("starting WorkerRunThread...");
    if (tWR == null) 
    {
        tWR = new WorkerRunThread(this);
        tWR.start();
    }     */

        //this.initializeCommon();

        logger.debug("load_common()...");
        load_common();
        // Create a resource and initialize it

        logger.debug("createNewInstance()...");
        GPResource gpResource = (GPResource) this.createNewInstance();
        //gpResource.initialize(commonState);

        logger.debug("initialize()...");
        gpResource.initialize();
        //gpResource.setCommonState(commonState);
        // Get key
        logger.debug("Get key...");
        ResourceKey key = new SimpleResourceKey(this.getKeyTypeName(),
                                                gpResource.getID());


        logger.debug("***+++***key = " + String.valueOf(key));
        logger.debug("***+++***QName = " + String.valueOf(this.getKeyTypeName()));
        logger.debug("***+++***Object = " + String.valueOf(gpResource.getID()));

        // Add the resource to the list of resources in this home

        logger.debug("Add the resource to the list of resources in this home...");
        this.add(key, gpResource);

        logger.info("Resource " + key.getValue() + " Created!");

        if (gpResourceWorker == null)
        {
            //setting the gpResourceWorker to the worker resource... this should be the first one!!!  
            gpResourceWorker = gpResource;
        }

        String temp =  (String)falkonConfig.get("maxNumNotificationWorkerThreads");
        if (temp != null)
        {
            maxNumNotificationWorkerThreads = Math.max(Integer.parseInt(temp), 1);
        }
        else
            maxNumNotificationWorkerThreads = 1;


        neWorker = new NotificationEngineWorker[maxNumNotificationWorkerThreads];




        temp =  (String)falkonConfig.get("maxNumNotificationUserThreads");
        if (temp != null)
        {
            maxNumNotificationUserThreads = Math.max(Integer.parseInt(temp), 1);
        }
        else
            maxNumNotificationUserThreads = 1;

        neUser = new NotificationEngineUser[maxNumNotificationUserThreads];



        //logger.debug("loading index...");

        //load_index();
        //logger.debug("index loaded!");


        ct.stop();
        //if (DIPERF) logger.warn("GPResourceHome:create(): " + ct.getElapsedTime() + " ms");
        if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + ": GPResourceHome:create(): " + ct.getElapsedTime() + " ms");
        ct.reset();

        //if (neUser == null)
        if (neUserTest == false)
        {
            for (int i=0;i<neUser.length;i++)
            {
                neUser[i] = new NotificationEngineUser(this);
                neUser[i].start();


            }
            neUserTest = true;
        }
        //if (neWorker == null)
        if (neWorkerTest == false)
        {

            for (int i=0;i<neWorker.length;i++)
            {
                neWorker[i] = new NotificationEngineWorker(this);
                neWorker[i].start();
            }
            neWorkerTest = true;
        }


        //start TCP-based server...
        //this.useTCPCore = gpResource.useTCPCore;

        temp = (String)falkonConfig.get("useTCPCore");
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



        int TCPCore_RECV_PORT = 0;
        int TCPCore_SEND_PORT = 0;
        int TCPCore_WORKER_PROC = 0;

        if (tcpCoreTest == false && useTCPCore == true && tcpCore == null)
        {
            logger.debug("initializing TCPCore...");

            tcpWorkQueue = new TCPWorkQueue();
            tcpResultQueue = new TCPWorkQueue();
            //we might need a resuult queue as well...

            temp = (String)falkonConfig.get("TCPCore_RECV_PORT");
            if (temp != null)
            {
                TCPCore_RECV_PORT = Integer.parseInt(temp);
            }
            else
                TCPCore_RECV_PORT = 55000;

            temp = (String)falkonConfig.get("TCPCore_SEND_PORT");
            if (temp != null)
            {
                TCPCore_SEND_PORT = Integer.parseInt(temp);
            }
            else
                TCPCore_SEND_PORT = 55001;

            temp = (String)falkonConfig.get("TCPCore_WORKER_PROC");
            if (temp != null)
            {
                TCPCore_WORKER_PROC = Integer.parseInt(temp);
            }
            else
                TCPCore_WORKER_PROC = 1;


            tcpCore = new TCPCore(tcpWorkQueue, tcpResultQueue, maxNumNotificationWorkerThreads, TCPCore_RECV_PORT, TCPCore_SEND_PORT, TCPCore_WORKER_PROC);

            tcpCore.start();
            tcpCoreTest = true;
            logger.debug("TCPCore initialized!");
        }

        String tEmulation = (String)falkonConfig.get("EMULATION");
        if (tEmulation != null)
        {
            if (tEmulation.contentEquals(new StringBuffer("true")))
            {
                EMULATION = true;
                commonState.EMULATION = EMULATION;
            }
            else
            {
            
                EMULATION = false;
                commonState.EMULATION = EMULATION;
            }
        }

        tEmulation = (String)falkonConfig.get("EMULATION_NUM_EXECUTORS");
        if (tEmulation != null)
        {
            EMULATION_NUM_EXECUTORS = Integer.parseInt(tEmulation);
        }

        if (EMULATION_INIT && EMULATION && EMULATION_NUM_EXECUTORS > 0)
        {
            EMULATION_INIT = false;

            for (int i=0;i<EMULATION_NUM_EXECUTORS;i++)
            {
                WorkerRegistration wreg = new WorkerRegistration();
                wreg.setMachID(new String("emulated.executor."+i));
                wreg.setWallTime(0);

                /*
                    // Create cache

    final int MAX_CACHE_SIZE_KB = 50000000;
    //assuming 10MB objects
    final int MAX_ENTRIES = MAX_CACHE_SIZE_KB/10000;

    Map cacheLRU = new LinkedHashMap((int)MAX_ENTRIES*1.33, .75F, true) {
        // This method is called just after a new entry has been added
        public boolean removeEldestEntry(Map.Entry eldest) {
            return size() > MAX_ENTRIES;
        }
    };

                // If the cache is to be used by multiple threads,
    // the cache must be wrapped with code to synchronize the methods
    cacheLRU = (Map)Collections.synchronizedMap(cacheLRU);


    commonState.workerCaches.put(wreg.getMachID(), cacheLRU);
    //remove if the worker de-registers
    */



                try
                {

                    gpResource.registerWorker(wreg);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

        }



        return key;
    }


    public boolean EMULATION_INIT = true;

    public boolean EMULATION = false;

    public int EMULATION_NUM_EXECUTORS = 0;

    public ResourceKey getKey(String s)
    {
        ResourceKey key = new SimpleResourceKey(this.getKeyTypeName(), s);
        return key;

    }

    public static void initializeCommon()
    {

        logger.debug("GPResourceCommon()...");
        commonState = new GPResourceCommon();

    }

    /*
    public synchronized static boolean initializeIndex(String fName) throws Exception
    {

        if (index != null && indexValid)
        {
            logger.debug("An index is already loaded in memory, ready to receive queries!");
            return true;

        } else
        {

            //String fName = i.getIndexFileName();
            logger.info("Loading index from '" + fName + "' this might take some time, depending on the size of the index... expect about 10000~20000 entries per second...");
            try
            {

                index = new KDTreeIndex(fName);
            } catch (Exception e)
            {
                logger.info("Failed to loaded index...  should not accept any queries...");
                indexFailed = true;
                throw new Exception("Failed to load index from '" + fName + "' ", e);
            }


            if (index.isValid)
            {

                logger.info("Index loaded successfully");
                indexValid = true;
                return true;
            } else
            {

                logger.info("Failed to loaded index...  should not accept any queries...");
                indexFailed = true;
                throw new   Exception("Failed to load index from '" + fName + "' ");
                //return new InitResponse(false);
            }

        }
        

    }   */


    public static void load_common()
    {
        logger.debug("load_common()...");
        if (commonState == null)
        {
            logger.debug("initializeCommon()...");

            initializeCommon();
        }
        //dummy place holder
    }

    /*
    public static void load_index() throws Exception
    {
        if (index == null || indexValid == false)
            initializeIndex(indexFileName);
    } */


    /*
    public static void load_common()
    {

        StopWatch ct = new StopWatch();
        ct.start();
        logger.trace("load_common()");

        

        // Try to retrieve the persisted resource from disk
        //File file = getKeyAsFile(key.getValue());
        File file = new File("/home/iraicu/.globus/persisted/GPWS/GenericPortalCommonResource.dat");
        
        //If the file does not exist, no resource with that key was ever
        //persisted
        
        if (!file.exists()) {
        logger.debug("No Common Resource Exists, loading stopped.");
        initializeCommon();
        store_common();
        return;
            //throw new NoSuchResourceException();
        }

        
        //We try to initialize the resource. This places default Values in the
        //RPs. We still have to load the Values of the RPs from disk
        
        //try {
            //initialize(key.getValue());
        //} catch (Exception e) {
        //    throw new ResourceException("Failed to initialize resource", e);
        //}

        //Now, we open the resource file and load the numJobss
        logger.debug("Attempting to load common resource ");

        // We will use this to read from the file
        FileInputStream fis = null;

        // We will store the RPs in these variables
        //GPResourceCommon cs;

        try {
            //Open the file
            fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Read the RPs
            commonState = (GPResourceCommon) ois.readObject(); 
            //logger.debug("Common resource read successful from disk...");




            // Assign the RPs to the resource class's attributes
            //commonState = cs;



        } catch (Exception e) 
        {
            //throw new ResourceException("Failed to load resource", e);
            logger.debug("Failed to load common resource: " + e);
        } finally {
            //Make sure we clean up, whether the load succeeds or not
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception ee) {
                }
            }
        }

        logger.debug("Successfully loaded resource from file '" + file.toString() + "'");

        ct.stop();
        if(DIPERF) logger.warn("GPResourceHome:load_common(): " + ct.getElapsedTime() + " ms");
        ct.reset();
    }
    */


    public static void store_common()
    {
        //dummy place holder
    }

    /*
    public static void store_common()
    {

        StopWatch ct = new StopWatch();
        ct.start();

        logger.trace("store_common() start...");
        //We will use these two variables to write the resource to disk
        FileOutputStream fos = null;
        File tmpFile = null;

        logger.debug("Attempting to store common state...");
        try {
            // We start by creating a temporary file 
            //tmpFile = File.createTempFile("GenericPortalCommonResource", ".tmp",
        //		    getPersistenceHelper().getStorageDirectory());
            tmpFile = new File("/home/iraicu/.globus/persisted/GPWS/GenericPortalCommonResource.tmp");
            //We open the file for writing
            fos = new FileOutputStream(tmpFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            //We write the RPs in the file 
            oos.writeObject(commonState);


           // oos.writeObject(this.commonState);


            oos.flush();
        } catch (Exception e) {
            //Delete the temporary file if something goes wrong 
            tmpFile.delete();
            //throw new ResourceException("Failed to store common resource", e);
            logger.debug("Failed to store common resource: " + e);
        } finally {
            //Clean up 
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception ee) {
                }
            }
        }

        
         //We have successfully created a temporary file with our resource's
         //RPs. Now, if there is a previous copy of our resource on disk, we
         //first have to delete it. Next, we rename the temporary file to the
         //file representing our resource.
         
        //File file = getKeyAsFile(this.key);
        
        File file = new File("/home/iraicu/.globus/persisted/GPWS/GenericPortalCommonResource.dat");

        if (file.exists()) {
            file.delete();
        }
        if (!tmpFile.renameTo(file)) {
            tmpFile.delete();
            //throw new ResourceException("Failed to store resource");
            logger.error("Failed to store common resource");
            return;
        }
        //System.out.println("Attempting to store common state...");
        logger.debug("Successfully stored resource to file '" + file.toString() + "'");

        ct.stop();
        if(DIPERF) logger.warn("GPResourceHome:store_common(): " + ct.getElapsedTime() + " ms");
        ct.reset();
    }
    */

    public String getInstanceServicePath()
    {
        logger.debug("ResourceHomeImpl getInstanceServicePath()");

        return instanceServicePath;
    }

    public void setInstanceServicePath(String instanceServicePath)
    {
        logger.debug("ResourceHomeImpl setInstanceServicePath()");
        this.instanceServicePath = instanceServicePath;
    }



    public boolean sendNotification(ResourceKey key, String dest)
    {
        //    boolean CACHE_GRID_GLOBAL = true;
        logger.debug("sendNotification(ResourceKey key, String dest): attempting to send notification with key " + key + " to " + dest + "...");
        if (this.notification.send(dest, key))
        {
            //logger.debug("sendNotification() success!");
            logger.debug("sendNotification(ResourceKey key, String dest): notification sent succesfuly with key " + key + " to " + dest + "!");


            return true;
        }
        else
        {

            if (commonState == null)
            {
                load_common();
            }

            //should be here to figure out incorect working workers...
            //logger.debug("sendNotification() failed... deRegistering worker " + dest);
            //commonState.deRegister(dest, CACHE_GRID_GLOBAL);
            logger.debug("sendNotification(ResourceKey key, String dest): notification sent failed with key " + key + " to " + dest + "!");
            return false;

        }

    }


    public boolean sendNotification_old(GPResource gpResource, Task task)
    //public boolean sendNotification(String key)
    {


        StopWatch ct = new StopWatch();


        // = false;
        //boolean test = false;
        while (true)
        {

            if (commonState == null)
            {
                load_common();
            }


            String next = null;
            //String next = this.commonState.getNextWorker();
            if (task != null)
            {
                next = this.commonState.getNextFreeBestWorker(task);
            }
            else
                next = this.commonState.getNextFreeWorker();
            //this should never happen
            if (next == null)
            {
                //no workers registered
                //return false;
                next = this.commonState.getNextFreeWorker();
                //this call blocks....

            }
            //else
            //{
            ct.start();
            if (this.notification.send(next, gpResource.resourceKey))
            {
                //this.notification.sendSize(key);
                logger.debug("sendNotification() success!");

                if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPResourceHome:sendNotification(ResourceKey): " + ct.getElapsedTime() + " ms");



                return true;
            }
            else
            {
                logger.debug("sendNotification() failed to " + next + "...");
                this.commonState.setFreeWorker(next);

                //notification failed, should remove the failed worker
                //logger.debug("sendNotification() failed... deRegistering worker " + next);
                //commonState.deRegister(next, CACHE_GRID_GLOBAL); //try again to the next worker

                return false;
            }
            //}
            //return true;
        }
        //return false;
    }


    //should send the unique ID, a string... instead of a key...
    public boolean sendNotificationUser(String msg, String dest)
    {
        StopWatch ct = new StopWatch();

        ct.start();
        if (this.notification.sendString(dest, msg))
        {
            logger.debug("sendNotificationUser() success!");

            if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPResourceHome:GPResourceHome:sendNotificationUser(msg,dst): " + ct.getElapsedTime() + " ms");

            return true;
        }
        else
        {
            if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPResourceHome:sendNotificationUser(msg,dst): " + ct.getElapsedTime() + " ms");
            logger.debug("sendNotificationUser() failed!");

            return false;

        }

    }

    //should send the unique ID, a string... instead of a key...
    public boolean sendNotificationUsers(String msg[], String dest)
    {
        StopWatch ct = new StopWatch();

        ct.start();
        if (this.notification.sendStrings(dest, msg))
        {
            logger.debug("sendNotificationUser() success!");

            if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPResourceHome:GPResourceHome:sendNotificationUser(msg,dst): " + ct.getElapsedTime() + " ms");

            return true;
        }
        else
        {
            if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPResourceHome:sendNotificationUser(msg,dst): " + ct.getElapsedTime() + " ms");
            logger.debug("sendNotificationUser() failed!");

            return false;

        }

    }


    public boolean sendNotificationUserRetry(String msg, String dest)
    {
        int maxNumRetries = 30;
        int retries = 1;

        while (retries <= maxNumRetries)
        {
            logger.debug("sendNotificationUser() try #" + retries);
            if (this.notification.sendString(dest, msg))
            {
                logger.debug("sendNotificationUser() success!");

                return true;
            }
            else
            {
                try
                {
                    Thread.sleep(10);
                }
                catch (Exception e)
                {

                    logger.debug("sendNotificationUser() error: " + e);
                    if (logger.isDebugEnabled()) e.printStackTrace();

                }

                retries++;
            }
        }
        logger.debug("sendNotificationUser() failed after " + retries + " retries!");

        return false;

    }

    public boolean sendNotificationTCPCore(GPResource gpResource)
    {


        StopWatch ct = new StopWatch();


        boolean CACHE_GRID_GLOBAL = false;
        //boolean test = false;
        while (true)
        {

            if (commonState == null)
            {
                load_common();
            }


            String next = null;

            try
            {


                //String next = this.commonState.getNextWorker();
                next = this.commonState.getNextFreeWorker();
                //this should never happen

            }
            catch (Exception e)
            {
                if (logger.isDebugEnabled()) e.printStackTrace();
                return false;

            }


            if (next == null)
            {
                //no workers registered
                return false;

            }
            else
            {
                int maxNumRetries = 1;
                int retries = 1;

                while (retries <= maxNumRetries)
                {


                    ct.start();

                    int taskQsize = 0;


                    try
                    {

                        taskQsize = gpResource.taskQ.size();
                    }
                    catch (Exception e)
                    {
                        //e.printStackTrace();
                        //should set worker back to free
                        this.commonState.setFreeWorker(next);
                        return false;
                    }

                    try
                    {

                        if (taskQsize > 0)
                        {


                            logger.debug("sendNotificationTCPCore(GPResource gpResource): attempting to send notification with key " + gpResource.resourceKey + " to " + next + "...");

                            if (this.tcpCore.workerInstance.sendTasks(next, gpResource, this))
                            {
                                //this.notification.sendSize(key);
                                logger.debug("sendNotificationTCPCore(GPResource gpResource): sent notification succesfully to " + next + "!");

                                if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPResourceHome:sendNotificationTCPCore(GPResource gpResource): " + ct.getElapsedTime() + " ms");



                                return true;
                            }
                            else
                            {
                                //logger.debug("sendNotification() failed (" + retries + " of " + maxNumRetries+ ") to " + next + "... trying again!");
                                logger.debug("sendNotificationTCPCore(GPResource gpResource): sent notification failed to " + next + "... (" + retries + " of " + maxNumRetries+ ")...");



                                try
                                {
                                    Thread.sleep(10);
                                }
                                catch (Exception e)
                                {

                                    //logger.debug("sendNotification() error: " + e);
                                    //e.printStackTrace();

                                }

                                retries++;


                                //return false;
                            }
                        }
                        else
                        {
                            this.commonState.setFreeWorker(next);
                            //notification failed, should remove the failed worker
                            logger.debug("sendNotification() to " + next + " not needed anymore as there are no more tasks...");
                            return true;
                        }
                    }
                    catch (Exception e)
                    {
                        if (logger.isDebugEnabled()) e.printStackTrace();

                        this.commonState.setFreeWorker(next);
                        return false;
                    }
                }
                this.commonState.setFreeWorker(next);
                //notification failed, should remove the failed worker
                logger.debug("sendNotification() failed... deRegistering worker " + next);
                this.commonState.deRegister(next, CACHE_GRID_GLOBAL); //try again to the next worker

            }
            //return true;


            //return false;
        }
    }

    public boolean sendNotification(GPResource gpResource)
    {

        if (logger.isDebugEnabled()) System.out.println("**************** sendNotification(gpResource)...");


        StopWatch ct = new StopWatch();


        boolean CACHE_GRID_GLOBAL = false;
        //boolean test = false;
        while (true)
        {

            if (commonState == null)
            {
                load_common();
            }


            String next = null;

            try
            {


                //String next = this.commonState.getNextWorker();
                next = this.commonState.getNextFreeWorker();
                //this should never happen

            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;

            }


            if (next == null)
            {
                //no workers registered
                return false;

            }
            else
            {
                int maxNumRetries = 5;
                int retries = 1;

                while (retries <= maxNumRetries)
                {


                    ct.start();

                    int taskQsize = 0;


                    try
                    {

                        taskQsize = gpResource.taskQ.size();
                    }
                    catch (Exception e)
                    {
                        //e.printStackTrace();
                        //should set worker back to free
                        this.commonState.setFreeWorker(next);
                        return false;
                    }

                    try
                    {

                        if (taskQsize > 0)
                        {


                            logger.debug("sendNotification(GPResource gpResource): attempting to send notification with key " + gpResource.resourceKey + " to " + next + "...");

                            if (EMULATION)
                            {
                                WorkerWork sourceWorker = new WorkerWork();
                                sourceWorker.setValid(true);
                                sourceWorker.setMachID(next);
                                boolean readFromGPFS = false;
                                //int readFromGPFScount = 0;


                                WorkerWorkResponse wwr = gpResource.dispatchWork(sourceWorker);
                                logger.debug("EMULATION: sendNotification(GPResource gpResource): sent notification succesfully with key " + gpResource.resourceKey + " to " + next + "!");

                                if (wwr.isValid())
                                {
                                    Task tasks[] = wwr.getTasks();

                                    if (tasks != null)
                                    {

                                        long taskDuration = 0;
                                        for (int i=0;i<tasks.length;i++)
                                        {
                                            Executable exec = tasks[i].getExecutable();

                                            //CacheHitMiss chm = (CacheHitMiss)GPResourceHome.commonState.activeTasksCacheHits.remove(exec.getId());
                                            CacheHitMiss chm = (CacheHitMiss)GPResourceHome.commonState.activeTasksCacheHits.get(exec.getId());

                                            if (chm.miss > 0)
                                            {
                                                if (readFromGPFS == false)
                                                {
                                                    commonState.openFileGPFS();
                                                    readFromGPFS = true;
                                                }
                                                
                                                //readFromGPFScount++;


                                                if (logger.isDebugEnabled()) System.out.println("**************** commonState.getOpenFileGPFS() = " + commonState.getOpenFileGPFS());
                                                
                                            }

                                            //if (chm.globalHit > 0)
                                            //{
                                            //    commonState.openFileWorker(chm.globalHitMachID);
                                            //    readFromWorker = chm.globalHitMachID;
                                            //}

                                            taskDuration += commonState.readFileLocal(chm.localHitKB);
                                            taskDuration += commonState.readFilePeer(chm.globalHitKB);
                                            taskDuration += commonState.readFileGPFS(chm.missKB);
                                            //this is where the contention should be added


                                            taskDuration += (long)(exec.getWallTime());
                                        }

                                        if (taskDuration > 0)
                                        {
                                            //fire timer...
                                            if (logger.isDebugEnabled()) System.out.println("**************** Emulation: setting worker " + next + " to busy for " + taskDuration + " ms...");
                                            GPResourceHome.commonState.workerEmulation.schedule(new WorkerEmulate(next, gpResource, GPResourceHome.commonState, tasks, readFromGPFS), new Date(System.currentTimeMillis()+taskDuration));

                                        }
                                        else
                                        {
                                            if (logger.isDebugEnabled()) System.out.println("**************** Emulation: taskDuration == 0, sending results directly back...");
                                            WorkerEmulate we = new WorkerEmulate(next, gpResource, GPResourceHome.commonState, tasks, readFromGPFS);
                                            we.sendResults();
                                            we = null;


                                        }


                                    }
                                }
                                else
                                {
                                    if (logger.isDebugEnabled()) System.out.println("**************** Emulation1: work is not valid...");

                                }

                                return wwr.isValid();
                            }
                            else
                            {
                                if (logger.isDebugEnabled()) System.out.println("**************** Emulation is disabled...");

                                if (this.notification.send(next, gpResource.resourceKey))
                                {
                                    //this.notification.sendSize(key);
                                    logger.debug("sendNotification(GPResource gpResource): sent notification succesfully with key " + gpResource.resourceKey + " to " + next + "!");

                                    if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPResourceHome:sendNotification(ResourceKey): " + ct.getElapsedTime() + " ms");



                                    return true;
                                }
                                else
                                {
                                    //logger.debug("sendNotification() failed (" + retries + " of " + maxNumRetries+ ") to " + next + "... trying again!");
                                    logger.debug("sendNotification(GPResource gpResource): sent notification failed with key " + gpResource.resourceKey + " to " + next + "... (" + retries + " of " + maxNumRetries+ ")...");



                                    try
                                    {
                                        Thread.sleep(10);
                                    }
                                    catch (Exception e)
                                    {

                                        //logger.debug("sendNotification() error: " + e);
                                        //e.printStackTrace();

                                    }

                                    retries++;


                                    //return false;
                                }
                            }
                        }
                        else
                        {
                            this.commonState.setFreeWorker(next);
                            //notification failed, should remove the failed worker
                            logger.debug("sendNotification() to " + next + " not needed anymore as there are no more tasks...");
                            return true;
                        }
                    }
                    catch (Exception e)
                    {
                        if (logger.isDebugEnabled()) e.printStackTrace();

                        this.commonState.setFreeWorker(next);
                        return false;
                    }
                }
                //this.commonState.setFreeWorker(next);
                this.commonState.setPendWorker(next);
                //notification failed, should remove the failed worker
                //logger.debug("sendNotification() failed... deRegistering worker " + next);
                //this.commonState.deRegister(next, CACHE_GRID_GLOBAL); //try again to the next worker

            }
            //return true;


            //return false;
        }
    }



    public boolean sendNotification(GPResource gpResource, Task task)
    {

        if (logger.isDebugEnabled()) System.out.println("**************** sendNotification(gpResource,task)...");


        StopWatch ct = new StopWatch();


        boolean CACHE_GRID_GLOBAL = false;
        //boolean test = false;
        while (true)
        {

            if (commonState == null)
            {
                load_common();
            }


            String next = null;
            //String next = this.commonState.getNextWorker();
            if (task != null)
            {
                next = this.commonState.getNextFreeBestWorker(task);
            }
            else
                next = this.commonState.getNextFreeWorker();
            //this should never happen
            if (next == null)
            {
                //no workers registered
                //return false;
                next = this.commonState.getNextFreeWorker();
                //this call blocks....

            }


            if (next == null)
            {
                if (logger.isDebugEnabled()) System.out.println("**************** sendNotification(gpResource,task): no worker registered...");
                //no workers registered
                return false;

            }
            else
            {
                int maxNumRetries = 5;
                int retries = 1;

                while (retries <= maxNumRetries)
                {


                    ct.start();

                    int taskQsize = 0;


                    try
                    {

                        taskQsize = gpResource.taskQ.size() + gpResource.taskPendQ.size();
                    }
                    catch (Exception e)
                    {
                        //e.printStackTrace();
                        //should set worker back to free
                        this.commonState.setFreeWorker(next);
                        if (logger.isDebugEnabled()) System.out.println("**************** sendNotification(gpResource,task): exception " + e.getMessage());
                        return false;
                    }

                    try
                    {

                        if (taskQsize > 0)
                        {

                            if (logger.isDebugEnabled()) System.out.println("**************** sendNotification(gpResource,task): taskQsize > " + taskQsize);

                            logger.debug("sendNotification(GPResource gpResource): attempting to send notification with key " + gpResource.resourceKey + " for task " + task.getExecutable().getId() + " to " + next + "...");


                            if (EMULATION)
                            {
                                if (logger.isDebugEnabled()) System.out.println("**************** sendNotification(gpResource,task): emulation enabled...");
                                WorkerWork sourceWorker = new WorkerWork();
                                sourceWorker.setValid(true);
                                sourceWorker.setMachID(next);
                                boolean readFromGPFS = false;
                                //int readFromGPFScount = 0;


                                if (logger.isDebugEnabled()) System.out.println("**************** sendNotification(gpResource,task): dispatchWork(" + next + ")...");


                                WorkerWorkResponse wwr = gpResource.dispatchWork(sourceWorker);
                                logger.debug("EMULATION: sendNotification(GPResource gpResource): sent notification succesfully with key " + gpResource.resourceKey + " to " + next + "!");

                                if (wwr.isValid())
                                {
                                    if (logger.isDebugEnabled()) System.out.println("**************** sendNotification(gpResource,task): dispatchWork(" + next + "): success...");

                                    Task tasks[] = wwr.getTasks();

                                    if (tasks != null)
                                    {
                                        if (logger.isDebugEnabled()) System.out.println("**************** sendNotification(gpResource,task): dispatchWork(" + next + "): found " + tasks.length + " tasks....");

                                        //long inputDuration = 0;
                                        long taskDuration = 0;
                                        //long outputDuration = 0;
                                        for (int i=0;i<tasks.length;i++)
                                        {
                                            if (logger.isDebugEnabled()) System.out.println("**************** sendNotification(gpResource,task): dispatchWork(" + next + "): processing task " + i + "....");
                                            Executable exec = tasks[i].getExecutable();

                                            CacheHitMiss chm = null;
                                            try

                                            {

                                                //Map.get();
                                                //chm = (CacheHitMiss)GPResourceHome.commonState.activeTasksCacheHits.remove(exec.getId());
                                                chm = (CacheHitMiss)GPResourceHome.commonState.activeTasksCacheHits.get(exec.getId());


                                            }
                                            catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }

                                            if (chm != null && chm.miss > 0)
                                            {
                                                if (logger.isDebugEnabled()) System.out.println("**************** sendNotification(gpResource,task): dispatchWork(" + next + "): cache miss, opening GPFS file...");
                                                if (readFromGPFS == false)
                                                {
                                                    commonState.openFileGPFS();
                                                    readFromGPFS = true;
                                                }
                                                //readFromGPFScount++;
                                                if (logger.isDebugEnabled()) System.out.println("**************** commonState.getOpenFileGPFS() = " + commonState.getOpenFileGPFS());
                                            }
                                            else
                                            {
                                                if (logger.isDebugEnabled()) System.out.println("**************** sendNotification(gpResource,task): dispatchWork(" + next + "): no cache misses...");

                                            }


                                            if (logger.isDebugEnabled()) System.out.println("**************** sendNotification(gpResource,task): dispatchWork(" + next + "): computing time needed to read data and compute time...");

                                            if (chm != null)
                                            {
                                            


                                            taskDuration += commonState.readFileLocal(chm.localHitKB);
                                            taskDuration += commonState.readFilePeer(chm.globalHitKB);
                                            taskDuration += commonState.readFileGPFS(chm.missKB);

                                            if (logger.isDebugEnabled()) System.out.println("**************** localHitKB = " + chm.localHitKB + " globalHitKB = " + chm.globalHitKB + " missKB = " + chm.missKB);


                                            }
                                            else
                                            {
                                                if (logger.isDebugEnabled()) System.out.println("**************** chm == null, localHitKB = 0 globalHitKB = 0 missKB = 0");

                                            }
                                            //this is where the contention should be added
                                            taskDuration += (long)(exec.getWallTime());

                                            if (logger.isDebugEnabled()) System.out.println("**************** sendNotification(gpResource,task): dispatchWork(" + next + "): needs " + taskDuration + " ms to handle both data and compute...");

                                        }

                                        if (taskDuration > 0)
                                        {
                                            //fire timer...
                                            if (logger.isDebugEnabled()) System.out.println("**************** Emulation: setting worker " + next + " to busy for " + taskDuration + " ms...");
                                            GPResourceHome.commonState.workerEmulation.schedule(new WorkerEmulate(next, gpResource, GPResourceHome.commonState, tasks, readFromGPFS), new Date(System.currentTimeMillis()+taskDuration));

                                        }
                                        else
                                        {
                                            if (logger.isDebugEnabled()) System.out.println("**************** Emulation: taskDuration == 0, sending results directly back...");
                                            WorkerEmulate we = new WorkerEmulate(next, gpResource, GPResourceHome.commonState, tasks, readFromGPFS);

                                            we.sendResults();
                                            we = null;


                                        }


                                    }
                                }
                                else
                                {
                                    if (logger.isDebugEnabled()) System.out.println("**************** sendNotification(gpResource,task): dispatchWork(" + next + "): failed...");

                                    if (logger.isDebugEnabled()) System.out.println("**************** Emulation2: work is not valid...");

                                }

                                return wwr.isValid();
                            }
                            else
                            {
                                if (logger.isDebugEnabled()) System.out.println("**************** Emulation2 is disabled...");


                                if (this.notification.send(next, gpResource.resourceKey))
                                {
                                    //this.notification.sendSize(key);
                                    logger.debug("sendNotification(GPResource gpResource): sent notification succesfully with key " + gpResource.resourceKey + " for task " + task.getExecutable().getId() + " to " + next + "!");

                                    if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPResourceHome:sendNotification(ResourceKey): " + ct.getElapsedTime() + " ms");



                                    return true;
                                }
                                else
                                {
                                    //logger.debug("sendNotification() failed (" + retries + " of " + maxNumRetries+ ") to " + next + "... trying again!");
                                    logger.debug("sendNotification(GPResource gpResource): sent notification failed with key " + gpResource.resourceKey + " for task " + task.getExecutable().getId() + " to " + next + "... (" + retries + " of " + maxNumRetries+ ")...");



                                    try
                                    {
                                        Thread.sleep(10);
                                    }
                                    catch (Exception e)
                                    {

                                        //logger.debug("sendNotification() error: " + e);
                                        //e.printStackTrace();

                                    }

                                    retries++;


                                    //return false;
                                }
                            }
                        }
                        else
                        {
                            this.commonState.setFreeWorker(next);
                            //notification failed, should remove the failed worker
                            logger.debug("sendNotification() to " + next + " not needed anymore as there are no more tasks, although we were trying to send notification for task " + task.getExecutable().getId());
                            return true;
                        }
                    }
                    catch (Exception e)
                    {
                        if (logger.isDebugEnabled()) e.printStackTrace();

                        this.commonState.setFreeWorker(next);
                        return false;
                    }
                }
                //this.commonState.setFreeWorker(next);
                this.commonState.setPendWorker(next);
                //notification failed, should remove the failed worker
                //logger.debug("sendNotification() failed... deRegistering worker " + next);
                //this.commonState.deRegister(next, CACHE_GRID_GLOBAL); //try again to the next worker

            }
            //return true;


            //return false;
        }
    }



    public boolean sendNotification_old_good_noemulation(GPResource gpResource, Task task)
    {


        StopWatch ct = new StopWatch();


        boolean CACHE_GRID_GLOBAL = false;
        //boolean test = false;
        while (true)
        {

            if (commonState == null)
            {
                load_common();
            }


            String next = null;
            //String next = this.commonState.getNextWorker();
            if (task != null)
            {
                next = this.commonState.getNextFreeBestWorker(task);
            }
            else
                next = this.commonState.getNextFreeWorker();
            //this should never happen
            if (next == null)
            {
                //no workers registered
                //return false;
                next = this.commonState.getNextFreeWorker();
                //this call blocks....

            }


            if (next == null)
            {
                //no workers registered
                return false;

            }
            else
            {
                int maxNumRetries = 5;
                int retries = 1;

                while (retries <= maxNumRetries)
                {


                    ct.start();

                    int taskQsize = 0;


                    try
                    {

                        taskQsize = gpResource.taskQ.size() + gpResource.taskPendQ.size();
                    }
                    catch (Exception e)
                    {
                        //e.printStackTrace();
                        //should set worker back to free
                        this.commonState.setFreeWorker(next);
                        return false;
                    }

                    try
                    {

                        if (taskQsize > 0)
                        {


                            logger.debug("sendNotification(GPResource gpResource): attempting to send notification with key " + gpResource.resourceKey + " for task " + task.getExecutable().getId() + " to " + next + "...");



                                if (this.notification.send(next, gpResource.resourceKey))
                                {
                                    //this.notification.sendSize(key);
                                    logger.debug("sendNotification(GPResource gpResource): sent notification succesfully with key " + gpResource.resourceKey + " for task " + task.getExecutable().getId() + " to " + next + "!");

                                    if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPResourceHome:sendNotification(ResourceKey): " + ct.getElapsedTime() + " ms");



                                    return true;
                                }
                                else
                                {
                                    //logger.debug("sendNotification() failed (" + retries + " of " + maxNumRetries+ ") to " + next + "... trying again!");
                                    logger.debug("sendNotification(GPResource gpResource): sent notification failed with key " + gpResource.resourceKey + " for task " + task.getExecutable().getId() + " to " + next + "... (" + retries + " of " + maxNumRetries+ ")...");



                                    try
                                    {
                                        Thread.sleep(10);
                                    }
                                    catch (Exception e)
                                    {

                                        //logger.debug("sendNotification() error: " + e);
                                        //e.printStackTrace();

                                    }

                                    retries++;


                                    //return false;
                                }
                        }
                        else
                        {
                            this.commonState.setFreeWorker(next);
                            //notification failed, should remove the failed worker
                            logger.debug("sendNotification() to " + next + " not needed anymore as there are no more tasks, although we were trying to send notification for task " + task.getExecutable().getId());
                            return true;
                        }
                    }
                    catch (Exception e)
                    {
                        if (logger.isDebugEnabled()) e.printStackTrace();

                        this.commonState.setFreeWorker(next);
                        return false;
                    }
                }
                //this.commonState.setFreeWorker(next);
                this.commonState.setPendWorker(next);
                //notification failed, should remove the failed worker
                //logger.debug("sendNotification() failed... deRegistering worker " + next);
                //this.commonState.deRegister(next, CACHE_GRID_GLOBAL); //try again to the next worker

            }
            //return true;


            //return false;
        }
    }


    public boolean sendNotification(ResourceKey key)
    //public boolean sendNotification(String key)
    {


        StopWatch ct = new StopWatch();


        boolean CACHE_GRID_GLOBAL = false;
        //boolean test = false;
        while (true)
        {

            if (commonState == null)
            {
                load_common();
            }


            //String next = this.commonState.getNextWorker();
            String next = this.commonState.getNextFreeWorker();
            //this should never happen
            if (next == null)
            {
                //no workers registered
                return false;

            }
            else
            {
                int maxNumRetries = 5;
                int retries = 1;

                while (retries <= maxNumRetries)
                {
                    logger.debug("sendNotification(ResourceKey key): attempting to send notification with key " + key + " to " + next + "...");

                    ct.start();
                    if (this.notification.send(next, key))
                    {
                        //this.notification.sendSize(key);
                        logger.debug("sendNotification(ResourceKey key): notification sent succesfully with key " + key + " to " + next + "!");

                        if (DIPERF) System.out.println("DIPERF: " + System.currentTimeMillis() + " : GPResourceHome:sendNotification(ResourceKey): " + ct.getElapsedTime() + " ms");



                        return true;
                    }
                    else
                    {
                        logger.debug("sendNotification(ResourceKey key): sendNotification with key " + key + " to " + next + " failed (" + retries + " of " + maxNumRetries+ ") to " + next + "...");


                        try
                        {
                            Thread.sleep(10);
                        }
                        catch (Exception e)
                        {

                            logger.debug("sendNotification() error: " + e);
                            if (logger.isDebugEnabled()) e.printStackTrace();

                        }

                        retries++;


                        //return false;
                    }
                }
                this.commonState.setFreeWorker(next);
                //notification failed, should remove the failed worker
                logger.debug("sendNotification() failed... deRegistering worker " + next);
                this.commonState.deRegister(next, CACHE_GRID_GLOBAL); //try again to the next worker

            }
            //return true;
        }
        //return false;
    }

    /*
    public boolean sendNotificationString(String key)
    {
        //boolean test = false;
        while(true)
        {

        if(commonState == null)
        {   
            load_common();
        }


        String next = this.commonState.getNextWorker();
        if(next == null)
        {
            //no workers registered
            return false;

        }
        else
        {

            if (this.notification.sendString(next, key))
            return true;
            else
            {
            //notification failed, should remove the failed worker
            commonState.deRegister(next); //try again to the next worker
            //return false;
            }
        }
        //return true;
        }
        //return false;
    } */



    /* public int getTotValue() {
         logger.debug("ResourceHomeImpl getTotValue()");

     return totValue;
 }


 public void setTotValue(int tv) {
         logger.debug("ResourceHomeImpl setTotValue()");

     totValue += tv;
 }    */


    /*
    public void setWorkerResource(GPResource gprw) {
            logger.debug("ResourceHomeImpl setWorkerResource()");

        gpResourceWorker = gprw;
    }


    public GPResource getWorkerResource() {
            logger.debug("ResourceHomeImpl getWorkerResource()");
                if (gpResourceWorker == null) 
                {
                    logger.debug("Worker resources not initialized");
                }
        return gpResourceWorker;
    }
        */
}


/*
class GPNotificationThread extends Thread {

    GPResourceHome GPResHome;
    public static int sleepTime = 1000; 
    public GPResource gpResourceWorker = null; 

    public GPThread(GPResourceHome GPrh) 
    {
        super("GP Notification Thread");
        this.GPResHome = GPrh;

    }
    public void run() 
    {
        while (gpResourceWorker == null) 
        {
            try 
            {

                GPResHome.logger.debug("GPNotificationThread gpResourceWorker == null, no workers have registered yet :( " + e);
                this.sleep(sleepTime);
            }
            catch (Exception e) 
            { 
                GPResHome.logger.error("GPNotificationThread run()... sleep() " + e);
            }
        }


        while (true) 
        {
        
            //System.out.println("THREAD = " + this.getName() + " \nID = " + this.getId() + " \nTime = " + System.currentTimeMillis());
            //GPResHome.logger.debug("GPThread = " + this.getName() + " running; sleeping " + sleepTime + " ms");
            //GPResHome.logger.info("GPThread = " + this.getName() + ": totValue = " + GPResHome.totValue);

            try 
            {
            
                this.sleep(sleepTime);
            }
            catch (Exception e) 
            { 
                GPResHome.logger.error("GPThread = " + this.getName() + ": run() " + e);
            }
        }
        //System.exit(0);
    }
}
*/



