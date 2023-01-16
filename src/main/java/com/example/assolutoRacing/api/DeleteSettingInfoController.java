package com.example.assolutoRacing.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Service.SettingInfoService;

import lombok.Data;


/*
 * 設定情報削除コントローラークラス
 * @author nakagawa.so
 *
 */
@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class DeleteSettingInfoController {
	@Autowired
	SettingInfoService settingInfoService;
	
	@RequestMapping(path = "/delete", method = RequestMethod.PUT)
	@Transactional
	public ResponseEntity<Boolean> delete(@RequestBody(required = true) Request req) {
		int deleteCount = 0;
		
		try {
			//該当idの設定情報を削除
			deleteCount = settingInfoService.deleteOne(req.getId());
		} catch(Exception e) {
			throw e;
		}
		
		//削除件数が1件の場合成功
		boolean deleteSucessFlag = deleteCount == 1 ? true : false;
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<Boolean> resEntity = new ResponseEntity<>(deleteSucessFlag,headers,HttpStatus.OK);
		return resEntity;
	}
	
;	@Data
    public static class Request{
		public int id;
	}
}
