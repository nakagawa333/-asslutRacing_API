package com.example.assolutoRacing.api;

import org.springframework.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.InfosBean;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Dto.SettingInfoDto;
import com.example.assolutoRacing.Dto.selectCarsDto;
import com.example.assolutoRacing.Service.HomeService;
import com.example.assolutoRacing.Service.InfosService;

@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class InfosController {
	@Autowired
	InfosService infosService;
	
	@RequestMapping(path = "/infos", method = RequestMethod.GET)
	public ResponseEntity<InfosBean> infos() {
		List<selectCarsDto> carsList = new ArrayList<>();
		try {
			carsList = infosService.selectCarsAll();
		} catch(Exception e) {
			throw e;
		}
		
		InfosBean infosBean = new InfosBean();
		infosBean.setCarsList(carsList);

		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<InfosBean> resEntity = new ResponseEntity<>(infosBean,headers,HttpStatus.CREATED); 
		return resEntity;
	}
}