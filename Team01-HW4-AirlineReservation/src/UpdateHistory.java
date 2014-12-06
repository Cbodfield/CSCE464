

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.ResultSet;

import classes.JDBCHelper;
import classes.Results;

/**
 * Servlet implementation class UpdateHistory
 */
public class UpdateHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateHistory() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accountid = request.getParameter("accountnumber");
		String sCost = request.getParameter("cost");
		int nCost = -1;
		Results results = new Results();
		
		ArrayList<Object> sqlParam = new ArrayList<Object>();
		
		if ((accountid != null) && (sCost != null)){
			sqlParam.add(accountid);
			nCost = Integer.parseInt(sCost);
			results.bSuccess = UpdateBookingHistory(sqlParam, nCost , request);
		} else {
			results.bSuccess = false;
			results.sMessage = "Parameters not found";
		}
		
		JSONObject oj = new JSONObject();
		try{
			oj.put("bSuccess", results.bSuccess);
			oj.put("sMessage", results.sMessage);
		} catch (JSONException e){			
		}
		if(results.bSuccess){
			Cookie cCartList = new Cookie(request.getSession().getAttribute("userid") + "LIST","");
			cCartList.setMaxAge(30 * 24 * 3600);
			response.addCookie(cCartList);
		}
		response.getWriter().write(oj.toString());
	}
	
	
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
