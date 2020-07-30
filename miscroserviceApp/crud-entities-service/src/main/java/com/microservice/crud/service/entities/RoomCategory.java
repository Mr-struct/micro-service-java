package com.microservice.crud.service.entities;

public enum RoomCategory {

	SR(5, 1000), S(3, 720), JS(2, 500), CD(2, 300), CS(2, 150);

	private float defaultPrice;
	private int capacity;

	RoomCategory(int capacity, float defaultPrice) {
		this.defaultPrice = defaultPrice;
		this.capacity = capacity;
	}

	public float getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(float defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
