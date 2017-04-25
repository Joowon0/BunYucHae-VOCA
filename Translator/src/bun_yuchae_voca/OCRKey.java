package bun_yuchae_voca;

import bun_yuchae_voca.KeyManager.CompanyType;

public class OCRKey extends Key {

	public OCRKey(String k, CompanyType t) {
		super(k, t);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public synchronized String  request() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	protected boolean checkSecretKey() {
		// TODO Auto-generated method stub
		return true;
	}

}
