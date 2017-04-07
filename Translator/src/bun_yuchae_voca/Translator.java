package bun_yuchae_voca;

import com.javonet.Javonet;
import com.javonet.JavonetException;
import com.javonet.JavonetFramework;
import com.javonet.api.NObject;

import bun_yuchae_voca.KeyManager.CompanyType;

public class Translator {
	// C# 클래스
	KeyManager manager;
	private NObject cs;
	
	public Translator() {
		manager = KeyManager.getInstance();

		// Javonet api 권한 획득	
		try {
			Javonet.activate("rnwhdghl12@naver.com", manager.requestKey(CompanyType.JAVONET),JavonetFramework.v45);
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
			result = cs.invoke("TranslateMethod",manager.requestKey(CompanyType.MS_BING),text,from,to);
		} catch (JavonetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
