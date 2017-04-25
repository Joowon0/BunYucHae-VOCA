package bun_yuchae_voca;

import com.javonet.Javonet;
import com.javonet.JavonetException;
import com.javonet.JavonetFramework;
import com.javonet.api.NObject;

import bun_yuchae_voca.KeyManager.CompanyType;

public class Translator {
	// C# Ŭ����
	KeyManager manager;
	private NObject cs;
	
	public Translator() {
		manager = KeyManager.getInstance();
		// Javonet api ���� ȹ��	
		try {
			Javonet.activate("rnwhdghl12@naver.com", manager.requestKey(CompanyType.JAVONET),JavonetFramework.v45);
			// ������ C# dll ����
			Javonet.addReference("dll/translatorCS.dll");
			// c# class ����
			cs = Javonet.New("Translator");
		} catch (JavonetException e) {
			e.printStackTrace();
		}
	}
	
	public String act(String text,String from,String to) {
		// SecretKey���� ��ȿ�ϸ� SecretKey���� ��û��������
		String result=null;
		text = modifyText(text);
		try {
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
