package org.globus.GenericPortal.clients.GPService_instance;

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



import java.io.FileInputStream;

import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.*;//ThreadLocal
import java.util.zip.*;

import nom.tam.fits.*;
import nom.tam.util.*;
import nom.tam.image.ImageTiler;

import JNI.FunTools.*;


import nom.tam.fits.*;
import nom.tam.util.*;
import nom.tam.image.ImageTiler;


import java.io.FileInputStream;
import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.zip.*;

import org.globus.GenericPortal.common.*;

import org.globus.GenericPortal.stubs.GPService_instance.*;


class ImageStacking extends Thread
{

    public static funsky_JNI funsky = null;//new funsky_JNI();
    public static boolean DEBUG = false;
    public static boolean DIPERF = true;
    public static boolean DIPERF_PRINT = false;

    public static Map perfProfMS = Collections.synchronizedMap(new HashMap());
    public static Map perfProfMICRO = Collections.synchronizedMap(new HashMap());
    public static Map perfProfMX = Collections.synchronizedMap(new HashMap());

    public static boolean measureMS = true;
    public static boolean measureMICRO = true;
    public static boolean measureMX = true;



    public static void perfProfiling(String msg, StopWatch sw)
    {
        synchronized (perfProfMS)
        {

            if (measureMS)
            {
                Long tt = (Long)perfProfMS.get(msg);
                long t = sw.getElapsedTime();
                if (tt == null)
                {
                    tt = new Long(t);
                } else
                    tt = new Long(tt.longValue() + t);

                perfProfMS.put(msg, tt);
                if (DIPERF_PRINT) System.out.println("DIPERF: MS: " + msg + " " + sw.getElapsedTime() + " ms");
            }
        }
        synchronized (perfProfMICRO)
        {

            if (measureMICRO)
            {
                Long tt = (Long)perfProfMICRO.get(msg);
                long t = sw.getElapsedTimeMicro();
                if (tt == null)
                {
                    tt = new Long(t);
                } else
                    tt = new Long(tt.longValue() + t);

                perfProfMICRO.put(msg, tt);
                if (DIPERF_PRINT) System.out.println("DIPERF: MICRO: " + msg + " " + sw.getElapsedTime() + " us");
            }
        }

        synchronized (perfProfMX)
        {
            if (measureMX)
            {
                Double tt = (Double)perfProfMX.get(msg);
                double t = sw.getElapsedTimeMX();

                if (tt == null)
                {
                    tt = new Double(t);
                } else
                    tt = new Double(tt.doubleValue() + t);

                perfProfMX.put(msg, tt);
                if (DIPERF_PRINT) System.out.println("DIPERF: MX: " + msg + " " + sw.getElapsedTime() + " ms");
            }
        }


    }



    ImageStacking()
    {
        //this.funsky = new funsky_JNI();
    }

    public static void initJNI()
    {
        try
        {

            if (funsky == null)
            {
                funsky = new funsky_JNI();
            }

        } catch (Exception e)
        {
            if (DEBUG) e.printStackTrace();
        }

    }


    public static byte[] readResult(String fileName) 
    {

        if (DEBUG) System.out.println("!!!!!!!!!!!!!!!!!!!: readResult(String fileName)...");
        StopWatch ct = new StopWatch(measureMS, measureMICRO, measureMS);
        ct.start();


        int bytesRead = 0;
        try
        {
            File file = new File(fileName);
            long length = file.length();
            InputStream is = new FileInputStream(file);
            //InputStream is = new InputStream(fileName);
            //is.close();

            //FileInputStream fis = new FileInputStream(fileName);
            //long length = fis.length();

            byte image[] = new byte[(int)length];

            bytesRead = is.read(image);
            is.close();


            ct.stop();

            if (DIPERF) perfProfiling("ASTRO:readResult():", ct);

            //System.out.println("Read " + bytesRead + " from file '" + fileName + "`!");    
            //Object o = null;
            return image;

        } catch (Exception e)
        {
            if (DEBUG) System.out.println("ERROR: only " + bytesRead + " read from file, reading results from '" + fileName + "`... possibly the worker node failed to write the result to disk..." + e);

            ct.stop();

            if (DIPERF) perfProfiling("ASTRO:readResult():error:", ct);

            return null;
        }

    }    


    public static int doStacking(List cutoutList, int HEIGHT, int WIDTH, String finalResult)
    {

        //WorkerWorkResponse wt = ap.workerWork(true);
        int numImagesStacked = 0;
        if (cutoutList.size() > 0)
        {

            if (DEBUG) if (DIPERF == false) System.out.println("stacking " + cutoutList.size() + " images...");

                //numTasksDone = stackQ.size();
                //String curImageFileName = cutoutList.remove(0);

            double[][] resultImageDouble = null;
            while (resultImageDouble == null && cutoutList.size() > 0)
            {
                resultImageDouble = readDouble((String)cutoutList.remove(0), HEIGHT, WIDTH);
            }
            if (resultImageDouble != null)
            {
                numImagesStacked++;

            }

            //double[][] resultImageDouble = (double[][])stackQ.remove();
            double[][] curImageInt = null;



            while (cutoutList.size() > 0)
            {
                //curImageInt = (double[][])stackQ.remove();
                curImageInt = null;
                while (curImageInt == null && cutoutList.size() > 0)
                {
                    curImageInt = readDouble((String)cutoutList.remove(0), HEIGHT, WIDTH);
                }

                if (curImageInt != null && resultImageDouble != null)
                {
                    resultImageDouble = sumArrayDouble(resultImageDouble, curImageInt, HEIGHT, WIDTH);
                    numImagesStacked++;
                }
            }

            //resultFile = home.scratchDisk + "results_" + System.currentTimeMillis() + "-" + home.rand.nextInt(2147483647)  + ".fit"; //set this to something random... plus add the path... needs to be in a globally accessible place


            try
            {

                writeDouble(resultImageDouble, finalResult);
                if (DEBUG) if (DIPERF == false) System.out.println("result file representing " + numImagesStacked + " images stacked saved in " + finalResult);
            } catch (Exception e)
            {

                if (DEBUG) e.printStackTrace();
                if (DEBUG) System.out.println("stacked " + numImagesStacked + ", but could not save results to " + finalResult);
                return -1;
            }

            if (DEBUG) if (DIPERF == false) System.out.println("result written to disk...");
        }
        return numImagesStacked;
    }   

