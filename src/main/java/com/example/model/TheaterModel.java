package com.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
//@Table(name = "theater")
public class TheaterModel {
	@Id
	private String theaterName;
	private String theaterImage;
	private String phoneNumber;
	private String location;
	private int seatNumber;

	public String getTheaterName() {
		return theaterName;
	}

	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}

	public String getTheaterImage() {
		return theaterImage;
	}

	public void setTheaterImage(String theaterImage) {
		this.theaterImage = theaterImage;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int i) {
		this.seatNumber = i;
	}
}