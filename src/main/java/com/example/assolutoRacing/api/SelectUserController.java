package com.example.assolutoRacing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.SelectUserBean;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Service.UserService;

/**
 * 
 * @author nakagawa.so
 * ユーザー名によるユーザー情報検索コントローラークラス
 *
 */
@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class SelectUserController {
	
	@Autowired
	UserService userService;
		
	@RequestMapping(path = "/select/user", method = RequestMethod.POST)
	public ResponseEntity<Integer> selectUser(@RequestBody(required = true) SelectUserBean selectUserBean){
		int userCount = 0;
		
		try {
			userCount = userService.select(selectUserBean);
		} catch(Exception e) {
			throw e;
		}

		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<Integer> resEntity = new ResponseEntity<>(userCount,headers,HttpStatus.CREATED); 
		return resEntity;
	}
}


