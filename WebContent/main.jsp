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
	<div align="center">
		<caption><h1>Welcome to Article Database</h1></caption>
		<h2>Continue as :</h2>
		<form action="ActionController" method="post">
			<input type="submit" name="action" value="Guest User">
			<input type="submit" name="action" value="Admin">
        </form>
    </div>
</body>
</html>