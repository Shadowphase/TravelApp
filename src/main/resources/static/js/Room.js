$(document).ready(function(){
	let date = new Date();
	date.setDate(date.getDate()+1);
	document.getElementById('checkInDate').value = date.toISOString().split('T')[0];
	date.setDate(date.getDate()+1);
	document.getElementById('checkOutDate').value = date.toISOString().split('T')[0];
});

$(document).ready(function(){
	console.log("hotel id "+hotelId);
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : context+"/rest/rooms/"+hotelId,
		success : function(result) {
			console.log("got "+result.length+" rooms")
			for(let room of result){
				if(!room.occupied){
					let amenityArr=[];
					for(let amenity of room.amenities){
						amenityArr.push(amenity.name);
					}
					let rowStr = "<tr><td style='display:none;' name='id'>"+room.id+
								"</td><td name='type' style='padding:5px'>"+room.type+
								"</td><td name='area' style='padding:5px'>"+room.area+
								"</td><td name='price' style='padding:5px'>$"+room.price+
								"</td><td name='amenityArr' style='padding:5px'>"+amenityArr+
								"</td><td name='occupancy' style='padding:5px'>"+room.occupancy+
								"</td><td><button type='button' onclick='addToCart(this)' class='btn-primary'>Add</button></td></tr>";
					$('#roomTable').append(rowStr);
				}
			}
		},
		error : function(e) {
			console.log("ERROR: ", e);
		}
	});
	
	$.ajax({
		type: "GET",
		contentType : "application/json",
		url : context+"/rest/roomAmenities",
		success : function(result){
			//console.log("amenities "+result);
			for(let amenity of result){
				//console.log(amenity);
				$("#amenities").append("<input type='checkbox' class='hotel_amenity form-check-input' id='"+amenity+"' value='"+amenity+"'/>"+
									"<label class='form-check-label' for='"+amenity+"'>"+amenity+"</label><br>");
			}
		},
		error : function(e){
			console.log("ERROR", e);
		}
	});
	
	$("#book").click(function(){
		let cartArr = [];
		let sumPrice = 0;
		$("#cart").children().each(function(){
			cartArr.push(parseInt($(this).children()[0].innerText));
			sumPrice += parseFloat($(this).children()[1].innerText);
		})
		console.log(cartArr, sumPrice);
		let booking = {
			"username":username,
			"baseCost":sumPrice,
			"checkin":$("#checkInDate").val(),
			"checkout":$("#checkOutDate").val(),
			"rooms":cartArr
		}
		console.log(booking);
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : context+"/rest/booking",
			dataType: "json",
			data: JSON.stringify(booking),	
			cache: false,
			success : function(result) {
				console.log(result);
				window.location = context+result.url;
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
	})
});

function addToCart(btn){
	$("#cart").append("<li style='display:none;'><div name='id'>"+parseInt(btn.closest("tr").querySelector('td[name="id"]').innerText)+"</div>"+
					"<div name='price'>"+parseFloat(btn.closest("tr").querySelector('td[name="price"]').innerText.substring(1))+"</div></li>")
 	$("#cartCount").text(parseInt($("#cartCount").text())+1);
}