package com.example.user.voca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Translation1 extends AppCompatActivity {
    final static int ACT_EDIT = 0;
    public Button trans;
    public EditText te;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        trans = (Button) findViewById(R.id.transbtn);
        te = (EditText)findViewById(R.id.textedit);

        trans.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent trans_intent = new Intent(Translation1.this, Dictionarymode.class);
                trans_intent.putExtra("tTextIn", te.getText().toString());
                startActivityForResult(trans_intent,ACT_EDIT);
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
