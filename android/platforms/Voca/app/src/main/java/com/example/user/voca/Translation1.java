package com.example.user.voca;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Camera;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Translation1 extends AppCompatActivity {
    final static int ACT_EDIT = 0;
    public Button trans;
    public Button save;
    public EditText te;
    ImageButton picture;
    dbHelper mydbHelper;
    public static final String TAG = "Test_Alert_Dialog";
    public static Activity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        trans = (Button) findViewById(R.id.transbtn);
        save = (Button) findViewById(R.id.savebtn);
        te = (EditText)findViewById(R.id.textedit);
        picture = (ImageButton)findViewById(R.id.camerabtn);

        mydbHelper = dbHelper.getInstance(getApplicationContext());
        thisActivity = Translation1.this;

        te.setMaxHeight(te.getMaxHeight());
        save.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        trans.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        te.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        trans.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent trans_intent = new Intent(Translation1.this, Dictionarymode.class);
                trans_intent.putExtra("tTextIn", te.getText().toString());
                startActivityForResult(trans_intent,ACT_EDIT);
            }
        });
        picture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent picture_intent = new Intent(Translation1.this, setImage.class);
                startActivityForResult(picture_intent,0);
            }
        });
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                android.app.AlertDialog.Builder ad = new AlertDialog.Builder(Translation1.this);

                final EditText et = new EditText(Translation1.this);
                ad.setView(et);

                ad.setTitle("원본과 번역본을 저장합니다.");
                ad.setMessage("저장할 text의 title을 입력하세요");
                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v(TAG, "Yes Btn Click");
                        String value = et.getText().toString();
                        String text1 = te.getText().toString();
                        mydbHelper.text().insert(value, text1, " ");
                        Log.v(TAG, value);
                        dialog.dismiss();

                        Intent intent = new Intent(Translation1.this, Text1.class);
                        startActivityForResult(intent, 0);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ACT_EDIT && resultCode == RESULT_OK){
            te.setText(data.getStringExtra("TextOut"));
        }
    }
}
