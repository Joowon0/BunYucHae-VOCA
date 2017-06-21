package com.example.user.voca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Editmode extends AppCompatActivity {
    public Button editbtn;
    public EditText uet;
    public EditText det;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmode);
        editbtn = (Button) findViewById(R.id.dicmode);
        uet = (EditText)findViewById(R.id.upedit);
        det = (EditText)findViewById(R.id.downedit);

        Intent dic_intent = getIntent();
        uet.setText(dic_intent.getStringExtra("TextIn"));
        det.setText(dic_intent.getStringExtra("TextIn1"));

        editbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent edit_intent = new Intent(Editmode.this, Dictionarymode.class);
                edit_intent.putExtra("TextOut", uet.getText().toString());
                edit_intent.putExtra("TextOut1", det.getText().toString());
                setResult(RESULT_OK,edit_intent);
                finish();
            }
        });
    }
}
