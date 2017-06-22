package com.example.joowon.trysqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joowon on 17. 6. 21.
 */

public class dbHelper extends SQLiteOpenHelper {
    static dbHelper instance = null;
    public textHelper th;
    public wordHelper wh;
    public tagHelper  tgh;

    public textHelper text(){
        return th;
    }
    public wordHelper word(){
        return wh;
    }
    public tagHelper tag(){
        return tgh;
    }

    SQLiteDatabase db;
    public static dbHelper getInstance(Context context){
        if(instance==null)
            instance=new dbHelper(context);
        return instance;
    }
    private dbHelper(Context context) {
        super(context, "bunYacSQLite6.db", null, 1);
        th = new textHelper();
        wh = new wordHelper();
        tgh =new tagHelper();

    }
    public static SQLiteDatabase getWritableDataBase(){
        return instance.getWriteDB();
    }
    public  SQLiteDatabase getWriteDB(){
        return getWritableDatabase();
    }
    // create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //this.db = getWritableDatabase();

        String sql = "create table if not exists tagList ( "
                + " _id   INTEGER PRIMARY KEY autoincrement, "
                + " date  text, "           // last updated
                + " name  VARCHAR(30), "    // TEXT
                + " tNum  INTEGER DEFAULT 0 CHECK(tNum >= 0), "
                + " wNum  INTEGER DEFAULT 0 CHECK(wNum >= 0)  "
                + " ); ";
        db.execSQL(sql);

        sql = "create table if not exists textList( "
                + " _id   INTEGER PRIMARY KEY autoincrement, "
                + " date  text, "
                + " title VARCHAR(40) NOT NULL, "     // TEXT
                + " texts VARCHAR(255), "    // TEXT
                + " txts2 VARCHAR(255), "    // TEXT
                + " tag1  INTEGER DEFAULT 0 REFERENCES tagList(_id), "
                + " tag2  INTEGER DEFAULT 0 REFERENCES tagList(_id), "
                + " tag3  INTEGER DEFAULT 0 REFERENCES tagList(_id) "
                + " ); ";
        db.execSQL(sql);

        sql = "create table if not exists wordList ( "
                + " _id   INTEGER PRIMARY KEY autoincrement, "
                + " date  text, "
                + " name  VARCHAR(30), "    // TEXT
                + " defs  VARCHAR(80), "    // TEXT
                + " tag1  INTEGER DEFAULT 0 REFERENCES tagList(_id), "
                + " tag2  INTEGER DEFAULT 0 REFERENCES tagList(_id), "
                + " tag3  INTEGER DEFAULT 0 REFERENCES tagList(_id)"
                + " ); ";
        db.execSQL(sql);

        sql = " CREATE TRIGGER increTagTNum AFTER UPDATE ON textList " +
                " BEGIN " +
                "   UPDATE tagList " +
                "   SET tNum = tNum + 1 " +
                "   WHERE OLD.tag1 != NEW.tag1 " +
                "     AND _id = NEW.tag1; " +

                "   UPDATE tagList " +
                "   SET tNum = tNum + 1 " +
                "   WHERE OLD.tag2 != NEW.tag2 " +
                "     AND _id = NEW.tag2;" +

                "   UPDATE tagList " +
                "   SET tNum = tNum + 1 " +
                "   WHERE OLD.tag3 != NEW.tag3 " +
                "     AND _id = NEW.tag3; " +
                " END;  ";
        db.execSQL(sql);

        sql = " CREATE TRIGGER increTagWNum AFTER UPDATE ON wordList " +
                " BEGIN " +
                    " UPDATE tagList " +
                    " SET wNum = wNum + 1 " +
                    " WHERE OLD.tag1 != NEW.tag1" +
                "      AND _id = NEW.tag1; " +

                    " UPDATE tagList " +
                    " SET wNum = wNum + 1 " +
                    " WHERE OLD.tag2 != NEW.tag2 " +
                "      AND _id = NEW.tag2; " +

                    " UPDATE tagList " +
                    " SET wNum = wNum + 1 " +
                    " WHERE OLD.tag3 != NEW.tag3 " +
                "     AND _id = NEW.tag3; " +
                " END; ";
        db.execSQL(sql);


        sql = " CREATE TRIGGER decreTagTNum AFTER UPDATE ON textList " +
                " BEGIN " +
                "   UPDATE tagList " +
                "   SET tNum = tNum - 1 " +
                "   WHERE OLD.tag1 != NEW.tag1 " +
                "     AND _id = OLD.tag1; " +

                "   UPDATE tagList " +
                "   SET tNum = tNum - 1 " +
                "   WHERE OLD.tag2 != NEW.tag2 " +
                "     AND _id = OLD.tag2;" +

