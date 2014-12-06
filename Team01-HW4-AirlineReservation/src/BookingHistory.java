import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import classes.JDBCHelper;
import classes.ResultSetConverter;

import com.mysql.jdbc.ResultSet;

/**
 * Servlet implementation class BookingHistory
 */
public class BookingHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookingHistory() {
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
		
		
		HttpSession session = request.getSession();
		boolean isnew = session.isNew();
		
		String userid = request.getSession().getAttribute("userid").toString();
		if (userid != null){
			JSONArray output = LoadBookingHistoryDB(userid);
			response.getWriter().write(output.toString());
		}else{
			response.getWriter().write("failed");
			//error out????!?!??
		}
	}

	public JSONArray LoadBookingHistoryDB(String userid) {
		JDBCHelper jdbc = new JDBCHelper();
		jdbc.connectToTeamDB();
		String query = "SELECT * FROM bookings WHERE user_id = ?;";

		ArrayList<Object> sqlParam = new ArrayList<Object>();
		sqlParam.add(userid); 
		
		ResultSet rs = (ResultSet) jdbc.queryDB(query, sqlParam);

		JSONArray results = new JSONArray();
		try {
			while(rs.next()){
				JSONObject j = new JSONObject();
				j.put("date",String.valueOf(rs.getObject("date_of_booking")));
				j.put("seats",String.valueOf(rs.getObject("number_of_seats")));
				j.put("id", String.valueOf(rs.getObject("booking_id")));
				j.put("flightid", String.valueOf(rs.getObject("flight_id")));
				j.put("cost",String.valueOf(rs.getObject("total_cost")));
				results.put(j);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			jdbc.closeConnection();
		}

		return results;
	}

}