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
        </form>
        <table border="1" cellpadding="5">
            <caption><h2>List of Articles</h2></caption>
            <tr>
                <th>id</th>
                <th>title</th>
                <th>publication</th>
                <th>author</th>
                <th>date</th>
                <th>url</th>
                <th>content</th>
            </tr>
            <c:forEach items="${dataList}" var="dataItem">
            	<form action="ActionController" method="post">
			        <tr>
			            <td>${dataItem.getId()}</td>
			            <td>${dataItem.getTitle()}</td>
			            <td>${dataItem.getPublication()}</td>
			            <td>${dataItem.getAuthor()}</td>
			            <td>${dataItem.getDate()}</td>
			            <td>${dataItem.getUrl()}</td>
			            <td>${dataItem.getContent()}</td>
			        </tr>
			        <input type="hidden" name="id" value="${dataItem.getId()}">
			        <input type="hidden" name="title" value="${dataItem.getTitle()}">
			        <input type="hidden" name="publication" value="${dataItem.getPublication()}">
			        <input type="hidden" name="author" value="${dataItem.getAuthor()}">
			        <input type="hidden" name="date" value="${dataItem.getDate()}">
			        <input type="hidden" name="url" value="${dataItem.getUrl()}">
			        <input type="hidden" name="content" value="${dataItem.getContent()}">
			     </form>
		    </c:forEach>
    </div>
</body>
</html>