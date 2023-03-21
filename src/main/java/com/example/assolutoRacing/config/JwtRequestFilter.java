package com.example.assolutoRacing.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.assolutoRacing.Service.CustomUserDetailsService;

/**
 * 
 * @author nakagawa.so
 *
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse, 
			FilterChain filterChain) throws ServletException, IOException{
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String username = "";
		String jwtToken = "";
		
		if(StringUtils.isNoneBlank(authorizationHeader) && authorizationHeader.startsWith("Basic")) {
			jwtToken = authorizationHeader.substring(7);
			username = "hello";
		}
		if(StringUtils.isNoneBlank(username) && 
				SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
		}
		
		
		
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
	
}
