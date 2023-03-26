package com.example.assolutoRacing.config;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import com.amazonaws.HttpMethod;
import com.example.assolutoRacing.Service.CustomUserDetailsService;
import com.example.assolutoRacing.Service.JwtUtil;
import com.example.assolutoRacing.api.LoginController;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {	
	private String secretKey = "secret";
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Bean
	SecurityFilterChain springSecurity(HttpSecurity http) throws Exception {
		http.cors().disable()
		.csrf().disable()
		.authorizeHttpRequests()
		.antMatchers("/select/notification","/login","/settings/account").permitAll()
		.anyRequest().authenticated()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilterBefore(new JwtRequestFilter(customUserDetailsService,jwtUtil),UsernamePasswordAuthenticationFilter.class);
	    return http.build();
	}
}
