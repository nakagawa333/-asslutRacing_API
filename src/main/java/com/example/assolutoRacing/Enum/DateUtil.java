package com.example.assolutoRacing.Enum;

import lombok.Getter;

@Getter
public enum DateUtil {
	YYYYMMDD("yyyy/MM/dd");
	
	private String format;
	
	private DateUtil(String format) {
		this.format = format;
	}
}
