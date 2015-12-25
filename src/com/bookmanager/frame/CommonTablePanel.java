package com.bookmanager.frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.bookmanager.sql.common.MyRender;

public class CommonTablePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public static final String CHECK = "CHECK";
	public static final String INFO = "INFO";
	
	private JTable table;

	public CommonTablePanel(Object[][] data, String[] tableHead, int[] width,
			int height, boolean haveButton, String commond) {
		
		table = new JTable(data, tableHead);
		boolean[] isNull = new boolean[data.length];
		if( haveButton ) {
			if( commond.equals(CommonTablePanel.CHECK)) {
				isNull = getNullArray(data);
			}
			setCellEditorAndRender(isNull, tableHead, commond);
		}
		this.setTableColumnWidthAndHeight(this.table, width, height);
		JScrollPane scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// 1*1格子布局，方便填满
		this.setLayout(new GridLayout(1, 1));
		// 加边界留白
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.add(scrollPane);
		this.setBackground(Color.LIGHT_GRAY);
	}

	private void setAllButtonListener(MyRender myRender, boolean[] isNull, String commond) {
		for (int i = 0; i < isNull.length; i++) {
			if ( !isNull[i] ) {
				myRender.getButton(i).setActionCommand(commond);
				myRender.getButton(i).addActionListener(this);
			}
		}
	}

	private boolean[] getNullArray(Object[][] data) {
		boolean[] isNull = new boolean[data.length];
		for (int i = 0; i < data.length; i++) {
			if((int)data[i][4] <= 0) {
				isNull[i] = true;
			}
		}
		return isNull;
	}

	private TableColumnModel setTableColumnWidthAndHeight(JTable table,
			int[] width, int height) {
		// TODO Auto-generated method stub
		TableColumnModel columns = table.getColumnModel();
		for (int i = 0; i < width.length; i++) {
			TableColumn column = columns.getColumn(i);
			column.setPreferredWidth(width[i]);
		}
		table.setRowHeight(height);
		return columns;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals(CommonTablePanel.CHECK)) {
			MainFrame mf = (MainFrame) JOptionPane.getFrameForComponent(this);
			mf.checkOutBook(table.getSelectedRow());
		}
	}

	private void setCellEditorAndRender(boolean[] isNull, String[] tableHead,
			String commond) {
		MyRender render = new MyRender(isNull, tableHead[tableHead.length-1]);
		// 设置编辑器
		table.getColumnModel().getColumn(tableHead.length - 1)
				.setCellEditor(render);
		table.getColumnModel().getColumn(tableHead.length - 1)
				.setCellRenderer(render);
		table.getColumnModel().getColumn(tableHead.length - 1)
				.setHeaderValue(null);
		this.setAllButtonListener(render, isNull, commond);
	}
}