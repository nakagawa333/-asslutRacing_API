package com.example.assolutoRacing.Dto;

import lombok.Data;

@Data
public class UpdateUserRes {
	//ユーザー更新成功可否フラグ
	private Boolean userUpdateSucessFlag;
	
	//アクセストークン
	private String acessToken;
	
	//リフレッシュトークン
	private String refreshToken;
}
