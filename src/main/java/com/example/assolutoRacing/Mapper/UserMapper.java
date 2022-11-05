package com.example.assolutoRacing.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.assolutoRacing.Dto.RegistUserDto;

@Mapper
public interface UserMapper {
	/**
	 * ユーザーを登録する
	 * @return 登録件数
	 */
	Integer insert(RegistUserDto registUser);
	
	/**
	 * ユーザーをidから取得する
	 * @return ユーザー件数
	 */
	
	Integer selectUserById(int userId);
}
