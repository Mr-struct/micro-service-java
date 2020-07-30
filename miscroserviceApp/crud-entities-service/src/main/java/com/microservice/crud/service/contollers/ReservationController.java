package com.microservice.crud.service.contollers;

import java.io.IOException;
import java.util.List;

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
import com.microservice.crud.service.entities.Reservation;
import com.microservice.crud.service.entities.Room;
import com.microservice.crud.service.repositories.HostelRepository;
import com.microservice.crud.service.repositories.ReservationRepository;
@RequestMapping("/reservation")
@RestController()
public class ReservationController {
	
	@Autowired
	private ReservationRepository repo;
	
	@Autowired
	private HostelRepository hostelRepo;
	
	@GetMapping("/{id}")
	public ResponseEntity<Reservation> getReservationById(@PathVariable("id") Long id, HttpServletResponse res) throws IOException {
		if(id==null) {
			res.sendError(HttpStatus.BAD_REQUEST.value(), "L'id ne peut etre null");
		}
		Reservation r = repo.findById(id).orElse(new Reservation());
		if (r.getId() == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Reservation>(r, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<Reservation>> getReservations() {
		List<Reservation> Reservations = (List<Reservation>) repo.findAll();
		if (Reservations == null || Reservations.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Reservation>>(Reservations, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {

		if (reservation == null) {
			return new ResponseEntity<Reservation>(HttpStatus.BAD_REQUEST);
		}

		Hostel findHostel = hostelRepo.findById(reservation.getHostel().getId()).get();

		if (findHostel == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Room findRoom = findHostel.getRooms().stream().filter(x-> x.getId() == reservation.getRoom().getId()).findFirst().orElse(null);
		
		if(findRoom.isTaken()) {
			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		}
		
		return new ResponseEntity<Reservation>(repo.save(reservation), HttpStatus.CREATED);
	}

//	@PutMapping("/{id}")
//	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody Reservation reservation) {
//
//		if (id == null) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//
//		Reservation findReservation = repo.findById(reservation.getId()).orElse(null);
//
//		if (findReservation == null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		repo.updateReservation(id, findReservation.getName(),findReservation.getPhoneNumber());
//		return new ResponseEntity<Void>(HttpStatus.OK);
//	}
//	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Reservation findReservation = repo.findById(id).orElse(new Reservation());

		if (findReservation.getId() == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repo.delete(findReservation);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
