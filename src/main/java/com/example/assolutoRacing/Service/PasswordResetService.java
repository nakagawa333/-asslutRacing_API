package com.example.assolutoRacing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assolutoRacing.Dto.RegistPasswordResetDto;
import com.example.assolutoRacing.Mapper.PasswordResetMapper;

/**
 * 
 * @author nakagawa.so
 * パスワードリセットサービスクラス
 *
 */

@Service
public class PasswordResetService {
	@Autowired
	PasswordResetMapper passwordResetMapper;
	
	/**
	 * トークンを条件にメールを取得する。
	 * @param token トークン
	 * @return メール
	 */
	public String selectByToken(String token) {
		String mail = "";
		
		try {
			mail = passwordResetMapper.selectByToken(token);
		} catch(Exception e) {
			throw e;
		}
		
		return mail;
	}
	
	public Integer deleteInsert(RegistPasswordResetDto registPasswordResetDto) {
		Integer insertCount = 0;
		
		try {
			insertCount = passwordResetMapper.deleteInsert(registPasswordResetDto);
		} catch(Exception e) {
			throw e;
		}
		return insertCount;
	}
}
