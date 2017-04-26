<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="bun_yuchae_voca.Scanner" %>
<%@ page import="bun_yuchae_voca.Scanner.Format" %>
<%@ page import="bun_yuchae_voca.Translator" %>

<%
	Translator tr = Translator.getInstance();
	Scanner sc = new Scanner();
	String text="", result="";
	if(tr==null)
		System.out.print("I`m null tr");
	if(sc==null)
		System.out.print("I`m null sc");
	String te = request.getParameter("url");
	System.out.println(te);
	if(te != null) {
		text = sc.act(false,te,"eng",Format.URL);
		result = tr.act(text,"en","ko");
		System.out.println(result);
		request.setAttribute("src",text);
		request.setAttribute("translated",result);
	}
%>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	
	<form method = "post" action ="index.jsp">
		Image: <input type="text" name = "url" ><br>
		preView<img alt="" src="http://dl.a9t9.com/blog/ocr-online/screenshot.jpg"><br><br>
		Text: <%=text %><br><br><br>
		Translated: <%=result %></text><br>
		<input type="submit" Value = "submit">
	</form>
</body>
</html>