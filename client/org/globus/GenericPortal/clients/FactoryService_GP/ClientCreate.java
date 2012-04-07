/*
 * This file is licensed under the terms of the Globus Toolkit Public License
 * v3, found at http://www.globus.org/toolkit/legal/4.0/license-v3.html.
 * 
 * This notice must gppear in redistributions of this file, with or without
 * modification.
 */
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

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.util.*;

import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.GenericPortal.services.core.WS.impl.GPConstants;
import org.globus.GenericPortal.stubs.Factory.CreateResource;
import org.globus.GenericPortal.stubs.Factory.CreateResourceResponse;
import org.globus.GenericPortal.stubs.Factory.FactoryPortType;
import org.globus.GenericPortal.stubs.Factory.service.FactoryServiceAddressingLocator;
import org.globus.wsrf.encoding.ObjectSerializer;




public class ClientCreate
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
            else if (args[ctr].equals("-factoryURI") && ctr + 1 < args.length)
            {
                ctr++;
                factoryURI = new String(args[ctr]);
            }
            else if (args[ctr].equals("-debug") )
            {
                DEBUG = true;
            }
            else if (args[ctr].equals("-diperf") )
            {
                DIPERF = true;

            }

            else if (args[ctr].equals("-CLIENT_DESC") && ctr + 1 < args.length)
            {
                ctr++;
                CLIENT_DESC = new String(args[ctr]);
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

            else if (args[ctr].equals("-help") )
            {
                System.out.println("Help Screen:");
                usage(args);

            }
            else
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



    public static void main(String[] args) {

        //String factoryURI = null;
        //String eprFilename = null;
        StopWatch lt = new StopWatch();
        lt.start();

        parseArgs(args);


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
            FactoryPortType gpFactory;

            // Get factory portType
            factoryEPR = new EndpointReferenceType();
            factoryEPR.setAddress(new Address(factoryURI));
            gpFactory = factoryLocator.getFactoryPortTypePort(factoryEPR);

            if (CLIENT_DESC != null && (new File(CLIENT_DESC)).exists())
            {
                System.out.println("Setting appropriate security from file '" + CLIENT_DESC + "'!");
                ((Stub)gpFactory)._setProperty(Constants.CLIENT_DESCRIPTOR_FILE, CLIENT_DESC);


            }

            /*
            if (AUTHORIZE)
            {
                //authorization
                System.out.println("Enable authorization!");
                ((Stub)gpFactory)._setProperty(Constants.AUTHORIZATION,SelfAuthorization.getInstance());
            }
            else
            {
                //no authorization
                System.out.println("Disable authorization!");
                ((Stub)gpFactory)._setProperty(Constants.AUTHORIZATION,NoAuthorization.getInstance());
            }

            if (AUTHENTICATE)
            {
                //authentication
                System.out.println("Enable authentication!");
                ((Stub)gpFactory)._setProperty(Constants.GSI_ANONYMOUS, Boolean.FALSE );
            }
            else
            {
            
            //no authentication
                System.out.println("Disable authentication!");
                ((Stub)gpFactory)._setProperty(Constants.GSI_ANONYMOUS, Boolean.TRUE );
            }

            if (SIGN)
            {
                if (CONV)
                {
                    //GSI Transport Conversation + Signature
                    System.out.println("Use GSI Transport Conversation with Signature!");
                    ((Stub)gpFactory)._setProperty(Constants.GSI_SEC_CONV,Constants.SIGNATURE);
                }
                else if (MSG)
                {
                    //GSI Transport Message + Signature
                    System.out.println("Use GSI Transport Message with Signature!");
                    ((Stub)gpFactory)._setProperty(Constants.GSI_SEC_MSG,Constants.SIGNATURE);
                }
                else if (TSL)
                {
                    //GSI (TSL) Transport Message + Signature
                    System.out.println("Use GSI (TSL) Transport Message with Signature!");
                    ((Stub)gpFactory)._setProperty(GSIConstants.GSI_TRANSPORT, Constants.SIGNATURE);
                }
            }

            else if (ENCRYPT)
            {
                if (CONV)
                {
                    //GSI Transport Conversation + Encryption
                    System.out.println("Use GSI Transport Conversation with Encryption!");
                    ((Stub)gpFactory)._setProperty(Constants.GSI_SEC_CONV,Constants.ENCRYPTION);
                }
                else if (MSG)
                {
                    //GSI Transport Message + Encryption
                    System.out.println("Use GSI Transport Message with Encryption!");
                    ((Stub)gpFactory)._setProperty(Constants.GSI_SEC_MSG,Constants.ENCRYPTION);
                }
                else if (TSL)
                {
                    //GSI (TSL) Transport Message + Encryption
                    System.out.println("Use GSI (TSL) Transport Message with Encryption!");
                    ((Stub)gpFactory)._setProperty(GSIConstants.GSI_TRANSPORT, Constants.ENCRYPTION);
                }
            }

            else
            {

                if (CONV)
                {
                    //GSI Transport Conversation + Encryption
                    System.out.println("Use GSI Transport Conversation!");
                    ((Stub)gpFactory)._setProperty(Constants.GSI_SEC_CONV,Constants.NONE);
                } else if (MSG)
                {
                    //GSI Transport Message + Encryption
                    System.out.println("Use GSI Transport Message!");
                    ((Stub)gpFactory)._setProperty(Constants.GSI_SEC_MSG,Constants.NONE);
                } else if (TSL)
                {
                    //GSI (TSL) Transport Message + Encryption
                    System.out.println("Use GSI (TSL) Transport Message!");
                    ((Stub)gpFactory)._setProperty(GSIConstants.GSI_TRANSPORT, Constants.NONE);
                }
            }
            */

            int numErrors = 0;
            int maxNumErrors = 60;
            boolean errorFound = true;

            CreateResourceResponse createResponse = null;
            String endpointString = null;

            while (numErrors<maxNumErrors && errorFound == true)
            {

            try
            {

                // Create resource and get endpoint reference of WS-Resource
                createResponse = gpFactory
                                 .createResource(new CreateResource());
                instanceEPR = createResponse.getEndpointReference();

                endpointString = ObjectSerializer.toString(instanceEPR,
                                                           GPConstants.RESOURCE_REFERENCE);
                errorFound = false;
            }
            catch (Exception e)
            {
                //e.printStackTrace();
                numErrors++;
                try
                {
                       System.out.println("Error # " + numErrors + ", " + e.getMessage() + "... trying again in 60 sec...");
                Thread.sleep(60000);
                }
                catch (Exception ee)
                {

                }
                //System.exit(3);

            }
            }


            if (numErrors >= maxNumErrors)
            {
                System.out.println("too many errors after " + numErrors + " errors... exiting!");
                System.exit(3);
            }


            FileWriter fileWriter = new FileWriter(eprFilename);
            BufferedWriter bfWriter = new BufferedWriter(fileWriter);
            bfWriter.write(endpointString);
            bfWriter.close();
            System.out.println("Endpoint reference written to file "
                               + eprFilename + " (" + lt.getElapsedTime()+ "ms)");

            lt.stop();
            lt.reset();



        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(2);
        }
    }

}
