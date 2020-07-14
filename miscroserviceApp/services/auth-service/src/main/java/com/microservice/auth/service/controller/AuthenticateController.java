package com.microservice.auth.service.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.auth.service.entities.AppUser;
import com.microservice.auth.service.entities.JwtUserDetails;
import com.microservice.auth.service.entities.UserDetails;
import com.microservice.auth.service.repositories.UserRepository;
import com.microservice.auth.service.security.config.JwtTokenUtil;
import com.microservice.auth.service.security.config.JwtUserDetailsService;

@RestController
@RequestMapping("/")
public class AuthenticateController {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/me")
	public ResponseEntity<org.springframework.security.core.userdetails.UserDetails> getMeAsUser(Principal principal) {
		JwtUserDetails userDetails = jwtUserDetailsService.loadUserByUsername(principal.getName());
		if (userDetails != null) {
			return new ResponseEntity<org.springframework.security.core.userdetails.UserDetails>(userDetails,
					HttpStatus.OK);
		}
		return new ResponseEntity<org.springframework.security.core.userdetails.UserDetails>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/register")
	public ResponseEntity<UserDetails> registerNewUser(@RequestBody AppUser user, HttpServletResponse response)
			throws IOException {
		if (user != null && user.getUsername() != null && user.getPassword() != null && user.getRole() != null) {
			AppUser isThereAnyUser = userRepository.findByUsername(user.getUsername());
			if (isThereAnyUser == null) {
				AppUser newUser = userRepository.save(
						new AppUser(user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getRole()));
				return new ResponseEntity<UserDetails>(new UserDetails(newUser.getUsername(), newUser.getRole()),
						HttpStatus.CREATED);
			} else {
				response.sendError(HttpStatus.CONFLICT.value(), "Un utilisateur portant ce nom existe déjà");
			}
		}
		return new ResponseEntity<UserDetails>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<Token> authenticate(@RequestBody AppUser user) throws Exception {
		if (user != null && user.getUsername() != null && user.getPassword() != null) {
			JwtUserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.getUsername());
			if (userDetails != null) {
				try {
					authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(userDetails.getUsername(), user.getPassword()));
					Token token = new Token(jwtTokenUtil.generateToken(userDetails));
					return new ResponseEntity<Token>(token, HttpStatus.OK);
				} catch (BadCredentialsException e) {
					e.printStackTrace();
				}
			}
		}
		return new ResponseEntity<Token>(HttpStatus.FORBIDDEN);
	}

	private class Token {
		private String token;

		Token(String token) {
			this.token = token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getToken() {
			return token;
		}
	}
}
