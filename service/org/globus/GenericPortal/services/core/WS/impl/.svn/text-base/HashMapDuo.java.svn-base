package org.globus.GenericPortal.services.core.WS.impl;

import java.util.*;
import java.io.*;
import java.lang.*;
//import impl.*;

public class HashMapDuo
{
    private SortedMap map1 = null;
    private SortedMap map2 = null;
    //private SortedSet ssAll = null;
    private boolean DEBUG = false;

    public HashMapDuo()
    {
        map1 = Collections.synchronizedSortedMap(new TreeMap());
        map2 = Collections.synchronizedSortedMap(new TreeMap());
        //ssAll = Collections.synchronizedSortedSet(new TreeSet<String>());
    }


    public HashMapDuo(HashMap stacks, boolean debug, boolean c) //this is the slow version, but has cleaner code...
    {

        this.DEBUG = debug;
        long start = System.currentTimeMillis();
        map1 = Collections.synchronizedSortedMap(new TreeMap());
        map2 = Collections.synchronizedSortedMap(new TreeMap());

        long elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println("HashMapDuo(): 1 " + elapsedTimeMillis);

        for (Iterator it=stacks.values().iterator(); it.hasNext(); )
        {
            SortedSet value = (SortedSet)it.next();
            if (value!=null)
            {


                for (Iterator its=value.iterator(); its.hasNext(); )
                {
                    String key = (String)its.next();
                    insert(key);
                }
            } else
            {
                insert("*");

            }
        }

        elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println("HashMapDuo(): 2 " + elapsedTimeMillis);
    }


    public HashMapDuo(HashMap stacks, boolean debug)
    {
        this.DEBUG = debug;
        long start = System.currentTimeMillis();
        map1 = Collections.synchronizedSortedMap(new TreeMap());
        map2 = Collections.synchronizedSortedMap(new TreeMap());
        //ssAll = Collections.synchronizedSortedSet(new TreeSet<String>());


        if (DEBUG) System.out.println("initializing HashMapDuo() with stacks.size() " + stacks.size());
        Map hmt = new HashMap((int)(stacks.size()*1.5));
        //SortedSet ssAll = Collections.synchronizedSortedSet(new TreeSet<String>());

        long elapsedTimeMillis = System.currentTimeMillis()-start;
        if (DEBUG) System.out.println("HashMapDuo(): 1 " + elapsedTimeMillis);

        for (Iterator it=stacks.values().iterator(); it.hasNext(); )
        {
            SortedSet value = (SortedSet)it.next();
            if (value!=null)
            {
                if (DEBUG) System.out.println("stacks value: " + value);

                for (Iterator its=value.iterator(); its.hasNext(); )
                {
                    String key = (String)its.next();
                    Integer i = (Integer)hmt.get(key);
                    if (i==null)
                    {
                        hmt.put(key, new Integer(1));
                        if (DEBUG) System.out.println("hmt.put(): " + key + " " + new Integer(1));


                    } else
                    {
                        if (DEBUG) System.out.println("hmt.put(): " + key + " " + (i.intValue()+1));
                        hmt.put(key, new Integer(i.intValue() + 1));
                    }
                }
            } else
            {
                String key = new String("*");
                Integer i = (Integer)hmt.get(key);
                if (i==null)
                {
                    hmt.put(key, new Integer(1));
                    if (DEBUG) System.out.println("hmt.put(): " + key + " " + new Integer(1));

                } else
                {
                    hmt.put(key, new Integer(i.intValue() + 1));
                    if (DEBUG) System.out.println("hmt.put(): " + key + " " + (i.intValue()+1));
                }


            }
        }


        //ssAll.add("*");

        elapsedTimeMillis = System.currentTimeMillis()-start;
        if (DEBUG) System.out.println("HashMapDuo(): 2 " + elapsedTimeMillis);



// For both the keys and values of a map

        if (DEBUG) System.out.println("HashMapDuo(): hmt.size() " + hmt.size());
        for (Iterator it=hmt.entrySet().iterator(); it.hasNext(); )
        {
            Map.Entry entry = (Map.Entry)it.next();
            if (DEBUG) System.out.println("hmt entry: " + entry.getKey() + " " + entry.getValue());
            insert((String)entry.getKey(), (Integer)entry.getValue());
            //String key = entry.getKey();
            //Integer value = entry.getValue();
        }     

        elapsedTimeMillis = System.currentTimeMillis()-start;
        if (DEBUG) System.out.println("HashMapDuo(): 3 " + elapsedTimeMillis);


    }



