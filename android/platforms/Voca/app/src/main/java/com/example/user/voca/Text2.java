package com.example.user.voca;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Text2 extends Activity
{

    public TextView title;
    public TextView cont;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text2);
        Intent intent = getIntent();
        title = (TextView)findViewById(R.id.title);
        cont = (TextView)findViewById(R.id.content);

        LinearLayout llBackground = (LinearLayout)findViewById(R.id.t2ll);
        title.setText(getIntent().getStringExtra("title"));
        cont.setText(getIntent().getStringExtra("desc"));

        // 다이얼로그를 생성해, 색상 스트링과 설명을 출력한다.
    }


}

