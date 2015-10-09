<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Points</title>
</head>
<body>
	<p>Define a point in a plane: </p>
	<form method="POST" action="list" style="margin-bottom: 25px;">
		x: <input type="text" name="x" /> <br/>
		y: <input type="text" name="y" />
	 	<input type="submit" value="Add" />
	</form>
	<hr>
	
	<c:choose>
		<c:when test="${not empty points}">
			<p>Existing points: </p>
			<ul>
				<c:forEach var="point" items="${points}">
			  		<c:url var="urlEdit" value="/point/edit">
			  			<c:param name="id" value="${point.id}"></c:param>
			  		</c:url>
			  		<c:url var="urlDelete" value="/point/edit">
			  			<c:param name="id" value="${point.id}"></c:param>
			  			<c:param name="delete" value="1"></c:param>
			  		</c:url>
			  		
			  		<li>${point} 
					  	<a href="${urlEdit}">[Update]</a>
					  	<a href="${urlDelete}">[x]</a>
			  		</li>
				</c:forEach>
			</ul>
		</c:when>
		<c:otherwise>
			<p>No points are defined yet.</p>
		</c:otherwise>
	</c:choose>

</body>
</html>