    public static int doStackingArrays(List cutoutList, int HEIGHT, int WIDTH, String finalResult)
    {
        StopWatch sw = new StopWatch(measureMS, measureMICRO, measureMS);
        sw.start();

        //WorkerWorkResponse wt = ap.workerWork(true);
        int numImagesStacked = 0;
        if (cutoutList.size() > 0)
        {

            if (DEBUG) if (DIPERF == false) System.out.println("stacking " + cutoutList.size() + " images...");

                //numTasksDone = stackQ.size();
                //String curImageFileName = cutoutList.remove(0);

            double[][] resultImageDouble = null;
            while (resultImageDouble == null && cutoutList.size() > 0)
            {
                //resultImageDouble = readDouble((String)cutoutList.remove(0), HEIGHT, WIDTH);
                resultImageDouble = (double[][])cutoutList.remove(0);
            }
            if (resultImageDouble != null)
            {
                numImagesStacked++;

            }

            //double[][] resultImageDouble = (double[][])stackQ.remove();
            double[][] curImageInt = null;



            while (cutoutList.size() > 0)
            {
                //curImageInt = (double[][])stackQ.remove();
                curImageInt = null;
                while (curImageInt == null && cutoutList.size() > 0)
                {
                    //curImageInt = readDouble((String)cutoutList.remove(0), HEIGHT, WIDTH);
                    curImageInt = (double[][])cutoutList.remove(0);
                }

                if (curImageInt != null && resultImageDouble != null)
                {
                    resultImageDouble = sumArrayDouble(resultImageDouble, curImageInt, HEIGHT, WIDTH);
                    numImagesStacked++;
                }
            }

            //resultFile = home.scratchDisk + "results_" + System.currentTimeMillis() + "-" + home.rand.nextInt(2147483647)  + ".fit"; //set this to something random... plus add the path... needs to be in a globally accessible place

            sw.stop();

            if (DIPERF) perfProfiling("ASTRO:doStacking():", sw);

            sw.reset();
            sw.start();


            try
            {

                writeDouble(resultImageDouble, finalResult);
                if (DEBUG) if (DIPERF == false) System.out.println("result file representing " + numImagesStacked + " images stacked saved in " + finalResult);
            } catch (Exception e)
            {

                if (DEBUG) e.printStackTrace();
                if (DEBUG) System.out.println("stacked " + numImagesStacked + ", but could not save results to " + finalResult);
                return -1;
            }

            if (DEBUG) if (DIPERF == false) System.out.println("result written to disk...");
        }

        sw.stop();

        if (DIPERF) perfProfiling("ASTRO:writeStacking():", sw);

        sw.reset();

        return numImagesStacked;
    }   


    public static boolean enableJNI = false;

    public static Random randInt = new Random();


    public static double[] radec2xyJNI(String fName, double ra, double dec, int HEIGHT, int WIDTH)
    {
        if (enableJNI)
        {
        
        if (DEBUG) System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!: initJNI()...");
        initJNI();
        if (DEBUG) System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!: radec2xyJNI(String fName, double ra, double dec)...");

        return funsky.sky2image(fName, ra, dec);
        }
        else
        {
            if (DEBUG) System.out.println("radec2xyJNI() disabled, returning a random coordinate...");
            double coord[] = new double[2];
            coord[0] = (double)(randInt.nextInt(1489-HEIGHT)+HEIGHT/2-1);
            coord[1] = (double)(randInt.nextInt(2048-WIDTH)+WIDTH/2-1);
            return coord;


        }

    }




