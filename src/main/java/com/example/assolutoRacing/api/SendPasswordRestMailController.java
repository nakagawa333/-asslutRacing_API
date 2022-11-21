package com.example.assolutoRacing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.SelectUserBean;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Service.MailService;
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
 * サインアップコントローラークラス
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
	
	@RequestMapping(path = "/password/reset", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<Object> submitSendPasswordResetMailForm(@RequestBody(required = true) @Validated SendPasswordRestMailBean sendPasswordRestMailBean){
		int userCount = 0;
		
		SelectUserBean selectUserBean = new SelectUserBean();
		selectUserBean.setMail(sendPasswordRestMailBean.getMail());
		
		try {
			userCount = userService.select(selectUserBean);
		} catch(Exception e) {
			throw e;
		}
		
		if(userCount != 0) {
			
		}
		
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<Object> resEntity = new ResponseEntity<>(headers,HttpStatus.OK); 
		return resEntity;
	}
}
