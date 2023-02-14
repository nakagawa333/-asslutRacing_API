package com.example.assolutoRacing.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.assolutoRacing.Bean.AuthUserRes;
import com.example.assolutoRacing.Bean.SelectUserBean;
import com.example.assolutoRacing.Bean.UserRes;
import com.example.assolutoRacing.Dto.AuthUserDto;
import com.example.assolutoRacing.Dto.RegistUserDto;
import com.example.assolutoRacing.Dto.UpdatePasswordDto;
import com.example.assolutoRacing.Dto.UpdateUserNameDto;

/**
 * @author nakagawa.so
 * ユーザー情報マッパークラス
 */
@Mapper
public interface UserMapper {
	/**
	 * ユーザーを登録する
	 * @return 登録件数
	 */
	Integer insert(RegistUserDto registUser);
	
	/**
	 * ユーザー数をユーザー名から取得する
	 * @return ユーザー件数
	 */
	Integer selectUserCountByUserName(String userName);
	
	/**
	 * ユーザー認証
	 * @return ユーザー件数
	 */
	AuthUserRes auth(AuthUserDto authUser);
	
	/**
	 * 
	 * ユーザー情報を取得する.
	 * @param selectUserBean 
	 * @return ユーザー件数
	 */
	Integer select(SelectUserBean selectUserBean);
	
	/**
	 * メールを条件にパスワードを更新する。
	 * @param updatePassword
	 * @return 更新件数
	 */
	Integer updatePassword(UpdatePasswordDto updatePassword);
	
	/**
	 * ユーザー名を条件にユーザー件数を取得する
	 * @param userName ユーザー名
	 * @return ユーザー件数
	 */
	Integer selectByUserName(String userName);
	
	/**
	 * メールを条件にユーザー件数を取得する
	 * @param mail メール
	 * @return ユーザー件数
	 * @param mail
	 * @return
	 */
	Integer selectByMail(String mail);
	
	/**
	 * ユーザーidを条件にユーザー情報を取得する
	 * @param userId ユーザーid
	 * @return
	 */
	UserRes selectByUserId(int userId);
	
	/**
	 * ユーザー名を更新する
	 * @param updateUserDto　ユーザー名更新用Dtoクラス
	 * @return 更新件数
	 */
	Integer updateUserName(UpdateUserNameDto updateUserNameDto);
}
