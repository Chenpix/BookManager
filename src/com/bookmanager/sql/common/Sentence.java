package com.bookmanager.sql.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bookmanager.model.Book;
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

	public String getCheckOutRecordSQL(Book book, Reader reader) {
		return "INSERT INTO [bookmanager].[dbo].[borrow] "
				+ "(reader_id, book_id, date_borrow, date_return, loss) "
				+ "VALUES ('"+ reader.getId()+ "','"+ book.getBookId()
				+ "',getdate(),NULL,0);";
	}

	public String getUserBookLimitSQL(Reader reader) {
		return "SELECT numbers FROM member_level WHERE level='"
				+ reader.getLevel() + "'";
	}

	public String getUpdateBorrowNumberSQL(Reader reader) {
		return "UPDATE reader SET borrow_number=" + reader.getBorrowNumber()
				+ " WHERE reader_id='" + reader.getId()+"'";
	}
}
