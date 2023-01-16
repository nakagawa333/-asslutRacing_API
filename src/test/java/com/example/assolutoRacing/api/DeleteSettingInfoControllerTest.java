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
import org.springframework.test.context.jdbc.Sql;

import com.example.assolutoRacing.Bean.AddSettingInfoBean;
import com.example.assolutoRacing.Service.SettingInfoService;
import com.example.assolutoRacing.TestBase.MySQLConnector;
import com.example.assolutoRacing.api.DeleteSettingInfoController.Request;

@SpringBootTest
class DeleteSettingInfoControllerTest{
	private AutoCloseable closeable;
	
	@Autowired
	private DeleteSettingInfoController deleteSettingInfoController;
	
	@InjectMocks
	private DeleteSettingInfoController deleteSettingInfoControllerMock;
	
	@Mock
	SettingInfoService settingInfoService;
	
	@Autowired
	MySQLConnector mySQLConnector;
	
	@BeforeEach
    public void setUp(@Autowired DataSource dataSource) throws Exception {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("deleteSettingInfoControllerTest.sql"));
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
	
    //正常終了 該当ユーザーあり
	@ParameterizedTest
	@Order(1)
	@CsvSource({
		"1"
	})
	void testSucess1(int id) throws Exception {
		Request req = new Request();
		req.setId(id);
		
		ResponseEntity<Boolean> resEntity = null;
		try {
			resEntity = deleteSettingInfoController.delete(req);
		} catch(Exception e) {
			fail();	
		}
		
		assertThat(resEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
		assertThat(resEntity.getBody()).isEqualTo(true);
	}
	
	//正常終了 該当削除ユーザーなし
	@ParameterizedTest
	@Order(2)
	@CsvSource({
		"2"
	})
	void testfail1(int id) throws Exception {
		Request req = new Request();
		req.setId(id);
		
		ResponseEntity<Boolean> resEntity = null;
		
		try {
			resEntity = deleteSettingInfoController.delete(req);
		} catch(Exception e) {
			fail();	
		}
		
		assertThat(resEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
		assertThat(resEntity.getBody()).isEqualTo(false);
	}
	
	//異常終了 該当削除ユーザーなし
	@ParameterizedTest
	@Order(3)
	@CsvSource({
		"1"
	})
	void testSucess2(int id) throws Exception {
		Request req = new Request();
		req.setId(id);
		
		doThrow(RuntimeException.class).when(settingInfoService).deleteOne(id);

		ResponseEntity<Boolean> resEntity = null;
		try {
			resEntity = deleteSettingInfoControllerMock.delete(req);
			fail();
		} catch(Exception e) {
			assertTrue(true);	
		}
	}	
}
