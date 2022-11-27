package com.example.assolutoRacing.Dto;

import lombok.Data;

/**
 * パスワード更新DTO.
 * @author nakagawa.so
 *
 */
@Data
public class UpdatePasswordDto {
	//パスワード
	private String password;
	//メール
	private String mail;
}
