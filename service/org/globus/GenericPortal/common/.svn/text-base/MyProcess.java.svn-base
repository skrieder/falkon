package org.globus.GenericPortal.common;

import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.io.File;
import java.lang.*;
import java.util.zip.*;
import java.net.*;


import java.util.*;
import java.io.*;
import java.io.File;
import java.lang.*;
import java.util.zip.*;
import java.net.*;


public class MyProcess
{
    //private Process child = null;

    boolean DEBUG = false;
    boolean shuttingDown = false;
    private static int id;
    private static boolean firstTime = true;


    private List streamBufferOut = new LinkedList();
    private List streamBufferErr = new LinkedList();

    boolean saveOutput = false;

    public MyProcess(boolean DEBUG)
    {
        this.DEBUG = DEBUG;
        this.saveOutput = false;

        if (firstTime)
        {
            firstTime = false;
            id = 0;
        }


    }

    public MyProcess(boolean DEBUG, boolean saveOutput)
    {
        this.DEBUG = DEBUG;
        this.saveOutput = saveOutput;
        if (firstTime)
        {
            firstTime = false;
            id = 0;
        }


    }



    public MyProcess()
    {
        //this.DEBUG = false;
        if (firstTime)
        {
            firstTime = false;
            id = 0;
        }


    }

    public void freeState()
    {
        shuttingDown = true;
        if (streamBufferOut !=  null)
        {
            streamBufferOut.clear();
            streamBufferOut =  new LinkedList();
        }
        if (streamBufferErr !=  null)
        {
            streamBufferErr.clear();
            streamBufferErr =  new LinkedList();
        }


    }