    public static int doCutout(String logicalImageName, double RA, double DEC, char BAND, int HEIGHT, int WIDTH, double SKY, double CAL, String logicalCutoutName) {
        if (DEBUG) System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!: doCutout(String logicalImageName, double RA, double DEC, char BAND, int HEIGHT, int WIDTH, double SKY, double CAL, String logicalCutoutName)...");
        int index = 0;
        int exitCode = 0;

        //if (DEBUG) System.out.println("ASTRO:doRun(): start " + index++);
        try
        {
            StopWatch sw = new StopWatch(measureMS, measureMICRO, measureMS);
            //while (true)
            //{
            sw.start();


            //if (DEBUG) System.out.println("ASTRO:doRun(): while() " + index++);

            //Object o = tQ.remove();
            //if (DEBUG) System.out.println("ASTRO:doRun(): tQ.remove() " + index++);
            //if (o == NO_MORE_WORK)
            //{
            //if (DEBUG) System.out.println("ASTRO:doRun(): if(o == NO_MORE_WORK) " + index++);
            //    break;
            //}
            //Task task = (Task) o;
            //if (DEBUG) System.out.println("ASTRO:doRun(): (Task)o " + index++);
            //if (task == null)
            //{
            //if (DEBUG) System.out.println("ASTRO:doRun(): if(task == null) " + index++);
            //    break;
            //}



            sw.stop();

            if (DIPERF) perfProfiling("ASTRO:getTask():", sw);


            sw.reset();

            boolean exists = true;

            if (exists)
            {

                //if (DEBUG) System.out.println("ASTRO:doRun(): if(exists) " + index++);

                sw.start();
                Fits img = read(logicalImageName);

                sw.stop();



                if (img != null)
                {
                    if (DIPERF) perfProfiling("ASTRO:open():", sw);


                    sw.reset();
                    sw.start();


                    //if (DEBUG) System.out.println("ASTRO:doRun(): if (img!=null) " + index++);


                    double[] coord = null;

                    //put this back... needed for the real stacking code
                    
                    //if (logicalImageName.startsWith("/") && logicalImageName.endsWith("fit"))
                    //{
                        if (DEBUG) System.out.println("query: " + logicalImageName + " at {" + RA + " " + DEC + "}");
                        coord = radec2xyJNI(logicalImageName, RA, DEC, HEIGHT, WIDTH);
                        //if (coord == null)
                        //{
                        //    if (DEBUG) System.out.println("query: " + logicalImageName + " coordinate lookup failed, choosing a random position...");
                        //    coord = new double[2];
                            //coord[0] = (double)(randInt.nextInt(1489-HEIGHT))+HEIGHT/2;
                            //coord[1] = (double)(randInt.nextInt(2048-WIDTH))+WIDTH/2;
                        //}

                    //}
                    //else
                    //{ 
                    //this is not correct, but it lets ms do some testing against the web server...
                    //if (DEBUG) System.out.println("query: " + logicalImageName + ", random as we don't support anything other than local file system without compression...");
                    //coord = new double[2];
                    //coord[0] = (double)(randInt.nextInt(2048-HEIGHT)+HEIGHT/2-1);
                    //coord[1] = (double)(randInt.nextInt(1489-WIDTH)+WIDTH/2-1);
                    //}

                    //double[] coord = radec2xy(logicalImageName, task.getTuple());
                    sw.stop();
                    if (coord == null)
                    {

                        if (DIPERF) perfProfiling("ASTRO:radec2xy():failed:", sw);

                        sw.reset();
                        //task.setErrorMsg("IMAGE_NOT_FOUND");
                        exitCode = -10;
                        img = null;
                        return exitCode;
                        //job.addTasksFailed(task);
                    } else if (((coord[0] - (HEIGHT-1)/2) < 0) || ((coord[0] + (HEIGHT-1)/2) >= 2048) || ((coord[1] - (WIDTH-1)/2) < 0) || ((coord[1] + (WIDTH-1)/2) >= 1489))
                    {
                        if (DIPERF) perfProfiling("ASTRO:radec2xy():failed2:", sw);
                        if (!DEBUG) System.out.println("ASTRO:radec2xy():failed2:" + coord[0] + " x " + coord[1] + " is outside the acceptable region for " + HEIGHT + " x " + WIDTH + " window and a min/max range of 0....1490 and 0... 2048");


                        sw.reset();

                        //task.setX_coord((int)coord[0]);
                        //task.setY_coord((int)coord[1]);
                        //task.setErrorMsg("COORDINATES_OUT_OF_BOUNDS");
                        //job.addTasksFailed(task);
                        exitCode = -11;
                        System.out.println("ASTRO:getCutout():failed: " + exitCode);

                        img = null;
                        return exitCode;

                    } else
                    {

                        if (DIPERF) perfProfiling("ASTRO:radec2xy():", sw);


                        sw.reset();

                        sw.start();

                        try
                        {

                            writeDouble(cropDouble(img, (coord[0] - HEIGHT/2), (coord[1] - WIDTH/2), HEIGHT, WIDTH, SKY, CAL), logicalCutoutName);
                            sw.stop();

                            if (DIPERF) perfProfiling("ASTRO:crop():", sw);

                            sw.reset();

                            exitCode = 0;
                            img = null;
                            return exitCode;

                        } catch (Exception e)
                        {
                            if (DEBUG) e.printStackTrace();
                            exitCode = -5;
                            img = null;
                            return exitCode;
                        }
                        //sQ.insert();



                        //job.numImagesStackedInc();
                    }
                } else
                {

                    //if (DEBUG) System.out.println("ASTRO:doRun(): if(img==null) " + index++);
                    if (DIPERF) perfProfiling("ASTRO:open():failed:", sw);


                    sw.reset();
                    //task.setErrorMsg("IMAGE_NOT_FOUND");

                    //job.addTasksFailed(task);
                    exitCode = -13;
                    img = null;
                    return exitCode;

                }

            } else
            {
                //task.setErrorMsg("IMAGE_NOT_FOUND");

                //job.addTasksFailed(task);

                exitCode = -14;
                //img = null;
                return exitCode;
            }
            //}

        } catch (Exception e)
        {

            if (DEBUG) e.printStackTrace();

            exitCode = -12;
            //img = null;
            return exitCode;
        }

        //in.close();
    }



