package com.bookmanager.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bookmanager.model.User;

public class ODBCConnection {

	private String url;
	private String driver;
	private String userName;
	private String userPassword;
	private Connection myConnection;
	private Statement myStatement;
	
	public ODBCConnection() {
		// 初始化连接信息
		this.url = "jdbc:odbc:bookmanager";
		this.driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		this.userName = "sa";
		this.userPassword = "";
	}

	/**
	 * 连接数据库
	 * @return 成功返回true，失败返回false
	 */
	public boolean dbConnect() {
		if(this.myConnection != null) {
			return true;
		}
		try {
			Class.forName(driver);
			myConnection = DriverManager.getConnection(url, userName, userPassword);
			myStatement = myConnection.createStatement();
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 断开数据库连接
	 * @return 成功返回true，失败返回false
	 */
	public boolean dbDisconnect() {
		try {
			this.myConnection.close();
			this.myConnection = null;
			this.myStatement.close();
			this.myStatement = null;
		} catch (Exception e) {
			e.printStackTrace();
			this.myConnection = null;
			this.myStatement = null;
			return false;
		}
		return true;
	}
	
	/**
	 * 根据给定的用户名查询密码是否正确，若正确则填充用户类型数据
	 * @return 用户名密码正确返回true，否则返回false
	 */
	public boolean isLegitimateUser(User user) {
		this.dbConnect();
		try {
			ResultSet resultSet = myStatement.executeQuery("SELECT password,type " +
					"FROM BMuser " +
					"WHERE name collate Chinese_PRC_CS_AS='" + user.getName() +"'");
			if ( !resultSet.next() ) {
				return false;
			}
			if (user.getPassword().equals(resultSet.getString("password")) ) {
				user.setType(resultSet.getInt("type"));
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
}
