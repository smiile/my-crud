<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Points</title>
</head>
<body>
	<p>Redefine the point: </p>
	<c:if test="${not empty error}">
		<span style="color: red">${error}</span>
	</c:if>
	<form method="POST" action="edit?id=${id}">
		x: <input type="text" name="x" value="${point.x}"/> <br/>
		y: <input type="text" name="y" value="${point.y}"/>
	 	<input type="submit" value="Update" />
	</form>
</body>
</html>