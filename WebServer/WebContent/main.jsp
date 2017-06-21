<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%!
    String ocrText=new String(),translated=new String(),url = new String();
	String data=new String(),id=null,href=null;
%>
<%	
    request.setCharacterEncoding("euc-kr");
    ocrText = "";
    translated = "";
    
    url = "https://www.hello.com/img_/hello_logo_hero.png";
    if(request.getAttribute("text")!=null)
    	ocrText = request.getAttribute("text").toString();
    if(request.getAttribute("translated")!=null)
    	translated = request.getAttribute("translated").toString();
    if(request.getAttribute("url")!=null)
    	url= request.getAttribute("url").toString();
    if(request.getAttribute("data")!=null)
    	data = request.getAttribute("data").toString();
    
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
		</ul>
	</div>
	
	<p class="head">������-VOCA</p>
	<br>
	<form method = "post" action ="/WebServer/Main">
		File: <input type="file" id="file-upload" onchange="changeImg()"> <br><br>		
		ImageURL: <input style="width: 50%;" type="text" id= "URL" name = "url" onchange="urlKeyDown()" ><br><br>
		Source: 
		<select name="source">
		    <option value="">��� ����</option>
		    <option value="English">����</option>
		    <option value="Korean">�ѱ���</option>
		    <option value="Japanese">�Ϻ���</option>
		    <option value="Chinese">�߱���</option>
		</select>
		Target: 
		<select name="target">
		    <option value="">��� ����</option>
		    <option value="English">����</option>
		    <option value="Korean">�ѱ���</option>
		    <option value="Japanese">�Ϻ���</option>
		    <option value="Chinese">�߱���</option>
		</select>	
		<br><br>
		<table style="width: 100%">
		<tr>
			<th style="width: 50%; text-align: center">ImagePreview</th>
			<th style="width: 50%; text-align: center">Text Value</th>				
		</tr>
		<tr>
			<td rowspan ="6" style="width: 50%; text-align: center"><img alt="" id="img" name="img" src=<%=data%>></td>		
			<td rowspan = "1"style="width: 50%; text-align: center" >Scanned Text</td>		
		</tr>
		<tr>
			<td rowspan = "2"style="width: 50%; text-align: left" ><%=ocrText %></td>
		</tr>
		<tr></tr>		
		<tr>	
			<td rowspan = "1" style="width: 50%; text-align: center" >Translated Text</td>			
		</tr>
		<tr>
			<td rowspan = "2" style="width: 50%; text-align: left" ><p class="trnaslated"><%=translated %></p></td>
		</tr>
		</table>
		<input type="hidden" id="data" name = "data" value = " ">
		<input type="hidden" name = "translated" value = " ">
		
		<table style="width:100%;">
			<tr><th>
			<input type="submit" Value = "����" style="width: 100%;font-size:20px">
			</th><th>
			<input type="Button" Value = "����" style="width: 100%;font-size:20px;">
			</th></tr>
		</table>
	</form>
	<script>
	function urlKeyDown() {	
	    document.getElementById("img").src = document.getElementById("URL").value;
	    document.getElementById("data").value=document.getElementById("URL").value;
	}
	
	function changeImg(){	
		var input = document.getElementById("file-upload");
		var fReader = new FileReader();
		fReader.readAsDataURL(input.files[0]);
		fReader.onloadend = function(event){
			var img = document.getElementById("img");			
			img.src = event.target.result;
			document.getElementById("data").value=img.src;
		}
	}	
	</script>
</body>
</html>