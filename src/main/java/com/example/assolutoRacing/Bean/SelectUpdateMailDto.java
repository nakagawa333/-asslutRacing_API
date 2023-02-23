package com.example.assolutoRacing.Bean;

import lombok.Data;

/**
 * メール更新取得Dto
 * @author nakagawa.so
 *
 */
@Data
public class SelectUpdateMailDto {
	//メール
	private String mail;
	
	//トークン
	private String token;
	
	//ユーザーid
	private Integer userId;
}
