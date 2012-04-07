package org.globus.GenericPortal.common;

import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.io.File;
import java.lang.*;
import java.util.zip.*;
import java.net.*;

public class FileUtility
{
    private static int BUF_SIZE = 1024;
    private static boolean DEBUG = false;
    private static boolean DIPERF = false;
    private static boolean PROFILING = false;
    private static String DATA_TRACE = null;

    private boolean NIO = false;
    private boolean SYS = true;


    public static StatCalc tpHist = new StatCalc();
    public static double acceptableThroughput = 250; //this is in bytes/ms
    public static long maxTransferTimeout = 1000; //in ms
    public static double acceptableThroughputPercent = 0.05; //this is in percentage (0...1) of average throughput

    public static int MaxBadTPCount = 1;
    public static int badTPCount = 0;

    public static int MaxRetryCount = 1;


    public FileUtility(String data_trace, boolean profiling, boolean diperf, boolean debug)
    {
        //this.BUF_SIZE = 32768;
        this.DIPERF = diperf;
        this.PROFILING = profiling;
        this.DEBUG = debug;
        this.DATA_TRACE = data_trace;
    }

    public FileUtility(int bufSize)
    {
        this.BUF_SIZE = bufSize;
    }

    public boolean checkFile(String fileName)
    {
        return(new File(fileName)).exists();
    }

    public boolean removeFile(String fileName)
    {
        //return true;
        return(new File(fileName)).delete();
    }


    //this is just a placeholder for the http and ftp cases
    public boolean fileExists(String fileURL)
    {
        if (fileURL == null)
        {
            return false;
        }

        if (fileURL.startsWith("http") || fileURL.startsWith("ftp"))
        {
            return true;
        }
        else if (fileURL.startsWith("/"))
        {
            return(new File(fileURL)).exists();
        }
        else
            return false;
    }


    public boolean fileExists_old(String fileURL)
    {
        if (fileURL == null)
        {
            return false;
        }

        if (fileURL.startsWith("http"))
        {
            URL url;
            URLConnection conn;
            int size;

            try
            {
                url = new URL(fileURL);

                conn = url.openConnection();
                //conn.setConnectTimeout(100);
                //conn.setReadTimeout(100);
                size = conn.getContentLength();

                conn.getInputStream().close();
                if (size < 0)
                    return false;
                //   System.out.println("Could not determine file size.");
                else
                    return true;
                //  System.out.println(args[0] + "\nSize: " + size);
            }
            catch (Exception e)
            {
                if (DEBUG) e.printStackTrace();
                return false;
            }


        }
        else if (fileURL.startsWith("/"))
        {
            return(new File(fileURL)).exists();
        }
        else
            return false;
    }

