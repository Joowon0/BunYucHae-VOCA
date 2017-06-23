package com.example.user.voca;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.user.voca.Dictionarymode.TAG;
import java.util.ArrayList;

public class Dictionary2 extends Activity
{
    dbHelper mydbHelper;
    public Button s_voca;
    public TextView Voca;
    ArrayList<DictWord> dictWords =null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary2);

        Intent intent = getIntent();
        Voca = (TextView)findViewById(R.id.voca);

        mydbHelper = dbHelper.getInstance(getApplicationContext());

        Voca.setMovementMethod(ScrollingMovementMethod.getInstance());
        s_voca = (Button)findViewById(R.id.savebtn);

        String href = getIntent().getStringExtra("TextIn");
        final String word = getIntent().getStringExtra("word");
        Show sh = new Show(href);
        sh.start();
        try {
            sh.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        s_voca.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));

        s_voca.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String means = " ";
                for(int i=0; i<dictWords.size(); ++i)
                    means += dictWords.get(i).mean + "\n";
                mydbHelper.word().insert(word, means);

                Intent remove_intent = new Intent(Dictionary2.this, Voca1.class);
                startActivityForResult(remove_intent,0);
                finish();
            }
        });

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

