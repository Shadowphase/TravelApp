$(document).ready(function(){
	$("#filterBtn").bind('click', filterRooms);
	$("#searchBtn").bind('click', filterRooms);
});

function filterRooms(){
	//console.log("filter");
    let setRoomAmenityFilter=[];
    $('#amenities').children().each(function(){
        if($(this).is(':checked')){
	        amenity =$(this).val();
	        setRoomAmenityFilter.push(amenity);
        }
    });
    //console.log("amenity ", setRoomAmenityFilter);
	$("#roomTable").children().children().not(':first').each(function(){
		let room=false, searched=false, amenities=false, price=false;
		
		$(this).hide();
		
		if(setRoomAmenityFilter.length == 0){
			amenities = true;
		}else{
			let hotelAmenities = $(this).find('td[name="amenityArr"]').text();
			for(let amenity of setRoomAmenityFilter){
				if(hotelAmenities.includes(amenity)){
					amenities=true;
				}else{
					amenities=false;
					break;
				}
			}
		}
		let search = $('#searchType').val().toLowerCase();
		//console.log("search ", search);
		if($(this).find('td[name="type"]').text().toLowerCase().includes(search)){
			searched=true;
		}
		if($(this).find('td[name="occupancy"]').text()>=$('#noGuests').val()){
			room = true;
		}
		if($(this).find('td[name="price"]').text().substr(1)<=$('#priceRange').val()){
			price = true;
		}
		if(room & searched & amenities & price){
			$(this).show();
		}
	});
}