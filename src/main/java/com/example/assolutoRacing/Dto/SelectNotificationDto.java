package com.example.assolutoRacing.Dto;


import java.sql.Timestamp;

import lombok.Data;

/**
 * お知らせ通知取得Dto
 * @author nakagawa.so
 *
 */
@Data
public class SelectNotificationDto {
	//タイトル
	private String title;
	//内容
	private String content;
	//作成時間
	private Timestamp createTime;
}