                "   UPDATE tagList " +
                "   SET tNum = tNum - 1 " +
                "   WHERE OLD.tag3 != NEW.tag3 " +
                "     AND _id = OLD.tag3; " +
                " END;  ";
        db.execSQL(sql);

        sql = " CREATE TRIGGER decreTagWNum AFTER UPDATE ON wordList " +
                " BEGIN " +
                " UPDATE tagList " +
                " SET wNum = wNum - 1 " +
                " WHERE OLD.tag1 != NEW.tag1" +
                "      AND _id = OLD.tag1; " +

                " UPDATE tagList " +
                " SET wNum = wNum - 1 " +
                " WHERE OLD.tag2 != NEW.tag2 " +
                "      AND _id = OLD.tag2; " +

                " UPDATE tagList " +
                " SET wNum = wNum - 1 " +
                " WHERE OLD.tag3 != NEW.tag3 " +
                "     AND _id = OLD.tag3; " +
                " END; ";
        db.execSQL(sql);


        sql = " CREATE TRIGGER decreTagTNum2 AFTER DELETE ON textList " +
                " BEGIN " +
                "   UPDATE tagList " +
                "   SET tNum = tNum - 1 " +
                "   WHERE _id = OLD.tag1 " +
                "     OR  _id = OLD.tag2 " +
                "     OR  _id = OLD.tag3; " +
                " END;  ";
        db.execSQL(sql);

        sql = " CREATE TRIGGER decreTagWNum2 AFTER DELETE ON wordList " +
                " BEGIN " +
                " UPDATE tagList " +
                " SET wNum = wNum - 1 " +
                " WHERE _id = OLD.tag1 " +
                "     OR  _id = OLD.tag2 " +
                "     OR  _id = OLD.tag3; " +
                " END; ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db = getWritableDatabase();

        String sql = " DROP TRIGGER IF EXISTS increTagTNum; ";
        db.execSQL(sql);
        sql = " DROP TRIGGER IF EXISTS increTagWNum; ";
        db.execSQL(sql);
        sql = " DROP TRIGGER IF EXISTS decreTagTNum; ";
        db.execSQL(sql);
        sql = " DROP TRIGGER IF EXISTS decreTagWNum; ";
        db.execSQL(sql);
        sql = " DROP TRIGGER IF EXISTS decreTagTNum2; ";
        db.execSQL(sql);
        sql = " DROP TRIGGER IF EXISTS decreTagWNum2; ";
        db.execSQL(sql);

        sql = " DROP TABLE IF EXISTS textList; ";
        db.execSQL(sql);
        sql = " DROP TABLE IF EXISTS wordList; ";
        db.execSQL(sql);
        sql = " DROP TABLE IF EXISTS tagList; ";
        db.execSQL(sql);

