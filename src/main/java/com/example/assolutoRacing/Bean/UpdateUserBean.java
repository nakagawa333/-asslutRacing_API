package com.example.assolutoRacing.Bean;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * 更新用ユーザーBeanクラス
 * @author nakagawa.so
 *
 */
@Data
public class UpdateUserBean {
	
	@NotNull
	//ユーザーid
	private Integer userId;
	
	@NotEmpty
	@Length(min=1,max=100)
	//ユーザー名
	private String userName;
}
