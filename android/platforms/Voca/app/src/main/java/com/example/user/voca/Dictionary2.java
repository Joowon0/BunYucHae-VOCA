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

        LinearLayout llBackground = (LinearLayout)findViewById(R.id.t2ll);
        Voca.setText(getIntent().getStringExtra("TextIn"));
        Voca.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));

    }
}

