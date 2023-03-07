package com.example.assolutoRacing.Bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * パスワード最新化Beanクラス
 * @author nakagawa.so
 *
 */
@Data
public class CurrentPasswordBean {
	
	@NotBlank
	//前のスワード
	private String oldPassword;
	
	@NotBlank
	private String newPassword;
	
	@NotNull
	//トークン
	private Integer userId;
}
