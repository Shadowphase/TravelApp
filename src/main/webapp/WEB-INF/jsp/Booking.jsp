<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Booking</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/Booking.js"></script>
<script>
let context = "${pageContext.request.contextPath}",
rooms="${booking.rooms}",
bookingId="${booking.id}",
username="${username}";
</script>
</head>
<style>
.table {
  display: table;
}
.table-row {
  display: table-row;
}
.group {
  display: table-row-group;
}
.table-cell {
  display: table-cell;
  padding: 10px;
  border: 1px solid black;
}
.table-row:nth-child(even) {
  background: lightblue;
}
.caption {
  caption-side: bottom;
  display: table-caption;
  text-align: left;
}
</style>
<body>
<jsp:include page="fragments/Topnav.jsp" />
<div align="center">
<h1>Booking</h1>
<table>
<tr>
<td>Check in <input type="date" id="checkin" value="${booking.checkin}"/></td>
</tr>
<tr>
<td>Check out <input type="date" id="checkout" value="${booking.checkout}"/></td>
</tr>
<tr>
<td colspan="2"><table id="roomList" border="1">
<tr align="left"><th>Type</th><th>Price</th><th>Area</th><th>Amenities</th></tr>
</table></td>
</tr>
<tr>
<td>Subtotal:</td><td id="baseCost">${booking.baseCost}</td>
</tr>
<tr>
<td>Discount:</td><td id="discount">${booking.discount}</td>
</tr>
<tr>
<td>Total:</td><td id="total">${booking.total}</td>
</tr>
<tr>
<td colspan="2"><input type="button" id="submit" value="Book Rooms"></td>
</tr>
</table>
</div>
</body>
</html>