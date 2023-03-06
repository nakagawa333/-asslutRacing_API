package com.example.assolutoRacing.Bean;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * パスワード更新Beanクラス
 * @author nakagawa.so
 *
 */
@Data
public class UpdatePasswordBean {
	@NotBlank
	//パスワード
	private String password;
	@NotBlank
	//トークン
	private String token;
}
