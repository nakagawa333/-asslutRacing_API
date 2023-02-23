package com.example.assolutoRacing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assolutoRacing.Bean.SelectUpdateMailDto;
import com.example.assolutoRacing.Dto.RegistPasswordResetDto;
import com.example.assolutoRacing.Dto.UpdateMailDto;
import com.example.assolutoRacing.Mapper.MailUpdateMapper;
import com.example.assolutoRacing.Mapper.PasswordResetMapper;

/**
 * 
 * @author nakagawa.so
 * メール更新サービスクラス
 *
 */

@Service
public class MailUpdateService {
	@Autowired
	MailUpdateMapper mailUpdateMapper;
	
	/**
	 * メール更新テーブルにデータを挿入する
	 * @param updateMailDto
	 * @return 登録件数
	 */
	public Integer insert(UpdateMailDto updateMailDto) {
		int insertCount = 0;
		
		try {
			insertCount = mailUpdateMapper.insert(updateMailDto);
		} catch(Exception e) {
			throw e;
		}
		return insertCount;
	}
	
	/**
	 * トークンからメール更新情報を取得する
	 * @param token トークン
	 * @return
	 */
	public SelectUpdateMailDto selectByToken(String token) {
		SelectUpdateMailDto selectUpdateMailDto = new SelectUpdateMailDto();
		try {
			selectUpdateMailDto = mailUpdateMapper.selectByToken(token);
		} catch(Exception e) {
			throw e;
		}
		
		return selectUpdateMailDto;
	}
}
