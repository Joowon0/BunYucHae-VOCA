package com.example.joowon.trysqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainTagActivity extends AppCompatActivity {
    dbHelper mydbHelper;
    //tagListHelper tagHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tag);

        mydbHelper = dbHelper.getInstance(getApplicationContext());
        //tagHelper = new tagListHelper(getApplicationContext());

        final TextView result = (TextView) findViewById(R.id.result);

        final EditText etTitle = (EditText) findViewById(R.id.title);
        final EditText etID    = (EditText) findViewById(R.id.id);

        Button goText = (Button) findViewById(R.id.toTextPage);
        goText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainTagActivity.this, MainActivity.class));
            }
        });

        Button goWord = (Button) findViewById(R.id.toWordPage);
        goWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainTagActivity.this, MainWordActivity.class));
            }
        });

        Button insert = (Button) findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                mydbHelper.tag().insert(title);
            }
        });

        Button removeByID = (Button) findViewById(R.id.removeByID);
        removeByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(etID.getText().toString());
                mydbHelper.tag().removeById(id);
            }
        });

        Button modiNameByID = (Button) findViewById(R.id.modiNameByID);
        modiNameByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                int id = Integer.parseInt(etID.getText().toString());
                mydbHelper.tag().modifyNameById(id, title);
            }
        });

        Button getSearch = (Button) findViewById(R.id.getSearch);
        getSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                result.setText(mydbHelper.tag().getSearch(title).toString());
            }
        });

        Button getTagByID = (Button) findViewById(R.id.getTagByID);
        getTagByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(etID.getText().toString());
                result.setText(mydbHelper.tag().getTagById(id).toString());
            }
        });

        Button getTNumById = (Button) findViewById(R.id.getTNumById);
        getTNumById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(etID.getText().toString());
                result.setText("" + mydbHelper.tag().getTNumbyId(id));
            }
        });

        Button getResultByDate = (Button) findViewById(R.id.getResultByDate);
        getResultByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(mydbHelper.tag().toString(mydbHelper.tag().getTagsSortedByDate()));
            }
        });

        Button getResultByName = (Button) findViewById(R.id.getResultByName);
        getResultByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(mydbHelper.tag().toString(mydbHelper.tag().getTagsSortedByName()));
            }
        });

        Button getWNumById = (Button) findViewById(R.id.getWNumById);
        getWNumById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(etID.getText().toString());
                result.setText("" + mydbHelper.tag().getWNumbyId(id));
            }
        });
    }
}
