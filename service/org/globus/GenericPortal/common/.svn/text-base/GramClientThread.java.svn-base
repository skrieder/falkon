package org.globus.GenericPortal.common;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.io.*;

import org.apache.axis.components.uuid.UUIDGen;
import org.apache.axis.components.uuid.UUIDGenFactory;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.exec.client.GramJob;
import org.globus.exec.client.GramJobListener;
import org.globus.exec.generated.StateEnumeration;
import org.globus.exec.utils.FaultUtils;
import org.globus.exec.utils.ManagedJobFactoryConstants;
import org.globus.exec.utils.client.ManagedJobFactoryClientHelper;
import org.globus.exec.utils.rsl.RSLHelper;
import org.globus.wsrf.impl.security.authentication.Constants;
import org.globus.wsrf.impl.security.authorization.Authorization;
import org.globus.wsrf.impl.security.authorization.HostAuthorization;
import org.gridforum.jgss.ExtendedGSSCredential;
import org.gridforum.jgss.ExtendedGSSManager;
import org.ietf.jgss.GSSCredential;
import org.oasis.wsrf.faults.BaseFaultType;

/**
 * A Custom GRAM Client for GT4
 * Based on the GlobusRun command from Globus WS-GRAM implementation
 * GT4 WSRF/libraries are quired to compile this stuff plus the
 * following VM arguments must be used:
 * 	-Daxis.ClientConfigFile=/opt/gt-4.0.1/client-config.wsdd
 *  	-DGLOBUS_LOCATION=/opt/gt-4.0.1
 * java -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} gram/GramClientThread
 * @author Vladimir Silva
 * 
 */
