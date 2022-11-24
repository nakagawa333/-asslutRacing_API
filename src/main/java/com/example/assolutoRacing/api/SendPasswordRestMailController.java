package com.example.assolutoRacing.api;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.SelectUserBean;
import com.example.assolutoRacing.Bean.SendPasswordRestMailBean;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Dto.RegistPasswordResetDto;
import com.example.assolutoRacing.Service.MailService;
import com.example.assolutoRacing.Service.PasswordResetService;
import com.example.assolutoRacing.Service.TempUserService;
import com.example.assolutoRacing.Service.TokenService;
import com.example.assolutoRacing.Service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author nakagawa.so
 * パスワードリセットメール送信コントローラークラス
 *
 */
@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class SendPasswordRestMailController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	TempUserService tempUserService;
	
	@Autowired
	PasswordResetService passwordResetService;
	
	@RequestMapping(path = "/password/reset", method = RequestMethod.POST)
	@Transactional
	public <T> ResponseEntity<T> submitSendPasswordResetMailForm(@RequestBody(required = true) @Validated SendPasswordRestMailBean sendPasswordRestMailBean) throws Exception{
		int userCount = 0;
		
		//メール
		String mail = sendPasswordRestMailBean.getMail();
		//リクエストURL
		String requestUrl = sendPasswordRestMailBean.getRequestUrl();
		
		SelectUserBean selectUserBean = new SelectUserBean();
		selectUserBean.setMail(mail);
		
		try {
			userCount = userService.select(selectUserBean);
		} catch(Exception e) {
			throw e;
		}
		
		//パスワードリセットテーブルに登録する
		if(userCount != 0) {
			//トークン
			String token = "";
			try {
				//メールを条件にトークンを生成する。
				token = tokenService.createToken(mail);
			} catch(Exception e) {
				throw new Exception("トークンの生成に失敗しました");
			}
			//遷移先のURL
			String url = requestUrl + "/verify/mail?token=" + token;
			//メール本文
			String textBody = mailService.createMailAuthTextbody(url);
			
			RegistPasswordResetDto registPasswordResetDto = new RegistPasswordResetDto();
			registPasswordResetDto.setMail(mail);
			registPasswordResetDto.setToken(token);
			
			int insertCount = 0;
			
			try {
				//パスワードリセットに値を挿入する
				insertCount = passwordResetService.deleteInsert(registPasswordResetDto);
			} catch(Exception e) {
				throw new SQLException("パスワードリセットへの値挿入が失敗しました");
			}
			
			if(insertCount == 0) {
				throw new SQLException("パスワードリセットへの値挿入が失敗しました");
			}
			
			try {
				//メール送信
				mailService.send(mail,"assolutoracingses@gmail.com", "パスワードをリセットします。", textBody);
			} catch(Exception e) {
				e.printStackTrace();
				throw e;				
			}
		}
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<T> resEntity = new ResponseEntity<>(headers,HttpStatus.OK); 
		return resEntity;
	}
}
