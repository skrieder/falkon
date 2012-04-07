/*
 * This file is licensed under the terms of the Globus Toolkit Public License
 * v3, found at http://www.globus.org/toolkit/legal/4.0/license-v3.html.
 * 
 * This notice must appear in redistributions of this file, with or without
 * modification.
 */
package org.globus.GenericPortal.clients.GPService_instance;

import java.io.FileInputStream;

import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.GenericPortal.services.core.WS.impl.GPConstants;
import org.globus.GenericPortal.stubs.GPService_instance.GPPortType;
import org.globus.GenericPortal.stubs.GPService_instance.service.GPServiceAddressingLocator;
import org.globus.wsrf.ResourceKey;

import org.globus.wsrf.encoding.ObjectDeserializer;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.xml.sax.InputSource;
//import org.apache.axis.message.addressing.AttributedURI;


import org.globus.wsrf.utils.AddressingUtils;

import org.globus.GenericPortal.stubs.GPService_instance.*;

import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.*;//ThreadLocal
import java.util.zip.*;

import org.globus.GenericPortal.common.*;

import org.globus.GenericPortal.clients.GPService_instance.*;

//I am not sure this is correct... 
import nom.tam.fits.*;
import nom.tam.util.*;
import nom.tam.image.ImageTiler;

public class WorkerMain
{

	public static final String WORKER_VERSION = "0.1.1";
	public String machID;

	public boolean DEBUG;
	public boolean DIPERF;

	public int activeNotifications;	//# of concurent notifications
	public int MAX_NUM_NOTIFICATIONS;
	public int activeThreads;	//# of concurent threads accross all the jobs
	public int MAX_NUM_THREADS;
	public int queueLength;	//# of individual tasks (image ROI) scheduled to take place

	public int LIFETIME;	//time in ms that the worker should accept work

	//public Thread                              notificationThread;

	public Notification workerNot;
	private int SO_TIMEOUT;

	public String fileEPR;
	public String serviceURI;
	public EndpointReferenceType homeEPR;
	public String homeURI;

	public Random rand;

	public String scratchDisk;

	GPPortType ap;


	WorkQueue threadQ;
	StopWatch lt;
	public int jobID;
	public boolean INTERACTIVE;
	public String DESC_FILE;
	public int JOB_SIZE;

	public int NUM_JOBS;
	public int ROI_Height;
	public int ROI_Width;



	public BufferedReader inBufRead = null;


	public int numTasksFailed = 0;
	public int numImagesStacked = 0;
	public Random randIndex;

	public String[] data;

	long elapsedTime;


	//public EndpointReferenceType notificationEPR;



	//constructor
	public WorkerMain()
	{
		this.ROI_Height = 100;
		this.ROI_Width = 100;
		this.randIndex = new Random();

		this.machID = "localhost:0";
		this.scratchDisk = "";

		this.rand = new Random();
		this.DEBUG = false;
		this.DIPERF = false;
		this.activeNotifications = 0;
		this.activeThreads = 0;
		this.MAX_NUM_THREADS = 10;
		this.MAX_NUM_NOTIFICATIONS = 1000000; //some large #, if setting lower, there needs a way to enforce it...
		this.queueLength = 0;
		this.SO_TIMEOUT = 0; //receive will block forever...
		this.LIFETIME = 0;

		this.workerNot = new Notification(this.SO_TIMEOUT);
		if(DEBUG) if (DIPERF == false) System.out.println("Notification initialized on port: " + this.workerNot.recvPort);
		this.threadQ = new WorkQueue();
		this.lt = new StopWatch();
		this.jobID = 0;
		this.INTERACTIVE = false;
		this.elapsedTime = 0;


	}

	public long getElapsedTime()
	{
	    return elapsedTime;

	}


	public void incElapsedTime(long t)
	{
	    elapsedTime += t;

	}


	public void initFileRead()
	{

		try
		{

		
		inBufRead = new BufferedReader(new FileReader(DESC_FILE));
		}
		catch(Exception e) { 
			if (DIPERF == false) System.out.println("Error in reading file: " + e);

		}
	}


	public synchronized String readLine()
	{
    
    // Random integers that range from from 0 to n
    //int n = 10;
		int index = randIndex.nextInt(data.length);
		String str = data[index];
		//System.out.println(index + ": " + str);
		return str;



	}


	public int fileSize()
	{
		try
		{


			String str;
			inBufRead = new BufferedReader(new FileReader(DESC_FILE));
			int i=0;
			while((str = inBufRead.readLine()) != null)
			{
				i++;
			}
			inBufRead.close();
			return i;
		}
		catch(Exception e) { 
			return 0;

		}

	}

	public void fileRead()
	{

		try
		{

			String str = null;
			int fileSize = fileSize();
			//System.out.println("Reading file '" + DESC_FILE + "' of size " + fileSize);
			data = new String[fileSize];
			inBufRead = new BufferedReader(new FileReader(DESC_FILE));
			int i=0;
			while((str = inBufRead.readLine()) != null)
			{
				data[i] = str;

				//System.out.println(i + ": " + str);
				i++;
			}
			//System.out.println("Finished reading file!");
		}
		catch(Exception e) { 
			if (DIPERF == false) System.out.println("Error in reading file '" + DESC_FILE + "': " + e);

		}
	}


	public synchronized String readLineFile()
	{
		if(inBufRead == null)
		{
			initFileRead();
		}
		String str;
		try
		{
		
		if((str = inBufRead.readLine()) != null)
			return str;
		else
		{
			initFileRead();
			if((str = inBufRead.readLine()) != null)
				return str;
			else
			{
				if (DIPERF == false) System.out.println("Error in reading work description, exiting...");
				shutDown();
				
			}
		}
		}
		catch(Exception e) { 
			if (DIPERF == false) System.out.println("Error in readLine(): " + e);

		}
		return null;
	}

	public synchronized int getJobID()
	{
		jobID++;
		return jobID;

	}


