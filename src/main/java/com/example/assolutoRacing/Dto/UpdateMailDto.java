package com.example.assolutoRacing.Dto;

import lombok.Data;

/**
 *　メール更新DTOクラス.
 * @author nakagawa.so
 *
 */
@Data
public class UpdateMailDto {
	//メール
	private String mail;
	//トークン
	private String token;
	//ユーザーid
	private Integer userId;
}
