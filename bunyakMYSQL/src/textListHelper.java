import java.sql.*;

/**
 * Created by joowon on 17. 6. 6.
 * THIS IS FOR MYSQL
 */

public class textListHelper{
    // create table
	static public void createTable() throws SQLException {
        String sql = " CREATE TABLE IF NOT EXISTS textList( "
        		+ "  _id   INTEGER PRIMARY KEY auto_increment, "
        		+ "  usrID INTEGER NOT NULL, "
        		+ "  date  TIMESTAMP, "
        		+ "  title TEXT CHARACTER SET utf8, "
        		+ "  texts TEXT CHARACTER SET utf8, "
        		+ "  txts2 TEXT CHARACTER SET utf8, "
        		+ "  tag1  INTEGER DEFAULT 0, "
        		+ "  tag2  INTEGER DEFAULT 0, "
        		+ "  tag3  INTEGER DEFAULT 0); ";
        
        MYSQLdbHelper.execSQL(sql);
    }

	static public void dropTable() throws SQLException {
        String sql = " DROP TABLE IF EXISTS textList; ";
        
        MYSQLdbHelper.execSQL(sql);
    }


    // insert a new text with auto timestamp setting
	static public void insertNew(int usrIn, String titleIn, String textIn, String text2In) throws SQLException {
        String sql = " INSERT INTO textList (usrID, title, texts, txts2) VALUES ( " + usrIn + ", '" + titleIn + "', '" + textIn + "', '" + text2In + "' );"; 
        
        MYSQLdbHelper.execSQL(sql);
    }

    // insert a new text with specified timestamp
    // used for backups
	static public void insert4Backup(int usrIn, String dateIn, String titleIn, String textIn, String text2In) throws SQLException {
        String sql = " INSERT INTO textList (usrID, date, title, texts, txts2) VALUES ( " 
        			+ usrIn + ", '" + dateIn + "', '" + titleIn + "', '" + textIn + "', '" + text2In + "' );"; 
        
        MYSQLdbHelper.execSQL(sql);
    }
/*
    // remove a text
    public void removeById(Connection con, int idIn) {
    	String sql = " DELETE FROM textList " +
                     " WHERE _id = " + idIn + " ; ";
    }

    // modify the title and text to corresponding id
    public boolean modifyContentById(Connection con, int idIn, String titleIn, String textIn) {
        if (titleIn == null && textIn == null) {
            return false;
        }
        else {
            String sql = " UPDATE textList SET "
            		+ " title = '" + titleIn + "', "
            		+ " texts = '" + textIn + "' "
            		+ " WHERE _id = " + idIn + "; ";
            
            return true;
        }
    }
*/
    // get all text element
    // used mostly in other methods
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
	                    "\nTitle : " + rs.getString("Title") +
	                    "\nText  : " + rs.getString("Texts") +
	                    "\nTxt2  : " + rs.getString("Txts2") +
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


    // get text
	static public String getTextById(int usrIn, int idIn) throws SQLException {
        String sql = "SELECT * FROM textList "
        		+ " where _id = " + idIn + " AND "
        		+ " usrID = " + usrIn + " ; ";
        return getResult(sql);
    }

	static public String getTextsSortedDate(int usrIn) throws SQLException {
        String sql = " SELECT * FROM textList ORDER BY date ASC "
        		+ " WHERE usrID = " + usrIn + "; ";
        return getResult(sql);
    }

	static public String getTextsSortedTitle(int usrIn) throws SQLException {
        String sql = " SELECT * FROM textList ORDER BY title ASC, date ASC "
        		+ " WHERE usrID = " + usrIn + "; ";
        return getResult(sql);
    }

	static public String getTextsGroupByT1(int usrIn) throws SQLException {
        String sql = " SELECT * FROM textList ORDER BY tag1 "
        		+ " WHERE usrID = " + usrIn + "; ";
        return getResult(sql);
    }

    // get all texts with matching string
	static public String getSearch(String lookFor, int usrIn) throws SQLException {
        String sql = " SELECT * FROM textList WHERE " +
                " title LIKE '%" + lookFor + "%' OR " +
                " texts LIKE '%" + lookFor + "%' OR "   +
                " txts2 LIKE '%" + lookFor + "%' "   +
                " WHERE usrID = " + usrIn + "; ";
        return getResult(sql);
    }
/*
    // add tag by tag id
    public boolean addTagById(int idIn, int newTag) {
        int t1, t2, t3;
        db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT tag1, tag2, tag3 FROM textList where _id = " + idIn + " ; ", null);

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
                String sql = "UPDATE textList SET ";

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
            System.out.println("cannot find matching id in textList");
            db.close();
            return false;
        }
    }

    // remove tag by tag id
    public boolean removeTagById(int idIn, int rmTag) {
        int t1, t2, t3;
        db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT tag1, tag2, tag3 FROM textList where _id = " + idIn + " ; ", null);

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

                String sql = "UPDATE textList SET " +
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
            System.out.println("cannot find matching id in textList");
            cursor.close();
            db.close();
            return false;
        }
    }*/
}
