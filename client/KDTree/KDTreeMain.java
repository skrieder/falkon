package KDTree;

import edu.wlu.cs.levy.CG.KDTree;
import java.io.*;
import java.lang.*;
import java.util.*;

import KDTree.*;
import org.globus.GenericPortal.common.*;


public class KDTreeMain
{


    class SDSS_Object
    {
        String name;
        double ra;
        double dec;
        char band;
    }

    public KDTreeMain()
    {

    }


    public KDTreeIndex sdssIndex = null;

    public boolean initSDSSIndex(String SDSS_INDEX)
    {
        String fName = SDSS_INDEX;
        System.out.println("Loading index from '" + fName + "' this might take some time, depending on the size of the index... expect about 10000~20000 entries per second...");
        try
        {

            sdssIndex = new KDTreeIndex(fName);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Failed to loaded index from file '" + fName + "'...  1 exiting...");
            //indexFailed = true;
            return false;//System.exit(-1);
        }


        if (sdssIndex.isValid)
        {

            System.out.println("Index loaded successfully");
            //indexValid = true;
            return true;
        }
        else
        {

            System.out.println("Failed to loaded index from file '" + fName + "'...  2 exiting...");
            //indexFailed = true;
            //System.exit(-1);
            return false;
        }

        //return false;

    }

    public Set files = Collections.synchronizedSet(new HashSet());
    //char bands[] = {'u', 'g', 'r', 'i', 'z'};
    char bands[] = {'u'};


    public void getCPcommandsAllBands(String args[])
    {

        if (args.length < 2)
        {
            System.out.println("no index file name and query file anme specified...");
            System.exit(-1);

        }
        if (initSDSSIndex(args[0]))
        {

        }
        else
        {
            System.out.println("loading index failed 1...");
            System.exit(-1);
        }

        try
        {
            BufferedReader in = new BufferedReader(new FileReader(args[1]));
            String str;
            int linesRead = 0;
            while ((str = in.readLine()) != null)
            {
                linesRead++;
                if (linesRead%1000 == 0)
                {
                    System.out.print(".");
                }
                String tokens[] = str.split(" ");
                if (tokens.length == 3)
                {
                    try
                    {
                        //SDSS_Object obj = new SDSS_Object();
                        double ra = Double.parseDouble(tokens[0].trim());
                        double dec = Double.parseDouble(tokens[1].trim());
                        char band = (tokens[2].trim()).charAt(0);
                        Entry entry = sdssIndex.lookupQuerySkyCal(ra, dec, band);
                        //obj.name = entry.image;

                        files.add(entry.image);



                    }
                    catch (Exception e)
                    {
                        //e.printStackTrace();
                        System.out.print("x");
                    }


                }
                else if (tokens.length == 2)
                {
                    try
                    {
                        //SDSS_Object obj = new SDSS_Object();
                        double ra = Double.parseDouble(tokens[0].trim());
                        double dec = Double.parseDouble(tokens[1].trim());
                        for (int i=0;i<bands.length;i++)
                        {
                            char band = bands[i];
                            Entry entry = sdssIndex.lookupQuerySkyCal(ra, dec, band);
                            //obj.name = entry.image;

                            files.add(entry.image);
                        }


                    }
                    catch (Exception e)
                    {
                        //e.printStackTrace();
                        System.out.print("x");
                    }


                }

                else
                {
                    System.out.print("X");
                }

                //process(str);
            }
            in.close();

            System.out.println("\nNumber of unique files found: " + files.size());

            // For a set or list
            for (Iterator it=files.iterator(); it.hasNext(); )
            {

                String s = (String)it.next();
                System.out.println("cp -u --parents " + s + " dst");
                //cp --parents
                //System.out.println(s);
            }


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }





    }


