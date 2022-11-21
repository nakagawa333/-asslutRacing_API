package com.example.assolutoRacing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assolutoRacing.Dto.SelectTempUserDto;
import com.example.assolutoRacing.Dto.TempRegistUserDto;
import com.example.assolutoRacing.Mapper.TempUserMapper;

/**
 * 
 * @author nakagawa.so
 * 仮ユーザーサービスクラス
 *
 */
@Service
public class TempUserService {
	@Autowired
	TempUserMapper tempUserMapper;
	
	/**
	 * 仮ユーザーを登録する
	 * @param tempRegistUserDto 仮ユーザーDTO
	 * @return 登録件数
	 */
	public Integer insert(TempRegistUserDto tempRegistUserDto){
		Integer insertCount = 0;
		
		try {
			insertCount = tempUserMapper.insert(tempRegistUserDto);
		} catch(Exception e) {
			throw e;
		}
		return insertCount;
	}
	
	/**
	 * トークンを条件に仮ユーザー情報を取得する
	 * @param token トークン-
	 * @return
	 */
	public SelectTempUserDto selectByToken(String token) {
		SelectTempUserDto tempUser = null;
		
		try {
			tempUser = tempUserMapper.selectByToken(token);
		} catch(Exception e) {
			throw e;
		}
		return tempUser;
	}
}
