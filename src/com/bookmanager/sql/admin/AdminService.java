package com.bookmanager.sql.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bookmanager.connect.ODBCConnection;
import com.bookmanager.model.Reader;
import com.bookmanager.sql.common.CommonService;
import com.bookmanager.sql.common.Sentence;

public class AdminService {
	
	private Statement myStatement;
	private Sentence mySentence;
	private static final AdminService myAdminService = new AdminService();
	
	private AdminService () {
		mySentence = Sentence.getSentenceInstance();
		myStatement = ODBCConnection.getStatement();
	}
	
	public static AdminService getAdminServiceInstance() {
		return myAdminService;
	}
	
	public List<Reader> getReaderList(String name, String id) {
		String sql = mySentence.getReaderListSQL(name, id);
		List<Reader> readerList = new ArrayList<Reader>(); 
		
		try {
			ResultSet resultSet = myStatement.executeQuery(sql);
			if( !fillReaderList(resultSet, readerList) ) {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return readerList;
	}

	private boolean fillReaderList(ResultSet resultSet, List<Reader> readerList) {
		try {
			if( !resultSet.next() ) {
				return false;
			}
			do {
				Reader reader = new Reader();
				reader.setName(resultSet.getString("reader_name"));
				reader.setSex(resultSet.getString("sex"));
				reader.setBirthday(resultSet.getDate("birthday"));
				reader.setPhone(resultSet.getInt("phone"));
				reader.setMobile(resultSet.getString("mobile"));
				reader.setCardName(resultSet.getString("card_name"));
				reader.setCardId(resultSet.getString("card_id"));
				reader.setLevel(resultSet.getString("level"));
				reader.setSignUpTime(resultSet.getDate("day"));
				reader.setBorrowNumber(resultSet.getInt("borrow_number"));
			} while(resultSet.next());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
