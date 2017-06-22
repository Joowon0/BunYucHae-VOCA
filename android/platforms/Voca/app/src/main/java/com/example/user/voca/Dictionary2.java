package com.example.user.voca;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Dictionary2 extends Activity
{

    public TextView Voca;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary2);
        Intent intent = getIntent();
        Voca = (TextView)findViewById(R.id.voca);
        String href = getIntent().getStringExtra("TextIn");
        Show sh = new Show(href);
        sh.start();
        try {
            sh.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<DictWord> dictWords =null;
        while(dictWords==null)
            dictWords = sh.getDictWords();

        String page = "";
        for(int i=0;i<dictWords.size();++i){
            DictWord dictWord =dictWords.get(i);
            page+=dictWord.value+="\n";
            page+=dictWord.mean+="\n";
            for(int j=0;j<dictWord.examples.size();++j){
                page+=dictWord.examples.get(j)+"\n";
            }
        }
        LinearLayout llBackground = (LinearLayout)findViewById(R.id.t2ll);
        Voca.setText(page);
        Voca.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
    }
    class Show extends Thread{
        String href;
        ArrayList<DictWord> dictWords;
        Show(String h){
            href = h;
        }
        public ArrayList<DictWord> getDictWords(){return dictWords;}
        public void run(){
            dictWords = Dictionary.parseWords(href);
        }
    }
}

