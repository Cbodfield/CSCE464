

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import classes.JDBCHelper;
import classes.ResultSetConverter;

/**
 * Servlet implementation class ViewAndBook
 */
public class ViewAndBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		// TODO Auto-generated method stub
	}

	
	public JSONArray getTransactionDetails(String sFlightNumber, int nRequestedSeats) {
		ArrayList<Object> sqlParam = new ArrayList<Object>();
		
		sqlParam.add(sFlightNumber);
		sqlParam.add(nRequestedSeats);

		JDBCHelper jdbc = new JDBCHelper();
		jdbc.connectToCSE464DB();
		String query = "SELECT * FROM flights JOIN planes ON flights.plane = planes.id WHERE flights.id = ?;";

		ResultSet rs = jdbc.queryDB(query, sqlParam);
		
		
		JSONArray results = new JSONArray();
		try {
			results = ResultSetConverter.convert(rs);
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
