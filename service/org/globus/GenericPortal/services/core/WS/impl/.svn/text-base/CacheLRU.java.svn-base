package org.globus.GenericPortal.services.core.WS.impl;

import java.util.*;

public class CacheLRU extends java.util.LinkedHashMap 
{
    // Create cache
    //set this high enough so we don't run out of number of entries
    private int MAX_ENTRIES = 0;

    private double MAX_SIZE_MB = 0;
    private double cacheContentSize = 0;

    Map fileSize = null;
    boolean DEBUG = false;

    public CacheLRU(double MAX_SIZE_MB)
    {
        super(10000, .75F, true);
        this.MAX_ENTRIES = 10000;

        this.MAX_SIZE_MB = MAX_SIZE_MB;
        fileSize = (Map)Collections.synchronizedMap(new HashMap(MAX_ENTRIES+1, .75F));;


    }

    public int size()
    {
        return super.size();
    }

    public double getCacheContentsSizeMB()
    {
        return cacheContentSize*1.0/(1024);
    }

    public synchronized void setCacheContentSize(int numKB, boolean inc)
    {
        if (inc)
        {
            cacheContentSize += numKB;
        }
        else
        {
            cacheContentSize -= numKB;
        }
    }





    protected synchronized boolean removeEldestEntry(Map.Entry eldest) 
    {
        return false;
    }


    public synchronized String removeEldest() 
    {

        if (super.size() == 0)
        {

            if (DEBUG) System.out.println("CacheIndex: removeEldest()... size = 0, nothing to remove...");
            return null;
        }

        if (super.size() > MAX_ENTRIES)
        {
            Iterator it=super.entrySet().iterator();
            Map.Entry entry = (Map.Entry)it.next();
            String eldest = (String)entry.getValue();
            if (DEBUG) System.out.println("CacheIndex: removeEldest()... size > MAX_ENTRIES " + super.size() + " > " + MAX_ENTRIES + ", eldest entry " + eldest);

            if (DEBUG) System.out.println("removeEldest(): it.remove()...");
            it.remove();

            //boolean success = removeFile(eldest);
            //if (!success)
            //{
            //    if (DEBUG) System.out.println("CacheIndex: removeEldest()... removal of " + eldest + " failed");
            //}
            //else
            //{
            //    if (DEBUG) System.out.println("CacheIndex: removeEldest()... removal of " + eldest + " succesful!");
            //}
            return eldest;
        }
        else
        {
            if (DEBUG) System.out.println("CacheIndex: removeEldest()... size <= MAX_ENTRIES " + super.size() + " <= " + MAX_ENTRIES + ", nothing to remove");
            return null;
        }
    }


    //must implement
    public synchronized String[] removeEldest(double numMB) 
    {
        double numMBremoved = 0.0;
        double numMBtoRemove = Math.max(getCacheContentsSizeMB() + numMB - MAX_SIZE_MB, 0);

        List evictedEldest = new LinkedList();


        if (super.size() == 0)
        {

            if (DEBUG) System.out.println("CacheIndex: removeEldest()... size = 0, nothing to remove...");
            return null;
        }

        ///if (super.size() > MAX_ENTRIES || getCurSizeMB() > MAX_SIZE_MB)
        while ((numMBtoRemove > 0 && numMBremoved < numMBtoRemove && super.size() > 0 && getCacheContentsSizeMB() > MAX_SIZE_MB) || super.size() > MAX_ENTRIES)
        {
            Iterator it=super.entrySet().iterator();
            Map.Entry entry = (Map.Entry)it.next();
            //String eldest = (String)entry.getValue();
            String eldest = (String)entry.getKey();
            if (DEBUG) System.out.println("CacheIndex: removeEldest()... size > MAX_ENTRIES " + super.size() + " > " + MAX_ENTRIES + ", eldest entry " + eldest);

            evictedEldest.add(eldest);

            if (DEBUG) System.out.println("removeEldest(): it.remove()...");
            it.remove();



            Integer iSize = (Integer)fileSize.remove(eldest);
            if (iSize != null)
            {
                setCacheContentSize(iSize.intValue(),false);
            }



        }
        if (evictedEldest.isEmpty())
        {
            return null;
        }
        else
        {
            return(String[])evictedEldest.toArray(new String[evictedEldest.size()]);
        }
    }

    /*
    public synchronized String put(String key, String value)
    {
        super.put(key, value);

        String evictedKey = null;
        evictedKey = removeEldest();

        if (evictedKey != null)
        {
            if (DEBUG) System.out.println("CacheIndex: put(" + key + "," + value + ")... evicted " + evictedKey);
            return evictedKey;
        }
        else
        {
            return null;
        }
    }
      */

    public synchronized String[] put(String key, Object value, int size)
    {
        if (super.get(key) == null)
        {
            super.put(key, value);
            fileSize.put(key,new Integer(size));
            setCacheContentSize(size,true);

            String evictedKeys[] = null;
            evictedKeys = removeEldest(size);

            if (evictedKeys != null)
            {
                if (DEBUG) System.out.println("CacheIndex: put(" + key + "," + value + ")... evicted: ");
                for (int i=0;i<evictedKeys.length;i++)
                {

                    if (DEBUG) System.out.print(evictedKeys[i] + " ");

                }
                if (DEBUG) System.out.println("");
                return evictedKeys;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }


}
