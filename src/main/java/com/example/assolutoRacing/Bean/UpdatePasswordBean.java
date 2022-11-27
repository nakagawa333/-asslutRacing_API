package com.example.assolutoRacing.Bean;

import lombok.Data;

/**
 * パスワード更新
 * @author nakagawa.so
 *
 */
@Data
public class UpdatePasswordBean {
	//パスワード
	private String password;
	//トークン
	private String token;
}
