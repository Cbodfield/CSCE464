

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSet;

import classes.JDBCHelper;

/**
 * Servlet implementation class TransactionConfirmation
 */
public class TransactionConfirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionConfirmation() {
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
		
		
		//TODO - pass in a negative value for the numeric input/cost
		public boolean ConfirmAccountDB(ArrayList<Object> sqlParam, int nCost){
			JDBCHelper jdbc = new JDBCHelper();
			jdbc.connectToTeamDB();
			String query = "SELECT balance FROM accounts WHERE account_id = ?, routing_number = ?;";

			ResultSet accountResults = (ResultSet) jdbc.queryDB(query, sqlParam);
			int nBalance = -1;
			jdbc.closeConnection();
			
			try {
				if (accountResults.next()){
					nBalance = accountResults.getInt(0);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
			if (nBalance >= nCost){
				int nNewBalance = nBalance - nCost;
				sqlParam.remove(1); //Removes routing number from params for next db calls
				
				if (ExecuteTransactionDB(sqlParam, nNewBalance)){
					if (UpdateBookingHistory(sqlParam, nCost)){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}	
			}else{
				return false;
			}
		}
		
		//TODO - test
		public boolean ExecuteTransactionDB(ArrayList<Object> sqlParam, int nNewBalance){
			JDBCHelper jdbc = new JDBCHelper();
			jdbc.connectToTeamDB();
			
			String query = "UPDATE accounts SET balance = ? WHERE account_id = ?;";
			sqlParam.add(0, nNewBalance); //Adds new balance to the head of the params
					
			int nRowsAffected = jdbc.updateDB(query, sqlParam);
			jdbc.closeConnection();
			
			if (nRowsAffected > 0){
				return true;
			}else{
				return false;
			}
		}

		
		//TODO - test
		public boolean UpdateBookingHistory(ArrayList<Object> sqlParam, int nCost){
			JDBCHelper jdbc = new JDBCHelper();
			jdbc.connectToTeamDB();
			
			String query = "INSERT INTO bookings (account_id, date_of_booking, flight_id, number_of_seats, user_id, total_cost) VALUES(?, ?, ?, ?, ?, ?);";
			
			//Build current date
			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			Date dateobj = new Date();
			
			//Param 1 is account id
			sqlParam.add(df.format(dateobj)); //Date of booking
			sqlParam.add(""); //TODO - add flight id from session
			sqlParam.add(""); //TODO - add number of seats from session
			sqlParam.add(""); //TODO - add user id from session
			sqlParam.add(nCost);
			
			int nRowsAffected = jdbc.insertDB(query, sqlParam);
			jdbc.closeConnection();
			
			if (nRowsAffected > 0){
				return true;
			}else{
				return false;
			}
		}
	
}
