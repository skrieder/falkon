package org.globus.GenericPortal.services.core.WS.impl;

import org.globus.GenericPortal.common.*;
import java.io.*;
import java.util.*;

public class PerformanceProfile
{
    public static boolean PROFILE;
    public static Map perfProfileMap = Collections.synchronizedMap(new TreeMap());
    

    public static String loggerPerfFileName;
    public static boolean captureCPU = false;


    public PerformanceProfile()
    {
        try
        {

            String temp = (String)GPResourceHome.falkonConfig.get("performanceProfile");
            if (temp != null)
            {
                if (temp.contentEquals(new StringBuffer("true")))
                {
                    this.PROFILE = true;
                } else
                    this.PROFILE = false;

                Properties props = System.getProperties();
                String falkonServiceHome = (String)props.get("FALKON_LOGS");

                loggerPerfFileName = (String)GPResourceHome.falkonConfig.get("performanceProfileOutputFile");
                if (loggerPerfFileName != null)
                {
                    loggerPerfFileName = new String(falkonServiceHome + "/" + loggerPerfFileName);
                }

                String temp2 = (String)GPResourceHome.falkonConfig.get("performanceProfileOutputInterval");
                if (temp2 != null)
                {
                    long performanceProfileOutputInterval = Long.parseLong(temp2);

                    if (performanceProfileOutputInterval > 0)
                    {
                    
                    OutputThread ot = new OutputThread(this, performanceProfileOutputInterval);
                    ot.start();
                    }
                }


            } else
                this.PROFILE = false;
        } catch (Exception e)
        {
            e.printStackTrace();
            this.PROFILE = false;

        }



    }


    public void addSample(String key, long value)
    {
        StatCalc stat = (StatCalc)perfProfileMap.get(key);
        if (stat == null)
        {
            stat = new StatCalc();
        }
        stat.enter(value);
        perfProfileMap.put(key, stat);
    }

    public void addSample(String key, double value)
    {
        StatCalc stat = (StatCalc)perfProfileMap.get(key);
        if (stat == null)
        {
            stat = new StatCalc();
        }
        stat.enter(value);
        perfProfileMap.put(key, stat);
    }

    public void addSampleReset(String key, int value)
    {
        StatCalc stat = (StatCalc)perfProfileMap.get(key);
        if (stat == null)
        {
            stat = new StatCalc();
        }
        else
        {
        
            stat.reset();
        }
        stat.enter(value*1.0);
        perfProfileMap.put(key, stat);
    }

    public void reset()
    {
        perfProfileMap.clear();
    }



    public String toString()
    {
        String retValue = new String("");
        retValue = retValue.concat("metric sample_counts sample_sum sample_mean sample_stdev\n");

        synchronized (perfProfileMap)
        {
        
        // For both the keys and values of a map
        for (Iterator it=perfProfileMap.entrySet().iterator(); it.hasNext(); )
        {
            Map.Entry entry = (Map.Entry)it.next();
            String key = (String)entry.getKey();
            StatCalc value = (StatCalc)entry.getValue();
            retValue = retValue.concat(key + " " + value.getCount() + " " + value.getSum() + " " + value.getMean() + " " + value.getStandardDeviation() + "\n");
            


        }
        }

        return retValue;
    }

    public BufferedWriter perfProfileOut = null;

    public void writePerfProfile()
    {
        if (PROFILE)
        {
            //BufferedWriter out = null;

            try
            {
                if (perfProfileOut == null)
                {
                    perfProfileOut = new BufferedWriter(new FileWriter(loggerPerfFileName));
                }
                
            } catch (Exception e)
            {
                e.printStackTrace();
                PROFILE = false;
            }



                try
                {

                    if (!perfProfileMap.isEmpty())
                    {

                    perfProfileOut.write(this.toString()+"\n");
                    perfProfileOut.flush();
                    }
                    //Thread.sleep(period);
                } catch (Exception e)
                {
                    e.printStackTrace();
                    PROFILE = false;
                }
        }



    }


}

// This class extends Thread
class OutputThread extends Thread
{

    PerformanceProfile pp;
    long period; 

    OutputThread(PerformanceProfile pp, long period)
    {
        this.pp = pp;
        this.period = period;

    }
    // This method is called when the thread runs
    public void run() {

        if (pp.PROFILE)
        {
            while (pp.PROFILE)
            {
                try
                {

                    pp.writePerfProfile();

                    Thread.sleep(period);
                } catch (Exception e)
                {
                    e.printStackTrace();
                    pp.PROFILE = false;
                }
            }
        }



    }
}
