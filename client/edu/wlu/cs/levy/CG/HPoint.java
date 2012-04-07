// Hyper-Point class supporting KDTree class

package edu.wlu.cs.levy.CG;

class HPoint {

    protected double [] coord;

    protected HPoint(int n) {
	coord = new double [n];
    }

    protected HPoint(double [] x) {

	coord = new double[x.length];
	for (int i=0; i<x.length; ++i) coord[i] = x[i];
    }

    protected Object clone() {

	return new HPoint(coord);
    }

    protected boolean equals(HPoint p) {

	// seems faster than java.util.Arrays.equals(), which is not 
	// currently supported by Matlab anyway
	for (int i=0; i<coord.length; ++i)
	    if (coord[i] != p.coord[i])
		return false;

	return true;
    }

    
    protected static double sqrdist(HPoint x, HPoint y) {

	double dist = 0;

	for (int i=0; i<x.coord.length; ++i) {
	    double diff = (x.coord[i] - y.coord[i]);
	    dist += (diff*diff);
	}

	return dist;

    }

    protected static double eucdist(HPoint x, HPoint y) {


        //System.out.println("*************************** eucdist()...");
        //System.err.println("*************************** eucdist()...");
	return Math.sqrt(sqrdist(x, y));
    }
    


                      /*
    public static double radians(double radians)
    {
        double degrees = radians * 180 / Math.PI;
        return degrees;

    }

    public static double degrees(double degrees)
    {
        double radians = degrees * 180 / Math.PI;
        return radians;

    }                   */

    //public double distance(double [] p1, double [] p2) throws Exception
    //this is needed to take into account the 3D sperical coordinate system in sky images... 2 dimentional space is hard coded
    public static double skydist(HPoint x, HPoint y)
    {
        //System.out.println("skydist(): Measuring distance from {" + x.coord[0] + " " + x.coord[1] + "} to {" + y.coord[0] + " " + y.coord[1] + "}  ... ");
       // System.err.println("skydist()...");
        if (x.coord.length == 2)
        {

        
        
        double cx0 = Math.cos(Math.toRadians(x.coord[1])) * Math.cos(Math.toRadians(x.coord[0]));
        double cy0 = Math.cos(Math.toRadians(x.coord[1])) * Math.sin(Math.toRadians(x.coord[0]));
        double cz0 = Math.sin(Math.toRadians(x.coord[1]));

        double cx1 = Math.cos(Math.toRadians(y.coord[1])) * Math.cos(Math.toRadians(y.coord[0]));
        double cy1 = Math.cos(Math.toRadians(y.coord[1])) * Math.sin(Math.toRadians(y.coord[0]));
        double cz1 = Math.sin(Math.toRadians(y.coord[1]));


        double dist = 2*Math.toDegrees(Math.asin(Math.sqrt(Math.pow(cx1 - cx0, 2) + Math.pow(cy1 - cy0, 2) + Math.pow(cz1 - cz0, 2))/2));

        //System.out.println("skydist(): Distance is " + dist);
        return dist;
        }
        else
        {
        
        //    System.out.println("skydist(): Distance is " + Double.MAX_VALUE);
            return Double.MAX_VALUE;
        }
    }


    public static double distanceEuclidian2D(double [] p1, double [] p2)
    {
        double dx = p2[0] - p1[0];
        double dy = p2[1] - p1[1];
        double distance = Math.sqrt(dx*dx + dy*dy);
        return distance;
    }

    public String toString() {
	String s = "";
	for (int i=0; i<coord.length; ++i) {
	    s = s + coord[i] + " ";
	}
	return s;
    }

}
