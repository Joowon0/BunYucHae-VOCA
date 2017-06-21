package com.example.joowon.trysqlite;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by joowon on 17. 6. 21.
 */

public class entry {
    public Date date;

    static DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    public String getDateInString() {
        String result = df.format(date);

        return result;
    }

    static public String makeCurrDateInString() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        String result = df.format(date);

        return result;
    }

    public boolean setDateByString(String str) {
        try {
            date = df.parse(str);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


}