    public void getCPcommandsOneBand(String args[])
    {
        if (args.length < 2)
        {
            System.out.println("no index file name and query file anme specified...");
            System.exit(-1);

        }
        if (initSDSSIndex(args[0]))
        {

        }
        else
        {
            System.out.println("loading index failed 2...");
            System.exit(-1);
        }

        try
        {
            BufferedReader in = new BufferedReader(new FileReader(args[1]));
            String str;
            int linesRead = 0;
            while ((str = in.readLine()) != null)
            {
                linesRead++;
                if (linesRead%1000 == 0)
                {
                    System.out.print(".");
                }
                String tokens[] = str.split(" ");
                if (tokens.length == 3)
                {
                    try
                    {
                        //SDSS_Object obj = new SDSS_Object();
                        double ra = Double.parseDouble(tokens[0].trim());
                        double dec = Double.parseDouble(tokens[1].trim());
                        char band = (tokens[2].trim()).charAt(0);
                        Entry entry = sdssIndex.lookupQuerySkyCal(ra, dec, band);
                        //obj.name = entry.image;

                        files.add(entry.image);



                    }
                    catch (Exception e)
                    {
                        //e.printStackTrace();
                        System.out.print("x");
                    }


                }
                else if (tokens.length == 2)
                {
                    try
                    {
                        //SDSS_Object obj = new SDSS_Object();
                        double ra = Double.parseDouble(tokens[0].trim());
                        double dec = Double.parseDouble(tokens[1].trim());
                        for (int i=0;i<bands.length;i++)
                        {
                            char band = bands[i];
                            Entry entry = sdssIndex.lookupQuerySkyCal(ra, dec, band);
                            //obj.name = entry.image;

                            files.add(entry.image);
                        }


                    }
                    catch (Exception e)
                    {
                        //e.printStackTrace();
                        System.out.print("x");
                    }


                }

                else
                {
                    System.out.print("X");
                }

                //process(str);
            }
            in.close();

            System.out.println("\nNumber of unique files found: " + files.size());

            // For a set or list
            for (Iterator it=files.iterator(); it.hasNext(); )
            {

                String s = (String)it.next();
                System.out.println("cp -u --parents " + s + " dst");
                //cp --parents
                //System.out.println(s);
            }


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }





    }


    public Map filesLocality = Collections.synchronizedMap(new HashMap());

    private int linesRead = 0;
    public synchronized void incLinesRead()
    {
        linesRead++;


    }

    public int getLinesRead()
    {
        return linesRead;


    }

