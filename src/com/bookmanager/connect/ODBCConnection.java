package com.bookmanager.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ODBCConnection {

	private static String url = "jdbc:odbc:bookmanager";
	private static String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	private static String userName = "sa";
	private static String userPassword = "";
	private static Connection myConnection;

	public ODBCConnection() {

	}

	public static Connection getMyConnection() {
		if (dbConnect())
			return myConnection;
		else
			return null;
	}

	/**
	 * �������ݿ�
	 * 
	 * @return �ɹ�����true��ʧ�ܷ���false
	 */
	public static boolean dbConnect() {
		if (myConnection != null) {
			return true;
		}
		try {
			Class.forName(driver);
			myConnection = DriverManager.getConnection(url, userName,
					userPassword);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * �Ͽ����ݿ�����
	 * 
	 * @return
	 * @return �ɹ�����true��ʧ�ܷ���false
	 */
	public static boolean dbDisconnect() {
		try {
			myConnection.close();
			myConnection = null;
		} catch (Exception e) {
			e.printStackTrace();
			myConnection = null;
			return false;
		}
		return true;
	}

	public static Statement getStatement() {
		try {
			dbConnect();
			return myConnection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
