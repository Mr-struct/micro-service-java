package com.microservice.crud.service.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;


@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "hostel_id", referencedColumnName = "id")
	private Hostel hostel;
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "room_id", referencedColumnName = "id")
	private Room room;
	
	@CreationTimestamp
	private Date reservationDate;
	
	public Reservation() {
		
	}
	
	public Reservation(Hostel hostel, Room room, Date reservationDate) {
		this.hostel = hostel;
		this.room = room;
		this.reservationDate = reservationDate;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Hostel getHostel() {
		return hostel;
	}
	public void setHostel(Hostel hostel) {
		this.hostel = hostel;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public Date getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}
}
