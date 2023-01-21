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
class SendPasswordRestMailControllerTest{
	private AutoCloseable closeable;
	
	@Autowired
	private SendPasswordRestMailController sendPasswordRestMailController;
	
	@InjectMocks
	private SendPasswordRestMailController 	sendPasswordRestMailControllerMock;
	
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
		populator.addScript(new ClassPathResource("sendPasswordRestMailControllerTest.sql"));
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
		"assolutoracingses@gmail.com,http://localhost:4200/", //ユーザーあり
		"testError@gmail.com,http://localhost:4200/" //ユーザーなし
	})
	@Order(1)
	void testSucess1(String mail,String requestUrl) throws Exception {
		SendPasswordRestMailBean sendPasswordRestMailBean= new SendPasswordRestMailBean();
		sendPasswordRestMailBean.setMail(mail);
		sendPasswordRestMailBean.setRequestUrl(requestUrl);
		
		try {
			sendPasswordRestMailController.submitSendPasswordResetMailForm(sendPasswordRestMailBean);
			assertTrue(true);
		} catch(Exception e) {
			fail();
		}
	}
	
	//異常系 バリデーションエラー
	@ParameterizedTest
	@CsvSource({
		"不正メール,http:localhost:4200" //メールアドレス不正
	})
	@Order(2)
	void testError1(String mail,String requestUrl) throws Exception{
		SendPasswordRestMailBean sendPasswordRestMailBean= new SendPasswordRestMailBean();
		sendPasswordRestMailBean.setMail(mail);
		sendPasswordRestMailBean.setRequestUrl(requestUrl);
		
	    BindingResult bindingResult = new BindException(sendPasswordRestMailBean, "sendPasswordRestMailBean");
		
	    validator.validate(sendPasswordRestMailBean, bindingResult);
	}
	
	//異常系 ユーザー取得エラー
	@ParameterizedTest
	@CsvSource({
		"assolutoracingses@gmail.com,http://localhost:4200/"
	})
	@Order(3)
	void testError2(String mail,String requestUrl) throws Exception{
		SendPasswordRestMailBean sendPasswordRestMailBean= new SendPasswordRestMailBean();
		sendPasswordRestMailBean.setMail(mail);
		sendPasswordRestMailBean.setRequestUrl(requestUrl);
		
		doThrow(RuntimeException.class).when(userService).selectByMail(mail);
		try {
			sendPasswordRestMailControllerMock.submitSendPasswordResetMailForm(sendPasswordRestMailBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("ユーザーが存在しません");
		}
	}
	
	//異常系 トークン生成エラー
	@ParameterizedTest
	@CsvSource({
		"assolutoracingses@gmail.com,http://localhost:4200/"
	})
	@Order(4)
	void testError3(String mail,String requestUrl) throws Exception{
		SendPasswordRestMailBean sendPasswordRestMailBean= new SendPasswordRestMailBean();
		sendPasswordRestMailBean.setMail(mail);
		sendPasswordRestMailBean.setRequestUrl(requestUrl);
		
		doReturn(1).when(userService).selectByMail(mail);
		doThrow(RuntimeException.class).when(tokenService).createToken(mail);
		try {
			sendPasswordRestMailControllerMock.submitSendPasswordResetMailForm(sendPasswordRestMailBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("トークンの生成に失敗しました");
		}
	}
	
	//異常系 パスワードリセット失敗
	@ParameterizedTest
	@CsvSource({
		"assolutoracingses@gmail.com,http://localhost:4200/"
	})
	@Order(5)
	void testError4(String mail,String requestUrl) throws Exception{
		SendPasswordRestMailBean sendPasswordRestMailBean= new SendPasswordRestMailBean();
		sendPasswordRestMailBean.setMail(mail);
		sendPasswordRestMailBean.setRequestUrl(requestUrl);
		
		String token = tokenService.createToken(mail);
		doReturn(1).when(userService).selectByMail(mail);
		doReturn(token).when(tokenService).createToken(mail);
		
		RegistPasswordResetDto registPasswordResetDto = new RegistPasswordResetDto();
		registPasswordResetDto.setMail(mail);
		registPasswordResetDto.setToken(token);
		doThrow(RuntimeException.class).when(passwordResetService).deleteInsert(registPasswordResetDto);
		
		try {
			sendPasswordRestMailControllerMock.submitSendPasswordResetMailForm(sendPasswordRestMailBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("パスワードリセットへの値挿入が失敗しました");
		}
	}
	
	//異常系 パスワードリセット失敗
	@ParameterizedTest
	@CsvSource({
		"assolutoracingses@gmail.com,http://localhost:4200/"
	})
	@Order(6)
	void testError5(String mail,String requestUrl) throws Exception{
		SendPasswordRestMailBean sendPasswordRestMailBean= new SendPasswordRestMailBean();
		sendPasswordRestMailBean.setMail(mail);
		sendPasswordRestMailBean.setRequestUrl(requestUrl);
		
		String token = tokenService.createToken(mail);
		doReturn(1).when(userService).selectByMail(mail);
		doReturn(token).when(tokenService).createToken(mail);
		
		RegistPasswordResetDto registPasswordResetDto = new RegistPasswordResetDto();
		registPasswordResetDto.setMail(mail);
		registPasswordResetDto.setToken(token);
		doReturn(0).when(passwordResetService).deleteInsert(registPasswordResetDto);
		
		try {
			sendPasswordRestMailControllerMock.submitSendPasswordResetMailForm(sendPasswordRestMailBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("パスワードリセットへの値挿入が失敗しました");
		}
	}
	
	//異常系 メッセージ送信失敗
	@ParameterizedTest
	@CsvSource({
		"assolutoracingses@gmail.com,http://localhost:4200/"
	})
	@Order(7)
	void testError6(String mail,String requestUrl) throws Exception{
		SendPasswordRestMailBean sendPasswordRestMailBean= new SendPasswordRestMailBean();
		sendPasswordRestMailBean.setMail(mail);
		sendPasswordRestMailBean.setRequestUrl(requestUrl);
		
		String token = tokenService.createToken(mail);
		doReturn(1).when(userService).selectByMail(mail);
		doReturn(token).when(tokenService).createToken(mail);
		
		RegistPasswordResetDto registPasswordResetDto = new RegistPasswordResetDto();
		registPasswordResetDto.setMail(mail);
		registPasswordResetDto.setToken(token);
		doReturn(1).when(passwordResetService).deleteInsert(registPasswordResetDto);
		
		doThrow(MessageRejectedException.class).when(mailService).send(any(),any(),any(),any());
		try {
			sendPasswordRestMailControllerMock.submitSendPasswordResetMailForm(sendPasswordRestMailBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("メッセージ送信に失敗しました");
		}
	}
	
	//異常系 MXレコード読み込み失敗
	@ParameterizedTest
	@CsvSource({
		"assolutoracingses@gmail.com,http://localhost:4200/"
	})
	@Order(8)
	void testError7(String mail,String requestUrl) throws Exception{
		SendPasswordRestMailBean sendPasswordRestMailBean= new SendPasswordRestMailBean();
		sendPasswordRestMailBean.setMail(mail);
		sendPasswordRestMailBean.setRequestUrl(requestUrl);
		
		String token = tokenService.createToken(mail);
		doReturn(1).when(userService).selectByMail(mail);
		doReturn(token).when(tokenService).createToken(mail);
		
		RegistPasswordResetDto registPasswordResetDto = new RegistPasswordResetDto();
		registPasswordResetDto.setMail(mail);
		registPasswordResetDto.setToken(token);
		doReturn(1).when(passwordResetService).deleteInsert(registPasswordResetDto);
		
		doThrow(MailFromDomainNotVerifiedException.class).when(mailService).send(any(),any(),any(),any());
		try {
			sendPasswordRestMailControllerMock.submitSendPasswordResetMailForm(sendPasswordRestMailBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("MXレコードが読み込まれませんでした");
		}
	}
	
	//異常系 MXレコード読み込み失敗
	@ParameterizedTest
	@CsvSource({
		"assolutoracingses@gmail.com,http://localhost:4200/"
	})
	@Order(9)
	void testError8(String mail,String requestUrl) throws Exception{
		SendPasswordRestMailBean sendPasswordRestMailBean= new SendPasswordRestMailBean();
		sendPasswordRestMailBean.setMail(mail);
		sendPasswordRestMailBean.setRequestUrl(requestUrl);
		
		String token = tokenService.createToken(mail);
		doReturn(1).when(userService).selectByMail(mail);
		doReturn(token).when(tokenService).createToken(mail);
		
		RegistPasswordResetDto registPasswordResetDto = new RegistPasswordResetDto();
		registPasswordResetDto.setMail(mail);
		registPasswordResetDto.setToken(token);
		doReturn(1).when(passwordResetService).deleteInsert(registPasswordResetDto);
		
		doThrow(ConfigurationSetDoesNotExistException.class).when(mailService).send(any(),any(),any(),any());
		try {
			sendPasswordRestMailControllerMock.submitSendPasswordResetMailForm(sendPasswordRestMailBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("コンフィグレーションセットが存在しませんでした");
		}
	}
	
	//異常系 コンフィグレーションセットが存在しない
	@ParameterizedTest
	@CsvSource({
		"assolutoracingses@gmail.com,http://localhost:4200/"
	})
	@Order(10)
	void testError9(String mail,String requestUrl) throws Exception{
		SendPasswordRestMailBean sendPasswordRestMailBean= new SendPasswordRestMailBean();
		sendPasswordRestMailBean.setMail(mail);
		sendPasswordRestMailBean.setRequestUrl(requestUrl);
		
		String token = tokenService.createToken(mail);
		doReturn(1).when(userService).selectByMail(mail);
		doReturn(token).when(tokenService).createToken(mail);
		
		RegistPasswordResetDto registPasswordResetDto = new RegistPasswordResetDto();
		registPasswordResetDto.setMail(mail);
		registPasswordResetDto.setToken(token);
		doReturn(1).when(passwordResetService).deleteInsert(registPasswordResetDto);
		
		doThrow(ConfigurationSetDoesNotExistException.class).when(mailService).send(any(),any(),any(),any());
		try {
			sendPasswordRestMailControllerMock.submitSendPasswordResetMailForm(sendPasswordRestMailBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("コンフィグレーションセットが存在しませんでした");
		}
	}
	
	//異常系 構成セットに対して電子メール送信が無効
	@ParameterizedTest
	@CsvSource({
		"assolutoracingses@gmail.com,http://localhost:4200/"
	})
	@Order(11)
	void testError10(String mail,String requestUrl) throws Exception{
		SendPasswordRestMailBean sendPasswordRestMailBean= new SendPasswordRestMailBean();
		sendPasswordRestMailBean.setMail(mail);
		sendPasswordRestMailBean.setRequestUrl(requestUrl);
		
		String token = tokenService.createToken(mail);
		doReturn(1).when(userService).selectByMail(mail);
		doReturn(token).when(tokenService).createToken(mail);
		
		RegistPasswordResetDto registPasswordResetDto = new RegistPasswordResetDto();
		registPasswordResetDto.setMail(mail);
		registPasswordResetDto.setToken(token);
		doReturn(1).when(passwordResetService).deleteInsert(registPasswordResetDto);
		
		doThrow(ConfigurationSetSendingPausedException.class).when(mailService).send(any(),any(),any(),any());
		try {
			sendPasswordRestMailControllerMock.submitSendPasswordResetMailForm(sendPasswordRestMailBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("構成セットに対して電子メール送信が無効です");
		}
	}
	
	//異常系 Amazon SESアカウント全体で電子メール送信が無効
	@ParameterizedTest
	@CsvSource({
		"assolutoracingses@gmail.com,http://localhost:4200/"
	})
	@Order(12)
	void testError11(String mail,String requestUrl) throws Exception{
		SendPasswordRestMailBean sendPasswordRestMailBean= new SendPasswordRestMailBean();
		sendPasswordRestMailBean.setMail(mail);
		sendPasswordRestMailBean.setRequestUrl(requestUrl);
		
		String token = tokenService.createToken(mail);
		doReturn(1).when(userService).selectByMail(mail);
		doReturn(token).when(tokenService).createToken(mail);
		
		RegistPasswordResetDto registPasswordResetDto = new RegistPasswordResetDto();
		registPasswordResetDto.setMail(mail);
		registPasswordResetDto.setToken(token);
		doReturn(1).when(passwordResetService).deleteInsert(registPasswordResetDto);
		
		doThrow(AccountSendingPausedException.class).when(mailService).send(any(),any(),any(),any());
		try {
			sendPasswordRestMailControllerMock.submitSendPasswordResetMailForm(sendPasswordRestMailBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("Amazon SESアカウント全体で電子メール送信が無効になっています");
		}
	}
	
	//異常系 原因不明のエラー発生
	@ParameterizedTest
	@CsvSource({
		"assolutoracingses@gmail.com,http://localhost:4200/"
	})
	@Order(13)
	void testError12(String mail,String requestUrl) throws Exception{
		SendPasswordRestMailBean sendPasswordRestMailBean= new SendPasswordRestMailBean();
		sendPasswordRestMailBean.setMail(mail);
		sendPasswordRestMailBean.setRequestUrl(requestUrl);
		
		String token = tokenService.createToken(mail);
		doReturn(1).when(userService).selectByMail(mail);
		doReturn(token).when(tokenService).createToken(mail);
		
		RegistPasswordResetDto registPasswordResetDto = new RegistPasswordResetDto();
		registPasswordResetDto.setMail(mail);
		registPasswordResetDto.setToken(token);
		doReturn(1).when(passwordResetService).deleteInsert(registPasswordResetDto);
		
		doThrow(RuntimeException.class).when(mailService).send(any(),any(),any(),any());
		try {
			sendPasswordRestMailControllerMock.submitSendPasswordResetMailForm(sendPasswordRestMailBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("原因不明のエラーが発生しました");
		}
	}
}
