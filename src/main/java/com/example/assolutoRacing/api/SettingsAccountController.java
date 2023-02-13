package com.example.assolutoRacing.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.UserRes;
import com.example.assolutoRacing.Service.UserService;

/**
 * 
 * @author nakagawa.so
 * アカウント情報取得コントローラークラス
 */

@RestController
public class SettingsAccountController{
	@Autowired
	UserService userService;
	
	@RequestMapping(path = "/settings/account", method = RequestMethod.GET)
	public ResponseEntity<UserRes> select(@RequestParam(value = "userId") Integer userId) throws SQLException {

		UserRes userRes = new UserRes();
			
		try {
			userRes = userService.selectbyUserId(userId);
		} catch(Exception e) {
			throw new SQLException("ユーザー情報の取得に失敗しました");
		}
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<UserRes> resEntity = new ResponseEntity<>(userRes,headers,HttpStatus.OK); 
		return resEntity;
	}
}