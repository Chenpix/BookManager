package com.bookmanager.sql.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.bookmanager.connect.ODBCConnection;
import com.bookmanager.model.Book;
import com.bookmanager.model.Reader;
import com.bookmanager.sql.common.Sentence;

public class UserService {
	
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
	 * 将bookList转换为构造BookTableModel所用的数据
	 * 
	 * @param bookList
	 *            原始数据
	 * @return 转换后的二维Object数组
	 */
	public Object[][] formatUserBookList(List<Book> bookList) {
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
	 * 插入借书记录 
	 * @param book 所借的书
	 * @param reader 当前用户
	 * @return 是否插入成功
	 */
	public boolean insertCheckOutRecord(Book book, Reader reader) {
		String sql = mySentence.getCheckOutRecordSQL(book, reader);
		System.out.println(sql);
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
	 * 查询某用户是否到达借书上限
	 * @param reader 用户
	 * @return true为达到上限
	 */
	public boolean isReachBookCeiling(Reader reader) {
		String sql = mySentence.getUserBookLimitSQL(reader);
		int i = 0;
		try {
			ResultSet resultSet = this.myStatement.executeQuery(sql);
			resultSet.first();
			i = resultSet.getInt(1) - reader.getBorrowNumber();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i <= 0? true: false;
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
}
