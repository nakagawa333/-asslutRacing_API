package com.example.assolutoRacing.Bean;

import java.math.BigDecimal;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author nakagawa.so
 * 設定情報レスポンスクラス
 */
@Data
public class SelectSettingInfoRes{
	//id
	private Integer id;
	//タイトル
	private String title;
	//車id
	private Integer carId;
	//メーカーid
	private Integer makerId;
	//コースid
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
	//ギア比6
	private BigDecimal gearSix;
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
