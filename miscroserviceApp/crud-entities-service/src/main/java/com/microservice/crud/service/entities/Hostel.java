package com.microservice.crud.service.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Hostel {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String phoneNumber;
	
	@Column(nullable = false)
	private String address;
	
	@OneToMany
	private List<Room> rooms = new ArrayList<>();

	
	public Hostel(String name, String phoneNumber, String address, List<Room> rooms) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.rooms = rooms;
	}

	public Hostel(String name, String phoneNumber, String address, Room rooms) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.rooms.add(rooms);
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	
	

}
