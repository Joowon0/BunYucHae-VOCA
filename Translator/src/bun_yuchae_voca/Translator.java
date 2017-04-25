
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

import bun_yuchae_voca.KeyManager.CompanyType;

public class Translator {
	// Azure 키값중 하나
	KeyManager manager;
		// C# 클래스
		private NObject cs;		
		public Translator() {
			// Javonet api 권한 획득
			manager = KeyManager.getInstance();
			try {
				Javonet.activate("rnwhdghl12@naver.com",  manager.requestKey(CompanyType.JAVONET),JavonetFramework.v45);				
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
			text = modifyText(text);
			try {
				System.out.println(manager.requestKey(CompanyType.MS_BING));
				result = cs.invoke("TranslateMethod",manager.requestKey(CompanyType.MS_BING),text,from,to);
			} catch (JavonetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		private String modifyText(String text) {
			String[] result=null;
			result = text.split("\n");
			StringBuilder a=new StringBuilder();
			for(int i=0;i<result.length;++i)
				a.append(result[i]);
			return a.toString();
		}
		
}
