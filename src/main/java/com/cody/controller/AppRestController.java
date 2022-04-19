package com.cody.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cody.domain.Booking;
import com.cody.rest.RestClient;
import com.cody.util.ReviewHelper;
import com.cody.util.ReviewType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("rest")
public class AppRestController {
	@Autowired
	RestClient restClient;

	@Autowired
	ReviewHelper reviewHelper;

	@GetMapping(value= "/hotels")
	public JsonNode getHotels() {
		var response = restClient.getHotels();
		return response;
	}

	@GetMapping(value= "/hotelAmenities")
	public JsonNode getHotelAmenities() {
		var response = restClient.getHotelAmenities();
		return response;
	}

	@GetMapping(value= "/rooms/{id}")
	public JsonNode getRoomsFromHotel(@PathVariable Long id) {
		var response = restClient.getRoomsFromHotel(id);
		return response;
	}

	@PostMapping(value="/rooms")
	public JsonNode getRooms(@RequestBody JsonNode json) {
		var response = restClient.getRooms(json);
		return response;
	}

	@GetMapping(value= "/roomAmenities")
	public JsonNode getRoomAmenities() {
		var response = restClient.getRoomAmenities();
		return response;
	}

	@PostMapping(value="/booking")
	public String booking(@RequestBody JsonNode json) {
		//System.out.println("Booking with " +  json);
		var user = restClient.getUserByName(json.get("username").asText());
		StringBuilder sb = new StringBuilder();
		sb.append("{\"baseCost\":"+json.get("baseCost").asDouble());
		if(user!=null) {
			sb.append(",\"userId\":"+user.get("id").asLong());
		}
		sb.append(",\"checkin\":"+json.get("checkin").toString());
		sb.append(",\"checkout\":"+json.get("checkout").toString());
		sb.append(",\"rooms\":"+json.get("rooms").toString()+"}");

		ObjectMapper objectMapper = new ObjectMapper();
		String bookingId="";
		try {
			bookingId = restClient.saveBooking(objectMapper.readTree(sb.toString()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return "{\"url\":\"/booking/"+bookingId+"\"}";
	}

	@PostMapping(value="/book")
	public String book(@RequestBody JsonNode json) {
		System.out.println("book "+ json);
		Booking booking = new Booking();
		booking.setId(json.get("id").asLong());
		if(json.has("customer")) {
			booking.setCustomer(json.get("customer").asLong());
		}else {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			var user = restClient.getUserByName(auth.getName());
			System.out.println("user id " + user);
			Long customerId = restClient.getCustomerId(user.get("id").asLong());
			System.out.println("customer id " + customerId);
			booking.setCustomer(customerId);
		}
		booking.setTotal(json.get("total").asDouble());
		booking.setBaseCost(json.get("baseCost").asDouble());
		booking.setDiscount(json.get("discount").asDouble());
		booking.setCheckin(LocalDate.parse(json.get("checkin").asText()));
		booking.setCheckout(LocalDate.parse(json.get("checkout").asText()));
		booking.setDateBooked(LocalDateTime.now());
		try{
			ObjectMapper mapper = new ObjectMapper();
			List<Long> rooms = Arrays.asList(mapper.readValue(json.get("rooms").asText(), Long[].class));
			for(Long roomId : rooms) {
				restClient.bookRoom(roomId, true);
			}
			booking.setRooms(rooms);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		restClient.saveBooking(booking);
		return "{\"url\":\"/bookingHistory\"}";
	}

	@PostMapping(value="/check")
	public JsonNode checkBooking(@RequestBody JsonNode json) {
		System.out.println("check called " + json);
		var response = restClient.checkBooking(json);
		return response;
	}

	@PostMapping(value="/submitReview")
	public JsonNode submitReview(@RequestBody JsonNode json) {
		System.out.println("Review " + json);
		System.out.println("rating " + json.get("rating").shortValue() + " - " + json.get("rating"));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		JsonNode user = restClient.getUserByName(auth.getName());
		StringBuilder sb = new StringBuilder();
		sb.append("{\"userId\":"+user.get("id").asLong());
		sb.append(",\"comments\":"+json.get("comments").toString());
		sb.append(",\"submitDate\":\""+LocalDateTime.now()+"\"");
		sb.append(",\"reviewType\":"+json.get("reviewType").toString());
		sb.append(",\"rating\":"+json.get("rating").asInt());
		sb.append(",\"entityId\":"+json.get("entityId").asLong()+"}");
		reviewHelper.updateRating(ReviewType.valueOf(json.get("reviewType").asText()),
				json.get("entityId").asLong(),
				json.get("rating").shortValue());

		return restClient.saveReview(sb.toString());
	}
}
