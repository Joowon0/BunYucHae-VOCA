<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="bunyack.mysql.java.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String idIn = request.getParameter("id");
	String pwdIn = request.getParameter("password");
	//if (accountHelper.checkPwd( idIn, pwdIn)) {
	if(true) {
		int idNum = accountHelper.getIDNum(idIn);
		%>
		<table width="100%" border="0">
			<tr valign="top">
				<td bgcolor="#b5dcb3" width="40%">
					<h3>THIS IS FOR TEXT LIST</h3>
					<br> <%= textListHelper.getTextsSortedDate(idNum) %>
				</td>
				<td bgcolor="#aaa" width="35%">
					<h3>THIS IS FOR WORD LIST</h3>
					<br> <%= wordListHelper.getWordsSortedDate(idNum) %>
				</td>
				<td bgcolor="#b5dcb3" width="25%">
					<h3>THIS IS FOR TAG LIST</h3>
					<br> <%= tagListHelper.getTagsSortedByDate(idNum) %>
				</td>
			</tr>
		</table>
		<%
	}
	else {
		%>
		<h1>UNABLE TO FIND MATCHING ACCOUNT!!</h1>
		<%
	}
%>
</body>
</html>