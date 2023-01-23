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

import com.amazonaws.services.simpleemail.model.AccountSendingPausedException;
import com.amazonaws.services.simpleemail.model.ConfigurationSetDoesNotExistException;
import com.amazonaws.services.simpleemail.model.ConfigurationSetSendingPausedException;
import com.amazonaws.services.simpleemail.model.MailFromDomainNotVerifiedException;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.example.assolutoRacing.Bean.SendPasswordRestMailBean;
import com.example.assolutoRacing.Bean.TempUserBean;
import com.example.assolutoRacing.Dto.RegistPasswordResetDto;
import com.example.assolutoRacing.Service.MailService;
import com.example.assolutoRacing.Service.PasswordResetService;
import com.example.assolutoRacing.Service.TempUserService;
import com.example.assolutoRacing.Service.TokenService;
import com.example.assolutoRacing.Service.UserService;
import com.example.assolutoRacing.TestBase.MySQLConnector;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@SpringBootTest
class SignupControllerTest{
	private AutoCloseable closeable;
	
	@Autowired
	private SignupController signupController;
	
	@InjectMocks
	private SignupController signupControllerMock;
	
	@Mock
	UserService userService;
	
	@Mock
	TokenService tokenService;
	
	@Mock
	MailService mailService;
	
	@Mock
	TempUserService tempUserService;
	
	@Mock
	PasswordResetService passwordResetService;
	
	@Autowired
	MySQLConnector mySQLConnector;
	
    @Autowired
    Validator validator;
	
	@BeforeEach
    public void setUp(@Autowired DataSource dataSource) throws Exception {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("signupControllerTest.sql"));
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

	//正常系 パスワードリセットメール送信
	@ParameterizedTest
	@CsvSource({
		"田中太郎,assolutoracingses@gmail.com,password,http:localhost:4200"
	})
	@Order(1)
	void testSucess1(String userName,String mail,String password,String requestUrl) throws Exception {
		TempUserBean tempUserBean = new TempUserBean();
		tempUserBean.setUserName(userName);
		tempUserBean.setMail(mail);
		tempUserBean.setPassword(password);
		tempUserBean.setRequestUrl(requestUrl);
		
		try {
			signupController.signup(tempUserBean);
			assertTrue(true);
		} catch(Exception e) {
			fail();
		}
	}
	
