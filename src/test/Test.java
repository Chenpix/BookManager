package test;

import com.bookmanager.connect.ODBCConnection;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ODBCConnection con = new ODBCConnection();
		con.Connect();
		con.QueryData();
	}

}
