<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
		<li><a id="login" href="" value="�α���"></a></li>
		<li><a href="${pageContext.request.contextPath}/MainBody">������</a></li>		
		</ul>
	</div>
	
	<p class="head">������-VOCA</p>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
	<script type="text/javascript">
        $(document).ready(function () {
            var val = location.href.substr(location.href.lastIndexOf('=') + 1);
            document.getElementById("login").innerHTML=val;
            console.log('val : ' + location.href);
        });
    </script>
</body>
</html>