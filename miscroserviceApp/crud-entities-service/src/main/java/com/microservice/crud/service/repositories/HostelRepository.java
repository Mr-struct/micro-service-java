package com.microservice.crud.service.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.crud.service.entities.Hostel;
import com.microservice.crud.service.entities.Room;


@Repository
public interface HostelRepository extends CrudRepository<Hostel, Long> {

	@Query("SELECT h.rooms FROM Hostel h WHERE h.id = :id")
	List<Room> getHostelRooms(Long id);
	
	@Query("SELECT h FROM Hostel h WHERE h.name = :name")
	Hostel findHostelByName(String name);

	@Transactional
	@Query("UPDATE Hostel SET name = :name, phoneNumber = :phoneNumber, id = :id")
	void updateHostel(Long id, String name, String phoneNumber);
}