    public static double[][] getCutout(String logicalImageName, double RA, double DEC, char BAND, int HEIGHT, int WIDTH, double SKY, double CAL) {
        if (DEBUG) System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!: doCutout(String logicalImageName, double RA, double DEC, char BAND, int HEIGHT, int WIDTH, double SKY, double CAL, String logicalCutoutName)...");
        int index = 0;
        int exitCode = 0;

        //if (DEBUG) System.out.println("ASTRO:doRun(): start " + index++);
        try
        {
            StopWatch sw = new StopWatch(measureMS, measureMICRO, measureMS);
            //while (true)
            //{
            sw.start();


            //if (DEBUG) System.out.println("ASTRO:doRun(): while() " + index++);

            //Object o = tQ.remove();
            //if (DEBUG) System.out.println("ASTRO:doRun(): tQ.remove() " + index++);
            //if (o == NO_MORE_WORK)
            //{
            //if (DEBUG) System.out.println("ASTRO:doRun(): if(o == NO_MORE_WORK) " + index++);
            //    break;
            //}
            //Task task = (Task) o;
            //if (DEBUG) System.out.println("ASTRO:doRun(): (Task)o " + index++);
            //if (task == null)
            //{
            //if (DEBUG) System.out.println("ASTRO:doRun(): if(task == null) " + index++);
            //    break;
            //}



            sw.stop();
            if (DIPERF) perfProfiling("ASTRO:getTask():", sw);

            sw.reset();

            boolean exists = true;

            if (exists)
            {

                //if (DEBUG) System.out.println("ASTRO:doRun(): if(exists) " + index++);

                sw.start();
                Fits img = read(logicalImageName);

                sw.stop();



                if (img != null)
                {

                    if (DIPERF) perfProfiling("ASTRO:open():", sw);

                    sw.reset();
                    sw.start();


                    //if (DEBUG) System.out.println("ASTRO:doRun(): if (img!=null) " + index++);


                    double[] coord = null;

                    //put this back... needed for the real stacking code
                    
                    //if (logicalImageName.startsWith("/") && logicalImageName.endsWith("fit"))
                    //{
                        if (DEBUG) System.out.println("query: " + logicalImageName + " at {" + RA + " " + DEC + "}");
                        coord = radec2xyJNI(logicalImageName, RA, DEC, HEIGHT, WIDTH);
                        //if (coord == null)
                        //{
                        //    if (DEBUG) System.out.println("query: " + logicalImageName + " coordinate lookup failed, choosing a random position...");
                        //    coord = new double[2];
                            //coord[0] = (double)(randInt.nextInt(1489-HEIGHT))+HEIGHT/2;
                            //coord[1] = (double)(randInt.nextInt(2048-WIDTH))+WIDTH/2;
                        //}
                    //}
                    //else
                    //{ 
                    //this is not correct, but it lets ms do some testing against the web server...
                    //if (DEBUG) System.out.println("query: " + logicalImageName + ", random as we don't support anything other than local file system without compression...");
                    //coord = new double[2];
                    //coord[0] = (double)(randInt.nextInt(1489-HEIGHT))+HEIGHT/2;
                    //coord[1] = (double)(randInt.nextInt(2048-WIDTH))+WIDTH/2;
                    //coord[0] = 1400;
                    //coord[1] = 900;
                    //}

                    //double[] coord = radec2xy(logicalImageName, task.getTuple());
                    sw.stop();
                    if (coord == null)
                    {

                        if (DIPERF) perfProfiling("ASTRO:radec2xy():failed:", sw);

                        sw.reset();
                        //task.setErrorMsg("IMAGE_NOT_FOUND");
                        exitCode = -10;
                        //return exitCode;
                        System.out.println("ASTRO:getCutout():failed: " + exitCode);
                        img = null;

                        return null;
                        //job.addTasksFailed(task);
                    } else if (((coord[0] - (HEIGHT-1)/2) < 0) || ((coord[0] + (HEIGHT-1)/2) >= 1489) || ((coord[1] - (WIDTH-1)/2) < 0) || ((coord[1] + (WIDTH-1)/2) >= 2048))
                    {
                        if (DIPERF) perfProfiling("ASTRO:radec2xy():failed2:", sw);


                        sw.reset();

                        //task.setX_coord((int)coord[0]);
                        //task.setY_coord((int)coord[1]);
                        //task.setErrorMsg("COORDINATES_OUT_OF_BOUNDS");
                        //job.addTasksFailed(task);
                        exitCode = -11;
                        if (!DEBUG) System.out.println("ASTRO:radec2xy():failed2:" + coord[0] + " x " + coord[1] + " is outside the acceptable region for " + HEIGHT + " x " + WIDTH + " window and a min/max range of 0....1490 and 0... 2048");

                        img = null;
                        //return exitCode;
                        System.out.println("ASTRO:getCutout():failed: " + exitCode);

                        return null;

                    } else
                    {
                        if (DIPERF) perfProfiling("ASTRO:radec2xy():", sw);



                        sw.reset();

                        //sw.start();

                        try
                        {

                            //writeDouble(cropDouble(img, (coord[0] - HEIGHT/2), (coord[1] - WIDTH/2), HEIGHT, WIDTH, SKY, CAL), logicalCutoutName);
                            double cutout[][] = cropDouble(img, (coord[0] - HEIGHT/2), (coord[1] - WIDTH/2), HEIGHT, WIDTH, SKY, CAL);;
                            //sw.stop();
                            //if (DIPERF) System.out.println("ASTRO:crop(): " + sw.getElapsedTime() + " ms");
                            //sw.reset();

                            exitCode = 0;
                            //return exitCode;
                            if (DEBUG) System.out.println("ASTRO:getCutout():success: " + exitCode);
                            img = null;

                            return cutout;

                        } catch (Exception e)
                        {
                            if (DEBUG) e.printStackTrace();
                            exitCode = -5;
                            System.out.println("ASTRO:getCutout():failed: " + exitCode);
                            //return exitCode;
                            img = null;
                            return null;
                        }
                        //sQ.insert();



                        //job.numImagesStackedInc();
                    }
                } else
                {

                    //if (DEBUG) System.out.println("ASTRO:doRun(): if(img==null) " + index++);
                    if (DIPERF) perfProfiling("ASTRO:open():failed:", sw);

                    sw.reset();
                    //task.setErrorMsg("IMAGE_NOT_FOUND");

                    //job.addTasksFailed(task);
                    exitCode = -13;
                    //return exitCode;
                    System.out.println("ASTRO:getCutout():failed: " + exitCode);
                    img = null;
                    return null;

                }

            } else
            {
                //task.setErrorMsg("IMAGE_NOT_FOUND");

                //job.addTasksFailed(task);

                exitCode = -14;
                //return exitCode;
                System.out.println("ASTRO:getCutout():failed: " + exitCode);
                //img = null;
                return null;
            }
            //}

        } catch (Exception e)
        {

            if (DEBUG) e.printStackTrace();

            exitCode = -12;
            //return exitCode;
            System.out.println("ASTRO:getCutout():failed: " + exitCode);
            //img = null;
            return null;
        }

        //in.close();
    }


