

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import classes.JDBCHelper;
import classes.ResultSetConverter;

/**
 * Servlet implementation class ViewAndBook
 */
public class ViewAndBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JSONArray flightArray;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewAndBook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String flightid = (String)request.getParameter("flightid");
//		String cost = (String)request.getParameter("cost");
//		String seats = (String)request.getParameter("seats");
//		String c = (String)request.getParameter("class");
//		String stops = (String)request.getParameter("stops");
//		if(!badParam(seats) && !badParam(cost) && !badParam(flightid) && !badParam(c) && !badParam(stops)){
//			JSONObject ja = new JSONObject();
//			ja = getTransactionDetails(flightid, seats,c,cost,stops,seats);
//			request.setAttribute("details", ja.toString());
//			
//			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Transaction.jsp");
//			rd.forward(request, response);
//		}
		
		String flightInfo = (String)request.getParameter("flightInfo");
		
		try {
			flightArray = new JSONArray(flightInfo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			flightArray = null;
			e.printStackTrace();
		}      
		try{
			JSONObject returnObj = new JSONObject();
			if(!badParam(flightInfo)){                               //reference (essentially)
				String goodtogo = checkIfSeatsAvailableForEachFlight(flightArray);
				
				//add bSuccess and sMessage to flightArray
				if(goodtogo.trim().equals("")){
					//ALL FLIGHTS HAVE THE REQUESTED NUMBER OF SEATS
						returnObj.put("bSuccess", true);
						returnObj.put("sMessage", "");
						returnObj.put("flightArray", flightArray.toString().replace("\"","\\\""));
				} else {
					if(flightArray.length()>0){
						//we have some flights we can't add, lets make sure we still 
						//have flights
						returnObj.put("bSuccess", true);
						returnObj.put("sMessage", goodtogo);
						returnObj.put("flightArray", flightArray.toString().replace("\"","\\\""));
					} else {
						returnObj.put("bSuccess", false);
						returnObj.put("sMessage","None of the flights have enough space");
						returnObj.put("flightArray", "");
						//NO FLIGHTS ARE AVAILABLE TO CHECK OUT
						//RETURN BSUCCESS=FALSE
					}
				}
				
				request.setAttribute("flights", returnObj.toString());
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/ConfirmBooking.jsp");
				rd.forward(request, response);
		} else {
			request.setAttribute("flight", "");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/ConfirmBooking.jsp");
			rd.forward(request, response);
		}
		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	public String checkIfSeatsAvailableForEachFlight(JSONArray arr){
		boolean goodToGo=false;
		String sListOfFlightsWithoutEnoughSeats="";
		for(int i =0;i<arr.length();i++){
			try {
				JSONObject obj = arr.getJSONObject(i);
				String flightId= obj.getString("id");
				String seatType = obj.getString("seatType");
				Integer seatsRequested = Integer.valueOf(obj.getString("seats"));
				String seatTypeReserved="";
				String seatTypeCapacity="";
				if(seatType.contains("first")){
					seatTypeReserved = "first_class_reserved";
					seatTypeCapacity = "first_class_capacity";
				} else if (seatType.contains("economy")){
					seatTypeReserved = "economy_reserved";
					seatTypeCapacity = "economy_capacity";
				} else if (seatType.contains("business")){
					seatTypeReserved = "business_reserved";
					seatTypeCapacity = "business_capacity";
				}
				Integer seatTypeCapacityAmount = 0;
				Integer seatTypeReservedAmount = 0;
				
				ArrayList<Object> sqlParam = new ArrayList<Object>();		
				sqlParam.add(flightId);

				JDBCHelper jdbc = new JDBCHelper();
				
				jdbc.connectToCSE464DB();
				
				String query = "SELECT * FROM flights left JOIN planes ON flights.plane = planes.id WHERE flights.id = ?;";

				ResultSet rs = jdbc.queryDB(query, sqlParam);

				
				JSONObject details = new JSONObject();
				//populate object
				//then determine if there are enough seats
				if(rs !=null){
					try {
						while(rs.next()){
							seatTypeCapacityAmount = (int) rs.getObject(seatTypeCapacity);
							seatTypeReservedAmount = (int) rs.getObject(seatTypeReserved);
						}
					} catch (SQLException e) {
						
						
					}
				}
				
				
				
				
				goodToGo = (Integer.valueOf(seatTypeCapacityAmount) - Integer.valueOf(seatTypeReservedAmount)) >= Integer.valueOf(seatsRequested);
				
				if(!goodToGo){
					if(!sListOfFlightsWithoutEnoughSeats.trim().equals("")){
						sListOfFlightsWithoutEnoughSeats +=",";
					}
					sListOfFlightsWithoutEnoughSeats+=flightId.toString();
					//REMOVE FLIGHT IF NOT ENOUGH CAPACITY
					arr.remove(i);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				goodToGo=false;
				e.printStackTrace();
			}
			
		}
		flightArray = arr; //passed by reference part
		return sListOfFlightsWithoutEnoughSeats;
		
	}
	
	public boolean badParam(String p){
		return (p==null || p.trim().equals(""));
	}
	
	 
	
	public JSONObject getTransactionDetails(String sFlightNumber, String sRequestedSeats, String sClass, String cost, String stops,String seats) {
		String c="";
		String r="";

			if(sClass.equalsIgnoreCase("economy")){
				c= "economy_capacity";
				r="economy_reserved";
			} else if (sClass.equalsIgnoreCase("first")){
				c= "first_class_capacity";
				r="first_class_reserved";
			} else if(sClass.equalsIgnoreCase("business")){
				c= "business_capacity";
				r="business_reserved";
			}

		
		ArrayList<Object> sqlParam = new ArrayList<Object>();		
		sqlParam.add(sFlightNumber);

		JDBCHelper jdbc = new JDBCHelper();
		
		jdbc.connectToCSE464DB();
		
		String query = "SELECT * FROM flights JOIN planes ON flights.plane = planes.id WHERE flights.id = ?;";

		ResultSet rs = jdbc.queryDB(query, sqlParam);

		
		JSONObject details = new JSONObject();
		//populate object
		//then determine if there are enough seats
	if(rs !=null){
		try {
			while(rs.next()){
				details.put("id", String.valueOf(rs.getObject("id")));
				details.put("operator",String.valueOf(rs.getObject("operator")));
				details.put("source",String.valueOf(rs.getObject("source")));
				details.put("destination",String.valueOf(rs.getObject("destination")));
				details.put("departure",String.valueOf(rs.getObject("departure")));
				details.put("arrival",String.valueOf(rs.getObject("arrival")));
				details.put("cost",Integer.valueOf(cost) * Integer.valueOf(sRequestedSeats));
				details.put("stops",stops);
				details.put("seats",seats);
				int seatsRequested = Integer.valueOf(sRequestedSeats);
				int seatsReservered = Integer.valueOf( String.valueOf(rs.getObject(r)));
				int seatsTotal = Integer.valueOf(String.valueOf(rs.getObject(c)));
				if((seatsTotal-seatsReservered)>= seatsRequested){
					details.put("bSuccess", true);
					details.put("sMessage", "");
					
				} else {
					details.put("bSuccess", false);
					details.put("sMessage", "Not enough seats available");
					
				}
			}
						
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} else {
			
			
			try {
				details.put("bSuccess",false);
				details.put("sMessage", "Could not find flight");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return details;
	}
	
}
