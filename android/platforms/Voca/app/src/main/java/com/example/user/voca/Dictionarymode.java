package com.example.user.voca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Dictionarymode extends AppCompatActivity {
    final static int ACT_EDIT = 0;
    public Button dicbtn;
    public TextView utv;
    public TextView dtv;
    public static final String TAG = "Test_Alert_Dialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionarymode);
        dicbtn = (Button) findViewById(R.id.editmode);
        utv = (TextView)findViewById(R.id.upedit);
        dtv = (TextView)findViewById(R.id.downtextview);

        Intent intent = getIntent();
        utv.setText(intent.getStringExtra("tTextIn"));

        dicbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent dic_intent = new Intent(Dictionarymode.this, Editmode.class);
                dic_intent.putExtra("TextIn", utv.getText().toString());
                dic_intent.putExtra("TextIn1", dtv.getText().toString());
                startActivityForResult(dic_intent,ACT_EDIT);
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
