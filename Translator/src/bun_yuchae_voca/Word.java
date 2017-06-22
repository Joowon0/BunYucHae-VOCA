package bun_yuchae_voca;

import java.util.ArrayList;

public class Word{
	public String value;
	public String mean;
	public String href;
	public String pronunciationSymbol;
	public ArrayList<String> examples; 
	public Word(){
		examples=new ArrayList<String>();
		value=mean=href=pronunciationSymbol="";
	}
}