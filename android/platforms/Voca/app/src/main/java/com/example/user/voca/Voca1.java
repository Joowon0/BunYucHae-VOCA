package com.example.user.voca;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Text;

import static com.example.user.voca.Dictionary1.LIST_MENU;

public class Voca1 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca1);

        ListView listview ;
        ListViewAdapter_voca adapter;

        // Adapter 생성
        adapter = new ListViewAdapter_voca() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem( "Box", "Account Box Black 36dp") ;
        // 두 번째 아이템 추가.
        adapter.addItem("Circle", "Account Circle Black 36dp") ;
        // 세 번째 아이템 추가.
        adapter.addItem("Ind", "Assignment Ind Black 36dp") ;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;
                Bundle extras = new Bundle();
                String titleStr = item.getTitle() ;
                String descStr = item.getDesc() ;
                extras.putString("title",titleStr);
                extras.putString("desc",descStr);
            }
        }) ;

    }
}
