package bun_yuchae_voca;

import bun_yuchae_voca.Scanner.Format;

public class Test {
	public static void main(String args[]){
		Scanner sc = new Scanner();
		String body = sc.act(false, "http://dl.a9t9.com/blog/ocr-online/screenshot.jpg", "eng",Format.URL);		
		System.out.println(body);
		
		Translator tr = new Translator();
		String trans = tr.act(body, "en", "ko");
		System.out.println(trans);
	}
}
