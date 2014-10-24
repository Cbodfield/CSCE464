
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classes.UserUtils;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		String valid = "false";
		String type;
		// TODO Auto-generated method stub

		if (!request.getParameter("type").toString().trim().equals("")) {
			type = request.getParameter("type").toString();
			if (type.equalsIgnoreCase("login")) {

				valid = LoginUser(request, response);
				if (Boolean.valueOf(valid)) {
					HttpSession session = request.getSession();
					session.setAttribute("user", request.getParameter("user"));
					session.setMaxInactiveInterval(30*60);
					
					// Successful login
					Cookie loginCookie = new Cookie("user",
					request.getParameter("user"));
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

	public String LoginUser(HttpServletRequest req, HttpServletResponse res) {
		String valid = "false";
		UserUtils util = new UserUtils(getServletContext());

		valid = String.valueOf(util.userExist(req.getParameter("user"),
				req.getParameter("pass")));
		return valid;
	}
}
