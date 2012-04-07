package org.globus.GenericPortal.services.core.WS.impl;

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


public class NotificationObject
{
    public GPResource gpResource = null;
    public String workerID = null;
    public long wallTime = 0;


    public NotificationObject(GPResource gpResource, String workerID, long wallTime)
    {
        this.gpResource = gpResource;
        this.workerID = workerID;
        this.wallTime = wallTime;
    }

}
