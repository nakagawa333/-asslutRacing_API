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
import org.apache.commons.lang3.StringUtils;


/**
 * 
 * @author nakagawa.so
 * ログインコントローラークラス
 *
 */
@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public ResponseEntity<AuthUserRes> login(@RequestBody(required = true) @Validated AuthUserBean authUserBean) throws Exception{
		AuthUserRes user = new AuthUserRes();
		
		String userName = authUserBean.getUserName();
		String mail = authUserBean.getMail();
		
		if(StringUtils.isEmpty(mail) && StringUtils.isEmpty(userName)) {
			throw new Exception("ユーザー名とパスワードの値がありません。");
		}
		//パスワードをハッシュ化
		String password = DigestUtils.sha256Hex(authUserBean.getPassword());
		AuthUserDto authUser = new AuthUserDto();
		authUser.setPassword(password);
		
		if(StringUtils.isNoneEmpty(userName)) {
			authUser.setUserName(userName);
		} else if(StringUtils.isNoneEmpty(mail)) {
			authUser.setMail(mail);
		}
		
		authUser.setUserName(authUserBean.getUserName());
		
		try {
			user = userService.auth(authUser);
		} catch(Exception e) {
			throw e;
		}
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<AuthUserRes> resEntity = new ResponseEntity<>(user,headers,HttpStatus.OK); 
		return resEntity;
	}
}