    public String getStreamOutFirstLine()
    {

        if (streamBufferOut != null)
        {

            try
            {

                for (Iterator it=streamBufferOut.iterator(); it.hasNext(); )
                {
                    String element = (String)it.next();
                    if (element != null)
                    {
                        return element;
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }


    public String getStreamOut()
    {

        String buf = new String("");

        if (streamBufferOut != null)
        {

            try
            {

                for (Iterator it=streamBufferOut.iterator(); it.hasNext(); )
                {
                    String element = (String)it.next();
                    if (element != null)
                    {

                        buf = buf.concat(element + "\n");
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return buf;
    }

    public String getStreamErr()
    {

        String buf = new String("");

        if (streamBufferErr != null)
        {

            try
            {

                for (Iterator it=streamBufferErr.iterator(); it.hasNext(); )
                {
                    String element = (String)it.next();
                    if (element != null)
                    {

                        buf = buf.concat(element + "\n");
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return buf;
    }


    public boolean searchStreamBuffers(String pattern)
    {

        // For a set or list
        for (Iterator it=streamBufferOut.iterator(); it.hasNext(); )
        {
            String element = (String)it.next();
            if (StringUtil.contains(element, pattern))
            {
                return true;
            }
        }

        for (Iterator it=streamBufferErr.iterator(); it.hasNext(); )
        {
            String element = (String)it.next();
            if (StringUtil.contains(element, pattern))
            {
                return true;
            }
        }


        return false;
    }


    private void closeStreams(Process p) throws IOException {
        p.getInputStream().close();
        p.getOutputStream().close();
        p.getErrorStream().close();
    }

    public synchronized int getID()
    {
        return id;
    }

    public synchronized int nextID()
    {
        id++;
        return id;
    }


    public double copyFile(File dir, String fSrc, String fDst) 
    {
        int bufSize = 32768;

        File src = null;
        File dst = null;

        if (fSrc == null || fDst == null)
        {
            return -4;
        }

        if (!fSrc.startsWith("/") && dir != null)
        {
            src = new File(dir, fSrc);


        }
        else
        {
            src = new File(fSrc);

        }

        if (!fDst.startsWith("/") && dir != null)
        {
            dst = new File(dir, fDst);


        }
        else
        {
            dst = new File(fDst);

        }



        // Copies src file to dst file.
        // If the dst file does not exist, it is created


        try
        {

            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst);

            double bytesCopied = 0;

            // Transfer bytes from in to out
            byte[] buf = new byte[bufSize];
            int len;
            while ((len = in.read(buf)) > 0)
            {
                out.write(buf, 0, len);

                bytesCopied += len;
            }
            in.close();
            out.close();

            return bytesCopied;
        } catch (Exception e)
        {
            if (DEBUG) e.printStackTrace();
            return -1;
        }
    }


    public int exec(String comArgs)
    {
        return exec(comArgs, null, null);
    }

    public int exec(String comArgs[])
    {
        return exec(comArgs, null, null);
    }


    public int exec(String comArgs, String[] env, File dir, long maxWallTime)
    {
        return exec(comArgs, null, env, dir, maxWallTime);
    }

    public int exec(String comArgs[], String[] env, File dir, long maxWallTime)
    {
        return exec(null, comArgs, env, dir, maxWallTime);
    }

    public int execJava(String comArgs, String comArgsArray[], String[] env, File dir, long maxWallTime)
    {
        //doesn't do anything with the maxWallTime... should in the future...
        if (comArgsArray.length == 4)
        {
            if (comArgsArray[0].startsWith("copy"))
            {

                if (DEBUG) System.out.println("java copy (" + dir + "), " + comArgsArray[1] + " ==> " + comArgsArray[2]);
                
                
            double numBytes = copyFile(dir, comArgsArray[1], comArgsArray[2]);
            if (numBytes <= 0)
            {
                if (DEBUG) System.out.println("failure in java copy command...");
                return -3;
            }


            try
            {
                long t = Long.parseLong(comArgsArray[3]);
                if (DEBUG) System.out.println("java sleep: " + t + " ms");
                Thread.sleep(t);
            }
            catch (Exception e)
            {
                if (DEBUG) System.out.println("failure in java sleep command...");
                if (DEBUG) e.printStackTrace();
                return -2;
            }

            
            if (DEBUG) System.out.println("java copy+sleep succesful!");
            return 0;
            }

        } 

            if (DEBUG) System.out.println("unsupported java command...");
            return -1;
        

    }

    public int exec(String comArgs, String comArgsArray[], String[] env, File dir, long maxWallTime)
    {
        try
        {
        
        if (maxWallTime <= 0)
        {
            if (DEBUG) System.out.println("Setting the maxWallTime to 0, waiting indefenitly for command to execute...");

            if (comArgs != null)
            {
                return exec(comArgs, env, dir);
            } else if (comArgsArray != null)
            {
                return exec(comArgsArray, env, dir);
            } else
            {
                //both commands are null
                return -666;
            }

        } else
        {

            if (comArgs != null && comArgs.startsWith("java"))
            {
                return execJava(comArgs, comArgsArray, env, dir, maxWallTime);


            } 
            else if (comArgsArray != null && comArgsArray.length >= 1 && comArgsArray[0].startsWith("java"))
            {
                String tComArgsArray[] = new String[comArgsArray.length - 1];
                for (int i=0;i<comArgsArray.length-1;i++)
                {
                    tComArgsArray[i] = comArgsArray[i+1];

                }
                return execJava(comArgsArray[0], tComArgsArray, env, dir, maxWallTime);
            }
            else
            {

                //DEBUG = true;

                StreamGobbler errorGobbler = null;
                StreamGobbler outputGobbler = null;
                streamBufferErr =  new LinkedList();
                streamBufferOut =  new LinkedList();
                Process child = null;
                child = null;
                int exitVal = -555;
                try
                {
                    if (DEBUG)
                    {
                        if (comArgs != null)
                        {
                            System.out.print("Executing command " + nextID() +": " + comArgs + " ");
                        } else if (comArgsArray != null)
                        {
                            System.out.print("Executing command " + nextID() +": ");
                            for (int i=0;i<comArgsArray.length;i++)
                            {

                                System.out.print(comArgsArray[i] + " ");
                            }
                        } else
                        {
                            //both commands are nnull
                            //return exitVal;
                        }

                        if (env != null)
                        {
                            for (int i=0;i<env.length;i++)
                            {
                                System.out.print(env[i] + " ");
                            }
                        }

                        if (dir != null)
                        {

                            System.out.print("[" + dir.getCanonicalPath() + "]");
                        }

                        System.out.println("");



                    }

                    if (comArgs != null)
                    {
                        child = Runtime.getRuntime().exec(comArgs, env, dir);
                    } else if (comArgsArray != null)
                    {
                        child = Runtime.getRuntime().exec(comArgsArray, env, dir);
                    } else
                    {
                        //both commands are null, nothing to execute
                        return exitVal;
                    }

                    // any error message?
                    errorGobbler = new StreamGobbler(child.getErrorStream(), "ERR", DEBUG, this);            

                    // any output?
                    outputGobbler = new StreamGobbler(child.getInputStream(), "OUT", DEBUG, this);

                    // kick them off
                    errorGobbler.start();
                    outputGobbler.start();

                    // any error???
                    //exitVal = child.waitFor();

                    boolean processStillRunning = true;
                    long pool_ms = 10;

                    StopWatch pTime = new StopWatch();
                    pTime.start();


                    if (DEBUG) System.out.println("Setting the maxWallTime to "+ maxWallTime + " ms...");
                    while (processStillRunning && pTime.getElapsedTime() < maxWallTime)
                    {
                        try
                        {
                            exitVal = child.exitValue();
                            processStillRunning = false;

                        } catch (Exception e)
                        {
                            try
                            {

                                Thread.sleep(pool_ms);
                                pool_ms = Math.min(pool_ms*2, 1000);

                            } catch (Exception ee)
                            {
                            }
                        }
                    }


                    if (DEBUG) System.out.println("326: Exit code for command "+getID()+": " + exitVal);
                    if (pTime.getElapsedTime() >= maxWallTime)
                    {
                        shuttingDown = true;
                    }

                    try
                    {
                        errorGobbler.join(10000);
                        if (errorGobbler.isAlive())
                        {
                            errorGobbler.interrupt();

                        }
                    } catch (Exception ee)
                    {
                        ee.printStackTrace();
                    }

                    try
                    {
                        outputGobbler.join(10000);
                        if (outputGobbler.isAlive())
                        {
                            outputGobbler.interrupt();

                        }
                    } catch (Exception ee)
                    {
                        ee.printStackTrace();
                    }



                    streamBufferErr = errorGobbler.getStreamBufferList();
                    streamBufferOut = outputGobbler.getStreamBufferList();


                    try
                    {
                        if (child != null)
                        {

                            closeStreams(child); 
                            child.destroy(); 
                        }
                    } catch (Exception ee)
                    {
                        ee.printStackTrace();
                    }






                    if (DEBUG) System.out.println("422: Exit code for command "+getID()+": " + exitVal);
                    //DEBUG = false;

                    return exitVal;




                } catch (Exception e)
                {
                    e.printStackTrace();
                }


                try
                {
                    if (child != null)
                    {

                        closeStreams(child); 
                        child.destroy(); 
                    }
                } catch (Exception ee)
                {
                    ee.printStackTrace();
                }

                if (DEBUG) System.out.println("411: Exit code for command "+getID()+": " + exitVal);
                //DEBUG = false;

                return exitVal;
            }

        }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -10;
        }

    }

    public int exec(String comArgs, String[] env, File dir)
    {
        return exec(comArgs, null,env,dir);
    }

    public int exec(String comArgs[], String[] env, File dir)
    {
        return exec(null, comArgs, env,dir);

    }

    public int exec(String comArgs, String comArgsArray[], String[] env, File dir)
    {
        try
        {
        

        if (comArgs != null && comArgs.startsWith("java"))
        {
            return execJava(comArgs, comArgsArray, env, dir, 0);


        } 
        else if (comArgsArray != null && comArgsArray.length >= 1 && comArgsArray[0].startsWith("java"))
        {
            String tComArgsArray[] = new String[comArgsArray.length - 1];
            for (int i=0;i<comArgsArray.length-1;i++)
            {
                tComArgsArray[i] = comArgsArray[i+1];

            }
            return execJava(comArgsArray[0], tComArgsArray, env, dir, 0);
        }
        else
        {
            //DEBUG = true;
            StreamGobbler errorGobbler = null;
            StreamGobbler outputGobbler = null;
            streamBufferErr =  new LinkedList();
            streamBufferOut =  new LinkedList();
            Process child = null;
            child = null;
            int exitVal = -557;
            try
            {
                if (DEBUG)
                {
                    if (comArgs != null)
                    {
                        System.out.print("Executing command " + nextID() +": " + comArgs + " ");
                    } else if (comArgsArray != null)
                    {
                        System.out.print("Executing command " + nextID() +": ");
                        for (int i=0;i<comArgsArray.length;i++)
                        {

                            System.out.print(comArgsArray[i] + " ");
                        }
                    } else
                    {
                        System.out.println("both comArgs and comArgsArray are null, nothing to execute...");
                        //both commands are nnull
                        //return exitVal;
                    }



                    if (env != null)
                    {
                        for (int i=0;i<env.length;i++)
                        {
                            System.out.print(env[i] + " ");
                        }
                    }

                    if (dir != null)
                    {

                        System.out.print("[" + dir.getCanonicalPath() + "]");
                    }

                    System.out.println("");



                }

                if (comArgs != null)
                {
                    if (DEBUG) System.out.println("***: Runtime.getRuntime().exec(" + comArgs+")");
                    child = Runtime.getRuntime().exec(comArgs, env, dir);
                } else if (comArgsArray != null)
                {
                    if (DEBUG) System.out.println("***: Runtime.getRuntime().exec(" + comArgsArray.length+")");
                    child = Runtime.getRuntime().exec(comArgsArray, env, dir);
                } else
                {
                    //both commands are null, nothing to execute
                    if (DEBUG) System.out.println("582: Exit code for command "+getID()+": " + exitVal);
                    //DEBUG = false;
                    return exitVal;
                }
                if (DEBUG) System.out.println("***: Runtime.getRuntime().exec() asynchronous call finished...");

                // any error message?
                errorGobbler = new StreamGobbler(child.getErrorStream(), "ERR", DEBUG, this);            

                // any output?
                outputGobbler = new StreamGobbler(child.getInputStream(), "OUT", DEBUG, this);

                // kick them off
                errorGobbler.start();
                outputGobbler.start();

                // any error???

                if (DEBUG) System.out.println("***: Waiting for child to finish...");
                exitVal = child.waitFor();
                if (DEBUG) System.out.println("509: Exit code for command "+getID()+": " + exitVal);
                //shuttingDown = true;
                try
                {
                    errorGobbler.join();
                } catch (Exception ee)
                {
                    ee.printStackTrace();
                }

                try
                {
                    outputGobbler.join();
                } catch (Exception ee)
                {
                    ee.printStackTrace();
                }



                streamBufferErr = errorGobbler.getStreamBufferList();
                streamBufferOut = outputGobbler.getStreamBufferList();


                try
                {
                    if (child != null)
                    {

                        closeStreams(child); 
                        child.destroy(); 
                    }
                } catch (Exception ee)
                {
                    ee.printStackTrace();
                }




                if (DEBUG) System.out.println("581: Exit code for command "+getID()+": " + exitVal);
                //DEBUG = false;

                return exitVal;




            } catch (Exception e)
            {
                e.printStackTrace();
            }


            try
            {
                if (child != null)
                {

                    closeStreams(child); 
                    child.destroy(); 
                }
            } catch (Exception ee)
            {
                ee.printStackTrace();
            }

            if (DEBUG) System.out.println("580: Exit code for command "+getID()+": " + exitVal);
            //DEBUG = false;
            return exitVal;
        }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -11;
        }

    }


}

class StreamGobbler extends Thread
{
    InputStream is;
    String type;
    OutputStream os;
    private List streamBuffer;
    boolean DEBUG;
    MyProcess myP;

    StreamGobbler(InputStream is, String type, boolean DEBUG, MyProcess myP)
    {
        this(is, type, null, DEBUG, myP);
    }
    StreamGobbler(InputStream is, String type, OutputStream redirect, boolean DEBUG, MyProcess myP)
    {
        this.is = is;
        this.type = type;
        this.os = redirect;
        this.DEBUG = DEBUG;
        this.myP = myP;
        streamBuffer = new LinkedList();
    }

    public List getStreamBufferList()
    {
        return streamBuffer;
    }




    public void run()
    {
        try
        {
            PrintWriter pw = null;
            if (os != null)
                pw = new PrintWriter(os);

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null && !myP.shuttingDown)
            {
                if (pw != null)
                    pw.println(line);
                if (DEBUG) System.out.println(type + "> " + line);
                if (myP.saveOutput)
                {
                    streamBuffer.add(line);
                }

            }
            if (pw != null)
                pw.flush();
        } catch (IOException ioe)
        {
            ioe.printStackTrace();  
        }
    }
}

