<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<title>Login</title>
</head>
<body>
<jsp:include page="fragments/HeaderImage.jsp" />
<div align="center">
<p style="color:red">${message}</p>
<form action="login" method="POST">
<table>
<tr>
<td>Username</td><td><input type="text" name="username" required="required"></td>
</tr>
<tr>
<td>Password</td><td><input type="password" name="password" required="required"></td>
</tr>

<tr>
<td colspan="2" align="center">
<input type="submit" name="submit" value="LogIn">&nbsp&nbsp
<button onclick="window.location.href='${pageContext.request.contextPath}/register'">Register</button>
</td>
</tr>
</table>
</form>
</div>


</body>
</html>