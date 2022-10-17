package com.example.assolutoRacing.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assolutoRacing.Dto.SelectCarsDto;
import com.example.assolutoRacing.Dto.SelectCourseDto;
import com.example.assolutoRacing.Dto.SelectMakerDto;
import com.example.assolutoRacing.Mapper.CarsMapper;
import com.example.assolutoRacing.Mapper.CourseMapper;
import com.example.assolutoRacing.Mapper.MakerMapper;

@Service
public class InfosService{
	@Autowired
	CarsMapper carsMapper;
	
	@Autowired
	CourseMapper courseMapper;
	
	@Autowired
	MakerMapper makerMapper;
	
	/** 車両一覧を取得する */
	public List<SelectCarsDto> selectCarsAll() {
		List<SelectCarsDto> carsList = new ArrayList<>();
		
		try {
			carsList = carsMapper.selectAll();
		} catch(Exception e) {
			throw e;
		}			
		return carsList;
	}

	/** コース一覧を取得する */
	public List<SelectCourseDto> selectCourseAll(){
		
		List<SelectCourseDto> courseList = new ArrayList<>();
		
		try {
			courseList = courseMapper.selectAll();
		} catch(Exception e) {
			throw e;
		}
		return courseList;
	}
	
	/** メーカー一覧を取得する **/
	public List<SelectMakerDto> selectMakerAll(){
		List<SelectMakerDto> makerList = new ArrayList<>();
		
		try {
			makerList = makerMapper.selectAll();
		} catch(Exception e) {
			throw e;
		}
		return makerList;
	}
}