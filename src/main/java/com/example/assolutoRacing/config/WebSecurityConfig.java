package com.example.assolutoRacing.config;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import com.amazonaws.HttpMethod;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {	
	private String secretKey = "secret";
	
	@Bean
	SecurityFilterChain springSecurity(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(author -> author
				.antMatchers("/select/notification").permitAll()
				.antMatchers("/login").permitAll()
				.anyRequest().authenticated()
		);
		
		http.csrf()
		.ignoringAntMatchers("/login");
		
		http.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		.formLogin().loginProcessingUrl("/login").permitAll()
//		.usernameParameter("email")
//		.usernameParameter("password")
//		.and()
//		.logout()
//		.logoutUrl("/logout")
//		http.authorizeHttpRequests(authz -> authz		
//		)
//		.mvcMatchers("/login").permitAll()
//		.anyRequest().authenticated();
	    return http.build();
	}
	
	
//	public void build() {
//		String secretKey = "secret";
//		try {
//			Algorithm algorithm = Algorithm.HMAC256(secretKey);
//			String token = JWT.create()
//					         .sign(algorithm);
//		} catch (Exception e) {
//			throw e;
//	    }
//	}
}
