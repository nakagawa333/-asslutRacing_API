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
import com.example.assolutoRacing.Dto.RegistUserDto;
import com.example.assolutoRacing.Dto.SelectTempUserDto;
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
class VerifyTokenControllerTest{
	private AutoCloseable closeable;
	
	@Autowired
	private VerifyTokenController verifyTokenController;
	
	@InjectMocks
	private VerifyTokenController verifyTokenControllerMock;
	
	@Mock
	UserService userService;
	
	@Mock
	TokenService tokenService;
	
	@Mock
	TempUserService tempUserService;	
	
    @Autowired
    Validator validator;
    
    @Autowired
    MySQLConnector mySQLConnector;
	
	@BeforeEach
    public void setUp(@Autowired DataSource dataSource) throws Exception {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("verifyTokenControllerTest.sql"));
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
		"token"
	})
	@Order(1)
	void testSucess1(String token) throws Exception {
		ResponseEntity<Boolean> res = null;
		
		try {
			res = verifyTokenController.verifyToken(token);
		} catch(Exception e) {
			fail();
		}
		
		assertThat(res.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(res.getBody()).isEqualTo(true);
	}

	//異常系 トークンを条件に仮ユーザー情報を取得
	@ParameterizedTest
	@CsvSource({
		"token"
	})
	@Order(2)
	void testError1(String token) throws Exception {
		ResponseEntity<Boolean> res = null;
		
		doThrow(RuntimeException.class).when(tempUserService).selectByToken(token);
		try {
			res = verifyTokenControllerMock.verifyToken(token);
			fail();
		} catch(Exception e) {
			assertThat(e.getMessage()).isEqualTo("仮ユーザー情報の取得に失敗しました");
		}
	}
	
	//異常系 トークンを条件に仮ユーザー情報を取得
	@ParameterizedTest
	@CsvSource({
		"token"
	})
	@Order(3)
	void testError2(String token) throws Exception {
		ResponseEntity<Boolean> res = null;
		
		doReturn(null).when(tempUserService).selectByToken(token);
		try {
			res = verifyTokenControllerMock.verifyToken(token);
			fail();
		} catch(Exception e) {
			assertThat(e.getMessage()).isEqualTo("仮ユーザーが存在しません");
		}
	}
	
	
	//異常系 トークンを条件に仮ユーザー情報を取得
	@ParameterizedTest
	@CsvSource({
		"token,test@gmail.com,password,テストユーザー"
	})
	@Order(4)
	void testError3(String token,String mail,String password,String userName) throws Exception {
		ResponseEntity<Boolean> res = null;
		
		SelectTempUserDto tempUser = new SelectTempUserDto();
		
		doReturn(tempUser).when(tempUserService).selectByToken(token);
		doThrow(RuntimeException.class).when(userService).insert(any());
		try {
			res = verifyTokenControllerMock.verifyToken(token);
			fail();
		} catch(Exception e) {
			assertThat(e.getMessage()).isEqualTo("ユーザー登録に失敗しました");
		}
	}
	
	//異常系 トークンを条件に仮ユーザー情報を取得
	@ParameterizedTest
	@CsvSource({
		"token,test@gmail.com,password,テストユーザー"
	})
	@Order(5)
	void testError4(String token,String mail,String password,String userName) throws Exception {
		ResponseEntity<Boolean> res = null;
		
		SelectTempUserDto tempUser = new SelectTempUserDto();
		
		doReturn(tempUser).when(tempUserService).selectByToken(token);
		doReturn(0).when(userService).insert(any());

		try {
			res = verifyTokenControllerMock.verifyToken(token);
			fail();
		} catch(Exception e) {
			assertThat(e.getMessage()).isEqualTo("ユーザー登録に失敗しました");
		}
	}
}
