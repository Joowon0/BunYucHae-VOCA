package com.bun_yuchae_voca;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bun_yuchae_voca.GTranslator;
import com.bun_yuchae_voca.Language;
import com.bun_yuchae_voca.Scanner;
import com.bun_yuchae_voca.Scanner.Format;

/**
 * Servlet implementation class Translating
 */
@WebServlet("/Translating")
public class Translating extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private GTranslator gTranslator;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Translating() {
        super();
        gTranslator = new GTranslator();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		request.setCharacterEncoding("UTF-8");		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");		
		String data,source,target,result="Hello";
		String body=null;
		try{
				body = getBody(request);
		}catch(Exception e){
			
		}
		if(body.length()<5)
			return;
		JSONParser parser = new JSONParser();
		JSONObject json = new JSONObject();
		try {
			json = (JSONObject) parser.parse(body);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		data = json.get("data").toString();
		source = json.get("source").toString();
		target = json.get("target").toString();
		if(data!=null&&source!=null&&target!=null)
			result = gTranslator.translateTextWithOptions(data,Language.Google.getToken(source),Language.Google.getToken(target));			

		json = new JSONObject();		
		json.put("translated", result);
		if(data==null){
			data="A";
		}
		json.put("text", data);
		System.out.println(json);
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	protected static String getBody(HttpServletRequest request) throws IOException {
		  
		 String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;

		}


}
