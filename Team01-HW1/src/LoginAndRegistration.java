

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;





import classes.UserUtils;

/**
 * Servlet implementation class LoginAndRegistration
 * This is the global class that handles logging in, logging out, and registration
 */
public class LoginAndRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String type;
	String user;
	String pass;
	String user2;
	String pass2;
	String firstname;
	String lastname;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginAndRegistration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * This do get needs a type parameter to tell the servlet what to do, "login" "logout" "register"
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String valid="false";
				
		//JSONObject json = new JSONObject();
		
		
		if(!request.getParameter("type").toString().trim().equals("")){
			   type = request.getParameter("type").toString();
			   //LOGIN
			   if(type.equalsIgnoreCase("login")){
				  valid = LoginUser(request,response);
				  
				  if(Boolean.valueOf(valid)){
					  //Successful login
					  Cookie loginCookie = new Cookie("user",request.getParameter("user"));
					  //setting cookie to expiry in 30 mins
					  loginCookie.setMaxAge(30*60);
					  response.addCookie(loginCookie);
					  response.getWriter().write("FlightSearchQuery.jsp");
					 
				  } else{
					  response.getWriter().write("failed");
				
				  }
				  
				  
			   } else  if(type.equalsIgnoreCase("register")){
				   //REGISTERING
				   valid = RegisterUser(request,response);
				   if(Boolean.valueOf(valid)){
						  
					   response.getWriter().write("Login.jsp");
					  } else {
						  //Failed login
						  // Set response content type
					      response.setContentType("text/html");
					      response.getWriter().write("failed");
					  }
			   } else if(type.equalsIgnoreCase("logout")){
				  //LOGGING OUT
			        //invalidate the session if exists
				   Cookie cookie = new Cookie("user",null); // Not necessary, but saves bandwidth.
				   
				   cookie.setValue("");
				   cookie.setMaxAge(0); // Don't set to -1 or it will become a session cookie!
				   response.addCookie(cookie);
				   
			        //no encoding because we have invalidated the session	
			        response.getWriter().write("Login.jsp");
			   }
			   
			   
			   
			  } else {
				  //error out
				  
			  }
		
			
	}
	
	public String LoginUser(HttpServletRequest req, HttpServletResponse res){
		String valid = "false";
		UserUtils util = new UserUtils(getServletContext());
	
		valid = String.valueOf(util.userExist(req.getParameter("user"), req.getParameter("pass")));
		return valid;
	}
	public String RegisterUser(HttpServletRequest req, HttpServletResponse res){
		String  valid="false";
		UserUtils util = new UserUtils(getServletContext());
		valid = String.valueOf(util.addUser(req.getParameter("user"), req.getParameter("pass")));
		
		return valid;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
