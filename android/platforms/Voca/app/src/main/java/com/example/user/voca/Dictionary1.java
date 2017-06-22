package com.example.user.voca;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Dictionary1 extends AppCompatActivity {
    static final String[] LIST_MENU = {"List1", "List2", "List3", "List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3", "List3", "List3"} ;
    final static int ACT_EDIT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary1);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU);

        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                String Text = (String) parent.getItemAtPosition(position);
                Intent dic_intent = new Intent(Dictionary1.this, Dictionary2.class);
                dic_intent.putExtra("TextIn", Text.toString());
                startActivityForResult(dic_intent,ACT_EDIT);

        }
    });



    }
}
