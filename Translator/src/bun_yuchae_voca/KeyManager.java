package bun_yuchae_voca;

import java.util.Vector;

public class KeyManager {
	private static KeyManager ins=null;
	public static enum CompanyType{MS_BING,OCR,JAVONET};
	private static Vector<Key> keys=new Vector<Key>();
	private KeyManager(){
		addKey(new MSKey("01994f84e3d64105afdb2662bc3e837e",CompanyType.MS_BING));
		addKey(new JAVONETKey("p7F4-Wd2m-m8EZ-Hz62-d2SX",CompanyType.JAVONET));
		addKey(new OCRKey("2e57ea933f88957",CompanyType.OCR));
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