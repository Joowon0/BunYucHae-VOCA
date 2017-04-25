package bun_yuchae_voca;

public class Test {
	public static void main(String args[]){
		//Scanner sc = new Scanner();
		//String body = sc.act(false, "http://dl.a9t9.com/blog/ocr-online/screenshot.jpg", "eng");
		//System.out.println(body);
		Translator tr = new Translator();
		String trans = tr.act("BEIJING ?? Jing Yuechen, the founder of an Internet start-up here in the Chinese capital, has no interest in overthrowing the Communist Party. But these days she finds herself cursing the nation's smothering cyberpolice as she tries ?? and fails to browse photo-sharing websites like Flickr and struggles to stay in touch nith the Facebook friends she has made during trips to France, India and Singapore. ", "en", "ko");
		System.out.println(trans);
	}
}
