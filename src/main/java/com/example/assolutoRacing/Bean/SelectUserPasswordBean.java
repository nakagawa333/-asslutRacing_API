package com.example.assolutoRacing.Bean;

import lombok.Data;

/**
 * パスワード情報検索Beanクラス
 * @author nakagawa.so
 *
 */
@Data
public class SelectUserPasswordBean {
	//ユーザーid
	private Integer userId;
	//パスワード
	private String password;
}
