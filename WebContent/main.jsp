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
		<caption><h2>Article Database</h2></caption>
		<form action="ActionController" method="post">
			<input type="submit" name="action" value="Search Article">
			<input type="submit" name="action" value="Login Admin">
        </form>
    </div>
    <div align="left">
    	<caption><h2>Top Articles</h2></caption>
    	<form action="ActionController" method="post">
			<input type="submit" name="action" value="Top Articles">
        </form>
	    	<c:forEach items="${dataList}" var="dataItem">
	    	<form action="ActionController" method="post">
	    		<h2>${dataItem.getTitle()}</h2>
	    		<p>${dataItem.getContent()}</p>
		        <input type="submit" name="action" value="Details">
	    		<input type="hidden" name="id" value="${dataItem.getId()}">
		        <input type="hidden" name="title" value="${dataItem.getTitle()}">
		        <input type="hidden" name="publication" value="${dataItem.getPublication()}">
		        <input type="hidden" name="author" value="${dataItem.getAuthor()}">
		        <input type="hidden" name="date" value="${dataItem.getDate()}">
		        <input type="hidden" name="url" value="${dataItem.getUrl()}">
		        <input type="hidden" name="content" value="${dataItem.getContent()}">
		        <input type="hidden" name="visitors" value="${dataItem.getVisitors()}">
		    </form>
	    	</c:forEach>
    </div>
</body>
</html>