package org.globus.GenericPortal.common;

import java.util.*;
import java.io.*;
import java.lang.*;
import org.globus.GenericPortal.common.*;

import java.util.*;
import java.io.*;
import java.io.File;
import java.lang.*;
import java.util.zip.*;
import java.net.*;


public class WorkerState
{
    public boolean isResetCache = false;
    //Map cache;
    public boolean online = false;
    public int activeWork = 0;
    // Create cache
    //final int MAX_ENTRIES = 100;
    //private Map cache = null;
    public CacheIndex cache = null;
    //private LinkedHashMap cache = null;
    public String persistantCachePathFile = null;
    public String persistantCachePathFileUpdate = null;

    public static final String cachePath1 = "/scratch/local/iraicu2/3DcacheGrid/"; 
    public static final String cachePath2 = "/tmp/iraicu2/3DcacheGrid/"; 
    public String cachePath = null; 
    public String scratchPath = null; 


    String resourceID = null;
    double availableDiskSpace2MB = 0;

    public int MAX_ENTRIES = 0;
    public int MAX_CACHE_SIZE_MB = 0;
    public float loadFactor = 0.75f;
    public boolean LRU = true;

    private static final float MAX_LOCAL_UTILIZATION = 0.9f;
    //private static final long MIN_FREE_SPACE = 1000;
    private static long MIN_FREE_SPACE = 1; //in KB
    private static final float AVERAGE_IMAGE_SIZE = 6.0f;

    public FileUtility fileUtility = null;


    public ObjectOutput outStateUpdate = null;

    Random rand = null;

    public static boolean DEBUG = false;

    public CacheStrategy CS = null;


    public String machName = null;

    public boolean DIPERF = false;
    public boolean PROFILING = false;
    public String DATA_TRACE = null;

