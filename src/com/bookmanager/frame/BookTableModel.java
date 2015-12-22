package com.bookmanager.frame;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

public class BookTableModel extends AbstractTableModel {

	private String[] head = { "书号", "书名", "作者", "出版社", "在馆数量", "  " };
	private Object[][] data;
	private Class[] cellType = { Object.class, Object.class, Object.class,
			Object.class, JButton.class };

	public BookTableModel(Object[][] data) {
		// TODO Auto-generated constructor stub
		this.data = data;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return head.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
