
package org.globus.GenericPortal.common;

import java.util.*;
import java.io.*;
import java.lang.*;
import org.globus.GenericPortal.common.*;

public class CacheIndex extends java.util.LinkedHashMap implements Serializable
{
    public int MAX_ENTRIES = 0;
    public int MAX_CACHE_SIZE_MB = 1000;
    static final long serialVersionUID = 1;
    public Random randInt = new Random();
    public boolean lru = false;
    public boolean random = false;
    public boolean fifo = false;
    public boolean popular = false;

    private Map popularityIndexIS = null;
    private Map popularityIndexSI = null;
    private Map popularityIndexSIX = null;

    long MIN_FREE_SPACE = 0;
    private DiskSpace2 disk = null;//new DiskSpace2(MIN_FREE_SPACE);
    private String cachePath = null;

    Map reverseSymbolicLinks = null;

    private long cacheContentSize = 0;
    private int cacheContentNum = 0;
    public boolean DEBUG = false;

    public double getAvailableDiskSpace2MB()
    {
        //return disk.getAvailableSpaceMB(cachePath);
        //if (DEBUG) System.out.println("cachePath = " + cachePath); 
        //if (DEBUG) disk.printStat(cachePath);
        if (cachePath == null)
        {
            return 0.0;
        }
        return disk.getUsableSpaceMB(cachePath)*1.0;
    }


    public double getCacheContentsSizeMB()
    {
        return cacheContentSize*1.0/(1024*1024);
    }

    public int getCacheContentsNum()
    {
        return cacheContentNum;
    }


    public synchronized void setCacheContentSize(long numBytes, boolean inc)
    {
        if (inc)
        {
            cacheContentSize += numBytes;
            cacheContentNum++;
        }
        else
        {
            cacheContentSize -= numBytes;
            cacheContentNum--;

        }


    }

    public double getCacheNumEntries()
    {
        return super.size();
    }



    public CacheIndex(int MAX_ENTRIES, float loadFactor, CacheStrategy cs, String cachePath, long MIN_FREE_SPACE, boolean DEBUG)
    {
        super(MAX_ENTRIES+1, loadFactor, cs.isLRU());
        this.DEBUG = DEBUG;
        //set this dynamically
        this.MAX_CACHE_SIZE_MB = 1000;
        reverseSymbolicLinks = (Map)Collections.synchronizedMap(new HashMap(MAX_ENTRIES+1, loadFactor));
        this.lru = cs.isLRU();
        this.random = cs.isRANDOM();
        this.fifo = cs.isFIFO();
        this.popular = cs.isPOPULAR();
        this.MAX_ENTRIES = MAX_ENTRIES;
        this.cachePath = cachePath;
        this.MIN_FREE_SPACE = MIN_FREE_SPACE;
        this.DEBUG = DEBUG;

        disk = new DiskSpace2(MIN_FREE_SPACE, cachePath, DEBUG);

        if (this.popular)
        {
            popularityIndexIS = new TreeMap();
            popularityIndexIS = (Map)Collections.synchronizedMap(popularityIndexIS);
            popularityIndexSI = new TreeMap();
            popularityIndexSI = (Map)Collections.synchronizedMap(popularityIndexSI);
            popularityIndexSIX = new TreeMap();
            popularityIndexSIX = (Map)Collections.synchronizedMap(popularityIndexSIX);
        }

    }

