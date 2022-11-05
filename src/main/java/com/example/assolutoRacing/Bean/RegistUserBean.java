package com.example.assolutoRacing.Bean;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistUserBean {
	/** ユーザー名 **/
	@NotBlank
	private String userName;
	@NotBlank
	private String password;
}
