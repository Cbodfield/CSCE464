import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

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

	//TODO - switch to return as boolean
	public String RegisterUser(HttpServletRequest req, HttpServletResponse res) {
		if(req.getParameter("user") !=null && req.getParameter("pass") !=null && !req.getParameter("user").equals("") && !req.getParameter("pass").equals("")){
				
			ArrayList<Object> params = new ArrayList<Object>();
			params.add(req.getParameter("user"));
			params.add(req.getParameter("pass"));
			
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
		String query = "INSERT INTO users (email, password) VALUES(?, ?);";

		int returnedKey = jdbc.insertDB(query, sqlParam);
		jdbc.closeConnection();
		
		if ((returnedKey == -1) || (returnedKey == 0)){
			return false;
		}else {
			if (CreateAccount(sqlParam)){
				return true;
			}else{
				return false;	
			}			
		}
	}
	
	public boolean CreateAccount(ArrayList<Object> sqlParam){
		JDBCHelper jdbc = new JDBCHelper();
		jdbc.connectToTeamDB();
		
		String userid = GetUserID(sqlParam);
		String query = "INSERT INTO accounts (holder_id, routing_number, balance) VALUES(?, ?, ?);";

		ArrayList<Object> sql = new ArrayList<Object>();
		sql.add(userid);
		sql.add((new Random()).nextInt(1000000));
		sql.add((new Random()).nextInt(5000));
		
		int returnedKey = jdbc.insertDB(query, sql);
		jdbc.closeConnection();
		
		if ((returnedKey == -1) || (returnedKey == 0)){
			return false;
		}else {
			return true;
		}
	}
	
	public String GetUserID(ArrayList<Object> sqlParam){
		JDBCHelper jdbc = new JDBCHelper();
		jdbc.connectToTeamDB();
		String query = "SELECT user_id FROM users WHERE email = ? AND password = ?;";
		int n;
		ResultSet rs = (ResultSet) jdbc.queryDB(query, sqlParam);
		

		if (rs != null) {
		try {
		if (rs.next()){
			n = rs.getInt("user_id");
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
	
	
	
}