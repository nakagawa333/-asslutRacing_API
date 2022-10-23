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

import com.example.assolutoRacing.Bean.SettingInfoBean;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Service.SettingInfoService;

/**
 * 
 * @author nakagawa.so
 * 設定情報登録コントローラークラス
 */
@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class AddSettingInfoController {	
	@Autowired
	SettingInfoService settingInfoService;
	
	@RequestMapping(path = "/add", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<Integer> add(@RequestBody(required = true) SettingInfoBean settingInfo) {
		int insertCount = 0;
		
		try {
			insertCount = settingInfoService.insert(settingInfo);
		} catch(Exception e) {
			throw e;
		}
		HttpHeaders headers = new HttpHeaders();
		
		ResponseEntity<Integer> resEntity = new ResponseEntity<>(insertCount,headers,HttpStatus.CREATED); 
		return resEntity;
	}

}