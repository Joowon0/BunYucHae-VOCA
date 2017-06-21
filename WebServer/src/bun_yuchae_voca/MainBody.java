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
@WebServlet("/Main")
public class MainBody extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	GTranslator gTranslator ;
    /**
     * Default constructor. 
     */
    public MainBody() {
    	gTranslator= new GTranslator();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");		
		response.setCharacterEncoding("euc-kr");		
		Scanner sc = new Scanner();
		String text="", result="",url="";
		String te = request.getParameter("data");
		String srcP = request.getParameter("source");		
		String tgP = request.getParameter("target");
		if(te != null && srcP!=null && tgP!=null) {			
			if(te.substring(0,te.indexOf(':')).compareTo("data")!=0){
				text = sc.act(false,te,Language.OCR.getToken(srcP),Format.URL);				
			}
			else{				
				text = sc.act(false,te,Language.OCR.getToken(srcP),Format.Base64);
			}
			url = te;			
			
			result = gTranslator.translateTextWithOptions(text,Language.Google.getToken(srcP),Language.Google.getToken(tgP));			
		}
		request.setAttribute("text", text);		
		request.setAttribute("translated", result);
		request.setAttribute("data", te);		
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
