package com.bookmanager.sql.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bookmanager.connect.ODBCConnection;
import com.bookmanager.model.Book;
import com.bookmanager.model.CheckOutRecord;
import com.bookmanager.model.Reader;
import com.bookmanager.sql.common.Sentence;

public class UserService {
	
	public static final int PINFOPANEL = 1001;
	public static final int RECORDPANEL = 1002;
	public static final int RESULTPANEL = 1003;
	
	private Statement myStatement;
	private Sentence mySentence;
	private static final UserService myUserService = new UserService();
	
	private UserService() {
		this.myStatement = ODBCConnection.getStatement();
		this.mySentence = Sentence.getSentenceInstance();
	}
	
	public static UserService getUserServiceInstance() {
		return myUserService;
	}
	
	public boolean updateBookQuanForCheckOut(Book book) {
		book.setQuanIn(book.getQuanIn()-1);
		book.setQuanOut(book.getQuanOut()+1);
		String sql = mySentence.getBookCheckOutSQL(book);
		try {
			this.myStatement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * ��bookListת��Ϊ�����ѯ�����ʾ��������
	 * 
	 * @param bookList
	 *            ԭʼ����
	 * @return ת����Ķ�άObject����
	 */
	public static Object[][] formatBookList(List<Book> bookList) {
		Object[][] list = new Object[bookList.size()][6];
		int i = 0;
		for (Book book : bookList) {
			list[i][0] = book.getBookId();
			list[i][1] = book.getBookName();
			list[i][2] = book.getAuthor();
			list[i][3] = book.getPublishing();
			list[i++][4] = book.getQuanIn();
		}
		return list;
	}
	
	/**
	 * ��recordListת��Ϊ������ļ�¼��ʾ��������
	 * 
	 * @param bookList
	 *            ԭʼ����
	 * @return ת����Ķ�άObject����
	 */
	public static Object[][] formatUserCheckOutRecord(List<CheckOutRecord> recordList) {
		Object[][] list = new Object[recordList.size()][6];
		int i = 0;
		for (CheckOutRecord record : recordList) {
			list[i][0] = record.getBookName();
			list[i][1] = record.getAuthor();
			list[i][2] = record.getReaderName();
			list[i][3] = record.getDateBorrow();
			list[i][4] = record.getDateReturn();
			list[i++][5] = record.isLoss()?"��":"��";
		}
		return list;
	}
	
	/**
	 * ��������¼ 
	 * @param book �������
	 * @param reader ��ǰ�û�
	 * @return �Ƿ����ɹ�
	 */
	public boolean insertCheckOutRecord(Book book, Reader reader) {
		String sql = mySentence.getInsertCheckOutRecordSQL(book, reader);
		try {
			this.myStatement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean updateBorrowNumber(Reader reader) {
		reader.setBorrowNumber(reader.getBorrowNumber()+1);
		String sql = mySentence.getUpdateBorrowNumberSQL(reader);
		try {
			this.myStatement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * ���ݸ����û���ѯ���ļ�¼
	 * @param reader ��ǰ�û�
	 * @return �û��Ľ��ļ�¼�б�
	 */
	public List<CheckOutRecord> getBorrowRecordList(Reader reader, String limit) {
		List<CheckOutRecord> recordList = new ArrayList<CheckOutRecord>();
		String sql = mySentence.getCheckOutRecordSQL(reader, limit);
		try {
			ResultSet resultSet = myStatement.executeQuery(sql);
			if( !this.fillCheckOutRecord(resultSet, recordList) ) {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return recordList;
	}

	private boolean fillCheckOutRecord(ResultSet resultSet, List<CheckOutRecord> recordList) {
		try {
			
			if ( !resultSet.next() ) {
				return false;
			}
			do {
				CheckOutRecord record = new CheckOutRecord();
				record.setBookName(resultSet.getString(1));
				record.setAuthor(resultSet.getString(2));
				record.setReaderName(resultSet.getString(3));
				record.setDateBorrow(resultSet.getDate(4));
				record.setDateReturn(resultSet.getString(5));
				record.setLoss(resultSet.getBoolean(6));
				recordList.add(record);
			}
			while( resultSet.next() );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
}
