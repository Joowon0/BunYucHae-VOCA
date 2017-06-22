package bun_yuchae_voca;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Dictionary  {
	final static String EK = "http://endic.naver.com/search.nhn?sLn=kr&searchOption=entry_idiom&query=";
	
	public static ArrayList<Word> getData(String word,String dst,String src){
		ArrayList<Word>words=new ArrayList<Word>();
		words = getFirstPageWord(word);
		for(int i=0;i<words.size();++i){
			Word data = words.get(i);			
		}
		return words;
	}
	public static ArrayList<Word> parseWords(String href){
		ArrayList<Word>words=new ArrayList<Word>();
		Word data = null;
		
		String url = "http://endic.naver.com"+href;		
		try {			
			Document doc = Jsoup.connect(url).get();			
			Elements ele = doc.select("div.tit h3");
			String value = ele.get(0).text();
			ele = doc.select("dl.list_a3 span");
			for(int i=0;i<ele.size();++i){
				String className =ele.get(i).className(); 				
				if(className.compareTo("itt_button")!=0&&className.compareTo("blind")!=0){
					if(className.compareTo("fnt_e11")==0){		
						if(data != null){
							words.add(data);
						}
						data = new Word();
						data.value = value;
						data.mean+=ele.get(i).text();						
					}
					else if(className.compareTo("fnt_e07")==0){
						data.examples.add(ele.get(i).text()+"\n"+ele.get(++i).text());
					}
					else{						
						data.mean+=ele.get(i).text();
					}								
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return words;
	}
	static public ArrayList<Word> getFirstPageWord(String word){
		ArrayList<Word> words = new ArrayList<Word>();
		try {
			Document doc = Jsoup.connect(EK+word+"&theme=&pageNo=1").get();
			Elements ele = doc.select("div.word_num_nobor span");
			String prev = "a";
			Word data=null;
			for(int i=0;i<ele.size();++i){				
				String className = ele.get(i).className();
				String text = ele.get(i).text();
				if(className.compareTo("fnt_e30")==0){
					if(data!=null){
						words.add(data);
					}
					data = new Word();
					data.href = ele.get(i).select("a").get(0).attr("href")+ele.get(i).getElementsByAttribute("a").attr("href");
					data.value=ele.get(i).select("a").get(0).text();													
				}
				else if(className.compareTo("fnt_e25")==0){
					if(ele.get(i).text()!=null)
						data.pronunciationSymbol=ele.get(i).text();
				}
				else if(className.compareTo("fnt_k09")==0){
					if(ele.get(i).text()!=null){						
						data.mean+=ele.get(i).text();						
					}
				}
				else if(className.compareTo("fnt_k05")==0){
					if(ele.get(i).text()!=null){
						data.mean +=ele.get(i).text();
					}
				}
				prev = className;				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return words;
	}
	static public void main(String[] args){
		String Word="mean";
		java.util.Scanner sc = new java.util.Scanner(System.in);
		while(sc.hasNext()){
			Word = sc.next();
			ArrayList<Word> words=Dictionary.getData(Word,null,null);		
			System.out.println("\n\n");	
			
			for(int i=0;i<words.size();++i){
				Word data = words.get(i);
				System.out.println(data.value+data.pronunciationSymbol);
				System.out.println(data.mean);
			}		
		}
	}
	static public void secondDictPage(String entryID){		
		String url = "http://endic.naver.com"+entryID;		
		try {
			Document doc = Jsoup.connect(url).get();			
			Elements ele = doc.select("div.tit h3");
			System.out.println(ele.get(0).text());
			ele = doc.select("dl.list_a3 span");			
			boolean first=true;
			for(int i=0;i<ele.size();++i){
				String className =ele.get(i).className(); 				
				if(className.compareTo("itt_button")!=0&&className.compareTo("blind")!=0){
					if(className.compareTo("fnt_e11")==0){						
						System.out.print("\n"+ele.get(i).text());
					}
					else if(className.compareTo("fnt_e07")==0){
						if(first){
							System.out.println("");
							first = false;
						}
						System.out.println(ele.get(i).text());
						System.out.println(ele.get(++i).text());												
					}				
					else{
						System.out.print(ele.get(i).text());
						first=true;
					}								
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	static public void printDictMainPage(String word){
		try {
			Document doc = Jsoup.connect("http://endic.naver.com/search.nhn?sLn=kr&searchOption=entry_idiom&query="+word+"&theme=&pageNo=1").get();
			Elements ele = doc.select("div.word_num_nobor span");
			String prev = "a";
			for(int i=0;i<ele.size();++i){				
				String className = ele.get(i).className();
				String text = ele.get(i).text();
				if(className.compareTo("fnt_e30")==0){					
					System.out.print("\n"+ele.get(i).select("a").get(0).attr("href"));
					System.out.println(ele.get(i).getElementsByAttribute("a").attr("href"));					
					System.out.print(ele.get(i).select("a").get(0).text());									
				}
				else if(className.compareTo("fnt_e25")==0)
					System.out.println(ele.get(i).text());
				else if(className.compareTo("fnt_k09")==0)
					System.out.print(ele.get(i).text());
				else if(className.compareTo("fnt_k05")==0){
					if(prev.compareTo("fnt_e30")==0||prev.compareTo("pad_left fnt_k11")==0)
						System.out.println("");
					System.out.println(" "+ele.get(i).text());
				}
				prev = className;				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}