    public void HashMapDuo2(HashMap stacks) //this is an old constructor which works OK, but the above is slightly better...
    {
        long start = System.currentTimeMillis();
        map1 = Collections.synchronizedSortedMap(new TreeMap());
        map2 = Collections.synchronizedSortedMap(new TreeMap());
        //ssAll = Collections.synchronizedSortedSet(new TreeSet<String>());

        Map hmt = new HashMap((int)(stacks.size()*1.5));
        SortedSet ssAll = Collections.synchronizedSortedSet(new TreeSet());

        long elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println("HashMapDuo(): 1 " + elapsedTimeMillis);

        for (Iterator it=stacks.values().iterator(); it.hasNext(); )
        {
            SortedSet value = (SortedSet)it.next();
            if (value!=null)
            {

                ssAll.addAll(value);
            }

        }
        ssAll.add("*");

        elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println("HashMapDuo(): 2 " + elapsedTimeMillis);


        String ssAllArray[] = (String[])ssAll.toArray(new String[ssAll.size()]);
        int ssAllArrayCount[] = new int[ssAllArray.length];
        int intFillValue = 0;
        Arrays.fill(ssAllArrayCount, intFillValue);


        for (Iterator it=stacks.values().iterator(); it.hasNext(); )
        {
            SortedSet value = (SortedSet)it.next();
            if (value!=null)
            {

                for (Iterator its=value.iterator(); its.hasNext(); )
                {
                    String key = (String)its.next();

                    int index = Arrays.binarySearch(ssAllArray, key);
                    ssAllArrayCount[index]++;
                }
            } else
            {
                int index = Arrays.binarySearch(ssAllArray, "*");
                ssAllArrayCount[index]++;
            }

        }

        elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println("HashMapDuo(): 3 " + elapsedTimeMillis);

        for (int i=0;i<ssAllArray.length;i++)
        {
            insert(ssAllArray[i], new Integer(ssAllArrayCount[i]));
        }

        elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println("HashMapDuo(): 4 " + elapsedTimeMillis);



    }


    public synchronized void insert(String key)
    {
        Integer temp = (Integer)map1.get(key);
        if (temp != null)
        {
            removeVal(temp,key);
        } else
        {
            temp = new Integer(0);
        }

        map1.put(key, new Integer(temp.intValue() + 1));
        SortedSet ss = (SortedSet)map2.get(new Integer(temp.intValue() + 1));
        if (ss == null)
        {
            ss = new TreeSet();
        }
        ss.add(key);
        map2.put(new Integer(temp.intValue() + 1), ss);

        //ssAll.add(key);

    }

    public synchronized void insert(String key, Integer i)
    {
        if (i.intValue() < 1)
        {
            remove(key);
            //ssAll.remove(key);

        } else
        {

            Integer temp = (Integer)map1.get(key);
            if (temp != null)
            {
                removeVal(temp,key);
            }

            map1.put(key, i);
            SortedSet ss = (SortedSet)map2.get(i);
            if (ss == null)
            {
                ss = new TreeSet();
            }
            ss.add(key);
            map2.put(i, ss);

            //ssAll.add(key);
        }
    }


    private synchronized void removeVal(Integer i, String key)
    {
        SortedSet ss = (SortedSet)map2.get(i);
        if (ss.contains(key))
        {
            ss.remove(key);
        }
        if (ss.isEmpty())
        {
            map2.remove(i);
        }
    }

    public synchronized void remove(String key)
    {
        Integer i = (Integer)map1.get(key);
        if (i != null)
        {
            map1.remove(key);
            removeVal(i,key );

            //ssAll.remove(key);
        }
    }


    public synchronized boolean isEmpty()
    {
        if (map1.isEmpty() && map2.isEmpty())
        {
            return true;
        } else
            return false;
    }

    public synchronized Integer findKey(String key)
    {
        return(Integer)map1.get(key);
    }

    public synchronized SortedSet findValue(Integer i)
    {
        return(SortedSet)map2.get(i);
    }

