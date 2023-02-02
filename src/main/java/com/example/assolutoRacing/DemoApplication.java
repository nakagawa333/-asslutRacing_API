package com.example.assolutoRacing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.assolutoRacing.Constants.Constants;

@SpringBootApplication
@ComponentScan
@EnableScheduling
public class DemoApplication implements WebMvcConfigurer{
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
	    .allowedOrigins(Constants.ORIGINS)
        .allowedMethods("GET", "POST", "PUT", "DELETE");
	}
}
