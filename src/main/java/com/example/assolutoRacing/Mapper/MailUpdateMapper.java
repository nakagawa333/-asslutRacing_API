package com.example.assolutoRacing.Mapper;


import org.apache.ibatis.annotations.Mapper;

import com.example.assolutoRacing.Dto.UpdateMailDto;
@Mapper
public interface MailUpdateMapper {
	
//	/**
//	 * トークンを条件にメールを取得する。
//	 * @param token トークン
//	 * @return メール
//	 */
//	String selectByToken(String token);
	
	/**
	 * メール更新テーブルにデータを挿入する
	 * @param UpdateMailDto
	 * @return 登録件数
	 */
	Integer insert(UpdateMailDto updateMailDto);
}
