import java.sql.*;

/**
 * Created by joowon on 17. 6. 20.
 * THIS IS FOR MYSQL
 */

public class wordListHelper {	
    // create table
	static public void onCreate() throws SQLException {
        String sql = " CREATE TABLE IF NOT EXISTS wordList ( "
        		+ "  _id   INTEGER PRIMARY KEY auto_increment, "
        		+ "  usrID INTEGER NOT NULL, "
        		+ "  date  TIMESTAMP,"
        		+ "  name  TEXT CHARACTER SET utf8,"
        		+ "  defs  TEXT CHARACTER SET utf8,"
        		+ "  tag1  INTEGER DEFAULT 0,"
        		+ "  tag2  INTEGER DEFAULT 0,"
        		+ "  tag3  INTEGER DEFAULT 0 );";
        MYSQLdbHelper.execSQL(sql);
    }

	static public void onUpgrade() throws SQLException {
        String sql = " DROP TABLE IF EXISTS wordList; ";
        MYSQLdbHelper.execSQL(sql);
    }

    // insert a new text with auto timestamp setting
	static public void insertNew(int usrIn, String wordIn, String defsIn) throws SQLException {
        String sql = " INSERT INTO wordList (usrID, name, defs) VALUES (" + usrIn + ", '" + wordIn + "', '" + defsIn + "');"; 
       
        MYSQLdbHelper.execSQL(sql);
    }