        onCreate(db);
        //db.close();
    }

    public class textHelper {
        // insert a new text
        public void insert(String titleIn, String textIn, String text2In) {
            db = getWritableDatabase();
            String sql = " INSERT INTO textList (date, title, texts, txts2) " +
                    " VALUES ( '" + Text.makeCurrDateInString() + "' , '" + titleIn + "' , '" + textIn + "', '" + text2In + "' ); ";

            db.execSQL(sql);
            //db.close();
        }

        // remove a text
        public boolean removeById(int idIn) {
            db = getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT _id FROM textList where _id = " + idIn, null);

            if (cursor.moveToNext()) { // check if exists
                String sql = "DELETE FROM textList " +
                        " WHERE _id = " + idIn + " ; ";

                cursor.close();
                db.execSQL(sql);
                //db.close();
                return true;
            }
            else {
                System.out.println("cannot find matching id in textList");
                cursor.close();
                //db.close();
                return false;
            }
        }

        // modify the title and text to corresponding id
        // if there is nothing to modify, put null
        // if titleIn and textIn are same as b4, just updates date
        public boolean modifyContentById(int idIn, String titleIn, String textIn, String text2In) {
            if (titleIn == null && textIn == null && text2In == null) {
                return false;
            }
            else {
                db = getWritableDatabase();
                String sql = " UPDATE textList " +
                        " SET date = '" + Text.makeCurrDateInString() + "' ";

                if (titleIn != null)
                    sql += ", title = '" + titleIn + "' " ;
                if (textIn != null)
                    sql += ", texts = '" + textIn + "' ";
                if (text2In != null)
                    sql += ", txts2 = '" + text2In + "' ";
                sql += " WHERE _id = " + idIn + "; ";

                db.execSQL(sql);
                //db.close();

                return true;
            }
        }

        // get all text element
        // used mostly in other methods
        public List<Text> getResult(String query) {
            db = getWritableDatabase();
            List<Text> result = new ArrayList<Text>();
            String tempDate = "";

            Cursor cursor = db.rawQuery(query, null);

            for (int i = 0; cursor.moveToNext(); i++){
                Text temp = new Text();

                temp.id    = cursor.getInt(0);
                tempDate = cursor.getString(1);
                temp.setDateByString(tempDate);

                temp.title = cursor.getString(2);
                temp.text  = cursor.getString(3);
                temp.txt2  = cursor.getString(4);
                temp.tag1  = cursor.getInt(5);
                temp.tag2  = cursor.getInt(6);
                temp.tag3  = cursor.getInt(7);

                result.add(temp);
            }
            cursor.close();
            //db.close();
            return result;
        }

        // toString function for temporary
        public String toString(List<Text> listIn) {
            return Text.toString(listIn);
        }

        // get text
        public Text getTextById(int idIn) {
            String sql = "SELECT * FROM textList where _id = " + idIn + " ; ";
            return getResult(sql).get(0);
        }


        // get texts that has tag num
        public List<Text> getTextsByTag(int tagIn) {
            String sql = " SELECT * FROM textList WHERE tag1 = " + tagIn +
                    " OR tag2 = " + tagIn + " OR tag3 = " + tagIn + " ; ";
            return getResult(sql);
        }

        public List<Text> getTextsSortedDate() {
            String sql = " SELECT * FROM textList ORDER BY date ASC; ";
            return getResult(sql);
        }

        public List<Text> getTextsSortedTitle() {
            String sql = " SELECT * FROM textList ORDER BY title ASC, date ASC; ";
            return getResult(sql);
        }

        public List<Text> getTextsGroupByT1() {
            String sql = " SELECT * FROM textList ORDER BY tag1; ";
            return getResult(sql);
        }

        // get all texts with matching string
        public List<Text> getSearch(String lookFor) {
            String sql = " SELECT * FROM textList WHERE " +
                    " title LIKE '%" + lookFor + "%' OR " +
                    " texts LIKE '%" + lookFor + "%' OR " +
                    " txts2 LIKE '%" + lookFor + "%' ;";
            return getResult(sql);
        }

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
                    //db.close();
                    return false;
                }
                else if (t1 == newTag || t2 == newTag || t3 == newTag) {
                    cursor.close();
                    //db.close();
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
                    //db.close();
                    return true;
                }
            }
            else {
                System.out.println("cannot find matching id in textList");
                //db.close();
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
                    //db.close();
                    return true;
                }
            }
            else {
                System.out.println("cannot find matching id in textList");
                cursor.close();
                //db.close();
                return false;
            }
        }
    }

    public class wordHelper {
        // insert a new word
        public void insert(String wordIn, String defsIn) {
            db = getWritableDatabase();

            String sql = " INSERT INTO wordList (date, name, defs) " +
                    " VALUES ( '" + Word.makeCurrDateInString() + "' , '" + wordIn + "' , '" + defsIn + "' ); ";

            db.execSQL(sql);
            //db.close();
        };

        // remove a word
        public boolean removeById(int idIn) {
            db = getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT _id FROM wordList where _id = " + idIn, null);

            if (cursor.moveToNext()) { // check if exists
                String sql = "DELETE FROM wordList " +
                        " WHERE _id = " + idIn + " ; ";

                cursor.close();
                db.execSQL(sql);
                //db.close();
                return true;
            }
            else {
                System.out.println("cannot find matching id in wordList");
                cursor.close();
                //db.close();
                return false;
            }
        }

        // modify the name and defs to corresponding id
        // if there is nothing to modify, put null
        // if nameIn and defsIn are same as b4, just updates date
        public boolean modifyContentById(int idIn, String nameIn, String defsIn) {
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
                //db.close();

                return true;
            }
        }

        // get all word element
        // used mostly in other methods
        public List<Word> getResult(String query) {
            db = getWritableDatabase();
            List<Word> result = new ArrayList<Word>();
            String tempDate = "";

            Cursor cursor = db.rawQuery(query, null);

            for (int i = 0; cursor.moveToNext(); i++){
                Word temp = new Word();

                temp.id    = cursor.getInt(0);
                tempDate   = cursor.getString(1);
                temp.setDateByString(tempDate);

                temp.name  = cursor.getString(2);
                temp.defs  = cursor.getString(3);
                temp.tag1  = cursor.getInt(4);
                temp.tag2  = cursor.getInt(5);
                temp.tag3  = cursor.getInt(6);

                result.add(temp);
            }
            cursor.close();
            //db.close();
            return result;
        }

        // toString function for temporary
        public String toString(List<Word> wordIn) {
            return Word.toString(wordIn);
        }

        // get words
        public Word getWordsById(int idIn) {
            String sql = "SELECT * FROM wordList where _id = " + idIn + " ; ";
            return getResult(sql).get(0);
        }

        // get words that has tag num
        public List<Word> getWordsByTag(int tagIn) {
            String sql = " SELECT * FROM wordList WHERE tag1 = " + tagIn +
                    " OR tag2 = " + tagIn + " OR tag3 = " + tagIn + " ; ";
            return getResult(sql);
        }

        public List<Word> getWordsSortedDate() {
            String sql = " SELECT * FROM wordList ORDER BY date ASC; ";
            return getResult(sql);
        }

        public List<Word> getWordsSortedName() {
            String sql = " SELECT * FROM wordList ORDER BY name ASC, date ASC; ";
            return getResult(sql);
        }

        // get word
        public List<Word> getWordsByName(String wordIn) {
            String sql = "SELECT * FROM wordList where name = '" + wordIn + "' ; ";
            return getResult(sql);
        }

        public List<Word> getWordsGroupByT1() {
            String sql = " SELECT * FROM wordList ORDER BY tag1; ";
            return getResult(sql);
        }

        // get all words with matching string
        public List<Word> getSearch(String lookFor) {
            String sql = " SELECT * FROM wordList WHERE name LIKE '%" + lookFor + "%' ;";
            return getResult(sql);
        }

        // add tag by tag id
        public boolean addTagById(int idIn, int newTag) {
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
                    //db.close();
                    return false;
                }
                else if (t1 == newTag || t2 == newTag || t3 == newTag) {
                    cursor.close();
                    //db.close();
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
                    //db.close();
                    return true;
                }
            }
            else {
                System.out.println("cannot find matching id in wordList");
                //db.close();
                return false;
            }
        }

        // remove tag by tag id
        public boolean removeTagById(int idIn, int rmTag) {
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
                    //db.close();
                    return true;
                }
            }
            else {
                System.out.println("cannot find matching id in wordList");
                cursor.close();
                //db.close();
                return false;
            }
        }
    }

    public class tagHelper {
        public void insert(String nameIn) {
            db = getWritableDatabase();

            String sql = " INSERT INTO tagList (date, name) " +
                    " VALUES ( '" + Tag.makeCurrDateInString() + "' , '" + nameIn + "' ); ";

            db.execSQL(sql);
            //db.close();
        }

        public boolean removeById(int idIn) {
            db = getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT _id FROM tagList where _id = " + idIn, null);

            if (cursor.moveToNext()) { // check if exists
                String sql = "DELETE FROM tagList " +
                        " WHERE _id = " + idIn + " ; ";

                cursor.close();
                db.execSQL(sql);
                //db.close();
                return true;
            } else {
                System.out.println("cannot find matching id in tagList");
                cursor.close();
                //db.close();
                return false;
            }
        }

        public boolean modifyNameById(int idIn, String nameIn) {
            db = getWritableDatabase();
            String sql = " UPDATE tagList " +
                    " SET date = '" + Tag.makeCurrDateInString() + "', " +
                    " name = '" + nameIn + "' " +
                    " WHERE _id = " + idIn + "; ";

            db.execSQL(sql);
            //db.close();

            return true;
        }

        public List<Tag> getResult(String query) {
            db = getWritableDatabase();
            List<Tag> result = new ArrayList<Tag>();
            String tempDate = "";

            Cursor cursor = db.rawQuery(query, null);

            for (int i = 0; cursor.moveToNext(); i++) {
                Tag temp = new Tag();

                temp.id = cursor.getInt(0);
                tempDate = cursor.getString(1);
                temp.setDateByString(tempDate);

                temp.name = cursor.getString(2);
                temp.tNum = cursor.getInt(3);
                temp.wNum = cursor.getInt(4);

                result.add(temp);
            }

            cursor.close();
            //db.close();
            return result;
        }

        public String toString(List<Tag> tagIn) {
            return Tag.toString(tagIn);
        }

        public Tag getTagById(int idIn) {
            String sql = "SELECT * FROM tagList where _id = " + idIn + "; ";
            return getResult(sql).get(0);
        }

        public List<Tag> getTagsSortedByName() {
            String sql = " SELECT * FROM tagList ORDER BY name ASC, date ASC; ";
            return getResult(sql);
        }

        public List<Tag> getTagsSortedByDate() {
            String sql = " SELECT * FROM tagList ORDER BY date ASC; ";
            return getResult(sql);
        }

        public List<Tag> getSearch(String lookFor) {
            String sql = " SELECT * FROM tagList WHERE name LIKE '%" + lookFor + "%' ;";
            return getResult(sql);
        }

        public int getTNumbyId(int idIn) {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery(" SELECT tNum FROM tagList WHERE _id = " + idIn, null);

            cursor.moveToNext();
            int result = cursor.getInt(0);

            cursor.close();
            //db.close();
            return result;
        }

        public int getWNumbyId(int idIn) {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery(" SELECT wNum FROM tagList WHERE _id = " + idIn, null);
            int result;

            if (cursor.moveToNext())
                result = cursor.getInt(0);
            else
                result = -1;

            cursor.close();
            //db.close();
            return result;
        }
    }
}

