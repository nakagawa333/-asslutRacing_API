package com.example.assolutoRacing.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.assolutoRacing.Bean.AuthUserRes;
import com.example.assolutoRacing.Bean.SelectUserBean;
import com.example.assolutoRacing.Dto.AuthUserDto;
import com.example.assolutoRacing.Dto.RegistUserDto;
import com.example.assolutoRacing.Dto.SelectNotificationDto;
import com.example.assolutoRacing.Dto.UpdatePasswordDto;

/**
 * @author nakagawa.so
 * お知らせ通知マッパークラス
 */
@Mapper
public interface NotificationMapper {
	/**
	 * お知らせ通知を全取得する
	 * @return お知らせ通知リスト
	 */
	List<SelectNotificationDto> selectAll();
}
