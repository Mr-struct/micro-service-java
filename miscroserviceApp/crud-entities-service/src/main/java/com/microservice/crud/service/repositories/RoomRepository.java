package com.microservice.crud.service.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.crud.service.entities.Room;
import com.microservice.crud.service.entities.RoomCategory;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

	
	@Query("SELECT r FROM Room r WHERE r.name = :name")
	Room findRoomByName(String name);

	@Transactional
	@Query("UPDATE Room SET name = :name, roomCategory = :roomCategory, capacity = :capacity, price = :price WHERE id = :id")
	void updateRoom(Long id, String name, RoomCategory roomCategory, int capacity, float price);
	
}
