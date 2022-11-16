package com.example.assolutoRacing.Bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.example.assolutoRacing.Constants.Constants;

import lombok.Data;

@Data
public class TempAccoutBean {
	//ユーザー名
	@NotBlank
	private String userName;
	//メール
//	@Pattern(regexp = Constants.REGEX.MAIL)
	private String mail;
	//パスワード
	@NotBlank
	private String password;
}