	public void notificationStats()
	{

		if(DEBUG) if (DIPERF == false) {
			System.out.println("Notification Summary");
			System.out.println("Total # of notifications received: " + workerNot.success);
			System.out.println("Total # of notifications failed: " + workerNot.failed);
			System.out.println("Total # of notifications retransmited: " + workerNot.retransmited);
			System.out.println("Total # of notifications out of order: " + workerNot.out_of_order);
			System.out.println("Total # of notifications duplicated: " + workerNot.duplicate);
		}
	}


	public EndpointReferenceType waitForNotification() throws Exception
	{
		//boolean test = false;
		ResourceKey key = null;
		//String key = null;

		while(key == null)
		{
			try
			{

				key = workerNot.recv();
			}
			catch(Exception e)
			{
				if(DEBUG) if (DIPERF == false) System.out.println("ERROR: notification recv() " + e);

			}

			if(key == null)
			{

				if(DEBUG) if (DIPERF == false) System.out.println("Notification received, but key was unexpectedly null. sleeping for 1 sec and trying recv again...");
				try
				{

					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
					if(DEBUG) if (DIPERF == false) System.out.println("ERROR: sleep() " + e);

				}
			}
			else
			{
				if(DEBUG) if (DIPERF == false) System.out.println("Received key = " + key.getValue());
				return getEPR(key);
				//if (DEBUG) if (DIPERF == false) System.out.println("Received key = " + key);
				//return getEPRfromKey(key);
			}

		}

		return getEPR(key);

	}

	/*
	private static void printResourceProperties(GPPortType ap)
					throws Exception {
			GetResourcePropertyResponse numJobsRP, stateRP, numWorkersRP, numUserResultsRP, numWorkerResultsRP, lastLogRP;
			String numJobs, state, numWorkers, numUserResults, numWorkerResults, lastLog;

			numJobsRP = ap.getResourceProperty(GPConstants.RP_NUMJOBS);
			stateRP = ap.getResourceProperty(GPConstants.RP_STATE);
			numWorkersRP = ap.getResourceProperty(GPConstants.RP_NUMWORKERS);
			numUserResultsRP = ap.getResourceProperty(GPConstants.RP_NUMUSERRESULTS);
			numWorkerResultsRP = ap.getResourceProperty(GPConstants.RP_NUMWORKERRESULTS);


			numJobs = numJobsRP.get_any()[0].getValue();
			state = stateRP.get_any()[0].getValue();
			numWorkers = numWorkersRP.get_any()[0].getValue();
			numUserResults = numUserResultsRP.get_any()[0].getValue();
			numWorkerResults = numWorkerResultsRP.get_any()[0].getValue();

			if (DEBUG) if (DIPERF == false) System.out.println("WORKER: NumJobs RP: " + numJobs);
			if (DEBUG) if (DIPERF == false) System.out.println("WORKER: State RP: " + state);
			if (DEBUG) if (DIPERF == false) System.out.println("WORKER: NumWorkers RP: " + numWorkers);
			if (DEBUG) if (DIPERF == false) System.out.println("WORKER: NumUserResults RP: " + numUserResults);
			if (DEBUG) if (DIPERF == false) System.out.println("WORKER: NumWorkerResults RP: " + numWorkerResults);

	}


	private static boolean getWorkerWorkAvailable(GPPortType ap)
					throws Exception {
		GetResourcePropertyResponse numUserResultsRP;
		String numUserResults;

		numUserResultsRP = ap.getResourceProperty(GPConstants.RP_NUMUSERRESULTS);

		numUserResults = numUserResultsRP.get_any()[0].getValue();

		if (DEBUG) if (DIPERF == false) System.out.println("WORKER: NumUserResults RP: " + numUserResults);

		if (Integer.valueOf(numUserResults).intValue() > 1) 
		{
			return true;
		}
		else
			return false;
	}
	*/



	public String getMachNamePort()
	{

		String machName = "";
		//String machIP;
		//Utility tools = new Utility();
		try
		{

			//    int iPID = tools.pid();
			//    String tPID = null;

			//    if ( iPID < 0 || iPID > 65535) 
			//    {
			//        tPID = (new Integer(tools.rand())).toString();
			//    }
			//    else
			//    {
			//        tPID = (new Integer(iPID)).toString();

			//    }

			machName = java.net.InetAddress.getLocalHost().getHostName();
			machName = machName +  ":" + workerNot.recvPort;
			//machIP = java.net.InetAddress.getLocalHost().getHostAddress();
			if(DEBUG) if (DIPERF == false) System.out.println("WORKER: Machine ID = " + machName);

		}
		catch(Exception e)
		{
			if(DEBUG) if (DIPERF == false) System.out.println("WORKER: ERROR: java.net.InetAddress.getLocalHost().getHostName() failed " + e);
		}
		return machName;
	}



	public String getMachNamePID()
	{

		String machName = "";
		//String machIP;
		Utility tools = new Utility();
		try
		{

			int iPID = tools.pid();
			String tPID = null;

			if(iPID < 0 || iPID > 65535)
			{
				tPID = (new Integer(tools.rand())).toString();
			}
			else
			{
				tPID = (new Integer(iPID)).toString();

			}

			machName = java.net.InetAddress.getLocalHost().getHostName();
			machName = machName +  ":" + tPID;
			//machIP = java.net.InetAddress.getLocalHost().getHostAddress();
			if(DEBUG) if (DIPERF == false) System.out.println("WORKER: Machine ID = " + machName);

		}
		catch(Exception e)
		{
			if(DEBUG) if (DIPERF == false) System.out.println("WORKER: ERROR: java.net.InetAddress.getLocalHost().getHostName() failed " + e);
		}
		return machName;
	}

