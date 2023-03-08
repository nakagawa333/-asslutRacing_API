package com.example.assolutoRacing.api;

import java.sql.SQLException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Dto.RegistUserDto;
import com.example.assolutoRacing.Dto.SelectTempUserDto;
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
 * トークン確認コントローラークラス
 */

@RestController
public class VerifyTokenController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	TempUserService tempUserService;
	
	@RequestMapping(path = "/verify/token", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Boolean> verifyToken(@RequestBody(required = true) String token) throws Exception{
		SelectTempUserDto tempUser = new SelectTempUserDto();
		
		try {
			//トークンを条件に仮ユーザー情報を取得する.
			tempUser = tempUserService.selectByToken(token);
		} catch(Exception e) {
			throw new Exception("仮ユーザー情報の取得に失敗しました");
		}
		
		if(tempUser == null) {
			throw new SQLException("仮ユーザーが存在しません");
		}
		
		RegistUserDto registUser = new RegistUserDto();
		registUser.setMail(tempUser.getMail());
		registUser.setPassword(tempUser.getPassword());
		registUser.setUserName(tempUser.getUserName());
		
		int insertUserCount = 0;
		try {
			//ユーザーを登録する
			insertUserCount = userService.insert(registUser);
		} catch(Exception e) {
			throw new SQLException("ユーザー登録に失敗しました");
		}
		
		if(insertUserCount == 0) {
			throw new SQLException("ユーザー登録に失敗しました");
		}
		
		boolean insertUserSucessFlag = insertUserCount == 1 ? true : false;
		
		HttpHeaders headers = new HttpHeaders();
		
		ResponseEntity<Boolean> resEntity = new ResponseEntity<>(insertUserSucessFlag,headers,HttpStatus.CREATED); 
		return resEntity;
	}
}
