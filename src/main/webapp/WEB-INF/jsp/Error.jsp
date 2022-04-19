<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error</title>
</head>
<body>
<jsp:include page="fragments/Topnav.jsp" />
<jsp:include page="fragments/HeaderImage.jsp" />
<div align="center">
<p style="font:25px'"><Strong>Oops. This Page does not exist.</Strong></p>
<c:if test="${not empty returnUrl}">
<p style="font:25px'"><Strong>Please <a href="${returnUrl}">return.</a></Strong></p>
</c:if>
</div>
</body>
</html>