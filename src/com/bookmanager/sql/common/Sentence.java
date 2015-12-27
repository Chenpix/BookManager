package com.bookmanager.sql.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bookmanager.model.Book;
import com.bookmanager.model.CheckOutRecord;
import com.bookmanager.model.Reader;

public class Sentence {

	private static final Sentence sentence = new Sentence();

	private Sentence() {
	}

	public static Sentence getSentenceInstance() {
		return sentence;
	}

	public String getNowDate() {
		String temp_str = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		temp_str = sdf.format(dt);
		return temp_str;
	}

	public String getReaderSQL(Reader reader) {
		return "SELECT * FROM reader WHERE reader_id collate Chinese_PRC_CS_AS='"
				+ reader.getId() + "'";
	}

	public String getBookListSQL(Book book) {
		StringBuffer sb = new StringBuffer("SELECT * FROM book WHERE ");
		if (!book.getBookId().equals("")) {
			sb.append("book_id = '" + book.getBookId() + "' AND ");
		}
		if (!book.getBookName().equals("")) {
			sb.append("book_name LIKE '%" + book.getBookName() + "%' AND ");
		}
		if (!book.getAuthor().equals("")) {
			sb.append("author LIKE '%" + book.getAuthor() + "%' AND ");
		}
		if (!book.getPublishing().equals("")) {
			sb.append("publishing LIKE '%" + book.getPublishing() + "%' AND ");
		}
		if (!book.getCategoryid().equals("")) {
			sb.append("category_id = '" + book.getCategoryid() + "' AND ");
		}
		sb.append("1=1");
		return sb.toString();
	}

	public String getBookCheckOutSQL(Book book) {
		return "UPDATE book SET quantity_in=" + book.getQuanIn()
				+ ", quantity_out=" + book.getQuanOut() + " WHERE book_id='"
				+ book.getBookId() + "'";
	}

	public String getInsertCheckOutRecordSQL(Book book, Reader reader) {
		return "INSERT INTO [bookmanager].[dbo].[borrow] "
				+ "(reader_id, book_id, date_borrow, date_return, loss) "
				+ "VALUES ('" + reader.getId() + "','" + book.getBookId()
				+ "',getdate(),NULL,0);";
	}

	public String getReaderBookLimitSQL(Reader reader) {
		return "SELECT numbers FROM member_level WHERE level='"
				+ reader.getLevel() + "'";
	}

	public String getUpdateBorrowNumberSQL(Reader reader) {
		return "UPDATE reader SET borrow_number=" + reader.getBorrowNumber()
				+ " WHERE reader_id='" + reader.getId() + "'";
	}

	/**
	 * 借阅记录查询语句
	 * 
	 * @param reader
	 *            用户
	 * @param limit
	 *            时间限制
	 * @return
	 */
	public String getCheckOutRecordSQL(Reader reader, String limit) {
		String tmp = "SELECT book_name,author,reader_name,date_borrow,date_return,loss "
				+ "FROM book,borrow,reader WHERE "
				+ "reader.reader_id = borrow.reader_id AND "
				+ "book.book_id = borrow.book_id";
		if (limit == null) {
			return tmp;
		} else if (limit.equals("半年内")) {
			tmp += " AND borrow.date_borrow between dateadd(MONTH,-6,GETDATE()) and GETDATE()";
		} else if (limit.equals("一年内")) {
			tmp += " AND borrow.date_borrow between dateadd(MONTH,-12,GETDATE()) and GETDATE()";
		}
		tmp += " AND reader.reader_id ='" + reader.getId() + "'";
		return tmp;
	}

	public String getOverDueRecordSQL() {
		return "SELECT borrow.borrow_id,borrow.book_id,borrow.reader_id,borrow.date_borrow,"
				+ "datediff(DAY,borrow.date_borrow,GETDATE())-member_level.days,book.book_name "
				+ "FROM borrow,reader,member_level,book "
				+ "WHERE borrow.reader_id = reader.reader_id "
				+ "AND book.book_id = borrow.book_id "
				+ "AND reader.level = member_level.level "
				+ "AND member_level.days < datediff(DAY,borrow.date_borrow,GETDATE()) "
				+ "AND borrow.date_return is null "
				+ "AND borrow.loss = '0'";
	}
	
	public String getReaderListSQL(String name, String id) {
		StringBuffer sb = new StringBuffer("SELECT * FROM reader WHERE ");
		if (name != null && !name.equals("")) {
			sb.append("reader_name LIKE '%" + name + "%' AND ");
		}
		if (id != null && !id.equals("")) {
			sb.append("reader_id='" + id + "' AND ");
		}
		sb.append("level <> 'ADMIN'");
		return sb.toString();
	}

