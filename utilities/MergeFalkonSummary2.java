import java.io.*;
import java.util.regex.*;
//import java.util.*;


public class MergeFalkonSummary2
{
    public MergeFalkonSummary2()
    {

    }

    public CharSequence StringToCharSequence(String s)
    {
        return s.subSequence(0, s.length());

    }


    public String replace(String original, String patternStr, String replacementStr)
    {

        CharSequence inputStr = StringToCharSequence(original);
    
            // Compile regular expression
            Pattern pattern = Pattern.compile(patternStr);
    
            // Replace all occurrences of pattern in input
            Matcher matcher = pattern.matcher(inputStr);
            return matcher.replaceAll(replacementStr);


    }

    public  double processMin(File dir) throws Exception
    {

        System.err.println("processMin " + dir.getCanonicalFile() + "...");
        BufferedReader in = new BufferedReader(new FileReader(dir));
        String str;

        //taskNum taskID workerID startTimeStamp submitTimeStamp resultsQueueTimeStamp endTimeStamp submitTime queueExecTime resultsTime totalTime exitCode
        //1213103557491 172.17.5.144:50147:218_390370352 QUEUE_50147 1213103462943 1213103463358 1213103557468 1213103557468 415 94110 0 94525 0

        str = in.readLine();

        if (str == null)
        {
            //System.out.println("error in reading file");
            throw new Exception("processMin(): error in reading file 1");
            //System.exit(1);
        }

        str = in.readLine();

        if (str == null)
        {
            //System.out.println("error in reading file");
            //System.exit(1);
            throw new Exception("processMin(): error in reading file 2");
        }

        String tokens[] = str.split(" ");
        double timeMax = 0;
        //double timeMax2 = 0;
        if (tokens.length == 3)
        {
            String sLong = replace(tokens[2], "ms", "");
            if (sLong != null)
            {
                //return Long.parseLong(sLong);
                timeMax = (Long.parseLong(sLong))/1000.0;
                //timeMax2 = timeMax;
            }
            else
            {
                throw new Exception("value is null");
                //return -1;
            }
        }
        else
        {

            throw new Exception("invalid number of tokens");
            //return -1;
        }


        in.close();
        return timeMax;




    }



    public  double processMax(File dir) throws Exception
    {
        System.err.println("processMax " + dir.getCanonicalFile() + "...");

        BufferedReader in = new BufferedReader(new FileReader(dir));
        String str;

        //taskNum taskID workerID startTimeStamp submitTimeStamp resultsQueueTimeStamp endTimeStamp submitTime queueExecTime resultsTime totalTime exitCode
        //1213103557491 172.17.5.144:50147:218_390370352 QUEUE_50147 1213103462943 1213103463358 1213103557468 1213103557468 415 94110 0 94525 0

        str = in.readLine();

        if (str == null)
        {
            //System.out.println("error in reading file");
            throw new Exception("processMax(): error in reading file 3");
            //System.exit(1);
        }

        str = in.readLine();

        if (str == null)
        {
            //System.out.println("error in reading file");
            //System.exit(1);

            throw new Exception("processMax(): error in reading file 4");
        }

        String tokens[] = str.split(" ");
        double timeMax = 0;
        double timeMax2 = 0;
        if (tokens.length == 3)
        {
            //String sLong = tokens[2].replace("ms","");
            String sLong = replace(tokens[2], "ms", "");

            if (sLong != null)
            {
                //return Long.parseLong(sLong);
                timeMax = (Long.parseLong(sLong))/1000.0;
                timeMax2 = timeMax;
            }
            else
            {
                throw new Exception("value is null");
                //return -1;
            }
        }
        else
        {

            throw new Exception("invalid number of tokens");
            //return -1;
        }

        while ((str = in.readLine()) != null)
        {
            tokens = str.split(" ");
            if (tokens != null && tokens.length >= 1 && tokens[0].length() > 0)
            {
                try
                {

                    double d = Double.parseDouble(tokens[0]);
                    if (timeMax2 + d > timeMax)
                    {
                        timeMax = timeMax2 + d;
                    }
                }
                catch (Exception e)
                {

                }

            }
        }

        in.close();

        return timeMax;




    }


    public  double MIN = 0;
    public  double MAX = 0;
    int fileProcessMaxMinIndex = 0;

