import java.io.*;
//import java.util.*;


public class ConvertPerTaskToSummary
{
    public ConvertPerTaskToSummary()
    {

    }


    public static void main(String args[])
    {
        if (args.length != 2)
        {
            System.out.println("invalid number of args: usage: java ConvertPerTaskToSummary <inputFile> <timeQuanta>");

            System.exit(-1);
        }
        String fileName = args[0];
        int quanta = Integer.parseInt(args[1]);


        long MIN = 0;
        long MAX = 0;

        int numSamples = 0;

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
                numSamples++;

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

                    temp = Long.parseLong(tokens[3]);

                    if (temp < MIN)
                    {
                        MIN = temp;
                    }
                    numSamples++;

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
        //double duration = (MAX - MIN)*1.0/1000.0;
        double duration = (MAX)*1.0/1000.0;
        System.out.println("Duration = " + duration + " seconds");

        int sCalcSize = (int)Math.ceil(duration/quanta);

        double heapSize = (double)sCalcSize*(double)numSamples/(1024.0*1024.0*1024.0);

        System.out.println("creating summary array of "+ sCalcSize + "x" + numSamples + " size, should require "+heapSize + " GB of heap");
        boolean sumArray[][] = new boolean[sCalcSize][numSamples];

        for (int i=0;i<sCalcSize;i++)
        {
            for (int j=0;j<numSamples;j++)
            {
                sumArray[i][j] = false;
            }
        }



        //StatCalc sCalc[] = new StatCalc[sCalcSize];

        //for (int i=0;i<sCalc.length;i++)
        //{
        //    sCalc[i] = new StatCalc();
        //    sCalc[i].reset();
        //}



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

            int taskCount = 0;

            while ((str = in.readLine()) != null)
            {
                String tokens[] = str.split(" ");
                if (tokens.length == 12)
                {
                    //long startTime = Long.parseLong(tokens[3])-MIN;
                    //long endTime = Long.parseLong(tokens[6])-MIN;
                    //this should be fixed, to offset MIN
                    long startTime = Long.parseLong(tokens[3]);
                    long endTime = Long.parseLong(tokens[6]);
                    int sTime = (int)Math.floor((startTime*1.0/1000.0)/quanta);
                    int eTime = (int)Math.floor((endTime*1.0/1000.0)/quanta);
                    //System.out.println("sTime: " + sTime);
                    //System.out.println("eTime: " + eTime);
                    //System.err.println("starting at time " + sTime + " and ending at time " + eTime);

                    for (int i = sTime; i<eTime; i++)
                    {
                        if (taskCount < numSamples)
                        {
                            sumArray[i][taskCount]=true;
                        }
                        else
                        {
                            //System.out.println("read more samples than expected: " + taskCount + " > " + numSamples + " this should not have happened...");
                        }
                        

                    }
                    taskCount++;

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

        //summarize

        double summary[][] = new double[sCalcSize][4];
        //0: active tasks
        //1: numCPUs
        //2: completed tasks
        //3: throughput tasks/quanta
        int maxTotTasks = 0;
        //0
        for (int i=0;i<sCalcSize;i++)
        {
            int totTasks = 0;
            for (int j=0;j<numSamples;j++)
            {
                if (sumArray[i][j])
                {
                    totTasks++;

                }
                //totTasks += sumArray[i][j];
            }

            if (totTasks > maxTotTasks)
            {
                maxTotTasks = totTasks;
            }

            summary[i][0] = totTasks;
        }

        //1
        for (int i=0;i<sCalcSize;i++)
        {
            summary[i][1] = maxTotTasks;
        }

        //3
        if (sCalcSize > 1)
        {

            for (int i=1;i<sCalcSize;i++)
            {
                int numDoneTasks = 0;

                for (int j=0;j<numSamples;j++)
                {
                    if (sumArray[i-1][j] && sumArray[i][j] == false)
                    {
                        numDoneTasks++;
                    }
                    //numDoneTasks += Math.max(sumArray[i-1][j] - sumArray[i][j], 0);
                }


                summary[i][3] = numDoneTasks;
            }
        }
        else
        {
            summary[0][3] = summary[0][0];

        }

        //2
        int numDoneTasksTotal = 0;
        for (int i=0;i<sCalcSize;i++)
        {

            numDoneTasksTotal += summary[i][3];
            summary[i][2] = numDoneTasksTotal;
        }



        System.out.println("//Time_ms num_users num_resources num_threads num_all_workers num_free_workers num_pend_workers num_busy_workers waitQ_length waitNotQ_length activeQ_length doneQ_length delivered_tasks throughput_tasks/sec success_tasks failed_tasks retried_tasks resourceAllocated submittedTasks cacheSize cacheLocalHits cacheGlobalHits cacheMisses cacheLocalHitRatio cacheGlobalHitRatio systemCPUuser systemCPUsystem systemCPUidle systemHeapSize systemHeapFree systemHeapMax");
        System.out.println("//0-time is " + MIN + "ms");

        //0.0 1 1 22 256 256 0 0 0 0 4 0 6 0.0 0 0 0 0 0 0 0 0 0 0.0 0.0 5 1 94 1472 1100 1472


        //System.out.println("time_sec average_exec_time_sec");
        for (int i=0;i<sCalcSize;i++)
        {

            System.out.println(i*quanta + " 0 0 0 " + summary[i][1] + " " + (summary[i][1]-summary[i][0]) + " 0 " + summary[i][0] + " 0 0 0 0 " + summary[i][2] + " " + summary[i][3]*1.0/quanta + " " + summary[i][2] + " 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
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
