package com.example.assolutoRacing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.simpleemail.model.AccountSendingPausedException;
import com.amazonaws.services.simpleemail.model.ConfigurationSetDoesNotExistException;
import com.amazonaws.services.simpleemail.model.ConfigurationSetSendingPausedException;
import com.amazonaws.services.simpleemail.model.MailFromDomainNotVerifiedException;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.example.assolutoRacing.Bean.TempUserBean;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Dto.TempRegistUserDto;
import com.example.assolutoRacing.Service.MailService;
import com.example.assolutoRacing.Service.TempUserService;
import com.example.assolutoRacing.Service.TokenService;
import com.example.assolutoRacing.Service.UserService;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * @author nakagawa.so
 * サインアップコントローラークラス
 *
 */

@RestController
public class SignupController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	TempUserService tempUserService;
	
	@RequestMapping(path = "/signup", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<Boolean> signup(@RequestBody(required = true) @Validated TempUserBean tempUserBean) throws Exception{
		
		//トークン
		String token = "";
		try {
			token = tokenService.createToken(tempUserBean.getMail());
		} catch(Exception e) {
			throw new Exception("トークン生成に失敗しました");
		}
		
		TempRegistUserDto tempRegistUserDto = new TempRegistUserDto();
		tempRegistUserDto.setUserName(tempUserBean.getUserName());
		tempRegistUserDto.setPassword(DigestUtils.sha256Hex(tempUserBean.getPassword()));
		tempRegistUserDto.setMail(tempUserBean.getMail());
		tempRegistUserDto.setAuthToken(token);
		
		int insertCount = 0;
		try {
			//仮ユーザーを登録する
			insertCount = tempUserService.insert(tempRegistUserDto);
		} catch(Exception e) {
			throw new SQLException("仮ユーザーの登録に失敗しました");
		}
		
		if(insertCount == 0) {
			throw new SQLException("仮ユーザーの登録に失敗しました");
		}
		
		String url = tempUserBean.getRequestUrl() + "/verify?token=" + token;
		
		String textBody = mailService.createMailAuthTextbody(url);
		try {
			//メール認証
			mailService.send(tempUserBean.getMail(),"assolutoracingses@gmail.com", "アカウント認証をお願い致します。", textBody);
		} catch(MessageRejectedException e) { 
			throw new Exception("メッセージ送信に失敗しました");
		} catch(MailFromDomainNotVerifiedException e) {
			throw new Exception("MXレコードが読み込まれませんでした");
		} catch(ConfigurationSetDoesNotExistException e) {
			throw new Exception("コンフィグレーションセットが存在しませんでした");
		} catch(ConfigurationSetSendingPausedException e) {
			throw new Exception("構成セットに対して電子メール送信が無効です");
		} catch(AccountSendingPausedException e) {
			throw new Exception("Amazon SESアカウント全体で電子メール送信が無効になっています");
		} catch(Exception e) {
			throw new Exception("原因不明のエラーが発生しました");
		}
		
		boolean insertSucessFlag;
		insertSucessFlag = insertCount == 1 ? true : false;
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<Boolean> resEntity = new ResponseEntity<>(insertSucessFlag,headers,HttpStatus.OK); 
		return resEntity;
	}
}
