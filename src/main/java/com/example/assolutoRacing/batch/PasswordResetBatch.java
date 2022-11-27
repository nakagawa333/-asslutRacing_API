package com.example.assolutoRacing.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.assolutoRacing.Mapper.PasswordResetMapper;

@Component
public class PasswordResetBatch {
	@Autowired
	PasswordResetMapper passwordResetMapper;
	
	/**
	 * 1日毎に作成日時が1日以降のパスワードリセットデータを削除する。
	 */
	@Scheduled(fixedDelay = 5000)
	public void deleteOfAfterOneDay() {
		
	}
}
