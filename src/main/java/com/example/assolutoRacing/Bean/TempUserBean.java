package com.example.assolutoRacing.Bean;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	@Size(min=1,max=100)
	private String userName;
	
	//メール
	@NotBlank
	@Pattern(regexp = Constants.REGEX.MAIL)
	@Size(min=1,max=100)
	private String mail;
	
	//パスワード
	@NotBlank
	@Size(min=7,max=100)
	private String password;
	
	//リクエストurl
	@NotBlank
	private String requestUrl;
}
