package KDTree;

import edu.wlu.cs.levy.CG.KDTree;
import java.io.*;
import java.lang.*;
import java.util.regex.*;
import java.util.*;

import KDTree.*;


public class KDTreeIndex
{


    public KDTree kd = null;

    public boolean isValid = true;


    public String path;// = "http://das.sdss.org/DR4/data/imaging";
    public boolean gz;// = true;

    public static String pathSentinel = "$P$";
    public static String bandSentinel = "$B$";
    public static String gzString = ".gz";

    public static int numBands = 5;

    public static char bands[] = new char[numBands];


    //public static String indexFile = "/disks/scratchgpfs1/iraicu/astro.portal/data/sdss.all.centers.txt";
    //public static String queryFile = "/disks/scratchgpfs1/iraicu/sdss.gz/sdss_ra_dec/ra_dec_band_2500-test.txt";
    //public static int MAX_ENTRIES = 245368;
    //public static int MAX_QUERIES = 2500;
    public static int MAX_DIM = 2;




    public class Query
    {
        double [] coord = new double[2];
        char band;
    }

    public class Point
    {
        double x;
        double y;
        public Point(double x, double y)
        {
            this.x = x;
            this.y = y;
        }
    }

    public class PointSky
    {
        double ra;
        double dec;
        public PointSky(double ra, double dec)
        {
            this.ra = ra;
            this.dec = dec;
        }
    }


    public int numTuples(String fileName) throws Exception
    {
        int countRec = 0;
        RandomAccessFile randFile = new RandomAccessFile(fileName,"r");
        long lastRec=randFile.length();
        randFile.close();
        FileReader fileRead = new FileReader(fileName);
        LineNumberReader lineRead = new LineNumberReader(fileRead);
        lineRead.skip(lastRec);
        countRec=lineRead.getLineNumber();
        fileRead.close();
        lineRead.close();
        return countRec;
    }

    int numEntries = 0;
    public synchronized void incNumEntries()
    {
        numEntries++;
    }

    public synchronized void setIsValid(boolean isValid)
    {
        this.isValid = isValid;
        System.out.println("setIsValid(): " + isValid);
    }


