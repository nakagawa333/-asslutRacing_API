package com.example.assolutoRacing.Constants;

import java.util.Date;

public class Constants {
	public static final String ORIGINS = "http://localhost:4200";
	
	public static class REGEX{
		public static final String MAIL = "^([a-zA-Z0-9])+([a-zA-Z0-9._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9._-]+)+$";
	}
	
	public static class TOKEN{
		public static final Date ACESSEXP = new Date(System.currentTimeMillis() + 30 * 60 * 60 * 24);
		public static final Date REFRESHEXP = new Date(System.currentTimeMillis() + 5000 * 60 * 60 * 24);
	}
}