    public void getRaDecLocality(String args[])
    {
        try
        {

            //char bands[] = {'u'};
            if (args.length != 4)
            {
                System.out.println("args: <index_file_name> <query_file_name> <locality> <num_objects>");
                System.exit(-1);

            }
            if (initSDSSIndex(args[0]))
            {

            }
            else
            {
                System.out.println("loading index failed 3...");
                System.exit(-1);
            }

            //try
            //{

            int locality = Integer.parseInt(args[2]);
            int numObjects = Integer.parseInt(args[3]);
            BufferedReader in = new BufferedReader(new FileReader(args[1]));
            String str;


            // Create the work queue
            LocalWorkMainQueue queue = new LocalWorkMainQueue();

            // Create a set of worker threads
            final int numWorkers = 8;
            LocalWorkerMain[] workers = new LocalWorkerMain[numWorkers];
            for (int i=0; i<workers.length; i++)
            {
                workers[i] = new LocalWorkerMain(queue, this);
                workers[i].start();
            }

            // Add some work to the queue; block if the queue is full.
            // Note that null cannot be added to a blocking queue.
            //for (int i=0; i<100; i++) {
            //    queue.addWork(i);
            //}



            //while ((str = in.readLine()) != null && numEntries < MAX_ENTRIES)
            while ((str = in.readLine()) != null)
            {
                queue.addWork(str);
                //Entry entry = processIndex(str);

            }
            in.close();
            //this.isValid = true;


            // Add special end-of-stream markers to terminate the workers
            for (int i=0; i<workers.length; i++)
            {
                queue.addWork(LocalWorkerMain.NO_MORE_WORK);
            }


            for (int i=0; i<workers.length; i++)
                workers[i].join();


            //System.out.println("\nNumber of unique files found: " + files.size());

            List raDecList = new LinkedList();
            StatCalc stat = new StatCalc();

            if (locality == 0 || locality == 1)
            {

                // For values of a map
                for (Iterator it=filesLocality.values().iterator(); it.hasNext(); )
                {
                    List tList = (List)it.next();


                    if (locality == 0)
                    {
                        raDecList.addAll(tList);
                        stat.enter(tList.size()*1.0);

                    }

                    else if (locality == 1)
                    {
                        //if (tList.size() >= locality)
                        //{

                        raDecList.add(tList.get(0));
                        stat.enter(1.0);
                        //}
                    }

                    if (raDecList.size() > numObjects)
                    {
                        break;
                    }

                    /*
                    else
                    {
    
    
                        
                    } */



                    //System.out.println("cp -u --parents " + s + " dst");
                    //cp --parents
                    //System.out.println(s);
                }
            }
            else// if (locality == 1)
            {
                //curLocality = locality;

                for (int curLocality = locality; curLocality >= 1; curLocality--)
                {
                    //if (curLocality < locality)
                    //{
                    //    break;
                    //}


                    for (Iterator it=filesLocality.values().iterator(); it.hasNext(); )
                    {
                        List tList = (List)it.next();


                        if (tList.size() >= curLocality)
                        {

                            
                            if (stat.getMean() < locality || raDecList.size() > numObjects)
                            {
                                break;


                            }
                            //raDecList.addAll(tList.subList(0, curLocality));
                            //stat.enter(curLocality*1.0);
                            raDecList.addAll(tList);
                            stat.enter(tList.size()*1.0);
                        }

                    }
                    if (stat.getMean() < locality || raDecList.size() > numObjects)
                    {
                        break;
                    }

                }
            }



            Collections.shuffle(raDecList);
            for (Iterator it=raDecList.iterator(); it.hasNext(); )
            {
                Entry entry = (Entry)it.next();
                //for (int i=0;i<bands.length;i++)
                //{
                if (entry != null && entry.coord != null)
                {
                
                System.out.println(entry.coord[0] + " " + entry.coord[1] + " " + entry.band);
                }
                else
                {
                    //System.out.println("there were some null entries");

                }


            }

            System.out.println("Workload stats: #objects " + raDecList.size() +" #uniqueFiles " + stat.getCount() + " locality " + stat.getMean());

            //raDecList


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }





    }

