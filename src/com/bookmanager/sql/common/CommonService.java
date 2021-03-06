package com.bookmanager.sql.common;

import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.bookmanager.connect.ODBCConnection;
import com.bookmanager.frame.CommonTablePanel;
import com.bookmanager.model.Book;
import com.bookmanager.model.Reader;

public class CommonService {

	public static final int SEARCHPANEL = 1000;

	public final static int HEIGHT = 30;

	private Statement myStatement;
	private Sentence mySentence;
	private static final CommonService myCommonService = new CommonService();

	private CommonService() {
		this.myStatement = ODBCConnection.getStatement();
		this.mySentence = Sentence.getSentenceInstance();
	}

	/**
	 * 获取CommonService的单例
	 * 
	 * @return CommonService的单例
	 */
	public static CommonService getCommonServiceInstance() {
		return myCommonService;
	}

	/**
	 * 根据给定的用户名查询密码是否正确，若正确则填充用户类型数据
	 * 
	 * @return 用户名密码正确返回true，否则返回false
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
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 根据给定的查询条件查询书目
	 * 
	 * @param book
	 *            使用给定条件所构造的book实例
	 * @return 符合条件的书本列表
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
			e.printStackTrace();
		}

		return bookList;
	}

	public int[] getRecordTableWidth() {
		return new int[] { 200, 50, 50, 100, 100, 50 };
	}

	public String[] getRecordTableHead() {
		return new String[] { "书名", "作者", "借阅人", "借阅日期", "归还日期", "是否挂失" };
	}

	public String[] getBookTableHead(String level) {
		if (level.equals("ADMIN")) {
			return new String[] { "书号", "书名", "作者", "出版社", "馆藏数量", "入馆时间", "查看详情" };
		} else {
			return new String[] { "书号", "书名", "作者", "出版社", "馆藏数量", "借阅" };
		}
	}

	public int[] getBookTableWidth(String type) {
		if(type.equals(CommonTablePanel.BINFO)) {
			return new int[] { 50, 100, 50, 100, 50, 80, 80};
		}
		else if( type.equals(CommonTablePanel.CHECK)) {
			return new int[] { 50, 100, 50, 100, 50, 80};
		}
		return null;
	}
	

	public void showReaderInfor(Reader reader) {
		int maxNumber = getReaderMaxNumber(reader);
		int tmp = confirmLossReader(reader.getId());
		boolean loss = false;
		if(tmp == 1) {
			loss = true;
		}
		if(reader.getSex().equals("女")) {
			JOptionPane.showMessageDialog(null,
					mySentence.getReaderInfo(reader, maxNumber,loss), "详细资料",
					JOptionPane.PLAIN_MESSAGE, new ImageIcon("image/female.png"));
		}
		else {
			JOptionPane.showMessageDialog(null,
					mySentence.getReaderInfo(reader, maxNumber,loss), "详细资料",
					JOptionPane.PLAIN_MESSAGE, new ImageIcon("image/male.png"));
		}
		
	}

	/**
	 * 查询某用户是否到达借书上限
	 * 
	 * @param reader
	 *            用户
	 * @return true为达到上限
	 */
	public boolean isReachBookCeiling(Reader reader) {
		int i = getReaderMaxNumber(reader) - reader.getBorrowNumber();
		return i <= 0 ? true : false;
	}

	/**
	 * 查询某用户的最大借书数量
	 * 
	 * @param reader
	 *            用户
	 * @return true为达到上限
	 */
	public int getReaderMaxNumber(Reader reader) {
		String sql = mySentence.getReaderBookLimitSQL(reader);
		try {
			ResultSet resultSet = myStatement.executeQuery(sql);
			if( resultSet.next() ) {
				return resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 确认用户是否挂失
	 * @param userID 
	 * @return -1 —— 不存在该用户
	 * 			0 —— 用户存在未挂失
	 * 			1 —— 用户存在已挂失
	 */
	public int confirmLossReader(String userID) {
		String sql = mySentence.getReaderSQL(userID);
		try {
			ResultSet resultSet = myStatement.executeQuery(sql);
			if( !resultSet.next() ) {
				return -1;
			}
			else {
				sql = mySentence.getQueeryLossSQL(userID);
				resultSet = myStatement.executeQuery(sql);
				if( !resultSet.next() ) {
					return 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public String getAuthorInfo() {
		
		return "作者：程昊\n" + "完成时间：2015.12.29\n" + "版本号：1.0\n";
	}
	
}
