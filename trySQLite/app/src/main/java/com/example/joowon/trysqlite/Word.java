package com.example.joowon.trysqlite;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by joowon on 17. 6. 20.
 */

public class Word extends entry{
    public int    id;
//    public Date   date;
    public String name;
    public String defs;
    public int    tag1;
    public int    tag2;
    public int    tag3;

    static public String toString(List<Word> words) {
        String result = "";
        for (int i = 0; i < words.size(); i++)
            result += words.get(i).toString();

        return result;
    }

    public String toString() {
        String result =
                "ID    : " + id +
                        "\nDate  : " + getDateInString() +
                        "\nName  : " + name +
                        "\nDefs  : " + defs +
                        "\nTag1 : " + tag1 +
                        "\t\tTag2 : " + tag2 +
                        "\t\tTag3 : " + tag3 + "\n\n";
        return result;
    }
}