    public CacheIndex(Map cache, int MAX_ENTRIES, int MAX_CACHE_SIZE_MB, CacheStrategy cs, String cachePath, long MIN_FREE_SPACE, boolean DEBUG)
    {

        super(cache);
        this.DEBUG = DEBUG;
        this.MAX_CACHE_SIZE_MB = MAX_CACHE_SIZE_MB;
        if (DEBUG) System.out.println("setting CacheIndex this.MAX_CACHE_SIZE_MB = " + this.MAX_CACHE_SIZE_MB);


        reverseSymbolicLinks = (Map)Collections.synchronizedMap(new HashMap(MAX_ENTRIES+1));
        this.lru = cs.isLRU();
        this.random = cs.isRANDOM();
        this.fifo = cs.isFIFO();
        this.popular = cs.isPOPULAR();
        this.MAX_ENTRIES = MAX_ENTRIES;

        this.cachePath = cachePath;
        this.MIN_FREE_SPACE = MIN_FREE_SPACE;

        disk = new DiskSpace2(MIN_FREE_SPACE, cachePath, DEBUG);

        if (this.popular)
        {
            popularityIndexIS = new TreeMap();
            popularityIndexIS = (Map)Collections.synchronizedMap(popularityIndexIS);
            popularityIndexSI = new TreeMap();
            popularityIndexSI = (Map)Collections.synchronizedMap(popularityIndexSI);
            popularityIndexSIX = new TreeMap();
            popularityIndexSIX = (Map)Collections.synchronizedMap(popularityIndexSIX);
        }
    }


    protected synchronized boolean removeEldestEntry(Map.Entry eldest) 
    {
        /*
        boolean removeEntry = false;
        
        if (super.size() > MAX_ENTRIES)
        {
            removeEntry = true;
            String entry = (String)eldest.getValue();
            boolean success = (new File(entry)).delete();
            if (!success)
            {
                // Deletion failed
            }
        } else
        {
            removeEntry = false;
        }
        
        return removeEntry;
        */
        return false;
    }

    //if LRU, returns the oldest accessed
    //if FIFO, returns the oldest inserted 
    public synchronized String removeEldest() 
    {

        if (super.size() == 0)
        {

            if (DEBUG) System.out.println("CacheIndex: removeEldest()... size = 0, nothing to remove...");
            return null;
        }

        if (super.size() > MAX_ENTRIES)
        {
            //Map.Entry eldest = super.header;
            Iterator it=super.entrySet().iterator();
            Map.Entry entry = (Map.Entry)it.next();
            //String key = entry.getKey();
            String eldest = (String)entry.getValue();
            //            String eldest = (String)it.next();
            if (DEBUG) System.out.println("CacheIndex: removeEldest()... size > MAX_ENTRIES " + super.size() + " > " + MAX_ENTRIES + ", eldest entry " + eldest);

            if (DEBUG) System.out.println("removeEldest(): it.remove()...");
            it.remove();


            //boolean success = (new File(eldest)).delete();
            boolean success = removeFile(eldest);
            if (!success)
            {
                if (DEBUG) System.out.println("CacheIndex: removeEldest()... removal of " + eldest + " failed");
                // Deletion failed
            }
            else
            {

//<<<<<<< .mine
                if (DEBUG) System.out.println("CacheIndex: removeEldest()... removal of " + eldest + " succesful!");
            }
//=======
//                System.out.println("CacheIndex: removeEldest()... removal of " + eldest + " successful");
//>>>>>>> .r1220

            return eldest;

            //return (String)eldest.getKey();
        }
        else
        {
            if (DEBUG) System.out.println("CacheIndex: removeEldest()... size <= MAX_ENTRIES " + super.size() + " <= " + MAX_ENTRIES + ", nothing to remove");
            return null;
        }
    }

