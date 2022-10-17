package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;

		for(int i=1; i<gpspoints.length;i++) {
			distance += GPSUtils.distance(gpspoints[i-1],gpspoints[i]);
		}
		
		return distance;

	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		for(int i=1; i<gpspoints.length;i++) {
			double n = gpspoints[i].getElevation() - gpspoints[i-1].getElevation();
			if(n>0) {
				elevation += n;
			}
		}
		
		return elevation;

	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {

		int time = gpspoints[gpspoints.length-1].getTime() - gpspoints[0].getTime();;
		
		return time;

	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		
		double[] speed = new double[gpspoints.length-1];
		
		for(int i=1; i<gpspoints.length;i++) {
			double speeds = GPSUtils.speed(gpspoints[i-1], gpspoints[i]);
			speed[i-1] = speeds;
		}
		
		return speed;

	}
	
	public double maxSpeed() {
		
		double maxspeed = 0;
		
		for(int i=1; i<gpspoints.length;i++) {
			double speeds = GPSUtils.speed(gpspoints[i-1], gpspoints[i]);
			if(speeds > maxspeed) {
				maxspeed = speeds;
			}
		}
		
		return maxspeed;
		
	}

	public double averageSpeed() {

		double average = 0;
		
		double time = gpspoints[gpspoints.length-1].getTime() - gpspoints[0].getTime();
		double distance = 0;
		for(int i=1; i<gpspoints.length;i++) {
			distance += GPSUtils.distance(gpspoints[i-1],gpspoints[i]);
		}		
		
		average = (distance/time)*3.6;
		
		return average;
		
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;		
		double speedmph = speed * MS;

		if(speedmph<10) {
			met = 4.0;
		} else if(10 <= speedmph && speedmph < 12) {
			met = 6.0;
		} else if(12 <= speedmph && speedmph < 14) {
			met = 8.0;
		} else if(14 <= speedmph && speedmph < 16) {
			met = 10;
		} else if(16 <= speedmph && speedmph < 20) {
			met = 12;	
		} else {
			met = 16;	
		}
		
		//double time = secs/3600; 
		kcal = weight*met*secs/3600; 
		
		
		return kcal;
		
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;		
		double[] speeds = speeds();
		
		for(int i = 1; i<gpspoints.length; i++) {
			int time = gpspoints[i].getTime() - gpspoints[i-1].getTime();
			double fart = speeds[i-1]/3.6;
			totalkcal += kcal(weight, time, fart);
		}
		
		return totalkcal;
		
		
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {

		String linjer = "==============================================";
		
		String KOLON = ":";
		String tekst = "Total time";
		String time = GPSUtils.formatTime(totalTime());
		time = time.replace(" ", "");
		String km = "km";
		double distance = totalDistance()/1000;
		double elevation = totalElevation();
		double speed = maxSpeed();
		double speedA = averageSpeed();
		double energy = kcal(WEIGHT,totalTime(),speedA);
		
		System.out.println(linjer);
		
		String string = "";
		string = String.format("%-15s %s %11s",tekst,KOLON,time); // print tid
		System.out.println(string);
		
		tekst = "Total distance";
		string = String.format("%-15s %s %11.2f %s",tekst,KOLON,distance,km);
		System.out.println(string);
		
		tekst = "Total elevation";
		km = "m";
		string = String.format("%-15s %s %11.2f %s",tekst,KOLON,elevation,km);
		System.out.println(string);
		
		tekst = "Max speed";
		km = "km/t";
		string = String.format("%-15s %s %11.2f %s",tekst,KOLON,speed,km);
		System.out.println(string);
		
		tekst = "Average Speed";
		string = String.format("%-15s %s %11.2f %s",tekst,KOLON,speedA,km);
		System.out.println(string);
		
		tekst = "Energy";
		km = "kcal";
		string = String.format("%-15s %s %11.2f %s",tekst,KOLON,energy,km);
		System.out.println(string);
		
		
		System.out.println(linjer);
	}

}
