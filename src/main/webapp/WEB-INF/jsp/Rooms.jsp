<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Rooms</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="${pageContext.request.contextPath}/js/Room.js"></script>
<script src="${pageContext.request.contextPath}/js/RoomFilter.js"></script>
<script>
var context = "${pageContext.request.contextPath}",
hotelId = "${hotelId}",
username = "${username}";
</script>
</head>
<body>
<jsp:include page="fragments/Topnav.jsp" />
<div align="center">
<h1><strong>Room Selection</strong></h1>
<div class="container border rounded" style="margin:auto;padding:50px;margin-top:50px;margin-bottom:50px">
	<h3>Narrow your search results</h3>
	<div class="form-row">
	<div class="col-3">
		Room type <input class="form-control" type="text" id="searchType" name="searchType"/>
	</div>
	<div class="col-2">
		No. Guests: <input class="form-control" type="number" id="noGuests" name="noGuests"/>
	</div>
	<div class="col">
	Check-In Date: <input type="date" id="checkInDate" name="checkInDate"/>
	</div>
	<div class="col">
	Check-Out Date: <input type="date" id="checkOutDate" name="checkOutDate"/>
	</div>
	<input class="btn-sm btn-primary" type="button" id="searchBtn" value="SEARCH"/>
	</div>
</div>

<div class="row" align="right">
<div class="col-7">Cart
<ul id="cart">
</ul>
</div>
<div id="cartCount" class="col-0">0
</div>
</div>

<div class="row">
<div class="col-2 border rounded" style="margin-left:50px;padding:25px">
Range:
<div class="slidecontainer">
	<input type="range" min="1" max="2000" value="500" class="slider" id="priceRange">
	<p>Price: $<span id="priceValue"></span></p>
</div>
<div>
	<div id="amenities" class="form-check">
	</div>
	<input style="margin-top:25px" class="btn btn-primary" type="button" id="filterBtn" value="FILTER"/>	
</div>
</div>
<div class="col-7" style="margin-left:50px;">
<table id="roomTable" border="1" style="padding:5px">
<tr><th>Type</th><th>Sqft</th><th>Price</th><th>Amenities</th><th>Max Occupancy</th></tr>
</table>
</div>
</div>
<button class="btn-primary" id="book">Book Rooms</button>
</div>
<script>
var slider = document.getElementById("priceRange");
var output = document.getElementById("priceValue");
output.innerHTML = slider.value;
slider.oninput = function() {
	output.innerHTML = this.value;
}
</script>
</body>
</html>