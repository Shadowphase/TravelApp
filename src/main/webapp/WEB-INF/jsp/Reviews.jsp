<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reviews</title>
<script>
let context = "${pageContext.request.contextPath}";
</script>
</head>
<body>
<jsp:include page="fragments/Topnav.jsp" />
<div align="center">
<table border="1">
<tr><th>Rating</th><th>Comments</th></tr>
<c:forEach items="${reviews}" var="r">
<tr><td>${r.rating}</td><td>${r.comments}</td></tr>
</c:forEach>
</table>
<button onclick="window.location.href='${pageContext.request.contextPath}/feedback/${reviewType}/${entityId}'">Write Review</button>
</div>
</body>
</html>