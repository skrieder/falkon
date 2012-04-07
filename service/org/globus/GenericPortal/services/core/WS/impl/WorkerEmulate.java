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



import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WorkerEmulate extends TimerTask
{

    static final Log logger = LogFactory.getLog(WorkerEmulate.class);
    public String workerID;
    public GPResource gpResource;
    public GPResourceCommon commonState;
    public Task tasks[];
    public boolean readFromGPFS;
    //public String readFromWorker;

    //public WorkerEmulate(String workerID, GPResource gpResource, GPResourceCommon commonState, Task tasks[], boolean readFromGPFS, String readFromWorker)
    public WorkerEmulate(String workerID, GPResource gpResource, GPResourceCommon commonState, Task tasks[], boolean readFromGPFS)
    {
        this.workerID = workerID;
        this.gpResource = gpResource;
        this.commonState = commonState;
        this.tasks = tasks;
        this.readFromGPFS = readFromGPFS;
        //    this.readFromWorker = readFromWorker;
    }


    public static Object object = new Object();

    public String[] updateCache(CacheLRU cache, Task tasks[])
    {
        if (cache == null)
        {
            return null;
        }



        List evictedCache = new LinkedList();
        for (int i=0;i<tasks.length;i++)
        {
            try
            {

                if (tasks[i].getExecutable().isDataCaching())
                {
                    String data[] = tasks[i].getExecutable().getInputData().getLogicalName();
                    int dataFileSize[] = tasks[i].getExecutable().getInputData().getFileSize();

                    if (data != null)
                    {

                        for (int j=0;j<data.length;j++)
                        {
                            String evictedCacheArray[] = cache.put(data[j],object, dataFileSize[j]);
                            if (evictedCacheArray != null)
                            {
                                for (int k=0;k<evictedCacheArray.length;k++)
                                {
                                    evictedCache.add(evictedCacheArray[k]);
                                }
                            }
                        }
                    }

                    data = tasks[i].getExecutable().getOutputData().getLogicalName();
                    dataFileSize = tasks[i].getExecutable().getOutputData().getFileSize();

                    if (data != null)
                    {

                        for (int j=0;j<data.length;j++)
                        {
                            String evictedCacheArray[] = cache.put(data[j],object, dataFileSize[j]);
                            if (evictedCacheArray != null)
                            {
                                for (int k=0;k<evictedCacheArray.length;k++)
                                {
                                    evictedCache.add(evictedCacheArray[k]);
                                }
                            }
                        }
                    }



                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }



        }

        if (cache != null)
        {
            commonState.workerCacheEmulated.put(this.workerID, cache);
        }
        


        if (evictedCache != null)
        {
        // Create an array containing the elements in a list
    //Object[] objectArray = evictedCache.toArray();
    //return (String[])evictedCache.toArray(new String[evictedCache.size()]);

    return(String[])evictedCache.toArray(new String[evictedCache.size()]);


        }

        else
        {
            return null;
        }

    }

    public void sendResults()
    {

        //*****************************************

        logger.debug("WorkerEmulate(): started...");

        if (logger.isDebugEnabled()) System.out.println("**************** Emulation: worker " + workerID + " woke up, and packaging results...");


        CacheLRU cache = null;


        if (commonState.EMULATED)
        {

            cache = (CacheLRU)commonState.workerCacheEmulated.get(this.workerID);
            if (cache == null)
            {
                cache = new CacheLRU(commonState.EMULATION_MAX_CACHE_SIZE_MB);
                commonState.workerCacheEmulated.put(this.workerID, cache);
            }
        }



        if (tasks != null)
        {
            for (int i=0;i<tasks.length;i++)
            {
                tasks[i].setExitCode(0);

                //Map cacheLRU = commonState.workerCaches.get(workerID);


                try
                {

                    commonState.activeTasksCacheHits.remove(tasks[i].getExecutable().getId());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            if (readFromGPFS)
            {
                //for (int i=0;i<readFromGPFScount;i++)
                //{
                    commonState.closeFileGPFS();
                //}
                
            }

            //if (readFromWorker != null)
            //{
            //    commonState.closeFileWorker(chm.globalHitMachID);
            //}


            int cacheEvictedNum = 0;
            String cacheEvicted[] = null;

            if (commonState.EMULATED)
            {


                cacheEvicted = updateCache(cache, tasks);

                if (cacheEvicted != null)
                {
                    cacheEvictedNum = cacheEvicted.length;
                }

            }

            WorkerResult wr = new WorkerResult();
            wr.setMachID(workerID);
            wr.setNumTasks(tasks.length);
            //task.setExitCode(0);
            //Task tasks = this.tasks;
            //tasks[0] = task;
            wr.setTasks(tasks);
            wr.setValid(true);

            if (commonState.EMULATED && cacheEvictedNum > 0)
            {
                wr.setNumCacheEvicted(cacheEvictedNum);
                wr.setCacheEvicted(cacheEvicted);
            }
            else
            {
                wr.setNumCacheEvicted(0);
                wr.setCacheEvicted(null);

            }
            wr.setShutingDown(false);
            //WorkerResultResponse wrr = gpResource.receiveResults(wr);
            gpResource.receiveResults(wr);


            logger.debug("WorkerEmulate(): completed....");


            if (logger.isDebugEnabled()) System.out.println("**************** Emulation: worker " + workerID + " finished sending results!");
        }

    }


    public void run() {
        sendResults();
    }

}
