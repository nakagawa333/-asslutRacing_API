package com.example.assolutoRacing.Bean;

import lombok.Data;

/**
 * ログインユーザーレスポンス情報
 * @author Nakagawa.so
 *
 */
@Data
public class LoginUserRes {
	//ユーザー名
	private Integer userId;
	
	//ユーザー名
	private String userName;
	
	//パスワード
	private String password;
	
	//アクセストークン
	private String acessToken;
	
	//リフレッシュトークン
	private String refreshToken;
}
