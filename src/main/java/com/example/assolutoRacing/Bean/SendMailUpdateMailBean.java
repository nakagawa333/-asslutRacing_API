package com.example.assolutoRacing.Bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * メール更新用メール送信Beanクラス
 * @author nakagawa.so
 *
 */
@Data
public class SendMailUpdateMailBean {
	@NotNull(message="ユーザーidが不正です")
	//ユーザーid
	private Integer userId;
	
	@NotBlank(message="メールアドレスが入力されていません")
	@Length(min=1,max=100,message="メールアドレスの最大文字数は100文字です")
	@Pattern(regexp="^([a-zA-Z0-9])+([a-zA-Z0-9\\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\\._-]+)+$",message="正しいメールアドレスではありません")
	//メール
	private String mail;
	
	@NotBlank(message="リクエストurlが不正です")
	//リクエストurl
	private String requestUrl;
}
