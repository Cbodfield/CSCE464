import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.ResultSet;

import classes.JDBCHelper;
import classes.Results;

/**
 * Servlet implementation class TransactionConfirmation
 */
public class TransactionConfirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       String n_cost;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionConfirmation() {
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
		// TODO Auto-generated method stub
		
		String accountid = request.getParameter("accountnumber");
		String routingnumber = request.getParameter("routingnumber");
		String sCost = request.getParameter("cost");
		n_cost = sCost;
		int nCost = -1;
		Results results = new Results();
		
		ArrayList<Object> sqlParam = new ArrayList<Object>();
		
		if ((accountid != null) && (routingnumber != null) && (sCost != null)){
			sqlParam.add(accountid);
			sqlParam.add(routingnumber);
			nCost = Integer.parseInt(sCost);
			results = ConfirmAccountDB(sqlParam, nCost , request);
		} else {
			results.bSuccess = false;
			results.sMessage = "Parameters not found";
		}

		
		if (results.bSuccess){
			
			//get details 
			results.oJSON =  GetFlightDetails(request);
			
		}else{
			//error out
			JSONObject oj = new JSONObject();
			try{
			oj.put("bSuccess", false);
			oj.put("sMessage", results.sMessage);
			results.oJSON = oj;
			} catch (JSONException e){
				
			}
		}
		request.setAttribute("details",results.oJSON.toString());
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/TransactionConfirmation.jsp");
		rd.forward(request,response);
		
	}
		
		public JSONObject GetFlightDetails(HttpServletRequest request){
			String id = request.getParameter("flightid");
			ArrayList<Object> sqlParam = new ArrayList<Object>();		
			sqlParam.add(id);

			JDBCHelper jdbc = new JDBCHelper();
			
			jdbc.connectToCSE464DB();
			
			String query = "SELECT * FROM flights JOIN planes ON flights.plane = planes.id WHERE flights.id = ?;";

			ResultSet rs = (ResultSet) jdbc.queryDB(query, sqlParam);

			
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
					details.put("cost",Integer.valueOf(n_cost));
					details.put("seats",String.valueOf(request.getParameter("seats")));
					details.put("bSuccess",true);
					details.put("sMessage", "");
				}
			} catch (SQLException e){
				
			} catch (JSONException e){
				
			}
		}
			return details;
		}
		public Results ConfirmAccountDB(ArrayList<Object> sqlParam, int nCost, HttpServletRequest request){
			Results results = new Results();
			JDBCHelper jdbc = new JDBCHelper();
			jdbc.connectToTeamDB();
			String query = "SELECT balance FROM accounts WHERE account_id = ? AND routing_number = ?;";

			ResultSet accountResults = (ResultSet) jdbc.queryDB(query, sqlParam);
			int nBalance = -1;
			
			try {
				while(accountResults.next()){
					nBalance = Integer.valueOf(String.valueOf(accountResults.getObject("balance")));
				}if(nBalance == -1){
					results.bSuccess = false;
					results.sMessage = "Incorrect account details";
					return results;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
			if (nBalance >= nCost){
				int nNewBalance = nBalance - nCost;
				sqlParam.remove(1); //Removes routing number from params for next db calls
				sqlParam.add(0,nNewBalance);
				if (ExecuteTransactionDB(sqlParam)){
					sqlParam.remove(0);
					if (UpdateBookingHistory(sqlParam, nCost, request)){
						results.bSuccess =  true;
					}else{
						results.bSuccess =  false;
						results.sMessage = "Unable to update Booking History";
					}
				}else{
					results.bSuccess =  false;
					results.sMessage = "There was a problem executing the transaction";
				}	
			}else{
				results.bSuccess = false;
				results.sMessage = "Insufficient funds available";
			}
			jdbc.closeConnection();
			return results;
		}
		
		//TODO - test
		public boolean ExecuteTransactionDB(ArrayList<Object> sqlParam){
			JDBCHelper jdbc = new JDBCHelper();
			jdbc.connectToTeamDB();
			
			String query = "UPDATE accounts SET balance = ? WHERE account_id = ?;";
			//sqlParam.add(0, nNewBalance); //Adds new balance to the head of the params
					
			int nRowsAffected = jdbc.updateDB(query, sqlParam);
			jdbc.closeConnection();
			
			if (nRowsAffected > 0){
				return true;
			}else{
				return false;
			}
		}

		
		//TODO - test
		public boolean UpdateBookingHistory(ArrayList<Object> sqlParam, int nCost, HttpServletRequest request){
			JDBCHelper jdbc = new JDBCHelper();
			jdbc.connectToTeamDB();
			
			String query = "INSERT INTO bookings (account_id, date_of_booking, flight_id, number_of_seats, user_id, total_cost) VALUES(?, ?, ?, ?, ?, ?);";
			
			//Build current date
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date dateobj = new Date();
							
			
			//Param 1 is account id
			sqlParam.add(df.format(dateobj)); //Date of booking
			sqlParam.add(request.getParameter("flightid")); //get flight id from session
			sqlParam.add(request.getParameter("seats")); //get number of seats from session
			sqlParam.add(request.getSession().getAttribute("userid")); //get add user id from session
			sqlParam.add(nCost);
			
			int nRowsAffected = jdbc.insertDB(query, sqlParam);
			jdbc.closeConnection();
			
			if (nRowsAffected > 0){
				return true;
			}else{
				return false;
			}
		}
	
}