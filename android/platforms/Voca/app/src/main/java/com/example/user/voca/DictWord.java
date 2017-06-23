package com.example.user.voca;

import java.util.ArrayList;

public class DictWord {
    public String value;
    public String mean;
    public String href;
    public String pronunciationSymbol;
    public ArrayList<String> examples;
    public DictWord(){
        examples=new ArrayList<String>();
        value=mean=href=pronunciationSymbol="";
    }
    public String toString(){
        return value+pronunciationSymbol+"\n"+mean+"\n";
    }
}