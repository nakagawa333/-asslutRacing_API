package com.example.assolutoRacing.Mapper;


import org.apache.ibatis.annotations.Mapper;

import com.example.assolutoRacing.Dto.RegistPasswordResetDto;
@Mapper
public interface PasswordResetMapper {
	
	/**
	 * トークンを条件にメールを取得する。
	 * @param token トークン
	 * @return メール
	 */
	String selectByToken(String token);
	
	/**
	 * パスワードリセットに値を挿入する
	 * @param registPasswordResetDto
	 * @return 登録件数
	 */
	Integer deleteInsert(RegistPasswordResetDto registPasswordResetDto);
}
