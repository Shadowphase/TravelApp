$(document).ready(function(){
	$("#filterBtn").bind('click', filterHotels);
	$("#searchBtn").bind('click', filterHotels);
});

function filterHotels(){
	let setstarRatingFilter=[];
    $('.star_rating').each(function(){
         if($(this).is(':checked')){
         starRating =$(this).val();
         setstarRatingFilter.push(parseInt(starRating));
         }
    });
    //console.log("star ", setstarRatingFilter);
    
    priceFilter = $('#priceRange').val();
    //console.log("price ", priceFilter);
    
    let sethotelAmenityFilter=[];
    $('.hotel_amenity').each(function(){
        if($(this).is(':checked')){
	        amenity =$(this).val();
	        sethotelAmenityFilter.push(amenity);
        }
    });
    //console.log("amenity ", sethotelAmenityFilter);
	$("#listHotel").children().each(function(){
		let room=false, searched=false, amenities=false, stars=false;
		$(this).hide();
		
		let hotelStar = $(this).find('div[name="stars"]').text();
		if(setstarRatingFilter.length == 0){
			stars=true;
		}else{
			stars=setstarRatingFilter.includes(Math.trunc(hotelStar));
		}
//		for(let star of setstarRatingFilter){
//			if(Math.trunc(hotelStar) == star){
//				stars=true;
//			}
//		}
		if(sethotelAmenityFilter.length == 0){
			amenities = true;
		}else{
			let hotelAmenities = $(this).find('div[name="amenities"]').text();
			for(let amenity of sethotelAmenityFilter){
				if(hotelAmenities.includes(amenity)){
					amenities=true;
				}else{
					amenities=false;
					break;
				}
			}
		}
		let search = $('#searchLocation').val().toLowerCase();
		$(this).hide();
		if($(this).find('div[name="name"]').text().toLowerCase().includes(search)){
			searched=true;
		}
		if($(this).find('div[name="city"]').text().toLowerCase().includes(search)){
			searched=true;
		}
		if($(this).find('div[name="state"]').text().toLowerCase().includes(search)){
			searched=true;
		}
		if($(this).find('div[name="address"]').text().toLowerCase().includes(search)){
			searched=true;
		}
		if($(this).find('div[name="numRooms"]').text()>=$('#noRooms').val()){
			room = true;
		}
		if(room & searched & amenities & stars){
			$(this).show();
		}
	});
}