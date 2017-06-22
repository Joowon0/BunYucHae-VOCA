package bunyack.mysql.java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class accountHelper {
	// create table
	static public void createTable() throws SQLException {
        String sql = " CREATE TABLE IF NOT EXISTS accounts("
        		+ "  _id    INTEGER NOT NULL auto_increment PRIMARY KEY, "
        		+ "  usrIDs CHAR(30) CHARACTER SET utf8 NOT NULL UNIQUE, "
        		+ "  pwd    CHAR(30) CHARACTER SET utf8 NOT NULL); ";
        MYSQLdbHelper.execSQL(sql);
    }
    
	static public void dropTable()  throws SQLException {
        String sql = " DROP TABLE IF EXISTS accounts; ";
        MYSQLdbHelper.execSQL(sql);
    }
    
	static public void insertNewAccount(String idsIn, String pwdIn) throws SQLException {
        String sql = " INSERT INTO accounts (usrIDs, pwd) VALUES ( '" + idsIn + "', '" + pwdIn + "' );";
        MYSQLdbHelper.execSQL(sql);
    }
    
	static public void deleteAccount(String idsIn) throws SQLException {
    	String sql = " DELETE FROM accounts "
    			+ " WHERE usrIDs = " + idsIn + "; ";
    	MYSQLdbHelper.execSQL(sql);
    }
	
	static public int getIDNum(String idsIn) throws SQLException {
		Statement stmt = null;
    	Connection con;
    	int result = -1;
	    try {
	    	con = ConnectionProvider.getRemoteConnection();
	        stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(" SELECT _id FROM accounts WHERE usrIDS = " + idsIn + "; ");
	        if (rs.next())
	        	result = rs.getInt("_id");       
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } catch (Exception e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { stmt.close(); }
	    }
	    return result;
	}
    
    // return true if password is right
	static public boolean checkPwd(String idsIn, String pwdIn) throws SQLException {
    	Statement stmt = null;
    	Connection con;
    	boolean result = false;
	    try {
	    	con = ConnectionProvider.getRemoteConnection();
	        stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(" SELECT pwd FROM accounts WHERE usrIDS = " + idsIn + "; ");
	        rs.next();
	        if (pwdIn == rs.getString("pwd"))
	        	result = true;	        
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } catch (Exception e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { stmt.close(); }
	    }
	    return result;
    }
    
	static public void changePwd(String idsIn, String pwdIn) throws SQLException {
    	String sql = " UPDATE accounts SET pwd = '" + pwdIn + "' WHERE usrIDs = '" + idsIn + "' );";
    	MYSQLdbHelper.execSQL(sql);
    }
}
