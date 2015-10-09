<%@ page language="java" contentType="text/html; charset=US-ASCII"
         pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <body>
        <c:choose>
            <c:when test="${sessionScope.authenticated}">
                <h2>Welcome, ${sessionScope.username}</h2>
            </c:when>
            <c:otherwise>
                <h2>Welcome!</h2>
            </c:otherwise>
        </c:choose>

        <h4>The following services are provided for authenticated users: </h4>
        <ul>
            <li><a href="point/list">Point CRUD</a></li>
            <li><a href="user/list">User management</a></li>
        </ul>
    </body>
</html>
