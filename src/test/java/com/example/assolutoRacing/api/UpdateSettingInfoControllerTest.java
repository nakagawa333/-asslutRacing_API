package com.example.assolutoRacing.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.amazonaws.services.simpleemail.model.AccountSendingPausedException;
import com.amazonaws.services.simpleemail.model.ConfigurationSetDoesNotExistException;
import com.amazonaws.services.simpleemail.model.ConfigurationSetSendingPausedException;
import com.amazonaws.services.simpleemail.model.MailFromDomainNotVerifiedException;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.example.assolutoRacing.Bean.SendPasswordRestMailBean;
import com.example.assolutoRacing.Bean.TempUserBean;
import com.example.assolutoRacing.Bean.UpdatePasswordBean;
import com.example.assolutoRacing.Bean.UpdateSettingInfoBean;
import com.example.assolutoRacing.Dto.RegistPasswordResetDto;
import com.example.assolutoRacing.Dto.SettingInfoDto;
import com.example.assolutoRacing.Dto.UpdateSettingInfoDto;
import com.example.assolutoRacing.Service.MailService;
import com.example.assolutoRacing.Service.PasswordResetService;
import com.example.assolutoRacing.Service.SettingInfoService;
import com.example.assolutoRacing.Service.TempUserService;
import com.example.assolutoRacing.Service.TokenService;
import com.example.assolutoRacing.Service.UserService;
import com.example.assolutoRacing.TestBase.MySQLConnector;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@SpringBootTest
class UpdateSettingInfoControllerTest{
	private AutoCloseable closeable;
	
	@Autowired
	private UpdateSettingInfoController updateSettingInfoController;
	
	@InjectMocks
	private UpdateSettingInfoController updateSettingInfoControllerMock;
	
	@Mock
	SettingInfoService settingInfoService;
	
    @Autowired
    Validator validator;
    
    @Autowired
    MySQLConnector mySQLConnector;
	
