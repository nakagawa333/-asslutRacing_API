package com.example.assolutoRacing.Bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class SendPasswordRestMailBean {
	@NotBlank
	@Pattern(regexp="^([a-zA-Z0-9])+([a-zA-Z0-9\\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\\._-]+)+$")
	private String mail;
	
	@NotBlank
	private String requestUrl;
}
