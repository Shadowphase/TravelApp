package com.cody.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class Booking {
	private Long id;
	private double total;
	private double baseCost;
	private double discount;
	@DateTimeFormat(pattern = "dd-MM-YYYY")
	private LocalDate checkin;
	@DateTimeFormat(pattern = "dd-MM-YYYY")
	private LocalDate checkout;
	@DateTimeFormat(pattern = "dd-MM-YYYY mm:HH")
	private LocalDateTime dateBooked;
	private List<Long> rooms;
	private Long customer;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getBaseCost() {
		return baseCost;
	}
	public void setBaseCost(double baseCost) {
		this.baseCost = baseCost;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public LocalDate getCheckin() {
		return checkin;
	}
	public void setCheckin(LocalDate checkin) {
		this.checkin = checkin;
	}
	public LocalDate getCheckout() {
		return checkout;
	}
	public void setCheckout(LocalDate checkout) {
		this.checkout = checkout;
	}
	public LocalDateTime getDateBooked() {
		return dateBooked;
	}
	public void setDateBooked(LocalDateTime dateBooked) {
		this.dateBooked = dateBooked;
	}
	public List<Long> getRooms() {
		return rooms;
	}
	public void setRooms(List<Long> rooms) {
		this.rooms = rooms;
	}
	public Long getCustomer() {
		return customer;
	}
	public void setCustomer(Long customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "{\"id\":"+id+
				",\"total\":"+total+
				",\"customer\":"+customer+
				",\"baseCost\":"+baseCost+
				",\"discount\":"+discount+
				",\"checkin\":\""+checkin+
				"\",\"checkout\":\""+checkout+
				"\",\"dateBooked\":\""+dateBooked+
				"\",\"rooms\":"+rooms+"}";
	}
}
