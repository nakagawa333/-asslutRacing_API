package com.example.assolutoRacing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import com.example.assolutoRacing.Bean.AuthUserBean;
import com.example.assolutoRacing.Bean.AuthUserRes;
import com.example.assolutoRacing.Bean.LoginUserRes;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Dto.AuthUserDto;
import com.example.assolutoRacing.Dto.CustromUserDetails;
import com.example.assolutoRacing.Service.CustomAuthenticationManager;
import com.example.assolutoRacing.Service.JwtUtil;
import com.example.assolutoRacing.Service.UserService;
import org.apache.commons.lang3.StringUtils;


/**
 * 
 * @author nakagawa.so
 * ログインコントローラークラス
 *
 */

@RestController
public class LoginController{
	
	private AuthenticationManager authenticationManager;

	public LoginController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	CustomAuthenticationManager customAuthenticationManager;
	
	
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public ResponseEntity<LoginUserRes> login(@RequestBody(required = true) @Validated AuthUserBean authUserBean) throws Exception{
			AuthUserRes user = new AuthUserRes();
		
		String userName = authUserBean.getUserName();
		String mail = authUserBean.getMail();
		
		if(StringUtils.isEmpty(mail) && StringUtils.isEmpty(userName)) {
			throw new Exception("ユーザー名とパスワードの値がありません。");
		}
		//パスワードをハッシュ化
		String password = DigestUtils.sha256Hex(authUserBean.getPassword());
		AuthUserDto authUser = new AuthUserDto();
		authUser.setPassword(password);
		
		if(StringUtils.isNoneEmpty(userName)) {
			authUser.setUserName(userName);
		} else if(StringUtils.isNoneEmpty(mail)) {
			authUser.setMail(mail);
		}
		
		authUser.setUserName(authUserBean.getUserName());
		
		try {
			user = userService.auth(authUser);
		} catch(Exception e) {
			throw e;
		}
		
		CustromUserDetails custromUserDetails = new CustromUserDetails(user);
		
		LoginUserRes loginUserRes = new LoginUserRes();
		loginUserRes.setPassword(user.getPassword());
		loginUserRes.setUserName(user.getUserName());
		loginUserRes.setUserId(user.getUserId());
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		Date acessExp = Constants.TOKEN.ACESSEXP;

		//アクセストークンを作成
		String acessToken = jwtUtil.generateToken(custromUserDetails,acessExp);
		
		Date refreshExp = Constants.TOKEN.REFRESHEXP;
		
		//リフレッシュトークンを作成
		String refreshToken = jwtUtil.generateToken(custromUserDetails,refreshExp);

		loginUserRes.setAcessToken(acessToken);
		loginUserRes.setRefreshToken(refreshToken);
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<LoginUserRes> resEntity = new ResponseEntity<>(loginUserRes,headers,HttpStatus.OK); 
		return resEntity;
	}
}
