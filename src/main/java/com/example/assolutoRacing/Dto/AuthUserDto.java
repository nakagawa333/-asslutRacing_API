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
	/** パスワード **/
	private String password;
}


