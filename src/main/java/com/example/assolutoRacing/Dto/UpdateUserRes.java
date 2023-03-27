package com.example.assolutoRacing.Dto;

import lombok.Data;

@Data
public class UpdateUserRes {
	//ユーザー更新成功可否フラグ
	public Boolean userUpdateSucessFlag;
	
	//アクセストークン
	public String acessToken;
}
