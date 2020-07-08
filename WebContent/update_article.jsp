<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update</title>
</head>
<body>
	<form action="ActionController" method="post">
		update title : <input type="text" name="title" value="${article.getTitle()}"> <BR>
		update publication : <input type="text" name="publication" value="${article.getPublication()}"> <BR>
		update author : <input type="text" name="author" value="${article.getAuthor()}"> <BR>
		update url : <input type="text" name="url" value="${article.getUrl()}"> <BR>
		update content :  <BR>
		<textarea rows="9" cols="70" name="content">${article.getContent()}</textarea> <BR>
		<input type="hidden" name="id" value="${article.getId()}">
		<input type="hidden" name="visitors" value="${article.getVisitors()}">
		<input type="hidden" name="action" value="update">
		<input type="submit" />
	</form>
</body>
</html>