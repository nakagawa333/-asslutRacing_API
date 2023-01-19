package com.example.assolutoRacing.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.RegistUserBean;
import com.example.assolutoRacing.Bean.SelectUserBean;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Service.UserService;

/**
 * 
 * @author nakagawa.so
 * ユーザー情報検索コントローラークラス
 *
 */
@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class SelectUserController {
	
	@Autowired
	UserService userService;
		
	@RequestMapping(path = "/select/user/username", method = RequestMethod.POST)
	public ResponseEntity<Integer> selectUserByUserName(@RequestBody(required = true) @Validated String userName) {
		int userCount = 0;
		
		if(StringUtils.isNoneEmpty(userName)) {
			try {
				userCount = userService.selectByUserName(userName);
			} catch(Exception e) {
				throw e;
			}
		}
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<Integer> resEntity = new ResponseEntity<>(userCount,headers,HttpStatus.OK); 
		return resEntity;
	}
	
	@RequestMapping(path = "/select/user/mail", method = RequestMethod.POST)
	public ResponseEntity<Integer> selectUserByMail(@RequestBody(required = true) @Validated String mail) {
		int userCount = 0;
		
		if(StringUtils.isNoneEmpty(mail)) {
			try {
				userCount = userService.selectByMail(mail);
			} catch(Exception e) {
				throw e;
			}
		}
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<Integer> resEntity = new ResponseEntity<>(userCount,headers,HttpStatus.OK); 
		return resEntity;
	}
}


