import java.io.*;
//import java.util.*;


public class CompareRuns
{
    public CompareRuns()
    {

    }

    public int readFile(String fileName, long execTimeRun[])
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
        }
        */

        while ((str = in.readLine()) != null)
        {

            String tokens[] = str.split(" ");
            if (tokens.length == 12)
            {
                String taskID[] = tokens[1].split(":");
                if (taskID.length == 3)
                {
                    String taskNumber[] = taskID[2].split("_");
                    if (taskNumber.length == 2)
                    {
                        int taskNum = Integer.parseInt(taskNumber[0]);
                        long execTime = Long.parseLong(tokens[8]);
                        int exitCode = Integer.parseInt(tokens[11]);

                        if (taskNum < execTimeRun.length && taskNum >= 0 && exitCode == 0)
                        {
                            execTimeRun[taskNum] = execTime;
                            numSamples++;
                        }
                        else
                        {
                            if (exitCode != 0)
                            {

                            }
                            else
                            {
                            
                                System.out.println("index out of bounds, should not happen... " + taskNum + " which is not between 0 and " + execTimeRun.length + ", ignoring...");
                            }
                        }

                    }
                }

            }
        }
        in.close();

        return numSamples;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;

        }
    }


    public static void main(String args[])
    {
        CompareRuns cr = new CompareRuns();
        cr.main_run(args);

    }

    public void main_run(String args[])
    {
        if (args.length != 3)
        {
            System.out.println("invalid number of args: usage: java CompareRuns <inputFile1> <inputFile2> <maxNumTasks>");

            System.exit(-1);
        }
        String fileName1 = args[0];
        String fileName2 = args[1];



        int maxNumTasks = Integer.parseInt(args[2]);



        if (maxNumTasks <= 0)
        {
            System.out.println("invalid number of tasks, must be a value greater than 0");

            System.exit(-1);
        }



        try
        {
            long execTimeRun1[] = new long[maxNumTasks];
            long execTimeRun2[] = new long[maxNumTasks];

            for (int i=0;i<execTimeRun1.length;i++)
            {
                execTimeRun1[i] = -1;

            }

            for (int i=0;i<execTimeRun2.length;i++)
            {
                execTimeRun2[i] = -1;

            }

            int numSample1 = readFile(fileName1,execTimeRun1); 
            System.out.println("Read " + numSample1 + " samples from file " + fileName1 + "...");
            int numSample2 = readFile(fileName2,execTimeRun2); 
            System.out.println("Read " + numSample2 + " samples from file " + fileName2 + "...");

            System.out.println("Comparing the two runs...");


            StatCalc scDif = new StatCalc();
            StatCalc sc1 = new StatCalc();
            StatCalc sc2 = new StatCalc();
            StatCalc sc1all = new StatCalc();
            StatCalc sc2all = new StatCalc();

            for (int i=0;i<execTimeRun1.length;i++)
            {
                if (execTimeRun1[i] >= 0 && execTimeRun2[i] >= 0)
                {
                    scDif.enter(execTimeRun2[i] - execTimeRun1[i]);
                    sc1.enter(execTimeRun1[i]);
                    sc2.enter(execTimeRun2[i]);


                }

                if (execTimeRun1[i] >= 0)
                    sc1all.enter(execTimeRun1[i]);
                if (execTimeRun2[i] >= 0)
                    sc2all.enter(execTimeRun2[i]);

            }


            System.out.println("Found " + scDif.getCount() + " tasks in common...");
            System.out.println("Run1: Average Task Execution Time: " + sc1.getMean() + " +/- " + sc1.getStandardDeviation());
            System.out.println("Run2: Average Task Execution Time: " + sc2.getMean() + " +/- " + sc2.getStandardDeviation());
            System.out.println("Run1 vs Run2: Average Task Execution Time Difference: " + scDif.getMean() + " +/- " + scDif.getStandardDeviation());
            System.out.println("Run1 vs Run2: Average Task Execution Time Difference %: " + scDif.getMean()/sc1.getMean() + " +/- " + scDif.getStandardDeviation()/sc1.getStandardDeviation());

            System.out.println("Run1all: Average Task Execution Time: " + sc1all.getMean() + " +/- " + sc1all.getStandardDeviation());
            System.out.println("Run2all: Average Task Execution Time: " + sc2all.getMean() + " +/- " + sc2all.getStandardDeviation());


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


