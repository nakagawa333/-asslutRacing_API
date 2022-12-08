package com.example.assolutoRacing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.codec.digest.DigestUtils;

import com.example.assolutoRacing.Bean.RegistUserBean;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Dto.RegistUserDto;
import com.example.assolutoRacing.Service.UserService;

/**
 * 
 * @author nakagawa.so
 * ユーザー登録情報コントローラークラス
 *
 */
@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class AddUserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(path = "/add/user", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<Integer> addUser(@RequestBody(required = true) @Validated RegistUserBean registUserBean){
		int insertCount = 0;
		
		String password = DigestUtils.sha256Hex(registUserBean.getPassword());
		RegistUserDto registUser = new RegistUserDto();
		registUser.setUserName(registUserBean.getUserName());
		registUser.setPassword(password);
		
		try {
			insertCount = userService.insert(registUser);
		} catch(Exception e) {
			throw e;
		}
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<Integer> resEntity = new ResponseEntity<>(insertCount,headers,HttpStatus.CREATED); 
		return resEntity;
	}
}
