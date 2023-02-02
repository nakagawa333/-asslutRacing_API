package com.example.assolutoRacing.api;

import org.springframework.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.InfosBean;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Dto.SelectCarsDto;
import com.example.assolutoRacing.Dto.SelectCourseDto;
import com.example.assolutoRacing.Service.InfosService;
import com.example.assolutoRacing.Dto.SelectMakerDto;
import com.example.assolutoRacing.Dto.SelectTireTypeDto;


@RestController
public class InfosController {
	@Autowired
	InfosService infosService;
	
	@RequestMapping(path = "/infos", method = RequestMethod.GET)
	public ResponseEntity<InfosBean> infos() {
		//車一覧
		List<SelectCarsDto> carsList = new ArrayList<>();
		//コース一覧
		List<SelectCourseDto> courseList = new ArrayList<>();
		//メーカー一覧
		List<SelectMakerDto> makerList = new ArrayList<>();
		//タイヤの種類一覧
		List<SelectTireTypeDto> tireTypeList = new ArrayList<>();
		
		try {
			carsList = infosService.selectCarsAll();
		} catch(Exception e) {
			throw e;
		}
		
		try {
			courseList = infosService.selectCourseAll();
		} catch(Exception e) {
			throw e;
		}
		
		try {
			makerList = infosService.selectMakerAll();
		} catch(Exception e) {
			throw e;
		}
		
		try {
			tireTypeList = infosService.selectTireTypeAll();
		} catch(Exception e) {
			throw e;
		}
		
		InfosBean infosBean = new InfosBean();
		infosBean.setCarsList(carsList);
		infosBean.setCourseList(courseList);
		infosBean.setMakerList(makerList);
		infosBean.setTireTypeList(tireTypeList);

		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<InfosBean> resEntity = new ResponseEntity<>(infosBean,headers,HttpStatus.OK); 
		return resEntity;
	}
}