package com.bookmanager.model;

import java.sql.Date;

public class Reader {

	private	String id;
	private String name;
	private String sex;
	private String birthday;
	private int phone;
	private String Mobile;
	private String cardName;
	private String cardId;
	private String level;
	private String signUpTime;
	private String password;
	private int borrowNumber;
	
	public Reader(String id, String password) {
		this.id = id;
		this.password = password;
	}
	
	public Reader() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(Date date) {
		if(date != null) {
			this.birthday = date.toString();
		}
		else {
			this.birthday = null;
		}
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSignUpTime() {
		return signUpTime;
	}

	public void setSignUpTime(String signUpTime) {
		this.signUpTime = signUpTime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSignUpTime(Date date) {
		// TODO Auto-generated method stub
		this.signUpTime = date.toString();
	}
	
	public int getBorrowNumber() {
		return borrowNumber;
	}

	public void setBorrowNumber(int borrowNumber) {
		this.borrowNumber = borrowNumber;
	}

}
