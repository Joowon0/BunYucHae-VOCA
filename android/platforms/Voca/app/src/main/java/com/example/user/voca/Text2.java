package com.example.user.voca;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Text2 extends Activity
{

    public TextView title;
    public TextView cont1;
    public TextView cont2;
    public Button remove;

    dbHelper mydbHelper;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text2);

        mydbHelper = dbHelper.getInstance(getApplicationContext());

        Intent intent = getIntent();
        title = (TextView)findViewById(R.id.title);
        cont1 = (TextView)findViewById(R.id.content1);
        cont2 = (TextView)findViewById(R.id.content2);

        cont1.setMovementMethod(ScrollingMovementMethod.getInstance());
        cont2.setMovementMethod(ScrollingMovementMethod.getInstance());

        id=0;

        title.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        cont1.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));
        cont2.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));

        LinearLayout llBackground = (LinearLayout)findViewById(R.id.t2ll);

        getIntent().getIntExtra("id",id);

        title.setText(mydbHelper.text().getTextById(getIntent().getIntExtra("id",id)).title);
        cont1.setText(mydbHelper.text().getTextById(getIntent().getIntExtra("id",id)).text);
        cont2.setText(mydbHelper.text().getTextById(getIntent().getIntExtra("id",id)).txt2);

        // 다이얼로그를 생성해, 색상 스트링과 설명을 출력한다.

        remove = (Button) findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntent().getIntExtra("id",id);
                mydbHelper.text().removeById(id);
                System.out.println(id);
                Intent remove_intent = new Intent(Text2.this, Text1.class);
                startActivityForResult(remove_intent,0);
                finish();
            }
        });
    }
}

