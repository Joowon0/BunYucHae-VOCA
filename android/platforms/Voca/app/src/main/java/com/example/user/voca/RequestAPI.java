package com.example.user.voca;

import android.content.ContentValues;



/**
 * Created by rnwhd on 2017-06-22.
 */

public class RequestAPI {
    public String requestScanning(String data, String source ){
        Request request = new Request(data,source,null,0);
        request.start();
        try {
            request.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return request.response();
    }
    public  String requestTranslating(String data, String source,String target){
        Request request = new Request(data,source,target,1);
        request.start();
        try {
            request.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return request.response();

    }
    public class Request extends Thread{
        int flag;
        String data,source,target,text;
        Request(String d,String s,String t,int f){
            flag=f;
            data=d;
            source=s;
            target=t;
        }
        public String response(){return text;}
        public  void run(){
            if(flag==0){
                String retV=null;
                JSONParser parser = new JSONParser();
                ContentValues values=new ContentValues();
                values.put("data",data);
                values.put("source",source);
                String obj=parser.httpRequest("http://default-environment.nsgxgwmm5p.us-west-2.elasticbeanstalk.com/Scanning","POST",values);
                int first=obj.indexOf(':')+2,last = obj.indexOf(',')-1;
                if(first<last) {
                    String sub = obj.substring(first,last);
                    text = sub;
                }
                else
                    text="";
            }
            else{
                String retV=null;
                JSONParser parser = new JSONParser();
                ContentValues values=new ContentValues();
                values.put("data",data);
                values.put("source",source);
                values.put("target",target);
                String obj=parser.httpRequest("http://default-environment.nsgxgwmm5p.us-west-2.elasticbeanstalk.com/Translating","POST",values);
                /*try {
                    text= obj.getString("translated");
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                int first=obj.indexOf(':')+2,last = obj.indexOf(',')-1;
                if(first<last) {
                    String sub = obj.substring(first,last);
                    text = sub;
                }
                else
                    text="";


            }
        }
    }
}
