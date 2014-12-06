import java.io.IOException;
import java.sql.ResultSet;
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

/**
 * Servlet implementation class FlightSearchResults
 */
public class FlightSearchResults extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FlightSearchResults() {
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
		// TODO Auto-generated method stub
		String flightID = (String) request.getParameter("f");
		String cost = String.valueOf(request.getParameter("c"));
		String stops = String.valueOf(request.getParameter("s"));
		if( flightID != null){
			JSONArray output = getFlightDetails(flightID, cost, stops);
			//request.setAttribute("details", output.toString());
			HttpSession session = request.getSession();
			session.setAttribute("details", output.toString());
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/ViewBook.jsp;jsessionid="+session.getId());
			rd.forward(request,response);
		} else {
			//error out????!?!??
		}
	}

	public JSONArray getFlightDetails(String sFlightNumber, String cost, String stops) {
		ArrayList<Object> sqlParam = new ArrayList<Object>();
		sqlParam.add(sFlightNumber);

		JDBCHelper jdbc = new JDBCHelper();
		jdbc.connectToCSE464DB();
		String query = "select * from flights left join planes on flights.plane=planes.id where flights.id = ?;";

		ResultSet rs = jdbc.queryDB(query, sqlParam);
		JSONArray results = new JSONArray();
		try {
			
			results = ResultSetConverter.convert(rs);
			//adding cost, since it isn't stored in the database
			//we must grab the first objject(should only be one)
			//then append to it, then readd it to array
			
			JSONObject p = (JSONObject)results.get(0);
			p.put("cost",cost);
			p.put("stops",stops);
			results = new JSONArray();
			results.put(p);
//			results.put((new JSONObject().put("cost",cost)));
//			results.put((new JSONObject().put("stops",stops)));
					
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