	/**
	 * 拼接内容——读者详细信息
	 * @param reader
	 * @param maxNumber 该读者最大可借书数量
	 * @return
	 */
	public String getReaderInfo(Reader reader, int maxNumber) {
		StringBuffer sb = new StringBuffer();
		sb.append("姓名 ： " + reader.getName() + "\n");
		sb.append("读者编号 ： " + reader.getId() + "\n");
		sb.append("性别 ： " + reader.getSex() + "\n");
		sb.append("生日 ： " + reader.getBirthday() + "\n");
		if (reader.getPhone() == 0) {
			sb.append("固定电话 ： 无记录 \n");
		} else {
			sb.append("固定电话 ：" + reader.getPhone() + " \n");
		}
		sb.append("手机 ： " + reader.getMobile() + "\n");
		sb.append("证件类型 ： " + reader.getCardName() + "\n");
		sb.append("证件号 ： " + reader.getCardId() + "\n");
		sb.append("会员等级 ： " + reader.getLevel() + "\n");
		sb.append("注册时间 ： " + reader.getSignUpTime() + "\n");
		sb.append("当前借书 ： " + reader.getBorrowNumber() + "本\n");
		sb.append("最多借书 ： " + maxNumber + "本\n");
		return sb.toString();
	}

	/**
	 * 拼接内容——书本详细信息
	 * @param book
	 * @param category
	 * @return
	 */
	public String getBookInfo(Book book, String category) {
		StringBuffer sb = new StringBuffer();
		sb.append("书本编号： " + book.getBookId() + " \n");
		sb.append("书名： " + book.getBookName() + " \n");
		sb.append("作者： " + book.getAuthor() + " \n");
		sb.append("出版社： " + book.getPublishing() + " \n");
		sb.append("分类： " + category + " \n");
		sb.append("价格： " + book.getPrice() + " \n");
		sb.append("入库时间： " + book.getPublishDate() + " \n");
		sb.append("在馆数量： " + book.getQuanIn() + " \n");
		sb.append("外借数量： " + book.getQuanOut() + " \n");
		sb.append("遗失数量： " + book.getQuanLoss() + " \n");
		return sb.toString();
	}

	/**
	 * 
	 * @param book
	 * @return 根据类别ID查询对应的类别名的SQL语句
	 */
	public String getCategorySQL(Book book) {
		return "SELECT category FROM book_category WHERE category_id='"
				+ book.getCategoryid() + "'";
	}

	/**
	 * 
	 * @return 获取最大bookID号的SQL语句
	 */
	public String getLastBookIDSQL() {
		return "SELECT TOP 1 book_id FROM book ORDER BY book_id DESC";
	}

	/**
	 * 
	 * @param book
	 * @return 插入新书的SQL语句
	 */
	public String getLayUpBookSQL(Book book) {
		return "INSERT INTO book " + "VALUES ('" + book.getBookId() + "','"
				+ book.getBookName() + "','" + book.getAuthor() + "','"
				+ book.getPublishing() + "','" + book.getCategoryid() + "',"
				+ book.getPrice() + ", getdate() ," + book.getQuanIn() + ","
				+ book.getQuanOut() + "," + book.getQuanLoss() + ");";
	}

	public String getAllCategorySQL() {
		return "SELECT category FROM book_category";
	}

	public String getAllLevelSQL() {
		return "SELECT level FROM member_level WHERE level <> 'ADMIN'";
	}

	public String getLastReaderIDSQL() {
		return "SELECT TOP 1 reader_id FROM reader ORDER BY reader_id DESC";
	}

	public String getSignUpReaderSQL(Reader reader) {
		return "INSERT INTO reader  " + "VALUES ('" + reader.getId() + "','"
				+ reader.getName() + "','" + reader.getSex() + "','"
				+ reader.getBirthday() + "',NULL,'" + reader.getMobile()
				+ "','" + reader.getCardName() + "','" + reader.getCardId()
				+ "','" + reader.getLevel() + "',getdate(),'"
				+ reader.getPassword() + "',0)";
	}

	public String getUpdatePhoneSQL(Reader reader) {
		return "UPDATE reader SET phone='" + reader.getPhone()
				+ "' WHERE reader_id='" + reader.getId() + "'";
	}
	
	/**
	 * 挂失相关语句，共有三条。对borrow,book,reader三个表进行操作。
	 * @param record
	 * @return
	 */
	public String getUpdateLossSQL(CheckOutRecord record) {
		System.out.println(record.getRecordID());
		return "UPDATE borrow SET loss='1' WHERE borrow_id=" + record.getRecordID() + ";"
				+ "UPDATE book SET quantity_out=quantity_out-1,quantity_loss=quantity_loss+1 WHERE "
				+ "book_id='" + record.getBookID() + "';"
				+ "UPDATE reader SET borrow_number=borrow_number-1 WHERE reader_id='"
				+ record.getReaderID() + "';";
	}
	
	public String getUpdateReturnSQL(CheckOutRecord record) {
		return "UPDATE borrow SET date_return=getdate() WHERE borrow_id='" + record.getRecordID()
				+ "';UPDATE book SET quantity_out=quantity_out-1,quantity_in=quantity_in+1 WHERE "
				+ "book_id='" + record.getBookID() + "';"
				+ "UPDATE reader SET borrow_number=borrow_number-1 WHERE reader_id='"
				+ record.getReaderID() + "';";
	}
	
	public String getBookReturnSQL() {
		return "SELECT borrow_id,book.book_id,book_name,author,reader_name,"
				+ "date_borrow,reader.reader_id "
				+ "FROM borrow,book,reader "
				+ "WHERE date_return is NULL AND loss=0 "
				+ "AND book.book_id = borrow.book_id "
				+ "AND reader.reader_id = borrow.reader_id";
	}
	
}
