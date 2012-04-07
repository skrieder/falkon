//portal
package org.globus.GenericPortal.common;

import java.io.Serializable;
import java.util.*;
import java.io.*;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class WorkerSet implements Serializable 
{


    static final Log logger = LogFactory.getLog(WorkerSet.class);
    public Set queue;
    //public LinkedList queue;
        //public boolean DEBUG = false;
	//LinkedList queue = Collections.synchronizedList(new LinkedList());

	public WorkerSet()
	{
	    logger.debug("Intializing Work Queue!");
            //might want to queue this higher for more workers
            this.queue =  new HashSet(200000, 0.75f);
            //this.DEBUG = logger.isDebugEnabled();

	}

        public synchronized Iterator iterator()
        {
            return queue.iterator();
        }


        // Add work to the work queue
        public synchronized void insert(Object o) {
            queue.add(o);
            logger.debug("insert(): entering notifyAll()... " + o.toString());
            notifyAll();
            logger.debug("insert(): exiting notifyAll()... " + o.toString());
        }


        public synchronized void insertNoNotify(Object o) {
            queue.add(o);
            //notify();
        }

        // Retrieve work from the work queue; block if the queue is empty
        public synchronized Object remove() throws InterruptedException {
            logger.debug("remove(): entering wait()...");

            while (queue.isEmpty()) {
                wait();
            }
            logger.debug("remove(): exiting wait()...");

            Object o = null;
            Iterator it=queue.iterator();
            if (it.hasNext())
            {
            
                o = it.next();
                it.remove();
            }

            //Object o = queue.removeFirst();
            if (o != null)
            {
            
                logger.debug("remove(): " + o.toString());
            }
            return o;
        }

        public synchronized boolean waitUntilNotEmpty() throws InterruptedException {
            logger.debug("waitUntilNotEmpty(): entering wait()...");
            logger.debug("waitUntilNotEmpty(): entering wait()... this.hashCode() = " + this.hashCode());
            logger.debug("waitUntilNotEmpty(): entering wait()... queue.hashCode() = " + queue.hashCode());

                while (queue.isEmpty())
                {

                    wait();
                    logger.debug("waitUntilNotEmpty(): waking up to check queue length... "+queue.size());
                }
            logger.debug("waitUntilNotEmpty(): exiting wait()... queue.size()=" + queue.size());
            return true;
        }


        // Retrieve work from the work queue; block if the queue is empty
        public synchronized boolean remove(String worker) //throws InterruptedException {
	{
            logger.debug("***** WorkerSet: remove(" + worker + ")...");

            boolean retCode = false;

            logger.debug("***** WorkerSet: queue.contains(worker) = " + queue.contains(worker));
	    while(queue.contains(worker))
	    {
		retCode = queue.remove(worker);
                logger.debug("***** WorkerSet: queue.remove(worker) = " + retCode);
                
	    }

            logger.debug("***** WorkerSet: returning " + retCode);
	    return retCode;
	}

        public synchronized String[] toArray()
        {

            Object[] objectArray = queue.toArray();
    return (String[])queue.toArray(new String[queue.size()]);

          //  return (String[])queue.toArray();
        }

        public synchronized String toString()
        {
            String result = new String("[ ");
            for (Iterator it=queue.iterator(); it.hasNext(); ) {
        result += (String)it.next() + " ";
    }
            result += "]";
            return result;

        }



        // Retrieve work from the work queue; block if the queue is empty
        public synchronized boolean removeTask(Object task) //throws InterruptedException {
        {

            boolean retCode = false;
            while(queue.contains(task))
            {
                retCode = queue.remove(task);
            }

            return retCode;
        }


        // Retrieve work from the work queue; block if the queue is empty
        public synchronized boolean exists(String worker) //throws InterruptedException {
	{

	
	    return queue.contains(worker);
	}

	// Retrieve work from the work queue; block if the queue is empty
	public synchronized Object removeNoWait() //throws InterruptedException {
	{
	
	    if (queue.isEmpty()) 
	    {
		return null;
		//wait();
	    }
                Object o = null;
                Iterator it=queue.iterator();
                if (it.hasNext())
                {

                    o = it.next();
                    it.remove();
                }

                //Object o = queue.removeFirst();
                if (o != null)
                {

                    logger.debug("remove(): " + o.toString());
                }
                return o;
            
	}


        public synchronized boolean removeNoWait(String worker) //throws InterruptedException {
        {
                return queue.remove(worker);
        }


	// gets the size of the queue
	public synchronized int size() 
	{
	    return queue.size();
	}


        
        private void writeObject(java.io.ObjectOutputStream out) throws IOException
        {
            out.defaultWriteObject();
        }

        private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
        {
             in.defaultReadObject();
        }

        private void readObjectNoData() throws ObjectStreamException
        {
            //hopefully this will never happen :)
            logger.debug("Serialization error: readObjectNoData");


        }   

    }
