package com.example.assolutoRacing.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.example.assolutoRacing.Dto.SettingInfoDto;

@Mapper
public interface SettingInfoMapper{
	
	/**
	 * 全設定情報を取得
	 * 
	 * @return 全設定情報
	 * 
	 */
	List<SettingInfoDto> selectAll();
	
	/**
	 * 全設定情報の取得
	 * @return 全設定情報の件数
	 */
	Integer getSelectCount();
	
	/**
	 * 該当idによる設置情報の削除
	 * @return 削除件数
	 */
	Integer deleteOne(int id);
	
}