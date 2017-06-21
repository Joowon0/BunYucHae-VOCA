package bun_yuchae_voca;


public class Test {
	public static void main(String args[]){
		String  text = "However, now suppose that process M becomes runnable, thereby preempting process L. Indirectly, a process with a lower priority—process M—has affected how long process H must wait for L to relinquish resource R.";
		MSTranslator msTranslator = new MSTranslator();		
		System.out.println("Bing: "+msTranslator.act(text, MSLanguage.ENGLISH, MSLanguage.KOREAN));
		GTranslator gTranslator= new GTranslator();
		try {
			String sourceLang = gTranslator.detectLanguage(text);
			String targetLang = Language.Google.Korean;		    
		    gTranslator.translateTextWithOptions(text, sourceLang, targetLang);
		} catch (ArrayIndexOutOfBoundsException ex) {
			gTranslator.translateText(text, System.out);	
		}		
	}
}