    public synchronized String[] removeEldest(double numMB) 
    {

        if (super.size() == 0)
        {

            if (DEBUG) System.out.println("CacheIndex: removeEldest()... size = 0, nothing to remove...");
            return null;
        }

        double numMBremoved = 0.0;

        //double numMBtoRemove = numMB - getAvailableDiskSpace2MB();
        double numMBtoRemove = numMB;
        List evictedEldest = new LinkedList();

        while ((numMBtoRemove > 0 && numMBremoved < numMBtoRemove && super.size() > 0) || super.size() > MAX_ENTRIES)
        {
            if (DEBUG) System.out.println("cache.getCacheContentsSizeMB() = " + getCacheContentsSizeMB());
            if (DEBUG) System.out.println("cache.getCacheContentsNum() = " + getCacheContentsNum());
            if (DEBUG) System.out.println("MAX_CACHE_SIZE_MB = " + MAX_CACHE_SIZE_MB);



            //if (numMB >= getAvailableDiskSpace2MB() || super.size() > MAX_ENTRIES)
            //{
            //Map.Entry eldest = super.header;
            Iterator it=super.entrySet().iterator();
            Map.Entry entry = (Map.Entry)it.next();
            //String key = entry.getKey();
            String eldest = (String)entry.getValue();
            //            String eldest = (String)it.next();
            if (DEBUG) System.out.println("CacheIndex: removeEldest()... size > MAX_ENTRIES " + super.size() + " > " + MAX_ENTRIES + ", eldest entry " + eldest);

            if (DEBUG) System.out.println("removeEldest(double): it.remove()...");
            it.remove();

            if (DEBUG) System.out.println("CacheIndex: reverseSymbolicLinks.get(" + eldest + ")...");
            String eldestLogicalName = (String)reverseSymbolicLinks.get(eldest);
            if (eldestLogicalName != null)
            {
                if (DEBUG) System.out.println("CacheIndex: reverseSymbolicLinks.get(" + eldest + ") = " + eldestLogicalName);
                evictedEldest.add(eldestLogicalName);
            }
            else
            {

                if (DEBUG) System.out.println("CacheIndex: reverseSymbolicLinks.get(" + eldest + ") = null");
                if (DEBUG) System.out.println("CacheIndex: printing reverseSymbolicLinks map");
                // For both the keys and values of a map
                //for (Iterator it2=reverseSymbolicLinks.entrySet().iterator(); it2.hasNext(); ) {
                //    Map.Entry entry2 = (Map.Entry)it2.next();
                //    Object key = entry2.getKey();
                //    Object value = entry2.getValue();
                //    if (DEBUG) System.out.println("CacheIndex: reverseSymbolicLinks: " + key + " ==> " + value);
                //}
            }


            //long numBytes = (new File(eldest)).length();
            long numBytes = getFileSize(eldest);
            //boolean success = (new File(eldest)).delete();
            boolean success = removeFile(eldest);
            if (!success)
            {
                if (DEBUG) System.out.println("CacheIndex: removeEldest()... removal of " + eldest + " failed");
                if (DEBUG) System.out.println("CacheIndex: 1 updating cache size: not needed for file " + eldest + " as it failed to be removed...");
                // Deletion failed
            }
            else
            {

                if (DEBUG) System.out.println("CacheIndex: removeEldest()... removal of " + eldest + " succesful!");
                numMBremoved += numBytes*1.0/(1024.0*1024.0);
                if (DEBUG) System.out.println("CacheIndex: 2 updating cache size: " + eldest + " -"+numBytes + " bytes");
                setCacheContentSize(numBytes,false);

            }

            //remove sym links
            String symLink = (String)reverseSymbolicLinks.remove(eldest);

            boolean remSymLink = removeFile(symLink);

            /*
            if (symLink != null)
            {
                symLink = cachePath + "/scratch/" + symLink;
                boolean remSymLink = (new File(symLink)).delete();
                if (DEBUG) System.out.println("Removing symbolic link " + symLink + " for file " + eldest + ": " + remSymLink);
                

            } */

            //return eldest;

            //return (String)eldest.getKey();
            //} else
            //{
            //    System.out.println("CacheIndex: removeEldest()... size <= MAX_ENTRIES " + super.size() + " <= " + MAX_ENTRIES + ", nothing to remove");
            //    return null;
            //}
        }

        if (DEBUG) System.out.println("cache.getCacheContentsSizeMB() = " + getCacheContentsSizeMB());
        if (DEBUG) System.out.println("cache.getCacheContentsNum() = " + getCacheContentsNum());
        if (DEBUG) System.out.println("MAX_CACHE_SIZE_MB = " + MAX_CACHE_SIZE_MB);


        if (evictedEldest.size() > 0)
        {
            // Create an array containing the elements in a list
            //Object[] objectArray = evictedEldest.toArray();
            if (DEBUG) System.out.println("Evicted " + evictedEldest.size() + " cache entries!");
            return(String[])evictedEldest.toArray(new String[evictedEldest.size()]);

            //  return (String[])evictedEldest.toArray();
        }
        else
        {
            if (DEBUG) System.out.println("Evicted 0 cache entries!");

            return null;
        }
    }