	/*
		public static void doWork()
		{
			if (DEBUG) if (DIPERF == false) System.out.println("simulating doing work... sleeping for 10 seconds...");
			try
			{
			
				Thread.sleep(10000);
			}
			catch (Exception e) {
				if (DEBUG) if (DIPERF == false) System.out.println("WORKER: ERROR doWork(): " + e.getMessage());
			}

		}   */

	// public static void waitForNotification(/*String host, int port*/)
	// {
	/*
		try
		{
		
		String host = "tg-viz-login1";
		int port = 50002;

		DatagramSocket  socket;
	  DatagramPacket  packet;
	  InetAddress     address;
	  byte[]          message = new byte[256];

	  //
	  // Send empty request
	  //
	  socket = new DatagramSocket(port);
	  address=InetAddress.getByName(host);
	  packet = new DatagramPacket(message, message.length, address, port);
	  //socket.send(packet);

	  //
	  // Receive reply and display on screen
	  //
	  //packet = new DatagramPacket(message, message.length);
	  if (DEBUG) if (DIPERF == false) System.out.println("Waiting for notification...");

	  socket.receive(packet);
	  String received =new String(packet.getData(), 0);
	  if (DEBUG) if (DIPERF == false) System.out.println("Received notification: " + received);

	  //Socket.close();
		}
		catch (Exception e) {
			if (DEBUG) if (DIPERF == false) System.out.println("WORKER: ERROR waitForNotification(): " + e.getMessage());
		}  */


	//     }

	/*
	public static void main_old(String[] args) {


		//start thread

		
		if (nThread == null) 
		{
		nThread = new NotificationThread(this);
		nThread.start();
		}      

			boolean isRegistered = false;

			String machID = getMachNamePID();

			StopWatch sw = new StopWatch();

			if (DEBUG) if (DIPERF == false) System.out.println("WORKER: v0.1 - " + machID);
		GPServiceAddressingLocator instanceLocator = new GPServiceAddressingLocator();

		try {
			int numJobs = Integer.parseInt(args[1]);

						sw.start();
						EndpointReferenceType instanceEPR;

			if (args[0].startsWith("http")) {
				// First argument contains a URI
				String serviceURI = args[0];
				// Create endpoint reference to service
				instanceEPR = new EndpointReferenceType();
				instanceEPR.setAddress(new Address(serviceURI));
			} else {
				// First argument contains an EPR file name
				String eprFile = args[0];
				// Get endpoint reference of WS-Resource from file
				FileInputStream fis = new FileInputStream(eprFile);
				instanceEPR = (EndpointReferenceType) ObjectDeserializer
						.deserialize(new InputSource(fis),
								EndpointReferenceType.class);
				fis.close();
			}
						sw.stop();
						if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Read EPR fro disk (" + sw.getElapsedTime() + "ms)");
						sw.reset();



						sw.start();
			// Get PortType
			GPPortType ap = instanceLocator
					.getGPPortTypePort(instanceEPR);
						sw.stop();
						if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Get ProtType (" + sw.getElapsedTime() + "ms)");
						sw.reset();

			// Perform registration
//			ap.workerRegistration(machID);
						boolean workerWork = false;
						boolean userResult = false;
						boolean workerWorkAvailable = false;

						int sleepErrorRetry = 1000;
						int sleepErrorRetryMax = 10000;
						int sleepErrorRetryStep = 1000;
						int pollStep = 1000;
						int sleepTime = 0;

						while (sleepErrorRetry <= sleepErrorRetryMax) 
						{
							try
							{
								if (isRegistered == false) 
								{
							   
									// Perform registration

									sw.start();
									ap.workerRegistration(machID);
									sw.stop();
									if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Performed one time Worker registration (" + sw.getElapsedTime() + "ms)");
									sw.reset();

									// Print resource properties
									printResourceProperties(ap);

									isRegistered = true;
								}


								//Thread.sleep(1000);
							

								/*
								while (workerWorkAvailable == false) 
								{
								
									sw.start();
									// Perform status
									workerWorkAvailable = getWorkerWorkAvailable(ap);
									//ap.status(0);
									sw.stop();
									if (DEBUG) if (DIPERF == false) System.out.println("WORKER: getWorkerWorkAvailable (" + sw.getElapsedTime() + "ms)");
									sw.reset();

									// Print resource properties

									//printResourceProperties(ap);

									//this should be replaced by some notification mechanism
									Thread.sleep(pollStep);
								} * /

								//this call blocks until a notification is received
								waitForNotification();
								

								// get work
								sw.start();
								//ap.status(0);
								ap.workerWork(0);
								sw.stop();
								if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Get work (" + sw.getElapsedTime() + "ms)");
								sw.reset();

								//do work
								sw.start();
								doWork();
								sw.stop();
								if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Do work (" + sw.getElapsedTime() + "ms)");
								sw.reset();

								//send results back (potentially to the originating resource)
								sw.start();
								ap.workerResult(0);
								sw.stop();
								if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Sent results (" + sw.getElapsedTime() + "ms)");
								sw.reset();


								//this should be replaced by some notification mechanism
								//Thread.sleep(1000);

								sleepErrorRetry = sleepErrorRetryStep;
								sleepTime = 0;

							}
							catch (Exception e) {
								  if (DEBUG) if (DIPERF == false) System.out.println("WORKER: ERROR while(): " + e.getMessage());
								  isRegistered = false; //try again
								  sleepErrorRetry += sleepErrorRetryStep;
								  sleepTime += sleepErrorRetry;
								  Thread.sleep(sleepErrorRetry);
						  }

						}

						if (DEBUG) if (DIPERF == false) System.out.println("WORKER: Cannot find GenericPortal WebService... timed out after " + sleepTime/1000.0 + " seconds... exiting :(");
			nThread.stop();

		} catch (Exception e) {
			if (DEBUG) if (DIPERF == false) System.out.println("WORKER: ERROR main(): " + e.getMessage());
		}
		
	}   */


