package bunyack.mysql.java;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.oracle.tutorial.jdbc.JDBCTutorialUtilities;

public class MYSQLdbHelper {
	// execute SQL queries
	static public void execSQL(String sql) throws SQLException {
		Connection con;
		Statement stmt = null;
        try {
        	con = ConnectionProvider.getRemoteConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            JDBCTutorialUtilities.printSQLException(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
	}
	
	static public void createTables() throws SQLException {
		accountHelper.createTable();
		tagListHelper.createTable();
		textListHelper.createTable();
		wordListHelper.createTable();
	}
	static public void dropTables() throws SQLException {
		accountHelper.dropTable();
		tagListHelper.dropTable();
		textListHelper.dropTable();
		wordListHelper.dropTable();
	}
	
	public static void main(String[] args) {
		try {
			System.out.println("asdf");
			createTables();
			System.out.println("asdf");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("fail: exception");
			e.printStackTrace();
		}
	}
}
