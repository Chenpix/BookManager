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
	
	private Statement myStatement;
	private static final CommonService myCommonService = new CommonService();
	private List<Book> bookList;
	
	private CommonService() {
		// TODO Auto-generated constructor stub
		this.myStatement = ODBCConnection.getStatement();
		this.bookList = new ArrayList<Book>();
	}

	/**
	 * 获取CommonService的单例
	 * @return CommonService的单例
	 */
	public static CommonService getCommonService() {
		return myCommonService;
	}
	
	/**
	 * 根据给定的用户名查询密码是否正确，若正确则填充用户类型数据
	 * @return 用户名密码正确返回true，否则返回false
	 */
	public boolean isLegitimateUser(Reader reader) {

		try {
			ResultSet resultSet = myStatement.executeQuery(Sentence.getReaderSQL(reader));
			if ( !resultSet.next() ) {
				return false;
			}
			if (reader.getPassword().equals(resultSet.getString("password")) ) {
				if ( this.fillReader(resultSet, reader) ) {
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
			reader.setBirthday(resultSet.getString("birthday"));
			reader.setPhone(resultSet.getInt("phone"));
			reader.setMobile(resultSet.getString("mobile"));
			reader.setCardName(resultSet.getString("card_name"));
			reader.setCardId(resultSet.getString("card_id"));
			reader.setLevel(resultSet.getString("level"));
			reader.setSignUpTime(resultSet.getDate("day"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean fillBookList(ResultSet resultSet, List<Book> bookList) {
		try {
			if( !resultSet.next()) {
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
			}
			while( resultSet.next() );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public List<Book> getBookList(Book book) {
		//判断书的信息是否为空
		if( book.isEmpty() ) {
			return null;
		}
		
		String sql = Sentence.getBookListSQL(book);
		try {
			ResultSet rs = this.myStatement.executeQuery(sql);
			if ( !this.fillBookList(rs, this.bookList) ) {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.bookList;
	}
	
	
	public boolean updateBookQuanForCheckOut(Book book) {
		book.setQuanIn(book.getQuanIn()-1);
		book.setQuanOut(book.getQuanOut()+1);
		String sql = Sentence.getBookCheckOutSQL(book);
		try {
			this.myStatement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean insertCheckOutRecord(Book book, Reader reader) {
		return true;
	}
}
