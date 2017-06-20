package com.example.joowon.trysqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joowon on 17. 6. 6.
 */

public class textListHelper extends SQLiteOpenHelper{
    SQLiteDatabase db;

    public textListHelper(Context context) {
        super(context, "textL.db", null, 1);
    }

    // create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //this.db = getWritableDatabase();
        Log.d("tag","AAA");
        String sql = "create table if not exists textList( "
                + " _id   INTEGER PRIMARY KEY autoincrement, "
                + " date  text, "
                + " title VARCHAR(40), "     // TEXT
                + " texts VARCHAR(255), "    // TEXT
                + " tag1  INTEGER DEFAULT 0, "
                + " tag2  INTEGER DEFAULT 0, "
                + " tag3  INTEGER DEFAULT 0 "
                + " ); ";
        db.execSQL(sql);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db = getWritableDatabase();

        String sql = " DROP TABLE IF EXISTS textList; ";
        db.execSQL(sql);
        onCreate(db);

        db.close();
    }


    // insert a new text
    public void insert(String titleIn, String textIn) {
        db = getWritableDatabase();

        String sql = " INSERT INTO textList (date, title, texts) " +
                " VALUES ( '" + Text.makeCurrDateInString() + "' , '" + titleIn + "' , '" + textIn + "' ); ";

        db.execSQL(sql);
        db.close();
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
            db.close();
            return true;
        }
        else {
            System.out.println("cannot find matching id in textList");
            cursor.close();
            db.close();
            return false;
        }
    }

    // modify the title and text to corresponding id
    // if there is nothing to modify, put null
    // if titleIn and textIn are same as b4, just updates date
    public boolean modifyContentById(int idIn, String titleIn, String textIn) {
        if (titleIn == null && textIn == null) {
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
            sql += " WHERE _id = " + idIn + "; ";

            db.execSQL(sql);
            db.close();

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
            temp.tag1  = cursor.getInt(4);
            temp.tag2  = cursor.getInt(5);
            temp.tag3  = cursor.getInt(6);

            result.add(temp);
        }
        cursor.close();
        db.close();
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
                " texts LIKE '%" + lookFor + "%' ;";
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
    }
}
