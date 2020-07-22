package com.microservice.crud.service.contollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.crud.service.entities.Room;
import com.microservice.crud.service.repositories.RoomRepository;

@RequestMapping("/room")
@RestController
public class RoomController {

	@Autowired
	private RoomRepository repo;

	@GetMapping("/{id}")
	public ResponseEntity<Room> getRoomById(@PathVariable("id") Long id) {
		Room r = repo.findById(id).orElseGet(null);
		if (r == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Room>(r, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<Room>> getRooms() {
		List<Room> rooms = (List<Room>) repo.findAll();
		if (rooms == null || rooms.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Room>>(rooms, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<Room> createRoom(@RequestBody Room room) {

		if (room == null || room.getCapacity() < 0 || room.getPrice() < 0f || room.getRoomCategory() == null) {
			return new ResponseEntity<Room>(HttpStatus.BAD_REQUEST);
		}

		Room findRoom = repo.findRoomByName(room.getName());

		if (findRoom == null) {
			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		}

		findRoom = repo.save(room);

		return new ResponseEntity<Room>(findRoom, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody Room room) {

		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Room findRoom = repo.findRoomByName(room.getName());

		if (findRoom == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repo.updateRoom(id, findRoom.getName(), findRoom.getRoomCategory(), findRoom.getCapacity(),
				findRoom.getPrice());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Room findRoom = repo.findById(id).orElse(null);

		if (findRoom == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repo.delete(findRoom);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
