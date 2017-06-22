package com.example.user.voca;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.w3c.dom.Text;

import java.util.List;


public class Voca1 extends AppCompatActivity {
    public Button s_date;
    public Button s_name;
    public Button s_tag;

    dbHelper mydbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca1);
        ListView listview ;
        final ListViewAdapter_voca adapter;

        mydbHelper = dbHelper.getInstance(getApplicationContext());

        // Adapter 생성
        adapter = new ListViewAdapter_voca() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        s_date = (Button) findViewById(R.id.a_sort);
        s_name = (Button) findViewById(R.id.b_sort);
        s_tag = (Button) findViewById(R.id.c_sort);


        // 첫 번째 아이템 추가.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;
                Bundle extras = new Bundle();
                String titleStr = item.getTitle() ;
                String descStr = item.getDesc() ;
            }
        }) ;

        adapter.clearall();
        List<com.example.user.voca.Word> inputs =  mydbHelper.word().getWordsSortedName();
        for (int i = 0; i < inputs.size(); i++) {
            int id = inputs.get(i).id;
            String t1 = inputs.get(i).name;
            String t2 = inputs.get(i).defs ;
            adapter.addItem(id, t1, t2) ;
        }
        s_date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                adapter.clearall();
                List<com.example.user.voca.Word> inputs =  mydbHelper.word().getWordsSortedDate();
                for (int i = 0; i < inputs.size(); i++) {
                    int id = inputs.get(i).id;
                    String t1 = inputs.get(i).name;
                    String t2 = inputs.get(i).defs ;
                    adapter.addItem(id, t1, t2) ;
                }
                adapter.notifyDataSetChanged();
            }
        });

        s_name.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                adapter.clearall();
                List<com.example.user.voca.Word> inputs =  mydbHelper.word().getWordsSortedName();
                for (int i = 0; i < inputs.size(); i++) {
                    int id = inputs.get(i).id;
                    String t1 = inputs.get(i).name;
                    String t2 = inputs.get(i).defs ;
                    adapter.addItem(id, t1, t2) ;
                }
                adapter.notifyDataSetChanged();
            }
        });

        s_tag.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                adapter.clearall();
                List<com.example.user.voca.Word> inputs =  mydbHelper.word().getWordsGroupByT1();
                for (int i = 0; i < inputs.size(); i++) {
                    int id = inputs.get(i).id;
                    String t1 = inputs.get(i).name;
                    String t2 = inputs.get(i).defs ;
                    adapter.addItem(id, t1, t2) ;
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
