package com.bookmanager.sql.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.bookmanager.connect.ODBCConnection;
import com.bookmanager.model.Book;
import com.bookmanager.model.CheckOutRecord;
import com.bookmanager.model.Reader;
import com.bookmanager.sql.common.CommonService;
import com.bookmanager.sql.common.Sentence;

public class AdminService {

	public static final int READER = 2000;
	public static final int URESULT = 2001;
	public static final int LAYUP = 2002;
	public static final int SIGNUP = 2003;
	public static final int ODRECORD = 2004;
	public static final int RLIST = 2005;

	private Statement myStatement;
	private Sentence mySentence;
	private static final AdminService myAdminService = new AdminService();

	private AdminService() {
		mySentence = Sentence.getSentenceInstance();
		myStatement = ODBCConnection.getStatement();
	}

	public static AdminService getAdminServiceInstance() {
		return myAdminService;
	}

	public boolean getReaderList(List<Reader> readerList, String name, String id) {
		String sql = mySentence.getReaderListSQL(name, id);
		try {
			ResultSet resultSet = myStatement.executeQuery(sql);
			if (fillReaderList(resultSet, readerList)) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean fillReaderList(ResultSet resultSet, List<Reader> readerList) {
		try {
			if (!resultSet.next()) {
				return false;
			}
			do {
				Reader reader = new Reader();
				reader.setId(resultSet.getString("reader_id"));
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
				readerList.add(reader);
			} while (resultSet.next());

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Object[][] formatUserList(List<Reader> readerList) {
		Object[][] list = new Object[readerList.size()][6];
		int i = 0;
		for (Reader reader : readerList) {
			list[i][0] = reader.getId();
			list[i][1] = reader.getName();
			list[i][2] = reader.getLevel();
			list[i][3] = reader.getMobile();
			list[i++][4] = confirmLossReader(reader.getId())==1?"挂失":"正常";
		}
		return list;
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
	
	public String[] getReaderTableHead() {
		return new String[] { "读者编号", "读者姓名", "会员等级", "联系电话", "账号状态", "详细信息" };
	}

	public int[] getReaderTableWidth() {
		return new int[] { 80, 50, 50, 100, 60, 50 };
	}

	public void showBookDetail(Book book) {
		String category = getCategory(book);
		JOptionPane.showMessageDialog(null,
				mySentence.getBookInfo(book, category), "详细资料",
				JOptionPane.PLAIN_MESSAGE, new ImageIcon());
	}

	private String getCategory(Book book) {
		String sql = mySentence.getCategorySQL(book);

		try {
			ResultSet resultSet = myStatement.executeQuery(sql);
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean layUpBook(Book book) {
		String sql = mySentence.getLayUpBookSQL(book);
		try {
			if (myStatement.execute(sql)) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 从数据库中查询最后一条ID
	 * 
	 * @return
	 */
	public String getLastID(int choice) {
		String sql = null;
		if (choice == AdminService.LAYUP) {
			sql = mySentence.getLastBookIDSQL();
		} else if (choice == AdminService.SIGNUP) {
			sql = mySentence.getLastReaderIDSQL();
		}
		try {
			ResultSet resultSet = myStatement.executeQuery(sql);
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成顺序ID号码
	 * 
	 * @param head
	 *            ID前缀
	 * @param lastID
	 *            最后一个ID号
	 * @return 生成的ID号
	 */
	public String genBookID(String head, String lastID) {
		String now = head;
		String last = lastID.substring(head.length());
		int tmp = Integer.parseInt(last) + 1;
		last = String.valueOf(tmp);
		for (tmp = last.length(); tmp < 3; tmp++) {
			now += "0";
		}
		return now + last;
	}

	/**
	 * 查询所有的类别名
	 * 
	 * @return
	 */
	public String[] getAllCategoryOrLevel(int type) {
		String sql = null;
		if (type == AdminService.LAYUP) {
			sql = mySentence.getAllCategorySQL();
		} else if (type == AdminService.SIGNUP) {
			sql = mySentence.getAllLevelSQL();
		}
		try {
			ResultSet resultSet = myStatement.executeQuery(sql);
			if (resultSet.last()) {
				String[] combo = new String[resultSet.getRow()];
				resultSet.first();
				int i = 0;
				do {
					combo[i++] = resultSet.getString(1);
				} while (resultSet.next());
				return combo;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String[] getCardType() {
		return new String[] { "身份证", "学生证", "士兵证", "一卡通" };
	}

	public void signUpReader(Reader reader) {
		String sql = mySentence.getSignUpReaderSQL(reader);
		try {
			myStatement.execute(sql);
			if (reader.getPhone() != 0) {
				myStatement.executeUpdate(mySentence.getUpdatePhoneSQL(reader));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 填充逾期列表
	 * 
	 * @param overDueList
	 * @return
	 */
	public boolean getOverDueRecordList(List<CheckOutRecord> overDueList) {
		String sql = mySentence.getOverDueRecordSQL();
		try {
			ResultSet resultSet = myStatement.executeQuery(sql);
			if (!fillOverDueList(resultSet, overDueList)) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	private boolean fillOverDueList(ResultSet resultSet,
			List<CheckOutRecord> overDueList) {
		try {
			if (!resultSet.next()) {
				return false;
			}
			do {
				CheckOutRecord record = new CheckOutRecord();
				record.setRecordID(resultSet.getInt(1));
				record.setBookID(resultSet.getString(2));
				record.setReaderID(resultSet.getString(3));
				record.setDateBorrow(resultSet.getDate(4));
				record.setOverDueDay(resultSet.getInt(5));
				record.setBookName(resultSet.getString(6));
				overDueList.add(record);
			} while (resultSet.next());

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 填充可还书队列
	 * @param recordList
	 * @return
	 */
	public boolean getReturnList(List<CheckOutRecord> recordList) {
		String sql = mySentence.getBookReturnSQL();
		try {
			ResultSet resultSet = myStatement.executeQuery(sql);
			if(fillReturnList(resultSet, recordList)) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean fillReturnList(ResultSet resultSet,
			List<CheckOutRecord> returnList) {
		try {
			if (!resultSet.next()) {
				return false;
			}
			do {
				CheckOutRecord record = new CheckOutRecord();
				record.setRecordID(resultSet.getInt(1));
				record.setBookID(resultSet.getString(2));
				record.setBookName(resultSet.getString(3));
				record.setAuthor(resultSet.getString(4));
				record.setReaderName(resultSet.getString(5));
				record.setDateBorrow(resultSet.getDate(6));
				record.setReaderID(resultSet.getString(7));
				returnList.add(record);
			} while (resultSet.next());

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Object[][] formatOverDueRecord(List<CheckOutRecord> overDueList) {
		Object[][] list = new Object[overDueList.size()][7];
		int i = 0;
		for (CheckOutRecord record : overDueList) {
			list[i][0] = record.getRecordID();
			list[i][1] = record.getBookID();
			list[i][2] = record.getBookName();
			list[i][3] = record.getReaderID();
			list[i][4] = record.getDateBorrow();
			list[i++][5] = record.getOverDueDay();
		}
		return list;
	}
	
	public Object[][] formatReturnList(List<CheckOutRecord> returnList ) {
		Object[][] list = new Object[returnList.size()][7];
		int i = 0;
		for (CheckOutRecord record : returnList) {
			list[i][0] = record.getRecordID();
			list[i][1] = record.getBookID();
			list[i][2] = record.getBookName();
			list[i][3] = record.getAuthor();
			list[i][4] = record.getReaderName();
			list[i++][5] = record.getDateBorrow();
		}
		return list;
	}

	public String[] getOverDueRecordTableHead() {
		return new String[]{"借阅记录编号", "书号", "书名", "读者ID", "借阅时间", "逾期天数", "挂失"};
	}
	
	public int[] getOverDueRecordTableWidth() {
		return new int[]{80, 50, 100, 50, 70, 50, 80};
	}
	
	public String[] getReturnTableHead() {
		return new String[]{"借阅记录编号", "书号", "书名", "作者", "读者姓名", "借阅时间", "还书"};
	}
	
	public int[] getReturnTableWidth() {
		return new int[]{80, 50, 100, 50, 70, 80, 60};
	}
	
	public boolean signLossRecord(CheckOutRecord record) {
		String sql = mySentence.getUpdateLossSQL(record);
		try {
			myStatement.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 根据用户ID挂失用户
	 * @param userID
	 * @return
	 */
	public boolean signLossReader(String userID) {
		String sql = mySentence.getSignLossReaderSQL(userID);
		try {
			myStatement.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 对借书记录进行还书
	 * @param record
	 * @return
	 */
	public boolean updateReturnBook(CheckOutRecord record) {
		String sql = mySentence.getUpdateReturnSQL(record);
		try {
			myStatement.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
