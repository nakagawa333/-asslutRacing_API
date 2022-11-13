package com.example.assolutoRacing.Bean;

import lombok.Data;
import javax.validation.constraints.NotBlank;


@Data
public class AuthUserBean {
	//ユーザー名
	private String userName;
	//メール
	private String mail;
	@NotBlank
	//パスワード
	private String password;
}