	public void parseArgs(String[] args) throws Exception
	{
		if(args.length < 1)
		{
			usage(args);
			return;
		}

		int ctr;
		for(ctr = 0; ctr < args.length; ctr++)
		{
			if(args[ctr].equals("-epr") && ctr + 1 < args.length)
			{
				ctr++;


				fileEPR = new String(args[ctr]);
				boolean exists = (new File(fileEPR)).exists();
				if(exists)
				{

					// Get endpoint reference of WS-Resource from file
					FileInputStream fis = new FileInputStream(fileEPR);


					homeEPR = (EndpointReferenceType) ObjectDeserializer
							  .deserialize(new InputSource(fis),
										   EndpointReferenceType.class);


					if(homeEPR == null)
					{
						throw new Exception("parseArgs(): homeEPR == null, probably EPR was not correctly read from file.");
					}
					else
					{

						if(DEBUG) if (DIPERF == false) System.out.println("parseArgs(): homeEPR is set correctly");
					}

					fis.close();
				}
				else
				{
					throw new Exception("File '" + fileEPR + "' does not exist.");
				}


				//fileEPR = new String(args[ctr]);
				//FileInputStream fis = new FileInputStream(fileEPR);

				//homeEPR = (EndpointReferenceType) ObjectDeserializer.deserialize(new InputSource(fis),EndpointReferenceType.class);
				//homeEPR = getEPR(fis);

				//fis.close();
			}
			//must implement properly as a new resource would have to be created :(
			else if(args[ctr].equals("-serviceURI") && ctr + 1 < args.length)
			{
				ctr++;
				serviceURI = new String(args[ctr]);
				//FileInputStream fis = new FileInputStream(fileEPR);
				//homeEPR = (EndpointReferenceType) ObjectDeserializer.deserialize(new InputSource(fis),EndpointReferenceType.class);
				homeEPR = getEPR(serviceURI);

				//fis.close();
			}
			else if(args[ctr].equals("-lifetime") && ctr + 1 < args.length)
			{
				ctr++;
				Integer NR = new Integer(args[ctr]);
				LIFETIME = NR.intValue();

			}


			else if(args[ctr].equals("-scratch_disk") && ctr + 1 < args.length)
			{
				ctr++;
				//Integer NR = new Integer(args[ctr]);
				//LIFETIME = NR.intValue();
				scratchDisk = args[ctr];

			}
			else if(args[ctr].equals("-max_not") && ctr + 1 < args.length)
			{
				ctr++;
				Integer NR = new Integer(args[ctr]);
				MAX_NUM_NOTIFICATIONS = NR.intValue();

			}
			else if(args[ctr].equals("-max_threads") && ctr + 1 < args.length)
			{
				ctr++;
				Integer NR = new Integer(args[ctr]);
				MAX_NUM_THREADS = NR.intValue();

			}
			else if(args[ctr].equals("-SO_TIMEOUT") && ctr + 1 < args.length)
			{
				ctr++;
				Integer NR = new Integer(args[ctr]);
				SO_TIMEOUT = NR.intValue();

			}
			else if(args[ctr].equals("-desc") && ctr + 1 < args.length)
			{
				ctr++;
				DESC_FILE = args[ctr];
				//System.out.println("Arguement: " + DESC_FILE + " == " + args[ctr]);

			}
			else if(args[ctr].equals("-job_size") && ctr + 1 < args.length)
			{
				ctr++;
				Integer NR = new Integer(args[ctr]);
				JOB_SIZE = NR.intValue();

			}
			else if(args[ctr].equals("-height") && ctr + 1 < args.length)
			{
				ctr++;
				Integer NR = new Integer(args[ctr]);
				ROI_Height = NR.intValue();

			}
			else if(args[ctr].equals("-width") && ctr + 1 < args.length)
			{
				ctr++;
				Integer NR = new Integer(args[ctr]);
				ROI_Width = NR.intValue();

			}
			else if(args[ctr].equals("-debug"))//&& ctr + 1 < args.length)
			{
				//ctr++;
				DEBUG = true;
			}
			else if(args[ctr].equals("-interactive"))//&& ctr + 1 < args.length)
			{
				//ctr++;
				INTERACTIVE = true;
			}
			else if(args[ctr].equals("-diperf"))//&& ctr + 1 < args.length)
			{
				//ctr++;
				DIPERF = true;

			}
			else if(args[ctr].equals("-help"))//&& ctr + 1 < args.length)
			{
				//ctr++;
				if(DEBUG) if (DIPERF == false) System.out.println("Help Screen:");
				usage(args);

			}
			else
			{

				if (DIPERF == false) {
					System.out.println("ERROR: invalid parameter - " + args[ctr]);
					System.out.println("Current parameters values:");
					System.out.println("-debug " + DEBUG);
					System.out.println("-diperf " + DIPERF);
					System.out.println("more... ");
				}


				usage(args);
			}
		}

	}

	public void initWorker()
	{
		//nothing yet
	}

	public void usage(String[] args)
	{
		if (DIPERF == false) {
			System.out.println("Help Screen: ");
			System.out.println("-diperf <>");
			System.out.println("-help <>");
		}
		System.exit(0);

	}

	public EndpointReferenceType getEPR(ResourceKey key) throws Exception
	{
		String instanceURI = homeEPR.getAddress().toString();
		return(EndpointReferenceType) AddressingUtils.createEndpointReference(instanceURI, key);
	}


	public EndpointReferenceType getEPRfromKey(String key) throws Exception
	{
		//String instanceURI = homeEPR.getAddress().toString();
		//return (EndpointReferenceType) AddressingUtils.createEndpointReference(instanceURI, key);
		return null;
	}

	public EndpointReferenceType getEPR(FileInputStream fis) throws Exception
	{
		return(EndpointReferenceType) ObjectDeserializer.deserialize(new InputSource(fis),EndpointReferenceType.class);
		///return (EndpointReferenceType) ObjectDeserializer.deserialize(fis,EndpointReferenceType.class);
	}



