
import helper.FlightHelper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




import classes.CostGenerator;
import classes.Flight;
import classes.JDBCHelper;

/**
 * Servlet implementation class FlightSearchQuery
 */
public class FlightSearchQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FlightSearchQuery() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String sSource = request.getParameter("source");
		String sDestination = request.getParameter("destination");
		String sDate = request.getParameter("date");
		String sSeats = request.getParameter("seats");
		String sClass = request.getParameter("class");
		if(sSource == null) sSource ="";
		if(sDestination ==null)sDestination = "";
		if(sDate == null) sDate = "";
		if(sSeats == null) sSeats = "";
		if(sClass == null) sClass= "";
		
		ArrayList<Flight> flights = new ArrayList<Flight>();
		FlightHelper fh = getSearchResults(sSource, sDestination, sDate, sSeats, sClass);
		flights = fh.list;
		JSONArray arr = new JSONArray();
		for(int i = 0; i<flights.size();i++){
			JSONObject obj = new JSONObject();
			Flight f = flights.get(i);
			try{
				obj.put("source",f.sSource);
				obj.put("destination",f.sDestination);
				obj.put("flightID",f.sFlightID);
				obj.put("operator",f.sOperator);
				obj.put("planeID",f.sPlaneNumber);
				obj.put("departuretime",f.sDepartureTime);
				obj.put("arrivaltime",f.sArrivalTime);
				obj.put("cost", String.valueOf(f.nCost));
				obj.put("stops", String.valueOf( (int)(new Random()).nextInt(3)+0));
				arr.put(obj);
			} catch (JSONException e){
				
			}
		}
		//request.setAttribute("flights", arr.toString());
		HttpSession session = request.getSession();
		session.setAttribute("flights", arr.toString());
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/FlightSearchResults.jsp;jsessionid="+session.getId());
		rd.forward(request,response);
	}
	
	public FlightHelper getSearchResults(String sSource, String sDestination, String sDate, String sSeats, String sClass){
		
		JDBCHelper jdbc = new JDBCHelper();
		if(jdbc.connectToCSE464DB()){
		
		ArrayList<String> lFields = new ArrayList<String>();
		ArrayList<Object> lParams = new ArrayList<Object>();

		if(!sSource.trim().equals("")){
			lFields.add("source");
			lParams.add(sSource);
		}
		if(!sDestination.trim().equals("")){
			lFields.add("destination");
			lParams.add(sDestination);
		}
		if(!sDate.trim().equals("")){
			lFields.add("departure");
			lParams.add(sDate);
		}
		//Need to join with planes to determine if the 
		//capacity of the given class is greater or equal
		//to the number of seats requested
		//
		//If no #ofseats is passed in, then we can just check
		//if that specific class is reserved?
		
		if(!sSeats.trim().equals("")){
			//THIS LOGIC IS HANDLED WHEN POPULATING THE FLIGHTS OBJECT
		}
		String c = "";
		String r = "";
		if(!sClass.trim().equals("")){
			//THIS LOGIC IS HANDLED WHEN POPULATING THE FLIGHTS OBJECT
			if(sClass.equalsIgnoreCase("economy")){
				c= "economy_capacity";
				r="economy_reserved";
			} else if (sClass.equalsIgnoreCase("first class")){
				c= "first_class_capacity";
				r="first_class_reserved";
			} else if(sClass.equalsIgnoreCase("business")){
				c= "business_capacity";
				r="business_reserved";
			}
		}
		String query = buildQuery("select * from flights left join planes on flights.plane=planes.id", lFields, lParams);
		ResultSet rs = jdbc.LIKEqueryDB(query, lParams);
		
		ArrayList<Flight> flights = new ArrayList<Flight>();
		FlightHelper fh = new FlightHelper();
		CostGenerator cg = new CostGenerator();
		if (rs != null) {
			
		
			try {
				while (rs.next()) {
					Flight f = new Flight();
					
					f.sDepartureTime = String.valueOf(rs.getObject("departure"));
					f.sArrivalTime = String.valueOf(rs.getObject("arrival"));
					f.sOperator = String.valueOf(rs.getObject("operator"));
					f.sSource = String.valueOf(rs.getObject("source"));
					f.sDestination = String.valueOf(rs.getObject("destination"));
					f.nCost = Integer.valueOf(cg.generateFlightCost(f.sSource, f.sDestination));
					f.sFlightID = String.valueOf(rs.getObject("id"));
					f.sPlaneNumber = String.valueOf(rs.getObject("plane"));
					
					if(!sSeats.isEmpty()){
						int seatsRequested = Integer.valueOf(sSeats);
						int seatsReserved = Integer.valueOf(String.valueOf(rs.getObject(r)));
						int seatsTotal = Integer.valueOf(String.valueOf(rs.getObject(c)));
						if((seatsTotal-seatsReserved)>= seatsRequested){
							//IF THERE ARE SEATS AVAILABLE
							flights.add(f);
							fh.nNumberOfFlights ++;
						}
					} else {
						int seatsReserved = Integer.valueOf(String.valueOf(rs.getObject(r)));
						int seatsTotal = Integer.valueOf(String.valueOf(rs.getObject(c)));
						
						if((seatsTotal-seatsReserved)>= 0){
							//IF THERE ARE SEATS AVAILABLE
							flights.add(f);
							fh.nNumberOfFlights ++;
						}
					}
					
				}
				
				fh.list = flights;
			} catch(Exception e){
				e.printStackTrace();
			}
		}
			return fh;
		} else {
			return null;
		}
	}

	private String buildQuery(String sbQueryBase, ArrayList<String> sFields,
			ArrayList<Object> oParams) {
		boolean bParamFound = false;
		StringBuilder sb = new StringBuilder();
		sb.append(sbQueryBase);

		for (int i = 0; i < sFields.size(); i++) {
			if (!oParams.get(i).toString().isEmpty()) {
				if (bParamFound) {
					sb.append(" AND (" + sFields.get(i).toUpperCase()
							+ " LIKE ? )");
				} else {
					sb.append(" WHERE (" + sFields.get(i).toUpperCase()
							+ " LIKE ? )");
					bParamFound = true;
				}
			}
		}

		sb.append(";");

		return sb.toString();
	}

	// TODO - not finished - returns 2 dimensional array of objects - like a
	// data table
