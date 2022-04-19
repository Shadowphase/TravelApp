var submitted = false;

$(document).ready(function(){
	$("#submit").click(function(){
		let booking = {
			"id":bookingId.replace(/['"]+/g, ''),
			"total":$("#total").html().replace(/['"]+/g, ''),
			"baseCost":$("#baseCost").html().replace(/['"]+/g, ''),
			"discount":$("#discount").html().replace(/['"]+/g, ''),
			"checkin":$("#checkin").val(),
			"checkout":$("#checkout").val(),
			"rooms":rooms.replace(/['"]+/g, ''),
			"username":username
		}
		$.ajax({
			type: "POST",
			contentType: "application/json",
			url: context+"/rest/book",
			dataType: "json",
			data: JSON.stringify(booking),	
			cache: false,
			success : function(result) {
				window.location.href = context+result.url;
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
		submitted = true;
	});
});

$(document).ready(function(){
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: context+"/rest/rooms",
		dataType: "json",
		data: JSON.stringify(rooms),
		cache: false,
		success : function(result) {
			for(let room of result){
				console.log(room);
				let amenityStr = "";
				for(let a of room.amenities){
					amenityStr += ", " + a.name;
				}
				$("#roomList").append("<tr><td>"+room.type+"</td><td>$"+room.price+"</td><td>"+room.area+"</td><td>"+amenityStr.substr(2)+"</td></tr>");
			}
		},
		error : function(e) {
			alert("Error!")
			console.log("ERROR: ", e);
		}
	});
});

$(window).bind('beforeunload', function(e){
	console.log(bookingId);
	if(!submitted){
		$.ajax({
			type: "POST",
			contentType : "application/json",
			url : context+"/rest/check",
			dataType: "json",
			data: JSON.stringify({"id":bookingId}),	
			cache: false
		});
		let message = "Hello Message";
		if (e) {
	        e.returnValue = message;
	    }
	    return message;
    }
});