	public EndpointReferenceType getEPR(String serviceURI) throws Exception
	{
		EndpointReferenceType epr = new EndpointReferenceType();
		epr.setAddress(new Address(serviceURI));
		return epr;
	}


	public GPPortType getGPPortType(EndpointReferenceType epr) throws Exception
	{
		GPServiceAddressingLocator instanceLocator = new GPServiceAddressingLocator();
		return instanceLocator.getGPPortTypePort(epr);

	}


	public GPPortType getGPPortType() throws Exception
	{
		if(homeEPR == null)
		{
			throw new Exception("homeEPR == null, probably EPR was not correctly read from file");
		}
		else
		{

			GPServiceAddressingLocator instanceLocator = new GPServiceAddressingLocator();
			return instanceLocator.getGPPortTypePort(homeEPR);
		}

	}

	public void shutDown()
	{

		try
		{

			//sw.start();
			ap.workerDeRegistration(new WorkerDeRegistration(machID));
			//sw.stop();
			if(DEBUG) if (DIPERF == false) System.out.println("WORKER: Performed one time worker de-registration");
			//sw.reset();
			if(DEBUG) if (DIPERF == false) System.out.println("WORKER: waiting for all running threads to terminate");

			while(threadQ.size() > 0)
			{
				try
				{
					Thread t = (Thread) threadQ.remove();
					t.join();
					// Finished
				}
				catch(InterruptedException e)
				{
					if(DEBUG) if (DIPERF == false) System.out.println("WORKER: waiting for threads to die was interrupted: " + e);

				}
			}

			if(DEBUG) if (DIPERF == false) System.out.println("WORKER: all threads have terminated");


			System.exit(0);
		}
		catch(Exception e)
		{

			if(DEBUG) if (DIPERF == false) System.out.println("WORKER: ERROR in shutting down " + e);
		}

	}


	public synchronized void activeNotificationsInc()
	{
		activeNotifications++;
	}

	public synchronized void activeNotificationsDec()
	{
		activeNotifications--;
	}

	public synchronized int getActiveNotifications()
	{
		return activeNotifications;
	}


	public synchronized boolean canAcceptMoreNotifications()
	{
		if(getActiveNotifications() < MAX_NUM_NOTIFICATIONS)
		{
			return true;
		}
		else
			return false;
	}

	public void main_run(String[] args) throws Exception
	{



		if(DEBUG) if (DIPERF == false) System.out.println("WORKER: " + WORKER_VERSION + " - " + machID);


		StopWatch sw = new StopWatch();
		lt.start();


		sw.start();
		parseArgs(args);
		sw.stop();
		if(DEBUG) if (DIPERF == false) System.out.println("WORKER: Parse Arguements and Setup Corresponding State (" + sw.getElapsedTime() + "ms)");
		sw.reset();

		fileRead();




		int sleepErrorRetry = 1000;
		int sleepErrorRetryMax = 10000;
		int sleepErrorRetryStep = 1000;
		int pollStep = 1000;
		int sleepTime = 0;

		if(INTERACTIVE)
		{


			Thread keyListen = new KeyboardListener(this);
			if(DEBUG) System.out.print("WORKER: keyListen Thread starting...");
			keyListen.start();
			if(DEBUG) System.out.print("WORKER: keyListen Thread started");
		}


		Thread lifeListen = new LifetimeListener(this);
		if(DEBUG) System.out.print("WORKER: lifeListen Thread starting...");

		lifeListen.start();
		if(DEBUG) System.out.print("WORKER: lifeListen Thread started");

		while(lt.getElapsedTime() < LIFETIME)
		{

			//sw.start();
			//if (DIPERF == false) System.out.println("Waiting for work...");

			//EndpointReferenceType notificationEPR = waitForNotification();
			//sw.stop();
			//if(DEBUG) if (DIPERF == false) System.out.println("WORKER: waitForNotification (" + sw.getElapsedTime() + "ms)");
			//sw.reset();


			Thread notificationThread = null;
			if(canAcceptMoreNotifications())
			{
				if (DIPERF == false) System.out.println("Accepted a new notification");
				if (DIPERF == false) System.out.println("Number of concurent active notifications: " + getActiveNotifications() + ", maximum allowed notifications: " + MAX_NUM_NOTIFICATIONS);
				notificationThread = new NotificationThread(this, DESC_FILE, JOB_SIZE);
				notificationThread.start();

				threadQ.insert(notificationThread);
				notificationThread.join();

			}
			else
			{
				if (DIPERF == false) System.out.println("Busy with too many concurent notifications: " + getActiveNotifications());
				if (DIPERF == false) System.out.println("Notification not served...");


			}


		}

		if (DIPERF == false) System.out.println("Worker lifetime of " + LIFETIME/1000.0 + " seconds ended after " + lt.getElapsedTime()/1000.0 + " seconds... exiting");
		if (DIPERF == false) System.out.println("Shutting down worker...");
		shutDown();

	}


	public static void main(String[] args)
	{
		try
		{

			StopWatch all = new StopWatch();
			all.start();
			WorkerMain worker = new WorkerMain();
			worker.main_run(args);
			all.stop();

			//if (worker.DIPERF == true) System.out.println("NumThreads Data Time Stacks/sec NumJobs JobSize ROI_Height ROI_Height numImagesStacked numTasksFailed");
			//if (worker.DIPERF == true) System.out.println(worker.MAX_NUM_THREADS + " " + worker.DESC_FILE + " " + all.getElapsedTime()/1000.0 + " " + worker.numImagesStacked*1.0 / (all.getElapsedTime()/1000.0)+ " " + worker.NUM_JOBS + " " + worker.JOB_SIZE + " " + worker.ROI_Height + " " + worker.ROI_Height + " " + worker.numImagesStacked + " " + worker.numTasksFailed);
			if (worker.DIPERF == true) System.out.println(worker.MAX_NUM_THREADS + " " + worker.DESC_FILE + " " + worker.getElapsedTime()/1000.0 + " " + worker.numImagesStacked*1.0 / (worker.getElapsedTime()/1000.0)+ " " + worker.NUM_JOBS + " " + worker.JOB_SIZE + " " + worker.ROI_Height + " " + worker.ROI_Height + " " + worker.numImagesStacked + " " + worker.numTasksFailed);
		}
		catch(Exception e)
		{
			System.out.println("WORKER: Fatal ERROR: " + e + "... exiting");
			System.exit(0);
		}

	}

}



