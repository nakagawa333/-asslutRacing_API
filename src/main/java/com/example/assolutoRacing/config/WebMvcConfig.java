package com.example.assolutoRacing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.assolutoRacing.Constants.Constants;

/**
 * すべてのリクエストに対して CORS を設定
 * @author nakagawa.so
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
	    .allowedOrigins(Constants.ORIGINS)
        .allowedMethods("GET", "POST", "PUT", "DELETE");
	}
}
