import java.io.*;
//import java.util.*;


public class SummarizeTaskPerf
{
    public SummarizeTaskPerf()
    {

    }


    public static void main(String args[])
    {
        if (args.length != 2)
        {
            System.out.println("invalid number of args: usage: java SummarizeTaskPerf <inputFile> <timeQuanta>");

            System.exit(-1);
        }
        String fileName = args[0];
        int quanta = Integer.parseInt(args[1]);


        long MIN = 0;
        long MAX = 0;

        try
        {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String str;

            //taskNum taskID workerID startTimeStamp submitTimeStamp resultsQueueTimeStamp endTimeStamp submitTime queueExecTime resultsTime totalTime exitCode
            //1213103557491 172.17.5.144:50147:218_390370352 QUEUE_50147 1213103462943 1213103463358 1213103557468 1213103557468 415 94110 0 94525 0

            //header
            str = in.readLine();

            if (str == null)
            {
                System.out.println("error in reading file");
                System.exit(1);
            }

            //time
            str = in.readLine();

            if (str == null)
            {
                System.out.println("error in reading file");
                System.exit(1);
            }


            //entries
            str = in.readLine();

            if (str == null)
            {
                System.out.println("error in reading file, file is empty");
                System.exit(1);
            }

            String tokens[] = str.split(" ");
            if (tokens.length == 12)
            {
                MIN = Long.parseLong(tokens[3]);
                MAX = Long.parseLong(tokens[6]);

            }
            else
            {
                System.out.println("error in reading file, parse error");
                System.out.println(str);
                System.exit(2);

            }



            while ((str = in.readLine()) != null)
            {

                tokens = str.split(" ");
                if (tokens.length == 12)
                {
                    long temp = Long.parseLong(tokens[6]);
                    if (temp > MAX)
                    {
                        MAX = temp;
                    }

                    if (temp < MIN)
                    {
                        MIN = temp;
                    }

                }
                else
                {

                }

                //process(str);
            }
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        System.out.println("MIN = " + MIN);
        System.out.println("MAX = " + MAX);
        double duration = (MAX - MIN)*1.0/1000.0;
        System.out.println("Duration = " + duration + " seconds");


        int sCalcSize = (int)Math.ceil(duration/quanta);
        StatCalc sCalc[] = new StatCalc[sCalcSize];

        for (int i=0;i<sCalc.length;i++)
        {
            sCalc[i] = new StatCalc();
            sCalc[i].reset();
        }



        try
        {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String str;


            str = in.readLine();

            if (str == null)
            {
                System.out.println("error in reading file, file is empty");
                System.exit(1);
            }



            while ((str = in.readLine()) != null)
            {
                String tokens[] = str.split(" ");
                if (tokens.length == 12)
                {
                    long temp = Long.parseLong(tokens[6]);
                    int index = (int)Math.floor(((temp - MIN)*1.0/1000.0)/quanta);
                    sCalc[index].enter(Long.parseLong(tokens[8]));

                }
                else
                {

                }
            }
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        System.out.println("time_sec average_exec_time_sec");
        for (int i=0;i<sCalc.length;i++)
        {
            
            System.out.println(i + " " + sCalc[i].getMean()/1000.0);
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
