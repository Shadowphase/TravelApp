<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register</title>
</head>
<style type="text/css">
.error{
	color:red;
}
</style>
<body>
<div align="center">
<h1>User Form</h1>
<form:form action="registerUser" method="POST" modelAttribute="customer">
<table>
<tr>
<td>User Name: </td>
<td><form:input path="user.name" value="${customer.user.getName()}" /></td>
<c:if test="${error}">
<td><form:errors path="user.name" cssClass="error" /></td>
</c:if>
</tr>
<tr>
<td>Password: </td>
<td><form:password path="user.password" /></td>
<c:if test="${error}">
<td><form:errors path="user.password" cssClass="error" /></td>
</c:if>
</tr>
<tr>
<td>First Name: </td>
<td><form:input path="firstName" /></td>
<c:if test="${error}">
<td><form:errors path="firstName" cssClass="error" /></td>
</c:if>
</tr>
<tr>
<td>Last Name: </td>
<td><form:input path="lastName" /></td>
<c:if test="${error}">
<td><form:errors path="lastName" cssClass="error" /></td>
</c:if>
</tr>
<tr>
<td>Age: </td>
<td><form:input path="age" /></td>
<c:if test="${error}">
<td><form:errors path="age" cssClass="error" /></td>
</c:if>
</tr>
<tr>
<td>Gender: </td>
<td><form:radiobutton path="gender" value="Male" label="Male"/>
<form:radiobutton path="gender" value="Female" label="Female"/></td>
<c:if test="${error}">
<td><form:errors path="gender" cssClass="error" /></td>
</c:if>
</tr>
<tr>
<td>Email: </td>
<td><form:input path="email" placeholder="email@example.com"/></td>
<c:if test="${error}">
<td><form:errors path="email" cssClass="error" /></td>
</c:if>
</tr>
<tr>
<td>Phone number: </td>
<td><form:input path="phone" placeholder="(XXX) XXX-XXXX"/></td>
<c:if test="${error}">
<td><form:errors path="phone" cssClass="error" /></td>
</c:if>
</tr>
<tr>
<td colspan="3" align="center">
<input type="submit" name="saveUser" value="Register" />
</td>
</tr>
</table>
</form:form>
</div>
</body>
</html>