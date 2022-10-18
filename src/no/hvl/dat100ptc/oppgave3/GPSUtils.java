package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;



import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;
		
		min = da[0];
		for (double d : da)  {
			if (d < min) {
				min = d;
			}
			
			}
		
		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double [] latitude = new double [gpspoints.length];
		
		for (int i = 0; i< gpspoints.length; i++) {
			
			latitude [i] =  gpspoints[i].getLatitude();
		}
		return latitude;
 		
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double [] longitude = new double [gpspoints.length];
		
		for (int i = 0; i <gpspoints.length; i++) {
			longitude [i] = gpspoints[i].getLongitude();
		}
		
		return longitude;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d,a,b;
		double latitude1, longitude1, latitude2, longitude2;
		
		
		latitude1 = toRadians(gpspoint1.getLatitude());
		longitude1 = toRadians(gpspoint1.getLongitude());
		latitude2 = toRadians(gpspoint2.getLatitude());
		longitude2 = toRadians(gpspoint2.getLongitude());
		
		double q1 = latitude2-latitude1;
		double q2 = longitude2-longitude1;
		
		a = (pow(sin(q1/2),2))+(cos(latitude1))*(cos(latitude2))*(pow(sin(q2/2),2));
		b = 2*atan2(sqrt(a),sqrt(1-a));
		d = R*b;
		
		return d;

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		double distanse;

		secs = gpspoint2.getTime() - gpspoint1.getTime(); 
		distanse = GPSUtils.distance(gpspoint1, gpspoint2);
		speed = (distanse/secs)*(3.6);
		
		return speed;

	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";
        
		int hh = secs/(60*60);
		int mm = (secs )/60;
	    int ss = (secs);
	    
	    if (secs<TEXTWIDTH) {
	    	timestr = "0"+ hh + TIMESEP +"0"+ mm + TIMESEP+"0" + ss;
	    	return timestr;
	    } else {
	    
	    timestr = hh + TIMESEP + mm + TIMESEP+ss;	
		return timestr;	}
	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;
		System.out.println(d);
		
		String sta = String.format("%.2f",d);
		String stb = "";
		System.out.println(sta);
		
		for (int i = sta.length()-1; i<TEXTWIDTH-1; i++ ) {
			stb += " ";
			}
		
		str = stb+sta;
		System.out.println(str);
		return str;
	
		
		
		
	}	
}
