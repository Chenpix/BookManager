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
import com.bookmanager.model.Reader;
import com.bookmanager.sql.common.CommonService;
import com.bookmanager.sql.common.Sentence;

public class AdminService {
	
	public static final int READER = 2000;
	public static final int URESULT = 2001;
	public static final int LAYUP = 2002;
	
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
			} while(resultSet.next());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Object[][] formatUserList(List<Reader> readerList) {
		Object[][] list = new Object[readerList.size()][5];
		int i = 0;
		for (Reader reader : readerList) {
			list[i][0] = reader.getId();
			list[i][1] = reader.getName();
			list[i][2] = reader.getLevel();
			list[i++][3] = reader.getMobile();
		}
		return list;
	}
	
	public String[] getReaderTableHead() {
		return new String[]{"读者编号", "读者姓名", "会员等级", "联系电话", "详细信息"};
	}
	
	public int[] getReaderTableWidth() {
		return new int[]{80, 50, 50, 100, 50};
	}

	public void showBookDetail(Book book) {
		String category = getCategory(book);
		JOptionPane.showMessageDialog(null,
				mySentence.getBookInfo(book, category), "详细资料",
				JOptionPane.PLAIN_MESSAGE, new ImageIcon());
	}

	private String getCategory(Book book) {
		// TODO Auto-generated method stub
		String sql = mySentence.getCategorySQL(book);
		
		try {
			ResultSet resultSet = myStatement.executeQuery(sql);
			if( resultSet.next() ) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean layUpBook(Book book) {
		String sql = mySentence.getLayUpBookSQL(book);
		 try {
			if( myStatement.execute(sql) ) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 从数据库中查询最后一条BookID
	 * @return
	 */
	public String getLastBookID() {
		String sql = mySentence.getLastBookIDSQL();
		try {
			ResultSet resultSet = myStatement.executeQuery(sql);
			if( resultSet.next() ) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 生成顺序ID号码
	 * @param head ID前缀
	 * @param lastID 最后一个ID号
	 * @return 生成的ID号
	 */
	public String genBookID(String head, String lastID) {
		String now = head;
		String last = lastID.substring(head.length());
		int tmp = Integer.parseInt(last) + 1;
		last = String.valueOf(tmp);
		for(tmp = last.length() ; tmp < 3 ; tmp++) {
			now += "0";
		}
		return now + last;
	}

	/**
	 * 查询所有的类别名
	 * @return
	 */
	public String[] getAllCategory() {
		String sql = mySentence.getAllCategorySQL();
		try {
			ResultSet resultSet = myStatement.executeQuery(sql);
			if( resultSet.last() ) {
				String[] combo = new String[resultSet.getRow()];
				resultSet.first();
				int i = 0;
				do {
					combo[i++] = resultSet.getString(1);
				} while(resultSet.next());
				return combo;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
