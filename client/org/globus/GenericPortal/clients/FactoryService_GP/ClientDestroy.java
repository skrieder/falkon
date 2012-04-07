/*
 * Portions of this file Copyright 1999-2005 University of Chicago
 * Portions of this file Copyright 1999-2005 The University of Southern California.
 *
 * This file or a portion of this file is licensed under the
 * terms of the Globus Toolkit Public License, found at
 * http://www.globus.org/toolkit/download/license.html.
 * If you redistribute this file, with or without
 * modifications, you must include this notice in the file.
 */
//package org.globus.wsrf.client;
package org.globus.GenericPortal.clients.FactoryService_GP;

import java.io.*;

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

import javax.xml.rpc.Stub;
import org.globus.wsrf.impl.security.authorization.NoAuthorization;
import org.globus.wsrf.security.Constants;


import org.oasis.wsrf.lifetime.WSResourceLifetimeServiceAddressingLocator;
import org.oasis.wsrf.lifetime.ImmediateResourceTermination;
import org.oasis.wsrf.faults.BaseFaultType;

import org.globus.wsrf.utils.FaultHelper;

import javax.xml.rpc.Stub;

import org.apache.commons.cli.ParseException;

import java.io.*;

import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.GenericPortal.services.core.WS.impl.GPConstants;
import org.globus.GenericPortal.stubs.GPService_instance.GPPortType;
import org.globus.GenericPortal.stubs.GPService_instance.service.GPServiceAddressingLocator;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.xml.sax.InputSource;
import org.globus.wsrf.NotificationConsumerManager;

import org.globus.GenericPortal.stubs.GPService_instance.*;

import org.globus.wsrf.ResourceKey;


import org.globus.wsrf.utils.AddressingUtils;

import org.globus.GenericPortal.common.*;


import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.GenericPortal.services.core.WS.impl.GPConstants;
import org.globus.GenericPortal.stubs.Factory.CreateResource;
import org.globus.GenericPortal.stubs.Factory.CreateResourceResponse;
import org.globus.GenericPortal.stubs.Factory.FactoryPortType;
import org.globus.GenericPortal.stubs.Factory.service.FactoryServiceAddressingLocator;
import org.globus.wsrf.encoding.ObjectSerializer;

public class ClientDestroy 
{

    public static String CLIENT_DESC = null;

    static String eprFilename = "epr.txt";
    static String factoryURI = "localhost";
    static boolean DEBUG = false;
    static boolean DIPERF = false;
    static boolean AUTHENTICATE = false;
    static boolean AUTHORIZE = false;
    static boolean ENCRYPT = false;
    static boolean SIGN = false;
    static boolean TSL = false;
    static boolean MSG = false;
    static boolean CONV = false;

    static {
    Util.registerTransport();
}


    public static void parseArgs(String[] args)
    {
        if (args.length < 1)
        {
            usage(args);
            return;
        }

        int ctr;
        for (ctr = 0; ctr < args.length; ctr++)
        {
            if (args[ctr].equals("-epr") && ctr + 1 < args.length)
            {
                ctr++;
                eprFilename = new String(args[ctr]);
            }

            /*else if (args[ctr].equals("-factoryURI") && ctr + 1 < args.length)
            {
                ctr++;
                factoryURI = new String(args[ctr]);
            }*/ 
            else if (args[ctr].equals("-debug") )
            {
                DEBUG = true;
            } else if (args[ctr].equals("-diperf") )
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

            else if (args[ctr].equals("-help") )
            {
                System.out.println("Help Screen:");
                usage(args);

            } else
            {

                System.out.println("ERROR: invalid parameter - " + args[ctr]);
                System.out.println("Current parameters values:");
                System.out.println("-epr " + eprFilename);
                System.out.println("-factoryURI " + factoryURI);
                System.out.println("-debug " + DEBUG);
                System.out.println("-diperf " + DIPERF);



                usage(args);
            }
        }

    }


    public static void usage(String[] args)
    {
        System.out.println("Help Screen: ");
        System.out.println("-diperf <>");
        System.out.println("-help <>");
        System.exit(0);

    }


    public static EndpointReferenceType getEPR(FileInputStream fis) throws Exception
    {
        return(EndpointReferenceType) ObjectDeserializer.deserialize(new InputSource(fis),EndpointReferenceType.class);
    }


