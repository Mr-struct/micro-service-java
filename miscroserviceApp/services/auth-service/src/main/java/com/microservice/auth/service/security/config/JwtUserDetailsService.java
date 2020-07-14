package com.microservice.auth.service.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservice.auth.service.entities.AppUser;
import com.microservice.auth.service.entities.JwtUserDetails;
import com.microservice.auth.service.repositories.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService{

		@Autowired
		private UserRepository userRepository;
	
	@Override
	public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepository.findByUsername(username);

		if(user != null){
			JwtUserDetails use = new JwtUserDetails(user);
			return new JwtUserDetails(user);
		}
		else
			throw new UsernameNotFoundException("L'utilisateur portant le nom " + username + " n'existe pas");
	}
}
