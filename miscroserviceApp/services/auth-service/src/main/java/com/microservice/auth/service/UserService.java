package com.microservice.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.auth.service.repositories.UserRepository;
import com.microservice.auth.service.entities.AppUser;

@Service
public class UserService {

	@Autowired
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public AppUser findUserByName(String username) {
			return userRepository.findByUsername(username);
	}

	public List<AppUser> getUsers() {
		return (List<AppUser>) userRepository.findAll();
	}

	public AppUser getUser(Long id) {
		return userRepository.findById(id).orElseThrow();
	}

	public void deleteUser(AppUser user) {
		userRepository.delete(user);
	}
	
//    @Transactional
//	public void updateUser(Long id, String username, String userRole) {
//		userRepository.update(id, username, userRole);
//	}

}
