package com.bookmanager.frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class CommonBookListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int[] width = { 50, 100, 50, 150, 80, 50 };
	private static final int height = 30;
	private JTable table = null;
	private DefaultTableModel model = null;
	private JScrollPane scrollPane = null;
	private MyRender myRender;

	public CommonBookListPanel(Object[][] data, String[] head) {

		model = new DefaultTableModel(data, head);
		table = new JTable(model);
		myRender = new MyRender();
		table.getColumnModel().getColumn(head.length - 1)
				.setCellEditor(myRender);// ÉèÖÃ±à¼­Æ÷
		table.getColumnModel().getColumn(head.length - 1)
				.setCellRenderer(myRender);
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		this.setTableColumnWidthAndHeight(table, CommonBookListPanel.width,
				CommonBookListPanel.height);
		scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// 1*1¸ñ×Ó²¼¾Ö£¬·½±ãÌîÂú
		this.setLayout(new GridLayout(1, 1));
		// ¼Ó±ß½çÁô°×
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

	public MyRender getRender() {
		return this.myRender;
	}
}

/**
 * äÖÈ¾Æ÷±à¼­Æ÷
 * 
 * @author Chenpix
 *
 */
class MyRender extends AbstractCellEditor implements TableCellRenderer,
		TableCellEditor {

	private static final long serialVersionUID = 1L;
	private JButton button = null;

	public MyRender() {
		button = new JButton("½èÔÄ");
	}

	public JButton getButton() {
		return button;
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		return button;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		return button;
	}

}