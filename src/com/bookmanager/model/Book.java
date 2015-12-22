package com.bookmanager.model;

import java.sql.Date;

public class Book {

	private String bookId;
	private String bookName;
	private String author;
	private String publishing;
	private String categoryid;
	private double price;
	private String publishDate;
	private int quanIn;
	private int quanOut;
	private int quanLoss;

	// public Book(String a) {
	// this.bookId = "";
	// this.bookName = "";
	// author = a;
	// publishing = "";
	// categoryid = "";
	// price = -1;
	// publishDate = "";
	// quanIn = -1;
	// quanOut = -1;
	// quanLoss = -1;
	// }

	public Book() {
		this("", "", "", "");
	}

	public Book(String bookId, String bookName, String author, String publishing) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.author = author;
		this.publishing = publishing;
		this.categoryid = "";
		this.price = -1;
		this.publishDate = "";
		this.quanIn = -1;
		this.quanOut = -1;
		this.quanLoss = -1;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublishing() {
		return publishing;
	}

	public void setPublishing(String publishing) {
		this.publishing = publishing;
	}

	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date date) {
		this.publishDate = date.toString();
	}

	public int getQuanIn() {
		return quanIn;
	}

	public void setQuanIn(int quanIn) {
		this.quanIn = quanIn;
	}

	public int getQuanOut() {
		return quanOut;
	}

	public void setQuanOut(int quanOut) {
		this.quanOut = quanOut;
	}

	public int getQuanLoss() {
		return quanLoss;
	}

	public void setQuanLoss(int quanLoss) {
		this.quanLoss = quanLoss;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		if (!this.bookId.equals("") || !this.bookName.equals("")
				|| !this.author.equals("") || !this.publishing.equals("")
				|| !this.categoryid.equals("") || this.price != -1
				|| !this.publishDate.equals("") || this.quanIn != -1
				|| this.quanOut != -1 || this.quanLoss != -1) {
			return false;
		}
		return true;
	}

}
