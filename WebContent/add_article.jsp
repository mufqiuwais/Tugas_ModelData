<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Admin Mode</h1>
	<form action="ActionController" method="post">
		Enter title : <input type="text" name="title"> <BR>
		Enter publication : <input type="text" name="publication"> <BR>
		Enter author : <input type="text" name="author"> <BR>
		Enter url : <input type="text" name="url"> <BR>
		<textarea rows="9" cols="70" name="content">Enter text here...</textarea> <BR>
		<input type="hidden" name="username" value="${username}">
		<input type="hidden" name="password" value="${password}">
		<input type="hidden" name="action" value="Insert">
		<input type="submit" />
	</form>
</body>
</html>