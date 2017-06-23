

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
	/*
	private static String username = "postgres";
	private static String password = "dktTk";
	private static String url = "jdbc:postgresql://localhost:5432/PNUIPS";
	
	private static Connection conn = null;
	
	public static Connection getConnection() throws Exception {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, username, password);
			} catch (Exception e) {
				// TODO Auto-generated catch block				e.printStackTrace();
		}
		return conn;
	}*/
	public static Connection getRemoteConnection() {
	    if (System.getProperty("rnwhdghl") != null) {
	      try {
	      Class.forName("db.t1.micro");
	      String dbName = System.getProperty("bunyakDB.db");
	      String userName = System.getProperty("rnwhdghl");
	      String password = System.getProperty("1234567890");
	      String hostname = System.getProperty("rnwhdghl");
	      String port = System.getProperty("3306");
	      String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
	      //logger.trace("Getting remote connection with connection string from environment variables.");
	      Connection con = DriverManager.getConnection(jdbcUrl);
	      //logger.info("Remote connection successful.");
	      return con;
	    }
	    catch (ClassNotFoundException e) { 
	    	//logger.warn(e.toString());
	    	}
	    catch (SQLException e) { 
	    	//logger.warn(e.toString());
	    	}
	    }
	    return null;
	  }
}
