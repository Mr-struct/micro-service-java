package com.microservice.crud.service.contollers;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.microservice.crud.service.entities.Hostel;
import com.microservice.crud.service.entities.Room;
import com.microservice.crud.service.repositories.HostelRepository;
import com.microservice.crud.service.repositories.RoomRepository;

@RequestMapping("/hostel")
@RestController
public class HostelController {
	
	@Autowired
	private HostelRepository repo;

	@Autowired
	private RoomRepository roomRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity<Hostel> getHostelById(@PathVariable("id") Long id) {
		Hostel r = repo.findById(id).orElse(new Hostel());
		if (r.getId() == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Hostel>(r, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<Hostel>> getHostels(Principal p, HttpServletResponse res, HttpServletRequest req) throws IOException {
		List<Hostel> hostels = (List<Hostel>) repo.findAll();
		if (hostels == null || hostels.isEmpty()) {
			  res.sendError(HttpStatus.NOT_FOUND.value(), "Aucun hostel trouvé");
		}
		return new ResponseEntity<List<Hostel>>(hostels, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<Hostel> createHostel(@RequestBody Hostel hostel) {

		if (hostel == null || hostel.getRooms() == null || hostel.getRooms().isEmpty()) {
			return new ResponseEntity<Hostel>(HttpStatus.BAD_REQUEST);
		}

		Hostel findHostel = repo.findHostelByName(hostel.getName());
		if (findHostel != null) {
			
			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		}
		
		for(Room room: hostel.getRooms()) {
			if(roomRepository.findRoomByName(room.getName()) != null) {
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			}
		}
		
		for(Room room : hostel.getRooms()) {
			roomRepository.save(room);
		}
		
		findHostel = repo.save(hostel);

		return new ResponseEntity<Hostel>(findHostel, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody Hostel hostel) {

		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Hostel findHostel = repo.findHostelByName(hostel.getName());

		if (findHostel == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repo.updateHostel(id, findHostel.getName(),findHostel.getPhoneNumber());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Hostel findHostel = repo.findById(id).orElse(new Hostel());

		if (findHostel.getId() == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repo.delete(findHostel);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}


