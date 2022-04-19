<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="frm" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Feedback</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/Review.js"></script>
<script>
var context = "${pageContext.request.contextPath}",
reviewType="${reviewType}",
entityId="${entityId}",
username="${username}";
</script>
</head>
<body>
<jsp:include page="fragments/Topnav.jsp" />
<div align="center">
<h1>Review</h1>
<div>Rating:
<input type="radio" name="rating" id="s1" value="1"/><label for="s1">1</label>
<input type="radio" name="rating" id="s2" value="2"/><label for="s2">2</label>
<input type="radio" name="rating" id="s3" value="3"/><label for="s3">3</label>
<input type="radio" name="rating" id="s4" value="4"/><label for="s4">4</label>
<input type="radio" name="rating" id="s5" value="5"/><label for="s5">5</label>
</div>
<div>
Additional comments<br>
<textarea id="comments" name="comments" rows="4" cols="50" placeholder="(Optional)"></textarea>
</div>
<button name="submit" id="submit">Submit</button>
</div>
</body>
</html>