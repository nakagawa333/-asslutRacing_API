package com.example.assolutoRacing.Dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class UpdateSettingInfoDto {
	//id
	private Integer id;
	//タイトル
	private String title;
	//車名
	private String carName;
	//メーカー
	private String markerName;
	//車id
	private Integer carId;
	//メーカーid
	private Integer makerId;
	//コースid
	private Integer courseId;
	//コース
	private String course;
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
	private String memo;
	//ユーザーid
	private Integer userId;
}
