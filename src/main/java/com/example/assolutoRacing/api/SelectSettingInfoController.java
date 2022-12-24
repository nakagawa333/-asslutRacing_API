package com.example.assolutoRacing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.SelectSettingInfoRes;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Service.SettingInfoService;

/**
 * 
 * @author nakagawa.so
 * 設定情報取得コントローラークラス
 */
@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class SelectSettingInfoController {
	@Autowired
	SettingInfoService settingInfoService;
	@RequestMapping(path = "/select",method = RequestMethod.GET)
	public ResponseEntity<SelectSettingInfoRes> selectSettingInfoById(@RequestParam(required = true) int id) {
		SelectSettingInfoRes selectSettingInfoRes = new SelectSettingInfoRes();
		
		try {
			//idから設定情報を取得する
			selectSettingInfoRes = settingInfoService.selectSettingInfoById(id);
		} catch(Exception e) {
			throw e;
		}

		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<SelectSettingInfoRes> resEntity = new ResponseEntity<>(selectSettingInfoRes,headers,HttpStatus.OK);
		return resEntity;
	}
}
