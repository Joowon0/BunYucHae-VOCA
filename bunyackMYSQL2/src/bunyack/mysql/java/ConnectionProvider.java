package bunyack.mysql.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
	/*
	public static Connection getRemoteConnection() {
		try {
      String jdbcUrl = "jdbc:mysql://aas3c4bl6pme2d.clh3fh5mumll.us-west-2.rds.amazonaws.com:3306/ebdb?user=rnwhdghl&password=12345678";
	  //String jdbcUrl = "jdbc:mysql://localhost:3306/bunyack&password=alfzmxlWid";
      Connection con = DriverManager.getConnection(jdbcUrl);
      return con;
    }
    catch (SQLException e) { 	
	}
    return null;
  }*/
	public static Connection getRemoteConnection() {
	    if (System.getProperty("RDS_HOSTNAME") != null) {
	      try {
	      Class.forName("org.postgresql.Driver");
	      String dbName = System.getProperty("RDS_DB_NAME");
	      String userName = System.getProperty("RDS_USERNAME");
	      String password = System.getProperty("RDS_PASSWORD");
	      String hostname = System.getProperty("RDS_HOSTNAME");
	      String port = System.getProperty("RDS_PORT");
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
