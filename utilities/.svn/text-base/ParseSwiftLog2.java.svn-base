import java.io.*;
import java.util.*;
import java.text.*;
    
    public class ParseSwiftLog2 
    {
        boolean DEBUG = false;

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



        public ParseSwiftLog2()
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

        //0             1               2       3           4           5                   6           7                                   8                               9   10      11  12      13                                                                      14
        //2009-03-28 21:54:55,231-0500 DEBUG vdl:execute2 JOB_START jobid=RInvoke-uc4kol8j tr=RInvoke arguments=[scripts/singlemodels.R, matrices/5_reg/net2_speech.cov, 94544, 0.5, speech, net2] tmpdir=5reg_exhaustive-20090328-2110-3zdtzah4/jobs/u/RInvoke-uc4kol8j host=RANGER
        //0             1               2       3       4                           5                                      6        7    8   9      10
        //2009-10-25 21:41:00,276-0500 DEBUG TaskImpl Task(type=JOB_SUBMISSION, identity=urn:0-1-6886-1-1-1256524752706) setting status to Active workerid=000951
        //0             1               2       3       4                           5                                                       6   7       8   9
        //2009-10-25 21:42:10,608-0500 DEBUG TaskImpl Task(type=JOB_SUBMISSION, identity=urn:1256524752479-1256524793584-1256524793585) setting status to Completed
        public void process(String str) throws Exception
        {
            String tokens[] = str.split(" ");
            String jobID = null;
            if (tokens.length >= 10)
            {
                long t = timeMS(tokens[0] + " " + tokens[1]);
                //System.out.println("timeMS: " + t);


                String jobIDtokens[] = tokens[5].split(":");
                if (jobIDtokens.length == 2)
                {
                    jobID = jobIDtokens[1].substring(0, jobIDtokens[1].length() - 1);
                }
                else
                {
                    if (DEBUG) System.err.println("failed to parse jobid: " + tokens[5]);
                    if (DEBUG) System.err.println("unable to parse log entry: " + str);

                    jobID = null;
                }

                if (jobID != null && tokens[9].contains("Active"))
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
                else if (jobID != null && tokens[9].contains("Completed"))
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
                    if (DEBUG) System.err.println("unkown job state: " + tokens[9]);
                    if (DEBUG) System.err.println("unable to parse log entry: " + str);
                }

                

            }
            else
            {
                if (DEBUG) System.err.println("unable to parse log entry: " + str);
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
                System.out.println("java ParseSwiftLog2 <fileName>");
                System.exit(1);
            }

            ParseSwiftLog2 psl = new ParseSwiftLog2();

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
