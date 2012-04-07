//package org.globus.SkyServerWS.services.core.SkyServer.impl;
package JNI.FunTools;

import java.lang.*;
import java.io.*;
import java.nio.*;

//for accessing objects, see http://www.camtp.uni-mb.si/books/Thinking-in-Java/AppendixB.html

public class funsky_JNI
{
	static
	{
            if (System.getProperty("os.arch").toLowerCase().startsWith("x86"))
            {
                System.out.println("Loading library for architecture " + System.getProperty("os.arch").toLowerCase() + " for the raDec2xy() conversion of FITs images...");
                System.loadLibrary("funsky");
            }
            /*
            else if (System.getProperty("os.arch").toLowerCase().startsWith("ia64"))
            {
                System.out.println("Loading library for architecture " + System.getProperty("os.arch").toLowerCase() + " for the raDec2xy() conversion of FITs images...");
                System.loadLibrary("funsky_ia64");

            } */
            else
            {
                System.out.println("Architecture " + System.getProperty("os.arch").toLowerCase() + " not supported for the raDec2xy() conversion of FITs images...");
            }
	    
	}

        /*
        public class Point
        {
            public double x;
            public double y;

            public Point(String x, String y)
            {
                this.x = (new Double(x)).doubleValue();
                this.y = (new Double(y)).doubleValue();
            }
        } */

	public native synchronized String raDec2xy(String image, double ra, double dec);

        public synchronized double[] sky2image(String image, double ra, double dec)
        {
            String result = raDec2xy(image, ra, dec);
            if (result.startsWith("null"))
            {
                return null;
            }
            else
            {
                String tokens[] = result.split(" ");
                if (tokens.length == 2)
                {
                    double p[] = new double[2];
                    p[0] = (new Double(tokens[0])).doubleValue();
                    p[1] = (new Double(tokens[1])).doubleValue();
                    return p;
                    //return (new Point(tokens[0], tokens[1]));
                }
                else
                    return null;

            }

        }

        
	public static void main (String arg[])
	{

		funsky_JNI funsky = new funsky_JNI();

		if (arg.length != 3)
		{
		    System.out.println("Incorrect number of parameters... usage: java funsky_JNI <image_name> <ra> <dec>");
		    System.exit(0);
		    
		}
                long start;
                long end;
                long elapsedTimeMillis;


			    start = System.currentTimeMillis();
				    //String result = funsky.raDec2xy(arg[0], (new Double(arg[1])).doubleValue(), (new Double(arg[2])).doubleValue());
                            //Point p = funsky.sky2image(arg[0], (new Double(arg[1])).doubleValue(), (new Double(arg[2])).doubleValue());
                            double p[] = funsky.sky2image(arg[0], (new Double(arg[1])).doubleValue(), (new Double(arg[2])).doubleValue());
                            //System.out.println(arg[0] + ": {" + arg[1] + " " + arg[2] + "} ==> (" + p.x + " " + p.y + ")");
                            System.out.println(arg[0] + ": {" + arg[1] + " " + arg[2] + "} ==> (" + p[0] + " " + p[1] + ")");
			    end = System.currentTimeMillis();
			    elapsedTimeMillis = end-start;
			    System.out.println("time: " + elapsedTimeMillis/1000.0 + " seconds"); 
			    System.out.println("throughput: " + 1 / (elapsedTimeMillis/1000.0) + " conversions / sec"); 




	} 
        
        
}
