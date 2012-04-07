package KDTree;

import edu.wlu.cs.levy.CG.KDTree;
import java.io.*;
import java.lang.*;
import java.util.regex.*;
import KDTree.*;


    public class Entry
    {
        public static int numBands = 5;
        public static int numDim = 2;


    public double [] coord = new double[numDim];
        public char band;
        public String image;
        public boolean valid;

        public double sky[] = new double[numBands];
        public double cal[] = new double[numBands];

        public Entry(double [] coord, char band, String image, double [] sky, double [] cal)
        {

            this.band = band;
            this.image = image;
            this.valid = true;

            if (coord.length == numDim)
            {
                for (int i=0;i<numDim;i++)
                    this.coord[i] = coord[i];
            }
            else
                this.valid = false;


            if (sky.length == numBands)
            {
                for (int i=0;i<numBands;i++)
                    this.sky[i] = sky[i];
            }
            else
                this.valid = false;

            if (cal.length == numBands)
            {
                for (int i=0;i<numBands;i++)
                    this.cal[i] = cal[i];
            }
            else
                this.valid = false;
            

        }


        public Entry(double [] coord, char band, String image)
        {
            this.valid = true;
            this.band = band;
            this.image = image;

            if (coord.length == numDim)
            {
                for (int i=0;i<numDim;i++)
                    this.coord[i] = coord[i];
            }
            else
                this.valid = false;
        }

        public Entry(Entry e)
        {
            this.band = e.band;
            this.image = e.image;

            for (int i=0;i<e.coord.length;i++)
                this.coord[i] = e.coord[i];

            for (int i=0;i<e.sky.length;i++)
                this.sky[i] = e.sky[i];

            for (int i=0;i<e.cal.length;i++)
                this.cal[i] = e.cal[i];

            this.valid = e.valid;
        }


        public Entry()
        {
            this.band = ' ';
            this.image = "";

            for (int i=0;i<this.coord.length;i++)
                this.coord[i] = 0.0;

            for (int i=0;i<this.sky.length;i++)
                this.sky[i] = 0;

            for (int i=0;i<this.cal.length;i++)
                this.cal[i] = 0;

            this.valid = false;
        }

    }

