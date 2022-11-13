package com.example.assolutoRacing.Dto;

import lombok.Data;


/**
 * @author nakagawa.so
 * ユーザー認証Dtoクラス
 */
@Data
public class AuthUserDto {
	/** ユーザー名 **/
	private String userName;
	/** メール **/
	private String mail;
	/** パスワード **/
	private String password;
}


