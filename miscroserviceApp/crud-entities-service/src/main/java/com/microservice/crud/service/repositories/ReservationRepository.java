package com.microservice.crud.service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.crud.service.entities.Reservation;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long>{

}
