package com.example.assolutoRacing.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assolutoRacing.Bean.AddSettingInfoBean;
import com.example.assolutoRacing.Bean.UpdateSettingInfoBean;
import com.example.assolutoRacing.Dto.AddSettingInfoDto;
import com.example.assolutoRacing.Dto.SettingInfoDto;
import com.example.assolutoRacing.Mapper.SettingInfoMapper;
import com.example.assolutoRacing.Bean.SelectSettingInfoRes;
import com.example.assolutoRacing.Dto.UpdateSettingInfoDto;

@Service
public class SettingInfoService {
	@Autowired
	SettingInfoMapper settingInfoMapper;
	
	/**
	 * 該当IDの設定情報を削除する
	 * @param id
	 * @return 削除件数
	 */
	public Integer deleteOne(int id) {
		return settingInfoMapper.deleteOne(id);
	}
	
	/**
	 * 設定情報を全取得する
	 * @return 設定情報リスト
	 */
	public List<SettingInfoDto> selectAll(Integer userId) {
		List<SettingInfoDto> settingInfoDtoList = new ArrayList<>();
		try {
			settingInfoDtoList = settingInfoMapper.selectAll(userId);
		} catch(Exception e) {
			throw e;
		}		
		return settingInfoDtoList;
	}
	
	/**
	 * 設定情報を登録する
	 * @param settingInfo  登録用設定情報格納クラス
	 * @return 登録件数
	 */
	public Integer insert(AddSettingInfoDto addSettingInfoDto) {
		int insertId = 0;
		try {
			insertId = settingInfoMapper.insert(addSettingInfoDto);
		} catch(Exception e) {
			throw e;
		}
		return insertId;
	}
	
	/**
	 * 設定情報を更新する
	 * @param settingInfo  登録用設定情報格納クラス
	 * @return 更新件数
	 */
	public Integer update(UpdateSettingInfoDto updateSettingInfoDto) {
		int updateCount = 0;
		try {
			updateCount = settingInfoMapper.update(updateSettingInfoDto);
		} catch(Exception e) {
			throw e;
		}
		return updateCount;
	}
	
	/**
	 * idから設定情報を取得する
	 * @param id id
	 * @return 設定情報
	 */
	public SelectSettingInfoRes selectSettingInfoById(Integer id) {
		SelectSettingInfoRes selectSettingInfoRes = new SelectSettingInfoRes();
		try {
			selectSettingInfoRes = settingInfoMapper.selectSettingInfoById(id);
		} catch(Exception e) {
			throw e;
		}
		return selectSettingInfoRes;
	}
}
