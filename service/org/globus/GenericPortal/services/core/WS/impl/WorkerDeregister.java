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

public class WorkerDeregister extends TimerTask 
{

    static final Log logger = LogFactory.getLog(WorkerDeregister.class);
    public String workerID;
    public GPResource gpResource;
    public GPResourceCommon commonState;

    public WorkerDeregister(String workerID, GPResource gpResource, GPResourceCommon commonState)
    {
        this.workerID = workerID;
        this.gpResource = gpResource;
        this.commonState = commonState;


    }


  public void run() {
      //*****************************************

      try
      {
      
      WorkerDeRegistration wdr = new WorkerDeRegistration();
      wdr.setMachID(workerID);


      WorkerDeRegistrationResponse wdrr = gpResource.deregisterWorker(wdr);

      if (wdrr.isValid())
      {
          logger.debug("success in deregistering worker " + workerID);

      }
      else
      {
          logger.debug("failure in deregistering worker " + workerID);

      }

      wdrr = null;
      wdr = null;
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }

      /*

      logger.debug("listenerNotification(): cleaning up active tasks for " + workerID);

      commonState.cleanupActiveTasks(workerID);
      //*****************************************


      logger.debug("listenerNotification(): de-registering worker " + workerID);
      if (commonState.deRegister(workerID, gpResource.CACHE_GRID_GLOBAL))
      {
      
          gpResource.setDeregisteredWorkers();


      logger.debug("listenerNotification(): updating common state...");
      synchronized(commonState)
      {

          if (commonState.getNum_Resources() > 0)
          {

              commonState.setNum_Resources(commonState.getNum_Resources() - 1);
          }
      }
      }
      
      logger.debug("listenerNotification(): completed....");
      */
  }

}
