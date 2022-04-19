<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
<nav>
<div align="left">
<button onclick="window.location.href='${pageContext.request.contextPath}/home'">Home</button>
<c:if test="${not empty pageContext.request.userPrincipal.name}">
<button onclick="window.location.href='${pageContext.request.contextPath}/bookingHistory'">Bookings</button>
</c:if>
</div>
<div align="right">
<c:if test="${not empty pageContext.request.userPrincipal.name}">
<strong>Logged in as ${pageContext.request.userPrincipal.name}</strong>
<a href="${pageContext.request.contextPath}/logout">Logout</a>
</c:if>
<c:if test="${empty pageContext.request.userPrincipal.name}">
<strong>Not logged in. </strong>
<a href="${pageContext.request.contextPath}/login">Login</a>
</c:if>
</div>
</nav>
</div>