package com.example.assolutoRacing.Constants;

import java.util.Date;

public class Constants {
	public static final String ORIGINS = "http://localhost:4200";
	
	public static class REGEX{
		public static final String MAIL = "^([a-zA-Z0-9])+([a-zA-Z0-9._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9._-]+)+$";
	}
	
	public static class TOKEN{
		//有効期限30分
		public static final Date ACESSEXP = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
		//有効期限14日
		public static final Date REFRESHEXP = new Date(System.currentTimeMillis() + 14 * 24 * 60 * 60 * 1000);
	}
}