    //this is just a place holder...
    public long getFileLength(String fileURL)
    {
        try
        {
        
        if (fileURL != null)
        {
            if (DEBUG) System.out.println("getFileLength(): " + fileURL);
            return ((new File(fileURL)).length());
            


        }
        else
            return 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public long getFileLength_old(String fileURL)
    {
        if (fileURL != null)
        {
            if (DEBUG) System.out.println("getFileLength() of file " + fileURL + "...");
        }
        else
        {
            if (DEBUG) System.out.println("getFileLength() of file 'null', this should never happen...");

        }


        if (fileURL == null)
        {
            return 0;
        }

        if (fileURL.startsWith("http"))
        {
            URL url;
            URLConnection conn;
            int size;

            try
            {
                url = new URL(fileURL);
                conn = url.openConnection();

                //conn.setConnectTimeout(100);
                //conn.setReadTimeout(100);
                size = conn.getContentLength();
                conn.getInputStream().close();
                if (size < 0)
                    return 0;
                //   System.out.println("Could not determine file size.");
                else
                    return(long)size;
                //  System.out.println(args[0] + "\nSize: " + size);
            }
            catch (Exception e)
            {
                if (DEBUG) e.printStackTrace();
                return 0;
            }


        }
        else if (fileURL.startsWith("/"))
        {
            return(new File(fileURL)).length();
        }
        else
            return 0;

    }



    public long getFileLength(String fileURL, String cachePath, String logicalName, String cacheLocations[])
    {                   
        if (fileURL != null)
            if (DEBUG) System.out.println("getFileLength(fileURL, logicalName, cacheLocations): fileURL = " + fileURL);
            else
                if (DEBUG) System.out.println("getFileLength(fileURL, logicalName, cacheLocations): fileURL = null");

        if (logicalName != null)
            if (DEBUG) System.out.println("getFileLength(fileURL, logicalName, cacheLocations): logicalName = " + logicalName);
            else
                if (DEBUG) System.out.println("getFileLength(fileURL, logicalName, cacheLocations): logicalName = null");

        if (cacheLocations != null)
        {

            if (DEBUG) System.out.println("getFileLength(fileURL, logicalName, cacheLocations): cacheLocations.length = " + cacheLocations.length);

            for (int i=0;i<cacheLocations.length;i++)
            {
                if (DEBUG) System.out.println("getFileLength(fileURL, logicalName, cacheLocations): cacheLocations[" + i + "] = "+cacheLocations[i]);
            }

        }
        else
        {
        
            if (DEBUG) System.out.println("getFileLength(fileURL, logicalName, cacheLocations): cacheLocations = null");
        }


            if (logicalName != null && cachePath != null && fileExists(cachePath+"/"+logicalName))
            {
                return getFileLength(cachePath+"/"+logicalName);
            }
        else if (fileURL != null && fileExists(fileURL))
        {
            return getFileLength(fileURL);
        }
        else
        {
            return getCachedFileSize(logicalName,cacheLocations);
        }
        /*
        if (cacheLocations != null && cacheLocations.length > 0)
        {
            Collections.shuffle(Arrays.asList(cacheLocations));
            for (int i=0;i<cacheLocations.length;i++)
            {
                if (fileExists(cacheLocations[i]))
                {
                    return getFileLength(cacheLocations[i]);

                }

            }

        }

        return -1;
          */

    }


    //this is not working yet...
    public double copyNIO(URL src, File dst)
    {
        try
        {
            // Create channel on the source
            InputStream in = src.openStream();
            //BufferedInputStream bis = new BufferedInputStream(in);
            ReadableByteChannel srcChannel = Channels.newChannel(in);

            //FileChannel srcChannel = new FileInputStream(src).getChannel();

            // Create channel on the destination
            FileChannel dstChannel = new FileOutputStream(dst).getChannel();


            StopWatch sw = new StopWatch();
            sw.start();
            // Copy file contents from source to destination
            //might have to use a while loop here...
            //dstChannel.transferFrom(srcChannel, 0, srcChannel.size());


            ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);
            int len = 0;
            double bytesCopied = 0;

            while ((len = srcChannel.read(buffer)) != -1)
            {
                buffer.flip();
                dstChannel.write(buffer);
                buffer.clear();

                bytesCopied += len;
            }

            sw.stop();
            if (DIPERF) System.out.println("WORKER:getCachedFile():copyNIO(src,dst):dstChannel.transferFrom(): " + sw.getElapsedTime() + " ms");
            sw.reset();

            // Close the channels
            srcChannel.close();
            dstChannel.close();
            return bytesCopied;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return -1;
        }
    }


    public double copyNIO(String src, String dst)
    {
        try
        {
            // Create channel on the source
            FileChannel srcChannel = new FileInputStream(src).getChannel();

            // Create channel on the destination
            FileChannel dstChannel = new FileOutputStream(dst).getChannel();


            StopWatch sw = new StopWatch();
            sw.start();
            // Copy file contents from source to destination
            //might have to use a while loop here...
            long bytesTransfered = dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            sw.stop();
            if (DIPERF) System.out.println("WORKER:getCachedFile():copyNIO(src,dst):dstChannel.transferFrom(): " + sw.getElapsedTime() + " ms");
            sw.reset();

            // Close the channels
            srcChannel.close();
            dstChannel.close();
            return bytesTransfered*1.0; 
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return -1;
        }
    }


    public double copyNIO(File src, File dst)
    {
        try
        {
            // Create channel on the source
            FileChannel srcChannel = new FileInputStream(src).getChannel();

            // Create channel on the destination
            FileChannel dstChannel = new FileOutputStream(dst).getChannel();


            StopWatch sw = new StopWatch();
            sw.start();
            // Copy file contents from source to destination
            //might have to use a while loop here...
            long bytesTransfered = dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            sw.stop();
            if (DIPERF) System.out.println("WORKER:getCachedFile():copyNIO(src,dst):dstChannel.transferFrom(): " + sw.getElapsedTime() + " ms");
            sw.reset();

            // Close the channels
            srcChannel.close();
            dstChannel.close();
            return bytesTransfered*1.0;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    public double uncompressFileNIO(String inFilename, String outFilename) throws IOException
    {
        //FileInputStream fin = new FileInputStream(inFilename);
        FileOutputStream fout = new FileOutputStream(outFilename);
        InputStream in0 = null;

        GZIPInputStream in1 = null;
        if (inFilename.startsWith("http") || inFilename.startsWith("ftp"))
        {


            URL url = new URL(inFilename);
            //File file = new File(url);
            in0 = url.openStream();
            in0 = new BufferedInputStream(in0, BUF_SIZE);
            in1 = new GZIPInputStream(in0);
        }
        else
        {
            //InputStream in0 = new FileInputStream(inFilename);
            in0 = new FileInputStream(inFilename);
            in0 = new BufferedInputStream(in0, BUF_SIZE);

            in1 = new GZIPInputStream(in0);
        }


        //GZIPInputStream gzin = new GZIPInputStream(fin);
        ReadableByteChannel in = Channels.newChannel(in1);

        WritableByteChannel out = Channels.newChannel(fout);
        ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);
        int len = 0;
        double bytesCopied = 0;

        while ((len = in.read(buffer)) != -1)
        {
            buffer.flip();
            out.write(buffer);
            buffer.clear();
            bytesCopied += len;

        }

        in.close();
        out.close();
        in0.close();
        in1.close();
        fout.close();
        return bytesCopied;

    }

    public synchronized void incBadTPCount()
    {
        badTPCount++;

    }


    public synchronized void resetBadTPCount()
    {
        badTPCount=0;

    }


    public boolean isPerformanceAcceptable(long elapsedTime, double bytesTransfered)
    {

        //tpHist.enter(bytesTransfered*1.0/elapsedTime);

        //if (tpHist.getCount() < 10000)
        //{
        //    return true;
        //}

        if (elapsedTime < maxTransferTimeout)
        {

            if (PROFILING) System.out.println("WORKER:isPerformanceAcceptable():???: " + bytesTransfered + "B " + elapsedTime + "ms : " + bytesTransfered*1.0/elapsedTime + " B/ms ??? " + acceptableThroughput + " B/ms");
            return true;
        }
        else if (elapsedTime >= maxTransferTimeout && bytesTransfered*1.0/elapsedTime >= acceptableThroughput)
        //if (bytesTransfered*1.0/elapsedTime < Math.max(1,tpHist.getMean()*acceptableThroughputPercent))
        {
            if (PROFILING) System.out.println("WORKER:isPerformanceAcceptable():HIGH: " + bytesTransfered + "B " + elapsedTime + "ms : " + bytesTransfered*1.0/elapsedTime + " B/ms >= " + acceptableThroughput + " B/ms");
            return true;
        }
        else
        {

            if (PROFILING) System.out.println("WORKER:isPerformanceAcceptable():LOW: "  + bytesTransfered + "B " + elapsedTime + "ms : " + bytesTransfered*1.0/elapsedTime + " B/ms < " + acceptableThroughput + " B/ms");
            //incBadTPCount();
            return true;
            //should be false if operations are to be aborted
            //return false;
        }

        //if (badTPCount >= MaxBadTPCount)
        //{
        //    System.out.println("WORKER:FileUtility():isPerformanceAcceptable(): " + badTPCount + " slow throughput samples when only " + MaxBadTPCount + " allowed...");
        //    System.out.println("WORKER:FileUtility():isPerformanceAcceptable(): reseting badTPCount and failing transfer...");
        //    resetBadTPCount();
        //    return false;
        //}
        //else
        //    return true;





    }

    public void closeStreams(Process p)
    {
        try
        {


            p.getInputStream().close();
            p.getOutputStream().close();
            p.getErrorStream().close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    ///bin/sh -c 'gunzip cache3.dat.gz -c > cache5.dat'
    public double decompressSYS(String src, String dst) throws IOException 
    {
        //Process child = null;
        MyProcess myChild = new MyProcess(DEBUG);
        try
        {
            StopWatch sw = new StopWatch();
            double numBytes = -1;
            int exitValue = -1;
            String comArgs = null;

            File dstHandle = null;
            if (src.startsWith("/") && dst.startsWith("/"))
            {
                comArgs = new String("/bin/sh -c '/bin/gunzip -f " + src + " -c > " + dst + "'");
                //comArgs = new String("/bin/gunzip " + src);
                if (DEBUG) System.out.println("WORKER:FileUtility():decompressSYS(): executing " + comArgs);
                sw.start();
                //child = Runtime.getRuntime().exec(comArgs, null, null);
                exitValue = myChild.exec(comArgs, null, null);
                dstHandle = new File(dst);


                if (exitValue == 0)
                {
                    //File file = new File(dst);

                    // Get the number of bytes in the file
                    numBytes = (double)dstHandle.length();
                    if (DEBUG) System.out.println("WORKER:FileUtility():decompressSYS(): decompression succesful, saved in " + dst + ", " + numBytes + " B...");

                    return numBytes;


                }
                else
                {
                    if (DEBUG) System.out.println("WORKER:FileUtility():decompressSYS(): failed decompression of " + src + " to " + dst + "...");

                    return -222;
                }

            }
            else
            {
                if (DEBUG) System.out.println("WORKER:FileUtility():decompressSYS(): " + src + " and " + dst + " not both on local file system...");

                //if (child != null)
                //{
                //    closeStreams(child);
                //    
                //}

                return -223;
            }
        }
        catch (Exception e)
        {
            if (DEBUG) System.out.println("WORKER:FileUtility():decompressSYS():failed: " + e);
            e.printStackTrace();
            return -224;
        }


    }



    public double decompressSYS(String dst) throws IOException 
    {
        //Process child = null;
        MyProcess myChild = new MyProcess(DEBUG);

        StopWatch sw = new StopWatch();
        try
        {

            double numBytes = -1;
            int exitValue = -1;
            String comArgs = null;


            File dstHandle = null;
            if (dst.startsWith("/"))
            {
                comArgs = new String("/bin/gunzip -f " + dst + ".gz");
                if (DEBUG) System.out.println("WORKER:FileUtility():decompressSYS(): executing " + comArgs);

                sw.start();
                //child = Runtime.getRuntime().exec(comArgs, null, null);
                exitValue = myChild.exec(comArgs);

                dstHandle = new File(dst);

                if (exitValue == 0)
                {
                    //File file = new File(dst);

                    // Get the number of bytes in the file
                    numBytes = (double)dstHandle.length();
                    if (DEBUG) System.out.println("WORKER:FileUtility():decompressSYS(): decompression succesful, saved in " + dst + ", " + numBytes + " B...");

                    return numBytes;


                }
                else
                {
                    if (DEBUG) System.out.println("WORKER:FileUtility():decompressSYS(): failed decompression to " + dst + "...");

                    return -222;
                }
            }
            else
            {
                if (DEBUG) System.out.println("WORKER:FileUtility():decompressSYS(): " + dst + ".gz" + " is not on local file system...");

                //if (child != null)
                //{
                //   closeStreams(child);

                //}

                return -223;
            }

        }
        catch (Exception e)
        {
            if (DEBUG) System.out.println("WORKER:FileUtility():decompressSYS():failed: " + e);
            e.printStackTrace();
            return -224;
        }

    }





    public double uncompressFileSYS(String inFilename, String outFilename) throws IOException 
    {
        boolean success = false;
        double numBytes = copySYS(inFilename,outFilename+".gz");
        if (numBytes >= 0)
        {
            if (DEBUG) System.out.println("WORKER:FileUtility():uncompressFileSYS():copySYS(): copy succesful from " + inFilename + " to " + outFilename+".gz");

            //numBytes = decompressSYS(outFilename+".tmp.gz", outFilename);
            numBytes = decompressSYS(outFilename);
            if (numBytes >= 0)
            {
                if (DEBUG) System.out.println("WORKER:FileUtility():uncompressFileSYS():decompressSYS(): decompression succesful from " + outFilename+".gz" + " to " + outFilename);
                success = true;
                //try
                //{


                //(new File(outFilename+".tmp.gz")).delete();
                //if (DEBUG) System.out.println("WORKER:FileUtility():uncompressFileSYS():decompressSYS(): deleted " + outFilename+".tmp.gz");
                //}
                //catch (Exception e)
                //{
                //    if (DEBUG) e.printStackTrace();
                //}

            }
            else
            {

                if (DEBUG) System.out.println("WORKER:FileUtility():uncompressFileSYS():decompressSYS(): decompression failed from " + outFilename+".tmp.gz" + " to " + outFilename);
                //try
                //{

                //(new File(outFilename+".tmp.gz")).delete();
                //(new File(outFilename)).delete();

                //if (DEBUG) System.out.println("WORKER:FileUtility():uncompressFileSYS():decompressSYS(): deleted " + outFilename+".tmp.gz");
                //if (DEBUG) System.out.println("WORKER:FileUtility():uncompressFileSYS():decompressSYS(): deleted " + outFilename);
                //}
                //catch (Exception e)
                //{
                //    if (DEBUG) e.printStackTrace();
                //}

            }

        }
        else
        {

            if (DEBUG) System.out.println("WORKER:FileUtility():uncompressFileSYS():copySYS(): copy failed from " + inFilename + " to " + outFilename+".gz");
            try
            {

                (new File(outFilename+".gz")).delete();

                if (DEBUG) System.out.println("WORKER:FileUtility():uncompressFileSYS():decompressSYS(): deleted " + outFilename+".gz");
            }
            catch (Exception e)
            {
                if (DEBUG) e.printStackTrace();
            }

        }

        if (success)
        {

            if (DEBUG) System.out.println("WORKER:FileUtility():uncompressFileSYS(): copy+uncompress succesful from " + inFilename + " to " + outFilename);
            return numBytes;
        }
        else
        {
            if (DEBUG) System.out.println("WORKER:FileUtility():uncompressFileSYS(): copy+uncompress failed from " + inFilename + " to " + outFilename);

            return -1;
        }



    }



    public double uncompressFile(String inFilename, String outFilename) throws IOException 
    {
        boolean isPerformanceAcceptable = true;
        //try
        //{
        // Open the compressed file
        //String inFilename = "infile.gzip";

        StopWatch sw = new StopWatch();

        GZIPInputStream in = null;

        sw.start();

        if (inFilename.startsWith("http") || inFilename.startsWith("ftp"))
        {


            URL url = new URL(inFilename);
            //File file = new File(url);
            InputStream in0 = url.openStream();
            in0 = new BufferedInputStream(in0, BUF_SIZE);
            in = new GZIPInputStream(in0);
        }
        else
        {
            //InputStream in0 = new FileInputStream(inFilename);
            InputStream in0 = new FileInputStream(inFilename);
            in0 = new BufferedInputStream(in0, BUF_SIZE);

            in = new GZIPInputStream(in0);
        }

        sw.stop();
        if (DIPERF && sw.getElapsedTime() >= 1000) System.out.println("WORKER:getFile(in,out):uncompressFile():openInputStream: " + sw.getElapsedTime() + " ms");
        sw.reset();

        sw.start();
        // Open the output file
        //String outFilename = "outfile";
        OutputStream out = new FileOutputStream(outFilename);
        out = new BufferedOutputStream(out, BUF_SIZE);

        sw.stop();
        if (DIPERF && sw.getElapsedTime() >= 1000) System.out.println("WORKER:getFile(in,out):uncompressFile():openOutputStream: " + sw.getElapsedTime() + " ms");
        sw.reset();

        // Transfer bytes from the compressed file to the output file
        byte[] buf = new byte[BUF_SIZE];
        int len;

        StopWatch sw2 = new StopWatch();

        sw2.start();
        sw.start();
        double bytesCopied = 0;
        int iocalls = 0;
        long currentReadWriteTime = 0;
        while ((len = in.read(buf)) > 0)
        {

            iocalls++;

            sw.stop();
            if (DIPERF && sw.getElapsedTime() >= 1000) System.out.println("WORKER:getFile(in,out):uncompressFile():in.read():"+len+": " + sw.getElapsedTime() + " ms");
            currentReadWriteTime = sw.getElapsedTime();
            sw.reset();

            sw.start();

            out.write(buf, 0, len);
            sw.stop();
            if (DIPERF && sw.getElapsedTime() >= 1000) System.out.println("WORKER:getFile(in,out):uncompressFile():out.write():"+len+": " + sw.getElapsedTime() + " ms");
            currentReadWriteTime+=sw.getElapsedTime();
            sw.reset();

            bytesCopied += len;

            //its not clear to me that we want to use the instantaneous sample, or the overage sample...
            //isPerformanceAcceptable = isPerformanceAcceptable(sw2.getElapsedTime(), bytesCopied);
            isPerformanceAcceptable = isPerformanceAcceptable(currentReadWriteTime, len);
            if (isPerformanceAcceptable == false)
            {
                System.out.println("WORKER:getFile(in,out):uncompressFile(): aborting transfer from " + inFilename + " to " + outFilename + "...");
                break;
            }

            sw.start();
        }
        sw2.stop();
        if (DIPERF && sw2.getElapsedTime() >= 1000) System.out.println("WORKER:getFile(in,out):uncompressFile():read+write:"+bytesCopied+":"+iocalls+": " + sw2.getElapsedTime() + " ms");
        sw.reset();

        sw.start();
        // Close the file and stream
        in.close();
        out.close();
        sw.stop();
        if (DIPERF && sw.getElapsedTime() >= 1000) System.out.println("WORKER:getFile(in,out):uncompressFile():in+out.close(): " + sw.getElapsedTime() + " ms");
        sw.reset();

        if (isPerformanceAcceptable)
        {
            return bytesCopied;
        }
        else
        {
            return -2;
        }


        // } catch (Exception e)
        //{
        //    System.out.println("Error: " + e);
        //    return false;
        // }
    }

    public static boolean dataTraceDetails = true;
    private static final String LAN_GPFS = new String("LAN_GPFS");
    private static final String WAN_GPFS = new String("WAN_GPFS");
    private static final String LAN_NFS = new String("LAN_NFS");
    private static final String LOCAL = new String("LOCAL");
    private static final String HTTP = new String("HTTP");
    private static final String FTP = new String("FTP");
    private static final String GRIDFTP = new String("GRIDFTP");
    private static final String UNKOWN = new String("UNKOWN");
    private static String machID = null;


    private String getTraceSimple(String s)
    {   
        if (s != null)
        {

            try
            {

                if (machID == null)
                {
                    machID = java.net.InetAddress.getLocalHost().getCanonicalHostName();
                }

                //String tSrc = null
                if (s.startsWith("/disks/scratchgpfs1"))
                {
                    return LAN_GPFS;

                }
                else if (s.startsWith("/gpfs-wan"))
                {
                    return WAN_GPFS;

                }
                else if (s.startsWith("/home"))
                {
                    return LAN_NFS;

                }
                else if (s.startsWith("/"))
                {
                    return new String(LOCAL + "_" + machID);

                }
                else if (s.startsWith("http"))
                {
                    return new String(HTTP + "_" + (new URL(s)).getHost());

                }
                else if (s.startsWith("ftp"))
                {
                    return new String(FTP + "_" + (new URL(s)).getHost());

                }
                else if (s.startsWith("gsiftp"))
                {
                    return GRIDFTP;

                }
                else
                {
                    return new String(UNKOWN + "_" + s);

                }
            }
            catch (Exception e)
            {
                return new String(UNKOWN + "_" + s);

            }
        }
        else
        {
            return new String(UNKOWN + "_null");

        }


    }

    public BufferedWriter outDataTrace = null;


    public void writeDataTrace(String s)
    //public synchronized void writeLogTaskPerf(TaskPerformance tPerf)
    {

        if (DATA_TRACE != null )
        {
        
            try
            {
                if (outDataTrace == null)
                {

                    //Properties props = System.getProperties();
                    //String falkonServiceHome = (String)props.get("FALKON_LOGS");

                    String outTaskPerfFileName = null;

                    //if (falkonServiceHome != null)
                    //    outTaskPerfFileName = new String(falkonServiceHome + "/" + DATA_TRACE);
                    //else
                    //    outTaskPerfFileName = new String(DATA_TRACE);

                    

                    //taskPerfStartTime = System.currentTimeMillis() - tPerf.getTotalTime();

                    outDataTrace = new BufferedWriter(new FileWriter(DATA_TRACE));
                    //outDataTrace.write("\n");
                }


                outDataTrace.write(s + "\n");
                outDataTrace.flush();



                //outWorker.close();
            } catch (IOException e)
            {
                System.out.println("Error in writeDataTrace() when trying to log message: " + e.getMessage());
                if (DEBUG) e.printStackTrace();
            }
        }

    }


    public void printDataTrace(String src, String dst, double numBytes, long elapsedTimeMS)
    {

        try
        {
            if (src != null && dst != null)
            {

                if (dataTraceDetails == false)
                {
                    writeDataTrace(System.currentTimeMillis() + " : " + getTraceSimple(src) + " ==> " + getTraceSimple(dst) + " : " + numBytes + " B in " + elapsedTimeMS + " ms");
                }
                else
                {
                    writeDataTrace(System.currentTimeMillis() + " : " + src + " ==> " + dst + " : " + numBytes + " B in " + elapsedTimeMS + " ms");
                }
            }
            else if (src != null && dst == null)
            {
                if (dataTraceDetails == false)
                {
                    writeDataTrace(System.currentTimeMillis() + " : " + getTraceSimple(src) + " ==> " + getTraceSimple(dst) + " : " + numBytes + " B in " + elapsedTimeMS + " ms");
                }
                else
                {
                    writeDataTrace(System.currentTimeMillis() + " : " + src + " ==> null : " + numBytes + " B in " + elapsedTimeMS + " ms");
                }
            }
            else if (src == null && dst != null)

            {

                if (dataTraceDetails == false)
                {
                    writeDataTrace(System.currentTimeMillis() + " : " + getTraceSimple(src) + " ==> " + getTraceSimple(dst) + " : " + numBytes + " B in " + elapsedTimeMS + " ms");
                }
                else
                {
                    writeDataTrace(System.currentTimeMillis() + " : null ==> " + dst + " : " + numBytes + " B in " + elapsedTimeMS + " ms");
                }

            }
            else
            {
                if (dataTraceDetails == false)
                {
                    writeDataTrace(System.currentTimeMillis() + " : " + getTraceSimple(src) + " ==> " + getTraceSimple(dst) + " : " + numBytes + " B in " + elapsedTimeMS + " ms");
                }
                else
                {
                    writeDataTrace(System.currentTimeMillis() + " : null ==> null : " + numBytes + " B in " + elapsedTimeMS + " ms");
                }
            }

        }
        catch (Exception e)
        {
            System.out.println("exception in printDataTrace: " + e.getMessage());
            e.printStackTrace();


        }

    }


    // Copies src file to dst file.
    // If the dst file does not exist, it is created
    public double copySYS(String src, String dst) throws IOException 
    {

        StopWatch sw = new StopWatch();
        sw.start();
        //Process child = null;
        MyProcess myChild = new MyProcess(DEBUG);

        if (DEBUG) System.out.println("WORKER:FileUtility():copySYS(): attempting to copy from " + src + " to " + dst);
        try
        {

            double numBytes = -1;
            int exitValue = -1;
            String comArgs = null;


            File dstHandle = null;

            if ((src.startsWith("http") || src.startsWith("ftp")) && dst.startsWith("/"))
            {
                comArgs = new String("/usr/bin/wget -q -N -T 1 --glob=on -t 1 " + src + " -O " + dst);



            }
            else if (src.startsWith("/") && dst.startsWith("/"))
            {
                comArgs = new String("/bin/cp -u " + src + " " + dst);

            }

            //sw.start();
            if (comArgs != null)
            {

                //child = Runtime.getRuntime().exec(comArgs, null, null);
                exitValue = myChild.exec(comArgs);


            }


            if (exitValue == 0)
            {
                //File file = new File(dst);

                // Get the number of bytes in the file
                dstHandle = new File(dst);
                numBytes = (double)dstHandle.length();
                if (DEBUG) System.out.println("WORKER:FileUtility():copySYS(): copy succesful, saved in " + dst + ", " + numBytes + " B...");

                if (DATA_TRACE != null) printDataTrace(src,dst ,numBytes , sw.getElapsedTime());

                return numBytes;


            }
            else
            {
                if (DEBUG) System.out.println("WORKER:FileUtility():copySYS(): failed copy of " + src + " to " + dst + "...");
                if (DATA_TRACE != null) printDataTrace(src,dst ,-1 , sw.getElapsedTime());
                return -333;
            }
        }
        catch (Exception e)
        {
            if (DEBUG) System.out.println("WORKER:FileUtility():copySYS():failed: " + e);
            e.printStackTrace();
            if (DATA_TRACE != null) printDataTrace(src,dst ,-1 , sw.getElapsedTime());


            return -334;

        }



    }





    // Copies src file to dst file.
    // If the dst file does not exist, it is created
    public double copy(File src, File dst, int retry) throws IOException 

    {
        boolean isPerformanceAcceptable = true;

        /*
        OutputStream os = new FileOutputStream(desktoppath+FileName+".txt");
os = new BufferedOutputStream(os);
InputStream is = client.get(FileName);
is = new BufferedInputStream(is);
byte[] buffer = new byte[4096]; // Tune to get best performance
for (int bytesRead = 0; (bytesRead = is.read(buffer)) > 0;)
{
    os.write(buffer, 0, bytesRead);
}
os.flush();
os.close();
is.close();
*/


        StopWatch sw = new StopWatch();
        sw.start();
        InputStream in = new FileInputStream(src);
        in = new BufferedInputStream(in, BUF_SIZE);
        OutputStream out = new FileOutputStream(dst);
        out = new BufferedOutputStream(out, BUF_SIZE);


        sw.stop();
        if (DIPERF && sw.getElapsedTime() >= 1000) System.out.println("WORKER:getFile():copy(src,dst):openInput+OutputStreams: " + sw.getElapsedTime() + " ms");
        sw.reset();

        double bytesCopied = 0;

        StopWatch sw2 = new StopWatch();
        sw2.start();

        // Transfer bytes from in to out
        byte[] buf = new byte[BUF_SIZE];
        int len;
        sw.start();
        int iocalls = 0;

        long currentReadWriteTime = 0;
        while ((len = in.read(buf)) > 0)
        {
            iocalls++;
            sw.stop();
            if (DIPERF && sw.getElapsedTime() >= 1000) System.out.println("WORKER:getFile():copy(src,dst):in.read():"+len+": " + sw.getElapsedTime() + " ms");
            currentReadWriteTime = sw.getElapsedTime();

            sw.reset();


            sw.start();
            out.write(buf, 0, len);
            bytesCopied += len;
            sw.stop();
            if (DIPERF && sw.getElapsedTime() >= 1000) System.out.println("WORKER:getFile():copy(src,dst):out.write():"+len+": " + sw.getElapsedTime() + " ms");

            currentReadWriteTime+=sw.getElapsedTime();
            sw.reset();

            //its not clear to me that we want to use the instantaneous sample, or the overage sample...
            //isPerformanceAcceptable = isPerformanceAcceptable(sw2.getElapsedTime(), bytesCopied);
            isPerformanceAcceptable = isPerformanceAcceptable(currentReadWriteTime, len);

            if (isPerformanceAcceptable == false)
            {
                System.out.println("WORKER:getFile(in,out):copy(src,dst): aborting transfer from " + src.getCanonicalFile() + " to " + dst.getCanonicalFile() + "...");


                break;
            }


            sw.start();
        }
        out.flush();
        sw2.stop();
        if (DIPERF && sw2.getElapsedTime() >= 1000) System.out.println("WORKER:getFile():copy(src,dst):read+write:"+bytesCopied+":"+iocalls+": " + sw2.getElapsedTime() + " ms");
        sw2.reset();

        sw.start();
        in.close();
        out.close();
        sw.stop();
        if (DIPERF && sw.getElapsedTime() >= 1000) System.out.println("WORKER:getFile():copy(src,dst):in+out.close(): " + sw.getElapsedTime() + " ms");
        sw.reset();

        if (isPerformanceAcceptable)
        {
            return bytesCopied;
        }
        else
        {
            /*
            if (retry < MaxRetryCount)
            {
                retry++;
                bytesCopied = copy(src, dst, retry);
                if (bytesCopied >= 0)
                    return bytesCopied;
                else
                    return -2;

            }
            else */
            return -2;
        }


    }


    // Copies src file to dst file.
    // If the dst file does not exist, it is created
    public double copy(URL src, File dst, int retry) throws IOException {
        boolean isPerformanceAcceptable = true;

        StopWatch sw = new StopWatch();
        sw.start();
        InputStream in = src.openStream();
        in = new BufferedInputStream(in, BUF_SIZE);
        OutputStream out = new FileOutputStream(dst);
        out = new BufferedOutputStream(out, BUF_SIZE);
        sw.stop();
        if (DIPERF && sw.getElapsedTime() >= 1000) System.out.println("WORKER:getFile(in,out):copyURL(src,dst):input+OutputStream: " + sw.getElapsedTime() + " ms");
        sw.reset();


        double bytesCopied = 0;

        StopWatch sw2 = new StopWatch();
        sw2.start();

        // Transfer bytes from in to out
        byte[] buf = new byte[BUF_SIZE];
        int len;
        sw.start();
        int iocalls = 0;

        long currentReadWriteTime = 0;
        while ((len = in.read(buf)) > 0)
        {
            iocalls++;
            sw.stop();
            if (DIPERF && sw.getElapsedTime() >= 1000) System.out.println("WORKER:getFile(in,out):copyURL(src,dst):in.read():"+len+": " + sw.getElapsedTime() + " ms");

            currentReadWriteTime = sw.getElapsedTime();
            sw.reset();


            sw.start();
            out.write(buf, 0, len);
            bytesCopied += len;
            sw.stop();
            if (DIPERF && sw.getElapsedTime() >= 1000) System.out.println("WORKER:getFile(in,out):copyURL(src,dst):out.write():"+len+": " + sw.getElapsedTime() + " ms");

            currentReadWriteTime+=sw.getElapsedTime();
            sw.reset();

            //its not clear to me that we want to use the instantaneous sample, or the overage sample...
            //isPerformanceAcceptable = isPerformanceAcceptable(sw2.getElapsedTime(), bytesCopied);
            isPerformanceAcceptable = isPerformanceAcceptable(currentReadWriteTime, len);

            if (isPerformanceAcceptable == false)
            {
                System.out.println("WORKER:getFile(in,out):copyURL(src,dst): aborting transfer from " + src.toExternalForm() + " or " + src.toString() + " to " + dst.getCanonicalFile() + "...");

                break;
            }


            sw.start();
        }
        sw2.stop();
        if (DIPERF && sw2.getElapsedTime() >= 1000) System.out.println("WORKER:getFile(in,out):copyURL(src,dst):read+write:"+bytesCopied+":"+iocalls+": " + sw2.getElapsedTime() + " ms");
        sw2.reset();

        sw.start();
        in.close();
        out.close();
        sw.stop();
        if (DIPERF && sw.getElapsedTime() >= 1000) System.out.println("WORKER:getFile(in,out):copyURL(src,dst):in+out.close(): " + sw.getElapsedTime() + " ms");
        sw.reset();

        if (isPerformanceAcceptable)
        {
            return bytesCopied;
        }
        else
        {    /*
            if (retry < MaxRetryCount)
            {
                retry++;
                bytesCopied = copy(src, dst, retry);
                if (bytesCopied >= 0)
                    return bytesCopied;
                else
                    return -2;

            }
            else */
            return -2;
        }
    }




    public double getFile(String inFilename, String outFilename)
    {


        double bytesRead = -1;
        StopWatch sw = new StopWatch();
        if (inFilename != null)
        {

            try
            {
                if (DEBUG) System.out.println("WORKER:getFile(in,out): " + inFilename + " to " + outFilename + "...");

                if (inFilename.endsWith(".gz"))
                {
                    sw.start();
                    bytesRead = uncompressFileSYS(inFilename, outFilename);
                    sw.stop();
                    if (DIPERF) System.out.println("WORKER:getFile(in,out):uncompressFileSYS(): " + sw.getElapsedTime() + " ms");
                    if (DEBUG) System.out.println("WORKER:getFile(in,out):uncompressFileSYS(): " + sw.getElapsedTime() + " ms");
                    sw.reset();
                }
                else
                {
                    sw.start();
                    bytesRead = copySYS(inFilename, outFilename);
                    sw.stop();
                    if (DIPERF) System.out.println("WORKER:getFile(in,out):copySYS(): " + sw.getElapsedTime() + " ms");
                    if (DEBUG) System.out.println("WORKER:getFile(in,out):copySYS(): " + sw.getElapsedTime() + " ms");
                    sw.reset();

                }

                if (bytesRead > 0)
                {
                    if (DEBUG) System.out.println("WORKER:getFile(in,out): retrieved file " + inFilename + " (" + Math.round(bytesRead) + " B) and saved to " +outFilename + " successfully!");
                    return bytesRead;
                }
                else
                {
                    if (DEBUG) System.out.println("WORKER:getFile(in,out): failed to retrieve file " + inFilename + " or save to " + outFilename + " ...");
                    return -1;
                }

            }
            catch (Exception e)
            {
                if (DEBUG) System.out.println("WORKER:getFile(in,out): failed to retrieve file " + inFilename + " or save to " + outFilename + " ...");
                if (DEBUG) System.out.println("WORKER:getFile(in,out): Error: " + e);
                if (DEBUG) e.printStackTrace();
                return bytesRead;
            }

            
        }
        else
            return -1;




    }




    public double getCachedFile(String logicalName, String cacheLocations[], String outFilename, String machName)
    {

        if (DEBUG) System.out.println("WORKER:getCachedFile(in,out): " + logicalName + " to " + outFilename + "...");
        StopWatch sw = new StopWatch();
        String url = null;
        String tokens[] = null;
        double numBytes = -1;

        try
        {


            if (logicalName != null && cacheLocations != null && outFilename != null)
            {


                sw.start();
                Collections.shuffle(Arrays.asList(cacheLocations));
                sw.stop();
                if (DIPERF) System.out.println("WORKER:getCachedFile():Collections.shuffle(Arrays.asList(cacheLocations)): " + sw.getElapsedTime() + " ms");
                sw.reset();

                for (int i=0;i<cacheLocations.length;i++)
                {
                    sw.start();
                    //this should be set from a config file
                    int port = 59000;
                    tokens = cacheLocations[i].split(":");

                    if (tokens.length == 2 && !tokens[0].contentEquals(new StringBuffer(machName)))
                    {
                        //url = new String("http://"+tokens[0]+":"+port+"/"+logicalName);
                        url = new String("ftp://"+tokens[0]+":"+port+"/dev/shm/falkon-diffusion/"+logicalName);
                        try
                        {


                            numBytes = copySYS(url, outFilename);
                        }
                        catch (Exception e)
                        {
                            if (DEBUG) System.out.println("WORKER:getCachedFile(): failed to retrieved cache " + logicalName + " from " + url +" ...");
                            if (DEBUG) e.printStackTrace();
                        }
                    }
                    sw.stop();
                    if (DIPERF) System.out.println("WORKER:getCachedFile():copy(newURL(url),newFile(outFilename)): " + sw.getElapsedTime() + " ms");
                    if (DIPERF) System.out.println("WORKER:getCachedFile():copy("+url+","+outFilename+")): " + sw.getElapsedTime() + " ms");
                    sw.reset();


                }

            }
        }
        catch (Exception e)
        {
            if (DEBUG) System.out.println("WORKER:getCachedFile(): failed to retrieved cache " + logicalName + " from any other cache to cache on " + machName + " ...");
            if (DEBUG) System.out.println("WORKER:getCachedFile(): Error: " + e);
            if (DEBUG) e.printStackTrace();
        }


        if (numBytes > 0)
        {
            if (DEBUG) System.out.println("WORKER:getCachedFile(): retrieved cache " + logicalName + " (" + Math.round(numBytes) + " B) from " +url + " successfully!");
            //return true;
            return numBytes;
        }
        else
        {
            if (DEBUG) System.out.println("WORKER:getCachedFile(): failed to retrieved cache " + logicalName + " from any other cache to cache on " + machName + " ...");
            removeFile("/dev/shm/falkon-diffusion/"+logicalName);
            removeFile(outFilename);
            return -1;
        }

        

    }


    public long getCachedFileSize(String logicalName, String cacheLocations[])
    {
        String url = null;
        String tokens[] = null;
        if (logicalName != null && cacheLocations != null)
        {

            Collections.shuffle(Arrays.asList(cacheLocations));
            for (int i=0;i<cacheLocations.length;i++)
            {
                //this should be set from a config file
                int port = 59000;
                tokens = cacheLocations[i].split(":");

                try
                {

                    if (tokens.length == 2)
                    {
                        //url = new String("http://"+tokens[0]+":"+port+"/"+logicalName);
                        url = new String("ftp://"+tokens[0]+":"+port+"/dev/shm/falkon-diffusion/"+logicalName);


                        return getFileLength(url);

                    }
                }
                catch (Exception e)
                {
                    if (DEBUG) System.out.println("failed to get cache " + logicalName + " size from " + url +" ...");
                    if (DEBUG) e.printStackTrace();
                }
            }

        }
        if (DEBUG) System.out.println("failed to get cache " + logicalName + " size from any other cache...");
        //}

        return -1;
    }



    /*
public static void main(String args[])
{

if (args.length == 3 || args.length == 4 )
{
//if (args.length == 4 && args[3].contains("-debug"))
if (args.length == 4 && StringUtil.contains(args[3], "-debug"))
{
DEBUG = true;


}
else
DEBUG = false;
//String input1 = "/disks/scratchgpfs1/iraicu/das.sdss.org.gz/dr5/data/imaging/1035/40/corr/1/fpC-001035-z1-0011.fit.gz";
//String input1 = "http://das.sdss.org/dr5/data/imaging/1035/40/corr/1/fpC-001035-z1-0011.fit.gz";
String input = new String(args[0]);
String output = new String(args[1]);

int bufSize = Integer.parseInt(args[2]);


FileUtility ft = new FileUtility(bufSize);
long start = System.currentTimeMillis();
ft.uncompressFile(input, output);
long elapsedTimeMillis = System.currentTimeMillis()-start;
if (DEBUG) System.out.println("Uncompressed file " + input + " ==> " + output + " in " + elapsedTimeMillis + " ms");

} else
{
System.out.println("Usage: java FileUtility <source> <destination> <bufferSize> <-debug: true if present>");
}

}     */

}

