package bun_yuchae_voca;

import bun_yuchae_voca.Scanner.Format;

public class Test {
	public static void main(String args[]){
		Scanner sc = new Scanner();
		String body = sc.act(false, "http://dl.a9t9.com/blog/ocr-online/screenshot.jpg", "eng",Format.URL);			
		Translator tr = Translator.getInstance();
		System.out.println(tr.act(body, "en", "ko"));
	}
}
