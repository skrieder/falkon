package org.globus.GenericPortal.services.core.WS.impl;

import java.rmi.RemoteException;

import org.globus.GenericPortal.stubs.GPService_instance.*;
import org.globus.wsrf.NoSuchResourceException;
import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.ResourceContextException;
import org.globus.wsrf.ResourceKey;

import org.globus.wsrf.impl.ResourceContextImpl;
import org.globus.wsrf.impl.ResourceHomeImpl;

import java.util.*; 

import java.net.*;
import java.io.*;

import java.lang.Object;
import java.lang.*;

import org.globus.GenericPortal.common.*;

public class PendingTasks
{
    private Map pendingTasks = null;
    public static String ANY = "any";

    private int numPendingTasks = 0;


    public PendingTasks(int initialCapacity, float loadFactor)
    {
        pendingTasks = Collections.synchronizedMap(new HashMap(initialCapacity, loadFactor));
        pendingTasks.put(ANY, Collections.synchronizedList(new LinkedList()));
    }

    public int numberExecutors()
    {
        return (pendingTasks.size() - 1);

    }

    public synchronized void incNumPendingTasks()
    {
        numPendingTasks++;
    }
    public synchronized void decNumPendingTasks()
    {
        numPendingTasks--;
    }

    public int getNumPendingTasks()
    {
        return numPendingTasks;
    }


    public boolean isEmpty()
    {
        return pendingTasks.isEmpty();
    }

    public boolean insertTask(String executor, Task task)
    {
        List curTasks = (List)pendingTasks.get(executor);
        //this should never happen
        if (curTasks == null)
            registerExecutor(executor);
        boolean retVal = curTasks.add(task);
        if (retVal)
        {
        
            incNumPendingTasks();
        }
        return retVal;
    }


    public Task getTask(String executor)
    {
        List curTasks = (List)pendingTasks.get(executor);
        Task task = null;
        if ((curTasks == null) || (curTasks != null && curTasks.isEmpty()))
        {
            task = getTask();
        }
        else
        {
            task = (Task)curTasks.get(0);
        }

        if (task != null)
        {
            decNumPendingTasks();
        }
        return task;
    }

    /*
    public Task getTask(String executor)
    {
        List curTasks = (List)pendingTasks.get(executor);
        //this should never happen
        if (curTasks == null)
            return null;
        else
        {
            if (!curTasks.isEmpty())
            {
                return curTasks.get(0);
            }
            else
                return null;
        }
    } */

    public Task getTask()
    {
        List curTasks = (List)pendingTasks.get(ANY);
        //this should never happen
        if (curTasks == null)
            return null;
        else
        {
            if (!curTasks.isEmpty())
            {
                return (Task)curTasks.get(0);
            }
            else
            {
                return getAnyTask();

            }
                
        }
    }

    public synchronized Task getAnyTask()
    {
        if (numPendingTasks > 0)
        {
        
        for (Iterator it=pendingTasks.entrySet().iterator(); it.hasNext(); )
        {
            Map.Entry entry = (Map.Entry)it.next();
            //String key = (String)entry.getKey();
            List value = (List)entry.getValue();
            if (!value.isEmpty())
            {
                return (Task)value.get(0);
            }
        }
        }
        return null;

    }


    public boolean registerExecutor(String executor)
    {
        if (!pendingTasks.containsKey(executor))
        {
            pendingTasks.put(executor, Collections.synchronizedList(new LinkedList()));
        }
        return true;
    }

    public boolean deregisterExecutor(String executor)
    {
        if (pendingTasks.containsKey(executor))
        {
            List curTasks = (List)pendingTasks.remove(executor);
            List anyTasks = (List)pendingTasks.get(ANY);
            if (curTasks != null && !curTasks.isEmpty())
            {
                anyTasks.addAll(curTasks);
            }
        }
        return true;
    }



}
