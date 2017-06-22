package com.example.joowon.trysqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {
    dbHelper mydbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydbHelper = dbHelper.getInstance(getApplicationContext());

        final TextView result = (TextView) findViewById(R.id.result);

        final EditText etTitle = (EditText) findViewById(R.id.title);
        final EditText etText  = (EditText) findViewById(R.id.text);
        final EditText etText2 = (EditText) findViewById(R.id.text2);
        final EditText etID    = (EditText) findViewById(R.id.id);
        final EditText etTag   = (EditText) findViewById(R.id.tag);

        Button goText = (Button) findViewById(R.id.toWordPage);
        goText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainWordActivity.class));
            }
        });

        Button goTag = (Button) findViewById(R.id.toTagPage);
        goTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainTagActivity.class));
            }
        });

        Button insert = (Button) findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            dbHelper helper = mydbHelper;
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String text  = etText.getText().toString();
                String text2  = etText2.getText().toString();
                mydbHelper.text().insert(title, text, text2);
            }
        });

        Button removeByID = (Button) findViewById(R.id.removeByID);
        removeByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(etID.getText().toString());
                if (!mydbHelper.text().removeById(id))
                    result.setText("fail in removeByID");
            }
        });

        Button getTextsByTag = (Button) findViewById(R.id.getTextsByTag);
        getTextsByTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = Integer.parseInt(etTag.getText().toString());
                result.setText(mydbHelper.text().toString(mydbHelper.text().getTextsByTag(tag)));
            }
        });

        Button modiContentById = (Button) findViewById(R.id.modiContentById);
        modiContentById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String text  = etText.getText().toString();
                String text2  = etText2.getText().toString();
                int id  = Integer.parseInt(etID.getText().toString());


                if (title == "") title = null;
                if (text  == "") text  = null;
                if (text  == "") text2  = null;

                if (!mydbHelper.text().modifyContentById(id, title, text, text2))
                    result.setText("fail in modifyContentByID");
            }
        });

        Button getSearch = (Button) findViewById(R.id.getSearch);
        getSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();

                result.setText(mydbHelper.text().toString(mydbHelper.text().getSearch(title)));
            }
        });

        Button getTextByID = (Button) findViewById(R.id.getTextByID);
        getTextByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id  = Integer.parseInt(etID.getText().toString());
                Text textResult = mydbHelper.text().getTextById(id);
                if (textResult == null)
                    result.setText("no matching ID");
                else
                    result.setText(textResult.toString());
            }
        });

        Button addTagByID = (Button) findViewById(R.id.addTagByID);
        addTagByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id  = Integer.parseInt(etID.getText().toString());
                int tag = Integer.parseInt(etTag.getText().toString());
                if (!mydbHelper.text().addTagById(id, tag))
                    result.setText("fail in addTagByID");
            }
        });

        Button removeTagById = (Button) findViewById(R.id.removeTagByID);
        removeTagById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id  = Integer.parseInt(etID.getText().toString());
                int tag = Integer.parseInt(etTag.getText().toString());
                if (!mydbHelper.text().removeTagById(id, tag))
                    result.setText("fail in removeTagByID");
            }
        });

        Button getResult = (Button) findViewById(R.id.getResult);
        getResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(mydbHelper.text().toString(mydbHelper.text().getTextsSortedDate()));
            }
        });

        Button getResult2 = (Button) findViewById(R.id.getResult2);
        getResult2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(mydbHelper.text().toString(mydbHelper.text().getTextsSortedTitle()));
            }
        });

        Button getResult3 = (Button) findViewById(R.id.getResult3);
        getResult3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(mydbHelper.text().toString(mydbHelper.text().getTextsGroupByT1()));
            }
        });
    }
}

