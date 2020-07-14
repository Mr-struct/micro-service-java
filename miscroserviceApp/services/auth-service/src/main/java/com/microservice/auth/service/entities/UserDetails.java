package com.microservice.auth.service.entities;

public class UserDetails {

	private String username;
	private String userRole;


	public UserDetails(String userName, String userRole) {
		super();
		this.username = userName;
		this.userRole = userRole;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "UserDetails [userName=" + username + ", userRole=" + userRole + "]";
	}
}
