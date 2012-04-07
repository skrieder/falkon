import java.io.*;
//import java.util.*;


public class NormalizeTaskPerf
{
    public NormalizeTaskPerf()
    {

    }

    public long findMinTime(String fileName)
    {
        try
        {


        int numSamples = 0;
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String str;

        //taskNum taskID workerID startTimeStamp submitTimeStamp resultsQueueTimeStamp endTimeStamp submitTime queueExecTime resultsTime totalTime exitCode
        //1213103557491 172.17.5.144:50147:218_390370352 QUEUE_50147 1213103462943 1213103463358 1213103557468 1213103557468 415 94110 0 94525 0

        str = in.readLine();

        if (str == null)
        {
            System.out.println("error in reading file");
            System.exit(1);
        }


        /*
        str = in.readLine();

        if (str == null)
        {
            System.out.println("error in reading file, file is empty");
            System.exit(1);
        } */

        //taskNum taskID workerID startTimeStamp submitTimeStamp resultsQueueTimeStamp endTimeStamp submitTime queueExecTime resultsTime totalTime exitCode
        //1213103557491 172.17.5.144:50147:218_390370352 QUEUE_50147 1213103462943 1213103463358 1213103557468 1213103557468 415 94110 0 94525 0

        long curMin = -1;


        while ((str = in.readLine()) != null)
        {




            String tokens[] = str.split(" ");
            if (tokens.length == 12)
            {
                if (curMin == -1)
                {
                    curMin = Long.parseLong(tokens[3]);

                }

                if (Long.parseLong(tokens[3]) < curMin)
                {
                    curMin = Long.parseLong(tokens[3]);
                }

                if (Long.parseLong(tokens[4]) < curMin)
                {
                    curMin = Long.parseLong(tokens[4]);
                }

                if (Long.parseLong(tokens[5]) < curMin)
                {
                    curMin = Long.parseLong(tokens[5]);
                }

                if (Long.parseLong(tokens[6]) < curMin)
                {
                    curMin = Long.parseLong(tokens[6]);
                }


            }
        }
        in.close();

        return curMin;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -2;

        }
    }

    public int normalizeFile(String fileName, long startTime)
    {
        try
        {
        

        int numSamples = 0;
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String str;

        //taskNum taskID workerID startTimeStamp submitTimeStamp resultsQueueTimeStamp endTimeStamp submitTime queueExecTime resultsTime totalTime exitCode
        //1213103557491 172.17.5.144:50147:218_390370352 QUEUE_50147 1213103462943 1213103463358 1213103557468 1213103557468 415 94110 0 94525 0

        str = in.readLine();

        if (str == null)
        {
            System.out.println("error in reading file");
            System.exit(1);
        }


        /*
        str = in.readLine();

        if (str == null)
        {
            System.out.println("error in reading file, file is empty");
            System.exit(1);
        } */


        int index = 0;

        while ((str = in.readLine()) != null)
        {

            String tokens[] = str.split(" ");
            if (tokens.length == 12)
            {
                //System.out.println(index + " " + tokens[1] + " " + tokens[2] + " " + (Long.parseLong(tokens[3])-startTime)/1000.0 + " " + (Long.parseLong(tokens[4])-startTime)/1000.0 + " " + (Long.parseLong(tokens[5])-startTime)/1000.0 + " " + (Long.parseLong(tokens[6])-startTime)/1000.0 + " " + Long.parseLong(tokens[7])/1000.0 + " " + Long.parseLong(tokens[8])/1000.0 + " " + Long.parseLong(tokens[9])/1000.0 + " " + Long.parseLong(tokens[10])/1000.0 + " " + tokens[11]);
                System.out.println(index + " " + tokens[1] + " " + tokens[2] + " " + (Long.parseLong(tokens[3])-startTime) + " " + (Long.parseLong(tokens[4])-startTime) + " " + (Long.parseLong(tokens[5])-startTime) + " " + (Long.parseLong(tokens[6])-startTime) + " " + Long.parseLong(tokens[7]) + " " + Long.parseLong(tokens[8]) + " " + Long.parseLong(tokens[9]) + " " + Long.parseLong(tokens[10]) + " " + tokens[11]);

                index++;
            }
        }
        in.close();

        return index;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;

        }
    }


    public static void main(String args[])
    {
        NormalizeTaskPerf cr = new NormalizeTaskPerf();
        cr.main_run(args);

    }

    public void main_run(String args[])
    {
        if (args.length != 1)
        {
            System.out.println("invalid number of args: usage: java NormalizeTaskPerf <inputFile1>");

            System.exit(-1);
        }
        String fileName1 = args[0];




        try
        {
            long startTime = findMinTime(fileName1); 
            int numSample1 = normalizeFile(fileName1,startTime); 
            System.out.println("Normalized " + numSample1 + " samples from file " + fileName1 + "...");



        }

        catch (Exception e)
        {
            e.printStackTrace();
        }







    }

}


/* 
   An object of class StatCalc can be used to compute several simple statistics
   for a set of numbers.  Numbers are entered into the dataset using
   the enter(double) method.  Methods are provided to return the following
   statistics for the set of numbers that have been entered: The number
   of items, the sum of the items, the average, and the standard deviation.
*/

class StatCalc
{

    private int count;   // Number of numbers that have been entered.
    private double sum;  // The sum of all the items that have been entered.
    private double squareSum;  // The sum of the squares of all the items.

    public StatCalc()
    {

    }

    public synchronized void reset() 
    {
        count = 0;
        sum = 0;
        squareSum = 0;

    }

    public synchronized void enter(double num) {
        // Add the number to the dataset.
        count++;
        sum += num;
        squareSum += num*num;
    }

    public synchronized void enter(long lnum) {
        // Add the number to the dataset.

        double num = (double)lnum;
        count++;
        sum += num;
        squareSum += num*num;
    }


    public int getCount() {   
        // Return number of items that have been entered.
        return count;
    }

    public double getSum() {
        // Return the sum of all the items that have been entered.
        return sum;
    }

    public double getMean() {
        // Return average of all the items that have been entered.
        // Value is Double.NaN if count == 0.
        if (count == 0)
        {
            return 0;
        }
        else
            return sum / count;  
    }

    public double getStandardDeviation() {  
        // Return standard deviation of all the items that have been entered.
        // Value will be Double.NaN if count == 0.
        double mean = getMean();
        return Math.sqrt( squareSum/count - mean*mean );
    }

}  // end of class StatCalc


