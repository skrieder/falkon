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


//import KDTree.*;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.GenericPortal.common.*;



public class NotificationEngineUser extends Thread
{

    static final Log logger = LogFactory.getLog(NotificationEngineUser.class);
    public GPResourceHome gpResourceHome;

    //public static boolean DEBUG=false;

    public static boolean DIPERF = false;

    public static long NOT_TIME_QUANTA = 1000;
    public static int MAX_NOT_PER_SEC = 1000000;
    public static int MAX_NOT_PER_MSG = 1000;
    private static int notificationsSent = 0;

    public static synchronized void setNotificationsSent()
    {
        notificationsSent++;
        logger.debug("setNotificationsSent():notificationsSent = " + notificationsSent);

    }
    //public static synchronized int getNotificationsSent()
    public static int getNotificationsSent()
    {
        logger.debug("getNotificationsSent():notificationsSent = " + notificationsSent);
        return notificationsSent;

    }
    public static synchronized void resetNotificationsSent()
    {
        logger.debug("resetNotificationsSent():notificationsSent = " + notificationsSent);
        notificationsSent = 0;

    }


    public NotificationEngineUser(GPResourceHome rescHome)
    {
        super("NotificationEngineUser_Thread");

        this.gpResourceHome = rescHome;
        //this.DEBUG = logger.isDebugEnabled();

    }

