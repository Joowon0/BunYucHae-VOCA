package com.example.user.voca;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by joowon on 17. 6. 20.
 */

public class Tag extends entry{
    public int    id;
//    public Date   date;
    public String name;
    public int    tNum;
    public int    wNum;

    static public String toString(List<Tag> tags) {
        String result = "";
        for (int i = 0; i < tags.size(); i++)
            result += tags.get(i).toString();

        return result;
    }

    public String toString() {
        String result =
                "ID    : " + id +
                        "\nDate  : " + getDateInString() +
                        "\nName  : " + name +
                        "\ntNum  : " + tNum +
                        "\nwNum  : "  + wNum + "\n\n";
        return result;
    }
}
