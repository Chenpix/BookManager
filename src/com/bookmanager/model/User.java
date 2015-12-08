package com.bookmanager.model;

public class User {

	private String name;
	private String password;
	private int type;
	
	public User() {
		// TODO Auto-generated constructor stub
		this.name = null;
		this.password = null;
	}
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "password = "+this.password + " type = "+ this.type;
	}
}
