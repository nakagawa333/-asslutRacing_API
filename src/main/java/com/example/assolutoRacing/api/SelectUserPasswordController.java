package com.example.assolutoRacing.api;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.SelectUserPasswordBean;
import com.example.assolutoRacing.Bean.UserRes;
import com.example.assolutoRacing.Service.UserService;

/**
 * 
 * @author nakagawa.so
 * パスワード情報検索コントローラークラス
 *
 */

@RestController
public class SelectUserPasswordController {
	
	@Autowired
	UserService userService;
	
	/**
	 * パスワードによるユーザー情報検索
	 * @param userName ユーザー名
	 * @return　
	 * @throws Exception 
	 */
	@RequestMapping(path = "/select/user/password", method = RequestMethod.POST)
	public ResponseEntity<Boolean> selectUserByPassword(@RequestBody(required = true) @Validated SelectUserPasswordBean selectUserPasswordBean) throws Exception {
		
		//パスワードが有効であるぁ
		boolean isvValidityPasswordFlag = false;
		//パスワード
		String password = DigestUtils.sha256Hex(selectUserPasswordBean.getPassword());
		//ユーザーid
		int userId = selectUserPasswordBean.getUserId();
		
		UserRes userRes = new UserRes();
		
		//パスワードに値が空もしくはnullでない場合
		if(StringUtils.isNoneEmpty(password)) {
			try {
				//ユーザーidからユーザー情報を取得する
				userRes = userService.selectbyUserId(userId);
			} catch(Exception e) {
				throw new Exception("ユーザー情報の取得に失敗しました");
			}
			
			if(userRes == null) {
				throw new Exception("ユーザー情報の取得に失敗しました");
			}
			
			//パスワードがデータベースの値と同じである場合
			if(StringUtils.equals(userRes.getPassword(),password)) {
				isvValidityPasswordFlag = true;
			}
		} else {
			throw new Exception("入力されたパスワードが不正です");
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Connection", "Keep-Alive");
		ResponseEntity<Boolean> resEntity = new ResponseEntity<>(isvValidityPasswordFlag,headers,HttpStatus.OK); 
		return resEntity;
	}
	

}


