package com.example.assolutoRacing.api;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.AuthUserRes;
import com.example.assolutoRacing.Bean.UpdateUserNameBean;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Dto.CustromUserDetails;
import com.example.assolutoRacing.Dto.UpdateUserNameDto;
import com.example.assolutoRacing.Dto.UpdateUserRes;
import com.example.assolutoRacing.Service.JwtUtil;
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
	
	@Autowired
	JwtUtil jwtUtil;
	
	/**
	 * ユーザー名を更新する
	 * @param updateUserNameBean 更新用ユーザー名Beanクラス
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(path = "/update/user/username", method = RequestMethod.PUT)
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<UpdateUserRes> updateUserName(@RequestBody(required = true) @Validated UpdateUserNameBean updateUserNameBean) throws Exception {
		int userCount = 0;
		
		try {
			//ユーザー名からユーザー数を取得する
			userCount = userService.selectUserCountByUserName(updateUserNameBean.getUserName());
		} catch(Exception e) {
			throw new SQLException("ユーザー情報取得に失敗しました");
		}
		
		//ユーザー名更新用Dtoクラス
		UpdateUserNameDto updateUserNameDto = new UpdateUserNameDto();
		updateUserNameDto.setUserId(updateUserNameBean.getUserId());
		updateUserNameDto.setUserName(updateUserNameBean.getUserName());
		
		//既にユーザーが存在する場合、更新処理を行わない
		if(1 <= userCount) {
			throw new Exception("既にユーザー名が使用されています");
		}
		
		//更新カウント
		int userUpdateCount = 0;
		
		try {
			userUpdateCount = userService.updateUserName(updateUserNameDto);
		} catch(Exception e) {
			throw new SQLException("ユーザー名更新に失敗しました");
		}
		
		boolean userUpdateSucessFlag = userUpdateCount == 1 ? true:false;
		
		AuthUserRes user = new AuthUserRes();
		user.setUserName(updateUserNameBean.getUserName());
		CustromUserDetails custromUserDetails = new CustromUserDetails(user);
		
		Date acessExp = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
		//アクセストークン
		String acessToken = jwtUtil.generateToken(custromUserDetails,acessExp);
		
		Date refreshExp = new Date(System.currentTimeMillis() + 5000 * 60 * 60 * 24);
		
		//リフレッシュトークン
		String refreshToken = jwtUtil.generateToken(custromUserDetails,refreshExp);
		
		UpdateUserRes updateUserRes = new UpdateUserRes();
		updateUserRes.setAcessToken(acessToken);
		updateUserRes.setUserUpdateSucessFlag(userUpdateSucessFlag);

		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<UpdateUserRes> resEntity = new ResponseEntity<>(updateUserRes,headers,HttpStatus.CREATED); 
		return resEntity;
	}
	
	@RequestMapping(path = "/update/user/mail", method = RequestMethod.PUT)
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Boolean> updateMail(){
		return null;
		
	}
}


