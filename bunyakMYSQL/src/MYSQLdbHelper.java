import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
            //JDBCTutorialUtilities.printSQLException(e);
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
            if (stmt != null) { stmt.close(); }
        }
	}
	
	static public void createTable() throws SQLException {
		
	}
}
