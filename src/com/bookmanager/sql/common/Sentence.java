package com.bookmanager.sql.common;

import com.bookmanager.model.Book;
import com.bookmanager.model.Reader;

public class Sentence {

	public static String getReaderSQL(Reader reader) {
		return "SELECT * FROM reader WHERE reader_id collate Chinese_PRC_CS_AS='"
				+ reader.getId() + "'";
	}

	public static String getBookListSQL(Book book) {
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

	public static String getBookCheckOutSQL(Book book) {
		return "UPDATE book SET quantity_in=" + book.getQuanIn()
				+ ", quantity_out=" + book.getQuanOut() + " WHERE book_id='"
				+ book.getBookId() + "'";
	}
}
