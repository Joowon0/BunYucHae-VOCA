package bun_yuchae_voca;

import bun_yuchae_voca.KeyManager.CompanyType;

public class JAVONETKey extends Key{

	public JAVONETKey(String k, CompanyType t) {
		super(k, t);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String request() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	protected boolean checkSecretKey() {
		// TODO Auto-generated method stub
		return true;
	}

}
