
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.ResultSet;

import classes.JDBCHelper;
import classes.UserUtils;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int nUser_id = -1;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
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
		//Validate input (check whitelist)
		if (!confirmWhitelistSaftey(request)){
			response.setContentType("text/html");
			response.getWriter().write("invalid");
		}else{
			String valid = "false";
			String type;
			// TODO Auto-generated method stub

			if (!request.getParameter("type").toString().trim().equals("")) {
				type = request.getParameter("type").toString();
				boolean remember = Boolean.valueOf(request.getParameter("checked"));
				if (type.equalsIgnoreCase("login")) {

					valid = LoginUser(request, response);
					if (Boolean.valueOf(valid)) {
						HttpSession session = request.getSession();
						session.setAttribute("user", request.getParameter("user"));
						session.setMaxInactiveInterval(30*60);
						
						// Successful login
						Cookie loginCookie = new Cookie("user",
						request.getParameter("user"));
						if(remember){
							Cookie email = new Cookie("email",request.getParameter("user"));
							Cookie password = new Cookie("password",request.getParameter("pass"));
							email.setMaxAge(30 * 24 * 3600);
							password.setMaxAge(30 * 24 * 3600);
							response.addCookie(email);
							response.addCookie(password);
						} else {
							Cookie email = new Cookie("email",request.getParameter("user"));
							Cookie password = new Cookie("password",request.getParameter("pass"));
							email.setMaxAge(0);
							password.setMaxAge(0);
							response.addCookie(email);
							response.addCookie(password);
						}
						// setting cookie to expiry in 30 mins
						loginCookie.setMaxAge(30 * 60);
						response.addCookie(loginCookie);
						
						response.getWriter().write("FlightSearchQuery.jsp");

					} else {
						response.getWriter().write("failed");

					}
				} else if (type.equalsIgnoreCase("logout")) {
					HttpSession session = request.getSession();
					session.invalidate();
					// no encoding because we have invalidated the session
					response.getWriter().write("Login.jsp");
				}

			} else {

			}		
		}
	}

	private final static Pattern VALID_TEXT_FIELD_PATTERN = Pattern.compile("[A-Za-z0-9-()@&\\.,\\s]*");
	private boolean confirmWhitelistSaftey(HttpServletRequest request){
		String username = request.getParameter( "user" );
		String password = request.getParameter( "pass" );
		String type = request.getParameter( "type" );
		
		//Do not check input when logging out
		if (type.equalsIgnoreCase("logout")){
			return true;
		}
	
 		if ( !VALID_TEXT_FIELD_PATTERN.matcher(username).matches())  {
 			return false;
 		}

 		if ( !VALID_TEXT_FIELD_PATTERN.matcher(password).matches())  {
 			return false;
 		}
	 		
		return true;
	}
	
	public String LoginUser(HttpServletRequest req, HttpServletResponse res) {


		ArrayList<Object> sqlParam = new ArrayList<Object>();
		sqlParam.add(req.getParameter("user"));
		sqlParam.add(req.getParameter("pass"));

		boolean result = LoginUserDB(sqlParam);

		if (result){
			HttpSession session = req.getSession();
			session.setAttribute("userid", nUser_id );
			session.setMaxInactiveInterval(30 * 60);
			return "true";
		}else {
			return "false";
		}

		}


		public boolean LoginUserDB(ArrayList<Object> sqlParam){
			JDBCHelper jdbc = new JDBCHelper();
			jdbc.connectToTeamDB();
			String query = "SELECT user_id FROM users WHERE email = ? AND password = ?;";
	
			ResultSet rs = (ResultSet) jdbc.queryDB(query, sqlParam);
			
	
			if (rs != null) {
			try {
			if (rs.next()){
				nUser_id = rs.getInt("user_id");
				jdbc.closeConnection();
				return true;
			}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			}
			return false;
	}
}
