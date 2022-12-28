package com.example.assolutoRacing.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.SelectNotificationRes;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Dto.SelectNotificationDto;
import com.example.assolutoRacing.Enum.DateUtil;
import com.example.assolutoRacing.Service.NotificationService;
import com.example.assolutoRacing.Enum.DateUtil;


/**
 * お知らせ通知取得コントローラークラス.
 * @author nakaagwa.so
 *
 */
@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class SelectNotificationController {
	@Autowired
	NotificationService notificationService;
	
	@RequestMapping(path = "/select/notification", method = RequestMethod.GET)
	public ResponseEntity<List<SelectNotificationRes>> select() {
		List<SelectNotificationDto> selectNotificationDtoList = new ArrayList<SelectNotificationDto>();
		
		try {
			//お知らせ通知を全取得する
			selectNotificationDtoList = notificationService.selectAll();
		} catch(Exception e) {
			throw e;
		}
		
		List<SelectNotificationRes> selectNotificationResList = new ArrayList<SelectNotificationRes>();
		
		for(SelectNotificationDto selectNotificationDto : selectNotificationDtoList) {
			SelectNotificationRes selectNotificationRes = new SelectNotificationRes();
			//タイトル
			selectNotificationRes.setTitle(selectNotificationDto.getTitle());
			//内容
			selectNotificationRes.setContent(selectNotificationDto.getContent());
			
			SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.YYYYMMDD.getFormat());
			//作成時間
			String createTime = sdf.format(selectNotificationDto.getCreateTime());
			selectNotificationRes.setCreateTime(createTime);
			selectNotificationResList.add(selectNotificationRes);
		}
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<List<SelectNotificationRes>> resEntity = new ResponseEntity<>(selectNotificationResList,headers,HttpStatus.OK);
		return resEntity;
	}
}