public class GramClientThread extends Thread 
// Listen for job status messages
implements GramJobListener   
{
    private static Log logger = LogFactory.getLog(GramClientThread.class.getName());

    // Amount of time to wait for job status changes
    private static final long STATE_CHANGE_BASE_TIMEOUT_MILLIS = 60000;

    /**
     * Job submission member variables.
     */
    private GramJob job;

    // completed if Done or Failed
    private boolean jobCompleted = false; 
    // Batch runs will not wait for the job to complete
    private boolean batch;

    // Delegation
    private boolean limitedDelegation = true;
    private boolean delegationEnabled = true;

    // Don't print messages by default
    private boolean quiet = false;

    // proxy credential
    private String proxyPath = null;        

    /**
     * Application error state.
     */
    private boolean noInterruptHandling = false;
    private boolean isInterrupted = true;
    private boolean normalApplicationEnd = false;
    public String contact;
    public String rslFileName;
    //public GramClient gramClient;

    public GramClientThread(String contact, String rslFileName)
    {
        this.contact = contact;
        this.rslFileName = rslFileName;
        //this.gramClient = gramClient;

    }

    /**
     * Callback as a GramJobListener.
     * Will not be called in batch mode.
     */
    public void stateChanged(GramJob job)
    {
        StateEnumeration jobState = job.getState();
        boolean holding = job.isHolding();
        printMessage("========== State Notification ==========");
        printJobState(jobState, holding);
        printMessage("========================================");


        synchronized (this)
        {
            if (   jobState.equals(StateEnumeration.Done)
                   || jobState.equals(StateEnumeration.Failed))
            {

                printMessage("Exit Code: "
                             + Integer.toString(job.getExitCode()));

                this.jobCompleted = true;
            }

            notifyAll();

            // if we a running an interractive job,
            // prevent a hold from hanging the client
            if ( holding && !batch)
            {
                logger.debug(
                            "Automatically releasing hold for interactive job");
                try
                {
                    job.release();
                } catch (Exception e)
                {
                    String errorMessage = "Unable to release job from hold";
                    logger.debug(errorMessage, e);
                    printError(errorMessage + " - " + e.getMessage());
                }
            }
        }
    }

    static private EndpointReferenceType getFactoryEPR (String contact, String factoryType)
    throws Exception
    {
        URL factoryUrl = ManagedJobFactoryClientHelper.getServiceURL(contact).getURL();

        logger.debug("Factory Url: " + factoryUrl);
        return ManagedJobFactoryClientHelper.getFactoryEndpoint(factoryUrl, factoryType);
    }

    /**
     * Submit a WS-GRAM Job (GT4)
     * @param factoryEndpoint Factory endpoint reference
     * @param simpleJobCommandLine Executable (null to use a job file)
     * @param rslFileJob XML file (null to use a command line)
     * @param authorization Authorizarion: Host, Self, Identity
     * @param xmlSecurity XML Sec: Encryption or signature
     * @param batchMode Submission mode: batch will not wait for completion
     * @param dryRunMode Used to parse RSL
     * @param quiet Messages/NO messages
     * @param duration Duartion date
     * @param terminationDate Termination date
     * @param timeout  Job timeout (ms)
     */
    private void submitRSL(EndpointReferenceType factoryEndpoint,
                           String simpleJobCommandLine, 
                           File rslFile,
                           Authorization authorization, 
                           Integer xmlSecurity,
                           boolean batchMode, 
                           boolean dryRunMode, 
                           boolean quiet,
                           Date duration, 
                           Date terminationDate, 
                           int timeout) 
    throws Exception
    {
        this.quiet = quiet;
        this.batch = batchMode || dryRunMode; // in single job only.
        // In multi-job, -batch is not allowed. Dryrun is.

        if (batchMode)
        {
            printMessage("Warning: Will not wait for job completion, "
                         + "and will not destroy job service.");
        }

        if (rslFile != null)
        {
            try
            {
                this.job = new GramJob(rslFile);
            } catch (Exception e)
            {
                String errorMessage = "Unable to parse RSL from file "
                                      + rslFile;
                logger.debug(errorMessage, e);
                throw new IOException(errorMessage + " - " + e.getMessage());
            }
        } else
        {
            this.job = new GramJob(RSLHelper
                                   .makeSimpleJob(simpleJobCommandLine));
        }

        job.setTimeOut(timeout);
        job.setAuthorization(authorization);
        job.setMessageProtectionType(xmlSecurity);
        job.setDelegationEnabled(this.delegationEnabled);
        job.setDuration(duration);
        job.setTerminationTime(terminationDate);
        job.setTerminationTime(terminationDate);




        this.processJob(job, factoryEndpoint, batch);
    }

    /**
     * Submit the GRAM Job 
     * @param job
     * @param factoryEndpoint
     * @param batch
     * @throws Exception
     */
    private void processJob(GramJob job, 
                            EndpointReferenceType factoryEndpoint,
                            boolean batch)
    throws Exception
    {
        // load custom proxy (if any)
        if (proxyPath != null)
        {
            try
            {
                ExtendedGSSManager manager = (ExtendedGSSManager) ExtendedGSSManager
                                             .getInstance();
                String handle = "X509_USER_PROXY=" + proxyPath.toString();

                GSSCredential proxy = manager.createCredential(handle
                                                               .getBytes(),
                                                               ExtendedGSSCredential.IMPEXP_MECH_SPECIFIC,
                                                               GSSCredential.DEFAULT_LIFETIME, null,
                                                               GSSCredential.INITIATE_AND_ACCEPT);
                job.setCredentials(proxy);
            } catch (Exception e)
            {
                logger.debug("Exception while obtaining user proxy: ", e);
                printError("error obtaining user proxy: " + e.getMessage());
                // don't exit, but resume using default proxy instead
            }
        }

        // Generate a Job ID
        UUIDGen uuidgen     = UUIDGenFactory.getUUIDGen();
        String submissionID = "uuid:" + uuidgen.nextUUID();

        printMessage("Submission ID: " + submissionID);

        if (!batch)
        {
            job.addListener(this);
        }

        boolean submitted = false;
        int tries = 0;

        while (!submitted)
        {
            tries++;

            try
            {
                job.submit(factoryEndpoint, batch, this.limitedDelegation,
                           submissionID);
                submitted = true;
            } catch (Exception e)
            {
                logger.debug("Exception while submitting the job request: ", e);
                throw new IOException("Job request error: " + e);
            }
        }

        if (batch)
        {
            printMessage("CREATED MANAGED JOB SERVICE WITH HANDLE:");
            printMessage(job.getHandle());
        }

        if (logger.isDebugEnabled())
        {
            long millis = System.currentTimeMillis();
            BigDecimal seconds = new BigDecimal(((double) millis) / 1000);
            seconds = seconds.setScale(3, BigDecimal.ROUND_HALF_DOWN);
            logger.debug("Submission time (secs) after: " + seconds.toString());
            logger.debug("Submission time in milliseconds: " + millis);
        }

        if (!batch)
        {
            printMessage("WAITING FOR JOB TO FINISH");

            waitForJobCompletion(STATE_CHANGE_BASE_TIMEOUT_MILLIS);

            try
            {
                this.destroyJob(this.job); 
            } catch (Exception e)
            {
                printError("coudl not destroy");
            }

            if (this.job.getState().equals(StateEnumeration.Failed))
            {
                printJobFault(this.job);
            }
        }
    }

    /**
     * Since messaging is assumed to be unreliable (i.e. a notification could
     * very well be lost), we implement policy of pulling the remote state when
     * a given waited-for notification has not has been received after a
     * timeout. Note: this could however have the side-effect of hiding bugs in
     * the service-side notification implementation.
     * 
     * The base delay in parameter is doubled each time the wait times out
     * (binary exponential backoff). When a state change notification is
     * received, the time out delay is reset to the base value.
     * 
     * @param maxWaitPerStateNotificationMillis
     *            long base timeout for each state transition before pulling the
     *            state from the service
     */
    private synchronized void waitForJobCompletion(
                                                  long maxWaitPerStateNotificationMillis) 
    throws Exception
    {

        long durationToWait = maxWaitPerStateNotificationMillis;
        long startTime;
        StateEnumeration oldState = job.getState();

        // prints one more state initially (Unsubmitted)
        // but cost extra remote call for sure. Null test below instead
        while (!this.jobCompleted)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Job not completed - waiting for state change "
                             + "(timeout before pulling: " + durationToWait
                             + " ms).");
            }

            startTime = System.currentTimeMillis(); // (re)set start time
            try
            {
                wait(durationToWait); // wait for a state change notif
            } catch (InterruptedException ie)
            {
                String errorMessage = "interrupted thread waiting for job to finish";
                logger.debug(errorMessage, ie);
                printError(errorMessage); // no exiting...
            }

            // now let's determine what stopped the wait():

            StateEnumeration currentState = job.getState();
            // A) New job state change notification (good!)
            if (currentState != null && !currentState.equals(oldState))
            {
                oldState = currentState; // wait for next state notif
                durationToWait = maxWaitPerStateNotificationMillis; // reset
            } else
            {
                long now = System.currentTimeMillis();
                long durationWaited = now - startTime;

                // B) Timeout when waiting for a notification (bad)
                if (durationWaited >= durationToWait)
                {
                    if (logger.isWarnEnabled())
                    {
                        logger.warn("Did not receive any new notification of "
                                    + "job state change after a delay of "
                                    + durationToWait + " ms.\nPulling job state.");
                    }
                    // pull state from remote job and print the
                    // state only if it is a new state
                    //refreshJobStatus();
                    job.refreshStatus();

                    // binary exponential backoff
                    durationToWait = 2 * durationToWait;
                }
                // C) Some other reason
                else
                {
                    // wait but only for remainder of timeout duration
                    durationToWait = durationToWait - durationWaited;
                }
            }
        } 
    }

    /**
     * @param args
     */
    public void run()
    {
        /*
        String propFile = "/home/iraicu/java/GenericPortal_v1.2.0/properties-gt4.txt";
        Properties properties = new Properties();
            try {
                System.out.println("Properties file read from '" + propFile + "'");
                properties.load(new FileInputStream(propFile));
            } catch (IOException e) {
                System.out.println("Error in reading properties file...");
            }
          */

        Properties props = System.getProperties();
        props.put("axis.ClientConfigFile", "/scratch/local/iraicu/GT4.0.1/client-config.wsdd");
        props.put("GLOBUS_LOCATION", "/scratch/local/iraicu/GT4.0.1");
        //java -Daxis.ClientConfigFile=${GLOBUS_LOCATION}/client-config.wsdd -DGLOBUS_LOCATION=${GLOBUS_LOCATION} gram/WorkerRunThread
        System.setProperties(props);
        /*

        // Write properties file.
try {
    props.store(new FileOutputStream(propFile), null);

    System.out.println("Properties file written to '" + propFile + "'");
} catch (IOException e) 
{
    System.out.println("Error in writing properties file...");
}  */




        //should set these from a file
        //Properties properties = new Properties();
        //properties.setProperty("axis.ClientConfigFile", "${GLOBUS_LOCATION}/client-config.wsdd");
        //properties.setProperty("GLOBUS_LOCATION", "${GLOBUS_LOCATION}");

        /*        	
            // Read properties file.
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream("filename.properties"));
            } catch (IOException e) {
            }
    
            // Write properties file.
            try {
                properties.store(new FileOutputStream("filename.properties"), null);
            } catch (IOException e) {
            }
    
        Here is an example of the contents of a properties file:
    
            # a comment
            ! a comment
    
            a = a string
            b = a string with escape sequences \t \n \r \\ \" \' \ (space) \u0123
            c = a string with a continuation line \
                continuation line
            d.e.f = another string
          */

        /*
         *  Job test parameters (adjust to your needs)
         */
        // remote host
        //String contact 		= "tg-grid1.uc.teragrid.org";

        // Factory type: Fork, Condor, PBS, LSF
        //String factoryType	= ManagedJobFactoryConstants.FACTORY_TYPE.FORK;
        String factoryType  = ManagedJobFactoryConstants.FACTORY_TYPE.PBS;
        //String factoryType	= ManagedJobFactoryConstants.FACTORY_TYPE.LSF;
        //String factoryType	= ManagedJobFactoryConstants.FACTORY_TYPE.Condor;

        // Job XML
        //File rslFile 		= new File("/home/iraicu/java/WebServices/exGRAM_IBM/WorkerRSL.xml");
        File rslFile        = new File(rslFileName);
        //File rslFile = null;

        // Deafult Security: Host authorization + XML encryption
        Authorization authz = HostAuthorization.getInstance();
        Integer xmlSecurity = Constants.ENCRYPTION;

        // Submission mode: batch = will not wait
        boolean batchMode           = false;

        // a Simple command executable (if no job file)
        //String simpleJobCommandLine = "/bin/sleep 15";
        String simpleJobCommandLine = null;

        // Job timeout values: duration, termination times

        //Date serviceDuration 		= new Date(System.currentTimeMillis() + 3600000);
        Date serviceDuration        = null;
        Date serviceTermination         = null;
        //Date serviceTermination 	= new Date(System.currentTimeMillis() + 3600000);
        int timeout                 = GramJob.DEFAULT_TIMEOUT;


        try
        {
            //GramClientThread gram = new GramClientThread();
            //gram.submitRSL(getFactoryEPR(contact,factoryType)
            submitRSL(getFactoryEPR(contact,factoryType)
                           , simpleJobCommandLine, rslFile
                           , authz, xmlSecurity
                           , batchMode, false, false
                           , serviceDuration, serviceTermination, timeout );

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


/*
<job>
    <executable>/home/iraicu/java/WebServices/exGRAM_IBM/run.sh</executable>
    <environment>  
        <name>GLOBUS_LOCATION</name>  
        <value>/home/iraicu/java/GenericWorker.1.0</value>  
        <name>GLOBUS_PATH</name>  
        <value>/home/iraicu/java/GenericWorker.1.0</value> 
        <name>LD_LIBRARY_PATH</name>  
        <value>/home/iraicu/java/GenericWorker.1.0/lib</value>  
    </environment>    
    <maxWallTime>1</maxWallTime>
    <extensions>
         <resourceAllocationGroup>
            <hostType>ia32-compute</hostType>
            <hostCount>1</hostCount>
            <cpuCount>1</cpuCount>
            <processCount>1</processCount>
         </resourceAllocationGroup>
     </extensions>
</job>
*/



    /**
     * Print message to user if not in quiet mode.
     *
     * @param message the message to send to stdout.
     */
    private void printMessage(String message)
    {
        if (!this.quiet)
        {
            System.out.println(message);
        }
    }

    /**
     * Print error message with prefix.
     */
    private void printError(String message)
    {
        System.err.println(message);
    }

    private void printJobState(StateEnumeration jobState, boolean holding)
    {
        String holdString = "";
        if (holding) holdString = "HOLD ";
        printMessage("Job State: " + holdString + jobState.getValue());
    }

    private void printJobFault(GramJob job)
    {
        BaseFaultType fault = job.getFault();
        if (fault != null)
        {
            printMessage("Fault:\n" + FaultUtils.faultToString(fault));
        }
    }
/*
    private String convertEPRtoString(EndpointReferenceType endpoint)
        throws Exception
    {
        return ObjectSerializer.toString(
            endpoint,
            org.apache.axis.message.addressing.Constants.
                QNAME_ENDPOINT_REFERENCE);
    }
*/

    /**
     * destroys the job WSRF resource
     * Precondition: job ! =null && job.isRequested() && !job.isLocallyDestroyed()
     */
    private void destroyJob(GramJob job) throws Exception
    {
        printMessage("DESTROYING JOB RESOURCE");
        job.destroy();
        printMessage("JOB RESOURCE DESTROYED");
    }
}
