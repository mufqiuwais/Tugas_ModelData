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
		Enter id : <input type="text" name="id"> <BR>
		Enter title : <input type="text" name="title"> <BR>
		Enter publication : <input type="text" name="publication"> <BR>
		Enter author : <input type="text" name="author"> <BR>
		Enter date (mm/dd/yyyy) : <input type="text" name="date"> <BR>
		Enter url : <input type="text" name="url"> <BR>
		Enter content : <input type="text" name="content"> <BR>
		<input type="hidden" name="action" value="Insert">
		<input type="submit" />
	</form>
</body>
</html>