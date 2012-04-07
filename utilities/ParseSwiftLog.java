import java.io.*;
import java.util.*;
import java.text.*;
    
    public class ParseSwiftLog 
    {
        class Entry
        {
            String jobID;
            long startTime;
            long endTime;

            public Entry()
            {
                jobID = new String("null");
                startTime = 0;
                endTime = 0;
            }

        }

        long maxTime = 0;
        long minTime = 0;

        Map map = new LinkedHashMap();



        public ParseSwiftLog()
        {
        }

        public long timeMS(String time) throws Exception
        {
            //2009-03-28 21:54:55,232-0500
            DateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss,SSSZ");
            Date d = (Date)formatter.parse(time);

            if (d.getTime() > maxTime)
            {
                maxTime = d.getTime();
                if (minTime == 0)
                {
                    minTime = maxTime;
                }
            }

            if (d.getTime() < minTime)
            {
                minTime = d.getTime();
            }

            return d.getTime();

        }

        //2009-03-28 21:54:55,231-0500 DEBUG vdl:execute2 JOB_START jobid=RInvoke-uc4kol8j tr=RInvoke arguments=[scripts/singlemodels.R, matrices/5_reg/net2_speech.cov, 94544, 0.5, speech, net2] tmpdir=5reg_exhaustive-20090328-2110-3zdtzah4/jobs/u/RInvoke-uc4kol8j host=RANGER
        public void process(String str) throws Exception
        {
            String tokens[] = str.split(" ");
            String jobID = null;
            if (tokens.length >= 6)
            {
                long t = timeMS(tokens[0] + " " + tokens[1]);
                //System.out.println("timeMS: " + t);


                String jobIDtokens[] = tokens[5].split("=");
                if (jobIDtokens.length == 2)
                {
                    jobID = jobIDtokens[1];
                }
                else
                {
                    System.out.println("failed to parse jobid: " + tokens[5]);
                    jobID = new String("null");
                }

                if (tokens[4].contains("JOB_START"))
                {
                    Entry e = (Entry)map.get(jobID);
                    if (e == null)
                    {
                        e = new Entry();
                    }
                    e.jobID = jobID;
                    e.startTime = t;
                    map.put(jobID, e);
                }
                else if (tokens[4].contains("JOB_END"))
                {
                    Entry e = (Entry)map.get(jobID);
                    if (e == null)
                    {
                        e = new Entry();
                    }
                    e.jobID = jobID;
                    e.endTime = t;
                    map.put(jobID, e);
                }
                else
                {
                    System.out.println("unkown job state: " + tokens[4]);
                }

                

            }
            else
            {
                System.out.println("unable to parse log entry: " + str);
            }


        }



        public void readFile(String fileName) throws Exception
        {
             //try {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String str;
        while ((str = in.readLine()) != null) {
            process(str);
        }
        in.close();
    //} catch (IOException e) {
    //    e.printStackTrace();
    //}


        }

        public void printMap() throws Exception
        {
            //System.out.println("Map size = " + map.size());
            System.out.println("//taskNum taskID workerID startTimeStamp execTimeStamp resultsQueueTimeStamp endTimeStamp waitQueueTime execTime resultsQueueTime totalTime exitCode");
            System.out.println("//0-time is " + minTime + "ms");

            int taskID = 1;

             // List the entries
    for (Iterator it=map.keySet().iterator(); it.hasNext(); ) {
        String jobID = (String)it.next();
        Entry e = (Entry)map.get(jobID);

        if (e.startTime == 0)
        {
            System.err.println("jobID " + e.jobID + " was missing the start of the job time stamp, ignoring");
        }
        else if (e.endTime == 0)
        {
            System.out.println(taskID + " localhost:00000:" + taskID + "_" + e.jobID + " SWIFT.log " + e.startTime + " " + e.startTime + " " + maxTime + " " + maxTime + " 0 " + (maxTime - e.startTime) + " 0 " + (maxTime - e.startTime) + " 0");
            
        }
        else
        {
        
            System.out.println(taskID + " localhost:00000:" + taskID + "_" + e.jobID + " SWIFT.log " + e.startTime + " " + e.startTime + " " + e.endTime + " " + e.endTime + " 0 " + (e.endTime - e.startTime) + " 0 " + (e.endTime - e.startTime) + " 0");
        }
        taskID++;


    }

        }



        public static void main(String[] args)
        {
            if (args.length < 1)
            {
                System.out.println("incorect args");
                System.exit(1);
            }

            ParseSwiftLog psl = new ParseSwiftLog();

            try
            {
            
            psl.readFile(args[0]);
            psl.printMap();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
    }
