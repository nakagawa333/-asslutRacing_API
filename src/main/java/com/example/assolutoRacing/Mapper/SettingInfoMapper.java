package com.example.assolutoRacing.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.example.assolutoRacing.Bean.AddSettingInfoBean;
import com.example.assolutoRacing.Bean.SelectSettingInfoRes;
import com.example.assolutoRacing.Bean.UpdateSettingInfoBean;
import com.example.assolutoRacing.Dto.AddSettingInfoDto;
import com.example.assolutoRacing.Dto.SettingInfoDto;
import com.example.assolutoRacing.Dto.UpdateSettingInfoDto;

@Mapper
public interface SettingInfoMapper{
	
	/**
	 * 全設定情報を取得
	 * @return 全設定情報
	 */
	List<SettingInfoDto> selectAll(Integer userId);
	
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
	 * @param settingInfo 登録用設定情報格納クラス
	 * @return 登録件数
	 */
	Integer insert(AddSettingInfoDto settingInfo);

	/**
	 * 設定情報を更新する
	 * @param settingInfo 更新用設定情報格納クラス
	 * @return　更新件数
	 */
	Integer update(UpdateSettingInfoDto updateSettingInfoDto);
	
	/**
	 * idから設定情報を取得する
	 * @param id id
	 * @return 設定情報レスポンスクラス
	 */
	SelectSettingInfoRes selectSettingInfoById(Integer id);
}