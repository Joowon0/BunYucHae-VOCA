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
	MYSQLdbHelper.createTables();
	accountHelper.insertNewAccount("FirstID", "1234");
	accountHelper.insertNewAccount("SecondID", "1234");
	
	int idN1 = accountHelper.getIDNum("FirstID");
	int idN2 = accountHelper.getIDNum("SecondID");
	
	tagListHelper.insertNew(idN1, "U1T1");
	tagListHelper.insertNew(idN1, "U1T2");
	tagListHelper.insertNew(idN2, "U2T1");
	tagListHelper.insertNew(idN2, "U2T2");
	
	textListHelper.insertNew(idN1, "This is Title", "Texts1", "Texts2");
	textListHelper.insertNew(idN1, "This is other Title", "Texts1", "");
	textListHelper.insertNew(idN2, "User2: This is Title", "Texts1", "Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2Texts2");
	textListHelper.insertNew(idN2, "User2: This is other Title", "Texts1", "");
	
	wordListHelper.insertNew(idN1, "a word 1", "a definition for this word");
	wordListHelper.insertNew(idN1, "a word 2", "another definition for this word");
	wordListHelper.insertNew(idN2, "User2:a word 1", "User2:a definition for this word");
	wordListHelper.insertNew(idN2, "User2:a word 2", "User2:another definition for this word");	
%>
<form name="f1" method="post" action="./contents.jsp">
	ID       : <input type="text" name="id"/>
	Password : <input type="text" name="password"/>
	<input type="submit" value="SUBMIT"/>
</form>

</body>
</html>