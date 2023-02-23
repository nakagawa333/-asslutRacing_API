package com.example.assolutoRacing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.SelectUpdateMailDto;
import com.example.assolutoRacing.Service.MailUpdateService;
import com.example.assolutoRacing.Service.UserService;

import lombok.Data;

/**
 * メール更新コントローラークラス
 * @author nakagawa.so
 * @param <T>
 **/

@RestController
public class UpdateMailController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MailUpdateService mailUpdateService;
	
	/**
	 * メールを更新する
	 * @param <T>
	 * @param token トークン
	 * @throws Exception
	 */
	@RequestMapping(path = "/mail/update", method = RequestMethod.PUT)
	@Transactional(rollbackFor = Exception.class)
	public <T> ResponseEntity<T> updateMail(@RequestBody(required = true) String token) throws Exception{
		SelectUpdateMailDto selectUpdateMailDto = new SelectUpdateMailDto();
		
		//トークンからメール更新情報を取得する
		try {
			selectUpdateMailDto = mailUpdateService.selectByToken(token);
		} catch(Exception e) {
			throw new Exception("メール更新情報の取得に失敗しました");
		}
		
		if(selectUpdateMailDto == null) {
			throw new Exception("メール更新の有効期限が切れています。");
		}
		
		int userCount = 0;
		
		//メールからユーザーを取得する
		try {
			userCount = userService.selectByMail(selectUpdateMailDto.getMail());
		} catch(Exception e) {
			throw new Exception("ユーザー取得失敗しました");
		}
		
		if(1 <= userCount) {
			throw new Exception("既にメールは使用されています。");
		}
		
		int updateCount = 0;
		
		//ユーザー情報のメールアドレスを更新する
		try {
			updateCount = userService.updateMail(selectUpdateMailDto);
		} catch(Exception e) {
			throw new Exception("メールの更新に失敗しました");
		}
		
		if(updateCount != 1) {
			throw new Exception("メールの更新に失敗しました");
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Connection", "Keep-Alive");
		
		ResponseEntity<T> resEntity = new ResponseEntity<>(headers,HttpStatus.OK); 
		return resEntity;
	}
}