    public void run() 
    {

        StopWatch st = null;
        if (GPResourceHome.perfProfile.PROFILE) {

            st = new StopWatch(false, false, true);
            st.start();
        }


        StopWatch ct = new StopWatch();
        int numWorkers = 0;

        try
        {

            if (GPResourceHome.commonState == null)
            {

                //logger.debug("NotificationEngineUser() loaded commonState!");
                GPResourceHome.load_common();
                //throw new RemoteException("workerDeRegistration(): commonState == null ");

            }
            //else
            //numWorkers = GPResourceHome.commonState.numWorkers();
            //logger.debug("Available workers: " + numWorkers);
        } catch (Exception e)
        {
            //logger.debug("get common state failed: " + e);
            e.printStackTrace();
        }

        ct.start();

        StopWatch tt = new StopWatch();

        StopWatch tt2 = new StopWatch();
        //boolean sendNotifications = false;
        //while (!gpResource.lastTime || gpResource.taskQ.size() > 0)



        while (true)
        {

            if (GPResourceHome.perfProfile.PROFILE) {
                st.stop();
                GPResourceHome.perfProfile.addSample("notification_engine_user", st.getElapsedTimeMX());
                st.reset();
                st.start();
            }

            //  if (sendNotifications)
            //{

            //NotificationTask notificationValue = null;
            List notList = new LinkedList();
            String notDest = null;


            try
            {
                /*
      while (GPResourceHome.commonState.notificationQ == null)
      {
          //logger.debug("notificationQ has not ben initialized yet, waiting for to not be null...");
          System.out.println("notificationQ has not ben initialized yet, waiting for to not be null...");
          Thread.sleep(1000);
                  
      }           */

                //System.out.println("gpResource.notificationQ.waitUntilNotEmpty()...");
                //GPResourceHome.commonState.notificationQ.waitUntilNotEmpty();



                logger.debug("gpResource.notificationQ.remove()...");
                boolean sameNotificationEndpoint = true;
                synchronized (GPResourceHome.commonState.notificationQ)
                {
                    while (GPResourceHome.commonState.notificationQ.size() > 0 && sameNotificationEndpoint && notList.size()<MAX_NOT_PER_MSG)
                    {
                        //logger.debug("while (GPResourceHome.commonState.notificationQ.size() > 0 && sameNotificationEndpoint): starting...");

                        NotificationTask notTaskTemp = (NotificationTask)GPResourceHome.commonState.notificationQ.remove();


                        //logger.debug("while (GPResourceHome.commonState.notificationQ.size() > 0 && sameNotificationEndpoint): notDest == null...");
                        if (notDest == null)
                        {

                            notDest = new String((notTaskTemp.task.getExecutable()).getNotification());
                            if (notDest != null)
                            {
                          //      logger.debug("while (GPResourceHome.commonState.notificationQ.size() > 0 && sameNotificationEndpoint): notList.add(notTaskTemp): first time: notDest == " + notDest);
                                notList.add(notTaskTemp);
                            } else
                            {
                                logger.debug("while (GPResourceHome.commonState.notificationQ.size() > 0 && sameNotificationEndpoint): notList.add(notTaskTemp): first time: notDest == null");

                            }

                        } else
                        {


                            if (notDest.compareTo((notTaskTemp.task.getExecutable()).getNotification()) == 0)
                            {
                                //logger.debug("while (GPResourceHome.commonState.notificationQ.size() > 0 && sameNotificationEndpoint): notList.add(notTaskTemp): same notification: notDest == " + notDest);
                                notList.add(notTaskTemp);//GPResourceHome.commonState.notificationQ.waitUntilNotEmpty();
                            } else
                            {
                                //logger.debug("while (GPResourceHome.commonState.notificationQ.size() > 0 && sameNotificationEndpoint): notList.add(notTaskTemp): different notification: notDest == " + (notTaskTemp.task.getExecutable()).getNotification());
                                GPResourceHome.commonState.notificationQ.insert(notTaskTemp);
                                sameNotificationEndpoint = false;
                                //break;

                            }
                        }


                    }

                    //notificationValue = (NotificationTask)GPResourceHome.commonState.notificationQ.remove();//GPResourceHome.commonState.notificationQ.waitUntilNotEmpty();
                }
                if (notList.size() == 0)
                {
                    NotificationTask notTaskTemp = (NotificationTask)GPResourceHome.commonState.notificationQ.remove();
                    if (notTaskTemp != null)
                    {
                        notList.add(notTaskTemp);//GPResourceHome.commonState.notificationQ.waitUntilNotEmpty();
                        notDest = new String((notTaskTemp.task.getExecutable()).getNotification());
                    }
                }

            } catch (Exception eee)
            {
                logger.debug("Error in GPResourceHome.commonState.notificationQ.waitUntilNotEmpty() or GPResourceHome.commonState.notificationQ.remove(): " + eee);
                eee.printStackTrace();  
                continue;

            }

            boolean sentOK = false;

            if (notList.size() > 0)
            //if (notificationValue != null)
            {
                //String tokens[] = notificationValue.split(" ");
                //if (tokens.length == 2)
                //{
                //GPResource gpResource = notificationValue.gpResource;
                //Task task = notificationValue.task;

                String msg[] = new String[notList.size()];

                // For a set or list
                int i=0;
                for (Iterator it=notList.iterator(); it.hasNext(); i++)
                {
                    NotificationTask notificationValue = (NotificationTask)it.next();
                    msg[i] = new String((notificationValue.task.getExecutable()).getId() + " " + notificationValue.task.getExitCode());
                    logger.debug("GPService:NotificationEngineUser:sendNotificationUser(): preparing notification " + msg[i] + " for destination " + (notificationValue.task.getExecutable()).getNotification());
                }

                //for (int i=0;i<msg.length;i++)
                //{
                //    msg[i] = new String();
                //}/



                logger.debug("GPService:NotificationEngineUser:sendNotificationUser(): sending " + notList.size() + " notifications to " + notDest);



                tt.reset();
                tt.start();
                //boolean sendNotTest = gpResourceHome.sendNotificationUser((notificationValue.task.getExecutable()).getId() + " " + notificationValue.task.getExitCode(), (notificationValue.task.getExecutable()).getNotification());
                boolean sendNotTest = false;
                if (msg.length == 1)
                    sendNotTest = gpResourceHome.sendNotificationUser(msg[0], notDest);
                else
                    sendNotTest = gpResourceHome.sendNotificationUsers(msg, notDest);

                tt.stop();

                if (sendNotTest)
                {
                    for (Iterator it=notList.iterator(); it.hasNext();)
                    {
                        NotificationTask notificationValue = (NotificationTask)it.next();

                        //

                        GPResourceHome.commonState.writeLogTask((notificationValue.task.getExecutable()).getId(), "NOTIFICATION_DELIVERED_" + notificationValue.task.getExitCode());

                        tt2.reset();
                        tt2.start();
                        setNotificationsSent();
                        sentOK = true;
                        logger.info("Notification for task "+ (notificationValue.task.getExecutable()).getId() + " with an exit code of " +notificationValue.task.getExitCode() + " sent successfully to "+ (notificationValue.task.getExecutable()).getNotification()+" ("+ tt.getElapsedTime()+ " ms)!");

                        logger.debug("writeLogTaskPerf()...");

                        logger.debug("inserting task performance metrics in hashMap...");
                        TaskPerformance tPerf = (TaskPerformance)notificationValue.gpResource.taskPerf.get((notificationValue.task.getExecutable()).getId());
                        if (tPerf == null)
                        {
                            logger.debug("ERROR: TaskPerformance not found in HashMap for taskID " + (notificationValue.task.getExecutable()).getId() + " although it should have been there... double check why not!");
                            //this should never happen
                            tPerf = new TaskPerformance();

                            tPerf.setTaskID((notificationValue.task.getExecutable()).getId());
                            tPerf.setWorkerID("localhost");
                            tPerf.setWaitQueueTime();
                            tPerf.setWorkerQueueTime();
                            tPerf.setResultsQueueTime();
                        }

                        tPerf.setEndTime();
                        tPerf.setExitCode(notificationValue.task.getExitCode());


                        GPResourceHome.commonState.writeLogTaskPerf(tPerf);

                        notificationValue.gpResource.taskPerf.remove((notificationValue.task.getExecutable()).getId());

                        Integer userMapValue = (Integer)GPResourceHome.commonState.userMap.get((notificationValue.task.getExecutable()).getNotification() + ":" + notificationValue.gpResource.resourceKey.getValue());
                        if (userMapValue == null)
                        {

                            //this sould never happen...
                            //GPResourceHome.commonState.userMap.put(execs[i].getNotification() + ":" + gpResource.resourceKey.getValue(), new Integer(1));
                        } else
                        {
                            if (userMapValue.intValue() <= 1)
                            {
                                GPResourceHome.commonState.userMap.remove((notificationValue.task.getExecutable()).getNotification() + ":" + notificationValue.gpResource.resourceKey.getValue());

                                logger.debug("Removing user persistent socket...");
                                boolean removedPS = GPResourceHome.notification.removePersistentSocket((notificationValue.task.getExecutable()).getNotification());
                                logger.debug("Removed user persistent socket: " + removedPS);

                            } else
                                GPResourceHome.commonState.userMap.put((notificationValue.task.getExecutable()).getNotification() + ":" + notificationValue.gpResource.resourceKey.getValue(), new Integer(userMapValue.intValue() - 1));
                        }

                        GPResourceHome.commonState.writeLogUser();
                        logger.debug("updated user in userMap!");

                        GPResourceHome.commonState.setDeliveredTask();
                        tt2.stop();
                        logger.info("Notification state updates complete ("+ tt2.getElapsedTime()+ " ms)!");

                        notificationValue.task = null;
                        notificationValue = null;

                    }

                    if (notList != null)
                    {
                        notList.clear();
                        notList = null;
                    }
                    

                } else
                {

                    for (Iterator it=notList.iterator(); it.hasNext(); i++)
                    {
                        NotificationTask notificationValue = (NotificationTask)it.next();
                        //insert back in to try again later...
                        try
                        {
                            logger.info("Failed to send notification (" + tt.getElapsedTime()+ " ms), trying again...");
                            logger.debug("gpResource.notificationQ.insert()...");

                            GPResourceHome.commonState.notificationQ.insert(notificationValue);


                        } catch (Exception eee)
                        {
                            logger.debug("Error in GPResourceHome.commonState.notificationQ.waitUntilNotEmpty(): " + eee);
                            eee.printStackTrace();

                        }

                    }
                    //notificationValue.gpResource.taskPerf.put(tPerf.getTaskID(), tPerf);
                    //logger.debug("Notificaiton failed, user should poll to retrieve results...");
                }

                if (getNotificationsSent() >= MAX_NOT_PER_SEC)
                {
                    long timeLeft = NOT_TIME_QUANTA - ct.getElapsedTime();
                    if (timeLeft > 20)
                    {

                        try
                        {
                            //System.currentTimeMillis();

                            logger.debug(getNotificationsSent() + " notifications sent in " + ct.getElapsedTime() + " ms, sleeping for " + timeLeft + " ms");
                            Thread.sleep(timeLeft);

                        } catch (Exception sss)
                        {

                        }

                    }

                }

                if (ct.getElapsedTime() >= NOT_TIME_QUANTA)
                {
                    logger.debug("NotificationEngineUser(): " + getNotificationsSent() + " notifications sent to users in " + ct.getElapsedTime() + " ms, maximum allowed notifications " + MAX_NOT_PER_SEC + " per " + NOT_TIME_QUANTA + " ms...");
                    ct.reset();
                    resetNotificationsSent();
                    ct.start();
                }



                /*
                if (GPResourceHome.commonState.notificationQ.size() > 0)
                {
                    //logger.debug("there is still work to be collected, insert back into the notification queue...");
                    //GPResourceHome.commonState.notificationWorkerQ.insert(gpResource);


                    //used for throtling notifications...

                    try
                    {
                        System.currentTimeMillis();
                        Thread.sleep(20);
                    }
                    catch (Exception sss)
                    {

                    } 
                } */



                //used for throtling notifications...

                /*
                try
                        {
                            Thread.sleep(10);
                        }
                        catch (Exception sss)
                        {

                        }
                        */



                //}
                //else
                //{
                //    sentOK = false;
                //    GPResourceHome.commonState.notificationQ.insert(notificationValue);//GPResourceHome.commonState.notificationQ.waitUntilNotEmpty();

                //}

            }

            /*
            if (notificationValue != null)
            {
                logger.debug("GPService:NotificationEngineUser:error...");
                try
                {
                    long sleepTime = 10;
                    Thread.sleep(sleepTime);
                    logger.debug("GPService:NotificationEngineUser:sleep(): " + sleepTime + " ms");
                } catch (Exception e)
                {
                    logger.debug("Error in sleep of sending notifications: " + e);

                }

            }
              */



            //}
        }
        //logger.debug("GPService:NotificationEngineUser:ne(): finished!");
    }

}
