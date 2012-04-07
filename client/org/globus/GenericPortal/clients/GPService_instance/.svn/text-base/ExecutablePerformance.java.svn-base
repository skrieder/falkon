package org.globus.GenericPortal.clients.GPService_instance;

public class ExecutablePerformance
{
    private String taskID;
    private String workerID;
    private long submitTime;
    private long notificationTime;
    private long resultsTime;
    private long startTime;
    private long endTime;
    private int exitCode;
    //private Executable exec;
    private String exec;


    public ExecutablePerformance()
    {
        this.startTime = 0;
        this.endTime = 0;
        this.submitTime = 0;
        this.notificationTime = 0;
        this.resultsTime = 0;
        this.taskID = null;
        this.workerID = null;
        this.exec = null;
    }

    public static final String UNKNOWN = new String("unknown");

    public void setTaskID(String id)
    {
        //viper.ci.uchicago.edu:50100:20_824460479
        String tokens[] = id.split(":");
        if (tokens.length == 3)
        {
            //this.taskID = new String(tokens[2]);
            this.taskID = new String(id);
            this.workerID = new String("QUEUE_"+tokens[1]);
        }
        else
        {
        
            this.taskID = new String(id);
            this.workerID = UNKNOWN;
        }
    }

    /*
    public void setExecutable(Executable exec)
    {
        this.exec = exec;
    }

    public Executable getExecutable()
    {
        return this.exec;
    }
    public String getExecutableString()
    {
        String com = new String(this.exec.getCommand() + " ");
        String args[] = this.exec.getArguements();
        for (int i=0;i<args.length;i++)
        {
            com = com.concat(args[i] + " ");
        }
        return this.exec.getCommand() + " " ;
    }
    */


    public void setExecutable(String exec)
    {
        this.exec = exec;
    }

    public String getExecutable()
    {
        return this.exec;
    }


    public void setExitCode(int ec)
    {
        this.exitCode = ec;
    }

    public int getExitCode()
    {
        return this.exitCode;
    }


    public void setSubmitTime()
    {
        //setStartTime();
        //this.submitTime = getStartTime();
        this.submitTime = System.currentTimeMillis();
    }

    public long getSubmitTime()
    {
        return this.submitTime;
    }


    public void setNotificationTime()
    {
        if (this.startTime == 0)
        {
            setStartTime();
            this.submitTime = getStartTime();
            this.notificationTime = getStartTime();
        }
        else
            this.notificationTime = System.currentTimeMillis();

    }

    public long getNotificationTime()
    {
        return this.notificationTime;
    }


    public void setResultsTime()
    {
        if (this.startTime == 0)
        {
            setStartTime();
            this.submitTime = getStartTime();
            this.notificationTime = getStartTime();
            this.resultsTime = getStartTime();
        }
        else
            this.resultsTime = System.currentTimeMillis();
    }

    public long getResultsTime()
    {
        return this.resultsTime;
    }

    public void setStartTime()
    {
        this.startTime = System.currentTimeMillis();
    }


    public void setEndTime()
    {
        this.endTime = System.currentTimeMillis();
    }

    public long getEndTime()
    {
        return this.endTime;
    }

    public long getStartTime()
    {
        return this.startTime;
    }


    public synchronized long getTotalTime()
    {
        return this.endTime - this.startTime;
    }

    /*
//"
taskID 
workerID 
startTimeStamp 
execTimeStamp 
resultsQueueTimeStamp 
endTimeStamp 
waitQueueTime 
execTime 
resultsQueueTime 
totalTime 
exitCode
//1 viper.ci.uchicago.edu:50100:20_824460479 tg-v083.uc.teragrid.org:50100 635007 646385 647754 647762 11378 1369 8 12755 0
    */
    public synchronized String toString()
    {
        return "" + this.taskID + " " + this.workerID + " " + this.getStartTime() + " " + this.getSubmitTime() + " " + this.getNotificationTime() + " " + this.getEndTime() + " " + (this.getSubmitTime()-this.getStartTime()) + " " + (this.getNotificationTime()-this.getSubmitTime()) + " " + (this.getEndTime()-this.getNotificationTime()) + " " + this.getTotalTime()  + " " + this.getExitCode();
    }

    public String getTaskID()
    {
        return this.taskID;
    }

}
