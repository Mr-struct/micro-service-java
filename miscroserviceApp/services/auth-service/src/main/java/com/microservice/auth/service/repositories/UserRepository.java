package com.microservice.auth.service.repositories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.auth.service.entities.AppUser;

@Repository
public interface UserRepository extends CrudRepository<AppUser, Long> {

	
    @Query("SELECT u FROM AppUser u WHERE u.username LIKE %:username")
    public AppUser findByUsername(String username);

	
}
