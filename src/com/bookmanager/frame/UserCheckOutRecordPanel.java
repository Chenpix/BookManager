package com.bookmanager.frame;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class UserCheckOutRecordPanel extends JPanel {
	
	private final String[] checkOutRecordHead = {"����", "����", "������",
		"����ʱ��", "�黹ʱ��", "�Ƿ���ʧ"};
	private final int[] width = { 100, 50, 50, 80, 80, 30 };
	private final int height = 30;
	
	private JTable table;
	
	public UserCheckOutRecordPanel(Object[][] data) {
		table = new JTable(data, this.checkOutRecordHead);
		this.setTableColumnWidthAndHeight(this.table, this.width,
				this.height);
		JScrollPane scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// 1*1���Ӳ��֣���������
		this.setLayout(new GridLayout(1, 1));
		// �ӱ߽�����
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.add(scrollPane);
		this.setBackground(Color.LIGHT_GRAY);
	}
	
	private TableColumnModel setTableColumnWidthAndHeight(JTable table, int[] width,
			int height) {
		// TODO Auto-generated method stub
		TableColumnModel columns = table.getColumnModel();  
	    for (int i = 0; i < width.length; i++) {  
	        TableColumn column = columns.getColumn(i);  
	        column.setPreferredWidth(width[i]);  
	    }  
	    table.setRowHeight(height);
	    return columns;  
	}
}