    public long getFileSize(String f)
    {
        try
        {




            if (f != null)
            {

                String f2 = null;
                if (f.startsWith("/"))
                {
                    f2 = f;
                    //isSymLink = false;

                }
                else
                {

                    f2 = cachePath + "/scratch/" + f;
                    //isSymLink = true;
                }


                //String f2 = cachePath + "/scratch/" + f;

                if (DEBUG) System.out.println("getFileSize(): " + f + " " + f2);
                return((new File(f2)).length());



            }
            else
                return 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }

    }

    public boolean removeFile(String f)
    {
        try
        {

            boolean isSymLink = false;
            boolean remReal = true;
            boolean remSym = true;


            if (f != null)
            {
                String f2 = null;
                if (f.startsWith("/"))
                {
                    f2 = f;
                    isSymLink = false;

                }
                else
                {
                    //removeFile("/dev/shm/falkon-diffusion/"+logicalName);
                    //removeFile(outFilename);


                    f2 = cachePath + "/scratch/" + f;
                    isSymLink = true;
                }
                if (DEBUG) System.out.println("removeFile(): logical " + f + " real " + f2);

                    remSym = ((new File(f2)).delete());

                    if (remSym)
                {
                    if (DEBUG) System.out.println("Succesful in removing file: " + f2);
                    return true;
                }
                else
                {
                    if (DEBUG) System.out.println("Failed to remove file: " + f2);
                    return false;
                }
                //getCanonicalPath();



            }
            else
            {
                if (DEBUG) System.out.println("Failed to remove file: null");
                return false;
            }
        }
        catch (Exception e)
        {
            if (DEBUG) System.out.println("Failed to remove file: exception " + e.getMessage());
            e.printStackTrace();
            return false;
        }


    }


    public boolean removeFileOld(String f)
    {
        try
        {

            boolean isSymLink = false;
            boolean remReal = true;
            boolean remSym = true;


            if (f != null)
            {
                String f2 = null;
                if (f.startsWith("/"))
                {
                    f2 = f;
                    isSymLink = false;

                }
                else
                {

                    f2 = cachePath + "/scratch/" + f;
                    isSymLink = true;
                }
                String fr = (new File(f2)).getCanonicalPath();
                if (DEBUG) System.out.println("removeFile(): logical " + f + " symlink " + f2 + " real " + fr);

                if (isSymLink)
                {

                    remSym = ((new File(f2)).delete());
                }

                remReal = ((new File(fr)).delete());

                if (remReal && remSym)
                {
                    if (DEBUG) System.out.println("Succesful in removing all files: real " + fr + " " + remReal + " sym " + f2 + " " + remSym );
                    return true;
                }
                else
                {
                    if (DEBUG) System.out.println("Failed to remove some files: real " + fr + " " + remReal + " sym " + f2 + " " + remSym );
                    return false;
                }
                //getCanonicalPath();



            }
            else
                return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }


    }

    public boolean fileExists(String f)
    {

        try
        {




            if (f != null)
            {

                String f2 = null;
                if (f.startsWith("/"))
                {
                    f2 = f;
                    //isSymLink = false;

                }
                else
                {

                    f2 = cachePath + "/scratch/" + f;
                    //isSymLink = true;
                }


                //String f2 = cachePath + "/scratch/" + f;

                if (DEBUG) System.out.println("fileExists(): " + f + " " + f2);
                return((new File(f2)).exists());



            }
            else
                return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }



    }



