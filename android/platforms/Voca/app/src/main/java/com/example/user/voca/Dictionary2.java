package com.example.user.voca;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.user.voca.Dictionarymode.TAG;

public class Dictionary2 extends Activity
{
    public Button s_voca;
    public TextView Voca;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary2);
        Intent intent = getIntent();
        Voca = (TextView)findViewById(R.id.voca);
        s_voca = (Button)findViewById(R.id.savebtn);

        LinearLayout llBackground = (LinearLayout)findViewById(R.id.t2ll);
        Voca.setText(getIntent().getStringExtra("TextIn"));
        Voca.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        s_voca.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));



    }
}

