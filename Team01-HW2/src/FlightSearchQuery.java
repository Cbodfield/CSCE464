
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		// TODO Auto-generated method stub
	}

	// TODO - the logic in this class will likely be more entirely into the
	// corresponding servlets

	// Do we want to move the individual methods into their corresponding
	// servlet files?

	// Helper class that dynamically builds up where clause in queries based on
	// parameters passed in
	// this will allow searching with empty params
	// TODO - test - also may need to be careful on how JOINs are built. will
	// worry about later.
	private String buildQuery(String sbQueryBase, ArrayList<String> sFields,
			ArrayList<Object> oParams) {
		boolean bParamFound = false;
		StringBuilder sb = new StringBuilder();
		sb.append(sbQueryBase);

		for (int i = 0; i < sFields.size(); i++) {
			if (!oParams.get(i).toString().isEmpty()) {
				if (bParamFound) {
					sb.append(" AND (" + sFields.get(i).toUpperCase()
							+ " = ? )");
				} else {
					sb.append(" WHERE (" + sFields.get(i).toUpperCase()
							+ " = ? )");
					bParamFound = true;
				}
			}
		}

		sb.append(";");

		return sb.toString();
	}

	// TODO - not finished - returns 2 dimensional array of objects - like a
	// data table
	public ArrayList<ArrayList<Object>> getSearchResults(String sSource,
			String sDestination, String sTravelDate, String sNumOfSeats,
			String sClass) {
		JDBCHelper jdbc = new JDBCHelper();
		jdbc.connectToCSE464DB();

		ArrayList<Object> param = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> results = new ArrayList<ArrayList<Object>>();
		ArrayList<String> sFields = new ArrayList<String>();
		ArrayList<Object> oParams = new ArrayList<Object>();

		sFields.add("Source");
		sFields.add("Destination");
		sFields.add("Date"); // fix?
		sFields.add("Seats"); // fix?
		sFields.add("Class"); // fix?

		oParams.add(sSource);
		oParams.add(sDestination);
		oParams.add(sTravelDate);
		oParams.add(sNumOfSeats);
		oParams.add(sClass);

		String sQuery = buildQuery("SELECT * FROM FLIGHTS", sFields, oParams);

		ResultSet rs1 = jdbc.queryDB(sQuery, param);

		try {
			if (rs1 != null) {
				while (rs1.next()) {
					ArrayList<Object> temp = new ArrayList<Object>();
					/* TODO - correct db to get date and stops count? */
					/* TODO - is Class a parameter to the db? */
					temp.add(rs1.getObject("FLIGHTNUMBER")); // flight number
					temp.add(rs1.getObject("DATE")); // flight date?
					temp.add(rs1.getObject("DEPARTURE")); // departure time,
															// date, or both?
					temp.add(rs1.getObject("ARRIVAL")); // arrival time, date,
														// or both?
					temp.add(rs1.getObject("")); // number of stops
					temp.add(rs1.getObject("COST")); // cost

					results.add(temp);
				}
			}
			jdbc.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return results;
	}

}
