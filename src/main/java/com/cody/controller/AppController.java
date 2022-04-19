package com.cody.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cody.domain.Booking;
import com.cody.domain.Customer;
import com.cody.domain.Review;
import com.cody.domain.Role;
import com.cody.domain.User;
import com.cody.rest.RestClient;
import com.cody.util.ReviewType;

@Controller
public class AppController {
	@Autowired
	RestClient restClient;

	@Autowired
	BCryptPasswordEncoder passEncoder;

	@RequestMapping(value= {"/home","/"})
	public String home() {
		return "Home";
	}

	@RequestMapping(value="/rooms")
	public String rooms(@RequestParam Long id, Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("username", auth.getName());
		model.addAttribute("hotelId", id);
		return "Rooms";
	}

	@RequestMapping(value="/booking/{id}")
	public String booking(@ModelAttribute Booking booking, @PathVariable Long id, Model model) {
		model.addAttribute("booking", restClient.getBooking(id));
		return "Booking";
	}

	@RequestMapping(value="register")
	public String register(@ModelAttribute Customer customer) {

		return "Register";
	}

	@RequestMapping(value="registerUser")
	public String registerUser(@Valid @ModelAttribute Customer customer, BindingResult br, Model model){
		System.out.println("Register customer " + customer);
		User user = customer.getUser();
		System.out.println("Register user " + user);
		if(br.hasErrors()) {
			model.addAttribute("user", user);
			model.addAttribute("error", true);
			return "Register";
		}
		System.out.println(br.getAllErrors());
		Set<Role> roles = new HashSet<>();
		roles.add(restClient.getRoleByName("User"));
		user.setRoles(roles);
		user.setPassword(passEncoder.encode(user.getPassword()));
		System.out.println("Save " + user);
		var saveUser = restClient.saveUser(user);
		customer.setUserId(saveUser.get("id").asLong());
		restClient.saveCustomer(customer);
		model.addAttribute("message", "Registered successfully. Login to begin.");
		return "Login";
	}

	@RequestMapping(value={"/accessDenied","/login"})
	public String accessDenied(@RequestHeader(value = "referer", required = false) final String referer,
							@RequestParam(value="logout", required=false) String logout,
							@RequestParam(value="error", required=false) String error,
							HttpServletRequest req, HttpServletResponse res, Model model) {
		String message = null;
		if(logout != null) {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth != null) {
				new SecurityContextLogoutHandler().logout(req, res, auth);
				message="You have been logged out.";
			}
		}

		if(error != null) {
			message="Either user name or password is incorrect.";
		}
		model.addAttribute("message", message);
		model.addAttribute("returnUrl", referer);
		return "Login";
	}

	@RequestMapping(value="/bookingHistory")
	public String bookingHistory(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Long userId = restClient.getUserByName(auth.getName()).get("id").asLong();
		model.addAttribute("bookings", restClient.getBookingsByUser(userId));

		return "BookingHistory";
	}

	@RequestMapping(value = "/reviews/{type}/{id}")
	public String reviews(@PathVariable String type, @PathVariable Long id, Model model) {
		System.out.println("going to feedback with " +type + "-" + id);
		List<Review> reviews = new ArrayList<>();
		switch(ReviewType.valueOf(type)) {
		case HOTEL:
			reviews = restClient.getHotelReviews(id);
			break;
		case ROOM:
			break;
		case APPLICATION:
			break;
		default:
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("username", auth.getName());
		model.addAttribute("reviews", reviews);
		model.addAttribute("reviewType", type);
		model.addAttribute("entityId", id);
		return "Reviews";
	}

	@RequestMapping(value = "/feedback/{type}/{id}")
	public String feedback(@PathVariable String type, @PathVariable Long id, Model model) {
		System.out.println("going to feedback with " +type + "-" + id);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("username", auth.getName());
		model.addAttribute("reviewType", type);
		model.addAttribute("entityId", id);
		return "Review";
	}
}
