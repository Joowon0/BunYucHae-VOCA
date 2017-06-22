<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%!
    String ocrText=new String(),translated=new String(),url = new String();
	String data=new String(),id="login",href="login.jsp";
%>
<%	
    request.setCharacterEncoding("euc-kr");
    ocrText = "";
    translated = "";
    
    url = "https://www.hello.com/img_/hello_logo_hero.png";
    System.out.println("Hello !!!!!");
    if(request.getAttribute("logID")!=null){
    	id=request.getAttribute("logID").toString();
    }
    if(request.getAttribute("text")!=null)
    	ocrText = request.getAttribute("text").toString();
    if(request.getAttribute("translated")!=null)
    	translated = request.getAttribute("translated").toString();
    if(request.getAttribute("url")!=null)
    	url= request.getAttribute("url").toString();
    if(request.getAttribute("data")!=null)
    	data = request.getAttribute("data").toString();
    if(request.getAttribute("logID")!=null){
    	id = request.getAttribute("logID").toString();
    	href="LookData.jsp?logID="+id;
    }
%>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<style>

	#nav_menu ul {
	list-style-type:none;
	/* 좌측 여백 없애기 */
	padding-left:0px;
	/* 우측 정렬 하기 */
	float:right;
	}
	#nav_menu ul li {
	display:inline;
	border-left: 1px solid #c0c0c0;
	/* 테두리와 메뉴 간격 벌리기. padding: 위 오른쪽 아래 왼쪽; */
	padding: 0px 10px 0px 10px;
	/* 메뉴와 테두리 사이 간격 벌리기. margin: 위 오른쪽 아래 왼쪽; */
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
		<li><a href=<%=href%>><%=id %></a></li>
		<li><a href="${pageContext.request.contextPath}/MainBody">Translation</a></li>		
		</ul>
	</div>
	
	<p class="head">Bunyuchae-VOCA</p>
	<br>
		<form method = "post" action ="${pageContext.request.contextPath}/MainBody">
		
		File: <input type="file" id="file-upload" onchange="changeImg()"> <br><br>		
		ImageURL: <input style="width: 50%;" type="text" id= "URL" name = "url" onchange="urlKeyDown()" > 		
		Source: 
		<select name="source">
		    <option value="">Language</option>
		    <option value="English">English</option>
		    <option value="Korean">Korean</option>
		    <option value="Japanese">Japanese</option>
		    <option value="Chinese">Chinese</option>
		</select>
		Target: 
		<select name="target">
		    <option value="">Language</option>
		    <option value="English">English</option>
		    <option value="Korean">Korean</option>
		    <option value="Japanese">Japanese</option>
		    <option value="Chinese">Chinese</option>
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
			<input type="submit" Value = "Translating" style="width: 100%;font-size:20px">
			</th><th>
			<input type="Button" Value = "Save  Data" style="width: 100%;font-size:20px;">
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
