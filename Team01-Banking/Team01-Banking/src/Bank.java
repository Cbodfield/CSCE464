

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import classes.JDBCHelper;
import classes.Results;

import com.mysql.jdbc.ResultSet;

/**
 * Servlet implementation class Bank
 */
public class Bank extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bank() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accountid = request.getParameter("accountnumber");
		String routingnumber = request.getParameter("routingnumber");
		String sCost = request.getParameter("cost");
		int nCost = -1;
		Results results = new Results();
		
		ArrayList<Object> sqlParam = new ArrayList<Object>();
		
		if ((accountid != null) && (routingnumber != null) && (sCost != null)){
			sqlParam.add(accountid);
			sqlParam.add(routingnumber);
			nCost = Integer.parseInt(sCost);
			results = ConfirmAccountDB(sqlParam, nCost , request);
		} else {
			results.bSuccess = false;
			results.sMessage = "Parameters not found";
		}

		JSONObject oj = new JSONObject();
		try{
			oj.put("bSuccess", results.bSuccess);
			oj.put("sMessage", results.sMessage);
		} catch (JSONException e){			
		}

		response.getWriter().write(oj.toString());		
	}
	
	
	public Results ConfirmAccountDB(ArrayList<Object> sqlParam, int nCost, HttpServletRequest request){
		Results results = new Results();
		JDBCHelper jdbc = new JDBCHelper();
		jdbc.connectToTeamDB();
		String query = "SELECT balance FROM accounts WHERE account_id = ? AND routing_number = ?;";

		ResultSet accountResults = (ResultSet) jdbc.queryDB(query, sqlParam);
		int nBalance = -1;
		
		try {
			while(accountResults.next()){
				nBalance = Integer.valueOf(String.valueOf(accountResults.getObject("balance")));
			}if(nBalance == -1){
				results.bSuccess = false;
				results.sMessage = "Incorrect account details";
				return results;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		if (nBalance >= nCost){
			int nNewBalance = nBalance - nCost;
			sqlParam.remove(1); //Removes routing number from params for next db calls
			sqlParam.add(0,nNewBalance);
			if (ExecuteTransactionDB(sqlParam)){
				sqlParam.remove(0);				
					results.bSuccess =  true;
			}else{
				results.bSuccess =  false;
				results.sMessage = "There was a problem executing the transaction";
			}	
		}else{
			results.bSuccess = false;
			results.sMessage = "Insufficient funds available";
		}
		jdbc.closeConnection();
		return results;
	}
	

	public boolean ExecuteTransactionDB(ArrayList<Object> sqlParam){
		JDBCHelper jdbc = new JDBCHelper();
		jdbc.connectToTeamDB();
		
		String query = "UPDATE accounts SET balance = ? WHERE account_id = ?;";
				
		int nRowsAffected = jdbc.updateDB(query, sqlParam);
		jdbc.closeConnection();
		
		if (nRowsAffected > 0){
			return true;
		}else{
			return false;
		}
	}
}





