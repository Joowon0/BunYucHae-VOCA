package com.example.user.voca;

import android.app.Activity;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by joowon on 17. 6. 8.
 */


public class Text extends entry{
    public int    id;
//    public Date   date;
    public String title;
    public String text;
    public int    tag1;
    public int    tag2;
    public int    tag3;

    static public String toString(List<Text> texts) {
        String result = "";
        for (int i = 0; i < texts.size(); i++)
            result += texts.get(i).toString();

        return result;
    }

    public String toString() {
        String result =
                  "ID    : " + id +
                "\nDate  : " + getDateInString() +
                "\nTitle : " + title +
                "\nText  : " + text +
                "\nTag1 : "  + tag1 +
                "\t\tTag2 : " + tag2 +
                "\t\tTag3 : " + tag3 + "\n\n";
        return result;
    }
}
