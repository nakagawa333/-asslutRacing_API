package com.example.assolutoRacing.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.assolutoRacing.Dto.SelectTempUserDto;
import com.example.assolutoRacing.Dto.TempRegistUserDto;

/**
 * 
 * @author nakagawa.so
 * 仮ユーザーマッパークラス
 */
@Mapper
public interface TempUserMapper {
	
	/**
	 * 仮ユーザーを登録する
	 * @param tempRegistUser 仮ユーザー情報
	 * @return 登録件数
	 */
	Integer insert(TempRegistUserDto tempRegistUser);
	
	/**
	 * 
	 * @param token トークン
	 * @return 
	 */
	SelectTempUserDto selectByToken(String token);
}
