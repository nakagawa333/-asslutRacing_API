package com.example.assolutoRacing.TestBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class MySQLConnector {
	
	@Autowired 
	DataSource dataSource;
	
	//データベースとの接続(セッション)
	private Connection conn;
	public void getConnection() throws Exception {
	    conn = null;
		try {
			conn = dataSource.getConnection();
		} catch(SQLException e) {
			throw new SQLException("データベースに接続できませんでした。");
		} catch(Exception e) {
			throw new Exception("原因不明のエラーが発生しました");
		}
	}
	
	public void close() throws Exception {
		try {
			conn.close();
		} catch(Exception e) {
			throw new Exception("原因不明のエラーが発生しました");
		}
	}
	
	public ResultSet select(String query) throws Exception {
		ResultSet rs = null;
		try {
			Statement stm = conn.createStatement();
			rs = stm.executeQuery(query);
		} catch(SQLException e) {
			throw new SQLException("データベースに接続できませんでした。");
		} catch(Exception e) {
			throw new Exception("原因不明のエラーが発生しました");
		}
		
		return rs;
	}
}
