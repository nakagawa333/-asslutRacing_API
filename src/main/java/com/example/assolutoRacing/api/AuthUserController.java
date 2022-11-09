package com.example.assolutoRacing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.codec.digest.DigestUtils;

import com.example.assolutoRacing.Bean.AuthUserBean;
import com.example.assolutoRacing.Bean.AuthUserRes;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Dto.AuthUserDto;
import com.example.assolutoRacing.Service.UserService;

/**
 * 
 * @author nakagawa.so
 * ユーザー登録情報コントローラークラス
 *
 */
@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class AuthUserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(path = "/auth/user", method = RequestMethod.POST)
	public ResponseEntity<AuthUserRes> authUser(@RequestBody(required = true) @Validated AuthUserBean authUserBean){
		AuthUserRes user = new AuthUserRes();
		
		String password = DigestUtils.sha1Hex(authUserBean.getPassword());
		AuthUserDto authUser = new AuthUserDto();
		authUser.setPassword(password);
		authUser.setUserName(authUserBean.getUserName());
		
		try {
			user = userService.auth(authUser);
		} catch(Exception e) {
			throw e;
		}
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<AuthUserRes> resEntity = new ResponseEntity<>(user,headers,HttpStatus.CREATED); 
		return resEntity;
	}
}
