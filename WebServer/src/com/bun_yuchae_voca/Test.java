package com.bun_yuchae_voca;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test {
	public static void main(String args[]){
		String data ="{\"source\":\"English\",\"target\":\"Korean\",\"data\":\"I eat apple.\"}";
		JSONObject json = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			json = (JSONObject) parser.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(json.get("data").toString());
	}
}
