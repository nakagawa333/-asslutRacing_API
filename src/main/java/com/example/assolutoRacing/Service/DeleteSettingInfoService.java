package com.example.assolutoRacing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assolutoRacing.Mapper.SettingInfoMapper;

@Service
public class DeleteSettingInfoService {
	@Autowired
	SettingInfoMapper settingInfoMapper;
	public Integer deleteOne(int id) {
		return settingInfoMapper.deleteOne(id);
	}
}
