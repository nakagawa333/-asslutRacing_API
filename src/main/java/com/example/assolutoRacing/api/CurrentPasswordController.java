package com.example.assolutoRacing.api;

import java.sql.SQLException;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.CurrentPasswordBean;
import com.example.assolutoRacing.Bean.UserRes;
import com.example.assolutoRacing.Dto.UpdatePasswordDto;
import com.example.assolutoRacing.Service.PasswordResetService;
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
 * パスワード最新化コントローラークラス
 */

@RestController
public class CurrentPasswordController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	TempUserService tempUserService;
	
	@Autowired
	PasswordResetService passwordResetService;
	
	@RequestMapping(path = "/password/current", method = RequestMethod.PUT)
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Boolean> updatePassword(@RequestBody(required = true) CurrentPasswordBean currentPasswordBean) throws Exception{
		//パスワードをハッシュ化
		String oldPassword = DigestUtils.sha256Hex(currentPasswordBean.getOldPassword());
		
		//新しいパスワードをハッシュ化
		String newPassword = DigestUtils.sha256Hex(currentPasswordBean.getNewPassword());
		
		//ユーザーid
		Integer userId = currentPasswordBean.getUserId();
		
		
		UserRes userRes = new UserRes();
		try {
			userRes = userService.selectbyUserId(userId);
		} catch(Exception e) {
			throw new Exception("");
		}
		
		if(userRes == null) {
			throw new Exception("");
		}
		
		int updateCount = 0;
		
		if(StringUtils.equals(oldPassword, userRes.getPassword())) {
			String mail = userRes.getMail();
			
			UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
			updatePasswordDto.setPassword(newPassword);
			updatePasswordDto.setMail(mail);
			
			try {
				//パスワードを更新する
				updateCount = userService.updatePassword(updatePasswordDto);
			} catch(Exception e) {
				throw new SQLException("パスワード更新に失敗しました");
			}
			
			if(updateCount == 0) {
				throw new SQLException("");
			}
			
		} else {
			throw new Exception("");
		}
		
		//メールの更新件数が1件の場合、true
		boolean updateSucessFlag = updateCount == 1 ? true : false;
		
		HttpHeaders headers = new HttpHeaders();
		
		ResponseEntity<Boolean> resEntity = new ResponseEntity<>(updateSucessFlag,headers,HttpStatus.CREATED); 
		return resEntity;
	}
}