    public static void write(Fits img, String fName)
    {
        BufferedFile bf;
        try
        {
            bf = new BufferedFile(fName, "rw");
            img.write(bf);
            bf.close();
        } catch (Exception e)
        {
            if (DEBUG) System.out.println("ERROR: write() : "+e);
        }

    }


    public static void writeInt(int[][] imgInt, String fName) throws Exception
    {

        Fits img = new Fits();

        BasicHDU hdu_img = Fits.makeHDU(imgInt);
        img.addHDU(hdu_img);

        BufferedFile bf;
        try
        {
            bf = new BufferedFile(fName, "rw");
            img.write(bf);
            bf.close();
        } catch (Exception e)
        {
            if (DEBUG) System.out.println("ERROR: write() : "+e);
        }
        img = null;

    }

    public static void writeDouble(double[][] imgInt, String fName) throws Exception
    {

        Fits img = new Fits();

        BasicHDU hdu_img = Fits.makeHDU(imgInt);
        img.addHDU(hdu_img);

        BufferedFile bf;
        try
        {
            bf = new BufferedFile(fName, "rw");
            img.write(bf);
            bf.close();
        } catch (Exception e)
        {
            if (DEBUG) System.out.println("ERROR: write() : "+e);
        }
        img = null;


    }


    public static Fits readGZ(String fName)
    {

        if (DEBUG) System.out.println("!!!!!!!!!!!!!!!!!!!: readGZ(String fName)...");
        Fits img = null;
        boolean zip = true;
        //BufferedReader gzipReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(fName))));
        try
        {
            //img = new Fits(fName);
            img = new Fits(new GZIPInputStream(new FileInputStream(fName)));
        } catch (Exception e)
        {
            if (DEBUG) System.out.println("ERROR: read() file '" + fName +"' : "+e);
        }
        return img;
    }

    public static Fits read(String fName)
    {
        Fits img = null;
        boolean zip = true;
        try
        {
            //if (DEBUG) 
            if (DEBUG) System.out.println("!!!!!!!!!!!!!!!!!!!: read() file '" + fName +"'...");
            img = new Fits(fName);
            
            if (img == null)
            {
                //if (DEBUG) 
                if (DEBUG) System.out.println("!!!!!!!!!!!!!!!!!!!: read() file '" + fName +"' failed");
            } else
            {
                //if (DEBUG) 
                if (DEBUG) System.out.println("!!!!!!!!!!!!!!!!!!!: read() file '" + fName +"' success");

            }

        } catch (Exception e)
        {
            if (DEBUG) System.out.println("ERROR: read() file '" + fName +"' : "+e);
            if (DEBUG) e.printStackTrace();
        }
        return img;
    }


    public static int[][] read(String fName, int h, int w) //throws Exception
    {
        //if (DEBUG) 
        if (DEBUG) System.out.println("!!!!!!!!!!!!!!!!!!!: read(String fName, int h, int w)...");


        Fits img = read(fName);
        int[][] fINT = new int[h][w];
        Object f = new Object();
        //we want to read the entire image
        int x = 0;
        int y = 0;


        try
        {

            //Fits img = new Fits();
            //img = img0;
            ImageHDU ih = (ImageHDU) img.readHDU();
            ImageTiler t = ih.getTiler();
            int[] dim = new int[]{h, w};
            int[] pos = new int[]{x, y};
            int[] size = new int[]{h, w};
            Object o = null;
            Object data = null;
            o = t.getTile(pos,size);
            data = ArrayFuncs.curl(o, dim);
            //Object o1 = nom.tam.util.ArrayFuncs.convertArray(hdu1.getKernel(), int.class);
            //f.addHDU(Fits.makeHDU(data));  
            //nom.tam.util.ArrayFuncs.convertArray(, int.class);
            f = nom.tam.util.ArrayFuncs.convertArray(data, int.class);

            fINT = (int[][]) f;
        } catch (Exception e)
        {
            if (DEBUG) System.out.println("ERROR: crop() : "+e);
        }
        return fINT;
    }

