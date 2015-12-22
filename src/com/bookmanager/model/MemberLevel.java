package com.bookmanager.model;

public class MemberLevel {

	private String level;//会员等级（名称）
	private int days;//最长借阅天数
	private int number;//可同时借阅数量
	private int fee;//办理费用
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

}