    public synchronized SortedSet findValueMax()
    {
        if (map2.isEmpty())
        {
            return null;
        } else
            return(SortedSet)map2.get(map2.lastKey());
    }

    public synchronized void printMap()
    {
        if (map1.isEmpty())
        {
            System.out.println("map1 is empty");
        }
        else
        {
        
        if (DEBUG) System.out.println("printing first map of size: " + map1.size());
        for (Iterator it=map1.keySet().iterator(); it.hasNext(); )
        {
            String key = (String)it.next();
            Integer value = (Integer)map1.get(key);
            if (DEBUG) System.out.println(key + " ==> " + value);
        }
        }


        if (map2.isEmpty())
        {
            System.out.println("map2 is empty");
        }
        else
        {

        if (DEBUG) System.out.println("printing second map of size: " + map2.size());
        for (Iterator it=map2.keySet().iterator(); it.hasNext(); )
        {
            Integer key = (Integer)it.next();
            SortedSet value = (SortedSet)map2.get(key);

            if (value == null)
            {
                if (DEBUG) System.out.println(key + " ==> null");
            }

            else
                if (DEBUG) System.out.println(key + " ==> " + value);

        }
        }
    }


    public static void main_old(String args[])
    {
        if (args.length != 2)
        {
            System.out.println("usage: java impl/HashMapDuo <numWorkers> <jobSize>");
            System.exit(0);
        }

        HashMapDuo hmd = new HashMapDuo();


        int numWorkers = Integer.parseInt(args[0]);
        int jobSize = Integer.parseInt(args[1])/numWorkers;

        long start = System.currentTimeMillis();

        for (int j = 0;j<numWorkers;j++)
            hmd.insert("tg-v"+j, new Integer(jobSize));

        int jobNum = 0;
        while (!hmd.isEmpty())
        {
            SortedSet ss = hmd.findValueMax();
            Integer i = hmd.findKey((String)ss.first());
            int workSize = Math.min(i.intValue(), 100);
            //System.out.println("Sending work # " + jobNum++ + " to " + (String)ss.first() + " of size " + workSize);
            hmd.insert((String)ss.first(), new Integer(i.intValue() - workSize));
        }
        long elapsedTimeMillis = System.currentTimeMillis()-start;

        System.out.println("numWorkers " + numWorkers + " jobSize " + jobSize + " time " + elapsedTimeMillis); 
    }

    public static void main(String args[])
    {
        if (args.length != 2)
        {
            System.out.println("usage: java impl/HashMapDuo <numWorkers> <jobSize>");
            System.exit(0);
        }


        int numWorkers = Integer.parseInt(args[0]);
        int jobSize = Integer.parseInt(args[1])/numWorkers;

        String stackings[] = new String[jobSize*numWorkers];
        for (int i=0;i<jobSize*numWorkers;i++)
        {
            stackings[i] = i+":/some/unique/image/location.fit";

        }

        HashMap hm = new HashMap((int)(stackings.length*1.5));

        for (int i=0;i<jobSize*numWorkers;i++)
        {
            SortedSet ss = new TreeSet();
            for (int j=0;j<numWorkers;j++)
            {
                ss.add("tg-v"+j);
            }
            hm.put(stackings[i], ss);
        }

        long start = System.currentTimeMillis();
        HashMapDuo hmd = new HashMapDuo(hm, true);

        long elapsedTimeMillis = System.currentTimeMillis()-start;

        System.out.println("initializing " + elapsedTimeMillis); 


        //for (int j = 0;j<numWorkers;j++)
        //    hmd.insert("tg-v"+j, new Integer(jobSize));

        int jobNum = 0;
        while (!hmd.isEmpty())
        {
            SortedSet ss = hmd.findValueMax();
            Integer i = hmd.findKey((String)ss.first());
            int workSize = Math.min(i.intValue(), 100);
            //System.out.println("Sending work # " + jobNum++ + " to " + (String)ss.first() + " of size " + workSize);
            hmd.insert((String)ss.first(), new Integer(i.intValue() - workSize));
        }
        elapsedTimeMillis = System.currentTimeMillis()-start;

        System.out.println("numWorkers " + numWorkers + " jobSize " + jobSize + " time " + elapsedTimeMillis); 
    }


}
