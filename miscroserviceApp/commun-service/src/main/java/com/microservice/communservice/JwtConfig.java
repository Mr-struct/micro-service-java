package com.microservice.communservice;

import org.springframework.beans.factory.annotation.Value;

public class JwtConfig {
    @Value("${security.jwt.uri:/auth/**}")
    private String Uri;

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;

    //TODO : réduire la durée du token < 10 min
    @Value("${security.jwt.expiration:#{24*60*60}}")
    private int expiration;

    @Value("${security.jwt.secret:JwtSecretKey}")
    private String secret;

	public String getUri() {
		return Uri;
	}

	public void setUri(String uri) {
		Uri = uri;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getExpiration() {
		return expiration;
	}

	public void setExpiration(int expiration) {
		this.expiration = expiration;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	
//	package com.microservice.auth.service.security.config;
//	import java.io.Serializable;
//	import java.util.Date;
//	import java.util.HashMap;
//	import java.util.Map;
//	import java.util.function.Function;
//	import org.springframework.security.core.userdetails.UserDetails;
//	import org.springframework.stereotype.Component;
//
//	import com.microservice.auth.service.entities.JwtUserDetails;
//
//	import io.jsonwebtoken.Claims;
//	import io.jsonwebtoken.Jwts;
//	import io.jsonwebtoken.SignatureAlgorithm;
//
//	@Component
//	public class JwtTokenUtil implements Serializable {
//		private static final long serialVersionUID = -9077248927410675523L;
//		public static final long JWT_TOKEN_VALIDITY = 24*60*60;
//		private String secret = "JwtSecretKey";
//		
//		// retrieve username from jwt token
//		public String getUsernameFromToken(String token) {
//			return getClaimFromToken(token, Claims::getSubject); 
//		}
//		
//		//retrieve expiration date from jwt token 
//		public Date getExpirationDateFromToken(String token) { 
//			return getClaimFromToken(token, Claims::getExpiration);
//			}
//		
//		public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//		    final Claims claims = getAllClaimsFromToken(token); 
//		    return claimsResolver.apply(claims);
//		 }
//		
//		// for retrieving any information from token we will need the secret key 
//		private Claims getAllClaimsFromToken(String token) { 
//			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody(); 
//		}
//		
//		//check if the token has expired 
//		private Boolean isTokenExpired(String token) { 
//			final Date expiration = getExpirationDateFromToken(token);
//			return expiration.before(new Date()); 
//			} 
//		
//		//generate token for user 
//		public String generateToken(String username) { 
//			Map claims = new HashMap<>(); 
//			return doGenerateToken(claims, username); 
//		}
//		
//		//while creating the token - 
//		//1. Define claims of the token, like Issuer, Expiration, Subject, and the ID 
//		//2. Sign the JWT using the HS512 algorithm and secret key. 
//		//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1) 
//		// compaction of the JWT to a URL-safe string
//		private String doGenerateToken(Map claims, String subject) { 
//			return Jwts.builder() 
//					.setClaims(claims) 
//					.setSubject(subject) 
//					.setIssuedAt(new Date(System.currentTimeMillis())) 
//					.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)) 
//					.signWith(SignatureAlgorithm.HS512, secret)
//					.compact();
//		} 
//		
//		//validate token 
//		public Boolean validateToken(String token, JwtUserDetails userDetails) { 
//			final String username = getUsernameFromToken(token);
//			return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); 
//		}
//	}
}