    //removes a random entry
    public synchronized String removeRandom() 
    {


        if (super.size() == 0)
        {

            if (DEBUG) System.out.println("CacheIndex: removeRandom()... size = 0, nothing to remove...");
            return null;
        }

        if (super.size() > MAX_ENTRIES)
        {
            int index = randInt.nextInt(super.size() - 1);
            int i = 0;
            for (Iterator it=super.entrySet().iterator(); it.hasNext(); i++)
            {

                if (i==index)
                {
                    Map.Entry entry = (Map.Entry)it.next();
                    String randomElement = (String)entry.getValue();
                    //            String eldest = (String)it.next();
                    if (DEBUG) System.out.println("CacheIndex: removeRandom()... size > MAX_ENTRIES " + super.size() + " > " + MAX_ENTRIES + ", random entry " + randomElement);

                    if (DEBUG) System.out.println("removeRandom(): it.remove()...");
                    it.remove();


                    //boolean success = (new File(randomElement)).delete();
                    boolean success = removeFile(randomElement);
                    if (!success)
                    {
                        if (DEBUG) System.out.println("CacheIndex: removeRandom()... removal of " + randomElement + " failed");
                        // Deletion failed
                    }
                    else
                    {

//<<<<<<< .mine
                        if (DEBUG) System.out.println("CacheIndex: removeRandom()... removal of " + randomElement + " succesful!");
                    }
//=======
//                        System.out.println("CacheIndex: removeRandom()... removal of " + randomElement + " succesful");
//>>>>>>> .r1220

                    return randomElement;
                }
                else
                {
                    it.next();

                }
            }

        }
        else
        {
            if (DEBUG) System.out.println("CacheIndex: removeRandom()... size <= MAX_ENTRIES " + super.size() + " <= " + MAX_ENTRIES + ", nothing to remove");
            return null;
        }

        return null;
    }



    //removes a random entry
    public synchronized String removeLeastPopular(String newKey) 
    {


        if (super.size() == 0)
        {

            if (DEBUG) System.out.println("CacheIndex: removePopular()... size = 0, nothing to remove...");
            return null;
        }

        if (super.size() > MAX_ENTRIES)
        {
            //insert POPULAR specific code here...
            String evictedEntry = null;

            Iterator it0=popularityIndexIS.entrySet().iterator(); 
            Integer firstKey = null;
            Set firstValue = null;

            if (it0.hasNext())
            {
                Map.Entry entry = (Map.Entry)it0.next();
                firstKey = (Integer)entry.getKey();
                firstValue = (HashSet)entry.getValue();

            }
            else
            {
                //this should never happen
                return null;
            }


            //Integer firstKey = (Integer)popularityIndexIS.firstKey();
            //        Set firstValue = (HashSet)popularityIndexIS.get(firstKey);
            Iterator it=firstValue.iterator(); 
            //this should always set evictedEntry to something to evict
            while ((evictedEntry == null || evictedEntry == newKey) && it.hasNext())
            {
                evictedEntry = (String)it.next();
            }
            if (evictedEntry != null)
            {
                if (DEBUG) System.out.println("removeLeastPopular(String): it.remove()...");

                it.remove();
                if (firstValue.isEmpty())
                {
                    popularityIndexIS.remove(firstKey);
                }
                popularityIndexSI.remove(evictedEntry);
                popularityIndexSIX.put(evictedEntry, firstKey);

                if (DEBUG) System.out.println("removeLeastPopular(String): super.remove()...");
                super.remove(evictedEntry);

                return evictedEntry;
            }
            else
                return null;
        }
        else
        {
            if (DEBUG) System.out.println("CacheIndex: removePopular()... size <= MAX_ENTRIES " + super.size() + " <= " + MAX_ENTRIES + ", nothing to remove");
            return null;
        }

        //return null;
    }


