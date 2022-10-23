package com.example.assolutoRacing.Bean;

import java.math.BigDecimal;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author nakagawa.so
 * 	更新用設定情報格納クラス
 */
@Data
public class UpdateSettingInfoBean{
	//id
	@NotNull
	public Integer id;
	//タイトル
	@NotBlank
	@Size(max=100,min=1,message="タイトルの見直しをお願い致します。")
	public String title;
	//車id
	@NotNull
	public Integer carId;
	//メーカーid
	@NotNull
	public Integer makerId;
	//コースid
	@NotNull
	public Integer courseId;
	//abs
	public Boolean abs;
	//パワーステリング
	public Integer powerSteering;
	//デブギア
	public BigDecimal diffgear;
	//フロントタイヤの圧力
	public BigDecimal frontTirePressure;
	//リアタイヤの圧力
	public BigDecimal rearTirePressure;
	//タイヤの種類
	@NotNull
	public Integer tireId;
	//空気圧
	public BigDecimal airPressure;
	//ギア比ファイナル
	public BigDecimal gearFinal;
	//ギア比1
	public BigDecimal gearOne;
	//ギア比2
	public BigDecimal gearTwo;
	//ギア比3
	public BigDecimal gearThree;
	//ギア比4
	public BigDecimal gearFour;
	//ギア比5
	public BigDecimal gearFive;
	//スタビライザー前
	public BigDecimal stabiliserAgo;
	//スタビライザー後
	public BigDecimal stabiliserAfter;
	//最大舵角
	public BigDecimal maxRudderAngle;
	//アッカーマンアングル
	public BigDecimal ackermannAngle;
	//キャンバー前
	public BigDecimal camberAgo;
	//キャンバー後
	public BigDecimal camberAfter;
	//ブレーキパワー
	public BigDecimal breakPower;
	//ブレーキバランス
	public BigDecimal breakBallance;
	//車高
	public BigDecimal carHigh;
	//オフセット
	public BigDecimal offset;
	//ホイールサイズ
	public BigDecimal hoilesize;
	//メモ
	@Size(max=700,message="メモの最大文字数を越えています。")
	public String memo;
}
