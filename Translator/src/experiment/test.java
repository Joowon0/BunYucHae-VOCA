package experiment;

import java.util.Scanner;

import com.javonet.Javonet;
import com.javonet.JavonetException;
import com.javonet.JavonetFramework;
import com.javonet.api.NObject;

public class test {
	public static void main(String[] args) throws JavonetException {

		String from="en";
		String to="ko";
		String result ;
		String text="We propose a detector using a two-stage hypothesize and classify framework. First, hand hypotheses are proposed from three independent methods: a sliding window hand-shape detector, a sliding window context-based detector, and a skin-based detector. The sliding window detectors employ part based deformable model with three components. Then, the proposals are scored by all three methods and a discriminatively trained model is used to verify them. The three proposal mechanisms ensure good recall, and the discriminative classification ensures good precision.";		
		Translator translator= new Translator();		
		
		Scanner sc = new Scanner(System.in);
		boolean stop = false;
		while(!stop){
			String cmd =sc.nextLine();
			if(cmd.compareTo("g")==0){
				result = translator.act(text,from,to);
				System.out.println(result);
			}
			else if(cmd.compareTo("e")==0)
				stop=true;				
		}		
	}
}