	@BeforeEach
    public void setUp(@Autowired DataSource dataSource) throws Exception {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("updateSettingInfoControllerTest.sql"));
		//テスト用の SQL ファイル
		populator.execute(dataSource);
		//データベースに接続
		mySQLConnector.getConnection();
    	closeable = MockitoAnnotations.openMocks(this);
    }
    
    @AfterEach
    void tearDown() throws Exception {
    	//データベースの接続を閉じる
    	mySQLConnector.close();
        closeable.close();
    }

	//正常系 パスワード更新成功
	@ParameterizedTest
	@CsvSource({
		"1,R35 ニュル仕様,12,5,20,1,100,0,10.0,13.5,5,13.5,4.40,3.80,2.40,1.90,1.40,1.10,0,1,1,40.0,-3.4,-1.4,-4.0,1.0,0.0,8.0,0.0,17.0,ニュル仕様2,1"
	})
	@Order(1)
	void testSucess1(int id,String title,Integer carId,Integer makerId,Integer courseId,Boolean abs,
			Integer powerSteering,BigDecimal diffgear,BigDecimal frontTirePressure,BigDecimal rearTirePressure,Integer tireId,
			BigDecimal airPressure,BigDecimal gearFinal,BigDecimal gearOne,BigDecimal gearTwo,BigDecimal gearThree,
			BigDecimal gearFour,BigDecimal gearFive,BigDecimal gearSix,BigDecimal stabiliserAgo,BigDecimal stabiliserAfter,
			BigDecimal maxRudderAngle,BigDecimal ackermannAngle,BigDecimal camberAgo,BigDecimal camberAfter,BigDecimal breakPower,
			BigDecimal breakBallance,BigDecimal carHigh,BigDecimal offset,BigDecimal hoilesize,String memo,Integer userId) throws Exception {
		
		UpdateSettingInfoBean updateSettingInfo = new UpdateSettingInfoBean();
		updateSettingInfo.setId(id);
		updateSettingInfo.setAbs(abs);
		updateSettingInfo.setAckermannAngle(ackermannAngle);
		updateSettingInfo.setAirPressure(airPressure);
		updateSettingInfo.setBreakBallance(breakBallance);
		updateSettingInfo.setBreakPower(breakPower);
		updateSettingInfo.setCamberAfter(camberAfter);
		updateSettingInfo.setCamberAgo(camberAgo);
		updateSettingInfo.setCarHigh(carHigh);
		updateSettingInfo.setCarId(carId);
		updateSettingInfo.setCourseId(courseId);
		updateSettingInfo.setDiffgear(diffgear);
		updateSettingInfo.setFrontTirePressure(frontTirePressure);
		updateSettingInfo.setGearFinal(gearFinal);
		updateSettingInfo.setGearSix(gearSix);
		updateSettingInfo.setGearFive(gearFive);
		updateSettingInfo.setGearFour(gearFour);
		updateSettingInfo.setGearThree(gearThree);
		updateSettingInfo.setGearTwo(gearTwo);
		updateSettingInfo.setGearOne(gearOne);
		updateSettingInfo.setHoilesize(hoilesize);
		updateSettingInfo.setMakerId(makerId);
		updateSettingInfo.setMaxRudderAngle(maxRudderAngle);
		updateSettingInfo.setMemo(memo);
		updateSettingInfo.setOffset(offset);
		updateSettingInfo.setPowerSteering(powerSteering);
		updateSettingInfo.setRearTirePressure(rearTirePressure);
		updateSettingInfo.setStabiliserAfter(stabiliserAfter);
		updateSettingInfo.setStabiliserAgo(stabiliserAgo);
		updateSettingInfo.setTireId(tireId);
		updateSettingInfo.setTitle(title);
		updateSettingInfo.setUserId(userId);
		
		ResponseEntity<Boolean> res = null;
		try {
			res = updateSettingInfoController.update(updateSettingInfo);
		} catch(Exception e) {
			fail();
		}
		
		//途中で検証が失敗しても残りの検証を続行させる
		SoftAssertions softly = new SoftAssertions();
		
		SettingInfoDto settingInfo = new SettingInfoDto();
		
		String query = "select"
				+ " id,"
				+ " title,"
				+ " car_id,"
				+ " maker_id,"
				+ " course_id,"
				+ " tire_id,"
				+ " abs,"
				+ " power_steering,"
				+ " diffgear,"
				+ " frontTire_pressure,"
				+ " rearTire_pressure,"
				+ " air_pressure,"
				+ " gear_final,"
				+ " gear_one,"
				+ " gear_two,"
				+ " gear_three,"
				+ " gear_four,"
				+ " gear_five,"
				+ " gear_six,"
				+ " stabiliser_ago,"
				+ " stabiliser_after,"
				+ " max_rudder_angle,"
				+ " ackermann_angle,"
				+ " camber_ago,"
				+ " camber_after,"
				+ " break_power,"
				+ " break_ballance,"
				+ " car_high,"
				+ " off_set,"
				+ " hoilesize,"
				+ " user_id,"
				+ " memo"
				+ " from setting_info"
				+ " where id = " + id;
		ResultSet resultSet = mySQLConnector.select(query);
		
		resultSet.next();
		softly.assertThat(id).isEqualTo(resultSet.getInt("id"));
		softly.assertThat(abs).isEqualTo(resultSet.getBoolean("abs"));
		softly.assertThat(ackermannAngle).isEqualTo(resultSet.getBigDecimal("ackermann_angle"));
		softly.assertThat(airPressure).isEqualTo(resultSet.getBigDecimal("air_pressure"));
		softly.assertThat(breakBallance).isEqualTo(resultSet.getBigDecimal("break_ballance"));
		softly.assertThat(breakPower).isEqualTo(resultSet.getBigDecimal("break_power"));
		softly.assertThat(camberAfter).isEqualTo(resultSet.getBigDecimal("camber_after"));
		softly.assertThat(camberAgo).isEqualTo(resultSet.getBigDecimal("camber_ago"));
		softly.assertThat(carHigh).isEqualTo(resultSet.getBigDecimal("car_high"));
		softly.assertThat(carId).isEqualTo(resultSet.getInt("car_id"));
		softly.assertThat(courseId).isEqualTo(resultSet.getInt("course_id"));
		softly.assertThat(diffgear).isEqualTo(resultSet.getBigDecimal("diffgear"));
		softly.assertThat(frontTirePressure).isEqualTo(resultSet.getBigDecimal("frontTire_pressure"));
		softly.assertThat(gearFinal).isEqualTo(resultSet.getBigDecimal("gear_final"));
		softly.assertThat(gearSix).isEqualTo(resultSet.getBigDecimal("gear_six"));
		softly.assertThat(gearFive).isEqualTo(resultSet.getBigDecimal("gear_five"));
		softly.assertThat(gearFour).isEqualTo(resultSet.getBigDecimal("gear_four"));
		softly.assertThat(gearThree).isEqualTo(resultSet.getBigDecimal("gear_three"));
		softly.assertThat(gearTwo).isEqualTo(resultSet.getBigDecimal("gear_two"));
		softly.assertThat(gearOne).isEqualTo(resultSet.getBigDecimal("gear_one"));
		softly.assertThat(hoilesize).isEqualTo(resultSet.getBigDecimal("hoilesize"));
		softly.assertThat(makerId).isEqualTo(resultSet.getInt("maker_id"));
		softly.assertThat(maxRudderAngle).isEqualTo(resultSet.getBigDecimal("max_rudder_angle"));
		softly.assertThat(memo).isEqualTo(resultSet.getString("memo"));
		softly.assertThat(offset).isEqualTo(resultSet.getBigDecimal("off_set"));
		softly.assertThat(powerSteering).isEqualTo(resultSet.getInt("power_steering"));
		softly.assertThat(rearTirePressure).isEqualTo(resultSet.getBigDecimal("rearTire_pressure"));
		softly.assertThat(stabiliserAfter).isEqualTo(resultSet.getBigDecimal("stabiliser_after"));
		softly.assertThat(stabiliserAgo).isEqualTo(resultSet.getBigDecimal("stabiliser_ago"));
		softly.assertThat(title).isEqualTo(resultSet.getString("title"));
		softly.assertThat(userId).isEqualTo(resultSet.getInt("user_id"));
		
		softly.assertThat(res.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
		softly.assertThat(res.getBody()).isEqualTo(true);
		softly.assertAll();
	}
	
	//正常系 該当するユーザーが存在しない
	@ParameterizedTest
	@CsvSource({
		"2,R35 ニュル仕様,12,5,105,1,100,0,10,13.5,5,13.5,4.4,3.8,2.4,1.9,1.4,1.1,0,1,1,40,-3.4,-1.4,-4,1,0,8,0,17,ニュル仕様2,0,1,2023/01/02 3:01:31,2023/01/02 3:01:31"
	})
	@Order(2)
	void testSucess2(int id,String title,Integer carId,Integer makerId,Integer courseId,Boolean abs,
			Integer powerSteering,BigDecimal diffgear,BigDecimal frontTirePressure,BigDecimal rearTirePressure,Integer tireId,
			BigDecimal airPressure,BigDecimal gearFinal,BigDecimal gearOne,BigDecimal gearTwo,BigDecimal gearThree,
			BigDecimal gearFour,BigDecimal gearFive,BigDecimal gearSix,BigDecimal stabiliserAgo,BigDecimal stabiliserAfter,
			BigDecimal maxRudderAngle,BigDecimal ackermannAngle,BigDecimal camberAgo,BigDecimal camberAfter,BigDecimal breakPower,
			BigDecimal breakBallance,BigDecimal carHigh,BigDecimal offset,BigDecimal hoilesize,String memo,Integer userId) throws Exception {
		
		UpdateSettingInfoBean updateSettingInfo = new UpdateSettingInfoBean();
		updateSettingInfo.setAbs(abs);
		updateSettingInfo.setAckermannAngle(ackermannAngle);
		updateSettingInfo.setAirPressure(airPressure);
		updateSettingInfo.setBreakBallance(breakBallance);
		updateSettingInfo.setBreakPower(breakPower);
		updateSettingInfo.setCamberAfter(camberAfter);
		updateSettingInfo.setCamberAgo(camberAgo);
		updateSettingInfo.setCarHigh(carHigh);
		updateSettingInfo.setCarId(carId);
		updateSettingInfo.setCourseId(courseId);
		updateSettingInfo.setDiffgear(diffgear);
		updateSettingInfo.setFrontTirePressure(frontTirePressure);
		updateSettingInfo.setGearFinal(gearFinal);
		updateSettingInfo.setGearSix(gearSix);
		updateSettingInfo.setGearFive(gearFive);
		updateSettingInfo.setGearFour(gearFour);
		updateSettingInfo.setGearThree(gearThree);
		updateSettingInfo.setGearTwo(gearTwo);
		updateSettingInfo.setGearOne(gearOne);
		updateSettingInfo.setHoilesize(hoilesize);
		updateSettingInfo.setMakerId(makerId);
		updateSettingInfo.setMaxRudderAngle(maxRudderAngle);
		updateSettingInfo.setMemo(memo);
		updateSettingInfo.setOffset(offset);
		updateSettingInfo.setPowerSteering(powerSteering);
		updateSettingInfo.setRearTirePressure(rearTirePressure);
		updateSettingInfo.setStabiliserAfter(stabiliserAfter);
		updateSettingInfo.setStabiliserAgo(stabiliserAgo);
		updateSettingInfo.setTireId(tireId);
		updateSettingInfo.setTitle(title);
		updateSettingInfo.setUserId(userId);
		
		ResponseEntity<Boolean> res = null;
		try {
			res = updateSettingInfoController.update(updateSettingInfo);
		} catch(Exception e) {
			fail();
		}
		
		assertThat(res.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(res.getBody()).isEqualTo(false);
	}
	
	//異常系 設定情報更新失敗
	@ParameterizedTest
	@CsvSource({
		"2,R35 ニュル仕様,12,5,105,1,100,0,10,13.5,5,13.5,4.4,3.8,2.4,1.9,1.4,1.1,0,1,1,40,-3.4,-1.4,-4,1,0,8,0,17,ニュル仕様2,0,1,2023/01/02 3:01:31,2023/01/02 3:01:31"
	})
	@Order(3)
	void testError1(int id,String title,Integer carId,Integer makerId,Integer courseId,Boolean abs,
			Integer powerSteering,BigDecimal diffgear,BigDecimal frontTirePressure,BigDecimal rearTirePressure,Integer tireId,
			BigDecimal airPressure,BigDecimal gearFinal,BigDecimal gearOne,BigDecimal gearTwo,BigDecimal gearThree,
			BigDecimal gearFour,BigDecimal gearFive,BigDecimal gearSix,BigDecimal stabiliserAgo,BigDecimal stabiliserAfter,
			BigDecimal maxRudderAngle,BigDecimal ackermannAngle,BigDecimal camberAgo,BigDecimal camberAfter,BigDecimal breakPower,
			BigDecimal breakBallance,BigDecimal carHigh,BigDecimal offset,BigDecimal hoilesize,String memo,Integer userId) throws Exception {
		
		UpdateSettingInfoBean updateSettingInfo = new UpdateSettingInfoBean();
		updateSettingInfo.setId(id);
		updateSettingInfo.setAbs(abs);
		updateSettingInfo.setAckermannAngle(ackermannAngle);
		updateSettingInfo.setAirPressure(airPressure);
		updateSettingInfo.setBreakBallance(breakBallance);
		updateSettingInfo.setBreakPower(breakPower);
		updateSettingInfo.setCamberAfter(camberAfter);
		updateSettingInfo.setCamberAgo(camberAgo);
		updateSettingInfo.setCarHigh(carHigh);
		updateSettingInfo.setCarId(carId);
		updateSettingInfo.setCourseId(courseId);
		updateSettingInfo.setDiffgear(diffgear);
		updateSettingInfo.setFrontTirePressure(frontTirePressure);
		updateSettingInfo.setGearFinal(gearFinal);
		updateSettingInfo.setGearSix(gearSix);
		updateSettingInfo.setGearFive(gearFive);
		updateSettingInfo.setGearFour(gearFour);
		updateSettingInfo.setGearThree(gearThree);
		updateSettingInfo.setGearTwo(gearTwo);
		updateSettingInfo.setGearOne(gearOne);
		updateSettingInfo.setHoilesize(hoilesize);
		updateSettingInfo.setMakerId(makerId);
		updateSettingInfo.setMaxRudderAngle(maxRudderAngle);
		updateSettingInfo.setMemo(memo);
		updateSettingInfo.setOffset(offset);
		updateSettingInfo.setPowerSteering(powerSteering);
		updateSettingInfo.setRearTirePressure(rearTirePressure);
		updateSettingInfo.setStabiliserAfter(stabiliserAfter);
		updateSettingInfo.setStabiliserAgo(stabiliserAgo);
		updateSettingInfo.setTireId(tireId);
		updateSettingInfo.setTitle(title);
		updateSettingInfo.setUserId(userId);
		
		doThrow(RuntimeException.class).when(settingInfoService).update(any());
		try {
			updateSettingInfoControllerMock.update(updateSettingInfo);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("設定情報の更新に失敗しました");
		}
	}
}
