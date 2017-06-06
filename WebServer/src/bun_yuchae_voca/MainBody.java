package bun_yuchae_voca;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bun_yuchae_voca.Scanner;
import bun_yuchae_voca.Scanner.Format; 
/**
 * Servlet implementation class MainBody
 */
@WebServlet("/MainBody")
public class MainBody extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MainBody() {
    	
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");		
		//response.setContentType("application/json");
		
		response.setCharacterEncoding("euc-kr");

		
		Scanner sc = new Scanner();
		String text="", result="",url="";
		String te = request.getParameter("url");
		if(te != null) {
			url = te;
			text = sc.act(false,te,"eng",Format.URL);
			result = MSTranslator.act(text,MSLanguage.ENGLISH,MSLanguage.KOREAN);			
		}
		request.setAttribute("text", text);		
		request.setAttribute("translated", result);
		request.setAttribute("url", url);		
		RequestDispatcher rd = request.getRequestDispatcher("/main.jsp");
		rd.forward(request,response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
