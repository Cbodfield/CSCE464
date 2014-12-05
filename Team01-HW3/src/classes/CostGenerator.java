package classes;

import java.util.HashMap;

public class CostGenerator {
	
	private HashMap<String, String> hashmap = new HashMap<String, String>();

	public CostGenerator(){
		this.hashmap.put("ATL", "33.6367,-84.4281");
		this.hashmap.put("ANC", "61.1744,-149.9964");
		this.hashmap.put("AUS", "30.1944,-97.6700");
		this.hashmap.put("BWI", "39.1753,-76.6683");
		this.hashmap.put("BOS", "42.3631,-71.0064");
		this.hashmap.put("CLT", "35.2139,-80.9431");
		this.hashmap.put("MDW", "41.7861,-87.7525");
		this.hashmap.put("ORD", "41.9808,-87.9067");
		this.hashmap.put("CVG", "39.0489,-84.6678");
		this.hashmap.put("CLE", "41.4094,-81.8550");
		this.hashmap.put("CMH", "39.9981,-82.8919");
		this.hashmap.put("DFW", "32.8969,-97.0381");
		this.hashmap.put("DEN", "39.8617,-104.6731");
		this.hashmap.put("DTW", "42.215,-83.3533");
		this.hashmap.put("FLL", "26.0726,-80.1528");
		this.hashmap.put("RSW", "26.5362,-81.7553");
		this.hashmap.put("BDL", "41.9389,-72.6833");
		this.hashmap.put("HNL", "21.3187,-157.9225");
		this.hashmap.put("IAH", "29.9844,-95.3414");
		this.hashmap.put("HOU", "29.6456,-95.2789");
		this.hashmap.put("IND", "39.7172,-86.2947");
		this.hashmap.put("MCI", "39.2975,-94.7139");
		this.hashmap.put("LAS", "36.0800,-115.1522");
		this.hashmap.put("LAX", "33.9425,-118.4072");
		this.hashmap.put("MEM", "35.0425,-89.9767");
		this.hashmap.put("MIA", "25.7933,-80.2906");
		this.hashmap.put("MSP", "44.8819,-93.2217");
		this.hashmap.put("BNA", "36.1244,-86.6783");
		this.hashmap.put("MSY", "29.9933,-90.2581");
		this.hashmap.put("JFK", "40.6397,-73.7789");
		this.hashmap.put("LGA", "40.7772,-73.8725");
		this.hashmap.put("EWR", "40.6925,-74.1686");
		this.hashmap.put("OAK", "37.7214,-122.2208");
		this.hashmap.put("ONT", "34.0561,-117.6011");
		this.hashmap.put("MCO", "28.4294,-81.3089");
		this.hashmap.put("PHL", "39.8722,-75.2408");
		this.hashmap.put("PHX", "33.4342,-112.0117");
		this.hashmap.put("PIT", "40.4914,-80.2328");
		this.hashmap.put("PDX", "45.5883,-122.5975");
		this.hashmap.put("RDU", "35.8778,-78.7875");
		this.hashmap.put("SMF", "38.6956,-121.5908");
		this.hashmap.put("SLC", "40.7883,-111.9778");
		this.hashmap.put("SAT", "29.5336,-98.4697");
		this.hashmap.put("SAN", "32.7336,-117.1897");
		this.hashmap.put("SFO", "37.6189,-122.3750");
		this.hashmap.put("SJC", "37.3628,-121.9292");
		this.hashmap.put("SNA", "33.6756,-117.8683");
		this.hashmap.put("SEA", "47.4500,-122.3117");
		this.hashmap.put("STL", "38.7486,-90.3700");
		this.hashmap.put("TPA", "27.9756,-82.5333");
		this.hashmap.put("IAD", "38.9475,-77.4600");
		this.hashmap.put("DCA", "38.8522,-77.0378");
	}
	
	public String generateFlightCost(String source, String destination){
		String sourceCoord = this.hashmap.get(source);
		String destinationCoord = this.hashmap.get(destination);
		
		double sourceLat = Double.parseDouble(sourceCoord.substring(0, sourceCoord.indexOf(",")));
		double sourceLong = Double.parseDouble(sourceCoord.substring(sourceCoord.indexOf(",")+1));
		
		double destLat = Double.parseDouble(destinationCoord.substring(0, destinationCoord.indexOf(",")));
		double destLong = Double.parseDouble(destinationCoord.substring(destinationCoord.indexOf(",")+1));
		
		String distance = getDistanceBetweenPoints(sourceLat,destLat,sourceLong,destLong);
		String cost = getCostForDistance(distance);
		
		return cost;
	}
	
	
	private String getCostForDistance(String distance){
		double nDistance = Math.floor(Double.valueOf(distance));
		
		if (nDistance < 100){
			return "150";
		}
		
		if (nDistance < 500){
			return "300";
		}
		
		if (nDistance < 1000){
			return "600";
		}
		
		if (nDistance < 2000){
			return "1000";
		}
		
		if (nDistance < 4000){
			return "1500";
		}
				
		if (nDistance < 6000){
			return "2000";
		}
		
		else if (nDistance < 10000){
			return "2500";
		}else{
			return "3000";
		}
		
	}
	
	private String getDistanceBetweenPoints(double latSource, double latDestination, double longSource, double longDestination){
		Double distance = 0.0;
		
		Double dLatAinRad = latSource * (Math.PI / 180);
		Double dLongAinRad = longSource * (Math.PI / 180);
		
		Double dLatBinRad  = latDestination * (Math.PI / 180);
		Double dLongBinRad  = longDestination * (Math.PI / 180);
		
		Double dLatitude = dLatBinRad - dLatAinRad;
		Double dLongitude = dLongBinRad - dLongAinRad;
		
		Double a = Math.pow(Math.sin(dLatitude / 2.0),  2.0) + Math.cos(dLatAinRad) * Math.cos(dLatBinRad) * Math.pow(Math.sin(dLongitude / 2.0), 2.0);
		Double c = 2.0 * Math.asin(Math.sqrt(a));
		Double mEarthRadius = 3962.165805;
		
		distance = mEarthRadius * c;
        return distance.toString();
	}
	
}