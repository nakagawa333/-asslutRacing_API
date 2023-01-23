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
import com.example.assolutoRacing.Bean.UpdatePasswordBean;
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
class UpdatePasswordControllerTest{
	private AutoCloseable closeable;
	
	@Autowired
	private UpdatePasswordController updatePasswordController;
	
	@InjectMocks
	private UpdatePasswordController updatePasswordControllerMock;
	
	@Mock
	UserService userService;
	
	@Mock
	TokenService tokenService;
	
	@Mock
	TempUserService tempUserService;
	
	@Mock
	PasswordResetService passwordResetService;
	
    @Autowired
    Validator validator;
    
    @Autowired
    MySQLConnector mySQLConnector;
	
	@BeforeEach
    public void setUp(@Autowired DataSource dataSource) throws Exception {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("updatePasswordControllerTest.sql"));
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
		"password,token"
	})
	@Order(1)
	void testSucess1(String password,String token) throws Exception {
		UpdatePasswordBean updatePasswordBean = new UpdatePasswordBean();
		updatePasswordBean.setPassword(password);
		updatePasswordBean.setToken(token);
		
		ResponseEntity<Boolean> res = null;
		try {
			res = updatePasswordController.updatePassword(updatePasswordBean);
		} catch(Exception e) {
			fail();
		}
		
		assertThat(res.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(res.getBody()).isEqualTo(true);
	}
	
	//正常系 パスワード更新2件以上
	@ParameterizedTest
	@CsvSource({
		"password,token,assolutoracingses@gmail.com"
	})
	@Order(2)
	void testSucess2(String password,String token,String expectMail) throws Exception {
		UpdatePasswordBean updatePasswordBean = new UpdatePasswordBean();
		updatePasswordBean.setPassword(password);
		updatePasswordBean.setToken(token);
		
		ResponseEntity<Boolean> res = null;
		
		doReturn(expectMail).when(passwordResetService).selectByToken(token);
		doReturn(2).when(userService).updatePassword(any());
		
		try {
			res = updatePasswordControllerMock.updatePassword(updatePasswordBean);
		} catch(Exception e) {
			fail();
		}
		
		assertThat(res.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(res.getBody()).isEqualTo(false);
	}
	
	//異常系 メール取得失敗
	@ParameterizedTest
	@CsvSource({
		"password,token"
	})
	@Order(3)
	void testError1(String password,String token) {
		UpdatePasswordBean updatePasswordBean = new UpdatePasswordBean();
		updatePasswordBean.setPassword(password);
		updatePasswordBean.setToken(token);
		
		doThrow(RuntimeException.class).when(passwordResetService).selectByToken(token);
		
		try {
			updatePasswordControllerMock.updatePassword(updatePasswordBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("メールの取得に失敗しました");
		}
	}
	
	//異常系 パスワード更新失敗
	@ParameterizedTest
	@CsvSource({
		"password,token,assolutoracingses@gmail.com"
	})
	@Order(4)
	void testError2(String password,String token,String expectMail) {
		UpdatePasswordBean updatePasswordBean = new UpdatePasswordBean();
		updatePasswordBean.setPassword(password);
		updatePasswordBean.setToken(token);
		
		doReturn(expectMail).when(passwordResetService).selectByToken(token);
		doThrow(RuntimeException.class).when(userService).updatePassword(any());
		
		try {
			updatePasswordControllerMock.updatePassword(updatePasswordBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("パスワード更新に失敗しました");
		}
	}
	
	//異常系 該当のメールアドレスのユーザーなし
	@ParameterizedTest
	@CsvSource({
		"password,token,assolutoracingses@gmail.com,0"
	})
	@Order(5)
	void testError3(String password,String token,String expectMail,int expectUpdateCount) {
		UpdatePasswordBean updatePasswordBean = new UpdatePasswordBean();
		updatePasswordBean.setPassword(password);
		updatePasswordBean.setToken(token);
		
		doReturn(expectMail).when(passwordResetService).selectByToken(token);
		
		doReturn(expectUpdateCount).when(userService).updatePassword(any());
		try {
			updatePasswordControllerMock.updatePassword(updatePasswordBean);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			assertThat(message).isEqualTo("該当のメールメールアドレスのユーザーが存在しませんでした");
		};
	}
}