class NotificationThread extends Thread
{

	WorkerMain home;
	public Thread[]                              readThreads;
	public boolean DEBUG;
	public boolean DIPERF;

	//public Queue tasks;
	//public Queue images;

	//public boolean busy;
	public EndpointReferenceType epr;
	public GPPortType ap;

	WorkQueue taskXQ;
	//public int numTasksFailed;
	public int numImagesStacked;
	public int height;
	public int width;

	public FIT f;
	public int jobID;

	public boolean GenericPortal;

	public String desc_file;
	public int job_size;
	public int ROI_height;
	public int ROI_width;


	public NotificationThread(WorkerMain h, String fn, int s) throws Exception 
	{
		super("Worker Notification Thread");
		this.GenericPortal = false;
		this.desc_file = fn;
		this.job_size = s;
		this.home = h;

		this.ROI_height = home.ROI_Height;
		this.ROI_width = home.ROI_Width;

		this.DEBUG = this.home.DEBUG;
		this.DIPERF = this.home.DIPERF;

		this.home.activeNotificationsInc();

		//this.epr = e;

		//this.ap = this.home.getGPPortType(home.notificationEPR);
		//this.ap = this.home.getGPPortType(this.epr);
		this.jobID = this.home.getJobID();



		//this.readThreads = new Thread[home.MAX_NUM_THREADS];

		//for(int i=0;i<home.MAX_NUM_THREADS;i++)
		//{
		//    this.readThreads[i] = new ReadThread(this);

		//}

		//this.tasks = new Queue();

		//numTasksToDo = 0;
		//numTasksDone = 0;
		//numTasksFailed = 0;
		numImagesStacked = 0;

		f = new FIT();

		taskXQ = new WorkQueue();


	}

	public NotificationThread(WorkerMain h, EndpointReferenceType e) throws Exception 
	{
		super("Worker Notification Thread");

		this.GenericPortal = true;
		this.home = h;
		this.DEBUG = this.home.DEBUG;

		this.home.activeNotificationsInc();

		this.epr = e;

		//this.ap = this.home.getGPPortType(home.notificationEPR);
		this.ap = this.home.getGPPortType(this.epr);
		this.jobID = this.home.getJobID();



		//this.readThreads = new Thread[home.MAX_NUM_THREADS];

		//for(int i=0;i<home.MAX_NUM_THREADS;i++)
		//{
		//    this.readThreads[i] = new ReadThread(this);

		//}

		//this.tasks = new Queue();

		//numTasksToDo = 0;
		//numTasksDone = 0;
		//numTasksFailed = 0;
		numImagesStacked = 0;

		f = new FIT();

		taskXQ = new WorkQueue();


	}

	public synchronized void addTasksFailed(Task task)
	{
		taskXQ.insert(task);

	}

	/*
	public synchronized void numTasksFailedInc()
	{
	numTasksFailed++;

	} */

	public synchronized void numImagesStackedInc()
	{
		numImagesStacked++;

	}

	public int max(int a, int b)
	{
		if(a>b)
		{
			return a;
		}
		else
			return b;
	}

	public int min(int a, int b)
	{
		if(a<b)
		{
			return a;
		}
		else
			return b;
	}

	//this is not properly implemented
	public long getID()
	{
		//return ThreadLocal.getId();
		long id = 0;
		return id;
	}


	public void run() 
	{
		try
		{

			doRunMain();
		}
		catch(Exception e)
		{
			if(DEBUG) if (DIPERF == false) System.out.println("There was an error in Notification Thread doRun(): " + e);
			if(DEBUG) if (DIPERF == false) System.out.println("Notification thread exiting...");

			//throw new Exception(e);
		}

	}

	public WorkerWorkResponse workerWork(WorkerWork ww)
	{
		//String t = null;
		String []t = new String[job_size];
		for(int i=0;i<job_size;i++)
		{

		
			t[i] = home.readLine();
		}


		WorkerWorkResponse RP = new WorkerWorkResponse();
		Task[] tasks;
		try
		{
		    //int i;
			int numWorkers = 1;
			int workSize = job_size;

			tasks = new Task[workSize];
			//i = 0;
			for(int i=0;i<job_size;i++)
			{

				Tuple tuple = new Tuple();
				tuple.setRa(0.0);
				tuple.setDec(0.0);
				tuple.setBand("r");
				Task task = new Task();
				task.setTuple(tuple);
				task.setFileName(t[i]);
				task.setX_coord(0);
				task.setY_coord(0);

			    tasks[i] = task;
			    //i++;
    
    
			}
		    
			RP.setTasks(tasks);
			RP.setValid(true);
			RP.setHeight(ROI_height);
			RP.setWidth(ROI_width);

		}
		 catch (Exception e) 
		{
		    if (DIPERF == false) System.out.println("Error in retrieving work: " + e);
		    if (DIPERF == false) System.out.println("Exiting...");
			home.shutDown();
		}

		return RP;

	}

