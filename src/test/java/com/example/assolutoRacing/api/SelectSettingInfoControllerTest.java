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

import com.example.assolutoRacing.Bean.AuthUserBean;
import com.example.assolutoRacing.Bean.AuthUserRes;
import com.example.assolutoRacing.Bean.InfosBean;
import com.example.assolutoRacing.Bean.SelectNotificationRes;
import com.example.assolutoRacing.Bean.SelectSettingInfoRes;
import com.example.assolutoRacing.Dto.SelectCarsDto;
import com.example.assolutoRacing.Dto.SelectCourseDto;
import com.example.assolutoRacing.Dto.SelectMakerDto;
import com.example.assolutoRacing.Dto.SelectTireTypeDto;
import com.example.assolutoRacing.Enum.DateUtil;
import com.example.assolutoRacing.Service.InfosService;
import com.example.assolutoRacing.Service.NotificationService;
import com.example.assolutoRacing.Service.SettingInfoService;
import com.example.assolutoRacing.Service.UserService;
import com.example.assolutoRacing.TestBase.MySQLConnector;

@SpringBootTest
class SelectSettingInfoControllerTest{
	private AutoCloseable closeable;
	
	@Autowired
	private SelectSettingInfoController selectSettingInfoController;
	
	@InjectMocks
	private SelectSettingInfoController selectSettingInfoControllerMock;
	
	@Mock
	private SettingInfoService settingInfoService;
	
	@Autowired
	MySQLConnector mySQLConnector;
	
	@BeforeEach
    public void setUp(@Autowired DataSource dataSource) throws Exception {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("selectNotificationControllerTest.sql"));
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

	//正常系
	@ParameterizedTest
	@CsvSource({
		"1,R35 ニュル仕様,12,5,105,true,100,0.0,10.0,13.5,5,13.5,4.40,3.80,2.40,1.90,1.40,1.10,0.00,1.0,1.0,40.0,-3.4,-1.4,-4.0,1.0,0.0,8.0,0.0,17.0,ニュル仕様2,1,1"
	})
	@Order(1)
	void testSucess1(int id,String title,Integer makerId,Integer courseId,Integer carId,Boolean abs,
			Integer powerSteering,BigDecimal diffgear,BigDecimal frontTirePressure,BigDecimal rearTirePressure,Integer tireId,
			BigDecimal airPressure,BigDecimal gearFinal,BigDecimal gearOne,BigDecimal gearTwo,BigDecimal gearThree,
			BigDecimal gearFour,BigDecimal gearFive,BigDecimal gearSix,BigDecimal stabiliserAgo,BigDecimal stabiliserAfter,
			BigDecimal maxRudderAngle,BigDecimal ackermannAngle,BigDecimal camberAgo,BigDecimal camberAfter,BigDecimal breakPower,
			BigDecimal breakBallance,BigDecimal carHigh,BigDecimal offset,BigDecimal hoilesize,String memo,Integer userId) throws Exception {
		SelectSettingInfoRes selectSettingInfoRes = new SelectSettingInfoRes();
		selectSettingInfoRes.setId(id);
		selectSettingInfoRes.setAbs(abs);
		selectSettingInfoRes.setAckermannAngle(ackermannAngle);
		selectSettingInfoRes.setAirPressure(airPressure);
		selectSettingInfoRes.setBreakBallance(breakBallance);
		selectSettingInfoRes.setBreakPower(breakPower);
		selectSettingInfoRes.setCamberAfter(camberAfter);
		selectSettingInfoRes.setCamberAgo(camberAgo);
		selectSettingInfoRes.setCarHigh(carHigh);
		selectSettingInfoRes.setCarId(carId);
		selectSettingInfoRes.setCourseId(courseId);
		selectSettingInfoRes.setDiffgear(diffgear);
		selectSettingInfoRes.setFrontTirePressure(frontTirePressure);
		selectSettingInfoRes.setGearFinal(gearFinal);
		selectSettingInfoRes.setGearSix(gearSix);
		selectSettingInfoRes.setGearFive(gearFive);
		selectSettingInfoRes.setGearFour(gearFour);
		selectSettingInfoRes.setGearThree(gearThree);
		selectSettingInfoRes.setGearTwo(gearTwo);
		selectSettingInfoRes.setGearOne(gearOne);
		selectSettingInfoRes.setHoilesize(hoilesize);
		selectSettingInfoRes.setMakerId(makerId);
		selectSettingInfoRes.setMaxRudderAngle(maxRudderAngle);
		selectSettingInfoRes.setMemo(memo);
		selectSettingInfoRes.setOffset(offset);
		selectSettingInfoRes.setPowerSteering(powerSteering);
		selectSettingInfoRes.setRearTirePressure(rearTirePressure);
		selectSettingInfoRes.setStabiliserAfter(stabiliserAfter);
		selectSettingInfoRes.setStabiliserAgo(stabiliserAgo);
		selectSettingInfoRes.setTireId(tireId);
		selectSettingInfoRes.setTitle(title);
		selectSettingInfoRes.setUserId(userId);		
		
		ResponseEntity<SelectSettingInfoRes> res = null;
		try {
			res = selectSettingInfoController.selectSettingInfoById(id);
		} catch(Exception e) {
			fail();
		}
		
		assertThat(res.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
		assertThat(res.getBody()).isEqualTo(selectSettingInfoRes);
	}
	
	//異常系
	@ParameterizedTest
	@Order(2)
	@CsvSource({
		"1"
	})
	void testError1(int id) {
		doThrow(RuntimeException.class).when(settingInfoService).selectSettingInfoById(id);
		try {
			selectSettingInfoControllerMock.selectSettingInfoById(id);
			fail();
		} catch(Exception e) {
			assertTrue(true);
		}
	}
}
