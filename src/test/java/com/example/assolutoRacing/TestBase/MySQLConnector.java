package com.example.assolutoRacing.TestBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class MySQLConnector {
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	//データベースとの接続(セッション)
	private Connection conn;
	public void getConnection() throws Exception {
	    conn = null;
		try {
			conn = DriverManager.getConnection(url,username,password);
		} catch(SQLException e) {
			throw new SQLException("データベースに接続できませんでした。");
		} catch(Exception e) {
			throw new Exception("原因不明のエラーが発生しました");
		}
	}
	
	public void close() throws Exception {
		conn.close();
	}
	
	public ResultSet select(String sql) throws Exception {
		ResultSet rs = null;
		try {
			Statement stm = conn.createStatement();
			rs = stm.executeQuery(sql);
		} catch(SQLException e) {
			throw new SQLException("データベースに接続できませんでした。");
		} catch(Exception e) {
			throw new Exception("原因不明のエラーが発生しました");
		}
		
		return rs;
	}
}
