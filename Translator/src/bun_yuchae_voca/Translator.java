package bun_yuchae_voca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.javonet.Javonet;
import com.javonet.JavonetException;
import com.javonet.JavonetFramework;
import com.javonet.api.NObject;

public class Translator {
	// Azure 키값중 하나
		final private String key = "01994f84e3d64105afdb2662bc3e837e";
		private long maked;
		private String secretKey;
		// C# 클래스
		private NObject cs;
		
		public Translator() {
			// Javonet api 권한 획득			
			try {
				Javonet.activate("rnwhdghl12@naver.com", "p7F4-Wd2m-m8EZ-Hz62-d2SX",JavonetFramework.v45);
				// 연동할 C# dll 설정
				Javonet.addReference("dll/translatorCS.dll");
				// c# class 생성
				cs = Javonet.New("Translator");
			} catch (JavonetException e) {
				e.printStackTrace();
			}
		}
		
		public String act(String text,String from,String to) {
			// SecretKey값이 유효하면 SecretKey값을 요청하지않음
			String result=null;
			try {
			if(!checkSecretKey())
				requestSecretKey();			
			
				result = cs.invoke("TranslateMethod",secretKey,text,from,to);
			} catch (JavonetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		// 생성시간으로부터 10분이 지나면 false 반환
		private boolean checkSecretKey() {
			long diff =maked - System.currentTimeMillis()/60000; 
			return (diff*diff)<100; 
		}
		
		// SecretKey 요청
		private void requestSecretKey() {			
			maked = System.currentTimeMillis()/60000;
			String line = new String(); 
			CloseableHttpClient client = HttpClientBuilder.create().build(); 
			HttpPost post = new HttpPost("https://api.cognitive.microsoft.com/sts/v1.0/issueToken"); 
			try { 
				post.setHeader("Ocp-Apim-Subscription-Key", key); 
				HttpResponse response = client.execute(post); 
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent())); 
				line = rd.readLine(); 
			} catch (IOException e) { 
				e.printStackTrace(); 
			} 		
			secretKey = "Bearer "+line;
		} 
		
}
