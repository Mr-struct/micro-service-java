package com.microservice.auth.service.security.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.microservice.auth.service.entities.JwtUserDetails;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(HttpHeaders.AUTHORIZATION);
		if (header == null || !header.startsWith("Bearer ")) {
			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}


	/**
	 * Récupere le token puis retourne la bonne Authentication
	 * @param request
	 * @return
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		if (token != null) {
			token = token.replace("Bearer ", "");
			final String username = jwtTokenUtil.getUsernameFromToken(token);
			if (username != null) {
				JwtUserDetails userDetail = this.jwtUserDetailsService.loadUserByUsername(username);
				if (jwtTokenUtil.validateToken(token, userDetail)) {
					return new UsernamePasswordAuthenticationToken(userDetail, userDetail.getPassword(), userDetail.getAuthorities());
				}
			}
		}
		return null;
	}
}
