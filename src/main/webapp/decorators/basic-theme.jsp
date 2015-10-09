<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body style="padding: 20px;">
    <p>
    	<a href="${pageContext.request.contextPath}">
	    	<c:set var="homeUrl" value="${pageContext.request.contextPath}/" />
	    	<c:set var="loginUrl" value="${pageContext.request.contextPath}/login" />
	    	<c:set var="registerUrl" value="${pageContext.request.contextPath}/register" />
	    	<c:set var="pointUrl" value="${pageContext.request.contextPath}/point/list" />
	    	<c:set var="userUrl" value="${pageContext.request.contextPath}/user/list" />
	    	
	    	<c:choose>
	    		<c:when test="${pageContext.request.requestURI == homeUrl}">
	    			[Home]
	    		</c:when>
	    		<c:when test="${pageContext.request.requestURI == loginUrl}">
	    			[Home]
	    		</c:when>
				<c:when test="${pageContext.request.requestURI == registerUrl}">
	    			[Home]
	    		</c:when>
	    		<c:otherwise>Home</c:otherwise>
	    	</c:choose>
    	</a> |
    	<a href="${pageContext.request.contextPath}/point/list">
    		<c:choose>
    			<c:when test="${pageContext.request.requestURI == pointUrl}">
    				[Points CRUD]
    			</c:when>
    			<c:otherwise>Points CRUD</c:otherwise>
    		</c:choose>
    	</a> | 
    	<a href="${pageContext.request.contextPath}/user/list">
    		<c:choose>
    			<c:when test="${pageContext.request.requestURI == userUrl }">
    				[User management]
    			</c:when>
    			<c:otherwise>User management</c:otherwise>
    		</c:choose>
    	</a>
    </p>
    <hr />
    <div style="margin-left: 20px;">
    	<decorator:body />
    </div>
    <hr/>
    <c:choose>
	<c:when test="${!sessionScope.authenticated}">
		<p>	
			<a href="${pageContext.request.contextPath}/login">Login</a> |
		 	<a href="${pageContext.request.contextPath}/register">Register</a>
	 	</p>
	</c:when>
	<c:otherwise>
		<p><a href="${pageContext.request.contextPath}/logout">[logout]</a></p>
	</c:otherwise>
</c:choose>
</body>
</html>