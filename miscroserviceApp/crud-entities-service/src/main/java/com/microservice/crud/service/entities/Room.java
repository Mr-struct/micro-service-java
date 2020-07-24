package com.microservice.crud.service.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Room {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RoomCategory roomCategory;
	
	@Column(nullable = false)
	private float price;
	
	@Column(nullable = false)
	private int capacity;

	@Column(nullable = false)
	private boolean taken;
	
	public Room() {
		
	}
	
	public Room(String name, RoomCategory roomCategory, float price, int capacity) {
		super();
		this.name = name;
		this.roomCategory = roomCategory;
		this.price = price <= 0 ? roomCategory.getDefaultPrice() : price;
		this.capacity = capacity;
		this.taken = false;
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
	public RoomCategory getRoomCategory() {
		return roomCategory;
	}
	public void setRoomCategory(RoomCategory roomCategory) {
		this.roomCategory = roomCategory;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isTaken() {
		return taken;
	}

	public void setTaken(boolean taken) {
		this.taken = taken;
	}
	
}
