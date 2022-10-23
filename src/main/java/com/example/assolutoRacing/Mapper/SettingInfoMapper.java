package com.example.assolutoRacing.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.example.assolutoRacing.Bean.AddSettingInfoBean;
import com.example.assolutoRacing.Dto.SettingInfoDto;

@Mapper
public interface SettingInfoMapper{
	
	/**
	 * 全設定情報を取得
	 * @return 全設定情報
	 */
	List<SettingInfoDto> selectAll();
	
	 /**
	 * 全設定情報の全カウント数を取得
	 * @return 全設定情報の件数
	 */
	Integer getSelectCount();
	
	/**
	 * 該当idによる設定情報の削除
	 * @param id ID
	 * @return 削除件数
	 */
	Integer deleteOne(int id);
	
	/**
	 * 設定情報を登録する
	 * @param settingInfo 
	 * @return 登録件数
	 */
	Integer insert(AddSettingInfoBean settingInfo);
}