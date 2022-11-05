package com.example.assolutoRacing.Bean;

import lombok.Data;
import lombok.NonNull;

@Data
public class AuthUserBean {
	//ユーザー名
	@NonNull
	private String userName;
	//パスワード
	@NonNull
	private String password;
}
