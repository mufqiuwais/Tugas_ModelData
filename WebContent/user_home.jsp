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
		<caption><h1>Article Database</h1></caption>
		<form action="ActionController" method="post">
			<input type="submit" name="action" value="List of All Articles">
			<input type="submit" name="action" value="Login Admin">
        </form>
    </div>
    <div align="center">
    	<table>
    		<tr>
                <th><h1>Top Articles</h1></th>
                <th><h1>Latest Articles</h1></th>
            </tr>
            <tr>
            	<td>
            		<c:forEach items="${topArticles}" var="dataItem">
			    	<form action="ActionController" method="post">
		   				<h2>${dataItem.getTitle()}</h2>
			    		<p>Author : ${dataItem.getAuthor()}</p>
					    <p>Date : ${dataItem.getDate()}</p>
				        <input type="submit" name="action" value="Details">
			    		<input type="hidden" name="id" value="${dataItem.getId()}">
				    </form>
			    	</c:forEach>
            	</td>
            	<td>
            		<c:forEach items="${latestArticles}" var="dataItem">
			    	<form action="ActionController" method="post">
		   				<h2>${dataItem.getTitle()}</h2>
			    		<p>Author : ${dataItem.getAuthor()}</p>
					    <p>Date : ${dataItem.getDate()}</p>
				        <input type="submit" name="action" value="Details">
			    		<input type="hidden" name="id" value="${dataItem.getId()}">
				    </form>
			    	</c:forEach>
            	</td>
            </tr>
    	</table>
    </div>
</body>
</html>