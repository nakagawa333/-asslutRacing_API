package com.example.assolutoRacing.api;

import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
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

import com.example.assolutoRacing.Bean.UpdateUserBean;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Dto.UpdateUserDto;
import com.example.assolutoRacing.Service.UserService;

/**
 * 
 * @author nakagawa.so
 * ユーザー情報更新コントローラークラス
 *
 */

@RestController
public class UpdateUserController {
	
	@Autowired
	UserService userService;
	
	/**
	 * ユーザー名を更新する
	 * @param updateUserBean 更新用ユーザーBeanクラス
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(path = "/update/user/username", method = RequestMethod.POST)
	public ResponseEntity<Boolean> updateUserName(@RequestBody(required = true) @Validated UpdateUserBean updateUserBean) throws Exception {
		int userCount = 0;
		
		try {
			//ユーザー名からユーザー数を取得する
			userCount = userService.selectUserCountByUserName(updateUserBean.getUserName());
		} catch(Exception e) {
			throw new SQLException("ユーザー情報取得に失敗しました");
		}
		
		//ユーザー名更新用Dtoクラス
		UpdateUserDto updateUserDto = new UpdateUserDto();
		updateUserDto.setUserId(updateUserBean.getUserId());
		updateUserDto.setUserName(updateUserBean.getUserName());
		
		//既にユーザーが存在する場合、更新処理を行わない
		if(1 <= userCount) {
			throw new Exception("既にユーザー名が使用されています");
		}
		
		//更新カウント
		int userUpdateCount = 0;
		
		try {
			userUpdateCount = userService.updateUserName(updateUserDto);
		} catch(Exception e) {
			throw new SQLException("ユーザー名更新に失敗しました");
		}
		
		
		boolean userUpdateSucessFlag = userUpdateCount == 1 ? true:false;
		

		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<Boolean> resEntity = new ResponseEntity<>(userUpdateSucessFlag,headers,HttpStatus.CREATED); 
		return resEntity;
	}
}


