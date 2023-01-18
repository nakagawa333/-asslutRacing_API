package com.example.assolutoRacing.api;

import org.apache.commons.lang3.StringUtils;
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
import com.example.assolutoRacing.Bean.UserNameBean;
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
		
	@RequestMapping(path = "/select/user", method = RequestMethod.GET)
	public ResponseEntity<Integer> selectUser(@RequestParam(value = "userName" String userName){
		int userCount = 0;
		String userName = userNameBean.getUserName();
		
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
}


