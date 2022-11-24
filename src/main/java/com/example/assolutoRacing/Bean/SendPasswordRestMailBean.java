package com.example.assolutoRacing.Bean;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SendPasswordRestMailBean {
	@NotBlank
	private String mail;
	@NotBlank
	private String requestUrl;
}