    public WorkerState(String data_trace, boolean profiling, boolean diperf, boolean debug, CacheStrategy cs, String machID, boolean isResetCache, int CACHE_SIZE_MB)
    {
        //hack to avoid not finding local scratch space due to some kind of race condition :(...
        this.MAX_CACHE_SIZE_MB = CACHE_SIZE_MB;
        if (DEBUG) System.out.println("setting WorkerState this.MAX_CACHE_SIZE_MB = " + this.MAX_CACHE_SIZE_MB);


        boolean foundScatchSpace = false;
        int numRetries = 0;
        int maxNumRetries = 3;
        while (foundScatchSpace == false && numRetries < maxNumRetries)
        {
            foundScatchSpace = true;
            //must set to false in all error places...
        


        this.isResetCache = isResetCache;


        try
        {
            String tokens[] = machID.split(":");
            if (tokens.length == 2)
            {
                this.machName = tokens[0];
            }
            else
                this.machName = machID;


        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.machName = machID;

        }

        this.DEBUG = debug;
        this.DIPERF = diperf;
        this.PROFILING = profiling;
        this.DATA_TRACE = data_trace;
        rand = new Random();
        if (DEBUG) System.out.println("setting worker state id...");

        try
        {
            this.resourceID = java.net.InetAddress.getLocalHost().getCanonicalHostName();
        }
        catch (Exception e)
        {
            System.out.println("ERROR: java.net.InetAddress.getLocalHost().getHostName() failed " + e);
            e.printStackTrace();
            this.resourceID = "localhost";
        }
        if (DEBUG) System.out.println("worker state id = " + this.resourceID);


        // Create a directory; all non-existent ancestor directories are
        // automatically created

        /*
        if (DEBUG) System.out.println("setting cachePath...");
        if (cachePath == null)
        {

            boolean success = (new File(cachePath1)).mkdirs();
            if (!success)
            {
                boolean exists = (new File(cachePath1)).exists();
                if (exists)
                {
                    cachePath = cachePath1; 
                    // File or directory exists
                }
                // Directory creation failed
            } else
            {
                cachePath = cachePath1;


            }
        }

        if (cachePath != null)
        {

            if (DEBUG) System.out.println("cachePath = " + cachePath);
        } else
        {

            if (DEBUG) System.out.println("cachePath = null");
        }
        */

        /*
        if (cachePath != null)
        {
        
        DiskSpace2 diskTemp = new DiskSpace2(0);
        MIN_FREE_SPACE = diskTemp.getAvailableSpaceMB(cachePath) - 250;
        } */

        DiskSpace2 disk1 = null;
        DiskSpace2 disk2 = null;
        boolean tooLittleSpace1 = true;
        boolean tooLittleSpace2 = true;
        boolean filePathSuccess1 = false;
        boolean filePathSuccess2 = false;


        (new File(cachePath1)).mkdirs();
        (new File(cachePath2)).mkdirs();


        if (cachePath1 != null)
        {
            //disk1 = new DiskSpace2(MIN_FREE_SPACE, cachePath1, DEBUG);
            disk1 = new DiskSpace2(MIN_FREE_SPACE, DEBUG);
            if (disk1.getAvailableSpaceMB(cachePath1) < MIN_FREE_SPACE)
                tooLittleSpace1 = true;
            else
                tooLittleSpace1 = false;

            if (DEBUG) System.out.println("available disk space at " + cachePath1 + " is " + disk1.getAvailableSpaceMB(cachePath1));


        }
        else {
            if (DEBUG) System.out.println("cachePath1 is null");

        }
        if (cachePath2 != null)
        {
            //disk2 = new DiskSpace2(MIN_FREE_SPACE, cachePath2, DEBUG);
            disk2 = new DiskSpace2(MIN_FREE_SPACE, DEBUG);
            if (disk2.getAvailableSpaceMB(cachePath2) < MIN_FREE_SPACE)
                tooLittleSpace2 = true;
            else
                tooLittleSpace2 = false;

            if (DEBUG) System.out.println("available disk space at " + cachePath2 + " is " + disk2.getAvailableSpaceMB(cachePath2));

        }
        else {
            if (DEBUG) System.out.println("cachePath2 is null");

        }

        filePathSuccess1 = (new File(cachePath1)).mkdirs();
        if (!filePathSuccess1)
        {
            filePathSuccess1 = (new File(cachePath1)).exists();
        }

        filePathSuccess2 = (new File(cachePath2)).mkdirs();
        if (!filePathSuccess2)
        {
            filePathSuccess2 = (new File(cachePath2)).exists();
        }


        if (disk1 != null && filePathSuccess1 && !tooLittleSpace1)
        {
            //nothing to do
            //if (disk2 != null)
            //{
            //    disk2.endMonitor();
            //}
            cachePath = cachePath1;
        }
        else if (disk2 != null && filePathSuccess2 && !tooLittleSpace2)
        {
            //if (disk1 != null)
            //{
            //    disk1.endMonitor();
                //disk1 = null;
            //}
            //disk1 = disk2;
            cachePath = cachePath2;

        }
        else
        {
            //failed to find local scratch space
            if (DEBUG) System.out.println("failed to find local scratch space...");
            //System.exit( -1 );
            foundScatchSpace = false;

        }

        if (cachePath != null)
        {

            scratchPath = cachePath + "scratch/";
            if (DEBUG) System.out.println("creating scratchPath " + scratchPath);

            boolean success = (new File(scratchPath)).mkdirs();

            if (success || (new File(scratchPath)).exists())
            {
                if (DEBUG) System.out.println("scratchPath = " + scratchPath);

            }
            else
            {
                if (DEBUG) System.out.println("creating scratchPath failed...");
                //System.exit( -1 );

                foundScatchSpace = false;

            }
        }



        //disk1 = null;
        //disk1 = new DiskSpace2(MIN_FREE_SPACE, cachePath, DEBUG);

        if (DEBUG) System.out.println("setting DiskSpace2...");
        if (cachePath != null)
        {
            this.availableDiskSpace2MB = disk1.getAvailableSpaceMB(cachePath);
        }
        else
        {
            if (DEBUG) System.out.println("Cannot set availableDiskSpace2MB...");

            this.availableDiskSpace2MB = 0;
            //System.exit( -1 );

            foundScatchSpace = false;

        }
        if (DEBUG) System.out.println("available diskspace = " + availableDiskSpace2MB);



        if (DEBUG) System.out.println("setting MAX_ENTRIES...");
        //if (this.resourceID.contains("tg-viz-login"))
        //if (StringUtil.contains(this.resourceID, "tg-viz-login"))
        //{
        //makes sure to leave ample space (10GB) on login nodes
        //    this.MAX_ENTRIES = (int)Math.max(((this.availableDiskSpace2MB-10000)*1.0/AVERAGE_IMAGE_SIZE), 0);
        //} else
        //this.MAX_ENTRIES = (int)Math.max(((this.availableDiskSpace2MB-MIN_FREE_SPACE)*1.0/AVERAGE_IMAGE_SIZE), 0);
        this.MAX_ENTRIES = 10000000; //set to some large #... 
        if (DEBUG) System.out.println("MAX_ENTRIES = " + MAX_ENTRIES);


        if (DEBUG) System.out.println("setting persistantCachePathFile and persistantCachePathFileUpdate...");
        if (cachePath != null)
        {
            this.persistantCachePathFile = cachePath + "persistentCache." + resourceID + ".cache"; 
            this.persistantCachePathFileUpdate = cachePath + "persistentCache." + resourceID + ".update"; 
            if (DEBUG) System.out.println("persistantCachePathFile = " + persistantCachePathFile);
            if (DEBUG) System.out.println("persistantCachePathFileUpdate = " + persistantCachePathFileUpdate);


        }
        else
        {

            this.persistantCachePathFile = null;
            this.persistantCachePathFileUpdate = null;

            if (DEBUG) System.out.println("persistantCachePathFile = null");
            if (DEBUG) System.out.println("persistantCachePathFileUpdate = null");

            //System.exit( -1 );

            foundScatchSpace = false;
        }


        if (DEBUG) System.out.println("initializing FileUtility()...");
        fileUtility = new FileUtility(DATA_TRACE, PROFILING, DIPERF, DEBUG);
        if (DEBUG) System.out.println("initialized FileUtility()!");


        this.CS = cs;


        //if (DEBUG) System.out.println("starting web server for data cache access...");
        //WebServer webServer = new WebServer(59000, 100, scratchPath, null, 1000, 50, DEBUG);
        //webServer.start();


        /*
        NanoHTTPD webServer = null;
        try
        {
            webServer = new NanoHTTPD( scratchPath, 59000, DEBUG );
            webServer.start();
        }
        catch ( IOException ioe )
        {
            System.err.println( "Couldn't start web server, data caches will not be served by this node:\n" + ioe );
            ioe.printStackTrace();
            //System.exit( -1 );
        }
        */

        //create symbolic link to point to the data-diffusion directory
        //ln -s /scratch/local/iraicu/3DcacheGrid/scratch /dev/shm/falkon-diffusion

        int exitValue = -999;
        try
        {
            MyProcess myChild = new MyProcess(DEBUG);
            
            //Runtime.getRuntime().exec("ln -s " + logicalName + " " + response.value);
            String comArgs = new String("ln -f -s "+ scratchPath + " /dev/shm/falkon-diffusion");

            //child = Runtime.getRuntime().exec(comArgs, null, new File(scratchPath));
            //exitValue = -2000;
            exitValue = myChild.exec(comArgs);
            if (exitValue != 0)
            {
                System.err.println( "Couldn't create symbolic link from /dev/shm/falkon-diffusion to " + scratchPath + ", data caches will not be served by this node... exiting!" );
                //System.exit( -1 );

                foundScatchSpace = false;
            }

        }
        catch (Exception e)
        {
            //exitValue = -1111;
            if (DEBUG) System.out.println("WORKER:getCache3(): creation of symbolic link failed with exit code " + exitValue);
            e.printStackTrace();

            foundScatchSpace = false;


        }






        

        //if (disk1 != null)
        //{
        //    disk1.endMonitor();
            //disk1 = null;
        //}

        if (foundScatchSpace == false)
        {
            numRetries++;
            try
            {
            
            Thread.sleep(numRetries*1000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        }

        if (foundScatchSpace == true)
        {
            if (this.availableDiskSpace2MB >= this.MAX_CACHE_SIZE_MB)
            {
                System.out.println("Finished initializing WorkerState() succesfull with a cache size of " + this.MAX_CACHE_SIZE_MB + " MB !!!");
            }
            else
            {

                if (this.availableDiskSpace2MB > 250)
                {
                    System.out.println("Reseting MAX_CACHE_SIZE from " + this.MAX_CACHE_SIZE_MB + " MB to " + this.availableDiskSpace2MB + " MB due to not enough available disk space...");

                    this.MAX_CACHE_SIZE_MB = (int) this.availableDiskSpace2MB;
                }
                else
                {
                
                    System.out.println("Failed to initializing WorkerState() due to not enough available disk space (" + this.availableDiskSpace2MB + " MB) with a cache size of " + this.MAX_CACHE_SIZE_MB + " MB !!!");
                    System.exit(14);
                }

            }

            
            //this.availableDiskSpace2MB
        }
        else
        {
            System.out.println("Failed to initialize WorkerState() :( after " + numRetries + ", exiting...");
            System.exit(15);

        }

        

    }

    /*
    public synchronized void printWorkerState()
    {
        // Iterate over the keys in the map
        Iterator it = cache.keySet().iterator();
        System.out.println("WorkerState: " + resourceID);
        System.out.println("Online: " + online);
        System.out.println("activeWork: " + activeWork);
        System.out.println("persistantCachePathFile: " + persistantCachePathFile);
        System.out.println("availableDiskSpace2MB: " + availableDiskSpace2MB);
        System.out.println("MAX Cache size: " + MAX_ENTRIES);
        System.out.println("Actual Cache size: " + cache.size());

        int i=0;

        String keyArray[] = new String[cache.size()];

        while (it.hasNext())
        {
            // Get key
            keyArray[i] = (String)it.next();
            i++;
        }

        for (int j=0;j<keyArray.length;j++)
        {


            System.out.println(j + ": " + keyArray[j] + " ==> " + getCache(keyArray[j]));
        }

    }  */

// Deletes all files and subdirectories under dir.
    // Returns true if all deletions were successful.
    // If a deletion fails, the method stops attempting to delete and returns false.
    public synchronized boolean deleteDir(File dir)
    {
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++)
            {
                if (DEBUG) System.out.println("deleteDir(): " + children[i]);
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success)
                {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
        //return true;
    }

    public synchronized boolean resetCache()
    {
        // Deletes all files and subdirectories under dir.
        // Returns true if all deletions were successful.
        // If a deletion fails, the method stops attempting to delete and returns false.

        File dir = new File(cachePath);


        boolean retVal = deleteDir(dir);

        (new File(cachePath)).mkdirs();
        (new File(scratchPath)).mkdirs();

        return retVal;

    }


    public synchronized boolean WorkerStateAcquire()
    {
        if (DEBUG) System.out.println("WorkerStateAcquire() starting...");
        DiskSpace2 disk = null;

        if (cachePath != null)
        {

            if (DEBUG) System.out.println("initializing DiskSpace2()...");
            disk = new DiskSpace2(MIN_FREE_SPACE, cachePath, DEBUG);
            if (DEBUG) System.out.println("initialized DiskSpace2()!");
        }

        boolean retVal = false;
        if (persistantCachePathFile != null && persistantCachePathFileUpdate != null && cachePath != null)
        {
            if (isResetCache)
            {
                resetCache();
            }

            // Create a directory; all non-existent ancestor directories are
            // automatically created


            if (DEBUG) System.out.println("testing directory existence at " + cachePath + "...");
            boolean dirExists = (new File(cachePath)).exists();
            if (DEBUG) System.out.println("directory at " + cachePath + ": " + dirExists);




            if (!dirExists)
            {
                if (DEBUG) System.out.println("creating directories at " + cachePath + "...");
                boolean mkdirsTest = (new File(cachePath)).mkdirs();
                if (DEBUG) System.out.println("created directories at " + cachePath + ": " + mkdirsTest);
            }





            if (DEBUG) System.out.println("testing file at " + persistantCachePathFile + "...");
            boolean exists = (new File(persistantCachePathFile)).exists();
            if (DEBUG) System.out.println("testing file at " + persistantCachePathFileUpdate + "...");
            boolean existsUpdate = (new File(persistantCachePathFileUpdate)).exists();
            if (DEBUG) System.out.println("if files exist... load()...");



            if ((exists || existsUpdate) && load())
            {
                if (DEBUG) System.out.println("WorkerStateAcquire(): loaded persistent state from disk!");
                // File or directory exists

                //should check if the cache needs to be shrunk or enlarged based on available disk space and the actual allocated cache...

                this.online = true;
                this.activeWork = 0;
                retVal = true;


            }
            else
            {
                // File or directory does not exist or failed to load()
                if (DEBUG) System.out.println("WorkerStateAcquire(): initializing state...");

                if (resetCache())
                {
                    if (DEBUG) System.out.println("WorkerStateAcquire(): cache reset succesful!");

                }
                else
                    if (DEBUG) System.out.println("WorkerStateAcquire(): cache reset failed!");



                this.availableDiskSpace2MB = disk.getAvailableSpaceMB();
                if (DEBUG) System.out.println("available disk space = " + availableDiskSpace2MB);

                if (DEBUG) System.out.println("setting MAX_ENTRIES...");
                //if (StringUtil.contains(this.resourceID, "tg-viz-login"))
                //{
                //makes sure to leave ample space (10GB) on login nodes
                //    this.MAX_ENTRIES = (int)Math.max(((this.availableDiskSpace2MB-10000)*1.0/AVERAGE_IMAGE_SIZE), 0);
                //} else
                //    this.MAX_ENTRIES = (int)Math.max(((this.availableDiskSpace2MB-MIN_FREE_SPACE)*1.0/AVERAGE_IMAGE_SIZE), 0);
                this.MAX_ENTRIES = 1000000;
                if (DEBUG) System.out.println("MAX_ENTRIES = " + MAX_ENTRIES);

                //this was commented out just for testing purposes :)
                //MAX_ENTRIES = 3;


                //initialize the proper kind of hashmap depending on the caching strategy....
                Map temp_cache = new LinkedHashMap(MAX_ENTRIES, loadFactor, CS.isLRU());
                if (DEBUG) System.out.println("initialized temp_cache!");

                temp_cache = (Map)Collections.synchronizedMap(temp_cache);

                try
                {
                
                cache = new CacheIndex(temp_cache, MAX_ENTRIES, MAX_CACHE_SIZE_MB, CS, cachePath, MIN_FREE_SPACE, DEBUG);
                }
                catch (Exception e)
                {
                    if (DEBUG)
                    {
                        e.printStackTrace();
                    }
                }
                if (cache != null)
                {
                    if (DEBUG) System.out.println("********** initialized CacheIndex succesful!");
                }
                else
                {
                    if (DEBUG) System.out.println("********** initialized CacheIndex failed!");
                }
                




                this.online = true;
                this.activeWork = 0;
                retVal = true;
            }
        }
        else
        {

            if (DEBUG) System.out.println("persistantCachePathFile != null && persistantCachePathFileUpdate != null && cachePath != null failed...");
            retVal = false;
        }


        try
        {
            if (DEBUG) System.out.println("initializing outStateUpdate...");

            outStateUpdate = new ObjectOutputStream(new FileOutputStream(this.persistantCachePathFileUpdate));
        }
        catch (Exception e)
        {
            if (DEBUG) System.out.println("Error in initializing outStateUpdate: " + e);
            retVal = false;
        }


        if (DEBUG) System.out.println("Finished WorkerStateAcquire()!");

        //if (disk != null)
        //{

        //    disk.endMonitor();
            //disk = null;
        //}
        return retVal;


    }

    public synchronized boolean WorkerStateRelease()
    {
        if (this.online == false)
        {
            //nothing to release
            return true;
        }

        if (store())
        {
            this.cache = null;
            this.online = false;
            this.activeWork = 0;
            return true;
        }
        else
        {
            //if store fails, maybe we want to do something else...
            this.cache = null;
            this.online = false;
            this.activeWork = 0;
            return false;
        }


    }


    private synchronized boolean load_dummy()
    {
        return false;
    }


    private synchronized boolean load()
    {

        if (DEBUG) System.out.println("starting load()...");
        try
        {
            // Deserialize from a file
            if (DEBUG) System.out.println("checking if file " + persistantCachePathFile + " exists...");

            File file = new File(persistantCachePathFile);

            if (file.exists())
            {
                if (DEBUG) System.out.println("file " + persistantCachePathFile + " exists!");
                if (DEBUG) System.out.println("get ObjectInputStream...");
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                if (DEBUG) System.out.println("got ObjectInputStream!");
                // Deserialize the object




                try
                {
                
                cache = (CacheIndex) in.readObject();
                }
                catch (Exception e)
                {
                    if (DEBUG)
                    {
                        e.printStackTrace();
                    }
                }
                if (cache != null)
                {
                    if (DEBUG) System.out.println("********** read cache from file succesful!");
                }
                else
                {
                    if (DEBUG) System.out.println("********** read cache from file failed!");
                }

                
                
                //this assumes that the cache data is still in tact, perhaps there should be a check here for that
                //if (this.MAX_ENTRIES > 100)

                //{

                if (cache == null)
                {
                    if (DEBUG) System.out.println("********** cache loaded is empty, trying loadUpdate()...");
                    if (!loadUpdate())
                    {
                        if (DEBUG) System.out.println("loadUpdate() failed... there was no persistent state info stored!");
                        if (DEBUG) System.out.println("ended load()!");
                        return false;
                        //resetCache();
                    }
                    if (DEBUG) System.out.println("loadUpdate() successful!");
                    return true;

                }
                else
                {
                    if (DEBUG) System.out.println("********** cache loaded succesful...");
                    
                }

                //this was commented out just for testing purposes :)
                cache.MAX_ENTRIES = cache.size() + this.MAX_ENTRIES;
                //cache.MAX_ENTRIES = 3;

                if (DEBUG) System.out.println("cache.size() = " + cache.size());
                if (DEBUG) System.out.println("cache.MAX_ENTRIES = " + cache.MAX_ENTRIES);
                this.MAX_ENTRIES = cache.MAX_ENTRIES;
                if (DEBUG) System.out.println("MAX_ENTRIES = " + MAX_ENTRIES);



                //}

                in.close();
                if (DEBUG) System.out.println("closed file stream!");
                if (DEBUG) System.out.println("performing loadUpdate()...");
                boolean loadUpdateTest = loadUpdate();
                if (DEBUG) System.out.println("loadUpdate(): " + loadUpdateTest);
            }
            else
            {
                if (DEBUG) System.out.println("file " + persistantCachePathFile + " does not exists!");
                if (DEBUG) System.out.println("performing loadUpdate()...");
                if (!loadUpdate())
                {
                    if (DEBUG) System.out.println("loadUpdate() failed... there was no persistent state info stored!");
                    if (DEBUG) System.out.println("ended load()!");
                    return false;
                    //resetCache();
                }
            }
            if (DEBUG) System.out.println("ended load()!");

            if (cache != null)
            {
                if (DEBUG) System.out.println("********** persistent state info loaded succesfully!");
                return true;
            }
            else
            {
                if (DEBUG) System.out.println("********** persistent state info loaded failed!");

                return false;
            }
            

            //return true;
        }
        //catch (ClassNotFoundException e)
        //{
       //     System.out.println("Error: load(): " + e);
       //     return false;
       // }
        catch (Exception e)
        {
            if (DEBUG) System.out.println("Error: load(): " + e);
            e.printStackTrace();
            return false;
        }

    }



    public synchronized boolean loadUpdate_dummy()
    {
        return false;

    }


    public synchronized boolean loadUpdate()
    {
        File file = new File(persistantCachePathFileUpdate);

        if (file.exists())
        {
            if (DEBUG) System.out.println("There have been some updates since the last state snapshot, loading...");


            try
            {

                /* We open the file for writing */
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);

                int i=0;
                while (ois.available() > 0)
                {

                    //boolean op = ois.readBoolean();
                    String key = ois.readUTF();
                    String value = ois.readUTF();
                    long size = ois.readLong();
                    if (key != null && value != null)
                    {
                        //if (op)
                        //updateCache(key,value);
                        if (value.length() == 0)
                        {
                            cache.get(key);
                        }
                        else
                        {
                            //the last number should be the file size
                            cache.put(key, value,size);
                        }
                        //else
                        //    removeElementCacheGridGlobal(key,value);
                    }
                    i++;
                }
                if (DEBUG) System.out.println(i + " updates have been applied...");
                ois.close();
                fis.close();

            }
            catch (Exception e)
            {
                if (DEBUG) System.out.println("There was an error in loading the update to the state info: " + e);
                return false;
            }


        }
        else
        {
            if (DEBUG) System.out.println("There have been no updates since the last state snapshot");
            return false;
        }

        if (store())
        {

            File fileUpdate = new File(persistantCachePathFileUpdate);

            if (fileUpdate.exists())
            {
                fileUpdate.delete();
            }

            //delete temp file

        }
        if (cache != null)
        {
            if (DEBUG) System.out.println("********** loadUpdate() succesfully!");
            return true;
        }
        else
        {
            if (DEBUG) System.out.println("********** loadUpdate() failed!");

            return false;
        }
        
    }


    public synchronized boolean store_dummy()
    {
        return true;
    }

    public synchronized boolean store()
    {
        try
        {

            outStateUpdate.close();
        }
        catch (Exception e)
        {
            if (DEBUG) System.out.println("Error in closing outStateUpdate: " + e);
        }


        FileOutputStream fos = null;
        File tmpFile = null;


        if (DEBUG) System.out.println("Attempting to store state to disk... ");
        try
        {
            /* We start by creating a temporary file */


            tmpFile = File.createTempFile("persistentCache." + resourceID , ".tmp", new File(cachePath));
            /* We open the file for writing */
            fos = new FileOutputStream(tmpFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            /* We write the RPs in the file */
            if (DEBUG) System.out.println("writing state to disk... ");
            oos.writeObject(cache);
            if (DEBUG) System.out.println("state written to temporary file successful!");
            oos.close();


            // oos.writeObject(this.commonState);


            oos.flush();
        }
        catch (Exception e)
        {
            /* Delete the temporary file if something goes wrong */
            tmpFile.delete();
            if (DEBUG) System.out.println("state written to temporary file failed!");
            return false;
            //throw new ResourceException("Failed to store resource", e);
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
        File file = new File(persistantCachePathFile);

        if (file.exists())
        {
            file.delete();
        }
        if (!tmpFile.renameTo(file))
        {
            tmpFile.delete();

            if (DEBUG) System.out.println("Failed to store state to disk!");
            return false;
            //throw new ResourceException("Failed to store resource");
        }


        if (DEBUG) System.out.println("State written to disk successful!");
        if (DEBUG) System.out.println("Cleaning up the updated state... not needed anymore :)");
        File fileUpdates = new File(persistantCachePathFileUpdate);
        if (fileUpdates.exists())
        {
            fileUpdates.delete();
        }
        if (DEBUG) System.out.println("Updated state cleaned up successful!");

        return true;

    }

    public synchronized boolean storeUpdate_dummy(String key, String value)
    {
        return true;
    }

    public synchronized boolean storeUpdate(String key, String value, long size)
    {
        if (outStateUpdate != null)
        {


            try
            {
                // Serialize to a file
                //the next 3 lines does the trick

                if (DEBUG) System.out.println("storeUpdate(key, value): (" + key + ", " + value + ")...");
                //outStateUpdate.writeBoolean(op);
                outStateUpdate.writeUTF(key);
                outStateUpdate.writeUTF(value);
                outStateUpdate.writeLong(size);
                outStateUpdate.flush();

                //if (DEBUG) System.out.println("state written to disk successful!");

                // Serialize to a byte array
                //ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
                //out = new ObjectOutputStream(bos) ;
                //out.writeObject(object);
                //out.close();

                // Get the bytes of the serialized object
                //byte[] buf = bos.toByteArray();
                return true;
            }
            catch (IOException e)
            {
                if (DEBUG) System.out.println("Error: storeUpdate(): " + e);
                e.printStackTrace();
                return false;
            }

        }
        else
        {
            //if (DEBUG) System.out.println("StoreUpdate() has not been properly initialized... not storing any of the updated state...");
            return false;
        }

    }

    /*
    private boolean updateCache(String key, String value)
    {
        // Add to cache
        //Object key = "key";
        try
        {
            boolean exists = (new File(value)).exists();
            if (!exists) 
                {
                // File or directory does not exist
                if (fileUtility.uncompressFile(key, value))
                {

                    ////cache.put(key, value);
                    //storeUpdate(key,value );

                }
            }
            else
            {
                
                ////cache.put(key, value);

                //storeUpdate(key,value );
            }


            
        } catch (Exception e)
        {
            System.out.println("Error: " + e);
            return false;

        }
        return true;



    }    */

    //this might not be unique, if many files are involved... should use something else, perhaps concatinate it with a random #...
    private String getUniqueFileName(String key)
    {
        //return cachePath + "image_cache-" + key.hashCode() + "-" + rand.nextInt() + ".fit"; 
        return cachePath + "data_" + key.hashCode() + ".cache"; 
    }

    /*
    public String getCache(String key)
    {
        String value = cache.get(key);
        String evictedEntry = null;
        if (value == null)
        {
            value = getUniqueFileName(key);
            ////evictedEntry = cache.put(key, value);

            storeUpdate(key,value );
            fileUtility.uncompressFile(key, value);
        } else
        {
            storeUpdate(key, "" );

            boolean exists = (new File(value)).exists();
            if (!exists)
            {
                fileUtility.uncompressFile(key, value);
            }
        }
        return value;
    }


    public CacheResp getCache2_old(String key)
    {
        CacheResp response = new CacheResp();
        response.value = cache.get(key);
        if (response.value == null)
        {
            response.value = getUniqueFileName(key);
            ////response.evictedKey = cache.put(key, response.value);

            storeUpdate(key,response.value );
            fileUtility.uncompressFile(key, response.value);
        } else
        {
            storeUpdate(key, "" );

            boolean exists = (new File(response.value)).exists();
            if (!exists)
            {
                fileUtility.uncompressFile(key, response.value);
            }
        }
        return response;
    }
    */

    /*
    //logic is broken :(
    public CacheResp getCache2(String key)
    {
        CacheResp response = new CacheResp();
        response.value = cache.get(key);



        if (response.value == null)
        {
            response.value = getUniqueFileName(key);

            boolean exists = (new File(response.value)).exists();
            boolean cachePopulate = false;
            if (!exists)
            {
                cachePopulate = fileUtility.uncompressFile(key, response.value);
            }

            //only update if cachePopulate was succesful
            if (cachePopulate)
            {
                storeUpdate(key,response.value );
                ////response.evictedKey = cache.put(key, response.value);
            } else
                response.evictedKey = null;

        } else
        {
            storeUpdate(key,"" );

            boolean exists = (new File(response.value)).exists();
            boolean cachePopulate = false;
            //this should never happen
            if (!exists)
            {
                cachePopulate = fileUtility.uncompressFile(key, response.value);
            }
        }


        return response;
    }    */




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

    public CacheResp[] getCache3(String logicalName/*key*/, String fileURL, String cacheLocations[])

    {
        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 1"); }

        try
        {
        
        //Process child = null;
        MyProcess myChild = new MyProcess(DEBUG);
        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 2"); }
        StopWatch sw = new StopWatch();
        sw.start();
        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 3"); }
        if (fileURL != null)
        {
            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 4"); }

            if (DEBUG) System.out.println("getCache3(String logicalName/*key*/, String fileURL, String cacheLocations[]): " + logicalName + " ==> " + fileURL);
        }
        else
        {
            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 5"); }

            if (DEBUG) System.out.println("getCache3(String logicalName/*key*/, String fileURL, String cacheLocations[]): " + logicalName + " ==> null");
        }
        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 6"); }
        CacheResp response = new CacheResp();

        try
        {
        
        response.value = cache.get(logicalName);
        }
        catch (Exception e)
        {
            if (logicalName != null)
            {
                if (DEBUG) System.out.println("error looking up " + logicalName + " in the cache: " + e.getMessage());

            }
            else
            {
                if (DEBUG) System.out.println("error looking up 'null' in the cache: " + e.getMessage());

            }
            if (DEBUG) e.printStackTrace();

            
        }

        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 7"); }
        sw.stop();
        if (DIPERF) System.out.println("WORKER:WorkerState():cache.get(logicalName): " + sw.getElapsedTime() + " ms");
        sw.reset();

        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 8"); }


        boolean exists = false;
        boolean cachePopulate = false;
        double cachePopulateExitCode = -1;
        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 9"); }

        sw.start();
        if (response.value != null)
            exists = (new File(response.value)).exists();
        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 10"); }

        if (exists)
        {
            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 11"); }
            response.evictedKey = null;
            response.success = true;
        }

        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 12"); }

        if (response.value != null)
        {
            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 13"); }
            if (DEBUG)
            {
                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 14"); }

                System.out.println("cache.get(logicalName): " + response.value + " " + exists);
            }
            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 15"); }
        }
        else
        {
            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 16"); }
            if (DEBUG)
            {

                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 17"); }
                System.out.println("cache.get(logicalName): null");
            }
        }
        sw.stop();
        if (DIPERF) System.out.println("WORKER:WorkerState():(new File(response.value)).exists(): " + sw.getElapsedTime() + " ms");
        sw.reset();

        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 18"); }

        String responsesArray[] = null;

        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 19"); }

        if (response.value == null || !exists)
        {
            sw.start();
            //String fileURL, String cacheLocations[];
            long fileLength = -1;
            //if (cacheLocations == null || (cacheLocations != null && cacheLocations.length == 0))
            //{
            //    fileLength = fileUtility.getFileLength(logicalName, cacheLocations);
            //}
            //else
            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 20"); }
            fileLength = fileUtility.getFileLength(fileURL, cachePath, logicalName, cacheLocations);

            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 21"); }

            //if (fileLength < 0)
            //{
            //    fileLength = fileUtility.getFileLength(logicalName, cacheLocations);

            //}


            try
            {
                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 22"); }
            
            if (fileURL != null && cacheLocations != null)
            {
                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 23"); }
                if (DEBUG) System.out.println("fileUtility.getFileLength(fileURL, cacheLocations): " + logicalName + " at " + fileURL + " and " + (cacheLocations.length) + " cached locations is " + fileLength + " bytes!");
            }
            else if (fileURL != null)
            {
                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 24"); }
                if (DEBUG) System.out.println("fileUtility.getFileLength(fileURL, cacheLocations): " + logicalName + " at " + fileURL + " and no cached locations is " + fileLength + " bytes!");
            }
            else if (cacheLocations != null)
            {
                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 25"); }
                if (DEBUG) System.out.println("fileUtility.getFileLength(fileURL, cacheLocations): " + logicalName + " at " + (cacheLocations.length) + " cached locations is " + fileLength + " bytes!");
                if (DEBUG)
                {
                    if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 26"); }
                    for (int i=0;i<cacheLocations.length;i++)
                    {
                        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 27"); }
                        System.out.println("fileUtility.getFileLength(fileURL, cacheLocations): cache_location_"+i+": " + cacheLocations[i]);

                    }

                }
                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 28"); }
            }
            else
            {
                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 29"); }
                if (DEBUG) System.out.println("fileUtility.getFileLength(fileURL, cacheLocations): " + logicalName + " at no locations is " + fileLength + " bytes... this should never happen, as the needed data to complete the task will never be available!");

            }
            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 30"); }
            }
            catch (Exception e)
            {
                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 31"); }
                if (DEBUG) e.printStackTrace();
            }


            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 32"); }

            sw.stop();
            if (DIPERF) System.out.println("WORKER:WorkerState():fileUtility.getFileLength(fileURL, logicalName, cacheLocations): " + sw.getElapsedTime() + " ms");
            sw.reset();

            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 33"); }


            //if ((fileURL == null && cacheLocations == null) || (fileURL == null && cacheLocations != null && cacheLocations.length <= 0))
            //{
            //    if (DEBUG) System.out.println("getCache3(): " + logicalName + " does not exist anywhere...");
            //   cachePopulate = false;

            //} 
            //else if (fileURL != null || (cacheLocations != null && cacheLocations.length > 0))
            //{
            //    fileLength = fileUtility.getFileLength(fileURL, cacheLocations);

            //}


            if (fileLength >= 0)
            {
                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 34"); }
                sw.start();
                double fileLengthMB = fileLength*1.0/(1024.0*1024.0);


                response.value = getUniqueFileName(logicalName);
                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 35"); }
                if (DEBUG) System.out.println("getUniqueFileName(logicalName): " + logicalName + " ==> " + response.value);

                sw.stop();
                if (DIPERF) System.out.println("WORKER:WorkerState():getUniqueFileName(logicalName): " + sw.getElapsedTime() + " ms");
                sw.reset();

                sw.start();

                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 36"); }
                //cleanup cache and make room for incoming cache...
                //times 2 to account for output data as well...

                /*
                System.out.println("cache.getCacheContentsSizeMB() = " + cache.getCacheContentsSizeMB());
                System.out.println("cache.getCacheContentsNum() = " + cache.getCacheContentsNum());
                System.out.println("fileLengthMB = " + fileLengthMB);
                System.out.println("cache.MAX_CACHE_SIZE_MB = " + cache.MAX_CACHE_SIZE_MB);
                */
                
                responsesArray = cache.removeEldest(Math.max(0,cache.getCacheContentsSizeMB() + fileLengthMB - cache.MAX_CACHE_SIZE_MB));

                sw.stop();
                if (DIPERF) System.out.println("WORKER:WorkerState():cache.removeEldest(Math.max(0,cache.getCacheContentsSizeMB()+fileLengthMB-cache.MAX_CACHE_SIZE_MB)): " + sw.getElapsedTime() + " ms");
                sw.reset();

                sw.start();

                if (!exists && cacheLocations != null && cacheLocations.length > 0)
                {
                    if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 37"); }

                    cachePopulateExitCode = fileUtility.getCachedFile(logicalName, cacheLocations, response.value, machName);
                    if (cachePopulateExitCode >= 0)
                    {
                        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 38"); }
                        cachePopulate = true;
                    }
                    else
                    {
                    
                        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 39"); }
                        cachePopulate = false;
                    }
                    if (DEBUG)
                    {
                        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 40"); }

                        for (int i=0;i<cacheLocations.length;i++)
                        {
                            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 41"); }
                            System.out.println("cacheLocations["+i+"] = " + cacheLocations[i]);
                        }
                    }
                }
                sw.stop();
                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 42"); }
                if (DIPERF) System.out.println("WORKER:WorkerState():fileUtility.getCachedFile(logicalName, cacheLocations, response.value, machName): " + sw.getElapsedTime() + " ms");
                sw.reset();


                sw.start();

                if (!cachePopulate)
                {
                    if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 43"); }
                    if (fileURL != null)
                    {
                        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 44"); }
                        cachePopulateExitCode = fileUtility.getFile(fileURL, response.value);

                        if (cachePopulateExitCode >= 0)
                        {
                            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 45"); }
                            cachePopulate = true;
                        }
                        else
                        {
                            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 46"); }

                            cachePopulate = false;
                        }
                        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 47"); }
                        if (DEBUG) System.out.println("fileUtility.getFile(fileURL, response.value): " + fileURL + " , " + response.value + " ==> " + cachePopulate);
                    }
                    else
                    {
                        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 48"); }

                        cachePopulate = false;
                        if (DEBUG) System.out.println("fileUtility.getFile(fileURL, response.value): failed to find file " + response.value + " anywhere...");
                    }

                    if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 49"); }


                }

                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 50"); }
                sw.stop();
                if (DIPERF) System.out.println("WORKER:WorkerState():fileUtility.getFile(fileURL, response.value): " + sw.getElapsedTime() + " ms");
                sw.reset();



                //only update if cachePopulate was succesful
                if (cachePopulate)
                {
                    if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 51"); }
                    sw.start();
                    storeUpdate(logicalName, response.value , fileLength);
                    if (DEBUG) System.out.println("storeUpdate(logicalName, response.value): " + logicalName + " , " + response.value);
                    if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 52"); }
                    response.evictedKey = cache.put(logicalName, response.value, fileLength);
                    if (DEBUG) System.out.println("cache.put(logicalName, response.value): => " + response.evictedKey);
                    if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 53"); }

                    if (DEBUG) System.out.println("exec('ln -f -s " + response.value + " " + logicalName + "')...");
                    if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 54"); }
                    sw.stop();
                    if (DIPERF) System.out.println("WORKER:WorkerState():storeUpdate(logicalName,response.value): " + sw.getElapsedTime() + " ms");
                    sw.reset();
                    if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 55"); }


                    int exitValue = -1111;
                    try
                    {

                        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 56"); }
                        sw.start();
                        //Runtime.getRuntime().exec("ln -s " + logicalName + " " + response.value);
                        String comArgs = new String("ln -f -s "+ response.value + " " + logicalName);

                        //child = Runtime.getRuntime().exec(comArgs, null, new File(scratchPath));
                        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 57"); }
                        //exitValue = -2000;
                        exitValue = myChild.exec(comArgs, null, new File(scratchPath));
                        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 58"); }

                    }
                    catch (Exception e)
                    {
                        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 59"); }
                        //exitValue = -1111;
                        if (DEBUG) System.out.println("WORKER:getCache3(): creation of symbolic link failed with exit code " + exitValue);
                        e.printStackTrace();


                    }

                    if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 60"); }
                    if (DEBUG) System.out.println("Exit value: " + exitValue);
                    if (exitValue == 0)
                    {
                        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 61"); }
                        response.evictedKey = null;
                        response.success = true;


                    }
                    else
                    {
                        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 62"); }
                        response.evictedKey = logicalName;
                        response.success = false;
                    }



                    if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 63"); }

                    if (DEBUG) System.out.println("symbolic linking: => " + response.value + " ==> " + logicalName);

                    sw.stop();
                    if (DIPERF) System.out.println("WORKER:WorkerState():Runtime.getRuntime().exec('ln-f-s'+response.value+''+logicalName,null,newFile(scratchPath)): " + sw.getElapsedTime() + " ms");
                    sw.reset();

                    if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 64"); }

                }
                else
                {
                    if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 65"); }

                    response.evictedKey = null;
                    if (cachePopulateExitCode >= 0)
                    {
                        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 66"); }
                        response.exitCode = 0;
                    }
                    else
                    {
                        if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 67"); }

                        response.exitCode = (int)cachePopulateExitCode;
                    }
                    if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 68"); }
                }

                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 69"); }
                response.success = cachePopulate;
            }
            else
            {
                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 70"); }
                sw.start();
                CacheResp responses[] = new CacheResp[1];
                responses[0] = new CacheResp();

                responses[0].evictedKey = logicalName;
                responses[0].value = response.value;
                responses[0].success = false;

                if (cachePopulateExitCode >= 0)
                {
                    if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 71"); }
                    responses[0].exitCode = 0;
                }
                else
                {
                    if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 72"); }

                    responses[0].exitCode = (int)cachePopulateExitCode;
                }

                sw.stop();
                if (DIPERF) System.out.println("WORKER:WorkerState():filelength<0: " + sw.getElapsedTime() + " ms");
                sw.reset();

                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 73"); }

                return responses;

            }

        }

        if (responsesArray != null)
        {

            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 74"); }
            CacheResp responses[] = new CacheResp[responsesArray.length];

// For a set or list

            if (DEBUG) System.out.println("Preparing " + responsesArray.length + " evicted cache entries...");
            for (int i=0;i<responsesArray.length;i++)
            {
                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 75"); }
                sw.start();
                if (DEBUG) System.out.println("Preparing evicted cache entry # " + i + " : " + responsesArray[i]);
                responses[i] = new CacheResp();

                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 76"); }
                responses[i].evictedKey = responsesArray[i];
                responses[i].value = response.value;
                responses[i].success = response.success;
                responses[i].exitCode = response.exitCode;
                //double check this....
                //responses[i].exitCode = cachePopulateExitCode;

                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 77"); }
                sw.stop();
                if (DIPERF) System.out.println("WORKER:WorkerState():preparingCacheEvictedEntries:" + sw.getElapsedTime() + " ms");
                sw.reset();
                if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 78"); }


            }
            //responses[0] = response;

            if (DEBUG) System.out.println("Fininished preparing evicted cache entries!");

            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 79"); }


            return responses;
        }
        else
        {
            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 80"); }
            CacheResp tResponses[] = new CacheResp[1];
            tResponses[0] = response;

            return tResponses;
        }
        }
        catch (Exception e)
        {
            if (DEBUG) { int linenumber = new Exception().getStackTrace()[0].getLineNumber(); System.out.println("getCache3(logicalName,fileURL,cacheLocations): 81"); }
            if (DEBUG) e.printStackTrace();
            e.printStackTrace();
            return null;

        }
    }

    public int persistCache3(String logicalNames[], String fileURLs[])
    {
        try
        {
        
        boolean success = true;
        int cachePopulateExitCode = 0;
        if (logicalNames != null && fileURLs != null)
        {


            for (int i=0;i<logicalNames.length && success;i++)
            {

                if (fileURLs[i] != null)
                {
                    if (DEBUG) System.out.println(logicalNames[i] + " is being persisted to " + fileURLs[i] + " ...");

                    double exitCode = fileUtility.getFile(scratchPath+logicalNames[i], fileURLs[i]);
                    if (exitCode < 0 && cachePopulateExitCode == 0)
                        cachePopulateExitCode = (int)exitCode;

                    //if (cachePopulateExitCode >= 0)
                    //{
                    //    success = true;
                    //}
                    //else
                    //    success = false;

                }
                else
                {
                    if (DEBUG) System.out.println(logicalNames[i] + " does not need to be persisted...");
                    //cachePopulateExitCode = 0;


                }
            }

            //if (cachePopulateExitCode >= 0)
            //{
            //    return 0;
            //}
            //else
            //return cachePopulateExitCode;
            //return cachePopulateExitCode;//success;
        }
        //else
        //    cachePopulateExitCode = -1002;

        return cachePopulateExitCode;//true;
        }
        catch (Exception e)
        {
            if (DEBUG)
            {
                e.printStackTrace();
                
            }
            return -6789;

        }
    }



    public CacheResp[] prepCache3(String logicalName)
    {
        try
        {
        
        //Process child = null;
        MyProcess myChild = new MyProcess(DEBUG);
        if (DEBUG) System.out.println("prepCache3(String logicalName): " + logicalName);
        CacheResp response = new CacheResp();
        response.value = cache.get(logicalName);
        int exitValue = -3333;


        boolean exists = false;
        if (response.value != null)
            exists = cache.fileExists(response.value);

        if (exists)
        {
            response.evictedKey = null;
            response.success = true;

            long nnb = cache.getFileSize(response.value);
            if (cache.removeFile(response.value))
                cache.setCacheContentSize(nnb,false);
            //(new File(response.value)).delete();
        }
        //else
        //{



        if (response.value == null)
        {
            response.value = getUniqueFileName(logicalName);
        }
            
            if (DEBUG) System.out.println("getUniqueFileName(logicalName): " + logicalName + " ==> " + response.value);

            storeUpdate(logicalName, response.value,0 );
            if (DEBUG) System.out.println("storeUpdate(logicalName, response.value): " + logicalName + " , " + response.value);
            response.evictedKey = cache.put(logicalName, response.value, 0);
            //need to add size after the data is created
            if (DEBUG) System.out.println("cache.put(logicalName, response.value): => " + response.evictedKey);

            if (DEBUG) System.out.println("exec('ln -f -s " + response.value + " " + logicalName + "')...");


            try
            {

                //Runtime.getRuntime().exec("ln -s " + logicalName + " " + response.value);

                StopWatch sw = new StopWatch();
                sw.start();
                String comArgs = new String("ln -f -s "+ response.value + " " + logicalName);

                //child = Runtime.getRuntime().exec(comArgs, null, new File(scratchPath));
                exitValue = myChild.exec(comArgs, null, new File(scratchPath));

            }
            catch (Exception e)
            {
                if (DEBUG) System.out.println("WORKER:getCache3(): creation of symbolic link failed with exit code " + exitValue);


            }

            if (DEBUG) System.out.println("Exit value: " + exitValue);
            if (exitValue == 0)
            {
                response.evictedKey = null;
                response.success = true;


            }
            else
            {
                response.evictedKey = logicalName;
                response.success = false;
            }



            if (DEBUG) System.out.println("symbolic linking: => " + response.value + " ==> " + logicalName);

        //}

        CacheResp responses[] = new CacheResp[1];

        responses[0] = response;



        return responses;
        }
        catch (Exception e)
        {
            if (DEBUG)
            {
                e.printStackTrace();
            }
            return null;
        }
    }


}
