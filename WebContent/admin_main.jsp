<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Admin Mode</h1>
	<div align="center">
		<caption><h2>Article Database</h2></caption>
		<caption><p>Welcome ${username}</p></caption>
		<form action="ActionController" method="post">
			<input type="submit" name="action" value="Admin Search Article">
			<input type="submit" name="action" value="Add Article">
        </form>
    </div>
</body>
</html>