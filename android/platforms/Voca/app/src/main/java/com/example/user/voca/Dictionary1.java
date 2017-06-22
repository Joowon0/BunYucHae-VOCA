package com.example.user.voca;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class Dictionary1 extends AppCompatActivity {
    ImageButton btn;
    EditText input;
    ArrayList<DictWord> dictWords;
    final static int ACT_EDIT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dictWords = new ArrayList<DictWord>();
        setContentView(R.layout.activity_dictionary1);
        input = (EditText)findViewById(R.id.save_text);
        btn = (ImageButton)findViewById(R.id.search_dic);
        final ArrayList<String> items = new ArrayList<String>();
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,items);
        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Input = input.getText().toString();
                Dictionary1.FindWord fd = new Dictionary1.FindWord(Input);
                fd.start();
                try {
                    fd.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                items.clear();
                dictWords = fd.getDictWord();
                for(int i=0;i<dictWords.size();++i){
                    Log.d("D",dictWords.get(i).toString());
                    items.add(dictWords.get(i).toString());
                }
                adapter.notifyDataSetChanged();
            }

        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                String Text = (String) parent.getItemAtPosition(position);
                Intent dic_intent = new Intent(Dictionary1.this, Dictionary2.class);
                DictWord word = dictWords.get(position);
                dic_intent.putExtra("TextIn", word.href);
                startActivityForResult(dic_intent,ACT_EDIT);
        }
    });
    }
    class FindWord extends Thread{
        ArrayList<DictWord> value;
        String Word;
        FindWord(String word){
            Word = word;
            value=new ArrayList<DictWord>();
        }
        public ArrayList<DictWord> getDictWord(){
            return value;
        }
        public String[] getWord(){
            String[] words = new String[value.size()];
            for(int i=0;i<words.length;++i) {
                words[i] = value.get(i).toString();
            }
            return words;
        }
        public void run(){
            value = null;
            while(value==null)
                value = Dictionary.getData(Word,null,null);

        }
    }
}
