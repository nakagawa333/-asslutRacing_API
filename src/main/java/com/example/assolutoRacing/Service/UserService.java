package com.example.assolutoRacing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assolutoRacing.Dto.RegistUserDto;
import com.example.assolutoRacing.Mapper.UserMapper;

/**
 * 
 * @author nakagawa.so
 * ユーザー登録サービスクラス
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
}
