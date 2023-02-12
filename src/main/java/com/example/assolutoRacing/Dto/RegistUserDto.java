package com.example.assolutoRacing.Dto;

import lombok.Data;

@Data
public class RegistUserDto {
	/** ユーザー名 **/
	private String userName;
	/** パスワード **/
	private String password;
	/** メール **/
	private String mail;
	/** パスワードの文字数 **/
	private Integer passwordLetters;
}
