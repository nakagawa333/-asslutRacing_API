package com.example.assolutoRacing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assolutoRacing.Bean.AuthUserRes;
import com.example.assolutoRacing.Bean.SelectUserBean;
import com.example.assolutoRacing.Dto.AuthUserDto;
import com.example.assolutoRacing.Dto.RegistUserDto;
import com.example.assolutoRacing.Mapper.UserMapper;

/**
 * 
 * @author nakagawa.so
 * ユーザーサービスクラス
 */
@Service
public class UserService {
	@Autowired
	UserMapper userMapper;
	
	/**
	 * ユーザーを登録する
	 * @return 登録件数
	 */
	public Integer insert(RegistUserDto registUser) {
		int insertCount = 0;
		try {
			insertCount = userMapper.insert(registUser);
		} catch(Exception e) {
			throw e;
		}
		return insertCount;
	}
	
	/**
	 * ユーザー数をユーザー名から取得する
	 * @return ユーザー件数
	 */
	public Integer selectUserCountByUserName(String userName) {
		int userCount = 0;
		try {
			userCount = userMapper.selectUserCountByUserName(userName);
		} catch(Exception e) {
			throw e;
		}
		return userCount;
	}
	
	/**
	 * ユーザー認証
	 * @return ユーザー件数
	 */
	public AuthUserRes auth(AuthUserDto authUser) {
		AuthUserRes user = new AuthUserRes();
		try {
			user = userMapper.auth(authUser);
		} catch(Exception e) {
			throw e;
		}
		return user;
	}
	
	/**
	 * ユーザーを取得する.
	 * @param selectUserBean
	 * @return ユーザー件数
	 */
	public Integer select(SelectUserBean selectUserBean) {
		int userCount = 0;
		try {
			userCount = userMapper.select(selectUserBean);
		} catch(Exception e) {
			throw e;
		}
		return userCount;		
	}
}
