package org.globus.GenericPortal.services.core.WS.impl;

public class CacheHitMiss
{
    public int localHit;
    public int globalHit;
    public int miss;

    public int localHitKB;
    public int globalHitKB;
    public int missKB;

    //public String globalHitMachID;


    public CacheHitMiss()
    {
        localHit = 0;
        globalHit = 0;
        miss = 0;

        localHitKB = 0;

        globalHitKB = 0;
        missKB = 0;
        //globalHitMachID = null;

    }


}