    public static void main(String[] args) 
    {

        StopWatch lt = new StopWatch();
        lt.start();

        parseArgs(args);


        try
        {
            //if (args.length != 2)
            //{
                //usage(args);
            //    System.out.println("usage: java org.globus.GenericPortal.clients.FactoryService_GP.ClientDestroy -epr fileName");
            //    return;
            //}

            //String eprFilename = null;
            EndpointReferenceType EPR = null;


            if (args[0].equals("-epr"))
            {
                eprFilename = new String(args[1]);
                FileInputStream fis = new FileInputStream(eprFilename);
                EPR = getEPR(fis);
                fis.close();
            } else
            {
                System.out.println("usage: java org.globus.GenericPortal.clients.FactoryService_GP.ClientDestroy -epr fileName");
                return;

            }

            //parse args... -epr epr_file        
            WSResourceLifetimeServiceAddressingLocator locator =
            new WSResourceLifetimeServiceAddressingLocator();

            try
            {
                ImmediateResourceTermination port = 
                locator.getImmediateResourceTerminationPort(EPR);

                //authentication
                //((Stub)gpFactory)._setProperty(Constants.AUTHORIZATION,SelfAuthorization.getInstance());

                //no authentication
                //((Stub)gpFactory)._setProperty(Constants.AUTHORIZATION,NoAuthorization.getInstance());

                //GSI Trasnport Conversation + Encryption
                //((Stub)gpFactory)._setProperty(Constants.GSI_SEC_CONV,Constants.ENCRYPTION);
                //GSI Trasnport Conversation + Signature
                //((Stub)gpFactory)._setProperty(Constants.GSI_SEC_CONV,Constants.SIGNATURE);

                //GSI Trasnport Message + Encryption
                //((Stub)gpFactory)._setProperty(Constants.GSI_SEC_MSG,,Constants.ENCRYPTION);
                //GSI Trasnport Message + Signature
                //((Stub)gpFactory)._setProperty(Constants.GSI_SEC_MSG,,Constants.SIGNATURE);


                if (CLIENT_DESC != null && (new File(CLIENT_DESC)).exists())
                {
                    System.out.println("Setting appropriate security from file '" + CLIENT_DESC + "'!");
                    ((Stub)port)._setProperty(Constants.CLIENT_DESCRIPTOR_FILE, CLIENT_DESC);


                }
                /*
                if (AUTHORIZE)
                {
                    //authorization
                    System.out.println("Enable authorization!");
                    ((Stub)port)._setProperty(Constants.AUTHORIZATION,SelfAuthorization.getInstance());
                }
                else
                {
                    //no authorization
                    System.out.println("Disable authorization!");
                    ((Stub)port)._setProperty(Constants.AUTHORIZATION,NoAuthorization.getInstance());
                }

                if (AUTHENTICATE)
                {
                    //authentication
                    System.out.println("Enable authentication!");
                    ((Stub)port)._setProperty(Constants.GSI_ANONYMOUS, Boolean.FALSE );
                }
                else
                {

                //no authentication
                    System.out.println("Disable authentication!");
                    ((Stub)port)._setProperty(Constants.GSI_ANONYMOUS, Boolean.TRUE );
                }

                if (SIGN)
                {
                    if (CONV)
                    {
                        //GSI Transport Conversation + Signature
                        System.out.println("Use GSI Transport Conversation with Signature!");
                        ((Stub)port)._setProperty(Constants.GSI_SEC_CONV,Constants.SIGNATURE);
                    }
                    else if (MSG)
                    {
                        //GSI Transport Message + Signature
                        System.out.println("Use GSI Transport Message with Signature!");
                        ((Stub)port)._setProperty(Constants.GSI_SEC_MSG,Constants.SIGNATURE);
                    }
                    else if (TSL)
                    {
                        //GSI (TSL) Transport Message + Signature
                        System.out.println("Use GSI (TSL) Transport Message with Signature!");
                        ((Stub)port)._setProperty(GSIConstants.GSI_TRANSPORT, Constants.SIGNATURE);
                    }
                }

                else if (ENCRYPT)
                {
                    if (CONV)
                    {
                        //GSI Transport Conversation + Encryption
                        System.out.println("Use GSI Transport Conversation with Encryption!");
                        ((Stub)port)._setProperty(Constants.GSI_SEC_CONV,Constants.ENCRYPTION);
                    }
                    else if (MSG)
                    {
                        //GSI Transport Message + Encryption
                        System.out.println("Use GSI Transport Message with Encryption!");
                        ((Stub)port)._setProperty(Constants.GSI_SEC_MSG,Constants.ENCRYPTION);
                    }
                    else if (TSL)
                    {
                        //GSI (TSL) Transport Message + Encryption
                        System.out.println("Use GSI (TSL) Transport Message with Encryption!");
                        ((Stub)port)._setProperty(GSIConstants.GSI_TRANSPORT, Constants.ENCRYPTION);
                    }
                }

                else
                {

                    if (CONV)
                    {
                        //GSI Transport Conversation + Encryption
                        System.out.println("Use GSI Transport Conversation!");
                        ((Stub)port)._setProperty(Constants.GSI_SEC_CONV,Constants.NONE);
                    } else if (MSG)
                    {
                        //GSI Transport Message + Encryption
                        System.out.println("Use GSI Transport Message!");
                        ((Stub)port)._setProperty(Constants.GSI_SEC_MSG,Constants.NONE);
                    } else if (TSL)
                    {
                        //GSI (TSL) Transport Message + Encryption
                        System.out.println("Use GSI (TSL) Transport Message!");
                        ((Stub)port)._setProperty(GSIConstants.GSI_TRANSPORT, Constants.NONE);
                    }
                }    
                */
                port.destroy(new org.oasis.wsrf.lifetime.Destroy());
                System.out.println("ClientDestroy operation was successful");

                boolean success = (new File(eprFilename)).delete();
                if (!success)
                {
                    // Deletion failed
                    System.out.println("EPR file removal failed, but the resource was still removed succesfully.");
                }
                else
                    System.out.println("EPR file removed");
                //System.out.println("EPR file removed");

            } catch (Exception e)
            {
                System.err.println("Error: " + FaultHelper.getMessage(e));
            }
        } catch (Exception ex)
        {
            System.out.println("Error: main(): " + ex);
        }
    }
}
