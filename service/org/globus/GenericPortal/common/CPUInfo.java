package org.globus.GenericPortal.common;

import java.lang.*;
import java.io.*;
import java.util.*;

public class CPUInfo
{
    public int user;
    public int system;
    public int idle;
    public int sample; //sec

    public Process vmstat;
    public InputStream in;

    public static boolean DEBUG = false;

    private GetSample thread = null;


    public CPUInfo()
    {
        this.sample = 1;
        this.user = 0;
        this.system = 0;
        this.idle = 100;

        try
        {


            if (DEBUG) System.out.println("CPUInfo executing vmstat...");
        this.vmstat = Runtime.getRuntime().exec("vmstat -n " + this.sample);
                    // Get the input stream and read from it
        in = vmstat.getInputStream();
        if (DEBUG) System.out.println("CPUInfo getting input stream...");


        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.vmstat = null;

        }

    }

    public CPUInfo(int sample)
    {
        this.sample = sample;
        this.user = 0;
        this.system = 0;
        this.idle = 100;


        try
        {

            if (DEBUG) System.out.println("CPUInfo executing vmstat...");

        this.vmstat = Runtime.getRuntime().exec("vmstat -n " + this.sample);
        if (DEBUG) System.out.println("CPUInfo getting input stream...");
        in = vmstat.getInputStream();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.vmstat = null;

        }
    }



   public boolean startUp()
   {
       try
       {
           if (DEBUG) System.out.println("CPUInfo creating thread...");
       
       thread = new GetSample(this);
       if (DEBUG) System.out.println("CPUInfo starting thread...");
       thread.start();
       //if (DEBUG) System.out.println("CPUInfo waiting for thread to complete...");
       //thread.join();
       //if (DEBUG) System.out.println("CPUInfo thread completed!");
       return true;
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
       if (DEBUG) System.out.println("CPUInfo thread failed!");
       return false;
       

   }

   public void monitor(long interval)
   {
       for (int i=0;i<30;i++)
       {
           System.out.println("User% " + user + " System% " + system + " Idle% " + idle);
           try
           {

           Thread.sleep(interval);
           }
           catch (Exception e)
           {
               e.printStackTrace();
           }
       }
       thread.stop();
   }


    public static void main(String args[])
    {
        if (DEBUG) System.out.println("CPUInfo v0.1");
        if (DEBUG) System.out.println("CPUInfo initializing...");
        CPUInfo cInfo = new CPUInfo(1);
        if (DEBUG) System.out.println("CPUInfo starting up...");
        cInfo.startUp();
        cInfo.monitor(1000);

                


        


    
    }

}

class GetSample extends Thread {
        CPUInfo cpu;


        private boolean vmstatExited = false;

        public boolean DEBUG = false;
    
        GetSample(CPUInfo cpu) {
            this.cpu = cpu;
            this.DEBUG = cpu.DEBUG;
        }

        private String getSample()
        {

            try {
            int c;
            //char array[] = new char[1];
            int index = 0;
            //String sSample = new String("");
            List sSample = new ArrayList();

            while ((c = cpu.in.read()) != -1) 
            {

                //if (DEBUG) System.out.println("CPUInfo captured: " + (char)c);
                //array[0] = (char)c;

                if ((char)c == '\n')
                {
                    break;
                }

                sSample.add(""+ (char)c);
                //sSample.concat(new String(array));
            }

            if (c == -1)
            {
                vmstatExited = true;
            }

            //Object[] objectArray = sSample.toArray();
            
            char[] array = new char[sSample.size()];

            String ch = new String();
            int i=0;
            for (Iterator it=sSample.iterator(); it.hasNext(); ) {
                ch = (String)it.next();
                array[i] = ch.charAt(0);
                it.remove();
                i++;
            }

            return new String(array);

            } catch (IOException e) 
            {
                e.printStackTrace();

            }
            return null;
        }



        public void run() {
            try {
                while (!vmstatExited) 
                {
                    String sSample = getSample();
                    if (DEBUG) System.out.println("Captured: " + sSample);
                    sSample.trim();
                    if (sSample.indexOf("cpu") <= 0 && sSample.indexOf("id") <= 0)
                    {
                    
                    String tokens[] = sSample.split(" ");
                    if (DEBUG) System.out.println("Sample has " + tokens.length + " tokens...");
                    for (int i=0;i<tokens.length;i++)
                    {
                        if (DEBUG) System.out.println("Token["+i+"] = " + tokens[i]);
                    }
                    if (DEBUG) System.out.println("");

                    int numValues = 16;
                    int vmstatValues[] = new int[numValues]; //the 16 might be platform specific
                    int j=0;
                    for (int i=0;i<tokens.length;i++ )
                    {
                        if (!tokens[i].contentEquals(new StringBuffer("")))
                        {
                            vmstatValues[j] = Integer.parseInt(tokens[i]);
                            j++;
                        }

                    }

                    for (int k=j;k<numValues;k++)
                    {
                        vmstatValues[k] = 0;

                    }

                    if (DEBUG) System.out.println("VMStat values: ");
                    for (int k=0;k<numValues;k++)
                    {
                        if (DEBUG) System.out.print(vmstatValues[k] + " ");

                    }
                    if (DEBUG) System.out.println("");

                    cpu.idle = vmstatValues[14];
                    cpu.system = vmstatValues[13];
                    cpu.user = vmstatValues[12];
                    

                    }



                }
            } catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
