package com.example.assolutoRacing.api;

import java.sql.SQLException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.UpdatePasswordBean;
import com.example.assolutoRacing.Constants.Constants;
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
 * パスワード更新コントローラークラス
 */
@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class UpdatePasswordController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	TempUserService tempUserService;
	
	@Autowired
	PasswordResetService passwordResetService;
	
	@RequestMapping(path = "/password/update", method = RequestMethod.PUT)
	@Transactional
	public ResponseEntity<Boolean> updatePassword(@RequestBody(required = true) UpdatePasswordBean updatePasswordBean) throws Exception{
		//パスワードをハッシュ化
		String password = DigestUtils.sha256Hex(updatePasswordBean.getPassword());
		//メール
		String mail = "";
		
		try {
			//トークンを条件にメールを取得する。
			mail = passwordResetService.selectByToken(updatePasswordBean.getToken());
		} catch(Exception e) {
			throw new SQLException("メールの取得に失敗しました。");
		}
		
		UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
		updatePasswordDto.setMail(mail);
		updatePasswordDto.setPassword(password);
		
		int updateCount = 0;
		try {
			//パスワードを更新する
			updateCount = userService.updatePassword(updatePasswordDto);
		} catch(Exception e) {
			throw new SQLException("パスワード更新に失敗しました");
		}
		
		if(updateCount == 0) {
			throw new SQLException("該当のメールメールアドレスのユーザーが存在しませんでした。");
		}
		
		//メールの更新件数が1件の場合、true
		boolean updateSucessFlag = updateCount == 1 ? true : false;
		
		HttpHeaders headers = new HttpHeaders();
		
		ResponseEntity<Boolean> resEntity = new ResponseEntity<>(updateSucessFlag,headers,HttpStatus.CREATED); 
		return resEntity;
	}
}
