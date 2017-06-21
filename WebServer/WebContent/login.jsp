<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%!
    String ocrText=new String(),translated=new String(),url = new String();
	String id=new String();
%>
<%	
    request.setCharacterEncoding("euc-kr");
    ocrText = " ";
    translated = " ";
    url = " ";
    if(request.getAttribute("text")!=null)
    	ocrText = request.getAttribute("text").toString();
    if(request.getAttribute("translated")!=null)
    	translated = request.getAttribute("translated").toString();
    if(request.getAttribute("url")!=null)
    	url= request.getAttribute("url").toString();    
%>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<style>

	#nav_menu ul {
	list-style-type:none;
	/* ���� ���� ���ֱ� */
	padding-left:0px;
	/* ���� ���� �ϱ� */
	float:right;
	}
	#nav_menu ul li {
	display:inline;
	border-left: 1px solid #c0c0c0;
	/* �׵θ��� �޴� ���� ������. padding: �� ������ �Ʒ� ����; */
	padding: 0px 10px 0px 10px;
	/* �޴��� �׵θ� ���� ���� ������. margin: �� ������ �Ʒ� ����; */
	margin: 5px 0px 5px 0px;
	}
	#nav_menu ul li:first-child {
	border-left: none;
	}
	p.head {
		border-style: groove;
    	background-color: lightblue;
    	font-size: 50px;
    	text-align: left;
    	color: #ff5722;
	}
	table, th, td {
    	border: 1px solid black;
    	border-collapse: collapse;    	
	}
	th, td {		
    	padding: 5px;
    	text-align: left;
	}
	p.trl{
		font-size: 15px;
	}
	p.ocr{
		font-size: 15px;
	}
	p.login{
		float: right;
		font-size: 10px;
		text-align: left;
	}	
</style>
<title>Bun_yuchae_VOCA</title>
</head>
<body>	
	<div id="nav_menu">
		<ul>
		<li><a href="login.jsp">�α���</a></li>
		<li><a href="main.jsp">������</a></li>
		<li>�� ������</li>
		</ul>
	</div>
	
	<p class="head">������-VOCA</p>
	
	<form method="post" action="main.jsp" style="text-align: left;">
		���̵�:  <input type="text" name="id"><br>
		��й�ȣ: <input type="password" name="password"><br>
		<input type="submit" value="�α���">
	</form>	
</body>
</html>