    // insert a new text with specified timestamp
    // used for backups
	static public void insert4Backup(int usrIn, String dateIn, String wordIn, String defsIn) throws SQLException {
        String sql = " INSERT INTO wordList (usrID, date, name, defs) VALUES (" 
        			+ usrIn + ", '" + dateIn + "', '" + wordIn + "', '" + defsIn + "');";
        MYSQLdbHelper.execSQL(sql);
    }
/*
    // remove a word
    public boolean removeById(Connection con, int idIn) throws SQLException {
        db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id FROM wordList where _id = " + idIn, null);

        if (cursor.moveToNext()) { // check if exists
            String sql = "DELETE FROM wordList " +
                    " WHERE _id = " + idIn + " ; ";

            cursor.close();
            db.execSQL(sql);
            db.close();
            return true;
        }
        else {
            System.out.println("cannot find matching id in wordList");
            cursor.close();
            db.close();
            return false;
        }
    }

    // modify the name and defs to corresponding id
    // if there is nothing to modify, put null
    // if nameIn and defsIn are same as b4, just updates date
    public boolean modifyContentById(Connection con, int idIn, String nameIn, String defsIn) throws SQLException {
        if (nameIn == null && defsIn == null) {
            return false;
        }
        else {
            db = getWritableDatabase();
            String sql = " UPDATE wordList " +
                    " SET date = '" + Word.makeCurrDateInString() + "' ";

            if (nameIn != null)
                sql += ", name = '" + nameIn + "' " ;
            if (defsIn != null)
                sql += ", defs = '" + defsIn + "' ";
            sql += " WHERE _id = " + idIn + "; ";

            db.execSQL(sql);
            db.close();

            return true;
        }
    }
*/
    // get all word element
    // used mostly in other methods
	static public String getResult(String query) throws SQLException {
    	Statement stmt = null;
    	String result = "";
    	Connection con;
	    try {
	    	con = ConnectionProvider.getRemoteConnection();
	        stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	result += "ID    : " + rs.getInt("_id") +
	        			"usrID   : " + rs.getInt("usrid") +
	        			"\nDate  : " + rs.getString("Date") +
	                    "\nname  : " + rs.getString("name") +
	                    "\ndefs  : " + rs.getString("defs") +
	                    "\nTag1 : "  + rs.getInt("tag1") +
	                    "\t\tTag2 : " + rs.getInt("tag2") +
	                    "\t\tTag3 : " + rs.getInt("tag3") + "\n\n";
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

    // get words that has tag num
	static public String getWordsByTag(int usrIn, int tagIn) throws SQLException {
        String sql = " SELECT * FROM wordList WHERE tag1 = " + tagIn +
                " OR tag2 = " + tagIn + " OR tag3 = " + tagIn + ", "
                + " usrID = " + usrIn + "; ";
        return getResult( sql);
    }

	static public String getWordsSortedDate(int usrIn) throws SQLException {
        String sql = " SELECT * FROM wordList ORDER BY date ASC "
        		+ " WHERE usrID = " + usrIn + "; "; 
        return getResult(sql);
    }

	static public String getWordsSortedName( int usrIn) throws SQLException {
        String sql = " SELECT * FROM wordList ORDER BY name ASC, date ASC "
        		+ " WHERE usrID = " + usrIn + "; ";
        return getResult(sql);
    }

    // get word
	static public String getWordsByName(int usrIn, String wordIn) throws SQLException {
        String sql = "SELECT * FROM wordList where name = '" + wordIn + "' "
        		+ " WHERE usrID = " + usrIn + "; ";
        return getResult(sql);
    }

	static public String getWordsGroupByT1(int usrIn) throws SQLException {
        String sql = " SELECT * FROM wordList ORDER BY tag1 "
        		+ " WHERE usrID = " + usrIn + "; ";
        return getResult(sql);
    }

    // get all words with matching string
	static public String getSearch(int usrIn, String lookFor) throws SQLException {
        String sql = " SELECT * FROM wordList WHERE name LIKE '%" + lookFor + "%', "
        		+ " usrID = " + usrIn + "; "; 
        return getResult(sql);
    }
/*
    // add tag by tag id
    public boolean addTagById(int idIn, int newTag) throws SQLException {
        int t1, t2, t3;
        db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT tag1, tag2, tag3 FROM wordList where _id = " + idIn + " ; ", null);

        // get inputs
        if (cursor.moveToNext()) {
            t1 = cursor.getInt(0);
            t2 = cursor.getInt(1);
            t3 = cursor.getInt(2);

            // if no where to put
            if (t1 != 0 && t2 != 0 && t3 != 0) {
                cursor.close();
                db.close();
                return false;
            }
            else if (t1 == newTag || t2 == newTag || t3 == newTag) {
                cursor.close();
                db.close();
                return true;
            }
            else {
                String sql = "UPDATE wordList SET ";

                if (t1 == 0) sql += " tag1 ";
                else if (t2 == 0) sql += " tag2 ";
                else sql += " tag3 ";

                sql += " = " + newTag +
                        " WHERE _id = " + idIn + " ; ";

                cursor.close();
                db.execSQL(sql);
                db.close();
                return true;
            }
        }
        else {
            System.out.println("cannot find matching id in wordList");
            db.close();
            return false;
        }
    }

    // remove tag by tag id
    public boolean removeTagById(int idIn, int rmTag) throws SQLException {
        int t1, t2, t3;
        db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT tag1, tag2, tag3 FROM wordList where _id = " + idIn + " ; ", null);

        // get inputs
        if (cursor.moveToNext()) {
            t1 = cursor.getInt(0);
            t2 = cursor.getInt(1);
            t3 = cursor.getInt(2);

            // check where to remove
            if (t1 != rmTag && t2 != rmTag && t3 != rmTag) {
                cursor.close();
                return false;
            }
            else {
                if (t1 == rmTag) {
                    t1 = t2;
                    t2 = t3;
                    t3 = 0;
                } else if (t2 == rmTag) {
                    t2 = t3;
                    t3 = 0;
                } else {
                    t3 = 0;
                }

                String sql = "UPDATE wordList SET " +
                        " tag1 = " + t1 + ", " +
                        " tag2 = " + t2 + ", " +
                        " tag3 = " + t3 +
                        " WHERE _id = " + idIn + " ; ";

                cursor.close();
                db.execSQL(sql);
                db.close();
                return true;
            }
        }
        else {
            System.out.println("cannot find matching id in wordList");
            cursor.close();
            db.close();
            return false;
        }
    }*/
}