	//this function is just pseudo code.. fix
	public void doRunMain() throws Exception //maybe we don't want to throw an exception here since we might want to recover from some of the errors...
	{
		StopWatch jobTimer = new StopWatch();
		StopWatch taskTimer = new StopWatch();
		jobTimer.start();

		//WorkerWorkResponse wt = ap.workerWork(true);
		int numTasksFailed = 0;

		if(DEBUG) if (DIPERF == false) System.out.println("doRun()...");
		//WorkerWorkResponse wt = ap.workerWork(new WorkerWork(true));
		WorkerWorkResponse wt = workerWork(new WorkerWork(true));

		if(DEBUG) if (DIPERF == false) System.out.println("work retrieved from GPWS...");

		//WorkTasks wt = ap.workerWork(0);
		boolean isWork = wt.isValid();

		if(isWork)
		{
			if(DEBUG) if (DIPERF == false) System.out.println("work is valid...");
			int numTasksToDo = 0;

			Task[] tasks = wt.getTasks();

			if(DEBUG) if (DIPERF == false) System.out.println("tasks retrieved...");
			numTasksToDo = tasks.length;

			if (DIPERF == false) System.out.println("Job #" + jobID + ": Total tasks to do: " + numTasksToDo);

			height = wt.getHeight();
			width = wt.getWidth();

			if(DEBUG) if (DIPERF == false) System.out.println("height and width retrieved...");

			// Create the task queue
			WorkQueue taskQ = new WorkQueue();
			// Create the stack queue
			WorkQueue stackQ = new WorkQueue();


			if(DEBUG) if (DIPERF == false) System.out.println("taskQ and stack initialized...");


			// Create a set of worker threads
			final int numReaders = min(home.MAX_NUM_THREADS, numTasksToDo);
			ReadThread[] readThreads = new ReadThread[numReaders];

			if(DEBUG) if (DIPERF == false) System.out.println("readThreads initialized...");

			taskTimer.start();
			for(int i=0; i<readThreads.length; i++)
			{
				readThreads[i] = new ReadThread(this, taskQ, stackQ);
				readThreads[i].start();
			}
			if(DEBUG) if (DIPERF == false) System.out.println("readThreads started...");

			//Task array = new Task[10]; //not the right size
			//get EPR from home thread
			//GPPortType ap = instanceLocator.getGPPortTypePort(epr);
			//access GPWS at specified EPR for work

			if(DEBUG) if (DIPERF == false) System.out.println("inserting tasks in taskQ...");


			for(int i=0; i<numTasksToDo; i++)
			{
				taskQ.insert(tasks[i]);
			}
			if(DEBUG) if (DIPERF == false) System.out.println("tasks inserted in taskQ...");


			// Add special end-of-stream markers to terminate the workers
			for(int i=0; i<readThreads.length; i++)
			{
				taskQ.insert(ReadThread.NO_MORE_WORK);
			}

			if(DEBUG) if (DIPERF == false) System.out.println("waiting for readThreads to finish...");


			for(int i=0;i<readThreads.length;i++)
			{
				// Wait indefinitely for the thread to finish
				try
				{
					readThreads[i].join();
					if(DEBUG) if (DIPERF == false) System.out.println("Notification Thread #"  + getID() + ": Read Thread #" + readThreads[i].getID());// + ": State = " + readThreads[i].State);
					// Finished
				}
				catch(InterruptedException e)
				{
					if(DEBUG) if (DIPERF == false) System.out.println("Notification Thread #"  + getID() + ": Read Thread #" + readThreads[i].getID()/* + ": State = " + readThreads[i].State*/ + ": was interupted");
					// Thread was interrupted
				}
			}


			String resultFile = null;
			//String result = null;
			WorkerResult result = new WorkerResult();
			if(DEBUG) if (DIPERF == false) System.out.println("initialized workerResult()...");




			//int numTasksDone = 0;
			//do stacking
			//if(stackQ.size() <= 0)
			//{
			//if (DEBUG) if (DIPERF == false) System.out.println("we are done, but no stacking was performed... probably it could not find any files...");
			//result = "NULL";
			//}
			//else
			//{
			if(stackQ.size() > 0)
			{

				if(DEBUG) if (DIPERF == false) System.out.println("stacking " + stackQ.size() + " images...");

				//numTasksDone = stackQ.size();
				int[][] resultImageInt = (int[][])stackQ.remove();
				int[][] curImageInt = null;



				while(stackQ.size() > 0)
				{
					curImageInt = (int[][])stackQ.remove();
					resultImageInt = f.sumArrayInt(resultImageInt, curImageInt, height, width);

				}

				resultFile = home.scratchDisk + "results_" + System.currentTimeMillis() + "-" + home.rand.nextInt(2147483647)  + ".fit"; //set this to something random... plus add the path... needs to be in a globally accessible place

				if(DEBUG) if (DIPERF == false) System.out.println("result file: " + resultFile);

				f.writeInt(resultImageInt, resultFile);


				if(DEBUG) if (DIPERF == false) System.out.println("result written to disk...");

				// = numTasksDone - numTasksFailed;
				//WorkerResult
				result.setResult(resultFile);
			}
			taskTimer.stop();
			result.setNumImagesStacked(numImagesStacked);


			if(DEBUG) if (DIPERF == false) System.out.println("set result values...");

			numTasksFailed = taskXQ.size();
			if(DEBUG) if (DIPERF == false) System.out.println("failed tasks: " + taskXQ.size());


			if(taskXQ.size() == 0)
			{
				if(DEBUG) if (DIPERF == false) System.out.println("There were no failed tasks to initialize...");
				//Task[] failedTasks = new Task[0];
				//result.setTasksFailed(failedTasks);
				result.setNumTasksFailed(0);
			}
			else
			{


				Task[] failedTasks = new Task[taskXQ.size()];
				result.setNumTasksFailed(taskXQ.size());

				int i=0;
				while(taskXQ.size() > 0)
				{
					failedTasks[i] = (Task)taskXQ.remove();
					i++;

				}

				if(DEBUG) if (DIPERF == false) System.out.println("initialized failed tasks...");
				result.setTasksFailed(failedTasks);
				if(DEBUG) if (DIPERF == false) System.out.println("set failed tasks...");
			}

			//result.setNumTasksFailed(numTasksFailed); 

			//}


			//send back the result to the GPWS to the specific EPR
			if(DEBUG) if (DIPERF == false) System.out.println("sending back the results to the GPWS...");

			//if (DIPERF == false) System.out.println("Stacking finished succesful: numImagesStacked = " + numImagesStacked + " numTasksFailed = " + numTasksFailed);
			//ap.workerResult(result);
			if(DEBUG) if (DIPERF == false) System.out.println("sent back the results to the GPWS succesfully...");

			jobTimer.stop();

			if (DIPERF == false) System.out.println("Job #" + jobID + ": Finished in " + jobTimer.getElapsedTime() + " ms (" + taskTimer.getElapsedTime() + " ms); " + numImagesStacked + " stackings, " + numTasksFailed + " failed tasks; result sent back to GPWS");
			synchronized (home)
			{
			
				home.NUM_JOBS++;
				home.numImagesStacked += numImagesStacked;
				home.numTasksFailed += numTasksFailed;
				home.elapsedTime += taskTimer.getElapsedTime(); 
			}

			//this.home.activeNotificationsDec();

		}
		else
		{
			if(DEBUG) if (DIPERF == false) System.out.println("There was no work returned by workerWork()...  perhaps the work was collected by a different worker, but should check into why the work signaled by the notification was not available...");
			if(DEBUG) if (DIPERF == false) System.out.println("Notification thread exiting...");
			//this.home.activeNotificationsDec();

		}
		this.home.activeNotificationsDec();

	}


}



