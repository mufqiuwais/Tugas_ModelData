<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="ActionController" method="post">
		Username : <input type="text" name="username"> <BR>
		Password : <input type="text" name="password"> <BR>
		<input type="hidden" name="action" value="Authentication">
		<input type="submit" />
	</form>
</body>
</html>