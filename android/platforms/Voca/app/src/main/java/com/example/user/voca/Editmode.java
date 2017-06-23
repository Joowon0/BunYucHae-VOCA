package com.example.user.voca;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Editmode extends AppCompatActivity {
    public Button dicbtn;
    public EditText uet;
    public EditText det;
    public Button savebtn;
    public Button trans;
    public Button search;
    dbHelper mydbHelper;
    Translation1 transActivity = (Translation1) Translation1.thisActivity;
    public static final String TAG = "Test_Alert_Dialog";
    Spinner bf,af;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmode);
        dicbtn = (Button) findViewById(R.id.dicmode);
        uet = (EditText)findViewById(R.id.upedit);
        det = (EditText)findViewById(R.id.downedit);
        savebtn = (Button)findViewById(R.id.savetext);

        trans = (Button) findViewById(R.id.translate);
        search = (Button) findViewById(R.id.searchbtn);
        bf = (Spinner)findViewById(R.id.before_lang);
        af = (Spinner)findViewById(R.id.after_lang);

        uet.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        det.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        savebtn.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));


        mydbHelper = dbHelper.getInstance(getApplicationContext());

        uet.setMovementMethod(ScrollingMovementMethod.getInstance());
        det.setMovementMethod(ScrollingMovementMethod.getInstance());
        uet.setMaxHeight(uet.getMaxHeight());
        det.setMaxHeight(det.getMaxHeight());
        dicbtn.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        uet.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        det.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        savebtn.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        trans.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        search.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));

        Intent dic_intent = getIntent();
        uet.setText(dic_intent.getStringExtra("TextIn"));
        det.setText(dic_intent.getStringExtra("TextIn1"));

        dicbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent edit_intent = new Intent(Editmode.this, Dictionarymode.class);
                edit_intent.putExtra("TextOut", uet.getText().toString());
                edit_intent.putExtra("TextOut1", det.getText().toString());
                setResult(RESULT_OK,edit_intent);
                finish();
            }
        });
        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestAPI request = new RequestAPI();
                det.setText(request.requestTranslating(uet.getText().toString(),bf.getSelectedItem().toString(),af.getSelectedItem().toString()));
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                android.app.AlertDialog.Builder ad = new AlertDialog.Builder(Editmode.this);

                final EditText et = new EditText(Editmode.this);
                ad.setView(et);

                ad.setTitle("알림");
                ad.setMessage("제목을 입력하세요");
                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v(TAG, "Yes Btn Click");
                        String value = et.getText().toString();
                        String text1 = uet.getText().toString();
                        String text2 = det.getText().toString();
                        Log.v(TAG, value);
                        dialog.dismiss();
                        mydbHelper.text().insert(value, text1, text2);

                        Intent intent = new Intent(Editmode.this, Text1.class);
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
    }
}
