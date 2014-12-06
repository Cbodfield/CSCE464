

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import classes.JDBCHelper;

import com.mysql.jdbc.ResultSet;

/**
 * Servlet implementation class ShoppingCart
 */
public class ShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingCart() {
        super();
        
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
		String action = request.getParameter("action");
		//Getting the cookie associated with userid
		String userid = String.valueOf(request.getSession().getAttribute("userid"));
		String cartList = "";
		Boolean found = false;
//		
//		if( cookies != null ){
//			   for (int i = 0; i < cookies.length; i++){
//			          cookie = cookies[i];
//			          if(cookie.getName().contains(userid + "LIST")){
//			        	  cartList=cookie.getValue();
//			        	  cookieFound=true;
//			          }
//			   }
//		}
		
		
		Object o =request.getSession().getAttribute(userid + "LIST");
		HttpSession session = request.getSession();
		if(o != null && !String.valueOf(o).equals("")){
			cartList = String.valueOf(o);
			found=true;
		} else {
			cartList="";
		}
		if(action.equals("add")){
			//ADDING TO THE SHOPPING CART
			String flightID = request.getParameter("flightID");
			String seats = request.getParameter("seats");
			String cost = request.getParameter("cost").replace("$", "");
			String seatType= request.getParameter("class");
			cost = String.valueOf(Integer.valueOf(cost) * Integer.valueOf(seats));
			
			//Now iterate through the list to make sure the 
			//flight isn't already added
			
			if(!cartList.contains(flightID)){
				if(!cartList.trim().equals("") && found){
					//It exists, now add to it
					cartList +="="+flightID+","+seats+","+cost+","+seatType;
				} else {
					//Doesn't exit, make one
					cartList =flightID+","+seats+","+cost+","+seatType;
				}
				
				
				session.setAttribute(userid +"LIST", cartList);
				session.setMaxInactiveInterval(30*60);
				
				response.getWriter().write("good");
			} else {
				response.getWriter().write("Already Added");
			}
		} else if(action.equals("get") ){
			if(cartList.trim().equals("")){
				//request.setAttribute("flights", "");
				session.setAttribute("flights", "");
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/ShoppingCart.jsp;jsessionid="+session.getId().toString());
				rd.forward(request, response);
			} else {
				String jsonifiedFlights = GetShoppingCartFlights(cartList);
				//request.setAttribute("flights", jsonifiedFlights);
				session.setAttribute("flights", jsonifiedFlights);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/ShoppingCart.jsp;jsessionid="+session.getId().toString());
				rd.forward(request, response);
			}
		}
		
	}
	
	public String GetShoppingCartFlights(String sListOfFlights){
		JSONArray arrReturn = new JSONArray();
		
		String[] flights = sListOfFlights.split("=");
		for(int i=0;i<flights.length; i++){
			String[] params = flights[i].split(",");
			String id=params[0];
			String seats = params[1];
			String cost = params[2].replace("$", "");
			String seatType = params[3];
			
			ArrayList<Object> sqlParam = new ArrayList<Object>();	
			
			sqlParam.add(id);
	
			JDBCHelper jdbc = new JDBCHelper();
			
			jdbc.connectToCSE464DB();
			
			String query = "SELECT * FROM flights JOIN planes ON flights.plane = planes.id WHERE flights.id = ?;";
	
			ResultSet rs = (ResultSet) jdbc.queryDB(query, sqlParam);
			JSONObject details = new JSONObject();
			//populate object
			//then determine if there are enough seats
			if(rs !=null){
				try {
					while(rs.next()){
						details.put("id", String.valueOf(rs.getObject("id")));
						details.put("operator",String.valueOf(rs.getObject("operator")));
						details.put("source",String.valueOf(rs.getObject("source")));
						details.put("destination",String.valueOf(rs.getObject("destination")));
						details.put("departure",String.valueOf(rs.getObject("departure")));
						details.put("arrival",String.valueOf(rs.getObject("arrival")));
						details.put("cost",cost);
						details.put("seats",seats);
						details.put("seatType", seatType);
					}
				} catch (SQLException e){
					
				} catch (JSONException e){
					
				}
			}
			
			arrReturn.put(details);
		}
	
		return arrReturn.toString();
		
	}

}