    public static double[][] readDouble(String fName, int h, int w) //throws Exception
    {
        if (DEBUG) System.out.println("!!!!!!!!!!!!!!!!!!!: readDouble(String fName, int h, int w)...");

        Fits img = null;
        try
        {

            img = read(fName);
            double[][] fINT = new double[h][w];
            Object f = new Object();
            //we want to read the entire image
            int x = 0;
            int y = 0;


            //try
            //{

            //Fits img = new Fits();
            //img = img0;
            ImageHDU ih = (ImageHDU) img.readHDU();
            ImageTiler t = ih.getTiler();
            int[] dim = new int[]{h, w};
            int[] pos = new int[]{x, y};
            int[] size = new int[]{h, w};
            Object o = null;
            Object data = null;
            o = t.getTile(pos,size);
            data = ArrayFuncs.curl(o, dim);
            //Object o1 = nom.tam.util.ArrayFuncs.convertArray(hdu1.getKernel(), int.class);
            //f.addHDU(Fits.makeHDU(data));  
            //nom.tam.util.ArrayFuncs.convertArray(, int.class);
            f = nom.tam.util.ArrayFuncs.convertArray(data, double.class);

            fINT = (double[][]) f;
            img = null;

            return fINT;
        } catch (Exception e)
        {
            //if (DEBUG) System.out.println("ERROR: crop() : "+e);
            if (DEBUG) e.printStackTrace();
            img = null;

            return null;
        }
        //return fINT;
    }



    public static Fits crop(Fits img0, int x, int y, int h, int w) //throws Exception
    {
        Fits f = new Fits();
        Fits img = null;

        try
        {

            img = new Fits();
            img = img0;
            ImageHDU ih = (ImageHDU) img.readHDU();
            ImageTiler t = ih.getTiler();
            int[] dim = new int[]{h, w};
            int[] pos = new int[]{x, y};
            int[] size = new int[]{h, w};
            Object o = null;
            Object data = null;
            o = t.getTile(pos,size);
            data = ArrayFuncs.curl(o, dim);
            f.addHDU(Fits.makeHDU(data));  
        } catch (Exception e)
        {
            if (DEBUG) System.out.println("ERROR: crop() : "+e);
        }
        img = null;
        return f;
    }




    //cal 1
    //sky 1000
    public static int[][] calibrateInt(int[] pix, int h, int w, double sky, double cal, double dxf, double dyf) 
    {

        int ntot = h*w;
        long below = 0;
        int[] fits = new int[ntot];
        int val = 0;

        for (int i=0; i<ntot;i++)
        {
            int j=0;
            if (j>=0 && j<ntot*5)
            {
                below = i + h; 
                if (below > 0 && below < ntot)
                {
                    // 4 point bi linear interpolation ... 
                    val = (int)(( (1- dxf) * (1- dyf) * pix[i]) +
                                dxf* (1-dyf)* pix[i+1] + dyf*(1-dxf)* pix[(int)below] +
                                (dxf * dyf)* pix[(int)below+1]) ; 

                } else
                {
                    val = pix[i];
                }
                // the histogram shows we are off by 1 but not adding it anymore
                // divide by calibration - not using roberts flux20s they are 
                // similar enough to cal in effect
                //val = MAX( (val-sky)*cal ,0.01);
                fits[j] = (int)((val-sky)*cal);
            }
            j += 5;
        } // for i, ntot

        int[][] fINT = new int[h][w];
        //convert to 2 dimentional array
        for (int i=0;i<h;i++)
        {
            for (int j=0;j<w;j++)
            {
                fINT[i][j] = fits[i*w+j];

            }
        }

        return fINT;


    }


    //cal 1
    //sky 1000
    public static int[][] calibrateInt2D(int[][] pix, int h, int w, double sky, double cal, double dxf, double dyf) 
    {

        int ntot = h*w;
        long below = 0;
        int[][] fits = new int[h][w];
        int val = 0;

        for (int i=0; i<h;i++)
        {
            for (int k=0;k<w;k++)
            {

                //int j=0;
                //if (j>=0 && j<ntot*5) {
                //    below = i*w+k + h; 
                if (i+1 < h && k+1 < w)
                {
                    // 4 point bi linear interpolation ... 
                    val = (int)(( (1- dxf) * (1- dyf) * pix[i][k]) +
                                dxf* (1-dyf)* pix[i][k+1] + dyf*(1-dxf)* pix[i+1][k] +
                                (dxf * dyf)* pix[i+1][k+1]) ; 

                } else
                {
                    val = pix[i][k];
                }
                // the histogram shows we are off by 1 but not adding it anymore
                // divide by calibration - not using roberts flux20s they are 
                // similar enough to cal in effect
                //val = MAX( (val-sky)*cal ,0.01);
                fits[i][k] = (int)((val-sky)*cal);
                //}
                //j += 5;
            }
        } // for i, ntot

        return fits;


    }

    public static double max(double a, double b)
    {
        if (a>b)
        {
            return a;
        } else
            return b;
    }