    private synchronized String getEldest() 
    {
        if (super.size() == 0)
        {
            return null;
        }

        if ((super.size() + 1) > MAX_ENTRIES)
        {
            //Map.Entry eldest = super.header;
            Iterator it=super.keySet().iterator();
            return(String)it.next();

            //return (String)eldest.getKey();
        }

        return null;
    }  


    public synchronized int size()
    {
        return super.size();
    }

    public synchronized String put(String key, String value, long fileLength)
    {
        //String evictedKey = getEldest();
        super.put(key, value);
        reverseSymbolicLinks.put(value, key);

        //add size to cache size
        if (DEBUG) System.out.println("CacheIndex: 3 updating cache size: " + key + " " + value + " "+fileLength + " bytes");
        setCacheContentSize(fileLength, true);

        if (DEBUG) System.out.println("cache.getCacheContentsSizeMB() = " + getCacheContentsSizeMB());
        if (DEBUG) System.out.println("cache.getCacheContentsNum() = " + getCacheContentsNum());
        if (DEBUG) System.out.println("MAX_CACHE_SIZE_MB = " + MAX_CACHE_SIZE_MB);


        //need to update the other remove functions with the size, isntead of num entries...
        /*
        String evictedKey = null;
        if (this.fifo || this.lru)
        {
            evictedKey = removeEldest();

        } else if (this.random)
        {

            evictedKey = removeRandom();
        } else if (this.popular)
        {
            //updatePopularity(value);
            updatePopularity(key);
            //evictedKey = removeLeastPopular(value);
            evictedKey = removeLeastPopular(key);
            removePopularity(evictedKey);
        } else
        {
            //if none are set, which should never happen, use fifo or lru
            evictedKey = removeEldest();
        }

        System.out.println("CacheIndex: put(" + key + "," + value + ")... evicted " + evictedKey);
        

        return evictedKey;
        */
        return null;

    }

    public synchronized void removePopularity(String evictedKey)
    {
        if (evictedKey != null && popularityIndexSI.containsKey(evictedKey))
        {

            Integer count = (Integer)popularityIndexSI.get(evictedKey);
            Set set = (HashSet)popularityIndexIS.get(count);
            set.remove(evictedKey);
            if (set.isEmpty())
            {
                popularityIndexIS.remove(count);

            }
            else
                popularityIndexIS.put(count, set);
            popularityIndexSI.remove(evictedKey);
            //store popularity count for later use
            popularityIndexSIX.put(evictedKey, count);

        }

    }

    public synchronized void updatePopularity(String key)
    {
        if (popularityIndexSI.containsKey(key))
        {

            Integer count = (Integer)popularityIndexSI.get(key);
            Set set = (HashSet)popularityIndexIS.get(count);
            set.remove(key);
            if (set.isEmpty())
            {
                popularityIndexIS.remove(count);

            }
            else
                popularityIndexIS.put(count, set);

            count = new Integer(count.intValue() + 1);
            popularityIndexSI.put(key, count);

            Set set2 = (HashSet)popularityIndexIS.get(count);
            if (set2 == null)
            {
                set2 = new HashSet();
            }
            set2.add(key);
            popularityIndexIS.put(count, set2);


        }
        //take the count info from the evicted index if it exists
        else
        {
            Integer count = null;
            if (popularityIndexSIX.containsKey(key))
            {
                //count = (Integer)popularityIndexSIX.get(key)  + (new Integer(1));
                Integer tCount = (Integer)popularityIndexSIX.get(key);
                count = new Integer(tCount.intValue() + 1);

                popularityIndexSIX.remove(key);
            }
            else
            {
                count = new Integer(1);
            }

            Set set = (HashSet)popularityIndexIS.get(count);
            if (set == null)
            {
                set = new HashSet();

            }
            set.add(key);
            popularityIndexIS.put(count, set);
            popularityIndexSI.put(key, count);
        }
    }

