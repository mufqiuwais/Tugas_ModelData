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
		<form action="ActionController" method="post">
			<input type="hidden" name="username" value="${username}">
			<input type="hidden" name="password" value="${password}">
			<input type="submit" name="action" value="Admin Home">
			<input type="submit" name="action" value="Add Article"><BR>
			<input type="text" name="keySearch">
			<input type="submit" name="action" value="Search">
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
                <th>details</th>
                <th>delete</th>
                <th>update</th>
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
			            <td><input type="submit" name="action" value="Admin Details"></td>
			            <td><input type="submit" name="action" value="to_update"></td>
			            <td><input type="submit" name="action" value="delete"></td>
			        </tr>
			        <input type="hidden" name="id" value="${dataItem.getId()}">
			        <input type="hidden" name="id" value="${dataItem.getId()}">
			        <input type="hidden" name="title" value="${dataItem.getTitle()}">
			        <input type="hidden" name="publication" value="${dataItem.getPublication()}">
			        <input type="hidden" name="author" value="${dataItem.getAuthor()}">
			        <input type="hidden" name="date" value="${dataItem.getDate()}">
			        <input type="hidden" name="url" value="${dataItem.getUrl()}">
			        <input type="hidden" name="visitors" value="${dataItem.getVisitors()}">
			        <input type="hidden" name="content" value="${dataItem.getContent()}">
					<input type="hidden" name="username" value="${username}">
					<input type="hidden" name="password" value="${password}">
			     </form>
		    </c:forEach>
		</table>
		<form action="ActionController" method="post">
			<input type="hidden" name="username" value="${username}">
			<input type="hidden" name="password" value="${password}">
			<p>Page : ${page}</p>
			<p>Count in page : ${count}</p>
			<p>Total : ${totalCount}</p>
			<c:choose>
			    <c:when test="${page==1&&totalCount<=20}">
					<input type="hidden" name="page" value="${page}">
					<input type="hidden" name="count" value="${count}">
					<input type="hidden" name="keySearch" value="${keySearch}">
			        <br />
			    </c:when>    
			    <c:when test="${page==1 && totalCount>20}">
					<input type="hidden" name="page" value="${page}">
					<input type="hidden" name="count" value="${count}">
					<input type="hidden" name="keySearch" value="${keySearch}">
					<input type="submit" name="action" value="Next_ST">
			        <br />
			    </c:when> 
			    <c:when test="${page>1 && count<20}">
					<input type="hidden" name="page" value="${page}">
					<input type="hidden" name="count" value="${count}">
					<input type="hidden" name="keySearch" value="${keySearch}">
					<input type="submit" name="action" value="Back_ST">
			        <br />
			    </c:when>
			    <c:otherwise>
			        <input type="hidden" name="page" value="${page}">
					<input type="hidden" name="count" value="${count}">
					<input type="hidden" name="keySearch" value="${keySearch}">
					<input type="submit" name="action" value="Back_ST">
					<input type="submit" name="action" value="Next_ST">
			        <br />
			    </c:otherwise>
			</c:choose>
        </form>
	</div>
    
</body>
</html>