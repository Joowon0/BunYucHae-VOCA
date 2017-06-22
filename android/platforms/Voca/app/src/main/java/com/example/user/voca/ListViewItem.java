package com.example.user.voca;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private int idint;
    private String titleStr ;
    private String descStr ;

    public void setId(int id) {
        idint = id ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }

    public int getId() {
        return this.idint ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
}