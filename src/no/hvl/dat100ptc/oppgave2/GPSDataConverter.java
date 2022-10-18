package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import static java.lang.Integer.*;
import static java.lang.Double.*;

public class GPSDataConverter {
	
	//private static int TIME_STARTINDEX = 11; // posisjon for start av tidspunkt i timestr

	public static int toSeconds(String timestr) {
		
		int secs;
		int hr, min, sec;
		
		int indexT = timestr.indexOf("T");
		int indexPkt = timestr.indexOf(".");
		
		String str = timestr.substring((indexT + 1), indexPkt);
		hr = parseInt(str.substring(0,2));
		min = parseInt(str.substring(3,5));
		sec = parseInt(str.substring(6,8));
		
		secs = hr*3600 + min*60 + sec;
		
		return secs;
		
	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {

		int time;
		double latitude, longitude, elevation;
		time = toSeconds(timeStr);
		latitude = parseDouble(latitudeStr);
		longitude = parseDouble(longitudeStr);
		elevation = parseDouble(elevationStr);
		
		GPSPoint gpspoint = new GPSPoint(time,latitude,longitude,elevation);
		
		return gpspoint;
	    
	}
	
}
