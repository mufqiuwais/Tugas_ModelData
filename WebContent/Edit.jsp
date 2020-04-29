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
		update id : <input type="text" name="id" value="${article.id}"> <BR>
		update title : <input type="text" name="title" value="${article.title}"> <BR>
		update publication : <input type="text" name="publication" value="${article.publication}"> <BR>
		update author : <input type="text" name="author" value="${article.author}"> <BR>
		update date (mm/dd/yyyy) : <input type="text" name="date" value="${article.date}"> <BR>
		update url : <input type="text" name="url" value="${article.url}"> <BR>
		update content : <input type="text" name="content" value="${article.content}"> <BR>
		<input type="hidden" name="row" value="${article.kode}">
		<input type="hidden" name="action" value="update">
		<input type="submit" />
	</form>
</body>
</html>