    public void getRaDecLocalityCompute(String args[])
    {
        try
        {

            //char bands[] = {'u'};
            if (args.length != 4)
            {
                System.out.println("args: <index_file_name> <query_file_name> <locality> <num_objects>");
                System.exit(-1);

            }
            if (initSDSSIndex(args[0]))
            {

            }
            else
            {
                System.out.println("loading index failed 3...");
                System.exit(-1);
            }

            //try
            //{

            int locality = Integer.parseInt(args[2]);
            int numObjects = Integer.parseInt(args[3]);
            BufferedReader in = new BufferedReader(new FileReader(args[1]));
            String str;


            // Create the work queue
            LocalWorkMainQueue queue = new LocalWorkMainQueue();

            // Create a set of worker threads
            final int numWorkers = 8;
            LocalWorkerMain[] workers = new LocalWorkerMain[numWorkers];
            for (int i=0; i<workers.length; i++)
            {
                workers[i] = new LocalWorkerMain(queue, this);
                workers[i].start();
            }

            // Add some work to the queue; block if the queue is full.
            // Note that null cannot be added to a blocking queue.
            //for (int i=0; i<100; i++) {
            //    queue.addWork(i);
            //}



            //while ((str = in.readLine()) != null && numEntries < MAX_ENTRIES)
            while ((str = in.readLine()) != null && getLinesRead() < numObjects)
            {
                queue.addWork(str);
                //numLines
                //Entry entry = processIndex(str);

            }
            in.close();
            //this.isValid = true;


            // Add special end-of-stream markers to terminate the workers
            for (int i=0; i<workers.length; i++)
            {
                queue.addWork(LocalWorkerMain.NO_MORE_WORK);
            }


            for (int i=0; i<workers.length; i++)
                workers[i].join();


            //System.out.println("\nNumber of unique files found: " + files.size());

            List raDecList = new LinkedList();
            StatCalc stat = new StatCalc();

            if (locality == 0 || locality == 1)
            {

                // For values of a map
                for (Iterator it=filesLocality.values().iterator(); it.hasNext(); )
                {
                    List tList = (List)it.next();


                    if (locality == 0)
                    {
                        raDecList.addAll(tList);
                        stat.enter(tList.size()*1.0);

                    }

                    else if (locality == 1)
                    {
                        //if (tList.size() >= locality)
                        //{

                        raDecList.add(tList.get(0));
                        stat.enter(1.0);
                        //}
                    }

                    if (raDecList.size() > numObjects)
                    {
                        break;
                    }

                    /*
                    else
                    {



                    } */



                    //System.out.println("cp -u --parents " + s + " dst");
                    //cp --parents
                    //System.out.println(s);
                }
            }
            else// if (locality == 1)
            {
                //curLocality = locality;

                for (int curLocality = locality; curLocality >= 1; curLocality--)
                {


                    for (Iterator it=filesLocality.values().iterator(); it.hasNext(); )
                    {
                        List tList = (List)it.next();


                        if (tList.size() >= curLocality)
                        {

                            //raDecList.addAll(tList.subList(0, locality));
                            if (stat.getMean() < locality || raDecList.size() > numObjects)
                            {
                                break;


                            }

                            raDecList.addAll(tList);
                            stat.enter(tList.size()*1.0);
                        }

                    }
                    if (stat.getMean() < locality || raDecList.size() > numObjects)
                    {
                        break;
                    }

                }
            }



            /*
            Collections.shuffle(raDecList);
            for (Iterator it=raDecList.iterator(); it.hasNext(); )
            {
                Entry entry = (Entry)it.next();
                //for (int i=0;i<bands.length;i++)
                //{
                if (entry != null && entry.coord != null)
                {

                System.out.println(entry.coord[0] + " " + entry.coord[1] + " " + entry.band);
                }
                else
                {
                    //System.out.println("there were some null entries");

                }


            }
              */
            System.out.println("Workload stats: #objects " + raDecList.size() +" #uniqueFiles " + stat.getCount() + " locality " + stat.getMean());

            //raDecList


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }





    }




    public static void main(String args[])
    {
        KDTreeMain kdm = new KDTreeMain();
        if (args.length < 1)
        {
            System.out.println("must at least specify the test to perform: <getRaDecLocality> || <getRaDecLocalityCompute> || <getCPcommandsAllBands> || <getCPcommandsOneBand>");
            System.exit(-1);
        }

        if (args[0].startsWith("getRaDecLocalityCompute"))
        {

            String tArgs[] = new String[args.length - 1];
            for (int i=0;i<tArgs.length;i++)
            {
                tArgs[i] = args[i+1];
            }
            kdm.getRaDecLocalityCompute(tArgs);

        }
        else if (args[0].startsWith("getRaDecLocality"))
        {
            String tArgs[] = new String[args.length - 1];
            for (int i=0;i<tArgs.length;i++)
            {
                tArgs[i] = args[i+1];
            }
            kdm.getRaDecLocality(tArgs);
        }
        else if (args[0].startsWith("getCPcommandsAllBands"))
        {

            String tArgs[] = new String[args.length - 1];
            for (int i=0;i<tArgs.length;i++)
            {
                tArgs[i] = args[i+1];
            }
            kdm.getCPcommandsAllBands(tArgs);

        }
        else if (args[0].startsWith("getCPcommandsOneBand"))
        {

            String tArgs[] = new String[args.length - 1];
            for (int i=0;i<tArgs.length;i++)
            {
                tArgs[i] = args[i+1];
            }
            kdm.getCPcommandsOneBand(tArgs);

        }
        else
        {
            System.out.println("must one of the following tests to perform: <getRaDecLocality> || <getRaDecLocalityCompute> || <getCPcommandsAllBands> || <getCPcommandsOneBand>");
            System.exit(-1);
        }

        
        //
        
        //
        //


    }
}


