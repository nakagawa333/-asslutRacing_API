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
import com.example.assolutoRacing.Bean.SelectUserBean;
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
class SelectUserControllerTest{
	private AutoCloseable closeable;
	
	@Autowired
	private SelectUserController selectUserController;
	
	@InjectMocks
	private SelectUserController selectUserControllerMock;
	
	@Mock
	private UserService userService;

	
	@Autowired
	MySQLConnector mySQLConnector;
	
	@BeforeEach
    public void setUp(@Autowired DataSource dataSource) throws Exception {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("selectUserController.sql"));
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

	//正常系 ユーザー名によるユーザー情報検索
	@ParameterizedTest
	@CsvSource({
		"田中太郎",
		"田中",
		"''"
	})
	@Order(1)
	void testSucess1(String userName) throws Exception {
		ResponseEntity<Integer> res = null;
		try {
			res = selectUserController.selectUserByUserName(userName);
		} catch(Exception e) {
			fail();
		}
		
		ResultSet resultSet = mySQLConnector.select("select COUNT(*) from user where user_name='" + userName + "'");
		resultSet.next();
		int userCount = resultSet.getInt(1);
		
		assertThat(res.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
		assertThat(res.getBody()).isEqualTo(userCount);
	}
	
	//正常系 メールによるユーザー情報検索
	@ParameterizedTest
	@CsvSource({
		"nakagawa@mail.com",
		"nakagawa@mail.jp",
		"''"
	})
	@Order(2)
	void testSucess2(String mail) throws Exception {
		ResponseEntity<Integer> res = null;
		try {
			res = selectUserController.selectUserByMail(mail);
		} catch(Exception e) {
			fail();
		}
		
		ResultSet resultSet = mySQLConnector.select("select COUNT(*) from user where mail='" + mail + "'");
		resultSet.next();
		int userCount = resultSet.getInt(1);
		
		assertThat(res.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
		assertThat(res.getBody()).isEqualTo(userCount);
	}
	
	//異常系 ユーザー名によるユーザー情報検索
	@ParameterizedTest
	@Order(3)
	@CsvSource({
		"田中太郎"
	})
	void testError1(String userName) {
		
		doThrow(RuntimeException.class).when(userService).selectByUserName(userName);
		try {
			selectUserControllerMock.selectUserByUserName(userName);
			fail();
		} catch(Exception e) {
			assertTrue(true);
		}
	}
	
	//異常系 メールによるユーザー情報検索
	@ParameterizedTest
	@Order(4)
	@CsvSource({
		"nakagawa@mail.com"
	})
	void testError2(String mail) {
		
		doThrow(RuntimeException.class).when(userService).selectByMail(mail);
		try {
			selectUserControllerMock.selectUserByMail(mail);
			fail();
		} catch(Exception e) {
			assertTrue(true);
		}
	}
	
}
