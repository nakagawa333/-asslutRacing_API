package com.example.assolutoRacing.Bean;

import lombok.Data;

@Data
public class UserRes {
	//ユーザー名
	private String userName;
	//パスワードの文字数
	private Integer passwordLetters;
	//メール
	private String mail;
}