class ReadThread extends Thread
{
	// Special end-of-stream marker. If a worker retrieves
	// an Integer that equals this marker, the worker will terminate.
	static final Object NO_MORE_WORK = new Object();
	//static final Object NO_MORE_WORK = new Object();

	NotificationThread job;
	WorkQueue tQ;
	WorkQueue sQ;
	boolean DEBUG;
	boolean DIPERF = true;

	ReadThread(NotificationThread j, WorkQueue tq, WorkQueue sq)
	{
		this.job = j;
		this.tQ = tq;
		this.sQ = sq;

		this.DEBUG = this.job.DEBUG;
	}

	//this is not correct
	public long getID()
	{
		//return ThreadLocal.getId();
		long id = 0;
		return id;
	}

	public void run()
	{
		try
		{

			doRun();
		}
		catch(Exception e)
		{
			if(DEBUG) if (DIPERF == false) System.out.println("There was an error in Read Thread doRun(): " + e);
			if(DEBUG) if (DIPERF == false) System.out.println("Read thread exiting...");
			// throw new Exception(e);
		}

	}

	public void doRun() throws Exception {
		try
		{
			//FIT image = new FIT();
			//Fits img = null;
			//perform ROIs
			while(true)
			{
				// Retrieve some work; block if the queue is empty
				//Task task = (Task)tQ.remove();
				Object o = tQ.remove();
				if(o == NO_MORE_WORK)
				{
					break;
				}
				Task task = (Task) o;

				if(task == null)
				{
					break;
				}

				//img = ;
				//int[][] imgInt = cropInt(read(x.fName), x.x, x.y, x.hashCode, x.wait);


				//Object y = getROI(x); ///implement this properly
				// Terminate if the end-of-stream marker was retrieved
				//if (task == NO_MORE_WORK) {
				//break;
				//}


				boolean exists = (new File(task.getFileName())).exists();
				if (task.getFileName().startsWith("http://"))
				    exists = true;

				if(exists)
				{


					//sQ.insert(job.f.cropInt(job.f.read(task.getFileName()), task.getX_coord(), task.getY_coord(), job.height, job.width));

					Fits img = job.f.read(task.getFileName());
					if(img != null)
					{

						sQ.insert(job.f.cropInt(img, task.getX_coord(), task.getY_coord(), job.height, job.width));
						job.numImagesStackedInc();
					}
					else
					{
						job.addTasksFailed(task);

					}

				}
				else
					job.addTasksFailed(task);


				// Compute the square of x
				//int y = ((Integer)x).intValue() * ((Integer)x).intValue();
			}

		}
		catch(InterruptedException e)
		{

			throw new Exception(e);
		}
	}




}


class KeyboardListener extends Thread 
{
	boolean DIPERF = true;


	String str = null;
	WorkerMain worker;
	boolean DEBUG;

	KeyboardListener(WorkerMain wr) 
	{
		this.worker = wr;

		this.DEBUG = this.worker.DEBUG;
	}

	public void run()
	{
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			//String str = "";
			//while (str != null) {
			if(DEBUG) System.out.print("WORKER: Press any key and hit enter to terminate this worker.");
			str = in.readLine();
			if(DEBUG) System.out.print("Shutting down worker...");

			worker.shutDown();
			// }
		}
		catch(IOException e)
		{

			if(DEBUG) System.out.print("WORKER: Error: " + e);
		}
	}

}


class LifetimeListener extends Thread 
{
	boolean DIPERF = true;


	String str = null;
	WorkerMain worker;
	boolean DEBUG;

	LifetimeListener(WorkerMain wr) 
	{
		this.worker = wr;

		this.DEBUG = this.worker.DEBUG;
	}

	public void run()
	{
		try
		{

			while(true)
			{

				if(worker.lt.getElapsedTime() >= worker.LIFETIME)
				{

					break;
				}

				try
				{
					this.sleep(1000);

				}
				catch(Exception e)
				{
					if(DEBUG) System.out.print("WORKER: Error, sleep was awoken early: " + e);
				}

			}
			if(DEBUG) if (DIPERF == false) System.out.println("WORKER: Worker lifetime of " + worker.LIFETIME/1000.0 + " seconds ended after " + worker.lt.getElapsedTime()/1000.0 + " seconds... exiting");
			if(DEBUG) System.out.print("Shutting down worker...");

			worker.shutDown();
		}
		catch(Exception e)
		{

			if(DEBUG) System.out.print("WORKER: Error: " + e);
			//return;
		}
	}

}


