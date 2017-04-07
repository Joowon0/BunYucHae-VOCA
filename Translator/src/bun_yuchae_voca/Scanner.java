package bun_yuchae_voca;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class Scanner {
	
	 final String key="2e57ea933f88957";
	 public Scanner(){
		 
	 }
	 public String act( boolean isOverlayRequired, String imageUrl, String language){
		 // ��ĵ�� ��� ��
		String scanned=null;
		// JSON body ��
		String post=null;
		try {
			// JSON ��û
			 post = sendPost( isOverlayRequired, imageUrl, language);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// JSON���κ��� text ����
		scanned = getTextFromJSON(post);
		return scanned;
	}
	 
	 private String getTextFromJSON(String body) {
		 JSONObject ob=new JSONObject(body);
		 String text = new String();
			JSONArray bodyArray = (JSONArray) ob.get("ParsedResults");

			for(int i = 0 ; i < bodyArray.length(); i++) {
				JSONObject data = (JSONObject) bodyArray.get(i);  
				text +=(data.get("ParsedText").toString());
			}
		return text;
	 }
	 
	 private String sendPost( boolean isOverlayRequired, String imageUrl, String language) throws Exception {			 
		URL obj = new URL("https://api.ocr.space/parse/image"); // OCR API Endpoints
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		//add ��û ��尪 ����
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
		// JSON �Ķ���� ����
		JSONObject postDataParams = new JSONObject();		
		postDataParams.put("apikey", key);//TODO Add your Registered API key
		postDataParams.put("isOverlayRequired", isOverlayRequired);
		postDataParams.put("url", imageUrl);
		postDataParams.put("language", language);
				
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		
		// JSON ������ ��Ʈ������ ��ȯ
		wr.writeBytes(getPostDataString(postDataParams));
		wr.flush();
		wr.close();
		

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
		    response.append(inputLine);
		    response.append("\n");
		}
		in.close();
		
		//return result
	    return String.valueOf(response);
	}
	 private String getPostDataString(JSONObject params) throws Exception {
	
		StringBuilder result = new StringBuilder();
	    boolean first = true;
	
	    Iterator<String> itr = params.keys();
	
	    while (itr.hasNext()) {		
	        String key = itr.next();
	        Object value = params.get(key);
	
	        if (first)
	            first = false;
	        else
	            result.append("&");
	
	        result.append(URLEncoder.encode(key, "UTF-8"));
	        result.append("=");
	        result.append(URLEncoder.encode(value.toString(), "UTF-8"));		
	    }
	    return result.toString();
	}
}
