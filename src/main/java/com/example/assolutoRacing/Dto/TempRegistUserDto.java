package com.example.assolutoRacing.Dto;

import lombok.Data;

@Data
public class TempRegistUserDto {
	/** ユーザー名 **/
	private String userName;
	/** メール **/
	private String mail;
	/*認証トークン*/
	private String authToken;
	/** パスワード(ハッシュ化後) **/
	private String password;
}
