//SVN version
package org.globus.GenericPortal.services.core.WS.impl;

import java.util.*;
import java.net.*;
import java.io.*;
import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceIdentifier;
import org.globus.wsrf.ResourceProperties;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.Topic;
import org.globus.wsrf.TopicList;
import org.globus.wsrf.TopicListAccessor;
import org.globus.wsrf.ResourceLifetime;
import org.globus.wsrf.WSRFConstants;
import org.globus.wsrf.impl.ReflectionResourceProperty;
import org.globus.wsrf.impl.ResourcePropertyTopic;
import org.globus.wsrf.impl.SimpleResourcePropertySet;
import org.globus.wsrf.impl.SimpleTopicList;
import org.globus.wsrf.impl.SimpleTopic; 
import org.globus.wsrf.impl.SimpleResourceProperty; 
import org.globus.wsrf.impl.SimpleResourcePropertyMetaData; 
import org.globus.GenericPortal.common.*;
import org.globus.GenericPortal.stubs.GPService_instance.*;

import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.impl.ResourceContextImpl;
import org.globus.wsrf.impl.ResourceHomeImpl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.globus.GenericPortal.common.*;


public class NotificationEngineWorker extends Thread
{

    static final Log logger = LogFactory.getLog(NotificationEngineWorker.class);
    public GPResourceHome gpResourceHome;

    //public static boolean DEBUG=false;

    public static boolean DIPERF = false;

    public static long NOT_TIME_QUANTA = 1000;
    public static int MAX_NOT_PER_SEC = 0;
    private static int notificationsSent = 0;

    public static synchronized void setNotificationsSent()
    {
        notificationsSent++;
        //logger.debug("setNotificationsSent():notificationsSent = " + notificationsSent);

    }
    //public static synchronized int getNotificationsSent()
    public static int getNotificationsSent()
    {
        //logger.debug("getNotificationsSent():notificationsSent = " + notificationsSent);
        return notificationsSent;

    }
    public static synchronized void resetNotificationsSent()
    {
        //logger.debug("resetNotificationsSent():notificationsSent = " + notificationsSent);
        notificationsSent = 0;

    }


    public NotificationEngineWorker(GPResourceHome rescHome)
    {
        super("NotificationEngineUser_Thread");
        this.gpResourceHome = rescHome;
        //this.DEBUG = logger.isDebugEnabled();

        try
        {

            MAX_NOT_PER_SEC = Integer.parseInt((String)GPResourceHome.falkonConfig.get("MAX_NOT_PER_SEC"));
        }
        catch (Exception e)
        {
            if (logger.isDebugEnabled()) e.printStackTrace();
        }

    }

