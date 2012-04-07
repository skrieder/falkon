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

public class ImageStackingMain
{
    public static boolean DEBUG = false;
    public static boolean DIPERF = false;


    public static boolean measureMS = true;
    public static boolean measureMICRO = true;
    public static boolean measureMX = true;

    public static void main(String args[])
    {
        if (args.length != 6)
        {
            System.out.println("usage: java ImageStackingMain <stackListName> <numStacks> <height> <width> <resultFile> <numThreads>");
            System.exit(-1);
        }

        String stackListName = args[0];
        int numStacks = Integer.parseInt(args[1]);
        int height = Integer.parseInt(args[2]);
        int width = Integer.parseInt(args[3]);
        String resultFile = args[4];
        int numThreads = Integer.parseInt(args[5]);
        double sky = 1.0;
        double cal = 1.0;
        double ra = 0.0;
        double dec = 0.0;
        char band = 'r';

        List stackList = Collections.synchronizedList(new LinkedList());
        List finalStackList = Collections.synchronizedList(new LinkedList());

        StopWatch sw = new StopWatch(measureMS, measureMICRO, measureMX);

        try
        {
            BufferedReader in = new BufferedReader(new FileReader(stackListName));
            String str;
            int numStacksRead = 0;
            while ((str = in.readLine()) != null && numStacksRead < numStacks)
            {
                stackList.add(str);
                numStacksRead++;
            }
            in.close();
            System.out.println("read " + numStacksRead + " stack files...");
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        /*
        // For a set or list
        for (Iterator it=stackList.iterator(); it.hasNext(); )
        {
            String element = (String)it.next();
            //String result = new String(element + "_cutout_" +height +"x" + width+ ".fit");
            //int exitCode = ImageStacking.doCutout(element, ra, dec, band, height, width, sky, cal, result);
            double cutout[][] = ImageStacking.getCutout(element, ra, dec, band, height, width, sky, cal);
            //if (exitCode == 0)
            if (cutout != null)
            {
                if (DEBUG) System.out.println("Cutout of " + height +"x" + width + " on " + element + " succesful!");
                //finalStackList.add(result);
                finalStackList.add(cutout);
            } else
                if (DEBUG) System.out.println("Cutout of " + height +"x" + width + " on " + element + " failed!");

        } */

        // Create the work queue
        //WorkQueue queue = new WorkQueue();

        try
        {

            // Create a set of worker threads
            final int numWorkers = numThreads;
            Worker[] workers = new Worker[numWorkers];


            // Add special end-of-stream markers to terminate the workers
            for (int i=0; i<workers.length; i++)
            {
                stackList.add(Worker.NO_MORE_WORK);
            }

            sw.start();

            for (int i=0; i<workers.length; i++)
            {
                workers[i] = new Worker(stackList, finalStackList, ra, dec, band, height, width, sky, cal, DEBUG, DIPERF);
                workers[i].start();
            }

            // Add some work to the queue; block if the queue is full.
            // Note that null cannot be added to a blocking queue.
            //for (int i=0; i<100; i++) {
            //    queue.addWork(i);
            //}

            for (int i=0; i<workers.length; i++)
            {
                workers[i].join();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        // Create an array containing the elements in a list
        //String[] array = (String[])finalStackList.toArray(new String[finalStackList.size()]);
        //String finalStacking = new String("finalResult.fit");

        //int numStackings = ImageStacking.doStacking(finalStackList, height, width, resultFile);
        int numStackings = ImageStacking.doStackingArrays(finalStackList, height, width, resultFile);

        sw.stop();
        System.out.println("Performed " + numStackings + " in " + sw.getElapsedTime() + " ms, final stackings and stored in " + resultFile + ": throughput = " + numStackings/(sw.getElapsedTime()*1.0/1000.0));

        if (measureMS)
        {

            double totalTime = 0;
            // For both the keys and values of a map
            for (Iterator it=ImageStacking.perfProfMS.entrySet().iterator(); it.hasNext(); )
            {
                Map.Entry entry = (Map.Entry)it.next();
                //String key = (String)entry.getKey();
                totalTime += (((Long)entry.getValue()).longValue()*1.0);
                //System.out.println(key + " ==> " + value + " us");
            }


            // For both the keys and values of a map
            for (Iterator it=ImageStacking.perfProfMS.entrySet().iterator(); it.hasNext(); )
            {
                Map.Entry entry = (Map.Entry)it.next();
                String key = (String)entry.getKey();
                Long value = (Long)entry.getValue();
                System.out.println("MS:"+key + " ==> " + value.longValue() + " ms ( " + (value.longValue()*1.0)/totalTime + " )");
            }

            System.out.println("MS:TOTAL ==> " + totalTime + " ms ( 1.0 )");
        }

        if (measureMICRO)
        {
            double totalTime = 0;
            // For both the keys and values of a map
            for (Iterator it=ImageStacking.perfProfMICRO.entrySet().iterator(); it.hasNext(); )
            {
                Map.Entry entry = (Map.Entry)it.next();
                //String key = (String)entry.getKey();
                totalTime += (((Long)entry.getValue()).longValue()*1.0/1000.0);
                //System.out.println(key + " ==> " + value + " us");
            }


            // For both the keys and values of a map
            for (Iterator it=ImageStacking.perfProfMICRO.entrySet().iterator(); it.hasNext(); )
            {
                Map.Entry entry = (Map.Entry)it.next();
                String key = (String)entry.getKey();
                Long value = (Long)entry.getValue();
                System.out.println("MICRO:"+key + " ==> " + value.longValue()*1.0/1000.0 + " ms ( " + (value.longValue()*1.0/1000.0)/totalTime + " )");
            }

            System.out.println("MICRO:TOTAL ==> " + totalTime + " ms ( 1.0 )");
        }


        if (measureMX)
        {
            double totalTime = 0;
            // For both the keys and values of a map
            for (Iterator it=ImageStacking.perfProfMX.entrySet().iterator(); it.hasNext(); )
            {
                Map.Entry entry = (Map.Entry)it.next();
                //String key = (String)entry.getKey();
                totalTime += (((Double)entry.getValue()).doubleValue());
                //System.out.println(key + " ==> " + value + " us");
            }


            // For both the keys and values of a map
            for (Iterator it=ImageStacking.perfProfMX.entrySet().iterator(); it.hasNext(); )
            {
                Map.Entry entry = (Map.Entry)it.next();
                String key = (String)entry.getKey();
                Double value = (Double)entry.getValue();
                System.out.println("MX:"+key + " ==> " + value.doubleValue() + " ms ( " + (value.doubleValue()*1.0)/totalTime + " )");
            }

            System.out.println("MX:TOTAL ==> " + totalTime + " ms ( 1.0 )");
        }

    }

}

class Worker extends Thread
{
    // Special end-of-stream marker. If a worker retrieves
    // an Integer that equals this marker, the worker will terminate.
    static final Object NO_MORE_WORK = new Object();

    //WorkQueue q;
    double ra;
    double dec;
    int height;
    int width;
    double sky;
    double cal;
    char band; 
    List stackList;
    List finalStackList; 
    boolean DEBUG;
    boolean DIPERF;

    Worker(List stackList, List finalStackList, double ra, double dec, char band, int height, int width, double sky, double cal, boolean DEBUG, boolean DIPERF) {
        //this.q = q;
        this.stackList = stackList;
        this.finalStackList = finalStackList;
        this.ra = ra;
        this.dec = dec;
        this.height = height;
        this.width = width;
        this.sky = sky;
        this.cal = cal;
        this.band = band;
        this.DEBUG = DEBUG;
        this.DIPERF = DIPERF;
    }
    public void run() {
        try
        {
            while (true)
            {
                // Retrieve some work; block if the queue is empty
                Object x = stackList.remove(0);//q.getWork();

                // Terminate if the end-of-stream marker was retrieved
                if (x == null || x == NO_MORE_WORK)
                {
                    break;
                }

                double cutout[][] = ImageStacking.getCutout((String)x, ra, dec, band, height, width, sky, cal);
                //if (exitCode == 0)
                if (cutout != null)
                {
                    if (DEBUG) System.out.println("Cutout of " + height +"x" + width + " on " + x + " succesful!");
                    //finalStackList.add(result);
                    finalStackList.add(cutout);
                } else
                    if (!DEBUG) System.out.println("Cutout of " + height +"x" + width + " on " + x + " failed!");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
