package bun_yuchae_voca;

import bun_yuchae_voca.KeyManager.CompanyType;
public abstract class Key {
	protected String value;
	protected CompanyType type;
	protected String secretKey;
	public Key(String k,CompanyType t) {
		value = k;
		type = t;					
	}
	public abstract String request();
	protected abstract boolean checkSecretKey() ;
	public CompanyType getType() {
		return type;
	}
}