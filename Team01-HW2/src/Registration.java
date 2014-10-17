
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		//String valid = "false";
		//UserUtils util = new UserUtils(getServletContext());
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

		int rowsAffected = jdbc.insertDB(query, sqlParam);
		jdbc.closeConnection();
		
		if ((rowsAffected == -1) || (rowsAffected == 0)){
			return false;
		}else {
			return true;
		}
	}
	
	
}
