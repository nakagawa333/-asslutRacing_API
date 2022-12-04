package com.example.assolutoRacing.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

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
import lombok.NonNull;

@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class DeleteSettingInfoController {
	@Autowired
	SettingInfoService settingInfoService;
	
	@RequestMapping(path = "/delete", method = RequestMethod.PUT)
	@Transactional
	public ResponseEntity<Integer> delete(@RequestBody(required = true) Request req) {
		int deleteCount = 0;
		
		try {
			deleteCount = settingInfoService.deleteOne(req.getId());
		} catch(Exception e) {
			throw e;
		}
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<Integer> resEntity = new ResponseEntity<>(deleteCount,headers,HttpStatus.CREATED);
		return resEntity;
	}
	
;	@Data
    public static class Request{
		public int id;
	}
}
