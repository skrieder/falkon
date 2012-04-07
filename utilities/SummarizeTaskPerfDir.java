import java.io.*;
//import java.util.*;


public class SummarizeTaskPerfDir
{
    public boolean DEBUG = true;

    public SummarizeTaskPerfDir()
    {

    }






    public  double MIN = 0;
    public  double MAX = 0;
    int fileProcessMaxMinIndex = 0;


    int fileProcessIndex = 0;

    //taskNum taskID workerID startTimeStamp execTimeStamp resultsQueueTimeStamp endTimeStamp waitQueueTime execTime resultsQueueTime totalTime exitCode
    //0-time is 1211225035182ms
    //1 viper.ci.uchicago.edu:50101:1_361427174 127.0.0.1:43794:0 17678 17693 18694 18699 15 1001 5 1021 0

    // Process only files under dir
    public  void visitAllFilesMerge(File dir) throws Exception {
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++)
            {
                visitAllFilesMerge(new File(dir, children[i]));
            }
        }
        else
        {
            //if ((dir.getName()).contains("falkon_task_perf.txt"))
            if ((dir.getName()).endsWith("falkon_task_perf.txt"))
            {

                //System.out.println("processing " + dir.getCanonicalFile() + "...");
                try
                {


                    if (DEBUG) System.err.println("Processing file " + fileProcessIndex + "...");
                    fileProcessIndex++;
                    processSummarize(dir);
                }
                catch (Exception e)
                {
                    if (DEBUG) System.err.println("Error: " + e.getMessage());
                }
            }
        }
    }

    public  String header = null;

    int successTasks = 0;
    int allTasks = 0;
    int failedTasks = 0;


    //taskNum taskID workerID startTimeStamp execTimeStamp resultsQueueTimeStamp endTimeStamp waitQueueTime execTime resultsQueueTime totalTime exitCode
    //0-time is 1211225035182ms
    //1 viper.ci.uchicago.edu:50101:1_361427174 127.0.0.1:43794:0 17678 17693 18694 18699 15 1001 5 1021 0

    public  void processSummarize(File dir) throws Exception
    {

        if (DEBUG) System.err.println("processSummarize " + dir.getCanonicalFile() + "...");

        try
        {
            //System.out.println("processSummarize => " + dir.getCanonicalFile());
            //double globalTime = 0;//processMin(dir);

            //System.out.println("globalTime = " + globalTime);

            BufferedReader in = new BufferedReader(new FileReader(dir));
            //System.out.println("opened file...");

            String str;



            str = in.readLine();

            if (str == null)
            {
                //System.out.println("error in reading file, file is empty");

                throw new Exception("processSummarize(): error in reading file 5");
                //System.exit(1);
            }
            if (header == null)
            {
                header = new String(str);
                //  System.out.println("header = " + header);
            }

            str = in.readLine();

            if (str == null)
            {
                throw new Exception("processSummarize(): error in reading file 6");
                //System.out.println("error in reading file, file is empty");
                //System.exit(1);
            }

            //double timeStamp = -1;

            while ((str = in.readLine()) != null)
            {
                String tokens[] = str.split(" ");
                if (tokens.length == 12)
                {
                    //double timeStamp = Double.parseDouble(tokens[0]);
                    //timeStamp += 1;
                    //int index = (int)(Math.floor(globalTime - MIN + timeStamp)/timeQuanta);
                    //System.out.println("globalTime = " + globalTime);
                    //System.out.println("MIN = " + MIN);
                    //System.out.println("timeStamp = " + timeStamp);
                    //System.out.println("index = " + index);
                    //  System.out.println("entered " + index);
                    for (int i=7;i<11;i++)
                    {
                        sCalc[0][i-7].enter(Integer.parseInt(tokens[i])*1.0);
                        //         System.out.println("entered " + Double.parseDouble(tokens[i]));
                    }

                    if (Integer.parseInt(tokens[11]) == 0)
                    {
                        successTasks++;

                    }
                    else
                    {
                        failedTasks++;

                    }
                    allTasks++;

                }
                else
                {
                    if (DEBUG) System.out.println("erorr in number of fields... "+ tokens.length + " != " + numFields);

                }
            }
            //    System.out.println("closing file...");

            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }




    }



    public  StatCalc sCalc[][] = null;


    public  int sCalcSize = 0;
    public  int numFields = 0;


    public void main_run(String args[])
    {
        if (args.length != 1)
        {
            System.err.println("invalid number of args: usage: java SummarizeTaskPerfDir <inputDir>");
            System.err.println("usage: java SummarizeTaskPerfDir ./");
            System.exit(-1);
        }

        String dirName = args[0];

        sCalcSize = 1;


        numFields = 4;

        if (DEBUG) System.err.println("Allocating " + sCalcSize + "*"+ numFields+ " elements...");


        sCalc = new StatCalc[sCalcSize][numFields];

        //0.0 0 0 18 43 43 0 0 0 0 0 0 0 0.0 0 0 0 0 0 0 0 0 0 0.0 0.0 0 0 100 512 431 512
        for (int i=0;i<sCalcSize;i++)
        {
            for (int j=0;j<numFields;j++)
            {

                sCalc[i][j] = new StatCalc();
                sCalc[i][j].reset();
            }

        }

        if (DEBUG) System.err.println("Completed allocating all memory!");

        if (DEBUG) System.err.println("Will merge " + fileProcessMaxMinIndex + " files...");


        try
        {

            visitAllFilesMerge(new File(dirName));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.err.println("Processed " + fileProcessIndex + " files...");


        if (DEBUG) System.err.println("Completed merging summaries...");

        if (DEBUG) System.err.println("Will output summary of length " + sCalcSize + "...");


//        System.out.println(header);

        if (DEBUG) System.out.println("Number of samples: " + sCalc[0][0].getCount());
        System.out.println("Number of Tasks: " + allTasks);
        System.out.println("Succesful tasks: " + successTasks);
        System.out.println("Succesful Tasks %: " + (successTasks*1.0/allTasks)*100.0);
        System.out.println("Failed tasks: " + failedTasks);

        System.out.println("Queue Time per Task (sec): " + sCalc[0][0].getMean()/1000.0 + " +/- " + sCalc[0][0].getStandardDeviation()/1000.0);
        System.out.println("Execution Time per Task (sec): " + sCalc[0][1].getMean()/1000.0 + " +/- " + sCalc[0][1].getStandardDeviation()/1000.0);
        System.out.println("Results Time per Task (sec): " + sCalc[0][2].getMean()/1000.0 + " +/- " + sCalc[0][2].getStandardDeviation()/1000.0);
        System.out.println("Total Time per Task (sec): " + sCalc[0][3].getMean()/1000.0 + " +/- " + sCalc[0][3].getStandardDeviation()/1000.0);
            
        System.out.println("Total CPU Time per Workload (CPU Hours): " + ((sCalc[0][1].getMean()/1000.0)*allTasks)/3600.0);


        if (DEBUG) System.err.println("Completed outputing merged summaries!");

    }

    public static void main(String args[])
    {
        SummarizeTaskPerfDir mfs = new SummarizeTaskPerfDir();
        mfs.main_run(args);


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
