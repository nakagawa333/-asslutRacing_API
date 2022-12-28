package com.example.assolutoRacing.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assolutoRacing.Dto.SelectNotificationDto;
import com.example.assolutoRacing.Mapper.NotificationMapper;

@Service
public class NotificationService {
	@Autowired
	NotificationMapper notificationMapper;
	
	/**
	 * お知らせ通知を全取得する
	 * @return お知らせ通知取得Dtoリスト
	 */
	public List<SelectNotificationDto> selectAll(){
		List<SelectNotificationDto> selectNotificationDtoList = new ArrayList<SelectNotificationDto>();
		try {
			selectNotificationDtoList = notificationMapper.selectAll();
		} catch(Exception e) {
			throw e;
		}
		return selectNotificationDtoList;
	}
}
