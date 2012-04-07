package org.globus.GenericPortal.common;

import java.util.*;
import java.io.*;
import java.lang.*;
import org.globus.GenericPortal.common.*;

public class CacheStrategy implements Serializable
{
    private boolean LRU;
    private boolean FIFO;
    private boolean RANDOM;
    private boolean POPULAR;

    public CacheStrategy()
    {
        //set to default strategy, LRU
        this.LRU = true;
        this.FIFO = false;
        this.RANDOM = false;
        this.POPULAR = false;

    }

    public void setLRU()
    {
        this.LRU = true;
        this.FIFO = false;
        this.RANDOM = false;
        this.POPULAR = false;
    }

    public void setFIFO()
    {
        this.LRU = false;
        this.FIFO = true;
        this.RANDOM = false;
        this.POPULAR = false;
    }

    public void setRANDOM()
    {
        this.LRU = false;
        this.FIFO = false;
        this.RANDOM = true;
        this.POPULAR = false;
    }

    public void setPOPULAR()
    {
        this.LRU = false;
        this.FIFO = false;
        this.RANDOM = false;
        this.POPULAR = true;
    }

    public boolean isLRU()
    {
        return this.LRU;
    }
    public boolean isFIFO()
    {
        return this.FIFO;
    }
    public boolean isRANDOM()
    {
        return this.RANDOM;
    }
    public boolean isPOPULAR()
    {
        return this.POPULAR;
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

}
