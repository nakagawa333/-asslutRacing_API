package com.example.assolutoRacing.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assolutoRacing.Dto.SettingInfoDto;
import com.example.assolutoRacing.Dto.selectCarsDto;
import com.example.assolutoRacing.Mapper.CarsMapper;
import com.example.assolutoRacing.Mapper.SettingInfoMapper;

@Service
public class InfosService{
	@Autowired
	CarsMapper carsMapper;
	
	public List<selectCarsDto> selectCarsAll() {
		List<selectCarsDto> carsList = new ArrayList<>();
		
		try {
			carsList = carsMapper.selectAll();
		} catch(Exception e) {
			throw e;
		}			
		return carsList;
	}
}