<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bookings</title>
</head>
<body>
<jsp:include page="fragments/Topnav.jsp" />
<div align="center">
<h1>Your Bookings</h1>
<table border="1">
<tr><th>Check in</th><th>Check out</th><th>Subtotal</th><th>Discount</th><th>Total</th></tr>
<c:forEach items="${bookings}" var="b">
<tr>
<td>${b.checkin}</td>
<td>${b.checkout}</td>
<td>${b.baseCost}</td>
<td>${b.discount}</td>
<td>${b.total}</td>
</tr>
<tr id="roomList">

</tr>
</c:forEach>
</table>
</div>
</body>
</html>