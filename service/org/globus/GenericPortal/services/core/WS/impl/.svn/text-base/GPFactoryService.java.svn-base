/*
 * This file is licensed under the terms of the Globus Toolkit Public License
 * v3, found at http://www.globus.org/toolkit/legal/4.0/license-v3.html.
 * 
 * This notice must appear in redistributions of this file, with or without
 * modification.
 */
package org.globus.GenericPortal.services.core.WS.impl;

import java.net.URL;
import java.rmi.RemoteException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.GenericPortal.stubs.Factory.CreateResource;
import org.globus.GenericPortal.stubs.Factory.CreateResourceResponse;
import org.globus.wsrf.Constants;
import org.globus.wsrf.NoResourceHomeException;
import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.ResourceContextException;
import org.globus.wsrf.ResourceHome;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.container.ServiceHost;
import org.globus.wsrf.utils.AddressingUtils;


import org.globus.GenericPortal.common.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.*;

//import org.globus.wsrf.jndi.*;
//import javax.xml.rpc.server.*;


//public class GPFactoryService implements GPConstants {
public class GPFactoryService /*implements ServiceLifecycle, Initializable*/
{

    /*
    
    public void initialize()
    {
        System.out.println("started falkon: initialize");

    }

    public void init(Object o)
    {
        System.out.println("started falkon: init");

    }


    public void destroy()
    {
        System.out.println("stopped falkon: destroy");

    } */  


    public static boolean DIPERF = false;
    public static final Log logger = LogFactory.getLog(GPFactoryService.class);

    /* Implementation of createResource Operation */
    public CreateResourceResponse createResource(CreateResource request)
    throws RemoteException {




        logger.debug("createResource()");

        // Get current size of heap in bytes
    long heapSize = Runtime.getRuntime().totalMemory();
    
    // Get maximum size of heap in bytes. The heap cannot grow beyond this size.
    // Any attempt will result in an OutOfMemoryException.
    long heapMaxSize = Runtime.getRuntime().maxMemory();
    
    // Get amount of free memory within the heap in bytes. This size will increase
    // after garbage collection and decrease as new objects are created.
    long heapFreeSize = Runtime.getRuntime().freeMemory();

    
    //logger.debug("createResource(): heapSize: " + heapSize);
    //logger.debug("createResource(): heapMaxSize: " + heapMaxSize);
    //logger.debug("createResource(): heapFreeSize: " + heapFreeSize);

    logger.debug("createResource(): heapSize: " + heapSize + " heapMaxSize: " + heapMaxSize + " heapFreeSize: " + heapFreeSize + " numThreads " + java.lang.Thread.activeCount());

        StopWatch ct = new StopWatch();
        ct.start();

        GPResourceHome home = null;
        ResourceKey key = null;

        /* First, we create a new GPResource through the GPResourceHome */
        logger.debug("First, we create a new GPResource through the GPResourceHome");
        try
        {
            logger.debug("calling getInstanceResourceHome()...");
            home = (GPResourceHome) getInstanceResourceHome();
            logger.debug("calling home.create()...");
            key = home.create();
        } catch (Exception e)
        {
            throw new RemoteException(e.getMessage(), e);
        }
        EndpointReferenceType epr = null;

        /*
         * We construct the instance's endpoint reference. The instance's
         * service path can be found in the JNDI deploy file (as a parameter to
         * the resource home). This means that we can retrieve it from the
         * GPResourceHome object.
         */

        logger.debug("constructing the EPR...");
        try
        {

            logger.debug("calling ServiceHost.getBaseURL()...");
            URL baseURL = ServiceHost.getBaseURL();
            logger.debug("calling home.getInstanceServicePath()...");
            String instanceService = home.getInstanceServicePath();
            logger.debug("setting instanceURI...");
            String instanceURI = baseURL.toString() + instanceService;
            // The endpoint reference includes the instance's URI and the
            // resource key
            logger.debug("calling AddressingUtils.createEndpointReference(instanceURI, key)...");
            epr = AddressingUtils.createEndpointReference(instanceURI, key);


        } catch (Exception e)
        {
            throw new RemoteException(e.getMessage(), e);
        }

        logger.debug("Finally, return the endpoint reference in a CreateResourceResponse");
        /* Finally, return the endpoint reference in a CreateResourceResponse */
        logger.debug("calling CreateResourceResponse()...");
        CreateResourceResponse response = new CreateResourceResponse();
        logger.debug("calling response.setEndpointReference(epr)...");
        response.setEndpointReference(epr);

        ct.stop();
        if (DIPERF) home.logger.warn("GPFactoryService:createResource(): " + ct.getElapsedTime() + " ms");
        ct.reset();

        return response;
    }

    protected ResourceHome getInstanceResourceHome()
    throws NoResourceHomeException, ResourceContextException {
        ResourceHome home;
        ResourceContext ctx;

        ctx = ResourceContext.getResourceContext();
        String homeLoc = Constants.JNDI_SERVICES_BASE_NAME + ctx.getService()
                         + "/instanceHome";
        try
        {
            Context initialContext = new InitialContext();
            home = (ResourceHome) initialContext.lookup(homeLoc);
        } catch (NameNotFoundException e)
        {
            throw new NoResourceHomeException();
        } catch (NamingException e)
        {
            throw new ResourceContextException("", e);
        }

        return home;
    }
}