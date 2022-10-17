package com.example.assolutoRacing.Bean;

import java.util.List;

import com.example.assolutoRacing.Dto.SelectCarsDto;
import com.example.assolutoRacing.Dto.SelectCourseDto;
import com.example.assolutoRacing.Dto.SelectMakerDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfosBean {
	public List<SelectCarsDto> carsList;
	
	public List<SelectCourseDto> courseList;
	
	public List<SelectMakerDto> makerList;
}