    public KDTreeIndex(String indexFile) throws Exception
    {

        kd = new KDTree(MAX_DIM);
        
        //int numQueries = 0;
        int dupEntries = 0;

        int MAX_ENTRIES = 0;

        try
        {

            MAX_ENTRIES = numTuples(indexFile);

        } catch (Exception e)
        {
            //System.err.println(e);
            setIsValid(false);
            //return;
            System.out.println("Finding length of file '" + indexFile + "' failed "+ e);
            throw new Exception("Finding length of file '" + indexFile + "' failed ", e);
        }


        long start = System.currentTimeMillis();
        try
        {
            //System.out.print("Reading index file from '" + indexFile + "' (. ~ 1%): ");


            BufferedReader in = new BufferedReader(new FileReader(indexFile));
            String str;
            str = in.readLine(); // set path
            String tokens[] = str.split(" ");
            if (tokens.length == 1)
            {
                tokens = str.split("\t");
            }

            if (tokens.length == 2 && tokens[0].contentEquals(new StringBuffer("$P$")))
            {
                path = tokens[1];
            } else
            {
                //System.err.println("error in file format at '$P$'...");
                //for (int i=0;i<tokens.length;i++)
                //{
                //    System.out.println(i + ": " + tokens[i]);
                //}
                setIsValid(false);
                in.close();
                System.out.println("error in file format at '$P$'...");
                throw new Exception("error in file format at '$P$'...");
                //return;

            }

            str = in.readLine(); //set band
            tokens = str.split(" ");
            if (tokens.length == 1)
            {
                tokens = str.split("\t");
            }


            if (tokens.length > 0 && tokens[0].contentEquals(new StringBuffer("$B$")))
            {
                bands = new char[tokens.length];
                for (int i=1;i<tokens.length;i++)
                {
                    bands[i-1] = tokens[i].charAt(0);
                }

            } else
            {
                //System.err.println("error in file format at '$B$'...");
                //for (int i=0;i<tokens.length;i++)
                //{
                //    System.out.println(i + ": " + tokens[i]);
                //}
                setIsValid(false);
                in.close();
                System.out.println("error in file format at '$B$'...");
                throw new Exception("error in file format at '$B$'...");

                //return;

            }


            str = in.readLine(); //set GZ
            tokens = str.split(" ");
            if (tokens.length == 1)
            {
                tokens = str.split("\t");
            }

            if (tokens.length == 2 && tokens[0].contentEquals(new StringBuffer("GZ")))
            {
                gz = (new Boolean(tokens[1])).booleanValue();
            } else
            {
                //System.err.println("error in file format at GZ...");
                //for (int i=0;i<tokens.length;i++)
                //{
                //    System.out.println(i + ": " + tokens[i]);
                //}
                setIsValid(false);
                in.close();
                //return;
                System.out.println("error in file format at 'GZ'...");
                throw new Exception("error in file format at 'GZ'...");


            }

            // Create the work queue
    LocalWorkQueue queue = new LocalWorkQueue();
    
    // Create a set of worker threads
    final int numWorkers = 8;
    LocalWorker[] workers = new LocalWorker[numWorkers];
    for (int i=0; i<workers.length; i++) {
        workers[i] = new LocalWorker(queue, this);
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
    for (int i=0; i<workers.length; i++) {
        queue.addWork(LocalWorker.NO_MORE_WORK);
    }


    if (this.isValid)
    {
    

    for (int i=0; i<workers.length; i++) {
        workers[i].join();
    }
    }
    else
    {

    }

        } catch (IOException e)
        {
            //System.err.println(e);
            this.isValid = false;
            System.out.println("IOException error in reading file '" + indexFile + "' ");
            throw new Exception("IOException error in reading file '" + indexFile + "' ");
        }

        //System.out.println("");
        long elapsedTimeMillis = System.currentTimeMillis()-start;
        float elapsedTimeSec = elapsedTimeMillis/1000F;
        //System.out.println("Read and inserted " + numEntries + " in " + elapsedTimeSec + " sec");
    }



    public Entry processIndex(String str) throws Exception
    {
        Entry entry = new Entry();
        //$P$/1035/40/corr/1/fpC-001035-$B$1-0101.fit 20.92 13.2959 1 1000 3 1 1 1

        String tokens[] = str.split(" ");
        if (tokens.length == 1)
        {
            tokens = str.split("\t");
        }



        if (tokens.length == 3)
        {
            entry.image = tokens[0];
            entry.coord[0] = (new Double(tokens[1])).doubleValue();
            entry.coord[1] = (new Double(tokens[2])).doubleValue();
            entry.valid = true;
            return entry;
            //Entry entry = new Entry(tokens[0], (new Double(tokens[1])).doubleValue(), (new Double(tokens[2])).doubleValue());
            //return (new Entry(tokens[0], (new Double(tokens[1])).doubleValue(), (new Double(tokens[2])).doubleValue()));
        } else
            return null;

    }


    public Entry processIndexSkyCal(String str) throws Exception
    {
        Entry entry = new Entry();
        //fits	ra	dec	cal_u	cal_g	cal_r	cal_i	cal_z	sky_u	sky_g	sky_r	sky_i	sky_z
        //$P$/1035/40/corr/1/fpC-001035-$B$1-0011.fit 7.073098 13.842101 -23.600876 -24.463956 -23.989415 -23.602503 -21.929801 1.592E-9 1.981E-9 4.469E-9 7.695E-9 2.171E-8

        String tokens[] = str.split(" ");
        if (tokens.length == 1)
        {
            tokens = str.split("\t");
        }

        if (tokens.length == (1 + entry.coord.length + entry.cal.length + entry.sky.length))
        {
            int index=0;
            //System.out.println("Processing index = " + index);
            entry.image = new String(tokens[index++]);

            for (int i=0;i<entry.coord.length;i++)
            {
                //System.out.println("Processing index = " + index + " i = " + i);
                entry.coord[i] = (new Double(tokens[index++])).doubleValue();
            }

            for (int i=0;i<entry.cal.length;i++)
            {
                //System.out.println("Processing index = " + index + " i = " + i);
                entry.cal[i] = (new Double(tokens[index++])).doubleValue();
            }

            for (int i=0;i<entry.sky.length;i++)
            {
               // System.out.println("Processing index = " + index + " i = " + i);
                entry.sky[i] = (new Double(tokens[index++])).doubleValue();
            }

            if (index == tokens.length)
            {
                entry.valid = true;
                return entry;
            }
            else
            {
                System.out.println("ERROR in processIndexSkyCal(): index = " + index + " tokens.length = " + tokens.length);
            
                return null;
            }
        } else
        {
        
            System.out.println("ERROR in processIndexSkyCal(): tokens.length = " + tokens.length + " = 1 + " + entry.coord.length + " + " + entry.cal.length + " + " + entry.sky.length);
            return null;
        }

    }


    public Query processQuery(String str) throws Exception
    {
        Query query = new Query();

        String tokens[] = str.split(" ");
        if (tokens.length == 1)
        {
            tokens = str.split("\t");
        }


        if (tokens.length == 3)
        {
            query.coord[0] = (new Double(tokens[0])).doubleValue();
            query.coord[1] = (new Double(tokens[1])).doubleValue();
            query.band = tokens[2].charAt(0);
            return query;
        } else
            return null;

    }

    String replace(String str, String pattern, String replace) throws Exception
    {
        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();

        while ((e = str.indexOf(pattern, s)) >= 0)
        {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e+pattern.length();
        }
        result.append(str.substring(s));
        return result.toString();
    }

    String getName(String image, String path, char band, boolean gz) throws Exception
    {
        String name = replace(image, pathSentinel, path);
        String sBand = "" + band;
        name = replace(name, bandSentinel, sBand);
        if (gz)
        {
            name = name + gzString;
        }
        return name;
    }

    boolean isBandValid(char band) throws Exception
    {
        for (int i=0;i<bands.length;i++)
        {
            if (bands[i] == band)
            {
                return true;
            }
        }
        return false;
    }

    public String lookupQuery(double ra, double dec, char band) throws Exception
    {
        if (isBandValid(band))
        {


            Query query = new Query();
            query.band = band;
            query.coord[0] = ra;
            query.coord[1] = dec;

            Entry entry = search(query);
            if (entry.valid)
            {
                return entry.image;
            } else
                return "null";
        } else
        {
            //System.out.print("Invalid band == " + band + " ... must be: ");
            //for (int i=0;i<bands.length;i++)
            //{
            //    System.out.print("'" + bands[i] + "' ");

            //}
            //System.out.println("");
            //return null;
            String sBands = "";
            for (int i=0;i<bands.length;i++)
            {
                sBands = sBands + "'" + bands[i] + "' ";

            }

            throw new Exception("Invalid band == " + band + " ... must be: " + sBands);
        }
    }


    public Entry lookupQuerySkyCal(double ra, double dec, char band) throws Exception
    {
        if (isBandValid(band))
        {


            Query query = new Query();
            query.band = band;
            query.coord[0] = ra;
            query.coord[1] = dec;

            return search(query);
        } else
        {
            //System.out.print("Invalid band == " + band + " ... must be: ");
            //for (int i=0;i<bands.length;i++)
            //{
            //    System.out.print("'" + bands[i] + "' ");

            //}
            //System.out.println("");
            //return null;
            String sBands = "";
            for (int i=0;i<bands.length;i++)
            {
                sBands = sBands + "'" + bands[i] + "' ";

            }

            throw new Exception("Invalid band == " + band + " ... must be: " + sBands);
        }
    }

    /*
    public String [] lookupRange(double ra1, double dec1, double ra2, double dec2, char band)
    {
        PointSky p1 = new PointSky(ra1, dec1);
        PointSky p2 = new PointSky(ra2, dec2);

        Entry [] results = range(p1, p2, band);

        String [] sResults = new String[results.length];

        for (int i=0;i<results.length;i++)
        {
            Entry res = new Entry(results[i]);
            sResults[i] = getName(res.image, path, band, gz);
            
        }

        return sResults;
    } */

    public double distance(double [] p1, double [] p2) throws Exception
    {
        //System.out.println("Measuring distance from {" + p1[0] + " " + p1[1] + "} to {" + p2[0] + " " + p2[1] + "}  ... ");

        double cx1 = Math.cos(Math.toRadians(p1[1])) * Math.cos(Math.toRadians(p1[0]));
        double cy1 = Math.cos(Math.toRadians(p1[1])) * Math.sin(Math.toRadians(p1[0]));
        double cz1 = Math.sin(Math.toRadians(p1[1]));

        //System.out.println("cx1: " + cx1);
        //System.out.println("cy1: " + cy1);
        //System.out.println("cz1: " + cz1);

        double cx2 = Math.cos(Math.toRadians(p2[1])) * Math.cos(Math.toRadians(p2[0]));
        double cy2 = Math.cos(Math.toRadians(p2[1])) * Math.sin(Math.toRadians(p2[0]));
        double cz2 = Math.sin(Math.toRadians(p2[1]));

        //System.out.println("cx2: " + cx2);
        //System.out.println("cy2: " + cy2);
        //System.out.println("cz2: " + cz2);


        double dist = 2*Math.toDegrees(Math.asin(Math.sqrt(Math.pow(cx2 - cx1, 2) + Math.pow(cy2 - cy1, 2) + Math.pow(cz2 - cz1, 2))/2));

        //System.out.println("Distance is " + dist);
        return dist;

    }


    public double distanceEuclidian(double [] p1, double [] p2) throws Exception
    {
        double dx = p2[0] - p1[0];
        double dy = p2[1] - p1[1];
        double distance = Math.sqrt(dx*dx + dy*dy);
        return distance;
    }


    public Entry search(Query q) throws Exception
    {
        double MAX_DIST = 0.14166666666666666666666666666667;
        try
        {
            Entry result = (Entry)kd.nearest(q.coord);
            double dist = distance(result.coord, q.coord);
            if (dist <= MAX_DIST) //this should be 8.5/60
            {

                //System.out.println("Query: {" + q.coord[0] + " " + q.coord[1] + " " + q.band + "} ==> " + getName(result.image, path, queries[i].band, gz) + " @ {" + result.coord[0] + " " + result.coord[1] + "} with a distance of " + distance(result.coord, queries[i].coord) + " [max allowed is 0.14]");
                Entry res = new Entry(result);
                res.image = getName(res.image, path, q.band, gz);

                return res;
            } else
            {

                //Entry res2 = new Entry(result);
                //res2.image = getName(res2.image, path, q.band, gz);
                //System.out.println("ERROR: exceed distance max of " + MAX_DIST + " with a distance of " + dist + " ==> image " + res2.image);
                Entry res = new Entry();
                return res;
            }

        } catch (Exception e)
        {
            //System.out.println("Search in KDTree failed " + e);
            throw new Exception("Search in KDTree failed ", e);

            //System.err.println(e);
        }
        //Entry res = new Entry();
        //return res;
    }



    public Entry [] lookupRange(double ra1, double dec1, double ra2, double dec2, char band) throws Exception
    {

        if (isBandValid(band))
        {
            PointSky p1 = new PointSky(ra1, dec1);
            PointSky p2 = new PointSky(ra2, dec2);

            //Entry entries[] = range(p1, p2, band);
            return range(p1, p2, band);



            //return entries;
        } else
        {
            //System.out.print("Invalid band == " + band + " ... must be: ");
            //for (int i=0;i<bands.length;i++)
            //{
            //    System.out.print("'" + bands[i] + "' ");

            //}
            //System.out.println("");
            //return null;
            String sBands = "";
            for (int i=0;i<bands.length;i++)
            {
                sBands = sBands + "'" + bands[i] + "' ";

            }

            throw new Exception("Invalid band == " + band + " ... must be: " + sBands);

        }
    }


    public Entry[] range(PointSky p1, PointSky p2, char band) throws Exception
    {
        //long start = System.currentTimeMillis();
        try
        {


            double [] lo = {p1.ra,p1.dec};
            double [] hi = {p2.ra,p2.dec};
            Object [] o = kd.range(lo, hi);
            //Entry [] o = (Entry [] )kd.range(lo, hi);

            //Entry [] entries = (Entry [])o.clone();

            // dump them to stdout in sorted order
            //Arrays.sort(o);
            //System.out.println("Range {" + p1.ra + ","+ p1.dec+ "} x {" + p2.ra + ","+ p2.dec+ "}... " + o.length + " results retrieved...");

            if (o.length > 0)
            {

                Entry [] entries = new Entry[o.length];

                for (int i=0; i<o.length; ++i)
                {
                    entries[i] = new Entry((Entry)o[i]);
                    entries[i].image = getName(entries[i].image, path, band, gz);
                    entries[i].band = band;
                    entries[i].valid = true;
                    //    Entry result = (Entry)o[i];
                    //System.out.println(o[i]);
                    //System.out.println(getName(result.image, path, 'r', gz) + " @ {" + result.coord[0] + " " + result.coord[1] + "}");
                }
                //long elapsedTimeMillis = System.currentTimeMillis()-start;
                //float elapsedTimeSec = elapsedTimeMillis/1000F;
                //System.out.println("Performed range query with " + o.length + " results in " + elapsedTimeSec + " sec");

                return entries;
            } else
            {
                return null;

            }
        } catch (Exception f)
        {

            //System.err.println(f);
            throw new Exception("Range search in KDTree failed ", f);
        }
        //return null;
    }

    /*
    public void main_run(String [] args)
    {
        //search query test
        try
        {
            int numQueries = 0;
            BufferedReader in = new BufferedReader(new FileReader(queryFile));
            String str;
            Query queries[] = new Query[MAX_QUERIES];
            while ((str = in.readLine()) != null && numQueries < MAX_QUERIES)
            {
                queries[numQueries] = processQuery(str);
                numQueries++;
                if (numQueries % MAX_QUERIES/100 == 0) System.out.print(".");
                
            }
            System.out.println("");

            long start = System.currentTimeMillis();
            for (int i=0;i<numQueries;i++)
            {
                if (queries[i] != null)
                {
                    try
                    {
                        Entry result = search(queries[i]);
                        if (result != null) 
                        {
                            //double dist = distance(result.coord, queries[i].coord);
                            //System.out.println("Query: {" + queries[i].coord[0] + " " + queries[i].coord[1] + " " + queries[i].band + "} ==> " + result.image + " @ {" + result.coord[0] + " " + result.coord[1] + "} with a distance of " + dist + " [max allowed is 0.14]");
                        }
                        else System.out.println("Query: {" + queries[i].coord[0] + " " + queries[i].coord[1] + " " + queries[i].band + "} ==> NULL, exceed maximum distance allowed...");
                        if (i % numQueries/100 == 0) System.out.print("s");
                    } catch (Exception e)
                    {
                        System.err.println(e);
                    }
                }
            }

            System.out.println("");
            long elapsedTimeMillis = System.currentTimeMillis()-start;
            float elapsedTimeSec = elapsedTimeMillis/1000F;
            System.out.println("Performed " + numQueries + " in " + elapsedTimeSec + " sec");
            in.close();
        } catch (IOException e)
        {
            System.err.println(e);
        }


        //range query test
        long start = System.currentTimeMillis();
        PointSky p1 = new PointSky(0.0, 0.0);
        PointSky p2 = new PointSky(10.0, 10.0);

        Entry [] results = range(p1, p2, 'r');
        if (results != null)
        {
        
            System.out.println("Range {" + p1.ra + ","+ p1.dec+ "} x {" + p2.ra + ","+ p2.dec+ "}... " + results.length + " results retrieved...");

        
            for (int i=0; i<results.length; ++i) 
            {
                //System.out.println(results[i].image + " @ {" + results[i].coord[0] + " " + results[i].coord[1] + "}");
            }
        
            long elapsedTimeMillis = System.currentTimeMillis()-start;
            float elapsedTimeSec = elapsedTimeMillis/1000F;
            System.out.println("Performed range query with " + results.length + " results in " + elapsedTimeSec + " sec");
        }
        else
            System.out.println("Error in range(), returned null...");

    }
    */


    public static void main(String [] args) 
    {
        /*
        KDTreeIndex kdt = new KDTreeIndex("/disks/scratchgpfs1/iraicu/astro.portal/data/sdss.all.centers.txt");
        kdt.main_run(args);
        */
        System.out.println("The KDTreeIndex should be accessed via the corresponding API, and not directly by this main() function...  see the source code for examples on how to use the API :)");
    }

}

class LocalWorkQueue {
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

class LocalWorker extends Thread {
        // Special end-of-stream marker. If a worker retrieves
        // an Integer that equals this marker, the worker will terminate.
        static final Object NO_MORE_WORK = new Object();
    
