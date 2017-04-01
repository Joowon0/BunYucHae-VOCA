package experiment;

import com.javonet.Javonet;
import com.javonet.JavonetException;
import com.javonet.JavonetFramework;
import com.javonet.api.NObject;

public class test {
	public static void main(String[] args) throws JavonetException {

		Javonet.activate("rnwhdghl12@naver.com", "p7F4-Wd2m-m8EZ-Hz62-d2SX",JavonetFramework.v45);
		
		Javonet.addReference("dll/JNICSharp.dll");
		
		//Calling instance method
		NObject class1 = Javonet.New("Translator");		
		String bear="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzY29wZSI6Imh0dHBzOi8vYXBpLm1pY3Jvc29mdHRyYW5zbGF0b3IuY29tLyIsInN1YnNjcmlwdGlvbi1pZCI6IjkwODA1OTIyMTA1MzRjMmJiNWIyMDhkYzNkNDMwNzZhIiwicHJvZHVjdC1pZCI6IlRleHRUcmFuc2xhdG9yLkYwIiwiY29nbml0aXZlLXNlcnZpY2VzLWVuZHBvaW50IjoiaHR0cHM6Ly9hcGkuY29nbml0aXZlLm1pY3Jvc29mdC5jb20vaW50ZXJuYWwvdjEuMC8iLCJhenVyZS1yZXNvdXJjZS1pZCI6Ii9zdWJzY3JpcHRpb25zL2Q1ZGM2Yjg0LWVjNTctNDRmMC05ODMxLWIwNjU5ZTRmNDg3NC9yZXNvdXJjZUdyb3Vwcy9qb293b24vcHJvdmlkZXJzL01pY3Jvc29mdC5Db2duaXRpdmVTZXJ2aWNlcy9hY2NvdW50cy9UZXN0X1RyYW5zbGF0b3IiLCJpc3MiOiJ1cm46bXMuY29nbml0aXZlc2VydmljZXMiLCJhdWQiOiJ1cm46bXMubWljcm9zb2Z0dHJhbnNsYXRvciIsImV4cCI6MTQ5MDk2MTQwOX0.dVS_hdx3yYJ_j_ml1tgiHcRjFRdrPhODWOTL0oy-6Y4";
		String text="We propose a detector using a two-stage hypothesize and classify framework. First, hand hypotheses are proposed from three independent methods: a sliding window hand-shape detector, a sliding window context-based detector, and a skin-based detector. The sliding window detectors employ part based deformable model with three components. Then, the proposals are scored by all three methods and a discriminatively trained model is used to verify them. The three proposal mechanisms ensure good recall, and the discriminative classification ensures good precision.";
		String from="en";
		String to="ko";
		String result = class1.invoke("TranslateMethod",bear,text,from,to);

		System.out.println(result);

		//Calling static method
	
	}
}
