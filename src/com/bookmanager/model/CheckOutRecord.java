package com.bookmanager.model;

import java.sql.Date;

public class CheckOutRecord {

	private String readerID;
	private String readerName;
	private String author;
	private String bookID;
	private String bookName;
	private String dateBorrow;
	private String dateReturn;
	private boolean loss;
	
	public String getReaderID() {
		return readerID;
	}

	public void setReaderID(String readerID) {
		this.readerID = readerID;
	}

	public String getReaderName() {
		return readerName;
	}

	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getDateBorrow() {
		return dateBorrow;
	}

	public void setDateBorrow(Date dateBorrow) {
		this.dateBorrow = dateBorrow.toString();
	}

	public String getDateReturn() {
		return dateReturn;
	}

	public void setDateReturn(String dateReturn) {
		if(dateReturn == null) {
			this.dateReturn = "ÉÐÎ´¹é»¹";
		}
		else {
			this.dateReturn = dateReturn;
		}
	}

	public boolean isLoss() {
		return loss;
	}

	public void setLoss(boolean loss) {
		this.loss = loss;
	}

	public void setLoss(int loss) {
		this.loss = (loss == 1? true:false);
	}
	
	public CheckOutRecord() {}
}
