package com.cody.rest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cody.domain.Booking;
import com.cody.domain.Customer;
import com.cody.domain.Review;
import com.cody.domain.Role;
import com.cody.domain.User;
import com.cody.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestClient {

	public JsonNode getUserById(Long id) {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("{\"id\":"+id+"}", headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(Constants.USER_MANAGEMENT_URL+"/getUserById", request, JsonNode.class);

		//System.out.println("response " + response.getBody());
		return response.getBody();
	}

	public JsonNode getUserByName(String name) {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("{\"name\":\""+name+"\"}", headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(Constants.USER_MANAGEMENT_URL+"/getUserByName", request, JsonNode.class);

		//System.out.println("response " + response.getBody());
		return response.getBody();
	}

	public JsonNode getHotels() {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.getForEntity(Constants.HOTEL_MANAGEMENT_URL+"/hotels", JsonNode.class);

		//System.out.println("response " + response.getBody());
		return response.getBody();
	}

	public JsonNode getHotelAmenities() {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.getForEntity(Constants.HOTEL_MANAGEMENT_URL+"/hotelAmenities", JsonNode.class);

		//System.out.println("response " + response.getBody());
		return response.getBody();
	}

	public JsonNode getRoomsFromHotel(Long id) {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.getForEntity(Constants.HOTEL_MANAGEMENT_URL+"/rooms/"+id, JsonNode.class);

		//System.out.println("response " + response.getBody());
		return response.getBody();
	}

	public JsonNode getRooms(JsonNode json) {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(Constants.HOTEL_MANAGEMENT_URL+"/rooms", request, JsonNode.class);

		//System.out.println("response " + response.getBody());
		return response.getBody();
	}

	public JsonNode updateHotelRating(Long id, float rating) {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("{\"id\":"+id+",\"rating\":"+rating+"}", headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(Constants.HOTEL_MANAGEMENT_URL+"/updateHotelRating", request, JsonNode.class);

		//System.out.println("response " + response.getBody());
		return response.getBody();
	}

	public List<Review> getHotelReviews(Long id) {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.getForEntity(Constants.BOOKING_MANAGEMENT_URL+"/getHotelReviews/"+id, JsonNode.class);
		System.out.println("response " + response.getBody());
		List<Review> reviews = new ArrayList<>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			var nodes = Arrays.asList(mapper.readValue(response.getBody().toString(), JsonNode[].class));
			for(JsonNode node : nodes) {
				System.out.println("node " + node);
				Review review = new Review();
				review.setRating(node.get("rating").shortValue());
				review.setComments(node.get("comments").asText());
				reviews.add(review);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return reviews;
	}

	public List<Float> getRoomRatings(Long id) {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.getForEntity(Constants.HOTEL_MANAGEMENT_URL+"/getRoomRatings/"+id, JsonNode.class);
		try {
			ObjectMapper mapper = new ObjectMapper();
			return Arrays.asList(mapper.readValue(response.getBody().get("ratings").toString(), Float[].class));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		//System.out.println("response " + response.getBody());
		return new ArrayList<Float>();
	}

	public JsonNode updateRoomRating(Long id, float rating) {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("{\"id\":"+id+",\"rating\":"+rating+"}", headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(Constants.HOTEL_MANAGEMENT_URL+"/updateRoomRating", request, JsonNode.class);

		//System.out.println("response " + response.getBody());
		return response.getBody();
	}

	public JsonNode bookRoom(Long roomId, Boolean occupied) {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("{\"id\":"+roomId+",\"occupied\":"+occupied+"}", headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(Constants.HOTEL_MANAGEMENT_URL+"/updateRoomBooking", request, JsonNode.class);

		//System.out.println("response " + response.getBody());
		return response.getBody();
	}

	public JsonNode saveReview(String reviewString) {
		System.out.println("save review rest " + reviewString);
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(reviewString, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(Constants.BOOKING_MANAGEMENT_URL+"/saveReview", request, JsonNode.class);

		//System.out.println("response " + response.getBody());
		return response.getBody();
	}

	public JsonNode getRoomAmenities() {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.getForEntity(Constants.HOTEL_MANAGEMENT_URL+"/roomAmenities", JsonNode.class);

		//System.out.println("response " + response.getBody());
		return response.getBody();
	}

	public Booking getBooking(Long id) {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.getForEntity(Constants.BOOKING_MANAGEMENT_URL+"/booking/"+id, JsonNode.class);

		System.out.println("get response " + response.getBody());
		Booking booking = new Booking();
		booking.setId(response.getBody().get("id").asLong());
		if(response.getBody().get("customer").get("id") != null) {
			booking.setCustomer(response.getBody().get("customer").get("id").asLong());
		}
		booking.setBaseCost(response.getBody().get("baseCost").asDouble());
		booking.setDiscount(response.getBody().get("discount").asDouble());
		booking.setTotal(response.getBody().get("total").asDouble());
		booking.setCheckin(LocalDate.parse(response.getBody().get("checkin").asText()));
		booking.setCheckout(LocalDate.parse(response.getBody().get("checkout").asText()));
		var dateBooked = response.getBody().get("dateBooked").asText();
		if(dateBooked != "null") {
			System.out.println("dateBooked " + dateBooked);
			booking.setDateBooked(LocalDateTime.parse(dateBooked));
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<Long> rooms = Arrays.asList(mapper.readValue(response.getBody().get("rooms").toString(), Long[].class));
			booking.setRooms(rooms);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return booking;
	}

	public List<Booking> getBookingsByUser(Long id){
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.getForEntity(Constants.BOOKING_MANAGEMENT_URL+"/getBookingsByUser/"+id, JsonNode.class);

		System.out.println("bookings json " + response.getBody().toString());

		List<Booking> bookings = new ArrayList<>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			var bookingsList = Arrays.asList(mapper.readValue(response.getBody().toString(), JsonNode[].class));
			for(JsonNode jBook : bookingsList) {
				Booking booking = new Booking();
				booking.setId(jBook.get("id").asLong());
				booking.setCustomer(jBook.get("customer").get("id").asLong());
				booking.setTotal(jBook.get("total").asDouble());
				booking.setBaseCost(jBook.get("baseCost").asDouble());
				booking.setDiscount(jBook.get("discount").asDouble());
				booking.setCheckin(LocalDate.parse(jBook.get("checkin").asText()));
				booking.setCheckout(LocalDate.parse(jBook.get("checkout").asText()));
				//booking.setDateBooked(LocalDateTime.parse(jBook.get("dateBooked").asText()));
				try {
					List<Long> rooms = Arrays.asList(mapper.readValue(jBook.get("rooms").toString(), Long[].class));
					booking.setRooms(rooms);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

				bookings.add(booking);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return bookings;
	}

	public String saveBooking(JsonNode json) {
		System.out.println("save JSON " + json);

		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(Constants.BOOKING_MANAGEMENT_URL+"/saveBooking", request, JsonNode.class);

		//System.out.println("create response " + response.getBody());
		return response.getBody().get("id").asText();
	}

	public String saveBooking(Booking booking) {
		System.out.println("save Booking " + booking);
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(booking.toString(), headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(Constants.BOOKING_MANAGEMENT_URL+"/saveBooking", request, JsonNode.class);

		//System.out.println("create response " + response.getBody());
		return response.getBody().get("id").asText();
	}

	public JsonNode checkBooking(JsonNode json) {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(Constants.BOOKING_MANAGEMENT_URL+"/checkBooking", request, JsonNode.class);

		System.out.println("check response " + response.getBody());
		return response.getBody();
	}

	public User findUserByName(String username) {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("{\"name\":\""+username+"\"}", headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<User> response = restTemplate.postForEntity(Constants.USER_MANAGEMENT_URL+"/getUserByName", request, User.class);

		System.out.println("user response " + response.getBody());
		return response.getBody();
	}

	public Role getRoleByName(String role) {
		String roleStr = "{\"name\":\""+role+"\"}";
		System.out.println("roleStr " + roleStr);
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(roleStr, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Role> response = restTemplate.postForEntity(Constants.USER_MANAGEMENT_URL+"/getRoleByName", request, Role.class);

		System.out.println("role response " + response.getBody());
		return response.getBody();
	}

	public JsonNode saveUser(User user) {
		List<Long> roles = new ArrayList<>();
		for(var role : user.getRoles()) {
			roles.add(role.getId());
		}
		StringBuilder sb = new StringBuilder();
		sb.append("{\"name\":\""+user.getName()+"\"");
		sb.append(",\"password\":\""+user.getPassword()+"\"");
		sb.append(",\"roles\":"+roles+"}");
		System.out.println("UserStr " + sb);
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(sb.toString(), headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(Constants.USER_MANAGEMENT_URL+"/saveUser", request, JsonNode.class);

		System.out.println("create user response " + response.getBody());
		return response.getBody();
	}

	public JsonNode saveCustomer(Customer customer) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"firstName\":\""+customer.getFirstName()+"\"");
		sb.append(",\"lastName\":\""+customer.getLastName()+"\"");
		sb.append(",\"age\":"+customer.getAge());
		sb.append(",\"gender\":\""+customer.getGender()+"\"");
		sb.append(",\"email\":\""+customer.getEmail()+"\"");
		sb.append(",\"phone\":\""+customer.getPhone()+"\"");
		sb.append(",\"userId\":"+customer.getUserId()+"}");
		System.out.println("UserStr " + sb);
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(sb.toString(), headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(Constants.BOOKING_MANAGEMENT_URL+"/saveCustomer", request, JsonNode.class);

		System.out.println("create customer response " + response.getBody());
		return response.getBody();
	}

	public Long getCustomerId(Long userId) {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Long> response = restTemplate.getForEntity(Constants.BOOKING_MANAGEMENT_URL+"/getCustomerIdFromUserId/"+userId, Long.class);

		//System.out.println("response " + response.getBody());
		return response.getBody();
	}
}
