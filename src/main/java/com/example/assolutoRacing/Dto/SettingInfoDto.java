package com.example.assolutoRacing.Dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SettingInfoDto {
	//id
	public Integer id;
	//タイトル
	public String title;
	//車名
	public String carName;
	//メーカー
	public String markerName;
	//車id
	public Integer carId;
	//メーカーid
	public Integer makerId;
	//コースid
	public Integer courseId;
	//コース
	public String course;
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
	public String tireType;
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
	public String memo;
	
}
