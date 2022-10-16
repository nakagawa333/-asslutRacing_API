package com.example.assolutoRacing.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assolutoRacing.Dto.SettingInfoDto;
import com.example.assolutoRacing.Mapper.SettingInfoMapper;

@Service
public class HomeService{
	@Autowired
	SettingInfoMapper settingInfoMapper;
	
	public List<SettingInfoDto> selectAll() {
		List<SettingInfoDto> settingInfoDtoList = new ArrayList<>();
		
		int selectCount = 0;
		
		try {
			selectCount = settingInfoMapper.getSelectCount();
		} catch(Exception e) {
			throw e;
		}
		
		if(selectCount != 0) {
			try {
				settingInfoDtoList = settingInfoMapper.selectAll();
			} catch(Exception e) {
				throw e;
			}			
		}
		return settingInfoDtoList;
	}
}