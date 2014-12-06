import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSet;

import classes.JDBCHelper;

/**
 * Servlet implementation class Registration
 */
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String valid = "false";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Registration() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!confirmWhitelistSaftey(request)){
			response.setContentType("text/html");
			response.getWriter().write("invalid");
		}else{	
		// REGISTERING
			valid = RegisterUser(request, response);
			if (Boolean.valueOf(valid)) {
				response.getWriter().write("Login.jsp");
			} else {
				// Failed login
				// Set response content type
				response.setContentType("text/html");
				response.getWriter().write("failed");
			}
		}
	}

	private final static Pattern VALID_TEXT_FIELD_PATTERN = Pattern.compile("[A-Za-z0-9-()@&\\.,\\s]*");
	private boolean confirmWhitelistSaftey(HttpServletRequest request){
		String username = request.getParameter( "user" );
		String password = request.getParameter( "pass" );
		String firstname = request.getParameter( "firstName" );
		String lastname = request.getParameter( "lastName" );
		String organization = request.getParameter( "organization" );
		String address = request.getParameter( "address" );
	
 		if ( !VALID_TEXT_FIELD_PATTERN.matcher(username).matches())  {
 			return false;
 		}

 		if ( !VALID_TEXT_FIELD_PATTERN.matcher(password).matches())  {
 			return false;
 		}
	
 		if ( !VALID_TEXT_FIELD_PATTERN.matcher(firstname).matches())  {
 			return false;
 		}
 		
 		if ( !VALID_TEXT_FIELD_PATTERN.matcher(lastname).matches())  {
 			return false;
 		}
 		
 		if ( !VALID_TEXT_FIELD_PATTERN.matcher(organization).matches())  {
 			return false;
 		}

 		if ( !VALID_TEXT_FIELD_PATTERN.matcher(address).matches())  {
 			return false;
 		}
	 		
		return true;
	}
	
	//TODO - switch to return as boolean
	public String RegisterUser(HttpServletRequest req, HttpServletResponse res) {
		if(req.getParameter("user") !=null && req.getParameter("pass") !=null && !req.getParameter("user").equals("") && !req.getParameter("pass").equals("")){
				
			ArrayList<Object> params = new ArrayList<Object>();
			params.add(req.getParameter("user"));
			params.add(req.getParameter("pass"));
			params.add(req.getParameter("firstName"));
			params.add(req.getParameter("lastName"));
			
			String org = req.getParameter("organization");
			String address = req.getParameter("address");
						
			String orgID = GetOrganization(org,address);
			params.add(orgID);
			boolean result = RegisterUserDB(params);
			
			if (result){
				return "true";
			}else{
				return "false";
			}	
		} else {
			return "false";
		}
	}

	
	public boolean RegisterUserDB(ArrayList<Object> sqlParam){
		JDBCHelper jdbc = new JDBCHelper();
		jdbc.connectToTeamDB();
		String query = "INSERT INTO users (email, password, first_name, last_name, organization_id) VALUES(?, ?, ?, ?, ?);";

		int returnedKey = jdbc.insertDB(query, sqlParam);
		jdbc.closeConnection();
		
		if ((returnedKey == -1) || (returnedKey == 0)){
			return false;
		}else {
			if (CreateAccount(String.valueOf(returnedKey))){
				return true;
			}else{
				return false;	
			}			
		}
	}
	
	public boolean CreateAccount(String userid){
		JDBCHelper jdbc = new JDBCHelper();
		jdbc.connectToTeamDB();
		
		//String userid = GetUserID(sqlParam);
		String query = "INSERT INTO accounts (holder_id, routing_number, balance, pin) VALUES(?, ?, ?, ?);";

		ArrayList<Object> sql = new ArrayList<Object>();
		sql.add(userid);
		sql.add((new Random()).nextInt(1000000));
		sql.add((new Random()).nextInt(5000));
		  StringBuilder pinNum = new StringBuilder();
		  int basePin = (new Random()).nextInt(9999) ;
		  if (basePin < 10){
		  pinNum.append("000");
		  pinNum.append(basePin);	
		  }else if (basePin < 100){
		  pinNum.append("00");
		  pinNum.append(basePin);
		  }else if (basePin < 1000){
		  pinNum.append("0");
		  pinNum.append(basePin);	
		  } else {
		  pinNum.append(basePin);
		  }

		  sql.add(pinNum.toString());
		int returnedKey = jdbc.insertDB(query, sql);
		jdbc.closeConnection();
		
		if ((returnedKey == -1) || (returnedKey == 0)){
			return false;
		}else {
			return true;
		}
	}
	
	
	public String GetOrganization(String organization, String address){
		
		if (organization.equals("")){
			//Returns 1 which is the row with the default organization in the database
			return "1";
		}
		
		if (address.equals("")){
			address = "1234 Main Street, Lincoln NE";
		}
		
		ArrayList<Object> sqlParam = new ArrayList<Object>();
		sqlParam.add(organization);
		sqlParam.add(address);
		
		//Check if organization exists
		String checkedOrgID = CheckOrganization(sqlParam);
		
		if (checkedOrgID.equals("")){
			//Doesn't exist, add it
			return AddOrganization(sqlParam);
		}else{
			return checkedOrgID;
		}
	}
	
	public String CheckOrganization(ArrayList<Object> sqlParam){
		JDBCHelper jdbc = new JDBCHelper();
		jdbc.connectToTeamDB();
		String query = "SELECT organization_id FROM organization WHERE name = ? AND address = ?;";
		int n;
		ResultSet rs = (ResultSet) jdbc.queryDB(query, sqlParam);
		
		if (rs != null) {
			try {
				if (rs.next()){
					n = rs.getInt("organization_id");
					jdbc.closeConnection();
					return String.valueOf(n);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return "";
			}
		}
		return "";
	}
	
	public String AddOrganization(ArrayList<Object> sqlParam){
		JDBCHelper jdbc = new JDBCHelper();
		jdbc.connectToTeamDB();
		
		String query = "INSERT INTO organization (name, address) VALUES(?, ?);";
		
		int returnedKey = jdbc.insertDB(query, sqlParam);
		jdbc.closeConnection();
						
		return String.valueOf(returnedKey);
	}
	
}