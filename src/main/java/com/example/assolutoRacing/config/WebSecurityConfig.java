package com.example.assolutoRacing.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.assolutoRacing.Service.CustomUserDetailsService;
import com.example.assolutoRacing.Service.JwtUtil;

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
		http.csrf().disable()
		.authorizeHttpRequests()
		.antMatchers("/select/notification","/login","/signup","/password/reset","/refresh/token").permitAll()
		.antMatchers("/select/**").permitAll()
		.antMatchers("/verify/**").permitAll()
		.anyRequest().authenticated()
		.and().cors()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilterBefore(new JwtRequestFilter(customUserDetailsService,jwtUtil),UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
