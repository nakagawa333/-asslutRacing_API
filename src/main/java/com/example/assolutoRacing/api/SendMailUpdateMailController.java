package com.example.assolutoRacing.api;

import java.nio.charset.Charset;
import java.sql.SQLException;

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
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.example.assolutoRacing.Bean.SelectUserBean;
import com.example.assolutoRacing.Bean.SendMailUpdateMailBean;
import com.example.assolutoRacing.Bean.SendMailUpdateMailRes;
import com.example.assolutoRacing.Bean.SendPasswordRestMailBean;
import com.example.assolutoRacing.Bean.UserRes;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Dto.RegistPasswordResetDto;
import com.example.assolutoRacing.Dto.UpdateMailDto;
import com.example.assolutoRacing.Service.MailService;
import com.example.assolutoRacing.Service.MailUpdateService;
import com.example.assolutoRacing.Service.PasswordResetService;
import com.example.assolutoRacing.Service.TempUserService;
import com.example.assolutoRacing.Service.TokenService;
import com.example.assolutoRacing.Service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author nakagawa.so
 * メール変更用メール送信コントローラークラス
 *
 */

@RestController
public class SendMailUpdateMailController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	TempUserService tempUserService;
	
	@Autowired
	MailUpdateService mailUpdateService;
	
	@RequestMapping(path = "/send/mail/update/mail", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<SendMailUpdateMailRes> sendMailUpdateMail(@RequestBody(required = true) @Validated SendMailUpdateMailBean sendMailUpdateMailBean) throws Exception{
		int userCount = 0;
		
		//メール
		String mail = sendMailUpdateMailBean.getMail();
		//リクエストURL
		String requestUrl = sendMailUpdateMailBean.getRequestUrl();
		//ユーザーid
		int userId = sendMailUpdateMailBean.getUserId();
		
		//ユーザーidからユーザーを取得
		UserRes user = new UserRes();
		try {
			user = userService.selectbyUserId(userId);
		} catch(Exception e) {
			throw new Exception("ユーザー取得に失敗しました");
		}
		//ユーザー
		if(user == null) {
			throw new Exception("不正なアクセスです。ログインしなおしてください。");
		}
		
		try {
			userCount = userService.selectByMail(mail);
		} catch(Exception e) {
			throw new Exception("ユーザー取得に失敗しました");
		}
		
		if(1 <= userCount) {
			throw new Exception("入力されたメールアドレスは既に使用されています。");
		}
		
		String token = "";
		try {
			//メールを条件にトークンを生成する。
			token = tokenService.createToken(mail);
		} catch(Exception e) {
			throw new Exception("トークンの生成に失敗しました");
		}
		
		//遷移先のURL
		String url = requestUrl + "/update/mail?token=" + token;
		//メール本文
		String textBody = mailService.createMailAuthTextbody(url);
		
		UpdateMailDto updateMailDto = new UpdateMailDto();
		updateMailDto.setMail(mail);
		updateMailDto.setToken(token);
		updateMailDto.setUserId(userId);
		
		int insertCount = 0;
		
		try {
			insertCount = mailUpdateService.insert(updateMailDto);
		} catch(Exception e) {
			throw new Exception("メール更新テーブルへのデータ挿入に失敗しました");
		}
		
		//登録件数が1件でない場合
		if(insertCount != 1) {
			throw new Exception("メール更新テーブルへのデータ挿入時に異常が発生しました");
		}
		
		try{
			//メール送信
			mailService.send(mail,"assolutoracingses@gmail.com", "パスワードをリセットします。", textBody);
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
		
		SendMailUpdateMailRes sendMailUpdateMailRes = new SendMailUpdateMailRes();
		sendMailUpdateMailRes.setMail(mail);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Connection", "Keep-Alive");
		ResponseEntity<SendMailUpdateMailRes> resEntity = new ResponseEntity<>(sendMailUpdateMailRes,headers,HttpStatus.OK); 
		return resEntity;
	}
}
