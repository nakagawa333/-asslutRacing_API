package com.example.assolutoRacing.Bean;

import lombok.Data;

@Data
public class SelectNotificationRes {
	//タイトル
	String title;
	
	//内容
	String content;
	
	//作成時間(yyyy/mm/dd)
	String createTime;
}