    public synchronized String get(String key)
    {

        if (!key.contentEquals(new StringBuffer("null")))
        {

            String value = (String)super.get(key);
            if (DEBUG) System.out.println("CacheIndex: get(" + key + ") ==> " + value);

            //update popularity
            if (this.popular && value != null)
            {
                updatePopularity(key);

            }
            return value;
        }
        else
            return null;
    }

    public synchronized boolean containsKey(String key)
    {
        return super.containsKey(key);
    }

    public synchronized void printCache()
    {
        // For both the keys and values of a map
        System.out.println("=========================================");
        System.out.println("Printing cache of size " + super.size());
        int i=0;
        for (Iterator it=super.entrySet().iterator(); it.hasNext(); i++)
        {
            Map.Entry entry = (Map.Entry)it.next();
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();
            System.out.println(i + ": " + key + " ==> " + value);
        }
        System.out.println("");

        if (this.popular)
        {
            System.out.println("Printing popularity information of size " + popularityIndexSI.size());
            for (Iterator it=popularityIndexSI.entrySet().iterator(); it.hasNext(); i++)
            {
                Map.Entry entry = (Map.Entry)it.next();
                String key = (String)entry.getKey();
                Integer value = (Integer)entry.getValue();
                System.out.println(i + ": " + key + " ==> " + value);
            }
            System.out.println("");
            System.out.println("Printing evicted popularity information of size " + popularityIndexSIX.size());
            for (Iterator it=popularityIndexSIX.entrySet().iterator(); it.hasNext(); i++)
            {
                Map.Entry entry = (Map.Entry)it.next();
                String key = (String)entry.getKey();
                Integer value = (Integer)entry.getValue();
                System.out.println(i + ": " + key + " ==> " + value);
            }
            System.out.println("");
        }
        System.out.println("-------------------------------------");
        System.out.println("");
    }


    private void writeObject(ObjectOutputStream out) throws IOException 
    {
        //if (nums.length > size) resize(size);  // Compact the array.
        out.defaultWriteObject();              // Then write it out normally.
    }
    /** Compute the transient size field after deserializing the array. */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();                // Read the array normally.
        //size = nums.length;                    // Restore the transient field.
    }

    public static void store(CacheIndex ci)
    {
        FileOutputStream fos = null;
        File file = null;


        try
        {
            file = new File("CacheIndex.obj");
            fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ci);
            oos.close();
            fos.close();
        }
        catch (Exception e)
        {
            System.out.println("Error in store(): " + e);
            e.printStackTrace();
        }

    }

    public static CacheIndex load()
    {
        CacheIndex ci = null;
        try
        {
            File file = new File("CacheIndex.obj");

            if (file.exists())
            {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                ci = (CacheIndex) in.readObject();
                in.close();
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in load(): " + e);
            e.printStackTrace();
        }
        return ci;
    }


    public static void main(String args[])
    {
        CacheStrategy cs = new CacheStrategy();
        //cs.setLRU();
        //cs.setFIFO();
        //logic isn't quite right :(
        cs.setPOPULAR();
        //cs.setRANDOM();

        CacheIndex cache = load();
        if (cache == null)
        {
            cache = new CacheIndex(5, .75f, cs, null, 0, true);
        }

        cache.printCache();

        int numPuts = 10;
        String keys[] = new String[numPuts];
        String values[] = new String[numPuts];
        for (int i=0;i<numPuts;i++)
        {
            keys[i] = new String("key" + i);
            values[i] = new String("value" + i);
        }

        for (int i=0;i<3;i++)
        {

            cache.put(keys[i], values[i],0);
        }

        cache.printCache();

        for (int i=3-1;i>=0;i--)
        {

            cache.get(keys[i]);
        }

        cache.printCache();

        for (int i=3;i<6;i++)
        {

            cache.put(keys[i], values[i], 0);
        }

        cache.printCache();

        for (int i=6;i<9;i++)
        {

            cache.put(keys[i], values[i], 0);
        }

        cache.printCache();

        store(cache);



    } 

}     