        LocalWorkQueue q;
        KDTreeIndex kd;
    
        LocalWorker(LocalWorkQueue q, KDTreeIndex kd) {
            this.q = q;
            this.kd = kd;
        }
        public void run() {
            try {
                while (kd.isValid) {
                    // Retrieve some work; block if the queue is empty
                    Object x = q.getWork();
    
                    // Terminate if the end-of-stream marker was retrieved
                    if (x == NO_MORE_WORK) {
                        kd.setIsValid(true);
                        break;
                    }

                    Entry entry = kd.processIndexSkyCal((String)x);
                    if (entry != null)
                    {
                        try
                        {

                            kd.kd.insert(entry.coord, entry);
                            kd.incNumEntries();
                            /*
                            if (numEntries % MAX_ENTRIES/100 == 0)
                            {
                                System.out.print(".");
                                System.out.flush();

                            }
                            */

                        } catch (Exception e)
                        {
                            /*
                            dupEntries++;
                            System.out.println("ERROR: DUPLICATE ENTRY #" + dupEntries + ": " + entry.image + " @ {" + entry.coord[0] + " " + entry.coord[1] + "} " + e);
                            try
                            {


                            Entry result = (Entry)kd.nearest(entry.coord);
                            System.out.println("ERROR: ORIGINAL ENTRY #" + dupEntries + ": " + result.image + " @ {" + result.coord[0] + " " + result.coord[1] + "}");
                            }
                            catch (Exception f)
                            {

                                System.out.println("ERROR: DUPLICATE ENTRY SEARCH: " + e);
                            }
                            */
                            //throw new Exception("Insert in KDTree failed, duplicate entry found!", e);

                        }
                    } else
                    {
                        //System.err.println("error in file format in entry # " + numEntries + " ..." );

                        kd.setIsValid(false);
                        //in.close();
                        System.out.println("error in file format in entry # " + kd.numEntries + " ");
                        //return;
                    }

    
                    // Compute the square of x
                    //int y = ((Integer)x).intValue() * ((Integer)x).intValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