//	public ArrayList<ArrayList<Object>> getSearchResults(String sSource,
//			String sDestination, String sTravelDate, String sNumOfSeats,
//			String sClass) {
//		JDBCHelper jdbc = new JDBCHelper();
//		jdbc.connectToCSE464DB();
//
//		ArrayList<Object> param = new ArrayList<Object>();
//		ArrayList<ArrayList<Object>> results = new ArrayList<ArrayList<Object>>();
//		ArrayList<String> sFields = new ArrayList<String>();
//		ArrayList<Object> oParams = new ArrayList<Object>();
//
//		sFields.add("Source");
//		sFields.add("Destination");
//		sFields.add("Date"); // fix?
//		sFields.add("Seats"); // fix?
//		sFields.add("Class"); // fix?
//
//		oParams.add(sSource);
//		oParams.add(sDestination);
//		oParams.add(sTravelDate);
//		oParams.add(sNumOfSeats);
//		oParams.add(sClass);
//
//		String sQuery = buildQuery("SELECT * FROM FLIGHTS", sFields, oParams);
//
//		ResultSet rs1 = jdbc.queryDB(sQuery, param);
//
//		try {
//			if (rs1 != null) {
//				while (rs1.next()) {
//					ArrayList<Object> temp = new ArrayList<Object>();
//					/* TODO - correct db to get date and stops count? */
//					/* TODO - is Class a parameter to the db? */
//					temp.add(rs1.getObject("FLIGHTNUMBER")); // flight number
//					temp.add(rs1.getObject("DATE")); // flight date?
//					temp.add(rs1.getObject("DEPARTURE")); // departure time,
//															// date, or both?
//					temp.add(rs1.getObject("ARRIVAL")); // arrival time, date,
//														// or both?
//					temp.add(rs1.getObject("")); // number of stops
//					temp.add(rs1.getObject("COST")); // cost
//
//					results.add(temp);
//				}
//			}
//			jdbc.closeConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return results;
//	}

}
