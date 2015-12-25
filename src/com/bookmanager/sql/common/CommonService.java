package com.bookmanager.sql.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bookmanager.connect.ODBCConnection;
import com.bookmanager.model.Book;
import com.bookmanager.model.Reader;

public class CommonService {

	public static final int SEARCHPANEL = 1000;
	
	public final static int HEIGHT = 30;
	
	private Statement myStatement;
	private Sentence mySentence;
	private static final CommonService myCommonService = new CommonService();

	private CommonService() {
		// TODO Auto-generated constructor stub
		this.myStatement = ODBCConnection.getStatement();
		this.mySentence = Sentence.getSentenceInstance();
	}

	/**
	 * ��ȡCommonService�ĵ���
	 * 
	 * @return CommonService�ĵ���
	 */
	public static CommonService getCommonServiceInstance() {
		return myCommonService;
	}

	/**
	 * ���ݸ������û�����ѯ�����Ƿ���ȷ������ȷ������û���������
	 * 
	 * @return �û���������ȷ����true�����򷵻�false
	 */
	public boolean isLegitimateUser(Reader reader) {

		try {
			ResultSet resultSet = myStatement.executeQuery(mySentence
					.getReaderSQL(reader));
			if (!resultSet.next()) {
				return false;
			}
			if (reader.getPassword().equals(resultSet.getString("password"))) {
				if (this.fillReader(resultSet, reader)) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private boolean fillReader(ResultSet resultSet, Reader reader) {
		try {
			resultSet.first();
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean fillBookList(ResultSet resultSet, List<Book> bookList) {
		try {
			if (!resultSet.next()) {
				return false;
			}
			do {
				Book book = new Book();
				book.setBookId(resultSet.getString(1));
				book.setBookName(resultSet.getString(2));
				book.setAuthor(resultSet.getString(3));
				book.setPublishing(resultSet.getString(4));
				book.setCategoryid(resultSet.getString(5));
				book.setPrice(resultSet.getDouble(6));
				book.setPublishDate(resultSet.getDate(7));
				book.setQuanIn(resultSet.getInt(8));
				book.setQuanOut(resultSet.getInt(9));
				book.setQuanLoss(resultSet.getInt(10));
				bookList.add(book);
			} while (resultSet.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * ���ݸ����Ĳ�ѯ������ѯ��Ŀ
	 * 
	 * @param book
	 *            ʹ�ø��������������bookʵ��
	 * @return �����������鱾�б�
	 */
	public List<Book> getBookList(Book book) {

		String sql = mySentence.getBookListSQL(book);
		List<Book> bookList = new ArrayList<Book>();
		try {
			ResultSet rs = this.myStatement.executeQuery(sql);
			if (!this.fillBookList(rs, bookList)) {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bookList;
	}

	public int[] getRecordTableWidth() {
		return new int[]{ 200, 50, 50, 100, 100, 50 };
	} 
	
	public String[] getRecordTableHead() {
		return new String[]{ "����", "����", "������", "��������",
				"�黹����", "�Ƿ���ʧ" };
	}
	
	public String[] getBookTableHead(String level) {
		if(level.equals("ADMIN")) {
			return new String[]{"���", "����", "����", "������", "�ݲ�����", "�鿴����"};
		}
		else {
			return new String[]{"���", "����", "����", "������", "�ݲ�����", "����"};
		}
	}
	
	public int[] getBookTableWidth() { 
		return new int[]{50, 100, 50, 150, 50, 80}; 
	}
}
