package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JDBCHelper {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	/**
	 * Before making a database call, call either connectToCSE464DB() or connectToTeamDB() to initiate the connection. 
	 * Be sure to call closeConnection() when finished.
	 */	
	public JDBCHelper(){
		this.conn = null;
		this.ps = null;
		this.rs = null;
	}
	
	/**
	 * Connects to the CSE464 database
	 * @return true when connection was successful
	 */
	public boolean connectToCSE464DB(){
		this.conn = this.initiateConnection("cse.unl.edu", "cse464", "emitchel", "camaro"); 
		if (this.conn == null){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * Connects to the Team01 database
	 * @return true when connection was successful
	 */
	public boolean connectToTeamDB(){
		this.conn = this.initiateConnection("cse.unl.edu", "cbodfie", "cbodfie", "hr8Kc3");
		if (this.conn == null){
			return false;
		}else{
			return true;
		}
	}
	

	/**
	 * This class loads the MySQL Driver and Connects to the entered database.
	 * @return A live connection or null
	 */
	private Connection initiateConnection(String host, String db, String user, String password){
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
	public <T> ResultSet LIKEqueryDB(String query, ArrayList<T> sqlParam){
		try{
			this.ps = conn.prepareStatement(query);

			int i = 1;
			if(sqlParam !=null){
				for (T a : sqlParam){
					//System.out.println(a.getClass());
					if (a.getClass() == String.class){
						this.ps.setString(i, (String)"%" +a+"%");
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
			}
			this.rs = this.ps.executeQuery();
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		return this.rs;
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
			if(sqlParam !=null){
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
			}
			this.rs = this.ps.executeQuery();
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		return this.rs;
	}
	
	
	public int insertDB(String query, ArrayList<Object> sqlParam){
		int rowsAffected;
		try{
			this.ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

						
			int i = 1;
			for (Object a : sqlParam){
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
			rowsAffected = this.ps.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
			return -1;
		}
		return rowsAffected;
	}
	
	
	/**
	 * Cleans up all resources and closes the connection
	 */
	public void closeConnection(){
		try {
			if(this.rs != null /*&& !this.rs.isClosed()*/)
				this.rs.close();
			if(this.ps != null /*&& !this.ps.isClosed()*/)
				this.ps.close();
			if(this.conn != null /*&& !this.conn.isClosed()*/)
				this.conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			//throw new RuntimeException(e);
		}
		
	}

}
