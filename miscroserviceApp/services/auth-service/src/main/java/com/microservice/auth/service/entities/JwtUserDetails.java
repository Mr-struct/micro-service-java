package com.microservice.auth.service.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtUserDetails implements org.springframework.security.core.userdetails.UserDetails {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private AppUser user;

	public JwtUserDetails(AppUser user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(this.getUserRole());
		return authorities != null ? authorities : AuthorityUtils.NO_AUTHORITIES;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	public AppUser getUser() {
		return user;
	}

	public String getUserRole() {
		return user.getRole();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
