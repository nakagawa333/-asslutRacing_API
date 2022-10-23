package com.example.assolutoRacing.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.UpdateSettingInfoBean;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Service.SettingInfoService;

/**
 * 
 * @author nakagawa.so
 * 設定情報更新コントローラークラス
 */
@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class UpdateSettingInfoController {	
	@Autowired
	SettingInfoService settingInfoService;
	
	@RequestMapping(path = "/update", method = RequestMethod.PUT)
	@Transactional
	public ResponseEntity<Integer> update(@RequestBody(required = true) UpdateSettingInfoBean settingInfo) {
		int updateCount = 0;
		
		try {
			updateCount = settingInfoService.update(settingInfo);
		} catch(Exception e) {
			throw e;
		}
		HttpHeaders headers = new HttpHeaders();
		
		ResponseEntity<Integer> resEntity = new ResponseEntity<>(updateCount,headers,HttpStatus.CREATED); 
		return resEntity;
	}

}