    public void run() 
    {

        StopWatch st = null;
        if (GPResourceHome.perfProfile.PROFILE)
        {

            st = new StopWatch(false, false, true);
            st.start();
        }

        StopWatch ct = new StopWatch();


        int numWorkers = 0;

        try
        {

            if (GPResourceHome.commonState == null)
            {

                GPResourceHome.load_common();

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        ct.start();
        while (true)
        {
            if (GPResourceHome.perfProfile.PROFILE)
            {
                st.stop();
                GPResourceHome.perfProfile.addSample("notification_engine_worker", st.getElapsedTimeMX());
                st.reset();
                st.start();
            }

            GPResource gpResource = null;

            try
            {
                //logger.debug("gpResource.notificationWorkerQ.remove()...");
                gpResource = (GPResource)GPResourceHome.commonState.getNextResource();
                //gpResource = (GPResource)GPResourceHome.commonState.notificationWorkerQ.remove();//GPResourceHome.commonState.notificationQ.waitUntilNotEmpty();

                boolean sentOK = false;

                if (gpResource != null && gpResource.taskQ != null && gpResource.taskPendQ != null && (gpResource.taskQ.size()+gpResource.taskPendQ.size()) > 0)
                //if (gpResource != null)
                {
                    Task task = null;
                    //if (gpResource.DATA_AWARE_SCHEDULER && !gpResource.MAX_CACHE_HIT)
                    if (gpResource.DATA_AWARE_SCHEDULER)
                    {
                        //synchronized(gpResource)
                        //{

                        //task = (Task)gpResource.taskQ.remove();
                        task = (Task)gpResource.taskQ.removeNoWait();
                        if (task == null)
                        {
                            task = (Task)gpResource.taskPendQ.remove();
                        }
                        logger.debug("GPService:NotificationEngine:sendNotification(): removed task " + task.getExecutable().getId() + " from gpResource.taskQ... gpResource.taskQ.size() = " + gpResource.taskQ.size());

                        gpResource.taskPendQ.insert(task);
                        logger.debug("GPService:NotificationEngine:sendNotification(): inserted task " + task.getExecutable().getId() + " into gpResource.taskPendQ... gpResource.taskPendQ.size() = " + gpResource.taskPendQ.size());
                        //}
                    }



                    logger.debug("GPService:NotificationEngine:sendNotification(): key = " + String.valueOf(gpResource.resourceKey) + " ...");
                    //if (gpResourceHome.sendNotification(gpResource.resourceKey))
                    boolean sendNotificationTest = false;

                    if (gpResourceHome.useTCPCore)
                    {
                        sendNotificationTest = gpResourceHome.sendNotificationTCPCore(gpResource);
                    }
                    //else if (gpResource.DATA_AWARE_SCHEDULER && !gpResource.MAX_CACHE_HIT)
                    else if (gpResource.DATA_AWARE_SCHEDULER)
                    {
                        sendNotificationTest = gpResourceHome.sendNotification(gpResource, task);
                    }  /*
                    else if (gpResourceHome.localFork) 
                    {
                        sendNotificationTest = gpResourceHome.sendNotificationLocal(gpResource);
                    }    */
                    else
                    {


                        sendNotificationTest = gpResourceHome.sendNotification(gpResource);
                    }

                    if (sendNotificationTest)
                    {
                        setNotificationsSent();

                        logger.debug("Notification for key "+ String.valueOf(gpResource.resourceKey) + " sent successfully!");
                        //if (gpResource.DATA_AWARE_SCHEDULER)
                        //{

                        //gpResource.taskPendQ.insert(task);
                        //logger.debug("GPService:NotificationEngine:sendNotification(): inserted task into gpResource.taskPendQ... gpResource.taskPendQ.size() = " + gpResource.taskPendQ.size());
                        //}
                        //insert back in to try again later... this is a hack to ensure that we never run out of notification messages :)
                        //if (!gpResourceHome.useTCPCore)
                        //if (gpResource != null && gpResource.taskQ != null && (gpResource.taskQ.size() + gpResource.taskPendQ.size()) > 0 && (gpResource.taskQ.size() + gpResource.taskPendQ.size()) <= GPResourceHome.maxNumNotificationWorkerThreads*2)
                        //{
                        //    logger.debug("there is still work to be collected and we have dipped below the number of notification threads, insert back into the notification queue...");
                        //   GPResourceHome.commonState.notificationWorkerQ.insert(gpResource);
                        //}



                    }
                    else
                    {
                        logger.debug("Notification for key "+ String.valueOf(gpResource.resourceKey) + " failed!");

                        //if (gpResource.DATA_AWARE_SCHEDULER && !gpResource.MAX_CACHE_HIT)
                        if (gpResource.DATA_AWARE_SCHEDULER)
                        {


                            //synchronized(gpResource)
                            //{
                            //gpResource.taskPendQ.removeTask(task);
                            //should insert back at the front... fix this
                            //gpResource.taskQ.insertFront(task);
                            //}
                            //logger.debug("GPService:NotificationEngine:sendNotification(): inserted task " + task.getExecutable().getId() + " into gpResource.taskQ... gpResource.taskQ.size() = " + gpResource.taskQ.size());
                        }




                        //insert back in to try again later...
                        //if ((gpResource.taskQ.size() + gpResource.taskPendQ.size()) > 0)
                        //{
                        //    logger.debug("there is still work to be collected, insert back into the notification queue...");
                        //   GPResourceHome.commonState.notificationWorkerQ.insert(gpResource);


                        //used for throtling notifications...

                        /*
                        try
                        {
                            System.currentTimeMillis();
                            Thread.sleep(20);
                        }
                        catch (Exception sss)
                        {

                        } */
                        // }
                    }

                }


                if (MAX_NOT_PER_SEC > 0 && getNotificationsSent() >= MAX_NOT_PER_SEC)
                {
                    long timeLeft = NOT_TIME_QUANTA - ct.getElapsedTime();
                    if (timeLeft > 20)

                        try
                        {
                            logger.debug(getNotificationsSent() + " notifications sent in " + ct.getElapsedTime() + " ms, sleeping for " + timeLeft + " ms");

                            //System.currentTimeMillis();
                            Thread.sleep(timeLeft);
                        }
                        catch (Exception sss)
                        {

                        }



                }

                if (ct.getElapsedTime() >= NOT_TIME_QUANTA)
                {
                    if (getNotificationsSent() > 0)
                    {

                        logger.debug("***NotificationEngineWorker(): " + getNotificationsSent() + " notifications sent to workers in " + ct.getElapsedTime() + " ms, maximum allowed notifications " + MAX_NOT_PER_SEC + " per " + NOT_TIME_QUANTA + " ms...");
                    }
                    ct.reset();
                    resetNotificationsSent();
                    ct.start();
                }

                //if (GPResourceHome.commonState.resourceQ.size() > 0 && GPResourceHome.commonState.getWaitingTasks() <= 0)
                if (GPResourceHome.commonState.getWaitingTasks() <= 0)
                {
                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (Exception e)
                    {
                        if (logger.isDebugEnabled()) e.printStackTrace();

                    }

                }




            }
            catch (Exception eee)
            {
                logger.debug("Error in NotificationEngineWorker thread: " + eee);
                if (logger.isDebugEnabled()) eee.printStackTrace();
                //continue;

                //if (gpResource != null && gpResource.taskQ != null && gpResource.taskQ.size() + gpResource.taskPendQ.size() > 0)
                //if (gpResource != null && gpResource.taskQ != null)
                //{
                //    logger.debug("there is still work to be collected, insert back into the notification queue...");
                //    GPResourceHome.commonState.notificationWorkerQ.insert(gpResource);
                //}

            }



        }
    }
}
