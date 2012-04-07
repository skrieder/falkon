package org.globus.GenericPortal.services.core.WS.impl;

public class TaskPerformance
{
    private String taskID;
    private String workerID;
    private long waitQueueTime;
    private long workerQueueTime;
    private long resultsQueueTime;
    private long startTime;
    private long endTime;
    //just added this...
    private int exitCode;
    private int numRetries;


    public TaskPerformance()
    {
        this.taskID = null;
        this.workerID = null;
        this.waitQueueTime = -1;
        this.workerQueueTime = -1;
        this.resultsQueueTime = -1;
        this.exitCode = -77777;
        this.numRetries = 0;
    }


    //public synchronized void setNumRetries(int n)
    public void setNumRetries(int n)
    {
        this.numRetries = n;
    }


    public int getNumRetries()
    {
        return this.numRetries;
    }

    public void setTaskID(String id)
    {
        this.taskID = new String(id);
    }

    public void setExitCode(int exitCode)
    {
        this.exitCode = exitCode;
    }


    public void setWorkerID(String id)
    {
        this.workerID = new String(id);
    }

    public void setWaitQueueTime()
    {
        this.startTime = System.currentTimeMillis();
        this.waitQueueTime = this.startTime;
        this.workerQueueTime = this.startTime;
        this.resultsQueueTime = this.startTime;
    }

    public void setWorkerQueueTime()
    {
        this.workerQueueTime = System.currentTimeMillis();
        this.resultsQueueTime = this.workerQueueTime;
    }

    public void setResultsQueueTime()
    {
        this.resultsQueueTime = System.currentTimeMillis();
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


    public synchronized long getWaitQueueTime()
    {
        return this.workerQueueTime - this.waitQueueTime;
    }


    public synchronized long getExecTime()
    {
        return this.resultsQueueTime - this.workerQueueTime;
    }

    public synchronized long getResultsQueueTime()
    {
        return this.endTime - this.resultsQueueTime;
    }

    public synchronized long getTotalTime()
    {
        return this.endTime - this.startTime;
    }

    /*
    public synchronized String toString()
    {
        return "" + this.getTaskID() + " " + this.getWorkerID() + " " + this.getStartTime() + " " + this.getEndTime() + " " + this.getWaitQueueTime() + " " + this.getExecTime() + " " + this.getResultsQueueTime() + " " + this.getTotalTime() + " " + this.getExitCode();
    } */

    //"taskID workerID startTimeStamp execTimeStamp resultsQueueTimeStamp endTimeStamp waitQueueTime execTime resultsQueueTime totalTime exitCode\n");
    //public synchronized String toString(long offset)
    public String toString(long offset)
    {
        return "" + this.getTaskID() + " " + this.getWorkerID() + " " + (this.waitQueueTime-offset) + " " + (this.workerQueueTime-offset) + " " + (this.resultsQueueTime-offset) + " " + (this.getEndTime()-offset) + " " + this.getWaitQueueTime() + " " + this.getExecTime() + " " + this.getResultsQueueTime() + " " + this.getTotalTime() + " " + this.getExitCode();
    }


    public String getTaskID()
    {
        return this.taskID;
    }

    public String getWorkerID()
    {
        return this.workerID;
    }

    public int getExitCode()
    {
        return this.exitCode;
    }


}
