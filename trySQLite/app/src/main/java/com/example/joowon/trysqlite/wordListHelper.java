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

public class wordListHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;

    public wordListHelper(Context context) {
        super(context, "wordL.db", null, 1);
    }

    // create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //this.db = getWritableDatabase();

        String sql = "create table if not exists wordList ( "
                + " _id   INTEGER PRIMARY KEY autoincrement, "
                + " date  text, "
                + " name  VARCHAR(30), "    // TEXT
                + " defs  VARCHAR(80), "    // TEXT
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
        String sql = " DROP TABLE IF EXISTS wordList; ";
        db.execSQL(sql);
        db.close();

        onCreate(db);
    }

    // insert a new word
    public void insert(String wordIn, String defsIn) {
        db = getWritableDatabase();

        String sql = " INSERT INTO wordList (date, name, defs) " +
                " VALUES ( '" + Word.makeCurrDateInString() + "' , '" + wordIn + "' , '" + defsIn + "' ); ";

        db.execSQL(sql);
        db.close();
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
            db.close();

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
        db.close();
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
    }
}
