package com.example.assolutoRacing.TestBase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.sql.SQLException;

import org.apache.commons.codec.binary.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
class MySQLConnectorTest {
	private AutoCloseable closeable;
	
	@Autowired
	private MySQLConnector mySQLConnector;
	
	@Mock
	private MySQLConnector mySQLConnectorMock;
	
	@Value("${spring.datasource.password}")
	private static String jdbcUrl;
	
	@BeforeEach
    public void setUp() {
        // これがないとmockedListはnullのまま
    	closeable = MockitoAnnotations.openMocks(this);
    }
    
    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

	
	@Test
	@Order(1)
	//正常系
	void sucess1() throws Exception {
		try {
			mySQLConnector.getConnection();
			String query = "select * from car";
			mySQLConnector.select(query);
			assertTrue(true);
		} catch(SQLException e) {
			fail();
		} finally {
			mySQLConnector.close();
		}
	}
	
	@Test
	@Order(2)
	//異常系(mysql接続エラー)
	void fail1() throws Exception {
		try {
			doThrow(new SQLException()).when(mySQLConnectorMock).getConnection();
			mySQLConnectorMock.getConnection();
			fail();
		} catch(SQLException e) {
			assertTrue(true);
		} finally {
			mySQLConnectorMock.close();
		}
	}
	
	@Test
	@Order(3)
	//異常系(不正なクエリ文)
	void fail2() throws Exception {
		try {
			mySQLConnector.getConnection();
			//不正なクエリ文
			String query = "あいうえお";
			mySQLConnector.select(query);
			fail();
		} catch(SQLException e) {
			String message = e.getMessage();
			if(StringUtils.equals(message,"データベースに接続できませんでした。")) {
				assertTrue(true);
			} else {
				fail();
			}
			assertTrue(true);
		} catch(Exception e) {
			fail();
		} finally {
			mySQLConnector.close();
		}
	}
	
	@Test
	@Order(4)
	//異常系(データベース未接続)
	void fail3() throws Exception {
		try {
			String query = "select * from car";
			mySQLConnector.select(query);
			fail();
		} catch(Exception e) {
			String message = e.getMessage();
			if(StringUtils.equals(message,"原因不明のエラーが発生しました")) {
				assertTrue(true);
			} else {
				fail();
			}
		}
	}
	
	@Test
	@Order(5)
	//異常系(原因不明のエラー)
	void fail4() throws Exception {
		try {
			mySQLConnectorMock.getConnection();
			String query = "select * from car";
			doThrow(new Exception("原因不明のエラーが発生しました")).when(mySQLConnectorMock).select(query);
			mySQLConnectorMock.select(query);
		} catch(Exception e) {
			String message = e.getMessage();
			if(StringUtils.equals(message, "原因不明のエラーが発生しました")) {
				assertTrue(true);
			} else {
				fail();
			}
		} finally {
			mySQLConnectorMock.close();
		}
	}
	
	@Test
	@Order(6)
	void fail5() {
		try {
			doThrow(new Exception("原因不明のエラーが発生しました")).when(mySQLConnectorMock).close();
			mySQLConnectorMock.getConnection();
			mySQLConnectorMock.close();
		} catch(Exception e) {
			String message = e.getMessage();
			if(StringUtils.equals(message, "原因不明のエラーが発生しました")) {
				assertTrue(true);
			} else {
				fail();
			}
		}
	}
	
	@Test
	@Order(7)
	void fail6() {
		try {
			mySQLConnector.close();
		} catch(Exception e) {
			String message = e.getMessage();
			if(StringUtils.equals(message, "原因不明のエラーが発生しました")) {
				assertTrue(true);
			} else {
				fail();
			}
		}
	}
}
