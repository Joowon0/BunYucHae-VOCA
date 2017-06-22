package com.example.user.voca;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import java.util.List;

import static com.example.user.voca.Dictionary1.LIST_MENU;

public class Text1 extends AppCompatActivity {
    final ListViewAdapter adapter = new ListViewAdapter() ;

    dbHelper mydbHelper;
    public Button s_date;
    public Button s_name;
    public Button s_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text1);

        mydbHelper = dbHelper.getInstance(getApplicationContext());

        ListView listview ;


        // Adapter 생성
        //adapter = new ListViewAdapter() ;


        s_date = (Button) findViewById(R.id.a_sort);
        s_name = (Button) findViewById(R.id.b_sort);
        s_tag = (Button) findViewById(R.id.c_sort);

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;
                Bundle extras = new Bundle();
                String titleStr = item.getTitle() ;
                String descStr = item.getDesc() ;
                extras.putInt("id",item.getId());
                Intent intent = new Intent(Text1.this, Text2.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        }) ;

        adapter.clearall();
        List<Text> inputs =  mydbHelper.text().getTextsSortedTitle();
        for (int i = 0; i < inputs.size(); i++) {
            int id = inputs.get(i).id;
            String t1 = inputs.get(i).title;
            String t2 = inputs.get(i).text + "...";
            adapter.addItem(id, t1, t2) ;
        }
        s_date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                adapter.clearall();
                List<Text> inputs =  mydbHelper.text().getTextsSortedDate();
                for (int i = 0; i < inputs.size(); i++) {
                    int id = inputs.get(i).id;
                    String t1 = inputs.get(i).title;
                    String t2 = inputs.get(i).text + "...";
                    adapter.addItem(id, t1, t2) ;
                }
                adapter.notifyDataSetChanged();
            }
        });

        s_name.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                adapter.clearall();
                List<Text> inputs =  mydbHelper.text().getTextsSortedTitle();
                for (int i = 0; i < inputs.size(); i++) {
                    int id = inputs.get(i).id;
                    String t1 = inputs.get(i).title;
                    String t2 = inputs.get(i).text + "...";
                    adapter.addItem(id, t1, t2) ;
                }
                adapter.notifyDataSetChanged();
            }
        });

        s_tag.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                adapter.clearall();
                List<Text> inputs =  mydbHelper.text().getTextsGroupByT1();
                for (int i = 0; i < inputs.size(); i++) {
                    int id = inputs.get(i).id;
                    String t1 = inputs.get(i).title;
                    String t2 = inputs.get(i).text + "...";
                    adapter.addItem(id, t1, t2) ;
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onPause() {
        adapter.clearall();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clearall();
        List<Text> inputs =  mydbHelper.text().getTextsSortedTitle();
        for (int i = 0; i < inputs.size(); i++) {
            int id = inputs.get(i).id;
            String t1 = inputs.get(i).title;
            String t2 = inputs.get(i).text + "...";
            adapter.addItem(id, t1, t2) ;
        }
        adapter.notifyDataSetChanged();
    }
}
