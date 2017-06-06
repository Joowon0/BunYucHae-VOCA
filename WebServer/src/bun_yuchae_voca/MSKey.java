package bun_yuchae_voca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import bun_yuchae_voca.KeyManager.CompanyType;
public class MSKey extends Key {
	private long maked;
	public MSKey(String k, CompanyType t) {
		super(k, t);
		maked=1000000000;
		// TODO Auto-generated constructor stub
	}
	
	// SecretKey 요청
	private void requestSecretKey() {			
		maked = System.currentTimeMillis()/60000;
		String line = new String(); 
		
		CloseableHttpClient client = HttpClientBuilder.create().build(); 
		HttpPost post = new HttpPost("https://api.cognitive.microsoft.com/sts/v1.0/issueToken"); 
		try { 
			post.setHeader("Ocp-Apim-Subscription-Key", value); 
			HttpResponse response = client.execute(post); 
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent())); 
			line = rd.readLine(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 				
		secretKey = "Bearer "+line;		
	}
	
	// 생성시간으로부터 10분이 지나면 false 반환
	protected boolean checkSecretKey() {
		long diff =maked - System.currentTimeMillis()/60000; 
		return (diff*diff)<100; 
	}
	
	@Override
	public synchronized String request() {
		// TODO Auto-generated method stub
		if(!checkSecretKey())
			requestSecretKey();
		
		return secretKey;
	}
	public static void main(String args[]){
		KeyManager mana=KeyManager.getInstance();
		String ms = mana.requestKey(KeyManager.CompanyType.MS_BING);
		System.out.println(ms);
	}
}
