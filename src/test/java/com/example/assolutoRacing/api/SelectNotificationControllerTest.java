package com.example.assolutoRacing.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

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
import com.example.assolutoRacing.Dto.SelectCarsDto;
import com.example.assolutoRacing.Dto.SelectCourseDto;
import com.example.assolutoRacing.Dto.SelectMakerDto;
import com.example.assolutoRacing.Dto.SelectTireTypeDto;
import com.example.assolutoRacing.Enum.DateUtil;
import com.example.assolutoRacing.Service.InfosService;
import com.example.assolutoRacing.Service.NotificationService;
import com.example.assolutoRacing.Service.UserService;
import com.example.assolutoRacing.TestBase.MySQLConnector;

@SpringBootTest
class SelectNotificationControllerTest{
	private AutoCloseable closeable;
	
	@Autowired
	private SelectNotificationController selectNotificationController;
	
	@InjectMocks
	private SelectNotificationController selectNotificationControllerMock;
	
	@Mock
	private NotificationService notificationService;
	
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
		"最初の投稿,投稿しました,2023/01/18"
	})
	@Order(1)
	void testSucess1(String title,String content,String createTime) throws Exception {
		List<SelectNotificationRes> selectNotificationResList = new ArrayList<>();
		SelectNotificationRes selectNotificationRes = new SelectNotificationRes();
		selectNotificationRes.setTitle(title);
		selectNotificationRes.setContent(content);
		selectNotificationRes.setCreateTime(createTime);
		selectNotificationResList.add(selectNotificationRes);
		
		ResponseEntity<List<SelectNotificationRes>> res = null;
		try {
			res = selectNotificationController.select();
		} catch(Exception e) {
			fail();
		}
		
		assertThat(res.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
		assertThat(res.getBody()).isEqualTo(selectNotificationResList);
	}
	
	//異常系
	@Test
	@Order(2)
	void testError1() {
		doThrow(RuntimeException.class).when(notificationService).selectAll();
		try {
			selectNotificationControllerMock.select();
			fail();
		} catch(Exception e) {
			assertTrue(true);
		}
	}
}
