$(document).ready(function(){
	console.log("ready");
	$("#submit").click(function(){
		let rating = $("input[name='rating']:checked").val();
		if(rating === undefined){
			alert("Please select a rating.");
			return;
		}
		let review = {
			"username":username.replace(/['"]+/g, ''),
			"comments":$("#comments").val(),
			"reviewType":reviewType.replace(/['"]+/g, ''),
			"entityId":entityId.replace(/['"]+/g, ''),
			"rating":rating
		}
		
		console.log(review);
		
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : context+"/rest/submitReview",
			dataType: "json",
			data: JSON.stringify(review),	
			cache: false,
			success:function(res){
				alert("Review submitted successfully.");
				window.location = context+"/home";
			},
			error:function(err){
				console.log(err);
				alert("An error has occured. Please try again.");
			}
		});
	});
});