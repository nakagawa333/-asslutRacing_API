package com.example.assolutoRacing.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

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

import com.example.assolutoRacing.Bean.InfosBean;
import com.example.assolutoRacing.Dto.SelectCarsDto;
import com.example.assolutoRacing.Dto.SelectCourseDto;
import com.example.assolutoRacing.Dto.SelectMakerDto;
import com.example.assolutoRacing.Dto.SelectTireTypeDto;
import com.example.assolutoRacing.Dto.SettingInfoDto;
import com.example.assolutoRacing.Service.InfosService;
import com.example.assolutoRacing.Service.SettingInfoService;
import com.example.assolutoRacing.TestBase.MySQLConnector;

@SpringBootTest
class HomeControllerTest{
	private AutoCloseable closeable;
	
	@Autowired
	private HomeController homeController;
	
	@InjectMocks
	private HomeController homeControllerMock;
	
	@Mock
	SettingInfoService settingInfoService;
	
	@Autowired
	MySQLConnector mySQLConnector;
	
	@BeforeEach
    public void setUp(@Autowired DataSource dataSource) throws Exception {
		
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("homeControllerTest.sql"));
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
    @Order(1)
    @CsvSource({"1"})
	void testSucess1(Integer userId) throws Exception {
		ResponseEntity<List<SettingInfoDto>> resEntity = null;
		
		int resCount = 0;
		try {
			resEntity = homeController.home(userId);
			//HTTPステータスが200 OKであること
			assertThat(HttpStatus.OK.value()).isEqualTo(resEntity.getStatusCodeValue());
			int resEntityCount = 0;
			
			for(SettingInfoDto info:resEntity.getBody()) {
				resEntityCount += 1;
			}
			
			ResultSet res = mySQLConnector.select("select * from setting_info where id = 1");
			
			while(res.next()) {
				resCount += 1;
			}
			
			assertThat(resEntityCount).isEqualTo(resCount);
		} catch(Exception e) {
			fail();
		}
	}
	
	//異常系 設定情報取得失敗
    @ParameterizedTest
    @Order(2)
    @CsvSource({"1"})
	void testError1(Integer userId) {
		try {
			doThrow(new RuntimeException()).when(settingInfoService).selectAll(userId);
			//対象メソッド実施
			homeControllerMock.home(userId);
			fail();
		} catch(Exception e) {
			assertTrue(true);
		}
	}
}
