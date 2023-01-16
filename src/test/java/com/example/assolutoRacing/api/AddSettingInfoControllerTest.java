package com.example.assolutoRacing.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.math.BigDecimal;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.example.assolutoRacing.Bean.AddSettingInfoBean;
import com.example.assolutoRacing.Service.SettingInfoService;
import com.example.assolutoRacing.TestBase.MySQLConnector;

@SpringBootTest
class AddSettingInfoControllerTest{
	private AutoCloseable closeable;
	
	@Autowired
	private AddSettingInfoController addSettingInfoController;
	
	@InjectMocks
	private AddSettingInfoController addSettingInfoControllerMock;
	
	@Mock
	SettingInfoService settingInfoService;
	
	@Autowired
	MySQLConnector mySQLConnector;
	
	@BeforeEach
    public void setUp(@Autowired DataSource dataSource) throws Exception {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("addSettingInfoControllerTest.sql"));
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
	
	@ParameterizedTest
	@Order(1)
	@CsvSource({
		"R35 ニュル仕様,12,5,105,1,100,0,10,13.5,5,13.5,4.4,3.8,2.4,1.9,1.4,1.1,0,1,1,40,-3.4,-1.4,-4,1,0,8,0,17,ニュル仕様2,0,1,2023/01/02 3:01:31,2023/01/02 3:01:31"
	})
	void testSucess1(String title,Integer carId,Integer makerId,Integer courseId,Boolean abs,
			Integer powerSteering,BigDecimal diffgear,BigDecimal frontTirePressure,BigDecimal rearTirePressure,Integer tireId,
			BigDecimal airPressure,BigDecimal gearFinal,BigDecimal gearOne,BigDecimal gearTwo,BigDecimal gearThree,
			BigDecimal gearFour,BigDecimal gearFive,BigDecimal gearSix,BigDecimal stabiliserAgo,BigDecimal stabiliserAfter,
			BigDecimal maxRudderAngle,BigDecimal ackermannAngle,BigDecimal camberAgo,BigDecimal camberAfter,BigDecimal breakPower,
			BigDecimal breakBallance,BigDecimal carHigh,BigDecimal offset,BigDecimal hoilesize,String memo,Integer userId) throws Exception {
		
		AddSettingInfoBean addSettingInfo = new AddSettingInfoBean();
		addSettingInfo.setAbs(abs);
		addSettingInfo.setAckermannAngle(ackermannAngle);
		addSettingInfo.setAirPressure(airPressure);
		addSettingInfo.setBreakBallance(breakBallance);
		addSettingInfo.setBreakPower(breakPower);
		addSettingInfo.setCamberAfter(camberAfter);
		addSettingInfo.setCamberAgo(camberAgo);
		addSettingInfo.setCarHigh(carHigh);
		addSettingInfo.setCarId(carId);
		addSettingInfo.setCourseId(courseId);
		addSettingInfo.setDiffgear(diffgear);
		addSettingInfo.setFrontTirePressure(frontTirePressure);
		addSettingInfo.setGearFinal(gearFinal);
		addSettingInfo.setGearSix(gearSix);
		addSettingInfo.setGearFive(gearFive);
		addSettingInfo.setGearFour(gearFour);
		addSettingInfo.setGearThree(gearThree);
		addSettingInfo.setGearTwo(gearTwo);
		addSettingInfo.setGearOne(gearOne);
		addSettingInfo.setHoilesize(hoilesize);
		addSettingInfo.setMakerId(makerId);
		addSettingInfo.setMaxRudderAngle(maxRudderAngle);
		addSettingInfo.setMemo(memo);
		addSettingInfo.setOffset(offset);
		addSettingInfo.setPowerSteering(powerSteering);
		addSettingInfo.setRearTirePressure(rearTirePressure);
		addSettingInfo.setStabiliserAfter(stabiliserAfter);
		addSettingInfo.setStabiliserAgo(stabiliserAgo);
		addSettingInfo.setTireId(tireId);
		addSettingInfo.setTitle(title);
		addSettingInfo.setUserId(userId);
		
		ResponseEntity<Boolean> resEntity = null;
		
		try {
			resEntity = addSettingInfoController.add(addSettingInfo);
		} catch(Exception e) {
			fail();
		}
		
		if(resEntity == null) fail();
		
		assertThat(resEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(resEntity.getBody()).isEqualTo(true);
		
		String query = "select * from setting_info where title = '" + title + "'";
		ResultSet res = mySQLConnector.select(query);
		
		int insertCount = 0;
		while(res.next()) {
			String str = res.getString("title");
			if(!StringUtils.isEmpty(str)) {
				insertCount += 1;
			}
		}
		
		assertThat(insertCount).isEqualTo(1);
	}
	
	@ParameterizedTest
	@Order(2)
	@CsvSource({
		"R35 ニュル仕様1,12,5,105,1,100,0,10,13.5,5,13.5,4.4,3.8,2.4,1.9,1.4,1.1,0,1,1,40,-3.4,-1.4,-4,1,0,8,0,17,ニュル仕様2,0,1,2023/01/02 3:01:31,2023/01/02 3:01:31"
	})
	void testSucess2(String title,Integer carId,Integer makerId,Integer courseId,Boolean abs,
			Integer powerSteering,BigDecimal diffgear,BigDecimal frontTirePressure,BigDecimal rearTirePressure,Integer tireId,
			BigDecimal airPressure,BigDecimal gearFinal,BigDecimal gearOne,BigDecimal gearTwo,BigDecimal gearThree,
			BigDecimal gearFour,BigDecimal gearFive,BigDecimal gearSix,BigDecimal stabiliserAgo,BigDecimal stabiliserAfter,
			BigDecimal maxRudderAngle,BigDecimal ackermannAngle,BigDecimal camberAgo,BigDecimal camberAfter,BigDecimal breakPower,
			BigDecimal breakBallance,BigDecimal carHigh,BigDecimal offset,BigDecimal hoilesize,String memo,Integer userId) {
		
		AddSettingInfoBean addSettingInfo = new AddSettingInfoBean();
		addSettingInfo.setAbs(abs);
		addSettingInfo.setAckermannAngle(ackermannAngle);
		addSettingInfo.setAirPressure(airPressure);
		addSettingInfo.setBreakBallance(breakBallance);
		addSettingInfo.setBreakPower(breakPower);
		addSettingInfo.setCamberAfter(camberAfter);
		addSettingInfo.setCamberAgo(camberAgo);
		addSettingInfo.setCarHigh(carHigh);
		addSettingInfo.setCarId(carId);
		addSettingInfo.setCourseId(courseId);
		addSettingInfo.setDiffgear(diffgear);
		addSettingInfo.setFrontTirePressure(frontTirePressure);
		addSettingInfo.setGearFinal(gearFinal);
		addSettingInfo.setGearSix(gearSix);
		addSettingInfo.setGearFive(gearFive);
		addSettingInfo.setGearFour(gearFour);
		addSettingInfo.setGearThree(gearThree);
		addSettingInfo.setGearTwo(gearTwo);
		addSettingInfo.setGearOne(gearOne);
		addSettingInfo.setHoilesize(hoilesize);
		addSettingInfo.setMakerId(makerId);
		addSettingInfo.setMaxRudderAngle(maxRudderAngle);
		addSettingInfo.setMemo(memo);
		addSettingInfo.setOffset(offset);
		addSettingInfo.setPowerSteering(powerSteering);
		addSettingInfo.setRearTirePressure(rearTirePressure);
		addSettingInfo.setStabiliserAfter(stabiliserAfter);
		addSettingInfo.setStabiliserAgo(stabiliserAgo);
		addSettingInfo.setTireId(tireId);
		addSettingInfo.setTitle(title);
		addSettingInfo.setUserId(userId);
		
		doReturn(0).when(settingInfoService).insert(any());
		ResponseEntity<Boolean> resEntity = null;
		
		try {
			resEntity = addSettingInfoControllerMock.add(addSettingInfo);
		} catch(Exception e) {
			assertTrue(true);
		}
		
		if(resEntity == null) fail();
		
		assertThat(resEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(resEntity.getBody()).isEqualTo(false);
	}
	
	@ParameterizedTest
	@Order(3)
	@CsvSource({
		"R35 ニュル仕様2,12,5,105,1,100,0,10,13.5,5,13.5,4.4,3.8,2.4,1.9,1.4,1.1,0,1,1,40,-3.4,-1.4,-4,1,0,8,0,17,ニュル仕様2,0,1,2023/01/02 3:01:31,2023/01/02 3:01:31"
	})
	void testError1(String title,Integer carId,Integer makerId,Integer courseId,Boolean abs,
			Integer powerSteering,BigDecimal diffgear,BigDecimal frontTirePressure,BigDecimal rearTirePressure,Integer tireId,
			BigDecimal airPressure,BigDecimal gearFinal,BigDecimal gearOne,BigDecimal gearTwo,BigDecimal gearThree,
			BigDecimal gearFour,BigDecimal gearFive,BigDecimal gearSix,BigDecimal stabiliserAgo,BigDecimal stabiliserAfter,
			BigDecimal maxRudderAngle,BigDecimal ackermannAngle,BigDecimal camberAgo,BigDecimal camberAfter,BigDecimal breakPower,
			BigDecimal breakBallance,BigDecimal carHigh,BigDecimal offset,BigDecimal hoilesize,String memo,Integer userId) {
		
		AddSettingInfoBean addSettingInfo = new AddSettingInfoBean();
		addSettingInfo.setAbs(abs);
		addSettingInfo.setAckermannAngle(ackermannAngle);
		addSettingInfo.setAirPressure(airPressure);
		addSettingInfo.setBreakBallance(breakBallance);
		addSettingInfo.setBreakPower(breakPower);
		addSettingInfo.setCamberAfter(camberAfter);
		addSettingInfo.setCamberAgo(camberAgo);
		addSettingInfo.setCarHigh(carHigh);
		addSettingInfo.setCarId(carId);
		addSettingInfo.setCourseId(courseId);
		addSettingInfo.setDiffgear(diffgear);
		addSettingInfo.setFrontTirePressure(frontTirePressure);
		addSettingInfo.setGearFinal(gearFinal);
		addSettingInfo.setGearSix(gearSix);
		addSettingInfo.setGearFive(gearFive);
		addSettingInfo.setGearFour(gearFour);
		addSettingInfo.setGearThree(gearThree);
		addSettingInfo.setGearTwo(gearTwo);
		addSettingInfo.setGearOne(gearOne);
		addSettingInfo.setHoilesize(hoilesize);
		addSettingInfo.setMakerId(makerId);
		addSettingInfo.setMaxRudderAngle(maxRudderAngle);
		addSettingInfo.setMemo(memo);
		addSettingInfo.setOffset(offset);
		addSettingInfo.setPowerSteering(powerSteering);
		addSettingInfo.setRearTirePressure(rearTirePressure);
		addSettingInfo.setStabiliserAfter(stabiliserAfter);
		addSettingInfo.setStabiliserAgo(stabiliserAgo);
		addSettingInfo.setTireId(tireId);
		addSettingInfo.setTitle(title);
		addSettingInfo.setUserId(userId);
		
		doThrow(new RuntimeException()).when(settingInfoService).insert(any());
		try {
			addSettingInfoControllerMock.add(addSettingInfo);
			fail();
		} catch(Exception e) {
			assertTrue(true);
		}
	}
}
