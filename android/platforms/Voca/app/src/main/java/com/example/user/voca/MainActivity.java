package com.example.user.voca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public Button trn;
    public Button text;
    public Button dic;
    public Button voca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trn = (Button) findViewById(R.id.translation);
        text = (Button) findViewById(R.id.text);
        dic = (Button) findViewById(R.id.dictionary);
        voca = (Button) findViewById(R.id.voca);
        trn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent trn_intent = new Intent(MainActivity.this, Translation1.class);
                startActivityForResult(trn_intent,0);
            }
        });
        text.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent text_intent = new Intent(MainActivity.this, Text1.class);
                startActivityForResult(text_intent, 0);
            }
        });
        dic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent dic_intent = new Intent(MainActivity.this, Dictionary1.class);
                startActivityForResult(dic_intent,0);
            }
        });
        voca.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent voca_intent = new Intent(MainActivity.this, Voca1.class);
                startActivityForResult(voca_intent,0);
            }
        });
    }
}