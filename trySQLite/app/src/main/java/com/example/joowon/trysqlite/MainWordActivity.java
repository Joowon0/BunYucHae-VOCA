package com.example.joowon.trysqlite;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainWordActivity extends AppCompatActivity {
    dbHelper mydbHelper;
    //wordListHelper wordHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_word);

        mydbHelper = dbHelper.getInstance(getApplicationContext());
        //wordHelper = new wordListHelper(getApplicationContext());

        final TextView result = (TextView) findViewById(R.id.result);

        final EditText etTitle = (EditText) findViewById(R.id.title);
        final EditText etText  = (EditText) findViewById(R.id.text);
        final EditText etID    = (EditText) findViewById(R.id.id);
        final EditText etTag   = (EditText) findViewById(R.id.tag);

        Button goText = (Button) findViewById(R.id.toWordPage);
        goText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainWordActivity.this, MainActivity.class));
            }
        });

        Button goTag = (Button) findViewById(R.id.toTagPage);
        goTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainWordActivity.this, MainTagActivity.class));
            }
        });

        Button insert = (Button) findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String text  = etText.getText().toString();
                mydbHelper.word().insert(title, text);
            }
        });

        Button removeByID = (Button) findViewById(R.id.removeByID);
        removeByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(etID.getText().toString());
                if (!mydbHelper.word().removeById(id))
                    result.setText("fail in removeByID");
            }
        });

        Button getTextsByTag = (Button) findViewById(R.id.getTextsByTag);
        getTextsByTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = Integer.parseInt(etTag.getText().toString());
                result.setText(mydbHelper.word().toString(mydbHelper.word().getWordsByTag(tag)));
            }
        });

        Button modiContentByID = (Button) findViewById(R.id.modiContentByID);
        modiContentByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String text  = etText.getText().toString();
                int id  = Integer.parseInt(etID.getText().toString());


                if (title == "") title = null;
                if (text  == "") text  = null;

                if (!mydbHelper.word().modifyContentById(id, title, text))
                    result.setText("fail in modifyContentByID");
            }
        });

        Button getTextByID = (Button) findViewById(R.id.getTextByID);
        getTextByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id  = Integer.parseInt(etID.getText().toString());
                Word wordResult = mydbHelper.word().getWordsById(id);
                if (wordResult == null)
                    result.setText("no matching ID");
                else
                    result.setText(wordResult.toString());
            }
        });


        Button getSearch = (Button) findViewById(R.id.getSearch);
        getSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();

                result.setText(mydbHelper.word().toString(mydbHelper.word().getSearch(title)));
            }
        });

        Button addTagByID = (Button) findViewById(R.id.addTagByID);
        addTagByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id  = Integer.parseInt(etID.getText().toString());
                int tag = Integer.parseInt(etTag.getText().toString());
                if (!mydbHelper.word().addTagById(id, tag))
                    result.setText("fail in addTagByID");
            }
        });

        Button removeTagById = (Button) findViewById(R.id.removeTagByID);
        removeTagById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id  = Integer.parseInt(etID.getText().toString());
                int tag = Integer.parseInt(etTag.getText().toString());
                if (!mydbHelper.word().removeTagById(id, tag))
                    result.setText("fail in removeTagByID");
            }
        });

        Button getResult = (Button) findViewById(R.id.getResult);
        getResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(mydbHelper.word().toString(mydbHelper.word().getWordsSortedDate()));
            }
        });

        Button getResult2 = (Button) findViewById(R.id.getResult2);
        getResult2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(mydbHelper.word().toString(mydbHelper.word().getWordsSortedName()));
            }
        });

        Button getResult3 = (Button) findViewById(R.id.getResult3);
        getResult3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(mydbHelper.word().toString(mydbHelper.word().getWordsGroupByT1()));
            }
        });
    }
}
