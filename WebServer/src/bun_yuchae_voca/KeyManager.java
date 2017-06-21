package bun_yuchae_voca;

import java.util.Vector;

public class KeyManager {
	private static KeyManager ins=null;
	public static enum CompanyType{MS_BING,OCR,JAVONET, Google};
	private static Vector<Key> keys=new Vector<Key>();
	private KeyManager(){
		addKey(new MSKey("59c5240fbe48408abfdbef9069c4e519",CompanyType.MS_BING));
		addKey(new JAVONETKey("q3QD-k2N8-My3s-Ak72-Jy75",CompanyType.JAVONET));
		addKey(new OCRKey("2e57ea933f88957",CompanyType.OCR));
		addKey(new GKey("AIzaSyCO6b2xn_NzKwVe4jT7Urdtj_oelSMIzRE",CompanyType.Google));
	}
	
	public void addKey(Key key){
		keys.add(key);
	}
	
	public String requestKey(CompanyType type){
		Key key=null;		
		for(int i=0;i<keys.size();++i){		
			if(keys.get(i).getType()==type){				
				key = keys.get(i);
				break;
			}
		}		
		return key.request();		
	}
	
	public static KeyManager getInstance(){		
		if(ins == null)
			ins = new KeyManager();		
		return ins;
	}
}