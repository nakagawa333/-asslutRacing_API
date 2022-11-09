package com.example.assolutoRacing.Bean;

import java.math.BigDecimal;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author nakagawa.so
 * 登録用設定情報格納クラス
 */
@Data
public class AddSettingInfoBean{
	//タイトル
	@NotBlank
	@Size(max=100,min=1,message="タイトルは1文字以上100文字以下でお願い致します")
	private String title;
	//車id
	@NotNull
	private Integer carId;
	//メーカーid
	@NotNull
	private Integer makerId;
	//コースid
	@NotNull
	private Integer courseId;
	//abs
	private Boolean abs;
	//パワーステリング
	private Integer powerSteering;
	//デブギア
	private BigDecimal diffgear;
	//フロントタイヤの圧力
	private BigDecimal frontTirePressure;
	//リアタイヤの圧力
	private BigDecimal rearTirePressure;
	//タイヤの種類
	@NotNull
	private Integer tireId;
	//空気圧
	private BigDecimal airPressure;
	//ギア比ファイナル
	private BigDecimal gearFinal;
	//ギア比1
	private BigDecimal gearOne;
	//ギア比2
	private BigDecimal gearTwo;
	//ギア比3
	private BigDecimal gearThree;
	//ギア比4
	private BigDecimal gearFour;
	//ギア比5
	private BigDecimal gearFive;
	//スタビライザー前
	private BigDecimal stabiliserAgo;
	//スタビライザー後
	private BigDecimal stabiliserAfter;
	//最大舵角
	private BigDecimal maxRudderAngle;
	//アッカーマンアングル
	private BigDecimal ackermannAngle;
	//キャンバー前
	private BigDecimal camberAgo;
	//キャンバー後
	private BigDecimal camberAfter;
	//ブレーキパワー
	private BigDecimal breakPower;
	//ブレーキバランス
	private BigDecimal breakBallance;
	//車高
	private BigDecimal carHigh;
	//オフセット
	private BigDecimal offset;
	//ホイールサイズ
	private BigDecimal hoilesize;
	//メモ
	@Size(max=700,message="メモは、700文字以内でお願い致します")
	private String memo;
	//ユーザーid
	@NotNull
	private Integer userId;
}
