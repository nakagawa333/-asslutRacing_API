package com.example.assolutoRacing.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.sql.ResultSet;
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
import com.example.assolutoRacing.Dto.SelectCarsDto;
import com.example.assolutoRacing.Dto.SelectCourseDto;
import com.example.assolutoRacing.Dto.SelectMakerDto;
import com.example.assolutoRacing.Dto.SelectTireTypeDto;
import com.example.assolutoRacing.Service.InfosService;
import com.example.assolutoRacing.Service.UserService;
import com.example.assolutoRacing.TestBase.MySQLConnector;

@SpringBootTest
class LoginControllerTest{
	private AutoCloseable closeable;
	
	@Autowired
	private LoginController loginController;
	
	@InjectMocks
	private LoginController loginControllerMock;
	
	@Mock
	private UserService userService;
	
	@Autowired
	MySQLConnector mySQLConnector;
	
	@BeforeEach
    public void setUp(@Autowired DataSource dataSource) throws Exception {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("loginControllerTest.sql"));
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
	@CsvSource({
		"田中太郎,test@mail.com,password,1,田中太郎",
		"'',test@mail.com,password,1,田中太郎",
		"田中太郎,'',password,1,田中太郎"
	})
	void testSucess1(String userName,String mail,String password,int expectedUserId,String expectedUserName) throws Exception {
		AuthUserBean authUserBean = new AuthUserBean();
		authUserBean.setUserName(userName);
		authUserBean.setMail(mail);
		authUserBean.setPassword(password);
		
		String expectedPassword = DigestUtils.sha256Hex(password);
		
		AuthUserRes authUserRes = new AuthUserRes();
		authUserRes.setPassword(expectedPassword);
		authUserRes.setUserId(expectedUserId);
		authUserRes.setUserName(expectedUserName);
		
		ResponseEntity<AuthUserRes> resEntity = null;
		
		try {
			resEntity = loginController.login(authUserBean);
		} catch(Exception e) {
			fail();
		}
		resEntity = loginController.login(authUserBean);
		assertThat(resEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
		assertThat(resEntity.getBody()).isEqualTo(authUserRes);
	}
	
	//異常系 ユーザー認証失敗
	@ParameterizedTest
	@Order(2)
	@CsvSource({"田中太郎,test@mail.com,password"})
	void testFail1(String userName,String mail,String password) {
		AuthUserBean authUserBean = new AuthUserBean();
		authUserBean.setUserName(userName);
		authUserBean.setMail(mail);
		authUserBean.setPassword(password);
		
		try {
			doThrow(RuntimeException.class).when(userService).auth(any());
			loginControllerMock.login(authUserBean);
			fail();
		} catch(Exception e) {
			assertTrue(true);
		}
	}
	
	//異常系 ユーザー名とパスワードが空
	@ParameterizedTest
	@Order(3)
	@CsvSource({"'','',''"})
	void testFail2(String userName,String mail,String password) {
		AuthUserBean authUserBean = new AuthUserBean();
		authUserBean.setUserName(userName);
		authUserBean.setMail(mail);
		authUserBean.setPassword(password);
		
		try {
			loginControllerMock.login(authUserBean);
		} catch(Exception e) {
			assertTrue(true);
		}
	}
}
