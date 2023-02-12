package com.example.assolutoRacing.Bean;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.example.assolutoRacing.Constants.Constants;

import lombok.Data;

/**
 * 仮ユーザーBeanクラス
 * @author nakagawa.so
 *
 */
@Data
public class TempUserBean {
	//ユーザー名
	@NotBlank
	@Max(100)
	private String userName;
	
	//メール
	@NotBlank
	@Pattern(regexp = Constants.REGEX.MAIL)
	@Max(100)
	private String mail;
	
	//パスワード
	@NotBlank
	@Max(100)
	@Min(7)
	private String password;
	
	//リクエストurl
	@NotBlank
	private String requestUrl;
}
