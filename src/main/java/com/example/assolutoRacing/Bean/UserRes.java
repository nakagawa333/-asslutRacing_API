package com.example.assolutoRacing.Bean;

import lombok.Data;

@Data
public class UserRes {
	//ユーザー名
	private String userName;
	//メール
	private String mail;
	//ユーザーid
	private Integer userId;
	//パスワード
	private String password;
}
