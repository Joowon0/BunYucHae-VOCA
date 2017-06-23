package com.example.user.voca;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Dictionarymode extends AppCompatActivity {
    final static int ACT_EDIT = 0;
    public Button savebtn;
    public Button search;
    public Button trans;
    public Button dicbtn;
    public TextView utv;
    public TextView dtv;

    dbHelper mydbHelper;

    Translation1 transActivity = (Translation1) Translation1.thisActivity;

    Spinner bf,af;

    public static final String TAG = "Test_Alert_Dialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionarymode);
        dicbtn = (Button) findViewById(R.id.editmode);
        utv = (TextView)findViewById(R.id.upedit);
        dtv = (TextView)findViewById(R.id.downtextview);
        savebtn = (Button)findViewById(R.id.savetext);

        trans = (Button) findViewById(R.id.translation);
        search = (Button) findViewById(R.id.search);

        mydbHelper = dbHelper.getInstance(getApplicationContext());


        utv.setMovementMethod(ScrollingMovementMethod.getInstance());
        dtv.setMovementMethod(ScrollingMovementMethod.getInstance());
        utv.setMaxHeight(utv.getMaxHeight());
        dtv.setMaxHeight(dtv.getMaxHeight());


        trans = (Button) findViewById(R.id.translation);
        bf = (Spinner)findViewById(R.id.before_lang);
        af = (Spinner)findViewById(R.id.after_lang);

        dicbtn.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        utv.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        dtv.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        savebtn.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        trans.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        search.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));

        RequestAPI request = new RequestAPI();

        Intent intent = getIntent();
        utv.setText(intent.getStringExtra("tTextIn"));
        dtv.setText(request.requestTranslating(intent.getStringExtra("tTextIn"),"English","Korean"));
        dicbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent dic_intent = new Intent(Dictionarymode.this, Editmode.class);
                dic_intent.putExtra("TextIn", utv.getText().toString());
                dic_intent.putExtra("TextIn1", dtv.getText().toString());
                startActivityForResult(dic_intent,ACT_EDIT);
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener(){
         @Override
            public void onClick(View v){
             android.app.AlertDialog.Builder ad = new AlertDialog.Builder(Dictionarymode.this);

             final EditText et = new EditText(Dictionarymode.this);
             ad.setView(et);

             ad.setTitle("원본과 번역본을 저장합니다.");
             ad.setMessage("저장할 text의 title을 입력하세요");
             ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     Log.v(TAG, "Yes Btn Click");
                     String value = et.getText().toString();
                     String text1 = utv.getText().toString();
                     String text2 = dtv.getText().toString();
                     Log.v(TAG, value);
                     dialog.dismiss();
                     mydbHelper.text().insert(value, text1, text2);

                     Intent intent = new Intent(Dictionarymode.this, Text1.class);
                     startActivityForResult(intent, 0);
                     transActivity.finish();
                     finish();
                 }
             });

             ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     Log.v(TAG, "No Btn Click");
                     dialog.dismiss();
                 }
             });
             ad.show();
         }
        });
        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestAPI request = new RequestAPI();
                dtv.setText(request.requestTranslating(utv.getText().toString(),bf.getSelectedItem().toString(),af.getSelectedItem().toString()));
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ACT_EDIT && resultCode == RESULT_OK){
            utv.setText(data.getStringExtra("TextOut"));
            dtv.setText(data.getStringExtra("TextOut1"));
        }
    }
}