    //tis function is not correct, double check why?
    public static double[][] calibrateDouble2D_old(double[][] pix, int h, int w, double sky, double cal, double dxf, double dyf) 
    {
        //System.out.println("calibrateDouble2D(): h=" + h + " w=" + w + " sky=" + sky + " cal=" + cal + " dxf=" +dxf + " dyf="+dyf);


        //int ntot = h*w;
        //long below = 0;
        double[][] fits = new double[h][w];
        double val = 0;
        //this doesn't work.. should be set to false for some results...
        boolean isCalibrate = true;

        for (int i=0; i<h;i++)
        {
            for (int k=0;k<w;k++)
            {
                if (isCalibrate)
                {


                    if (i+1 < h && k+1 < w)
                    {
                        // 4 point bi linear interpolation ... 
                        val = (double)(( (1- dxf) * (1- dyf) * pix[i][k]) + 
                                       dxf* (1-dyf)* pix[i][k+1] + dyf*(1-dxf)* pix[i+1][k] +
                                       (dxf * dyf)* pix[i+1][k+1]) ; 

                    } else
                    {
                        val = pix[i][k];
                    }

                    pix[i][k] -= 1000;




                    //no bi-linear interpolation....
                    //val = pix[i][k];
                    //fits[i][k] = max((double)(val-sky)*cal, 0.01);
                    fits[i][k] = (double)(val-sky)*cal;

                    //System.out.println("calibrateDouble2D(): pix[" + i + "]["+k+"]=" + pix[i][k] + " val=" + val + " fits[" + i + "]["+k+"]=" + fits[i][k]);


                } else
                {

                    fits[i][k] = pix[i][k];
                }
            }
        } 

        return fits;


    }


    public static double[][] calibrateDouble2D(double[][] pix, int h, int w, double sky, double cal, double dxf, double dyf) 
    {

        StopWatch sw = new StopWatch(measureMS, measureMICRO, measureMS);
        sw.start();
        double[][] fits = new double[h][w];
        double val = 0;
        boolean isCalibrate = true;
        boolean isInterpolate = true;

        //float dxff = (float)dxf;
        //float dyff = (float)dyf;

        //InterpolationBilinear img = new InterpolationBilinear(64);


        double curCal = Math.pow(10.0,0.4*(cal - 2.31765 + 26.0));
        double curSky = 2.511886432E+10*sky;

        if (DEBUG) System.out.println("!!! cal = " + curCal + " sky = " + curSky);

        //needed for calibration
        for (int i=0; i<h;i++)
        {
            for (int k=0;k<w;k++)
            {
                pix[i][k] += 32768; //needed for correcting the incorrect interpretation that java has of the fits image 
                if (isCalibrate)
                {
                    pix[i][k] = (pix[i][k]-1000)*curCal - curSky;
                    //fits[i][k] = (val-1000)*cal - 2.511886432E+10*sky;

                } else
                {

                    pix[i][k] = pix[i][k] - 1000;
                }
            }
        } 
        sw.stop();


        if (DIPERF) perfProfiling("ASTRO:cropDouble():calibration:", sw);

        sw.reset();


        sw.start();

        // 4 point bi linear interpolation ... needed for shifting image...
        for (int i=0; i<h;i++)
        {
            for (int k=0;k<w;k++)
            {
                //double check this, the interpolation is not working :(
                if (isInterpolate)
                {
                    if (i+1 < h && k+1 < w)
                    {



                        val = (double)(( (1- dxf) * (1- dyf) * pix[i][k]) + 
                                       dxf* (1-dyf)* pix[i][k+1] + dyf*(1-dxf)* pix[i+1][k] +
                                       (dxf * dyf)* pix[i+1][k+1]) ; 
                        //System.out.println(val + " ?= " + val2 + " = interpolate(" + pix[i][k] + ", " + pix[i][k+1] + ", " + pix[i+1][k] + ", " + pix[i+1][k+1] + ", " + dxf + ", " + dyf + ")");
                    } else
                    {
                        val = pix[i][k];
                    }
                } else
                    val = pix[i][k];

                fits[i][k] = val;
            }
        } 


        sw.stop();

        if (DIPERF) perfProfiling("ASTRO:cropDouble():interpolation:", sw);

        sw.reset();

        // print2D(fits, h, w);


        return fits;


    }


    public static int[][] conv_1d_2d(int[] fits, int h, int w) 
    {

        int[][] fINT = new int[h][w];
        //convert to 2 dimentional array
        for (int i=0;i<h;i++)
        {
            for (int j=0;j<w;j++)
            {
                fINT[i][j] = fits[i*w+j];

            }
        }

        return fINT;


    }

    public static int[][] cropInt(Fits img, double dx, double dy, int h, int w, double sky, double cal) //throws Exception
    {

        int[][] fINT = new int[h][w];


        try
        {
            //Fits f = new Fits();

            int x = (int)Math.floor(dx);
            int y = (int)Math.floor(dy);
            double dxf = dx - x;
            double dyf = dy - y;


            Object f = new Object();


            //Fits img = new Fits();
            //img = img0;
            ImageHDU ih = (ImageHDU) img.readHDU();
            ImageTiler t = ih.getTiler();
            int[] dim = new int[]{h, w};
            int[] pos = new int[]{x, y};
            int[] size = new int[]{h, w};
            Object o = null;
            Object data = null;
            o = t.getTile(pos,size);

            //calibration

//	    int[] dim1 = new int[]{1, h*w};
//	    Object data1 = ArrayFuncs.curl(o, dim1);
//	    int[] fINT1 = new int[h*w];
//	    Object f1 = new Object(); 
//	    f1 = nom.tam.util.ArrayFuncs.convertArray(data, int.class);
//            fINT1 = (int[]) f1;

            data = ArrayFuncs.curl(o, dim);
            f = nom.tam.util.ArrayFuncs.convertArray(data, int.class);
            //fINT = (int[][]) f;


            fINT = calibrateInt2D((int[][]) f,h ,w ,sky ,cal ,dxf ,dyf );
            //fINT = calibrateInt(fINT1,h ,w ,sky ,cal ,dxf ,dyf );
            //fINT = conv_1d_2d(fINT1,h ,w);
//	    Object o1 = new Object();
//	    o1 = (Object) fINT1;

            //data = ArrayFuncs.curl(o, dim);
            //f = nom.tam.util.ArrayFuncs.convertArray(data, int.class);
            //fINT = (int[][]) f;


            //no calibration
            /*
            data = ArrayFuncs.curl(o, dim);
            f = nom.tam.util.ArrayFuncs.convertArray(data, int.class);
            fINT = (int[][]) f;
            */



            //data = ArrayFuncs.curl(data1, dim);




            //Object o1 = nom.tam.util.ArrayFuncs.convertArray(hdu1.getKernel(), int.class);
            //f.addHDU(Fits.makeHDU(data));  
            //nom.tam.util.ArrayFuncs.convertArray(, int.class);

        } catch (Exception e)
        {
            if (DEBUG) System.out.println("ERROR: crop() : "+e);
        }
        return fINT;
    }