class LocalWorkMainQueue {
        LinkedList queue = new LinkedList();
    
        // Add work to the work queue
        public synchronized void addWork(Object o) {
            queue.addLast(o);
            notify();
        }
    
        // Retrieve work from the work queue; block if the queue is empty
        public synchronized Object getWork() throws InterruptedException {
            while (queue.isEmpty()) {
                wait();
            }
            return queue.removeFirst();
        }
    }

class LocalWorkerMain extends Thread {
        // Special end-of-stream marker. If a worker retrieves
        // an Integer that equals this marker, the worker will terminate.
        static final Object NO_MORE_WORK = new Object();
    
        LocalWorkMainQueue q;
        KDTreeMain kd;
    
        LocalWorkerMain(LocalWorkMainQueue q, KDTreeMain kd) {
            this.q = q;
            this.kd = kd;
        }
        public void run() {
            try {
                while (true) {
                    // Retrieve some work; block if the queue is empty
                    Object x = q.getWork();
    
                    // Terminate if the end-of-stream marker was retrieved
                    if (x == NO_MORE_WORK) {
                        //kd.setIsValid(false);
                        break;
                    }

                    String str = (String)x;

                    kd.incLinesRead();
                    if (kd.getLinesRead()%1000 == 0)
                    {
                        System.out.print(".");
                    }
                    String tokens[] = str.split(" ");
                    if (tokens.length == 3)
                    {
                        try
                        {
                            //SDSS_Object obj = new SDSS_Object();
                            double ra = Double.parseDouble(tokens[0].trim());
                            double dec = Double.parseDouble(tokens[1].trim());
                            char band = (tokens[2].trim()).charAt(0);
                            Entry entry = kd.sdssIndex.lookupQuerySkyCal(ra, dec, band);

                            if (entry.valid)
                            {
                                entry.band = band;
                                entry.coord[0] = ra;
                                entry.coord[1] = dec;
                                //System.out.println("DEBUG: " + entry.valid + " " + entry.coord[0] + " " + entry.coord[1]);
                                List tList = (List)kd.filesLocality.get(entry.image);
                                if (tList == null)
                                {
                                    tList = new LinkedList();
                                }
                                tList.add(entry);
                                kd.filesLocality.put(entry.image, tList);

                            }
                            else
                            {
                                System.out.print("x");
                            }
                            //obj.name = entry.image;

                        }
                        catch (Exception e)
                        {
                            //e.printStackTrace();
                            System.out.print("x");
                        }


                    }
                    else if (tokens.length == 2)
                    {
                        try
                        {
                            //SDSS_Object obj = new SDSS_Object();
                            double ra = Double.parseDouble(tokens[0].trim());
                            double dec = Double.parseDouble(tokens[1].trim());
                            for (int i=0;i<kd.bands.length;i++)
                            {
                                char band = kd.bands[i];
                                Entry entry = kd.sdssIndex.lookupQuerySkyCal(ra, dec, band);

                                //obj.name = entry.image;

                                if (entry.valid)
                                {
                                    entry.band = band;
                                    entry.coord[0] = ra;
                                    entry.coord[1] = dec;

                                List tList = (List)kd.filesLocality.get(entry.image);
                                if (tList == null)
                                {
                                    tList = new LinkedList();
                                }
                                tList.add(entry);
                                kd.filesLocality.put(entry.image, tList);
                                }
                                else
                                {
                                    System.out.print("x");

                                }
                            }


                        }
                        catch (Exception e)
                        {
                            //e.printStackTrace();
                            System.out.print("x");
                        }


                    }

                    else
                    {
                        System.out.print("X");
                    }

                    //process(str);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
