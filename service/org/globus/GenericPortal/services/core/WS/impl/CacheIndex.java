package org.globus.GenericPortal.services.core.WS.impl;

import java.util.*;

public class CacheIndex
{
    private Map cacheIndex = null;

    public CacheIndex(int initialCapacity, float loadFactor)
    {
        cacheIndex = Collections.synchronizedMap(new HashMap(initialCapacity, loadFactor));

    }

    public int size()
    {
        return cacheIndex.size();

    }

    public boolean insert(String uid, String url, String cacheLocation)
    {
        IndexElement ie = (IndexElement)cacheIndex.get(uid);
        if (ie == null)
        {
            ie = new IndexElement(url);
            ie.updateCache(cacheLocation);
            cacheIndex.put(uid, ie);
        }
        else
        {
            ie.updateGlobal(url);
            ie.updateCache(cacheLocation);
        }

        return true;
    }


    public boolean remove(String uid, String cacheLocation)
    {
        IndexElement ie = (IndexElement)cacheIndex.get(uid);
        if (ie == null)
        {
            return true;
        }
        else
        {
            ie.removeCache(cacheLocation);
            if (ie.isEmpty())
            {
                cacheIndex.remove(uid);
            }
        }

        return true;
    }

    public boolean exists(String uid)
    {
        return cacheIndex.containsKey(uid);
    }

    public IndexElement remove(String uid)
    {
        return(IndexElement)cacheIndex.remove(uid);
    }

    public IndexElement getIndexElement(String uid)
    {
        return(IndexElement)cacheIndex.get(uid);
    }

    public String getIndexElementGlobal(String uid)
    {

        IndexElement ie = (IndexElement)cacheIndex.get(uid);
        if (ie != null)
        {
            return ie.getGlobal();
        }
        else
            return null;
    }


    public Set getIndexElementLocal(String uid)
    {

        IndexElement ie = (IndexElement)cacheIndex.get(uid);
        if (ie != null)
        {
            return ie.getLocal();
        }
        else
            return null;
    }


    public String[] getIndexElementLocalArray(String uid)
    {

        IndexElement ie = (IndexElement)cacheIndex.get(uid);
        if (ie != null)
        {
            Set localSet = ie.getLocal();
            // Create an array containing the elements in a set
            Object[] objectArray = localSet.toArray();
            String[] array = (String[])localSet.toArray(new String[localSet.size()]);
            return array;
        }
        else
            return null;
    }

}

class IndexElement
{
    private String global;
    private Set local;
    //private boolean persist;

    public IndexElement(String g)
    {
        if (g == null)
        {
            global = null;
            //      persist = false;
        }
        else
        {
            global = new String(g);
            //      persist = true;
        }

        local = Collections.synchronizedSortedSet(new TreeSet());
    }

    /*
    public void setPersist(boolean sp)
    {
        persist = sp;
    }

    public boolean getPersist()
    {
        return persist;
    }
      */
    public String getGlobal()
    {
        return global;
    }

    public Set getLocal()
    {
        return local;
    }

    public String getLocalElement()
    {
        Iterator it=local.iterator();
        if (it.hasNext())
        {
            return(String)it.next();
        }

        return null;
    }

    public boolean updateGlobal(String URL)
    {
        if (URL != null)
        {
            global = new String(URL);
        }
        return true;


    }



    public boolean updateCache(String cacheLocation)
    {
        if (cacheLocation != null)
        {
            return local.add(cacheLocation);
        }
        else
            return true;


    }

    public boolean removeCache(String cacheLocation)
    {
        return local.remove(cacheLocation);
    }

    public int size()
    {
        return local.size();
    }

    public boolean isEmpty()
    {
        if (local.isEmpty() && global == null)
        {
            return true;
        }
        else
            return false;
    }

}
