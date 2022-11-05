package com.example.assolutoRacing.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.assolutoRacing.Dto.AuthUserDto;
import com.example.assolutoRacing.Dto.RegistUserDto;

/**
 * @author nakagawa.so
 * ユーザー情報マッパークラス
 */
@Mapper
public interface UserMapper {
	/**
	 * ユーザーを登録する
	 * @return 登録件数
	 */
	Integer insert(RegistUserDto registUser);
	
	/**
	 * ユーザー数をユーザー名から取得する
	 * @return ユーザー件数
	 */
	Integer selectUserCountByUserName(String userName);
	
	/**
	 * ユーザー認証
	 * @return ユーザー件数
	 */
	Integer auth(AuthUserDto authUser);
}
