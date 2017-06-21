package bun_yuchae_voca;

public class Language {
	public static class Google{
		//https://cloud.google.com/translate/docs/languages
		 public static String  Chinese_simplified = "zh-CN";
		 public static String Chinese_Traditional = "zh-TW";
		 public static String English = "en";
		 public static String French = "fr";
		 public static String German = "de";
		 public static String Japanese = "ja";
		 public static String Korean = "ko";
         public static String getToken(String srcP) {
			// TODO Auto-generated method stub
			if(srcP.compareTo("English")==0)
				return English;
			if(srcP.compareTo("Korean")==0)
				return Korean;
			if(srcP.compareTo("Japanese")==0)
				return Japanese;
			if(srcP.compareTo("Chinese")==0)
				return Chinese_Traditional;					
			return null;
		}

	}
	public static class OCR{
		 public static String  Chinese_simplified = "chs";
		 public static String Chinese_Traditional = "cht";
		 public static String English = "eng";
		 public static String French = "fre";
		 public static String German = "ger";
		 public static String Japanese = "jpn";
		 public static String Korean = "ko";
		public static String getToken(String srcP) {
			// TODO Auto-generated method stub
			if(srcP.compareTo("English")==0)
				return English;
			if(srcP.compareTo("Korean")==0)
				return Korean;
			if(srcP.compareTo("Japanese")==0)
				return Japanese;
			if(srcP.compareTo("Chinese")==0)
				return Chinese_Traditional;					
			return null;
		}
	}
}
