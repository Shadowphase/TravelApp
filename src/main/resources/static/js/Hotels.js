$( document ).ready(function() {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : context+"/rest/hotels",
		success : function(result) {
			//console.log("got "+result.length+" hotels")
			for(let hotel of result){
				let amenityArr=[];
				for(let amenity of hotel.amenities){
					amenityArr.push(amenity.name);
				}
				let rowStr = "<div style='width:100%;clear:both;'><div style='float:left;width:300px'>"+
				"<a href='"+context+"/rooms?id="+hotel.id+"'>"+
				"<img src='"+hotel.imageUrl+"'  alt='image' style='height:300px;width:300px;padding:10px' />"+
				"</a></div>"+
				"<div style='float:left;height:300px;width:auto;'>"+
				"<div name='name'>"+hotel.name+"</div>"+
				"<div name='city'>"+hotel.city+"</div>"+
				"<div name='state'>"+hotel.state+"</div>"+
				"<div name='address'>"+hotel.address+"</div>"+
				"<div name='email'>"+hotel.email+"</div>"+
				"<div name='phone'>"+hotel.phone+"</div>"+
				"<div name='stars'>"+hotel.stars+"</div>"+
				"<div name='rating'>"+hotel.rating+"</div>"+
				"<div name='amenities'>"+amenityArr+"</div>"+
				"<div name='numRooms' style='hidden'>"+hotel.rooms.length+"</div>"+
				"<button onclick=\"window.location.href='"+context+"/reviews/HOTEL/"+hotel.id+"'\">Review</button>"+
				"</div>";
				$("#listHotel").append(rowStr);
			}
		},
		error : function(e) {
			console.log("ERROR: ", e);
		}
	});
	
	$.ajax({
		type: "GET",
		contentType : "application/json",
		url : context+"/rest/hotelAmenities",
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
});