	//正常系 パスワードリセットメール送信
	@ParameterizedTest
	@CsvSource({
		"田中太郎,assolutoracingses@gmail.com,password,http:localhost:4200"
	})
	@Order(2)
	void testSucess2(String userName,String mail,String password,String requestUrl) {
		TempUserBean tempUserBean = new TempUserBean();
		tempUserBean.setUserName(userName);
		tempUserBean.setMail(mail);
		tempUserBean.setPassword(password);
		tempUserBean.setRequestUrl(requestUrl);
		
		doReturn("token").when(tokenService).createToken(mail);
		
		doReturn(2).when(tempUserService).insert(any());
		
		try {
			signupControllerMock.signup(tempUserBean);
			assertThat(true);
		} catch(Exception e) {
			fail();
		}		
	}
	
	
	//異常系 トークン生成失敗
	@ParameterizedTest
	@CsvSource({
		"田中太郎,assolutoracingses@gmail.com,password,http:localhost:4200"
	})
	@Order(3)
	void testError1(String userName,String mail,String password,String requestUrl) throws Exception {
		TempUserBean tempUserBean = new TempUserBean();
		tempUserBean.setUserName(userName);
		tempUserBean.setMail(mail);
		tempUserBean.setPassword(password);
		tempUserBean.setRequestUrl(requestUrl);
		
		doThrow(RuntimeException.class).when(tokenService).createToken(mail);

		try {
			signupControllerMock.signup(tempUserBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("トークン生成に失敗しました");
		}
	}
	
	//異常系 仮ユーザー登録失敗
	@ParameterizedTest
	@CsvSource({
		"田中太郎,assolutoracingses@gmail.com,password,http:localhost:4200"
	})
	@Order(4)
	void testError2(String userName,String mail,String password,String requestUrl) throws Exception {
		TempUserBean tempUserBean = new TempUserBean();
		tempUserBean.setUserName(userName);
		tempUserBean.setMail(mail);
		tempUserBean.setPassword(password);
		tempUserBean.setRequestUrl(requestUrl);
		
		doReturn("token").when(tokenService).createToken(mail);
		
		doThrow(RuntimeException.class).when(tempUserService).insert(any());
		try {
			signupControllerMock.signup(tempUserBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("仮ユーザーの登録に失敗しました");
		}
	}
	
	//異常系 仮ユーザー登録失敗
	@ParameterizedTest
	@CsvSource({
		"田中太郎,assolutoracingses@gmail.com,password,http:localhost:4200"
	})
	@Order(5)
	void testError3(String userName,String mail,String password,String requestUrl) throws Exception {
		TempUserBean tempUserBean = new TempUserBean();
		tempUserBean.setUserName(userName);
		tempUserBean.setMail(mail);
		tempUserBean.setPassword(password);
		tempUserBean.setRequestUrl(requestUrl);
		
		doReturn("token").when(tokenService).createToken(mail);
		
		doReturn(0).when(tempUserService).insert(any());
		try {
			signupControllerMock.signup(tempUserBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("仮ユーザーの登録に失敗しました");
		}
	}
	
	//異常系 仮ユーザー登録失敗
	@ParameterizedTest
	@CsvSource({
		"田中太郎,assolutoracingses@gmail.com,password,http:localhost:4200"
	})
	@Order(6)
	void testError4(String userName,String mail,String password,String requestUrl) throws Exception {
		TempUserBean tempUserBean = new TempUserBean();
		tempUserBean.setUserName(userName);
		tempUserBean.setMail(mail);
		tempUserBean.setPassword(password);
		tempUserBean.setRequestUrl(requestUrl);
		
		doReturn("token").when(tokenService).createToken(mail);
		
		doReturn(1).when(tempUserService).insert(any());
		
		doThrow(MessageRejectedException.class).when(mailService).send(any(), any(), any(), any());
		try {
			signupControllerMock.signup(tempUserBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("メッセージ送信に失敗しました");
		}
	}
	
	//異常系 MXレコードが読み込まれなかった
	@ParameterizedTest
	@CsvSource({
		"田中太郎,assolutoracingses@gmail.com,password,http:localhost:4200"
	})
	@Order(7)
	void testError5(String userName,String mail,String password,String requestUrl) throws Exception {
		TempUserBean tempUserBean = new TempUserBean();
		tempUserBean.setUserName(userName);
		tempUserBean.setMail(mail);
		tempUserBean.setPassword(password);
		tempUserBean.setRequestUrl(requestUrl);
		
		doReturn("token").when(tokenService).createToken(mail);
		
		doReturn(1).when(tempUserService).insert(any());
		
		doThrow(MailFromDomainNotVerifiedException.class).when(mailService).send(any(), any(), any(), any());
		try {
			signupControllerMock.signup(tempUserBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("MXレコードが読み込まれませんでした");
		}
	}
	
	//異常系 コンフィグレーションセットが存在しない
	@ParameterizedTest
	@CsvSource({
		"田中太郎,assolutoracingses@gmail.com,password,http:localhost:4200"
	})
	@Order(8)
	void testError6(String userName,String mail,String password,String requestUrl) throws Exception {
		TempUserBean tempUserBean = new TempUserBean();
		tempUserBean.setUserName(userName);
		tempUserBean.setMail(mail);
		tempUserBean.setPassword(password);
		tempUserBean.setRequestUrl(requestUrl);
		
		doReturn("token").when(tokenService).createToken(mail);
		
		doReturn(1).when(tempUserService).insert(any());
		
		doThrow(ConfigurationSetDoesNotExistException.class).when(mailService).send(any(), any(), any(), any());
		try {
			signupControllerMock.signup(tempUserBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("コンフィグレーションセットが存在しませんでした");
		}
	}
	
	//異常系 構成セットに対して電子メール送信が無効
	@ParameterizedTest
	@CsvSource({
		"田中太郎,assolutoracingses@gmail.com,password,http:localhost:4200"
	})
	@Order(9)
	void testError7(String userName,String mail,String password,String requestUrl) throws Exception {
		TempUserBean tempUserBean = new TempUserBean();
		tempUserBean.setUserName(userName);
		tempUserBean.setMail(mail);
		tempUserBean.setPassword(password);
		tempUserBean.setRequestUrl(requestUrl);
		
		doReturn("token").when(tokenService).createToken(mail);
		
		doReturn(1).when(tempUserService).insert(any());
		
		doThrow(ConfigurationSetSendingPausedException.class).when(mailService).send(any(), any(), any(), any());
		try {
			signupControllerMock.signup(tempUserBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("構成セットに対して電子メール送信が無効です");
		}
	}
	
	//異常系 Amazon SESアカウント全体で電子メール送信が無効
	@ParameterizedTest
	@CsvSource({
		"田中太郎,assolutoracingses@gmail.com,password,http:localhost:4200"
	})
	@Order(10)
	void testError8(String userName,String mail,String password,String requestUrl) throws Exception {
		TempUserBean tempUserBean = new TempUserBean();
		tempUserBean.setUserName(userName);
		tempUserBean.setMail(mail);
		tempUserBean.setPassword(password);
		tempUserBean.setRequestUrl(requestUrl);
		
		doReturn("token").when(tokenService).createToken(mail);
		
		doReturn(1).when(tempUserService).insert(any());
		
		doThrow(AccountSendingPausedException.class).when(mailService).send(any(), any(), any(), any());
		try {
			signupControllerMock.signup(tempUserBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("Amazon SESアカウント全体で電子メール送信が無効になっています");
		}
	}
	
	//異常系 原因不明のエラーが発生
	@ParameterizedTest
	@CsvSource({
		"田中太郎,assolutoracingses@gmail.com,password,http:localhost:4200"
	})
	@Order(11)
	void testError9(String userName,String mail,String password,String requestUrl) throws Exception {
		TempUserBean tempUserBean = new TempUserBean();
		tempUserBean.setUserName(userName);
		tempUserBean.setMail(mail);
		tempUserBean.setPassword(password);
		tempUserBean.setRequestUrl(requestUrl);
		
		doReturn("token").when(tokenService).createToken(mail);
		
		doReturn(1).when(tempUserService).insert(any());
		
		doThrow(RuntimeException.class).when(mailService).send(any(), any(), any(), any());
		try {
			signupControllerMock.signup(tempUserBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("原因不明のエラーが発生しました");
		}
	}
}
