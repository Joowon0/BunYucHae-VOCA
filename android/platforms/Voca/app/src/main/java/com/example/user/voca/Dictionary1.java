package com.example.user.voca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Dictionary1 extends AppCompatActivity {
    static final String[] LIST_MENU = {"List1", "List2", "List3", "List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3\", \"List3", "List3", "List3"} ;

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
            }

        });
    }

}
