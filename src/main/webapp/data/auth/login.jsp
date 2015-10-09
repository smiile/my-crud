<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login</title>
</head>
<body>
	<h3>Login</h3>
	<c:if test="${not empty error}">
		<p style="color: red">${error}</p>
	</c:if>
	<div style="width: 250px; padding: 20px;">
		<form method="POST" action="login">
			<div class="form-group">
				<label for="emailInput">E-mail</label>
				<input type="text" id="emailInput" class="form-control" name="email" />
			</div>
			<div class="form-group">
				<label for="passwordInput">Password</label>
				<input type="password" id="passwordInput" class="form-control" name="password" />
			</div>
			<input type="submit" class="btn btn-default" value="Submit" />
		</form>
	</div>
</body>
</html>