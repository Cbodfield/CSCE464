package classes;

import java.util.Random;

public class Flight {
	public String sSource ="";
	public String sDestination = "";
	public String sFlightID = "";
	public String sOperator = "";
	public String sPlaneNumber = "";
	public String sDepartureTime = "";
	public String sArrivalTime = "";
	public int nNumberOfStops =0;
	public int nCost =0;
	
//	public void generateCost(){
//		//this.nCost = (int)(new Random()).nextInt(1000)+100;
//		CostGenerator cg = new CostGenerator();
//		this.nCost = Integer.valueOf(cg.generateFlightCost(this.sSource, this.sDestination));
//	}
}
