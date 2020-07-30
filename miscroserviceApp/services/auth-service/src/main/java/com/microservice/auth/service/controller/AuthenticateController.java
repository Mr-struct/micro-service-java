package com.microservice.auth.service.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.microservice.auth.service.repositories.UserRepository;
import com.microservice.auth.service.security.config.JwtUserDetailsService;
import com.microservice.communservice.JwtConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import com.microservice.auth.service.entities.AppUser;

@RestController
@RequestMapping("/")
public class AuthenticateController {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtConfig jwtConfig;

	@GetMapping("/me")
	public ResponseEntity<UserDetails> getMeAsUser(HttpServletRequest request) {
		 String token = request.getHeader(jwtConfig.getHeader());
		 
		if(token == null) {
			return new ResponseEntity<UserDetails>(HttpStatus.BAD_REQUEST);
		}
		 String userName;
         try {
        	 userName = Jwts.parser()
                     .setSigningKey(jwtConfig.getSecret().getBytes())
                     .parseClaimsJws(token.replace(jwtConfig.getPrefix(), ""))
                     .getBody()
                     .getSubject();
         } catch (SignatureException e) {
 			return new ResponseEntity<UserDetails>(HttpStatus.BAD_REQUEST);
         }
		UserDetails user = jwtUserDetailsService.loadUserByUsername(userName);
		if (user != null) {
			return new ResponseEntity<UserDetails>(user,
					HttpStatus.OK);
		}
		return new ResponseEntity<UserDetails>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/register")
	public ResponseEntity<com.microservice.auth.service.entities.UserDetails> registerNewUser(@RequestBody AppUser user, HttpServletResponse response)
			throws IOException {
		if (user != null && user.getUsername() != null && user.getPassword() != null && user.getRole() != null) {
			AppUser isThereAnyUser = userRepository.findByUsername(user.getUsername());
			if (isThereAnyUser == null) {
				AppUser newUser = userRepository.save(
						new AppUser(user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getRole()));
				return new ResponseEntity<com.microservice.auth.service.entities.UserDetails>(new com.microservice.auth.service.entities.UserDetails(newUser.getUsername(), newUser.getRole()),
						HttpStatus.CREATED);
			} else {
				response.sendError(HttpStatus.CONFLICT.value(), "Un utilisateur portant ce nom existe déjà");
			}
		}
		return new ResponseEntity<com.microservice.auth.service.entities.UserDetails>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<Token> authenticate(@RequestBody AppUser user) throws Exception {
		if (user != null && user.getUsername() != null && user.getPassword() != null) {
			UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.getUsername());
			if (userDetails != null) {
				try {
					authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(userDetails.getUsername(), user.getPassword()));
					Long now = System.currentTimeMillis();
					Token token = new Token(Jwts.builder()
							.setSubject(userDetails.getUsername())	
							// Convert to list of strings. 
							// This is important because it affects the way we get them back in the Gateway.
							.claim("authorities", userDetails.getAuthorities().stream()
								.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
							.setIssuedAt(new Date(now))
							.setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))  // in milliseconds
							.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
							.compact());
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
