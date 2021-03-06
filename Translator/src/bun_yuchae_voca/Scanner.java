package bun_yuchae_voca;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import sun.misc.BASE64Encoder;

public class Scanner {
	
	 final String key="2e57ea933f88957";
	 enum Format{URL, Base64};
	 public Scanner(){
		 
	 }
	 public String act( boolean isOverlayRequired, String data, String language,Format f){
		 // 스캔된 결과 값
		String scanned=null;
		// JSON body 값
		String post=null;
		try {
			// JSON 요청
			 post = sendPost( isOverlayRequired, data, language,f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		// JSON으로부터 text 추출
		scanned = getTextFromJSON(post);
		return scanned;
	}
	 
	private String getTextFromJSON(String body) {
		JSONObject ob=new JSONObject(body);
		String text = new String();
		System.out.println(ob.toString());
		JSONArray bodyArray = (JSONArray) ob.get("ParsedResults");			
		for(int i = 0 ; i < bodyArray.length(); i++) {
			JSONObject data = (JSONObject) bodyArray.get(i);  
			text +=(data.get("ParsedText").toString());
		}
		return text;
	}
	private HttpsURLConnection getConnection() {
		URL obj=null;
		try {
			obj = new URL("https://api.ocr.space/parse/image");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // OCR API Endpoints
			HttpsURLConnection con=null;
			try {
				con = (HttpsURLConnection) obj.openConnection();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//add 요청 헤드값 설정
			try {
				con.setRequestMethod("POST");
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			return con;
	 }
	 private String sendPost( boolean isOverlayRequired, String data, String language,Format f) throws Exception {			 		
		HttpsURLConnection con = getConnection();
		// JSON 파라미터 설정
		JSONObject postDataParams = getJSONObejct(isOverlayRequired,data,language,f);
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		
		// JSON 데이터 스트링으로 변환
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
	private JSONObject getJSONObejct(boolean isOverlayRequired, String data, String language, Format f) {
		// TODO Auto-generated method stub
		JSONObject object = new JSONObject();		
		object.put("apikey", key);//TODO Add your Registered API key		
		if(f == Format.URL) {
			object.put("url", data);
		}
		else if(f == Format.Base64){			
			String fileExt = data.substring(data.lastIndexOf(".")+1);
			if(fileExt.compareTo("jpg")==0){
				fileExt="jpeg";
			}
			String base64 = imageToBase64(data);
			object.put("base64Image", "data:image/"+fileExt+";base64,"+base64);
		}
		object.put("isOverlayRequired", isOverlayRequired);
		object.put("language", language);
		return object;
	}

	private String imageToBase64(String path){
		File file =  new File(path);
        String encodedfile = null;                	
        try {
        	FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");            
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return encodedfile;
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
