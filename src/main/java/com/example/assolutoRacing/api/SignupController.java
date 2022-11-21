package com.example.assolutoRacing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

/**
 * 
 * @author nakagawa.so
 * サインアップコントローラークラス
 *
 */
@CrossOrigin(origins = Constants.ORIGINS)
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
	public ResponseEntity<Object> signup(@RequestBody(required = true) @Validated TempUserBean tempUserBean,UriComponentsBuilder builder){
		
		//トークン
		String token = "";
		try {
			token = tokenService.createToken(tempUserBean.getMail());
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		TempRegistUserDto tempRegistUserDto = new TempRegistUserDto();
		tempRegistUserDto.setUserName(tempUserBean.getUserName());
		tempRegistUserDto.setPassword(tempUserBean.getPassword());
		tempRegistUserDto.setMail(tempUserBean.getMail());
		tempRegistUserDto.setAuthToken(token);
		
		int insertCount = 0;
		try {
			//仮ユーザーを登録する
			insertCount = tempUserService.insert(tempRegistUserDto);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		String url = tempUserBean.getRequestUrl() + "/verify?token=" + token;
		
		String textBody = mailService.createMailAuthTextbody(url);
		try {
			//メール認証
			mailService.send(tempUserBean.getMail(),"assolutoracingses@gmail.com", "アカウント認証をお願い致します。", textBody);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<Object> resEntity = new ResponseEntity<>(headers,HttpStatus.OK); 
		return resEntity;
	}
}