    // Process only files under dir
    public  void visitAllFiles(File dir) throws Exception {
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++)
            {
                visitAllFiles(new File(dir, children[i]));
            }
        }
        else
        {
            //if ((dir.getName()).contains("falkon_summary.txt") || (dir.getName()).contains("GenericPortalWS_perf_per_sec.txt") || (dir.getName()).contains("GenericPortalWS_perf_per_sec.log"))
            if ((dir.getName()).endsWith("falkon_summary.txt") || (dir.getName()).endsWith("GenericPortalWS_perf_per_sec.txt") || (dir.getName()).endsWith("GenericPortalWS_perf_per_sec.log"))
            {

                //System.out.println("processMax " + dir.getCanonicalFile() + "...");

                try
                {

                    System.err.println("ProcessMax/Min file " + fileProcessMaxMinIndex);
                    fileProcessMaxMinIndex++;


                    double temp = processMax(dir);
                    if (temp >= MAX)
                    {
                        MAX = temp;
                        if (MIN == 0)
                        {
                            MIN = MAX;
                        }
                    }

                    //System.out.println("processMin " + dir.getCanonicalFile() + "...");
                    temp = processMin(dir);
                    if (temp < MIN)
                    {
                        MIN = temp;
                    }
                }
                catch (Exception e)
                {
                    System.err.println("Error: " + e.getMessage());
                }

            }
        }
    }

    int fileProcessIndex = 0;

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
            //if ((dir.getName()).contains("falkon_summary.txt") || (dir.getName()).contains("GenericPortalWS_perf_per_sec.txt") || (dir.getName()).contains("GenericPortalWS_perf_per_sec.log"))
            if ((dir.getName()).endsWith("falkon_summary.txt") || (dir.getName()).endsWith("GenericPortalWS_perf_per_sec.txt") || (dir.getName()).endsWith("GenericPortalWS_perf_per_sec.log"))
            {

                //System.out.println("processing " + dir.getCanonicalFile() + "...");
                try
                {


                    System.err.println("Processing file " + fileProcessIndex + " out of " + fileProcessMaxMinIndex + " files: " + fileProcessIndex*100.0/fileProcessMaxMinIndex + "% completed...");
                    fileProcessIndex++;
                    processMerge(dir);
                }
                catch (Exception e)
                {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
    }

    public  String header = null;

    public  void processMerge(File dir) throws Exception
    {

        System.err.println("processMerge " + dir.getCanonicalFile() + "...");

        try
        {
            //System.out.println("processMerge => " + dir.getCanonicalFile());
            double globalTime = processMin(dir);

            //System.out.println("globalTime = " + globalTime);

            BufferedReader in = new BufferedReader(new FileReader(dir));
            //System.out.println("opened file...");

            String str;



            str = in.readLine();

            if (str == null)
            {
                //System.out.println("error in reading file, file is empty");

                throw new Exception("processMerge(): error in reading file 5");
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
                throw new Exception("processMerge(): error in reading file 6");
                //System.out.println("error in reading file, file is empty");
                //System.exit(1);
            }

            double timeStamp = -1;

            double oldSuccessTasks = 0;
            double oldFailedTasks = 0;

            while ((str = in.readLine()) != null)
            {
                String tokens[] = str.split(" ");
                if (tokens.length == numFields)
                {
                    //double timeStamp = Double.parseDouble(tokens[0]);
                    timeStamp += 1;
                    int index = (int)(Math.floor(globalTime - MIN + timeStamp)/timeQuanta);
                    //System.out.println("globalTime = " + globalTime);
                    //System.out.println("MIN = " + MIN);
                    //System.out.println("timeStamp = " + timeStamp);
                    //System.out.println("index = " + index);
                    //  System.out.println("entered " + index);
                    if (sCalc[index][0] == null)
                    {
                        sCalc[index][0] = new StatCalc();
                    }
                    sCalc[index][0].enter(index*timeQuanta*1.0);
                    for (int i=1;i<numFields;i++)
                    {
                        double td = Double.parseDouble(tokens[i]);
                        double ttd = td;
                        //success tasks
                        if (i == 14)
                        {
                            td = td - oldSuccessTasks;
                            oldSuccessTasks = ttd;
                        }

                        //failed tasks
                        else if (i == 15)
                        {
                            td = td - oldFailedTasks;
                            oldFailedTasks = ttd;

                        }
                        

                        if (sCalc[index][i] == null)
                        {
                            sCalc[index][i] = new StatCalc();
                        }

                        sCalc[index][i].enter(td);
                        //         System.out.println("entered " + Double.parseDouble(tokens[i]));
                    }


                }
                else
                {
                    System.out.println("erorr in number of fields... "+ tokens.length + " != " + numFields);

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
    int timeQuanta = 1;


    public void main_run(String args[])
    {
        if (args.length != 2)
        {
            System.err.println("invalid number of args: usage: java MergeFalkonSummary2 <inputDir> <timeQuanta>");
            System.err.println("usage: java MergeFalkonSummary2 ./ 1");
            System.exit(-1);
        }

        String dirName = args[0];
        try
        {

            timeQuanta = Integer.parseInt(args[1]);
        }
        catch (Exception e)
        {
            System.err.println("invalid time quanta");
            e.printStackTrace();
            System.exit(-1);
        }

        try
        {
            visitAllFiles(new File(dirName));

        }
        catch (Exception e)
        {
            e.printStackTrace();


        }

        System.err.println("Visited " + fileProcessMaxMinIndex + " files");


        System.err.println("MIN = " + MIN);
        System.err.println("MAX = " + MAX);


        double duration = (MAX - MIN);
        System.err.println("Duration = " + duration + " seconds");




        sCalcSize = (int)(Math.ceil(duration)/timeQuanta)+1000;


        numFields = 31;

        System.err.println("Allocating " + sCalcSize + "*"+ numFields+ " elements for timeQuanta " + timeQuanta + "...");


        sCalc = new StatCalc[sCalcSize][numFields];

        
        //0.0 0 0 18 43 43 0 0 0 0 0 0 0 0.0 0 0 0 0 0 0 0 0 0 0.0 0.0 0 0 100 512 431 512
        for (int i=0;i<sCalcSize;i++)
        {
            for (int j=0;j<numFields;j++)
            {

                //sCalc[i][j] = new StatCalc();
                //sCalc[i][j].reset();
                //will allocate in line with processing... to support sparse graphs...
                sCalc[i][j] = null;
            }

            int displayStatusInterval = sCalcSize/10;
            if (i%displayStatusInterval == 0)
            {
                System.err.println("Allocating " + i*100.0/sCalcSize + "% complete...");

            }
        }
        

        System.err.println("Completed allocating all memory!");

        System.err.println("Will merge " + fileProcessMaxMinIndex + " files...");


        try
        {

            visitAllFilesMerge(new File(dirName));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.err.println("Processed " + fileProcessIndex + " files...");


        System.err.println("Completed merging summaries...");

        System.err.println("Will output summary of length " + sCalcSize + "...");


        System.out.println("TimeQuanta CPUs CPUTimeHr TasksSuccess TasksFailed");
        int numEntries = 0;
        for (int i=0;i<sCalcSize;i++)
        {

            if (sCalc[i][0] != null && sCalc[i][4] != null && sCalc[i][7] != null && sCalc[i][14] != null && sCalc[i][15] != null)
            {
                //time quanta
                System.out.print(i + " ");

                //aver CPUs allocated
                System.out.print(sCalc[i][4].getSum()/timeQuanta + " ");

                //CPU hours
                System.out.print(sCalc[i][7].getSum()/3600.0 + " ");

                //success tasks
                System.out.print(sCalc[i][14].getSum() + " ");

                //failed tasks
                System.out.print(sCalc[i][15].getSum() + " ");



                System.out.println("");
                numEntries++;
            }
            else
            {
                //time quanta
                System.out.println(i + " 0.0 0.0 0.0 0.0");

            }




        }

        System.err.println("Completed outputing merged summaries with " + numEntries + " number of samples!");

    }

    public static void main(String args[])
    {
        MergeFalkonSummary2 mfs = new MergeFalkonSummary2();
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
    private double min;
    private double max;
    private boolean justReset;

    public StatCalc()
    {

    }

    public synchronized void reset() 
    {
        count = 0;
        sum = 0;
        squareSum = 0;
        min = 0;
        max = 0;

    }

    public synchronized void enter(double num) {

        if (count == 0)
        {
            min = max = num;
        }


        // Add the number to the dataset.
        count++;
        sum += num;
        squareSum += num*num;



        if (num > max)
        {
            max = num;
        }
        if (num < min)
        {
            min = num;
        }
    }

    public synchronized void enter(long lnum) {
        // Add the number to the dataset.

        double num = (double)lnum;


        if (count == 0)
        {
            min = max = num;
        }


        count++;
        sum += num;
        squareSum += num*num;



        if (num > max)
        {
            max = num;
        }
        if (num < min)
        {
            min = num;
        }

    }


    public int getCount() {   
        // Return number of items that have been entered.
        return count;
    }

    public double getSum() {
        // Return the sum of all the items that have been entered.
        return sum;
    }

    public double getMax() {
        // Return the sum of all the items that have been entered.
        return max;
    }
    public double getMin() {
        // Return the sum of all the items that have been entered.
        return min;
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
