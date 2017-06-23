package bunyack.mysql.java;

import java.sql.*;

/**
 * Created by joowon on 17. 6. 20.
 * THIS IS FOR MYSQL
 */

public class tagListHelper {
	static public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tagList ("
        		+ "  _id   INTEGER NOT NULL auto_increment, "
        		+ "  usrID INTEGER NOT NULL,"
        		+ "  date  TIMESTAMP, "
        		+ "  name  TEXT CHARACTER SET utf8, "
        		+ "  tNum  INTEGER DEFAULT 0, "
        		+ "  wNum  INTEGER DEFAULT 0, "
        		+ "  CONSTRAINT PRIMARY KEY ( _id, usrID), "
        		+ "  FOREIGN KEY (usrID) REFERENCES accounts(_id)); ";
        MYSQLdbHelper.execSQL(sql);
    }

	static public void dropTable()  throws SQLException {
        String sql = " DROP TABLE IF EXISTS tagList; ";
        MYSQLdbHelper.execSQL(sql);
    }

    // insert a new text with auto timestamp setting
	static public void insertNew(int usrIn, String wordIn) throws SQLException {
        String sql = " INSERT INTO tagList (usrID, name) VALUES (" + usrIn + ", '" + wordIn + "'); "; 
        MYSQLdbHelper.execSQL(sql);
    }

    // insert a new text with specified timestamp
    // used for backups
	static public void insert4Backup(int usrIn, String dateIn, String wordIn) throws SQLException {
        String sql = " INSERT INTO tagList (usrID, date, name) VALUES ('" + usrIn + "', '" + dateIn + "', '" + wordIn + "');";
        MYSQLdbHelper.execSQL(sql);
    }
    
/*
    public boolean removeById(Connection con, int idIn) throws SQLException {
        db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id FROM tagList where _id = " + idIn, null);

        if (cursor.moveToNext()) { // check if exists
            String sql = "DELETE FROM tagList " +
                    " WHERE _id = " + idIn + " ; ";

            cursor.close();
            db.execSQL(sql);
            db.close();
            return true;
        }
        else {
            System.out.println("cannot find matching id in tagList");
            cursor.close();
            db.close();
            return false;
        }
    }

    public boolean modifyNameById(Connection con, int idIn, String nameIn) throws SQLException {
        db = getWritableDatabase();
        String sql = " UPDATE tagList " +
                " SET date = '" + Tag.makeCurrDateInString() + "', " +
                " name = '" + nameIn + "' " +
                " WHERE _id = " + idIn + "; ";

        db.execSQL(sql);
        db.close();

        return true;
    }
*/
	static public String getResult(String query) throws SQLException {
    	Statement stmt = null;
    	Connection con;
    	String result = "";
	    try {
	    	con = ConnectionProvider.getRemoteConnection();
	        stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	result += "ID    : " + rs.getInt("_id") +
	        			"usrID   : " + rs.getInt("usrid") +
                        "\nDate  : " + rs.getString("Date") +
                        "\nName  : " + rs.getString("name") +
                        "\ntNum  : " + rs.getInt("tNum") +
                        "\nwNum  : "  + rs.getInt("wNum") + "\n\n";
	        }
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } catch (Exception e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { stmt.close(); }
	    }
	    return result;
    }


	static public String getTagById(int usrIn, int idIn) throws SQLException {
        String sql = "SELECT * FROM tagList where _id = " + idIn + " AND usrID = " + usrIn + "; ";
        return getResult(sql);
    }

	static public String getTagsSortedByName(int usrIn) throws SQLException {
        String sql = " SELECT * FROM tagList ORDER BY name ASC, date ASC WHERE usrID = " + usrIn + "; ";
        return getResult(sql);
    }

	static public String getTagsSortedByDate(int usrIn) throws SQLException {
        String sql = " SELECT * FROM tagList ORDER BY date ASC WHERE usrID = " + usrIn + "; ";
        return getResult(sql);
    }

	static public String getSearch(int usrIn, String lookFor) throws SQLException {
        String sql = " SELECT * FROM tagList WHERE name LIKE '%" + lookFor + "%' WHERE usrID = " + usrIn + ";";
        return getResult(sql);
    }
}