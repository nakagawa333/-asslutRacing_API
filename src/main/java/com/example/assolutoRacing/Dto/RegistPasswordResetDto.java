package com.example.assolutoRacing.Dto;

import lombok.Data;

/**
 * パスワードリセット登録用
 * @author nakagawa.so
 */
@Data
public class RegistPasswordResetDto {
	//メール
	private String mail;
	//トークン
	private String token;
}
