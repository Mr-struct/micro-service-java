package com.microservice.auth.service.entities;


import org.springframework.stereotype.Component;


@Component
public class JwtUserDetails  {

	private AppUser user;

	public JwtUserDetails() {}
	
	public JwtUserDetails(AppUser user) {
		this.user = user;
	}
	
	   private String username, password;
	    
	    public String getUsername() {
			return username;
		}
	    
	    public void setUsername(String username) {
			this.username = username;
		}
	    public String getPassword() {
			return password;
		}
	    public void setPassword(String password) {
			this.password = password;
		}


	public AppUser getUser() {
		return user;
	}

	public String getUserRole() {
		return user.getRole();
	}

}
