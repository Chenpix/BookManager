package com.bookmanager.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ODBCConnection {

	private String url;
	private String driver;
	private String userName;
	private String userPassword;
	private Connection myConnection;
	private static Statement myStatement;
	
	public ODBCConnection() {
		// 初始化连接信息
		this.url = "jdbc:odbc:bookshoplk";
		this.driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		this.userName = "sa";
		this.userPassword = "";
	}

	public void Connect() {
		try {
			Class.forName(driver);
			myConnection = DriverManager.getConnection(url, userName, userPassword);
			myStatement = myConnection.createStatement();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void QueryData() {
		try {
			ResultSet resultSet = myStatement.executeQuery("SELECT * " +
					"FROM BOOK ");
			System.out.println(resultSet.getRow());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
