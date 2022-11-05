package com.example.assolutoRacing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.StringUtils;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Service.UserService;

/**
 * 
 * @author nakagawa.so
 * ユーザー登録情報コントローラークラス
 *
 */
@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class SelectUserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(path = "/select/user/{userName}", method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<Integer> selectUser(@PathVariable String userName){
		int userCount = 0;
		
		if(StringUtils.isEmpty(userName)) {
			throw new NullPointerException();
		}
		
		try {
			userCount = userService.selectUserCountByUserName(userName);
		} catch(Exception e) {
			throw e;
		}
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<Integer> resEntity = new ResponseEntity<>(userCount,headers,HttpStatus.CREATED); 
		return resEntity;
	}
}
