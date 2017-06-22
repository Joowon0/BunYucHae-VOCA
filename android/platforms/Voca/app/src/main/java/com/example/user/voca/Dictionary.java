package com.example.user.voca;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Dictionary {
    final static String EK = "http://endic.naver.com/search.nhn?sLn=kr&searchOption=entry_idiom&query=";

    public static ArrayList<DictWord> getData(String word,String dst,String src){
        ArrayList<DictWord>words=new ArrayList<DictWord>();
        words = getFirstPageWord(word);
        for(int i=0;i<words.size();++i){
            DictWord data = words.get(i);
        }
        return words;
    }
    public static ArrayList<DictWord> parseWords(String href){
        ArrayList<DictWord>words=new ArrayList<DictWord>();
        DictWord data = null;

        String url = "http://endic.naver.com"+href;
        try {
            Document doc = null;
            while(doc==null)
                doc = Jsoup.connect(url).get();
            Elements ele = doc.select("div.tit h3");
            String value = ele.get(0).text();
            ele = doc.select("dl.list_a3 span");
            for(int i=0;i<ele.size();++i){
                String className =ele.get(i).className();
                if(className.compareTo("itt_button")!=0&&className.compareTo("blind")!=0){
                    if(className.compareTo("fnt_e11")==0){
                        if(data != null){
                            words.add(data);
                        }
                        data = new DictWord();
                        data.value = value;
                        data.mean+=ele.get(i).text();
                    }
                    else if(className.compareTo("fnt_e07")==0){
                        data.examples.add(ele.get(i).text()+"\n"+ele.get(++i).text());
                    }
                    else{
                        Log.d("Text",ele.get(i).text());
                        if(ele.get(i)!=null) {
                            if(data==null) {
                                data = new DictWord();
                                words.add(data);
                            }
                            data.mean += ele.get(i).text();
                        }
                    }
                }
            }
            Log.d("Data",data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }
    static public ArrayList<DictWord> getFirstPageWord(String word){
        ArrayList<DictWord> words = new ArrayList<DictWord>();
        try {
            Document doc = Jsoup.connect(EK+word+"&theme=&pageNo=1").get();
            Elements ele = doc.select("div.word_num_nobor span");
            String prev = "a";
            DictWord data=null;
            for(int i=0;i<ele.size();++i){
                String className = ele.get(i).className();
                String text = ele.get(i).text();
                if(className.compareTo("fnt_e30")==0){
                    if(data!=null){
                        words.add(data);
                    }
                    data = new DictWord();
                    data.href = ele.get(i).select("a").get(0).attr("href")+ele.get(i).getElementsByAttribute("a").attr("href");
                    data.value=ele.get(i).select("a").get(0).text();
                }
                else if(className.compareTo("fnt_e25")==0){
                    if(ele.get(i).text()!=null)
                        data.pronunciationSymbol=ele.get(i).text();
                }
                else if(className.compareTo("fnt_k09")==0){
                    if(ele.get(i).text()!=null){
                        data.mean+=ele.get(i).text();
                    }
                }
                else if(className.compareTo("fnt_k05")==0){
                    if(ele.get(i).text()!=null){
                        data.mean +=ele.get(i).text();
                    }
                }
                prev = className;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return words;
    }
}