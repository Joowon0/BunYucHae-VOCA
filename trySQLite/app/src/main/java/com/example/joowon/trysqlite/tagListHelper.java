package com.example.joowon.trysqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joowon on 17. 6. 20.
 */

public class tagListHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;

    public tagListHelper(Context context) {
        super(context, "tagL.db", null, 1);
    }

    // create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //this.db = getWritableDatabase();

        String sql = "create table if not exists tagList ( "
                + " _id   INTEGER PRIMARY KEY autoincrement, "
                + " date  text, "           // last updated
                + " name  VARCHAR(30), "    // TEXT
                + " tNum  INTEGER DEFAULT 0, "
                + " wNum  INTEGER DEFAULT 0  "
                + " ); ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = " DROP TABLE IF EXISTS tagList; ";
        db.execSQL(sql);

        onCreate(db);
    }

    public void insert(String nameIn) {
        db = getWritableDatabase();

        String sql = " INSERT INTO tagList (date, name) " +
                " VALUES ( '" + Tag.makeCurrDateInString() + "' , '" + nameIn + "' ); ";

        db.execSQL(sql);
        db.close();
    }

    public boolean removeById(int idIn) {
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

    public boolean modifyNameById(int idIn, String nameIn) {
        db = getWritableDatabase();
        String sql = " UPDATE tagList " +
                " SET date = '" + Tag.makeCurrDateInString() + "', " +
                " name = '" + nameIn + "' " +
                " WHERE _id = " + idIn + "; ";

        db.execSQL(sql);
        db.close();

        return true;
    }

    public List<Tag> getResult(String query) {
        db = getWritableDatabase();
        List<Tag> result = new ArrayList<Tag>();
        String tempDate = "";

        Cursor cursor = db.rawQuery(query, null);

        for (int i = 0; cursor.moveToNext(); i++){
            Tag temp = new Tag();

            temp.id    = cursor.getInt(0);
            tempDate   = cursor.getString(1);
            temp.setDateByString(tempDate);

            temp.name  = cursor.getString(2);
            temp.tNum  = cursor.getInt(3);
            temp.wNum  = cursor.getInt(4);

            result.add(temp);
        }

        cursor.close();
        db.close();
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
        db.close();
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
        db.close();
        return result;
    }
}