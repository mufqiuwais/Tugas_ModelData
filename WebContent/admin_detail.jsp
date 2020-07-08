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
			<input type="hidden" name="username" value="${username}">
			<input type="hidden" name="password" value="${password}">
			<input type="submit" name="action" value="Admin Home">
    </form>
	<h2>${article.getTitle()}</h2>
	<p>Id : ${article.getId()}</p>
    <p>Author : ${article.getAuthor()}</p>
    <p>Publication : ${article.getPublication()}</p>
    <p>Date : ${article.getDate()}</p>
  	<p>Url : ${article.getUrl()}</p>
  	<p>Visitors : ${article.getVisitors()}</p>
  	<p>Content : </p>
	<p>${article.getContent()}</p>
</body>
</html>