    public static double[][] cropDouble(Fits img, double dx, double dy, int h, int w, double sky, double cal) //throws Exception
    {
        StopWatch sw = new StopWatch(measureMS, measureMICRO, measureMS);
        sw.start();

        double[][] fDOUBLE = new double[h][w];


        try
        {
            //Fits f = new Fits();

            int x = (int)Math.floor(dx);
            int y = (int)Math.floor(dy);
            double dxf = dx - x;
            double dyf = dy - y;



            Object f = new Object();


            //Fits img = new Fits();
            //img = img0;
            ImageHDU ih = (ImageHDU) img.readHDU();

            sw.stop();


            if (DIPERF) perfProfiling("ASTRO:cropDouble():readHDU():", sw);

            sw.reset();

            sw.start();

            ImageTiler t = ih.getTiler();


            int[] dim = new int[]{h, w};
            int[] pos = new int[]{x, y};
            int[] size = new int[]{h, w};
            Object o = null;
            Object data = null;
            o = t.getTile(pos,size);

            sw.stop();
            if (DIPERF) perfProfiling("ASTRO:cropDouble():getTile():", sw);

            sw.reset();


            //calibration

//	    int[] dim1 = new int[]{1, h*w};
//	    Object data1 = ArrayFuncs.curl(o, dim1);
//	    int[] fINT1 = new int[h*w];
//	    Object f1 = new Object(); 
//	    f1 = nom.tam.util.ArrayFuncs.convertArray(data, int.class);
//            fINT1 = (int[]) f1;


            sw.start();

            data = ArrayFuncs.curl(o, dim);


            sw.stop();
            if (DIPERF) perfProfiling("ASTRO:cropDouble():curl():", sw);


            sw.reset();

            sw.start();

            f = nom.tam.util.ArrayFuncs.convertArray(data, double.class);

            sw.stop();
            if (DIPERF) perfProfiling("ASTRO:cropDouble():convertArray():", sw);

            sw.reset();


            //fDOUBLE = (double[][]) f;
            //double check this call
            //boolean isCalibrate = true;

            //if (isCalibrate)
            //{
            //sw.start();
            fDOUBLE = calibrateDouble2D((double[][]) f,h ,w ,sky ,cal ,dxf ,dyf );
            //  sw.stop();
            //if (DIPERF) System.out.println("ASTRO:cropDouble():calibrateDouble2D(): " + sw.getElapsedTime() + " ms");
            //sw.reset();


            //} else
            //    fDOUBLE = (double[][]) f;




            //fINT = calibrateInt2D((int[][]) f,h ,w ,sky ,cal ,dxf ,dyf );
            //fINT = calibrateInt(fINT1,h ,w ,sky ,cal ,dxf ,dyf );
            //fINT = conv_1d_2d(fINT1,h ,w);
//	    Object o1 = new Object();
//	    o1 = (Object) fINT1;

            //data = ArrayFuncs.curl(o, dim);
            //f = nom.tam.util.ArrayFuncs.convertArray(data, int.class);
            //fINT = (int[][]) f;


            //no calibration
            /*
            data = ArrayFuncs.curl(o, dim);
            f = nom.tam.util.ArrayFuncs.convertArray(data, int.class);
            fINT = (int[][]) f;
            */



            //data = ArrayFuncs.curl(data1, dim);




            //Object o1 = nom.tam.util.ArrayFuncs.convertArray(hdu1.getKernel(), int.class);
            //f.addHDU(Fits.makeHDU(data));  
            //nom.tam.util.ArrayFuncs.convertArray(, int.class);

        } catch (Exception e)
        {
            if (DEBUG || DIPERF) System.out.println("ERROR: crop() : "+e);
        }
        return fDOUBLE;
    }



    public static int[][] sumArrayInt(int[][] img1, int[][] img2, int h, int w)
    {

        int[][] sum_array = new int[h][w];
        for (int i=0;i<h;i++)
        {
            for (int j=0;j<w;j++)
            {
                sum_array[i][j] = img1[i][j] + img2[i][j];
            }

        }    
        return sum_array;
    }
    public static double[][] sumArrayDouble(double[][] img1, double[][] img2, int h, int w)
    {

        double[][] sum_array = new double[h][w];
        for (int i=0;i<h;i++)
        {
            for (int j=0;j<w;j++)
            {
                sum_array[i][j] = img1[i][j] + img2[i][j];
            }

        }    
        return sum_array;
    }


    public static void print2D(double[][] pix, int h, int w) 
    {
        System.out.println("DOUBLE: Printing image [" + h + "][" + w + "]:");

        for (int i=0; i<h;i++)
        {
            for (int k=0;k<w;k++)
            {
                System.out.print(pix[i][k] + " ");
            }
            System.out.println("");
        } 

        System.out.println("");

    }


}
