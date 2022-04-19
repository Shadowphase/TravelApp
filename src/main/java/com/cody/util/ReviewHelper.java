package com.cody.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cody.domain.Review;
import com.cody.rest.RestClient;

@Component
public class ReviewHelper {
	@Autowired
	RestClient restClient;

	public void updateRating(ReviewType type, Long entityId, short rating) {
		switch(type) {
		case HOTEL:
			var ratings = restClient.getHotelReviews(entityId);
			var newRating = calculateAverage(ratings, rating);
			restClient.updateHotelRating(entityId, newRating);
			break;
		case ROOM:
			break;
		case APPLICATION:
			break;
		default:
		}
	}

	private float calculateAverage(List<Review> ratings, short rating) {
		double sum = 0.0;
		for(Review r : ratings) {
			System.out.println("rating " + r.getRating());
			sum+=r.getRating();
		}
		sum+=rating;
		System.out.println("sum " + sum);
		System.out.println("result " + (float)sum/(ratings.size()+1));
		return (float)sum/(ratings.size()+1);
	}
}
