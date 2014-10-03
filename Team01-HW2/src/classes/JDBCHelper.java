package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JDBCHelper {
	
	public Connection conn;
	public PreparedStatement ps;
	public ResultSet rs;

	public JDBCHelper(){
		this.conn = this.initiateConnection("cse.unl.edu", "cbodfie", "cbodfie", "hr8Kc3");
		this.ps = null;
		this.rs = null;
	}

	/***
	 * Make sure to close your connection!!!
	 */
/*	private static void example() {
		JDBCHelper jdbc = new JDBCHelper();
		ArrayList<Object> param =  new ArrayList<Object>();
		*//**
		param.add("Hello");
		param.add(35767);
		param.add(4.0);
		param.add(Timestamp.valueOf("2014-09-30 11:41:00"));
		 *//*
		param.add(35767);
		ResultSet rs1 = jdbc.queryDB("SELECT flights.arrival AS arrival FROM flights JOIN planes ON flights.flightnumber=planes.id WHERE planes.flightnumber=?", param);

		try {
			if (rs1 != null){
				while (rs1.next()){
					System.out.println(rs1.getTimestamp("arrival"));
				}
			}
			jdbc.conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/


	/**
	 * This class loads the MySQL Driver and Connects to the entered database.
	 * @return A live connection or null
	 */
	public Connection initiateConnection(String host, String db, String user, String password){

		Connection dbConnection = null;

		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//System.out.println("Driver Loaded");
		}catch(Exception x){
			System.out.println("Unable to load the driver class");
		}


		try{
			String connString = String.format("jdbc:mysql://%s:3306/%s?user=%s&password=%s", host, db, user, password);
			dbConnection = DriverManager.getConnection(connString);
			//System.out.println("Connected to Database");
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Couldn't get Connection");
			return null;
		}
		return dbConnection;
	}

	/**
	 * 
	 * 
	 * @param query A Select Statement.  Use ? for parameters and the sqlParam parameters to pass in values. 
	 * @param sqlParam An ArrayList of objects of parameters for the Select Statement.
	 * @return A resultset if the query is successful, else null
	 */
	public <T> ResultSet queryDB(String query, ArrayList<T> sqlParam){
		try{
			this.ps = conn.prepareStatement(query);

			int i = 1;
			for (T a : sqlParam){
				//System.out.println(a.getClass());
				if (a.getClass() == String.class){
					this.ps.setString(i, (String)a);
					//System.out.println(String.format("I'm a String!  %d - %s", i, (String) a));
				}else if(a.getClass() == Integer.class){
					this.ps.setInt(i, (Integer)a);
					//System.out.println(String.format("I'm an Integer!  %d - %d", i, (Integer) a));
				}else if(a.getClass() == Double.class){
					this.ps.setDouble(i, (Double)a);
					//System.out.println(String.format("I'm a Double!  %d - %f", i, (Double) a));
				}else if (a.getClass() == Timestamp.class){
					this.ps.setTimestamp(i, (Timestamp)a);
					//System.out.println(String.format("I'm a DateTime!  %d - %s", i, a.toString()));
				}
				i++;
			}
			this.rs = this.ps.executeQuery();
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		return this.rs;
	}
	
	public void closeConnection(){
		try {
			if(this.rs != null && !this.rs.isClosed())
				this.rs.close();
			if(this.ps != null && !this.ps.isClosed())
				this.ps.close();
			if(this.conn != null && !this.conn.isClosed())
				this.conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
