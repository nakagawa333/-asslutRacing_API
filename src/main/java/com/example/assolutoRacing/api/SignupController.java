package com.example.assolutoRacing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.assolutoRacing.Bean.TempAccoutBean;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Service.MailService;
import com.example.assolutoRacing.Service.TokenService;
import com.example.assolutoRacing.Service.UserService;
import org.springframework.web.util.UriComponentsBuilder;

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
	
	@RequestMapping(path = "/signup", method = RequestMethod.POST)
	public void signup(@RequestBody(required = true) @Validated TempAccoutBean tempAccoutBean,UriComponentsBuilder builder){
		
		//トークン
		String token = "";
		try {
			token = tokenService.createToken(tempAccoutBean.getMail());
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		String url = builder.build().toString() + "/token?=" + token;
		
		String textBody = mailService.createMailAuthTextbody(url);
		try {
			//メール認証
			mailService.send(tempAccoutBean.getMail(),"assolutoracingses@gmail.com", "アカウント認証をお願い致します。", textBody);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
		
//		HttpHeaders headers = new HttpHeaders();
//		ResponseEntity<AuthUserRes> resEntity = new ResponseEntity<>(user,headers,HttpStatus.CREATED); 
//		return resEntity;
	}
}
